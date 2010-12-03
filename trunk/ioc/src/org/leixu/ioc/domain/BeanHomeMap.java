package org.leixu.ioc.domain;

import org.leixu.ioc.bean.core.IBeanHome;

/**
 * 用于存储BeanHome
 * 
 * @creator cydric
 * @create-time 2010-11-30 下午10:26:54
 * @revision $Id
 **/
public class BeanHomeMap {
	private String beanNameClassName;
	private IBeanHome beanHome;

	public BeanHomeMap(String beanNameClassName, IBeanHome beanHome) {
		this.beanNameClassName = beanNameClassName;
		this.beanHome = beanHome;
	}

	public String getBeanNameClassName() {
		return beanNameClassName;
	}

	public IBeanHome getBeanHome() {
		return beanHome;
	}

	public String toString() {
		return "beanNameClassName is:" + beanNameClassName + ", beanHome is:" + beanHome;
	}
}
