package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PageFieldVO extends ValueObject implements VO {
	private String fieldId = "";
	private String fieldName = "";
	private String isChecked = "";

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public PageFieldVO() {}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("FIELD_ID",this.fieldId);
		hashMap.put("FIELD_NAME",this.fieldName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.fieldId = (String) hashMap.get("FIELD_ID");
			this.fieldName = (String) hashMap.get("FIELD_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("FIELD_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PAGE_FIELD";
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
