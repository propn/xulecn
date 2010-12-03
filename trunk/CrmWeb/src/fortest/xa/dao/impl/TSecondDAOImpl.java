package fortest.xa.dao.impl;

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
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import fortest.xa.dao.TSecondDAO;
import fortest.xa.vo.TSecondVO;

public class TSecondDAOImpl implements TSecondDAO {

	private String SQL_SELECT = "SELECT ID,S_NAME,S_DESC,F_ID FROM X_SECOND";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM X_SECOND";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO X_SECOND ( ID,S_NAME,S_DESC,F_ID ) VALUES ( ?,?,?,? )";

	private String SQL_UPDATE = "UPDATE X_SECOND SET  ID = ?, S_NAME = ?, S_DESC = ?, F_ID = ? WHERE";

	private String SQL_DELETE = "DELETE FROM X_SECOND WHERE";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM X_SECOND ";

	public TSecondDAOImpl() {

	}

	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = ConnectionContext.getContext().getConnection("crm2");
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
			TSecondVO vo = new TSecondVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(TSecondVO vo, ResultSet rs) throws SQLException {
		vo.setSid( DAOUtils.trimStr( rs.getString( "ID" ) ) );
		vo.setSName( DAOUtils.trimStr( rs.getString( "S_NAME" ) ) );
		vo.setSDesc( DAOUtils.trimStr( rs.getString( "S_DESC" ) ) );
		vo.setFId( DAOUtils.trimStr( rs.getString( "F_ID" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		TSecondVO vo = new TSecondVO();
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

			conn = ConnectionContext.getContext().getConnection("crm2");
			SQL = SQL_SELECT + " WHERE " + whereCond;
			stmt = conn.prepareStatement( SQL );
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

			conn = ConnectionContext.getContext().getConnection("crm2");
			stmt = conn.prepareStatement( SQL_INSERT );
			int index = 1;
			if ("".equals(((TSecondVO)vo).getSid())) {
				((TSecondVO)vo).setSid(null);
			}
			stmt.setString( index++, ((TSecondVO)vo).getSid() );
			stmt.setString( index++, ((TSecondVO)vo).getSName() );
			stmt.setString( index++, ((TSecondVO)vo).getSDesc() );
			if ("".equals(((TSecondVO)vo).getFId())) {
				((TSecondVO)vo).setFId(null);
			}
			stmt.setString( index++, ((TSecondVO)vo).getFId() );
			int rows = stmt.executeUpdate();
		}
		catch (SQLException se) {
			Debug.print(SQL_INSERT,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+DAOSQLUtils.getFilterSQL(SQL_INSERT), se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	public boolean update(String whereCond,VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection("crm2");
			sql.append( "UPDATE X_SECOND SET ID = ?,S_NAME = ?,S_DESC = ?,F_ID = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( sql.toString() );
			int index = 1;
			if ("".equals(((TSecondVO)vo).getSid())) {
				((TSecondVO)vo).setSid(null);
			}
			stmt.setString( index++, ((TSecondVO)vo).getSid() );
			stmt.setString( index++, ((TSecondVO)vo).getSName() );
			stmt.setString( index++, ((TSecondVO)vo).getSDesc() );
			if ("".equals(((TSecondVO)vo).getFId())) {
				((TSecondVO)vo).setFId(null);
			}
			stmt.setString( index++, ((TSecondVO)vo).getFId() );
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
			conn = ConnectionContext.getContext().getConnection("crm2");
			SQL = SQL_SELECT_COUNT + " WHERE " + whereCond;
			stmt = conn.prepareStatement( SQL );
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

			conn = ConnectionContext.getContext().getConnection("crm2");
			SQL = SQL_DELETE_BY_WHERE + " WHERE " + whereCond;
			stmt = conn.prepareStatement( SQL );
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

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public VO getEmptyVO() {
		return new TSecondVO();
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

			conn = ConnectionContext.getContext().getConnection("crm2");
			SQL = filterSQL;
			stmt = conn.prepareStatement( SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
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
