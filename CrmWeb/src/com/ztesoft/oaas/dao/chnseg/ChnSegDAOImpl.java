package com.ztesoft.oaas.dao.chnseg;

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
import com.ztesoft.oaas.vo.ChnSegVO;

public class ChnSegDAOImpl   implements ChnSegDAO {

	private String SQL_SELECT = "SELECT channel_segment_id,cooperant_part_id,channel_segment_type,channel_segment_name,channel_segment_detail FROM CHANNEL_SEGMENT";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM CHANNEL_SEGMENT";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO CHANNEL_SEGMENT ( channel_segment_id,cooperant_part_id,channel_segment_type,channel_segment_name,channel_segment_detail ) VALUES ( ?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE CHANNEL_SEGMENT SET  cooperant_part_id = ?, channel_segment_type = ?, channel_segment_name = ?, channel_segment_detail = ? WHERE channel_segment_id = ? ";

	private String SQL_DELETE = "DELETE FROM CHANNEL_SEGMENT WHERE channel_segment_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM CHANNEL_SEGMENT ";

	public ChnSegDAOImpl() {

	}

	public ChnSegVO findByPrimaryKey(String pchannel_segment_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE channel_segment_id = ? ", new String[] { pchannel_segment_id } );
		if (arrayList.size()>0)
			return (ChnSegVO)arrayList.get(0);
		else
			return (ChnSegVO) getEmptyVO();
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
			ChnSegVO vo = new ChnSegVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(ChnSegVO vo, ResultSet rs) throws SQLException {
		vo.setChnSegId( rs.getString( "channel_segment_id" ) );
		vo.setCooperantPartId( rs.getString( "cooperant_part_id" ) );
		vo.setChnSegType( rs.getString( "channel_segment_type" ) );
		vo.setChnSegName( rs.getString( "channel_segment_name" ) );
		vo.setChnSegDeta( rs.getString( "channel_segment_detail" ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		ChnSegVO vo = new ChnSegVO();
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
			 ((ChnSegVO)vo).setChnSegId(smDAO.getNextSequence(vo.getTableName(), "CHANNEL_SEGMENT_ID"));
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
           
			int index = 1;
			if ("".equals(((ChnSegVO)vo).getChnSegId())) {
				((ChnSegVO)vo).setChnSegId(null);
			}
			stmt.setString( index++, ((ChnSegVO)vo).getChnSegId() );
			if ("".equals(((ChnSegVO)vo).getCooperantPartId())) {
				((ChnSegVO)vo).setCooperantPartId(null);
			}
			stmt.setString( index++, ((ChnSegVO)vo).getCooperantPartId() );
			stmt.setString( index++, ((ChnSegVO)vo).getChnSegType() );
			stmt.setString( index++, ((ChnSegVO)vo).getChnSegName() );
			stmt.setString( index++, ((ChnSegVO)vo).getChnSegDeta() );
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

	public boolean update( String pchannel_segment_id,ChnSegVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE CHANNEL_SEGMENT SET channel_segment_id = ?,cooperant_part_id = ?,channel_segment_type = ?,channel_segment_name = ?,channel_segment_detail = ?" );
			sql.append( " WHERE  channel_segment_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((ChnSegVO)vo).getChnSegId())) {
				((ChnSegVO)vo).setChnSegId(null);
			}
			stmt.setString( index++, vo.getChnSegId() );
			if ("".equals(((ChnSegVO)vo).getCooperantPartId())) {
				((ChnSegVO)vo).setCooperantPartId(null);
			}
			stmt.setString( index++, vo.getCooperantPartId() );
			stmt.setString( index++, vo.getChnSegType() );
			stmt.setString( index++, vo.getChnSegName() );
			stmt.setString( index++, vo.getChnSegDeta() );
			stmt.setString( index++, pchannel_segment_id );
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
			sql.append( "UPDATE CHANNEL_SEGMENT SET channel_segment_id = ?,cooperant_part_id = ?,channel_segment_type = ?,channel_segment_name = ?,channel_segment_detail = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((ChnSegVO)vo).getChnSegId())) {
				((ChnSegVO)vo).setChnSegId(null);
			}
			stmt.setString( index++, ((ChnSegVO)vo).getChnSegId() );
			if ("".equals(((ChnSegVO)vo).getCooperantPartId())) {
				((ChnSegVO)vo).setCooperantPartId(null);
			}
			stmt.setString( index++, ((ChnSegVO)vo).getCooperantPartId() );
			stmt.setString( index++, ((ChnSegVO)vo).getChnSegType() );
			stmt.setString( index++, ((ChnSegVO)vo).getChnSegName() );
			stmt.setString( index++, ((ChnSegVO)vo).getChnSegDeta() );
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

	public long delete( String pchannel_segment_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pchannel_segment_id );
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
		return new ChnSegVO();
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

    public ArrayList getChnSegsByPartyRole(String pparty_role_id) throws DAOSystemException
    {
        return findBySql( SQL_SELECT + " WHERE cooperant_part_id = ? ", new String[] { pparty_role_id } );
    }
    
    public long deleteByPartyRole(String pparty_role_id) throws DAOSystemException
    {
        return deleteByCond(" cooperant_part_id = " + pparty_role_id);
    }
}
