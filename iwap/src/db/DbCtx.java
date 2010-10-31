package db;

import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import config.Constants;
import config.ParamsUtils;

public class DbCtx {

	public static final ThreadLocal<Map<String, Connection>> ctx = new ThreadLocal<Map<String, Connection>>();

	public static Connection getConnection(String dataSourceName) throws Exception {
		
		if(null == dataSourceName){
			dataSourceName=ParamsUtils.getParamValue(Constants.DEFAULT_DATASOURCE);
		}
		
		Map<String, Connection> cache = ctx.get();

		Connection conn = null;

		if (null == cache) {
			cache = Collections
					.synchronizedMap(new HashMap<String, Connection>());
			conn = getConn(dataSourceName);
			cache.put(dataSourceName, conn);
			ctx.set(cache);
		} else {
			conn = cache.get(dataSourceName);
			if (null == conn) {
				conn = getConn(dataSourceName);
				cache.put(dataSourceName, conn);
			}
		}
		conn.setAutoCommit(false);
		return conn;
	}

	/**
	 * 
	 * @param dataSourceName
	 * @return
	 * @throws Exception
	 */
	private static Connection getConn(String dataSourceName) throws Exception {

//		return ServiceLocator.getInstance().getDataSource(dataSourceName)
//				.getConnection();
		
		return BoneCpUtils.getConn();
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
