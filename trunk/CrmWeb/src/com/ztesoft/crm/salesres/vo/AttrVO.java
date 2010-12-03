package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class AttrVO extends ValueObject implements VO {

	private String attrId = "";
	private String attrValueUnitId = "";
	private String attrName = "";
	private String attrDesc = "";
	private String valueType = "";
	private String inputMethod = "";
	private String minValue = "";
	private String maxValue = "";
	private String displayFlag = "";
	private String state = "";
	private String effDate = "";
	private String expDate = "";
	private String ifDefaultValue = "";
	private String nullable = "";
	private String defaultValue = "";
	private String attrShortCode = "";
	private String attrCode = "";
	private String valueMaxLen = "";
	private String valueAllowEdit = "";
	private String valueKind = "";

	public AttrVO() {}

	public AttrVO( String pattrId, String pattrValueUnitId, String pattrName, String pattrDesc, String pvalueType, String pinputMethod, String pminValue, String pmaxValue, String pdisplayFlag, String pstate, String peffDate, String pexpDate, String pifDefaultValue, String pnullable, String pdefaultValue, String pattrShortCode, String pattrCode, String pvalueMaxLen, String pvalueAllowEdit, String pvalueKind ) {
		attrId = pattrId;
		attrValueUnitId = pattrValueUnitId;
		attrName = pattrName;
		attrDesc = pattrDesc;
		valueType = pvalueType;
		inputMethod = pinputMethod;
		minValue = pminValue;
		maxValue = pmaxValue;
		displayFlag = pdisplayFlag;
		state = pstate;
		effDate = peffDate;
		expDate = pexpDate;
		ifDefaultValue = pifDefaultValue;
		nullable = pnullable;
		defaultValue = pdefaultValue;
		attrShortCode = pattrShortCode;
		attrCode = pattrCode;
		valueMaxLen = pvalueMaxLen;
		valueAllowEdit = pvalueAllowEdit;
		valueKind = pvalueKind;
	}

	public String getAttrId() {
		return attrId;
	}

	public String getAttrValueUnitId() {
		return attrValueUnitId;
	}

	public String getAttrName() {
		return attrName;
	}

	public String getAttrDesc() {
		return attrDesc;
	}

	public String getValueType() {
		return valueType;
	}

	public String getInputMethod() {
		return inputMethod;
	}

	public String getMinValue() {
		return minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public String getDisplayFlag() {
		return displayFlag;
	}

	public String getState() {
		return state;
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public String getIfDefaultValue() {
		return ifDefaultValue;
	}

	public String getNullable() {
		return nullable;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getAttrShortCode() {
		return attrShortCode;
	}

	public String getAttrCode() {
		return attrCode;
	}

	public String getValueMaxLen() {
		return valueMaxLen;
	}

	public String getValueAllowEdit() {
		return valueAllowEdit;
	}

	public String getValueKind() {
		return valueKind;
	}

	public void setAttrId(String pAttrId) {
		attrId = pAttrId;
	}

	public void setAttrValueUnitId(String pAttrValueUnitId) {
		attrValueUnitId = pAttrValueUnitId;
	}

	public void setAttrName(String pAttrName) {
		attrName = pAttrName;
	}

	public void setAttrDesc(String pAttrDesc) {
		attrDesc = pAttrDesc;
	}

	public void setValueType(String pValueType) {
		valueType = pValueType;
	}

	public void setInputMethod(String pInputMethod) {
		inputMethod = pInputMethod;
	}

	public void setMinValue(String pMinValue) {
		minValue = pMinValue;
	}

	public void setMaxValue(String pMaxValue) {
		maxValue = pMaxValue;
	}

	public void setDisplayFlag(String pDisplayFlag) {
		displayFlag = pDisplayFlag;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setEffDate(String pEffDate) {
		effDate = pEffDate;
	}

	public void setExpDate(String pExpDate) {
		expDate = pExpDate;
	}

	public void setIfDefaultValue(String pIfDefaultValue) {
		ifDefaultValue = pIfDefaultValue;
	}

	public void setNullable(String pNullable) {
		nullable = pNullable;
	}

	public void setDefaultValue(String pDefaultValue) {
		defaultValue = pDefaultValue;
	}

	public void setAttrShortCode(String pAttrShortCode) {
		attrShortCode = pAttrShortCode;
	}

	public void setAttrCode(String pAttrCode) {
		attrCode = pAttrCode;
	}

	public void setValueMaxLen(String pValueMaxLen) {
		valueMaxLen = pValueMaxLen;
	}

	public void setValueAllowEdit(String pValueAllowEdit) {
		valueAllowEdit = pValueAllowEdit;
	}

	public void setValueKind(String pValueKind) {
		valueKind = pValueKind;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ATTR_ID",this.attrId);
		hashMap.put("ATTR_VALUE_UNIT_ID",this.attrValueUnitId);
		hashMap.put("ATTR_NAME",this.attrName);
		hashMap.put("ATTR_DESC",this.attrDesc);
		hashMap.put("VALUE_TYPE",this.valueType);
		hashMap.put("INPUT_METHOD",this.inputMethod);
		hashMap.put("MIN_VALUE",this.minValue);
		hashMap.put("MAX_VALUE",this.maxValue);
		hashMap.put("DISPLAY_FLAG",this.displayFlag);
		hashMap.put("STATE",this.state);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		hashMap.put("IF_DEFAULT_VALUE",this.ifDefaultValue);
		hashMap.put("NULLABLE",this.nullable);
		hashMap.put("DEFAULT_VALUE",this.defaultValue);
		hashMap.put("ATTR_SHORT_CODE",this.attrShortCode);
		hashMap.put("ATTR_CODE",this.attrCode);
		hashMap.put("VALUE_MAX_LEN",this.valueMaxLen);
		hashMap.put("VALUE_ALLOW_EDIT",this.valueAllowEdit);
		hashMap.put("VALUE_KIND",this.valueKind);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.attrId = (String) hashMap.get("ATTR_ID");
			this.attrValueUnitId = (String) hashMap.get("ATTR_VALUE_UNIT_ID");
			this.attrName = (String) hashMap.get("ATTR_NAME");
			this.attrDesc = (String) hashMap.get("ATTR_DESC");
			this.valueType = (String) hashMap.get("VALUE_TYPE");
			this.inputMethod = (String) hashMap.get("INPUT_METHOD");
			this.minValue = (String) hashMap.get("MIN_VALUE");
			this.maxValue = (String) hashMap.get("MAX_VALUE");
			this.displayFlag = (String) hashMap.get("DISPLAY_FLAG");
			this.state = (String) hashMap.get("STATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.ifDefaultValue = (String) hashMap.get("IF_DEFAULT_VALUE");
			this.nullable = (String) hashMap.get("NULLABLE");
			this.defaultValue = (String) hashMap.get("DEFAULT_VALUE");
			this.attrShortCode = (String) hashMap.get("ATTR_SHORT_CODE");
			this.attrCode = (String) hashMap.get("ATTR_CODE");
			this.valueMaxLen = (String) hashMap.get("VALUE_MAX_LEN");
			this.valueAllowEdit = (String) hashMap.get("VALUE_ALLOW_EDIT");
			this.valueKind = (String) hashMap.get("VALUE_KIND");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ATTR_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ATTRIBUTE";
	}

}
