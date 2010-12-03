package org.leixu.ioc.application;

import org.apache.log4j.Logger;
import org.leixu.ioc.bean.core.manager.BeanConfigRelationshipCtrollor;
import org.leixu.ioc.bean.core.manager.IBeanManager;
import org.leixu.ioc.bean.core.manager.impl.BeanManagerImpl;
import org.leixu.ioc.bean.core.manager.impl.MultithreadInitManagerImpl;

/**
 * ApplicationFactory的工厂，用于提供bean实例，包括beans的初始化等
 * 
 * @creator cydric
 * @create-time 2010-11-27 下午04:47:42
 * @revision $Id
 **/
public class ApplicationFactory {
	private final static Logger LOG = Logger.getLogger(ApplicationFactory.class);
	private static IBeanManager beanManager = null;

	public static void main(String[] args) {
		start();
		//		DoManager dm = (DoManager)get("dom");
		//		dm.sys();
		//		Group g = dm.getG();
		//		g.sys();
	}

	/**
	 * 启动框架
	 */
	public static void start() {
		LOG.info("jegg start init!");
		new BeanConfigRelationshipCtrollor(new MultithreadInitManagerImpl()).start();
		//初始化
		beanManager = new BeanManagerImpl();
		LOG.info("jegg init sucessful!");
	}

	/**
	 * 重新加载所有bean
	 */
	public static void reloadAll(String configPath) {
		//清除
		beanManager.clean();

		//重新初始化
		start();
	}

	/**
	 * 重新加载，暂时不处理
	 * @param beanName
	 */
	public static void reload(String beanName) {
		beanManager.reload(beanName);
	}

	/**
	* 获取实例
	* @param name
	* @return
	*/
	public static Object get(String name) {
		return beanManager.get(name);
	}

	/**
	 * 关闭jegg
	 */
	public static void close() {
		//清除
		beanManager.clean();
		beanManager = null;
		LOG.info("jegg closed!");
	}
}
