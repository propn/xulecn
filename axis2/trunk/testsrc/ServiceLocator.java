import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
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

	private Map cache = Collections.synchronizedMap(new HashMap());

	private static final ServiceLocator instance = new ServiceLocator();

	public static ServiceLocator getInstance() {
		return instance;
	}

	private ServiceLocator() {
		try {
			ic = new InitialContext();
		} catch (Exception e) {
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

}