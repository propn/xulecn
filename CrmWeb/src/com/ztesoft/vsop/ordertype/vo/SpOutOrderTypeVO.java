package com.ztesoft.vsop.ordertype.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class SpOutOrderTypeVO extends ValueObject implements VO {
	

	private String out_order_type_id;
	

	private String out_order_type_code;
	

	private String catalog_id;
	

	private String int_sys_id;
	

	private String order_feedback_type_id;
	

	private String order_type_name;
	

	private String is_rule;
	

	private String comments;
	

	private String is_syn;
	

	private String type_class;
	

	public String getOut_order_type_id() {
		return out_order_type_id;
	}
	
	public void setOut_order_type_id(String out_order_type_id) {
		this.out_order_type_id = out_order_type_id;
	}
	public String getOut_order_type_code() {
		return out_order_type_code;
	}
	
	public void setOut_order_type_code(String out_order_type_code) {
		this.out_order_type_code = out_order_type_code;
	}
	public String getCatalog_id() {
		return catalog_id;
	}
	
	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getInt_sys_id() {
		return int_sys_id;
	}
	
	public void setInt_sys_id(String int_sys_id) {
		this.int_sys_id = int_sys_id;
	}
	public String getOrder_feedback_type_id() {
		return order_feedback_type_id;
	}
	
	public void setOrder_feedback_type_id(String order_feedback_type_id) {
		this.order_feedback_type_id = order_feedback_type_id;
	}
	public String getOrder_type_name() {
		return order_type_name;
	}
	
	public void setOrder_type_name(String order_type_name) {
		this.order_type_name = order_type_name;
	}
	public String getIs_rule() {
		return is_rule;
	}
	
	public void setIs_rule(String is_rule) {
		this.is_rule = is_rule;
	}
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getIs_syn() {
		return is_syn;
	}
	
	public void setIs_syn(String is_syn) {
		this.is_syn = is_syn;
	}
	public String getType_class() {
		return type_class;
	}
	
	public void setType_class(String type_class) {
		this.type_class = type_class;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("out_order_type_id",this.out_order_type_id);	
				
		hashMap.put("out_order_type_code",this.out_order_type_code);	
				
		hashMap.put("catalog_id",this.catalog_id);	
				
		hashMap.put("int_sys_id",this.int_sys_id);	
				
		hashMap.put("order_feedback_type_id",this.order_feedback_type_id);	
				
		hashMap.put("order_type_name",this.order_type_name);	
				
		hashMap.put("is_rule",this.is_rule);	
				
		hashMap.put("comments",this.comments);	
				
		hashMap.put("is_syn",this.is_syn);	
				
		hashMap.put("type_class",this.type_class);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.out_order_type_id = Const.getStrValue(hashMap, "out_order_type_id");
						
			this.out_order_type_code = Const.getStrValue(hashMap, "out_order_type_code");
						
			this.catalog_id = Const.getStrValue(hashMap, "catalog_id");
						
			this.int_sys_id = Const.getStrValue(hashMap, "int_sys_id");
						
			this.order_feedback_type_id = Const.getStrValue(hashMap, "order_feedback_type_id");
						
			this.order_type_name = Const.getStrValue(hashMap, "order_type_name");
						
			this.is_rule = Const.getStrValue(hashMap, "is_rule");
						
			this.comments = Const.getStrValue(hashMap, "comments");
						
			this.is_syn = Const.getStrValue(hashMap, "is_syn");
						
			this.type_class = Const.getStrValue(hashMap, "type_class");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "SP_OUT_ORDER_TYPE";
	}
	
}
