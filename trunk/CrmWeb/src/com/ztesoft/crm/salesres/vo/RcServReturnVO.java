package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcServReturnVO extends ValueObject implements VO {

	private String rAcceptId = "";
	private String sAcceptId = "";
	private String custName = "";
	private String certificateTypeName = "";
	private String certificateNo = "";
	private String rCustTel = "";
	private String operId = "";
	private String aTime = "";
	
	private String rescInstanceId = "";
	private String salesRescId = "";

	public String getRescInstanceId() {
		return rescInstanceId;
	}

	public void setRescInstanceId(String rescInstanceId) {
		this.rescInstanceId = rescInstanceId;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public void setSalesRescId(String salesRescId) {
		this.salesRescId = salesRescId;
	}

	public RcServReturnVO() {}

	public RcServReturnVO( String prAcceptId, String psAcceptId, String pcustName, String pcertificateTypeName, String pcertificateNo, String prCustTel, String poperId, String paTime ) {
		rAcceptId = prAcceptId;
		sAcceptId = psAcceptId;
		custName = pcustName;
		certificateTypeName = pcertificateTypeName;
		certificateNo = pcertificateNo;
		rCustTel = prCustTel;
		operId = poperId;
		aTime = paTime;
	}

	public String getRAcceptId() {
		return rAcceptId;
	}

	public String getSAcceptId() {
		return sAcceptId;
	}

	public String getCustName() {
		return custName;
	}

	public String getCertificateTypeName() {
		return certificateTypeName;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public String getRCustTel() {
		return rCustTel;
	}

	public String getOperId() {
		return operId;
	}

	public String getATime() {
		return aTime;
	}

	public void setRAcceptId(String pRAcceptId) {
		rAcceptId = pRAcceptId;
	}

	public void setSAcceptId(String pSAcceptId) {
		sAcceptId = pSAcceptId;
	}

	public void setCustName(String pCustName) {
		custName = pCustName;
	}

	public void setCertificateTypeName(String pCertificateTypeName) {
		certificateTypeName = pCertificateTypeName;
	}

	public void setCertificateNo(String pCertificateNo) {
		certificateNo = pCertificateNo;
	}

	public void setRCustTel(String pRCustTel) {
		rCustTel = pRCustTel;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setATime(String pATime) {
		aTime = pATime;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("R_ACCEPT_ID",this.rAcceptId);
		hashMap.put("S_ACCEPT_ID",this.sAcceptId);
		hashMap.put("CUST_NAME",this.custName);
		hashMap.put("CERTIFICATE_TYPE_NAME",this.certificateTypeName);
		hashMap.put("CERTIFICATE_NO",this.certificateNo);
		hashMap.put("R_CUST_TEL",this.rCustTel);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("A_TIME",this.aTime);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.rAcceptId = (String) hashMap.get("R_ACCEPT_ID");
			this.sAcceptId = (String) hashMap.get("S_ACCEPT_ID");
			this.custName = (String) hashMap.get("CUST_NAME");
			this.certificateTypeName = (String) hashMap.get("CERTIFICATE_TYPE_NAME");
			this.certificateNo = (String) hashMap.get("CERTIFICATE_NO");
			this.rCustTel = (String) hashMap.get("R_CUST_TEL");
			this.operId = (String) hashMap.get("OPER_ID");
			this.aTime = (String) hashMap.get("A_TIME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RC_SERVICE_RETURN";
	}

}
