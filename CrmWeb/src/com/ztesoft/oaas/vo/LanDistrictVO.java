package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class LanDistrictVO extends ValueObject implements VO {

	private String districtId = "";
	private String districtCode = "";
	private String districtName = "";
	private String districtType = "";
	private String districtAddr = "";
	private String dealExch = "";
	private String dealExchName = "";
	private String subExch = "";
	private String subExchName = "";

	public String getDealExchName() {
		return dealExchName;
	}

	public void setDealExchName(String dealExchName) {
		this.dealExchName = dealExchName;
	}

	public String getSubExchName() {
		return subExchName;
	}

	public void setSubExchName(String subExchName) {
		this.subExchName = subExchName;
	}

	public LanDistrictVO() {}

	public LanDistrictVO( String pdistrictId, String pdistrictCode, String pdistrictName, String pdistrictType, String pdistrictAddr, String pdealExch, String psubExch ) {
		districtId = pdistrictId;
		districtCode = pdistrictCode;
		districtName = pdistrictName;
		districtType = pdistrictType;
		districtAddr = pdistrictAddr;
		dealExch = pdealExch;
		subExch = psubExch;
	}

	public String getDistrictId() {
		return districtId;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public String getDistrictType() {
		return districtType;
	}

	public String getDistrictAddr() {
		return districtAddr;
	}

	public String getDealExch() {
		return dealExch;
	}

	public String getSubExch() {
		return subExch;
	}

	public void setDistrictId(String pDistrictId) {
		districtId = pDistrictId;
	}

	public void setDistrictCode(String pDistrictCode) {
		districtCode = pDistrictCode;
	}

	public void setDistrictName(String pDistrictName) {
		districtName = pDistrictName;
	}

	public void setDistrictType(String pDistrictType) {
		districtType = pDistrictType;
	}

	public void setDistrictAddr(String pDistrictAddr) {
		districtAddr = pDistrictAddr;
	}

	public void setDealExch(String pDealExch) {
		dealExch = pDealExch;
	}

	public void setSubExch(String pSubExch) {
		subExch = pSubExch;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("DISTRICT_ID",this.districtId);
		hashMap.put("DISTRICT_CODE",this.districtCode);
		hashMap.put("DISTRICT_NAME",this.districtName);
		hashMap.put("DISTRICT_TYPE",this.districtType);
		hashMap.put("DISTRICT_ADDR",this.districtAddr);
		hashMap.put("DEAL_EXCH",this.dealExch);
		hashMap.put("SUB_EXCH",this.subExch);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.districtId = (String) hashMap.get("DISTRICT_ID");
			this.districtCode = (String) hashMap.get("DISTRICT_CODE");
			this.districtName = (String) hashMap.get("DISTRICT_NAME");
			this.districtType = (String) hashMap.get("DISTRICT_TYPE");
			this.districtAddr = (String) hashMap.get("DISTRICT_ADDR");
			this.dealExch = (String) hashMap.get("DEAL_EXCH");
			this.subExch = (String) hashMap.get("SUB_EXCH");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("DISTRICT_ID");
		return arrayList;
	}

	public String getTableName() {
		return "LAN_DISTRICT";
	}

}