/**
 * 
 */
package org.leixu.iap.dao;

import java.util.Properties;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author xulei xu.lei55@zet.com.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ConfigUtils {
	
	private volatile Properties property = null;

	public Properties getProperty() throws Exception {

		if (property == null) {
			synchronized (this) {
				if (property == null) {
					property = new Properties();
					property.load(ClassLoader
									.getSystemResourceAsStream(Constants.PROPERTIES_FILE_NAME));
				}
			}
		}
		return property;

	}

	public String getProperty(String key) throws Exception {
		return getProperty().getProperty("DEFAULT_JNDI");
	}

	public static void main(String[] args) {
		try {
			System.out.println(new ConfigUtils().getProperty("DEFAULT_JNDI"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
