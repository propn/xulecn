package com.ztesoft.crm.business.common.logic.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.consts.Keys;

public abstract class Inst implements BusiObject{
	// ��������,A,D,K,M,R,(װ���𣬲��䣬�ģ�����)
	// ʵ�����ԣ���������Ʒ���ԣ�������Ʒ���ԣ�����Ʒ���Ե�����
	public Map attributes = new HashMap();

	// ͨ�÷����� ��Map�м������ݣ��������ݣ�
	public void loadFromMap(Map map) {
		attributes.putAll(map);
	}

	// ͨ�÷�������List�ܼ������ݣ������������ݣ�
	public void loadFromList(List maps) {
		for (int i = 0; i < maps.size(); i++) {
			Map map = (Map) maps.get(i);
			String key = (String) map.get(Keys.FIELD_NAME);
			String value = (String) map.get(Keys.ATTR_VAL);
			attributes.put(key, value);
		}
	}

	public Map getAttributes() {
		return attributes;
	}

	public void setAttributes(Map attributes) {
		this.attributes = attributes;
	}

	public void set(String name, String value) {
		
		this.attributes.put(name, value);

		if (KeyValues.ACTION_TYPE_K.equals(get(Keys.ACTION_TYPE))) {
			this.attributes.put(Keys.ACTION_TYPE, KeyValues.ACTION_TYPE_M);
		}
			
	}

	public String get(String name) {
		return (String) this.attributes.get(name);
	}
	
	public void setActionType(String sactiontype){
		this.attributes.put(Keys.ACTION_TYPE, sactiontype);
	}

}
