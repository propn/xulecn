package com.ztesoft.crm.business.common.sem.kernel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;





public class BeanParserHelper {
	public static void checkMapUsedInfo(Map map) {
		Set mapEntries = map.entrySet();
		
		Iterator it=mapEntries.iterator();
		
		while (it.hasNext()) {
			
			Entry mapEntry=(Entry)it.next();
			
			if (!Boolean.TRUE.equals(mapEntry.getValue())&&!Boolean.FALSE.equals(mapEntry.getValue())) {
				

			}
		}
	}
	//校验XML文件是否是有效
	public static void validateXml(XmlObject xml) {
		boolean isXmlValid = false;

		ArrayList validationMessages = new ArrayList();

		isXmlValid = xml.validate(new XmlOptions()
				.setErrorListener(validationMessages));

		
		if (!isXmlValid) {
			throw new BeanParseException(validationMessages.toString());
		}
	}
	

}
