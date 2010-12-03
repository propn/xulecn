package com.ztesoft.vsop.protocol.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeProtocolVO extends ValueObject implements VO {
	

	private String ne_protocol_id;
	

	private String business_obj_id;
	

	private String name;
	

	private String max_timeout;
	

	private String retry_times;
	

	private String need_heart_beat;
	

	private String retry_inter;
	

	private String effect_date;
	

	private String expire_date;
	

	private String comments;
	

	private String max_connect;
	

	public String getNe_protocol_id() {
		return ne_protocol_id;
	}
	
	public void setNe_protocol_id(String ne_protocol_id) {
		this.ne_protocol_id = ne_protocol_id;
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
	public String getMax_timeout() {
		return max_timeout;
	}
	
	public void setMax_timeout(String max_timeout) {
		this.max_timeout = max_timeout;
	}
	public String getRetry_times() {
		return retry_times;
	}
	
	public void setRetry_times(String retry_times) {
		this.retry_times = retry_times;
	}
	public String getNeed_heart_beat() {
		return need_heart_beat;
	}
	
	public void setNeed_heart_beat(String need_heart_beat) {
		this.need_heart_beat = need_heart_beat;
	}
	public String getRetry_inter() {
		return retry_inter;
	}
	
	public void setRetry_inter(String retry_inter) {
		this.retry_inter = retry_inter;
	}
	public String getEffect_date() {
		return effect_date;
	}
	
	public void setEffect_date(String effect_date) {
		this.effect_date = effect_date;
	}
	public String getExpire_date() {
		return expire_date;
	}
	
	public void setExpire_date(String expire_date) {
		this.expire_date = expire_date;
	}
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getMax_connect() {
		return max_connect;
	}
	
	public void setMax_connect(String max_connect) {
		this.max_connect = max_connect;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("ne_protocol_id",this.ne_protocol_id);	
				
		hashMap.put("business_obj_id",this.business_obj_id);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("max_timeout",this.max_timeout);	
				
		hashMap.put("retry_times",this.retry_times);	
				
		hashMap.put("need_heart_beat",this.need_heart_beat);	
				
		hashMap.put("retry_inter",this.retry_inter);	
				
		hashMap.put("effect_date",this.effect_date);	
				
		hashMap.put("expire_date",this.expire_date);	
				
		hashMap.put("comments",this.comments);	
				
		hashMap.put("max_connect",this.max_connect);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.ne_protocol_id = Const.getStrValue(hashMap, "ne_protocol_id");
						
			this.business_obj_id = Const.getStrValue(hashMap, "business_obj_id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.max_timeout = Const.getStrValue(hashMap, "max_timeout");
						
			this.retry_times = Const.getStrValue(hashMap, "retry_times");
						
			this.need_heart_beat = Const.getStrValue(hashMap, "need_heart_beat");
						
			this.retry_inter = Const.getStrValue(hashMap, "retry_inter");
						
			this.effect_date = Const.getStrValue(hashMap, "effect_date");
						
			this.expire_date = Const.getStrValue(hashMap, "expire_date");
						
			this.comments = Const.getStrValue(hashMap, "comments");
						
			this.max_connect = Const.getStrValue(hashMap, "max_connect");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_PROTOCOL";
	}
	
}
