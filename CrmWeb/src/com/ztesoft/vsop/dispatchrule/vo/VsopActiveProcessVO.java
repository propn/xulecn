package com.ztesoft.vsop.dispatchrule.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class VsopActiveProcessVO extends ValueObject implements VO {
	

	private String process_code;
	

	private String process_name;
	

	private String process_type;
	

	private String process_state;
	

	private String create_date;
	

	private String state_date;
	

	private String process_desc;
	

	public String getProcess_code() {
		return process_code;
	}
	
	public void setProcess_code(String process_code) {
		this.process_code = process_code;
	}
	public String getProcess_name() {
		return process_name;
	}
	
	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}
	public String getProcess_type() {
		return process_type;
	}
	
	public void setProcess_type(String process_type) {
		this.process_type = process_type;
	}
	public String getProcess_state() {
		return process_state;
	}
	
	public void setProcess_state(String process_state) {
		this.process_state = process_state;
	}
	public String getCreate_date() {
		return create_date;
	}
	
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getState_date() {
		return state_date;
	}
	
	public void setState_date(String state_date) {
		this.state_date = state_date;
	}
	public String getProcess_desc() {
		return process_desc;
	}
	
	public void setProcess_desc(String process_desc) {
		this.process_desc = process_desc;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("process_code",this.process_code);	
				
		hashMap.put("process_name",this.process_name);	
				
		hashMap.put("process_type",this.process_type);	
				
		hashMap.put("process_state",this.process_state);	
				
		hashMap.put("create_date",this.create_date);	
				
		hashMap.put("state_date",this.state_date);	
				
		hashMap.put("process_desc",this.process_desc);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.process_code = Const.getStrValue(hashMap, "process_code");
						
			this.process_name = Const.getStrValue(hashMap, "process_name");
						
			this.process_type = Const.getStrValue(hashMap, "process_type");
						
			this.process_state = Const.getStrValue(hashMap, "process_state");
						
			this.create_date = Const.getStrValue(hashMap, "create_date");
						
			this.state_date = Const.getStrValue(hashMap, "state_date");
						
			this.process_desc = Const.getStrValue(hashMap, "process_desc");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "VSOP_ACTIVE_PROCESS";
	}
	
}
