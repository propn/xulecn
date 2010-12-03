package com.ztesoft.crm.business.common.sem.kernel;

import java.util.List;

/**
 * Bean 容器 包装 Service Step Component类
 */
public interface BeanContainer {

	/**
	 * @param key xml配置中的属性name关键字
	 * @return 返回具体class 实例
	 */
	public Object getBean(Object key);

	/**
	 * @param class service step component具体类
	 * @return 返回具体class 实例
	 */
	public Object getBeanByClass(Class cls);

	/**
	 * @return 返回容器中所有class实例
	 */
	public List getBeans();

	/**
	 * @param cls service step component具体类
	 * @return 返回具体class包装器 ClassWrapper
	 */
	
	public BeanWrapper registerClass(Class cls);

	/**
	 * @param key xml配置中的属性name关键字
	 * @param cls service step component具体类
	 * @return 返回具体class包装器 ClassWrapper
	 */
	public BeanWrapper registerClass(Object key, Class cls);

	
	/**
	 * @param key xml配置中的属性name关键字
	 * @param cls service step component具体类
	 * @param parameters 属性的类型的封装，在实例化具体属性的时候
	 * @return 返回具体class包装器 ClassWrapper
	 */
	
	public BeanWrapper registerClass(Object key, Class cls,
			Parameter[] parameters);

	/**
	 * 从容器中删除具体类包装
	 * @param key xml配置中的属性name关键字
	 * @return 返回具体class包装器 ClassWrapper
	 */
	
	public BeanWrapper unregisterClass(Object key);

	/**
	 *  判断容器中是否存在某个具体类包装器
	 *  @param key xml配置中的属性name关键字
	 *  
	 * */
	public boolean isExist(Object key);

}
