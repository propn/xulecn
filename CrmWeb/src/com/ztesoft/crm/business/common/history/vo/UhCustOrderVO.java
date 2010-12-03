package com.ztesoft.crm.business.common.history.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class UhCustOrderVO extends ValueObject implements VO {
	

	private String cust_ord_id;
	

	private String cust_ord_no;
	

	private String cust_id;
	

	private String state;
	

	private String status_date;
	

	private String pre_handle_flag;
	

	private String priority;
	

	private String business_id;
	

	private String lan_id;
	

	private String staff_no;
	

	private String site_no;
	

	private String ask_source;
	

	private String ask_source_srl;
	

	public String getCust_ord_id() {
		return cust_ord_id;
	}
	
	public void setCust_ord_id(String cust_ord_id) {
		this.cust_ord_id = cust_ord_id;
	}
	public String getCust_ord_no() {
		return cust_ord_no;
	}
	
	public void setCust_ord_no(String cust_ord_no) {
		this.cust_ord_no = cust_ord_no;
	}
	public String getCust_id() {
		return cust_id;
	}
	
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getStatus_date() {
		return status_date;
	}
	
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getPre_handle_flag() {
		return pre_handle_flag;
	}
	
	public void setPre_handle_flag(String pre_handle_flag) {
		this.pre_handle_flag = pre_handle_flag;
	}
	public String getPriority() {
		return priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getBusiness_id() {
		return business_id;
	}
	
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}
	public String getLan_id() {
		return lan_id;
	}
	
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
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
	public String getAsk_source() {
		return ask_source;
	}
	
	public void setAsk_source(String ask_source) {
		this.ask_source = ask_source;
	}
	public String getAsk_source_srl() {
		return ask_source_srl;
	}
	
	public void setAsk_source_srl(String ask_source_srl) {
		this.ask_source_srl = ask_source_srl;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("cust_ord_id",this.cust_ord_id);	
				
		hashMap.put("cust_ord_no",this.cust_ord_no);	
				
		hashMap.put("cust_id",this.cust_id);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("status_date",this.status_date);	
				
		hashMap.put("pre_handle_flag",this.pre_handle_flag);	
				
		hashMap.put("priority",this.priority);	
				
		hashMap.put("business_id",this.business_id);	
				
		hashMap.put("lan_id",this.lan_id);	
				
		hashMap.put("staff_no",this.staff_no);	
				
		hashMap.put("site_no",this.site_no);	
				
		hashMap.put("ask_source",this.ask_source);	
				
		hashMap.put("ask_source_srl",this.ask_source_srl);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.cust_ord_id = Const.getStrValue(hashMap, "cust_ord_id");
						
			this.cust_ord_no = Const.getStrValue(hashMap, "cust_ord_no");
						
			this.cust_id = Const.getStrValue(hashMap, "cust_id");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.status_date = Const.getStrValue(hashMap, "status_date");
						
			this.pre_handle_flag = Const.getStrValue(hashMap, "pre_handle_flag");
						
			this.priority = Const.getStrValue(hashMap, "priority");
						
			this.business_id = Const.getStrValue(hashMap, "business_id");
						
			this.lan_id = Const.getStrValue(hashMap, "lan_id");
						
			this.staff_no = Const.getStrValue(hashMap, "staff_no");
						
			this.site_no = Const.getStrValue(hashMap, "site_no");
						
			this.ask_source = Const.getStrValue(hashMap, "ask_source");
						
			this.ask_source_srl = Const.getStrValue(hashMap, "ask_source_srl");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "UH_CUSTOMER_ORDER";
	}
	
}
