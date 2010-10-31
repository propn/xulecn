/**
 * 
 */
package config;

import java.util.Properties;

public class ConfigUtils {

	private volatile Properties property = null;

	public Properties getProperty() throws Exception {

		if (property == null) {
			synchronized (this) {
				if (property == null) {
					property = new Properties();
					
					System.out.println("Load properties");
					property
							.load(ClassLoader
									.getSystemResourceAsStream(Constants.PROPERTIES_FILE_NAME));
				}
			}
		}
		return property;

	}

	public static  String getProperty(String key) throws Exception {
		return new ConfigUtils().getProperty().getProperty(key);
	}

	public static void main(String[] args) {
		try {
			System.out.println(getProperty("JNDI"));
			System.out.println(getProperty("url"));
			System.out.println(getProperty("driver"));
			System.out.println(getProperty("username"));
			System.out.println(getProperty("password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
