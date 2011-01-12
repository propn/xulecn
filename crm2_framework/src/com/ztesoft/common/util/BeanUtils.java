package com.ztesoft.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;



/**
 * @Classname     : BeanUtils
 * @Description   : �����෴��ķ�����һ����������Խ��и�ֵ����һ�����������ֵ
 *                  ��������һ������
 * @Copyright ? 2006 ZTEsoft.
 * @Author        : Aries.Java
 * @Create Date   : 2006-3-22
 * @Last Modified : Aries.Java
 * @Version       : 1.0
 */
public class BeanUtils {


	/**
	 * <p>
	 * ����Map,ȡ��ֵ��ֵ��һ��Object��������ԡ� Map�Ĺؼ�����������Object����������������
	 * </p>
	 * @param map������Object����ֵ��Map
	 * @param object��Ҫ��ֵ�Ķ���
	 * @exception IllegalAccessException,InvocationTargetException
	 */

	public static Object describe(Map map, Object object) throws IllegalAccessException, InvocationTargetException {
		Iterator keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String propertyName = (String) keys.next();
			Object propertyValue = (Object) map.get(propertyName);
			Method method = findMethod(object.getClass(), propertyName);
			if (method != null && propertyValue != null) {
				invokeMethod(method, object, propertyValue);
			}
		}
		return object;
	}

	/**
	 * <p>
	 * ����Map,ȡ��ֵ��ֵ��һ��Object��������ԡ� Map�Ĺؼ�����������Object����������������
	 * </p>
	 * @param map������Object����ֵ��Map
	 * @param object��Ҫ��ֵ�Ķ���
	 * @exception IllegalAccessException,InvocationTargetException
	 */

	public static Map describeObjToMap(Object object, Map map) throws IllegalAccessException, InvocationTargetException {
		Class origclass=object.getClass();
		Field[] fields = origclass.getDeclaredFields();
		int x = 0;
		while (x < fields.length) {
			String propertyName = fields[x].getName();
			Method method = getReadMethod(origclass, propertyName);
			if (method != null) {
				Object value = method.invoke(object, new Object[0]);
				map.put(propertyName, value);
			}
			x++;
		}
		return map;
	}

	/**
	 * <p>
	 * ��һ���ض��ַ����ĵ�һ����ĸ��ʽ���ɴ�д��
	 * </p>
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

	/**
	 * <p>
	 * �������Ե����ƣ���һ��ָ����JAVABEAN��Ѱ�ң�����Ӧ��set����
	 * </p>
	 * @param beanClass Class����
	 * @param propertyName������
	 * @return ����Method����
	 */
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

	/**
	 * <p>
	 * ִ��һ��OBJECT �����ָ��������ִ������setProperty()�ķ���
	 * </p>
	 * @param method ��Ҫִ�еķ���
	 * @param bean  ָ���Ķ��������method����
	 * @param value ִ�з����Ĳ���
	 */
	private static Object invokeMethod(Method method, Object bean, Object value) throws IllegalAccessException,
			InvocationTargetException {
		try {

			Object[] values = new Object[] { value };
			return method.invoke(bean, values);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Cannot invoke " + method.getDeclaringClass().getName() + "." + method.getName()
					+ " - " + e.getMessage());
		}
	}

	/**
	 * ��ȡһ�������getProerty����
	 * @param object����
	 * @param propertyName��������
	 * @return method���巽��
	 */
	private static Method getReadMethod(Class beanClass, String propertyName) {

		Method[] methods = beanClass.getMethods();
		String methodName = "get" + capitalizePropertyName(propertyName);
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

	/**
	 * ��ȡһ�������ĳ������ֵ
	 * @param object����ʵ��
	 * @param propertyName��������
	 * @return objectֵ
	 */
	public static Object getSimpleProperty(Object object, String propertyName) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		//��ȡ���Ե�getProperty����
		Method method = getReadMethod(object.getClass(), propertyName);
		return method.invoke(object, new Object[0]);
	}

	/**
	 * ��һ�������ĳ�����Ը�ֵ
	 * @param object����ʵ��
	 * @param propertyName��������
	 * @param value����ֵ
	 */
	public static void setSimpleProperty(Object object, String propertyName, Object value) throws IllegalAccessException,
			InvocationTargetException {
		Method method = findMethod(object.getClass(), propertyName);
		invokeMethod(method, object, value);
	}
	
	public static Object getFieldInstance(Field field){
		if(field==null){
			throw new IllegalArgumentException("The Field object is null!");
		}
		Object object=null;
		try {
			object=(Object)field.getClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
		
	}
	

	/**
	 * ��һ���������������Ĺ������Ը�ֵ����һ���������Ӧ������
	 * orig->dest
	 * @param origԴ����
	 * @param destĿ�����
	 */

	public static Object copyProperties(Object orig, Object dest) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Class origclass = orig.getClass();
		//BeanUtils.copyProperties(orig, dest);
		Field[] fields = origclass.getDeclaredFields();
		int x = 0;
		while (x < fields.length) {
			String propertyName = fields[x].getName();
			if ((hasProperty(dest, propertyName))) {
				Object value = getSimpleProperty(orig, propertyName);
				if ((!"".equals(value)) && (value != null)) {
					setSimpleProperty(dest, propertyName, value);
				}
			}
			x++;
		}
		return dest;
	}

	
	/**
	 * ��һ�����������������������ԣ�����˽�У���ֵ����һ���������Ӧ������
	 * orig->dest
	 * @param origԴ����
	 * @param destĿ�����
	 */

	public static Object copyAllProperties(Object orig, Object dest) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Class origclass = orig.getClass();
		Field[] fields = origclass.getDeclaredFields();
		int x = 0;
		while (x < fields.length) {
			String propertyName = fields[x].getName();
			if ((hasAllProperty(dest, propertyName))) {
				Object value = getSimpleProperty(orig, propertyName);
				if ((!"".equals(value)) && (value != null)) {
					setSimpleProperty(dest, propertyName, value);
				}
			}
			x++;
		}
		return dest;
	}
	
	
	/**
	 * �ж�һ���������Ƿ����һ��ָ�����Ƶ����ԣ� ��object������Ѱ������ΪpropertyName�ķ���
	 * 
	 * @param object����ʵ��
	 * @param propertyName��������
	 * @return ����boolean��ʾ�Ƿ����
	 */
	public static boolean hasProperty(Object object, String propertyName) {
		boolean isExist = false;

		Class origclass = object.getClass();
		Field[] fields = origclass.getDeclaredFields();
		int x = 0;
		while (x < fields.length) {
			if (propertyName.equalsIgnoreCase(fields[x].getName())) {
				return true;
			}
			x++;
		}
		return isExist;
	}

	/**
	 * �ж�һ���������Ƿ����һ��ָ�����Ƶ����ԣ� ��object������Ѱ������ΪpropertyName�ķ���
	 * 
	 * @param object����ʵ��
	 * @param propertyName��������
	 * @return ����boolean��ʾ�Ƿ����
	 */
	public static boolean hasAllProperty(Object object, String propertyName) {
		boolean isExist = false;

		Class origclass = object.getClass();
		Field[] fields = origclass.getDeclaredFields();
		int x = 0;
		while (x < fields.length) {
			if (propertyName.equalsIgnoreCase(fields[x].getName())) {
				return true;
			}
			x++;
		}
		return isExist;
	}
	
	/**
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * 
	 */
	public static void printPropertiesValue(Object object) {
		//Class origclass = object.getClass();
		Field[] fields = object.getClass().getDeclaredFields();
		int x = 0;
		while (x < fields.length) {
			String propertyName = fields[x].getName();
			Method method = getReadMethod(object.getClass(), propertyName);
			Object value = null;
			try {
				value = method.invoke(object, new Object[0]);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String message = (value == null || "".equals(value)) ? "null" : value.toString();


			x++;
		}

	}

	/**
	 * set properties value of the des object from the org object
	 *@param org which the object is given to set another object's property value
	 *@param des which the object need to set property's value
	 *@param strs String array which the elements are the property relation between  org object and des object.
	 */
	public static Object copyProperties(Object org, Object des, String[] strs) throws Exception {
		int x = 0;
		if ((strs == null && strs.length == 0) || (org == null || des == null)) {
			StringBuffer sb = new StringBuffer();
			sb.append("There is a system exception when set the property value of the des object  from the org object.\n");
			sb.append("May be someone object is null!");
			throw new Exception(sb.toString());
		}
		while (x < strs.length) {
			String str = strs[x];
			if(str.indexOf(".")==-1){
				throw new Exception("The "+str+"'s change pattern is not match.");
			}
            
            if("*".equals(str)){
                copyProperties(org,des);
            }else{
    			String desPropertyName = str.substring(0, str.indexOf(":"));
    			String orgPropertyName = str.substring(str.indexOf(":"));
    			if (hasProperty(des, desPropertyName.trim())) {
    				Object value = getSimpleProperty(org, orgPropertyName.trim());
    				if (value != null && !"".equals(value)) {
    					setSimpleProperty(des, desPropertyName, value);
    				}
    			}
            }
			x++;
		}
		return des;

	}

}
