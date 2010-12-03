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
import com.ztesoft.component.common.staticdata.dao.BillFormatDAO;
import com.ztesoft.component.common.staticdata.vo.BillFormatVO;
public class BillFormatDAOImpl   implements BillFormatDAO {

	private String SQL_SELECT = "SELECT bill_format_id,remark_id,format_name,template_file FROM BILL_FORMAT";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM BILL_FORMAT";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO BILL_FORMAT ( bill_format_id,remark_id,format_name,template_file ) VALUES ( ?,?,?,? )";

	private String SQL_UPDATE = "UPDATE BILL_FORMAT SET  remark_id = ?, format_name = ?, template_file = ? WHERE bill_format_id = ? ";

	private String SQL_DELETE = "DELETE FROM BILL_FORMAT WHERE bill_format_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM BILL_FORMAT ";

	public BillFormatDAOImpl() {

	}

	public BillFormatVO findByPrimaryKey(String pbill_format_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE bill_format_id = ? ", new String[] { pbill_format_id } );
		if (arrayList.size()>0)
			return (BillFormatVO)arrayList.get(0);
		else
			return (BillFormatVO) getEmptyVO();
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
			BillFormatVO vo = new BillFormatVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(BillFormatVO vo, ResultSet rs) throws SQLException {
		vo.setBillFormatId( DAOUtils.trimStr( rs.getString( "bill_format_id" ) ) );
		vo.setRemarkId( DAOUtils.trimStr( rs.getString( "remark_id" ) ) );
		vo.setFormatName( DAOUtils.trimStr( rs.getString( "format_name" ) ) );
		vo.setTemplateFile( DAOUtils.trimStr( rs.getString( "template_file" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		BillFormatVO vo = new BillFormatVO();
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
			if ("".equals(((BillFormatVO)vo).getBillFormatId())) {
				((BillFormatVO)vo).setBillFormatId(null);
			}
			stmt.setString( index++, ((BillFormatVO)vo).getBillFormatId() );
			if ("".equals(((BillFormatVO)vo).getRemarkId())) {
				((BillFormatVO)vo).setRemarkId(null);
			}
			stmt.setString( index++, ((BillFormatVO)vo).getRemarkId() );
			stmt.setString( index++, ((BillFormatVO)vo).getFormatName() );
			stmt.setString( index++, ((BillFormatVO)vo).getTemplateFile() );
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

	public boolean update( String pbill_format_id,BillFormatVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			sql.append( "UPDATE BILL_FORMAT SET bill_format_id = ?,remark_id = ?,format_name = ?,template_file = ?" );
			sql.append( " WHERE  bill_format_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((BillFormatVO)vo).getBillFormatId())) {
				((BillFormatVO)vo).setBillFormatId(null);
			}
			stmt.setString( index++, vo.getBillFormatId() );
			if ("".equals(((BillFormatVO)vo).getRemarkId())) {
				((BillFormatVO)vo).setRemarkId(null);
			}
			stmt.setString( index++, vo.getRemarkId() );
			stmt.setString( index++, vo.getFormatName() );
			stmt.setString( index++, vo.getTemplateFile() );
			stmt.setString( index++, pbill_format_id );
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
			sql.append( "UPDATE BILL_FORMAT SET bill_format_id = ?,remark_id = ?,format_name = ?,template_file = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((BillFormatVO)vo).getBillFormatId())) {
				((BillFormatVO)vo).setBillFormatId(null);
			}
			stmt.setString( index++, ((BillFormatVO)vo).getBillFormatId() );
			if ("".equals(((BillFormatVO)vo).getRemarkId())) {
				((BillFormatVO)vo).setRemarkId(null);
			}
			stmt.setString( index++, ((BillFormatVO)vo).getRemarkId() );
			stmt.setString( index++, ((BillFormatVO)vo).getFormatName() );
			stmt.setString( index++, ((BillFormatVO)vo).getTemplateFile() );
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

	public long delete( String pbill_format_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pbill_format_id );
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
		return new BillFormatVO();
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
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL,DAOSQLUtils.BSN_DB)  );
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
