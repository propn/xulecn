/**
 * 
 */
package org.leixu.ioc.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Administrator
 * 
 * xml操作类，涉及xml文件的读取、等操作
 *
 */
public class XmlFileUtil {
	private final static Logger LOG = Logger.getLogger(XmlFileUtil.class);

	/**
	 * 读取xml
	 * @param xmlPath
	 * @return
	 */
	public static Document read(String xmlPath) {
		Document document = DocumentHelper.createDocument();
		try {
			document = new SAXReader().read(xmlPath);
		} catch (DocumentException e) {
			LOG.error("DocumentException happened:", e);
		}
		return document;
	}

	/**
	 * 提取给定的XPath表达式对应的XML内容.
	 * 
	 * @param xpathExpression
	 * @return 字符串表示的XML内容
	 */
	public static String getStringByXPath(Document document, String xpathExpression) {
		return document.valueOf(xpathExpression);
	}

	/**
	 * 获取节点
	 * @param document
	 * @param xpathExpression
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> getNodes(Document document, String xpathExpression) {
		return document.selectNodes(xpathExpression);
	}

	/**
	 * 获取其XML编码.
	 * 
	 * @return
	 */
	public static String getEncoding(Document document) {
		return document.getXMLEncoding();
	}

	/**
	 * 获取子元素
	 * @param element
	 * @param subName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> getSubElements(Element element, String subName) {
		return element.selectNodes(subName);
	}
}
