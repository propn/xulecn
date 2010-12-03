package com.ztesoft.component.job.dao.impl;

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
import com.ztesoft.component.job.dao.CrmJobRunLogDAO;
import com.ztesoft.component.job.vo.CrmJobRunLogVO;
public class CrmJobRunLogDAOImpl    implements CrmJobRunLogDAO {

	private String SQL_SELECT = "SELECT a.job_log_id,a.job_id,a.job_log_time,a.job_run_state,a.job_run_msg,b.job_name FROM CRM_JOB_RUN_LOG a, CRM_JOB b where a.job_id = b.job_id";

	private String SQL_SELECT_COUNT = "SELECT COUNT(a.job_log_id) AS COL_COUNTS FROM CRM_JOB_RUN_LOG a, CRM_JOB b where a.job_id = b.job_id";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO CRM_JOB_RUN_LOG ( job_log_id,job_id,job_log_time,job_run_state,job_run_msg ) VALUES ( ?,?,?,?,? )";

	private String SQL_DELETE = "DELETE FROM CRM_JOB_RUN_LOG WHERE job_log_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM CRM_JOB_RUN_LOG ";

	public CrmJobRunLogDAOImpl() {

	}

	public CrmJobRunLogVO findByPrimaryKey(String pjob_log_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE job_log_id = ? ", new String[] { pjob_log_id } );
		if (arrayList.size()>0)
			return (CrmJobRunLogVO)arrayList.get(0);
		else
			return (CrmJobRunLogVO) getEmptyVO();
	}

	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
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
			Utils.closeResultSet(rs, this);
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
	}

	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			CrmJobRunLogVO vo = new CrmJobRunLogVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(CrmJobRunLogVO vo, ResultSet rs) throws SQLException {
		vo.setJobLogId( DAOUtils.trimStr( rs.getString( "job_log_id" ) ) );
		vo.setJobId( DAOUtils.trimStr( rs.getString( "job_id" ) ) );
		vo.setJobLogTime( DAOUtils.getFormatedDateTime( rs.getTimestamp( "job_log_time" ) ) );
		vo.setJobRunState( DAOUtils.trimStr( rs.getString( "job_run_state" ) ) );
		vo.setJobRunMsg( DAOUtils.trimStr( rs.getString( "job_run_msg" ) ) );
		vo.setJobName(DAOUtils.trimStr( rs.getString( "job_name" ) ));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		CrmJobRunLogVO vo = new CrmJobRunLogVO();
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

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
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
			Utils.closeResultSet(rs, this);
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
	}

	public void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			if ("".equals(((CrmJobRunLogVO)vo).getJobLogId())) {
				((CrmJobRunLogVO)vo).setJobLogId(null);
			}
			stmt.setString( index++, ((CrmJobRunLogVO)vo).getJobLogId() );
			if ("".equals(((CrmJobRunLogVO)vo).getJobId())) {
				((CrmJobRunLogVO)vo).setJobId(null);
			}
			stmt.setString( index++, ((CrmJobRunLogVO)vo).getJobId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((CrmJobRunLogVO)vo).getJobLogTime()) );
			if ("".equals(((CrmJobRunLogVO)vo).getJobRunState())) {
				((CrmJobRunLogVO)vo).setJobRunState(null);
			}
			stmt.setString( index++, ((CrmJobRunLogVO)vo).getJobRunState() );
			stmt.setString( index++, ((CrmJobRunLogVO)vo).getJobRunMsg() );
			stmt.executeUpdate();
			conn.commit();
		}
		catch (SQLException se) {
			Debug.print(SQL_INSERT,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
	}

	public boolean update( String pjob_log_id,CrmJobRunLogVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			sql.append( "UPDATE CRM_JOB_RUN_LOG SET job_log_id = ?,job_id = ?,job_log_time = ?,job_run_state = ?,job_run_msg = ?" );
			sql.append( " WHERE  job_log_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((CrmJobRunLogVO)vo).getJobLogId())) {
				((CrmJobRunLogVO)vo).setJobLogId(null);
			}
			stmt.setString( index++, vo.getJobLogId() );
			if ("".equals(((CrmJobRunLogVO)vo).getJobId())) {
				((CrmJobRunLogVO)vo).setJobId(null);
			}
			stmt.setString( index++, vo.getJobId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getJobLogTime()) );
			if ("".equals(((CrmJobRunLogVO)vo).getJobRunState())) {
				((CrmJobRunLogVO)vo).setJobRunState(null);
			}
			stmt.setString( index++, vo.getJobRunState() );
			stmt.setString( index++, vo.getJobRunMsg() );
			stmt.setString( index++, pjob_log_id );
			int rows = stmt.executeUpdate();
			if (rows>0) {
				bResult = true;
			}
			conn.commit();
		}
		catch (SQLException se) {
			Debug.print(sql.toString(),this);
			throw new DAOSystemException("SQLException while update sql:\n"+sql.toString(), se);
		}
		finally {
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
		return bResult;
	}

	public boolean update(String whereCond,VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			sql.append( "UPDATE CRM_JOB_RUN_LOG SET job_log_id = ?,job_id = ?,job_log_time = ?,job_run_state = ?,job_run_msg = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((CrmJobRunLogVO)vo).getJobLogId())) {
				((CrmJobRunLogVO)vo).setJobLogId(null);
			}
			stmt.setString( index++, ((CrmJobRunLogVO)vo).getJobLogId() );
			if ("".equals(((CrmJobRunLogVO)vo).getJobId())) {
				((CrmJobRunLogVO)vo).setJobId(null);
			}
			stmt.setString( index++, ((CrmJobRunLogVO)vo).getJobId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((CrmJobRunLogVO)vo).getJobLogTime()) );
			if ("".equals(((CrmJobRunLogVO)vo).getJobRunState())) {
				((CrmJobRunLogVO)vo).setJobRunState(null);
			}
			stmt.setString( index++, ((CrmJobRunLogVO)vo).getJobRunState() );
			stmt.setString( index++, ((CrmJobRunLogVO)vo).getJobRunMsg() );
			int rows = stmt.executeUpdate();
			if (rows>0) {
				bResult = true;
			}
			conn.commit();
		}
		catch (SQLException se) {
			Debug.print(sql.toString(),this);
			throw new DAOSystemException("SQLException while update sql:\n"+sql.toString(), se);
		}
		finally {
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
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
			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			SQL = SQL_SELECT_COUNT + whereCond;
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
			Utils.closeResultSet(rs, this);
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
		return lCount;
	}

	public long deleteByCond(String whereCond) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		String SQL = "";
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			SQL = SQL_DELETE_BY_WHERE + " WHERE " + whereCond;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
			rows = stmt.executeUpdate();
			conn.commit();
		}
		catch (SQLException se) {
			Debug.print(SQL,this);
			throw new DAOSystemException("SQLException while deleting sql:\n"+SQL, se);
		}
		finally {
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
		return rows;
	}

	public long delete( String pjob_log_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pjob_log_id );
			rows = stmt.executeUpdate();
			conn.commit();
		}
		catch (SQLException se) {
			Debug.print(SQL_DELETE,this);
			throw new DAOSystemException("SQLException while deleting sql:\n"+SQL_DELETE, se);
		}
		finally {
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
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
		return new CrmJobRunLogVO();
	}

	public List findByCond(String whereCond,QueryFilter queryFilter) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = SQL_SELECT +  whereCond + " order by a.job_log_time desc";
		String filterSQL = SQL;
		if (queryFilter != null) {
			filterSQL = queryFilter.doPreFilter(SQL);
		}
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			SQL = filterSQL;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL)  );
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
			Utils.closeResultSet(rs, this);
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
	}

}
