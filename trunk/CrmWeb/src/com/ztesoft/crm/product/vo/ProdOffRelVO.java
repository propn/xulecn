package com.ztesoft.crm.product.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class ProdOffRelVO extends ValueObject implements VO {
	

	private String prod_offer_rel_id;
	

	private String offer_a_id;
	

	private String offer_z_id;
	

	private String relation_type_id;
	

	private String eff_date;
	

	private String exp_date;
	
	private String offer_name_a;
	
	private String offer_name_z;
	
	public String getOffer_name_a() {
		return offer_name_a;
	}

	public void setOffer_name_a(String offer_name_a) {
		this.offer_name_a = offer_name_a;
	}

	public String getOffer_name_z() {
		return offer_name_z;
	}

	public void setOffer_name_z(String offer_name_z) {
		this.offer_name_z = offer_name_z;
	}

	public String getProd_offer_rel_id() {
		return prod_offer_rel_id;
	}
	
	public void setProd_offer_rel_id(String prod_offer_rel_id) {
		this.prod_offer_rel_id = prod_offer_rel_id;
	}
	public String getOffer_a_id() {
		return offer_a_id;
	}
	
	public void setOffer_a_id(String offer_a_id) {
		this.offer_a_id = offer_a_id;
	}
	public String getOffer_z_id() {
		return offer_z_id;
	}
	
	public void setOffer_z_id(String offer_z_id) {
		this.offer_z_id = offer_z_id;
	}
	public String getRelation_type_id() {
		return relation_type_id;
	}
	
	public void setRelation_type_id(String relation_type_id) {
		this.relation_type_id = relation_type_id;
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
				
		hashMap.put("prod_offer_rel_id",this.prod_offer_rel_id);	
				
		hashMap.put("offer_a_id",this.offer_a_id);	
				
		hashMap.put("offer_z_id",this.offer_z_id);	
				
		hashMap.put("relation_type_id",this.relation_type_id);	
				
		hashMap.put("eff_date",this.eff_date);	
				
		hashMap.put("exp_date",this.exp_date);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.prod_offer_rel_id = Const.getStrValue(hashMap, "prod_offer_rel_id");
						
			this.offer_a_id = Const.getStrValue(hashMap, "offer_a_id");
						
			this.offer_z_id = Const.getStrValue(hashMap, "offer_z_id");
						
			this.relation_type_id = Const.getStrValue(hashMap, "relation_type_id");
						
			this.eff_date = Const.getStrValue(hashMap, "eff_date");
						
			this.exp_date = Const.getStrValue(hashMap, "exp_date");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "PROD_OFFER_REL";
	}
	
}
