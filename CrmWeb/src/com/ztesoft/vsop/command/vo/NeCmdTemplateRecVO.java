package com.ztesoft.vsop.command.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeCmdTemplateRecVO extends ValueObject implements VO {
	

	private String template_id;
	

	private String record_id;
	

	public String getTemplate_id() {
		return template_id;
	}
	
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getRecord_id() {
		return record_id;
	}
	
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("template_id",this.template_id);	
				
		hashMap.put("record_id",this.record_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.template_id = Const.getStrValue(hashMap, "template_id");
						
			this.record_id = Const.getStrValue(hashMap, "record_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_CMD_TEMPLATE_REC";
	}
	
}
