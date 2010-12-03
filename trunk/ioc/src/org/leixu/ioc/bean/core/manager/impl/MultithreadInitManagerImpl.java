/**
 * 
 */
package org.leixu.ioc.bean.core.manager.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.leixu.ioc.application.ApplicationFactory;
import org.leixu.ioc.bean.core.BaseBeanHome;
import org.leixu.ioc.bean.core.manager.IInitJeggManager;
import org.leixu.ioc.factories.ThreadCounter;
import org.leixu.ioc.parse.ParseTask;
import org.leixu.ioc.util.JeggConfigUtil;
import org.leixu.ioc.util.XmlFileUtil;


/**
 * 
 * 
 * @creator cydric
 * @create-time 2010-12-1 下午08:24:53
 * @revision $Id
 **/
public class MultithreadInitManagerImpl extends BaseInitManagerImpl implements IInitJeggManager {
	private final static Logger LOG = Logger.getLogger(ApplicationFactory.class);
	private final ExecutorService exec = Executors.newCachedThreadPool();

	private int initThreadSize = 0;

	/**
	 * 用于存储bean的beanHome
	 */
	private BaseBeanHome beanHome;

	public void init(BaseBeanHome beanHome) {
		this.beanHome = beanHome;

		LOG.info("----------------jegg begin init!");
		long begin = System.currentTimeMillis();
		refindImport(defaultConfPath);

		//设置初始值
		ThreadCounter.setInitThreadSize(initThreadSize);

		//如果子线程没有执行完毕，则主线程循环等待
		while (!ThreadCounter.isFinished()) {
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				LOG.error(" main thread is interrupted , initThreadSize is:" + initThreadSize, e);
				e.printStackTrace();
			}
		}

		exec.shutdownNow();
		LOG.info("-----------------jegg init end! use: " + (System.currentTimeMillis() - begin) + " ms");
	}

	/**
	 * 递归处理document
	 * @param configPath
	 */
	private void refindImport(String configPath) {
		initThreadSize++;

		Document document = XmlFileUtil.read(configPath);
		exec.execute(new ParseTask(document, beanHome));
		List<Element> imports = JeggConfigUtil.importArrParse(document);
		if (0 != imports.size()) {
			for (Element e : imports) {
				String resourcePath = JeggConfigUtil.getImportResourceValue(e).substring(1);
				String rootPath = configPath.substring(0, configPath.lastIndexOf("/"));
				refindImport(rootPath + resourcePath);
			}
		}
	}

	@Override
	public void setConfPath(String path) {
		super.setConfPath(path);
	}

}
