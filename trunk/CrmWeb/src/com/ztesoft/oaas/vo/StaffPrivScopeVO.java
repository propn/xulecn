package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class StaffPrivScopeVO extends ValueObject implements VO {

	private String staffPrivScopeId = "";
	private String staffPrivId = "";
	private String regionId = "";
	private String regionType = "";

	public StaffPrivScopeVO() {}

	public StaffPrivScopeVO( String pstaffPrivScopeId, String pstaffPrivId, String pregionId, String pregionType ) {
		staffPrivScopeId = pstaffPrivScopeId;
		staffPrivId = pstaffPrivId;
		regionId = pregionId;
		regionType = pregionType;
	}

	public String getStaffPrivScopeId() {
		return staffPrivScopeId;
	}

	public String getStaffPrivId() {
		return staffPrivId;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getRegionType() {
		return regionType;
	}

	public void setStaffPrivScopeId(String pStaffPrivScopeId) {
		staffPrivScopeId = pStaffPrivScopeId;
	}

	public void setStaffPrivId(String pStaffPrivId) {
		staffPrivId = pStaffPrivId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setRegionType(String pRegionType) {
		regionType = pRegionType;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("STAFF_PRIVILEGE_SCOPE_ID",this.staffPrivScopeId);
		hashMap.put("STAFF_PRIVILEGE_ID",this.staffPrivId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("REGION_TYPE",this.regionType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.staffPrivScopeId = (String) hashMap.get("STAFF_PRIVILEGE_SCOPE_ID");
			this.staffPrivId = (String) hashMap.get("STAFF_PRIVILEGE_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.regionType = (String) hashMap.get("REGION_TYPE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("STAFF_PRIVILEGE_SCOPE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "STAFF_PRIVILEGE_SCOPE";
	}

}
