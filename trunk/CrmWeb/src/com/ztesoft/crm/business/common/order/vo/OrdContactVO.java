package com.ztesoft.crm.business.common.order.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class OrdContactVO extends ValueObject implements VO {
	

	private String ord_item_id;
	

	private String cust_ord_id;
	

	private String ord_id;
	

	private String action_type;
	

	private String state_date;
	

	private String end_time;
	

	private String beg_time;
	

	private String serv_contact_id;
	

	private String product_offer_instance_id;
	

	private String contact_name;
	

	private String tel;
	

	private String mobile_phone;
	

	private String fax;
	

	private String bp;
	

	private String email;
	

	private String addr;
	

	private String post_code;
	

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
	public String getServ_contact_id() {
		return serv_contact_id;
	}
	
	public void setServ_contact_id(String serv_contact_id) {
		this.serv_contact_id = serv_contact_id;
	}
	public String getProduct_offer_instance_id() {
		return product_offer_instance_id;
	}
	
	public void setProduct_offer_instance_id(String product_offer_instance_id) {
		this.product_offer_instance_id = product_offer_instance_id;
	}
	public String getContact_name() {
		return contact_name;
	}
	
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
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

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("ord_item_id",this.ord_item_id);	
				
		hashMap.put("cust_ord_id",this.cust_ord_id);	
				
		hashMap.put("ord_id",this.ord_id);	
				
		hashMap.put("action_type",this.action_type);	
				
		hashMap.put("state_date",this.state_date);	
				
		hashMap.put("end_time",this.end_time);	
				
		hashMap.put("beg_time",this.beg_time);	
				
		hashMap.put("serv_contact_id",this.serv_contact_id);	
				
		hashMap.put("product_offer_instance_id",this.product_offer_instance_id);	
				
		hashMap.put("contact_name",this.contact_name);	
				
		hashMap.put("tel",this.tel);	
				
		hashMap.put("mobile_phone",this.mobile_phone);	
				
		hashMap.put("fax",this.fax);	
				
		hashMap.put("bp",this.bp);	
				
		hashMap.put("email",this.email);	
				
		hashMap.put("addr",this.addr);	
				
		hashMap.put("post_code",this.post_code);	
				
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
						
			this.serv_contact_id = Const.getStrValue(hashMap, "serv_contact_id");
						
			this.product_offer_instance_id = Const.getStrValue(hashMap, "product_offer_instance_id");
						
			this.contact_name = Const.getStrValue(hashMap, "contact_name");
						
			this.tel = Const.getStrValue(hashMap, "tel");
						
			this.mobile_phone = Const.getStrValue(hashMap, "mobile_phone");
						
			this.fax = Const.getStrValue(hashMap, "fax");
						
			this.bp = Const.getStrValue(hashMap, "bp");
						
			this.email = Const.getStrValue(hashMap, "email");
						
			this.addr = Const.getStrValue(hashMap, "addr");
						
			this.post_code = Const.getStrValue(hashMap, "post_code");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "ORD_CONTACT";
	}
	
}
