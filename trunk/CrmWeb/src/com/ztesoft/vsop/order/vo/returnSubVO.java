package com.ztesoft.vsop.order.vo;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.vsop.DateUtil;

public class returnSubVO {
	private String orderFeedId;
	private String inTime;
	private String orderId;
	private String outOrderId;
	private String streamNo;
	private String prodSpecCode;
	private String productNo;
	private String systemId;
	private String threadId;
	private List returnList;
	public String getOrderFeedId() {
		return orderFeedId;
	}
	public void setOrderFeedId(String orderFeedId) {
		this.orderFeedId = orderFeedId;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	
	public String getProdSpecCode() {
		return prodSpecCode;
	}
	public void setProdSpecCode(String prodSpecCode) {
		this.prodSpecCode = prodSpecCode;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getThreadId() {
		return threadId;
	}
	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	public List getReturnList() {
		if(returnList == null)returnList = new ArrayList();
		return returnList;
	}
	public void setReturnList(List returnList) {
		this.returnList = returnList;
	}
	public String toXML(){
		StringBuffer xml = new StringBuffer();
		xml.append("<Request>").append("<SessionBody>")
		   .append("<SendSubRTFromVSOPReq>").append("<StreamingNo>").append("123").append("</StreamingNo>")
		   .append("<TimeStamp>").append(DateUtil.getVSOPDateTime14()).append("</TimeStamp>").append("<SystemId>").append(this.getSystemId())
		   .append("</SystemId>").append("<OrderId>").append(this.getOrderId()).append("</OrderId>")
		   .append("<ProdSpecCode>").append(this.getProdSpecCode()).append("</ProdSpecCode>")
		   .append("<ProductNo>").append(this.getProductNo()).append("</ProductNo>");
		List list = this.getReturnList();
		for(int i=0;i<list.size();i++){
			returnProdOfferVO vo = (returnProdOfferVO)list.get(i);
			xml.append("<ResultInfo>").append("<ProductOfferID>").append(vo.getProductOfferID()).append("</ProductOfferID>")
			   .append("<OPResult>").append(vo.getOPResult()).append("</OPResult>")
			   .append("<OPDesc>").append(vo.getOPDesc()).append("</OPDesc>")
			   .append("</ResultInfo>");
		}
		xml.append("</SendSubRTFromVSOPReq>").append("</SessionBody>").append("</Request>");
		return xml.toString();
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStreamNo() {
		return streamNo;
	}
	public void setStreamNo(String streamNo) {
		this.streamNo = streamNo;
	}
	public String getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}
}
