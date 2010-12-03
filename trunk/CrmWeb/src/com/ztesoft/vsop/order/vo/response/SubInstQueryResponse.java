package com.ztesoft.vsop.order.vo.response;

import java.util.Iterator;
import java.util.List;

import com.ztesoft.vsop.order.vo.VproducInfo;

public class SubInstQueryResponse {
	private String streamingNo;
	private List VproducInfos;
	private String resultCode;
	private String resultDesc;
	
	public SubInstQueryResponse(){
		
	}
	
	public SubInstQueryResponse(String streamingNo, List vproducInfos) {
		setResultCode("0");
		setResultDesc("success");
		this.streamingNo = streamingNo;
		VproducInfos = vproducInfos;
	}
	
	public SubInstQueryResponse(String streamingNo2, Exception e) {
		setResultCode("-1");
		setResultDesc(e.getMessage());
		this.streamingNo = streamingNo2;
	}

	public String toXml(){
		StringBuffer bf = new StringBuffer("");
		bf.append("").append("<Response>").append("<SubInstQryFromVSOPResp>")
			.append("<StreamingNo>").append(getStreamingNo()).append("</StreamingNo>")
			.append("<ResultCode>").append(getResultCode()).append("</ResultCode>")
			.append("<ResultDesc>").append(getResultDesc()).append("</ResultDesc>");
		if(VproducInfos != null){
			for (Iterator iterator = VproducInfos.iterator(); iterator.hasNext();) {
				VproducInfo product = (VproducInfo) iterator.next();
				bf.append("<ResultInfo>")
				.append("<VproductID>").append(product.getVproductID()).append("</VproductID>")
				.append("<VproductName>").append(product.getVproductName()).append("</VproductName>")
				.append("<SPID>").append(product.getSPID()).append("</SPID>")
				.append("<SPName>").append(product.getSPName()).append("</SPName>")
				.append("<ProductOfferType>").append(product.getProductOfferType()).append("</ProductOfferType>")
				.append("<ProductOfferID>").append(product.getProductOfferID()).append("</ProductOfferID>")
				.append("<ProductOfferName>").append(product.getProductOfferName()).append("</ProductOfferName>")
				.append("<ChargingPolicyCN>").append(product.getChargingPolicyCN()).append("</ChargingPolicyCN>")
				.append("<Status>").append(product.getStatus()).append("</Status>")
				.append("<SubscribeTime>").append(product.getSubscribeTime()).append("</SubscribeTime>")
				.append("<EffDate>").append(product.getEffDate()).append("</EffDate>")
				.append("<ExpDate>").append(product.getExpDate()).append("</ExpDate>")
				.append("<ChannelPlayerID>").append(product.getChannelPlayerID()).append("</ChannelPlayerID>")
				.append("</ResultInfo>");
			}
		}
		bf.append("</SubInstQryFromVSOPResp>").append("</Response>");
		return bf.toString();
	}
	
	//request参数为空时返回
	public String toXMLForParamError(){
		StringBuffer bf = new StringBuffer("");
		bf.append("<").append("SubInstQryFromVSOPResp").append(">")
			.append("<StreamingNo>").append(getStreamingNo()).append("</StreamingNo>")
			.append("<ResultCode>").append("1").append("</ResultCode>")
			.append("<ResultDesc>").append("参数错误").append("</ResultDesc>");
		bf.append("</").append("SubInstQryFromVSOPResp").append(">");
		return bf.toString();
	}
	
	public String getStreamingNo() {
		return streamingNo;
	}
	public void setStreamingNo(String streamingNo) {
		this.streamingNo = streamingNo;
	}
	public List getVproducInfos() {
		return VproducInfos;
	}
	public void setVproducInfos(List vproducInfos) {
		VproducInfos = vproducInfos;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	
}
