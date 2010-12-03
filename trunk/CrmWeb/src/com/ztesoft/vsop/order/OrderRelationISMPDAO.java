package com.ztesoft.vsop.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.ismpSyn.vo.SubscriptionSyncReq;
import com.ztesoft.vsop.web.DcSystemParamManager;

import common.Logger;

public class OrderRelationISMPDAO {
	
	private static Logger log = Logger.getLogger(OrderRelationISMPDAO.class);
	
	private static String SEQ_ORDER_INFO = "SEQ_ORDER_INFO_ID";
	private static String SQL_INSERT = "insert into CUSTOMER_ORDER_HIS (CUST_ORDER_ID, OTHER_SYS_ORDER_ID, CUST_SO_NUMBER,CUST_ORDER_TYPE,TIME_NAME_ID,CUST_ID,STAFF_ID,CHANNEL_ID,STATUS,STATUS_DATE,PRE_HANDLE_FLAG,HANDLE_PEOPLE_NAME,CONTACT_PHONE," +
			"CONTACT_PEOPLE,PRIORITY,REASON,ORDER_CHANNEL,ORDER_SYSTEM,RECEIVE_DATE,DISPOSAL_RESULT,DISPOSAL_RESULT_DESC,ACC_NBR,PRODUCT_ID,LAN_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	public static boolean saveOrderInfo(OrderRelationISMPVO orderRelationISMPVO,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
		// PreparedStatement extPstm = null;
		int count = -1;

		long t1 = System.currentTimeMillis();
		String userIdType = orderRelationISMPVO.getUserIDType();
		String prodTypeStr = "";
		if ("0".equals(userIdType)) {
			prodTypeStr = DcSystemParamManager.getParameter("DC_MSISDN");
		} else if ("1".equals(userIdType)) {
			prodTypeStr = DcSystemParamManager.getParameter("DC_PHS");
		} else {
			prodTypeStr = DcSystemParamManager.getParameter("DC_PSTN");
		}
		HashMap map = getProductInfo(orderRelationISMPVO.getUserID(),
				prodTypeStr, conn);
		String insertSql = "";
		int index = 1;
		/*
		 * long custOrderId = getSequence(SEQ_ORDER_INFO, conn); //保存订单 insertSt =
		 * conn.prepareStatement(SQL_INSERT);
		 * 
		 * insertSt.setLong(index++, custOrderId);//CUST_ORDER_ID
		 * insertSt.setString(index++, "0");//OTHER_SYS_ORDER_ID,
		 * insertSt.setString(index++, "0");//CUST_SO_NUMBER,
		 * insertSt.setString(index++, "0");//CUST_ORDER_TYPE,
		 * insertSt.setString(index++, "0");//TIME_NAME_ID,
		 * insertSt.setString(index++, "0");//CUST_ID,
		 * insertSt.setString(index++, "0");//STAFF_ID,
		 * insertSt.setString(index++, "0");//CHANNEL_ID,
		 * insertSt.setString(index++, "00A");//STATUS,
		 * insertSt.setTimestamp(index++, getCurrentTimestamp());//STATUS_DATE,
		 * insertSt.setString(index++, "0");//PRE_HANDLE_FLAG,
		 * insertSt.setString(index++, "");//HANDLE_PEOPLE_NAME,
		 * insertSt.setString(index++, "");//CONTACT_PHONE,
		 * insertSt.setString(index++, "");//CONTACT_PEOPLE,
		 * insertSt.setString(index++, "0");//PRIORITY,
		 * insertSt.setString(index++, "");//REASON, insertSt.setString(index++,
		 * "");//ORDER_CHANNEL, insertSt.setString(index++,
		 * systemId);//ORDER_SYSTEM, insertSt.setTimestamp(index++,
		 * getCurrentTimestamp());//RECEIVE_DATE, insertSt.setString(index++,
		 * "");//DISPOSAL_RESULT, insertSt.setString(index++,
		 * "");//DISPOSAL_RESULT_DESC, insertSt.setString(index++,
		 * orderRelationISMPVO.getUserID());//ACC_NBR,
		 * insertSt.setString(index++,
		 * (String)map.get("PROD_INST_ID"));//PRODUCT_ID,
		 * insertSt.setString(index++, (String)map.get("LAN_ID"));//LAN_ID
		 * 
		 * count = insertSt.executeUpdate(); if(insertSt!=null){
		 * insertSt.close(); }
		 * 
		 * long t2 = System.currentTimeMillis();
		 * log.info("saveOrderInfo.保存订单===>" + (t2 - t1));
		 * 
		 * 
		 * //保存子订单 insertSql = "insert into ORDER_ITEM_HIS
		 * (ORDER_ITEM_ID,CUST_ORDER_ID,ORDER_ITEM_CD,ORDER_ITEM_OBJ_ID,CUST_WORKSHEET_ID,STATUS,STATUS_DATE,STATE_CHANGE_REASON,PRIORITY,PRE_HANDLE_FLAG" +
		 * ",HANDLE_TIME,ARCHIVE_DATE,FINISH_TIME,PRODUCT_ID,ACC_NBR,PACKAGE_ID,PRODUCT_OFFER_ID,EFF_TIME,EXP_TIME,RESULT_TYPE,RESULT_INFO,LAN_ID)
		 * values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
		 * ?)";
		 * 
		 * 
		 * long t3 = System.currentTimeMillis();
		 * 
		 * long orderItemId = getSequence("SEQ_SUB_ORDER_INFO_ID", conn);
		 * 
		 * PreparedStatement insertSt2 = conn.prepareStatement(insertSql); index =
		 * 1; insertSt2.setLong(index++, orderItemId);//ORDER_ITEM_ID
		 * insertSt2.setLong(index++,custOrderId);//CUST_ORDER_ID,
		 * insertSt2.setString(index++, "04");//ORDER_ITEM_CD,
		 * insertSt2.setString(index++,
		 * orderRelationISMPVO.getProductID());//ORDER_ITEM_OBJ_ID,
		 * insertSt2.setString(index++, "");//CUST_WORKSHEET_ID,
		 * insertSt2.setString(index++, "00A");//STATUS,
		 * insertSt2.setTimestamp(index++, getCurrentTimestamp());//STATUS_DATE,
		 * insertSt2.setString(index++, "");//STATE_CHANGE_REASON,
		 * insertSt2.setString(index++, "");//PRIORITY,
		 * insertSt2.setString(index++, "");//PRE_HANDLE_FLAG,
		 * insertSt2.setString(index++, "");//HANDLE_TIME,
		 * insertSt2.setString(index++, "");//ARCHIVE_DATE,
		 * insertSt2.setString(index++, "");//FINISH_TIME,
		 * insertSt2.setString(index++,
		 * (String)map.get("PROD_INST_ID"));//PRODUCT_ID,
		 * insertSt2.setString(index++,
		 * orderRelationISMPVO.getUserID());//ACC_NBR,
		 * insertSt2.setString(index++, "");//PACKAGE_ID,
		 * insertSt2.setString(index++, "");//PRODUCT_OFFER_ID,
		 * if(orderRelationISMPVO.getEffectiveTime()!=null&&!orderRelationISMPVO.getEffectiveTime().equals(""))
		 * insertSt2.setTimestamp(index++,
		 * parseTimestamp(orderRelationISMPVO.getEffectiveTime()));//EFF_TIME,
		 * else insertSt2.setTimestamp(index++, getCurrentTimestamp());
		 * insertSt2.setTimestamp(index++,
		 * parseTimestamp(orderRelationISMPVO.getExpireTime()));//EXP_TIME,
		 * insertSt2.setString(index++, "");//RESULT_TYPE,
		 * insertSt2.setString(index++, "");//RESULT_INFO,
		 * insertSt2.setString(index++, (String)map.get("LAN_ID"));//LAN_ID
		 * insertSt2.executeUpdate(); if(insertSt2!=null){ insertSt2.close(); }
		 * 
		 * long t4 = System.currentTimeMillis();
		 * log.info("saveOrderInfo.保存子订单===>" + (t4 - t3));
		 */

		long t5 = System.currentTimeMillis();

		PreparedStatement insertSt3 = null;
		try {
			if (orderRelationISMPVO.getOPType().equals("0")) {// 订购
				insertSql = "insert into ORDER_RELATION(ACC_NBR,PROD_TYPE_CD,ORDER_RELATION_ID,PROD_INST_ID,PRODUCT_ID,PROD_OFFER_INST_ID,PACKAGE_ID,PROD_OFFER_ID,PPROD_OFFER_ID,ORDER_CHANNEL,STATE,EFF_DATE,EXP_DATE,LAN_ID,STATE_DATE,CREATE_DATE,ACTIVE_STATE) "
						+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,"+DatabaseFunction.current()+","+DatabaseFunction.current()+",?)";
				long s = System.currentTimeMillis();
				long orderRelationId = getSequence("seq_order_relation_id",
						conn);
				insertSt3 = conn.prepareStatement(insertSql);
				index = 1;

				insertSt3.setString(index++, orderRelationISMPVO.getUserID());// ACC_NBR
				insertSt3.setString(index++, (String) map.get("PRODUCT_ID"));// PROD_TYPE_CD
				insertSt3.setLong(index++, orderRelationId);// ORDER_RELATION_ID
				insertSt3.setString(index++, (String) map.get("PROD_INST_ID"));// PROD_INST_ID
				insertSt3
						.setString(index++, orderRelationISMPVO.getProductID());// PRODUCT_ID,
				insertSt3.setString(index++, "");// PROD_OFFER_INST_ID,
				insertSt3
						.setString(index++, orderRelationISMPVO.getPackageID());// PACKAGE_ID
				insertSt3
						.setString(index++, orderRelationISMPVO.getProductID());// PROD_OFFER_ID,
				insertSt3.setString(index++, orderRelationISMPVO
						.getPProductOfferID());// PPROD_OFFER_ID,
				insertSt3.setString(index++, systemId);// ORDER_CHANNEL,
				insertSt3.setString(index++, "00A");// STATE,
				if (orderRelationISMPVO.getEffectiveTime() != null
						&& !orderRelationISMPVO.getEffectiveTime().equals(""))
					insertSt3.setTimestamp(index++,
							parseTimestamp(orderRelationISMPVO
									.getEffectiveTime()));// EFF_TIME,
				else
					insertSt3.setTimestamp(index++, getCurrentTimestamp());
				insertSt3.setTimestamp(index++,
						parseTimestamp(orderRelationISMPVO.getExpireTime()));// EXP_TIME,
				insertSt3.setString(index++, (String) map.get("LAN_ID"));// LAN_ID,
				insertSt3.setString(index++, "1");
				insertSt3.executeUpdate();
				/*if (insertSt3 != null) {
					insertSt3.close();
				}*/
				long t6 = System.currentTimeMillis();
				log.info("saveOrderInfo.订购====>" + (t6 - t5));

			}

			else if (orderRelationISMPVO.getOPType().equals("6")) {// 替换
				insertSql = "update ORDER_RELATION set PRODUCT_ID=?,STATE=?,STATE_DATE="+DatabaseFunction.current()+",EFF_DATE=?,EXP_DATE=?,PACKAGE_ID=?,PROD_OFFER_ID=?,PPROD_OFFER_ID=?,ORDER_CHANNEL=? where PROD_INST_ID=? and PRODUCT_ID=?";
				index = 1;
				insertSt3 = conn.prepareStatement(insertSql);
				insertSt3
						.setString(index++, orderRelationISMPVO.getProductID());// PRODUCT_ID,
				insertSt3.setString(index++, "00A");
				if (orderRelationISMPVO.getEffectiveTime() != null
						&& !orderRelationISMPVO.getEffectiveTime().equals(""))
					insertSt3.setTimestamp(index++,
							parseTimestamp(orderRelationISMPVO
									.getEffectiveTime()));// EFF_TIME,
				else
					insertSt3.setTimestamp(index++, getCurrentTimestamp());
				insertSt3.setTimestamp(index++,
						parseTimestamp(orderRelationISMPVO.getExpireTime()));// EXP_TIME,
				insertSt3
						.setString(index++, orderRelationISMPVO.getPackageID());// PACKAGE_ID,
				insertSt3
						.setString(index++, orderRelationISMPVO.getProductID());// PROD_OFFER_ID,
				insertSt3.setString(index++, orderRelationISMPVO
						.getPProductOfferID());// PPROD_OFFER_ID,
				insertSt3.setString(index++, systemId);// ORDER_CHANNEL,

				insertSt3.setString(index++, (String) map.get("PROD_INST_ID"));// PROD_INST_ID
				insertSt3.setString(index++, orderRelationISMPVO
						.getOldProductID());// PRODUCT_ID,
				insertSt3.executeUpdate();
				/*if (insertSt3 != null) {
					insertSt3.close();
				}*/
				long t6 = System.currentTimeMillis();
				log.info("saveOrderInfo.替换====>" + (t6 - t5));

			} else {
				insertSql = "update ORDER_RELATION set PRODUCT_ID=?,STATE=?,STATE_DATE="+DatabaseFunction.current()+",EFF_DATE=?,EXP_DATE=?,PACKAGE_ID=?,PROD_OFFER_ID=?,PPROD_OFFER_ID=?,ORDER_CHANNEL=? where PROD_INST_ID=? and PRODUCT_ID=?";
				index = 1;
				insertSt3 = conn.prepareStatement(insertSql);
				insertSt3
						.setString(index++, orderRelationISMPVO.getProductID());// PRODUCT_ID,
				if (orderRelationISMPVO.getOPType().equals("3"))// 退订
					insertSt3.setString(index++, "00X");
				else if (orderRelationISMPVO.getOPType().equals("1"))// 暂停
					insertSt3.setString(index++, "00A");
				else if (orderRelationISMPVO.getOPType().equals("2"))// 恢复暂停
					insertSt3.setString(index++, "00A");
				else
					insertSt3.setString(index++, "00A");

				if (orderRelationISMPVO.getEffectiveTime() != null
						&& !orderRelationISMPVO.getEffectiveTime().equals(""))
					insertSt3.setTimestamp(index++,
							parseTimestamp(orderRelationISMPVO
									.getEffectiveTime()));// EFF_TIME,
				else
					insertSt3.setTimestamp(index++, getCurrentTimestamp());
				insertSt3.setTimestamp(index++,
						parseTimestamp(orderRelationISMPVO.getExpireTime()));// EXP_TIME,
				insertSt3
						.setString(index++, orderRelationISMPVO.getPackageID());// PACKAGE_ID,
				insertSt3
						.setString(index++, orderRelationISMPVO.getProductID());// PROD_OFFER_ID,
				insertSt3.setString(index++, orderRelationISMPVO
						.getPProductOfferID());// PPROD_OFFER_ID,
				insertSt3.setString(index++, systemId);// ORDER_CHANNEL,

				insertSt3.setString(index++, (String) map.get("PROD_INST_ID"));// PROD_INST_ID
				insertSt3
						.setString(index++, orderRelationISMPVO.getProductID());// PRODUCT_ID,
				insertSt3.executeUpdate();
				/*if (insertSt3 != null) {
					insertSt3.close();
				}*/
				long t6 = System.currentTimeMillis();
				log.info("saveOrderInfo.退订、暂停、恢复暂停====>" + (t6 - t5));

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("",e);
			throw new DAOSystemException("SQLException while getting sql:\n", e);
		} finally {
			try {
				if (insertSt3 != null)
					insertSt3.close();
			} catch (Exception se) {
				throw new DAOSystemException("SQL Exception while closing "
						+ "Statement : \n" + se);
			}
		}
		//if(insertSt!=null) insertSt.close();

		return true;
	}
	
	private static long getSequence(String sequenceName, Connection conn)  throws SQLException{
		PreparedStatement ps = null;
		long seq = 0;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT " + sequenceName + ".NEXTVAL FROM DUAL");
			rs = ps.executeQuery();
			while(rs.next()){
				seq = rs.getLong(1);
			}
		} catch (SQLException se) {
			// TODO Auto-generated catch block
			se.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n", se);
		}finally{
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
			}catch(Exception se){
				throw new DAOSystemException("SQL Exception while closing "
						+ "Statement : \n" + se);
			}
						
		}
		return seq;
	}
	/**
	 * 
	 * @param productNo
	 * @param prodTypeStr  增加产品类型字段查询，某些省份是由产品号码+产品类型确定数据唯一性
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private static HashMap getProductInfo(String productNo, 
										  String prodTypeStr, 
										  Connection conn)  throws SQLException{
		PreparedStatement ps = null;
		long seq = 0;
		ResultSet rs = null;
		HashMap map = new HashMap();
		String sql=null;
		try {
			sql="SELECT PROD_INST_ID,LAN_ID,PRODUCT_ID FROM PROD_INST where ACC_NBR=? "
				   +" and product_id in ("+"torepalce"+")";
			
			
			String [] types=prodTypeStr.split(",");
			String where="";
			for(int j=0;j<types.length;j++){
				if(j==0){
					where+="?";
				}else{
					where+=",?";
				}
			}
			sql=sql.replaceAll("torepalce", where);
			ps = conn.prepareStatement(sql);
			int i=1;
			ps.setString(i++, productNo);
			for(int j=0;j<types.length;j++){
				ps.setString(i++, types[j]);
			}
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				map.put("PROD_INST_ID",rs.getString("PROD_INST_ID"));
				map.put("LAN_ID",rs.getString("LAN_ID"));
				map.put("PRODUCT_ID",rs.getString("PRODUCT_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, e);
			
		}finally{
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
			}catch(Exception se){
				throw new DAOSystemException("SQL Exception while closing "
						+ "Statement : \n" + se);
			}
						
		}
		
		return map;
	}	

	public HashMap getProductId(String productNbr, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		long seq = 0;
		ResultSet rs = null;
		HashMap map = new HashMap();
		try {
			ps = conn.prepareStatement("SELECT PRODUCT_ID,SCOPE,PRODUCT_PROVIDER_ID FROM PRODUCT " +
					" where PRODUCT_NBR=?");
			ps.setString(1, productNbr);
			rs = ps.executeQuery();

			if(rs.next()){
				map.put("PRODUCT_ID",rs.getString("PRODUCT_ID"));
				map.put("SCOPE",rs.getString("SCOPE"));
				map.put("PRODUCT_PROVIDER_ID", rs.getString("PRODUCT_PROVIDER_ID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n", e);
		}finally{
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
			}catch(Exception se){
				throw new DAOSystemException("SQL Exception while closing "
						+ "Statement : \n" + se);
			}
		}
		return map;
	}	

	public HashMap getProductOfferId(String OfferNbr, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		long seq = 0;
		ResultSet rs = null;
		HashMap map = new HashMap();
		try {
			ps = conn.prepareStatement("select prod_offer_id from prod_offer  where offer_nbr=?");
			ps.setString(1, OfferNbr);
			rs = ps.executeQuery();
			
			if(rs.next()){
				map.put("PRODUCT_OFFER_ID",rs.getString("prod_offer_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n", e);
		}finally{
			try{
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
			}catch(Exception se){
				throw new DAOSystemException("SQL Exception while closing "
						+ "Statement : \n" + se);
			}
		}
		return map;
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
		
		if (null == sdate || "".equals(sdate)) return null;
		java.util.Date tDate = null;

		// 只有日期类型
		if (sdate.length() <= 10) {
			SimpleDateFormat dateFormator = new SimpleDateFormat("yyyy-MM-dd");
			tDate = dateFormator.parse(sdate, new ParsePosition(0));
			
		} else if (sdate.length() == 14) {
			SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT_14);
			tDate = dateFormator.parse(sdate, new ParsePosition(0));
			
		} else {
			SimpleDateFormat dateFormator = new SimpleDateFormat("yyyyMMdd");
			tDate = dateFormator.parse(sdate, new ParsePosition(0));
		}
		
		if (tDate == null) return null;
		return new java.sql.Timestamp(tDate.getTime());

	}
	/**
	 * 
	 * @param orderRelationISMPVO
	 * @param systemId
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static boolean saveOrderInfoIsmpGX(SubscriptionSyncReq req,String systemId, Connection conn) throws SQLException {
//		PreparedStatement insertSt = null;
		// PreparedStatement extPstm = null;
//		int count = -1;

		long t1 = System.currentTimeMillis();
		String userIdType = req.getUserIDType()+"";
		String prodTypeStr = "";
		if ("0".equals(userIdType)) {
			prodTypeStr = DcSystemParamManager.getParameter("DC_MSISDN");
		} else if ("1".equals(userIdType)) {
			prodTypeStr = DcSystemParamManager.getParameter("DC_PHS");
		} else {
			prodTypeStr = DcSystemParamManager.getParameter("DC_PSTN");
		}
		HashMap map = getProductInfo(req.getUserID(),
				prodTypeStr, conn);
		String insertSql = "";
		int index = 1;
		long t5 = System.currentTimeMillis();

		PreparedStatement insertSt3 = null;
		try {
			if (req.getOPType() == 0) {// 订购
				insertSql = "insert into ORDER_RELATION(ACC_NBR,PROD_TYPE_CD,ORDER_RELATION_ID,PROD_INST_ID,PRODUCT_ID,PROD_OFFER_INST_ID,PACKAGE_ID,PROD_OFFER_ID,PPROD_OFFER_ID,ORDER_CHANNEL,STATE,EFF_DATE,EXP_DATE,LAN_ID,STATE_DATE,CREATE_DATE,ACTIVE_STATE) "
						+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,"+DatabaseFunction.current()+","+DatabaseFunction.current()+",?)";
				long s = System.currentTimeMillis();
				long orderRelationId = getSequence("seq_order_relation_id",
						conn);
				insertSt3 = conn.prepareStatement(insertSql);
				index = 1;

				insertSt3.setString(index++, req.getUserID());// ACC_NBR
				insertSt3.setString(index++, (String) map.get("PRODUCT_ID"));// PROD_TYPE_CD
				insertSt3.setLong(index++, orderRelationId);// ORDER_RELATION_ID
				insertSt3.setString(index++, (String) map.get("PROD_INST_ID"));// PROD_INST_ID
				insertSt3
						.setString(index++, req.getProductID());// PRODUCT_ID,
				insertSt3.setString(index++, "");// PROD_OFFER_INST_ID,
				insertSt3
						.setString(index++, req.getPackageID());// PACKAGE_ID
				insertSt3
						.setString(index++, req.getProductID());// PROD_OFFER_ID,
				insertSt3.setString(index++, "");// PPROD_OFFER_ID,ISMP不会有传统+增值产品订购场景
				insertSt3.setString(index++, systemId);// ORDER_CHANNEL,
				insertSt3.setString(index++, "00A");// STATE,
				insertSt3.setTimestamp(index++, getCurrentTimestamp());
				insertSt3.setTimestamp(index++, null);// EXP_TIME,
				insertSt3.setString(index++, (String) map.get("LAN_ID"));// LAN_ID,
				insertSt3.setString(index++, "1");
				insertSt3.executeUpdate();
				/*if (insertSt3 != null) {
					insertSt3.close();
				}*/
				long t6 = System.currentTimeMillis();
				log.info("saveOrderInfoIsmpGX.订购====>" + (t6 - t5));

			}
			/*不存在替换操作
			else if (req.getOPType() == 6) {// 替换
				insertSql = "update ORDER_RELATION set PRODUCT_ID=?,STATE=?,STATE_DATE=sysdate,EFF_DATE=?,EXP_DATE=?,PACKAGE_ID=?,PROD_OFFER_ID=?,PPROD_OFFER_ID=?,ORDER_CHANNEL=? where PROD_INST_ID=? and PRODUCT_ID=?";
				index = 1;
				insertSt3 = conn.prepareStatement(insertSql);
				insertSt3
						.setString(index++, req.getProductID());// PRODUCT_ID,
				insertSt3.setString(index++, "00A");
				insertSt3.setTimestamp(index++, getCurrentTimestamp());
				insertSt3.setTimestamp(index++,
						parseTimestamp(""));// EXP_TIME,
				insertSt3
						.setString(index++, req.getPackageID());// PACKAGE_ID,
				insertSt3
						.setString(index++, req.getProductID());// PROD_OFFER_ID,
				insertSt3.setString(index++, "");// PPROD_OFFER_ID,
				insertSt3.setString(index++, systemId);// ORDER_CHANNEL,

				insertSt3.setString(index++, (String) map.get("PROD_INST_ID"));// PROD_INST_ID
				insertSt3.setString(index++, req.getOldProductID());// PRODUCT_ID,
				insertSt3.executeUpdate();
				//if (insertSt3 != null) {
					//insertSt3.close();
				//}
				long t6 = System.currentTimeMillis();
				log.info("saveOrderInfo.替换====>" + (t6 - t5));

			}*/ else {
				insertSql = "update ORDER_RELATION set PRODUCT_ID=?,STATE=?,STATE_DATE="+DatabaseFunction.current()+",EFF_DATE=?,EXP_DATE=?,PACKAGE_ID=?,PROD_OFFER_ID=?,PPROD_OFFER_ID=?,ORDER_CHANNEL=? where PROD_INST_ID=? and PRODUCT_ID=?";
				index = 1;
				insertSt3 = conn.prepareStatement(insertSql);
				insertSt3
						.setString(index++, req.getProductID());// PRODUCT_ID,
				if (req.getOPType() == 3)// 退订
					insertSt3.setString(index++, "00X");
				else if (req.getOPType() == 1)// 暂停
					insertSt3.setString(index++, "00A");
				else if (req.getOPType() == 2)// 恢复暂停
					insertSt3.setString(index++, "00A");
				else
					insertSt3.setString(index++, "00A");

				insertSt3.setTimestamp(index++, getCurrentTimestamp());
				insertSt3.setTimestamp(index++, null);// EXP_TIME,
				insertSt3
						.setString(index++, req.getPackageID());// PACKAGE_ID,
				insertSt3
						.setString(index++, req.getProductID());// PROD_OFFER_ID,
				insertSt3.setString(index++, "");// PPROD_OFFER_ID,
				insertSt3.setString(index++, systemId);// ORDER_CHANNEL,

				insertSt3.setString(index++, (String) map.get("PROD_INST_ID"));// PROD_INST_ID
				insertSt3
						.setString(index++, req.getProductID());// PRODUCT_ID,
				insertSt3.executeUpdate();
				/*if (insertSt3 != null) {
					insertSt3.close();
				}*/
				long t6 = System.currentTimeMillis();
				log.info("saveOrderInfo.退订、暂停、恢复暂停====>" + (t6 - t5));

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("",e);
			throw new DAOSystemException("SQLException while getting sql:\n", e);
		} finally {
			try {
				if (insertSt3 != null)
					insertSt3.close();
			} catch (Exception se) {
				throw new DAOSystemException("SQL Exception while closing "
						+ "Statement : \n" + se);
			}
		}
		//if(insertSt!=null) insertSt.close();

		return true;
	}
}
