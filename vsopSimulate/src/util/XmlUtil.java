package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class XmlUtil {
	/**
	 * 格式化字符串为标准XML格式
	 * @param sourcStr	无规则源字符串
	 * @param
	 * @return	格式化后标准XML格式字符串
	 */
	public static String formatXML(byte[] sourceByte, String encoding) throws Exception {
		if (sourceByte.length == 0) {
			return null;
		}
		SAXReader reader = new SAXReader();
		ByteArrayInputStream inputStream = null;
		OutputFormat formater = OutputFormat.createPrettyPrint();
		StringWriter out = new StringWriter();
		XMLWriter writer = new XMLWriter();
		Document doc;
		try {
			inputStream = new ByteArrayInputStream(sourceByte);
			doc = reader.read(inputStream);
			if (encoding != null) {
				formater.setEncoding(encoding);
			}
			writer = new XMLWriter(out, formater);
			writer.write(doc);
			String temp = out.toString();
			return temp;
		} catch (Exception e) {
			return new String(sourceByte);
			
		} finally {
			try {
				IOUtils.closeQuietly(inputStream);
				IOUtils.closeQuietly(out);
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
	}
}
