package com.ztesoft.vsop.engine.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class UserinfoSyncMsgVO extends ValueObject implements VO {

	private String msgId ;
	private String systemId ;
	private String servUrl ;
	private String createDate ;
	private String custOrderId ;
	private String accNbr ;
	private String requestXml ;
	private String responseXml ;
	private String state ;
	private String dealTime ;
	private String resultDesc ;
	private String threadId ;

	public UserinfoSyncMsgVO() {}

	public UserinfoSyncMsgVO( String pmsgId, String psystemId, String pservUrl, String pcreateDate, String pcustOrderId, String paccNbr, String prequestXml, String presponseXml, String pstate, String pdealTime, String presultDesc, String pthreadId ) {
		msgId = pmsgId;
		systemId = psystemId;
		servUrl = pservUrl;
		createDate = pcreateDate;
		custOrderId = pcustOrderId;
		accNbr = paccNbr;
		requestXml = prequestXml;
		responseXml = presponseXml;
		state = pstate;
		dealTime = pdealTime;
		resultDesc = presultDesc;
		threadId = pthreadId;
	}

	public String getMsgId() {
		return msgId;
	}

	public String getSystemId() {
		return systemId;
	}

	public String getServUrl() {
		return servUrl;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getCustOrderId() {
		return custOrderId;
	}

	public String getAccNbr() {
		return accNbr;
	}

	public String getRequestXml() {
		return requestXml;
	}

	public String getResponseXml() {
		return responseXml;
	}

	public String getState() {
		return state;
	}

	public String getDealTime() {
		return dealTime;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setMsgId(String pMsgId) {
		msgId = pMsgId;
	}

	public void setSystemId(String pSystemId) {
		systemId = pSystemId;
	}

	public void setServUrl(String pServUrl) {
		servUrl = pServUrl;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setCustOrderId(String pCustOrderId) {
		custOrderId = pCustOrderId;
	}

	public void setAccNbr(String pAccNbr) {
		accNbr = pAccNbr;
	}

	public void setRequestXml(String pRequestXml) {
		requestXml = pRequestXml;
	}

	public void setResponseXml(String pResponseXml) {
		responseXml = pResponseXml;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setDealTime(String pDealTime) {
		dealTime = pDealTime;
	}

	public void setResultDesc(String pResultDesc) {
		resultDesc = pResultDesc;
	}

	public void setThreadId(String pThreadId) {
		threadId = pThreadId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("MSG_ID",this.msgId);
		hashMap.put("SYSTEM_ID",this.systemId);
		hashMap.put("SERVICE_URL",this.servUrl);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("CUST_ORDER_ID",this.custOrderId);
		hashMap.put("ACC_NBR",this.accNbr);
		hashMap.put("REQUEST_XML",this.requestXml);
		hashMap.put("RESPONSE_XML",this.responseXml);
		hashMap.put("STATE",this.state);
		hashMap.put("DEAL_TIME",this.dealTime);
		hashMap.put("RESULT_DESC",this.resultDesc);
		hashMap.put("THREAD_ID",this.threadId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.msgId = (String) hashMap.get("MSG_ID");
			this.systemId = (String) hashMap.get("SYSTEM_ID");
			this.servUrl = (String) hashMap.get("SERVICE_URL");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.custOrderId = (String) hashMap.get("CUST_ORDER_ID");
			this.accNbr = (String) hashMap.get("ACC_NBR");
			this.requestXml = (String) hashMap.get("REQUEST_XML");
			this.responseXml = (String) hashMap.get("RESPONSE_XML");
			this.state = (String) hashMap.get("STATE");
			this.dealTime = (String) hashMap.get("DEAL_TIME");
			this.resultDesc = (String) hashMap.get("RESULT_DESC");
			this.threadId = (String) hashMap.get("THREAD_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("MSG_ID");
		return arrayList;
	}

	public String getTableName() {
		return "USERINFO_SYNC_MSG";
	}

}
