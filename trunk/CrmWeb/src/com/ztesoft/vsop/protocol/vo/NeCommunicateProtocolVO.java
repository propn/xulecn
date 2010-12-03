package com.ztesoft.vsop.protocol.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeCommunicateProtocolVO extends ValueObject implements VO {
	

	private String communicate_protocol_id;
	

	private String business_obj_id;
	

	private String name;
	

	private String cat_code;
	

	private String cat_desc;
	

	private String is_syn;
	

	private String memo;
	

	public String getCommunicate_protocol_id() {
		return communicate_protocol_id;
	}
	
	public void setCommunicate_protocol_id(String communicate_protocol_id) {
		this.communicate_protocol_id = communicate_protocol_id;
	}
	public String getBusiness_obj_id() {
		return business_obj_id;
	}
	
	public void setBusiness_obj_id(String business_obj_id) {
		this.business_obj_id = business_obj_id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getCat_code() {
		return cat_code;
	}
	
	public void setCat_code(String cat_code) {
		this.cat_code = cat_code;
	}
	public String getCat_desc() {
		return cat_desc;
	}
	
	public void setCat_desc(String cat_desc) {
		this.cat_desc = cat_desc;
	}
	public String getIs_syn() {
		return is_syn;
	}
	
	public void setIs_syn(String is_syn) {
		this.is_syn = is_syn;
	}
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("communicate_protocol_id",this.communicate_protocol_id);	
				
		hashMap.put("business_obj_id",this.business_obj_id);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("cat_code",this.cat_code);	
				
		hashMap.put("cat_desc",this.cat_desc);	
				
		hashMap.put("is_syn",this.is_syn);	
				
		hashMap.put("memo",this.memo);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.communicate_protocol_id = Const.getStrValue(hashMap, "communicate_protocol_id");
						
			this.business_obj_id = Const.getStrValue(hashMap, "business_obj_id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.cat_code = Const.getStrValue(hashMap, "cat_code");
						
			this.cat_desc = Const.getStrValue(hashMap, "cat_desc");
						
			this.is_syn = Const.getStrValue(hashMap, "is_syn");
						
			this.memo = Const.getStrValue(hashMap, "memo");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_COMMUNICATE_PROTOCOL";
	}
	
}
