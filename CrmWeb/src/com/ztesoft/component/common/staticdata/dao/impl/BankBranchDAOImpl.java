package com.ztesoft.component.common.staticdata.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.staticdata.dao.BankBranchDAO;
import com.ztesoft.component.common.staticdata.vo.BankBranchVO;
public class BankBranchDAOImpl   implements BankBranchDAO {

	private String SQL_SELECT = "SELECT bank_branch_id,bank_id,bank_branch_name,bank_acct,bank_acct_name,branch_code FROM BANK_BRANCH";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM BANK_BRANCH";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO BANK_BRANCH ( bank_branch_id,bank_id,bank_branch_name,bank_acct,bank_acct_name,branch_code ) VALUES ( ?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE BANK_BRANCH SET  bank_id = ?, bank_branch_name = ?, bank_acct = ?, bank_acct_name = ?, branch_code = ? WHERE bank_branch_id = ? ";

	private String SQL_DELETE = "DELETE FROM BANK_BRANCH WHERE bank_branch_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM BANK_BRANCH ";

	public BankBranchDAOImpl() {

	}

	public BankBranchVO findByPrimaryKey(String pbank_branch_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE bank_branch_id = ? ", new String[] { pbank_branch_id } );
		if (arrayList.size()>0)
			return (BankBranchVO)arrayList.get(0);
		else
			return (BankBranchVO) getEmptyVO();
	}

	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql,DAOSQLUtils.BSN_DB) );
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
			DAOUtils.closeConnection(conn, this);
		}
	}

	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			BankBranchVO vo = new BankBranchVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(BankBranchVO vo, ResultSet rs) throws SQLException {
		vo.setBankBranchId( DAOUtils.trimStr( rs.getString( "bank_branch_id" ) ) );
		vo.setBankId( DAOUtils.trimStr( rs.getString( "bank_id" ) ) );
		vo.setBankBranchName( DAOUtils.trimStr( rs.getString( "bank_branch_name" ) ) );
		vo.setBankAcct( DAOUtils.trimStr( rs.getString( "bank_acct" ) ) );
		vo.setBankAcctName( DAOUtils.trimStr( rs.getString( "bank_acct_name" ) ) );
		vo.setBranchCode( DAOUtils.trimStr( rs.getString( "branch_code" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		BankBranchVO vo = new BankBranchVO();
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

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			SQL = SQL_SELECT + " WHERE " + whereCond;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.BSN_DB) );
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
			DAOUtils.closeConnection(conn, this);
		}
	}

	public void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			if ("".equals(((BankBranchVO)vo).getBankBranchId())) {
				((BankBranchVO)vo).setBankBranchId(null);
			}
			stmt.setString( index++, ((BankBranchVO)vo).getBankBranchId() );
			if ("".equals(((BankBranchVO)vo).getBankId())) {
				((BankBranchVO)vo).setBankId(null);
			}
			stmt.setString( index++, ((BankBranchVO)vo).getBankId() );
			stmt.setString( index++, ((BankBranchVO)vo).getBankBranchName() );
			stmt.setString( index++, ((BankBranchVO)vo).getBankAcct() );
			stmt.setString( index++, ((BankBranchVO)vo).getBankAcctName() );
			stmt.setString( index++, ((BankBranchVO)vo).getBranchCode() );
			int rows = stmt.executeUpdate();
		}
		catch (SQLException se) {
			Debug.print(SQL_INSERT,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}

	public boolean update( String pbank_branch_id,BankBranchVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			sql.append( "UPDATE BANK_BRANCH SET bank_branch_id = ?,bank_id = ?,bank_branch_name = ?,bank_acct = ?,bank_acct_name = ?,branch_code = ?" );
			sql.append( " WHERE  bank_branch_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((BankBranchVO)vo).getBankBranchId())) {
				((BankBranchVO)vo).setBankBranchId(null);
			}
			stmt.setString( index++, vo.getBankBranchId() );
			if ("".equals(((BankBranchVO)vo).getBankId())) {
				((BankBranchVO)vo).setBankId(null);
			}
			stmt.setString( index++, vo.getBankId() );
			stmt.setString( index++, vo.getBankBranchName() );
			stmt.setString( index++, vo.getBankAcct() );
			stmt.setString( index++, vo.getBankAcctName() );
			stmt.setString( index++, vo.getBranchCode() );
			stmt.setString( index++, pbank_branch_id );
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
			DAOUtils.closeConnection(conn, this);
		}
		return bResult;
	}

	public boolean update(String whereCond,VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			sql.append( "UPDATE BANK_BRANCH SET bank_branch_id = ?,bank_id = ?,bank_branch_name = ?,bank_acct = ?,bank_acct_name = ?,branch_code = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((BankBranchVO)vo).getBankBranchId())) {
				((BankBranchVO)vo).setBankBranchId(null);
			}
			stmt.setString( index++, ((BankBranchVO)vo).getBankBranchId() );
			if ("".equals(((BankBranchVO)vo).getBankId())) {
				((BankBranchVO)vo).setBankId(null);
			}
			stmt.setString( index++, ((BankBranchVO)vo).getBankId() );
			stmt.setString( index++, ((BankBranchVO)vo).getBankBranchName() );
			stmt.setString( index++, ((BankBranchVO)vo).getBankAcct() );
			stmt.setString( index++, ((BankBranchVO)vo).getBankAcctName() );
			stmt.setString( index++, ((BankBranchVO)vo).getBranchCode() );
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
			DAOUtils.closeConnection(conn, this);
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
			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			SQL = SQL_SELECT_COUNT + " WHERE " + whereCond;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.BSN_DB) );
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
			DAOUtils.closeConnection(conn, this);
		}
		return lCount;
	}

	public long deleteByCond(String whereCond) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		String SQL = "";
		try {

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			SQL = SQL_DELETE_BY_WHERE + " WHERE " + whereCond;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.BSN_DB) );
			rows = stmt.executeUpdate();

		}
		catch (SQLException se) {
			Debug.print(SQL,this);
			throw new DAOSystemException("SQLException while deleting sql:\n"+SQL, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return rows;
	}

	public long delete( String pbank_branch_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pbank_branch_id );
			rows = stmt.executeUpdate();

		}
		catch (SQLException se) {
			Debug.print(SQL_DELETE,this);
			throw new DAOSystemException("SQLException while deleting sql:\n"+SQL_DELETE, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
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
		return new BankBranchVO();
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

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			SQL = filterSQL;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.BSN_DB) );
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
			DAOUtils.closeConnection(conn, this);
		}
	}

}