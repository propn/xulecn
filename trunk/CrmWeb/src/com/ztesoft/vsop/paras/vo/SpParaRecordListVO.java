package com.ztesoft.vsop.paras.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class SpParaRecordListVO extends ValueObject implements VO {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String record_id;
	

	private String command_id;
	private String name;
	private String para_code;
	private String get_method;
	private String is_key;
	private String node_path;
	private String node_attr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPara_code() {
		return para_code;
	}

	public void setPara_code(String para_code) {
		this.para_code = para_code;
	}

	public String getGet_method() {
		return get_method;
	}

	public void setGet_method(String get_method) {
		this.get_method = get_method;
	}

	public String getIs_key() {
		return is_key;
	}

	public void setIs_key(String is_key) {
		this.is_key = is_key;
	}

	public String getNode_path() {
		return node_path;
	}

	public void setNode_path(String node_path) {
		this.node_path = node_path;
	}

	public String getNode_attr() {
		return node_attr;
	}

	public void setNode_attr(String node_attr) {
		this.node_attr = node_attr;
	}

	public String getRecord_id() {
		return record_id;
	}
	
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
	public String getCommand_id() {
		return command_id;
	}
	
	public void setCommand_id(String command_id) {
		this.command_id = command_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("record_id",this.record_id);	
				
		hashMap.put("command_id",this.command_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.record_id = Const.getStrValue(hashMap, "record_id");
						
			this.command_id = Const.getStrValue(hashMap, "command_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "SP_PARA_RECORD_LIST";
	}
	
}
