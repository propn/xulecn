
package com.ztesoft.common.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import com.ztesoft.common.servicelocator.web.ServiceLocator;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;



/**
 * Connection 管理上下文
 * 
 * @author easonwu 2009-12-22
 *
 */
public class ConnectionContext {
//	public static final String DEFAULT_JNDINAME = "jdbc/CRM";
	public static final String DEFAULT_JNDINAME = JNDINames.DEFAULT_DATASOURCE;
	
	public static final String RUN_MODE = "develop" ;//develop 开发模式 product 生产模式
	
	private static ThreadLocal connectionContext = new ConnectionContextThreadLocal();
	
	static{
		DAOUtils.systempInit() ;//先进行系统是否初始化处理
	}
	
	private Map context;
	
	public ConnectionContext(Map context) {
		this.context = context;
	}
	
	public void setConnection(String jndiName , Connection anCon){
		put(jndiName, anCon) ;
	}
	
	
	public void setConnection(Connection anCon){
		setConnection(DEFAULT_JNDINAME, anCon) ;
	}
	
	public Connection getConnection()  {
		return getConnection(DEFAULT_JNDINAME) ;
	}
	
	/**
	 * 获取Connection
	 * @param aDBName
	 * @return
	 * @throws FrameException 
	 */
	public Connection getConnection(String aDBName )  {
		aDBName = (aDBName == null || "".equals(aDBName)) ?
					DEFAULT_JNDINAME : aDBName ;
		
		if( context.get(aDBName) != null)
			return (Connection)context.get(aDBName) ;
		
		Connection conn = null;
		try {
			conn = getDBConnection(aDBName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(conn != null){
			setConnection(aDBName, conn) ;
		}else{
			throw new DAOSystemException("根据dbName="+aDBName+",获取数据库连接失败！");
		}
		System.out.println(aDBName+" get conn ======"+conn) ;
		return conn ;	
	}
	/**
	 * close connection
	 * @param aDBName
	 * @return
	 * @throws SQLException
	 * @throws FrameException 
	 */
	public boolean closeConnection(String aDBName ) throws SQLException{
		Connection conn = getConnection( aDBName ) ;

		System.out.println(aDBName+" close conn ======"+conn) ;
		if( conn == null )
			return false ;
		System.out.println("connection ============") ;
		System.out.println(aDBName+" close conn isClose ======"+conn.isClosed()) ;
		conn.close() ;
		
		System.out.println(aDBName+" context: ======"+this.context) ;
		this.context.remove(aDBName) ;

		conn = null ;
		return true ;
	}
	
	/**
	 * commit operation
	 * @param aDBName
	 * @return
	 * @throws SQLException
	 * @throws FrameException 
	 */
	public boolean commit(String aDBName ) throws SQLException{
		Connection conn = getConnection( aDBName ) ;
		if( conn == null )
			return false ;
		conn.commit() ;
		System.out.println(aDBName+" commit conn ======"+conn) ;
		return true ;
	}
	
	/**
	 * batch commit for multi db evn
	 * @return
	 * @throws SQLException
	 */
	public boolean allCommit() throws SQLException{
		if(this.context == null || this.context.isEmpty())
			return false ;
		Iterator it = this.context.keySet().iterator() ;
		while ( it.hasNext() ){
			String dbName = (String) it.next() ;
			System.out.println("db.commit==========="+dbName) ;
			Connection conn = (Connection) context.get(dbName) ;
			conn.commit() ;
		}
		return true ;
	}
	
	/**
	 * batch rollback for multi db evn
	 * @return
	 * @throws SQLException
	 */
	public boolean allRollback() throws SQLException{
		if(this.context == null || this.context.isEmpty())
			return false ;
		Iterator it = this.context.keySet().iterator() ;
		while ( it.hasNext() ){
			String dbName = (String) it.next() ;
			System.out.println("db.rollback==========="+dbName) ;
			Connection conn = (Connection) context.get(dbName) ;
			conn.rollback() ;
			
		}
		return true ;
	}
	
	/**
	 * batch close conn
	 * @return
	 * @throws SQLException
	 */
	public boolean allCloseConnection( ) throws SQLException {
		if(this.context == null || this.context.isEmpty())
			return false ;
		Iterator it = this.context.keySet().iterator() ;
		while ( it.hasNext() ){
			String dbName = (String) it.next() ;

			System.out.println("db.close==========="+dbName) ;
			Connection conn = (Connection) context.get(dbName) ;
			conn.close() ;
			conn = null ;
		}
		this.context.clear() ;
		return true ;
	}
	
	/**
	 * rollback operation
	 * @param aDBName
	 * @return
	 * @throws SQLException
	 * @throws FrameException 
	 */
	public boolean rollback(String aDBName ) throws SQLException {
		Connection conn = getConnection( aDBName ) ;
		if( conn == null )
			return false ;
		conn.rollback() ;
		System.out.println(aDBName+" rollback conn ======"+conn) ;
		return true ;
	}
	
	
	private void put(String key, Object value) {
		context.put(key, value);		
	}

	private static class ConnectionContextThreadLocal extends ThreadLocal {
		protected Object initialValue() {
			return new ConnectionContext(new HashMap());
		}
	}
	
	public static ConnectionContext getContext() {
		
		ConnectionContext context = (ConnectionContext) connectionContext.get();
		
		if (context == null) {
			context = new ConnectionContext(new HashMap());
			setContext(context);
		}
		
		return context;
	}
	
	public Map getContextMap(){
		return this.context ;
	}

	public static void setContext(ConnectionContext context) {
		connectionContext.set(context);
	}
	
	

	/**
	 * @param :dataSourceName
	 *            数据源名字
	 */
	private static DataSource getDataSource(String dataSourceName)
			throws DAOSystemException {
		String jndiName = CrmParamsConfig.getInstance().getParamValue(dataSourceName+"_JNDI")  ;
		System.out.print("dbName==="+dataSourceName+",jndiName==="+jndiName) ;
		DataSource ds =  (DataSource) ServiceLocator.getInstance().getDataSource(jndiName);
		System.out.print(",ds==="+ds+"\n") ;
		return ds ;
	}

	/**
	 * @param :dataSourceName
	 *            数据源名字， callerObj 调用者句柄
	 */
	private static DataSource getDataSource(String dataSourceName,
			Object callerObj) throws DAOSystemException {
		return (DataSource) ServiceLocator.getInstance().getDataSource(
				CrmParamsConfig.getInstance().getParamValue(dataSourceName+"_JNDI") 
				, callerObj);
	}
	
	/**
	 * @param :source
	 *            数据源名字
	 */
	public static Connection getDBConnection(String source)
			throws DAOSystemException {
		Connection conn = null ;
		try {
			// 为了方便测试设置自动切换连接方式，实际环境可以关闭这些判断代码。
			String DIRECT_CONNECTION = CrmParamsConfig.getInstance().getParamValue("DIRECT_CONNECTION") ;
			if (DIRECT_CONNECTION != null && "true".equalsIgnoreCase(DIRECT_CONNECTION)) {
				conn = getDirectConnection(source);
			} else {
				DataSource ds = getDataSource(source);
				if( ds != null ){
					conn = ds.getConnection();
				}else{
					System.out.println("\n\n\nDataSource == " + ds + "\n\n\n");
				}
				//conn = getDataSource(source).getConnection();
			}
			conn.setAutoCommit(false) ;
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while getting "
					+ "DB connection : \n" + se);
		} catch( Exception e ){
			System.out.println("\n\n\nconn == " + conn + "\n\n\n");
			e.printStackTrace();
		}
		return conn ;
	}
	
	/**
	 * 直接使用JDBC的方式获取当前数据库的连接.
	 * 
	 * @return
	 */
	public static Connection getDirectConnection(String dbName) throws DAOSystemException {
		Connection connection = null;
		try {
			dbName = dbName.toUpperCase() ;
			String driver = CrmParamsConfig.getInstance().getParamValue(
					dbName+"_Driver") ;
			
			
			String url = CrmParamsConfig.getInstance().getParamValue(
					dbName+"_DBUrl");
			String username = CrmParamsConfig.getInstance().getParamValue(
					dbName+"_DBUser");
			String password = CrmParamsConfig.getInstance().getParamValue(
					dbName+"_DBPwd");
			
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOSystemException(
					"SQL Exception while init connection \n" + e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOSystemException(
					"SQL Exception while get connection \n" + e);
		}

		return connection;
	}


}
