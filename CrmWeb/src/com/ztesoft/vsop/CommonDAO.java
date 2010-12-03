package com.ztesoft.vsop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.vo.ProdRelationVO;

public class CommonDAO {
	private static Logger logger = Logger.getLogger(CommonDAO.class);
	private String getProdServiceAbilitysSpliByComma(String productId){
		StringBuffer serviceCapabilitys=new StringBuffer("");
		List prodServiceAbilitys=DcSystemParamManager.getInstance().getProductServiceAbilitysByid(productId);
		if(prodServiceAbilitys!=null){
			for(int j=0;j<prodServiceAbilitys.size();j++){
				String tmp=(String)prodServiceAbilitys.get(j);
				serviceCapabilitys.append(tmp+",");
			}
		}
		return serviceCapabilitys.toString();
	}
	public ArrayList getSpInfoFromCache(String productids)throws SQLException {
		//cooltan改写从缓存读取数据 传入参数也改变
		ArrayList lst = new ArrayList();
		String [] products=productids.split(",");
		if(products!=null&&products.length>0){
			for(int i=0;i<products.length;i++){
				String productId=products[i];
				SpProductVo spProductVo= new SpProductVo(); 
				spProductVo.setServiceId(productId);
				//获取产品关联业务能力
				String serviceCapabilitys=this.getProdServiceAbilitysSpliByComma(productId);
				spProductVo.setServiceCapabilityId(serviceCapabilitys);
				spProductVo.setState(DcSystemParamManager.getInstance().getProductStateById(productId));
				spProductVo.setSpState(DcSystemParamManager.getInstance().getSpcpStateById(productId));
				spProductVo.setNameCn(DcSystemParamManager.getInstance().getProductnameById(productId));
				lst.add(spProductVo);
			}
		}
		return lst;
		
	}
	/**
	 * 鉴权不再使用这个方法，保留兼容其他入口的调用 关联方式需要修改
	 * @param conn
	 * @param cond
	 * @return
	 * @throws SQLException
	 */
	public ArrayList getSpInfo(Connection conn,String cond)throws SQLException {
		ArrayList lst = new ArrayList();
		Statement stmt=null; 
		ResultSet rs=null; 
		String sql = "select PRODUCT_ID,PRODUCT_STATE_CD,STATE,PRODUCT_NAME from PRODUCT a left join PARTNER b on a.PRODUCT_PROVIDER_ID=b.PARTNER_ID where "+cond;
		try{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next()) 
			{ 
				SpProductVo spProductVo= new SpProductVo(); 
				spProductVo.setServiceId(rs.getString("PRODUCT_ID"));
				spProductVo.setServiceCapabilityId(getServiceCapability(conn,rs.getString("PRODUCT_ID")));
				spProductVo.setState(rs.getString("PRODUCT_STATE_CD"));
				spProductVo.setSpState(rs.getString("STATE"));
				spProductVo.setNameCn(rs.getString("PRODUCT_NAME"));
				lst.add(spProductVo);
			}
		}
		catch(SQLException se){
			se.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally{
			try{
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
			}catch(Exception se){
				throw new DAOSystemException("SQL Exception while closing "
						+ "Statement : \n" + se);
			}
		}
		return lst;
	}
	
	public ProductVo getProductInfo(Connection conn,String productNo, String productId)throws SQLException {
		PreparedStatement stmt=null; 
		ResultSet rs=null; 
		ProductVo productVo= new ProductVo(); 
		String sql = "select ACC_NBR,LAN_ID,PROD_INST_ID,STATE_CD,PAYMENT_MODE_CD,PRODUCT_ID from PROD_INST where ACC_NBR=? and PRODUCT_ID= ?";
		try{
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, productNo);
			stmt.setString(2, productId);
			rs=stmt.executeQuery();
			while (rs.next()) 
			{ 
				productVo.setProductNo(rs.getString("ACC_NBR"));
				productVo.setProductId(rs.getString("PROD_INST_ID"));
				productVo.setLanId(rs.getString("LAN_ID"));
				productVo.setUserState(rs.getString("STATE_CD"));
				productVo.setPaymentModeCd(rs.getString("PAYMENT_MODE_CD"));
				productVo.setProductSpec(rs.getString("PRODUCT_ID"));//产品规格id
			}
		}
		catch(SQLException se){
			se.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally{
			try{
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
			}catch(Exception se){
				throw new DAOSystemException("SQL Exception while closing "
						+ "Statement : \n" + se);
			}
		}
		return productVo;
	}

/*	public String getProductOfferId(Connection conn,String prodInstId,String productId)throws SQLException {
//		ArrayList lst = new ArrayList();
		Statement stmt=null; 
		ResultSet rs=null; 
		String prodOfferId = "";
		String sql = "select PROD_OFFER_ID where ORDER_RELATION=?"+prodInstId+" and product_id = ?"+productId+" STATE<>'00X'";
		try{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next()) 
			{ 
				prodOfferId = rs.getString("PROD_OFFER_ID");
			}
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		finally{
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
		}
		return prodOfferId;
	}	*/
	
	public void getServiceCapability(Connection conn,ProductVo vo)throws SQLException {
		ArrayList lst = new ArrayList();
		PreparedStatement stmt=null; 
		ResultSet rs=null; 
		/*String sql = "select c.SERVICE_CODE as PRODUCT_ID from BIZ_CAPABILITY_INST a,PRODUCT b," +
				" PRODUCT_SERVICE_ABILITY_REL c where a.product_id=b.product_id " +
				" and a.product_id=c.product_id and c.REL_TYPE='1' and PROD_INST_ID=?";*/
		String sql = "select PRODUCT_ID from BIZ_CAPABILITY_INST a " +
				" where  PROD_INST_ID=?";
		try{
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, vo.getProductId());
			rs=stmt.executeQuery();
			while (rs.next()) 
			{ 
				String productId=rs.getString("PRODUCT_ID");
				List tmp=DcSystemParamManager.getInstance().getProductServiceAbilitysByid(productId);
				if(tmp!=null){
					for(int i=0;i<tmp.size();i++){
						HashMap map = new HashMap();
						String serviceCode=(String)tmp.get(i);
						map.put("productId",serviceCode);
						map.put("productName",DcSystemParamManager.getInstance().getServicenameByCode(serviceCode));
						lst.add(map);
					}
				}
				
				/*String productId=rs.getString("PRODUCT_ID");
				map.put("productId",productId);
				map.put("productName",DcSystemParamManager.getInstance().getServicenameByCode(productId));
				lst.add(map);*/
			}
			vo.setServiceCapability(lst);
		}
		catch(SQLException se){
			se.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally{
			try{
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
			}catch(Exception se){
				throw new DAOSystemException("SQL Exception while closing "
						+ "Statement : \n" + se);
			}
		}
		//return vo;
	}	

	public void getIncrProdInst(Connection conn,ProductVo vo,String productNo)throws SQLException {
		long s = System.currentTimeMillis();
		ArrayList lst = new ArrayList();
		PreparedStatement stmt=null; 
		ResultSet rs=null; 
		String sql = "select a.PRODUCT_ID,PPROD_OFFER_ID,a.package_id," +
				" a.STATE from ORDER_RELATION a  where  " +
				" a.state in ('00B','00A') and PROD_INST_ID=?";
		try{
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, vo.getProductId());
			rs=stmt.executeQuery();
			while (rs.next()) 
			{ 	
				SpProductVo spProductVo= new SpProductVo();
				String productId=rs.getString("PRODUCT_ID");
				spProductVo.setServiceId(productId);
				//spProductVo.setServiceCapabilityId(getServiceCapability(conn,productId));
				String tmp=this.getProdServiceAbilitysSpliByComma(productId);
				spProductVo.setServiceCapabilityId(tmp);
				spProductVo.setState(rs.getString("STATE"));
				String productName=DcSystemParamManager.getInstance().getProductnameById(productId);
				spProductVo.setNameCn(productName);
				spProductVo.setProductOfferId(rs.getString("PPROD_OFFER_ID"));
				spProductVo.setPackageId(rs.getString("PACKAGE_ID"));
				spProductVo.setType("M");
				spProductVo.setProductNo(productNo);
				lst.add(spProductVo);
			}
			vo.setSpProductInfo(lst);
		}
		catch(SQLException se){
			se.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally{
			try{
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
			}catch(Exception se){
				throw new DAOSystemException("SQL Exception while closing "
						+ "Statement : \n" + se);
			}
		}
		logger.debug("#getIncrProdInst sql->" + sql +",PROD_INST_ID->" + vo.getProductId()+"#getIncrProdInst cost" + (System.currentTimeMillis()-s));
		//return vo;
	}
	
	public ArrayList getBizRestraintFromCache(String productids)throws SQLException {
		//cooltan改写从缓存读取数据
		ArrayList lst = new ArrayList();
		String [] products=productids.split(",");
		if(products!=null&&products.length>0){
			//二层循环
			for(int i=0;i<products.length;i++){
				String productId=products[i];
				List prodRelations=DcSystemParamManager.getInstance().getProdRelationById(productId);
				if(prodRelations!=null){
					for(int j=0;j<prodRelations.size();j++){
						ProdRelationVO vo=(ProdRelationVO)prodRelations.get(j);
						BizRestraintVo bizRestraintVo= new BizRestraintVo(); 
						bizRestraintVo.setaObjectId(vo.getProdId());
						bizRestraintVo.setzObjectId(vo.getProProdId());
						String aObjectName=DcSystemParamManager.getInstance().getProductnameById(vo.getProdId());
						String zObjectName=DcSystemParamManager.getInstance().getProductnameById(vo.getProProdId());
						bizRestraintVo.setaObjectName(aObjectName);
						bizRestraintVo.setzObjectName(zObjectName);
						bizRestraintVo.setRestraintType(vo.getRelationTypeCd());
						lst.add(bizRestraintVo);
					}
				}
				
			}
		}
		return lst;
		
	}
	
	public ArrayList getBizRestraint(Connection conn,String cond)throws SQLException {
		ArrayList lst = new ArrayList();
		Statement stmt=null; 
		ResultSet rs=null; 
		String sql1 = "select PRODUCT_ID,(select PRODUCT_NAME from PRODUCT b where b.PRODUCT_ID=a.PRODUCT_ID) as a_object_name,PRO_PRODUCT_ID,(select PRODUCT_NAME from PRODUCT b where b.PRODUCT_ID=a.PRO_PRODUCT_ID) as z_object_name,RELATION_TYPE_CD from PRODUCT_RELATION a where a.PRODUCT_ID in ("+cond+")";
		String sql2 = "select PRODUCT_ID,(select PRODUCT_NAME from PRODUCT b where b.PRODUCT_ID=a.PRODUCT_ID) as a_object_name,PRO_PRODUCT_ID,(select PRODUCT_NAME from PRODUCT b where b.PRODUCT_ID=a.PRO_PRODUCT_ID) as z_object_name,RELATION_TYPE_CD from PRODUCT_RELATION a where a.PRO_PRODUCT_ID in ("+cond+")";
		String sql = sql1 + " union " + sql2;
		System.out.println(sql);
		try{
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery(sql);
			while (rs.next()) 
			{ 
				BizRestraintVo bizRestraintVo= new BizRestraintVo(); 
				bizRestraintVo.setaObjectId(rs.getString("PRODUCT_ID"));
				bizRestraintVo.setzObjectId(rs.getString("PRO_PRODUCT_ID"));
				bizRestraintVo.setaObjectName(rs.getString("a_object_Name"));
				bizRestraintVo.setzObjectName(rs.getString("z_object_Name"));
				bizRestraintVo.setRestraintType(rs.getString("RELATION_TYPE_CD"));
				lst.add(bizRestraintVo);
			}
		}
		catch(SQLException se){
			se.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally{
			try{
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
			}catch(Exception se){
				throw new DAOSystemException("SQL Exception while closing "
						+ "Statement : \n" + se);
			}
			
		}
		return lst;
	}
	//获取业务能力
	public String getServiceCapability(Connection conn,String productId)throws SQLException {
		PreparedStatement stmt=null; 
		ResultSet rs=null; 
		String retStr = "";
		String sql = "select SERVICE_CODE from PRODUCT_SERVICE_ABILITY_REL where product_id=?";
		try{
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, productId);
			rs=stmt.executeQuery();
			while (rs.next()) 
			{ 
				String serviceCode = rs.getString("SERVICE_CODE");
				retStr = retStr + serviceCode + ",";
			}
		}
		catch(SQLException se){
			se.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally{
			try{
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
			}catch(Exception se){
				throw new DAOSystemException("SQL Exception while closing "
						+ "Statement : \n" + se);
			}
		}
		return retStr;
	}
}
