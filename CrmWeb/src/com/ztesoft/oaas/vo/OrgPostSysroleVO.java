package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class OrgPostSysroleVO extends ValueObject implements VO {

	private String orgPostRoleId = "";
	private String orgPostId = "";
	private String roleId = "";
	private String roleName = "";
	private String state = "";
	private String effDate = "";
	private String expDate = "";
	
	private String regionId = "";
	private String regionType = "";

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public OrgPostSysroleVO() {}

	public OrgPostSysroleVO( String porgPostRoleId, String porgPostId, String proleId, String pstate, String peffDate, String pexpDate,String pregionId,String pregionType ) {
		orgPostRoleId = porgPostRoleId;
		orgPostId = porgPostId;
		roleId = proleId;
		//roleName = proleName;
		state = pstate;
		effDate = peffDate;
		expDate = pexpDate;
		regionId = pregionId ;
		regionType = pregionType ;
	}

	public String getOrgPostRoleId() {
		return orgPostRoleId;
	}

	public String getOrgPostId() {
		return orgPostId;
	}

	public String getRoleId() {
		return roleId;
	}

	public String getState() {
		return state;
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setOrgPostRoleId(String pOrgPostRoleId) {
		orgPostRoleId = pOrgPostRoleId;
	}

	public void setOrgPostId(String pOrgPostId) {
		orgPostId = pOrgPostId;
	}

	public void setRoleId(String pRoleId) {
		roleId = pRoleId;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setEffDate(String pEffDate) {
		effDate = pEffDate;
	}

	public void setExpDate(String pExpDate) {
		expDate = pExpDate;
	}

	public void setRoleName(String pRoleName){
		roleName = pRoleName;
	}
	public String getRoleName(){
		return this.roleName ;
	}
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ORG_POST_ROLE_ID",this.orgPostRoleId);
		hashMap.put("ORG_POST_ID",this.orgPostId);
		hashMap.put("ROLE_ID",this.roleId);
		hashMap.put("ROLE_NAME",this.roleName);
		hashMap.put("STATE",this.state);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		hashMap.put("REGION_ID", this.regionId);
		hashMap.put("REGION_TYPE", this.regionType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.orgPostRoleId = (String) hashMap.get("ORG_POST_ROLE_ID");
			this.orgPostId = (String) hashMap.get("ORG_POST_ID");
			this.roleId = (String) hashMap.get("ROLE_ID");
			this.roleName = (String)hashMap.get(("ROLE_NAME"));
			this.state = (String) hashMap.get("STATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.regionId = (String)hashMap.get("REGION_ID");
			this.regionType = (String)hashMap.get("REGION_TYPE"); 
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ORG_POST_ROLE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ORG_POST_SYSROLE";
	}

}
