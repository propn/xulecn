package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class StAreaVO extends ValueObject implements VO {

	private String lanId = "";
	private String regionId = "";
	private String areaId = "";
	private String areaName = "";

	public StAreaVO() {}

	public StAreaVO( String planId, String pregionId, String pareaId, String pareaName ) {
		lanId = planId;
		regionId = pregionId;
		areaId = pareaId;
		areaName = pareaName;
	}

	public String getLanId() {
		return lanId;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getAreaId() {
		return areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setAreaId(String pAreaId) {
		areaId = pAreaId;
	}

	public void setAreaName(String pAreaName) {
		areaName = pAreaName;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("AREA_ID",this.areaId);
		hashMap.put("AREA_NAME",this.areaName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.lanId = (String) hashMap.get("LAN_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.areaId = (String) hashMap.get("AREA_ID");
			this.areaName = (String) hashMap.get("AREA_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("AREA_ID");
		arrayList.add("LAN_ID");
		arrayList.add("REGION_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ST_AREA";
	}

}
