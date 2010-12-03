package com.ztesoft.oaas.service;

import java.io.Serializable;
public class RoleStateBsnStrucVO implements Serializable {

    private String privilegeID;//权限标识
    private String PrivilegeCode;//权限编码
    private String RegionID;//区域标识
    private String permitionFlag;//允许标志
    private String partyRoleSchema;// 参与人角色权限类型" 98A" 仅有本区域的权限"98B" 有与本区域同级的所有权限
    private String validTime;//权限有效时间（单位为分钟），最长一星期
    
	public String getPartyRoleSchema() {
		return partyRoleSchema;
	}
	public void setPartyRoleSchema(String partyRoleSchema) {
		this.partyRoleSchema = partyRoleSchema;
	}
	public String getPermitionFlag() {
		return permitionFlag;
	}
	public void setPermitionFlag(String permitionFlag) {
		this.permitionFlag = permitionFlag;
	}
	public String getPrivilegeCode() {
		return PrivilegeCode;
	}
	public void setPrivilegeCode(String privilegeCode) {
		this.PrivilegeCode = privilegeCode;
	}
	public String getPrivilegeID() {
		return privilegeID;
	}
	public void setPrivilegeID(String privilegeID) {
		this.privilegeID = privilegeID;
	}
	public String getRegionID() {
		return RegionID;
	}
	public void setRegionID(String regionID) {
		this.RegionID = regionID;
	}
	public String getValidTime() {
		return validTime;
	}
	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

}
