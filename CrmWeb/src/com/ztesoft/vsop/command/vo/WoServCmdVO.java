package com.ztesoft.vsop.command.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoServCmdVO extends ValueObject implements VO {
	

	private String command_collect_id;
	

	private String template_id;
	

	private String seq;
	

	public String getCommand_collect_id() {
		return command_collect_id;
	}
	
	public void setCommand_collect_id(String command_collect_id) {
		this.command_collect_id = command_collect_id;
	}
	public String getTemplate_id() {
		return template_id;
	}
	
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getSeq() {
		return seq;
	}
	
	public void setSeq(String seq) {
		this.seq = seq;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("command_collect_id",this.command_collect_id);	
				
		hashMap.put("template_id",this.template_id);	
				
		hashMap.put("seq",this.seq);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.command_collect_id = Const.getStrValue(hashMap, "command_collect_id");
						
			this.template_id = Const.getStrValue(hashMap, "template_id");
						
			this.seq = Const.getStrValue(hashMap, "seq");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_SERVICE_CMD";
	}
	
}
