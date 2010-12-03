package com.ztesoft.oaas.dao.party;

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
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartyVO;

public class PartyDAOImpl   implements PartyDAO {

	private String SQL_SELECT = "SELECT party_id,party_name,eff_date,state,state_date,address_id,add_description FROM PARTY";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PARTY";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PARTY ( party_id,party_name,eff_date,state,state_date,address_id,add_description, region_id ) VALUES ( ?,?,?,?,?,?,?,?)";

	private String SQL_UPDATE = "UPDATE PARTY SET  party_name = ?, eff_date = ?, state = ?, state_date = ?, address_id = ?, add_description = ? WHERE party_id = ? ";

	private String SQL_DELETE = "DELETE FROM PARTY WHERE party_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PARTY ";

	public PartyDAOImpl() {

	}

	public PartyVO findByPrimaryKey(String pparty_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE party_id = ? ", new String[] { pparty_id } );
		if (arrayList.size()>0)
			return (PartyVO)arrayList.get(0);
		else
			return (PartyVO) getEmptyVO();
	}

	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql = DAOSQLUtils.getFilterSQL(sql) ;
			stmt = conn.prepareStatement( sql );
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
			PartyVO vo = new PartyVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(PartyVO vo, ResultSet rs) throws SQLException {
		vo.setPartyId( rs.getString( "party_id" ) );
		vo.setPartyName( rs.getString( "party_name" ) );
		vo.setEffDate( DAOUtils.getFormatedDate(rs.getDate( "eff_date" ) ));
		vo.setState( rs.getString( "state" ) );
		vo.setStateDate( DAOUtils.getFormatedDate(rs.getDate( "state_date" ) ));
		vo.setAddrId( rs.getString( "address_id" ) );
		vo.setAddDescription( rs.getString( "add_description" ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		PartyVO vo = new PartyVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord����",this);
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
			((PartyVO)vo).setPartyId(smDAO.getNextSequence(vo.getTableName(), "PARTY_ID"));
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
            
			int index = 1;
			if ("".equals(((PartyVO)vo).getPartyId())) {
				((PartyVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((PartyVO)vo).getPartyId() );
			stmt.setString( index++, ((PartyVO)vo).getPartyName() );
			
			if("".equals(((PartyVO)vo).getEffDate() )){
				((PartyVO)vo).setEffDate(DateFormatUtils.getFormatedDateTime());
			}
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartyVO)vo).getEffDate()) );
			stmt.setString( index++, ((PartyVO)vo).getState() );
			
			stmt.setTimestamp( index++, DAOUtils.getCurrentTimestamp() );
			
			if ("".equals(((PartyVO)vo).getAddrId())) {
				((PartyVO)vo).setAddrId(null);
			}
			stmt.setString( index++, ((PartyVO)vo).getAddrId() );
			stmt.setString( index++, ((PartyVO)vo).getAddDescription() );
			stmt.setString(index++, "-1");
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

	public boolean update( String pparty_id,PartyVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE PARTY SET party_id = ?,party_name = ?,eff_date = ?,state = ?,state_date = ?,address_id = ?,add_description = ?" );
			sql.append( " WHERE  party_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PartyVO)vo).getPartyId())) {
				((PartyVO)vo).setPartyId(null);
			}
			stmt.setString( index++, vo.getPartyId() );
			stmt.setString( index++, vo.getPartyName() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getEffDate()) );
			stmt.setString( index++, vo.getState() );
			
			//stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getStateDate()) );
			stmt.setTimestamp( index++, DAOUtils.getCurrentTimestamp() );
			
			if ("".equals(((PartyVO)vo).getAddrId())) {
				((PartyVO)vo).setAddrId(null);
			}
			stmt.setString( index++, vo.getAddrId() );
			stmt.setString( index++, vo.getAddDescription() );
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
			sql.append( "UPDATE PARTY SET party_id = ?,party_name = ?,eff_date = ?,state = ?,state_date = ?,address_id = ?,add_description = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PartyVO)vo).getPartyId())) {
				((PartyVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((PartyVO)vo).getPartyId() );
			stmt.setString( index++, ((PartyVO)vo).getPartyName() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartyVO)vo).getEffDate()) );
			stmt.setString( index++, ((PartyVO)vo).getState() );
			//stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartyVO)vo).getStateDate()) );
			stmt.setTimestamp( index++, DAOUtils.getCurrentTimestamp() );
			if ("".equals(((PartyVO)vo).getAddrId())) {
				((PartyVO)vo).setAddrId(null);
			}
			stmt.setString( index++, ((PartyVO)vo).getAddrId() );
			stmt.setString( index++, ((PartyVO)vo).getAddDescription() );
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
		return new PartyVO();
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
