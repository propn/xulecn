package com.ztesoft.crm.business.common.logic.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.consts.Keys;

public class Ord implements BusiObject{
	
		private Map attributes = new HashMap();		
		private String operFlag = KeyValues.ACTION_TYPE_A;
		
		
		public String getOperFlag() {
			return operFlag;
		}
		public void setOperFlag(String operFlag) {
			this.operFlag = operFlag;
		}
		public Map getAttributes() {
			return attributes;
		}
		public void setAttributes(Map attributes) {
			this.attributes = attributes;
		}
		public void set(String name, String value) {
			this.attributes.put(name, value);
		}
		public String get(String name){
			return (String)this.attributes.get(name);
		}
		
		// ͨ�÷����� ��Map�м������ݣ��������ݣ�
		public void loadFromMap(Map map) {
			attributes.putAll(map);
		}

		// ͨ�÷�������List�ܼ������ݣ������������ݣ�
		public void loadFromList(List maps) {
			for (int i = 0; i < maps.size(); i++) {
				Map map = (Map) maps.get(i);
				String key = (String) map.get(Keys.FIELD_NAME);
				String value = (String) map.get(Keys.FIELD_VALUE);
				attributes.put(key, value);
			}
		}
	

	

}
