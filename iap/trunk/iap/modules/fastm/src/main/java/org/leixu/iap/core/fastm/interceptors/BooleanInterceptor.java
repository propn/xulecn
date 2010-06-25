package org.leixu.iap.core.fastm.interceptors;

/**
 * if true or false than display
 */
public class BooleanInterceptor 
		extends DelegatedInterceptor {

	protected Object getValue(Object bean, String propertyName, Object value){
		if(value != null && value instanceof Boolean) {
			boolean b = ((Boolean)value).booleanValue();
			if(b == false) return "";
		}
		return value;
	}
}
