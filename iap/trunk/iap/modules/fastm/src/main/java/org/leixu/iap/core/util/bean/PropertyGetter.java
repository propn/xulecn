/*
 * Like read-only java.beans.PropertyDescriptor
 */
package org.leixu.iap.core.util.bean;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
/**
 * @author hailong.wang
 *
 */
public class PropertyGetter {
	Method method = null;
	Field field = null;

	/**
	 * @param field
	 */
	public void setField(Field field) {
		this.field = field;
	}

	/**
	 * @param method
	 */
	public void setMethod(Method method) {
		this.method = method;
	}
	
	public boolean isValid(){
		return method != null || field != null;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	public Object get(Object bean){
		try{
			if(method != null){
                Object[] values = null;
                return method.invoke(bean, values);
            }
			if(field != null) 
				return field.get(bean);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return null;
	}
}
