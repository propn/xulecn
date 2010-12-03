package com.ztesoft.vsop.dispatchrule.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoDispatchStartegyVO extends ValueObject implements VO {
	

	private String dispatch_rule_id;
	

	private String item_val;
	

	private String item_code;
	

	public String getDispatch_rule_id() {
		return dispatch_rule_id;
	}
	
	public void setDispatch_rule_id(String dispatch_rule_id) {
		this.dispatch_rule_id = dispatch_rule_id;
	}
	public String getItem_val() {
		return item_val;
	}
	
	public void setItem_val(String item_val) {
		this.item_val = item_val;
	}
	public String getItem_code() {
		return item_code;
	}
	
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("dispatch_rule_id",this.dispatch_rule_id);	
				
		hashMap.put("item_val",this.item_val);	
				
		hashMap.put("item_code",this.item_code);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.dispatch_rule_id = Const.getStrValue(hashMap, "dispatch_rule_id");
						
			this.item_val = Const.getStrValue(hashMap, "item_val");
						
			this.item_code = Const.getStrValue(hashMap, "item_code");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_DISPATCH_STARTEGY";
	}
	
}
