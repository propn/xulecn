package com.ztesoft.oaas.vo;

import java.io.Serializable;

public class SmsVo implements Serializable {
	private long     id;//sms_id;
	private String   chargeTermid;  //CHARGETERMID 发送号码
	private String   destTermid;// DESTTERMID  目的号码
	private String   msgContent;//MSGCONTENT  内容
	private String   procFlag;//PROC_FLAG   --‘0’
	public void setId(long id){
		this.id=id;
	}
	public long getId(){
		return id;
	}
	public void setChargeTermid(String chargeTermid){
		this.chargeTermid=chargeTermid;
	}
	public String getChargeTermid(){
		return chargeTermid;
	}
	public void setDestTermid(String destTermid){
		this.destTermid=destTermid;
	}
	public String getDestTermid(){
		return destTermid;
	}
	public void setMsgContent(String msgContent){
		this.msgContent=msgContent;
	}
	public String getMsgContent(){
		return msgContent;
	}
	public void setProcFlag(String procFlag){
		this.procFlag=procFlag;
	}
	public String getProcFlag(){
		return procFlag;
	}

}
