package com.ztesoft.oaas.dao.staffpost;

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
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.StaffPostVO;
import com.ztesoft.oaas.vo.StaffVO;

public class StaffPostDAOImpl   implements StaffPostDAO {

	private String SQL_SELECT = "SELECT party_role_id,org_post_id,state,eff_date,exp_date FROM STAFF_POST";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM STAFF_POST";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO STAFF_POST ( party_role_id,org_post_id,state,eff_date,exp_date,state_date ) VALUES ( ?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE STAFF_POST SET  state = ?, eff_date = ?, exp_date = ? WHERE org_post_id = ? AND party_role_id = ? ";

	private String SQL_DELETE = "DELETE FROM STAFF_POST WHERE org_post_id = ? AND party_role_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM STAFF_POST ";

	public StaffPostDAOImpl() {

	}

	public StaffPostVO findByPrimaryKey(String porg_post_id,String pparty_role_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE org_post_id = ? AND party_role_id = ? ", new String[] { porg_post_id,pparty_role_id } );
		if (arrayList.size()>0)
			return (StaffPostVO)arrayList.get(0);
		else
			return (StaffPostVO) getEmptyVO();
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
			StaffPostVO vo = new StaffPostVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(StaffPostVO vo, ResultSet rs) throws SQLException {
		vo.setPartyRoleId( rs.getString( "party_role_id" ) );
		vo.setOrgPostId( rs.getString( "org_post_id" ) );
		vo.setState( rs.getString( "state" ) );
		vo.setEffDate( DAOUtils.getFormatedDate(rs.getDate( "eff_date" )) );
		vo.setExpDate( DAOUtils.getFormatedDate(rs.getDate( "exp_date" ) ));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		StaffPostVO vo = new StaffPostVO();
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

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			if ("".equals(((StaffPostVO)vo).getPartyRoleId())) {
				((StaffPostVO)vo).setPartyRoleId(null);
			}
			stmt.setString( index++, ((StaffPostVO)vo).getPartyRoleId() );
			if ("".equals(((StaffPostVO)vo).getOrgPostId())) {
				((StaffPostVO)vo).setOrgPostId(null);
			}
			stmt.setString( index++, ((StaffPostVO)vo).getOrgPostId() );
			stmt.setString( index++, ((StaffPostVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffPostVO)vo).getEffDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffPostVO)vo).getExpDate()) );
			stmt.setTimestamp(index++, DAOUtils.parseTimestamp(DAOUtils.getFormatedDate()));
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

	public boolean update( String porg_post_id, String pparty_role_id,StaffPostVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE STAFF_POST SET party_role_id = ?,org_post_id = ?,state = ?,eff_date = ?,exp_date = ?,state_date=? " );
			sql.append( " WHERE  org_post_id = ? AND party_role_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((StaffPostVO)vo).getPartyRoleId())) {
				((StaffPostVO)vo).setPartyRoleId(null);
			}
			stmt.setString( index++, vo.getPartyRoleId() );
			if ("".equals(((StaffPostVO)vo).getOrgPostId())) {
				((StaffPostVO)vo).setOrgPostId(null);
			}
			stmt.setString( index++, vo.getOrgPostId() );
			stmt.setString( index++, vo.getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getEffDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getExpDate()) );
			stmt.setTimestamp(index++, DAOUtils.parseTimestamp(DAOUtils.getFormatedDate()));
			stmt.setString( index++, porg_post_id );
			stmt.setString( index++, pparty_role_id );
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
			sql.append( "UPDATE STAFF_POST SET party_role_id = ?,org_post_id = ?,state = ?,eff_date = ?,exp_date = ?,state_date=? " );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((StaffPostVO)vo).getPartyRoleId())) {
				((StaffPostVO)vo).setPartyRoleId(null);
			}
			stmt.setString( index++, ((StaffPostVO)vo).getPartyRoleId() );
			if ("".equals(((StaffPostVO)vo).getOrgPostId())) {
				((StaffPostVO)vo).setOrgPostId(null);
			}
			stmt.setString( index++, ((StaffPostVO)vo).getOrgPostId() );
			stmt.setString( index++, ((StaffPostVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffPostVO)vo).getEffDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffPostVO)vo).getExpDate()) );
			stmt.setTimestamp(index++, DAOUtils.parseTimestamp(DAOUtils.getFormatedDate()));
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

	public long delete( String porg_post_id, String pparty_role_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, porg_post_id );
			stmt.setString( index++, pparty_role_id );
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
		return new StaffPostVO();
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
     * 根据员工标识获取其所有岗位
     * @param pstaff_id 员工参与人角色标识
     * @return 指定员工的所有员工岗位列表（StaffPostVO构成的ArrayList）
     * @throws DAOSystemException
     */
    public ArrayList getStaffPostsByStaff(String pstaff_id) throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT sp.party_role_id, sp.org_post_id, sp.state, sp.eff_date, sp.exp_date, p.position_name FROM STAFF_POST sp, ORGANIZATION_POST op, POSITION p WHERE sp.org_post_id=op.org_post_id AND op.position_id=p.position_id AND sp.party_role_id = ? ";
        ArrayList alStaffPosts = new ArrayList();
        try {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
            //stmt = conn.prepareStatement( sql );
            stmt.setMaxRows( maxRows );
            stmt.setString( 1, pstaff_id );
            rs = stmt.executeQuery();
            StaffPostVO vo;
            while(rs.next())
            {
                vo = new StaffPostVO();
                populateDto( vo, rs);
                vo.setOrgPostName(rs.getString("position_name"));
                vo.setPositionName(rs.getString("position_name"));
                alStaffPosts.add( vo );
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
        return alStaffPosts;
    }
    
    public long deleteByStaff(String pstaff_id) throws DAOSystemException
    {
        return deleteByCond(" party_role_id = " + pstaff_id);
    }

    public PageModel getStaffListByPartyAndPost(String partyId, String positionId, int pageIndex, int pageSize) throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		
        try {
		conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
		String baseSqlStr = "SELECT c.org_name,d.staff_code, d.staff_desc "
				+ "FROM organization_post a ,staff_post b,organization c,staff d "
				+ "WHERE a.org_post_id = b.org_post_id AND a.party_id = c.party_id "
				+ "AND b.party_role_id = d.party_role_id AND a.party_id = ? AND a.position_id = ?";
		baseSqlStr = DAOSQLUtils.getFilterSQL( baseSqlStr ) ;
		String cntSqlStr = "SELECT count(*) FROM ( " + baseSqlStr + " )";

		stmt = conn.prepareStatement(cntSqlStr);
		stmt.setString(1, partyId);
		stmt.setString(2, positionId);

		Debug.print("baseSqlStr="+baseSqlStr,this);
		Debug.print("partyId="+partyId,this);
		Debug.print("positionId="+positionId,this);

		rs = stmt.executeQuery();
		int totalCount = 0;
		if(rs.next()){
			totalCount = Integer.parseInt(rs.getString(1));
		}
		
		DAOUtils.closeResultSet(rs, this);
		DAOUtils.closeStatement(stmt, this);

		if (totalCount == 0) {
			return new PageModel();
		}
		
		PageModel pageModel = new PageModel();
		pageModel.setTotalCount(totalCount);
		if (totalCount % pageSize > 0) {
			pageModel.setPageCount(totalCount / pageSize + 1);
		} else {
			pageModel.setPageCount(totalCount / pageSize);
		}

		if (pageIndex < 0) {
			pageModel.setPageIndex(1);
		} else if (pageIndex > pageModel.getPageCount()) {
			pageModel.setPageIndex(pageModel.getPageCount());
		} else {
			pageModel.setPageIndex(pageIndex);
		}

		if (pageSize < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}

		String sqlStr =  "SELECT * FROM (SELECT rownum mynum, table_a.* FROM ("
		            + baseSqlStr + ") table_a WHERE rownum <= ? ) table_b WHERE table_b.mynum>?"; 
		stmt = conn.prepareStatement(sqlStr);
	
		stmt.setString(1, partyId);
		stmt.setString(2, positionId);
		stmt.setInt(3, pageModel.getPageSize()*pageModel.getPageIndex());
		stmt.setInt(4, pageModel.getPageSize()*(pageModel.getPageIndex() - 1));
		rs = stmt.executeQuery();

		ArrayList arraylist=new ArrayList();
	       while(rs.next()){
			StaffVO staffVO = new StaffVO();
			staffVO.setOrgPartyName(rs.getString(2));
			staffVO.setStaffCode(rs.getString(3));
			staffVO.setStaffDesc(rs.getString(4));
			arraylist.add(staffVO);
		}
 
		pageModel.setList(arraylist);
		
		return pageModel;

	} catch (SQLException se) {
		throw new DAOSystemException("SQLException while execute StaffPostDAOImpl.getStaffListByPartyAndPost():\n", se);
	} finally {
		DAOUtils.closeResultSet(rs, this);
		DAOUtils.closeStatement(stmt, this);
		//DAOUtils.closeConnection(conn, this);
	}
    }

}
