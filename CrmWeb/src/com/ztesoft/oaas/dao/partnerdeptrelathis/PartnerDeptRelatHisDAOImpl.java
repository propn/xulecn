package com.ztesoft.oaas.dao.partnerdeptrelathis;

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
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartnerDeptRelatHisVO;

public class PartnerDeptRelatHisDAOImpl   implements PartnerDeptRelatHisDAO {

	private String SQL_SELECT = "SELECT oper_hist_id,partner_id,party_id,oper_id,oper_time,oper_type FROM PARTNER_DEPT_RELAT_HIS";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PARTNER_DEPT_RELAT_HIS";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PARTNER_DEPT_RELAT_HIS ( oper_hist_id,partner_id,party_id,oper_id,oper_time,oper_type ) VALUES ( ?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PARTNER_DEPT_RELAT_HIS SET  oper_hist_id = ?, oper_id = ?, oper_time = ?, oper_type = ? WHERE partner_id = ? AND party_id = ? ";

	private String SQL_DELETE = "DELETE FROM PARTNER_DEPT_RELAT_HIS WHERE partner_id = ? AND party_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PARTNER_DEPT_RELAT_HIS ";

	public PartnerDeptRelatHisDAOImpl() {

	}

	public PartnerDeptRelatHisVO findByPrimaryKey(String ppartner_id,String pparty_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE partner_id = ? AND party_id = ? ", new String[] { ppartner_id,pparty_id } );
		if (arrayList.size()>0)
			return (PartnerDeptRelatHisVO)arrayList.get(0);
		else
			return (PartnerDeptRelatHisVO) getEmptyVO();
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
			PartnerDeptRelatHisVO vo = new PartnerDeptRelatHisVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(PartnerDeptRelatHisVO vo, ResultSet rs) throws SQLException {
		vo.setOperHistId( DAOUtils.trimStr( rs.getString( "oper_hist_id" ) ) );
		vo.setPartnerId( DAOUtils.trimStr( rs.getString( "partner_id" ) ) );
		vo.setPartyId( DAOUtils.trimStr( rs.getString( "party_id" ) ) );
		vo.setOperId( DAOUtils.trimStr( rs.getString( "oper_id" ) ) );
		vo.setOperTime( DAOUtils.getFormatedDateTime( rs.getTimestamp( "oper_time" ) ) );
		vo.setOperType( DAOUtils.trimStr( rs.getString( "oper_type" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		PartnerDeptRelatHisVO vo = new PartnerDeptRelatHisVO();
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

			SequenceManageDAO smDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
			String seq = smDAO.getNextSequence(vo.getTableName(), "OPER_HIST_ID");
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			
			((PartnerDeptRelatHisVO)vo).setOperHistId(seq);
			
			stmt.setString( index++, ((PartnerDeptRelatHisVO)vo).getOperHistId() );
			if ("".equals(((PartnerDeptRelatHisVO)vo).getPartnerId())) {
				((PartnerDeptRelatHisVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, ((PartnerDeptRelatHisVO)vo).getPartnerId() );
			if ("".equals(((PartnerDeptRelatHisVO)vo).getPartyId())) {
				((PartnerDeptRelatHisVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((PartnerDeptRelatHisVO)vo).getPartyId() );
			if ("".equals(((PartnerDeptRelatHisVO)vo).getOperId())) {
				((PartnerDeptRelatHisVO)vo).setOperId(null);
			}
			stmt.setString( index++, ((PartnerDeptRelatHisVO)vo).getOperId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartnerDeptRelatHisVO)vo).getOperTime()) );
			stmt.setString( index++, ((PartnerDeptRelatHisVO)vo).getOperType() );
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

	public boolean update( String ppartner_id, String pparty_id,PartnerDeptRelatHisVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE PARTNER_DEPT_RELAT_HIS SET oper_hist_id = ?,partner_id = ?,party_id = ?,oper_id = ?,oper_time = ?,oper_type = ?" );
			sql.append( " WHERE  partner_id = ? AND party_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PartnerDeptRelatHisVO)vo).getOperHistId())) {
				((PartnerDeptRelatHisVO)vo).setOperHistId(null);
			}
			stmt.setString( index++, vo.getOperHistId() );
			if ("".equals(((PartnerDeptRelatHisVO)vo).getPartnerId())) {
				((PartnerDeptRelatHisVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, vo.getPartnerId() );
			if ("".equals(((PartnerDeptRelatHisVO)vo).getPartyId())) {
				((PartnerDeptRelatHisVO)vo).setPartyId(null);
			}
			stmt.setString( index++, vo.getPartyId() );
			if ("".equals(((PartnerDeptRelatHisVO)vo).getOperId())) {
				((PartnerDeptRelatHisVO)vo).setOperId(null);
			}
			stmt.setString( index++, vo.getOperId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getOperTime()) );
			stmt.setString( index++, vo.getOperType() );
			stmt.setString( index++, ppartner_id );
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
			sql.append( "UPDATE PARTNER_DEPT_RELAT_HIS SET oper_hist_id = ?,partner_id = ?,party_id = ?,oper_id = ?,oper_time = ?,oper_type = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PartnerDeptRelatHisVO)vo).getOperHistId())) {
				((PartnerDeptRelatHisVO)vo).setOperHistId(null);
			}
			stmt.setString( index++, ((PartnerDeptRelatHisVO)vo).getOperHistId() );
			if ("".equals(((PartnerDeptRelatHisVO)vo).getPartnerId())) {
				((PartnerDeptRelatHisVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, ((PartnerDeptRelatHisVO)vo).getPartnerId() );
			if ("".equals(((PartnerDeptRelatHisVO)vo).getPartyId())) {
				((PartnerDeptRelatHisVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((PartnerDeptRelatHisVO)vo).getPartyId() );
			if ("".equals(((PartnerDeptRelatHisVO)vo).getOperId())) {
				((PartnerDeptRelatHisVO)vo).setOperId(null);
			}
			stmt.setString( index++, ((PartnerDeptRelatHisVO)vo).getOperId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartnerDeptRelatHisVO)vo).getOperTime()) );
			stmt.setString( index++, ((PartnerDeptRelatHisVO)vo).getOperType() );
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

	public long delete( String ppartner_id, String pparty_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, ppartner_id );
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
		return new PartnerDeptRelatHisVO();
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
