package com.ztesoft.crm.business.common.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Attributes implements java.io.Serializable{

	//里面放置AttrRestrict对象
	public List attrRestrict=new ArrayList();
	
	public List getAttrRestrict(){
		  return this.attrRestrict;
	}
	
	
	public AttrRestrict getAttrByAttrId(String attrId){
		//判断入参与结果集
		if (null == attrId || null == this.attrRestrict || this.attrRestrict.isEmpty()) {
			return null;
		}
		
		//遍历列表，匹配attrid
		for (int i = 0; i < this.attrRestrict.size(); i++) {
			AttrRestrict retattrrestrict = (AttrRestrict)this.attrRestrict.get(i);
			
			if (retattrrestrict.getAttr_id().equals(attrId)) {
				return retattrrestrict;
			}
		}
		
		return null;
	}
	
	//要将map转成list？
	public List getAttrValuesByAttrId(String attr_id){		
		//从 attrs列表中循环去查找匹配的attrRestrict		
		AttrRestrict attrrestrict = getAttrByAttrId(attr_id);
		List attrlist = new ArrayList();
		
		if (null == attrrestrict) {
			return attrlist;
		}
		
		//遍历map，将所有的value放到list中
		Map valueMap = attrrestrict.getValue_name_map();		
		Iterator keyit = valueMap.keySet().iterator();
		
		while (keyit.hasNext()) {
			String key = keyit.next().toString();
	
			String value = (String) valueMap.get(key);
			attrlist.add(value);
			}
		
		return attrlist;
		
	}
	
    //根据attr_Id 取默认值
	public String getAttrDefaultValByAttrId(String attrId){
		AttrRestrict attrrestrict = this.getAttrByAttrId(attrId);
		
		if (null == attrrestrict) {
			return null;
		}

		return attrrestrict.getDefault_value();
	}
	
	//根据field_name取attr_Id
	public String getAttrIdByFieldName(String fieldname){
		AttrRestrict attrrestrict = this.getAttrByFieldName(fieldname);
		
		if (null == attrrestrict) {
			return null;
		}

		return attrrestrict.getAttr_id();
	}
	
	//根据fieldname找到AttrRestrict
	public AttrRestrict getAttrByFieldName(String fieldName){
		//判断入参与结果集
		if (null == fieldName || null == this.attrRestrict || this.attrRestrict.isEmpty()) {
			return null;
		}
		
		//遍历列表，匹配attrid
		for (int i = 0; i < this.attrRestrict.size(); i++) {
			AttrRestrict retattrrestrict = (AttrRestrict)this.attrRestrict.get(i);
			
			if (retattrrestrict.getField_name().equals(fieldName)) {
				return retattrrestrict;
			}
		}
		
		return null;		
	}
	
	public String getAttrDefaultValByFieldName(String fieldName){
		//根据fieldname找到AttrRestrict
		AttrRestrict retattrrestrict = getAttrByFieldName(fieldName);
		
		if (null == retattrrestrict) {
			return null;
		}
		
		return retattrrestrict.getDefault_value();
	}
	
	//从this.attrs中找到表名匹配的attrRestrict对象
	public List getAttrByTableName(String tableName){
		if (null == tableName || null == this.attrRestrict || this.attrRestrict.isEmpty()) {
			return null;
		}
		
		List attrs = new ArrayList();
		
		//从this.attrs中找到表名匹配的attrRestrict对象
		for (int i = 0; i < this.attrRestrict.size(); i++) {
			AttrRestrict attrrestrict = (AttrRestrict)this.attrRestrict.get(i);
			
			if (tableName.equalsIgnoreCase(attrrestrict.getTable_name())) {
				attrs.add(attrrestrict);
			}
		}
		
		return attrs;		
	}
	
	//找到表的所有字段
	 public List getAttrFieldNamesByTableName(String tableName){
		//从this.attrs中找到表名匹配的attrRestrict对象
	   List tabattrs = getAttrByTableName(tableName);
	   List fieldnamelist = new ArrayList();
	   
	   for (int i = 0; i < attrRestrict.size(); i++) {
		   AttrRestrict attrrestrict = (AttrRestrict)tabattrs.get(i);
		   
		   fieldnamelist.add(attrrestrict.getField_name());
	   }
	   
		//返回的是值字符串
	   	return fieldnamelist;			
	}
	
}
