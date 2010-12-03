package com.ztesoft.component.job.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.servicelocator.web.ServiceLocator;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;

public class Utils {
	
	public static Connection getDBConnection(String source, Object callerObj)
			throws DAOSystemException {
		return getDBConnection(source) ;
	}
	public static Connection getDBConnection(String source)
	throws DAOSystemException {
		
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
		try {
			if(conn.isClosed()){
				System.out.println(" connection is close");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;

	}
	
	public static void closeConnection(String source){
		
		try {
			ConnectionContext.getContext().closeConnection(source);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closeConnection(String source,Object obj) {
		closeConnection(source);
	}
	
	/**
	 * @param :result
	 *            需要关闭的结果集, callerObj 调用者句柄
	 */
	public static void closeResultSet(ResultSet result, Object callerObj)
			throws DAOSystemException {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing "
					+ "Result Set : \n" + se);
		}
	}
	
	/**
	 * @param :stmt
	 *            需要关闭的陈述, callerObj 调用者句柄
	 */
	public static void closeStatement(PreparedStatement stmt, Object callerObj)
			throws DAOSystemException {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing "
					+ "Statement : \n" + se);
		}
	}
	
	public static void main(String[] args){
		Connection conn = Utils.getDBConnection("DEFAULT");
		try {
			conn.prepareStatement("select * from dc_public");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
