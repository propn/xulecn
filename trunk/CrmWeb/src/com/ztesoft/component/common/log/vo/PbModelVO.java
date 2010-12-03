package com.ztesoft.component.common.log.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PbModelVO extends ValueObject implements VO {

	private String id = "";
	private String name = "";

	public PbModelVO() {}

	public PbModelVO( String pid, String pname ) {
		id = pid;
		name = pname;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String pId) {
		id = pId;
	}

	public void setName(String pName) {
		name = pName;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ID",this.id);
		hashMap.put("NAME",this.name);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.id = (String) hashMap.get("ID");
			this.name = (String) hashMap.get("NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ID");
		return arrayList;
	}

	public String getTableName() {
		return "PB_MODEL";
	}

}
