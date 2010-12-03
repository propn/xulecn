package com.ztesoft.oaas.dao.staffrole;

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
import com.ztesoft.oaas.vo.RolesVO;
import com.ztesoft.oaas.vo.StaffRoleVO;

public class StaffRoleDAOImpl   implements StaffRoleDAO {

	private String SQL_SELECT = "SELECT party_role_id,role_id,state,eff_date,exp_date,region_id,region_type FROM STAFF_ROLE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM STAFF_ROLE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO STAFF_ROLE ( party_role_id,role_id,state,eff_date,exp_date,region_id,region_type ) VALUES ( ?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE STAFF_ROLE SET  party_role_id = ?, role_id = ?, state = ?, eff_date = ?, exp_date = ?, region_id = ?, region_type = ? WHERE party_role_id = ? AND role_id = ? AND region_id = ? AND region_type = ? ";

	private String SQL_DELETE = "DELETE FROM STAFF_ROLE WHERE party_role_id = ? AND role_id = ? AND region_id = ? AND region_type = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM STAFF_ROLE ";

	public StaffRoleDAOImpl() {

	}

	public StaffRoleVO findByPrimaryKey(String pstaff_role_id,String role_id, String region_id, String region_type ) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE party_role_id = ? AND role_id = ? AND region_id = ? AND region_type = ? ", new String[] { pstaff_role_id,role_id,region_id,region_type } );
		if (arrayList.size()>0)
			return (StaffRoleVO)arrayList.get(0);
		else
			return (StaffRoleVO) getEmptyVO();
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
			StaffRoleVO vo = new StaffRoleVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(StaffRoleVO vo, ResultSet rs) throws SQLException {
		vo.setPartyRoleId( rs.getString( "party_role_id" ) );
		vo.setRoleId( rs.getString( "role_id" ) );
		vo.setState( rs.getString( "state" ) );
		vo.setEffDate(  DAOUtils.getFormatedDate(rs.getDate( "eff_date" )) );
		vo.setExpDate( DAOUtils.getFormatedDate(rs.getDate( "exp_date" )) );
		vo.setRegionId( rs.getString("region_id")) ;
		vo.setRegionType( rs.getString("region_type"));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		StaffRoleVO vo = new StaffRoleVO();
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
			if ("".equals(((StaffRoleVO)vo).getPartyRoleId())) {
				((StaffRoleVO)vo).setPartyRoleId(null);
			}
			stmt.setString( index++, ((StaffRoleVO)vo).getPartyRoleId() );

			if ("".equals(((StaffRoleVO)vo).getRoleId())) {
				((StaffRoleVO)vo).setRoleId(null);
			}
			stmt.setString( index++, ((StaffRoleVO)vo).getRoleId() );
			stmt.setString( index++, ((StaffRoleVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffRoleVO)vo).getEffDate()) );
			if( "".equals(((StaffRoleVO)vo).getExpDate()) || ((StaffRoleVO)vo).getExpDate() == null ){
				((StaffRoleVO)vo).setExpDate("2029-01-01 00:00:00");
			}
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffRoleVO)vo).getExpDate()) );
			if( "".equals( ((StaffRoleVO)vo).getRegionId())){
				((StaffRoleVO)vo).setRegionId(null);
			}
			stmt.setString( index++, ((StaffRoleVO)vo).getRegionId());
			if( "".equals(((StaffRoleVO)vo).getRegionType())){
				((StaffRoleVO)vo).setRegionType(null);
			}
			stmt.setString( index++, ((StaffRoleVO)vo).getRegionType());
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

	public boolean update(StaffRoleVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE STAFF_ROLE SET party_role_id = ?,role_id = ?,state = ?,eff_date = ?,exp_date = ?, region_id = ?, region_type = ?" );
			sql.append( " WHERE  party_role_id = ? AND role_id = ? AND region_id = ? AND region_type = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			/*
			if ("".equals(((StaffRoleVO)vo).getPartyRoleId())) {
				((StaffRoleVO)vo).setPartyRoleId(null);
			}
			stmt.setString( index++, vo.getPartyRoleId() );
			if ("".equals(((StaffRoleVO)vo).getRoleId())) {
				((StaffRoleVO)vo).setRoleId(null);
			}
			if( "".equals(((StaffRoleVO)vo).getRegionId())){
				((StaffRoleVO)vo).setRegionId(null);
			}
			if( "".equals(((StaffRoleVO)vo).getRegionType())){
				((StaffRoleVO)vo).setRegionType(null);
			}*/
			stmt.setString( index++, vo.getRoleId() );
			stmt.setString( index++, vo.getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getEffDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getExpDate()) );
			stmt.setString( index++, vo.getRegionId());
			stmt.setString( index++, vo.getRegionType());
			stmt.setString( index++, vo.getPartyRoleId());
			stmt.setString(index++,vo.getRoleId());
			stmt.setString(index++,vo.getRegionId());
			stmt.setString( index++, vo.getRegionType());
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
			sql.append( "UPDATE STAFF_ROLE SET staff_role_id = ?,party_role_id = ?,role_id = ?,state = ?,eff_date = ?,exp_date = ?, region_id = ?, region_type = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((StaffRoleVO)vo).getPartyRoleId())) {
				((StaffRoleVO)vo).setPartyRoleId(null);
			}
			stmt.setString( index++, ((StaffRoleVO)vo).getPartyRoleId() );
			if ("".equals(((StaffRoleVO)vo).getRoleId())) {
				((StaffRoleVO)vo).setRoleId(null);
			}
			if( "".equals(((StaffRoleVO)vo).getRegionId())){
				((StaffRoleVO)vo).setRegionId( null ) ;
			}
			if( "".equals(((StaffRoleVO)vo).getRegionType())){
				((StaffRoleVO)vo).setRegionType(null);
			}
			stmt.setString( index++, ((StaffRoleVO)vo).getRoleId() );
			stmt.setString( index++, ((StaffRoleVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffRoleVO)vo).getEffDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffRoleVO)vo).getExpDate()) );
			stmt.setString( index++, ((StaffRoleVO)vo).getRegionId());
			stmt.setString( index++, ((StaffRoleVO)vo).getRegionType());
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

	public long delete( String party_role_id,String role_id, String region_id, String region_type ) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			
			stmt.setString( index++, party_role_id );
			stmt.setString( index++, role_id );
			stmt.setString( index++, region_id );
			stmt.setString( index++, region_type );
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
		return new StaffRoleVO();
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
     * 查询员工角色(简单数据方式)
     * @param pstaff_id
     * @return 员工对应的所有员工角色列表(StaffRoleVO构成的ArrayList)
     * @throws DAOSystemException
     */
	public ArrayList getSimpleStaffRolesByPartyRoleId( String pstaff_id ) throws DAOSystemException{
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT distinct sr.party_role_id,sr.role_id,sr.state, r.role_name,r.role_desc , r.role_name_short "+
        					" FROM STAFF_ROLE sr, ROLES r WHERE sr.role_id = r.role_id AND sr.party_role_id = ?";
        
        ArrayList alStaffRoles = new ArrayList();
        
        try {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
            stmt.setMaxRows( maxRows );
            stmt.setString( 1, pstaff_id );
            rs = stmt.executeQuery();
            //StaffRoleVO vo;
            RolesVO vo ;
            while(rs.next())
            {
                //vo = new StaffRoleVO();
            	vo = new RolesVO();
                //vo.setPartyRoleId(rs.getString("party_role_id"));
                vo.setRoleId(rs.getString("role_id"));
                vo.setRoleName(rs.getString("role_name"));
                vo.setRoleDesc(rs.getString("role_desc"));
                vo.setRoleNameShort(rs.getString("role_name_short"));
                alStaffRoles.add( vo );
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
        return alStaffRoles;
	}
    /**
     * 查询员工角色
     * @param pstaff_id
     * @return 员工对应的所有员工角色列表(StaffRoleVO构成的ArrayList)
     * @throws DAOSystemException
     */
    public ArrayList getStaffRolesByStaff(String pstaff_id) throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT sr.party_role_id,sr.role_id,sr.state,sr.eff_date,sr.exp_date,sr.region_id,sr.region_type, " +
        					"reg.region_name,r.role_name,r.role_desc " +
        					"FROM STAFF_ROLE sr, ROLES r, REGION reg " +
        					"WHERE sr.role_id = r.role_id AND reg.region_id = sr.region_id AND sr.region_type != '3' AND sr.party_role_id = ?";
        
        ArrayList alStaffRoles = new ArrayList();
        
        try {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
            stmt.setMaxRows( maxRows );
            stmt.setString( 1, pstaff_id );
            rs = stmt.executeQuery();
            StaffRoleVO vo;
            while(rs.next())
            {
                vo = new StaffRoleVO();
                populateDto( vo, rs);
                vo.setRoleName(rs.getString("role_name"));
                vo.setRegionName( rs.getString("region_name"));
                vo.setRoleDesc(rs.getString("role_desc"));
                alStaffRoles.add( vo );
            }
            
            sql = "SELECT sr.party_role_id,sr.role_id,sr.state,sr.eff_date,sr.exp_date,sr.region_id,sr.region_type, " +
			"org.org_name as region_name,r.role_name,r.role_desc " +
			"FROM STAFF_ROLE sr, ROLES r, ORGANIZATION org " +
			"WHERE sr.role_id = r.role_id AND org.party_id = sr.region_id AND sr.region_type = '3' AND sr.party_role_id = ?";
            
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setMaxRows( maxRows ) ;
            stmt.setString( 1, pstaff_id ) ;
            rs = stmt.executeQuery();
            while( rs.next() ){
            	vo = new StaffRoleVO();
            	populateDto( vo, rs ) ;
            	vo.setRoleName( rs.getString("role_name")) ;
            	vo.setRegionName( rs.getString("region_name")) ;
            	vo.setRoleDesc(rs.getString("role_desc"));
            	alStaffRoles.add( vo ) ;
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
        return alStaffRoles;
    }
    
    public long deleteByStaff(String pstaff_id) throws DAOSystemException
    {
        return deleteByCond(" party_role_id = " + pstaff_id);
    }
    
}
