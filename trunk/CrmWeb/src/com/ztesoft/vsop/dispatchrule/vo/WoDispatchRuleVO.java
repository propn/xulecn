package com.ztesoft.vsop.dispatchrule.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoDispatchRuleVO extends ValueObject implements VO {
	

	private String dispatch_rule_id;
	

	private String name;
	

	private String code;
	

	private String state;
	

	private String dispatch_rule;
	

	private String dispatch_queue_id;
	

	public String getDispatch_rule_id() {
		return dispatch_rule_id;
	}
	
	public void setDispatch_rule_id(String dispatch_rule_id) {
		this.dispatch_rule_id = dispatch_rule_id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getDispatch_rule() {
		return dispatch_rule;
	}
	
	public void setDispatch_rule(String dispatch_rule) {
		this.dispatch_rule = dispatch_rule;
	}
	public String getDispatch_queue_id() {
		return dispatch_queue_id;
	}
	
	public void setDispatch_queue_id(String dispatch_queue_id) {
		this.dispatch_queue_id = dispatch_queue_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("dispatch_rule_id",this.dispatch_rule_id);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("code",this.code);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("dispatch_rule",this.dispatch_rule);	
				
		hashMap.put("dispatch_queue_id",this.dispatch_queue_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.dispatch_rule_id = Const.getStrValue(hashMap, "dispatch_rule_id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.code = Const.getStrValue(hashMap, "code");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.dispatch_rule = Const.getStrValue(hashMap, "dispatch_rule");
						
			this.dispatch_queue_id = Const.getStrValue(hashMap, "dispatch_queue_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_DISPATCH_RULE";
	}
	
}
