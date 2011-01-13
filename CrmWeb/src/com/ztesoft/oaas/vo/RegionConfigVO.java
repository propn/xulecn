package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RegionConfigVO extends ValueObject implements VO {

	private String configId = "";
	private String regionLevel = "";
	private String levelName = "";
	private String levelType = "";

	public RegionConfigVO() {}

	public RegionConfigVO( String pconfigId, String pregionLevel, String plevelName, String plevelType ) {
		configId = pconfigId;
		regionLevel = pregionLevel;
		levelName = plevelName;
		levelType = plevelType;
	}

	public String getConfigId() {
		return configId;
	}

	public String getRegionLevel() {
		return regionLevel;
	}

	public String getLevelName() {
		return levelName;
	}

	public String getLevelType() {
		return levelType;
	}

	public void setConfigId(String pConfigId) {
		configId = pConfigId;
	}

	public void setRegionLevel(String pRegionLevel) {
		regionLevel = pRegionLevel;
	}

	public void setLevelName(String pLevelName) {
		levelName = pLevelName;
	}

	public void setLevelType(String pLevelType) {
		levelType = pLevelType;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("CONFIG_ID",this.configId);
		hashMap.put("REGION_LEVEL",this.regionLevel);
		hashMap.put("LEVEL_NAME",this.levelName);
		hashMap.put("LEVEL_TYPE",this.levelType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.configId = (String) hashMap.get("CONFIG_ID");
			this.regionLevel = (String) hashMap.get("REGION_LEVEL");
			this.levelName = (String) hashMap.get("LEVEL_NAME");
			this.levelType = (String) hashMap.get("LEVEL_TYPE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("CONFIG_ID");
		return arrayList;
	}

	public String getTableName() {
		return "REGION_CONFIG";
	}

}
