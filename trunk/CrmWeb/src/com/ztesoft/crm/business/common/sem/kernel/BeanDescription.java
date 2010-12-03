package com.ztesoft.crm.business.common.sem.kernel;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Step Component 类具体描述
 **/
public abstract class BeanDescription implements java.io.Serializable{
	
	protected Object key;//类关键字
	protected Class beanClass; //类名
	public BeanDescription(Object key,Class beanClass){
		this.key=key;
		this.beanClass=beanClass;
	}
	protected List parameters=new ArrayList(); 
	
	public Class getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}
	/**
	 * 返回属性参数数组
	 * @return 返回类属性实例及赋值所需要的参数
	 * */
	public Parameter[] getParameters() {
		
		Parameter[] propertyParameters=new PropertyParameter[parameters.size()]; 
		
		for(int i=0;i<propertyParameters.length;i++){
			
			propertyParameters[i]=(PropertyParameter)parameters.get(i);
		}
		return propertyParameters;
	}
	
	/**
	 * 添加属性参数
	 * @param propName 属性名
	 * @param value 属性值 value 可以是具体的类实例，Map ，Collection,String 等
	 * */
	public abstract void addPropertyParameters(Object propName,Object vlaue);
	
	/**
	 * 此方法在子类ComponentDescrption中用到
	 * @param propName 属性名
	 * @param propType 属性类型
	 * @param value 属性值 value 可以是具体的类实例，Map ，Collection,String 等
	 * */
	public  void addPropertyParameters(Object propName,Object propType,Object vlaue){
		
		
	}
}
