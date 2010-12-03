/**
 * 
 */
package org.leixu.ioc.bean.core.manager.impl;

import org.leixu.ioc.bean.core.BaseBeanHome;
import org.leixu.ioc.bean.core.BeanHomePool;
import org.leixu.ioc.bean.core.manager.IBeanManager;

/**
 * 
 * 
 * @creator cydric
 * @create-time 2010-12-1 下午09:20:58
 * @revision $Id
 **/
public class BeanManagerImpl implements IBeanManager {
	private BaseBeanHome beanHome = BeanHomePool.getInstance().getEffectiveBeanHome();

	public void clean() {
		beanHome.clean();
	}

	public void reload(String beanName) {
		//		if(!beanHome.beanExist(beanName)){
		//			return ;
		//		}
		//		beanHome.put(name, bean);
	}

	public Object get(String name) {
		return beanHome.get(name);
	}

	public int getSize() {
		return beanHome.getSize();
	}

}
