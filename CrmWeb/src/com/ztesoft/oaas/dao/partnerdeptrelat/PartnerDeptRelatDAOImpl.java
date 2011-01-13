package com.ztesoft.oaas.dao.partnerdeptrelat;

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
import com.ztesoft.oaas.vo.PartnerDeptRelatVO;

public class PartnerDeptRelatDAOImpl   implements PartnerDeptRelatDAO {

	private String SQL_SELECT = "SELECT party_id,partner_id FROM PARTNER_DEPT_RELAT";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PARTNER_DEPT_RELAT";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PARTNER_DEPT_RELAT ( party_id,partner_id ) VALUES ( ?,? )";

	private String SQL_UPDATE = "UPDATE PARTNER_DEPT_RELAT SET  partner_id = ? WHERE party_id = ? ";

	private String SQL_DELETE = "DELETE FROM PARTNER_DEPT_RELAT WHERE party_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PARTNER_DEPT_RELAT ";

	public PartnerDeptRelatDAOImpl() {

	}

	public List queryDepartRelatByPartnerId( String partnerId ) throws DAOSystemException {
		String SQL = "SELECT ORG.ORG_NAME,ORG.ORG_CODE,REL.PARTY_ID,REL.PARTNER_ID FROM ORGANIZATION ORG, PARTNER_DEPT_RELAT REL WHERE ORG.PARTY_ID = REL.PARTY_ID AND REL.PARTNER_ID = ?";
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		try{
			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this ) ;
			pstmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL( SQL ) ) ;
			pstmt.setString(1, partnerId ) ;
			rs = pstmt.executeQuery() ;
			
			List resultList = new ArrayList();
			while (rs.next()) {
				PartnerDeptRelatVO vo = new PartnerDeptRelatVO();
				populateDto( vo, rs);
				vo.setPartyName( rs.getString("ORG_NAME") );
				vo.setPartyCode( rs.getString("ORG_CODE"));
				resultList.add( vo );
			}
			return resultList ;
		}catch (SQLException se) {
			Debug.print(DAOSQLUtils.getFilterSQL(SQL),this);
			throw new DAOSystemException("SQLException while getting sql:\n"+DAOSQLUtils.getFilterSQL(SQL), se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(pstmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
	
	public PartnerDeptRelatVO findByPrimaryKey(String pparty_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE party_id = ? ", new String[] { pparty_id } );
		if (arrayList.size()>0)
			return (PartnerDeptRelatVO)arrayList.get(0);
		else
			return (PartnerDeptRelatVO) getEmptyVO();
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
			PartnerDeptRelatVO vo = new PartnerDeptRelatVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(PartnerDeptRelatVO vo, ResultSet rs) throws SQLException {
		vo.setPartyId( DAOUtils.trimStr( rs.getString( "party_id" ) ) );
		vo.setPartnerId( DAOUtils.trimStr( rs.getString( "partner_id" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		PartnerDeptRelatVO vo = new PartnerDeptRelatVO();
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

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
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
			if ("".equals(((PartnerDeptRelatVO)vo).getPartyId())) {
				((PartnerDeptRelatVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((PartnerDeptRelatVO)vo).getPartyId() );
			if ("".equals(((PartnerDeptRelatVO)vo).getPartnerId())) {
				((PartnerDeptRelatVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, ((PartnerDeptRelatVO)vo).getPartnerId() );
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

	public boolean update( String pparty_id,PartnerDeptRelatVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE PARTNER_DEPT_RELAT SET party_id = ?,partner_id = ?" );
			sql.append( " WHERE  party_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PartnerDeptRelatVO)vo).getPartyId())) {
				((PartnerDeptRelatVO)vo).setPartyId(null);
			}
			stmt.setString( index++, vo.getPartyId() );
			if ("".equals(((PartnerDeptRelatVO)vo).getPartnerId())) {
				((PartnerDeptRelatVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, vo.getPartnerId() );
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
			sql.append( "UPDATE PARTNER_DEPT_RELAT SET party_id = ?,partner_id = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PartnerDeptRelatVO)vo).getPartyId())) {
				((PartnerDeptRelatVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((PartnerDeptRelatVO)vo).getPartyId() );
			if ("".equals(((PartnerDeptRelatVO)vo).getPartnerId())) {
				((PartnerDeptRelatVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, ((PartnerDeptRelatVO)vo).getPartnerId() );
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
		return new PartnerDeptRelatVO();
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

}
