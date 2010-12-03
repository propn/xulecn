package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RaTownVO extends ValueObject implements VO {

	private String townId = "";
	private String townCode = "";
	private String townName = "";
	private String postCode = "";
	private String spell = "";
	private String cityId = "";
	private String lanId = "";
	private String ppdomId = "";
	private String businessId = "";
	private String exchId = "";
	private String measureId = "";

	public RaTownVO() {}

	public RaTownVO( String ptownId, String ptownCode, String ptownName, String ppostCode, String pspell, String pcityId, String planId, String pppdomId, String pbusinessId, String pexchId, String pmeasureId ) {
		townId = ptownId;
		townCode = ptownCode;
		townName = ptownName;
		postCode = ppostCode;
		spell = pspell;
		cityId = pcityId;
		lanId = planId;
		ppdomId = pppdomId;
		businessId = pbusinessId;
		exchId = pexchId;
		measureId = pmeasureId;
	}

	public String getTownId() {
		return townId;
	}

	public String getTownCode() {
		return townCode;
	}

	public String getTownName() {
		return townName;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getSpell() {
		return spell;
	}

	public String getCityId() {
		return cityId;
	}

	public String getLanId() {
		return lanId;
	}

	public String getPpdomId() {
		return ppdomId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public String getExchId() {
		return exchId;
	}

	public String getMeasureId() {
		return measureId;
	}

	public void setTownId(String pTownId) {
		townId = pTownId;
	}

	public void setTownCode(String pTownCode) {
		townCode = pTownCode;
	}

	public void setTownName(String pTownName) {
		townName = pTownName;
	}

	public void setPostCode(String pPostCode) {
		postCode = pPostCode;
	}

	public void setSpell(String pSpell) {
		spell = pSpell;
	}

	public void setCityId(String pCityId) {
		cityId = pCityId;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setPpdomId(String pPpdomId) {
		ppdomId = pPpdomId;
	}

	public void setBusinessId(String pBusinessId) {
		businessId = pBusinessId;
	}

	public void setExchId(String pExchId) {
		exchId = pExchId;
	}

	public void setMeasureId(String pMeasureId) {
		measureId = pMeasureId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("TOWN_ID",this.townId);
		hashMap.put("TOWN_CODE",this.townCode);
		hashMap.put("TOWN_NAME",this.townName);
		hashMap.put("POST_CODE",this.postCode);
		hashMap.put("SPELL",this.spell);
		hashMap.put("CITY_ID",this.cityId);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("PPDOM_ID",this.ppdomId);
		hashMap.put("BUSINESS_ID",this.businessId);
		hashMap.put("EXCH_ID",this.exchId);
		hashMap.put("MEASURE_ID",this.measureId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.townId = (String) hashMap.get("TOWN_ID");
			this.townCode = (String) hashMap.get("TOWN_CODE");
			this.townName = (String) hashMap.get("TOWN_NAME");
			this.postCode = (String) hashMap.get("POST_CODE");
			this.spell = (String) hashMap.get("SPELL");
			this.cityId = (String) hashMap.get("CITY_ID");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.ppdomId = (String) hashMap.get("PPDOM_ID");
			this.businessId = (String) hashMap.get("BUSINESS_ID");
			this.exchId = (String) hashMap.get("EXCH_ID");
			this.measureId = (String) hashMap.get("MEASURE_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("TOWN_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RA_TOWN";
	}

}
