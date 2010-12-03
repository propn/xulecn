/**
 * 
 */
package org.leixu.ioc.bean.core.manager.impl;

import org.leixu.ioc.util.StringUtil;

/**
 * 配置文件解析器处理基类
 * 
 * @creator cydric
 * @create-time 2010-12-2 下午10:08:26
 * @revision $Id
 **/
public abstract class BaseInitManagerImpl {
	/**
	 * 默认jegg配置文件的存储路径
	 */
	protected String defaultConfPath = "conf/jegg.xml";
	
	public void setConfPath(String path) {
		if(!StringUtil.isEmpty(path)){
			this.defaultConfPath = path;
		}
	}
}
