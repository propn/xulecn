/**
 * 
 */
package org.leixu.ioc.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * bean有效性映射
 * 
 * @creator cydric
 * @create-time 2010-11-30 下午10:30:03
 * @revision $Id
 **/
public class BeanEffectiveMap {
	/**
	 * 存储有效性的list
	 */
	private List<Boolean> effectiveList = new ArrayList<Boolean>();

	/**
	 * 存储实例的Map
	 */
	private List<BeanHomeMap> beanHomeList = new ArrayList<BeanHomeMap>();

	/**
	 * 添加方法
	 * @param effective
	 * @param bean
	 */
	public void add(boolean isEffective, BeanHomeMap bean) {
		effectiveList.add(isEffective);
		beanHomeList.add(bean);
	}

	public List<Boolean> getEffectiveList() {
		return effectiveList;
	}

	public List<BeanHomeMap> getBeanHomeList() {
		return beanHomeList;
	}
}
