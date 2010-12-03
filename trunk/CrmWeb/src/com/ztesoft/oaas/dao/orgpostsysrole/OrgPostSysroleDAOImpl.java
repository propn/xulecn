package com.ztesoft.oaas.dao.orgpostsysrole;

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
import com.ztesoft.oaas.dao.roles.RolesDAO;
import com.ztesoft.oaas.dao.roles.RolesDAOFactory;
import com.ztesoft.oaas.vo.OrgPostSysroleVO;

public class OrgPostSysroleDAOImpl   implements OrgPostSysroleDAO {

	private String SQL_SELECT = "SELECT org_post_role_id,org_post_id,role_id,state,eff_date,exp_date,region_id,region_type FROM ORG_POST_SYSROLE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM ORG_POST_SYSROLE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO ORG_POST_SYSROLE ( org_post_role_id,org_post_id,role_id,state,eff_date,exp_date,region_id,region_type ) VALUES ( ?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE ORG_POST_SYSROLE SET  org_post_id = ?, role_id = ?, state = ?, eff_date = ?, exp_date = ?, region_id = ?, region_type = ? WHERE org_post_role_id = ? ";

	private String SQL_DELETE = "DELETE FROM ORG_POST_SYSROLE WHERE org_post_role_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM ORG_POST_SYSROLE ";

	public OrgPostSysroleDAOImpl() {

	}

	public OrgPostSysroleVO findByPrimaryKey(String porg_post_role_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE org_post_role_id = ? ", new String[] { porg_post_role_id } );
		if (arrayList.size()>0)
			return (OrgPostSysroleVO)arrayList.get(0);
		else
			return (OrgPostSysroleVO) getEmptyVO();
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
			OrgPostSysroleVO vo = new OrgPostSysroleVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(OrgPostSysroleVO vo, ResultSet rs) throws SQLException {
		vo.setOrgPostRoleId( rs.getString( "org_post_role_id" ) );
		vo.setOrgPostId( rs.getString( "org_post_id" ) );
		vo.setRoleId( rs.getString( "role_id" ) );
		vo.setState( rs.getString( "state" ) );
		vo.setEffDate( DAOUtils.getFormatedDate(rs.getDate( "eff_date" ) ));
		vo.setExpDate( DAOUtils.getFormatedDate(rs.getDate( "exp_date" ) ));
		vo.setRegionId( rs.getString( "region_id"));
		vo.setRegionType( rs.getString("region_type"));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		OrgPostSysroleVO vo = new OrgPostSysroleVO();
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

			SequenceManageDAO smDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
			((OrgPostSysroleVO)vo).setOrgPostRoleId(smDAO.getNextSequence(vo.getTableName(),"ORG_POST_ROLE_ID"));
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			
			int index = 1;
			if ("".equals(((OrgPostSysroleVO)vo).getOrgPostRoleId())) {
				((OrgPostSysroleVO)vo).setOrgPostRoleId(null);
			}
			stmt.setString( index++, ((OrgPostSysroleVO)vo).getOrgPostRoleId() );
			if ("".equals(((OrgPostSysroleVO)vo).getOrgPostId())) {
				((OrgPostSysroleVO)vo).setOrgPostId(null);
			}
			stmt.setString( index++, ((OrgPostSysroleVO)vo).getOrgPostId() );
			if ("".equals(((OrgPostSysroleVO)vo).getRoleId())) {
				((OrgPostSysroleVO)vo).setRoleId(null);
			}
			stmt.setString( index++, ((OrgPostSysroleVO)vo).getRoleId() );
			stmt.setString( index++, ((OrgPostSysroleVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((OrgPostSysroleVO)vo).getEffDate()) );
			
			if( "".equals(((OrgPostSysroleVO)vo).getExpDate()) || ((OrgPostSysroleVO)vo).getExpDate() == null ){
				((OrgPostSysroleVO)vo).setExpDate("2029-01-01 00:00:00");
			}
			
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((OrgPostSysroleVO)vo).getExpDate()) );
			stmt.setString( index++, ((OrgPostSysroleVO)vo).getRegionId());
			stmt.setString( index++, ((OrgPostSysroleVO)vo).getRegionType());
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

	public boolean update( String porg_post_role_id,OrgPostSysroleVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE ORG_POST_SYSROLE SET org_post_role_id = ?,org_post_id = ?,role_id = ?,state = ?,eff_date = ?,exp_date = ?, region_id = ?, region_type = ?" );
			sql.append( " WHERE  org_post_role_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((OrgPostSysroleVO)vo).getOrgPostRoleId())) {
				((OrgPostSysroleVO)vo).setOrgPostRoleId(null);
			}
			stmt.setString( index++, vo.getOrgPostRoleId() );
			if ("".equals(((OrgPostSysroleVO)vo).getOrgPostId())) {
				((OrgPostSysroleVO)vo).setOrgPostId(null);
			}
			stmt.setString( index++, vo.getOrgPostId() );
			if ("".equals(((OrgPostSysroleVO)vo).getRoleId())) {
				((OrgPostSysroleVO)vo).setRoleId(null);
			}
			stmt.setString( index++, vo.getRoleId() );
			stmt.setString( index++, vo.getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getEffDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getExpDate()) );
			stmt.setString( index++, vo.getRegionId());
			stmt.setString( index++, vo.getRegionType());
			stmt.setString( index++, porg_post_role_id );
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
			sql.append( "UPDATE ORG_POST_SYSROLE SET org_post_role_id = ?,org_post_id = ?,role_id = ?,state = ?,eff_date = ?,exp_date = ?, region_id = ?, region_type = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((OrgPostSysroleVO)vo).getOrgPostRoleId())) {
				((OrgPostSysroleVO)vo).setOrgPostRoleId(null);
			}
			stmt.setString( index++, ((OrgPostSysroleVO)vo).getOrgPostRoleId() );
			if ("".equals(((OrgPostSysroleVO)vo).getOrgPostId())) {
				((OrgPostSysroleVO)vo).setOrgPostId(null);
			}
			stmt.setString( index++, ((OrgPostSysroleVO)vo).getOrgPostId() );
			if ("".equals(((OrgPostSysroleVO)vo).getRoleId())) {
				((OrgPostSysroleVO)vo).setRoleId(null);
			}
			stmt.setString( index++, ((OrgPostSysroleVO)vo).getRoleId() );
			stmt.setString( index++, ((OrgPostSysroleVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((OrgPostSysroleVO)vo).getEffDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((OrgPostSysroleVO)vo).getExpDate()) );
			stmt.setString( index++, ((OrgPostSysroleVO)vo).getRegionId());
			stmt.setString( index++, ((OrgPostSysroleVO)vo).getRegionType());
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

	public long delete( String porg_post_role_id) throws DAOSystemException {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, porg_post_role_id );
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
		return new OrgPostSysroleVO();
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
     * 根据组织岗位标识查询其所有角色
     * @param org_pos_id
     * @return 指定组织岗位对应的所有角色列表（RolesVO构成的ArrayList）
     * @throws DAOSystemException
     */
    public ArrayList getRolesByOrgPost(String org_pos_id) throws DAOSystemException
    {
        RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
        //查询计费线的地域,营销线的地域,资源线的地域
        String sql = "SELECT r.role_id, r.role_name, r.role_desc, r.state, r.state_date, r.ability_grade_num,r.role_name_short," +
        					"ops.org_post_role_id,ops.region_id,ops.region_type,reg.region_name " + 
        					"FROM ROLES r, ORG_POST_SYSROLE ops, REGION reg " + 
        					"WHERE r.role_id=ops.role_id AND ops.region_id = reg.region_id AND ops.region_type != '3' AND ops.org_post_id=? ";
        ArrayList regionList = new ArrayList();
        regionList = daoRoles.findBySql2(sql , new String[]{org_pos_id});
        
			 //查询营销线的组织
	        sql = "SELECT r.role_id, r.role_name, r.role_desc, r.state, r.state_date, r.ability_grade_num,r.role_name_short," + 
	        		"ops.org_post_role_id,ops.region_id,ops.region_type,org.org_name as region_name " + 
	        		"FROM ROLES r, ORG_POST_SYSROLE ops, ORGANIZATION org " + 
	        		"WHERE r.role_id=ops.role_id AND ops.region_id = org.party_id AND ops.region_type = '3' " +
	        		"AND ops.org_post_id=?";
	        ArrayList list = daoRoles.findBySql2( sql , new String[]{org_pos_id});
			
	        //合并结果集合
	        for( int i = 0; i < list.size(); i ++ ){
	        	regionList.add( list.get(i ));
	        }
        return regionList ;
    }
    
    public long deleteByOrgPost(String org_pos_id) throws DAOSystemException
    {
        return deleteByCond(" org_post_id = "+org_pos_id);
    }
}
