package com.ztesoft.oaas.vo;

import java.io.Serializable;

public class SimpleStaffRoleVO implements Serializable{
	private String partyRoleId = "";
	private String roleId = "";
	private String regionType = "";
	private String[] regionIds ;
	public String getPartyRoleId() {
		return partyRoleId;
	}
	public void setPartyRoleId(String partyRoleId) {
		this.partyRoleId = partyRoleId;
	}
	public String[] getRegionIds() {
		return regionIds;
	}
	public void setRegionIds(String[] regionIds) {
		this.regionIds = regionIds;
	}
	public String getRegionType() {
		return regionType;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	} 
}
