package com.ztesoft.vsop.simulate.service;

import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.test.SubscribeServiceClient;

public class SiOrderService {
	
	public List subInstQryFromVSOP(String prodCode,String productNo,int pi,int ps) throws RemoteException, DocumentException{
		String requestXml = createRequestXml(prodCode,productNo);
		String ret =  new SubscribeServiceClient().subInstQryFromVSOP(requestXml);
		return extractFromXml(ret);
	}

	private String createRequestXml(String prodCode, String productNo) {
		StringBuffer bf = new StringBuffer();
		bf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<SessionBody>")
		.append("<SubInstQryFromVSOPReq>")
			.append("<StreamingNo>").append(generateTimemillis()).append("</StreamingNo>")
			.append("<TimeStamp>").append(generateTimemillis()).append("</TimeStamp>")
			.append("<SystemId>").append("").append("</SystemId>")
			.append("<ProdSpecCode>").append(prodCode).append("</ProdSpecCode>")
			.append("<ProductNo>").append(productNo).append("</ProductNo>")
		.append("</SubInstQryFromVSOPReq>")
		.append("</SessionBody>")
		;
		String xml= bf.toString();
		return xml;
	}
	private long generateTimemillis() {
		long l = System.currentTimeMillis();
		return l;
	}
	
	private List extractFromXml(String requestXml) throws DocumentException{
		System.out.println("从VSOP Web Service 返回查询订购关系报文= "+requestXml);
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		.append("<root>");
		requestXml = bf.toString()+requestXml+"</root>";
		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(requestXml);
		Document doc = saxReader.read(reader);
		Element root = doc.getRootElement();
		Element tmpEl;
		List result = new ArrayList();
		for (Iterator itr = root.elementIterator("ResultInfo"); itr.hasNext();) {
			tmpEl = (Element) itr.next();
			Map m = new HashMap();
			m.put("SPName", XMLUtils.getElementStringValue(tmpEl,"SPName"));
			m.put("Status", XMLUtils.getElementStringValue(tmpEl,"Status"));
			m.put("ChannelPlayerID", XMLUtils.getElementStringValue(tmpEl,"ChannelPlayerID"));
			m.put("ProductOfferName", XMLUtils.getElementStringValue(tmpEl,"ProductOfferName"));
			m.put("VproductID", XMLUtils.getElementStringValue(tmpEl,"VproductID"));
			m.put("VproductName", XMLUtils.getElementStringValue(tmpEl,"VproductName"));
			m.put("Status", XMLUtils.getElementStringValue(tmpEl,"Status"));
			m.put("EffDate", XMLUtils.getElementStringValue(tmpEl,"EffDate"));
			m.put("ExpDate", XMLUtils.getElementStringValue(tmpEl,"ExpDate"));
			m.put("SubscribeTime", XMLUtils.getElementStringValue(tmpEl,"SubscribeTime"));
			m.put("ProductOfferType", XMLUtils.getElementStringValue(tmpEl,"ProductOfferType"));
			m.put("ProductOfferID", XMLUtils.getElementStringValue(tmpEl,"ProductOfferID"));
			result.add(m);
		}
		
		return result;
	}
	/**
	 * 退订增值产品
	 * @param productIds 增值产品id字符串逗号分隔
	 * @param accNbr     产品号码，退订的产品号码，如手机号码
	 * @return
	 * @throws RemoteException 
	 * @throws DocumentException 
	 */
	public String delProducts(String accNbr,String productIds) throws RemoteException, DocumentException{
		String ret =  new SubscribeServiceClient().delProduct(accNbr,productIds);
		Document doc = XMLUtils.parse(ret);
		Element root = doc.getRootElement();
		String resultCode = XMLUtils.getElementStringValue(root,"resultCode");
		if(resultCode.equals("0")){
			return "true";
		}else{
			return "false";
		}
	}
}
