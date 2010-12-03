package org.leixu.ioc.parse;

import org.dom4j.Document;
import org.leixu.ioc.bean.core.BaseBeanHome;
import org.leixu.ioc.factories.ThreadCounter;

/**
 * 解析jegg配置文件的线程
 * 
 * @creator cydric
 * @create-time 2010-11-27 下午08:18:37
 * @revision $Id
 **/
public class ParseTask implements Runnable {

	private Document document;
	private BaseBeanHome beanHome;

	public ParseTask(Document document, BaseBeanHome beanHome) {
		this.document = document;
		this.beanHome = beanHome;
	}

	private ConfigParser cp = new ConfigParser();

	public void run() {
		//处理init类型实例化
		cp.parserInitEle(document, beanHome);
		//处理ref类型的实例化
		cp.parserRef(document, beanHome);

		//自增
		ThreadCounter.incCurrentThread();
	}

}
