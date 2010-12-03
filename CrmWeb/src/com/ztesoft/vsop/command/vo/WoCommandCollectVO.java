package com.ztesoft.vsop.command.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoCommandCollectVO extends ValueObject implements VO {
	

	private String command_collect_id;
	

	private String device_id;
	

	private String name;
	

	private String collect_seq;
	

	public String getCommand_collect_id() {
		return command_collect_id;
	}
	
	public void setCommand_collect_id(String command_collect_id) {
		this.command_collect_id = command_collect_id;
	}
	public String getDevice_id() {
		return device_id;
	}
	
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getCollect_seq() {
		return collect_seq;
	}
	
	public void setCollect_seq(String collect_seq) {
		this.collect_seq = collect_seq;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("command_collect_id",this.command_collect_id);	
				
		hashMap.put("device_id",this.device_id);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("collect_seq",this.collect_seq);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.command_collect_id = Const.getStrValue(hashMap, "command_collect_id");
						
			this.device_id = Const.getStrValue(hashMap, "device_id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.collect_seq = Const.getStrValue(hashMap, "collect_seq");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_COMMAND_COLLECT";
	}
	
}
