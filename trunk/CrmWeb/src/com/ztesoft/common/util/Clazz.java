package com.ztesoft.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.exception.CrmException;





public class Clazz {

	public static Method EQUALS_METHOD;
	public static Method HASHCODE_METHOD;
	public static Method TOSTRING_METHOD;

	static {	
		try {
			EQUALS_METHOD = 
				Object.class.getMethod("equals", new Class[] { Object.class });
			HASHCODE_METHOD = 
				Object.class.getMethod("hashCode", null);
			TOSTRING_METHOD = 
				Object.class.getMethod("toString", null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Method[] OBJECT_METHODS = new Method[] { EQUALS_METHOD,
			HASHCODE_METHOD, TOSTRING_METHOD };
	public static Set OBJECT_METHODS_SET = 
			new HashSet(Arrays.asList(OBJECT_METHODS));
	
    private Clazz() {}

	public static Object newInstance(Class clazz) {
		try {
			return clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	public static Object newInstance(Constructor constructor, 
			Object[] arguments) {
		try {
			return constructor.newInstance(arguments);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	public static boolean implementsInterfaces(Class clazz, 
			Class[] interfaces) {
		for (int i = 0; i < interfaces.length; i++)
			if (!interfaces[i].isAssignableFrom(clazz))
				return false;
		return true;
	}
    public static Object invoke(Object target, Method method,
            Object[] args) throws Exception {
        /*if (target instanceof Proxy) {
        	return Proxy.getInvocationHandler(target).
        		invoke(target, method, args);
        }
        else {*/
			try {
				return method.invoke(target, args);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage());
			}
			catch (InvocationTargetException e) {
				Throwable cause =e.getTargetException();
				if(cause instanceof CrmException){
					throw  (CrmException)cause;
				}else if(cause instanceof DAOSystemException){
					
					throw ((DAOSystemException)cause);
					
				}else {
					throw e;
				}
			}catch(Exception e){
				throw e;
			}
       // }
    }
    public static ClassLoader getClassLoader(Class clazz) {
    	ClassLoader loader = clazz.getClassLoader();
        return (loader == null) ? ClassLoader.getSystemClassLoader() :
            loader;
    }
	public static ClassLoader getClassLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		return (loader == null) ? ClassLoader.getSystemClassLoader() :
			loader;
	}
    public static ClassLoader commonLoader(Class[] classes) {
    	if (classes.length == 0) throw new IllegalArgumentException(
            "No classes provided.");
        if (classes.length == 1) return getClassLoader(classes[0]);
		for (int i = 1; i < classes.length; i++) {
			try {
				ClassLoader loader = getClassLoader(classes[i]);
				for (int i2 = 0; i2 < classes.length; i2++) {
					if (i == i2)
						continue;
					loader.loadClass(classes[i2].getName());
				}
				return loader;
			}
			catch (ClassNotFoundException e) {}			
		}
		
		throw new RuntimeException("No common class loader: " + 
			Arrays.asList(classes));
    }
    public static ClassLoader commonLoader(Collection classes) {
    	return commonLoader(
    		(Class[]) classes.toArray(new Class[classes.size()]));
    }
 
    public static List getAllInterfacesAsList(Class clazz) {
    	List interfaces = null;
    	if (clazz.isInterface())
    		return Collections.singletonList(clazz);
        else {
        	interfaces = new ArrayList();
        	addAllInterfaces(clazz, interfaces);
        	Collections.reverse(interfaces);
        	return interfaces;
        }
    }
   
	public static Class[] getAllInterfaces(Class clazz) {
		return (Class[]) getAllInterfacesAsList(clazz).
			toArray(new Class[0]);
	}
  
    static void addAllInterfaces(Class clazz, List list) {
        while (clazz != null && clazz != Object.class) {
            Class[] interfaces = clazz.getInterfaces();
            for (int i = interfaces.length - 1; i >= 0; i--)
            	if (!list.contains(interfaces[i]))
            		list.add(interfaces[i]);
            clazz = clazz.getSuperclass();
        }
    }

    static final Map primitiveMap = new HashMap();

	static {
		primitiveMap.put("boolean", boolean.class);
		primitiveMap.put("byte", byte.class);
		primitiveMap.put("char", char.class);
		primitiveMap.put("short", short.class);
		primitiveMap.put("int", int.class);
		primitiveMap.put("long", long.class);
		primitiveMap.put("float", float.class);
		primitiveMap.put("double", double.class);
		primitiveMap.put("void", void.class);
	}
	public static Class forName(String name) throws ClassNotFoundException {
		Class clazz = (Class) primitiveMap.get(name);
		if (clazz != null) 
			return clazz;
		return getClassLoader().loadClass(name);
	}
	
	public static Method findMethod(Class beanClass, String methodName) {
		Method[] methods = beanClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method == null) {
				continue;
			}
			if (method.getName().equals(methodName))
				return method;
		}
		return null;
	}
	
	public static List getAllMethods(Class beanClass){
		
		return Arrays.asList(beanClass.getDeclaredMethods());
	}
	
}
