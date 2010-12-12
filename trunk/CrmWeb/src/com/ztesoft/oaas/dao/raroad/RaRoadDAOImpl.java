package com.ztesoft.oaas.dao.raroad;

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
import com.ztesoft.oaas.vo.RaRoadVO;

public class RaRoadDAOImpl    implements RaRoadDAO {

	private String SQL_SELECT = "SELECT road_id,road_code,road_name,post_code,spell,city_id,town_id,measure_id,region_id FROM RA_ROAD";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RA_ROAD";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO RA_ROAD ( road_id,road_code,road_name,post_code,spell,city_id,town_id,measure_id,region_id ) VALUES ( ?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE RA_ROAD SET  road_code = ?, road_name = ?, post_code = ?, spell = ?, city_id = ?, town_id = ?, measure_id = ?, region_id = ? WHERE road_id = ? ";

	private String SQL_DELETE = "DELETE FROM RA_ROAD WHERE road_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM RA_ROAD ";

	public RaRoadDAOImpl() {

	}

	public RaRoadVO findByPrimaryKey(String proad_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE road_id = ? ", new String[] { proad_id } );
		if (arrayList.size()>0)
			return (RaRoadVO)arrayList.get(0);
		else
			return (RaRoadVO) getEmptyVO();
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
			RaRoadVO vo = new RaRoadVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(RaRoadVO vo, ResultSet rs) throws SQLException {
		vo.setRoadId( rs.getString( "road_id" ) );
		vo.setRoadCode( rs.getString( "road_code" ) );
		vo.setRoadName( rs.getString( "road_name" ) );
		vo.setPostCode( rs.getString( "post_code" ) );
		vo.setSpell( rs.getString( "spell" ) );
		vo.setCityId( rs.getString( "city_id" ) );
		vo.setTownId( rs.getString( "town_id" ) );
		vo.setMeasureId( rs.getString( "measure_id" ) );
		vo.setRegionId( rs.getString( "region_id" ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		RaRoadVO vo = new RaRoadVO();
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
			((RaRoadVO)vo).setRoadId(smDAO.getNextSequence(vo.getTableName(), "ROAD_ID"));
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
            
			int index = 1;
			if ("".equals(((RaRoadVO)vo).getRoadId())) {
				((RaRoadVO)vo).setRoadId(null);
			}
			stmt.setString( index++, ((RaRoadVO)vo).getRoadId() );
			stmt.setString( index++, ((RaRoadVO)vo).getRoadCode() );
			stmt.setString( index++, ((RaRoadVO)vo).getRoadName() );
			stmt.setString( index++, ((RaRoadVO)vo).getPostCode() );
			stmt.setString( index++, ((RaRoadVO)vo).getSpell() );
			if ("".equals(((RaRoadVO)vo).getCityId())) {
				((RaRoadVO)vo).setCityId(null);
			}
			stmt.setString( index++, ((RaRoadVO)vo).getCityId() );
			if ("".equals(((RaRoadVO)vo).getTownId())) {
				((RaRoadVO)vo).setTownId(null);
			}
			stmt.setString( index++, ((RaRoadVO)vo).getTownId() );
			if ("".equals(((RaRoadVO)vo).getMeasureId())) {
				((RaRoadVO)vo).setMeasureId(null);
			}
			stmt.setString( index++, ((RaRoadVO)vo).getMeasureId() );
			if ("".equals(((RaRoadVO)vo).getRegionId())) {
				((RaRoadVO)vo).setRegionId(null);
			}
			stmt.setString( index++, ((RaRoadVO)vo).getRegionId() );
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

	public boolean update( String proad_id,RaRoadVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE RA_ROAD SET road_id = ?,road_code = ?,road_name = ?,post_code = ?,spell = ?,city_id = ?,town_id = ?,measure_id = ?,region_id = ?" );
			sql.append( " WHERE  road_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((RaRoadVO)vo).getRoadId())) {
				((RaRoadVO)vo).setRoadId(null);
			}
			stmt.setString( index++, vo.getRoadId() );
			stmt.setString( index++, vo.getRoadCode() );
			stmt.setString( index++, vo.getRoadName() );
			stmt.setString( index++, vo.getPostCode() );
			stmt.setString( index++, vo.getSpell() );
			if ("".equals(((RaRoadVO)vo).getCityId())) {
				((RaRoadVO)vo).setCityId(null);
			}
			stmt.setString( index++, vo.getCityId() );
			if ("".equals(((RaRoadVO)vo).getTownId())) {
				((RaRoadVO)vo).setTownId(null);
			}
			stmt.setString( index++, vo.getTownId() );
			if ("".equals(((RaRoadVO)vo).getMeasureId())) {
				((RaRoadVO)vo).setMeasureId(null);
			}
			stmt.setString( index++, vo.getMeasureId() );
			if ("".equals(((RaRoadVO)vo).getRegionId())) {
				((RaRoadVO)vo).setRegionId(null);
			}
			stmt.setString( index++, vo.getRegionId() );
			stmt.setString( index++, proad_id );
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
			sql.append( "UPDATE RA_ROAD SET road_id = ?,road_code = ?,road_name = ?,post_code = ?,spell = ?,city_id = ?,town_id = ?,measure_id = ?,region_id = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((RaRoadVO)vo).getRoadId())) {
				((RaRoadVO)vo).setRoadId(null);
			}
			stmt.setString( index++, ((RaRoadVO)vo).getRoadId() );
			stmt.setString( index++, ((RaRoadVO)vo).getRoadCode() );
			stmt.setString( index++, ((RaRoadVO)vo).getRoadName() );
			stmt.setString( index++, ((RaRoadVO)vo).getPostCode() );
			stmt.setString( index++, ((RaRoadVO)vo).getSpell() );
			if ("".equals(((RaRoadVO)vo).getCityId())) {
				((RaRoadVO)vo).setCityId(null);
			}
			stmt.setString( index++, ((RaRoadVO)vo).getCityId() );
			if ("".equals(((RaRoadVO)vo).getTownId())) {
				((RaRoadVO)vo).setTownId(null);
			}
			stmt.setString( index++, ((RaRoadVO)vo).getTownId() );
			if ("".equals(((RaRoadVO)vo).getMeasureId())) {
				((RaRoadVO)vo).setMeasureId(null);
			}
			stmt.setString( index++, ((RaRoadVO)vo).getMeasureId() );
			if ("".equals(((RaRoadVO)vo).getRegionId())) {
				((RaRoadVO)vo).setRegionId(null);
			}
			stmt.setString( index++, ((RaRoadVO)vo).getRegionId() );
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

	public long delete( String proad_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, proad_id );
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
		return new RaRoadVO();
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