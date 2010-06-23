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

	// ʹ��ThreadLocal���е�ǰ�̵߳����ݿ�����
	private final static ThreadLocal<Connection> connection_holder = new ThreadLocal<Connection>();

	// ��ǰ�Ƿ������񻷾�
	private final static ThreadLocal<Boolean> existsTransaction = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	// �Ƿ����ع�
	private final static ThreadLocal<Boolean> rollbackOnly = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	// �������ã�����connection.properties
	private final static Properties connectionProp = new Properties();

	static {
		// ���������ļ�
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("connection.properties");
		try {

			connectionProp.load(is);
			is.close();
			// ������������
			Class.forName(connectionProp.getProperty("driverClassName"));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("����δ�ҵ�", e);
		}
	}

	/**
	 * �Ƿ����ع�
	 */
	public static boolean isRollbackOnly() {
		return rollbackOnly.get();
	}

	/**
	 * ���õ�ǰ���񻷾��Ļع�״̬
	 */
	public static void setRollbackOnly(boolean flag) {
		rollbackOnly.set(flag);
	}

	/**
	 * ��ǰ�Ƿ��������
	 */
	public static boolean existsTransaction() {
		return existsTransaction.get();
	}

	// ���õ�ǰ���񻷾�
	private static void setExistsTransaction(boolean flag) {
		existsTransaction.set(flag);
	}

	/**
	 * ��ʼһ������
	 */
	public static void beginTransaction() {
		Connection conn = createNotAutoCommitConnection();
		connection_holder.set(conn);
		setExistsTransaction(Boolean.TRUE);
	}

	// ��ȡ��ǰ�߳��е����ݿ�����
	private static Connection getCurrentConnection() {
		return connection_holder.get();
	}

	// ִ��SQL���
	public static int executeNonQuery(String sql) throws SQLException {

		Connection conn = getCurrentConnection();

		return conn.createStatement().executeUpdate(sql);

	}

	/**
	 * �ύ����
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
	 * �ع�����
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

	// ����һ�����Զ�Commit�����ݿ�����
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
	
	//��������Ϊ����threadlocal��д
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
