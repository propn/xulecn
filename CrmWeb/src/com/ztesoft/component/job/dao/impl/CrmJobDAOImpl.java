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
import com.ztesoft.component.job.dao.CrmJobDAO;
import com.ztesoft.component.job.vo.CrmJobVO;

public class CrmJobDAOImpl   implements CrmJobDAO {

	private String SQL_SELECT = "SELECT job_id,job_name,job_group_name,job_class_name,job_type,job_interval,job_method,job_rule,job_runtime,job_start_day,job_terminate_day,job_desc,job_state,job_args,job_start_run,job_clustored,clustor_id FROM CRM_JOB";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM CRM_JOB";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO CRM_JOB ( job_id,job_name,job_group_name,job_class_name,job_type,job_interval,job_method,job_rule,job_runtime,job_start_day,job_terminate_day,job_desc,job_state,job_args,job_start_run,job_clustored) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_DELETE = "DELETE FROM CRM_JOB WHERE job_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM CRM_JOB ";

	private String RUN_SQL = "select b.job_id,a.job_name,b.clustor_id,b.last_runtime,b.last_run_endtime,b.last_runmsg from crm_job a,crm_job_clustor_state b where a.job_id = b.job_id order by b.job_id,b.clustor_id";

	public CrmJobDAOImpl() {

	}

	public CrmJobVO findByPrimaryKey(String pjob_id) throws DAOSystemException {
		ArrayList arrayList = findBySql(SQL_SELECT + " WHERE job_id = ? ", new String[] { pjob_id });
		if (arrayList.size() > 0)
			return (CrmJobVO) arrayList.get(0);
		else
			return (CrmJobVO) getEmptyVO();
	}

	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			stmt.setMaxRows(maxRows);
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setString(i + 1, sqlParams[i]);
			}

			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
		} catch (SQLException se) {
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + sql, se);
		} finally {
			Utils.closeResultSet(rs, this);
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
	}

	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			CrmJobVO vo = new CrmJobVO();
			populateDto(vo, rs);
			resultList.add(vo);
		}
		return resultList;
	}

	protected void populateDto(CrmJobVO vo, ResultSet rs) throws SQLException {
		vo.setJobId(DAOUtils.trimStr(rs.getString("job_id")));
		vo.setJobName(DAOUtils.trimStr(rs.getString("job_name")));
		vo.setJobGrpName(DAOUtils.trimStr(rs.getString("job_group_name")));
		vo.setJobClassName(DAOUtils.trimStr(rs.getString("job_class_name")));
		vo.setJobType(DAOUtils.trimStr(rs.getString("job_type")));
		vo.setJobInterval(DAOUtils.trimStr(rs.getString("job_interval")));
		vo.setJobMethod(DAOUtils.trimStr(rs.getString("job_method")));
		vo.setJobRule(DAOUtils.trimStr(rs.getString("job_rule")));
		vo.setJobRuntime(DAOUtils.trimStr(rs.getString("job_runtime")));
		vo.setJobStartDay(DAOUtils.getFormatedDateTime(rs.getTimestamp("job_start_day")));
		vo.setJobTerminateDay(DAOUtils.getFormatedDateTime(rs.getTimestamp("job_terminate_day")));
		vo.setJobDesc(DAOUtils.trimStr(rs.getString("job_desc")));
		vo.setJobState(DAOUtils.trimStr(rs.getString("job_state")));
		vo.setJobArgs(DAOUtils.trimStr(rs.getString("job_args")));
		vo.setJobStartRun(DAOUtils.trimStr(rs.getString("job_start_run")));
		vo.setJobClustored(DAOUtils.trimStr(rs.getString("job_clustored")));
		vo.setClustorId(DAOUtils.trimStr(rs.getString("clustor_id")));
		
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
		CrmJobVO vo = new CrmJobVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord³ö´í", this);
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
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			stmt.setMaxRows(maxRows);
			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + SQL, se);
		} finally {
			Utils.closeResultSet(rs, this);
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
	}

	public void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = Utils.getDBConnection(JNDINames.DEFAULT_DATASOURCE, this);
			
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));
			int index = 1;
			if ("".equals(((CrmJobVO) vo).getJobId())) {
				((CrmJobVO) vo).setJobId(null);
			}
			stmt.setString(index++, ((CrmJobVO) vo).getJobId());
			stmt.setString(index++, ((CrmJobVO) vo).getJobName());
			stmt.setString(index++, ((CrmJobVO) vo).getJobGrpName());
			stmt.setString(index++, ((CrmJobVO) vo).getJobClassName());
			if ("".equals(((CrmJobVO) vo).getJobType())) {
				((CrmJobVO) vo).setJobType(null);
			}
			stmt.setString(index++, ((CrmJobVO) vo).getJobType());
			if ("".equals(((CrmJobVO) vo).getJobInterval())) {
				((CrmJobVO) vo).setJobInterval(null);
			}
			stmt.setString(index++, ((CrmJobVO) vo).getJobInterval());
			if ("".equals(((CrmJobVO) vo).getJobMethod())) {
				((CrmJobVO) vo).setJobMethod(null);
			}
			stmt.setString(index++, ((CrmJobVO) vo).getJobMethod());
			stmt.setString(index++, ((CrmJobVO) vo).getJobRule());
			stmt.setString(index++, ((CrmJobVO) vo).getJobRuntime());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((CrmJobVO) vo).getJobStartDay()));
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((CrmJobVO) vo).getJobTerminateDay()));
			stmt.setString(index++, ((CrmJobVO) vo).getJobDesc());
			if ("".equals(((CrmJobVO) vo).getJobState())) {
				((CrmJobVO) vo).setJobState(null);
			}
			stmt.setString(index++, ((CrmJobVO) vo).getJobState());
			stmt.setString(index++, ((CrmJobVO) vo).getJobArgs());
			stmt.setString(index++, ((CrmJobVO) vo).getJobStartRun());
			stmt.setString(index++, ((CrmJobVO) vo).getJobClustored());
			stmt.executeUpdate();
			conn.commit();
		} catch (SQLException se) {
			Debug.print(SQL_INSERT, this);
			throw new DAOSystemException("SQLException while insert sql:\n" + SQL_INSERT, se);
		} finally {
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
	}

	public boolean update(String pjob_id, CrmJobVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			sql
					.append("UPDATE CRM_JOB SET job_id = ?,job_name = ?,job_group_name = ?,job_class_name = ?,job_type = ?,job_interval = ?,job_method = ?,job_rule = ?,job_runtime = ?,job_start_day = ?,job_terminate_day = ?,job_desc = ?,job_state = ?,job_args=?,job_start_run=?,job_clustored=?");
			sql.append(" WHERE  job_id = ? ");
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql.toString()));
			int index = 1;
			if ("".equals(((CrmJobVO) vo).getJobId())) {
				((CrmJobVO) vo).setJobId(null);
			}
			stmt.setString(index++, vo.getJobId());
			stmt.setString(index++, vo.getJobName());
			stmt.setString(index++, vo.getJobGrpName());
			stmt.setString(index++, vo.getJobClassName());
			if ("".equals(((CrmJobVO) vo).getJobType())) {
				((CrmJobVO) vo).setJobType(null);
			}
			stmt.setString(index++, vo.getJobType());
			if ("".equals(((CrmJobVO) vo).getJobInterval())) {
				((CrmJobVO) vo).setJobInterval(null);
			}
			stmt.setString(index++, vo.getJobInterval());
			if ("".equals(((CrmJobVO) vo).getJobMethod())) {
				((CrmJobVO) vo).setJobMethod(null);
			}
			stmt.setString(index++, vo.getJobMethod());
			stmt.setString(index++, vo.getJobRule());
			stmt.setString(index++, vo.getJobRuntime());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getJobStartDay()));
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getJobTerminateDay()));
			stmt.setString(index++, vo.getJobDesc());
			if ("".equals(((CrmJobVO) vo).getJobState())) {
				((CrmJobVO) vo).setJobState(null);
			}
			stmt.setString(index++, vo.getJobState());
			stmt.setString(index++, ((CrmJobVO) vo).getJobArgs());
			stmt.setString(index++, ((CrmJobVO) vo).getJobStartRun());
			stmt.setString(index++, ((CrmJobVO) vo).getJobClustored());
			stmt.setString(index++, pjob_id);
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				bResult = true;
			}
			conn.commit();
		} catch (SQLException se) {
			Debug.print(sql.toString(), this);
			throw new DAOSystemException("SQLException while update sql:\n" + sql.toString(), se);
		} finally {
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
		return bResult;
	}

	public boolean update(String whereCond, VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			sql
					.append("UPDATE CRM_JOB SET job_id = ?,job_name = ?,job_group_name = ?,job_class_name = ?,job_type = ?,job_interval = ?,job_method = ?,job_rule = ?,job_runtime = ?,job_start_day = ?,job_terminate_day = ?,job_desc = ?,job_state = ?,job_args=?,job_start_run=?,job_clustored=?");
			sql.append(" WHERE " + whereCond);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql.toString()));
			int index = 1;
			if ("".equals(((CrmJobVO) vo).getJobId())) {
				((CrmJobVO) vo).setJobId(null);
			}
			stmt.setString(index++, ((CrmJobVO) vo).getJobId());
			stmt.setString(index++, ((CrmJobVO) vo).getJobName());
			stmt.setString(index++, ((CrmJobVO) vo).getJobGrpName());
			stmt.setString(index++, ((CrmJobVO) vo).getJobClassName());
			if ("".equals(((CrmJobVO) vo).getJobType())) {
				((CrmJobVO) vo).setJobType(null);
			}
			stmt.setString(index++, ((CrmJobVO) vo).getJobType());
			if ("".equals(((CrmJobVO) vo).getJobInterval())) {
				((CrmJobVO) vo).setJobInterval(null);
			}
			stmt.setString(index++, ((CrmJobVO) vo).getJobInterval());
			if ("".equals(((CrmJobVO) vo).getJobMethod())) {
				((CrmJobVO) vo).setJobMethod(null);
			}
			stmt.setString(index++, ((CrmJobVO) vo).getJobMethod());
			stmt.setString(index++, ((CrmJobVO) vo).getJobRule());
			stmt.setString(index++, ((CrmJobVO) vo).getJobRuntime());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((CrmJobVO) vo).getJobStartDay()));
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((CrmJobVO) vo).getJobTerminateDay()));
			stmt.setString(index++, ((CrmJobVO) vo).getJobDesc());
			if ("".equals(((CrmJobVO) vo).getJobState())) {
				((CrmJobVO) vo).setJobState(null);
			}
			stmt.setString(index++, ((CrmJobVO) vo).getJobState());
			stmt.setString(index++, ((CrmJobVO) vo).getJobArgs());
			stmt.setString(index++, ((CrmJobVO) vo).getJobStartRun());
			stmt.setString(index++, ((CrmJobVO) vo).getJobClustored());
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				bResult = true;
			}
			conn.commit();
		} catch (SQLException se) {
			Debug.print(sql.toString(), this);
			throw new DAOSystemException("SQLException while update sql:\n" + sql.toString(), se);
		} finally {
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
			SQL = SQL_SELECT_COUNT + " WHERE " + whereCond;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			rs = stmt.executeQuery();

			while (rs.next()) {
				lCount = rs.getLong("COL_COUNTS");
			}
		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + SQL, se);
		} finally {
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
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			rows = stmt.executeUpdate();
			conn.commit();
		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while deleting sql:\n" + SQL, se);
		} finally {
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
		return rows;
	}

	public long delete(String pjob_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));
			int index = 1;
			stmt.setString(index++, pjob_id);
			rows = stmt.executeUpdate();
			conn.commit();

		} catch (SQLException se) {
			Debug.print(SQL_DELETE, this);
			throw new DAOSystemException("SQLException while deleting sql:\n" + SQL_DELETE, se);
		} finally {
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
		return new CrmJobVO();
	}

	public List findByCond(String whereCond, QueryFilter queryFilter) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = SQL_SELECT + " WHERE " + whereCond;
		String filterSQL = SQL;
		if (queryFilter != null) {
			filterSQL = queryFilter.doPreFilter(SQL);
		}
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			SQL = filterSQL;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL) );
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
			throw new DAOSystemException("SQLException while getting sql:\n" + SQL, se);
		} finally {
			Utils.closeResultSet(rs, this);
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
	}

	public List findRuningByCond() throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = RUN_SQL;
		String filterSQL = SQL;
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			SQL = filterSQL;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL) );
			rs = stmt.executeQuery();

			List retList = new ArrayList();
			while (rs.next()) {
				CrmJobVO vo = new CrmJobVO();
				vo.setJobId(DAOUtils.trimStr(rs.getString("job_id")));
				vo.setJobName(DAOUtils.trimStr(rs.getString("job_name")));
				vo.setClustorId(DAOUtils.trimStr(rs.getString("clustor_id")));
				vo.setLastExeuteBeginTime(DAOUtils.getFormatedDateTime( rs.getTimestamp("last_runtime")));
				vo.setLastExeuteEndTime(DAOUtils.getFormatedDateTime( rs.getTimestamp("last_run_endtime")));
				vo.setLastExeuteReSult(DAOUtils.trimStr(rs.getString("last_runmsg")));
				retList.add(vo);
			}
			return retList;
		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n" + SQL, se);
		} finally {
			Utils.closeResultSet(rs, this);
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE, this);
		}
	}
}
