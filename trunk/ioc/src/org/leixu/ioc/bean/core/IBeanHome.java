package org.leixu.ioc.bean.core;

/**
 * 自定义的BeanHome必须实现该接口，用于在Bean池中注册
 * 
 * @creator cydric
 * @create-time 2010-11-29 下午08:42:52
 * @revision $Id
 **/
public interface IBeanHome {
	/**
	 * 注册bean，在beanPool中管理这些beanHome，默认是LazySingleBeanHome生效。可以自己实现，集成BaseBeanHome类，并实现该方法即可。
	 * 需要在com.jegg.customizedBeanPool包下，该包需要自己创建,且只能创建文件。
	 */
	void register();
}
