package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcServAcceptVO extends ValueObject implements VO {

	private String sAcceptId = "";
	private String rescInstanceId = "";
	private String salesRescId = "";
	private String rescInstanceCode = "";
	private String sInfo = "";
	private String rCustName = "";
	private String rCustTel = "";
	private String logId = "";
	private String rDate = "";
	private String operId = "";
	private String aTime = "";
	private String lanId = "";
	private String state = "";
	private String prodId = "";
	private String prodNo = "";
	private String custId = "";
	private String custName = "";
	
	private String familyId = "";
	private String salesRescName ="";
	

	public RcServAcceptVO() {}

	public RcServAcceptVO( String psAcceptId, String prescInstanceId, String psalesRescId, String prescInstanceCode, String psInfo, String prCustName, String prCustTel, String plogId, String prDate, String poperId, String paTime, String planId, String pstate, String pprodId, String pprodNo, String pcustId, String pcustName ) {
		sAcceptId = psAcceptId;
		rescInstanceId = prescInstanceId;
		salesRescId = psalesRescId;
		rescInstanceCode = prescInstanceCode;
		sInfo = psInfo;
		rCustName = prCustName;
		rCustTel = prCustTel;
		logId = plogId;
		rDate = prDate;
		operId = poperId;
		aTime = paTime;
		lanId = planId;
		state = pstate;
		prodId = pprodId;
		prodNo = pprodNo;
		custId = pcustId;
		custName = pcustName;
	}

	public String getSAcceptId() {
		return sAcceptId;
	}

	public String getRescInstanceId() {
		return rescInstanceId;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public String getRescInstanceCode() {
		return rescInstanceCode;
	}

	public String getSInfo() {
		return sInfo;
	}

	public String getRCustName() {
		return rCustName;
	}

	public String getRCustTel() {
		return rCustTel;
	}

	public String getLogId() {
		return logId;
	}

	public String getRDate() {
		return rDate;
	}

	public String getOperId() {
		return operId;
	}

	public String getATime() {
		return aTime;
	}

	public String getLanId() {
		return lanId;
	}

	public String getState() {
		return state;
	}

	public String getProdId() {
		return prodId;
	}

	public String getProdNo() {
		return prodNo;
	}

	public String getCustId() {
		return custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setSAcceptId(String pSAcceptId) {
		sAcceptId = pSAcceptId;
	}

	public void setRescInstanceId(String pRescInstanceId) {
		rescInstanceId = pRescInstanceId;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public void setRescInstanceCode(String pRescInstanceCode) {
		rescInstanceCode = pRescInstanceCode;
	}

	public void setSInfo(String pSInfo) {
		sInfo = pSInfo;
	}

	public void setRCustName(String pRCustName) {
		rCustName = pRCustName;
	}

	public void setRCustTel(String pRCustTel) {
		rCustTel = pRCustTel;
	}

	public void setLogId(String pLogId) {
		logId = pLogId;
	}

	public void setRDate(String pRDate) {
		rDate = pRDate;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setATime(String pATime) {
		aTime = pATime;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setProdNo(String pProdNo) {
		prodNo = pProdNo;
	}

	public void setCustId(String pCustId) {
		custId = pCustId;
	}

	public void setCustName(String pCustName) {
		custName = pCustName;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("S_ACCEPT_ID",this.sAcceptId);
		hashMap.put("RESOURCE_INSTANCE_ID",this.rescInstanceId);
		hashMap.put("SALES_RESOURCE_ID",this.salesRescId);
		hashMap.put("RESOURCE_INSTANCE_CODE",this.rescInstanceCode);
		hashMap.put("S_INFO",this.sInfo);
		hashMap.put("R_CUST_NAME",this.rCustName);
		hashMap.put("R_CUST_TEL",this.rCustTel);
		hashMap.put("LOG_ID",this.logId);
		hashMap.put("R_DATE",this.rDate);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("A_TIME",this.aTime);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("STATE",this.state);
		hashMap.put("PRODUCT_ID",this.prodId);
		hashMap.put("PRODUCT_NO",this.prodNo);
		hashMap.put("CUST_ID",this.custId);
		hashMap.put("CUST_NAME",this.custName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.sAcceptId = (String) hashMap.get("S_ACCEPT_ID");
			this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.rescInstanceCode = (String) hashMap.get("RESOURCE_INSTANCE_CODE");
			this.sInfo = (String) hashMap.get("S_INFO");
			this.rCustName = (String) hashMap.get("R_CUST_NAME");
			this.rCustTel = (String) hashMap.get("R_CUST_TEL");
			this.logId = (String) hashMap.get("LOG_ID");
			this.rDate = (String) hashMap.get("R_DATE");
			this.operId = (String) hashMap.get("OPER_ID");
			this.aTime = (String) hashMap.get("A_TIME");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.state = (String) hashMap.get("STATE");
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.prodNo = (String) hashMap.get("PRODUCT_NO");
			this.custId = (String) hashMap.get("CUST_ID");
			this.custName = (String) hashMap.get("CUST_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RC_SERVICE_ACCEPT";
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getSalesRescName() {
		return salesRescName;
	}

	public void setSalesRescName(String salesRescName) {
		this.salesRescName = salesRescName;
	}

}
