package com.ztesoft.crm.business.common.history.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class UhHandlerVO extends ValueObject implements VO {
	

	private String cust_ord_id;
	

	private String handler_name;
	

	private String handler_card_type;
	

	private String handler_card_no;
	

	private String tel;
	

	private String mobile_phone;
	

	private String fax;
	

	private String bp;
	

	private String email;
	

	private String addr;
	

	private String post_code;
	

	private String notes;
	

	public String getCust_ord_id() {
		return cust_ord_id;
	}
	
	public void setCust_ord_id(String cust_ord_id) {
		this.cust_ord_id = cust_ord_id;
	}
	public String getHandler_name() {
		return handler_name;
	}
	
	public void setHandler_name(String handler_name) {
		this.handler_name = handler_name;
	}
	public String getHandler_card_type() {
		return handler_card_type;
	}
	
	public void setHandler_card_type(String handler_card_type) {
		this.handler_card_type = handler_card_type;
	}
	public String getHandler_card_no() {
		return handler_card_no;
	}
	
	public void setHandler_card_no(String handler_card_no) {
		this.handler_card_no = handler_card_no;
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
				
		hashMap.put("cust_ord_id",this.cust_ord_id);	
				
		hashMap.put("handler_name",this.handler_name);	
				
		hashMap.put("handler_card_type",this.handler_card_type);	
				
		hashMap.put("handler_card_no",this.handler_card_no);	
				
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
						
			this.cust_ord_id = Const.getStrValue(hashMap, "cust_ord_id");
						
			this.handler_name = Const.getStrValue(hashMap, "handler_name");
						
			this.handler_card_type = Const.getStrValue(hashMap, "handler_card_type");
						
			this.handler_card_no = Const.getStrValue(hashMap, "handler_card_no");
						
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
		return "UH_HANDLER";
	}
	
}
