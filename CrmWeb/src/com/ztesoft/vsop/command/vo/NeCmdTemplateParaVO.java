package com.ztesoft.vsop.command.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeCmdTemplateParaVO extends ValueObject implements VO {
	

	private String template_id;
	

	private String command_id;
	

	private String is_key;
	

	public String getTemplate_id() {
		return template_id;
	}
	
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getCommand_id() {
		return command_id;
	}
	
	public void setCommand_id(String command_id) {
		this.command_id = command_id;
	}
	public String getIs_key() {
		return is_key;
	}
	
	public void setIs_key(String is_key) {
		this.is_key = is_key;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("template_id",this.template_id);	
				
		hashMap.put("command_id",this.command_id);	
				
		hashMap.put("is_key",this.is_key);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.template_id = Const.getStrValue(hashMap, "template_id");
						
			this.command_id = Const.getStrValue(hashMap, "command_id");
						
			this.is_key = Const.getStrValue(hashMap, "is_key");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_CMD_TEMPLATE_PARA";
	}
	
}
