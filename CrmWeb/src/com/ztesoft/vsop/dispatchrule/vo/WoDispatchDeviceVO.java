package com.ztesoft.vsop.dispatchrule.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoDispatchDeviceVO extends ValueObject implements VO {
	

	private String device_id;
	

	private String dispatch_rule_id;
	

	public String getDevice_id() {
		return device_id;
	}
	
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getDispatch_rule_id() {
		return dispatch_rule_id;
	}
	
	public void setDispatch_rule_id(String dispatch_rule_id) {
		this.dispatch_rule_id = dispatch_rule_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("device_id",this.device_id);	
				
		hashMap.put("dispatch_rule_id",this.dispatch_rule_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.device_id = Const.getStrValue(hashMap, "device_id");
						
			this.dispatch_rule_id = Const.getStrValue(hashMap, "dispatch_rule_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_DISPATCH_DEVICE";
	}
	
}
