package com.ztesoft.vsop.order;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.powerise.ibss.framework.Const;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.OracleConnect;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.order.dao.ProductOfferDAO;
import com.ztesoft.vsop.order.vo.ProdRelationVO;
import com.ztesoft.vsop.order.vo.ProductOPCodeVO;
import com.ztesoft.vsop.order.vo.ProductOfferVO;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ProductOfferCRMProcess {
	public static ProductOfferVO parseXml(String xmlStr) throws DocumentException{
		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(xmlStr);
		Document doc = saxReader.read(reader);
		Element root2 = doc.getRootElement();
		Element root=root2.element("SessionBody").element("ProdOfferSyncToVSOPReq");
		ProductOfferVO productOfferVO = new ProductOfferVO();
		
		String streamingNo = root.element("streamingNo").getStringValue();
		String OPFlag = root.element("OPFlag").getStringValue();
		String PProductOfferID = root.element("PProductOfferID").getStringValue();
		String PackageID = root.element("PackageID").getStringValue();
		String PNameCN = root.element("PNameCN").getStringValue();
		String PNameEN = root.element("PNameEN").getStringValue();
		String PDesCN = root.element("PDesCN").getStringValue();
		String PDesEn = root.element("PDesEn").getStringValue();
		String Status = root.element("Status").getStringValue();
		String chargingPolicyCN = root.element("chargingPolicyCN").getStringValue();
		String chargingPolicyID = root.element("chargingPolicyID").getStringValue();
		String subOption = root.element("subOption").getStringValue();
		String presentOption = root.element("presentOption").getStringValue();
		String corpOnly = root.element("corpOnly").getStringValue();
		String Scope = root.element("Scope").getStringValue();
		String PackageHost = root.element("PackageHost").getStringValue();

		
		//产品构成 ArrayList
		ArrayList productLst = new ArrayList();
		List productElementLst = root.elements("productID");
		if(productElementLst!=null){
			for(int i=0;i<productElementLst.size();i++){
				Element subElement =  (Element)productElementLst.get(i);
				String productId = subElement.getStringValue();
				productLst.add(productId);
			}
		}
		
		//ProdOfferRelationLst
		ArrayList ProdOfferRelationLst = new ArrayList();
		List ProdOfferRelationElementLst = root.elements("ProdOfferRelation");
		if(ProdOfferRelationElementLst!=null){
			for(int i=0;i<ProdOfferRelationElementLst.size();i++){
				Element subElement =  (Element)ProdOfferRelationElementLst.get(i);
				String opType = subElement.element("opType").getStringValue();
				String RelationType = subElement.element("RelationType").getStringValue();
				String RProductID = subElement.element("RProdOfferID").getStringValue();
				
				ProdRelationVO prodRelationVO = new ProdRelationVO();
				prodRelationVO.setOpType(opType);
				prodRelationVO.setRelationType(RelationType);
				prodRelationVO.setRProductID(RProductID);
				ProdOfferRelationLst.add(prodRelationVO);
			}
		}		
		
		productOfferVO.setChargingPolicyCN(chargingPolicyCN);
		productOfferVO.setChargingPolicyID(chargingPolicyID);
		productOfferVO.setCorpOnly(corpOnly);
		productOfferVO.setOPFlag(OPFlag);
		productOfferVO.setPackageHost(PackageHost);
		productOfferVO.setPackageID(PProductOfferID);
		productOfferVO.setPDesCN(PDesCN);
		productOfferVO.setPDesEn(PDesEn);
		productOfferVO.setPNameCN(PNameCN);
		productOfferVO.setPNameEN(PNameEN);
		productOfferVO.setPresentOption(presentOption);
		productOfferVO.setProdOfferRelationLst(ProdOfferRelationLst);
		productOfferVO.setProductLst(productLst);
		productOfferVO.setScope(Scope);
		productOfferVO.setStreamingNo(streamingNo);
		productOfferVO.setSubOption(subOption);
		productOfferVO.setFeeSetFlag("02");
		productOfferVO.setProdOfferSubType("1");
		return productOfferVO;
	}
	
	public static String process(String xmlStr,String systemId) throws DocumentException, SQLException{
		ProductOfferVO productOfferVO = parseXml(xmlStr);
		String retXml = "";
		Connection conn = LegacyDAOUtil.getConnection();
		StringUtil su = StringUtil.getInstance();
		try{
			if(productOfferVO.getOPFlag().equals("1"))//新增
				ProductOfferDAO.saveProductOfferInfo(productOfferVO,systemId,conn);
			else if(productOfferVO.getOPFlag().equals("2"))//修改
				ProductOfferDAO.updateProdcutOfferInfo(productOfferVO,systemId,conn);
			else if(productOfferVO.getOPFlag().equals("3")){//删除
				productOfferVO.setStatus("4");
				ProductOfferDAO.updateProdcutOfferInfo(productOfferVO,systemId,conn);
			}
//			retXml = getResult(productOfferVO.getStreamingNo(),"0");	
			retXml = su.getVsopResponse("ProdOfferSyncToVSOPResp", 
										productOfferVO.getStreamingNo(),
								   		 "1",
								   		 "同步成功", 
								   		 null);
		}
		catch(SQLException se){
			conn.rollback();
//			retXml = getResult(productOfferVO.getStreamingNo(),"1");
			retXml = su.getVsopResponse("ProdOfferSyncToVSOPResp", 
										productOfferVO.getStreamingNo(),
								   		 "1",
								   		 "同步成功", 
								   		 null);
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
		bf.append("<Response>").append("<ProdOfferSyncToVSOPResp>")
		.append("<streamingNo>").append(streamingNo).append("</streamingNo>")
		.append("<resultCode>").append(resultCode).append("</resultCode>")
		.append("</ProdOfferSyncToVSOPResp>").append("</Response>");
		retXml = bf.toString();
		return retXml;
	}
	
	public static void main(String[] args) throws DocumentException, SQLException{
		
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<Request>").append("<ProdOfferSyncToVSOPReq>")
			.append("<streamingNo>").append("123456").append("</streamingNo>")
			.append("<OPFlag>").append("2").append("</OPFlag>")
			.append("<PProductOfferID>").append("2021145").append("</PProductOfferID>")
			.append("<PackageID>").append("2021145").append("</PackageID>")
			.append("<PNameCN>").append("2021145").append("</PNameCN>")
			.append("<PNameEN>").append("0").append("</PNameEN>")
			.append("<PDesCN>").append("test").append("</PDesCN>")
			.append("<PDesEn>").append("1").append("</PDesEn>")
			.append("<Status>").append("1").append("</Status>")
			.append("<chargingPolicyCN>").append("1").append("</chargingPolicyCN>")
			.append("<chargingPolicyID>").append("1").append("</chargingPolicyID>")
			.append("<subOption>").append("1").append("</subOption>")
			.append("<presentOption>").append("1").append("</presentOption>")
			.append("<corpOnly>").append("11").append("</corpOnly>")
			.append("<Scope>").append("22").append("</Scope>")
			.append("<PackageHost>").append("33").append("</PackageHost>")
			.append("<productID>").append("2021145").append("</productID>")
			
			
			.append("<ProdOfferRelation>")
			.append("<opType>").append("0").append("</opType>")
			.append("<RelationType>").append("3").append("</RelationType>")
			.append("<RProdOfferID>").append("2020966").append("</RProdOfferID>")
			.append("</ProdOfferRelation>")
			
		.append("</ProdOfferSyncToVSOPReq>").append("</Request>");
		
		System.out.println(bf.toString());
		process(bf.toString(),"1");
		
	}
}
