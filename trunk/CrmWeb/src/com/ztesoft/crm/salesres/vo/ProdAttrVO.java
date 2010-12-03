package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class ProdAttrVO extends ValueObject implements VO {

	private String attrId = "";
	private String prodId = "";
	private String attrValue = "";
	private String attrValueUnitId = "";
	private String attrCharacter = "";
	private String nullable = "";
	private String defaultValue = "";
	private String crmAppType = "";
	private String sidAppType = "";
	private String tableCode = "";
	private String fieldCode = "";
	private String allowCustomizedFlag = "";
	private String attrValueTypeId = "";
	private String ifDefaultValue = "";
	private String prodClass = "";
	private String attrName="";
    private String inputMethod="";
    private String attrSeq = "";

	public ProdAttrVO() {}

	public ProdAttrVO( String pattrId, String pprodId, String pattrValue, String pattrValueUnitId, String pattrCharacter, String pnullable, String pdefaultValue, String pcrmAppType, String psidAppType, String ptableCode, String pfieldCode, String pallowCustomizedFlag, String pattrValueTypeId, String pifDefaultValue, String pprodClass ) {
		attrId = pattrId;
		prodId = pprodId;
		attrValue = pattrValue;
		attrValueUnitId = pattrValueUnitId;
		attrCharacter = pattrCharacter;
		nullable = pnullable;
		defaultValue = pdefaultValue;
		crmAppType = pcrmAppType;
		sidAppType = psidAppType;
		tableCode = ptableCode;
		fieldCode = pfieldCode;
		allowCustomizedFlag = pallowCustomizedFlag;
		attrValueTypeId = pattrValueTypeId;
		ifDefaultValue = pifDefaultValue;
		prodClass = pprodClass;
	}

	public String getAttrId() {
		return attrId;
	}

	public String getProdId() {
		return prodId;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public String getAttrValueUnitId() {
		return attrValueUnitId;
	}

	public String getAttrCharacter() {
		return attrCharacter;
	}

	public String getNullable() {
		return nullable;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getCrmAppType() {
		return crmAppType;
	}

	public String getSidAppType() {
		return sidAppType;
	}

	public String getTableCode() {
		return tableCode;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public String getAllowCustomizedFlag() {
		return allowCustomizedFlag;
	}

	public String getAttrValueTypeId() {
		return attrValueTypeId;
	}

	public String getIfDefaultValue() {
		return ifDefaultValue;
	}

	public String getProdClass() {
		return prodClass;
	}

	public void setAttrId(String pAttrId) {
		attrId = pAttrId;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setAttrValue(String pAttrValue) {
		attrValue = pAttrValue;
	}

	public void setAttrValueUnitId(String pAttrValueUnitId) {
		attrValueUnitId = pAttrValueUnitId;
	}

	public void setAttrCharacter(String pAttrCharacter) {
		attrCharacter = pAttrCharacter;
	}

	public void setNullable(String pNullable) {
		nullable = pNullable;
	}

	public void setDefaultValue(String pDefaultValue) {
		defaultValue = pDefaultValue;
	}

	public void setCrmAppType(String pCrmAppType) {
		crmAppType = pCrmAppType;
	}

	public void setSidAppType(String pSidAppType) {
		sidAppType = pSidAppType;
	}

	public void setTableCode(String pTableCode) {
		tableCode = pTableCode;
	}

	public void setFieldCode(String pFieldCode) {
		fieldCode = pFieldCode;
	}

	public void setAllowCustomizedFlag(String pAllowCustomizedFlag) {
		allowCustomizedFlag = pAllowCustomizedFlag;
	}

	public void setAttrValueTypeId(String pAttrValueTypeId) {
		attrValueTypeId = pAttrValueTypeId;
	}

	public void setIfDefaultValue(String pIfDefaultValue) {
		ifDefaultValue = pIfDefaultValue;
	}

	public void setProdClass(String pProdClass) {
		prodClass = pProdClass;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ATTR_ID",this.attrId);
		hashMap.put("PRODUCT_ID",this.prodId);
		hashMap.put("ATTR_VALUE",this.attrValue);
		hashMap.put("ATTR_VALUE_UNIT_ID",this.attrValueUnitId);
		hashMap.put("ATTR_CHARACTER",this.attrCharacter);
		hashMap.put("NULLABLE",this.nullable);
		hashMap.put("DEFAULT_VALUE",this.defaultValue);
		hashMap.put("CRM_APP_TYPE",this.crmAppType);
		hashMap.put("SID_APP_TYPE",this.sidAppType);
		hashMap.put("TABLE_CODE",this.tableCode);
		hashMap.put("FIELD_CODE",this.fieldCode);
		hashMap.put("ALLOW_CUSTOMIZED_FLAG",this.allowCustomizedFlag);
		hashMap.put("ATTR_VALUE_TYPE_ID",this.attrValueTypeId);
		hashMap.put("IF_DEFAULT_VALUE",this.ifDefaultValue);
		hashMap.put("PRODUCT_CLASS",this.prodClass);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.attrId = (String) hashMap.get("ATTR_ID");
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.attrValue = (String) hashMap.get("ATTR_VALUE");
			this.attrValueUnitId = (String) hashMap.get("ATTR_VALUE_UNIT_ID");
			this.attrCharacter = (String) hashMap.get("ATTR_CHARACTER");
			this.nullable = (String) hashMap.get("NULLABLE");
			this.defaultValue = (String) hashMap.get("DEFAULT_VALUE");
			this.crmAppType = (String) hashMap.get("CRM_APP_TYPE");
			this.sidAppType = (String) hashMap.get("SID_APP_TYPE");
			this.tableCode = (String) hashMap.get("TABLE_CODE");
			this.fieldCode = (String) hashMap.get("FIELD_CODE");
			this.allowCustomizedFlag = (String) hashMap.get("ALLOW_CUSTOMIZED_FLAG");
			this.attrValueTypeId = (String) hashMap.get("ATTR_VALUE_TYPE_ID");
			this.ifDefaultValue = (String) hashMap.get("IF_DEFAULT_VALUE");
			this.prodClass = (String) hashMap.get("PRODUCT_CLASS");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ATTR_ID");
		arrayList.add("PRODUCT_ID");
		arrayList.add("TABLE_CODE");
		return arrayList;
	}

	public String getTableName() {
		return "PRODUCT_ATTR";
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getInputMethod() {
		return inputMethod;
	}

	public void setInputMethod(String inputMethod) {
		this.inputMethod = inputMethod;
	}

	public String getAttrSeq() {
		return attrSeq;
	}

	public void setAttrSeq(String attrSeq) {
		this.attrSeq = attrSeq;
	}

}
