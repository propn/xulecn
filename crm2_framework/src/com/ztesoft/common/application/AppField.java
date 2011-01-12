package com.ztesoft.common.application;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AppField {
//�����ֶ��������:��ԭ��java�����ֶ�����һ�������ֶ����ƣ��ֶ����ͣ��ֶη���Ȩ�ޣ��ֶγ���
private HashMap hsFieldsInfo=new HashMap();
private final String CLASS_NAME="className";
private final String FIELD_NAME="fieldName";
private final String FIELD_TYPE="fieldType";
private final String FIELD_PRIVILEGE="fieldPrivilege";
private final String FIELD_LENGTH="fieldLength";
private final String DEFAULT_VALUE="defaultValue";

public static void main(String[]args){
	String testField1=" public String a = \"1\" ";
	String testField2="         int   a   = 12345   ";
	AppField testInst=new AppField();
	try {
		testInst.setField(testField2);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println(testInst.toString());
}
public void setClassName(String clssName){
	hsFieldsInfo.put(this.CLASS_NAME, clssName);
}
public void setFieldName(String fieldName){
	hsFieldsInfo.put(this.FIELD_NAME, fieldName);
}
public void setFieldType(String fieldType){
	hsFieldsInfo.put(this.FIELD_TYPE, fieldType);
}
public void setFieldPrivilege(String fieldPrivilege){
	hsFieldsInfo.put(this.FIELD_PRIVILEGE, fieldPrivilege);
}
public void setFieldLength(String fieldLength){
	hsFieldsInfo.put(this.FIELD_LENGTH, fieldLength);
}
public void setDefaultValue(Object defaultValue){
	hsFieldsInfo.put(this.DEFAULT_VALUE, defaultValue);
}
//public void setDefaultValue(int defaultValue){
//	hsFieldsInfo.put(this.DEFAULT_VALUE, new Integer(defaultValue));
//}
public void setDefaultValue(float defaultValue){
	hsFieldsInfo.put(this.DEFAULT_VALUE, new Float(defaultValue));
}
public void setDefaultValue(long defaultValue){
	hsFieldsInfo.put(this.DEFAULT_VALUE, new Long(defaultValue));
}
public void setDefaultValue(double defaultValue){
	hsFieldsInfo.put(this.DEFAULT_VALUE, new Double(defaultValue));
}
public void setDefaultValue(boolean defaultValue){
	hsFieldsInfo.put(this.DEFAULT_VALUE, new Boolean(defaultValue));
}
public String getClassName(){
	return (String)hsFieldsInfo.get(this.CLASS_NAME);
}
public String getFieldName(){
	return (String)hsFieldsInfo.get(this.FIELD_NAME);
}
public String getFieldType(){
	return (String)hsFieldsInfo.get(this.FIELD_TYPE);
}
public String getFieldPrivilege(){
	return (String)hsFieldsInfo.get(this.FIELD_PRIVILEGE);
}
public String getFieldLength(){
	return (String)hsFieldsInfo.get(this.FIELD_LENGTH);
}
public Object getDefaultValue(){
	return hsFieldsInfo.get(this.DEFAULT_VALUE);
}

public void loadFromHashMap(HashMap vo){
	if(vo!=null && !vo.isEmpty()){
		hsFieldsInfo.put(this.CLASS_NAME, (String)vo.get("clssName"));
		hsFieldsInfo.put(this.FIELD_NAME, (String)vo.get("fieldName"));
		hsFieldsInfo.put(this.FIELD_TYPE, (String)vo.get("fieldType"));
		hsFieldsInfo.put(this.FIELD_PRIVILEGE, (String)vo.get("fieldPrivilege"));
		hsFieldsInfo.put(this.FIELD_LENGTH, (String)vo.get("fieldLength"));
		if(((String)vo.get("defaultValue")).equals("null"))
			hsFieldsInfo.put(this.DEFAULT_VALUE, null);
		else
			hsFieldsInfo.put(this.DEFAULT_VALUE, (String)vo.get("defaultValue"));
	}
}
public void setField(String fieldInfo) throws Exception{
	Complier ComplierBo=new Complier();
	this.loadFromHashMap(ComplierBo.getFieldInfo(fieldInfo));
}
public String toString(){
	StringBuffer strbPrintMes=new StringBuffer("");
	for(Iterator it=this.hsFieldsInfo.entrySet().iterator();it.hasNext();){
		Map.Entry map=(Map.Entry)it.next();
		strbPrintMes.append("[");
		strbPrintMes.append(map.getKey());
		strbPrintMes.append(":");
		strbPrintMes.append(map.getValue());
		strbPrintMes.append("]");
	}
	return strbPrintMes.toString();
}

}
