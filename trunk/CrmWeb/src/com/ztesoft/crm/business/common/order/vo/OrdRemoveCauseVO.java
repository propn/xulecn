package com.ztesoft.crm.business.common.order.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class OrdRemoveCauseVO extends ValueObject implements VO {
	

	private String ord_item_id;
	

	private String cust_ord_id;
	

	private String ord_id;
	

	private String action_type;
	

	private String state_date;
	

	private String end_time;
	

	private String beg_time;
	

	private String remove_cause_id;
	

	private String comp_inst_id;
	

	private String remove_date;
	

	private String out_cause;
	

	private String out_to;
	

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
	public String getRemove_cause_id() {
		return remove_cause_id;
	}
	
	public void setRemove_cause_id(String remove_cause_id) {
		this.remove_cause_id = remove_cause_id;
	}
	public String getComp_inst_id() {
		return comp_inst_id;
	}
	
	public void setComp_inst_id(String comp_inst_id) {
		this.comp_inst_id = comp_inst_id;
	}
	public String getRemove_date() {
		return remove_date;
	}
	
	public void setRemove_date(String remove_date) {
		this.remove_date = remove_date;
	}
	public String getOut_cause() {
		return out_cause;
	}
	
	public void setOut_cause(String out_cause) {
		this.out_cause = out_cause;
	}
	public String getOut_to() {
		return out_to;
	}
	
	public void setOut_to(String out_to) {
		this.out_to = out_to;
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
				
		hashMap.put("remove_cause_id",this.remove_cause_id);	
				
		hashMap.put("comp_inst_id",this.comp_inst_id);	
				
		hashMap.put("remove_date",this.remove_date);	
				
		hashMap.put("out_cause",this.out_cause);	
				
		hashMap.put("out_to",this.out_to);	
				
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
						
			this.remove_cause_id = Const.getStrValue(hashMap, "remove_cause_id");
						
			this.comp_inst_id = Const.getStrValue(hashMap, "comp_inst_id");
						
			this.remove_date = Const.getStrValue(hashMap, "remove_date");
						
			this.out_cause = Const.getStrValue(hashMap, "out_cause");
						
			this.out_to = Const.getStrValue(hashMap, "out_to");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "ORD_REMOVE_CAUSE";
	}
	
}
