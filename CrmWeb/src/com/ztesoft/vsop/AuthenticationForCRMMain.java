package com.ztesoft.vsop;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ztesoft.vsop.order.dao.ProductDAO;
import com.ztesoft.vsop.order.OrderRelationISMPDAO;
import com.ztesoft.vsop.order.OrderRelationISMPVO;
import com.ztesoft.vsop.order.XMLUtils;

public class AuthenticationForCRMMain {
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws DocumentException 
	 */
	public static void main(String[] args) throws SQLException, DocumentException {
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<SubscribeAuthReq>")
			.append("<StreamingNo>").append("123456").append("</StreamingNo>")
			.append("<TimeStamp>").append("20091212").append("</TimeStamp>")
			//.append("<ProductOfferId>").append("1").append("</ProductOfferId>")
			
			/*.append("<ProductInfo>")
			.append("<ProdSpecCode>").append("2020966").append("</ProdSpecCode>")
			.append("<ProductNo>").append("13377050499").append("</ProductNo>")
			
			.append("<VProductInfo>")
			.append("<ActionType>").append("0").append("</ActionType>")
			.append("<VProductID>").append("2021145").append("</VProductID>")
			.append("</VProductInfo>")				
			
			.append("<VProductInfo>")
			.append("<ActionType>").append("0").append("</ActionType>")
			.append("<VProductID>").append("seq_").append("</VProductID>")
			.append("</VProductInfo>")
			
			.append("</ProductInfo>")*/
			
		.append("</SubscribeAuthReq>");
		
		System.out.println(bf.toString());
		process(bf.toString());
	}
	
	public static String process(String xmlStr) throws DocumentException, SQLException{
		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(xmlStr);
		System.out.println(xmlStr);
		Document doc = saxReader.read(reader);
		Element requestEle = doc.getRootElement();
		Element serssionBodyEle = requestEle.element("SessionBody");
		Element root = serssionBodyEle.element("SubAuthFromCRMReq");
		ArrayList productLst = new ArrayList();
		String streamingNo = "";
		String TimeStamp = "";
		String SystemId = "";
		String ProductOfferId = "";
		List ProductInfoLst = root.elements("ProductInfo");
		StringBuffer bf = new StringBuffer("");
		ArrayList lst = new ArrayList();//增值产品列表
		ArrayList incrLst = new ArrayList();//附属产品列表
		ArrayList ProductNoLst = new ArrayList();//号码列表
		try{
		streamingNo = XMLUtils.getElementStringValue(root,"StreamingNo");
		TimeStamp = XMLUtils.getElementStringValue(root,"TimeStamp"); 
		SystemId = XMLUtils.getElementStringValue(root,"SystemId");  
		ProductOfferId = XMLUtils.getElementStringValue(root,"ProductOfferId"); 
		if(ProductInfoLst!=null){
			Connection conn = LegacyDAOUtil.getConnection();
			for(int i=0;i<ProductInfoLst.size();i++){
				Element ProductInfoElement = (Element)ProductInfoLst.get(i);
				String ProdSpecCode = XMLUtils.getElementStringValue(ProductInfoElement,"ProdSpecCode"); 
				String ProductNo = XMLUtils.getElementStringValue(ProductInfoElement,"ProductNo");
				List VProductInfoLst = ProductInfoElement.elements("VProductInfo");//增值产品
				List AProductInfoLst = ProductInfoElement.elements("AProductInfo");//附属产品
				
				HashMap productNoMap = new HashMap();
				productNoMap.put("ProductNo",ProductNo);
				productNoMap.put("ProdSpecCode",ProdSpecCode);
				ProductNoLst.add(productNoMap);
				
				if(VProductInfoLst!=null){
					for(int m=0;m<VProductInfoLst.size();m++){
						Element VProductInfoLstElement = (Element)VProductInfoLst.get(m);
						String ActionType = XMLUtils.getElementStringValue(VProductInfoLstElement,"ActionType"); 
						String VProductID =  XMLUtils.getElementStringValue(VProductInfoLstElement,"VProductID"); 
						HashMap spProductInfoMap = new HashMap();//增值产品
						ProductDAO productDAO = new ProductDAO();
						//String SPProdSpecID = productDAO.getProductIdByCode(VProductID,SystemId,conn);
						String SPProdSpecID = VProductID;
						if(SPProdSpecID==null||SPProdSpecID.equals("")){
							String retStr = getRetStr("增值产品"+VProductID+"在系统中不存在",streamingNo,ProductNo,ProdSpecCode,"99");
							return retStr;
						}
						spProductInfoMap.put("SPProdSpecID",SPProdSpecID);
						spProductInfoMap.put("ActionType",ActionType);
						spProductInfoMap.put("ProductOfferId",ProductOfferId);
						spProductInfoMap.put("ProductOfferType","2");
						lst.add(spProductInfoMap);
					}
				}
				
				if(AProductInfoLst!=null){
					for(int m=0;m<AProductInfoLst.size();m++){
						Element AProductInfoLstElement = (Element)AProductInfoLst.get(m);
						String ActionType = XMLUtils.getElementStringValue(AProductInfoLstElement,"ActionType"); 
						String AProductID = XMLUtils.getElementStringValue(AProductInfoLstElement,"AProductID");
						HashMap incrMap = new HashMap();
						incrMap.put("ProductId",AProductID);
						incrMap.put("ActionType",ActionType);
						incrLst.add(incrMap);
					}
				}
				HashMap productMap = new HashMap();
				productMap.put("SPProdInfo",lst);
				productMap.put("IncrProdInfo",incrLst);
				productMap.put("ProductNo",ProductNo);
				productMap.put("ProductId",ProdSpecCode);
				productMap.put("LanId","");//去掉默认值，默认为空串
				productLst.add(productMap);
			}
			LegacyDAOUtil.commitAndCloseConnection(conn);
		}


		String retXml = "";
		bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<Response>")
		.append("<SubAuthFromCRMResp>")
		.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>");
		
		ArrayList resultLst = AuthenticationMain.process(productLst,SystemId);
		if(resultLst!=null){
			HashMap retMap = (HashMap)resultLst.get(0); 
			String ResultCode = (String)retMap.get("ResultCode");
			String ResultDesc = (String)retMap.get("ResultDesc");
			ArrayList retLst = (ArrayList)retMap.get("lst");
			bf.append("<ResultCode>").append(ResultCode).append("</ResultCode>")
			.append("<ResultDesc>").append(ResultDesc).append("</ResultDesc>");
			
			for(int m=0;m<ProductNoLst.size();m++){
				HashMap ProductNoMap = (HashMap)ProductNoLst.get(m);
				String ProductNoIn = (String)ProductNoMap.get("ProductNo");
				String ProdSpecCode = (String)ProductNoMap.get("ProdSpecCode");
				bf.append("<ProductInfo>")
				.append("<ProdSpecCode>").append(ProdSpecCode).append("</ProdSpecCode>")
				.append("<ProductNo>").append(ProductNoIn).append("</ProductNo>");
				
				for(int i=0;i<retLst.size();i++){
					HashMap map = (HashMap)retLst.get(i);
					String resultCode = (String)map.get("Result");
					String ProductNo = (String)map.get("ProductNo");
					String SPProdSpecId = (String)map.get("SPProdSpecId");
					String FailureNote = (String)map.get("FailureNote");
					String SPProdSpecName = (String)map.get("SPProdSpecName");
					if(ProductNoIn.equals(ProductNo)){
						bf.append("<VProductInfo>")
						.append("<VProductID>").append(SPProdSpecId).append("</VProductID>")
						.append("<VProductName>").append(SPProdSpecName).append("</VProductName>")
						.append("<OPResult>").append(resultCode).append("</OPResult>")
						.append("<OPDesc>").append(FailureNote).append("</OPDesc>")
						.append("</VProductInfo>");
					}
				}
				bf.append("</ProductInfo>");
			}
			bf.append("</SubAuthFromCRMResp>")
			.append("</Response>");
		}
		}
		catch(Exception e){
			bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bf.append("<Response>")
			.append("<SubAuthFromCRMResp>")
			.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>");
			bf.append("<OPResult>").append("999").append("</OPResult>")
			.append("<OPDesc>").append(e.toString()).append("</OPDesc>")
			.append("</ProductInfo>")
			.append("</SubAuthFromCRMResp>")
			.append("</Response>");
		}
		System.out.println(bf.toString());
		return bf.toString();
	}
	
	public static String getRetStr(String resultInfo,String streamingNo,String ProdSpecCode,String ProductNoIn,String resultCode){
		StringBuffer bf = new StringBuffer();
		bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<Response>")
		.append("<SubAuthFromCRMResp>")
		.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>");
		bf.append("<ProductInfo>")
		.append("<ProdSpecCode>").append(ProdSpecCode).append("</ProdSpecCode>")
		.append("<ProductNo>").append(ProductNoIn).append("</ProductNo>");
		bf.append("<VProductInfo>")
		.append("<resultCode>").append(resultCode).append("</resultCode>")
		.append("<FailureNote>").append(resultInfo).append("</FailureNote>")
		.append("</VProductInfo>")
		.append("</SubAuthFromCRMResp>")
		.append("</Response>");
		
		return bf.toString();
	}
}
