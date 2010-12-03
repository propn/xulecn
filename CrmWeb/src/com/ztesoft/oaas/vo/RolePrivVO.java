package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RolePrivVO extends ValueObject implements VO {

	private String roleId = "";
	private String privId = "";
	private String privType = "";

	public RolePrivVO() {}

	public RolePrivVO( String proleId, String pprivId, String pprivType ) {
		roleId = proleId;
		privId = pprivId;
		privType = pprivType;
	}

	public String getPrivType(){
		return privType;
	}
	public void setPrivType( String pprivType ){
		privType = pprivType ;
	}
	public String getRoleId() {
		return roleId;
	}

	public String getPrivId() {
		return privId;
	}

	public void setRoleId(String pRoleId) {
		roleId = pRoleId;
	}

	public void setPrivId(String pPrivId) {
		privId = pPrivId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ROLE_ID",this.roleId);
		hashMap.put("PRIVILEGE_ID",this.privId);
		hashMap.put("PRIVILEGE_TYPE",this.privType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.roleId = (String) hashMap.get("ROLE_ID");
			this.privId = (String) hashMap.get("PRIVILEGE_ID");
			this.privType = (String)hashMap.get("PRIVILEGE_TYPE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PRIVILEGE_ID");
		arrayList.add("ROLE_ID");
		arrayList.add("PRIVILEGE_TYPE");
		return arrayList;
	}

	public String getTableName() {
		return "ROLE_PRIVILEGE";
	}

}
