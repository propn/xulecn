package org.leixu.iwap.db;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.leixu.iwap.config.ParamsUtils;

public class Cache {

	private InitialContext ic;

	private Map<String, DataSource> cache = Collections
			.synchronizedMap(new HashMap<String, DataSource>());

	private static final Cache instance = new Cache();

	public static Cache getInstance() {
		return instance;
	}

	private Cache() {
		try {
			ic = new InitialContext();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	DataSource getDataSource(String dataSourceName) throws Exception {
		DataSource dataSource = (DataSource) cache.get(dataSourceName);
		if (dataSource == null) {
			if (Boolean.valueOf(ParamsUtils.getParamValue("debug"))) {
				dataSource = BoneCpUtils.getDataSource(dataSourceName);
			} else {
				dataSource = (DataSource) ic.lookup(dataSourceName);
			}
			cache.put(dataSourceName, dataSource);
		}
		return dataSource;
	}
}