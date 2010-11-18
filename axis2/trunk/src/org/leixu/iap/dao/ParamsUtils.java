package org.leixu.iap.dao;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ParamsUtils {
	private static final Log logger = LogFactory.getLog(ParamsUtils.class);
	private static final Properties property = new Properties();

	static {
		try {
			property.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(Constants.PROPERTIES_FILE_NAME));
		} catch (Exception e) {
			logger.error(Constants.PROPERTIES_FILE_NAME + "");
		}
	}

	public static Properties getProperty() {
		return property;
	}

	public static String getParamValue(String key) throws Exception {
		return getProperty().getProperty(key);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(getParamValue("DEFAULT_JNDI"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// private Map selectSqls = Collections.synchronizedMap(new HashMap());
	// public Map executeSelect(final TableConfig tableConfig, Map keys) {
	// PreparedSql psql = null;
	// synchronized(selectSqls) {
	// if (selectSqls.get(tableConfig.getId()) == null) {
	// selectSqls.put(tableConfig.getId(), constructSelectSql(tableConfig));
	// }
	// psql = (PreparedSql) selectSqls.get(tableConfig.getId());
	// }
	//
	// List result = executeSql(...);
	//        
	// return result.isEmpty() ? null : (Map) result.get(0);
	// }

}
