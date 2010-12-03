package com.ztesoft.oaas.dao.roles;

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
import com.ztesoft.oaas.dao.orgpostsysrole.OrgPostSysroleDAO;
import com.ztesoft.oaas.dao.orgpostsysrole.OrgPostSysroleDAOFactory;
import com.ztesoft.oaas.vo.RolesVO;

public class RolesDAOImpl   implements RolesDAO {

	private String SQL_SELECT = "SELECT role_id,role_name,role_desc,state,state_date,ability_grade_num,ROLE_NAME_SHORT FROM ROLES";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM ROLES";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO ROLES ( role_id,role_name,role_desc,state,state_date,ability_grade_num,ROLE_NAME_SHORT ) VALUES ( ?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE ROLES SET  role_name = ?, role_desc = ?, state = ?, state_date = ?, ability_grade_num = ?, ROLE_NAME_SHORT = ? WHERE role_id = ? ";

	private String SQL_DELETE = "DELETE FROM ROLES WHERE role_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM ROLES ";

	public RolesDAOImpl() {

	}

	public RolesVO findByPrimaryKey(String prole_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE role_id = ? ", new String[] { prole_id } );
		if (arrayList.size()>0)
			return (RolesVO)arrayList.get(0);
		else
			return (RolesVO) getEmptyVO();
	}

	public ArrayList findBySql2(String sql, String[] sqlParams) throws DAOSystemException {
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

			return fetchMultiResults2(rs);
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

	protected ArrayList fetchMultiResults2(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			RolesVO vo = new RolesVO();
			populateDto2( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}
	
	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			RolesVO vo = new RolesVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(RolesVO vo, ResultSet rs) throws SQLException {
		vo.setRoleId( rs.getString( "role_id" ) );
		vo.setRoleName( rs.getString( "role_name" ) );
		vo.setRoleDesc( rs.getString( "role_desc" ) );
		vo.setState( rs.getString( "state" ) );
		vo.setStateDate( DAOUtils.getFormatedDate(rs.getDate( "state_date" )) );
		vo.setAbilityGradeNum( rs.getString( "ability_grade_num" ) );
		vo.setRoleNameShort(rs.getString("ROLE_NAME_SHORT"));
	}
	
	protected void populateDto2(RolesVO vo, ResultSet rs) throws SQLException {
		vo.setRoleId( rs.getString( "role_id" ) );
		vo.setRoleName( rs.getString( "role_name" ) );
		vo.setRoleDesc( rs.getString( "role_desc" ) );
		vo.setState( rs.getString( "state" ) );
		vo.setStateDate( rs.getString( "state_date" ) );
		vo.setAbilityGradeNum( rs.getString( "ability_grade_num" ) );
		vo.setRoleNameShort(rs.getString("ROLE_NAME_SHORT"));
		vo.setOrgPostRoleId(rs.getString("org_post_role_id"));
		vo.setRegionId(rs.getString("region_id"));
		vo.setRegionName(rs.getString("region_name"));
		vo.setRegionType(rs.getString("region_type"));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		RolesVO vo = new RolesVO();
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
			((RolesVO)vo).setRoleId(smDAO.getNextSequence(vo.getTableName(), "ROLE_ID"));
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
            
			int index = 1;
			if ("".equals(((RolesVO)vo).getRoleId())) {
				((RolesVO)vo).setRoleId(null);
			}
			stmt.setString( index++, ((RolesVO)vo).getRoleId() );
			stmt.setString( index++, ((RolesVO)vo).getRoleName() );
			stmt.setString( index++, ((RolesVO)vo).getRoleDesc() );
			stmt.setString( index++, ((RolesVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.getCurrentTimestamp());
			
			if ("".equals(((RolesVO)vo).getAbilityGradeNum())) {
				((RolesVO)vo).setAbilityGradeNum(null);
			}
			stmt.setString( index++, ((RolesVO)vo).getAbilityGradeNum() );
			stmt.setString( index++, ((RolesVO)vo).getRoleNameShort());
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

	public boolean update( String prole_id,RolesVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE ROLES SET role_id = ?,role_name = ?,role_desc = ?,state = ?,state_date = ?,ability_grade_num = ?,ROLE_NAME_SHORT=?" );
			sql.append( " WHERE  role_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((RolesVO)vo).getRoleId())) {
				((RolesVO)vo).setRoleId(null);
			}
			stmt.setString( index++, vo.getRoleId() );
			stmt.setString( index++, vo.getRoleName() );
			stmt.setString( index++, vo.getRoleDesc() );
			stmt.setString( index++, vo.getState() );
			stmt.setTimestamp( index++, DAOUtils.getCurrentTimestamp());
			
			if ("".equals(((RolesVO)vo).getAbilityGradeNum())) {
				((RolesVO)vo).setAbilityGradeNum(null);
			}
			stmt.setString( index++, vo.getAbilityGradeNum() );
			stmt.setString( index++, vo.getRoleNameShort());
			stmt.setString( index++, prole_id );
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
			sql.append( "UPDATE ROLES SET role_id = ?,role_name = ?,role_desc = ?,state = ?,state_date = ?,ability_grade_num = ?,ROLE_NAME_SHORT=?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((RolesVO)vo).getRoleId())) {
				((RolesVO)vo).setRoleId(null);
			}
			stmt.setString( index++, ((RolesVO)vo).getRoleId() );
			stmt.setString( index++, ((RolesVO)vo).getRoleName() );
			stmt.setString( index++, ((RolesVO)vo).getRoleDesc() );
			stmt.setString( index++, ((RolesVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.getCurrentTimestamp());
			
			if ("".equals(((RolesVO)vo).getAbilityGradeNum())) {
				((RolesVO)vo).setAbilityGradeNum(null);
			}
			stmt.setString( index++, ((RolesVO)vo).getAbilityGradeNum() );
			stmt.setString( index++, ((RolesVO)vo).getRoleNameShort());
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

	public long delete( String prole_id) throws DAOSystemException {
		OrgPostSysroleDAO orgPostSysroleDAO = OrgPostSysroleDAOFactory.getOrgPostSysroleDAO();
		long roleCount = orgPostSysroleDAO.countByCond( "role_id = " + prole_id );
		if( roleCount > 0 ){
			throw new DAOSystemException("不能删除角色,该角色被分配给某个岗位.");
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, prole_id );
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
		return new RolesVO();
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

    public ArrayList getAllRoles()
    {
        return findBySql( SQL_SELECT , null );
    }
}
