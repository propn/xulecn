package com.ztesoft.component.common.staticdata.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.staticdata.dao.AcctItemGrpDAO;
import com.ztesoft.component.common.staticdata.vo.AcctItemGrpVO;
public class AcctItemGrpDAOImpl   implements AcctItemGrpDAO {

	//private String SQL_SELECT = "SELECT acct_item_group_id,acct_item_group_name,priority,state,state_date FROM ACCT_ITEM_GROUP where state ='00A'" ;
	private String SQL_SELECT = "select acct_item_group_id,acct_item_group_name,priority,state,state_date from acct_item_group where type_code = '5UB'";
	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM ACCT_ITEM_GROUP";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO ACCT_ITEM_GROUP ( acct_item_group_id,acct_item_group_name,priority,state,state_date ) VALUES ( ?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE ACCT_ITEM_GROUP SET  acct_item_group_name = ?, priority = ?, state = ?, state_date = ? WHERE acct_item_group_id = ? ";

	private String SQL_DELETE = "DELETE FROM ACCT_ITEM_GROUP WHERE acct_item_group_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM ACCT_ITEM_GROUP ";

	public AcctItemGrpDAOImpl() {

	}

	public AcctItemGrpVO findByPrimaryKey(String pacct_item_group_id)
			throws DAOSystemException {
		ArrayList arrayList = findBySql(SQL_SELECT
				+ " WHERE acct_item_group_id = ? ",
				new String[] { pacct_item_group_id });
		if (arrayList.size() > 0)
			return (AcctItemGrpVO) arrayList.get(0);
		else
			return (AcctItemGrpVO) getEmptyVO();
	}

	public ArrayList findBySql(String sql, String[] sqlParams)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql,DAOSQLUtils.CRM_DB));
			stmt.setMaxRows(maxRows);
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setString(i + 1, sqlParams[i]);
			}

			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
		} catch (SQLException se) {
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}

	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			AcctItemGrpVO vo = new AcctItemGrpVO();
			populateDto(vo, rs);
			resultList.add(vo);
		}
		return resultList;
	}

	protected void populateDto(AcctItemGrpVO vo, ResultSet rs)
			throws SQLException {
		vo.setAcctItemGrpId(DAOUtils
				.trimStr(rs.getString("acct_item_group_id")));
		vo.setAcctItemGrpName(DAOUtils.trimStr(rs
				.getString("acct_item_group_name")));
		vo.setPriority(DAOUtils.trimStr(rs.getString("priority")));
		vo.setState(DAOUtils.trimStr(rs.getString("state")));
		vo.setStateDate(DAOUtils.getFormatedDateTime( rs.getTimestamp("state_date")));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
		AcctItemGrpVO vo = new AcctItemGrpVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord����", this);
			throw new DAOSystemException("SQLException while populateDto:\n",
					se);
		}
		return vo;
	}

	public List findByCond(String whereCond) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = "";
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			SQL = SQL_SELECT ;//+ " WHERE " + whereCond;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.CRM_DB));
			stmt.setMaxRows(maxRows);
			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
		} catch (SQLException se) {
			Debug.print(SQL, this);
			se.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}

	public void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));
			int index = 1;
			if ("".equals(((AcctItemGrpVO) vo).getAcctItemGrpId())) {
				((AcctItemGrpVO) vo).setAcctItemGrpId(null);
			}
			stmt.setString(index++, ((AcctItemGrpVO) vo).getAcctItemGrpId());
			stmt.setString(index++, ((AcctItemGrpVO) vo).getAcctItemGrpName());
			if ("".equals(((AcctItemGrpVO) vo).getPriority())) {
				((AcctItemGrpVO) vo).setPriority(null);
			}
			stmt.setString(index++, ((AcctItemGrpVO) vo).getPriority());
			stmt.setString(index++, ((AcctItemGrpVO) vo).getState());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((AcctItemGrpVO) vo)
					.getStateDate()));
			int rows = stmt.executeUpdate();
		} catch (SQLException se) {
			Debug.print(SQL_INSERT, this);
			throw new DAOSystemException("SQLException while insert sql:\n"
					+ SQL_INSERT, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}

	public boolean update(String pacct_item_group_id, AcctItemGrpVO vo)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			sql
					.append("UPDATE ACCT_ITEM_GROUP SET acct_item_group_id = ?,acct_item_group_name = ?,priority = ?,state = ?,state_date = ?");
			sql.append(" WHERE  acct_item_group_id = ? ");
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((AcctItemGrpVO) vo).getAcctItemGrpId())) {
				((AcctItemGrpVO) vo).setAcctItemGrpId(null);
			}
			stmt.setString(index++, vo.getAcctItemGrpId());
			stmt.setString(index++, vo.getAcctItemGrpName());
			if ("".equals(((AcctItemGrpVO) vo).getPriority())) {
				((AcctItemGrpVO) vo).setPriority(null);
			}
			stmt.setString(index++, vo.getPriority());
			stmt.setString(index++, vo.getState());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getStateDate()));
			stmt.setString(index++, pacct_item_group_id);
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				bResult = true;
			}
		} catch (SQLException se) {
			Debug.print(sql.toString(), this);
			throw new DAOSystemException("SQLException while update sql:\n"
					+ sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return bResult;
	}

	public boolean update(String whereCond, VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			sql
					.append("UPDATE ACCT_ITEM_GROUP SET acct_item_group_id = ?,acct_item_group_name = ?,priority = ?,state = ?,state_date = ?");
			sql.append(" WHERE " + whereCond);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((AcctItemGrpVO) vo).getAcctItemGrpId())) {
				((AcctItemGrpVO) vo).setAcctItemGrpId(null);
			}
			stmt.setString(index++, ((AcctItemGrpVO) vo).getAcctItemGrpId());
			stmt.setString(index++, ((AcctItemGrpVO) vo).getAcctItemGrpName());
			if ("".equals(((AcctItemGrpVO) vo).getPriority())) {
				((AcctItemGrpVO) vo).setPriority(null);
			}
			stmt.setString(index++, ((AcctItemGrpVO) vo).getPriority());
			stmt.setString(index++, ((AcctItemGrpVO) vo).getState());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((AcctItemGrpVO) vo)
					.getStateDate()));
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				bResult = true;
			}
		} catch (SQLException se) {
			Debug.print(sql.toString(), this);
			throw new DAOSystemException("SQLException while update sql:\n"
					+ sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return bResult;
	}

	public long countByCond(String whereCond) throws DAOSystemException {
		Connection conn = null;
		long lCount = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = "";
		try {

			int orderbyIndex = whereCond.toUpperCase().lastIndexOf("ORDER BY");
			if (orderbyIndex > 0) {
				whereCond = whereCond.substring(0, orderbyIndex);
			}
			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			SQL = SQL_SELECT_COUNT + " WHERE " + whereCond;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.CRM_DB));
			rs = stmt.executeQuery();

			while (rs.next()) {
				lCount = rs.getLong("COL_COUNTS");
			}
		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return lCount;
	}

	public long deleteByCond(String whereCond) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		String SQL = "";
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			SQL = SQL_DELETE_BY_WHERE + " WHERE " + whereCond;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.CRM_DB));
			rows = stmt.executeUpdate();

		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while deleting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return rows;
	}

	public long delete(String pacct_item_group_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));
			int index = 1;
			stmt.setString(index++, pacct_item_group_id);
			rows = stmt.executeUpdate();

		} catch (SQLException se) {
			Debug.print(SQL_DELETE, this);
			throw new DAOSystemException("SQLException while deleting sql:\n"
					+ SQL_DELETE, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return rows;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public VO getEmptyVO() {
		return new AcctItemGrpVO();
	}

	public List findByCond(String whereCond, QueryFilter queryFilter)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = SQL_SELECT + " WHERE " + whereCond;
		String filterSQL = SQL;
		if (queryFilter != null) {
			filterSQL = queryFilter.doPreFilter(SQL);
		}
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			SQL = filterSQL;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.CRM_DB));
 
			rs = stmt.executeQuery();

			List retList = null;
			if (queryFilter != null) {
				retList = queryFilter.doPostFilter(rs, this);
			} else {
				retList = fetchMultiResults(rs);
			}
			return retList;
		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}

}
