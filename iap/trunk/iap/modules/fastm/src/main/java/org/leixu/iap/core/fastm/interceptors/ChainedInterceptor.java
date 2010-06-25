package org.leixu.iap.core.fastm.interceptors;

import org.leixu.iap.core.fastm.IValueInterceptor;

public class ChainedInterceptor implements IValueInterceptor{
	private IValueInterceptor chain;

	/**
	 * 
	 * @param interceptors
	 */
	public ChainedInterceptor(DelegatedInterceptor[] interceptors){
		if(interceptors == null || interceptors.length == 0) return;
		
		chain =  interceptors[0];
		for(int i = 1; i < interceptors.length; i++){
			DelegatedInterceptor interceptor = interceptors[i];
			interceptor.setInner(chain);
			chain = interceptor;
		}
	}

	/**
	 * 
	 */
	public Object getProperty(Object bean, String propertyName) {
		if(chain == null) return null;
		return chain.getProperty(bean, propertyName);
	}
}
