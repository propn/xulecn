package com.ztesoft.crm.business.common.sem.kernel;

import java.util.ArrayList;
import java.util.Collection;




public class ServiceBeanDescription extends BeanDescription{

	
	public Collection steps=new ArrayList();
	
	public final static String DEFAULT_PROP_NAME="steps";
	
	
	public ServiceBeanDescription(Object key,Class beanClass){
		
		super(key,beanClass);
		
		this.parameters.add(new PropertyParameter(DEFAULT_PROP_NAME,steps));
	}
	
	public void addPropertyParameters(Object objKey, Object className) {

		steps.add(new ClassValue(objKey,className.toString()));
		
	}
	
}


