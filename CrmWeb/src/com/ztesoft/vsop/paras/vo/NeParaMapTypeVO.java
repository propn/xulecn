package com.ztesoft.vsop.paras.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeParaMapTypeVO extends ValueObject implements VO {
	

	private String map_type_id;
	

	private String name;
	

	public String getMap_type_id() {
		return map_type_id;
	}
	
	public void setMap_type_id(String map_type_id) {
		this.map_type_id = map_type_id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("map_type_id",this.map_type_id);	
				
		hashMap.put("name",this.name);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.map_type_id = Const.getStrValue(hashMap, "map_type_id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_PARA_MAP_TYPE";
	}
	
}
