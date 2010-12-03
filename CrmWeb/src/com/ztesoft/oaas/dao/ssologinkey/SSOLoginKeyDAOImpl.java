/**
 * 
 */
package com.ztesoft.oaas.dao.ssologinkey;

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
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.oaas.vo.SSOLoginKeyVO;

/**
 * @author Administrator
 *
 */
public class SSOLoginKeyDAOImpl    implements SSOLoginKeyDAO{

	private static final String SQL_INSERT = "INSERT INTO SSO_LOGIN_KEY " + 
		" ( LOGIN_KEY, STAFF_CODE , LOGIN_DATE ) VALUES " +
		" ( ? , ? , ? ) ";
	private static final String SQL_SELECT = "SELECT LOGIN_KEY, STAFF_CODE, " +
		"LOGIN_DATE FROM SSO_LOGIN_KEY ";
	private static final String SQL_UPDATE = "UPDATE SSO_LOGIN_KEY SET " +
		" LOGIN_KEY = ? , STAFF_CODE = ? , LOGIN_DATE = ? " +
		"WHERE LOGIN_KEY = ? ";
	private static final String SQL_DELETE = "DELETE FROM SSO_LOGIN_KEY " + 
		"WHERE LOGIN_KEY = ? ";
	
	private int maxRows;
	
	public void insert( SSOLoginKeyVO vo ) throws DAOSystemException{
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			stmt.setString( index++, ((SSOLoginKeyVO)vo).getLoginKey() );
			stmt.setString( index++, ((SSOLoginKeyVO)vo).getStaffCode() );
			stmt.setDate( index++, DAOUtils.parseDateTime(((SSOLoginKeyVO)vo).getLoginDate()));
			stmt.executeUpdate();
		}
		catch (SQLException se) {
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
	
	public boolean update( String loginKey , SSOLoginKeyVO vo ) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_UPDATE) );
			int index = 1;
			stmt.setString( index++, vo.getLoginKey() );
			stmt.setString( index++, vo.getStaffCode());
			stmt.setDate( index++, DAOUtils.parseDateTime(vo.getLoginDate()));
			stmt.setString( index++, vo.getLoginKey() ) ;
			int rows = stmt.executeUpdate();
            if (rows>0) {
				bResult = true;
			}
		}
		catch (SQLException se) {
			throw new DAOSystemException("SQLException while update sql:\n"+SQL_UPDATE, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return bResult;
	}
	
	public int delete( String loginKey ) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, loginKey );
			rows = stmt.executeUpdate();
		}
		catch (SQLException se) {
			throw new DAOSystemException("SQLException while deleting sql:\n"+SQL_DELETE, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return rows;
	}
	
	public SSOLoginKeyVO getSSOLoginKey( String loginKey ) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE LOGIN_KEY = ? ", new String[] { loginKey } );
		if (arrayList.size()>0)
			return (SSOLoginKeyVO)arrayList.get(0);
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
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
	
	public long deleteByCond(String whereCond) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		String SQL = "";
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			SQL = "DELETE SSO_LOGIN_KEY WHERE " + whereCond;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			rows = stmt.executeUpdate();

		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while deleting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return rows;
	}
	
	protected void populateDto(SSOLoginKeyVO vo, ResultSet rs) throws SQLException {
		vo.setLoginKey( rs.getString( "LOGIN_KEY" ) );
		vo.setStaffCode( rs.getString( "STAFF_CODE" ) );
		vo.setLoginDate( DAOUtils.getFormatedDate(rs.getDate( "LOGIN_DATE" )));
	}
	
	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			SSOLoginKeyVO vo = new SSOLoginKeyVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}
	
	public List findByCond(String whereCond) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = "";
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			SQL = SQL_SELECT + " WHERE " + whereCond;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			stmt.setMaxRows(maxRows);
			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
}
