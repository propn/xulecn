/**
 * 
 */
package org.leixu.ioc.bean.core.manager;

/**
 * Bean管理接口
 * 
 * @creator cydric
 * @create-time 2010-12-1 下午09:17:42
 * @revision $Id
 **/
public interface IBeanManager {
	/**
	 * 重新载入
	 */
	void clean();
	
	/**
	 * 重新载入制定bean
	 * @param beanName
	 */
	void reload(String beanName);
	
	/**
	 * 获取bean实例
	 * @param name
	 * @return
	 */
	Object get(String name);
	
	/**
	 * 获取bean的数目
	 * @return
	 */
	public int getSize();
}
