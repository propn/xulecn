/**
 * 
 */
package com.ztesoft.oaas.common.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;

/**
 * @author –Ì»Ò∫¿
 *
 */
public class SSOUtils {

	public String getIOMUrlByCrmIPPort( String ip, String port ) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT iom_ip, iom_port FROM crm_ipport_cfg WHERE crm_ip = ? AND crm_port = ? and iom_ip is not null and iom_port is not null";
		try {
			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
			stmt.setString(1, ip);
			stmt.setString(2,port);

			rs = stmt.executeQuery();
			if( rs.next() ){
				DCSystemParamUtil param = new DCSystemParamUtil();
				String iomUrl = param.getParam("IOM_SSO_URL");
				return "http://" + rs.getString("iom_ip") + ":" + rs.getString("iom_port") + iomUrl;
			}else{
				return "";
			}
		}
		catch (SQLException se) {
			se.printStackTrace();
			Debug.print(sql,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}
	public String getRIMUrlByCrmIPPort( String ip, String port ) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT rim_ip, rim_port FROM crm_ipport_cfg WHERE crm_ip = ? AND crm_port = ? and rim_ip is not null and rim_port is not null ";
		try {
			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
			stmt.setString(1, ip);
			stmt.setString(2,port);

			rs = stmt.executeQuery();
			if( rs.next() ){
				DCSystemParamUtil param = new DCSystemParamUtil();
				String malfunctionUrl = param.getParam("MAL_SSO_URL") ;
				return "http://" + rs.getString("rim_ip") + ":" + rs.getString("rim_port") + malfunctionUrl;
			}else{
				return "";
			}
		}
		catch (SQLException se) {
			se.printStackTrace();
			Debug.print(sql,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}		
	}
	
	public String getMcmUrlByCrmIPPort( String ip, String port ) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT mcm_ip, mcm_port FROM crm_ipport_cfg WHERE crm_ip = ? AND crm_port = ? and mcm_ip is not null and mcm_port is not null";
		try {
			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
			stmt.setString(1, ip);
			stmt.setString(2,port);

			rs = stmt.executeQuery();
			if( rs.next() ){
				return "http://" + rs.getString("mcm_ip") + ":" + rs.getString("mcm_port") ;
			}else{
				return "";
			}
		}
		catch (SQLException se) {
			se.printStackTrace();
			Debug.print(sql,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}
	
	public String getCoopUrlByCrmIPPort( String ip, String port ) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT coop_ip, coop_port FROM crm_ipport_cfg WHERE crm_ip = ? AND crm_port = ? and coop_ip is not null and coop_port is not null";
		try {
			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
			stmt.setString(1, ip);
			stmt.setString(2,port);

			rs = stmt.executeQuery();
			if( rs.next() ){
				return "http://" + rs.getString("coop_ip") + ":" + rs.getString("coop_port") ;
			}else{
				return "";
			}
		}
		catch (SQLException se) {
			se.printStackTrace();
			Debug.print(sql,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}
}
