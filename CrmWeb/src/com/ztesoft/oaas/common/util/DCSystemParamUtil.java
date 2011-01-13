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

/**
 * @author Administrator
 *
 */
public class DCSystemParamUtil {
	public String getParam( String paramCode ) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select param_val from dc_system_param where param_code = ?";
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
			stmt.setString(1, paramCode ) ;
			rs = stmt.executeQuery();
			if( rs.next() ){
				return rs.getString("param_val");
			}else{
				return null ;
			}
		}
		catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}
}
