package com.ztesoft.vsop.order.vo.response;


public class BaseResponse {
	private String rootNodeName = "result";
	private String StreamingNo = "";
	private String ResultCode ="";
	private String ResultDesc = "";
	
	/*
	 * resultCode
	 */
	public static final String RESULT_SUCCESS = "0";
	public static final String RESULT_FAILURE = "-1";
	
	public String getStreamingNo() {
		return StreamingNo;
	}
	public void setStreamingNo(String streamingNo) {
		StreamingNo = streamingNo;
	}
	public String getResultCode() {
		return ResultCode;
	}
	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}
	public String getResultDesc() {
		return ResultDesc;
	}
	public void setResultDesc(String resultDesc) {
		ResultDesc = resultDesc;
	}
	public String getRootNodeName() {
		return rootNodeName;
	}
	public void setRootNodeName(String rootNodeName) {
		this.rootNodeName = rootNodeName;
	}
	
	public String toXml(){
		StringBuffer bf = new StringBuffer();
		bf.append("<").append(getRootNodeName()).append(">")
				.append("<StreamingNo>").append(getStreamingNo()).append("</StreamingNo>")
				.append("<ResultCode>").append(getResultCode()).append("</ResultCode>")
				.append("<ResultDesc>").append(getResultDesc()).append("</ResultDesc>");
		bf.append("</").append(getRootNodeName()).append(">");
		
		return bf.toString();
	}
}
