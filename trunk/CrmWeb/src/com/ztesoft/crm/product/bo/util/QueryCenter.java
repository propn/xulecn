package com.ztesoft.crm.product.bo.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
/**
 * 
 * @author liruxin
 * ²éÑ¯ÖÐÐÄ
 */
public class QueryCenter {
	public String querySingleValue(String sql,String[] sqlParams){
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String returnValue = "";
		try {
			dbConnection = DAOUtils.getDBConnection(JNDINames.PM_DATASOURCE, this);
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ){
				stmt.setString( i+1, sqlParams[i] );
			}
			result = stmt.executeQuery();
			if (result.next()) {
				returnValue = result.getString(1);
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(dbConnection, this);
		}
		return returnValue;
	}
}
