package com.ztesoft.vsop.order;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.xerces.parsers.SAXParser;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.ztesoft.vsop.StringUtil;

public class XMLUtils {
	private static final boolean VALIDATE = false;
	private static final String ENCODING = "UTF-8";

	public static Document parse(String inXML) throws DocumentException {
		SAXReader saxReader = new SAXReader(VALIDATE);
		// saxReader.setEncoding(ENCODING);
		StringReader reader = new StringReader(inXML);
		Document doc = saxReader.read(reader);
		return doc;
	}

	public static String getElementStringValue(Element root, String nodeName) {
		Element el = root.element(nodeName);
		if (el != null) {
			String elValue = el.getStringValue(); // 如果为空的话，老是返回 "null"
			if (null == elValue || "".equals(elValue) || "null".equals(elValue))
				return "";
			else
				return elValue;
		}
		return "";
	}

	/**
	 * 获取指定nodeName的值的List,如: <root> <info> <eleA>123</eleA> <eleB>456</eleB>
	 * </info> <info> <eleA>789</eleA> <eleB>222</eleB> </info> </root>
	 * 
	 * getElementStringValueList(root, "info", "eleA"); 获取到的List包含["123", "789];
	 * 
	 * @param root
	 * @param parentNodeName
	 * @param nodeName
	 * @return
	 */
	public static List getElementStringValueList(Element root,
			String parentNodeName, String nodeName) {
		List list = new ArrayList();
		Element foo;
		String nv = "";
		for (Iterator iterator = root.elementIterator(parentNodeName); iterator
				.hasNext();) {
			foo = (Element) iterator.next();
			nv = foo.elementText(nodeName);
			if (nv != null && nv.equalsIgnoreCase("null")) {
				list.add("");
			} else if (nv != null && !nv.equalsIgnoreCase("null")) {
				list.add(nv);
			} else {
				list.add("");
			}
		}
		return list;
	}
	
//	public static String getSingleTagValueUsePattern(String xml,String tagName){
//		if(tagName == null || (tagName != null && "".equals(tagName.trim()))){
//			return "";
//		}
//		String result  = "";
//		StringBuffer bf = new StringBuffer();
//		bf.append("<").append(tagName).append(">(.*?)</").append(tagName).append(">");
//		Pattern pattern = Pattern.compile(bf.toString());
//		Matcher matcher = pattern.matcher(xml);
//		if(matcher.find()){
//			result = matcher.group(1);
//		}
//		return result;
//	}
	
	public static String getSingleTagValue(String xml,String tagName){
		String result = StringUtil.getInstance().getTagValue(tagName, xml);
		if(result == null)  result = "";
		return result;
	}

}
