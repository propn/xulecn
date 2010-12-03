package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class StaffRoleScopeVO extends ValueObject implements VO {

	private String staffRoleScopeId = "";
	private String staffRoleId = "";
	private String regionId = "";
	private String regionType = "";

	public StaffRoleScopeVO() {}

	public StaffRoleScopeVO( String pstaffRoleScopeId, String pstaffRoleId, String pregionId, String pregionType ) {
		staffRoleScopeId = pstaffRoleScopeId;
		staffRoleId = pstaffRoleId;
		regionId = pregionId;
		regionType = pregionType;
	}

	public String getStaffRoleScopeId() {
		return staffRoleScopeId;
	}

	public String getStaffRoleId() {
		return staffRoleId;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getRegionType() {
		return regionType;
	}

	public void setStaffRoleScopeId(String pStaffRoleScopeId) {
		staffRoleScopeId = pStaffRoleScopeId;
	}

	public void setStaffRoleId(String pStaffRoleId) {
		staffRoleId = pStaffRoleId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setRegionType(String pRegionType) {
		regionType = pRegionType;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("STAFF_ROLE_SCOPE_ID",this.staffRoleScopeId);
		hashMap.put("STAFF_ROLE_ID",this.staffRoleId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("REGION_TYPE",this.regionType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.staffRoleScopeId = (String) hashMap.get("STAFF_ROLE_SCOPE_ID");
			this.staffRoleId = (String) hashMap.get("STAFF_ROLE_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.regionType = (String) hashMap.get("REGION_TYPE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("STAFF_ROLE_SCOPE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "STAFF_ROLE_SCOPE";
	}

}
