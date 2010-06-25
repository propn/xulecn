/*
 * like java.beans.
 */
package org.leixu.iap.core.util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.leixu.iap.core.util.loader.Capitalizer;
import org.leixu.iap.core.util.map.ReadWriteMap;

/**
 * @author hailong.wang
 *
 */
public class BeanGetter {
	private final Map propertyMap = new ReadWriteMap();
	private Class beanClass; 

	/**
	 * 
	 * @param beanClass
	 */
	public void setBeanClass(Class beanClass){
		this.beanClass = beanClass;
	}

	/**
	 * 
	 * @return
	 */
	public Class getBeanClass(){
		return beanClass;
	}

	/**
	 * 
	 * @param bean
	 * @param propertyName
	 * @return
	 */
	public PropertyGetter getPropertyGetter(Object bean, String propertyName){
		if(bean == null || propertyName == null) 
			return null;
		PropertyGetter propGetter = (PropertyGetter)propertyMap.get(propertyName);

		if(propGetter == null){
			boolean hit = false;
			Object propertyValue = null;
	
			// first find getter
			String propName = Capitalizer.capitalize(propertyName);
			String methodName = "get" + propName;			

			Method method = null;
			try{
                Class[] args = null;
				method = beanClass.getMethod(methodName, args);
				hit = true;
			}catch(Exception e){
				method = null;
			}

			if(!hit){	
				try{
					methodName = "is" + propName;
                    Class[] args = null;
					method = beanClass.getMethod(methodName, args);
					hit = true;
				}catch(Exception e){
					method = null;
				}
			}
			
			Field field = null;
			if(!hit) {
				// try field
				try{
					field = beanClass.getField(propertyName);
					hit = true;
				}catch(Exception e){
					field = null;
				}
			}

			propGetter = new PropertyGetter();
			propGetter.setMethod(method);
			propGetter.setField(field);

			propertyMap.put(propertyName, propGetter);
		}
		return propGetter;
	}
	
	public Object get(Object bean, String propertyName){
		PropertyGetter propGetter = getPropertyGetter(bean, propertyName);
		if(propGetter == null)
			return null;

		return propGetter.get(bean);
	}
	
	public boolean containsProperty(Object bean, String propertyName){
		PropertyGetter propGetter = getPropertyGetter(bean, propertyName);
		if(propGetter == null)
			return false;
		
		return propGetter.isValid();
	}
}
