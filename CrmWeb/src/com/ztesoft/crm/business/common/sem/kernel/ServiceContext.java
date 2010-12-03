package com.ztesoft.crm.business.common.sem.kernel;

import java.util.HashMap;
import java.util.Map;

public class ServiceContext {

	Map context;

	BeanContainer beanContainer;

	BeanXMLParser beanParser;

	public ServiceContext() {

		if (context == null) {
			context = new HashMap();
			beanContainer = new DefaultBeanContainer();
		}
	}

	public Object getService(String key) {
		Object object;
		if (!context.containsKey(key)) {
			object = this.beanContainer.getBean(key);
			context.put(key, object);
		} else {
			object = this.context.get(key);
		}
		return object;
	}
}
