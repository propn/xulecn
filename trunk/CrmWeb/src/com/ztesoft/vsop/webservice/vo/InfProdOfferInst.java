package com.ztesoft.vsop.webservice.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ztesoft.vsop.order.XMLUtils;

public class InfProdOfferInst {
	private String infOfferInstanceId = "";
	private String offerInstId = "";
	private String offerId = "";
	private String compOfferId = "";
	private String effDate = "";
	private String expDate = "";
	private String operType = "";
	private String lanId = "";
	private String state = "";
	private String stateDate = "";
	private String createDate = "";
	private String sendTimes = "";
	private String streamingNo = "";
	private String timeStamp = "";
	private String systemId = "";
	private List offerDetailInfo = null; 
	public String getInfOfferInstanceId() {
		return infOfferInstanceId;
	}
	public void setInfOfferInstanceId(String infOfferInstanceId) {
		this.infOfferInstanceId = infOfferInstanceId;
	}
	public String getOfferInstId() {
		return offerInstId;
	}
	public void setOfferInstId(String offerInstId) {
		this.offerInstId = offerInstId;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getCompOfferId() {
		return compOfferId;
	}
	public void setCompOfferId(String compOfferId) {
		this.compOfferId = compOfferId;
	}
	public String getEffDate() {
		return effDate;
	}
	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getLanId() {
		return lanId;
	}
	public void setLanId(String lanId) {
		this.lanId = lanId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateDate() {
		return stateDate;
	}
	public void setStateDate(String stateDate) {
		this.stateDate = stateDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getSendTimes() {
		return sendTimes;
	}
	public void setSendTimes(String sendTimes) {
		this.sendTimes = sendTimes;
	}
	
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getStreamingNo() {
		return streamingNo;
	}
	public void setStreamingNo(String streamingNo) {
		this.streamingNo = streamingNo;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public List getOfferDetailInfo() {
		return offerDetailInfo;
	}
	public void setOfferDetailInfo(List offerDetailInfo) {
		this.offerDetailInfo = offerDetailInfo;
	}
	public void loadFromXml(String inXML){
		this.setCompOfferId(XMLUtils.getSingleTagValue(inXML,"CompOfferId"));
//		this.createDate(XMLUtils.getSingleTagValue(inXML,"CompOfferId"));
		this.setEffDate(XMLUtils.getSingleTagValue(inXML,"EffDate"));
		this.setExpDate(XMLUtils.getSingleTagValue(inXML,"ExpDate"));
//		this.setInfOfferInstanceId(XMLUtils.getSingleTagValue(inXML,"EffDate"));
		this.setLanId(XMLUtils.getSingleTagValue(inXML,"LanId"));
		this.setOfferId(XMLUtils.getSingleTagValue(inXML,"OfferId"));
		this.setOfferInstId(XMLUtils.getSingleTagValue(inXML,"OfferInstId"));
		this.setOperType(XMLUtils.getSingleTagValue(inXML,"OperType"));
		this.setStreamingNo(XMLUtils.getSingleTagValue(inXML,"StreamingNo"));
		this.setSystemId(XMLUtils.getSingleTagValue(inXML,"SystemId"));
		this.setTimeStamp(XMLUtils.getSingleTagValue(inXML,"TimeStamp"));
		this.offerDetailInfo = new ArrayList();
		String result  = "";
		StringBuffer bf = new StringBuffer();
		String tagName = "OfferInstDetail";
		bf.append("<").append(tagName ).append(">(.*?)</").append(tagName).append(">");
		Pattern pattern = Pattern.compile(bf.toString());
		Matcher matcher = pattern.matcher(inXML);
		while(matcher.find()){
			result = matcher.group(1);
			OfferDetailInfo detail = new OfferDetailInfo(result);
			this.offerDetailInfo.add(detail);
		}
	}
}
