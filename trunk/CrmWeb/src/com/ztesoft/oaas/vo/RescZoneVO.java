package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RescZoneVO extends ValueObject implements VO {

	private String rescZoneId = "";
	private String exchId = "";
	private String regionId = "";
	private String resouceZoneName = "";
	private String rescId = "";

	public RescZoneVO() {}

	public RescZoneVO( String prescZoneId, String pexchId, String pregionId, String presouceZoneName, String prescId ) {
		rescZoneId = prescZoneId;
		exchId = pexchId;
		regionId = pregionId;
		resouceZoneName = presouceZoneName;
		rescId = prescId;
	}

	public String getRescZoneId() {
		return rescZoneId;
	}

	public String getExchId() {
		return exchId;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getResouceZoneName() {
		return resouceZoneName;
	}

	public String getRescId() {
		return rescId;
	}

	public void setRescZoneId(String pRescZoneId) {
		rescZoneId = pRescZoneId;
	}

	public void setExchId(String pExchId) {
		exchId = pExchId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setResouceZoneName(String pResouceZoneName) {
		resouceZoneName = pResouceZoneName;
	}

	public void setRescId(String pRescId) {
		rescId = pRescId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("RESOURCE_ZONE_ID",this.rescZoneId);
		hashMap.put("EXCHANGE_ID",this.exchId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("RESOUCE_ZONE_NAME",this.resouceZoneName);
		hashMap.put("RESOURCE_ID",this.rescId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.rescZoneId = (String) hashMap.get("RESOURCE_ZONE_ID");
			this.exchId = (String) hashMap.get("EXCHANGE_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.resouceZoneName = (String) hashMap.get("RESOUCE_ZONE_NAME");
			this.rescId = (String) hashMap.get("RESOURCE_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("RESOURCE_ZONE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RESOURCE_ZONE";
	}

}
