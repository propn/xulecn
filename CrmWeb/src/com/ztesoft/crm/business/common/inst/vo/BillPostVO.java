package com.ztesoft.crm.business.common.inst.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class BillPostVO extends ValueObject implements VO {
	

	private String ord_item_id;
	

	private String cust_ord_id;
	

	private String ord_id;
	

	private String action_type;
	

	private String state_date;
	

	private String end_time;
	

	private String beg_time;
	

	private String serv_id;
	

	private String bill_type;
	

	private String owner_type;
	

	private String post_method;
	

	private String post_code;
	

	private String incept_person;
	

	private String post_address;
	

	private String email;
	

	private String contact_name;
	

	private String contact_phone;
	

	private String guard_post;
	

	private String certificate_type;
	

	private String certificate_no;
	

	private String query_password;
	

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
	public String getBill_type() {
		return bill_type;
	}
	
	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}
	public String getOwner_type() {
		return owner_type;
	}
	
	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}
	public String getPost_method() {
		return post_method;
	}
	
	public void setPost_method(String post_method) {
		this.post_method = post_method;
	}
	public String getPost_code() {
		return post_code;
	}
	
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public String getIncept_person() {
		return incept_person;
	}
	
	public void setIncept_person(String incept_person) {
		this.incept_person = incept_person;
	}
	public String getPost_address() {
		return post_address;
	}
	
	public void setPost_address(String post_address) {
		this.post_address = post_address;
	}
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact_name() {
		return contact_name;
	}
	
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getGuard_post() {
		return guard_post;
	}
	
	public void setGuard_post(String guard_post) {
		this.guard_post = guard_post;
	}
	public String getCertificate_type() {
		return certificate_type;
	}
	
	public void setCertificate_type(String certificate_type) {
		this.certificate_type = certificate_type;
	}
	public String getCertificate_no() {
		return certificate_no;
	}
	
	public void setCertificate_no(String certificate_no) {
		this.certificate_no = certificate_no;
	}
	public String getQuery_password() {
		return query_password;
	}
	
	public void setQuery_password(String query_password) {
		this.query_password = query_password;
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
				
		hashMap.put("bill_type",this.bill_type);	
				
		hashMap.put("owner_type",this.owner_type);	
				
		hashMap.put("post_method",this.post_method);	
				
		hashMap.put("post_code",this.post_code);	
				
		hashMap.put("incept_person",this.incept_person);	
				
		hashMap.put("post_address",this.post_address);	
				
		hashMap.put("email",this.email);	
				
		hashMap.put("contact_name",this.contact_name);	
				
		hashMap.put("contact_phone",this.contact_phone);	
				
		hashMap.put("guard_post",this.guard_post);	
				
		hashMap.put("certificate_type",this.certificate_type);	
				
		hashMap.put("certificate_no",this.certificate_no);	
				
		hashMap.put("query_password",this.query_password);	
				
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
						
			this.bill_type = Const.getStrValue(hashMap, "bill_type");
						
			this.owner_type = Const.getStrValue(hashMap, "owner_type");
						
			this.post_method = Const.getStrValue(hashMap, "post_method");
						
			this.post_code = Const.getStrValue(hashMap, "post_code");
						
			this.incept_person = Const.getStrValue(hashMap, "incept_person");
						
			this.post_address = Const.getStrValue(hashMap, "post_address");
						
			this.email = Const.getStrValue(hashMap, "email");
						
			this.contact_name = Const.getStrValue(hashMap, "contact_name");
						
			this.contact_phone = Const.getStrValue(hashMap, "contact_phone");
						
			this.guard_post = Const.getStrValue(hashMap, "guard_post");
						
			this.certificate_type = Const.getStrValue(hashMap, "certificate_type");
						
			this.certificate_no = Const.getStrValue(hashMap, "certificate_no");
						
			this.query_password = Const.getStrValue(hashMap, "query_password");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "BILL_POST";
	}
	
}
