package com.hwadee.demo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class TransactionHelper {

	// 使用ThreadLocal持有当前线程的数据库连接
	private final static ThreadLocal<Connection> connection_holder = new ThreadLocal<Connection>();

	// 当前是否处于事务环境
	private final static ThreadLocal<Boolean> existsTransaction = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	// 是否必须回滚
	private final static ThreadLocal<Boolean> rollbackOnly = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	// 连接配置，来自connection.properties
	private final static Properties connectionProp = new Properties();

	static {
		// 加载配置文件
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("connection.properties");
		try {

			connectionProp.load(is);
			is.close();
			// 加载驱动程序
			Class.forName(connectionProp.getProperty("driverClassName"));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("驱动未找到", e);
		}
	}

	/**
	 * 是否必须回滚
	 */
	public static boolean isRollbackOnly() {
		return rollbackOnly.get();
	}

	/**
	 * 设置当前事务环境的回滚状态
	 */
	public static void setRollbackOnly(boolean flag) {
		rollbackOnly.set(flag);
	}

	/**
	 * 当前是否存在事务
	 */
	public static boolean existsTransaction() {
		return existsTransaction.get();
	}

	// 设置当前事务环境
	private static void setExistsTransaction(boolean flag) {
		existsTransaction.set(flag);
	}

	/**
	 * 开始一个事务
	 */
	public static void beginTransaction() {
		Connection conn = createNotAutoCommitConnection();
		connection_holder.set(conn);
		setExistsTransaction(Boolean.TRUE);
	}

	// 获取当前线程中的数据库连接
	private static Connection getCurrentConnection() {
		return connection_holder.get();
	}

	// 执行SQL语句
	public static int executeNonQuery(String sql) throws SQLException {

		Connection conn = getCurrentConnection();

		return conn.createStatement().executeUpdate(sql);

	}

	/**
	 * 提交事务
	 */
	public static void commit() {
		Connection conn = getCurrentConnection();
		try {
			conn.commit();
			conn.close();
			connection_holder.set(null);
			setExistsTransaction(Boolean.FALSE);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 回滚事务
	 */
	public static void rollback() {
		Connection conn = getCurrentConnection();
		try {
			conn.rollback();
			conn.close();
			connection_holder.set(null);
			setExistsTransaction(Boolean.FALSE);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	// 创建一个不自动Commit的数据库连接
	private static Connection createNotAutoCommitConnection() {
		try {

			Connection conn = DriverManager.getConnection(connectionProp
					.getProperty("url")
					+ ";databaseName="
					+ connectionProp.getProperty("databaseName"),
					connectionProp.getProperty("username"), connectionProp
							.getProperty("password"));
			conn.setAutoCommit(false);
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	//以下内容为测试threadlocal所写
	private final static ThreadLocal<Map> map_holder = new ThreadLocal<Map>();  
	 
	public static Map getTestMap(){  
        Map map = map_holder.get();  
        if(map == null){  
            map = createMap();  
            map_holder.set(map);  
        }  
        return map;  
    }  
      
    public static Map createMap(){  
        Map map = new HashMap();  
        return map;  
    }  

}
