package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcAttrDefVO extends ValueObject implements VO {

	private String familyId = "";
	private String attrId = "";
	private String attrOwnerType = "";
	private String attrValueUnitId = "";
	private String attrValue = "";
	private String attrName = "";
	private String attrValueId = "";
	private String inputMethod = "";

	public RcAttrDefVO() {}

	public RcAttrDefVO( String pfamilyId, String pattrId, String pattrOwnerType, String pattrValueUnitId, String pattrValue, String pattrName, String pattrValueId,String pinputMethod ) {
		familyId = pfamilyId;
		attrId = pattrId;
		attrOwnerType = pattrOwnerType;
		attrValueUnitId = pattrValueUnitId;
		attrValue = pattrValue;
		attrName = pattrName;
		attrValueId = pattrValueId;
		inputMethod = pinputMethod;
	}

	public String getFamilyId() {
		return familyId;
	}

	public String getAttrId() {
		return attrId;
	}

	public String getAttrOwnerType() {
		return attrOwnerType;
	}

	public String getAttrValueUnitId() {
		return attrValueUnitId;
	}

	public void setFamilyId(String pFamilyId) {
		familyId = pFamilyId;
	}

	public void setAttrId(String pAttrId) {
		attrId = pAttrId;
	}

	public void setAttrOwnerType(String pAttrOwnerType) {
		attrOwnerType = pAttrOwnerType;
	}

	public void setAttrValueUnitId(String pAttrValueUnitId) {
		attrValueUnitId = pAttrValueUnitId;
	}
	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String pAttrValue) {
		this.attrValue = pAttrValue;
	}
	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String pAttrName) {
		this.attrName = pAttrName;
	}
	public String getAttrValueId() {
		return attrValueId;
	}
	public void setAttrValueId(String pAttrValueId) {
		this.attrValueId = pAttrValueId;
	}
	public String getInputMethod() {
		return inputMethod;
	}
	public void setInputMethod(String pInputMethod) {
		this.inputMethod = pInputMethod;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("FAMILY_ID",this.familyId);
		hashMap.put("ATTR_ID",this.attrId);
		hashMap.put("ATTR_OWNER_TYPE",this.attrOwnerType);
		hashMap.put("ATTR_VALUE_UNIT_ID",this.attrValueUnitId);
		hashMap.put("ATTR_VALUE",this.attrValue);
		hashMap.put("ATTR_NAME",this.attrName);
		hashMap.put("INPUT_METHOD",this.inputMethod);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.familyId = (String) hashMap.get("FAMILY_ID");
			this.attrId = (String) hashMap.get("ATTR_ID");
			this.attrOwnerType = (String) hashMap.get("ATTR_OWNER_TYPE");
			this.attrValueUnitId = (String) hashMap.get("ATTR_VALUE_UNIT_ID");
			this.attrValue = (String) hashMap.get("ATTR_VALUE");
			this.attrName = (String) hashMap.get("ATTR_NAME");
			this.inputMethod = (String) hashMap.get("INPUT_METHOD");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ATTR_ID");
		arrayList.add("FAMILY_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RC_ATTR_DEF";
	}



}
