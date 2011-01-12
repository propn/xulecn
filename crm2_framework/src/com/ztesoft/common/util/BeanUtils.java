package com.ztesoft.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;



/**
 * @Classname     : BeanUtils
 * @Description   : 采用类反射的方法对一个对象的属性进行赋值，或将一个对象的属性值
 *                  赋给另外一个对象
 * @Copyright ? 2006 ZTEsoft.
 * @Author        : Aries.Java
 * @Create Date   : 2006-3-22
 * @Last Modified : Aries.Java
 * @Version       : 1.0
 */
public class BeanUtils {


	/**
	 * <p>
	 * 遍历Map,取出值赋值给一个Object对象的属性。 Map的关键字索引根据Object的属性名称命名。
	 * </p>
	 * @param map保存有Object属性值的Map
	 * @param object需要赋值的对象
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
	 * 遍历Map,取出值赋值给一个Object对象的属性。 Map的关键字索引根据Object的属性名称命名。
	 * </p>
	 * @param map保存有Object属性值的Map
	 * @param object需要赋值的对象
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
	 * 对一个特定字符串的第一个字母格式化成大写。
	 * </p>
	 * @param s需要格式化的字符串。
	 * @return 返回格式化后的字符串
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
	 * 根据属性的名称，在一个指定的JAVABEAN中寻找，其相应的set方法
	 * </p>
	 * @param beanClass Class类型
	 * @param propertyName的名称
	 * @return 返回Method对象
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
	 * 执行一个OBJECT 对象的指定方法。执行类似setProperty()的方法
	 * </p>
	 * @param method 需要执行的方法
	 * @param bean  指定的对象包含有method方法
	 * @param value 执行方法的参数
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
	 * 获取一个对象的getProerty方法
	 * @param object类型
	 * @param propertyName属性名称
	 * @return method具体方法
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
	 * 获取一个对象的某个属性值
	 * @param object对象实例
	 * @param propertyName属性名称
	 * @return object值
	 */
	public static Object getSimpleProperty(Object object, String propertyName) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		//获取属性的getProperty方法
		Method method = getReadMethod(object.getClass(), propertyName);
		return method.invoke(object, new Object[0]);
	}

	/**
	 * 将一个对象的某个属性赋值
	 * @param object对象实例
	 * @param propertyName属性名称
	 * @param value属性值
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
	 * 将一个对象的里面包含的公共属性赋值给另一个对象，相对应的属性
	 * orig->dest
	 * @param orig源对象
	 * @param dest目标对象
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
	 * 将一个对象的里面包含的所有属性（包含私有）赋值给另一个对象，相对应的属性
	 * orig->dest
	 * @param orig源对象
	 * @param dest目标对象
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
	 * 判断一个对象中是否存在一个指定名称的属性： 在object对象在寻找名称为propertyName的方法
	 * 
	 * @param object对象实例
	 * @param propertyName属性名称
	 * @return 返回boolean表示是否存在
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
	 * 判断一个对象中是否存在一个指定名称的属性： 在object对象在寻找名称为propertyName的方法
	 * 
	 * @param object对象实例
	 * @param propertyName属性名称
	 * @return 返回boolean表示是否存在
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
