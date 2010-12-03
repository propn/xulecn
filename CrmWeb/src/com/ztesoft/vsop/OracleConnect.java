package com.ztesoft.vsop;

import java.lang.*; 
import java.util.*; 
import java.sql.*; 

import com.ztesoft.common.util.CrmParamsConfig;

public class OracleConnect {
	public static Connection getDirectConnection() throws DAOSystemException {
		Connection connection = null;
		// Load the JDBC driver
		String driverName = "oracle.jdbc.driver.OracleDriver";
		try {
			Class.forName(driverName);
			CrmParamsConfig.getInstance().initParams(driverName);
			String url = CrmParamsConfig.getInstance().getParamValue(
					"DirectDBUrl");
			String username = CrmParamsConfig.getInstance().getParamValue(
					"DirectDBUser");
			String password = CrmParamsConfig.getInstance().getParamValue(
					"DirectDBPwd");

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
	public static void main(String[] args) throws SQLException {
		Connection conn = getDirectConnection();
		Statement stmt=null; 
		ResultSet rs=null; 
		try {
			stmt=conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs=stmt.executeQuery("select processdefine_id from wl_processdefine");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		while (rs.next()) 
		{ 
			System.out.println("processdefine_id: "+ rs.getString(1)); 
		} 
		rs.close();
		stmt.close();
		conn.close();
	}
}
