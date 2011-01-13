package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RaCityVO extends ValueObject implements VO {

	private String cityId = "";
	private String cityCode = "";
	private String cityName = "";
	private String spell = "";
	private String postCode = "";
	private String lanId = "";
	private String provId = "";

	public RaCityVO() {}

	public RaCityVO( String pcityId, String pcityCode, String pcityName, String pspell, String ppostCode, String planId, String pprovId ) {
		cityId = pcityId;
		cityCode = pcityCode;
		cityName = pcityName;
		spell = pspell;
		postCode = ppostCode;
		lanId = planId;
		provId = pprovId;
	}

	public String getCityId() {
		return cityId;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public String getSpell() {
		return spell;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getLanId() {
		return lanId;
	}

	public String getProvId() {
		return provId;
	}

	public void setCityId(String pCityId) {
		cityId = pCityId;
	}

	public void setCityCode(String pCityCode) {
		cityCode = pCityCode;
	}

	public void setCityName(String pCityName) {
		cityName = pCityName;
	}

	public void setSpell(String pSpell) {
		spell = pSpell;
	}

	public void setPostCode(String pPostCode) {
		postCode = pPostCode;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setProvId(String pProvId) {
		provId = pProvId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("CITY_ID",this.cityId);
		hashMap.put("CITY_CODE",this.cityCode);
		hashMap.put("CITY_NAME",this.cityName);
		hashMap.put("SPELL",this.spell);
		hashMap.put("POST_CODE",this.postCode);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("PROV_ID",this.provId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.cityId = (String) hashMap.get("CITY_ID");
			this.cityCode = (String) hashMap.get("CITY_CODE");
			this.cityName = (String) hashMap.get("CITY_NAME");
			this.spell = (String) hashMap.get("SPELL");
			this.postCode = (String) hashMap.get("POST_CODE");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.provId = (String) hashMap.get("PROV_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("CITY_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RA_CITY";
	}

}
