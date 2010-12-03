package com.ztesoft.vsop.command.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeCommandTemplateVO extends ValueObject implements VO {
	

	private String template_id;
	

	private String back_template_id;
	

	private String name;
	

	private String state;
	

	private String is_feedback_cmd;
	

	private String seq_command_id;
	

	private String record_id;
	

	private String template_class;
	

	private String template_type;
	

	private String template_cmd_type;
	

	private String template_content;
	

	public String getTemplate_id() {
		return template_id;
	}
	
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getBack_template_id() {
		return back_template_id;
	}
	
	public void setBack_template_id(String back_template_id) {
		this.back_template_id = back_template_id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getIs_feedback_cmd() {
		return is_feedback_cmd;
	}
	
	public void setIs_feedback_cmd(String is_feedback_cmd) {
		this.is_feedback_cmd = is_feedback_cmd;
	}
	public String getSeq_command_id() {
		return seq_command_id;
	}
	
	public void setSeq_command_id(String seq_command_id) {
		this.seq_command_id = seq_command_id;
	}
	public String getRecord_id() {
		return record_id;
	}
	
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
	public String getTemplate_class() {
		return template_class;
	}
	
	public void setTemplate_class(String template_class) {
		this.template_class = template_class;
	}
	public String getTemplate_type() {
		return template_type;
	}
	
	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}
	public String getTemplate_cmd_type() {
		return template_cmd_type;
	}
	
	public void setTemplate_cmd_type(String template_cmd_type) {
		this.template_cmd_type = template_cmd_type;
	}
	public String getTemplate_content() {
		return template_content;
	}
	
	public void setTemplate_content(String template_content) {
		this.template_content = template_content;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("template_id",this.template_id);	
				
		hashMap.put("back_template_id",this.back_template_id);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("is_feedback_cmd",this.is_feedback_cmd);	
				
		hashMap.put("seq_command_id",this.seq_command_id);	
				
		hashMap.put("record_id",this.record_id);	
				
		hashMap.put("template_class",this.template_class);	
				
		hashMap.put("template_type",this.template_type);	
				
		hashMap.put("template_cmd_type",this.template_cmd_type);	
				
		hashMap.put("template_content",this.template_content);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.template_id = Const.getStrValue(hashMap, "template_id");
						
			this.back_template_id = Const.getStrValue(hashMap, "back_template_id");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.is_feedback_cmd = Const.getStrValue(hashMap, "is_feedback_cmd");
						
			this.seq_command_id = Const.getStrValue(hashMap, "seq_command_id");
						
			this.record_id = Const.getStrValue(hashMap, "record_id");
						
			this.template_class = Const.getStrValue(hashMap, "template_class");
						
			this.template_type = Const.getStrValue(hashMap, "template_type");
						
			this.template_cmd_type = Const.getStrValue(hashMap, "template_cmd_type");
						
			this.template_content = Const.getStrValue(hashMap, "template_content");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_COMMAND_TEMPLATE";
	}
	
}
