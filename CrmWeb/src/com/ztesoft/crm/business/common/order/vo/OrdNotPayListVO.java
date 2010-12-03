package com.ztesoft.crm.business.common.order.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class OrdNotPayListVO extends ValueObject implements VO {
	

	private String ord_id;
	

	private String ord_type;
	

	private String cust_id;
	

	private String bureau_no;
	

	private String instance_type;
	

	private String instance_id;
	

	private String price_object_type;
	

	private String price_object_id;
	

	private String object_id;
	

	private String service_offer_id;
	

	private String acct_id;
	

	private String acct_item_type_id;
	

	private String need_cash;
	

	private String type_type;
	

	private String type_adsc;
	

	private String price_id;
	

	private String base_instance_type;
	

	private String base_instance_id;
	

	private String time_limit_type;
	

	private String time_limit_vale;
	

	private String check_date;
	

	private String charge_mode;
	

	private String fact_cash;
	

	private String pay_type;
	

	private String pay_date;
	

	private String send_date;
	

	private String notes;
	

	private String fee_serialno;
	

	private String plan_serial;
	

	private String state;
	

	private String staff_no;
	

	private String site_no;
	

	public String getOrd_id() {
		return ord_id;
	}
	
	public void setOrd_id(String ord_id) {
		this.ord_id = ord_id;
	}
	public String getOrd_type() {
		return ord_type;
	}
	
	public void setOrd_type(String ord_type) {
		this.ord_type = ord_type;
	}
	public String getCust_id() {
		return cust_id;
	}
	
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getBureau_no() {
		return bureau_no;
	}
	
	public void setBureau_no(String bureau_no) {
		this.bureau_no = bureau_no;
	}
	public String getInstance_type() {
		return instance_type;
	}
	
	public void setInstance_type(String instance_type) {
		this.instance_type = instance_type;
	}
	public String getInstance_id() {
		return instance_id;
	}
	
	public void setInstance_id(String instance_id) {
		this.instance_id = instance_id;
	}
	public String getPrice_object_type() {
		return price_object_type;
	}
	
	public void setPrice_object_type(String price_object_type) {
		this.price_object_type = price_object_type;
	}
	public String getPrice_object_id() {
		return price_object_id;
	}
	
	public void setPrice_object_id(String price_object_id) {
		this.price_object_id = price_object_id;
	}
	public String getObject_id() {
		return object_id;
	}
	
	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
	public String getService_offer_id() {
		return service_offer_id;
	}
	
	public void setService_offer_id(String service_offer_id) {
		this.service_offer_id = service_offer_id;
	}
	public String getAcct_id() {
		return acct_id;
	}
	
	public void setAcct_id(String acct_id) {
		this.acct_id = acct_id;
	}
	public String getAcct_item_type_id() {
		return acct_item_type_id;
	}
	
	public void setAcct_item_type_id(String acct_item_type_id) {
		this.acct_item_type_id = acct_item_type_id;
	}
	public String getNeed_cash() {
		return need_cash;
	}
	
	public void setNeed_cash(String need_cash) {
		this.need_cash = need_cash;
	}
	public String getType_type() {
		return type_type;
	}
	
	public void setType_type(String type_type) {
		this.type_type = type_type;
	}
	public String getType_adsc() {
		return type_adsc;
	}
	
	public void setType_adsc(String type_adsc) {
		this.type_adsc = type_adsc;
	}
	public String getPrice_id() {
		return price_id;
	}
	
	public void setPrice_id(String price_id) {
		this.price_id = price_id;
	}
	public String getBase_instance_type() {
		return base_instance_type;
	}
	
	public void setBase_instance_type(String base_instance_type) {
		this.base_instance_type = base_instance_type;
	}
	public String getBase_instance_id() {
		return base_instance_id;
	}
	
	public void setBase_instance_id(String base_instance_id) {
		this.base_instance_id = base_instance_id;
	}
	public String getTime_limit_type() {
		return time_limit_type;
	}
	
	public void setTime_limit_type(String time_limit_type) {
		this.time_limit_type = time_limit_type;
	}
	public String getTime_limit_vale() {
		return time_limit_vale;
	}
	
	public void setTime_limit_vale(String time_limit_vale) {
		this.time_limit_vale = time_limit_vale;
	}
	public String getCheck_date() {
		return check_date;
	}
	
	public void setCheck_date(String check_date) {
		this.check_date = check_date;
	}
	public String getCharge_mode() {
		return charge_mode;
	}
	
	public void setCharge_mode(String charge_mode) {
		this.charge_mode = charge_mode;
	}
	public String getFact_cash() {
		return fact_cash;
	}
	
	public void setFact_cash(String fact_cash) {
		this.fact_cash = fact_cash;
	}
	public String getPay_type() {
		return pay_type;
	}
	
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_date() {
		return pay_date;
	}
	
	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}
	public String getSend_date() {
		return send_date;
	}
	
	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getFee_serialno() {
		return fee_serialno;
	}
	
	public void setFee_serialno(String fee_serialno) {
		this.fee_serialno = fee_serialno;
	}
	public String getPlan_serial() {
		return plan_serial;
	}
	
	public void setPlan_serial(String plan_serial) {
		this.plan_serial = plan_serial;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
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

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("ord_id",this.ord_id);	
				
		hashMap.put("ord_type",this.ord_type);	
				
		hashMap.put("cust_id",this.cust_id);	
				
		hashMap.put("bureau_no",this.bureau_no);	
				
		hashMap.put("instance_type",this.instance_type);	
				
		hashMap.put("instance_id",this.instance_id);	
				
		hashMap.put("price_object_type",this.price_object_type);	
				
		hashMap.put("price_object_id",this.price_object_id);	
				
		hashMap.put("object_id",this.object_id);	
				
		hashMap.put("service_offer_id",this.service_offer_id);	
				
		hashMap.put("acct_id",this.acct_id);	
				
		hashMap.put("acct_item_type_id",this.acct_item_type_id);	
				
		hashMap.put("need_cash",this.need_cash);	
				
		hashMap.put("type_type",this.type_type);	
				
		hashMap.put("type_adsc",this.type_adsc);	
				
		hashMap.put("price_id",this.price_id);	
				
		hashMap.put("base_instance_type",this.base_instance_type);	
				
		hashMap.put("base_instance_id",this.base_instance_id);	
				
		hashMap.put("time_limit_type",this.time_limit_type);	
				
		hashMap.put("time_limit_vale",this.time_limit_vale);	
				
		hashMap.put("check_date",this.check_date);	
				
		hashMap.put("charge_mode",this.charge_mode);	
				
		hashMap.put("fact_cash",this.fact_cash);	
				
		hashMap.put("pay_type",this.pay_type);	
				
		hashMap.put("pay_date",this.pay_date);	
				
		hashMap.put("send_date",this.send_date);	
				
		hashMap.put("notes",this.notes);	
				
		hashMap.put("fee_serialno",this.fee_serialno);	
				
		hashMap.put("plan_serial",this.plan_serial);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("staff_no",this.staff_no);	
				
		hashMap.put("site_no",this.site_no);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.ord_id = Const.getStrValue(hashMap, "ord_id");
						
			this.ord_type = Const.getStrValue(hashMap, "ord_type");
						
			this.cust_id = Const.getStrValue(hashMap, "cust_id");
						
			this.bureau_no = Const.getStrValue(hashMap, "bureau_no");
						
			this.instance_type = Const.getStrValue(hashMap, "instance_type");
						
			this.instance_id = Const.getStrValue(hashMap, "instance_id");
						
			this.price_object_type = Const.getStrValue(hashMap, "price_object_type");
						
			this.price_object_id = Const.getStrValue(hashMap, "price_object_id");
						
			this.object_id = Const.getStrValue(hashMap, "object_id");
						
			this.service_offer_id = Const.getStrValue(hashMap, "service_offer_id");
						
			this.acct_id = Const.getStrValue(hashMap, "acct_id");
						
			this.acct_item_type_id = Const.getStrValue(hashMap, "acct_item_type_id");
						
			this.need_cash = Const.getStrValue(hashMap, "need_cash");
						
			this.type_type = Const.getStrValue(hashMap, "type_type");
						
			this.type_adsc = Const.getStrValue(hashMap, "type_adsc");
						
			this.price_id = Const.getStrValue(hashMap, "price_id");
						
			this.base_instance_type = Const.getStrValue(hashMap, "base_instance_type");
						
			this.base_instance_id = Const.getStrValue(hashMap, "base_instance_id");
						
			this.time_limit_type = Const.getStrValue(hashMap, "time_limit_type");
						
			this.time_limit_vale = Const.getStrValue(hashMap, "time_limit_vale");
						
			this.check_date = Const.getStrValue(hashMap, "check_date");
						
			this.charge_mode = Const.getStrValue(hashMap, "charge_mode");
						
			this.fact_cash = Const.getStrValue(hashMap, "fact_cash");
						
			this.pay_type = Const.getStrValue(hashMap, "pay_type");
						
			this.pay_date = Const.getStrValue(hashMap, "pay_date");
						
			this.send_date = Const.getStrValue(hashMap, "send_date");
						
			this.notes = Const.getStrValue(hashMap, "notes");
						
			this.fee_serialno = Const.getStrValue(hashMap, "fee_serialno");
						
			this.plan_serial = Const.getStrValue(hashMap, "plan_serial");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.staff_no = Const.getStrValue(hashMap, "staff_no");
						
			this.site_no = Const.getStrValue(hashMap, "site_no");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "ORD_NOT_PAY_LIST";
	}
	
}
