package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RrRegionVO extends ValueObject implements VO {

	private String lanId = "";
	private String regionId = "";
	private String regionCode = "";
	private String regionName = "";

	public RrRegionVO() {}

	public RrRegionVO( String planId, String pregionId, String pregionCode, String pregionName ) {
		lanId = planId;
		regionId = pregionId;
		regionCode = pregionCode;
		regionName = pregionName;
	}

	public String getLanId() {
		return lanId;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

    public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setRegionCode(String pRegionCode) {
		regionCode = pRegionCode;
	}

	public void setRegionName(String pRegionName) {
		regionName = pRegionName;
	}

    public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("REGION_CODE",this.regionCode);
		hashMap.put("REGION_NAME",this.regionName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.lanId = (String) hashMap.get("LAN_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.regionCode = (String) hashMap.get("REGION_CODE");
			this.regionName = (String) hashMap.get("REGION_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("REGION_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RR_REGION";
	}

}
