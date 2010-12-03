package com.ztesoft.vsop.paras.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeTranRuleVO extends ValueObject implements VO {
	

	private String tran_rule_id;
	

	private String business_obj_id;
	

	private String id;
	

	private String map_type_id;
	

	private String int_sys_id;
	

	private String name;
	

	private String get_method;
	

	private String execute_sql;
	

	private String create_date;
	

	public String getTran_rule_id() {
		return tran_rule_id;
	}
	
	public void setTran_rule_id(String tran_rule_id) {
		this.tran_rule_id = tran_rule_id;
	}
	public String getBusiness_obj_id() {
		return business_obj_id;
	}
	
	public void setBusiness_obj_id(String business_obj_id) {
		this.business_obj_id = business_obj_id;
	}
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getMap_type_id() {
		return map_type_id;
	}
	
	public void setMap_type_id(String map_type_id) {
		this.map_type_id = map_type_id;
	}
	public String getInt_sys_id() {
		return int_sys_id;
	}
	
	public void setInt_sys_id(String int_sys_id) {
		this.int_sys_id = int_sys_id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getGet_method() {
		return get_method;
	}
	
	public void setGet_method(String get_method) {
		this.get_method = get_method;
	}
	public String getExecute_sql() {
		return execute_sql;
	}
	
	public void setExecute_sql(String execute_sql) {
		this.execute_sql = execute_sql;
	}
	public String getCreate_date() {
		return create_date;
	}
	
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("tran_rule_id",this.tran_rule_id);	
				
		hashMap.put("business_obj_id",this.business_obj_id);	
				
		hashMap.put("id",this.id);	
				
		hashMap.put("map_type_id",this.map_type_id);	
				
		hashMap.put("int_sys_id",this.int_sys_id);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("get_method",this.get_method);	
				
		hashMap.put("execute_sql",this.execute_sql);	
				
		hashMap.put("create_date",this.create_date);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.tran_rule_id = Const.getStrValue(hashMap, "tran_rule_id");
						
			this.business_obj_id = Const.getStrValue(hashMap, "business_obj_id");
						
			this.id = Const.getStrValue(hashMap, "id");
						
			this.map_type_id = Const.getStrValue(hashMap, "map_type_id");
						
			this.int_sys_id = Const.getStrValue(hashMap, "int_sys_id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.get_method = Const.getStrValue(hashMap, "get_method");
						
			this.execute_sql = Const.getStrValue(hashMap, "execute_sql");
						
			this.create_date = Const.getStrValue(hashMap, "create_date");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_TRAN_RULE";
	}
	
}
