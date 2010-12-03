package com.ztesoft.vsop.paras.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class SpParaInfoVO extends ValueObject implements VO {
	

	private String para_id;
	

	private String parent_para_id;
	

	private String para_group_id;
	

	private String para_dir_id;
	

	private String int_sys_id;
	

	private String name;
	

	private String para_code;
	

	private String node_path;
	

	private String node_attr;
	

	private String comments;
	

	private String is_reflect;
	

	private String para_type;
	

	private String is_key;
	

	public String getPara_id() {
		return para_id;
	}
	
	public void setPara_id(String para_id) {
		this.para_id = para_id;
	}
	public String getParent_para_id() {
		return parent_para_id;
	}
	
	public void setParent_para_id(String parent_para_id) {
		this.parent_para_id = parent_para_id;
	}
	public String getPara_group_id() {
		return para_group_id;
	}
	
	public void setPara_group_id(String para_group_id) {
		this.para_group_id = para_group_id;
	}
	public String getPara_dir_id() {
		return para_dir_id;
	}
	
	public void setPara_dir_id(String para_dir_id) {
		this.para_dir_id = para_dir_id;
	}
	public String getInt_sys_id() {
		return int_sys_id;
	}
	
	public void setInt_sys_id(String int_sys_id) {
		this.int_sys_id = int_sys_id;
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
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getIs_reflect() {
		return is_reflect;
	}
	
	public void setIs_reflect(String is_reflect) {
		this.is_reflect = is_reflect;
	}
	public String getPara_type() {
		return para_type;
	}
	
	public void setPara_type(String para_type) {
		this.para_type = para_type;
	}
	public String getIs_key() {
		return is_key;
	}
	
	public void setIs_key(String is_key) {
		this.is_key = is_key;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("para_id",this.para_id);	
				
		hashMap.put("parent_para_id",this.parent_para_id);	
				
		hashMap.put("para_group_id",this.para_group_id);	
				
		hashMap.put("para_dir_id",this.para_dir_id);	
				
		hashMap.put("int_sys_id",this.int_sys_id);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("para_code",this.para_code);	
				
		hashMap.put("node_path",this.node_path);	
				
		hashMap.put("node_attr",this.node_attr);	
				
		hashMap.put("comments",this.comments);	
				
		hashMap.put("is_reflect",this.is_reflect);	
				
		hashMap.put("para_type",this.para_type);	
				
		hashMap.put("is_key",this.is_key);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.para_id = Const.getStrValue(hashMap, "para_id");
						
			this.parent_para_id = Const.getStrValue(hashMap, "parent_para_id");
						
			this.para_group_id = Const.getStrValue(hashMap, "para_group_id");
						
			this.para_dir_id = Const.getStrValue(hashMap, "para_dir_id");
						
			this.int_sys_id = Const.getStrValue(hashMap, "int_sys_id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.para_code = Const.getStrValue(hashMap, "para_code");
						
			this.node_path = Const.getStrValue(hashMap, "node_path");
						
			this.node_attr = Const.getStrValue(hashMap, "node_attr");
						
			this.comments = Const.getStrValue(hashMap, "comments");
						
			this.is_reflect = Const.getStrValue(hashMap, "is_reflect");
						
			this.para_type = Const.getStrValue(hashMap, "para_type");
						
			this.is_key = Const.getStrValue(hashMap, "is_key");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "SP_PARA_INFO";
	}
	
}
