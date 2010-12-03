package org.leixu.ioc.parse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.leixu.ioc.bean.core.BaseBeanHome;
import org.leixu.ioc.util.ClassUtil;
import org.leixu.ioc.util.JeggConfigUtil;
import org.leixu.ioc.util.XmlFileUtil;

/**
 * 配置文件解析
 * 
 * @creator cydric
 * @create-time 2010-11-27 下午07:50:14
 * @revision $Id
 **/
public class ConfigParser {

	private final static Logger LOG = Logger.getLogger(ConfigParser.class);

	public void parserInitEle(Document document, BaseBeanHome beanHome) {
		List<Element> inie = JeggConfigUtil.getInitClassNodes(document);
		for (Element e : inie) {
			initBeanparser(e, beanHome);
		}
	}

	/**
	 * 解析初始化的bean(init的)
	 * @param e
	 */
	protected void initBeanparser(Element e, BaseBeanHome beanHome) {
		String className = JeggConfigUtil.getClassName(e);
		String beanName = JeggConfigUtil.getBeanName(e);
		beanHome.put(beanName, ClassUtil.getInstance(className));
	}

	/**
	 * refbean解析
	 * @param document
	 */
	public void parserRef(Document document, BaseBeanHome beanHome) {
		List<Element> refEs = JeggConfigUtil.getRefNodes(document);
		for (Element e : refEs) {
			refBeanParser(e, beanHome);
		}
	}

	/**
	 * refbean解析
	 * @param e
	 */
	protected void refBeanParser(Element e, BaseBeanHome beanHome) {
		String className = JeggConfigUtil.getClassName(e);
		String beanName = JeggConfigUtil.getBeanName(e);
		Object cl = ClassUtil.getInstance(className);

		//处理properties属性
		List<Element> properties = XmlFileUtil.getSubElements(e, "property");
		for (Element pe : properties) {
			String proName = pe.attributeValue("name");
			String setMethod = "set" + Character.toUpperCase(proName.charAt(0)) + proName.substring(1);
			String proRef = pe.attributeValue("ref");

			Object instance = beanHome.get(proRef);
			Method method = null;
			;
			try {
				method = cl.getClass().getMethod(setMethod, new Class[] { instance.getClass() });
				try {
					method.invoke(cl, instance);
				} catch (IllegalArgumentException e1) {
					LOG.error("IllegalArgumentException", e1);
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					LOG.error("IllegalAccessException", e1);
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					LOG.error("InvocationTargetException", e1);
					e1.printStackTrace();
				}
			} catch (SecurityException e1) {
				LOG.error("SecurityException", e1);
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				LOG.error("NoSuchMethodException,method name is:" + method.getName(), e1);
				e1.printStackTrace();
			}
		}
		beanHome.put(beanName, cl);
	}
}
