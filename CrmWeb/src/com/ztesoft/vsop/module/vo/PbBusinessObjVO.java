package com.ztesoft.vsop.module.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class PbBusinessObjVO extends ValueObject implements VO {
	

	private String business_obj_id;
	

	private String obj_type_code;
	

	private String business_obj_name;
	

	private String class_name;
	

	private String method_name;
	

	private String create_date;
	

	private String state;
	

	private String state_date;
	

	public String getBusiness_obj_id() {
		return business_obj_id;
	}
	
	public void setBusiness_obj_id(String business_obj_id) {
		this.business_obj_id = business_obj_id;
	}
	public String getObj_type_code() {
		return obj_type_code;
	}
	
	public void setObj_type_code(String obj_type_code) {
		this.obj_type_code = obj_type_code;
	}
	public String getBusiness_obj_name() {
		return business_obj_name;
	}
	
	public void setBusiness_obj_name(String business_obj_name) {
		this.business_obj_name = business_obj_name;
	}
	public String getClass_name() {
		return class_name;
	}
	
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getMethod_name() {
		return method_name;
	}
	
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}
	public String getCreate_date() {
		return create_date;
	}
	
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getState_date() {
		return state_date;
	}
	
	public void setState_date(String state_date) {
		this.state_date = state_date;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("business_obj_id",this.business_obj_id);	
				
		hashMap.put("obj_type_code",this.obj_type_code);	
				
		hashMap.put("business_obj_name",this.business_obj_name);	
				
		hashMap.put("class_name",this.class_name);	
				
		hashMap.put("method_name",this.method_name);	
				
		hashMap.put("create_date",this.create_date);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("state_date",this.state_date);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.business_obj_id = Const.getStrValue(hashMap, "business_obj_id");
						
			this.obj_type_code = Const.getStrValue(hashMap, "obj_type_code");
						
			this.business_obj_name = Const.getStrValue(hashMap, "business_obj_name");
						
			this.class_name = Const.getStrValue(hashMap, "class_name");
						
			this.method_name = Const.getStrValue(hashMap, "method_name");
						
			this.create_date = Const.getStrValue(hashMap, "create_date");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.state_date = Const.getStrValue(hashMap, "state_date");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "PB_BUSINESS_OBJ";
	}
	
}
