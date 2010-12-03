package com.ztesoft.crm.business.common.order.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class OrdAskVO extends ValueObject implements VO {
	

	private String ord_id;
	

	private String cust_ord_id;
	

	private String same_ask_no;
	

	private String service_offer_id;
	

	private String ord_type;
	

	private String instance_type;
	

	private String instance_type_id;
	

	private String instance_id;
	

	private String solution_id;
	

	private String notes;
	

	private String state;
	

	private String state_reason_id;
	

	private String ask_date;
	

	private String confirm_date;
	

	private String fin_date;
	

	private String beg_time;
	

	private String staff_no;
	

	private String site_no;
	

	private String agreement_id;
	

	private String last_ord_id;
	

	public String getOrd_id() {
		return ord_id;
	}
	
	public void setOrd_id(String ord_id) {
		this.ord_id = ord_id;
	}
	public String getCust_ord_id() {
		return cust_ord_id;
	}
	
	public void setCust_ord_id(String cust_ord_id) {
		this.cust_ord_id = cust_ord_id;
	}
	public String getSame_ask_no() {
		return same_ask_no;
	}
	
	public void setSame_ask_no(String same_ask_no) {
		this.same_ask_no = same_ask_no;
	}
	public String getService_offer_id() {
		return service_offer_id;
	}
	
	public void setService_offer_id(String service_offer_id) {
		this.service_offer_id = service_offer_id;
	}
	public String getOrd_type() {
		return ord_type;
	}
	
	public void setOrd_type(String ord_type) {
		this.ord_type = ord_type;
	}
	public String getInstance_type() {
		return instance_type;
	}
	
	public void setInstance_type(String instance_type) {
		this.instance_type = instance_type;
	}
	public String getInstance_type_id() {
		return instance_type_id;
	}
	
	public void setInstance_type_id(String instance_type_id) {
		this.instance_type_id = instance_type_id;
	}
	public String getInstance_id() {
		return instance_id;
	}
	
	public void setInstance_id(String instance_id) {
		this.instance_id = instance_id;
	}
	public String getSolution_id() {
		return solution_id;
	}
	
	public void setSolution_id(String solution_id) {
		this.solution_id = solution_id;
	}
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getState_reason_id() {
		return state_reason_id;
	}
	
	public void setState_reason_id(String state_reason_id) {
		this.state_reason_id = state_reason_id;
	}
	public String getAsk_date() {
		return ask_date;
	}
	
	public void setAsk_date(String ask_date) {
		this.ask_date = ask_date;
	}
	public String getConfirm_date() {
		return confirm_date;
	}
	
	public void setConfirm_date(String confirm_date) {
		this.confirm_date = confirm_date;
	}
	public String getFin_date() {
		return fin_date;
	}
	
	public void setFin_date(String fin_date) {
		this.fin_date = fin_date;
	}
	public String getBeg_time() {
		return beg_time;
	}
	
	public void setBeg_time(String beg_time) {
		this.beg_time = beg_time;
	}
	public String getStaff_no() {
		return staff_no;
	}
	
	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}
	public String getSite_no() {
		return site_no;
	}
	
	public void setSite_no(String site_no) {
		this.site_no = site_no;
	}
	public String getAgreement_id() {
		return agreement_id;
	}
	
	public void setAgreement_id(String agreement_id) {
		this.agreement_id = agreement_id;
	}
	public String getLast_ord_id() {
		return last_ord_id;
	}
	
	public void setLast_ord_id(String last_ord_id) {
		this.last_ord_id = last_ord_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("ord_id",this.ord_id);	
				
		hashMap.put("cust_ord_id",this.cust_ord_id);	
				
		hashMap.put("same_ask_no",this.same_ask_no);	
				
		hashMap.put("service_offer_id",this.service_offer_id);	
				
		hashMap.put("ord_type",this.ord_type);	
				
		hashMap.put("instance_type",this.instance_type);	
				
		hashMap.put("instance_type_id",this.instance_type_id);	
				
		hashMap.put("instance_id",this.instance_id);	
				
		hashMap.put("solution_id",this.solution_id);	
				
		hashMap.put("notes",this.notes);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("state_reason_id",this.state_reason_id);	
				
		hashMap.put("ask_date",this.ask_date);	
				
		hashMap.put("confirm_date",this.confirm_date);	
				
		hashMap.put("fin_date",this.fin_date);	
				
		hashMap.put("beg_time",this.beg_time);	
				
		hashMap.put("staff_no",this.staff_no);	
				
		hashMap.put("site_no",this.site_no);	
				
		hashMap.put("agreement_id",this.agreement_id);	
				
		hashMap.put("last_ord_id",this.last_ord_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.ord_id = Const.getStrValue(hashMap, "ord_id");
						
			this.cust_ord_id = Const.getStrValue(hashMap, "cust_ord_id");
						
			this.same_ask_no = Const.getStrValue(hashMap, "same_ask_no");
						
			this.service_offer_id = Const.getStrValue(hashMap, "service_offer_id");
						
			this.ord_type = Const.getStrValue(hashMap, "ord_type");
						
			this.instance_type = Const.getStrValue(hashMap, "instance_type");
						
			this.instance_type_id = Const.getStrValue(hashMap, "instance_type_id");
						
			this.instance_id = Const.getStrValue(hashMap, "instance_id");
						
			this.solution_id = Const.getStrValue(hashMap, "solution_id");
						
			this.notes = Const.getStrValue(hashMap, "notes");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.state_reason_id = Const.getStrValue(hashMap, "state_reason_id");
						
			this.ask_date = Const.getStrValue(hashMap, "ask_date");
						
			this.confirm_date = Const.getStrValue(hashMap, "confirm_date");
						
			this.fin_date = Const.getStrValue(hashMap, "fin_date");
						
			this.beg_time = Const.getStrValue(hashMap, "beg_time");
						
			this.staff_no = Const.getStrValue(hashMap, "staff_no");
						
			this.site_no = Const.getStrValue(hashMap, "site_no");
						
			this.agreement_id = Const.getStrValue(hashMap, "agreement_id");
						
			this.last_ord_id = Const.getStrValue(hashMap, "last_ord_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "ORD_ASK_LIST";
	}
	
}
