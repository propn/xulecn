package org.leixu.iap.core.fastm.interceptors;

/**
 * if can not find property in bean, find the property in the context
 */
public class ContextInterceptor 
		extends DelegatedInterceptor {

	private Object context;
	public void setContext(Object context) {
		this.context = context;
	}

	protected Object getValue(Object bean, String propertyName, Object value){
		if(value == null){ 
			value = inner.getProperty(context, propertyName);
		}
		return value;
	}
}
