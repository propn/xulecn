package com.ztesoft.crm.business.common.sem.kernel;


public class StepBeanDescription extends BeanDescription{
	
	public StepBeanDescription(Object key,Class beanClass){
		super(key,beanClass);		
	}
	
	//属性名称,引用的关键值
	public void addPropertyParameters(Object propName,Object refKey) {
		
		this.parameters.add(new PropertyParameter(propName.toString(),new RefValue(refKey)));
	}
	
}
