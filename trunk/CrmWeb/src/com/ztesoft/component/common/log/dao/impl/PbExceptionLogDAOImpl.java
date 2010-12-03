package com.ztesoft.component.common.log.dao.impl;


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
import com.ztesoft.component.common.log.dao.PbExceptionLogDAO;
import com.ztesoft.component.common.log.vo.PbExceptionLogVO;

public class PbExceptionLogDAOImpl   implements PbExceptionLogDAO {

	private String SQL_SELECT = "SELECT log_id,exception_code,exception_reason,exception_project,stack,class_name,meth_name FROM PB_EXCEPTION_LOG";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PB_EXCEPTION_LOG";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PB_EXCEPTION_LOG ( log_id,exception_code,exception_reason,exception_project,stack,class_name,meth_name ) VALUES ( ?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PB_EXCEPTION_LOG SET  exception_code = ?, exception_reason = ?, exception_project = ?, stack = ?, class_name = ?, meth_name = ? WHERE log_id = ? ";

	private String SQL_DELETE = "DELETE FROM PB_EXCEPTION_LOG WHERE log_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PB_EXCEPTION_LOG ";

	public PbExceptionLogDAOImpl() {

	}

	public PbExceptionLogVO findByPrimaryKey(String plog_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE log_id = ? ", new String[] { plog_id } );
		if (arrayList.size()>0)
			return (PbExceptionLogVO)arrayList.get(0);
		else
			return (PbExceptionLogVO) getEmptyVO();
	}

	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
			stmt.setMaxRows( maxRows );
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setString( i+1, sqlParams[i] );
			}

			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
		}
		catch (SQLException se) {
			Debug.print(sql,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}

	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			PbExceptionLogVO vo = new PbExceptionLogVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(PbExceptionLogVO vo, ResultSet rs) throws SQLException {
		vo.setLogId( DAOUtils.trimStr( rs.getString( "log_id" ) ) );
		vo.setExceptionCode( DAOUtils.trimStr( rs.getString( "exception_code" ) ) );
		vo.setExceptionReason( DAOUtils.trimStr( rs.getString( "exception_reason" ) ) );
		vo.setExceptionProject( DAOUtils.trimStr( rs.getString( "exception_project" ) ) );
		//vo.setStack( DAOUtils.trimStr( rs.getString( "stack" ) ) );
		vo.setStack( new String(rs.getBytes("stack")));
		vo.setClassName( DAOUtils.trimStr( rs.getString( "class_name" ) ) );
		vo.setMethName( DAOUtils.trimStr( rs.getString( "meth_name" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		PbExceptionLogVO vo = new PbExceptionLogVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord³ö´í",this);
			throw new DAOSystemException("SQLException while populateDto:\n", se);
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
			SQL = SQL_SELECT + " WHERE " + whereCond;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
			stmt.setMaxRows( maxRows );
			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
		}
		catch (SQLException se) {
			Debug.print(SQL,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+SQL, se);
		}
		finally {
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
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			if ("".equals(((PbExceptionLogVO)vo).getLogId())) {
				((PbExceptionLogVO)vo).setLogId(null);
			}
			stmt.setString( index++, ((PbExceptionLogVO)vo).getLogId() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getExceptionCode() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getExceptionReason() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getExceptionProject() );
			//stmt.setString( index++, ((PbExceptionLogVO)vo).getStack() );
			stmt.setBytes( index++, ((PbExceptionLogVO)vo).getStack().getBytes());
			stmt.setString( index++, ((PbExceptionLogVO)vo).getClassName() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getMethName() );
			int rows = stmt.executeUpdate();
		}
		catch (SQLException se) {
			Debug.print(SQL_INSERT,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}

	public boolean update( String plog_id,PbExceptionLogVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			sql.append( "UPDATE PB_EXCEPTION_LOG SET log_id = ?,exception_code = ?,exception_reason = ?,exception_project = ?,stack = ?,class_name = ?,meth_name = ?" );
			sql.append( " WHERE  log_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PbExceptionLogVO)vo).getLogId())) {
				((PbExceptionLogVO)vo).setLogId(null);
			}
			stmt.setString( index++, vo.getLogId() );
			stmt.setString( index++, vo.getExceptionCode() );
			stmt.setString( index++, vo.getExceptionReason() );
			stmt.setString( index++, vo.getExceptionProject() );
			stmt.setString( index++, vo.getStack() );
			stmt.setString( index++, vo.getClassName() );
			stmt.setString( index++, vo.getMethName() );
			stmt.setString( index++, plog_id );
			int rows = stmt.executeUpdate();
			if (rows>0) {
				bResult = true;
			}
		}
		catch (SQLException se) {
			Debug.print(sql.toString(),this);
			throw new DAOSystemException("SQLException while update sql:\n"+sql.toString(), se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return bResult;
	}

	public boolean update(String whereCond,VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			sql.append( "UPDATE PB_EXCEPTION_LOG SET log_id = ?,exception_code = ?,exception_reason = ?,exception_project = ?,stack = ?,class_name = ?,meth_name = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PbExceptionLogVO)vo).getLogId())) {
				((PbExceptionLogVO)vo).setLogId(null);
			}
			stmt.setString( index++, ((PbExceptionLogVO)vo).getLogId() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getExceptionCode() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getExceptionReason() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getExceptionProject() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getStack() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getClassName() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getMethName() );
			int rows = stmt.executeUpdate();
			if (rows>0) {
				bResult = true;
			}
		}
		catch (SQLException se) {
			Debug.print(sql.toString(),this);
			throw new DAOSystemException("SQLException while update sql:\n"+sql.toString(), se);
		}
		finally {
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
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
			rs = stmt.executeQuery();

			while (rs.next()) {
				lCount = rs.getLong( "COL_COUNTS" );
			}
		}
		catch (SQLException se) {
			Debug.print(SQL,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+SQL, se);
		}
		finally {
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
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
			rows = stmt.executeUpdate();

		}
		catch (SQLException se) {
			Debug.print(SQL,this);
			throw new DAOSystemException("SQLException while deleting sql:\n"+SQL, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return rows;
	}

	public long delete( String plog_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, plog_id );
			rows = stmt.executeUpdate();

		}
		catch (SQLException se) {
			Debug.print(SQL_DELETE,this);
			throw new DAOSystemException("SQLException while deleting sql:\n"+SQL_DELETE, se);
		}
		finally {
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
		return new PbExceptionLogVO();
	}

	public List findByCond(String whereCond,QueryFilter queryFilter) throws DAOSystemException {
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
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
			rs = stmt.executeQuery();
			List retList = null;
			if (queryFilter != null) {
				retList = queryFilter.doPostFilter(rs,this);
			}
			else {
				retList=fetchMultiResults(rs);
			}
			return retList;
		}
		catch (SQLException se) {
			Debug.print(SQL,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+SQL, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}

}

