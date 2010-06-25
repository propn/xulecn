/*
 * Fastm
 */
package org.leixu.iap.core.fastm;

/**
 * @author hailong.wang
 *
 */
public interface IValueInterceptor {
	/**
	 * get the property from the bean. 
	 * 
	 * @param bean
	 * @param propertyName
	 * @return
	 */
	Object getProperty(Object bean, String propertyName);
}
