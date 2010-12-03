package com.ztesoft.vsop.simulate.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class SiAccpetVO extends ValueObject implements VO {
	

	private String app_id;
	

	private String create_time;
	

	private String product_no;
	

	private String prod_type;
	

	private String user_state;
	

	private String old_no;
	

	private String auth_state;
	

	private String app_type;
	

	private String result_code;
	

	private String result_msg;
	

	private String result_date;
	

	public String getApp_id() {
		return app_id;
	}
	
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getProduct_no() {
		return product_no;
	}
	
	public void setProduct_no(String product_no) {
		this.product_no = product_no;
	}
	public String getProd_type() {
		return prod_type;
	}
	
	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}
	public String getUser_state() {
		return user_state;
	}
	
	public void setUser_state(String user_state) {
		this.user_state = user_state;
	}
	public String getOld_no() {
		return old_no;
	}
	
	public void setOld_no(String old_no) {
		this.old_no = old_no;
	}
	public String getAuth_state() {
		return auth_state;
	}
	
	public void setAuth_state(String auth_state) {
		this.auth_state = auth_state;
	}
	public String getApp_type() {
		return app_type;
	}
	
	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}
	public String getResult_code() {
		return result_code;
	}
	
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getResult_msg() {
		return result_msg;
	}
	
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	public String getResult_date() {
		return result_date;
	}
	
	public void setResult_date(String result_date) {
		this.result_date = result_date;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("app_id",this.app_id);	
				
		hashMap.put("create_time",this.create_time);	
				
		hashMap.put("product_no",this.product_no);	
				
		hashMap.put("prod_type",this.prod_type);	
				
		hashMap.put("user_state",this.user_state);	
				
		hashMap.put("old_no",this.old_no);	
				
		hashMap.put("auth_state",this.auth_state);	
				
		hashMap.put("app_type",this.app_type);	
				
		hashMap.put("result_code",this.result_code);	
				
		hashMap.put("result_msg",this.result_msg);	
				
		hashMap.put("result_date",this.result_date);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.app_id = Const.getStrValue(hashMap, "app_id");
						
			this.create_time = Const.getStrValue(hashMap, "create_time");
						
			this.product_no = Const.getStrValue(hashMap, "product_no");
						
			this.prod_type = Const.getStrValue(hashMap, "prod_type");
						
			this.user_state = Const.getStrValue(hashMap, "user_state");
						
			this.old_no = Const.getStrValue(hashMap, "old_no");
						
			this.auth_state = Const.getStrValue(hashMap, "auth_state");
						
			this.app_type = Const.getStrValue(hashMap, "app_type");
						
			this.result_code = Const.getStrValue(hashMap, "result_code");
						
			this.result_msg = Const.getStrValue(hashMap, "result_msg");
						
			this.result_date = Const.getStrValue(hashMap, "result_date");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "SI_ACCPET";
	}
	
}
