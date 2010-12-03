package com.ztesoft.vsop.paras.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoParaCatgVO extends ValueObject implements VO {
	

	private String para_dir_id;
	

	private String parent_para_dir_id;
	

	private String dir_name;
	

	public String getPara_dir_id() {
		return para_dir_id;
	}
	
	public void setPara_dir_id(String para_dir_id) {
		this.para_dir_id = para_dir_id;
	}
	public String getParent_para_dir_id() {
		return parent_para_dir_id;
	}
	
	public void setParent_para_dir_id(String parent_para_dir_id) {
		this.parent_para_dir_id = parent_para_dir_id;
	}
	public String getDir_name() {
		return dir_name;
	}
	
	public void setDir_name(String dir_name) {
		this.dir_name = dir_name;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("para_dir_id",this.para_dir_id);	
				
		hashMap.put("parent_para_dir_id",this.parent_para_dir_id);	
				
		hashMap.put("dir_name",this.dir_name);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.para_dir_id = Const.getStrValue(hashMap, "para_dir_id");
						
			this.parent_para_dir_id = Const.getStrValue(hashMap, "parent_para_dir_id");
						
			this.dir_name = Const.getStrValue(hashMap, "dir_name");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_PARA_CATALOG";
	}
	
}
