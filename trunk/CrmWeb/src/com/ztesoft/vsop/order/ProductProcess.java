package com.ztesoft.vsop.order;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.order.dao.ProductDAO;
import com.ztesoft.vsop.order.vo.ProductVO;
import com.ztesoft.vsop.product.vo.ProductSysInfo;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ProductProcess {
	
	
	
	public static ProductVO parseXml(String xmlStr) throws DocumentException{
		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(xmlStr);
		Document doc = saxReader.read(reader);
		Element root = doc.getRootElement();
		ProductVO productVO = new ProductVO();
		String StreamingNo = root.element("StreamingNo").getStringValue();
		String TimeStamp = root.element("TimeStamp").getStringValue();
		String SPID = root.element("SPID").getStringValue();
		
		String OPFlag = root.element("OPFlag").getStringValue();
		String ProductID = root.element("ProductID").getStringValue();
		
		String PnameCN = root.element("PnameCN").getStringValue();
		
		String PnameEN = root.element("PnameEN").getStringValue();
		String PdescriptionCN = root.element("PdescriptionCN").getStringValue();
		String PdescriptionEn = root.element("PdescriptionEn").getStringValue();
		String Status = root.element("Status").getStringValue();
		String Scope = root.element("Scope").getStringValue();
		String ProductHost = root.element("ProductHost").getStringValue();
		
		
		//业务平台 ArrayList
		ArrayList platformLst = new ArrayList();
		List PlatForm = root.elements("PlatForm");
		if(PlatForm!=null){
			for(int i=0;i<PlatForm.size();i++){
				Element subElement =  (Element)PlatForm.get(i);
				String platform_id = subElement.getStringValue();
				Map pf = new HashMap() ;
				pf.put("platform_id", platform_id) ;
				pf.put("product_id", ProductID ) ;
				platformLst.add( pf);
			}
		}
		
		//接入号 List
		ArrayList accNbrList = new ArrayList();
		List ProductOPCode = root.elements("ProductOPCode");
		if(ProductOPCode!=null){
			for(int i=0;i<ProductOPCode.size();i++){
				Element subElement =  (Element)ProductOPCode.get(i);
				String feature_no = subElement.element("FeatureStr").getStringValue();
				String access_no = subElement.element("AccessNO").getStringValue();
				String acc_nbr_type_cd = subElement.element("OPType").getStringValue();
				String match_mode = subElement.element("MatchMode").getStringValue();
				
				Map accNbr = new HashMap() ;
				accNbr.put("feature_no", feature_no) ;
				accNbr.put("access_no", access_no) ;
				accNbr.put("acc_nbr_type_cd", acc_nbr_type_cd) ;
				accNbr.put("match_mode", match_mode ) ;
				accNbr.put("product_id", ProductID ) ;
				
				accNbrList.add( accNbr );
			}
		}
		
		//业务能力 List
		ArrayList servAbilityLst = new ArrayList();
		List ServiceCapabilityID = root.elements("ServiceCapabilityID");
		if(ServiceCapabilityID!=null){
			for(int i=0;i<ServiceCapabilityID.size();i++){
				Element subElement =  (Element)ServiceCapabilityID.get(i);
				Map servMap = new HashMap() ;
				String service_code = subElement.getStringValue();
				servMap.put("service_code", service_code) ;
				servMap.put("product_id", ProductID ) ;
				servAbilityLst.add(servMap);
			}
		}
		
		//产品关系 List
		ArrayList refLst = new ArrayList();
		List ProdRelationElementLst = root.elements("ProdRelation");
		if(ProdRelationElementLst!=null){
			for(int i=0;i<ProdRelationElementLst.size();i++){
				Element subElement =  (Element)ProdRelationElementLst.get(i);
				String OPType = subElement.element("OPType").getStringValue();
				String RelationType = subElement.element("RelationType").getStringValue();
				String RProductID = subElement.element("RProductID").getStringValue();
				
				Map refMap = new HashMap() ;
				refMap.put("product_id", ProductID ) ;
				refMap.put("relation_type_cd", RelationType ) ;
				refMap.put("pro_product_id", RProductID ) ;
				refMap.put("OPType", OPType ) ;
				refMap.put("state_cd", "00A" ) ;
				refMap.put("create_date", new Date().toString() ) ;
				refMap.put("state_date", new Date().toString() ) ;
				
				refLst.add(refMap);
			}
		}		
				
		return productVO;
	}
	
	public static String process(String xmlStr,String systemId) throws DocumentException, SQLException{
		ProductVO productVO = parseXml(xmlStr);
		String retXml = "";
		Connection conn = LegacyDAOUtil.getConnection();
		try{
			if(productVO.getOPFlag().equals("1"))//新增
				ProductDAO.saveProductInfo(productVO,systemId,conn);
			else if(productVO.getOPFlag().equals("2"))//修改
				ProductDAO.updateProdcutInfo(productVO,systemId,conn);
			else if(productVO.getOPFlag().equals("3")){//删除
				productVO.setStatus("4");
				ProductDAO.updateProdcutInfo(productVO,systemId,conn);
			}
			retXml = getResult(productVO.getStreamingNo(),"0");	
		}
		catch(SQLException se){
			conn.rollback();
			retXml = getResult(productVO.getStreamingNo(),"1");
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
		bf.append("<ProductInfoSyncResp>")
		.append("<streamingNo>").append(streamingNo).append("</streamingNo>")
		.append("<resultCode>").append(resultCode).append("</resultCode>")
		.append("</ProductInfoSyncResp>");
		retXml = bf.toString();
		return retXml;
	}
	
	public static void main(String[] args) throws DocumentException, SQLException{
		
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<ProductInfoSyncReq>")
			.append("<streamingNo>").append("123456").append("</streamingNo>")
			.append("<OPFlag>").append("2").append("</OPFlag>")
			.append("<ProductID>").append("2021145").append("</ProductID>")
			.append("<SPID>").append("2021145").append("</SPID>")
			.append("<Status>").append("0").append("</Status>")
			.append("<chargingPolicyCN>").append("test").append("</chargingPolicyCN>")
			.append("<chargingPolicyID>").append("1").append("</chargingPolicyID>")
			.append("<subOption>").append("1").append("</subOption>")
			.append("<present>").append("1").append("</present>")
			.append("<corpOnly>").append("1").append("</corpOnly>")
			.append("<packageOnly>").append("1").append("</packageOnly>")
			.append("<productOPCode>").append("1").append("</productOPCode>")
			.append("<settlementCycle>").append("11").append("</settlementCycle>")
			.append("<settlementPayType>").append("22").append("</settlementPayType>")
			.append("<settlementPercent>").append("33").append("</settlementPercent>")
			.append("<Scope>").append("111").append("</Scope>")
			.append("<ProductHost>").append("1").append("</ProductHost>")
			.append("<PnameCN>").append("1").append("</PnameCN>")
			.append("<PnameEN>").append("1").append("</PnameEN>")
			.append("<PdescriptionCN>").append("1").append("</PdescriptionCN>")
			.append("<PdescriptionEn>").append("1").append("</PdescriptionEn>")
			.append("<serviceCapabilityID>").append("1").append("</serviceCapabilityID>")
			.append("<PlatForm>").append("12").append("</PlatForm>")
			.append("<ProdRelation>")
			.append("<opType>").append("0").append("</opType>")
			.append("<RelationType>").append("3").append("</RelationType>")
			.append("<RProductID>").append("2020966").append("</RProductID>")
			.append("</ProdRelation>")
			.append("<ProductOPCode>")
			.append("<featureStr>").append("0").append("</featureStr>")
			.append("<accessNO>").append("123").append("</accessNO>")
			.append("<opType>").append("0").append("</opType>")
			.append("<matchMode>").append("1").append("</matchMode>")
			.append("</ProductOPCode>")
		.append("</ProductInfoSyncReq>");
		
		System.out.println(bf.toString());
		process(bf.toString(),"1");
		
	}
}
