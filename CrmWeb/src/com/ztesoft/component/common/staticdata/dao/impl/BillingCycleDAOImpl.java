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
import com.ztesoft.component.common.staticdata.dao.BillingCycleDAO;
import com.ztesoft.component.common.staticdata.vo.BillingCycleVO;
public class BillingCycleDAOImpl   implements BillingCycleDAO {

	private String SQL_SELECT = "SELECT billing_cycle_id,last_billing_cycle_id,cycle_begin_date,cycle_end_date,due_date,block_date,state,state_date FROM BILLING_CYCLE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM BILLING_CYCLE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO BILLING_CYCLE ( billing_cycle_id,last_billing_cycle_id,cycle_begin_date,cycle_end_date,due_date,block_date,state,state_date ) VALUES ( ?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE BILLING_CYCLE SET  last_billing_cycle_id = ?, cycle_begin_date = ?, cycle_end_date = ?, due_date = ?, block_date = ?, state = ?, state_date = ? WHERE billing_cycle_id = ? ";

	private String SQL_DELETE = "DELETE FROM BILLING_CYCLE WHERE billing_cycle_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM BILLING_CYCLE ";

	public BillingCycleDAOImpl() {

	}

	public BillingCycleVO findByPrimaryKey(String pbilling_cycle_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE billing_cycle_id = ? ", new String[] { pbilling_cycle_id } );
		if (arrayList.size()>0)
			return (BillingCycleVO)arrayList.get(0);
		else
			return (BillingCycleVO) getEmptyVO();
	}

	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql,DAOSQLUtils.BSN_DB) );
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
			BillingCycleVO vo = new BillingCycleVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(BillingCycleVO vo, ResultSet rs) throws SQLException {
		vo.setBillingCycleId( DAOUtils.trimStr( rs.getString( "billing_cycle_id" ) ) );
		vo.setLastBillingCycleId( DAOUtils.trimStr( rs.getString( "last_billing_cycle_id" ) ) );
		vo.setCycleBeginDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "cycle_begin_date" ) ) );
		vo.setCycleEndDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "cycle_end_date" ) ) );
		vo.setDueDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "due_date" ) ) );
		vo.setBlockDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "block_date" ) ) );
		vo.setState( DAOUtils.trimStr( rs.getString( "state" ) ) );
		vo.setStateDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "state_date" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		BillingCycleVO vo = new BillingCycleVO();
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

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			SQL = SQL_SELECT + " WHERE " + whereCond;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.BSN_DB) );
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

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			if ("".equals(((BillingCycleVO)vo).getBillingCycleId())) {
				((BillingCycleVO)vo).setBillingCycleId(null);
			}
			stmt.setString( index++, ((BillingCycleVO)vo).getBillingCycleId() );
			if ("".equals(((BillingCycleVO)vo).getLastBillingCycleId())) {
				((BillingCycleVO)vo).setLastBillingCycleId(null);
			}
			stmt.setString( index++, ((BillingCycleVO)vo).getLastBillingCycleId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((BillingCycleVO)vo).getCycleBeginDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((BillingCycleVO)vo).getCycleEndDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((BillingCycleVO)vo).getDueDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((BillingCycleVO)vo).getBlockDate()) );
			stmt.setString( index++, ((BillingCycleVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((BillingCycleVO)vo).getStateDate()) );
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

	public boolean update( String pbilling_cycle_id,BillingCycleVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			sql.append( "UPDATE BILLING_CYCLE SET billing_cycle_id = ?,last_billing_cycle_id = ?,cycle_begin_date = ?,cycle_end_date = ?,due_date = ?,block_date = ?,state = ?,state_date = ?" );
			sql.append( " WHERE  billing_cycle_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((BillingCycleVO)vo).getBillingCycleId())) {
				((BillingCycleVO)vo).setBillingCycleId(null);
			}
			stmt.setString( index++, vo.getBillingCycleId() );
			if ("".equals(((BillingCycleVO)vo).getLastBillingCycleId())) {
				((BillingCycleVO)vo).setLastBillingCycleId(null);
			}
			stmt.setString( index++, vo.getLastBillingCycleId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getCycleBeginDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getCycleEndDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getDueDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getBlockDate()) );
			stmt.setString( index++, vo.getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getStateDate()) );
			stmt.setString( index++, pbilling_cycle_id );
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

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			sql.append( "UPDATE BILLING_CYCLE SET billing_cycle_id = ?,last_billing_cycle_id = ?,cycle_begin_date = ?,cycle_end_date = ?,due_date = ?,block_date = ?,state = ?,state_date = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((BillingCycleVO)vo).getBillingCycleId())) {
				((BillingCycleVO)vo).setBillingCycleId(null);
			}
			stmt.setString( index++, ((BillingCycleVO)vo).getBillingCycleId() );
			if ("".equals(((BillingCycleVO)vo).getLastBillingCycleId())) {
				((BillingCycleVO)vo).setLastBillingCycleId(null);
			}
			stmt.setString( index++, ((BillingCycleVO)vo).getLastBillingCycleId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((BillingCycleVO)vo).getCycleBeginDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((BillingCycleVO)vo).getCycleEndDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((BillingCycleVO)vo).getDueDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((BillingCycleVO)vo).getBlockDate()) );
			stmt.setString( index++, ((BillingCycleVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((BillingCycleVO)vo).getStateDate()) );
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
			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			SQL = SQL_SELECT_COUNT + " WHERE " + whereCond;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.BSN_DB) );
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

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			SQL = SQL_DELETE_BY_WHERE + " WHERE " + whereCond;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.BSN_DB) );
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

	public long delete( String pbilling_cycle_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pbilling_cycle_id );
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
		return new BillingCycleVO();
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

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			SQL = filterSQL;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.BSN_DB) );
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
