/**
 * 
 */
package org.leixu.ioc.bean.core.manager;

import org.leixu.ioc.bean.core.BaseBeanHome;
import org.leixu.ioc.bean.core.BeanHomePool;
import org.leixu.ioc.bean.core.SingleBeanHome;

/**
 * 连接beanHome与配置文件的控制器
 * 
 * @creator cydric
 * @create-time 2010-12-1 下午08:10:43
 * @revision $Id
 **/
public class BeanConfigRelationshipCtrollor {
	IInitJeggManager configManger;
	public BeanConfigRelationshipCtrollor(IInitJeggManager configManger){
		this.configManger = configManger;
	}
	
	/**
	 * 启动整个框架的初始化
	 */
	public void start(){
		//现行注册
		prepare();
		
		BaseBeanHome beanHome = BeanHomePool.getInstance().getEffectiveBeanHome();
		configManger.init(beanHome);
	}
	
	/**
	 * 准备工作，主要用于BeanHome注册，此方法可以覆盖
	 */
	protected void prepare(){
		BeanHomePool.getInstance().prepare(SingleBeanHome.getInstance());
	}
}
