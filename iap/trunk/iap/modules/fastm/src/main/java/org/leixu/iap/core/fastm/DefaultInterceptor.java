package org.leixu.iap.core.fastm;

import org.leixu.iap.core.util.bean.BeanUtils;

public class DefaultInterceptor implements IValueInterceptor{

	public Object getProperty(Object bean, String propertyName) {
		return BeanUtils.getProperty(bean, propertyName);
	}

	public static final IValueInterceptor instance = new DefaultInterceptor();  
}
