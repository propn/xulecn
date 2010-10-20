package org.leixu.iap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

/**
 * Connection 管理上下文
 * 
 * @author easonwu 2009-12-22
 * 
 */
public class ConnectionContext {
	// public static final String DEFAULT_JNDINAME = "jdbc/CRM";
	public static final String DEFAULT_JNDINAME = Constants.DEFAULT_DATASOURCE;

	public static final String RUN_MODE = "develop";// develop 开发模式 product 生产模式

	private static ThreadLocal connectionContext = new ConnectionContextThreadLocal();

	static {
		// DAOUtils.systempInit() ;//先进行系统是否初始化处理
	}

	private Map context;

	public ConnectionContext(Map context) {
		this.context = context;
	}

	public void setConnection(String jndiName, Connection anCon) {
		put(jndiName, anCon);
	}

	public void setConnection(Connection anCon) {
		setConnection(DEFAULT_JNDINAME, anCon);
	}

	public Connection getConnection() {
		return getConnection(DEFAULT_JNDINAME);
	}

	/**
	 * 获取Connection
	 * 
	 * @param aDBName
	 * @return
	 * @throws FrameException
	 */
	public Connection getConnection(String aDBName) {
		aDBName = (aDBName == null || "".equals(aDBName)) ? DEFAULT_JNDINAME
				: aDBName;
		Connection conn = null;
		// Closed Connection Exception 2010-09-12
		if (context.get(aDBName) != null) {
			conn = (Connection) context.get(aDBName);
			try {
				if (conn != null && !conn.isClosed())
					return conn;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("根据dbName=" + aDBName
						+ "查询到无效数据库连接,close时报错！");
			}
			this.context.remove(aDBName);
		}

		try {
			conn = getDBConnection(aDBName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (conn != null) {
			setConnection(aDBName, conn);
		} else {
			throw new RuntimeException("根据dbName=" + aDBName + ",获取数据库连接失败！");
		}
		System.out.println(aDBName + " get conn ======" + conn);
		return conn;
	}

	/**
	 * close connection
	 * 
	 * @param aDBName
	 * @return
	 * @throws SQLException
	 * @throws FrameException
	 */
	public boolean closeConnection(String aDBName) throws SQLException {
		Connection conn = getConnection(aDBName);

		System.out.println(aDBName + " close conn ======" + conn);
		if (conn == null)
			return false;
		// Closed Connection Exception 2010-09-12
		if (!conn.isClosed()) {
			conn.close();
		}
		this.context.remove(aDBName);
		conn = null;
		return true;
	}

	/**
	 * commit operation
	 * 
	 * @param aDBName
	 * @return
	 * @throws SQLException
	 * @throws FrameException
	 */
	public boolean commit(String aDBName) throws SQLException {
		Connection conn = getConnection(aDBName);
		if (conn == null)
			return false;
		// Closed Connection Exception 2010-09-12
		if (!conn.isClosed()) {
			conn.commit();
		}
		System.out.println(aDBName + " commit conn ======" + conn);
		return true;
	}

	/**
	 * batch commit for multi db evn
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean allCommit() throws SQLException {
		if (this.context == null || this.context.isEmpty())
			return false;
		Iterator it = this.context.keySet().iterator();
		while (it.hasNext()) {
			String dbName = (String) it.next();
			System.out.println("db.commit===========" + dbName);
			Connection conn = (Connection) context.get(dbName);
			// Closed Connection Exception 2010-09-12
			if (!conn.isClosed()) {
				conn.commit();
			}
		}
		return true;
	}

	/**
	 * batch rollback for multi db evn
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean allRollback() throws SQLException {
		if (this.context == null || this.context.isEmpty())
			return false;
		Iterator it = this.context.keySet().iterator();
		while (it.hasNext()) {
			String dbName = (String) it.next();
			System.out.println("db.rollback===========" + dbName);
			Connection conn = (Connection) context.get(dbName);
			// Closed Connection Exception 2010-09-12
			if (!conn.isClosed()) {
				conn.rollback();
			}

		}
		return true;
	}

	/**
	 * batch close conn
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean allCloseConnection() throws SQLException {
		if (this.context == null || this.context.isEmpty())
			return false;
		Iterator it = this.context.keySet().iterator();
		while (it.hasNext()) {
			String dbName = (String) it.next();

			System.out.println("db.close===========" + dbName);
			Connection conn = (Connection) context.get(dbName);
			// Closed Connection Exception 2010-09-12
			if (!conn.isClosed()) {
				conn.close();
			}
			conn = null;
		}
		this.context.clear();
		return true;
	}

	/**
	 * rollback operation
	 * 
	 * @param aDBName
	 * @return
	 * @throws SQLException
	 * @throws FrameException
	 */
	public boolean rollback(String aDBName) throws SQLException {
		Connection conn = getConnection(aDBName);
		if (conn == null)
			return false;
		// Closed Connection Exception 2010-09-12
		if (!conn.isClosed()) {
			conn.rollback();
		}
		System.out.println(aDBName + " rollback conn ======" + conn);
		return true;
	}

	private void put(String key, Object value) {
		context.put(key, value);
	}

	private static class ConnectionContextThreadLocal extends ThreadLocal {
		protected Object initialValue() {
			return new ConnectionContext(new HashMap());
		}
	}

	/**
	 * 判断是否存在上下文，true : 存在 , false :不存在
	 * 
	 * @return
	 */
	public static boolean checkConnectionContext() {
		return connectionContext.get() != null
				&& ((ConnectionContext) connectionContext.get())
						.getContextMap() != null;
	}

	public static ConnectionContext getContext() {

		ConnectionContext context = (ConnectionContext) connectionContext.get();

		if (context == null) {
			context = new ConnectionContext(new HashMap());
			setContext(context);
		}

		return context;
	}

	public Map getContextMap() {
		return this.context;
	}

	public static void setContext(ConnectionContext context) {
		connectionContext.set(context);
	}

	/**
	 * @param :dataSourceName 数据源名字
	 */
	private static DataSource getDataSource(String dataSourceName)
			throws Exception {
		String jndiName = ParamsUtils.getParamValue(dataSourceName + "_JNDI");
		System.out.print("dbName===" + dataSourceName + ",jndiName==="
				+ jndiName);
		DataSource ds = (DataSource) ServiceLocator.getInstance()
				.getDataSource(jndiName);
		System.out.print(",ds===" + ds + "\n");
		return ds;
	}

	/**
	 * @param :dataSourceName 数据源名字， callerObj 调用者句柄
	 */
	private static DataSource getDataSource(String dataSourceName,
			Object callerObj) throws Exception {
		return (DataSource) ServiceLocator.getInstance().getDataSource(
				ParamsUtils.getParamValue(dataSourceName + "_JNDI"), callerObj);
	}

	/**
	 * @param :source 数据源名字
	 */
	private static Connection getDBConnection(String source) throws Exception {
		Connection conn = null;
		try {
			// 为了方便测试设置自动切换连接方式，实际环境可以关闭这些判断代码。
			String DIRECT_CONNECTION = ParamsUtils
					.getParamValue("DIRECT_CONNECTION");
			if (DIRECT_CONNECTION != null
					&& "true".equalsIgnoreCase(DIRECT_CONNECTION)) {
				conn = getDirectConnection(source);
			} else {
				DataSource ds = getDataSource(source);
				if (ds != null) {
					conn = ds.getConnection();
				} else {
					System.out.println("\n\n\nDataSource == " + ds + "\n\n\n");
				}
				// conn = getDataSource(source).getConnection();
			}
			conn.setAutoCommit(false);
		} catch (SQLException se) {
			throw new Exception("SQL Exception while getting "
					+ "DB connection : \n" + se);
		} catch (Exception e) {
			System.out.println("\n\n\nconn == " + conn + "\n\n\n");
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 直接使用JDBC的方式获取当前数据库的连接.
	 * 
	 * @return
	 */
	private static Connection getDirectConnection(String dbName)
			throws Exception {
		Connection connection = null;
		try {
			dbName = dbName.toUpperCase();
			String driver = ParamsUtils.getParamValue(dbName + "_Driver");

			String url = ParamsUtils.getParamValue(dbName + "_DBUrl");
			String username = ParamsUtils.getParamValue(dbName + "_DBUser");
			String password = ParamsUtils.getParamValue(dbName + "_DBPwd");

			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new Exception("SQL Exception while init connection \n" + e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("SQL Exception while get connection \n" + e);
		}

		return connection;
	}

}
