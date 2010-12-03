package com.ztesoft.vsop.command.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoCmdCollectMapVO extends ValueObject implements VO {
	

	private String command_collect_id;
	

	private String value;
	

	private String command_id;
	

	private String order_type_id;
	

	private String order_act_type;
	

	public String getCommand_collect_id() {
		return command_collect_id;
	}
	
	public void setCommand_collect_id(String command_collect_id) {
		this.command_collect_id = command_collect_id;
	}
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	public String getCommand_id() {
		return command_id;
	}
	
	public void setCommand_id(String command_id) {
		this.command_id = command_id;
	}
	public String getOrder_type_id() {
		return order_type_id;
	}
	
	public void setOrder_type_id(String order_type_id) {
		this.order_type_id = order_type_id;
	}
	public String getOrder_act_type() {
		return order_act_type;
	}
	
	public void setOrder_act_type(String order_act_type) {
		this.order_act_type = order_act_type;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("command_collect_id",this.command_collect_id);	
				
		hashMap.put("value",this.value);	
				
		hashMap.put("command_id",this.command_id);	
				
		hashMap.put("order_type_id",this.order_type_id);	
				
		hashMap.put("order_act_type",this.order_act_type);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.command_collect_id = Const.getStrValue(hashMap, "command_collect_id");
						
			this.value = Const.getStrValue(hashMap, "value");
						
			this.command_id = Const.getStrValue(hashMap, "command_id");
						
			this.order_type_id = Const.getStrValue(hashMap, "order_type_id");
						
			this.order_act_type = Const.getStrValue(hashMap, "order_act_type");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_CMD_COLLECT_MAP";
	}
	
}
