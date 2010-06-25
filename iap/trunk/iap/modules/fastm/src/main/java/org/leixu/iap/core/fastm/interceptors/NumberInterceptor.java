package org.leixu.iap.core.fastm.interceptors;

public class NumberInterceptor {

	protected Object getValue(Object bean, String propertyName, Object value){
		if(value != null && value instanceof Number) {
			Number number = (Number)value;
			if("0".equals(value.toString()))return "";
		}
		return value;
	}
}
