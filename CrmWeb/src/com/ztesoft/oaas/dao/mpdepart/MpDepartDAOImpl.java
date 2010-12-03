package com.ztesoft.oaas.dao.mpdepart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.MpDepartVO;

public class MpDepartDAOImpl   implements MpDepartDAO {
		
	private String SQL_SELECT = "SELECT dep.party_id,dep.depart_type,dep.term_flag,dep.pay_seat_type,dep.region_id,dep.super_party_id,org.org_name FROM MP_DEPARTMENT dep, organization org where org.party_id = dep.party_id";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM MP_DEPARTMENT";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO MP_DEPARTMENT ( party_id,depart_type,term_flag,pay_seat_type,region_id,super_party_id) VALUES ( ?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE MP_DEPARTMENT SET  depart_type = ?, term_flag = ?, pay_seat_type = ?, region_id = ?, super_party_id =? WHERE party_id = ? ";

	private String SQL_DELETE = "DELETE FROM MP_DEPARTMENT WHERE party_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM MP_DEPARTMENT ";

	public MpDepartDAOImpl() {

	}

	public MpDepartVO findByPrimaryKey(String pparty_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " AND dep.party_id = ? ", new String[] { pparty_id } );
		if (arrayList.size()>0)
			return (MpDepartVO)arrayList.get(0);
		else
			return null;
	}

	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
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
			//DAOUtils.closeConnection(conn, this);
		}
	}

	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			MpDepartVO vo = new MpDepartVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(MpDepartVO vo, ResultSet rs) throws SQLException {
		vo.setPartyId( rs.getString( "party_id" ) );
		vo.setDepartType( rs.getString( "depart_type" ) );
		vo.setTermFlag( rs.getString( "term_flag" ) );
		vo.setPaySeatType( rs.getString( "pay_seat_type" ) );
		vo.setRegionId( rs.getString( "region_id" ) );
		vo.setSuperPartyId( rs.getString("super_party_id"));
		vo.setOrgName(rs.getString("org_name"));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		MpDepartVO vo = new MpDepartVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord³ö´í",this);
			throw new DAOSystemException("SQLException while populateDto:\n", se);
		}
		return vo;
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

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			SQL = filterSQL;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
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
			//DAOUtils.closeConnection(conn, this);
		}
	}
	public List findByCond(String whereCond) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = "";
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			SQL = SQL_SELECT + " AND " + whereCond;
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
			//DAOUtils.closeConnection(conn, this);
		}
	}

	public void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			if ("".equals(((MpDepartVO)vo).getPartyId())) {
				((MpDepartVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((MpDepartVO)vo).getPartyId() );
			stmt.setString( index++, ((MpDepartVO)vo).getDepartType() );
			stmt.setString( index++, ((MpDepartVO)vo).getTermFlag() );
			stmt.setString( index++, ((MpDepartVO)vo).getPaySeatType() );
			if ("".equals(((MpDepartVO)vo).getRegionId())) {
				((MpDepartVO)vo).setRegionId(null);
			}
			stmt.setString( index++, ((MpDepartVO)vo).getRegionId() );
			stmt.setString( index++, ((MpDepartVO)vo).getSuperPartyId());
			int rows = stmt.executeUpdate();
		}
		catch (SQLException se) {
			Debug.print(SQL_INSERT,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	public boolean update( String pparty_id,MpDepartVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE MP_DEPARTMENT SET party_id = ?,depart_type = ?,term_flag = ?,pay_seat_type = ?,region_id = ?,super_party_id = ?" );
			sql.append( " WHERE  party_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((MpDepartVO)vo).getPartyId())) {
				((MpDepartVO)vo).setPartyId(null);
			}
			stmt.setString( index++, vo.getPartyId() );
			stmt.setString( index++, vo.getDepartType() );
			stmt.setString( index++, vo.getTermFlag() );
			stmt.setString( index++, vo.getPaySeatType() );
			if ("".equals(((MpDepartVO)vo).getRegionId())) {
				((MpDepartVO)vo).setRegionId(null);
			}
			stmt.setString( index++, vo.getRegionId() );
			stmt.setString( index++, vo.getSuperPartyId());
			stmt.setString( index++, pparty_id );
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
			//DAOUtils.closeConnection(conn, this);
		}
		return bResult;
	}

	public boolean update(String whereCond,VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE MP_DEPARTMENT SET party_id = ?,depart_type = ?,term_flag = ?,pay_seat_type = ?,region_id = ?,super_party_id = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((MpDepartVO)vo).getPartyId())) {
				((MpDepartVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((MpDepartVO)vo).getPartyId() );
			stmt.setString( index++, ((MpDepartVO)vo).getDepartType() );
			stmt.setString( index++, ((MpDepartVO)vo).getTermFlag() );
			stmt.setString( index++, ((MpDepartVO)vo).getPaySeatType() );
			if ("".equals(((MpDepartVO)vo).getRegionId())) {
				((MpDepartVO)vo).setRegionId(null);
			}
			stmt.setString( index++, ((MpDepartVO)vo).getRegionId() );
			stmt.setString( index++, ((MpDepartVO)vo).getSuperPartyId());
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
			//DAOUtils.closeConnection(conn, this);
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
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
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
			//DAOUtils.closeConnection(conn, this);
		}
		return lCount;
	}

	public long deleteByCond(String whereCond) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		String SQL = "";
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
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
			//DAOUtils.closeConnection(conn, this);
		}
		return rows;
	}

	public long delete( String pparty_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pparty_id );
			rows = stmt.executeUpdate();

		}
		catch (SQLException se) {
			Debug.print(SQL_DELETE,this);
			throw new DAOSystemException("SQLException while deleting sql:\n"+SQL_DELETE, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
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
		return new MpDepartVO();
	}

}
