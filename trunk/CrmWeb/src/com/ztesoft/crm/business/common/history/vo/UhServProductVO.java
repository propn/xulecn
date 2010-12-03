package com.ztesoft.crm.business.common.history.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class UhServProductVO extends ValueObject implements VO {
	

	private String ord_item_id;
	

	private String cust_ord_id;
	

	private String ord_id;
	

	private String action_type;
	

	private String state_date;
	

	private String end_time;
	

	private String beg_time;
	

	private String staff_no;
	

	private String site_no;
	

	private String serv_product_id;
	

	private String serv_id;
	

	private String product_id;
	

	private String rent_date;
	

	private String r_serv_id;
	

	private String state;
	

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
	public String getServ_product_id() {
		return serv_product_id;
	}
	
	public void setServ_product_id(String serv_product_id) {
		this.serv_product_id = serv_product_id;
	}
	public String getServ_id() {
		return serv_id;
	}
	
	public void setServ_id(String serv_id) {
		this.serv_id = serv_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getRent_date() {
		return rent_date;
	}
	
	public void setRent_date(String rent_date) {
		this.rent_date = rent_date;
	}
	public String getR_serv_id() {
		return r_serv_id;
	}
	
	public void setR_serv_id(String r_serv_id) {
		this.r_serv_id = r_serv_id;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
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
				
		hashMap.put("serv_product_id",this.serv_product_id);	
				
		hashMap.put("serv_id",this.serv_id);	
				
		hashMap.put("product_id",this.product_id);	
				
		hashMap.put("rent_date",this.rent_date);	
				
		hashMap.put("r_serv_id",this.r_serv_id);	
				
		hashMap.put("state",this.state);	
				
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
						
			this.serv_product_id = Const.getStrValue(hashMap, "serv_product_id");
						
			this.serv_id = Const.getStrValue(hashMap, "serv_id");
						
			this.product_id = Const.getStrValue(hashMap, "product_id");
						
			this.rent_date = Const.getStrValue(hashMap, "rent_date");
						
			this.r_serv_id = Const.getStrValue(hashMap, "r_serv_id");
						
			this.state = Const.getStrValue(hashMap, "state");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "UH_SERV_PRODUCT";
	}
	
}
