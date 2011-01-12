package com.ztesoft.common.application;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * @author AyaKoizumi
 * @date 20100906
 * @desc ����һ��java�������ڷ�������ǰ���ҵ��У��
 * */
public class AppProxy implements InvocationHandler{

	public static void main(String[]args){
		HashMap vo=new HashMap();
		AppProxy apx=new AppProxy();
		Object obj=apx.getProxyObject(vo);
		try {
			apx.addBfInterc("put", "com.ztesoft.common.application.AppProxy", "bfPut", apx);
			apx.addAfInterc("put", "com.ztesoft.common.application.AppProxy", "afPut", apx);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		((Map)obj).put("cc","dd");
		System.out.println(((Map)obj).get("cc"));
	}

	private boolean isUseArrayListMethodParam=false;
	public void setIsUseArrayListMethodParam(boolean setFlag){
		this.isUseArrayListMethodParam=setFlag;
	}
	public boolean getIsUseArrayListMethodParam(){
		return this.isUseArrayListMethodParam;
	}
	private Object _proxy;
	//��ȡ�������
	public Object getProxyObject(Object inObj){
		_proxy=inObj;
		Class clazz=inObj.getClass();
		ClassLoader clsLoader=clazz.getClassLoader();
		Class[]clsInterfaces=clazz.getInterfaces();
		return java.lang.reflect.Proxy.newProxyInstance(clsLoader, clsInterfaces,this);
	}
	//��ȡ�������
	public Object getProxyObject(Object inObj,Class[]clsInterfaces){
		_proxy=inObj;
		Class clazz=inObj.getClass();
		ClassLoader clsLoader=clazz.getClassLoader();
//		Class[]clsInterfaces=clazz.getInterfaces();
		return java.lang.reflect.Proxy.newProxyInstance(clsLoader, clsInterfaces,this);
	}
	//ִ��ԭ���󷽷�
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String methodName=method.getName();
		String intercId=getIntercId(methodName, "bf");

		callInterc(proxy,intercId, args);
		
		Object ret=method.invoke(_proxy, args);
	
		intercId=getIntercId(methodName, "af");
		callInterc(proxy,intercId, args);
		
		return ret;
	}
	
	public AppProxy(){
	}

	public void callInterc(Object proxy,String intercId,Object[] args) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		if(intercMapCls.containsKey(intercId)){//��������������
			AppClass aps=(AppClass)intercMapCls.get(intercId);
			String methodName=(String)intercMapInst.get(intercId);
			if(!isUseArrayListMethodParam)
				aps.executeMethodNoAop(methodName, args,null);
			else{
				ArrayList methodParam=new ArrayList();
				if(args!=null){
					for(int i=0;i<args.length;i++){
						methodParam.add(args[i]);
					}
				}
				aps.executeMethodNoAop(methodName, new Object[]{methodParam},null);
			}
		}
	}
	//inst���ض���(�����null��newһ��instance),mapInst������������ʹ���ʱ�����󣨱����ض��󣩣�����̳�AppProxyI�ӿ�
	public void addBfInterc(String oriMethodName,String className,String methodName,Object inst) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String intercId=this.getIntercId(oriMethodName, "bf");
		//����������ʵ��
		AppClass aps=new AppClass();
		aps.loadFromClass(className, inst);
		intercMapCls.put(intercId, aps);
		intercMapInst.put(intercId, methodName);
	}
	public void addAfInterc(String oriMethodName,String className,String methodName,Object inst) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String intercId=this.getIntercId(oriMethodName, "af");
		AppClass aps=new AppClass();
		aps.loadFromClass(className, inst);
		intercMapCls.put(intercId, aps);
		intercMapInst.put(intercId, methodName);
		
	}
	
	private String getIntercId(String oriMethodName,String intercTime){
		return "_________"+intercTime+oriMethodName;
	}
	
	public void bfPut(Object c,Object d){
		String msg="bf___";
		if(c!=null)
			msg=msg+c.toString();
		System.out.println(msg);
	}
	public void afPut(Object c,Object d){
		String msg="af___";
		if(c!=null)
			msg=msg+c.toString();
		System.out.println(msg);
	}
	
	private static HashMap intercMapCls=new HashMap();//�������ʱ����Ӧ����������ʵ��
	public static HashMap getIntercMapCls(){
		return intercMapCls;
	}
	private static HashMap intercMapInst=new HashMap();//�������ʱ����Ӧ���������෽����
	public static HashMap getiIntercMapInst(){
		return intercMapInst;
	}
	
	//�Ƴ�������(��Ҫ����ע�������)
	public static void setIntercMapInstToNullById(String id){
		if(intercMapInst.containsKey(id)){
			Object obj=intercMapInst.get(id);
			obj=null;
			intercMapInst.remove(id);
			if(intercMapInst.isEmpty()){
				intercMapInst=null;
				intercMapInst=new HashMap();
			}
			HashMap copy=(HashMap)intercMapInst.clone();
			intercMapInst=null;//ȥ��ԭ�����ڴ�����
			intercMapInst=copy;//��һ��HashMap����������HashMap�����ڴ�й¶
		}
		if(intercMapCls.containsKey(id)){
			Object obj=intercMapCls.get(id);
			obj=null;
			intercMapCls.remove(id);
			if(intercMapCls.isEmpty()){
				intercMapCls=null;
				intercMapCls=new HashMap();
			}
			HashMap copy=(HashMap)intercMapCls.clone();
			intercMapCls=null;//ȥ��ԭ�����ڴ�����
			intercMapCls=copy;//��һ��HashMap����������HashMap�����ڴ�й¶
		}
	}
	
}
