package org.leixu.ioc.util;

import org.apache.log4j.Logger;

/**
 * class工具类
 * 
 * @creator cydric
 * @create-time 2010-11-27 下午05:32:55
 * @revision $Id
 **/
public class ClassUtil {
	private final static Logger LOG = Logger.getLogger(ClassUtil.class);

	public static Object getInstance(String className) {
		Object cls = new Object();
		;
		try {
			cls = Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			LOG.error(e);
		} catch (IllegalAccessException e) {
			LOG.error(e);
		} catch (ClassNotFoundException e) {
			LOG.error(e);
		}

		return cls;
	}
}
