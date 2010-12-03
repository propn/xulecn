package com.ztesoft.oaas.dao.racity;

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
import com.ztesoft.oaas.vo.RaCityVO;

public class RaCityDAOImpl    implements RaCityDAO {

	private String SQL_SELECT = "SELECT city_id,city_code,city_name,spell,post_code,lan_id,prov_id FROM RA_CITY";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RA_CITY";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO RA_CITY ( city_id,city_code,city_name,spell,post_code,lan_id,prov_id ) VALUES ( ?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE RA_CITY SET  city_code = ?, city_name = ?, spell = ?, post_code = ?, lan_id = ?, prov_id = ? WHERE city_id = ? ";

	private String SQL_DELETE = "DELETE FROM RA_CITY WHERE city_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM RA_CITY ";

	public RaCityDAOImpl() {

	}

	public RaCityVO findByPrimaryKey(String pcity_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE city_id = ? ", new String[] { pcity_id } );
		if (arrayList.size()>0)
			return (RaCityVO)arrayList.get(0);
		else
			return (RaCityVO) getEmptyVO();
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
			RaCityVO vo = new RaCityVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(RaCityVO vo, ResultSet rs) throws SQLException {
		vo.setCityId( rs.getString( "city_id" ) );
		vo.setCityCode( rs.getString( "city_code" ) );
		vo.setCityName( rs.getString( "city_name" ) );
		vo.setSpell( rs.getString( "spell" ) );
		vo.setPostCode( rs.getString( "post_code" ) );
		vo.setLanId( rs.getString( "lan_id" ) );
		vo.setProvId( rs.getString( "prov_id" ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		RaCityVO vo = new RaCityVO();
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
			((RaCityVO)vo).setCityId(smDAO.getNextSequence(vo.getTableName(), "CITY_ID"));
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
            
			int index = 1;
			if ("".equals(((RaCityVO)vo).getCityId())) {
				((RaCityVO)vo).setCityId(null);
			}
			stmt.setString( index++, ((RaCityVO)vo).getCityId() );
			stmt.setString( index++, ((RaCityVO)vo).getCityCode() );
			stmt.setString( index++, ((RaCityVO)vo).getCityName() );
			stmt.setString( index++, ((RaCityVO)vo).getSpell() );
			stmt.setString( index++, ((RaCityVO)vo).getPostCode() );
			if ("".equals(((RaCityVO)vo).getLanId())) {
				((RaCityVO)vo).setLanId(null);
			}
			stmt.setString( index++, ((RaCityVO)vo).getLanId() );
			if ("".equals(((RaCityVO)vo).getProvId())) {
				((RaCityVO)vo).setProvId(null);
			}
			stmt.setString( index++, ((RaCityVO)vo).getProvId() );
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

	public boolean update( String pcity_id,RaCityVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE RA_CITY SET city_id = ?,city_code = ?,city_name = ?,spell = ?,post_code = ?,lan_id = ?,prov_id = ?" );
			sql.append( " WHERE  city_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((RaCityVO)vo).getCityId())) {
				((RaCityVO)vo).setCityId(null);
			}
			stmt.setString( index++, vo.getCityId() );
			stmt.setString( index++, vo.getCityCode() );
			stmt.setString( index++, vo.getCityName() );
			stmt.setString( index++, vo.getSpell() );
			stmt.setString( index++, vo.getPostCode() );
			if ("".equals(((RaCityVO)vo).getLanId())) {
				((RaCityVO)vo).setLanId(null);
			}
			stmt.setString( index++, vo.getLanId() );
			if ("".equals(((RaCityVO)vo).getProvId())) {
				((RaCityVO)vo).setProvId(null);
			}
			stmt.setString( index++, vo.getProvId() );
			stmt.setString( index++, pcity_id );
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
			sql.append( "UPDATE RA_CITY SET city_id = ?,city_code = ?,city_name = ?,spell = ?,post_code = ?,lan_id = ?,prov_id = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((RaCityVO)vo).getCityId())) {
				((RaCityVO)vo).setCityId(null);
			}
			stmt.setString( index++, ((RaCityVO)vo).getCityId() );
			stmt.setString( index++, ((RaCityVO)vo).getCityCode() );
			stmt.setString( index++, ((RaCityVO)vo).getCityName() );
			stmt.setString( index++, ((RaCityVO)vo).getSpell() );
			stmt.setString( index++, ((RaCityVO)vo).getPostCode() );
			if ("".equals(((RaCityVO)vo).getLanId())) {
				((RaCityVO)vo).setLanId(null);
			}
			stmt.setString( index++, ((RaCityVO)vo).getLanId() );
			if ("".equals(((RaCityVO)vo).getProvId())) {
				((RaCityVO)vo).setProvId(null);
			}
			stmt.setString( index++, ((RaCityVO)vo).getProvId() );
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

	public long delete( String pcity_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pcity_id );
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
		return new RaCityVO();
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
