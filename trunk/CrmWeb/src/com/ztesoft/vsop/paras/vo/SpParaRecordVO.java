package com.ztesoft.vsop.paras.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class SpParaRecordVO extends ValueObject implements VO {
	

	private String record_id;
	

	private String record_name;
	

	private String record_path;
	

	private String rec_type;
	

	private String groupby_codes;
	

	private String orderby_asc_codes;
	

	private String orderby_desc_codes;
	

	private String group_order_type;
	

	private String target_path;
	

	public String getRecord_id() {
		return record_id;
	}
	
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
	public String getRecord_name() {
		return record_name;
	}
	
	public void setRecord_name(String record_name) {
		this.record_name = record_name;
	}
	public String getRecord_path() {
		return record_path;
	}
	
	public void setRecord_path(String record_path) {
		this.record_path = record_path;
	}
	public String getRec_type() {
		return rec_type;
	}
	
	public void setRec_type(String rec_type) {
		this.rec_type = rec_type;
	}
	public String getGroupby_codes() {
		return groupby_codes;
	}
	
	public void setGroupby_codes(String groupby_codes) {
		this.groupby_codes = groupby_codes;
	}
	public String getOrderby_asc_codes() {
		return orderby_asc_codes;
	}
	
	public void setOrderby_asc_codes(String orderby_asc_codes) {
		this.orderby_asc_codes = orderby_asc_codes;
	}
	public String getOrderby_desc_codes() {
		return orderby_desc_codes;
	}
	
	public void setOrderby_desc_codes(String orderby_desc_codes) {
		this.orderby_desc_codes = orderby_desc_codes;
	}
	public String getGroup_order_type() {
		return group_order_type;
	}
	
	public void setGroup_order_type(String group_order_type) {
		this.group_order_type = group_order_type;
	}
	public String getTarget_path() {
		return target_path;
	}
	
	public void setTarget_path(String target_path) {
		this.target_path = target_path;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("record_id",this.record_id);	
				
		hashMap.put("record_name",this.record_name);	
				
		hashMap.put("record_path",this.record_path);	
				
		hashMap.put("rec_type",this.rec_type);	
				
		hashMap.put("groupby_codes",this.groupby_codes);	
				
		hashMap.put("orderby_asc_codes",this.orderby_asc_codes);	
				
		hashMap.put("orderby_desc_codes",this.orderby_desc_codes);	
				
		hashMap.put("group_order_type",this.group_order_type);	
				
		hashMap.put("target_path",this.target_path);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.record_id = Const.getStrValue(hashMap, "record_id");
						
			this.record_name = Const.getStrValue(hashMap, "record_name");
						
			this.record_path = Const.getStrValue(hashMap, "record_path");
						
			this.rec_type = Const.getStrValue(hashMap, "rec_type");
						
			this.groupby_codes = Const.getStrValue(hashMap, "groupby_codes");
						
			this.orderby_asc_codes = Const.getStrValue(hashMap, "orderby_asc_codes");
						
			this.orderby_desc_codes = Const.getStrValue(hashMap, "orderby_desc_codes");
						
			this.group_order_type = Const.getStrValue(hashMap, "group_order_type");
						
			this.target_path = Const.getStrValue(hashMap, "target_path");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "SP_PARA_RECORD";
	}
	
}
