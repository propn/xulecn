package util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * <pre>
 * Title:文件读取类
 * Description: 文件读取 
 * </pre>
 * @author caozj  cao.zhijun3@zet.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ProptiesFileRead {
	/**
	 * 读取配置文件
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Map getProperties(String fileName) throws Exception {
		Map retMap = new HashMap();
		FileInputStream fs = null;
		try {
			Properties ps = new Properties();

			fs = new FileInputStream(new File(fileName));
			ps.load(fs);
			for (Enumeration e = ps.propertyNames(); e.hasMoreElements();) {
				String key = (String) e.nextElement();
				String putkey = new String(key.getBytes("ISO8859-1"), "GBK");
				String str = ps.getProperty(key);
				String value = new String(str.getBytes("ISO8859-1"), "GBK");
				retMap.put(putkey, value);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			fs.close();
		}

		return retMap;

	}

	/**
	 * 读取properties文件KEY
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static List getPropertiesKey(String fileName) throws Exception {
		List retList = new ArrayList();
		FileInputStream fs = null;
		try {
			Properties ps = new Properties();

			fs = new FileInputStream(new File(fileName));
			ps.load(fs);
			for (Enumeration e = ps.propertyNames(); e.hasMoreElements();) {
				String key = (String) e.nextElement();
				key = new String(key.getBytes("ISO8859-1"), "GBK");
				retList.add(key);

			}
		} catch (Exception e) {
			throw e;
		} finally {
			fs.close();
		}

		return retList;

	}
}
