package com.ztesoft.crm.business.common.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class AttrRestrict implements java.io.Serializable{	
    // 属性标识
	private String attr_id = null;

	// 字段名称
	private String field_name = null;

	// 表名
	private String table_name = null;

	// 属性名称
	private String attr_name = null;

	// 属性编码
	private String attr_code = null;

	// 属性长度
	private String attr_length = null;

	// 是否允许为空
	private String is_null = null;

	// 是否需要校验
	private String is_check = null;

	// 是否可编辑
	private String is_edit = null;

	// 是否显示
	private String is_make = null;

	// 校验提示信息
	private String check_message = null;

	// 长度
	private String colspan = null;

	// 属性类型
	private String attr_value_type_id = null;

	// 关联属性
	private String make_field = null;

	// 默认值
	private String default_value = null;

	// 最小值
	private String min_value = null;

	//最大值
	private String max_value = null; 
	
	//列表值 new A ,从attribute_value表中取数据 
	private Map value_name_map = new HashMap();

	public String getAttr_code() {
		
		return attr_code;
	}

	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}

	public String getAttr_id() {
		return attr_id;
	}

	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}

	public String getAttr_length() {
		return attr_length;
	}

	public void setAttr_length(String attr_length) {
		this.attr_length = attr_length;
	}

	public String getAttr_name() {
		return attr_name;
	}

	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}

	public String getAttr_value_type_id() {
		return attr_value_type_id;
	}

	public void setAttr_value_type_id(String attr_value_type_id) {
		this.attr_value_type_id = attr_value_type_id;
	}

	public String getCheck_message() {
		return check_message;
	}

	public void setCheck_message(String check_message) {
		this.check_message = check_message;
	}

	public String getColspan() {
		return colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getIs_check() {
		return is_check;
	}

	public void setIs_check(String is_check) {
		this.is_check = is_check;
	}

	public String getIs_edit() {
		return is_edit;
	}

	public void setIs_edit(String is_edit) {
		this.is_edit = is_edit;
	}

	public String getIs_make() {
		return is_make;
	}

	public void setIs_make(String is_make) {
		this.is_make = is_make;
	}

	public String getIs_null() {
		return is_null;
	}

	public void setIs_null(String is_null) {
		this.is_null = is_null;
	}

	public String getMake_field() {
		return make_field;
	}

	public void setMake_field(String make_field) {
		this.make_field = make_field;
	}

	public String getMax_value() {
		return max_value;
	}

	public void setMax_value(String max_value) {
		this.max_value = max_value;
	}

	public String getMin_value() {
		return min_value;
	}

	public void setMin_value(String min_value) {
		this.min_value = min_value;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public Map getValue_name_map() {
		return value_name_map;
	}

	public void setValue_name_map(Map value_name_map) {
		this.value_name_map = value_name_map;
	} 
	
	
	public void loadFromMap(Map map) {
		this.attr_id = (String)map.get("attr_id"); 
		this.field_name = (String)map.get("field_name");
		this.table_name = (String)map.get("table_name");
		this.attr_name = (String)map.get("attr_name"); 
		this.attr_code = (String)map.get("attr_code"); 
		this.attr_length = (String)map.get("attr_length");
		this.is_null = (String)map.get("is_null");  
		this.is_check = (String)map.get("is_check"); 
		this.is_edit = (String)map.get("is_edit"); 
		this.is_make = (String)map.get("is_make"); 
		this.check_message = (String)map.get("check_message");
		this.colspan = (String)map.get("colspan"); 
		this.attr_value_type_id = (String)map.get("attr_value_type_id");
		this.make_field = (String)map.get("make_field"); 
		this.default_value = (String)map.get("default_value");
		this.min_value = (String)map.get("min_value"); 
		this.max_value = (String)map.get("max_value");
	}
	
	public Map isMeet(String value){

		String check = "true";
		Map checkMap = new HashMap();
		
		if(value==null||"".equals(value)){
			checkMap.put("check", "false");
			checkMap.put("defvalue", this.getDefault_value());
			return checkMap;
		}
			
			
			
		String maxValue = this.getMax_value();
		String minValue = this.getMin_value();
		String defValue = this.getDefault_value();
		/********判断是否满足最大最小值约束**********/
		if(maxValue!=null&&!"".equals(maxValue)){
			if(Integer.parseInt(value)>Integer.parseInt(maxValue)){
				if(defValue==null||"".equals(defValue)){
					checkMap.put("defvalue", maxValue);
				}else{
					checkMap.put("defvalue", defValue);
				}
				checkMap.put("check", "false");
				return checkMap;
			}
		}
		if(minValue!=null&&!"".equals(minValue)){
			if(Integer.parseInt(value)<Integer.parseInt(minValue)){
				if(defValue==null||"".equals(defValue)){
					checkMap.put("defvalue", minValue);
				}else{
					checkMap.put("defvalue", defValue);
				}
				checkMap.put("check", "false");
				return checkMap;
			}
		}
		/***************判断是否在值列表中***********/
		if(value_name_map!=null&&!value_name_map.isEmpty()){
			if(!value_name_map.containsValue(value)){//如果不在其中 取默认值
				Iterator entryIt=value_name_map.entrySet().iterator();
				if(defValue==null||"".equals(defValue)){//如果默认值为空 那么从值列表中随机取一个
					while(entryIt.hasNext()){
							Entry entry=(Entry)entryIt.next();
							if(entry.getValue()!=null&&!"".equals(entry.getValue())){
								defValue = (String)entry.getValue();
								break;
							}
						}
					checkMap.put("defvalue", defValue);
				}else{
					checkMap.put("defvalue", defValue);
				}
				//checkMap.put("check", false);
				return checkMap;
			}
		}
		checkMap.put("check", check);
		return checkMap;
	}
	
	public Object clone() {
		AttrRestrict cloneObj = null;
		ObjectOutputStream oo = null;
		ObjectInputStream oi = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(out);
			oo.writeObject(this);
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			oi = new ObjectInputStream(in);
			cloneObj = (AttrRestrict) oi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(oo!=null){
				try {
					oo.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(oi!=null){
				try {
					oi.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return cloneObj;
	}
	
}
