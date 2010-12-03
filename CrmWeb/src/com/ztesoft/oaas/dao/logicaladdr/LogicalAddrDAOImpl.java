package com.ztesoft.oaas.dao.logicaladdr;

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
import com.ztesoft.oaas.vo.LogicalAddrVO;

public class LogicalAddrDAOImpl   implements LogicalAddrDAO {

	private String SQL_SELECT = "SELECT logical_address_id,address_id,logical_address_type,logical_address_detail FROM LOGICAL_ADDRESS";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM LOGICAL_ADDRESS";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO LOGICAL_ADDRESS ( logical_address_id,address_id,logical_address_type,logical_address_detail ) VALUES ( ?,?,?,? )";

	private String SQL_UPDATE = "UPDATE LOGICAL_ADDRESS SET  address_id = ?, logical_address_type = ?, logical_address_detail = ? WHERE logical_address_id = ? ";

	private String SQL_DELETE = "DELETE FROM LOGICAL_ADDRESS WHERE logical_address_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM LOGICAL_ADDRESS ";

	public LogicalAddrDAOImpl() {

	}

	public LogicalAddrVO findByPrimaryKey(String plogical_address_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE logical_address_id = ? ", new String[] { plogical_address_id } );
		if (arrayList.size()>0)
			return (LogicalAddrVO)arrayList.get(0);
		else
			return (LogicalAddrVO) getEmptyVO();
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
			LogicalAddrVO vo = new LogicalAddrVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(LogicalAddrVO vo, ResultSet rs) throws SQLException {
		vo.setLogicalAddrId( rs.getString( "logical_address_id" ) );
		vo.setAddrId( rs.getString( "address_id" ) );
		vo.setLogicalAddrType( rs.getString( "logical_address_type" ) );
		vo.setLogicalAddrDeta( rs.getString( "logical_address_detail" ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		LogicalAddrVO vo = new LogicalAddrVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord出错",this);
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
			
			SequenceManageDAO seqDAO = SeqDAOFactory.getInstance().getSequenceManageDAO() ;
			((LogicalAddrVO)vo).setLogicalAddrId(seqDAO.getNextSequence(vo.getTableName(), "LOGICAL_ADDRESS_ID"));
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
            
			int index = 1;
			if ("".equals(((LogicalAddrVO)vo).getLogicalAddrId())) {
				((LogicalAddrVO)vo).setLogicalAddrId(null);
			}
			stmt.setString( index++, ((LogicalAddrVO)vo).getLogicalAddrId() );
			if ("".equals(((LogicalAddrVO)vo).getAddrId())) {
				((LogicalAddrVO)vo).setAddrId(null);
			}
			stmt.setString( index++, ((LogicalAddrVO)vo).getAddrId() );
			stmt.setString( index++, ((LogicalAddrVO)vo).getLogicalAddrType() );
			stmt.setString( index++, ((LogicalAddrVO)vo).getLogicalAddrDeta() );
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

	public boolean update( String plogical_address_id,LogicalAddrVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE LOGICAL_ADDRESS SET logical_address_id = ?,address_id = ?,logical_address_type = ?,logical_address_detail = ?" );
			sql.append( " WHERE  logical_address_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((LogicalAddrVO)vo).getLogicalAddrId())) {
				((LogicalAddrVO)vo).setLogicalAddrId(null);
			}
			stmt.setString( index++, vo.getLogicalAddrId() );
			if ("".equals(((LogicalAddrVO)vo).getAddrId())) {
				((LogicalAddrVO)vo).setAddrId(null);
			}
			stmt.setString( index++, vo.getAddrId() );
			stmt.setString( index++, vo.getLogicalAddrType() );
			stmt.setString( index++, vo.getLogicalAddrDeta() );
			stmt.setString( index++, plogical_address_id );
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
			sql.append( "UPDATE LOGICAL_ADDRESS SET logical_address_id = ?,address_id = ?,logical_address_type = ?,logical_address_detail = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((LogicalAddrVO)vo).getLogicalAddrId())) {
				((LogicalAddrVO)vo).setLogicalAddrId(null);
			}
			stmt.setString( index++, ((LogicalAddrVO)vo).getLogicalAddrId() );
			if ("".equals(((LogicalAddrVO)vo).getAddrId())) {
				((LogicalAddrVO)vo).setAddrId(null);
			}
			stmt.setString( index++, ((LogicalAddrVO)vo).getAddrId() );
			stmt.setString( index++, ((LogicalAddrVO)vo).getLogicalAddrType() );
			stmt.setString( index++, ((LogicalAddrVO)vo).getLogicalAddrDeta() );
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

	public long delete( String plogical_address_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, plogical_address_id );
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
		return new LogicalAddrVO();
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

    /**
     * 获取指定地址标识对应的所有逻辑地址
     * @param paddr_id
     * @return 指定地址标识对应的所有逻辑地址（LogicalAddrVO组成的ArrayList）
     * @throws DAOSystemException
     */
    public ArrayList getLogicalAddrsByAddr(String paddr_id) throws DAOSystemException
    {
        return findBySql( SQL_SELECT + " WHERE address_id = ? ", new String[] { paddr_id } );
    }
    
    public long deleteByAddr(String paddr_id) throws DAOSystemException
    {
        return deleteByCond(" address_id = " + paddr_id);
    }

}
