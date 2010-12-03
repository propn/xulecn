package com.ztesoft.crm.business.common.history.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class UhRemoveCauseVO extends ValueObject implements VO {
	

	private String remove_cause_id;
	

	private String cust_ord_id;
	

	private String product_offer_instance_id;
	

	private String remove_date;
	

	private String out_cause;
	

	private String out_to;
	

	public String getRemove_cause_id() {
		return remove_cause_id;
	}
	
	public void setRemove_cause_id(String remove_cause_id) {
		this.remove_cause_id = remove_cause_id;
	}
	public String getCust_ord_id() {
		return cust_ord_id;
	}
	
	public void setCust_ord_id(String cust_ord_id) {
		this.cust_ord_id = cust_ord_id;
	}
	public String getProduct_offer_instance_id() {
		return product_offer_instance_id;
	}
	
	public void setProduct_offer_instance_id(String product_offer_instance_id) {
		this.product_offer_instance_id = product_offer_instance_id;
	}
	public String getRemove_date() {
		return remove_date;
	}
	
	public void setRemove_date(String remove_date) {
		this.remove_date = remove_date;
	}
	public String getOut_cause() {
		return out_cause;
	}
	
	public void setOut_cause(String out_cause) {
		this.out_cause = out_cause;
	}
	public String getOut_to() {
		return out_to;
	}
	
	public void setOut_to(String out_to) {
		this.out_to = out_to;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("remove_cause_id",this.remove_cause_id);	
				
		hashMap.put("cust_ord_id",this.cust_ord_id);	
				
		hashMap.put("product_offer_instance_id",this.product_offer_instance_id);	
				
		hashMap.put("remove_date",this.remove_date);	
				
		hashMap.put("out_cause",this.out_cause);	
				
		hashMap.put("out_to",this.out_to);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.remove_cause_id = Const.getStrValue(hashMap, "remove_cause_id");
						
			this.cust_ord_id = Const.getStrValue(hashMap, "cust_ord_id");
						
			this.product_offer_instance_id = Const.getStrValue(hashMap, "product_offer_instance_id");
						
			this.remove_date = Const.getStrValue(hashMap, "remove_date");
						
			this.out_cause = Const.getStrValue(hashMap, "out_cause");
						
			this.out_to = Const.getStrValue(hashMap, "out_to");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "UH_REMOVE_CAUSE";
	}
	
}
