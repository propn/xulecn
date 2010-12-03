package com.ztesoft.oaas.dao.officemachine;

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
import com.ztesoft.oaas.vo.OfficeMachineVO;

public class OfficeMachineDAOImpl   implements OfficeMachineDAO {

	private String SQL_SELECT = "SELECT machine_id,office_id,mac_address,ip_address FROM OFFICE_MACHINE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM OFFICE_MACHINE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO OFFICE_MACHINE ( machine_id,office_id,mac_address,ip_address ) VALUES ( ?,?,?,? )";

	private String SQL_UPDATE = "UPDATE OFFICE_MACHINE SET  office_id = ?, mac_address = ?, ip_address = ? WHERE machine_id = ? ";

	private String SQL_DELETE = "DELETE FROM OFFICE_MACHINE WHERE machine_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM OFFICE_MACHINE ";

	public OfficeMachineDAOImpl() {

	}

	public OfficeMachineVO findByPrimaryKey(String pmachine_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE machine_id = ? ", new String[] { pmachine_id } );
		if (arrayList.size()>0)
			return (OfficeMachineVO)arrayList.get(0);
		else
			return (OfficeMachineVO) getEmptyVO();
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
			OfficeMachineVO vo = new OfficeMachineVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(OfficeMachineVO vo, ResultSet rs) throws SQLException {
		vo.setMachineId( rs.getString( "machine_id" ) );
		vo.setOfficeId( rs.getString( "office_id" ) );
		vo.setMacAddr( rs.getString( "mac_address" ).trim() );
		vo.setIpAddr( rs.getString( "ip_address" ).trim() );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		OfficeMachineVO vo = new OfficeMachineVO();
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

			SequenceManageDAO smDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
			        	
            ((OfficeMachineVO)vo).setMachineId(smDAO.getNextSequence(vo.getTableName(), "MACHINE_ID"));
			int index = 1;
			if ("".equals(((OfficeMachineVO)vo).getMachineId())) {
				((OfficeMachineVO)vo).setMachineId(null);
			}
			stmt.setString( index++, ((OfficeMachineVO)vo).getMachineId() );
			if ("".equals(((OfficeMachineVO)vo).getOfficeId())) {
				((OfficeMachineVO)vo).setOfficeId(null);
			}
			stmt.setString( index++, ((OfficeMachineVO)vo).getOfficeId() );
			stmt.setString( index++, ((OfficeMachineVO)vo).getMacAddr() );
			stmt.setString( index++, ((OfficeMachineVO)vo).getIpAddr().trim() );
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

	public boolean update( String pmachine_id,OfficeMachineVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE OFFICE_MACHINE SET machine_id = ?,office_id = ?,mac_address = ?,ip_address = ?" );
			sql.append( " WHERE  machine_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((OfficeMachineVO)vo).getMachineId())) {
				((OfficeMachineVO)vo).setMachineId(null);
			}
			stmt.setString( index++, vo.getMachineId() );
			if ("".equals(((OfficeMachineVO)vo).getOfficeId())) {
				((OfficeMachineVO)vo).setOfficeId(null);
			}
			stmt.setString( index++, vo.getOfficeId() );
			stmt.setString( index++, vo.getMacAddr() );
			stmt.setString( index++, vo.getIpAddr() );
			stmt.setString( index++, pmachine_id );
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
			sql.append( "UPDATE OFFICE_MACHINE SET machine_id = ?,office_id = ?,mac_address = ?,ip_address = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((OfficeMachineVO)vo).getMachineId())) {
				((OfficeMachineVO)vo).setMachineId(null);
			}
			stmt.setString( index++, ((OfficeMachineVO)vo).getMachineId() );
			if ("".equals(((OfficeMachineVO)vo).getOfficeId())) {
				((OfficeMachineVO)vo).setOfficeId(null);
			}
			stmt.setString( index++, ((OfficeMachineVO)vo).getOfficeId() );
			stmt.setString( index++, ((OfficeMachineVO)vo).getMacAddr() );
			stmt.setString( index++, ((OfficeMachineVO)vo).getIpAddr() );
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

	public long delete( String pmachine_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pmachine_id );
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
		return new OfficeMachineVO();
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

    public ArrayList getOfficeMachinesByOffice(String poffice_id) throws DAOSystemException
    {
        return findBySql( SQL_SELECT + " WHERE office_id = ? ", new String[] { poffice_id } );
    }

    public long deleteByOffice(String poffice_id) throws DAOSystemException
    {
        return deleteByCond(" office_id = " + poffice_id);
    }
    
}
