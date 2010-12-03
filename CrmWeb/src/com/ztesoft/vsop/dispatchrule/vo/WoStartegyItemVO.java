package com.ztesoft.vsop.dispatchrule.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoStartegyItemVO extends ValueObject implements VO {
	

	private String item_code;
	

	private String item_name;
	

	private String default_value;
	

	public String getItem_code() {
		return item_code;
	}
	
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getDefault_value() {
		return default_value;
	}
	
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("item_code",this.item_code);	
				
		hashMap.put("item_name",this.item_name);	
				
		hashMap.put("default_value",this.default_value);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.item_code = Const.getStrValue(hashMap, "item_code");
						
			this.item_name = Const.getStrValue(hashMap, "item_name");
						
			this.default_value = Const.getStrValue(hashMap, "default_value");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_STARTEGY_ITEM";
	}
	
}
