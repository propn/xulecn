/**
 * 
 */
package com.ztesoft.common.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.tracer.Debug;

/**
 * @author –Ì»Ò∫¿
 *
 */
public class DcSystemParamUtil extends DictAction{

	public String getSystemParam(DynamicDict dto ) throws Exception {
		 String paramCode = (String)dto.getValueByName("parameter") ;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select param_val from dc_system_param where param_code = ? ";
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			stmt.setString(1, paramCode);
			rs = stmt.executeQuery();
			if( rs.next() ){
				return rs.getString("param_val");
			}else{
				return "" ;
			}
		} catch (SQLException se) {
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);
		}
	}
}
