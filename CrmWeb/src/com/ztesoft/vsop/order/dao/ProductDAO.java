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
import com.ztesoft.vsop.order.vo.ProductVO;;
public class ProductDAO {
	private static String SEQ_PRODUCT_ID = "SEQ_PRODUCT_ID";
	private static String SQL_INSERT = "insert into PRODUCT (PRODUCT_ID,PRODUCT_NBR,PRODUCT_NAME,PRODUCT_DESC,MANAGE_GRADE,PROD_BUNDLE_TYPE,PRODUCT_PROVIDER_ID,PRODUCT_STATE_CD,STATE_DATE,EFF_DATE,EXP_DATE,CREATE_DATE,PROD_FUNC_TYPE,ORDER_HOST,CHARGING_POLICY_CN,CHARGING_POLICY_ID,SUB_OPTION,PRESENT,CORP_ONLY,PACKAGE_ONLY,SETTLEMENT_CYCLE,SETTLEMENT_PAYTYPE,SETTLEMENT_PERCENT,SCOPE,SYSTEM_CODE)" +
			" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static String SQL_UPDATE = "update PRODUCT set PRODUCT_NBR=?,PRODUCT_NAME=?,PRODUCT_DESC=?,MANAGE_GRADE=?,PROD_BUNDLE_TYPE=?,PRODUCT_PROVIDER_ID=?,PRODUCT_STATE_CD=?,STATE_DATE=?,EFF_DATE=?,EXP_DATE=?,CREATE_DATE=?,PROD_FUNC_TYPE=?,ORDER_HOST=?,CHARGING_POLICY_CN=?,CHARGING_POLICY_ID=?,SUB_OPTION=?,PRESENT=?,CORP_ONLY=?,PACKAGE_ONLY=?,SETTLEMENT_CYCLE=?,SETTLEMENT_PAYTYPE=?,SETTLEMENT_PERCENT=?,SCOPE=?,SYSTEM_CODE=? where PRODUCT_NBR=? and SYSTEM_CODE=?";	
	
	private static String SEQ_PRODUCT_SERVICE_ABILITY_REL_ID = "SEQ_SERVICE_ABILITY_REL_ID";
	private static String SQL_INSERT_SERVICEABILITY = "insert into PRODUCT_SERVICE_ABILITY_REL(PRODUCT_SERVICE_ABILITY_REL_ID,PRODUCT_ID,SERVICE_CODE,REL_TYPE) values (?,?,?,?)";
	private static String SQL_DELETE_SERVICEABILITY = "delete from PRODUCT_SERVICE_ABILITY_REL where product_id=?";
	
	private static String SEQ_PLATFORM_ID = "SEQ_PLATFORM_ID";
	private static String SQL_INSERT_PLATFORM = "insert into PROD_PLATFORM(PROD_PLATFORM_ID,PRODUCT_ID,PLATFORM_ID) values (?,?,?)";
	private static String SQL_DELETE_PLATFORM = "delete from PROD_PLATFORM where product_id=?";

	private static String SEQ_PRODUCT_OP_ID= "SEQ_PRODUCT_OP_ID";
	private static String SQL_INSERT_PRODUCT_OP_CODE = "insert into PRODUCT_OP_CODE(PRODUCT_OP_ID,OP_TYPE,PRODUCT_ID,ACCESS_NO,FEATURE_STR,MATCH_MODE) values (?,?,?,?,?,?)";
	private static String SQL_DELETE_PRODUCT_OP_CODE = "delete from PRODUCT_OP_CODE where product_id=?";
	

	private static String SEQ_PRODUCT_REL_ID= "SEQ_PRODUCT_REL_ID";
	private static String SQL_INSERT_PRODUCT_RELATION = "insert into PRODUCT_RELATION(PRODUCT_REL_ID,RELATION_TYPE_CD,PRODUCT_ID,PRO_PRODUCT_ID,STATE_CD,STATE_DATE,CREATE_DATE) values (?,?,?,?,?,?,?)";
	private static String SQL_DELETE_PRODUCT_RELATION = "delete from PRODUCT_RELATION where PRODUCT_ID=? and PRO_PRODUCT_ID=? and RELATION_TYPE_CD=?";
		
	
	public static boolean saveProductInfo(ProductVO productVO,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			long productId = getSequence(SEQ_PRODUCT_ID, conn);
			//保存订单
			insertSt = conn.prepareStatement(SQL_INSERT);
			int index = 1;
			insertSt.setLong(index++, productId);//PRODUCT_ID
			insertSt.setString(index++, productVO.getProductID());//PRODUCT_NBR, 
			insertSt.setString(index++, productVO.getPnameCN());//PRODUCT_NAME,
			insertSt.setString(index++, productVO.getProductName());//PRODUCT_DESC,
			insertSt.setString(index++, "02");//MANAGE_GRADE,
			insertSt.setString(index++, "01");//PROD_BUNDLE_TYPE,
			insertSt.setString(index++, getSpIdByCode(productVO.getSPID(),systemId,conn));//PRODUCT_PROVIDER_ID,
			insertSt.setString(index++, productVO.getStatus());//PRODUCT_STATE_CD,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//STATE_DATE,
			insertSt.setString(index++, "");//EFF_DATE,
			insertSt.setString(index++, "");//EFF_DATE,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//CREATE_DATE,
			insertSt.setString(index++, "01");//PROD_FUNC_TYPE,
			insertSt.setString(index++, productVO.getProductHost());//ORDER_HOST,
			insertSt.setString(index++, productVO.getChargingPolicyCN());//CHARGING_POLICY_CN,
			insertSt.setString(index++, productVO.getChargingPolicyID());//CHARGING_POLICY_ID,
			insertSt.setString(index++, productVO.getSubOption());//SUB_OPTION,
			insertSt.setString(index++, productVO.getPresent());//PRESENT,
			insertSt.setString(index++, productVO.getCorpOnly());//CORP_ONLY,
			insertSt.setString(index++, productVO.getPackageOnly());//PACKAGE_ONLY,
			insertSt.setString(index++, productVO.getSettlementCycle());//SETTLEMENT_CYCLE,
			insertSt.setString(index++, productVO.getSettlementPayType());//SETTLEMENT_PAYTYPE,
			insertSt.setString(index++, productVO.getSettlementPercent());//SETTLEMENT_PERCENT
			insertSt.setString(index++, productVO.getScope());//SCOPE
			insertSt.setString(index++, systemId);//SYSTEM_CODE
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
			//如果业务能力列表不为空
			if(productVO.getServiceAblityLst()!=null){
				for(int i=0;i<productVO.getServiceAblityLst().size();i++){
					String serviceCode = (String)productVO.getServiceAblityLst().get(i);
					saveServiceInfo(serviceCode,productId,conn);
				}
			}
			
			//如果平台列表不为空
			if(productVO.getPlatFormLst()!=null){
				for(int i=0;i<productVO.getPlatFormLst().size();i++){
					String platFormId = (String)productVO.getPlatFormLst().get(i);
					savePlatFormInfo(productId,platFormId,conn);
				}
			}
			
			//如果productOPCode不为空
			if(productVO.getProductOPCodeLst()!=null){
				for(int i=0;i<productVO.getProductOPCodeLst().size();i++){
					ProductOPCodeVO productOPCodeVO = (ProductOPCodeVO)productVO.getProductOPCodeLst().get(i);
					saveProdNbrInfo(productOPCodeVO,productId,conn);
				}
			}
			
			//如果ProdRelationLst不为空
			if(productVO.getProdRelationLst()!=null){
				for(int i=0;i<productVO.getProdRelationLst().size();i++){
					ProdRelationVO prodRelationVO = (ProdRelationVO)productVO.getProdRelationLst().get(i);
					saveProdRelation(prodRelationVO,productId,conn);
				}
			}
			
			
		return true;
	}

	public static boolean updateProdcutInfo(ProductVO productVO,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			//保存订单
			insertSt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			insertSt.setString(index++, productVO.getProductID());//PRODUCT_NBR, 
			insertSt.setString(index++, productVO.getPnameCN());//PRODUCT_NAME,
			insertSt.setString(index++, productVO.getProductName());//PRODUCT_DESC,
			insertSt.setString(index++, "02");//MANAGE_GRADE,
			insertSt.setString(index++, "01");//PROD_BUNDLE_TYPE,
			insertSt.setString(index++, "0");//PRODUCT_PROVIDER_ID,
			insertSt.setString(index++, productVO.getStatus());//PRODUCT_STATE_CD,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//STATE_DATE,
			insertSt.setString(index++, "");//EFF_DATE,
			insertSt.setString(index++, "");//EFF_DATE,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//CREATE_DATE,
			insertSt.setString(index++, "01");//PROD_FUNC_TYPE,
			insertSt.setString(index++, productVO.getProductHost());//ORDER_HOST,
			insertSt.setString(index++, productVO.getChargingPolicyCN());//CHARGING_POLICY_CN,
			insertSt.setString(index++, productVO.getChargingPolicyID());//CHARGING_POLICY_ID,
			insertSt.setString(index++, productVO.getSubOption());//SUB_OPTION,
			insertSt.setString(index++, productVO.getPresent());//PRESENT,
			insertSt.setString(index++, productVO.getCorpOnly());//CORP_ONLY,
			insertSt.setString(index++, productVO.getPackageOnly());//PACKAGE_ONLY,
			insertSt.setString(index++, productVO.getSettlementCycle());//SETTLEMENT_CYCLE,
			insertSt.setString(index++, productVO.getSettlementPayType());//SETTLEMENT_PAYTYPE,
			insertSt.setString(index++, productVO.getSettlementPercent());//SETTLEMENT_PERCENT
			insertSt.setString(index++, productVO.getScope());//SCOPE
			insertSt.setString(index++, systemId);//SYSTEM_CODE
			
			//where条件
			insertSt.setString(index++, productVO.getProductID());//PRODUCT_NBR,
			insertSt.setString(index++, systemId);//SYSTEM_CODE
			
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
			String productId = getProductIdByCode(productVO.getProductID(),systemId,conn);
			
			deleteServiceInfo(productId,systemId,conn);
			deletePlatFormInfo(productId,systemId,conn);
			deleteProdNbrInfo(productId,systemId,conn);

			//如果业务能力列表不为空
			if(productVO.getServiceAblityLst()!=null){
				for(int i=0;i<productVO.getServiceAblityLst().size();i++){
					String serviceCode = (String)productVO.getServiceAblityLst().get(i);
					saveServiceInfo(serviceCode,Long.parseLong(productId),conn);
				}
			}
			
			//如果平台列表不为空
			if(productVO.getPlatFormLst()!=null){
				for(int i=0;i<productVO.getPlatFormLst().size();i++){
					String platFormId = (String)productVO.getPlatFormLst().get(i);
					savePlatFormInfo(Long.parseLong(productId),platFormId,conn);
				}
			}
			
			//如果productOPCode不为空
			if(productVO.getProductOPCodeLst()!=null){
				for(int i=0;i<productVO.getProductOPCodeLst().size();i++){
					ProductOPCodeVO productOPCodeVO = (ProductOPCodeVO)productVO.getProductOPCodeLst().get(i);
					saveProdNbrInfo(productOPCodeVO,Long.parseLong(productId),conn);
				}
			}			
			
			//如果ProdRelationLst不为空
			if(productVO.getProdRelationLst()!=null){
				for(int i=0;i<productVO.getProdRelationLst().size();i++){
					ProdRelationVO prodRelationVO = (ProdRelationVO)productVO.getProdRelationLst().get(i);
					if(prodRelationVO.getOpType().equals("0")){
						deleteProdRelation(prodRelationVO,Long.parseLong(productId),conn);
						saveProdRelation(prodRelationVO,Long.parseLong(productId),conn);
					}
					else
						deleteProdRelation(prodRelationVO,Long.parseLong(productId),conn);
				}
			}
			
			
		return true;
	}	
	
	
	public static boolean saveServiceInfo(String serviceCode,long productId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			long productServiceAbilityRelId = getSequence(SEQ_PRODUCT_SERVICE_ABILITY_REL_ID, conn);
			insertSt = conn.prepareStatement(SQL_INSERT_SERVICEABILITY);
			int index = 1;
			insertSt.setLong(index++, productServiceAbilityRelId);//PRODUCT_SERVICE_ABILITY_REL_ID
			insertSt.setLong(index++, productId);//PRODUCT_ID, 
			insertSt.setString(index++, serviceCode);//SERVICE_CODE,
			insertSt.setString(index++, "0");//REL_TYPE,
			
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
		return true;
	}
	
	public static boolean deleteServiceInfo(String productId,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			insertSt = conn.prepareStatement(SQL_DELETE_SERVICEABILITY);
			int index = 1;
			insertSt.setString(index++, productId);//PRODUCT_ID
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
		return true;
	}	

	
	public static boolean savePlatFormInfo(long productId,String platFormId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			long prodPlatFormId = getSequence(SEQ_PLATFORM_ID, conn);
			insertSt = conn.prepareStatement(SQL_INSERT_PLATFORM);
			int index = 1;
			insertSt.setLong(index++, prodPlatFormId);//PROD_PLATFORM_ID
			insertSt.setLong(index++, productId);//PRODUCT_ID, 
			insertSt.setString(index++, platFormId);//PLATFORM_ID,
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
		return true;
	}
	
	public static boolean deletePlatFormInfo(String productId,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			insertSt = conn.prepareStatement(SQL_DELETE_PLATFORM);
			int index = 1;
			insertSt.setString(index++, productId);//PRODUCT_ID
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
		return true;
	}		

	public static boolean saveProdNbrInfo(ProductOPCodeVO productOPCodeVO,long productId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			long prodPlatFormId = getSequence(SEQ_PRODUCT_OP_ID, conn);
			insertSt = conn.prepareStatement(SQL_INSERT_PRODUCT_OP_CODE);
			int index = 1;

			insertSt.setLong(index++, prodPlatFormId);//PROD_ACC_NBR_TYPE_ID
			insertSt.setString(index++, productOPCodeVO.getOpType());//OP_TYPE, 
			insertSt.setLong(index++, productId);//PRODUCT_ID,
			insertSt.setString(index++, productOPCodeVO.getAccessNO());//ACCESS_NO, 
			insertSt.setString(index++, productOPCodeVO.getFeatureStr());//FEATURE_STR, 
			insertSt.setString(index++, productOPCodeVO.getMatchMode());//MATCH_MODE, 
						
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
		return true;
	}
	
	public static boolean deleteProdNbrInfo(String productId,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			insertSt = conn.prepareStatement(SQL_DELETE_PRODUCT_OP_CODE);
			int index = 1;
			insertSt.setString(index++, productId);//PRODUCT_ID
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
		return true;
	}		

	public static boolean saveProdRelation(ProdRelationVO ProdRelationVO,long productId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			long productRelId = getSequence(SEQ_PRODUCT_REL_ID, conn);
			insertSt = conn.prepareStatement(SQL_INSERT_PRODUCT_RELATION);
			int index = 1;

			insertSt.setLong(index++, productRelId);//PRODUCT_REL_ID
			insertSt.setString(index++, ProdRelationVO.getRelationType());//RELATION_TYPE_CD, 
			insertSt.setLong(index++, productId);//PRODUCT_ID,
			insertSt.setString(index++, ProdRelationVO.getRProductID());//PRO_PRODUCT_ID, 
			insertSt.setString(index++, "00A");//STATE_CD, 
			insertSt.setTimestamp(index++, getCurrentTimestamp());//STATE_DATE, 
			insertSt.setTimestamp(index++, getCurrentTimestamp());//CREATE_DATE, 
						
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
		return true;
	}
	
	public static boolean deleteProdRelation(ProdRelationVO ProdRelationVO,long productId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			insertSt = conn.prepareStatement(SQL_DELETE_PRODUCT_RELATION);
			int index = 1;
			insertSt.setLong(index++, productId);//PRODUCT_ID
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

	private static String getSpIdByCode(String spCode,String systemId, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		String spId = "";
		ResultSet rs = null;
		ps = conn.prepareStatement("SELECT PARTNER_ID from PARTNER where PARTNER_CODE=? and SYSTEM_CODE=?");
		int index = 1;
		ps.setString(index++,spCode);
		ps.setString(index++,systemId);
		rs = ps.executeQuery();
		while(rs.next()){
			spId = rs.getString(1);
		}
		if(rs!=null) rs.close();
		if(ps!=null) ps.close();
		return spId;
	}

	public static String getProductIdByCode(String productCode,String systemId, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		String spId = "";
		ResultSet rs = null;
		ps = conn.prepareStatement("SELECT product_id from PRODUCT where PRODUCT_NBR=? and SYSTEM_CODE=?");
		int index = 1;
		ps.setString(index++,productCode);
		ps.setString(index++,systemId);
		rs = ps.executeQuery();
		while(rs.next()){
			spId = rs.getString(1);
		}
		if(rs!=null) rs.close();
		if(ps!=null) ps.close();
		return spId;
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
