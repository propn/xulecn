package com.ztesoft.vsop.paras.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeValueMapListVO extends ValueObject implements VO {
	

	private String map_type_id;
	

	private String para_value;
	

	private String map_value;
	

	public String getMap_type_id() {
		return map_type_id;
	}
	
	public void setMap_type_id(String map_type_id) {
		this.map_type_id = map_type_id;
	}
	public String getPara_value() {
		return para_value;
	}
	
	public void setPara_value(String para_value) {
		this.para_value = para_value;
	}
	public String getMap_value() {
		return map_value;
	}
	
	public void setMap_value(String map_value) {
		this.map_value = map_value;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("map_type_id",this.map_type_id);	
				
		hashMap.put("para_value",this.para_value);	
				
		hashMap.put("map_value",this.map_value);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.map_type_id = Const.getStrValue(hashMap, "map_type_id");
						
			this.para_value = Const.getStrValue(hashMap, "para_value");
						
			this.map_value = Const.getStrValue(hashMap, "map_value");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_VALUE_MAP_LIST";
	}
	
}
