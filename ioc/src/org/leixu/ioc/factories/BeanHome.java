/**
 * 
 */
package org.leixu.ioc.factories;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * 
 * 用来维护bean的单例
 *
 */
public class BeanHome {
	private static Map<String, Object> beans = new HashMap<String, Object>();

	/**
	 * 注册bean
	 * @param name
	 * @param bean
	 */
	protected synchronized static void put(String name, Object bean) {
		beans.put(name, bean);
	}

	/**
	 * 获取实例
	 * @param name
	 * @return
	 */
	protected static Object get(String name) {
		return beans.get(name);
	}

	/**
	 * 获取实例大小
	 * @return
	 */
	protected static int getSize() {
		return beans.size();
	}

	/**
	 * bean是否存在
	 * @param beanName
	 * @return
	 */
	protected static boolean beanExist(String beanName) {
		return beans.containsKey(beanName);
	}

	/**
	 * 通过实例检查bean是否存在
	 * @param bean
	 * @return
	 */
	protected static boolean beanExist(Object bean) {
		return beans.containsValue(bean);
	}

	/**
	 * 清除bean实例
	 */
	protected synchronized static void clean() {
		beans.clear();
	}

	/**
	 * 删除bean
	 * @param beanName
	 */
	protected synchronized static void remove(String beanName) {
		beans.remove(beanName);
	}

	/**
	 * 修改bean
	 * @param beanName
	 * @param bean
	 */
	protected synchronized static void update(String beanName, Object bean) {
		beans.put(beanName, bean);
	}
}
