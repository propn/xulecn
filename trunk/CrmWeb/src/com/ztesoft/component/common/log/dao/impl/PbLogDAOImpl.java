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
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.log.dao.PbLogDAO;
import com.ztesoft.component.common.log.vo.PbLogVO;

public class PbLogDAOImpl   implements PbLogDAO {

	private String SQL_SELECT = "SELECT id,module_id,action_id,log_type,staff_id,staff_name,log_detail,state,state_date,create_date,log_date FROM PB_LOG";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PB_LOG";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PB_LOG ( id,module_id,action_id,log_type,staff_id,staff_name,log_detail,state,state_date,create_date,log_date ) VALUES ( ?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PB_LOG SET  module_id = ?, action_id = ?, log_type = ?, staff_id = ?, staff_name = ?, log_detail = ?, state = ?, state_date = ?, create_date = ?, log_date = ? WHERE id = ? ";

	private String SQL_DELETE = "DELETE FROM PB_LOG WHERE id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PB_LOG ";

	public PbLogDAOImpl() {

	}

	public PbLogVO findByPrimaryKey(String pid) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE id = ? ", new String[] { pid } );
		if (arrayList.size()>0)
			return (PbLogVO)arrayList.get(0);
		else
			return (PbLogVO) getEmptyVO();
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
			PbLogVO vo = new PbLogVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(PbLogVO vo, ResultSet rs) throws SQLException {
		vo.setId( DAOUtils.trimStr( rs.getString( "id" ) ) );
		vo.setModuleId( DAOUtils.trimStr( rs.getString( "module_id" ) ) );
		vo.setActId( DAOUtils.trimStr( rs.getString( "action_id" ) ) );
		vo.setLogType( DAOUtils.trimStr( rs.getString( "log_type" ) ) );
		vo.setStaffId( DAOUtils.trimStr( rs.getString( "staff_id" ) ) );
		vo.setStaffName( DAOUtils.trimStr( rs.getString( "staff_name" ) ) );
		vo.setLogDeta( DAOUtils.trimStr( rs.getString( "log_detail" ) ) );
		vo.setState( DAOUtils.trimStr( rs.getString( "state" ) ) );
		vo.setStateDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "state_date" ) ) );
		vo.setCreateDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "create_date" ) ) );
		vo.setLogDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "log_date" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		PbLogVO vo = new PbLogVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord出错",this);
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
			String seqId = SeqDAOFactory.getInstance().getSequenceManageDAO().getNextSequence(vo.getTableName(), "ID");
			
			((PbLogVO)vo).setId(seqId);
			
			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			
			int index = 1;
			
			stmt.setString( index++, ((PbLogVO)vo).getId() );
			if ("".equals(((PbLogVO)vo).getModuleId())) {
				((PbLogVO)vo).setModuleId(null);
			}
			stmt.setString( index++, ((PbLogVO)vo).getModuleId() );
			if ("".equals(((PbLogVO)vo).getActId())) {
				((PbLogVO)vo).setActId(null);
			}
			stmt.setString( index++, ((PbLogVO)vo).getActId() );
			stmt.setString( index++, ((PbLogVO)vo).getLogType() );
			if ("".equals(((PbLogVO)vo).getStaffId())) {
				((PbLogVO)vo).setStaffId(null);
			}
			stmt.setString( index++, ((PbLogVO)vo).getStaffId() );
			stmt.setString( index++, ((PbLogVO)vo).getStaffName() );
			stmt.setString( index++, ((PbLogVO)vo).getLogDeta() );
			stmt.setString( index++, ((PbLogVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PbLogVO)vo).getStateDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PbLogVO)vo).getCreateDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PbLogVO)vo).getLogDate()) );
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

	public boolean update( String pid,PbLogVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			sql.append( "UPDATE PB_LOG SET id = ?,module_id = ?,action_id = ?,log_type = ?,staff_id = ?,staff_name = ?,log_detail = ?,state = ?,state_date = ?,create_date = ?,log_date = ?" );
			sql.append( " WHERE  id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PbLogVO)vo).getId())) {
				((PbLogVO)vo).setId(null);
			}
			stmt.setString( index++, vo.getId() );
			if ("".equals(((PbLogVO)vo).getModuleId())) {
				((PbLogVO)vo).setModuleId(null);
			}
			stmt.setString( index++, vo.getModuleId() );
			if ("".equals(((PbLogVO)vo).getActId())) {
				((PbLogVO)vo).setActId(null);
			}
			stmt.setString( index++, vo.getActId() );
			stmt.setString( index++, vo.getLogType() );
			if ("".equals(((PbLogVO)vo).getStaffId())) {
				((PbLogVO)vo).setStaffId(null);
			}
			stmt.setString( index++, vo.getStaffId() );
			stmt.setString( index++, vo.getStaffName() );
			stmt.setString( index++, vo.getLogDeta() );
			stmt.setString( index++, vo.getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getStateDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getCreateDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getLogDate()) );
			stmt.setString( index++, pid );
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
			sql.append( "UPDATE PB_LOG SET id = ?,module_id = ?,action_id = ?,log_type = ?,staff_id = ?,staff_name=?,log_detail = ?,state = ?,state_date = ?,create_date = ?,log_date = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PbLogVO)vo).getId())) {
				((PbLogVO)vo).setId(null);
			}
			stmt.setString( index++, ((PbLogVO)vo).getId() );
			if ("".equals(((PbLogVO)vo).getModuleId())) {
				((PbLogVO)vo).setModuleId(null);
			}
			stmt.setString( index++, ((PbLogVO)vo).getModuleId() );
			if ("".equals(((PbLogVO)vo).getActId())) {
				((PbLogVO)vo).setActId(null);
			}
			stmt.setString( index++, ((PbLogVO)vo).getActId() );
			stmt.setString( index++, ((PbLogVO)vo).getLogType() );
			if ("".equals(((PbLogVO)vo).getStaffId())) {
				((PbLogVO)vo).setStaffId(null);
			}
			stmt.setString( index++, ((PbLogVO)vo).getStaffId() );
			stmt.setString( index++, ((PbLogVO)vo).getStaffName() );
			stmt.setString( index++, ((PbLogVO)vo).getLogDeta() );
			stmt.setString( index++, ((PbLogVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PbLogVO)vo).getStateDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PbLogVO)vo).getCreateDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PbLogVO)vo).getLogDate()) );
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

	/**
	 * 删除操作不执行数据删除,而是将状态修改为"删除"状态
	 */
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

	public long delete( String pid) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pid );
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
		return new PbLogVO();
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

