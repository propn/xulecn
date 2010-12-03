package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class AttrRestrictVO extends ValueObject implements VO {

	private String offDetaId = "";
	private String prodId = "";
	private String attrId = "";
	private String offId = "";
	private String controlType = "";
	private String valueList = "";
	private String ruleId = "";
	private String attrName="";
	private String valueKind="";

	public AttrRestrictVO() {}

	public AttrRestrictVO( String poffDetaId, String pprodId, String pattrId, String poffId, String pcontrolType, String pvalueList, String pruleId ) {
		offDetaId = poffDetaId;
		prodId = pprodId;
		attrId = pattrId;
		offId = poffId;
		controlType = pcontrolType;
		valueList = pvalueList;
		ruleId = pruleId;
	}

	public String getOffDetaId() {
		return offDetaId;
	}

	public String getProdId() {
		return prodId;
	}

	public String getAttrId() {
		return attrId;
	}

	public String getOffId() {
		return offId;
	}

	public String getControlType() {
		return controlType;
	}

	public String getValueList() {
		return valueList;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setOffDetaId(String pOffDetaId) {
		offDetaId = pOffDetaId;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setAttrId(String pAttrId) {
		attrId = pAttrId;
	}

	public void setOffId(String pOffId) {
		offId = pOffId;
	}

	public void setControlType(String pControlType) {
		controlType = pControlType;
	}

	public void setValueList(String pValueList) {
		valueList = pValueList;
	}

	public void setRuleId(String pRuleId) {
		ruleId = pRuleId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("OFFER_DETAIL_ID",this.offDetaId);
		hashMap.put("PRODUCT_ID",this.prodId);
		hashMap.put("ATTR_ID",this.attrId);
		hashMap.put("OFFER_ID",this.offId);
		hashMap.put("CONTROL_TYPE",this.controlType);
		hashMap.put("VALUE_LIST",this.valueList);
		hashMap.put("RULE_ID",this.ruleId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.offDetaId = (String) hashMap.get("OFFER_DETAIL_ID");
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.attrId = (String) hashMap.get("ATTR_ID");
			this.offId = (String) hashMap.get("OFFER_ID");
			this.controlType = (String) hashMap.get("CONTROL_TYPE");
			this.valueList = (String) hashMap.get("VALUE_LIST");
			this.ruleId = (String) hashMap.get("RULE_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ATTR_ID");
		arrayList.add("OFFER_DETAIL_ID");
		arrayList.add("PRODUCT_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ATTRIBUTE_RESTRICT";
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getValueKind() {
		return valueKind;
	}

	public void setValueKind(String valueKind) {
		this.valueKind = valueKind;
	}

}
