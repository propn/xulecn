
package com.ztesoft.common.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import com.ztesoft.common.application.AppProxy;
import com.ztesoft.common.servicelocator.web.ServiceLocator;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;



/**
 * Connection ����������
 * 
 * @author easonwu 2009-12-22
 *
 */
public class ConnectionContext {
//	public static final String DEFAULT_JNDINAME = "jdbc/CRM";
	public static final String DEFAULT_JNDINAME = JNDINames.DEFAULT_DATASOURCE;
	
	public static final String RUN_MODE = "develop" ;//develop ����ģʽ product ����ģʽ
	
	private static ThreadLocal connectionContext = new ConnectionContextThreadLocal();
	
	static{
		DAOUtils.systempInit() ;//�Ƚ���ϵͳ�Ƿ��ʼ������
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
	
	//add by AyaKoizumi 100922
	private static Connection getConnection(java.sql.Connection conn){
		AppProxy apx=new AppProxy();
		//����������
		try {
			apx.addBfInterc("prepareStatement", "com.ztesoft.common.dao.SqlDAOStackTrace", "writeStackTrace", null);
			apx.addBfInterc("prepareCall", "com.ztesoft.common.dao.SqlDAOStackTrace", "writeStackTrace", null);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOSystemException("��Ӵ���java.sql.Connection������ʧ�ܣ�");
		}
		Object obj= apx.getProxyObject(conn,new Class[]{java.sql.Connection.class});
		java.sql.Connection _conn=null;
		try{
			_conn=(java.sql.Connection)obj;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOSystemException("����java.sql.Connectionʧ�ܣ�");
		}
		return _conn;
	}
	/**
	 * ��ȡConnection
	 * @param aDBName
	 * @return
	 * @throws FrameException 
	 */
	public Connection getConnection(String aDBName )  {
		aDBName = (aDBName == null || "".equals(aDBName)) ?
					DEFAULT_JNDINAME : aDBName ;
		Connection conn = null ;
		//Closed Connection Exception 2010-09-12
		if( context.get(aDBName) != null){
			conn = (Connection)context.get(aDBName) ;
			try {
				if(conn != null && !conn.isClosed())
					return conn ;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOSystemException("����dbName="+aDBName+"��ѯ����Ч���ݿ�����,closeʱ����");
			}
			this.context.remove(aDBName) ;
		}
		
		try {
			conn = getDBConnection(aDBName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(conn != null){
			setConnection(aDBName, conn) ;
		}else{
			throw new DAOSystemException("����dbName="+aDBName+",��ȡ���ݿ�����ʧ�ܣ�");
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
		//Closed Connection Exception 2010-09-12
		if(!conn.isClosed()){
			conn.close() ;
		}
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
		//Closed Connection Exception 2010-09-12
		if(!conn.isClosed()){
			conn.commit() ;
		}		
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
			//Closed Connection Exception 2010-09-12
			if(!conn.isClosed()){
				conn.commit() ;
			}
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
			//Closed Connection Exception 2010-09-12
			if(!conn.isClosed()){
				conn.rollback() ;
			}
			
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
			//Closed Connection Exception 2010-09-12
			if(!conn.isClosed()){
				conn.close() ;
			}
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
		//Closed Connection Exception 2010-09-12
		if(!conn.isClosed()){
			conn.rollback() ;
		}		
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
	
	/**
	 * �ж��Ƿ���������ģ�true : ���� , false :������
	 * @return
	 */
	public static boolean checkConnectionContext(){
		return connectionContext.get() != null && 
		((ConnectionContext) connectionContext.get()).getContextMap() != null;
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
	 *            ����Դ����
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
	 *            ����Դ���֣� callerObj �����߾��
	 */
	private static DataSource getDataSource(String dataSourceName,
			Object callerObj) throws DAOSystemException {
		return (DataSource) ServiceLocator.getInstance().getDataSource(
				CrmParamsConfig.getInstance().getParamValue(dataSourceName+"_JNDI") 
				, callerObj);
	}
	
	/**
	 * @param :source
	 *            ����Դ����
	 */
	private static Connection getDBConnection(String source)
			throws DAOSystemException {
		Connection conn = null ;
		try {
			// Ϊ�˷�����������Զ��л����ӷ�ʽ��ʵ�ʻ������Թر���Щ�жϴ��롣
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
		//modify by AYaKoizumi ���ش����Connection
		Connection _conn=getConnection(conn);
		return _conn ;
	}
	
	/**
	 * ֱ��ʹ��JDBC�ķ�ʽ��ȡ��ǰ���ݿ������.
	 * 
	 * @return
	 */
	private static Connection getDirectConnection(String dbName) throws DAOSystemException {
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
