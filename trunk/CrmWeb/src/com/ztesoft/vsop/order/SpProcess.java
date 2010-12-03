package com.ztesoft.vsop.order;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;

import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.OracleConnect;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.ztesoft.vsop.order.vo.SpVO;
import com.ztesoft.vsop.order.dao.SPinfoDAO;

public class SpProcess {
	public static SpVO parseXml(String xmlStr) throws DocumentException{
		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(xmlStr);
		Document doc = saxReader.read(reader);
		Element root = doc.getRootElement();
		SpVO spVO = new SpVO();
		String streamingNo = root.element("StreamingNo").getStringValue();
		String TimeStamp = root.element("TimeStamp").getStringValue();
		String SystemId = root.element("SystemId").getStringValue();
		String OPFlag = root.element("OPFlag").getStringValue();
		
		Element spInfoElement = root.element("SPInfo");
		String SPID = spInfoElement.element("SPID").getStringValue();	
		String type = spInfoElement.element("Type").getStringValue();
		String nameCN = spInfoElement.element("NameCN").getStringValue();
		String nameEN = spInfoElement.element("NameEN").getStringValue();
		String descriptionCN = spInfoElement.element("DescriptionCN").getStringValue();
		String descriptionEN = spInfoElement.element("DescriptionEN").getStringValue();
		String customerCare = spInfoElement.element("CustomerCare").getStringValue();
		String websiteURL = spInfoElement.element("WebsiteURL").getStringValue();
		String provinceID = spInfoElement.element("ProvinceID").getStringValue();
		String roamProperty = spInfoElement.element("RoamProperty").getStringValue();
		String companyAddress = spInfoElement.element("CompanyAddress").getStringValue();
		String legalRepresentative = spInfoElement.element("LegalRepresentative").getStringValue();
		String principal = spInfoElement.element("Principal").getStringValue();
		String principalTel = spInfoElement.element("principalTel").getStringValue();
		String principalEmail = spInfoElement.element("principalEmail").getStringValue();
		String serviceManager = spInfoElement.element("ServiceManager").getStringValue();
		String serviceManagerTel = spInfoElement.element("ServiceManagerTel").getStringValue();
		String serviceManagerEmail = spInfoElement.element("ServiceManagerEmail").getStringValue();
		String serviceManagerAddr = spInfoElement.element("ServiceManagerAddr").getStringValue();
		String serviceManagerPC = spInfoElement.element("ServiceManagerPC").getStringValue();
		String serviceManagerFax = spInfoElement.element("ServiceManagerFax").getStringValue();
		String license = spInfoElement.element("License").getStringValue();
		String contractExpireDate = spInfoElement.element("ContractExpireDate").getStringValue();
		String accessNO = spInfoElement.element("AccessNO").getStringValue();
		String settlementCycle = spInfoElement.element("SettlementCycle").getStringValue();
		String settlementPayType = spInfoElement.element("SettlementPayType").getStringValue();
		String settlementPercent = spInfoElement.element("SettlementPercent").getStringValue();
		String CSWebsite = spInfoElement.element("CSWebsite").getStringValue();
		String status = spInfoElement.element("Status").getStringValue();
		String PlatForm = spInfoElement.element("PlatForm").getStringValue();
		
		spVO.setAccessNO(accessNO);
		spVO.setCompanyAddress(companyAddress);
		spVO.setContractExpireDate(contractExpireDate);
		spVO.setCSWebsite(CSWebsite);
		spVO.setCustomerCare(customerCare);
		spVO.setDescriptionCN(descriptionCN);
		spVO.setDescriptionEN(descriptionEN);
		spVO.setLegalRepresentative(legalRepresentative);
		spVO.setLicense(license);
		spVO.setNameCN(nameCN);
		spVO.setNameEN(nameEN);
		spVO.setOPFlag(OPFlag);
		spVO.setPrincipal(principal);
		spVO.setPrincipalEmail(principalEmail);
		spVO.setPrincipalTel(principalTel);
		spVO.setProvinceID(provinceID);
		spVO.setRoamProperty(roamProperty);
		spVO.setServiceManager(serviceManager);
		spVO.setServiceManagerAddr(serviceManagerAddr);
		spVO.setServiceManagerEmail(serviceManagerEmail);
		spVO.setServiceManagerFax(serviceManagerFax);
		spVO.setServiceManagerPC(serviceManagerPC);
		spVO.setServiceManagerTel(serviceManagerTel);
		spVO.setSettlementCycle(settlementCycle);
		spVO.setSettlementPayType(settlementPayType);
		spVO.setSettlementPercent(settlementPercent);
		spVO.setSPID(SPID);
		spVO.setStatus(status);
		spVO.setStreamingNo(streamingNo);
		spVO.setTimeStamp(TimeStamp);
		spVO.setType(type);
		spVO.setWebsiteURL(websiteURL);
		
		
		
		return spVO;
	}
	
	public static String process(String xmlStr,String systemId) throws DocumentException, SQLException{
		SpVO spVO = parseXml(xmlStr);
		String retXml = "";
		Connection conn = LegacyDAOUtil.getConnection();
		try{
			if(spVO.getOPFlag().equals("1"))//新增
				SPinfoDAO.saveSPInfo(spVO,systemId,conn);
			else if(spVO.getOPFlag().equals("2"))//修改
				SPinfoDAO.updateSPInfo(spVO,systemId,conn);
			else if(spVO.getOPFlag().equals("3")){//删除
				spVO.setStatus("3");
				SPinfoDAO.updateSPInfo(spVO,systemId,conn);
			}
			retXml = getResult(spVO.getStreamingNo(),"0");		
		}
		catch(SQLException se){
			conn.rollback();
			retXml = getResult(spVO.getStreamingNo(),"1");
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
		bf.append("<CSPInfoSyncResp>")
		.append("<streamingNo>").append(streamingNo).append("</streamingNo>")
		.append("<resultCode>").append(resultCode).append("</resultCode>")
		.append("</CSPInfoSyncResp>");
		retXml = bf.toString();
		return retXml;
	}	
	
	public static void main(String[] args) throws DocumentException, SQLException{
		
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<CSPInfoSyncReq>")
			.append("<streamingNo>").append("123456").append("</streamingNo>")
			.append("<TimeStamp>").append("2010-03-27").append("</TimeStamp>")
			.append("<OPFlag>").append("3").append("</OPFlag>")
			.append("<SPInfo>")
			.append("<SPID>").append("2021145").append("</SPID>")
			.append("<type>").append("0").append("</type>")
			.append("<nameCN>").append("测试1").append("</nameCN>")
			.append("<nameEN>").append("test").append("</nameEN>")
			.append("<descriptionCN>").append("测试1").append("</descriptionCN>")
			.append("<descriptionEN>").append("test").append("</descriptionEN>")
			.append("<customerCare>").append("6").append("</customerCare>")
			.append("<websiteURL>").append("www.sina.com.cn").append("</websiteURL>")
			.append("<provinceID>").append("1").append("</provinceID>")
			.append("<roamProperty>").append("1").append("</roamProperty>")
			.append("<companyAddress>").append("华景新城").append("</companyAddress>")
			.append("<legalRepresentative>").append("中兴软创").append("</legalRepresentative>")
			.append("<principal>").append("中兴软创").append("</principal>")
			
			.append("<principalTel>").append("1234").append("</principalTel>")
			.append("<principalEmail>").append("1234").append("</principalEmail>")
			.append("<serviceManager>").append("12356").append("</serviceManager>")
			.append("<serviceManagerTel>").append("1111111").append("</serviceManagerTel>")
			.append("<serviceManagerEmail>").append("11111").append("</serviceManagerEmail>")
			.append("<serviceManagerAddr>").append("111111").append("</serviceManagerAddr>")
			.append("<serviceManagerPC>").append("1111").append("</serviceManagerPC>")
			.append("<serviceManagerFax>").append("111").append("</serviceManagerFax>")
			.append("<license>").append("wwwsss").append("</license>")
			.append("<contractExpireDate>").append("2012-12-12").append("</contractExpireDate>")
			.append("<accessNO>").append("10001").append("</accessNO>")
			.append("<settlementCycle>").append("10").append("</settlementCycle>")
			.append("<settlementPayType>").append("1").append("</settlementPayType>")
			.append("<settlementPercent>").append("60").append("</settlementPercent>")
			.append("<CSWebsite>").append("www.qq.com").append("</CSWebsite>")
			.append("<status>").append("0").append("</status>")
			.append("</SPInfo>")
		.append("</CSPInfoSyncReq>");
		
		System.out.println(bf.toString());
		process(bf.toString(),"1");
		
	}
}
