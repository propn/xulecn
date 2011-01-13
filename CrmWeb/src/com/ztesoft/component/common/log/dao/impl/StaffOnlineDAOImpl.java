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
import com.ztesoft.component.common.log.dao.StaffOnlineDAO;
import com.ztesoft.component.common.log.vo.StaffOnlineVO;

public class StaffOnlineDAOImpl   implements StaffOnlineDAO {

	private String SQL_SELECT = "SELECT log_info_id,staff_id,staff_name,ip,machine_name,mac,logon_date,logout_date,online_state,addtion FROM STAFF_ONLINE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM STAFF_ONLINE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO STAFF_ONLINE ( log_info_id,staff_id,staff_name,ip,machine_name,mac,logon_date,logout_date,online_state,addtion ) VALUES ( ?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE STAFF_ONLINE SET  staff_id = ?, staff_name = ?, ip = ?, machine_name = ?, mac = ?, logon_date = ?, logout_date = ?, online_state = ?, addtion = ? WHERE log_info_id = ? ";

	private String SQL_DELETE = "DELETE FROM STAFF_ONLINE WHERE log_info_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM STAFF_ONLINE ";

	public StaffOnlineDAOImpl() {

	}

	public StaffOnlineVO findByPrimaryKey(String plog_info_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE log_info_id = ? ", new String[] { plog_info_id } );
		if (arrayList.size()>0)
			return (StaffOnlineVO)arrayList.get(0);
		else
			return (StaffOnlineVO) getEmptyVO();
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
			StaffOnlineVO vo = new StaffOnlineVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(StaffOnlineVO vo, ResultSet rs) throws SQLException {
		vo.setLogInfoId( DAOUtils.trimStr( rs.getString( "log_info_id" ) ) );
		vo.setStaffId( DAOUtils.trimStr( rs.getString( "staff_id" ) ) );
		vo.setStaffName( DAOUtils.trimStr( rs.getString( "staff_name" ) ) );
		vo.setIp( DAOUtils.trimStr( rs.getString( "ip" ) ) );
		vo.setMachineName( DAOUtils.trimStr( rs.getString( "machine_name" ) ) );
		vo.setMac( DAOUtils.trimStr( rs.getString( "mac" ) ) );
		vo.setLogonDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "logon_date" ) ) );
		vo.setLogoutDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "logout_date" ) ) );
		vo.setOnlineState( DAOUtils.trimStr( rs.getString( "online_state" ) ) );
		vo.setAddtion( DAOUtils.trimStr( rs.getString( "addtion" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		StaffOnlineVO vo = new StaffOnlineVO();
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
			SQL = SQL_SELECT + " WHERE " + whereCond + " order by logon_date desc ";
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
			String seqId = SeqDAOFactory.getInstance().getSequenceManageDAO().getNextSequence(vo.getTableName(), "LOG_INFO_ID");
			((StaffOnlineVO)vo).setLogInfoId(seqId);
			
			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			stmt.setString( index++, ((StaffOnlineVO)vo).getLogInfoId() );
			
			if ("".equals(((StaffOnlineVO)vo).getStaffId())) {
				((StaffOnlineVO)vo).setStaffId(null);
			}
			stmt.setString( index++, ((StaffOnlineVO)vo).getStaffId() );
			stmt.setString( index++, ((StaffOnlineVO)vo).getStaffName() );
			stmt.setString( index++, ((StaffOnlineVO)vo).getIp() );
			stmt.setString( index++, ((StaffOnlineVO)vo).getMachineName() );
			stmt.setString( index++, ((StaffOnlineVO)vo).getMac() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffOnlineVO)vo).getLogonDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffOnlineVO)vo).getLogoutDate()) );
			stmt.setString( index++, ((StaffOnlineVO)vo).getOnlineState() );
			stmt.setString( index++, ((StaffOnlineVO)vo).getAddtion() );
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

	public boolean update( String plog_info_id,StaffOnlineVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			sql.append( "UPDATE STAFF_ONLINE SET log_info_id = ?,staff_id = ?,staff_name = ?,ip = ?,machine_name = ?,mac = ?,logon_date = ?,logout_date = ?,online_state = ?,addtion = ?" );
			sql.append( " WHERE  log_info_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((StaffOnlineVO)vo).getLogInfoId())) {
				((StaffOnlineVO)vo).setLogInfoId(null);
			}
			stmt.setString( index++, vo.getLogInfoId() );
			if ("".equals(((StaffOnlineVO)vo).getStaffId())) {
				((StaffOnlineVO)vo).setStaffId(null);
			}
			stmt.setString( index++, vo.getStaffId() );
			stmt.setString( index++, vo.getStaffName() );
			stmt.setString( index++, vo.getIp() );
			stmt.setString( index++, vo.getMachineName() );
			stmt.setString( index++, vo.getMac() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getLogonDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getLogoutDate()) );
			stmt.setString( index++, vo.getOnlineState() );
			stmt.setString( index++, vo.getAddtion() );
			stmt.setString( index++, plog_info_id );
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
			sql.append( "UPDATE STAFF_ONLINE SET log_info_id = ?,staff_id = ?,staff_name = ?,ip = ?,machine_name = ?,mac = ?,logon_date = ?,logout_date = ?,online_state = ?,addtion = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((StaffOnlineVO)vo).getLogInfoId())) {
				((StaffOnlineVO)vo).setLogInfoId(null);
			}
			stmt.setString( index++, ((StaffOnlineVO)vo).getLogInfoId() );
			if ("".equals(((StaffOnlineVO)vo).getStaffId())) {
				((StaffOnlineVO)vo).setStaffId(null);
			}
			stmt.setString( index++, ((StaffOnlineVO)vo).getStaffId() );
			stmt.setString( index++, ((StaffOnlineVO)vo).getStaffName() );
			stmt.setString( index++, ((StaffOnlineVO)vo).getIp() );
			stmt.setString( index++, ((StaffOnlineVO)vo).getMachineName() );
			stmt.setString( index++, ((StaffOnlineVO)vo).getMac() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffOnlineVO)vo).getLogonDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffOnlineVO)vo).getLogoutDate()) );
			stmt.setString( index++, ((StaffOnlineVO)vo).getOnlineState() );
			stmt.setString( index++, ((StaffOnlineVO)vo).getAddtion() );
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

	public long delete( String plog_info_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, plog_info_id );
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
		return new StaffOnlineVO();
	}

	public List findByCond(String whereCond,QueryFilter queryFilter) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = SQL_SELECT + " WHERE " + whereCond + " order by logon_date desc ";
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

