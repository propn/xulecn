package com.ztesoft.vsop.simulate.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class SiCapabilityVO extends ValueObject implements VO {
	

	private String id;
	

	private String app_id;
	

	private String product_id;
	

	private String act_type;
	

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
	public String getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getAct_type() {
		return act_type;
	}
	
	public void setAct_type(String act_type) {
		this.act_type = act_type;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("id",this.id);	
				
		hashMap.put("app_id",this.app_id);	
				
		hashMap.put("product_id",this.product_id);	
				
		hashMap.put("act_type",this.act_type);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.id = Const.getStrValue(hashMap, "id");
						
			this.app_id = Const.getStrValue(hashMap, "app_id");
						
			this.product_id = Const.getStrValue(hashMap, "product_id");
						
			this.act_type = Const.getStrValue(hashMap, "act_type");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "SI_CAPABILITY";
	}
	
}
