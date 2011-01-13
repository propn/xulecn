package com.ztesoft.oaas.dao.exch;

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
import com.ztesoft.oaas.vo.ExchVO;

public class ExchDAOImpl   implements ExchDAO {

	private String SQL_SELECT = "SELECT exchange_id,region_id,exchange_code,exchange_name,acc_nbr_begin,acc_nbr_end,state,eff_date,exp_date,area_id,product_family_id FROM EXCHANGE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM EXCHANGE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO EXCHANGE ( exchange_id,region_id,exchange_code,exchange_name,acc_nbr_begin,acc_nbr_end,state,eff_date,exp_date,area_id,product_family_id ) VALUES ( ?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE EXCHANGE SET  region_id = ?, exchange_code = ?, exchange_name = ?, acc_nbr_begin = ?, acc_nbr_end = ?, state = ?, eff_date = ?, exp_date = ?, area_id = ?, product_family_id = ? WHERE exchange_id = ? ";

	private String SQL_DELETE = "DELETE FROM EXCHANGE WHERE exchange_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM EXCHANGE ";

	public ExchDAOImpl() {

	}

	public ExchVO findByPrimaryKey(String pexchange_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE exchange_id = ? ", new String[] { pexchange_id } );
		if (arrayList.size()>0)
			return (ExchVO)arrayList.get(0);
		else
			return (ExchVO) getEmptyVO();
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
			ExchVO vo = new ExchVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(ExchVO vo, ResultSet rs) throws SQLException {
		vo.setExchId( rs.getString( "exchange_id" ) );
		vo.setRegionId( rs.getString( "region_id" ) );
		vo.setExchCode( rs.getString( "exchange_code" ) );
		vo.setExchName( rs.getString( "exchange_name" ) );
		vo.setAccNbrBegin( rs.getString( "acc_nbr_begin" ) );
		vo.setAccNbrEnd( rs.getString( "acc_nbr_end" ) );
		vo.setState( rs.getString( "state" ) );
		vo.setEffDate( DAOUtils.getFormatedDate(rs.getDate( "eff_date" ) ));
		vo.setExpDate( DAOUtils.getFormatedDate(rs.getDate( "exp_date" ) ));
		vo.setAreaId( rs.getString( "area_id" ) );
		vo.setProdFamilyId( rs.getString( "product_family_id" ) );
		//vo.setProdFamilyName( rs.getString("product_family_name"));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		ExchVO vo = new ExchVO();
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
			        	
			((ExchVO)vo).setExchId(smDAO.getNextSequence(vo.getTableName(), "EXCHANGE_ID"));
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
            
			int index = 1;
			if ("".equals(((ExchVO)vo).getExchId())) {
				((ExchVO)vo).setExchId(null);
			}
			stmt.setString( index++, ((ExchVO)vo).getExchId() );
			if ("".equals(((ExchVO)vo).getRegionId())) {
				((ExchVO)vo).setRegionId(null);
			}
			stmt.setString( index++, ((ExchVO)vo).getRegionId() );
			stmt.setString( index++, ((ExchVO)vo).getExchCode() );
			stmt.setString( index++, ((ExchVO)vo).getExchName() );
			stmt.setString( index++, ((ExchVO)vo).getAccNbrBegin() );
			stmt.setString( index++, ((ExchVO)vo).getAccNbrEnd() );
			stmt.setString( index++, ((ExchVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((ExchVO)vo).getEffDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((ExchVO)vo).getExpDate()) );
			if ("".equals(((ExchVO)vo).getAreaId())) {
				((ExchVO)vo).setAreaId(null);
			}
			stmt.setString( index++, ((ExchVO)vo).getAreaId() );
			if ("".equals(((ExchVO)vo).getProdFamilyId())) {
				((ExchVO)vo).setProdFamilyId(null);
			}
			stmt.setString( index++, ((ExchVO)vo).getProdFamilyId() );
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

	public boolean update( String pexchange_id,ExchVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE EXCHANGE SET exchange_id = ?,region_id = ?,exchange_code = ?,exchange_name = ?,acc_nbr_begin = ?,acc_nbr_end = ?,state = ?,eff_date = ?,exp_date = ?,area_id = ?,product_family_id = ?" );
			sql.append( " WHERE  exchange_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((ExchVO)vo).getExchId())) {
				((ExchVO)vo).setExchId(null);
			}
			stmt.setString( index++, vo.getExchId() );
			if ("".equals(((ExchVO)vo).getRegionId())) {
				((ExchVO)vo).setRegionId(null);
			}
			stmt.setString( index++, vo.getRegionId() );
			stmt.setString( index++, vo.getExchCode() );
			stmt.setString( index++, vo.getExchName() );
			stmt.setString( index++, vo.getAccNbrBegin() );
			stmt.setString( index++, vo.getAccNbrEnd() );
			stmt.setString( index++, vo.getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getEffDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getExpDate()) );
			if ("".equals(((ExchVO)vo).getAreaId())) {
				((ExchVO)vo).setAreaId(null);
			}
			stmt.setString( index++, vo.getAreaId() );
			if ("".equals(((ExchVO)vo).getProdFamilyId())) {
				((ExchVO)vo).setProdFamilyId(null);
			}
			stmt.setString( index++, vo.getProdFamilyId() );
			stmt.setString( index++, pexchange_id );
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
			sql.append( "UPDATE EXCHANGE SET exchange_id = ?,region_id = ?,exchange_code = ?,exchange_name = ?,acc_nbr_begin = ?,acc_nbr_end = ?,state = ?,eff_date = ?,exp_date = ?,area_id = ?,product_family_id = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((ExchVO)vo).getExchId())) {
				((ExchVO)vo).setExchId(null);
			}
			stmt.setString( index++, ((ExchVO)vo).getExchId() );
			if ("".equals(((ExchVO)vo).getRegionId())) {
				((ExchVO)vo).setRegionId(null);
			}
			stmt.setString( index++, ((ExchVO)vo).getRegionId() );
			stmt.setString( index++, ((ExchVO)vo).getExchCode() );
			stmt.setString( index++, ((ExchVO)vo).getExchName() );
			stmt.setString( index++, ((ExchVO)vo).getAccNbrBegin() );
			stmt.setString( index++, ((ExchVO)vo).getAccNbrEnd() );
			stmt.setString( index++, ((ExchVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((ExchVO)vo).getEffDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((ExchVO)vo).getExpDate()) );
			if ("".equals(((ExchVO)vo).getAreaId())) {
				((ExchVO)vo).setAreaId(null);
			}
			stmt.setString( index++, ((ExchVO)vo).getAreaId() );
			if ("".equals(((ExchVO)vo).getProdFamilyId())) {
				((ExchVO)vo).setProdFamilyId(null);
			}
			stmt.setString( index++, ((ExchVO)vo).getProdFamilyId() );
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

	public long delete( String pexchange_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pexchange_id );
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
		return new ExchVO();
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

    public ArrayList getAccNbrsByExchRegion(String exchregion_id)
    {
    	String sql = "SELECT ex.exchange_id,ex.region_id,ex.exchange_code,ex.exchange_name,ex.acc_nbr_begin," +
    		"ex.acc_nbr_end,ex.state,ex.eff_date,ex.exp_date,ex.area_id,ex.product_family_id,pf.product_family_id, "+
    		"pf.product_family_name from product_family pf ,EXCHANGE ex " + 
    		"where pf.product_family_id = ex.product_family_id and ex.region_id = ?";
    	//return findBySql( sql , new String[]{exchregion_id});
    	Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
    	try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			stmt.setString(1,exchregion_id);
			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				ExchVO vo = new ExchVO();
				populateDto( vo, rs);
				vo.setProdFamilyName( rs.getString("product_family_name"));
				resultList.add( vo );
			}
			return resultList;
		}
		catch (SQLException se) {
			Debug.print(sql,this);
			se.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
    }
    
    public long deleteByExchRegion(String region_id)
    {
        return deleteByCond(" region_id=" + region_id);
    }
}
