package com.ztesoft.vsop.order;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.OracleConnect;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.ztesoft.vsop.order.vo.PartnerServiceAbilityVO;
import com.ztesoft.vsop.order.vo.SPSignInfoVO;
import com.ztesoft.vsop.order.dao.PartnerServiceAbilityDAO;

public class PartnerServiceAblityProcess {
	public static PartnerServiceAbilityVO parseXml(String xmlStr) throws DocumentException{
		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(xmlStr);
		Document doc = saxReader.read(reader);
		Element root = doc.getRootElement();
		PartnerServiceAbilityVO partnerServiceAbilityVO = new PartnerServiceAbilityVO();
		String streamingNo = root.element("streamingNo").getStringValue();
		String TimeStamp = root.element("TimeStamp").getStringValue();
		String OPFlag = root.element("OPFlag").getStringValue();
		
		List spInfoElementLst = root.elements("SPSignInfo");
		ArrayList lst = new ArrayList();
		if(spInfoElementLst!=null){
			for(int i=0;i<spInfoElementLst.size();i++){
				Element spInfoElement = (Element)spInfoElementLst.get(i);
				String SPSignID = spInfoElement.element("SPSignID").getStringValue();	
				String SPID = spInfoElement.element("SPID").getStringValue();
				String serviceCapabilityID = spInfoElement.element("serviceCapabilityID").getStringValue();
				String effectiveTime = spInfoElement.element("effectiveTime").getStringValue();
				String expiryTime = spInfoElement.element("expiryTime").getStringValue();
				String chargingPolicyID = spInfoElement.element("chargingPolicyID").getStringValue();
				
				SPSignInfoVO sPSignInfoVO = new SPSignInfoVO();
				sPSignInfoVO.setSPSignID(SPSignID);
				sPSignInfoVO.setSPID(SPID);
				sPSignInfoVO.setServiceCapabilityID(serviceCapabilityID);
				sPSignInfoVO.setEffectiveTime(effectiveTime);
				sPSignInfoVO.setExpiryTime(expiryTime);
				sPSignInfoVO.setChargingPolicyID(chargingPolicyID);
				if(OPFlag.equals("3"))
					sPSignInfoVO.setState("3");
				else
					sPSignInfoVO.setState("0");
					
				lst.add(sPSignInfoVO);
			}
		}
		
		partnerServiceAbilityVO.setStreamingNo(streamingNo);
		partnerServiceAbilityVO.setTimeStamp(TimeStamp);
		partnerServiceAbilityVO.setOPFlag(OPFlag);
		partnerServiceAbilityVO.setSPSignInfoLst(lst);
		
		
		return partnerServiceAbilityVO;
	}
	
	public static String process(String xmlStr,String systemId) throws DocumentException, SQLException{
		PartnerServiceAbilityVO partnerServiceAbilityVO = parseXml(xmlStr);
		String retXml = "";
		Connection conn = LegacyDAOUtil.getConnection();
		try{
			if(partnerServiceAbilityVO.getOPFlag().equals("1"))//–¬‘ˆ
				PartnerServiceAbilityDAO.savePartnerServiceAbilityId(partnerServiceAbilityVO,systemId,conn);
			else if(partnerServiceAbilityVO.getOPFlag().equals("2"))//–ﬁ∏ƒ
				PartnerServiceAbilityDAO.updatePartnerServiceAbilityId(partnerServiceAbilityVO,systemId,conn);
			else if(partnerServiceAbilityVO.getOPFlag().equals("3")){//…æ≥˝
				PartnerServiceAbilityDAO.updatePartnerServiceAbilityId(partnerServiceAbilityVO,systemId,conn);
			}
			retXml = getResult(partnerServiceAbilityVO.getStreamingNo(),"0");		
		}
		catch(SQLException se){
			conn.rollback();
			retXml = getResult(partnerServiceAbilityVO.getStreamingNo(),"1");
			se.printStackTrace();
		}
		finally{
			LegacyDAOUtil.commitAndCloseConnection(conn);
		}
		return retXml;
	}

	public static String getResult(String streamingNo,String resultCode){
		String retXml = "";
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<CSPCapabilitySyncResp>")
		.append("<streamingNo>").append(streamingNo).append("</streamingNo>")
		.append("<resultCode>").append(resultCode).append("</resultCode>")
		.append("</CSPCapabilitySyncResp>");
		retXml = bf.toString();
		return retXml;
	}	
	
	public static void main(String[] args) throws DocumentException, SQLException{
		
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<CSPCapabilitySyncReq>")
			.append("<streamingNo>").append("123456").append("</streamingNo>")
			.append("<TimeStamp>").append("2010-03-27").append("</TimeStamp>")
			.append("<OPFlag>").append("3").append("</OPFlag>")
			.append("<SPSignInfo>")
				.append("<SPSignID>").append("2021145").append("</SPSignID>")
				.append("<SPID>").append("2021145").append("</SPID>")
				.append("<serviceCapabilityID>").append("≤‚ ‘1").append("</serviceCapabilityID>")
				.append("<effectiveTime>").append("").append("</effectiveTime>")
				.append("<expiryTime>").append("").append("</expiryTime>")
				.append("<chargingPolicyID>").append("test").append("</chargingPolicyID>")
			.append("</SPSignInfo>")
		.append("</CSPCapabilitySyncReq>");
		
		System.out.println(bf.toString());
		process(bf.toString(),"1");
		
	}
}
