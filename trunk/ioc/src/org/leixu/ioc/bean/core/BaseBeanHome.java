package org.leixu.ioc.bean.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean底层的抽象类
 * 
 * @creator cydric
 * @create-time 2010-11-29 下午08:07:56
 * @revision $Id
 **/
public abstract class BaseBeanHome {
	/**
	 * bean的存储地
	 */
	private Map<String, Object> beans = new HashMap<String, Object>();

	/**
	 * 注册bean
	 * @param name
	 * @param bean
	 */
	public synchronized void put(String name, Object bean) {
		beans.put(name, bean);
	}

	/**
	 * 获取实例
	 * @param name
	 * @return
	 */
	public Object get(String name) {
		return beans.get(name);
	}

	/**
	 * 获取实例大小
	 * @return
	 */
	public int getSize() {
		return beans.size();
	}

	/**
	 * bean是否存在
	 * @param beanName
	 * @return
	 */
	public boolean beanExist(String beanName) {
		return beans.containsKey(beanName);
	}

	/**
	 * 通过实例检查bean是否存在
	 * @param bean
	 * @return
	 */
	public boolean beanExist(Object bean) {
		return beans.containsValue(bean);
	}

	/**
	 * 清除bean实例
	 */
	public synchronized void clean() {
		beans.clear();
	}

	/**
	 * 删除bean
	 * @param beanName
	 */
	public synchronized void remove(String beanName) {
		beans.remove(beanName);
	}

	/**
	 * 修改bean
	 * @param beanName
	 * @param bean
	 */
	public synchronized void update(String beanName, Object bean) {
		beans.put(beanName, bean);
	}
}
