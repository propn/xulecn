package org.leixu.ioc.util;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * Jegg配置文件工具类
 * 
 * @creator cydric
 * @create-time 2010-11-27 下午07:09:13
 * @revision $Id
 **/
public class JeggConfigUtil {
	/**
	 * 获取对应bean
	 * 
	 * @param document
	 * @param beanNane
	 * @return
	 */
	public static Element getBean(Document document, String beanName) {
		return XmlFileUtil.getNodes(document, "//bean[@name='" + beanName + "']").get(0);
	}

	/**
	 * 获取非初始化的类
	 * 
	 * @param document
	 * @param beanNane
	 * @return
	 */
	public static List<Element> getInitClassNodes(Document document) {
		return XmlFileUtil.getNodes(document, "//bean[@init='true']");
	}

	/**
	 * 获取含有Ref属性的节点元素
	 * 
	 * @param document
	 * @return
	 */
	public static List<Element> getRefNodes(Document document) {
		return XmlFileUtil.getNodes(document, "//bean[property]");
	}

	/**
	 * 获取Property属性节点
	 * @param element
	 * @return
	 */
	public static List<Element> getPropertiesNodes(Element element) {
		return XmlFileUtil.getSubElements(element, "properties");
	}

	/**
	 * 获取Properties的name属性
	 * @param bean
	 * @return
	 */
	public static String getPropertiesName(Element bean) {
		return bean.attributeValue("name");
	}

	/**
	 * 获取Properties的ref属性值
	 * @param bean
	 * @return
	 */
	public static String getPropertiesRef(Element bean) {
		return bean.attributeValue("ref");
	}

	/**
	 * 获取类路径
	 * 
	 * @param bean
	 * @return
	 */
	public static String getClassName(Element bean) {
		return bean.attributeValue("class").trim();
	}

	/**
	 * 获取bean名称
	 * 
	 * @param bean
	 * @return
	 */
	public static String getBeanName(Element bean) {
		return bean.attributeValue("name").trim();
	}

	/**
	 * 获取init属性值
	 * 
	 * @param document
	 * @param beanName
	 * @return
	 */
	public static String getInitArr(Document document, String beanName) {
		return getBean(document, beanName).attributeValue("init").trim();
	}

	/**
	 * 判断是否为initbean
	 * @param bean
	 * @return
	 */
	public static boolean isInitBean(Element bean) {
		return !StringUtil.isEmpty(bean.attributeValue("init").trim());
	}

	/**
	 * 获取指定bean的class名称
	 * 
	 * @param document
	 * @param beanName
	 * @return
	 */
	public static String getClassName(Document document, String beanName) {
		return getClassName(getBean(document, beanName)).trim();
	}

	/**
	 * 获取import元素
	 * 
	 * @param document
	 * @return
	 */
	public static List<Element> importArrParse(Document document) {
		return XmlFileUtil.getNodes(document, "//import");
	}

	/**
	 * 获取import对应的resource属性值
	 * @param importElement
	 * @return
	 */
	public static String getImportResourceValue(Element importElement) {
		return importElement.attributeValue("resource");
	}
}
