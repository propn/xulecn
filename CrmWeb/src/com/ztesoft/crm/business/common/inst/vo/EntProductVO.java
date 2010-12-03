package com.ztesoft.crm.business.common.inst.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class EntProductVO extends ValueObject implements VO {
	

	private String ord_item_id;
	

	private String cust_ord_id;
	

	private String ord_id;
	

	private String state_date;
	

	private String action_type;
	

	private String end_time;
	

	private String beg_time;
	

	private String staff_no;
	

	private String site_no;
	

	private String ent_product_id;
	

	private String product_id;
	

	private String mkt_res_id;
	

	private String mkt_res_inst_id;
	

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
	public String getEnt_product_id() {
		return ent_product_id;
	}
	
	public void setEnt_product_id(String ent_product_id) {
		this.ent_product_id = ent_product_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getMkt_res_id() {
		return mkt_res_id;
	}
	
	public void setMkt_res_id(String mkt_res_id) {
		this.mkt_res_id = mkt_res_id;
	}
	public String getMkt_res_inst_id() {
		return mkt_res_inst_id;
	}
	
	public void setMkt_res_inst_id(String mkt_res_inst_id) {
		this.mkt_res_inst_id = mkt_res_inst_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("ord_item_id",this.ord_item_id);	
				
		hashMap.put("cust_ord_id",this.cust_ord_id);	
				
		hashMap.put("ord_id",this.ord_id);	
				
		hashMap.put("state_date",this.state_date);	
				
		hashMap.put("action_type",this.action_type);	
				
		hashMap.put("end_time",this.end_time);	
				
		hashMap.put("beg_time",this.beg_time);	
				
		hashMap.put("staff_no",this.staff_no);	
				
		hashMap.put("site_no",this.site_no);	
				
		hashMap.put("ent_product_id",this.ent_product_id);	
				
		hashMap.put("product_id",this.product_id);	
				
		hashMap.put("mkt_res_id",this.mkt_res_id);	
				
		hashMap.put("mkt_res_inst_id",this.mkt_res_inst_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.ord_item_id = Const.getStrValue(hashMap, "ord_item_id");
						
			this.cust_ord_id = Const.getStrValue(hashMap, "cust_ord_id");
						
			this.ord_id = Const.getStrValue(hashMap, "ord_id");
						
			this.state_date = Const.getStrValue(hashMap, "state_date");
						
			this.action_type = Const.getStrValue(hashMap, "action_type");
						
			this.end_time = Const.getStrValue(hashMap, "end_time");
						
			this.beg_time = Const.getStrValue(hashMap, "beg_time");
						
			this.staff_no = Const.getStrValue(hashMap, "staff_no");
						
			this.site_no = Const.getStrValue(hashMap, "site_no");
						
			this.ent_product_id = Const.getStrValue(hashMap, "ent_product_id");
						
			this.product_id = Const.getStrValue(hashMap, "product_id");
						
			this.mkt_res_id = Const.getStrValue(hashMap, "mkt_res_id");
						
			this.mkt_res_inst_id = Const.getStrValue(hashMap, "mkt_res_inst_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "ENT_PRODUCT";
	}
	
}
