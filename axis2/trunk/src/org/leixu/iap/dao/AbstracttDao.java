/**
 * 
 */
package org.leixu.iap.dao;

import java.sql.Connection;


/**
 * @author lei
 * 
 */
public class AbstracttDao {
	private Connection conn=null;

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	

}
