package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class SalesRescAttrVO extends ValueObject implements VO {

	private String salesRescId = "";

	private String attrId = "";

	private String attrValue = "";

	private String attrValueRange = "";

	private String familyId = "";

	private String attrOwnerType = "";

	private String attrValueUnitId = "";

	private String attrName = "";

	private String inputMethod = "";

	public SalesRescAttrVO() {
	}

	public SalesRescAttrVO(String psalesRescId, String pattrId,
			String pattrValue, String pattrValueRange) {
		salesRescId = psalesRescId;
		attrId = pattrId;
		attrValue = pattrValue;
		attrValueRange = pattrValueRange;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public String getAttrId() {
		return attrId;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public String getAttrValueRange() {
		return attrValueRange;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public void setAttrId(String pAttrId) {
		attrId = pAttrId;
	}

	public void setAttrValue(String pAttrValue) {
		attrValue = pAttrValue;
	}

	public void setAttrValueRange(String pAttrValueRange) {
		attrValueRange = pAttrValueRange;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrOwnerType() {
		return attrOwnerType;
	}

	public void setAttrOwnerType(String attrOwnerType) {
		this.attrOwnerType = attrOwnerType;
	}

	public String getAttrValueUnitId() {
		return attrValueUnitId;
	}

	public void setAttrValueUnitId(String attrValueUnitId) {
		this.attrValueUnitId = attrValueUnitId;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getInputMethod() {
		return inputMethod;
	}

	public void setInputMethod(String inputMethod) {
		this.inputMethod = inputMethod;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("SALES_RESOURCE_ID", this.salesRescId);
		hashMap.put("ATTR_ID", this.attrId);
		hashMap.put("ATTR_VALUE", this.attrValue);
		hashMap.put("ATTR_VALUE_RANGE", this.attrValueRange);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.attrId = (String) hashMap.get("ATTR_ID");
			this.attrValue = (String) hashMap.get("ATTR_VALUE");
			this.attrValueRange = (String) hashMap.get("ATTR_VALUE_RANGE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ATTR_ID");
		arrayList.add("SALES_RESOURCE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "SALES_RESOURCE_ATTR";
	}

}
