package com.ztesoft.vsop.ordermonitor.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoNeOrderHisVO extends ValueObject implements VO {
	

	private String ne_order_id;
	

	private String order_id;
	

	private String state_code;
	

	private String device_id;
	

	private String command_collect_id;
	

	private String rela_ne_order_id;
	

	private String execute_time;
	

	private String create_date;
	

	private String finish_date;
	

	private String alert_date;
	

	private String finish_limit;
	

	private String workitem_id;
	

	private String cmd_content;
	

	private String is_success;
	

	private String result_comment;
	

	private String sub_order_type_id;
	

	public String getNe_order_id() {
		return ne_order_id;
	}
	
	public void setNe_order_id(String ne_order_id) {
		this.ne_order_id = ne_order_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getState_code() {
		return state_code;
	}
	
	public void setState_code(String state_code) {
		this.state_code = state_code;
	}
	public String getDevice_id() {
		return device_id;
	}
	
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getCommand_collect_id() {
		return command_collect_id;
	}
	
	public void setCommand_collect_id(String command_collect_id) {
		this.command_collect_id = command_collect_id;
	}
	public String getRela_ne_order_id() {
		return rela_ne_order_id;
	}
	
	public void setRela_ne_order_id(String rela_ne_order_id) {
		this.rela_ne_order_id = rela_ne_order_id;
	}
	public String getExecute_time() {
		return execute_time;
	}
	
	public void setExecute_time(String execute_time) {
		this.execute_time = execute_time;
	}
	public String getCreate_date() {
		return create_date;
	}
	
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getFinish_date() {
		return finish_date;
	}
	
	public void setFinish_date(String finish_date) {
		this.finish_date = finish_date;
	}
	public String getAlert_date() {
		return alert_date;
	}
	
	public void setAlert_date(String alert_date) {
		this.alert_date = alert_date;
	}
	public String getFinish_limit() {
		return finish_limit;
	}
	
	public void setFinish_limit(String finish_limit) {
		this.finish_limit = finish_limit;
	}
	public String getWorkitem_id() {
		return workitem_id;
	}
	
	public void setWorkitem_id(String workitem_id) {
		this.workitem_id = workitem_id;
	}
	public String getCmd_content() {
		return cmd_content;
	}
	
	public void setCmd_content(String cmd_content) {
		this.cmd_content = cmd_content;
	}
	public String getIs_success() {
		return is_success;
	}
	
	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}
	public String getResult_comment() {
		return result_comment;
	}
	
	public void setResult_comment(String result_comment) {
		this.result_comment = result_comment;
	}
	public String getSub_order_type_id() {
		return sub_order_type_id;
	}
	
	public void setSub_order_type_id(String sub_order_type_id) {
		this.sub_order_type_id = sub_order_type_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("ne_order_id",this.ne_order_id);	
				
		hashMap.put("order_id",this.order_id);	
				
		hashMap.put("state_code",this.state_code);	
				
		hashMap.put("device_id",this.device_id);	
				
		hashMap.put("command_collect_id",this.command_collect_id);	
				
		hashMap.put("rela_ne_order_id",this.rela_ne_order_id);	
				
		hashMap.put("execute_time",this.execute_time);	
				
		hashMap.put("create_date",this.create_date);	
				
		hashMap.put("finish_date",this.finish_date);	
				
		hashMap.put("alert_date",this.alert_date);	
				
		hashMap.put("finish_limit",this.finish_limit);	
				
		hashMap.put("workitem_id",this.workitem_id);	
				
		hashMap.put("cmd_content",this.cmd_content);	
				
		hashMap.put("is_success",this.is_success);	
				
		hashMap.put("result_comment",this.result_comment);	
				
		hashMap.put("sub_order_type_id",this.sub_order_type_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.ne_order_id = Const.getStrValue(hashMap, "ne_order_id");
						
			this.order_id = Const.getStrValue(hashMap, "order_id");
						
			this.state_code = Const.getStrValue(hashMap, "state_code");
						
			this.device_id = Const.getStrValue(hashMap, "device_id");
						
			this.command_collect_id = Const.getStrValue(hashMap, "command_collect_id");
						
			this.rela_ne_order_id = Const.getStrValue(hashMap, "rela_ne_order_id");
						
			this.execute_time = Const.getStrValue(hashMap, "execute_time");
						
			this.create_date = Const.getStrValue(hashMap, "create_date");
						
			this.finish_date = Const.getStrValue(hashMap, "finish_date");
						
			this.alert_date = Const.getStrValue(hashMap, "alert_date");
						
			this.finish_limit = Const.getStrValue(hashMap, "finish_limit");
						
			this.workitem_id = Const.getStrValue(hashMap, "workitem_id");
						
			this.cmd_content = Const.getStrValue(hashMap, "cmd_content");
						
			this.is_success = Const.getStrValue(hashMap, "is_success");
						
			this.result_comment = Const.getStrValue(hashMap, "result_comment");
						
			this.sub_order_type_id = Const.getStrValue(hashMap, "sub_order_type_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_NE_ORDER_HIS";
	}
	
}
