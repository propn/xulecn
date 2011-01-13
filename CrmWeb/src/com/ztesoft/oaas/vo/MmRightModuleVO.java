package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class MmRightModuleVO extends ValueObject implements VO {

	private String privId = "";
	private String menuId = "";

	public MmRightModuleVO() {}

	public MmRightModuleVO( String pprivId, String pmenuId ) {
		privId = pprivId;
		menuId = pmenuId;
	}

	public String getPrivId() {
		return privId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setPrivId(String pPrivId) {
		privId = pPrivId;
	}

	public void setMenuId(String pMenuId) {
		menuId = pMenuId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PRIVILEGE_ID",this.privId);
		hashMap.put("MENU_ID",this.menuId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.privId = (String) hashMap.get("PRIVILEGE_ID");
			this.menuId = (String) hashMap.get("MENU_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("MENU_ID");
		arrayList.add("PRIVILEGE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "MM_RIGHT_MODULE";
	}

}
