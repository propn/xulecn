package com.ztesoft.vsop.spManage.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class PartnerServAbilityVO extends ValueObject implements VO {
	

	private String partner_service_ability_id;
	

	private String service_code;
	

	private String partner_id;
	

	private String state;
	

	private String state_date;

	private String eff_date;
	

	private String exp_date;
	

	public String getPartner_service_ability_id() {
		return partner_service_ability_id;
	}
	
	public void setPartner_service_ability_id(String partner_service_ability_id) {
		this.partner_service_ability_id = partner_service_ability_id;
	}
	public String getService_code() {
		return service_code;
	}
	
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getPartner_id() {
		return partner_id;
	}
	
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getState_date() {
		return state_date;
	}
	
	public void setState_date(String state_date) {
		this.state_date = state_date;
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
				
		hashMap.put("partner_service_ability_id",this.partner_service_ability_id);	
				
		hashMap.put("service_code",this.service_code);	
				
		hashMap.put("partner_id",this.partner_id);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("state_date",this.state_date);	
				
		hashMap.put("eff_date",this.eff_date);	
				
		hashMap.put("exp_date",this.exp_date);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.partner_service_ability_id = Const.getStrValue(hashMap, "partner_service_ability_id");
						
			this.service_code = Const.getStrValue(hashMap, "service_code");
						
			this.partner_id = Const.getStrValue(hashMap, "partner_id");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.state_date = Const.getStrValue(hashMap, "state_date");
						
			this.eff_date = Const.getStrValue(hashMap, "eff_date");
						
			this.exp_date = Const.getStrValue(hashMap, "exp_date");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "PARTNER_SERVICE_ABILITY";
	}

	
}
