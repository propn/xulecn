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
 * @desc 构造一个java代理，用于方法调用前后的业务校验
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
	//获取代理对象
	public Object getProxyObject(Object inObj){
		_proxy=inObj;
		Class clazz=inObj.getClass();
		ClassLoader clsLoader=clazz.getClassLoader();
		Class[]clsInterfaces=clazz.getInterfaces();
		return java.lang.reflect.Proxy.newProxyInstance(clsLoader, clsInterfaces,this);
	}
	//获取代理对象
	public Object getProxyObject(Object inObj,Class[]clsInterfaces){
		_proxy=inObj;
		Class clazz=inObj.getClass();
		ClassLoader clsLoader=clazz.getClassLoader();
//		Class[]clsInterfaces=clazz.getInterfaces();
		return java.lang.reflect.Proxy.newProxyInstance(clsLoader, clsInterfaces,this);
	}
	//执行原对象方法
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
		if(intercMapCls.containsKey(intercId)){//增加拦截器调用
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
	//inst拦截对象(如果传null则new一个instance),mapInst存放拦截器规格和触发时机对象（被拦截对象），必须继承AppProxyI接口
	public void addBfInterc(String oriMethodName,String className,String methodName,Object inst) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String intercId=this.getIntercId(oriMethodName, "bf");
		//生成拦截器实例
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
	
	private static HashMap intercMapCls=new HashMap();//存放拦截时机对应的拦截器类实例
	public static HashMap getIntercMapCls(){
		return intercMapCls;
	}
	private static HashMap intercMapInst=new HashMap();//存放拦截时机对应的拦截器类方法名
	public static HashMap getiIntercMapInst(){
		return intercMapInst;
	}
	
	//移除拦截器(需要重新注册才有用)
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
			intercMapInst=null;//去除原来的内存引用
			intercMapInst=copy;//换一个HashMap容器，避免HashMap容器内存泄露
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
			intercMapCls=null;//去除原来的内存引用
			intercMapCls=copy;//换一个HashMap容器，避免HashMap容器内存泄露
		}
	}
	
}
