/**
 * 
 */
package org.leixu.ioc.bean.core.manager;

import org.leixu.ioc.bean.core.BaseBeanHome;

/**
 * jegg框架定义的配置文件的管理接口，此实现类可以重写
 * 
 * @creator cydric
 * @create-time 2010-12-1 下午08:07:43
 * @revision $Id
 **/
public interface IInitJeggManager {
	/**
	 * 初始化bean
	 * @param beanHome
	 */
	void init(BaseBeanHome beanHome);
	
	/**
	 * 设置jegg配置文件的路径，如果没有设置，则默认为conf/jegg.xml。
	 * 注意：必须将自定义的配置文件放到根目录下的conf目录下，如果conf不存在，则需要创建该目录
	 * @param path
	 */
	void setConfPath(String path);
}
