package com.ztesoft.oaas.dao.rolepriv;

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
import com.ztesoft.oaas.dao.priv.PrivDAO;
import com.ztesoft.oaas.dao.priv.PrivDAOFactory;
import com.ztesoft.oaas.vo.RolePrivVO;
import com.ztesoft.oaas.vo.RolesVO;

public class RolePrivDAOImpl   implements RolePrivDAO {

	private String SQL_SELECT = "SELECT role_id,privilege_id FROM ROLE_PRIVILEGE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM ROLE_PRIVILEGE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO ROLE_PRIVILEGE ( role_id,privilege_id,privilege_type, state, state_date ) VALUES ( ?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE ROLE_PRIVILEGE SET  WHERE privilege_id = ? AND role_id = ? AND privilege_type = ?  ";

	private String SQL_DELETE = "DELETE FROM ROLE_PRIVILEGE WHERE privilege_id = ? AND role_id = ? AND privilege_type = ?  ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM ROLE_PRIVILEGE ";

	public RolePrivDAOImpl() {

	}

	public RolePrivVO findByPrimaryKey(String pprivilege_id,String prole_id,String pprivilege_type) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE privilege_id = ? AND role_id = ? AND privilege_type = ? ", new String[] { pprivilege_id,prole_id, pprivilege_type } );
		if (arrayList.size()>0)
			return (RolePrivVO)arrayList.get(0);
		else
			return (RolePrivVO) getEmptyVO();
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
			RolePrivVO vo = new RolePrivVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(RolePrivVO vo, ResultSet rs) throws SQLException {
		vo.setRoleId( rs.getString( "role_id" ) );
		vo.setPrivId( rs.getString( "privilege_id" ) );
		vo.setPrivType(rs.getString("privilege_type"));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		RolePrivVO vo = new RolePrivVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord3?¡ä¨ª",this);
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
			if ("".equals(((RolePrivVO)vo).getRoleId())) {
				((RolePrivVO)vo).setRoleId(null);
			}
			stmt.setString( index++, ((RolePrivVO)vo).getRoleId() );
			
			if ("".equals(((RolePrivVO)vo).getPrivId())) {
				((RolePrivVO)vo).setPrivId(null);
			}
			stmt.setString( index++, ((RolePrivVO)vo).getPrivId() );
			
			if("".equals(((RolePrivVO)vo).getPrivType())){
				((RolePrivVO)vo).setPrivType(null);
			}
			stmt.setString( index ++, ((RolePrivVO)vo).getPrivType());
			
			stmt.setString(index++, "00A");
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(DAOUtils.getFormatedDate()));//¡Á¡ä¨¬?¨º¡À??
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

	public boolean update( String pprivilege_id, String prole_id,String pprivilege_type,RolePrivVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null; 
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE ROLE_PRIVILEGE SET role_id = ?,privilege_id = ?,privilege_type = ?" );
			sql.append( " WHERE  privilege_id = ? AND role_id = ? AND privilege_type = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((RolePrivVO)vo).getRoleId())) {
				((RolePrivVO)vo).setRoleId(null);
			}
			stmt.setString( index++, vo.getRoleId() );
			
			if ("".equals(((RolePrivVO)vo).getPrivId())) {
				((RolePrivVO)vo).setPrivId(null);
			}
			stmt.setString( index++, vo.getPrivId() );
			
			if( "".equals(((RolePrivVO)vo).getPrivType())){
				((RolePrivVO)vo).setPrivType(null);
			}
			stmt.setString( index ++, vo.getPrivType());
			
			stmt.setString( index++, pprivilege_id );
			stmt.setString( index++, prole_id );
			stmt.setString( index++, pprivilege_type);
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
			sql.append( "UPDATE ROLE_PRIVILEGE SET role_id = ?,privilege_id = ?,privilege_type = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((RolePrivVO)vo).getRoleId())) {
				((RolePrivVO)vo).setRoleId(null);
			}
			stmt.setString( index++, ((RolePrivVO)vo).getRoleId() );
			
			if ("".equals(((RolePrivVO)vo).getPrivId())) {
				((RolePrivVO)vo).setPrivId(null);
			}
			stmt.setString( index++, ((RolePrivVO)vo).getPrivId() );
			
			if("".equals(((RolePrivVO)vo).getPrivType())){
				((RolePrivVO)vo).setPrivType(null);
			}
			stmt.setString( index++, ((RolePrivVO)vo).getPrivType());
			
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

	public long delete( String pprivilege_id, String prole_id,String pprivilege_type) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pprivilege_id );
			stmt.setString( index++, prole_id );
			stmt.setString( index++, pprivilege_type);
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
		return new RolePrivVO();
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

    public ArrayList getPrivsByRole(String role_id,String privilegeType) throws DAOSystemException
    {
        PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
        return daoPriv.findBySql(
                "SELECT priv.privilege_id, priv.parent_prg_id, priv.privilege_name, priv.privilege_type, priv.privilege_desc, priv.privilege_code, priv.path_code FROM PRIVILEGE priv, ROLE_PRIVILEGE rp WHERE priv.privilege_id=rp.privilege_id AND rp.role_id=? AND rp.privilege_type = ?  ORDER BY priv.path_code", 
                new String[]{role_id,privilegeType});
    }
    
    public ArrayList getRelatingRoleByRole(String role_id) throws DAOSystemException{
    	//RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
    	//return daoRoles.findBySql("select r.role_id,r.role_name ,r.role_desc, r.state, r.state_date,r.ability_grade_num from roles r, role_privilege rp where r.role_id = rp.privilege_id and rp.privilege_type = 1 and rp.role_id = ?",new String[]{role_id});
    	String SQL = "select r.role_id,r.role_name ,r.role_name_short,r.role_desc, r.state, r.state_date,r.ability_grade_num from roles r, role_privilege rp where r.role_id = rp.privilege_id and rp.privilege_type = 1 and rp.role_id = ?";
    	Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
			stmt.setMaxRows( maxRows );
			stmt.setString( 1 , role_id );

			rs = stmt.executeQuery();
			
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				RolesVO vo = new RolesVO();
				vo.setRoleId( rs.getString("role_id"));
				vo.setRoleName(rs.getString("role_name"));
				vo.setRoleDesc(rs.getString("role_desc"));
				vo.setState(rs.getString("state"));
				vo.setStateDate(rs.getString("state_date"));
				vo.setAbilityGradeNum(rs.getString("ability_grade_num"));
				vo.setRoleNameShort(rs.getString("role_name_short"));
				//populateDto( vo, rs);
				resultList.add( vo );
			}
			return resultList;
		}catch (SQLException se) {
			Debug.print(SQL,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+SQL, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
    }
}