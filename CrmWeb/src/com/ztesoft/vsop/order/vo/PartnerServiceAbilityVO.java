package com.ztesoft.vsop.order.vo;

import java.util.ArrayList;

public class PartnerServiceAbilityVO {
	String streamingNo="";
	String TimeStamp="";
	String OPFlag="";
	ArrayList SPSignInfoLst = new ArrayList();
	
	public String getOPFlag() {
		return OPFlag;
	}
	public void setOPFlag(String flag) {
		OPFlag = flag;
	}
	public ArrayList getSPSignInfoLst() {
		return SPSignInfoLst;
	}
	public void setSPSignInfoLst(ArrayList signInfoLst) {
		SPSignInfoLst = signInfoLst;
	}
	public String getStreamingNo() {
		return streamingNo;
	}
	public void setStreamingNo(String streamingNo) {
		this.streamingNo = streamingNo;
	}
	public String getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}

}
