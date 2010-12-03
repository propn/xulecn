package com.ztesoft.crm.business.common.logic.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.consts.Keys;

public abstract class Inst implements BusiObject{
	// 动作类型,A,D,K,M,R,(装，拆，不变，改，重置)
	// 实例属性，例如主产品属性，附属产品属性，销售品属性等数据
	public Map attributes = new HashMap();

	// 通用方法， 从Map中加载数据（基本数据）
	public void loadFromMap(Map map) {
		attributes.putAll(map);
	}

	// 通用方法，从List总加载数据（附加属性数据）
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
