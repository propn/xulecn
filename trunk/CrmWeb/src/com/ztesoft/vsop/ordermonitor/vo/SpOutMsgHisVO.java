package com.ztesoft.vsop.ordermonitor.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class SpOutMsgHisVO extends ValueObject implements VO {
	

	private String id;
	

	private String in_time;
	

	private String msg;
	

	private String msg_id;
	

	private String sys;
	

	private String order_id;
	

	private String deal_time;
	

	private String state;
	

	private String error_content;
	

	private String prod_no;
	

	private String lan_code;
	

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getIn_time() {
		return in_time;
	}
	
	public void setIn_time(String in_time) {
		this.in_time = in_time;
	}
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg_id() {
		return msg_id;
	}
	
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getSys() {
		return sys;
	}
	
	public void setSys(String sys) {
		this.sys = sys;
	}
	public String getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getDeal_time() {
		return deal_time;
	}
	
	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getError_content() {
		return error_content;
	}
	
	public void setError_content(String error_content) {
		this.error_content = error_content;
	}
	public String getProd_no() {
		return prod_no;
	}
	
	public void setProd_no(String prod_no) {
		this.prod_no = prod_no;
	}
	public String getLan_code() {
		return lan_code;
	}
	
	public void setLan_code(String lan_code) {
		this.lan_code = lan_code;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("id",this.id);	
				
		hashMap.put("in_time",this.in_time);	
				
		hashMap.put("msg",this.msg);	
				
		hashMap.put("msg_id",this.msg_id);	
				
		hashMap.put("sys",this.sys);	
				
		hashMap.put("order_id",this.order_id);	
				
		hashMap.put("deal_time",this.deal_time);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("error_content",this.error_content);	
				
		hashMap.put("prod_no",this.prod_no);	
				
		hashMap.put("lan_code",this.lan_code);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.id = Const.getStrValue(hashMap, "id");
						
			this.in_time = Const.getStrValue(hashMap, "in_time");
						
			this.msg = Const.getStrValue(hashMap, "msg");
						
			this.msg_id = Const.getStrValue(hashMap, "msg_id");
						
			this.sys = Const.getStrValue(hashMap, "sys");
						
			this.order_id = Const.getStrValue(hashMap, "order_id");
						
			this.deal_time = Const.getStrValue(hashMap, "deal_time");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.error_content = Const.getStrValue(hashMap, "error_content");
						
			this.prod_no = Const.getStrValue(hashMap, "prod_no");
						
			this.lan_code = Const.getStrValue(hashMap, "lan_code");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "SP_OUT_MSG_HIS";
	}
	
}
