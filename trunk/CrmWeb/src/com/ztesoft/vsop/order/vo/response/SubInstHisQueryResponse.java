package com.ztesoft.vsop.order.vo.response;

import java.util.Iterator;
import java.util.List;

import com.ztesoft.vsop.order.SystemCode;
import com.ztesoft.vsop.order.vo.VproducInfo;

public class SubInstHisQueryResponse {
	
	public SubInstHisQueryResponse() {
	}

	public SubInstHisQueryResponse(String streamingNo2, Exception e) {
		setResultCode("-1");
		setResultDesc(e.getMessage());
		this.streamingNo = streamingNo2;
	}
	
	public SubInstHisQueryResponse(String streamingNo2, List products) {
		setResultCode("0");
		setStreamingNo(streamingNo2);
		setVproducInfos(products);
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

	private String streamingNo = "";
	private List VproducInfos ;
	private String resultCode = "";
	private String resultDesc = "";
	public String toXml() {
		StringBuffer bf = new StringBuffer("");
		
		bf.append("").append("<Response>").append("<SubInstHisQryFromVSOPResp>")
			.append("<StreamingNo>").append(getStreamingNo()).append("</StreamingNo>")
			.append("<ResultCode>").append(getResultCode()).append("</ResultCode>")
			.append("<ResultDesc>").append(getResultDesc()).append("</ResultDesc>");
		if(VproducInfos != null){
			for (Iterator iterator = VproducInfos.iterator(); iterator.hasNext();) {
				VproducInfo product = (VproducInfo) iterator.next();
				
				//江西本地化，过滤IT侧 订购历史查询，只返回订购历史页面中处理状态为：处理成功的 记录
				String staticValue = com.ztesoft.common.util.CrmConstants.JX_PROV_CODE;
				String provinceCode = com.ztesoft.vsop.web.DcSystemParamManager.getParameter("DC_PROVINCE_CODE");
				if(provinceCode.equals(staticValue)){
					//江西 只返回 竣工10F， 人工回正常10M的记录
					if(VproducInfo.FINISH.equals( product.getState_code())||VproducInfo.MANUAL_FEEDBACK_SUCC.equals( product.getState_code())){
						
						bf.append("<ResultInfo>")
						.append("<VproductID>").append(product.getProductNbr()).append("</VproductID>")
						.append("<VproductName>").append(product.getVproductName()).append("</VproductName>")
						.append("<SPID>").append(product.getSPID()).append("</SPID>")
						.append("<SPName>").append(product.getSPName()).append("</SPName>")
						.append("<ProductOfferType>").append(product.getProductOfferType()).append("</ProductOfferType>")
						.append("<ProductOfferID>").append(product.getProdOfferNbr()).append("</ProductOfferID>")
						.append("<ProductOfferName>").append(product.getProductOfferName()).append("</ProductOfferName>")
						.append("<ChargingPolicyCN>").append(product.getChargingPolicyCN()).append("</ChargingPolicyCN>")
						.append("<ActionType>").append(product.getActionType()).append("</ActionType>")
						.append("<SubscribeTime>").append(product.getSubscribeTime()).append("</SubscribeTime>")
						.append("<EffDate>").append(product.getEffDate()).append("</EffDate>")
						.append("<ExpDate>").append(product.getExpDate()).append("</ExpDate>")
						.append("<ChannelPlayerID>").append(getChannelNameById(product.getChannelPlayerID())).append("</ChannelPlayerID>")
						.append("</ResultInfo>");
					}
					
				}else{
				//---------------------------江西本地化结束----------------------------------
					bf.append("<ResultInfo>")
					.append("<VproductID>").append(product.getProductNbr()).append("</VproductID>")
					.append("<VproductName>").append(product.getVproductName()).append("</VproductName>")
					.append("<SPID>").append(product.getSPID()).append("</SPID>")
					.append("<SPName>").append(product.getSPName()).append("</SPName>")
					.append("<ProductOfferType>").append(product.getProductOfferType()).append("</ProductOfferType>")
					.append("<ProductOfferID>").append(product.getProdOfferNbr()).append("</ProductOfferID>")
					.append("<ProductOfferName>").append(product.getProductOfferName()).append("</ProductOfferName>")
					.append("<ChargingPolicyCN>").append(product.getChargingPolicyCN()).append("</ChargingPolicyCN>")
					.append("<ActionType>").append(product.getActionType()).append("</ActionType>")
					.append("<SubscribeTime>").append(product.getSubscribeTime()).append("</SubscribeTime>")
					.append("<EffDate>").append(product.getEffDate()).append("</EffDate>")
					.append("<ExpDate>").append(product.getExpDate()).append("</ExpDate>")
					.append("<ChannelPlayerID>").append(product.getChannelPlayerID()).append("</ChannelPlayerID>")
					.append("</ResultInfo>");
				}
			}
		}
		bf.append("</SubInstHisQryFromVSOPResp>").append("</Response>");
		return bf.toString();
	}

	/**
	 * 系统编码翻译为系统名称
	 * @author yi.chengfeng 2010-9-28
	 * @param channelPlayerID
	 * @return
	 */
	private String getChannelNameById(String channelPlayerID) {
		
		return SystemCode.getSystemName(channelPlayerID);
	}
}
