package com.ztesoft.oaas.dao.mmdataprivrule;

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
import com.ztesoft.oaas.vo.MmDataPrivRuleVO;

public class MmDataPrivRuleDAOImpl   implements MmDataPrivRuleDAO {

	private String SQL_SELECT = "SELECT privilege_type,get_val_sql,if_region_rela,data_table_code,trans_sql,get_val_mode FROM MM_DATA_PRIVILEGE_RULE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM MM_DATA_PRIVILEGE_RULE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO MM_DATA_PRIVILEGE_RULE ( privilege_type,get_val_sql,if_region_rela,data_table_code,trans_sql,get_val_mode ) VALUES ( ?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE MM_DATA_PRIVILEGE_RULE SET  get_val_sql = ?, if_region_rela = ?, data_table_code = ?, trans_sql = ?, get_val_mode = ? WHERE privilege_type = ? ";

	private String SQL_DELETE = "DELETE FROM MM_DATA_PRIVILEGE_RULE WHERE privilege_type = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM MM_DATA_PRIVILEGE_RULE ";

	public MmDataPrivRuleDAOImpl() {

	}

	public MmDataPrivRuleVO findByPrimaryKey(String pprivilege_type) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE privilege_type = ? ", new String[] { pprivilege_type } );
		if (arrayList.size()>0)
			return (MmDataPrivRuleVO)arrayList.get(0);
		else
			return (MmDataPrivRuleVO) getEmptyVO();
	}

	public String findTranSQLByType( String type ) throws DAOSystemException {
		String sql = SQL_SELECT + " where PRIVILEGE_TYPE = '" + type + "'";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			
			rs = stmt.executeQuery();
			if( rs.next()){
				return rs.getString(1);
			}else{
				return "";
			}
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
	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			
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
			MmDataPrivRuleVO vo = new MmDataPrivRuleVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateSimpleDto(MmDataPrivRuleVO vo, ResultSet rs) throws SQLException {
		vo.setPrivType( DAOUtils.trimStr( rs.getString( "privilege_type" ) ) );
		vo.setGetValMode(DAOUtils.trimStr(rs.getString("get_val_mode")));
		vo.setGetValSql( DAOUtils.trimStr( rs.getString( "get_val_sql" ) ) );
		vo.setTransSql( DAOUtils.trimStr(rs.getString("trans_sql")));
	}
	protected void populateDto(MmDataPrivRuleVO vo, ResultSet rs) throws SQLException {
		populateSimpleDto( vo, rs ) ;
		vo.setIfRegionRela( DAOUtils.trimStr( rs.getString( "if_region_rela" ) ) );
		vo.setDataTableCode( DAOUtils.trimStr( rs.getString( "data_table_code" ) ) );
		vo.setTransSql( DAOUtils.trimStr( rs.getString( "trans_sql" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		MmDataPrivRuleVO vo = new MmDataPrivRuleVO();
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
			stmt.setString( index++, ((MmDataPrivRuleVO)vo).getPrivType() );
			stmt.setString( index++, ((MmDataPrivRuleVO)vo).getGetValSql() );
			stmt.setString( index++, ((MmDataPrivRuleVO)vo).getIfRegionRela() );
			stmt.setString( index++, ((MmDataPrivRuleVO)vo).getDataTableCode() );
			stmt.setString( index++, ((MmDataPrivRuleVO)vo).getTransSql() );
			stmt.setString( index++, ((MmDataPrivRuleVO)vo).getGetValMode());
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

	public boolean update( String pprivilege_type,MmDataPrivRuleVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE MM_DATA_PRIVILEGE_RULE SET privilege_type = ?,get_val_sql = ?,if_region_rela = ?,data_table_code = ?,trans_sql = ?, get_val_mode = ?" );
			sql.append( " WHERE  privilege_type = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			stmt.setString( index++, vo.getPrivType() );
			stmt.setString( index++, vo.getGetValSql() );
			stmt.setString( index++, vo.getIfRegionRela() );
			stmt.setString( index++, vo.getDataTableCode() );
			stmt.setString( index++, vo.getTransSql() );
			stmt.setString( index++, pprivilege_type );
			stmt.setString( index++, vo.getGetValMode());
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
			sql.append( "UPDATE MM_DATA_PRIVILEGE_RULE SET privilege_type = ?,get_val_sql = ?,if_region_rela = ?,data_table_code = ?,trans_sql = ?,get_val_mode = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			stmt.setString( index++, ((MmDataPrivRuleVO)vo).getPrivType() );
			stmt.setString( index++, ((MmDataPrivRuleVO)vo).getGetValSql() );
			stmt.setString( index++, ((MmDataPrivRuleVO)vo).getIfRegionRela() );
			stmt.setString( index++, ((MmDataPrivRuleVO)vo).getDataTableCode() );
			stmt.setString( index++, ((MmDataPrivRuleVO)vo).getTransSql() );
			stmt.setString( index++, ((MmDataPrivRuleVO)vo).getGetValMode());
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

	public long delete( String pprivilege_type) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pprivilege_type );
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
		return new MmDataPrivRuleVO();
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

	public ArrayList getSimpleDataPrivilegeRule() throws DAOSystemException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = "SELECT privilege_type,get_val_sql,get_val_mode,trans_sql FROM MM_DATA_PRIVILEGE_RULE";
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			rs = stmt.executeQuery();

			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				MmDataPrivRuleVO vo = new MmDataPrivRuleVO();
				populateSimpleDto( vo, rs);
				resultList.add( vo );
			}
			return resultList;
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
