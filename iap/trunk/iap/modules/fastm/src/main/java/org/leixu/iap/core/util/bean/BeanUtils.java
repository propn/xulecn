/*
 * Bean Reflection, or Map. 
 */
package org.leixu.iap.core.util.bean;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.util.Map;
import java.util.logging.Logger;

import org.leixu.iap.core.util.map.ReadWriteMap;

/**
 * @author hailong.wang
 *
 */
public class BeanUtils {
	private static final Logger log = Logger.getLogger(BeanUtils.class.getName());
	public static final Map beanMap = new ReadWriteMap(); 

	/**
	 * 
	 * @param bean
	 * @param propertyName
	 * @param clazz
	 * @return
	 */
	public static Object getProperty(Object bean, String propertyName){
		if(bean == null || propertyName == null) 
			return null;

		// map
		boolean isMap = bean instanceof Map;
		if(isMap) {
			// short cut for the complete name ?
			Object value = ((Map)bean).get(propertyName);
			if(value != null) 
				return value;
		}

		// a.b.c.d
		int dot = propertyName.indexOf('.');
		if(dot >= 0){
			String beanName = propertyName.substring(0, dot);
			if(beanName.length() > 0){
				bean = getProperty(bean, beanName);
				if(bean == null) 
					return null;
			}
			propertyName = propertyName.substring(dot + 1, propertyName.length());
			return getProperty(bean, propertyName);  
		}else if(bean instanceof ResultSet){
			ResultSet rs = (ResultSet)bean;
			try{
				return rs.getObject(propertyName);
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}

		// [][]
		int leftBracket = propertyName.lastIndexOf("[");
		if(leftBracket >= 0){
			int rightBracket = propertyName.indexOf("]", leftBracket);
			String preName = propertyName.substring(0, leftBracket);

			Object value = getProperty(bean, preName);
			if(rightBracket > 0){
				String str = propertyName.substring(leftBracket + 1, rightBracket);
				int index = Integer.parseInt(str); 

				if(value.getClass().isArray()){ 
					value = Array.get(value, index);
				}else if(value instanceof ResultSet){
					ResultSet rs = (ResultSet)value;
					try{
						value = rs.getObject(index);
					}catch(Exception e){
						throw new RuntimeException(e);
					}
				}
			}
			return value;
		}

		// map
		if(isMap) return null;

		// bean
		BeanGetter beanGetter = getBeanGetter(bean);
		if(beanGetter == null)
			return null;

		Object propertyValue = beanGetter.get(bean, propertyName);
		if(propertyValue == null && "@self".equalsIgnoreCase(propertyName))
			return bean;

		return propertyValue;
	}
	
	public static BeanGetter getBeanGetter(Object obj){
		if(obj == null)
			return null;

		Class clazz = null;
		if(obj instanceof Class){
			clazz = (Class)obj;
		}else{
			clazz = obj.getClass();
		}
		String className = clazz.getName();
		BeanGetter beanGetter = (BeanGetter)beanMap.get(className);
		if(beanGetter == null){
			beanGetter = new BeanGetter();
			beanGetter.setBeanClass(clazz);
			beanMap.put(className, beanGetter);
		}
		
		return beanGetter;
	}

	public static boolean containsProperty(Object bean, String propertyName){
		if(bean == null || propertyName == null) 
			return false;
		
		BeanGetter beanGetter = getBeanGetter(bean);
		if(beanGetter == null)
			return false;
		
		return beanGetter.containsProperty(bean, propertyName);
	}
}
