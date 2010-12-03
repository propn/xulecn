package org.leixu.ioc.bean.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.leixu.ioc.domain.BeanEffectiveMap;
import org.leixu.ioc.domain.BeanHomeMap;

/**
 * beanHome池，即管理beanHome的地方
 * 
 * @creator cydric
 * @create-time 2010-11-29 下午08:48:28
 * @revision $Id
 **/
public class BeanHomePool {
	private final static Logger LOG = Logger.getLogger(BeanHomePool.class);

	/**
	 * 应用单例模式
	 */
	private BeanHomePool() {
	}

	private static BeanHomePool beanHomePool = null;

	/**
	 * 有效的bean，只能有一个bean是有效的
	 */
	private BaseBeanHome effectiveBeanHome = null;

	private BeanEffectiveMap beanEffectiveMap = new BeanEffectiveMap();

	/**
	 * 懒汉式模式获取实例
	 * 
	 * @return
	 */
	public static BeanHomePool getInstance() {
		if (null == beanHomePool) {
			beanHomePool = new BeanHomePool();
		}

		return beanHomePool;
	}

	public void prepare(IBeanHome... beanHomes) {
		for (IBeanHome bh : beanHomes) {
			register(true, bh);
		}
	}

	/**
	 * 注册beanHome
	 * @param isEffective
	 * @param beanHome
	 */
	void register(boolean isEffective, IBeanHome beanHome) {
		String beanHomeName = beanHome.getClass().toString().trim();
		BeanHomeMap bhm = new BeanHomeMap(beanHomeName, beanHome);
		beanEffectiveMap.add(isEffective, bhm);
		LOG.info("BeanHome : " + beanHomeName + " registed successed!");
	}

	/**
	 * 获取所有的BeanHome
	 * @return
	 */
	public List<BaseBeanHome> listBaseBeanHomes() {
		List<BaseBeanHome> bbhList = new ArrayList<BaseBeanHome>();
		for (BeanHomeMap bhm : getBeanMap()) {
			bbhList.add((BaseBeanHome) bhm.getBeanHome());
		}
		return bbhList;
	}

	/**
	 * 获取有效的bean
	 * @return
	 */
	public BaseBeanHome getEffectiveBeanHome() {
		//第一次需要获取，否则直接返回
		if (null != effectiveBeanHome) {
			return effectiveBeanHome;
		}

		int beanSize = getBeanSize();

		BaseBeanHome result = null;
		if (1 == beanSize) {
			result = (BaseBeanHome) getBeanMap().get(0).getBeanHome();
		} else {
			//计数器
			int count = 0;
			BaseBeanHome bbh = null;
			for (int i = beanSize - 1; i > -1; i--) {
				if (beanSize == count++) {
					result = bbh;
				}

				BeanHomeMap bhm = getBeanMap().get(i);
				if (bhm.getBeanNameClassName().equals(SingleBeanHome.class)) {
					bbh = (BaseBeanHome) bhm.getBeanHome();
					continue;
				} else {
					if (false == getEffectiveList().get(i)) {
						continue;
					} else {
						result = (BaseBeanHome) getBeanMap().get(i).getBeanHome();
						break;
					}
				}
			}
		}
		return effectiveBeanHome = result;
	}

	/**
	 * 获取bean实例的数目
	 * @return
	 */
	public int getBeanSize() {
		return getBeanMap().size();
	}

	/**
	 * 获取bean映射列表
	 * @return
	 */
	private List<BeanHomeMap> getBeanMap() {
		return beanEffectiveMap.getBeanHomeList();
	}

	/**
	 * 获取有效性列表
	 * @return
	 */
	private List<Boolean> getEffectiveList() {
		return beanEffectiveMap.getEffectiveList();
	}

	/**
	 * 获取默认BaseBeanHome
	 * @return
	 */
	public BaseBeanHome getDefault() {
		return SingleBeanHome.getInstance();
	}

}
