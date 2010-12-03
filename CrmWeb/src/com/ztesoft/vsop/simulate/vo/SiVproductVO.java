package com.ztesoft.vsop.simulate.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class SiVproductVO extends ValueObject implements VO {
	

	private String id;
	

	private String app_id;
	

	private String vproduct_id;
	

	private String package_id;
	

	private String ppordoffer_id;
	

	private String act_type;
	

	private String eff_time;
	

	private String exp_time;
	

	private String order_state;
	

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getApp_id() {
		return app_id;
	}
	
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getVproduct_id() {
		return vproduct_id;
	}
	
	public void setVproduct_id(String vproduct_id) {
		this.vproduct_id = vproduct_id;
	}
	public String getPackage_id() {
		return package_id;
	}
	
	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}
	public String getPpordoffer_id() {
		return ppordoffer_id;
	}
	
	public void setPpordoffer_id(String ppordoffer_id) {
		this.ppordoffer_id = ppordoffer_id;
	}
	public String getAct_type() {
		return act_type;
	}
	
	public void setAct_type(String act_type) {
		this.act_type = act_type;
	}
	public String getEff_time() {
		return eff_time;
	}
	
	public void setEff_time(String eff_time) {
		this.eff_time = eff_time;
	}
	public String getExp_time() {
		return exp_time;
	}
	
	public void setExp_time(String exp_time) {
		this.exp_time = exp_time;
	}
	public String getOrder_state() {
		return order_state;
	}
	
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("id",this.id);	
				
		hashMap.put("app_id",this.app_id);	
				
		hashMap.put("vproduct_id",this.vproduct_id);	
				
		hashMap.put("package_id",this.package_id);	
				
		hashMap.put("ppordoffer_id",this.ppordoffer_id);	
				
		hashMap.put("act_type",this.act_type);	
				
		hashMap.put("eff_time",this.eff_time);	
				
		hashMap.put("exp_time",this.exp_time);	
				
		hashMap.put("order_state",this.order_state);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.id = Const.getStrValue(hashMap, "id");
						
			this.app_id = Const.getStrValue(hashMap, "app_id");
						
			this.vproduct_id = Const.getStrValue(hashMap, "vproduct_id");
						
			this.package_id = Const.getStrValue(hashMap, "package_id");
						
			this.ppordoffer_id = Const.getStrValue(hashMap, "ppordoffer_id");
						
			this.act_type = Const.getStrValue(hashMap, "act_type");
						
			this.eff_time = Const.getStrValue(hashMap, "eff_time");
						
			this.exp_time = Const.getStrValue(hashMap, "exp_time");
						
			this.order_state = Const.getStrValue(hashMap, "order_state");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "SI_VPRODUCT";
	}
	
}
