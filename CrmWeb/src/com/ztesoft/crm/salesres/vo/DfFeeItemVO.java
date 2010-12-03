package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class DfFeeItemVO extends ValueObject implements VO {

	private String feeitemCode = "";
	private String feeitemName = "";
	private String feekindCode = "";
	private String invoiceType = "";
	private String feerule = "";
	private String feeruleType = "";
	private String returnrule = "";
	private String returnruleType = "";
	private String billCode = "";
	private String catgId = "";
	private String validFlag = "";

	public DfFeeItemVO() {}

	public DfFeeItemVO( String pfeeitemCode, String pfeeitemName, String pfeekindCode, String pinvoiceType, String pfeerule, String pfeeruleType, String preturnrule, String preturnruleType, String pbillCode, String pcatgId, String pvalidFlag ) {
		feeitemCode = pfeeitemCode;
		feeitemName = pfeeitemName;
		feekindCode = pfeekindCode;
		invoiceType = pinvoiceType;
		feerule = pfeerule;
		feeruleType = pfeeruleType;
		returnrule = preturnrule;
		returnruleType = preturnruleType;
		billCode = pbillCode;
		catgId = pcatgId;
		validFlag = pvalidFlag;
	}

	public String getFeeitemCode() {
		return feeitemCode;
	}

	public String getFeeitemName() {
		return feeitemName;
	}

	public String getFeekindCode() {
		return feekindCode;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public String getFeerule() {
		return feerule;
	}

	public String getFeeruleType() {
		return feeruleType;
	}

	public String getReturnrule() {
		return returnrule;
	}

	public String getReturnruleType() {
		return returnruleType;
	}

	public String getBillCode() {
		return billCode;
	}

	public String getCatgId() {
		return catgId;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setFeeitemCode(String pFeeitemCode) {
		feeitemCode = pFeeitemCode;
	}

	public void setFeeitemName(String pFeeitemName) {
		feeitemName = pFeeitemName;
	}

	public void setFeekindCode(String pFeekindCode) {
		feekindCode = pFeekindCode;
	}

	public void setInvoiceType(String pInvoiceType) {
		invoiceType = pInvoiceType;
	}

	public void setFeerule(String pFeerule) {
		feerule = pFeerule;
	}

	public void setFeeruleType(String pFeeruleType) {
		feeruleType = pFeeruleType;
	}

	public void setReturnrule(String pReturnrule) {
		returnrule = pReturnrule;
	}

	public void setReturnruleType(String pReturnruleType) {
		returnruleType = pReturnruleType;
	}

	public void setBillCode(String pBillCode) {
		billCode = pBillCode;
	}

	public void setCatgId(String pCatgId) {
		catgId = pCatgId;
	}

	public void setValidFlag(String pValidFlag) {
		validFlag = pValidFlag;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("FEEITEM_CODE",this.feeitemCode);
		hashMap.put("FEEITEM_NAME",this.feeitemName);
		hashMap.put("FEEKIND_CODE",this.feekindCode);
		hashMap.put("INVOICE_TYPE",this.invoiceType);
		hashMap.put("FEERULE",this.feerule);
		hashMap.put("FEERULE_TYPE",this.feeruleType);
		hashMap.put("RETURNRULE",this.returnrule);
		hashMap.put("RETURNRULE_TYPE",this.returnruleType);
		hashMap.put("BILL_CODE",this.billCode);
		hashMap.put("CATALOG_ID",this.catgId);
		hashMap.put("VALID_FLAG",this.validFlag);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.feeitemCode = (String) hashMap.get("FEEITEM_CODE");
			this.feeitemName = (String) hashMap.get("FEEITEM_NAME");
			this.feekindCode = (String) hashMap.get("FEEKIND_CODE");
			this.invoiceType = (String) hashMap.get("INVOICE_TYPE");
			this.feerule = (String) hashMap.get("FEERULE");
			this.feeruleType = (String) hashMap.get("FEERULE_TYPE");
			this.returnrule = (String) hashMap.get("RETURNRULE");
			this.returnruleType = (String) hashMap.get("RETURNRULE_TYPE");
			this.billCode = (String) hashMap.get("BILL_CODE");
			this.catgId = (String) hashMap.get("CATALOG_ID");
			this.validFlag = (String) hashMap.get("VALID_FLAG");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "DF_FEE_ITEM";
	}

}
