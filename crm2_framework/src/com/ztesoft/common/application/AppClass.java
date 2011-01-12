package com.ztesoft.common.application;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AppClass  {
public static void main(String[]args){
	AppClass bo=new AppClass();
	try {
		//���������:���캯���������Ϊ�յ������
		Object inst=new AppTest();
		bo.loadFromClass("com.ztesoft.common.application.AppTest", inst);
		//���������:���캯���������Ϊ�յ������,���빹�캯������
//		bo.loadFromClass("com.ztesoft.common.Application.AppTest", new Object[]{"abc"});
		//���������:���캯���������Ϊ�յ������,ֱ�Ӵ���ʵ����Ķ���
//		bo.loadFromClass("com.ztesoft.common.Application.AppTest", new AppTest());
		//���Լ��ط���������,��һ���Ǳ����ص���ͷ����������ʽ���ڶ����������������࣬������������������������
		//���һ��������������������ִ��ʵ��
		bo.addIntercs("bfprintinterc","com.ztesoft.common.application.AppTest","print", 
				"com.ztesoft.common.application.AppTest", "bfprint", inst);
		//�����ֹ����ӷ���
		//bo.addMethod("com.ztesoft.common.Application.AppTest", "interc", "myMethod");
		//����ִ�з���
		bo.executeMethod("print", new Object[]{"�ҵĳ���"});
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
//��������
private String _className="";
//�����ֶ�
private ArrayList _fields=new ArrayList();
//�����ֶ���
private HashMap _fieldsName=new HashMap();
//���巽��
private HashMap _methods=new HashMap();
//����������:key:�������ʱ�� val:��Ӧ��ʵ���ķ���
private HashMap _intercs=new HashMap();
//����������:�������ʱ��->�����ص���ķ���
private HashMap _intercsEd=new HashMap();

//��ȡʵ������ʵʵ��
public Object getInst(){
	if(_methods!=null){
		Iterator it=_methods.entrySet().iterator();
		if(it.hasNext()){
			Map.Entry map=(Map.Entry)it.next();
			AppMethod _tmpMethod=(AppMethod)map.getValue();
			return _tmpMethod.getInstance();
		}
	}
	return null;
}
public void setInst(Object obj){
	//��ȡ���еķ���
	Iterator it=_methods.entrySet().iterator();
	while(it.hasNext()){
		Map.Entry map=(Map.Entry)it.next();
		AppMethod _method=(AppMethod)map.getValue();
		_method.setInstance(obj);
	}
}
//�����������������
private void setIntercs(String interTime,String classNameEd,String methodNameEd,String className,String methodName,Object[]newInst) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	String intercId=interTime.substring(0, 2)+"_______"+classNameEd+"________"+methodNameEd;
	AppMethod _tmpMethod=new AppMethod();
	//�ж��Ƿ��Ѿ����������
	if(!this._methods.containsKey(methodName)){
		//�½�����
		_tmpMethod.setMethod(className, methodName,newInst);
		this._methods.put(methodName, _tmpMethod);//�����һ��methodName����
	}else{
		//�½�����
		_tmpMethod.setMethod(className, methodName,newInst);
		//���ܴ��ڷ�������,��ô��������Ҫ��������:methodName+��ǰʱ��
		String synMethodName=methodName+String.valueOf(System.currentTimeMillis());
		this._methods.put(synMethodName, _tmpMethod);//�����һ��������ֵķ���
	}
	//�����������ʶ
	if(this._intercs.containsKey(interTime)){//���֮ǰ�Ѿ�������������ʱ��
		ArrayList _interc=(ArrayList)_intercs.get(interTime);
		if(_interc==null){//�����null���ͽ�һ���µ�
			_interc=new ArrayList();
			this._intercs.put(interTime, _interc);
		}
		_interc.add(_tmpMethod);//������������뵽��������ʱ����ȥ
		
	}else{//���û���������ʱ��,��ô�½�һ��
		ArrayList _interc=new ArrayList();
		_interc.add(_tmpMethod);//������ط���
		this._intercs.put(interTime,_interc);//�������ʱ��
	}
	//��¼�����صķ���������ʱ���Ĺ�ϵ
	_intercsEd.put(intercId,interTime);
}

//�����������������
private void setIntercs(String interTime,String classNameEd,String methodNameEd,String className,String methodName,Object newInst) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	String intercId=interTime.substring(0, 2)+"_______"+classNameEd+"________"+methodNameEd;
	AppMethod _tmpMethod=new AppMethod();
	//�ж��Ƿ��Ѿ����������
	if(!this._methods.containsKey(methodName)){
		//�½�����
		_tmpMethod.setMethod(className, methodName,newInst);
		this._methods.put(methodName, _tmpMethod);//�����һ��methodName����
	}else{
		//�½�����
		_tmpMethod.setMethod(className, methodName,newInst);
		//���ܴ��ڷ�������,��ô��������Ҫ��������:methodName+��ǰʱ��
		String synMethodName=methodName+String.valueOf(System.currentTimeMillis());
		this._methods.put(synMethodName, _tmpMethod);//�����һ��������ֵķ���
	}
	//�����������ʶ
	if(this._intercs.containsKey(interTime)){//���֮ǰ�Ѿ�������������ʱ��
		ArrayList _interc=(ArrayList)_intercs.get(interTime);
		if(_interc==null){//�����null���ͽ�һ���µ�
			_interc=new ArrayList();
			this._intercs.put(interTime, _interc);
		}
		_interc.add(_tmpMethod);//������������뵽��������ʱ����ȥ
	}else{//���û���������ʱ��,��ô�½�һ��
		ArrayList _interc=new ArrayList();
		_interc.add(_tmpMethod);//������ط���
		this._intercs.put(interTime,_interc);//�������ʱ��
	}
	//��¼�����صķ���������ʱ���Ĺ�ϵ
	_intercsEd.put(intercId,interTime);
}

//���������
public void addIntercs(String interTime, String classNameEd,String methodNameEd,String className,String methodName,String _instObj) throws Exception{
	Object []_objParam=null;
		if(_instObj!=null && !_instObj.equals("")){
			String[]strbParam=_instObj.split(",");
			_objParam=new Object[strbParam.length];
			for(int i=0;i<strbParam.length;i++){
				_objParam[i]=this.getValue(strbParam[i]);
			}
	}
	this.setIntercs(interTime,classNameEd,methodNameEd,className, methodName, _objParam);
}

//���������,��һ�������Ǳ����ص���ͷ����������������ڶ��������������������������������������������෽���������ĸ���������������ʵ��
public void addIntercs(String interTime, String classNameEd,String methodNameEd,String className,String methodName,Object _instObj) throws Exception{
	AppMethod _tmpMethod=new AppMethod();
	String intercId=interTime.substring(0, 2)+"_______"+classNameEd+"________"+methodNameEd;
	//�ж��Ƿ��Ѿ����������
	if(!this._methods.containsKey(methodName)){
		//����ӷ���
		_tmpMethod.setMethod(className, methodName,_instObj);
		this._methods.put(methodName, _tmpMethod);//�����һ��methodName����
	}else{
		_tmpMethod=(AppMethod)this._methods.get(methodName);
	}
	//�����������ʶ
	if(this._intercs.containsKey(interTime)){
		ArrayList _interc=(ArrayList)_intercs.get(interTime);
		if(_interc==null)_interc=new ArrayList();
		_interc.add(_tmpMethod);
	}else{
		ArrayList _interc=new ArrayList();
		_interc.add(_tmpMethod);
		this._intercs.put(interTime,_interc);//����������ʱ���µķ���
	}
	
	//��¼�����صķ���������ʱ���Ĺ�ϵ
	_intercsEd.put(intercId,interTime);
}

public ArrayList getFields(){
	return _fields;
}
public void setFields(ArrayList lstInFields){
	this._fields=(ArrayList)lstInFields.clone();
}
public HashMap getMethods(){
	return _methods;
}
public void setMethods(HashMap lstInMethods){
	this._methods=(HashMap)lstInMethods.clone();
}
//������(ȫ·��)ֱ�Ӽ�������ֶκͷ���
public void loadFromClass(String className,Object[]obj) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	Class clazz=this.getClass(className);
	loadFromClass(clazz,obj);
}
//����ֱ�Ӽ����ֶκͷ��� obj�Ǵ������ʵ������
public void loadFromClass(String className,Object obj) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	Class clazz=this.getClass(className);
	loadFromClass(clazz,obj);
}

public void loadFromClass(Class clazz,Object obj) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	this._className=clazz.getName();
	Field[] _tmpFields=clazz.getDeclaredFields();
	for(int i=0;i<_tmpFields.length;i++){
		Field _field=_tmpFields[i];
		Class _type=_field.getType();
		String _name=_field.getName();
		AppField _myField=new AppField();
		_myField.setClassName(this._className);
		_myField.setFieldName(_name);
		_myField.setFieldType(_type.getName());
		_myField.setFieldPrivilege("public");
		_fieldsName.put(_name, _name);//������ֵĲ�ѯ�ֶ�
		this._fields.add(_myField);
	}
	Method[] _tmpMethods=clazz.getDeclaredMethods();
	for(int i=0;i<_tmpMethods.length;i++){
		Method _method=_tmpMethods[i];
		String _name=_method.getName();
		AppMethod _myMethod=new AppMethod();
		_myMethod.setMethod(this._className, _name, obj);
		this._methods.put(_name, _myMethod);
	}
}

public void loadFromClass(Class clazz,Object[]obj) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	this._className=clazz.getName();
	Field[] _tmpFields=clazz.getDeclaredFields();
	for(int i=0;i<_tmpFields.length;i++){
		Field _field=_tmpFields[i];
		Class _type=_field.getType();
		String _name=_field.getName();
		AppField _myField=new AppField();
		_myField.setClassName(this._className);
		_myField.setFieldName(_name);
		_fieldsName.put(_name, _name);//������ֵĲ�ѯ�ֶ�
		_myField.setFieldType(_type.getName());
		_myField.setFieldPrivilege("public");
		this._fields.add(_myField);
	}
	Method[] _tmpMethods=clazz.getDeclaredMethods();
	Object instance=null;//������ķ�ʽʵ��,���з�������һ��ʵ���ķ���
	for(int i=0;i<_tmpMethods.length;i++){
		Method _method=_tmpMethods[i];
		String _name=_method.getName();
		AppMethod _myMethod=new AppMethod();
		if(instance==null){
			_myMethod.setMethod(this._className, _name, obj);
			instance=_myMethod.getInstance();
		}
		else
		_myMethod.setMethod(this._className, _name, instance);	
		this._methods.put(_name, _myMethod);
	}
}
//������������
public void setClassName(String pClassName){
	this._className=pClassName;
}
//��ȡ����
public String getClassName(){
	return this._className;
}
//��������ֶ�
public void setField(HashMap fieldVo){
	AppField _tmpField=new AppField();
	_tmpField.loadFromHashMap(fieldVo);
	//�����ͨ�������ķ�ʽ��ӵ��ֶ�,��ôclassname�ǿյ�
	_fieldsName.put(_tmpField.getFieldName(), _tmpField.getFieldName());//������ֵĲ�ѯ�ֶ�
	_fields.add(_tmpField);
}
//��������ֶ�
public void setField(String fieldString) throws Exception{
	AppField _tmpField=new AppField();
	_tmpField.setField(fieldString);
	//�����ͨ�������ķ�ʽ��ӵ��ֶ�,��ôclassname�ǿյ�
	_fieldsName.put(_tmpField.getFieldName(), _tmpField.getFieldName());//������ֵĲ�ѯ�ֶ�
	_fields.add(_tmpField);
}
//�ж��Ƿ�������ֶ�
public boolean isField(String fieldName){
	return _fieldsName.containsKey(fieldName);
}
//�����޸��ֶ�ֵ
public void setValue(String fieldName,Object fieldVal) throws Exception{
	AppField _tmpField=this.getField(fieldName);
	_tmpField.setDefaultValue(fieldVal);
}
//�����޸��ֶ�ֵ
//public void setValue(String fieldName,int fieldVal) throws Exception{
//	AppField _tmpField=this.getField(fieldName);
//	_tmpField.setDefaultValue(fieldVal);
//}
//�����޸��ֶ�ֵ
public void setValue(String fieldName,long fieldVal) throws Exception{
	AppField _tmpField=this.getField(fieldName);
	_tmpField.setDefaultValue(fieldVal);
}
//�����޸��ֶ�ֵ
public void setValue(String fieldName,float fieldVal) throws Exception{
	AppField _tmpField=this.getField(fieldName);
	_tmpField.setDefaultValue(fieldVal);
}
//�����޸��ֶ�ֵ
public void setValue(String fieldName,double fieldVal) throws Exception{
	AppField _tmpField=this.getField(fieldName);
	_tmpField.setDefaultValue(fieldVal);
}
//�����޸��ֶ�ֵ
public void setValue(String fieldName,boolean fieldVal) throws Exception{
	AppField _tmpField=this.getField(fieldName);
	_tmpField.setDefaultValue(fieldVal);
}
//�����ȡ�ֶ�ֵ
public Object getValue(String fieldName) throws Exception{
	AppField _field=this.getField(fieldName);
	if(_field!=null)return _field.getDefaultValue();
	return null;
}

//�ж��Ƿ������fieldname
public boolean isContainField(String fieldName) throws Exception{
	if(fieldName==null || fieldName.equals(""))return false;
	if(_fields ==null || _fields.isEmpty()) return false;
	for(int i=0;i<_fields.size();i++){
		AppField _tmpField=(AppField)_fields.get(i);
		if(_tmpField!=null){
			String _fieldName=_tmpField.getFieldName();
			if(_fieldName.equals(fieldName))return true;
		}
	}
	return false;
}
//��ȡ�ֶζ���
public AppField getField(String fieldName){
	if(fieldName==null || fieldName.equals(""))return null;
	if(_fields ==null || _fields.isEmpty()) return null;
	for(int i=0;i<_fields.size();i++){
		AppField _tmpField=(AppField)_fields.get(i);
		if(_tmpField!=null){
			String _fieldName=_tmpField.getFieldName();
			if(_fieldName.equals(fieldName))return _tmpField;
		}
	}
	return null;
}

//�ֹ����ĳ�����ĳ�����������һ�������������Ĺ��캯���Ĳ���
public void addMethod(String className,String methodName,String newMethodName,Object[]newInst) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	AppMethod _tmpMethod=new AppMethod();
	_tmpMethod.setMethod(className, methodName,newInst);
	_methods.put(newMethodName,_tmpMethod);
}

//�ֹ����ĳ�����ĳ�����������һ��������������ʵ��
public void addMethod(String className,String methodName,String newMethodName,Object newInst) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	AppMethod _tmpMethod=new AppMethod();
	_tmpMethod.setMethod(className, methodName,newInst);
	_methods.put(newMethodName,_tmpMethod);
}

//�ֹ����ĳ�����ĳ�����������һ������Ϊ�գ�����ǿյģ���ôĬ�ϵ���Class.newInstance()����
public void addMethod(String className,String methodName,String newMethodName) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	this.addMethod(className, methodName, newMethodName, null);
}
//��ȡ��������
public AppMethod getMethod(String pMethodName) {
	if(pMethodName ==null || pMethodName.equals("")) return null;
	if(_methods==null || _methods.isEmpty())return null;
	AppMethod _tmpMethod=(AppMethod)_methods.get(pMethodName);
	
	if (_tmpMethod==null){//����û�м��ص��ĸ��෽��
		try {
			_tmpMethod=new AppMethod();
			_tmpMethod.setMethod(this._className, pMethodName, null);
			this._methods.put(pMethodName, _tmpMethod);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	return _tmpMethod;
}
public void setFieldName(HashMap vo){
	this._fieldsName=(HashMap)vo.clone();
}
//���Ƶ�ǰ������
public Object clone(){
	AppClass _appInst=new AppClass();
	_appInst.setClassName(this._className);
	_appInst.setFields(this._fields);
	_appInst.setMethods(this._methods);
	_appInst.setFieldName(this._fieldsName);
	return _appInst;
}
//ִ�е�ǰ��ķ���
public Object executeMethodNoAop(String pMethodName,Object[] objParam,Object newInstance) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
	//��_methods���ȡһ������
	AppMethod _tmpMethod=this.getMethod(pMethodName);
	//ִ��ԭ���ķ���
	return _tmpMethod.executeMethod(pMethodName, objParam, newInstance);
}
//ִ�е�ǰ��ķ���
public Object executeMethod(String pMethodName,Object[] objParam,Object newInstance) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
	//��_methods���ȡһ������
	AppMethod _tmpMethod=this.getMethod(pMethodName);
	//����������
	String intercKey="bf_______"+_tmpMethod.getClassName()+"________"+pMethodName;
	if(_intercsEd.containsKey(intercKey)){//����ж��������������������ʱ��
		//��ȡ����ʱ��
		String intercTime=(String)_intercsEd.get(intercKey);
		//��������ʱ����ȡִ�з���
		ArrayList _intercMethodName=(ArrayList)this._intercs.get(intercTime);
		if(_intercMethodName!=null && !_intercMethodName.isEmpty())
		for(int i=0;i<_intercMethodName.size();i++){
			AppMethod _md=(AppMethod)_intercMethodName.get(i);
			_md.executeMethod(_md.getMethodName(), objParam);
		}
	}
	//ִ��ԭ���ķ���
	Object _obj= _tmpMethod.executeMethod(pMethodName, objParam, newInstance);
	
	//����������
	intercKey="af_______"+_tmpMethod.getClassName()+"________"+pMethodName;
	if(_intercsEd.containsKey(intercKey)){//����ж��������������������ʱ��
		//��ȡ����ʱ��
		String intercTime=(String)_intercsEd.get(intercKey);
		//��������ʱ����ȡִ�з���
		ArrayList _intercMethodName=(ArrayList)this._intercs.get(intercTime);
		if(_intercMethodName!=null && !_intercMethodName.isEmpty())
		for(int i=0;i<_intercMethodName.size();i++){
			AppMethod _md=(AppMethod)_intercMethodName.get(i);
			_md.executeMethod(_md.getMethodName(), objParam);
		}
	}
	
	return _obj;
}
//ִ�е�ǰ��ķ���
public Object executeMethod(String pMethodName,Object[] objParam) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
	return executeMethod(pMethodName,objParam,null);
}
//ִ�е�ǰ��ķ���
public Object executeMethod(String pMethodName,String strParam) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
	String[]_strParam=strParam.split(",");
	Object[] objParam=new Object[_strParam.length];
	for(int i=0;i<_strParam.length;i++){
		AppField _field=this.getField(_strParam[i]);
		Object _obj=_field.getDefaultValue();
		objParam[i]=_obj;
	}
	return executeMethod(pMethodName,objParam,null);
}

//��������
private Class getClass(String className) throws ClassNotFoundException{
	//��ȡȫ�ֵ�classloader
	ClassLoader globeLoader=Thread.currentThread().getContextClassLoader();
	//����ִ����
	Class clazz=globeLoader.loadClass(className);
	return clazz;
}
}
