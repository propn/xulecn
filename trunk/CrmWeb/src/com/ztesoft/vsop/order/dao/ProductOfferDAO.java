package com.ztesoft.vsop.order.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.order.vo.ProdRelationVO;
import com.ztesoft.vsop.order.vo.ProductOPCodeVO;
import com.ztesoft.vsop.order.vo.ProductOfferVO;
;

public class ProductOfferDAO {
	private static String SEQ_PROD_OFFER_ID = "SEQ_PROD_OFFER_ID";
	private static String SQL_INSERT = "insert into PROD_OFFER (PROD_OFFER_ID,FEE_SET_FLAG,PROD_OFFER_SUB_TYPE,PROD_OFFER_NAME,STATE_DATE,PROD_OFFER_PUBLISHER,STATE,EFF_DATE,EXP_DATE,MANAGE_GRADE,OFFER_NBR,OFFER_PROVIDER_ID,BRAND_ID,SERV_BRAND_ID,TEMPLET_ID,DEFAULT_TIME_PERIOD,OFFER_DESC,PRICING_DESC,PNAME_CN,PNAME_EN,PDES_CN,PDES_EN,CHARGINGPOLICY_CN,CHARGINGPOLICY_ID,SUB_OPTION,PRESENT_OPTION,CORP_ONLY,SCOPE,PACKAGE_HOST)" +
			" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static String SQL_UPDATE = "update PROD_OFFER set PROD_OFFER_ID=?,FEE_SET_FLAG=?,PROD_OFFER_SUB_TYPE=?,PROD_OFFER_NAME=?,STATE_DATE=?,PROD_OFFER_PUBLISHER=?,STATE=?,EFF_DATE=?,EXP_DATE=?,MANAGE_GRADE=?,OFFER_NBR=?,OFFER_PROVIDER_ID=?,BRAND_ID=?,SERV_BRAND_ID=?,TEMPLET_ID=?,DEFAULT_TIME_PERIOD=?,OFFER_DESC=?,PRICING_DESC=?,PNAME_CN=?,PNAME_EN=?,PDES_CN=?,PDES_EN=?,CHARGINGPOLICY_CN=?,CHARGINGPOLICY_ID=?,SUB_OPTION=?,PRESENT_OPTION=?,CORP_ONLY=?,SCOPE=?,PACKAGE_HOST=? where PROD_OFFER_ID=?";	
	
	private static String SEQ_PROD_OFFER_ROLE_CD = "SEQ_PROD_OFFER_ROLE_CD";
	private static String SQL_INSERT_PROD_OFFER_ROLE = "insert into PROD_OFFER_DETAIL_ROLE(PROD_OFFER_ROLE_CD,PROD_OFFER_ID,PRODUCT_ID) values (?,?,?)";
	private static String SQL_DELETE_PROD_OFFER_ROLE = "delete from PROD_OFFER_DETAIL_ROLE where PROD_OFFER_ID=?";

	private static String SEQ_PROD_OFFER_REL_ID= "SEQ_PROD_OFFER_REL_ID";
	private static String SQL_INSERT_PRODUCT_RELATION = "insert into PROD_OFFER_REL(PROD_OFFER_REL_ID,RELATION_TYPE_ID,OFFER_A_ID,OFFER_Z_ID,EFF_DATE,EXP_DATE) values (?,?,?,?,?,?)";
	private static String SQL_DELETE_PRODUCT_RELATION = "delete from PROD_OFFER_REL where OFFER_A_ID=? and OFFER_Z_ID=? and RELATION_TYPE_ID=?";
		
	
	public static boolean saveProductOfferInfo(ProductOfferVO productOfferVO,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			long productOfferId = getSequence(SEQ_PROD_OFFER_ID, conn);
			//保存订单
			insertSt = conn.prepareStatement(SQL_INSERT);
			int index = 1;
			insertSt.setLong(index++, productOfferId);//PROD_OFFER_ID
			insertSt.setString(index++, productOfferVO.getFeeSetFlag());//FEE_SET_FLAG, 
			insertSt.setString(index++, productOfferVO.getProdOfferSubType());//PROD_OFFER_SUB_TYPE,
			insertSt.setString(index++, productOfferVO.getPNameCN());//PROD_OFFER_NAME,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//STATE_DATE,
			insertSt.setString(index++, "");//PROD_OFFER_PUBLISHER,
			insertSt.setString(index++, productOfferVO.getStatus());//STATE,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//EFF_DATE,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//EXP_DATE,
			insertSt.setString(index++, "");//MANAGE_GRADE,
			insertSt.setString(index++, productOfferVO.getPackageID());//OFFER_NBR,
			insertSt.setString(index++, "");//OFFER_PROVIDER_ID,
			insertSt.setString(index++, "");//BRAND_ID,
			insertSt.setString(index++, "");//SERV_BRAND_ID,
			insertSt.setString(index++, "");//TEMPLET_ID,
			insertSt.setString(index++, "");//DEFAULT_TIME_PERIOD,
			insertSt.setString(index++, productOfferVO.getPDesCN());//OFFER_DESC,
			insertSt.setString(index++, "");//PRICING_DESC,
			insertSt.setString(index++, productOfferVO.getPNameCN());//PNAME_CN,
			insertSt.setString(index++, productOfferVO.getPNameEN());//PNAME_EN,
			insertSt.setString(index++, productOfferVO.getPDesCN());//PDES_CN,
			insertSt.setString(index++, productOfferVO.getPDesEn());//PDES_EN
			insertSt.setString(index++, productOfferVO.getChargingPolicyCN());//CHARGINGPOLICY_CN
			insertSt.setString(index++, productOfferVO.getChargingPolicyID());//CHARGINGPOLICY_ID
			insertSt.setString(index++, productOfferVO.getSubOption());//SUB_OPTION
			insertSt.setString(index++, productOfferVO.getPresentOption());//PRESENT_OPTION
			insertSt.setString(index++, productOfferVO.getCorpOnly());//CORP_ONLY
			insertSt.setString(index++, productOfferVO.getScope());//SCOPE
			insertSt.setString(index++, productOfferVO.getPackageHost());//PACKAGE_HOST
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
			//如果产品列表不为空
			if(productOfferVO.getProductLst()!=null){
				for(int i=0;i<productOfferVO.getProductLst().size();i++){
					String productId = getProductIdByCode((String)productOfferVO.getProductLst().get(i),systemId,conn);
					saveProdOfferRoleInfo(String.valueOf(productOfferId),productId,systemId,conn);
				}
			}
			
			//如果ProdRelationLst不为空
			if(productOfferVO.getProdOfferRelationLst()!=null){
				for(int i=0;i<productOfferVO.getProdOfferRelationLst().size();i++){
					ProdRelationVO prodRelationVO = (ProdRelationVO)productOfferVO.getProdOfferRelationLst().get(i);
					String pproductOfferId = getProductOfferIdByCode(prodRelationVO.getRProductID(),productOfferVO.getPackageHost(),conn);
					prodRelationVO.setRProductID(pproductOfferId);
					saveProdRelation(prodRelationVO,String.valueOf(productOfferId),conn);
				}
			}
			
			
		return true;
	}

	public static boolean updateProdcutOfferInfo(ProductOfferVO productOfferVO ,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			//保存订单
			insertSt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			insertSt.setString(index++, productOfferVO.getPackageID());//PROD_OFFER_ID
			insertSt.setString(index++, "03");//FEE_SET_FLAG, 
			insertSt.setString(index++, "0");//PROD_OFFER_SUB_TYPE,
			insertSt.setString(index++, productOfferVO.getPNameCN());//PROD_OFFER_NAME,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//STATE_DATE,
			insertSt.setString(index++, "");//PROD_OFFER_PUBLISHER,
			insertSt.setString(index++, productOfferVO.getStatus());//STATE,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//EFF_DATE,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//EXP_DATE,
			insertSt.setString(index++, "");//MANAGE_GRADE,
			insertSt.setString(index++, "");//OFFER_NBR,
			insertSt.setString(index++, "");//OFFER_PROVIDER_ID,
			insertSt.setString(index++, "");//BRAND_ID,
			insertSt.setString(index++, "");//SERV_BRAND_ID,
			insertSt.setString(index++, "");//TEMPLET_ID,
			insertSt.setString(index++, "");//DEFAULT_TIME_PERIOD,
			insertSt.setString(index++, productOfferVO.getPDesCN());//OFFER_DESC,
			insertSt.setString(index++, "");//PRICING_DESC,
			insertSt.setString(index++, productOfferVO.getPNameCN());//PNAME_CN,
			insertSt.setString(index++, productOfferVO.getPNameEN());//PNAME_EN,
			insertSt.setString(index++, productOfferVO.getPDesCN());//PDES_CN,
			insertSt.setString(index++, productOfferVO.getPDesEn());//PDES_EN
			insertSt.setString(index++, productOfferVO.getChargingPolicyCN());//CHARGINGPOLICY_CN
			insertSt.setString(index++, productOfferVO.getChargingPolicyID());//CHARGINGPOLICY_ID
			insertSt.setString(index++, productOfferVO.getSubOption());//SUB_OPTION
			insertSt.setString(index++, productOfferVO.getPresentOption());//PRESENT_OPTION
			insertSt.setString(index++, productOfferVO.getCorpOnly());//CORP_ONLY
			insertSt.setString(index++, productOfferVO.getScope());//SCOPE
			insertSt.setString(index++, productOfferVO.getPackageHost());//PACKAGE_HOST
			
			//where条件
			insertSt.setString(index++, productOfferVO.getPackageID());//PROD_OFFER_ID
			
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
			
			
			String productOfferId = getProductOfferIdByCode(productOfferVO.getPackageID(),productOfferVO.getPackageHost(),conn);
			deleteProdOfferRoleInfo(productOfferId,conn);
			//如果产品列表不为空
			if(productOfferVO.getProductLst()!=null){
				for(int i=0;i<productOfferVO.getProductLst().size();i++){
					String productId = getProductIdByCode((String)productOfferVO.getProductLst().get(i),systemId,conn);
					saveProdOfferRoleInfo(productOfferId,productId,systemId,conn);
				}
			}
			
			//如果ProdRelationLst不为空
			if(productOfferVO.getProdOfferRelationLst()!=null){
				for(int i=0;i<productOfferVO.getProdOfferRelationLst().size();i++){
					ProdRelationVO prodRelationVO = (ProdRelationVO)productOfferVO.getProdOfferRelationLst().get(i);
					String pproductOfferId = getProductOfferIdByCode(prodRelationVO.getRProductID(),productOfferVO.getPackageHost(),conn);
					prodRelationVO.setRProductID(pproductOfferId);
					if(prodRelationVO.getOpType().equals("0")){
						deleteProdRelation(prodRelationVO,productOfferId,conn);
						saveProdRelation(prodRelationVO,productOfferId,conn);
					}
					else
						deleteProdRelation(prodRelationVO,productOfferId,conn);
				}
			}
			
			
			
		return true;
	}	
	
	
	public static boolean saveProdOfferRoleInfo(String prodOfferId,String productId,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			long prodOfferRoleCD = getSequence(SEQ_PROD_OFFER_ROLE_CD, conn);
			insertSt = conn.prepareStatement(SQL_INSERT_PROD_OFFER_ROLE);
			int index = 1;
			insertSt.setLong(index++, prodOfferRoleCD);//PROD_OFFER_ROLE_CD
			insertSt.setString(index++, prodOfferId);//PROD_OFFER_ID, 
			insertSt.setString(index++, productId);//PRODUCT_ID,
			
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
		return true;
	}
	
	public static boolean deleteProdOfferRoleInfo(String prodOfferId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			insertSt = conn.prepareStatement(SQL_DELETE_PROD_OFFER_ROLE);
			int index = 1;
			insertSt.setString(index++, prodOfferId);//PROD_OFFER_ID
			
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
		return true;
	}	



	public static boolean saveProdRelation(ProdRelationVO ProdRelationVO,String packageId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			long prodOfferRelId = getSequence(SEQ_PROD_OFFER_REL_ID, conn);
			insertSt = conn.prepareStatement(SQL_INSERT_PRODUCT_RELATION);
			int index = 1;

			insertSt.setLong(index++, prodOfferRelId);//PRODUCT_REL_ID
			insertSt.setString(index++, ProdRelationVO.getRelationType());//RELATION_TYPE_CD, 
			insertSt.setString(index++, packageId);//PRODUCT_ID,
			insertSt.setString(index++, ProdRelationVO.getRProductID());//OFFER_Z_ID, 
			insertSt.setTimestamp(index++, getCurrentTimestamp());//STATE_DATE, 
			insertSt.setTimestamp(index++, getCurrentTimestamp());//CREATE_DATE, 
						
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
		return true;
	}
	
	public static boolean deleteProdRelation(ProdRelationVO ProdRelationVO,String packageId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			insertSt = conn.prepareStatement(SQL_DELETE_PRODUCT_RELATION);
			int index = 1;
			insertSt.setString(index++, packageId);//PRODUCT_ID
			insertSt.setString(index++, ProdRelationVO.getRProductID());//PRO_PRODUCT_ID
			insertSt.setString(index++, ProdRelationVO.getRelationType());//RELATION_TYPE_CD
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
		return true;
	}	
	
	private static long getSequence(String sequenceName, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		long seq = 0;
		ResultSet rs = null;
		ps = conn.prepareStatement("SELECT " + sequenceName + ".NEXTVAL FROM DUAL");
		rs = ps.executeQuery();
		while(rs.next()){
			seq = rs.getLong(1);
		}
		if(rs!=null) rs.close();
		if(ps!=null) ps.close();
		return seq;
	}

	private static String getProductOfferIdByCode(String productOfferCode,String systemId, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		String productId = "";
		ResultSet rs = null;
		ps = conn.prepareStatement("SELECT PROD_OFFER_ID from PROD_OFFER where OFFER_NBR=? and PACKAGE_HOST=?");
		int index = 1;
		ps.setString(index++,productOfferCode);
		ps.setString(index++,systemId);
		rs = ps.executeQuery();
		while(rs.next()){
			productId = rs.getString(1);
		}
		if(rs!=null) rs.close();
		if(ps!=null) ps.close();
		return productId;
	}

	private static String getProductIdByCode(String productCode,String systemId, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		String productId = "";
		ResultSet rs = null;
		ps = conn.prepareStatement("SELECT product_id from product where PRODUCT_NBR=? and SYSTEM_CODE=?");
		int index = 1;
		ps.setString(index++,productCode);
		ps.setString(index++,systemId);
		rs = ps.executeQuery();
		while(rs.next()){
			productId = rs.getString(1);
		}
		if(rs!=null) rs.close();
		if(ps!=null) ps.close();
		return productId;
	}	
	
	/**
	 * 获取当前的时间。当前实现从应用服务器取时间。 如果要统一取数据库时间，也将从这里统一获取。
	 * 
	 * @return
	 */
	public static java.sql.Timestamp getCurrentTimestamp() {

		return new java.sql.Timestamp(System.currentTimeMillis());

	}
	
	public static Timestamp parseTimestamp(String sdate) {
		if (null == sdate || "".equals(sdate))
			return null;
		
		java.util.Date tDate = null;

		// 只有日期类型
		if (sdate.length() <= 10) {
			SimpleDateFormat dateFormator = new SimpleDateFormat(
					"yyyy-MM-dd");

			tDate = dateFormator.parse(sdate, new ParsePosition(0));
		}else{

			SimpleDateFormat dateFormator = new SimpleDateFormat(
					"yyyyMMdd");

			tDate = dateFormator.parse(sdate, new ParsePosition(0));
		}
		
		if (tDate == null)
			return null;

		return new java.sql.Timestamp(tDate.getTime());

	}
	
	/**
	 * 通过统一的格式将文本转换成Date。输入为日期。
	 * 
	 * @return
	 */
	public static Date parseDate(String sdate) {
		if (null == sdate || "".equals(sdate))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(
				CrmConstants.DATE_FORMAT);

		java.util.Date tDate = dateFormator.parse(sdate, new ParsePosition(0));
		if (tDate == null)
			return null;

		return new java.sql.Date(tDate.getTime());
	}
}
