package com.ztesoft.common.application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class AppMethod {
//��������
private String className="";
//���巽����
private String methodName="";
//������ʵ��
private Object _instance=null;
public Object getInstance(){
	return _instance;
}
public void setInstance(Object _obj){
	_instance=_obj;
}
public void setMethod(String pClassName,String pMethodName,Object pInst) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
	this.className=pClassName;
	this.methodName=pMethodName;
	//����ִ����
	Class clazz=getClass(this.className);
	//��ȡ��ʵ��
	if(pInst==null){
		try{
			_instance=clazz.newInstance();
		}catch(Throwable ee){
//			ee.printStackTrace();
		}
	}else{
		_instance=pInst;
	}
}

public void setMethod(String pClassName,String pMethodName,Object[] pInst) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
	this.className=pClassName;
	this.methodName=pMethodName;
	//����ִ����
	Class clazz=getClass(this.className);
	//��ȡ��ʵ��
	if(pInst==null){
		try{
			_instance=clazz.newInstance();
		}catch(Throwable ee){
//			ee.printStackTrace();
		}
	}else{
		Constructor []parameterTypes=clazz.getConstructors();
		if(parameterTypes==null || parameterTypes.length==0)throw new ClassNotFoundException("�޷���ȡ�๹�캯��[classname:"+pClassName+"]");
		
		for(int i=0;i<parameterTypes.length;i++){
			boolean isCanInst=true;
			Constructor _constructor=parameterTypes[i];
			Class[]_paramTypes=_constructor.getParameterTypes();
			int paramLength=_paramTypes.length;
			if(paramLength==0 && pInst.length!=0){
				isCanInst=false;
			}
			for(int j=0;j<paramLength;j++){
				Class _paramType=_paramTypes[j];
				if(!_paramType.equals(pInst[j].getClass())){
					isCanInst=false;
				}
			}
			if(isCanInst){
				try{
					_instance=_constructor.newInstance(pInst);
				}catch(Throwable ee){
//					ee.printStackTrace();
				}
				break;
			}
		}
	}
}

public String getMethodName(){
	return this.methodName;
}
public String getClassName(){
	return this.className;
}
public void loadFromHashMap(HashMap methodVo){
	if(methodVo!=null && !methodVo.isEmpty()){
		this.className=(String)methodVo.get("className");
		this.methodName=(String)methodVo.get("methodName");
	}
}

//ִ��ʵ���ķ���:���뷽����+�������
public Object executeMethod(String methodName,Object []objParam) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
	return executeMethod(methodName,objParam,null);
}

//ִ��ʵ���ķ���:���뷽����+�������+�Ƿ���static����
public Object executeMethod(String methodName,Object []objParam,Object newInst) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
	//����ִ����
	Class clazz=getClass(this.className);
	if(objParam==null)objParam=(new Object[]{});
	boolean isNull=false;//�������û�в�������ôȫ����Ҫ��Ϊ��
	if(objParam.length==1){//���ȫ���������ǿ�,��������һ������,��ô��־һ����־
		if(objParam[0]==null){
			isNull=true;
		}
	}
	if(isNull){//���ȫ���������ǿ�,��������һ������,��ô
		objParam=null;
	}
	
	Method [] lstMethod=clazz.getMethods();
	Method _method=null;
	for(int i=0;i<lstMethod.length;i++){
		Method _tmpMethod=lstMethod[i];
		if(_tmpMethod.getName().equals(methodName)){//��ȡ���ķ��������ܴ������أ�������ܻ���ó���
			//У���������,�����������һ��Ϊ��,��û��У��������,������������У��,ֻУ��ǿյĲ�������
			if(computeMethod(objParam,_tmpMethod)){
				_method=_tmpMethod;
				break;
			}
		}
	}
	if(_method==null)throw new NoSuchMethodException(this.className+":"+methodName);
	Object objRet=null;
	if(newInst!=null){
		objRet=_method.invoke(newInst, objParam);
	}else{
		objRet=_method.invoke(_instance, objParam);
	}
	return objRet;
}
//У�������ʽ�Ƿ���Ϸ������:��Ϊ����ʱֻ��������Object[],�޷���֪���õĲ��������ͣ���������ֻ�����Ƚϣ�
//ֻ�Ƚϴ����Object[]�в�Ϊ�յĲ��������ͺ�˳���Ƿ�����.
private boolean computeMethod(Object[] _paramVal,Method _method){
	if(_method==null || _method==null)return false;
	Class []_lstParamType=_method.getParameterTypes();
	//�жϿղ�����Ϊ��εĿղ�������-----------------------------
	if(_paramVal==null){
		//��ȡ�������������
		if(_lstParamType==null || _lstParamType.length==0)
			return true;
	}else{//------------------------------------------------------
		if( _paramVal.length==1 && _paramVal[0]==null){
			//��ȡ�������������
			if(_lstParamType==null || _lstParamType.length==0)
				return true;
		}
		else{//�жϷǿղ�����Ϊ��εķǿղ�������------------------
			//����������ȣ���ô���ز���
			if(_lstParamType.length!=_paramVal.length)return false;
			return true;
		}
	}
	return false;
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
