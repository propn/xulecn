package com.ztesoft.crm.business.common.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Attributes implements java.io.Serializable{

	//�������AttrRestrict����
	public List attrRestrict=new ArrayList();
	
	public List getAttrRestrict(){
		  return this.attrRestrict;
	}
	
	
	public AttrRestrict getAttrByAttrId(String attrId){
		//�ж����������
		if (null == attrId || null == this.attrRestrict || this.attrRestrict.isEmpty()) {
			return null;
		}
		
		//�����б�ƥ��attrid
		for (int i = 0; i < this.attrRestrict.size(); i++) {
			AttrRestrict retattrrestrict = (AttrRestrict)this.attrRestrict.get(i);
			
			if (retattrrestrict.getAttr_id().equals(attrId)) {
				return retattrrestrict;
			}
		}
		
		return null;
	}
	
	//Ҫ��mapת��list��
	public List getAttrValuesByAttrId(String attr_id){		
		//�� attrs�б���ѭ��ȥ����ƥ���attrRestrict		
		AttrRestrict attrrestrict = getAttrByAttrId(attr_id);
		List attrlist = new ArrayList();
		
		if (null == attrrestrict) {
			return attrlist;
		}
		
		//����map�������е�value�ŵ�list��
		Map valueMap = attrrestrict.getValue_name_map();		
		Iterator keyit = valueMap.keySet().iterator();
		
		while (keyit.hasNext()) {
			String key = keyit.next().toString();
	
			String value = (String) valueMap.get(key);
			attrlist.add(value);
			}
		
		return attrlist;
		
	}
	
    //����attr_Id ȡĬ��ֵ
	public String getAttrDefaultValByAttrId(String attrId){
		AttrRestrict attrrestrict = this.getAttrByAttrId(attrId);
		
		if (null == attrrestrict) {
			return null;
		}

		return attrrestrict.getDefault_value();
	}
	
	//����field_nameȡattr_Id
	public String getAttrIdByFieldName(String fieldname){
		AttrRestrict attrrestrict = this.getAttrByFieldName(fieldname);
		
		if (null == attrrestrict) {
			return null;
		}

		return attrrestrict.getAttr_id();
	}
	
	//����fieldname�ҵ�AttrRestrict
	public AttrRestrict getAttrByFieldName(String fieldName){
		//�ж����������
		if (null == fieldName || null == this.attrRestrict || this.attrRestrict.isEmpty()) {
			return null;
		}
		
		//�����б�ƥ��attrid
		for (int i = 0; i < this.attrRestrict.size(); i++) {
			AttrRestrict retattrrestrict = (AttrRestrict)this.attrRestrict.get(i);
			
			if (retattrrestrict.getField_name().equals(fieldName)) {
				return retattrrestrict;
			}
		}
		
		return null;		
	}
	
	public String getAttrDefaultValByFieldName(String fieldName){
		//����fieldname�ҵ�AttrRestrict
		AttrRestrict retattrrestrict = getAttrByFieldName(fieldName);
		
		if (null == retattrrestrict) {
			return null;
		}
		
		return retattrrestrict.getDefault_value();
	}
	
	//��this.attrs���ҵ�����ƥ���attrRestrict����
	public List getAttrByTableName(String tableName){
		if (null == tableName || null == this.attrRestrict || this.attrRestrict.isEmpty()) {
			return null;
		}
		
		List attrs = new ArrayList();
		
		//��this.attrs���ҵ�����ƥ���attrRestrict����
		for (int i = 0; i < this.attrRestrict.size(); i++) {
			AttrRestrict attrrestrict = (AttrRestrict)this.attrRestrict.get(i);
			
			if (tableName.equalsIgnoreCase(attrrestrict.getTable_name())) {
				attrs.add(attrrestrict);
			}
		}
		
		return attrs;		
	}
	
	//�ҵ���������ֶ�
	 public List getAttrFieldNamesByTableName(String tableName){
		//��this.attrs���ҵ�����ƥ���attrRestrict����
	   List tabattrs = getAttrByTableName(tableName);
	   List fieldnamelist = new ArrayList();
	   
	   for (int i = 0; i < attrRestrict.size(); i++) {
		   AttrRestrict attrrestrict = (AttrRestrict)tabattrs.get(i);
		   
		   fieldnamelist.add(attrrestrict.getField_name());
	   }
	   
		//���ص���ֵ�ַ���
	   	return fieldnamelist;			
	}
	
}
