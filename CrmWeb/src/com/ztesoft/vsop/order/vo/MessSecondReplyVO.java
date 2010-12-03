package com.ztesoft.vsop.order.vo;

public class MessSecondReplyVO {
	private String streamingNo;
	private String timeStamp;
	private String systemId;
	private String prodSpecCode;
	private String productNo;
	private String rQCode;
	private String rQResult;
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
	public String getRQCode() {
		return rQCode;
	}
	public void setRQCode(String code) {
		rQCode = code;
	}
	public String getRQResult() {
		return rQResult;
	}
	public void setRQResult(String result) {
		rQResult = result;
	}
}
