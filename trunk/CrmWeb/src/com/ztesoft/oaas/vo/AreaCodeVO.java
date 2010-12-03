package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class AreaCodeVO extends ValueObject implements VO {

	private String areaCode = "";
	private String name = "";
	private String regionId = "";
	private String regionLevel = "";

	public AreaCodeVO() {}

	public AreaCodeVO( String pareaCode, String pname, String pregionId, String pregionLevel ) {
		areaCode = pareaCode;
		name = pname;
		regionId = pregionId;
		regionLevel = pregionLevel;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getName() {
		return name;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getRegionLevel() {
		return regionLevel;
	}

	public void setAreaCode(String pAreaCode) {
		areaCode = pAreaCode;
	}

	public void setName(String pName) {
		name = pName;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setRegionLevel(String pRegionLevel) {
		regionLevel = pRegionLevel;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("AREA_CODE",this.areaCode);
		hashMap.put("NAME",this.name);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("REGION_LEVEL",this.regionLevel);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.areaCode = (String) hashMap.get("AREA_CODE");
			this.name = (String) hashMap.get("NAME");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.regionLevel = (String) hashMap.get("REGION_LEVEL");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("REGION_ID");
		return arrayList;
	}

	public String getTableName() {
		return "AREA_CODE";
	}

}
