/**
 * 
 */
package org.leixu.ioc.bean.core;

/**
 * @author Administrator
 * 
 * 用来维护bean的单例
 *
 */
public class SingleBeanHome extends BaseBeanHome implements IBeanHome {
	/**
	 * 应用单例模式
	 */
	private SingleBeanHome() {
		//		System.out.println("init 21");
		//		register();
		//		System.out.println("init SingleBeanHome");
	}

	private static SingleBeanHome beanHome = new SingleBeanHome();

	/**
	 * 懒汉式模式获取实例
	 * 
	 * @return
	 */
	public static SingleBeanHome getInstance() {
		return beanHome;
	}

	public void register() {
		BeanHomePool.getInstance().register(true, getInstance());
	}
}
