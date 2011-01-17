package org.leixu.iwap.db;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.leixu.iwap.config.ParamsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DSCache {

	private static final Logger log = LoggerFactory.getLogger(ConnUtils.class);

	private static Map<String, DataSource> cache = Collections
			.synchronizedMap(new HashMap<String, DataSource>());
	
	private  InitialContext ic;
	private static final DSCache instance = new DSCache();

	public static DSCache getInstance() {
		return instance;
	}

	private DSCache() {
		try {
			log.debug("init DataSourceCache, Thread:{}",Thread.currentThread().getId());
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
			
			log.debug("init DataSource,dataSource:{}, Thread:{}",dataSourceName,Thread.currentThread().getId());
			
			cache.put(dataSourceName, dataSource);
		}
		
		return dataSource;
	}
}