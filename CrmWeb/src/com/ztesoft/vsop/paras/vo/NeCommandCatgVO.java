package com.ztesoft.vsop.paras.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeCommandCatgVO extends ValueObject implements VO {
	

	private String command_catalog_id;
	

	private String name;
	

	public String getCommand_catalog_id() {
		return command_catalog_id;
	}
	
	public void setCommand_catalog_id(String command_catalog_id) {
		this.command_catalog_id = command_catalog_id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("command_catalog_id",this.command_catalog_id);	
				
		hashMap.put("name",this.name);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.command_catalog_id = Const.getStrValue(hashMap, "command_catalog_id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_COMMAND_CATALOG";
	}
	
}
