package com.ztesoft.oaas.dao.ratown;

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
import com.ztesoft.oaas.vo.RaTownVO;

public class RaTownDAOImpl    implements RaTownDAO {

	private String SQL_SELECT = "SELECT town_id,town_code,town_name,post_code,spell,city_id,lan_id,ppdom_id,business_id,exch_id,measure_id FROM RA_TOWN";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RA_TOWN";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO RA_TOWN ( town_id,town_code,town_name,post_code,spell,city_id,lan_id,ppdom_id,business_id,exch_id,measure_id ) VALUES ( ?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE RA_TOWN SET  town_code = ?, town_name = ?, post_code = ?, spell = ?, city_id = ?, lan_id = ?, ppdom_id = ?, business_id = ?, exch_id = ?, measure_id = ? WHERE town_id = ? ";

	private String SQL_DELETE = "DELETE FROM RA_TOWN WHERE town_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM RA_TOWN ";

	public RaTownDAOImpl() {

	}

	public RaTownVO findByPrimaryKey(String ptown_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE town_id = ? ", new String[] { ptown_id } );
		if (arrayList.size()>0)
			return (RaTownVO)arrayList.get(0);
		else
			return (RaTownVO) getEmptyVO();
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
			RaTownVO vo = new RaTownVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(RaTownVO vo, ResultSet rs) throws SQLException {
		vo.setTownId( rs.getString( "town_id" ) );
		vo.setTownCode( rs.getString( "town_code" ) );
		vo.setTownName( rs.getString( "town_name" ) );
		vo.setPostCode( rs.getString( "post_code" ) );
		vo.setSpell( rs.getString( "spell" ) );
		vo.setCityId( rs.getString( "city_id" ) );
		vo.setLanId( rs.getString( "lan_id" ) );
		vo.setPpdomId( rs.getString( "ppdom_id" ) );
		vo.setBusinessId( rs.getString( "business_id" ) );
		vo.setExchId( rs.getString( "exch_id" ) );
		vo.setMeasureId( rs.getString( "measure_id" ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		RaTownVO vo = new RaTownVO();
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
			((RaTownVO)vo).setTownId(smDAO.getNextSequence(vo.getTableName(), "TOWN_ID"));
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
            
			int index = 1;
			if ("".equals(((RaTownVO)vo).getTownId())) {
				((RaTownVO)vo).setTownId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getTownId() );
			stmt.setString( index++, ((RaTownVO)vo).getTownCode() );
			stmt.setString( index++, ((RaTownVO)vo).getTownName() );
			stmt.setString( index++, ((RaTownVO)vo).getPostCode() );
			stmt.setString( index++, ((RaTownVO)vo).getSpell() );
			if ("".equals(((RaTownVO)vo).getCityId())) {
				((RaTownVO)vo).setCityId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getCityId() );
			if ("".equals(((RaTownVO)vo).getLanId())) {
				((RaTownVO)vo).setLanId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getLanId() );
			if ("".equals(((RaTownVO)vo).getPpdomId())) {
				((RaTownVO)vo).setPpdomId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getPpdomId() );
			if ("".equals(((RaTownVO)vo).getBusinessId())) {
				((RaTownVO)vo).setBusinessId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getBusinessId() );
			if ("".equals(((RaTownVO)vo).getExchId())) {
				((RaTownVO)vo).setExchId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getExchId() );
			if ("".equals(((RaTownVO)vo).getMeasureId())) {
				((RaTownVO)vo).setMeasureId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getMeasureId() );
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

	public boolean update( String ptown_id,RaTownVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE RA_TOWN SET town_id = ?,town_code = ?,town_name = ?,post_code = ?,spell = ?,city_id = ?,lan_id = ?,ppdom_id = ?,business_id = ?,exch_id = ?,measure_id = ?" );
			sql.append( " WHERE  town_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((RaTownVO)vo).getTownId())) {
				((RaTownVO)vo).setTownId(null);
			}
			stmt.setString( index++, vo.getTownId() );
			stmt.setString( index++, vo.getTownCode() );
			stmt.setString( index++, vo.getTownName() );
			stmt.setString( index++, vo.getPostCode() );
			stmt.setString( index++, vo.getSpell() );
			if ("".equals(((RaTownVO)vo).getCityId())) {
				((RaTownVO)vo).setCityId(null);
			}
			stmt.setString( index++, vo.getCityId() );
			if ("".equals(((RaTownVO)vo).getLanId())) {
				((RaTownVO)vo).setLanId(null);
			}
			stmt.setString( index++, vo.getLanId() );
			if ("".equals(((RaTownVO)vo).getPpdomId())) {
				((RaTownVO)vo).setPpdomId(null);
			}
			stmt.setString( index++, vo.getPpdomId() );
			if ("".equals(((RaTownVO)vo).getBusinessId())) {
				((RaTownVO)vo).setBusinessId(null);
			}
			stmt.setString( index++, vo.getBusinessId() );
			if ("".equals(((RaTownVO)vo).getExchId())) {
				((RaTownVO)vo).setExchId(null);
			}
			stmt.setString( index++, vo.getExchId() );
			if ("".equals(((RaTownVO)vo).getMeasureId())) {
				((RaTownVO)vo).setMeasureId(null);
			}
			stmt.setString( index++, vo.getMeasureId() );
			stmt.setString( index++, ptown_id );
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
			sql.append( "UPDATE RA_TOWN SET town_id = ?,town_code = ?,town_name = ?,post_code = ?,spell = ?,city_id = ?,lan_id = ?,ppdom_id = ?,business_id = ?,exch_id = ?,measure_id = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((RaTownVO)vo).getTownId())) {
				((RaTownVO)vo).setTownId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getTownId() );
			stmt.setString( index++, ((RaTownVO)vo).getTownCode() );
			stmt.setString( index++, ((RaTownVO)vo).getTownName() );
			stmt.setString( index++, ((RaTownVO)vo).getPostCode() );
			stmt.setString( index++, ((RaTownVO)vo).getSpell() );
			if ("".equals(((RaTownVO)vo).getCityId())) {
				((RaTownVO)vo).setCityId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getCityId() );
			if ("".equals(((RaTownVO)vo).getLanId())) {
				((RaTownVO)vo).setLanId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getLanId() );
			if ("".equals(((RaTownVO)vo).getPpdomId())) {
				((RaTownVO)vo).setPpdomId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getPpdomId() );
			if ("".equals(((RaTownVO)vo).getBusinessId())) {
				((RaTownVO)vo).setBusinessId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getBusinessId() );
			if ("".equals(((RaTownVO)vo).getExchId())) {
				((RaTownVO)vo).setExchId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getExchId() );
			if ("".equals(((RaTownVO)vo).getMeasureId())) {
				((RaTownVO)vo).setMeasureId(null);
			}
			stmt.setString( index++, ((RaTownVO)vo).getMeasureId() );
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

	public long delete( String ptown_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, ptown_id );
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
		return new RaTownVO();
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
