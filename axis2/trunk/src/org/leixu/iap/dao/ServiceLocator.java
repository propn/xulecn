package org.leixu.iap.dao;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * This class is an implementation of the Service Locator pattern. It is used to
 * looukup resources such as EJBHomes, JMS Destinations, etc. This
 * implementation uses the "singleton" strategy and also the "caching" strategy.
 * This implementation is intended to be used on the web tier and not on the ejb
 * tier.
 */
public class ServiceLocator {

	private InitialContext ic;

	// used to hold references to EJBHomes/JMS Resources for re-use
	private Map cache = Collections.synchronizedMap(new HashMap());

	private static ServiceLocator instance = new ServiceLocator();

	public static ServiceLocator getInstance() {
		return instance;
	}

	private ServiceLocator() {
		try {
			ic = new InitialContext();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method obtains the datasource itself for a caller
	 * 
	 * @return the DataSource corresponding to the name parameter
	 */
	public DataSource getDataSource(String dataSourceName) throws Exception {
		DataSource dataSource = (DataSource) cache.get(dataSourceName);
		if (dataSource == null) {
			dataSource = (DataSource) ic.lookup(dataSourceName);
			cache.put(dataSourceName, dataSource);
		}
		return dataSource;
	}

	public DataSource getDataSource(String dataSourceName, Object callerObj)
			throws Exception {
		DataSource dataSource = (DataSource) cache.get(dataSourceName);
		if (dataSource == null) {
			dataSource = (DataSource) ic.lookup(dataSourceName);
			cache.put(dataSourceName, dataSource);
		}
		return dataSource;
	}

	/**
	 * @return the URL value corresponding to the env entry name.
	 */
	public URL getUrl(String envName) throws Exception {
		return (URL) ic.lookup(envName);
	}

	/**
	 * @return the boolean value corresponding to the env entry such as
	 *         SEND_CONFIRMATION_MAIL property.
	 */
	public boolean getBoolean(String envName) throws Exception {
		return ((Boolean) ic.lookup(envName)).booleanValue();
	}

	/**
	 * @return the String value corresponding to the env entry name.
	 */
	public String getString(String envName) throws Exception {
		return (String) ic.lookup(envName);
	}

	/**
	 * getCatchList
	 * 
	 * @param jndiName
	 *            String
	 * @return Map
	 */
	public Map getCatchList(String jndiName) {
		Map catchListMap = null;
		try {
			catchListMap = (Map) ic.lookup(jndiName);
		} catch (NamingException ex) {
			// Debug.print("未能获取缓存列表" + jndiName + "：" + ex.toString(), this);
		}
		return catchListMap;
	}

	/**
	 * setCatchList
	 * 
	 * @param jndiName
	 *            String
	 * @param catchListMap
	 *            Map
	 * @return boolean
	 */
	public boolean setCatchList(String jndiName, Map catchListMap) {
		try {
			ic.rebind(jndiName, catchListMap);
		} catch (NamingException ex) {
			// Debug.print("绑定缓存列表时出错：" + ex.toString(), this);
			return false;
		}
		return true;
	}

}