package com.ztesoft.crm.business.common.inst.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class ServAcctVO extends ValueObject implements VO {
	

	private String ord_item_id;
	

	private String cust_ord_id;
	

	private String ord_id;
	

	private String action_type;
	

	private String state_date;
	

	private String end_time;
	

	private String beg_time;
	

	private String staff_no;
	

	private String site_no;
	

	private String serv_id;
	

	private String acct_item_group_id;
	

	private String acct_id;
	

	private String invoice_require_id;
	

	private String bill_require_id;
	

	private String payment_rule_id;
	

	private String self_flag;
	

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
	public String getServ_id() {
		return serv_id;
	}
	
	public void setServ_id(String serv_id) {
		this.serv_id = serv_id;
	}
	public String getAcct_item_group_id() {
		return acct_item_group_id;
	}
	
	public void setAcct_item_group_id(String acct_item_group_id) {
		this.acct_item_group_id = acct_item_group_id;
	}
	public String getAcct_id() {
		return acct_id;
	}
	
	public void setAcct_id(String acct_id) {
		this.acct_id = acct_id;
	}
	public String getInvoice_require_id() {
		return invoice_require_id;
	}
	
	public void setInvoice_require_id(String invoice_require_id) {
		this.invoice_require_id = invoice_require_id;
	}
	public String getBill_require_id() {
		return bill_require_id;
	}
	
	public void setBill_require_id(String bill_require_id) {
		this.bill_require_id = bill_require_id;
	}
	public String getPayment_rule_id() {
		return payment_rule_id;
	}
	
	public void setPayment_rule_id(String payment_rule_id) {
		this.payment_rule_id = payment_rule_id;
	}
	public String getSelf_flag() {
		return self_flag;
	}
	
	public void setSelf_flag(String self_flag) {
		this.self_flag = self_flag;
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
				
		hashMap.put("staff_no",this.staff_no);	
				
		hashMap.put("site_no",this.site_no);	
				
		hashMap.put("serv_id",this.serv_id);	
				
		hashMap.put("acct_item_group_id",this.acct_item_group_id);	
				
		hashMap.put("acct_id",this.acct_id);	
				
		hashMap.put("invoice_require_id",this.invoice_require_id);	
				
		hashMap.put("bill_require_id",this.bill_require_id);	
				
		hashMap.put("payment_rule_id",this.payment_rule_id);	
				
		hashMap.put("self_flag",this.self_flag);	
				
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
						
			this.staff_no = Const.getStrValue(hashMap, "staff_no");
						
			this.site_no = Const.getStrValue(hashMap, "site_no");
						
			this.serv_id = Const.getStrValue(hashMap, "serv_id");
						
			this.acct_item_group_id = Const.getStrValue(hashMap, "acct_item_group_id");
						
			this.acct_id = Const.getStrValue(hashMap, "acct_id");
						
			this.invoice_require_id = Const.getStrValue(hashMap, "invoice_require_id");
						
			this.bill_require_id = Const.getStrValue(hashMap, "bill_require_id");
						
			this.payment_rule_id = Const.getStrValue(hashMap, "payment_rule_id");
						
			this.self_flag = Const.getStrValue(hashMap, "self_flag");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "SERV_ACCT";
	}
	
}
