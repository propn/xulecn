package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RrProvinceVO extends ValueObject implements VO {

	private String provId = "";
	private String prvCode = "";
	private String prvName = "";
	private String prvFlag = "";

	public RrProvinceVO() {}

	public RrProvinceVO( String pprovId, String pprvCode, String pprvName, String pprvFlag ) {
		provId = pprovId;
		prvCode = pprvCode;
		prvName = pprvName;
		prvFlag = pprvFlag;
	}

	public String getProvId() {
		return provId;
	}

	public String getPrvCode() {
		return prvCode;
	}

	public String getPrvName() {
		return prvName;
	}

	public String getPrvFlag() {
		return prvFlag;
	}

	public void setProvId(String pProvId) {
		provId = pProvId;
	}

	public void setPrvCode(String pPrvCode) {
		prvCode = pPrvCode;
	}

	public void setPrvName(String pPrvName) {
		prvName = pPrvName;
	}

	public void setPrvFlag(String pPrvFlag) {
		prvFlag = pPrvFlag;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PROV_ID",this.provId);
		hashMap.put("PRV_CODE",this.prvCode);
		hashMap.put("PRV_NAME",this.prvName);
		hashMap.put("PRV_FLAG",this.prvFlag);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.provId = (String) hashMap.get("PROV_ID");
			this.prvCode = (String) hashMap.get("PRV_CODE");
			this.prvName = (String) hashMap.get("PRV_NAME");
			this.prvFlag = (String) hashMap.get("PRV_FLAG");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PROV_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RR_PROVINCE";
	}

}
