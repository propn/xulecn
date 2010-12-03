package com.ztesoft.crm.business.common.order.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class OrdOffInstVO extends ValueObject implements VO {
	

	private String ord_item_id;
	

	private String cust_ord_id;
	

	private String ord_id;
	

	private String flow_id;
	

	private String action_type;
	

	private String state_date;
	

	private String end_time;
	

	private String beg_time;
	

	private String staff_no;
	

	private String site_no;
	

	private String comp_inst_id;
	

	private String product_offer_instance_id;
	

	private String cust_id;
	

	private String product_offer_id;
	

	private String offer_kind;
	

	private String price_id;
	

	private String lan_id;
	

	private String business_id;
	

	private String create_date;
	

	private String eff_date;
	

	private String exp_date;
	

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
	public String getFlow_id() {
		return flow_id;
	}
	
	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
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
	public String getComp_inst_id() {
		return comp_inst_id;
	}
	
	public void setComp_inst_id(String comp_inst_id) {
		this.comp_inst_id = comp_inst_id;
	}
	public String getProduct_offer_instance_id() {
		return product_offer_instance_id;
	}
	
	public void setProduct_offer_instance_id(String product_offer_instance_id) {
		this.product_offer_instance_id = product_offer_instance_id;
	}
	public String getCust_id() {
		return cust_id;
	}
	
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getProduct_offer_id() {
		return product_offer_id;
	}
	
	public void setProduct_offer_id(String product_offer_id) {
		this.product_offer_id = product_offer_id;
	}
	public String getOffer_kind() {
		return offer_kind;
	}
	
	public void setOffer_kind(String offer_kind) {
		this.offer_kind = offer_kind;
	}
	public String getPrice_id() {
		return price_id;
	}
	
	public void setPrice_id(String price_id) {
		this.price_id = price_id;
	}
	public String getLan_id() {
		return lan_id;
	}
	
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	public String getBusiness_id() {
		return business_id;
	}
	
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}
	public String getCreate_date() {
		return create_date;
	}
	
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getEff_date() {
		return eff_date;
	}
	
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public String getExp_date() {
		return exp_date;
	}
	
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("ord_item_id",this.ord_item_id);	
				
		hashMap.put("cust_ord_id",this.cust_ord_id);	
				
		hashMap.put("ord_id",this.ord_id);	
				
		hashMap.put("flow_id",this.flow_id);	
				
		hashMap.put("action_type",this.action_type);	
				
		hashMap.put("state_date",this.state_date);	
				
		hashMap.put("end_time",this.end_time);	
				
		hashMap.put("beg_time",this.beg_time);	
				
		hashMap.put("staff_no",this.staff_no);	
				
		hashMap.put("site_no",this.site_no);	
				
		hashMap.put("comp_inst_id",this.comp_inst_id);	
				
		hashMap.put("product_offer_instance_id",this.product_offer_instance_id);	
				
		hashMap.put("cust_id",this.cust_id);	
				
		hashMap.put("product_offer_id",this.product_offer_id);	
				
		hashMap.put("offer_kind",this.offer_kind);	
				
		hashMap.put("price_id",this.price_id);	
				
		hashMap.put("lan_id",this.lan_id);	
				
		hashMap.put("business_id",this.business_id);	
				
		hashMap.put("create_date",this.create_date);	
				
		hashMap.put("eff_date",this.eff_date);	
				
		hashMap.put("exp_date",this.exp_date);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.ord_item_id = Const.getStrValue(hashMap, "ord_item_id");
						
			this.cust_ord_id = Const.getStrValue(hashMap, "cust_ord_id");
						
			this.ord_id = Const.getStrValue(hashMap, "ord_id");
						
			this.flow_id = Const.getStrValue(hashMap, "flow_id");
						
			this.action_type = Const.getStrValue(hashMap, "action_type");
						
			this.state_date = Const.getStrValue(hashMap, "state_date");
						
			this.end_time = Const.getStrValue(hashMap, "end_time");
						
			this.beg_time = Const.getStrValue(hashMap, "beg_time");
						
			this.staff_no = Const.getStrValue(hashMap, "staff_no");
						
			this.site_no = Const.getStrValue(hashMap, "site_no");
						
			this.comp_inst_id = Const.getStrValue(hashMap, "comp_inst_id");
						
			this.product_offer_instance_id = Const.getStrValue(hashMap, "product_offer_instance_id");
						
			this.cust_id = Const.getStrValue(hashMap, "cust_id");
						
			this.product_offer_id = Const.getStrValue(hashMap, "product_offer_id");
						
			this.offer_kind = Const.getStrValue(hashMap, "offer_kind");
						
			this.price_id = Const.getStrValue(hashMap, "price_id");
						
			this.lan_id = Const.getStrValue(hashMap, "lan_id");
						
			this.business_id = Const.getStrValue(hashMap, "business_id");
						
			this.create_date = Const.getStrValue(hashMap, "create_date");
						
			this.eff_date = Const.getStrValue(hashMap, "eff_date");
						
			this.exp_date = Const.getStrValue(hashMap, "exp_date");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "ORD_OFFER_INST";
	}
	
}
