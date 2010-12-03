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
import com.ztesoft.vsop.order.vo.SpVO;

public class SPinfoDAO {
	private static String SEQ_SP_ID = "SEQ_PARTNER_ID";
	private static String SQL_INSERT = "insert into PARTNER (PARTNER_ID,PARTNER_CODE,PARTNER_TYPE,PARTNER_DESC,STATE,STATE_DATE,PARTNER_LEVEL,PARTNER_NAME,PARTNER_PASSWORD,PARTNER_AREA_CODE,PARTNER_URL,PARTNER_IP,CREATE_DATE,PARTNER_ENG_NAME,PARTNER_ENG_DESC,SETTLE_CYCLE,SETTLE_PAY_METHOD,SETTLE_RATE,CUST_SERVICE_PHONE,IF_ROAM,COMPANY_ADDRESS,ARTIFICIAL_PERSON,PRIMARY_PERSON_NAME,PRIMARY_PERSON_PHONE,PRIMARY_PERSON_EMAIL,BUSINESS_LICENSE,CONTRACT_EXP_DATE,COMPANY_CODE,PARTNER_NUMBER,CUST_SERVICE_URL,SYSTEM_CODE)" +
			" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static String SQL_UPDATE = "update PARTNER set PARTNER_CODE =?,PARTNER_TYPE =?,PARTNER_DESC =?,STATE=?,STATE_DATE =?,PARTNER_LEVEL=?,PARTNER_NAME =?,PARTNER_PASSWORD =?,PARTNER_AREA_CODE=?,PARTNER_URL=?,PARTNER_IP =?,CREATE_DATE=?,PARTNER_ENG_NAME =?,PARTNER_ENG_DESC =?,SETTLE_CYCLE =?,SETTLE_PAY_METHOD=?,SETTLE_RATE=?,CUST_SERVICE_PHONE =?,IF_ROAM=?,COMPANY_ADDRESS=?,ARTIFICIAL_PERSON=?,PRIMARY_PERSON_NAME=?,PRIMARY_PERSON_PHONE =?,PRIMARY_PERSON_EMAIL =?,BUSINESS_LICENSE =?,CONTRACT_EXP_DATE=?,COMPANY_CODE =?,PARTNER_NUMBER =?,CUST_SERVICE_URL =?,SYSTEM_CODE=? where PARTNER_CODE=? and SYSTEM_CODE=?";	
	
	public static boolean saveSPInfo(SpVO spVO,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			long spId = getSequence(SEQ_SP_ID, conn);
			//保存订单
			insertSt = conn.prepareStatement(SQL_INSERT);
			int index = 1;
			insertSt.setLong(index++, spId);//PARTNER_ID
			insertSt.setString(index++, spVO.getSPID());//PARTNER_CODE, 
			insertSt.setString(index++, spVO.getType());//PARTNER_TYPE,
			insertSt.setString(index++, spVO.getDescriptionCN());//PARTNER_DESC,
			insertSt.setString(index++, spVO.getStatus());//STATE,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//STATE_DATE,
			insertSt.setString(index++, "0");//PARTNER_LEVEL,
			insertSt.setString(index++, spVO.getNameCN());//PARTNER_NAME,
			insertSt.setString(index++, "");//PARTNER_PASSWORD,
			insertSt.setString(index++, spVO.getProvinceID());//PARTNER_AREA_CODE,
			insertSt.setString(index++, spVO.getWebsiteURL());//PARTNER_URL,
			insertSt.setString(index++, "");//PARTNER_IP,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//CREATE_DATE,
			insertSt.setString(index++, spVO.getNameEN());//PARTNER_ENG_NAME,
			insertSt.setString(index++, spVO.getDescriptionEN());//PARTNER_ENG_DESC,
			insertSt.setString(index++, spVO.getSettlementCycle());//SETTLE_CYCLE,
			insertSt.setString(index++, spVO.getSettlementPayType());//SETTLE_PAY_METHOD,
			insertSt.setString(index++, spVO.getSettlementPercent());//SETTLE_RATE,
			insertSt.setString(index++, spVO.getCustomerCare());//CUST_SERVICE_PHONE,
			insertSt.setString(index++, spVO.getRoamProperty());//IF_ROAM,
			insertSt.setString(index++, spVO.getCompanyAddress());//COMPANY_ADDRESS,
			insertSt.setString(index++, spVO.getLegalRepresentative());//ARTIFICIAL_PERSON,
			insertSt.setString(index++, spVO.getPrincipal());//PRIMARY_PERSON_NAME,
			insertSt.setString(index++, spVO.getPrincipalTel());//PRIMARY_PERSON_PHONE
			insertSt.setString(index++, spVO.getPrincipalEmail());//PRIMARY_PERSON_EMAIL
			insertSt.setString(index++, spVO.getLicense());//BUSINESS_LICENSE
			insertSt.setDate(index++, parseDate(spVO.getContractExpireDate()));//CONTRACT_EXP_DATE
			insertSt.setString(index++, "");//COMPANY_CODE
			insertSt.setString(index++, spVO.getAccessNO());//PARTNER_NUMBER
			insertSt.setString(index++, spVO.getCSWebsite());//CUST_SERVICE_URL
			insertSt.setString(index++, systemId);//SYSTEM_CODE
			count = insertSt.executeUpdate();
			if(insertSt!=null) insertSt.close();
			
		return true;
	}

	public static boolean updateSPInfo(SpVO spVO,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			//保存订单
			insertSt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			insertSt.setString(index++, spVO.getSPID());//PARTNER_CODE, 
			insertSt.setString(index++, spVO.getType());//PARTNER_TYPE,
			insertSt.setString(index++, spVO.getDescriptionCN());//PARTNER_DESC,
			insertSt.setString(index++, spVO.getStatus());//STATE,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//STATE_DATE,
			insertSt.setString(index++, "0");//PARTNER_LEVEL,
			insertSt.setString(index++, spVO.getNameCN());//PARTNER_NAME,
			insertSt.setString(index++, "");//PARTNER_PASSWORD,
			insertSt.setString(index++, spVO.getProvinceID());//PARTNER_AREA_CODE,
			insertSt.setString(index++, spVO.getWebsiteURL());//PARTNER_URL,
			insertSt.setString(index++, "");//PARTNER_IP,
			insertSt.setTimestamp(index++, getCurrentTimestamp());//CREATE_DATE,
			insertSt.setString(index++, spVO.getNameEN());//PARTNER_ENG_NAME,
			insertSt.setString(index++, spVO.getDescriptionEN());//PARTNER_ENG_DESC,
			insertSt.setString(index++, spVO.getSettlementCycle());//SETTLE_CYCLE,
			insertSt.setString(index++, spVO.getSettlementPayType());//SETTLE_PAY_METHOD,
			insertSt.setString(index++, spVO.getSettlementPercent());//SETTLE_RATE,
			insertSt.setString(index++, spVO.getCustomerCare());//CUST_SERVICE_PHONE,
			insertSt.setString(index++, spVO.getRoamProperty());//IF_ROAM,
			insertSt.setString(index++, spVO.getCompanyAddress());//COMPANY_ADDRESS,
			insertSt.setString(index++, spVO.getLegalRepresentative());//ARTIFICIAL_PERSON,
			insertSt.setString(index++, spVO.getPrincipal());//PRIMARY_PERSON_NAME,
			insertSt.setString(index++, spVO.getPrincipalTel());//PRIMARY_PERSON_PHONE
			insertSt.setString(index++, spVO.getPrincipalEmail());//PRIMARY_PERSON_EMAIL
			insertSt.setString(index++, spVO.getLicense());//BUSINESS_LICENSE
			insertSt.setDate(index++, parseDate(spVO.getContractExpireDate()));//CONTRACT_EXP_DATE
			insertSt.setString(index++, "");//COMPANY_CODE
			insertSt.setString(index++, spVO.getAccessNO());//PARTNER_NUMBER
			insertSt.setString(index++, spVO.getCSWebsite());//CUST_SERVICE_URL
			insertSt.setString(index++, systemId);//SYSTEM_CODE
			
			//where条件
			insertSt.setString(index++, spVO.getSPID());//PARTNER_CODE,
			insertSt.setString(index++, systemId);//SYSTEM_CODE
			
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
