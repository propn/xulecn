package com.ztesoft.crm.business.common.order.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class OrdServAttrVO extends ValueObject implements VO {
	

	private String ord_item_id;
	

	private String cust_ord_id;
	

	private String ord_id;
	

	private String action_type;
	

	private String state_date;
	

	private String end_time;
	

	private String beg_time;
	

	private String serv_id;
	

	private String attr_id;
	

	private String field_name;
	

	private String product_id;
	

	private String attr_val;
	

	public String getOrd_item_id() {
		return ord_item_id;
	}
	
	public void setOrd_item_id(String ord_item_id) {
		this.ord_item_id = ord_item_id;
	}
	public String getCust_ord_id() {
		return cust_ord_id;
	}
	
	public void setCust_ord_id(String cust_ord_id) {
		this.cust_ord_id = cust_ord_id;
	}
	public String getOrd_id() {
		return ord_id;
	}
	
	public void setOrd_id(String ord_id) {
		this.ord_id = ord_id;
	}
	public String getAction_type() {
		return action_type;
	}
	
	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}
	public String getState_date() {
		return state_date;
	}
	
	public void setState_date(String state_date) {
		this.state_date = state_date;
	}
	public String getEnd_time() {
		return end_time;
	}
	
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getBeg_time() {
		return beg_time;
	}
	
	public void setBeg_time(String beg_time) {
		this.beg_time = beg_time;
	}
	public String getServ_id() {
		return serv_id;
	}
	
	public void setServ_id(String serv_id) {
		this.serv_id = serv_id;
	}
	public String getAttr_id() {
		return attr_id;
	}
	
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	public String getField_name() {
		return field_name;
	}
	
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getAttr_val() {
		return attr_val;
	}
	
	public void setAttr_val(String attr_val) {
		this.attr_val = attr_val;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("ord_item_id",this.ord_item_id);	
				
		hashMap.put("cust_ord_id",this.cust_ord_id);	
				
		hashMap.put("ord_id",this.ord_id);	
				
		hashMap.put("action_type",this.action_type);	
				
		hashMap.put("state_date",this.state_date);	
				
		hashMap.put("end_time",this.end_time);	
				
		hashMap.put("beg_time",this.beg_time);	
				
		hashMap.put("serv_id",this.serv_id);	
				
		hashMap.put("attr_id",this.attr_id);	
				
		hashMap.put("field_name",this.field_name);	
				
		hashMap.put("product_id",this.product_id);	
				
		hashMap.put("attr_val",this.attr_val);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.ord_item_id = Const.getStrValue(hashMap, "ord_item_id");
						
			this.cust_ord_id = Const.getStrValue(hashMap, "cust_ord_id");
						
			this.ord_id = Const.getStrValue(hashMap, "ord_id");
						
			this.action_type = Const.getStrValue(hashMap, "action_type");
						
			this.state_date = Const.getStrValue(hashMap, "state_date");
						
			this.end_time = Const.getStrValue(hashMap, "end_time");
						
			this.beg_time = Const.getStrValue(hashMap, "beg_time");
						
			this.serv_id = Const.getStrValue(hashMap, "serv_id");
						
			this.attr_id = Const.getStrValue(hashMap, "attr_id");
						
			this.field_name = Const.getStrValue(hashMap, "field_name");
						
			this.product_id = Const.getStrValue(hashMap, "product_id");
						
			this.attr_val = Const.getStrValue(hashMap, "attr_val");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "ORD_SERV_ATTR";
	}
	
}
