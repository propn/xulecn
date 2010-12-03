package com.ztesoft.vsop;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.order.vo.request.SubscribeToVSOPRequest;

/**
 * 新增一个公共DAO帮助类，不结合crm2.0框架的事务单独使用crm2.0的Connection
 * 需要自己提交事务。
 * @author yi.chengfeng Mar 29, 2010 10:41:58 AM
 *
 */
public class LegacyDAOUtil {
	private static String dbName = JNDINames.DEFAULT_DATASOURCE ;
	/**
	 * 
	 * @return  Connection  获取的Connection不能Connection.close()来关闭链接，只能用
	 * ConnectionContext.getContext().closeConnection(dbName);
	 */
	public static Connection getConnection() {
		//这种获取connection的方法conn是不自动提交的，被setAutoCommit(false)了的，需要自己控制提交
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
			if(null != conn && !conn.isClosed()){//如果是异常，事务已回滚连接已关闭；不能再执行事务提交与关闭连接
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
	 * 异常回滚，释放链接
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
	 * 异常回滚，释放链接
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
