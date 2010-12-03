package com.ztesoft.vsop.command.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoCmdCollectTypeVO extends ValueObject implements VO {
	

	private String cmd_collect_type_id;
	

	private String name;
	

	private String is_rule;
	

	private String device_id;
	

	public String getCmd_collect_type_id() {
		return cmd_collect_type_id;
	}
	
	public void setCmd_collect_type_id(String cmd_collect_type_id) {
		this.cmd_collect_type_id = cmd_collect_type_id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getIs_rule() {
		return is_rule;
	}
	
	public void setIs_rule(String is_rule) {
		this.is_rule = is_rule;
	}
	public String getDevice_id() {
		return device_id;
	}
	
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("cmd_collect_type_id",this.cmd_collect_type_id);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("is_rule",this.is_rule);	
				
		hashMap.put("device_id",this.device_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.cmd_collect_type_id = Const.getStrValue(hashMap, "cmd_collect_type_id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.is_rule = Const.getStrValue(hashMap, "is_rule");
						
			this.device_id = Const.getStrValue(hashMap, "device_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_CMD_COLLECT_TYPE";
	}
	
}
