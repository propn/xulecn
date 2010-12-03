package com.ztesoft.vsop;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.order.vo.request.SubscribeToVSOPRequest;

/**
 * ����һ������DAO�����࣬�����crm2.0��ܵ����񵥶�ʹ��crm2.0��Connection
 * ��Ҫ�Լ��ύ����
 * @author yi.chengfeng Mar 29, 2010 10:41:58 AM
 *
 */
public class LegacyDAOUtil {
	private static String dbName = JNDINames.DEFAULT_DATASOURCE ;
	/**
	 * 
	 * @return  Connection  ��ȡ��Connection����Connection.close()���ر����ӣ�ֻ����
	 * ConnectionContext.getContext().closeConnection(dbName);
	 */
	public static Connection getConnection() {
		//���ֻ�ȡconnection�ķ���conn�ǲ��Զ��ύ�ģ���setAutoCommit(false)�˵ģ���Ҫ�Լ������ύ
		return ConnectionContext.getContext().getConnection(dbName);
//		Context initCtx;
//		java.sql.Connection conn = null;
//		String jndiName = CrmParamsConfig.getInstance().getParamValue("DEFAULT"+"_JNDI");
//		try {
//			initCtx = new InitialContext();
//			javax.sql.DataSource ds = (javax.sql.DataSource) initCtx
//					.lookup(jndiName);
//			conn = ds.getConnection();
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//		catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return conn;
	}
	public static void commitAndCloseConnection(Connection conn) {
		try {
			if(null != conn && !conn.isClosed()){//������쳣�������ѻع������ѹرգ�������ִ�������ύ��ر�����
				conn.commit();
				ConnectionContext.getContext().closeConnection(dbName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void commitAndCloseConnection() {
		Connection conn = getConnection();
		if(conn != null){
			try {
				conn.commit();
				ConnectionContext.getContext().closeConnection(dbName);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * �쳣�ع����ͷ�����
	 * @param conn
	 */
	public static void rollbackOnException(Connection conn) {
		if(conn != null){
			try {
				conn.rollback();
				ConnectionContext.getContext().closeConnection(dbName);
			} catch (SQLException e) {
			}
		}
	}
	/**
	 * �쳣�ع����ͷ�����
	 */
	public static void rollbackOnException() {
		Connection conn = ConnectionContext.getContext().getConnection(dbName);
		rollbackOnException(conn);
	}
	public static void commitConnection(Connection conn){
		if(conn != null){
			try {
				conn.commit();
			} catch (SQLException e) {
			}
		}
	}
	public static void releaseConnection(Connection conn){
		/*if(conn != null){*/
			try {
				ConnectionContext.getContext().closeConnection(dbName);
			} catch (SQLException e) {
			}
		//}
	}
	
	public static void releaseConnection(){
		Connection conn = getConnection();
		if(conn != null){
			try {
				ConnectionContext.getContext().closeConnection(dbName);
			} catch (SQLException e) {
			}
		}
	}
}
