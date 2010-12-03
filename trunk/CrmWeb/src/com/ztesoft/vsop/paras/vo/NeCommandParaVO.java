package com.ztesoft.vsop.paras.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeCommandParaVO extends ValueObject implements VO {
	

	private String command_id;
	

	private String command_catalog_id;
	

	private String tran_rule_id;
	

	private String name;
	

	private String para_code;
	

	private String get_method;
	

	private String para_id;
	

	private String para_type;
	

	private String cmd_regexp;
	

	private String default_value;
	

	private String node_path;
	

	private String node_attr;
	

	private String is_key;
	
	private String paraname;
	
	private String rulename;
	
	private String catalogname;

	public String getCatalogname() {
		return catalogname;
	}

	public void setCatalogname(String catalogname) {
		this.catalogname = catalogname;
	}

	public String getParaname() {
		return paraname;
	}

	public void setParaname(String paraname) {
		this.paraname = paraname;
	}

	public String getRulename() {
		return rulename;
	}

	public void setRulename(String rulename) {
		this.rulename = rulename;
	}

	public String getCommand_id() {
		return command_id;
	}
	
	public void setCommand_id(String command_id) {
		this.command_id = command_id;
	}
	public String getCommand_catalog_id() {
		return command_catalog_id;
	}
	
	public void setCommand_catalog_id(String command_catalog_id) {
		this.command_catalog_id = command_catalog_id;
	}
	public String getTran_rule_id() {
		return tran_rule_id;
	}
	
	public void setTran_rule_id(String tran_rule_id) {
		this.tran_rule_id = tran_rule_id;
	}
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
	public String getPara_id() {
		return para_id;
	}
	
	public void setPara_id(String para_id) {
		this.para_id = para_id;
	}
	public String getPara_type() {
		return para_type;
	}
	
	public void setPara_type(String para_type) {
		this.para_type = para_type;
	}
	public String getCmd_regexp() {
		return cmd_regexp;
	}
	
	public void setCmd_regexp(String cmd_regexp) {
		this.cmd_regexp = cmd_regexp;
	}
	public String getDefault_value() {
		return default_value;
	}
	
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
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
	public String getIs_key() {
		return is_key;
	}
	
	public void setIs_key(String is_key) {
		this.is_key = is_key;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("command_id",this.command_id);	
				
		hashMap.put("command_catalog_id",this.command_catalog_id);	
				
		hashMap.put("tran_rule_id",this.tran_rule_id);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("para_code",this.para_code);	
				
		hashMap.put("get_method",this.get_method);	
				
		hashMap.put("para_id",this.para_id);	
				
		hashMap.put("para_type",this.para_type);	
				
		hashMap.put("cmd_regexp",this.cmd_regexp);	
				
		hashMap.put("default_value",this.default_value);	
				
		hashMap.put("node_path",this.node_path);	
				
		hashMap.put("node_attr",this.node_attr);	
				
		hashMap.put("is_key",this.is_key);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.command_id = Const.getStrValue(hashMap, "command_id");
						
			this.command_catalog_id = Const.getStrValue(hashMap, "command_catalog_id");
						
			this.tran_rule_id = Const.getStrValue(hashMap, "tran_rule_id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.para_code = Const.getStrValue(hashMap, "para_code");
						
			this.get_method = Const.getStrValue(hashMap, "get_method");
						
			this.para_id = Const.getStrValue(hashMap, "para_id");
						
			this.para_type = Const.getStrValue(hashMap, "para_type");
						
			this.cmd_regexp = Const.getStrValue(hashMap, "cmd_regexp");
						
			this.default_value = Const.getStrValue(hashMap, "default_value");
						
			this.node_path = Const.getStrValue(hashMap, "node_path");
						
			this.node_attr = Const.getStrValue(hashMap, "node_attr");
						
			this.is_key = Const.getStrValue(hashMap, "is_key");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_COMMAND_PARA";
	}
	
}
