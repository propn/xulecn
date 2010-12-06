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
 * Title:�ļ���ȡ��
 * Description: �ļ���ȡ 
 * </pre>
 * @author caozj  cao.zhijun3@zet.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class ProptiesFileRead {
	/**
	 * ��ȡ�����ļ�
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
	 * ��ȡproperties�ļ�KEY
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
