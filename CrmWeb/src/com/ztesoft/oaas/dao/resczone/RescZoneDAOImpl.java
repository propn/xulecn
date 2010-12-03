package com.ztesoft.oaas.dao.resczone;

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
import com.ztesoft.oaas.vo.RescZoneVO;

public class RescZoneDAOImpl    implements RescZoneDAO {

	private String SQL_SELECT = "SELECT resource_zone_id,exchange_id,region_id,resouce_zone_name,resource_id FROM RESOURCE_ZONE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RESOURCE_ZONE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO RESOURCE_ZONE ( resource_zone_id,exchange_id,region_id,resouce_zone_name,resource_id ) VALUES ( ?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE RESOURCE_ZONE SET  exchange_id = ?, region_id = ?, resouce_zone_name = ?, resource_id = ? WHERE resource_zone_id = ? ";

	private String SQL_DELETE = "DELETE FROM RESOURCE_ZONE WHERE resource_zone_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM RESOURCE_ZONE ";

	public RescZoneDAOImpl() {

	}

	public RescZoneVO findByPrimaryKey(String presource_zone_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE resource_zone_id = ? ", new String[] { presource_zone_id } );
		if (arrayList.size()>0)
			return (RescZoneVO)arrayList.get(0);
		else
			return (RescZoneVO) getEmptyVO();
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
			RescZoneVO vo = new RescZoneVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(RescZoneVO vo, ResultSet rs) throws SQLException {
		vo.setRescZoneId( rs.getString( "resource_zone_id" ) );
		vo.setExchId( rs.getString( "exchange_id" ) );
		vo.setRegionId( rs.getString( "region_id" ) );
		vo.setResouceZoneName( rs.getString( "resouce_zone_name" ) );
		vo.setRescId( rs.getString( "resource_id" ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		RescZoneVO vo = new RescZoneVO();
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
			((RescZoneVO)vo).setRescZoneId(smDAO.getNextSequence(vo.getTableName(), "RESOURCE_ZONE_ID"));
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
            
			int index = 1;
			if ("".equals(((RescZoneVO)vo).getRescZoneId())) {
				((RescZoneVO)vo).setRescZoneId(null);
			}
			stmt.setString( index++, ((RescZoneVO)vo).getRescZoneId() );
			if ("".equals(((RescZoneVO)vo).getExchId())) {
				((RescZoneVO)vo).setExchId(null);
			}
			stmt.setString( index++, ((RescZoneVO)vo).getExchId() );
			if ("".equals(((RescZoneVO)vo).getRegionId())) {
				((RescZoneVO)vo).setRegionId(null);
			}
			stmt.setString( index++, ((RescZoneVO)vo).getRegionId() );
			stmt.setString( index++, ((RescZoneVO)vo).getResouceZoneName() );
			if ("".equals(((RescZoneVO)vo).getRescId())) {
				((RescZoneVO)vo).setRescId(null);
			}
			stmt.setString( index++, ((RescZoneVO)vo).getRescId() );
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

	public boolean update( String presource_zone_id,RescZoneVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE RESOURCE_ZONE SET resource_zone_id = ?,exchange_id = ?,region_id = ?,resouce_zone_name = ?,resource_id = ?" );
			sql.append( " WHERE  resource_zone_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((RescZoneVO)vo).getRescZoneId())) {
				((RescZoneVO)vo).setRescZoneId(null);
			}
			stmt.setString( index++, vo.getRescZoneId() );
			if ("".equals(((RescZoneVO)vo).getExchId())) {
				((RescZoneVO)vo).setExchId(null);
			}
			stmt.setString( index++, vo.getExchId() );
			if ("".equals(((RescZoneVO)vo).getRegionId())) {
				((RescZoneVO)vo).setRegionId(null);
			}
			stmt.setString( index++, vo.getRegionId() );
			stmt.setString( index++, vo.getResouceZoneName() );
			if ("".equals(((RescZoneVO)vo).getRescId())) {
				((RescZoneVO)vo).setRescId(null);
			}
			stmt.setString( index++, vo.getRescId() );
			stmt.setString( index++, presource_zone_id );
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
			sql.append( "UPDATE RESOURCE_ZONE SET resource_zone_id = ?,exchange_id = ?,region_id = ?,resouce_zone_name = ?,resource_id = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((RescZoneVO)vo).getRescZoneId())) {
				((RescZoneVO)vo).setRescZoneId(null);
			}
			stmt.setString( index++, ((RescZoneVO)vo).getRescZoneId() );
			if ("".equals(((RescZoneVO)vo).getExchId())) {
				((RescZoneVO)vo).setExchId(null);
			}
			stmt.setString( index++, ((RescZoneVO)vo).getExchId() );
			if ("".equals(((RescZoneVO)vo).getRegionId())) {
				((RescZoneVO)vo).setRegionId(null);
			}
			stmt.setString( index++, ((RescZoneVO)vo).getRegionId() );
			stmt.setString( index++, ((RescZoneVO)vo).getResouceZoneName() );
			if ("".equals(((RescZoneVO)vo).getRescId())) {
				((RescZoneVO)vo).setRescId(null);
			}
			stmt.setString( index++, ((RescZoneVO)vo).getRescId() );
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

	public long delete( String presource_zone_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, presource_zone_id );
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
		return new RescZoneVO();
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
