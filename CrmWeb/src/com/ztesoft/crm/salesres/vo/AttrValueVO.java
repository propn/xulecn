package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class AttrValueVO extends ValueObject implements VO {

	private String attrValueId = "";
	private String attrId = "";
	private String attrValue = "";
	private String attrValueDesc = "";
	private String sortby = "";
	private String attrName="";
	private String isChecked="";
	private String offAId = "";
	private String offZId = "";
	private String relationTypeId = "";

	private String factorValue="";
	private String factorName="";


	public AttrValueVO() {}

	public AttrValueVO( String pattrValueId, String pattrId, String pattrValue, String pattrValueDesc, String psortby ) {
		attrValueId = pattrValueId;
		attrId = pattrId;
		attrValue = pattrValue;
		attrValueDesc = pattrValueDesc;
		sortby = psortby;
	}

	public String getAttrValueId() {
		return attrValueId;
	}

	public String getAttrId() {
		return attrId;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public String getAttrValueDesc() {
		return attrValueDesc;
	}

	public String getSortby() {
		return sortby;
	}

	public void setAttrValueId(String pAttrValueId) {
		attrValueId = pAttrValueId;
	}

	public void setAttrId(String pAttrId) {
		attrId = pAttrId;
	}

	public void setAttrValue(String pAttrValue) {
		attrValue = pAttrValue;
	}

	public void setAttrValueDesc(String pAttrValueDesc) {
		attrValueDesc = pAttrValueDesc;
	}

	public void setSortby(String pSortby) {
		sortby = pSortby;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ATTR_VALUE_ID",this.attrValueId);
		hashMap.put("ATTR_ID",this.attrId);
		hashMap.put("ATTR_VALUE",this.attrValue);
		hashMap.put("ATTR_VALUE_DESC",this.attrValueDesc);
		hashMap.put("SORTBY",this.sortby);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.attrValueId = (String) hashMap.get("ATTR_VALUE_ID");
			this.attrId = (String) hashMap.get("ATTR_ID");
			this.attrValue = (String) hashMap.get("ATTR_VALUE");
			this.attrValueDesc = (String) hashMap.get("ATTR_VALUE_DESC");
			this.sortby = (String) hashMap.get("SORTBY");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ATTR_VALUE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ATTRIBUTE_VALUE";
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getOffAId() {
		return offAId;
	}

	public void setOffAId(String offAId) {
		this.offAId = offAId;
	}

	public String getOffZId() {
		return offZId;
	}

	public void setOffZId(String offZId) {
		this.offZId = offZId;
	}

	public String getRelationTypeId() {
		return relationTypeId;
	}

	public void setRelationTypeId(String relationTypeId) {
		this.relationTypeId = relationTypeId;
	}

	public String getFactorName() {
		return factorName;
	}

	public void setFactorName(String factorName) {
		this.factorName = factorName;
	}

	public String getFactorValue() {
		return factorValue;
	}

	public void setFactorValue(String factorValue) {
		this.factorValue = factorValue;
	}

}
