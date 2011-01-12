/**
 *
 */
package com.ztesoft.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.tracer.Debug;

public class SqlMapExe implements java.io.Serializable{

	private static final Log LOG = LogFactory.getLog(SqlMapExe.class);

	private static SqlMapExe instance;

	private SqlMapExe() {

	}

	public static SqlMapExe getInstance() {

		if (instance == null)
			instance = new SqlMapExe();
		return instance;
	}

	public List queryForStringListEx(String sql, String[] sqlParams, int num) {
		List list = new ArrayList();
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			dbConnection = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				Debug.print("sqlParams[" + i + "]=" + sqlParams[i]);
				stmt.setString(i + 1, sqlParams[i]);
			}
			result = stmt.executeQuery();
			if (result.next()) {
				for (int i = 1; sqlParams != null && i < num + 1; i++) {
					list.add(DAOUtils.trimStr(result.getString(i)));
				}

			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	public String queryValueBySqlAndCond(String sql, String param) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String returnValue = "";

		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));

			if (param != null) {
				stmt.setString(1, param);
			}

			result = stmt.executeQuery();
			if (result.next()) {
				returnValue = DAOUtils.trimStr(result.getString(1));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return returnValue;
	}

	public String queryValueBySqlAndCond(String sql, String params[]) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String returnValue = "";

		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));

			for (int i = 0; params != null && i < params.length; i++) {
				stmt.setString(i + 1, params[i]);
			}
			result = stmt.executeQuery();
			if (result.next()) {
				returnValue = DAOUtils.trimStr(result.getString(1));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return returnValue;
	}

	public String queryValueBySqlAndCond(String sql, List params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String returnValue = "";

		try {
			if (params == null) {
				params = new ArrayList();
			}
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			for (int i = 0, argsCnt = params.size(); i < argsCnt; i++) {
				stmt.setString(i + 1, (String) params.get(i));
			}
			result = stmt.executeQuery();
			if (result.next()) {
				returnValue = DAOUtils.trimStr(result.getString(1));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return returnValue;
	}

//	public String querySingleValue(String sqlname, String param) {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		String returnValue = "";
//
//		String sql = Sqls.getSql(sqlname);
//
//		try {
//			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
//
//			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
//
//			if (param != null) {
//				stmt.setString(1, param);
//			}
//
//			result = stmt.executeQuery();
//			if (result.next()) {
//				returnValue = DAOUtils.trimStr(result.getString(1));
//			}
//		} catch (Exception se) {
//			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
//		} finally {
//			DAOUtils.closeResultSet(result, this);
//			DAOUtils.closeStatement(stmt, this);
//
//		}
//		return returnValue;
//	}

//	public String querySingleValue(String sqlname, List params) {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		String returnValue = "";
//
//		String sql = Sqls.getSql(sqlname);
//
//		try {
//			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
//
//			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
//			if (params == null) {
//				params = new ArrayList();
//			}
//			for (int i = 0, argsCnt = params.size(); i < argsCnt; i++) {
//				stmt.setString(i + 1, (String) params.get(i));
//			}
//			result = stmt.executeQuery();
//			if (result.next()) {
//				returnValue = DAOUtils.trimStr(result.getString(1));
//			}
//		} catch (Exception se) {
//			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
//		} finally {
//			DAOUtils.closeResultSet(result, this);
//			DAOUtils.closeStatement(stmt, this);
//
//		}
//		return returnValue;
//	}

	public String querySingleValue(String sql, String[] params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String returnValue = "";
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			if (sql.indexOf("dual") > -1 || sql.indexOf("DUAL") > -1) {
				stmt = conn.prepareStatement(sql);
			} else {
				stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			}

			for (int i = 0; i < params.length; i++) {
				stmt.setString(i + 1, params[i]);
			}
			result = stmt.executeQuery();
			if (result.next()) {
				returnValue = DAOUtils.trimStr(result.getString(1));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return returnValue;
	}

	public String querySingleValue(String sql, ArrayList params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String returnValue = "";
		try {
			if (params == null) {
				params = new ArrayList();
			}
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			for (int i = 0, argsCnt = params.size(); i < argsCnt; i++) {
				stmt.setString(i + 1, (String) params.get(i));
			}
			result = stmt.executeQuery();
			if (result.next()) {
				returnValue = DAOUtils.trimStr(result.getString(1));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return returnValue;
	}

	// 直接通过SQL 获取结果集
	public List queryForMapListBySql(String sql, String[] sqlParams) throws DAOSystemException {

		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				Debug.print("sqlParams[" + i + "]=" + sqlParams[i]);
				stmt.setString(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();

			list = handle(rs);
		} catch (SQLException se) {
			Debug.print(se.toString(), this);
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	// 直接通过SQL 获取结果集
	public List queryForMapListBySql(String sql, List sqlParams) throws DAOSystemException {

		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			for (int i = 0; sqlParams != null && i < sqlParams.size(); i++) {
				Debug.print("sqlParams[" + i + "]=" + sqlParams.get(i));
				stmt.setString(i + 1, (String) sqlParams.get(i));
			}
			rs = stmt.executeQuery();

			list = handle(rs);
		} catch (SQLException se) {
			Debug.print(se.toString(), this);
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	/**
	 * 直接通过SQL 获取结果集
	 * 指定数据源
	 * @param sql
	 * @param sqlParams
	 * @param dataSource
	 * @return
	 * @throws DAOSystemException
	 */
	public List queryForMapListBySql(String sql, List sqlParams,String dataSource) throws DAOSystemException {

		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			if (dataSource == null || "".equals(dataSource))
				dataSource = JNDINames.CRM_DATASOURCE;

			conn = ConnectionContext.getContext().getConnection(dataSource);

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			for (int i = 0; sqlParams != null && i < sqlParams.size(); i++) {
				Debug.print("sqlParams[" + i + "]=" + sqlParams.get(i));
				stmt.setString(i + 1, (String) sqlParams.get(i));
			}
			rs = stmt.executeQuery();

			list = handle(rs);
		} catch (SQLException se) {
			Debug.print(se.toString(), this);
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	public List queryForStringList(String sql, String[] sqlParams) {
		List list = new ArrayList();
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			dbConnection = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				Debug.print("sqlParams[" + i + "]=" + sqlParams[i]);
				stmt.setString(i + 1, sqlParams[i]);
			}
			result = stmt.executeQuery();
			while (result.next()) {
				list.add(DAOUtils.trimStr(result.getString(1)));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}


	public Map queryRowsForMap(String sql, String[] sqlParams) throws DAOSystemException {

		Map map = new HashMap();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));

			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {

				stmt.setString(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();

			while (rs.next()) {

				map.put(DAOUtils.trimStr(rs.getString(1)), DAOUtils.trimStr(rs.getString(2)));
			}

			return map;
		} catch (SQLException se) {
			Debug.print(se.toString(), this);
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}

	}

	public int executeUpdate(String sql) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql.toString()));

			return stmt.executeUpdate();

		} catch (SQLException se) {
			Debug.print(sql.toString(), this);
			throw new DAOSystemException("SQLException while update sql:\n" + sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);

		}
	}

	/**
	 * 根据条件更新数据。
	 *
	 * @param sql
	 * @param sqlParams
	 * @return
	 * @throws DAOSystemException
	 */
	public int excuteUpdate(String sql, List sqlParams) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql.toString()));
			for (int i = 0; i < sqlParams.size(); i++) {
				Debug.print("sqlParams[" + i + "]=" + (String)sqlParams.get(i));
				stmt.setString(i + 1, (String) sqlParams.get(i));
			}

			return stmt.executeUpdate();

		} catch (SQLException se) {
			Debug.print(sql.toString(), this);
			throw new DAOSystemException("SQLException while update sql:\n" + sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);

		}
	}

	public String querySingleValue(String sql) {
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String returnValue = "";
		try {
			dbConnection = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			String sqlStr = "";
			if (sql.indexOf("dual") > -1 || sql.indexOf("DUAL") > -1) {
				sqlStr = sql;
			} else {
				sqlStr = DAOSQLUtils.getFilterSQL(sql);
			}
			stmt = dbConnection.prepareStatement(sqlStr);
			result = stmt.executeQuery();
			if (result.next()) {
				returnValue = DAOUtils.trimStr(result.getString(1));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

			// DAOUtils.closeConnection(dbConnection, this);
		}
		return returnValue;
	}

	public List queryForStringList(String sql) {
		List list = new ArrayList();
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			dbConnection = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);

			result = stmt.executeQuery();
			while (result.next()) {
				list.add(result.getString(1));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}


	
	public List queryForMapList(String sql) throws DAOSystemException {

		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			rs = stmt.executeQuery();

			list = handle(rs);
		} catch (SQLException se) {
			Debug.print(se.toString(), this);
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	//连接地址库临时测试

	/**
	 * @param sqlname
	 * @param sqlParams
	 * @return
	 * @throws DAOSystemException
	 */
	public List queryForMapList(String sqlname, List sqlParams,String JNDINames) throws DAOSystemException {

		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = sqlname;

		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames);

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			for (int i = 0; sqlParams != null && i < sqlParams.size(); i++) {
				Debug.print("sqlParams[" + i + "]=" + sqlParams.get(i));
				stmt.setString(i + 1, (String) sqlParams.get(i));
			}
			rs = stmt.executeQuery();

			list = handle(rs);
		} catch (SQLException se) {
			Debug.print(se.toString(), this);
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			/*
			 * try{ ConnectionContext.getContext().closeConnection(JNDINames.CRM_DATASOURCE); }catch(Exception e){
			 *  }
			 */

		}
		return list;
	}


	public List execForMapList(String sql, String[] sqlParams) throws DAOSystemException {

		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);

			LOG.debug(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				Debug.print("sqlParams[" + i + "]=" + sqlParams[i]);
				stmt.setString(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();

			list = handle(rs);
		} catch (SQLException se) {
			Debug.print(se.toString(), this);
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return list;
	}

	/**
	* 根据SQL,参数，分页查询结果
	* @param String  sql，List params
	* @return PageModel
	*@throws Exception
	*/
	public PageModel queryPageModelResult(String sql,ArrayList params,int pi,int ps) throws Exception
	{
		PageModel pageModel = new PageModel();

		String countSql = " select count(*) count from ( " + sql + " ) ";//计算总记录数

		int totalCount = new Long(this.querySingleValue(countSql, params)).intValue();

		pageModel.setTotalCount(totalCount);

		if (totalCount == 0) return new PageModel();

		if (totalCount % ps > 0) {
			pageModel.setPageCount(totalCount / ps + 1);
		} else {
			pageModel.setPageCount(totalCount / ps);
		}

		// 边界的处理
		if (pi < 0) {
			pageModel.setPageIndex(1);
		} else if(pi>pageModel.getPageCount()){
			pageModel.setPageIndex(pageModel.getPageCount());
		}else {
			pageModel.setPageIndex(pi);
		}

		if (ps < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(ps);
		}

		String queryResultSql = " select * from ( select mytable.*, rownum num from ( ";
		queryResultSql += sql;
		queryResultSql += " ) mytable )where num > ? and num <= ?";

		params.add(String.valueOf(ps*(pi-1)));

		params.add(String.valueOf(ps*pi));

		List resultList = this.queryForMapListBySql(queryResultSql,params);

		pageModel.setList(resultList);

		return pageModel;

	}

	public List getUpcaseKeyMapList(String sql, String[] sqlParams) throws DAOSystemException {
		return getMapList(sql, sqlParams, "T");
	}

	public List getLowercaseKeyMapList(String sql, String[] sqlParams) throws DAOSystemException {
		return getMapList(sql, sqlParams, "F");
	}

	// 现在系统都是默认小写key，增加一个方法，用来选定MAP中是大写还是小写
	// T 大写 否则小写
	public List getMapList(String sql, String[] sqlParams, String Upcaseflag) throws DAOSystemException {

		List list = new ArrayList();
		List retList = new ArrayList();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);

			LOG.debug(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				Debug.print("sqlParams[" + i + "]=" + sqlParams[i]);
				stmt.setString(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();

			list = handle(rs);
			for (int i = 0; i < list.size(); i++) {
				HashMap map = (HashMap) list.get(i);
				HashMap cloneMap = new HashMap();
				Iterator ite = map.keySet().iterator();
				while (ite.hasNext()) {
					String key = (String) ite.next();

					if ("T".equals(Upcaseflag)) {
						key.toUpperCase();
					} else {
						key.toLowerCase();
					}

					cloneMap.put(key, (String) map.get(key));
				}
				retList.add(cloneMap);
			}
		} catch (SQLException se) {
			Debug.print(se.toString(), this);
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
		return retList;
	}

	private List handle(ResultSet rs) throws SQLException {

		List results = new ArrayList();

		while (rs.next()) {
			results.add(this.rowToMap(rs));
		}

		return results;
	}

	private Map rowToMap(ResultSet rs) throws SQLException {
		Map result = new HashMap();
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		for (int i = 1; i <= cols; i++) {
			result.put(rsmd.getColumnName(i).toLowerCase(), rs.getString(i));
		}
		return result;
	}

	public Map queryMapBySql(String sql, String[] sqlParams) throws DAOSystemException {

		Map map = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);

			System.out.println(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql, DAOSQLUtils.CRM_DB));

			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				Debug.print("sqlParams[" + i + "]=" + sqlParams[i]);
				stmt.setString(i + 1, sqlParams[i]);
			}
			rs = stmt.executeQuery();
			if (rs.next()) {
				map = this.rowToMap(rs);
			}

			return map;
		} catch (SQLException se) {
			Debug.print(se.toString(), this);
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);

		}
	}



}
