package com.ztesoft.crm.business.common.sem.kernel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassUtils {

	public static Object newInstanceFromClassName(String sNamclassNamee)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class clazz = Class.forName(sNamclassNamee);
		return clazz.newInstance();
	}
	
	public static Class getClassByName(String sNamclassNamee)throws ClassNotFoundException{
		Class clazz=null;
		try {
			clazz = Class.forName(sNamclassNamee);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
			throw e;
			
		}
		return clazz;
}

	public static void setSimpleProperty(Object object, String propertyName,
			Object value) throws IllegalAccessException,
			InvocationTargetException {
		Method method = findMethod(object.getClass(), propertyName);
		invokeMethod(method, object, value);
	}

	private static Method findMethod(Class beanClass, String propertyName) {
		Method[] methods = beanClass.getMethods();
		String methodName = "set" + capitalizePropertyName(propertyName);
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

	private static Object invokeMethod(Method method, Object bean, Object value)
			throws IllegalAccessException, InvocationTargetException {
		try {

			Object[] values =null;
			if(value!=null){
				values=new Object[] { value };
			}
			return method.invoke(bean, values);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Cannot invoke "
					+ method.getDeclaringClass().getName() + "."
					+ method.getName() + " - " + e.getMessage());
		}
	}
	
	public static Object invokeMethod(Object bean,String methodName, Object value)
	throws IllegalAccessException, InvocationTargetException {
		Method[] methods = bean.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method == null) {
				continue;
			}
			if (method.getName().equals(methodName)){
				return invokeMethod(method,bean,value);
			}
		}
		return null;
		
	}

	/**
	 * <p>
	 * ��һ���ض��ַ����ĵ�һ����ĸ��ʽ���ɴ�д��
	 * </p>
	 * 
	 * @param s��Ҫ��ʽ�����ַ�����
	 * @return ���ظ�ʽ������ַ���
	 */
	private static String capitalizePropertyName(String s) {
		if (s.length() == 0) {
			return s;
		}
		char chars[] = s.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);

		return new String(chars);
	}

}
