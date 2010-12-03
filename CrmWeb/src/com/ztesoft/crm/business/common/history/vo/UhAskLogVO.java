package com.ztesoft.crm.business.common.history.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class UhAskLogVO extends ValueObject implements VO {
	

	private String ord_item_id;
	

	private String cust_ord_id;
	

	private String field_name;
	

	private String field_value;
	

	private String old_field_value;
	

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
	public String getField_name() {
		return field_name;
	}
	
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getField_value() {
		return field_value;
	}
	
	public void setField_value(String field_value) {
		this.field_value = field_value;
	}
	public String getOld_field_value() {
		return old_field_value;
	}
	
	public void setOld_field_value(String old_field_value) {
		this.old_field_value = old_field_value;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("ord_item_id",this.ord_item_id);	
				
		hashMap.put("cust_ord_id",this.cust_ord_id);	
				
		hashMap.put("field_name",this.field_name);	
				
		hashMap.put("field_value",this.field_value);	
				
		hashMap.put("old_field_value",this.old_field_value);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.ord_item_id = Const.getStrValue(hashMap, "ord_item_id");
						
			this.cust_ord_id = Const.getStrValue(hashMap, "cust_ord_id");
						
			this.field_name = Const.getStrValue(hashMap, "field_name");
						
			this.field_value = Const.getStrValue(hashMap, "field_value");
						
			this.old_field_value = Const.getStrValue(hashMap, "old_field_value");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "UH_ASK_LOG";
	}
	
}
