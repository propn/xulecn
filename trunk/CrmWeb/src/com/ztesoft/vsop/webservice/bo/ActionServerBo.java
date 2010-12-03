package com.ztesoft.vsop.webservice.bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.order.XMLUtils;

public class ActionServerBo {
	private static Logger logger = Logger.getLogger(ActionServerBo.class);
	private static ActionServerBo instance = new ActionServerBo();
	public static ActionServerBo getInstance(){
		return instance;
	}
	public String serverActionSV(String requestXml) {
		long star = System.currentTimeMillis();
		Document doc = null;
		String responseXml = null;
		try {
			doc = XMLUtils.parse(StringUtil.getInstance().getTagValue("SessionBody", requestXml));
			Element root = doc.getRootElement();
			String StreamingNo = XMLUtils.getElementStringValue(root,"StreamingNo");
			Element vprodElement;
			List vproductId = new ArrayList();
			for (Iterator it = root.elementIterator("VProductInfo"); it.hasNext();) {
				vprodElement = (Element) it.next();
				XMLUtils.getElementStringValue(vprodElement,"VProductID");
				vproductId.add(vproductId);
			}
			responseXml = StringUtil.getInstance().getVsopResponse("SrvActionFromVSOPResp", StreamingNo, "0", "", simulateResponse(StreamingNo, vproductId));
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("serverActionSV cost:" + processTime);
		logger.info("serverActionSV responseXml:" + responseXml);
		return responseXml;
	}
	private String simulateResponse(String streamNo, List vproductId) {
		StringBuffer bf = new StringBuffer();
		for (Iterator iterator = vproductId.iterator(); iterator.hasNext();) {
			String vprodId = (String) iterator.next();
			
			bf
			.append("<ResultInfo>")
			.append("<ProductID>").append(vprodId).append("<ProductID/>")
			.append("<OPResult>0").append("<OPResult/>")
			.append("<OPDesc>").append("<OPDesc/>")
			.append("<EffDate>").append("<EffDate/>")
			.append("<ExpDate>").append("<ExpDate/>")
			.append("</ResultInfo>");
		}
		return bf.toString();
	}
	public String subActionFromVSOP(String requestXml) {
		long star = System.currentTimeMillis();
		Document doc = null;
		String responseXml = null;
		try {
			doc = XMLUtils.parse(StringUtil.getInstance().getTagValue("SessionBody", requestXml));
			Element root = doc.getRootElement();
			String StreamingNo = XMLUtils.getElementStringValue(root,"StreamingNo");
			Element vprodElement;
			List vproductId = new ArrayList();
			for (Iterator it = root.elementIterator("ProductInfo"); it.hasNext();) {
				vprodElement = (Element) it.next();
				XMLUtils.getElementStringValue(vprodElement,"VProductID");
				vproductId.add(vproductId);
			}
			responseXml = StringUtil.getInstance().getVsopResponse("SubActionToISMPResp", StreamingNo, "0", "", simulateResponse(StreamingNo, vproductId));
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("subActionFromVSOP cost:" + processTime);
		logger.info("subActionFromVSOP responseXml:" + responseXml);
		return responseXml;
	}
	public String chgActionFromVSOP(String requestXml) {
		long star = System.currentTimeMillis();
		Document doc = null;
		String responseXml = null;
		try {
			doc = XMLUtils.parse(StringUtil.getInstance().getTagValue("SessionBody", requestXml));
			Element root = doc.getRootElement();
			String StreamingNo = XMLUtils.getElementStringValue(root,"StreamingNo");
			Element vprodElement;
			List vproductId = new ArrayList();
			for (Iterator it = root.elementIterator("ProductInfo"); it.hasNext();) {
				vprodElement = (Element) it.next();
				XMLUtils.getElementStringValue(vprodElement,"VProductID");
				vproductId.add(vproductId);
			}
			responseXml = StringUtil.getInstance().getVsopResponse("ChgActionToISMPResp", StreamingNo, "0", "", simulateResponse(StreamingNo, vproductId));
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("chgActionFromVSOP cost:" + processTime);
		logger.info("chgActionFromVSOP responseXml:" + responseXml);
		return responseXml;
	}
	public String unSubActionFromVSOP(String requestXml) {
		long star = System.currentTimeMillis();
		Document doc = null;
		String responseXml = null;
		try {
			doc = XMLUtils.parse(StringUtil.getInstance().getTagValue("SessionBody", requestXml));
			Element root = doc.getRootElement();
			String StreamingNo = XMLUtils.getElementStringValue(root,"StreamingNo");
			Element vprodElement;
			List vproductId = new ArrayList();
			for (Iterator it = root.elementIterator("ProductInfo"); it.hasNext();) {
				vprodElement = (Element) it.next();
				XMLUtils.getElementStringValue(vprodElement,"VProductID");
				vproductId.add(vproductId);
			}
			responseXml = StringUtil.getInstance().getVsopResponse("UnSubActionToISMPResp", StreamingNo, "0", "", simulateResponse(StreamingNo, vproductId));
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("unSubActionFromVSOP cost:" + processTime);
		logger.info("unSubActionFromVSOP responseXml:" + responseXml);
		return responseXml;
	}
}
