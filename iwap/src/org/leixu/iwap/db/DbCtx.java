package org.leixu.iwap.db;

import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.leixu.iwap.config.Constants;
import org.leixu.iwap.config.ParamsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbCtx {

	public static final ThreadLocal<Map<String, Connection>> ctx = new ThreadLocal<Map<String, Connection>>();

	private static final Logger log = LoggerFactory.getLogger(DbCtx.class);

	/**
	 * 
	 * @param dataSourceName
	 * @return
	 * @throws Exception
	 */
	public static Connection getConn(String dataSourceName) throws Exception {

		if (null == dataSourceName) {
			dataSourceName = ParamsUtils
					.getParamValue(Constants.DEFAULT_DATASOURCE);
		}

		Map<String, Connection> cache = ctx.get();

		Connection conn = null;

		if (null == cache) {
			
			cache = Collections
					.synchronizedMap(new HashMap<String, Connection>());
			
			conn = Cache.getInstance().getDataSource(dataSourceName)
					.getConnection();

			cache.put(dataSourceName, conn);
			ctx.set(cache);
			
			log.debug("init cache ");

		} else {
			
			conn = cache.get(dataSourceName);
			
			if (null == conn) {

				conn = Cache.getInstance().getDataSource(dataSourceName)
						.getConnection();

				cache.put(dataSourceName, conn);
				
				log.debug("init {} cache ", dataSourceName);
			}
		}

		conn.setAutoCommit(false);

		log.debug("getConn:{} . Thread:{} .", dataSourceName, Thread
				.currentThread().getId());

		return conn;
		
	}

	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		return getConn(null);
	}

	
	/**
	 * @throws Exception
	 * 
	 */
	public static void commit() {
		Map<String, Connection> cache = ctx.get();
		for (Connection conn : cache.values()) {
			try {
				if (!conn.isClosed()) {
					conn.commit();
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		close();
	}

	/**
	 * 
	 */
	public static void rollback() {
		Map<String, Connection> cache = ctx.get();
		for (Map.Entry<String, Connection> entry : cache.entrySet()) {
			Connection conn = entry.getValue();
			try {
				if (!conn.isClosed()) {
					conn.rollback();
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		close();
	}

	/**
	 * 
	 */
	public static void close() {
		Map<String, Connection> cache = ctx.get();

		for (Connection conn : cache.values()) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ctx.set(null);
	}
}
