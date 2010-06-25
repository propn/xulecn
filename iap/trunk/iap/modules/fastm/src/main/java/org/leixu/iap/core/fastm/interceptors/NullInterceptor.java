package org.leixu.iap.core.fastm.interceptors;

public class NullInterceptor extends DelegatedInterceptor {
	protected Object getValue(Object bean, String propertyName, Object value){
		if(value == null && propertyName.indexOf(" ") < 0) 
			return emptyStatic;

		return value;
	}
}
