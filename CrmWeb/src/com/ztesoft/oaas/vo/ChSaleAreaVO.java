package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class ChSaleAreaVO extends ValueObject implements VO {

	private String areaId = "";
	private String areaName = "";
	private String lanId = "";
	private String validFlag = "";
	private String remark = "";

	public ChSaleAreaVO() {}

	public ChSaleAreaVO( String pareaId, String pareaName, String pLanId, String pvalidFlag, String premark ) {
		areaId = pareaId;
		areaName = pareaName;
		lanId = pLanId;
		validFlag = pvalidFlag;
		remark = premark;
	}

	public String getAreaId() {
		return areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public String getLanId() {
		return lanId;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setAreaId(String pAreaId) {
		areaId = pAreaId;
	}

	public void setAreaName(String pAreaName) {
		areaName = pAreaName;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setValidFlag(String pValidFlag) {
		validFlag = pValidFlag;
	}

	public void setRemark(String pRemark) {
		remark = pRemark;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("AREA_ID",this.areaId);
		hashMap.put("AREA_NAME",this.areaName);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("VALID_FLAG",this.validFlag);
		hashMap.put("REMARK",this.remark);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.areaId = (String) hashMap.get("AREA_ID");
			this.areaName = (String) hashMap.get("AREA_NAME");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.validFlag = (String) hashMap.get("VALID_FLAG");
			this.remark = (String) hashMap.get("REMARK");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("AREA_ID");
		return arrayList;
	}

	public String getTableName() {
		return "CH_SALE_AREA";
	}

}
