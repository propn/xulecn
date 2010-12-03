package com.ztesoft.vsop.engine.service.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ztesoft.vsop.engine.OrderEngine;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.IAccess;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class OfferCrmToVsopSynAccessService extends CommonAccessService implements
		IAccess {

	public Map concreteInOpertion(Map in) throws Exception {
		String requestXML = (String) in.get("accessInObject");
		CustomerOrder order=new CustomerOrder();
		this.loadFromXml(requestXML,order);
		in.put("busiObject", order);
		in.put("serviceCode", OrderEngine.SERVICE_OFFERCRMTOVSOPSYN34_STR);
		return in;
	}

	public Map concreteOutOpertion(Map in) throws Exception {
		CustomerOrder order = (CustomerOrder) in.get("busiObject");
		String resultCode = (String) in.get("resultCode");
		String resultMsg = (String) in.get("resultMsg");
		String responseXML = getResult(order.getCustSoNumber(),resultCode,resultMsg);
		in.put("accessOutObject", responseXML);
		return in;
	}
	public void loadFromXml(String inXML,CustomerOrder order) throws Exception{
		//this.setCompOfferId(XMLUtils.getSingleTagValue(inXML,"CompOfferId"));
//		this.createDate(XMLUtils.getSingleTagValue(inXML,"CompOfferId"));
		//this.setEffDate(XMLUtils.getSingleTagValue(inXML,"EffDate"));
		//this.setExpDate(XMLUtils.getSingleTagValue(inXML,"ExpDate"));
//		this.setInfOfferInstanceId(XMLUtils.getSingleTagValue(inXML,"EffDate"));
		//this.setLanId(XMLUtils.getSingleTagValue(inXML,"LanId"));
		String offerNbr = XMLUtils.getSingleTagValue(inXML,"OfferId");
		String offerName = XMLUtils.getSingleTagValue(inXML,"OfferName");
		String effDate = XMLUtils.getSingleTagValue(inXML,"EffDate");
		String expDate = XMLUtils.getSingleTagValue(inXML,"ExpDate");
		//this.setOfferId(XMLUtils.getSingleTagValue(inXML,"OfferId"));
		//this.setOfferInstId(XMLUtils.getSingleTagValue(inXML,"OfferInstId"));
		String opType=XMLUtils.getSingleTagValue(inXML,"OperType");
		//this.setOperType(XMLUtils.getSingleTagValue(inXML,"OperType"));
		order.setCustSoNumber(XMLUtils.getSingleTagValue(inXML,"StreamingNo"));
		//this.setSystemId(XMLUtils.getSingleTagValue(inXML,"SystemId"));
		//this.setTimeStamp(XMLUtils.getSingleTagValue(inXML,"TimeStamp"));
		//this.offerDetailInfo = new ArrayList();
		ProductOfferInfo productOffer  = new ProductOfferInfo();
		productOffer.setOfferId(DcSystemParamManager.getInstance().getProdOfferIdByNbr(offerNbr));
		productOffer.setOfferNbr(offerNbr);
		productOffer.setOfferName(offerName);
		productOffer.setEffDate(effDate);
		productOffer.setExpDate(expDate);
		List prodOfferList = new ArrayList();

		
		List productList = new ArrayList();
		String result  = "";
		StringBuffer bf = new StringBuffer();
		String tagName = "OfferInstDetail";
		bf.append("<").append(tagName ).append(">([\\s\\S]*?)</").append(tagName).append(">");
		Pattern pattern = Pattern.compile(bf.toString());
		Matcher matcher = pattern.matcher(inXML);
		String prodInstId = "";
		while(matcher.find()){
			VproductInfo product = new VproductInfo();
			result = matcher.group(1);
			//product.setVProductInstID(XMLUtils.getSingleTagValue(result,"InstanceRelationId"));
			String productNbr = XMLUtils.getSingleTagValue(result,"OfferDetailId");
			product.setVProductId(DcSystemParamManager.getInstance().getProductIdByNbr(productNbr));
			prodInstId=XMLUtils.getSingleTagValue(result,"ProductId");
			product.setVProductInstID(prodInstId);
			 
			String operType =XMLUtils.getSingleTagValue(result,"OperType");
			if("A".equals(operType))
				product.setActionType("0");
			else 
				product.setActionType("1");
			productList.add(product);
		}
		if(null ==prodInstId || "".equals(prodInstId))
			throw new Exception("用户实例不存在!");
		order.setProdInstId(prodInstId);
		order.setAccNbr(prodInstId);//为了写interface_log.acc_nbr
		order.setOrderSystem("200");
		productOffer.setVproductInfoList(productList);
		prodOfferList.add(productOffer);
		order.setProductOfferInfoList(prodOfferList);
	}
	private String getResult(String streamingNo,String resultCode,String resultName){
		String retXml = "";
		StringBuffer bf = new StringBuffer("");
//		String resultName = "";
		if(resultName == null || "".equals(resultName)){
			if(resultCode.equals("0")) resultName = "成功";
			else resultName = "失败";
		}
		bf.append("<ProdOfferSyncCrmToVSOPResp>")
		.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>")
		.append("<ResultCode>").append(resultCode).append("</ResultCode>")
		.append("<ResultDesc>").append(resultName).append("</ResultDesc>")
		.append("</ProdOfferSyncCrmToVSOPResp>");
		retXml = bf.toString();
		return retXml;
	}
}
