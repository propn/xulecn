package com.ztesoft.crm.business.common.inst.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class OffInstAssureVO extends ValueObject implements VO {
	

	private String ord_id;
	

	private String cust_ord_id;
	

	private String ord_item_id;
	

	private String state_date;
	

	private String action_type;
	

	private String end_time;
	

	private String beg_time;
	

	private String guarantor_id;
	

	private String comp_inst_id;
	

	private String assure_method;
	

	private String assure_info;
	

	private String assure_name;
	

	private String card_type;
	

	private String card_no;
	

	private String tel;
	

	private String mobile_phone;
	

	private String fax;
	

	private String bp;
	

	private String email;
	

	private String addr;
	

	private String post_code;
	

	private String notes;
	

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
	public String getOrd_item_id() {
		return ord_item_id;
	}
	
	public void setOrd_item_id(String ord_item_id) {
		this.ord_item_id = ord_item_id;
	}
	public String getState_date() {
		return state_date;
	}
	
	public void setState_date(String state_date) {
		this.state_date = state_date;
	}
	public String getAction_type() {
		return action_type;
	}
	
	public void setAction_type(String action_type) {
		this.action_type = action_type;
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
	public String getGuarantor_id() {
		return guarantor_id;
	}
	
	public void setGuarantor_id(String guarantor_id) {
		this.guarantor_id = guarantor_id;
	}
	public String getComp_inst_id() {
		return comp_inst_id;
	}
	
	public void setComp_inst_id(String comp_inst_id) {
		this.comp_inst_id = comp_inst_id;
	}
	public String getAssure_method() {
		return assure_method;
	}
	
	public void setAssure_method(String assure_method) {
		this.assure_method = assure_method;
	}
	public String getAssure_info() {
		return assure_info;
	}
	
	public void setAssure_info(String assure_info) {
		this.assure_info = assure_info;
	}
	public String getAssure_name() {
		return assure_name;
	}
	
	public void setAssure_name(String assure_name) {
		this.assure_name = assure_name;
	}
	public String getCard_type() {
		return card_type;
	}
	
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getCard_no() {
		return card_no;
	}
	
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile_phone() {
		return mobile_phone;
	}
	
	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getBp() {
		return bp;
	}
	
	public void setBp(String bp) {
		this.bp = bp;
	}
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddr() {
		return addr;
	}
	
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPost_code() {
		return post_code;
	}
	
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("ord_id",this.ord_id);	
				
		hashMap.put("cust_ord_id",this.cust_ord_id);	
				
		hashMap.put("ord_item_id",this.ord_item_id);	
				
		hashMap.put("state_date",this.state_date);	
				
		hashMap.put("action_type",this.action_type);	
				
		hashMap.put("end_time",this.end_time);	
				
		hashMap.put("beg_time",this.beg_time);	
				
		hashMap.put("guarantor_id",this.guarantor_id);	
				
		hashMap.put("comp_inst_id",this.comp_inst_id);	
				
		hashMap.put("assure_method",this.assure_method);	
				
		hashMap.put("assure_info",this.assure_info);	
				
		hashMap.put("assure_name",this.assure_name);	
				
		hashMap.put("card_type",this.card_type);	
				
		hashMap.put("card_no",this.card_no);	
				
		hashMap.put("tel",this.tel);	
				
		hashMap.put("mobile_phone",this.mobile_phone);	
				
		hashMap.put("fax",this.fax);	
				
		hashMap.put("bp",this.bp);	
				
		hashMap.put("email",this.email);	
				
		hashMap.put("addr",this.addr);	
				
		hashMap.put("post_code",this.post_code);	
				
		hashMap.put("notes",this.notes);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.ord_id = Const.getStrValue(hashMap, "ord_id");
						
			this.cust_ord_id = Const.getStrValue(hashMap, "cust_ord_id");
						
			this.ord_item_id = Const.getStrValue(hashMap, "ord_item_id");
						
			this.state_date = Const.getStrValue(hashMap, "state_date");
						
			this.action_type = Const.getStrValue(hashMap, "action_type");
						
			this.end_time = Const.getStrValue(hashMap, "end_time");
						
			this.beg_time = Const.getStrValue(hashMap, "beg_time");
						
			this.guarantor_id = Const.getStrValue(hashMap, "guarantor_id");
						
			this.comp_inst_id = Const.getStrValue(hashMap, "comp_inst_id");
						
			this.assure_method = Const.getStrValue(hashMap, "assure_method");
						
			this.assure_info = Const.getStrValue(hashMap, "assure_info");
						
			this.assure_name = Const.getStrValue(hashMap, "assure_name");
						
			this.card_type = Const.getStrValue(hashMap, "card_type");
						
			this.card_no = Const.getStrValue(hashMap, "card_no");
						
			this.tel = Const.getStrValue(hashMap, "tel");
						
			this.mobile_phone = Const.getStrValue(hashMap, "mobile_phone");
						
			this.fax = Const.getStrValue(hashMap, "fax");
						
			this.bp = Const.getStrValue(hashMap, "bp");
						
			this.email = Const.getStrValue(hashMap, "email");
						
			this.addr = Const.getStrValue(hashMap, "addr");
						
			this.post_code = Const.getStrValue(hashMap, "post_code");
						
			this.notes = Const.getStrValue(hashMap, "notes");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "OFFER_INST_ASSURE";
	}
	
}
