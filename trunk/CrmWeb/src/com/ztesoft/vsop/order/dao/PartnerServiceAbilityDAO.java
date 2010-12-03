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
import com.ztesoft.vsop.order.vo.PartnerServiceAbilityVO;
import com.ztesoft.vsop.order.vo.SPSignInfoVO;
import com.ztesoft.vsop.order.vo.SpVO;

public class PartnerServiceAbilityDAO {
	private static String SEQ_PARTNER_SERVICE_ABILITY_ID = "SEQ_PARTNER_SERVICE_ABILITY_ID";
	private static String SQL_INSERT = "insert into PARTNER_SERVICE_ABILITY (PARTNER_SERVICE_ABILITY_ID,SERVICE_CODE,PARTNER_ID,STATE,STATE_DATE,EFF_DATE,EXP_DATE)" +
			" values (?,?,?,?,?,?,?)";
	private static String SQL_UPDATE = "update PARTNER_SERVICE_ABILITY set SERVICE_CODE=?,PARTNER_ID=?,STATE=?,STATE_DATE=?,EFF_DATE=?,EXP_DATE=? where PARTNER_ID=? and SERVICE_CODE=?";	
	
	public static boolean savePartnerServiceAbilityId(PartnerServiceAbilityVO partnerServiceAbilityVO,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			
			
			//保存订单
			insertSt = conn.prepareStatement(SQL_INSERT);
			if(partnerServiceAbilityVO.getSPSignInfoLst()!=null){
				for(int i=0;i<partnerServiceAbilityVO.getSPSignInfoLst().size();i++){
					SPSignInfoVO sPSignInfoVO = new SPSignInfoVO();
					sPSignInfoVO = (SPSignInfoVO)partnerServiceAbilityVO.getSPSignInfoLst().get(i);
					int index = 1;
					long partnerServiceAbilityId = getSequence(SEQ_PARTNER_SERVICE_ABILITY_ID, conn);
					insertSt.setLong(index++, partnerServiceAbilityId);//PARTNER_SERVICE_ABILITY_ID
					insertSt.setString(index++, sPSignInfoVO.getServiceCapabilityID());//SERVICE_CODE, 
					insertSt.setString(index++, getSpIdByCode(sPSignInfoVO.getSPID(),systemId,conn));//PARTNER_ID,
					insertSt.setString(index++, sPSignInfoVO.getState());//STATE,
					insertSt.setTimestamp(index++, getCurrentTimestamp());//STATE_DATE,
					insertSt.setTimestamp(index++, parseTimestamp(sPSignInfoVO.getEffectiveTime()));//EFF_DATE,
					insertSt.setTimestamp(index++, parseTimestamp(sPSignInfoVO.getExpiryTime()));//EXP_DATE,
					count = insertSt.executeUpdate();
				}
			}
			
			if(insertSt!=null) insertSt.close();
			
		return true;
	}

	public static boolean updatePartnerServiceAbilityId(PartnerServiceAbilityVO partnerServiceAbilityVO,String systemId, Connection conn) throws SQLException {
		PreparedStatement insertSt = null;
//		PreparedStatement extPstm = null;
		int count = -1;
			//保存订单
			insertSt = conn.prepareStatement(SQL_UPDATE);
			if(partnerServiceAbilityVO.getSPSignInfoLst()!=null){
				for(int i=0;i<partnerServiceAbilityVO.getSPSignInfoLst().size();i++){
					SPSignInfoVO sPSignInfoVO = new SPSignInfoVO();
					sPSignInfoVO = (SPSignInfoVO)partnerServiceAbilityVO.getSPSignInfoLst().get(i);
					int index = 1;
					String spId = getSpIdByCode(sPSignInfoVO.getSPID(),systemId,conn);
					insertSt.setString(index++, sPSignInfoVO.getServiceCapabilityID());//SERVICE_CODE, 
					insertSt.setString(index++, spId);//PARTNER_ID,
					insertSt.setString(index++, sPSignInfoVO.getState());//STATE,
					insertSt.setTimestamp(index++, getCurrentTimestamp());//STATE_DATE,
					insertSt.setTimestamp(index++, parseTimestamp(sPSignInfoVO.getEffectiveTime()));//EFF_DATE,
					insertSt.setTimestamp(index++, parseTimestamp(sPSignInfoVO.getExpiryTime()));//EXP_DATE,
					
					//where 条件
					insertSt.setString(index++, spId);//PARTNER_ID,
					insertSt.setString(index++, sPSignInfoVO.getServiceCapabilityID());//SERVICE_CODE, 
					
					count = insertSt.executeUpdate();
				}
			}
			
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
