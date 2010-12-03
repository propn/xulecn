package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RaRoadVO extends ValueObject implements VO {

	private String roadId = "";
	private String roadCode = "";
	private String roadName = "";
	private String postCode = "";
	private String spell = "";
	private String cityId = "";
	private String townId = "";
	private String measureId = "";
	private String regionId = "";

	public RaRoadVO() {}

	public RaRoadVO( String proadId, String proadCode, String proadName, String ppostCode, String pspell, String pcityId, String ptownId, String pmeasureId, String pregionId ) {
		roadId = proadId;
		roadCode = proadCode;
		roadName = proadName;
		postCode = ppostCode;
		spell = pspell;
		cityId = pcityId;
		townId = ptownId;
		measureId = pmeasureId;
		regionId = pregionId;
	}

	public String getRoadId() {
		return roadId;
	}

	public String getRoadCode() {
		return roadCode;
	}

	public String getRoadName() {
		return roadName;
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

	public String getTownId() {
		return townId;
	}

	public String getMeasureId() {
		return measureId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRoadId(String pRoadId) {
		roadId = pRoadId;
	}

	public void setRoadCode(String pRoadCode) {
		roadCode = pRoadCode;
	}

	public void setRoadName(String pRoadName) {
		roadName = pRoadName;
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

	public void setTownId(String pTownId) {
		townId = pTownId;
	}

	public void setMeasureId(String pMeasureId) {
		measureId = pMeasureId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ROAD_ID",this.roadId);
		hashMap.put("ROAD_CODE",this.roadCode);
		hashMap.put("ROAD_NAME",this.roadName);
		hashMap.put("POST_CODE",this.postCode);
		hashMap.put("SPELL",this.spell);
		hashMap.put("CITY_ID",this.cityId);
		hashMap.put("TOWN_ID",this.townId);
		hashMap.put("MEASURE_ID",this.measureId);
		hashMap.put("REGION_ID",this.regionId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.roadId = (String) hashMap.get("ROAD_ID");
			this.roadCode = (String) hashMap.get("ROAD_CODE");
			this.roadName = (String) hashMap.get("ROAD_NAME");
			this.postCode = (String) hashMap.get("POST_CODE");
			this.spell = (String) hashMap.get("SPELL");
			this.cityId = (String) hashMap.get("CITY_ID");
			this.townId = (String) hashMap.get("TOWN_ID");
			this.measureId = (String) hashMap.get("MEASURE_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ROAD_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RA_ROAD";
	}

}
