package com.ztesoft.crm.business.common.order.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class OrdOffInstPartyVO extends ValueObject implements VO {
	

	private String ord_item_id;
	

	private String cust_ord_id;
	

	private String ord_id;
	

	private String action_type;
	

	private String state_date;
	

	private String end_time;
	

	private String beg_time;
	

	private String serv_id;
	

	private String rel_type;
	

	private String rel_id;
	

	private String party_org_id;
	

	private String party_id;
	

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
	public String getServ_id() {
		return serv_id;
	}
	
	public void setServ_id(String serv_id) {
		this.serv_id = serv_id;
	}
	public String getRel_type() {
		return rel_type;
	}
	
	public void setRel_type(String rel_type) {
		this.rel_type = rel_type;
	}
	public String getRel_id() {
		return rel_id;
	}
	
	public void setRel_id(String rel_id) {
		this.rel_id = rel_id;
	}
	public String getParty_org_id() {
		return party_org_id;
	}
	
	public void setParty_org_id(String party_org_id) {
		this.party_org_id = party_org_id;
	}
	public String getParty_id() {
		return party_id;
	}
	
	public void setParty_id(String party_id) {
		this.party_id = party_id;
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
				
		hashMap.put("action_type",this.action_type);	
				
		hashMap.put("state_date",this.state_date);	
				
		hashMap.put("end_time",this.end_time);	
				
		hashMap.put("beg_time",this.beg_time);	
				
		hashMap.put("serv_id",this.serv_id);	
				
		hashMap.put("rel_type",this.rel_type);	
				
		hashMap.put("rel_id",this.rel_id);	
				
		hashMap.put("party_org_id",this.party_org_id);	
				
		hashMap.put("party_id",this.party_id);	
				
		hashMap.put("eff_date",this.eff_date);	
				
		hashMap.put("exp_date",this.exp_date);	
				
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
						
			this.serv_id = Const.getStrValue(hashMap, "serv_id");
						
			this.rel_type = Const.getStrValue(hashMap, "rel_type");
						
			this.rel_id = Const.getStrValue(hashMap, "rel_id");
						
			this.party_org_id = Const.getStrValue(hashMap, "party_org_id");
						
			this.party_id = Const.getStrValue(hashMap, "party_id");
						
			this.eff_date = Const.getStrValue(hashMap, "eff_date");
						
			this.exp_date = Const.getStrValue(hashMap, "exp_date");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "ORD_OFFER_INST_PARTY";
	}
	
}
