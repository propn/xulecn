import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Ctx {

	public static final ThreadLocal<Map<String, Connection>> ctx = new ThreadLocal<Map<String, Connection>>();

	public static Connection getConnection(String jndiName) throws Exception {
		Map<String, Connection> cache = ctx.get();

		Connection conn = null;

		if (null == cache) {
			cache = Collections
					.synchronizedMap(new HashMap<String, Connection>());
			conn = getConn(jndiName);
			cache.put(jndiName, conn);
			ctx.set(cache);
		} else {
			conn = cache.get(jndiName);
			if (null == conn) {
				conn = getConn(jndiName);
				cache.put(jndiName, conn);
			}
		}
		conn.setAutoCommit(false);
		return conn;
	}

	/**
	 * 
	 * @param jndiName
	 * @return
	 * @throws Exception
	 */
	private static Connection getConn(String jndiName) throws Exception {

		return ServiceLocator.getInstance().getDataSource(jndiName)
				.getConnection();
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
