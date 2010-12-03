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
import com.ztesoft.component.job.dao.CrmJobClustorDAO;
import com.ztesoft.component.job.vo.CrmJobClustorVO;
public class CrmJobClustorDAOImpl implements CrmJobClustorDAO {

	private String SQL_SELECT = "SELECT clustor_id,itime,otime FROM CRM_JOB_CLUSTOR";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM CRM_JOB_CLUSTOR";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO CRM_JOB_CLUSTOR ( clustor_id,itime,otime ) VALUES ( ?,?,? )";

	private String SQL_UPDATE = "UPDATE CRM_JOB_CLUSTOR SET  itime = ?, otime = ? WHERE clustor_id = ? ";

	private String SQL_DELETE = "DELETE FROM CRM_JOB_CLUSTOR WHERE clustor_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM CRM_JOB_CLUSTOR ";

	public CrmJobClustorDAOImpl() {

	}

	public CrmJobClustorVO findByPrimaryKey(String pclustor_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE clustor_id = ? ", new String[] { pclustor_id } );
		if (arrayList.size()>0)
			return (CrmJobClustorVO)arrayList.get(0);
		else
			return (CrmJobClustorVO) getEmptyVO();
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
			Utils.closeConnection(JNDINames.CRM_DATASOURCE);
		}
	}
	
	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			CrmJobClustorVO vo = new CrmJobClustorVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(CrmJobClustorVO vo, ResultSet rs) throws SQLException {
		vo.setClustorId( DAOUtils.trimStr( rs.getString( "clustor_id" ) ) );
		vo.setItime( DAOUtils.getFormatedDateTime( rs.getTimestamp( "itime" ) ) );
		vo.setOtime( DAOUtils.getFormatedDateTime( rs.getTimestamp( "otime" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		CrmJobClustorVO vo = new CrmJobClustorVO();
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
			Utils.closeConnection(JNDINames.CRM_DATASOURCE);
		}
	}

	public void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			
			stmt.setString( index++, ((CrmJobClustorVO)vo).getClustorId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((CrmJobClustorVO)vo).getItime()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((CrmJobClustorVO)vo).getOtime()) );
			int rows = stmt.executeUpdate();
			conn.commit();
		}
		catch (SQLException se) {
			Debug.print(SQL_INSERT,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			Utils.closeStatement(stmt, this);
			Utils.closeConnection(JNDINames.CRM_DATASOURCE);
		}
	}

	public boolean update( String pclustor_id,CrmJobClustorVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			sql.append( "UPDATE CRM_JOB_CLUSTOR SET clustor_id = ?,itime = ?,otime = ?" );
			sql.append( " WHERE  clustor_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			stmt.setString( index++, vo.getClustorId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getItime()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getOtime()) );
			stmt.setString( index++, pclustor_id );
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
			Utils.closeConnection(JNDINames.CRM_DATASOURCE);
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
			sql.append( "UPDATE CRM_JOB_CLUSTOR SET clustor_id = ?,itime = ?,otime = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			stmt.setString( index++, ((CrmJobClustorVO)vo).getClustorId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((CrmJobClustorVO)vo).getItime()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((CrmJobClustorVO)vo).getOtime()) );
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

	public long delete( String pclustor_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = Utils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pclustor_id );
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
		return new CrmJobClustorVO();
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
