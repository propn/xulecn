package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RrPpdomVO extends ValueObject implements VO {

	private String lanId = "";
	private String ppdomId = "";
	private String ppdomCode = "";
	private String ppdomName = "";

	public RrPpdomVO() {}

	public RrPpdomVO( String planId, String pppdomId, String pppdomCode, String pppdomName ) {
		lanId = planId;
		ppdomId = pppdomId;
		ppdomCode = pppdomCode;
		ppdomName = pppdomName;
	}

	public String getLanId() {
		return lanId;
	}

	public String getPpdomId() {
		return ppdomId;
	}

	public String getPpdomCode() {
		return ppdomCode;
	}

	public String getPpdomName() {
		return ppdomName;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setPpdomId(String pPpdomId) {
		ppdomId = pPpdomId;
	}

	public void setPpdomCode(String pPpdomCode) {
		ppdomCode = pPpdomCode;
	}

	public void setPpdomName(String pPpdomName) {
		ppdomName = pPpdomName;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("PPDOM_ID",this.ppdomId);
		hashMap.put("PPDOM_CODE",this.ppdomCode);
		hashMap.put("PPDOM_NAME",this.ppdomName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.lanId = (String) hashMap.get("LAN_ID");
			this.ppdomId = (String) hashMap.get("PPDOM_ID");
			this.ppdomCode = (String) hashMap.get("PPDOM_CODE");
			this.ppdomName = (String) hashMap.get("PPDOM_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PPDOM_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RR_PPDOM";
	}

}
