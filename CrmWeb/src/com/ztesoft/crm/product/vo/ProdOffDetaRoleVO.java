package com.ztesoft.crm.product.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class ProdOffDetaRoleVO extends ValueObject implements VO {
	

	private String prod_offer_role_cd;
	

	private String role_name;
	

	private String up_prod_offer_role_cd;
	

	private String prod_offer_id;
	

	private String role_down_num;
	

	private String role_up_num;
	

	private String state;
	

	private String create_date;
	

	private String state_date;
	

	private String rule_type;
	

	private String min_count;
	

	private String max_count;
	

	private String product_id;
	
	private String product_name;

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProd_offer_role_cd() {
		return prod_offer_role_cd;
	}
	
	public void setProd_offer_role_cd(String prod_offer_role_cd) {
		this.prod_offer_role_cd = prod_offer_role_cd;
	}
	public String getRole_name() {
		return role_name;
	}
	
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getUp_prod_offer_role_cd() {
		return up_prod_offer_role_cd;
	}
	
	public void setUp_prod_offer_role_cd(String up_prod_offer_role_cd) {
		this.up_prod_offer_role_cd = up_prod_offer_role_cd;
	}
	public String getProd_offer_id() {
		return prod_offer_id;
	}
	
	public void setProd_offer_id(String prod_offer_id) {
		this.prod_offer_id = prod_offer_id;
	}
	public String getRole_down_num() {
		return role_down_num;
	}
	
	public void setRole_down_num(String role_down_num) {
		this.role_down_num = role_down_num;
	}
	public String getRole_up_num() {
		return role_up_num;
	}
	
	public void setRole_up_num(String role_up_num) {
		this.role_up_num = role_up_num;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getCreate_date() {
		return create_date;
	}
	
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getState_date() {
		return state_date;
	}
	
	public void setState_date(String state_date) {
		this.state_date = state_date;
	}
	public String getRule_type() {
		return rule_type;
	}
	
	public void setRule_type(String rule_type) {
		this.rule_type = rule_type;
	}
	public String getMin_count() {
		return min_count;
	}
	
	public void setMin_count(String min_count) {
		this.min_count = min_count;
	}
	public String getMax_count() {
		return max_count;
	}
	
	public void setMax_count(String max_count) {
		this.max_count = max_count;
	}
	public String getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("prod_offer_role_cd",this.prod_offer_role_cd);	
				
		hashMap.put("role_name",this.role_name);	
				
		hashMap.put("up_prod_offer_role_cd",this.up_prod_offer_role_cd);	
				
		hashMap.put("prod_offer_id",this.prod_offer_id);	
				
		hashMap.put("role_down_num",this.role_down_num);	
				
		hashMap.put("role_up_num",this.role_up_num);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("create_date",this.create_date);	
				
		hashMap.put("state_date",this.state_date);	
				
		hashMap.put("rule_type",this.rule_type);	
				
		hashMap.put("min_count",this.min_count);	
				
		hashMap.put("max_count",this.max_count);	
				
		hashMap.put("product_id",this.product_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.prod_offer_role_cd = Const.getStrValue(hashMap, "prod_offer_role_cd");
						
			this.role_name = Const.getStrValue(hashMap, "role_name");
						
			this.up_prod_offer_role_cd = Const.getStrValue(hashMap, "up_prod_offer_role_cd");
						
			this.prod_offer_id = Const.getStrValue(hashMap, "prod_offer_id");
						
			this.role_down_num = Const.getStrValue(hashMap, "role_down_num");
						
			this.role_up_num = Const.getStrValue(hashMap, "role_up_num");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.create_date = Const.getStrValue(hashMap, "create_date");
						
			this.state_date = Const.getStrValue(hashMap, "state_date");
						
			this.rule_type = Const.getStrValue(hashMap, "rule_type");
						
			this.min_count = Const.getStrValue(hashMap, "min_count");
						
			this.max_count = Const.getStrValue(hashMap, "max_count");
						
			this.product_id = Const.getStrValue(hashMap, "product_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "PROD_OFFER_DETAIL_ROLE";
	}
	
}
