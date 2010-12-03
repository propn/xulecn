package com.ztesoft.vsop.paras.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeJavaEngineVO extends ValueObject implements VO {
	

	private String id;
	

	private String name;
	

	private String rule_desc;
	

	private String rule_content;
	

	private String state;
	

	private String class_name;
	

	private String ceate_date;
	

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getRule_desc() {
		return rule_desc;
	}
	
	public void setRule_desc(String rule_desc) {
		this.rule_desc = rule_desc;
	}
	public String getRule_content() {
		return rule_content;
	}
	
	public void setRule_content(String rule_content) {
		this.rule_content = rule_content;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getClass_name() {
		return class_name;
	}
	
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getCeate_date() {
		return ceate_date;
	}
	
	public void setCeate_date(String ceate_date) {
		this.ceate_date = ceate_date;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("id",this.id);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("rule_desc",this.rule_desc);	
				
		hashMap.put("rule_content",this.rule_content);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("class_name",this.class_name);	
				
		hashMap.put("ceate_date",this.ceate_date);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.id = Const.getStrValue(hashMap, "id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.rule_desc = Const.getStrValue(hashMap, "rule_desc");
						
			this.rule_content = Const.getStrValue(hashMap, "rule_content");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.class_name = Const.getStrValue(hashMap, "class_name");
						
			this.ceate_date = Const.getStrValue(hashMap, "ceate_date");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_JAVA_ENGINE";
	}
	
}
