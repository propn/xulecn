package com.ztesoft.oaas.dao.staffpriv;

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
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.staticdata.StaticAttrCache;
import com.ztesoft.component.common.staticdata.vo.StaticAttrVO;
import com.ztesoft.oaas.dao.partyrole.PartyRoleDAO;
import com.ztesoft.oaas.dao.partyrole.PartyRoleDAOFactory;
import com.ztesoft.oaas.vo.PartyRoleVO;
import com.ztesoft.oaas.vo.StaffPrivVO;

public class StaffPrivDAOImpl   implements StaffPrivDAO {

	private String SQL_SELECT = "SELECT staff_privilege_id,party_role_id,privilege_id,state,eff_date,exp_date,region_id,region_type FROM STAFF_PRIVILEGE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM STAFF_PRIVILEGE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO STAFF_PRIVILEGE ( staff_privilege_id,party_role_id,privilege_id,state,eff_date,exp_date,region_id,region_type,privilege_type ) VALUES ( ?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE STAFF_PRIVILEGE SET  party_role_id = ?, privilege_id = ?, state = ?, eff_date = ?, exp_date = ?, region_id = ?, region_type = ? WHERE staff_privilege_id = ? ";

	private String SQL_DELETE = "DELETE FROM STAFF_PRIVILEGE WHERE staff_privilege_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM STAFF_PRIVILEGE ";

	public StaffPrivDAOImpl() {

	}

	public StaffPrivVO findByPrimaryKey(String pstaff_privilege_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE staff_privilege_id = ? ", new String[] { pstaff_privilege_id } );
		if (arrayList.size()>0)
			return (StaffPrivVO)arrayList.get(0);
		else
			return (StaffPrivVO) getEmptyVO();
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
			StaffPrivVO vo = new StaffPrivVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(StaffPrivVO vo, ResultSet rs) throws SQLException {
		vo.setStaffPrivId( rs.getString( "staff_privilege_id" ) );
		vo.setPartyRoleId( rs.getString( "party_role_id" ) );
		vo.setPrivId( rs.getString( "privilege_id" ) );
		vo.setState( rs.getString( "state" ) );
		vo.setEffDate( DAOUtils.getFormatedDate(rs.getDate( "eff_date" ) ) );
		vo.setExpDate( DAOUtils.getFormatedDate(rs.getDate( "exp_date" ) ) );
		vo.setRegionId( rs.getString( "region_id"));
		vo.setRegionType( rs.getString( "region_type" ));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		StaffPrivVO vo = new StaffPrivVO();
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
			((StaffPrivVO)vo).setStaffPrivId(smDAO.getNextSequence(vo.getTableName(), "STAFF_PRIVILEGE_ID"));
			
			PartyRoleDAO partyRoleDao = PartyRoleDAOFactory.getPartyRoleDAO();
			PartyRoleVO partyRoleVo = partyRoleDao.findByPrimaryKey(((StaffPrivVO)vo).getPartyRoleId());
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
            
			int index = 1;
			if ("".equals(((StaffPrivVO)vo).getStaffPrivId())) {
				((StaffPrivVO)vo).setStaffPrivId(null);
			}
			stmt.setString( index++, ((StaffPrivVO)vo).getStaffPrivId() );
			if ("".equals(((StaffPrivVO)vo).getPartyRoleId())) {
				((StaffPrivVO)vo).setPartyRoleId(null);
			}
			stmt.setString( index++, ((StaffPrivVO)vo).getPartyRoleId() );
			if ("".equals(((StaffPrivVO)vo).getPrivId())) {
				((StaffPrivVO)vo).setPrivId(null);
			}
			
			stmt.setString( index++, ((StaffPrivVO)vo).getPrivId() );
			stmt.setString( index++, ((StaffPrivVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffPrivVO)vo).getEffDate()) );
			//stmt.setTimestamp( index++, DAOUtils.parseTimestamp(partyRoleVo.getExpDate()));
			if( "".equals(((StaffPrivVO)vo).getExpDate()) || ((StaffPrivVO)vo).getExpDate() == null ){
				((StaffPrivVO)vo).setExpDate("2029-01-01 00:00:00");
			}
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffPrivVO)vo).getExpDate()) );			
			stmt.setString( index++, ((StaffPrivVO)vo).getRegionId());
			stmt.setString( index++, ((StaffPrivVO)vo).getRegionType());
			
			stmt.setString(index++, "2");//暂时使用2作为权限类型
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

	public boolean update( String pstaff_privilege_id,StaffPrivVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE STAFF_PRIVILEGE SET staff_privilege_id = ?,party_role_id = ?,privilege_id = ?,state = ?,eff_date = ?,exp_date = ?,region_id=?,region_type=?" );
			sql.append( " WHERE  staff_privilege_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((StaffPrivVO)vo).getStaffPrivId())) {
				((StaffPrivVO)vo).setStaffPrivId(null);
			}
			stmt.setString( index++, vo.getStaffPrivId() );
			if ("".equals(((StaffPrivVO)vo).getPartyRoleId())) {
				((StaffPrivVO)vo).setPartyRoleId(null);
			}
			stmt.setString( index++, vo.getPartyRoleId() );
			if ("".equals(((StaffPrivVO)vo).getPrivId())) {
				((StaffPrivVO)vo).setPrivId(null);
			}
			stmt.setString( index++, vo.getPrivId() );
			stmt.setString( index++, vo.getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getEffDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getExpDate()) );
			stmt.setString( index++, vo.getRegionId());
			stmt.setString( index++, vo.getRegionType());
			stmt.setString( index++, pstaff_privilege_id );
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
			sql.append( "UPDATE STAFF_PRIVILEGE SET staff_privilege_id = ?,party_role_id = ?,privilege_id = ?,state = ?,eff_date = ?,exp_date = ?,region_id=?,region_type=?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((StaffPrivVO)vo).getStaffPrivId())) {
				((StaffPrivVO)vo).setStaffPrivId(null);
			}
			stmt.setString( index++, ((StaffPrivVO)vo).getStaffPrivId() );
			if ("".equals(((StaffPrivVO)vo).getPartyRoleId())) {
				((StaffPrivVO)vo).setPartyRoleId(null);
			}
			stmt.setString( index++, ((StaffPrivVO)vo).getPartyRoleId() );
			if ("".equals(((StaffPrivVO)vo).getPrivId())) {
				((StaffPrivVO)vo).setPrivId(null);
			}
			stmt.setString( index++, ((StaffPrivVO)vo).getPrivId() );
			stmt.setString( index++, ((StaffPrivVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffPrivVO)vo).getEffDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffPrivVO)vo).getExpDate()) );
			stmt.setString( index++, ((StaffPrivVO)vo).getRegionId());
			stmt.setString( index++, ((StaffPrivVO)vo).getRegionType());
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

	public long delete( String pstaff_privilege_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pstaff_privilege_id );
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
		return new StaffPrivVO();
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
     * 获取员工权限
     * @param pstaff_id 员工参与人角色标识
     * @return 参与人权限列表（StaffPrivVO构成的ArrayList）
     * @throws DAOSystemException
     */
    public ArrayList getStaffPrivsByStaff(String pstaff_id) throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        //查询计费区域,资源区域,营销区域范围的权限
        String sql = "SELECT sp.staff_privilege_id, sp.party_role_id, sp.privilege_id, sp.state, sp.eff_date, sp.exp_date, sp.region_id,sp.region_type," +
        					"reg.region_name,p.privilege_name,p.privilege_type " + 
        					"FROM STAFF_PRIVILEGE sp, PRIVILEGE p , REGION reg " +
        					"WHERE sp.privilege_id=p.privilege_id AND reg.region_id = sp.region_id AND sp.region_type != '3' AND sp.region_type != '-1' AND sp.region_id != -1 AND sp.party_role_id = ? " +
        					"union " +
        					"SELECT sp.staff_privilege_id, sp.party_role_id, sp.privilege_id, sp.state, sp.eff_date, sp.exp_date, sp.region_id, ' ' as region_type," +
        					" '没有区域限制' as region_name,p.privilege_name,p.privilege_type " + 
        					"FROM STAFF_PRIVILEGE sp, PRIVILEGE p " +
        					"WHERE sp.privilege_id=p.privilege_id  AND sp.region_type != '3' AND sp.region_type = '-1' AND sp.region_id = -1 AND sp.party_role_id = ? " ;
        ArrayList alStaffPrivs = new ArrayList();
        try {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
            stmt.setMaxRows( maxRows );
            stmt.setString( 1, pstaff_id );stmt.setString( 2, pstaff_id );
            rs = stmt.executeQuery();
            StaffPrivVO vo;
            while(rs.next())
            {
                vo = new StaffPrivVO();
                populateDto( vo, rs);
                vo.setPrivName(rs.getString("privilege_name"));
                vo.setRegionName(rs.getString("region_name"));
                vo.setPrivType(rs.getString("privilege_type"));
                alStaffPrivs.add( vo );
            }
            
            //查询营销组织范围的权限
            sql = "SELECT sp.staff_privilege_id, sp.party_role_id, sp.privilege_id, sp.state, sp.eff_date, sp.exp_date, sp.region_id,sp.region_type," +
            		"org.org_name as region_name,p.privilege_name " + 
            		"FROM STAFF_PRIVILEGE sp, PRIVILEGE p , ORGANIZATION org " +
            		"WHERE sp.privilege_id=p.privilege_id AND org.party_id = sp.region_id AND sp.region_type = '3' AND sp.party_role_id = ? " ;
            
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setMaxRows( maxRows );
            stmt.setString(1, pstaff_id );
            rs = stmt.executeQuery();
            while( rs.next() ){
            	vo = new StaffPrivVO();
            	populateDto( vo, rs ) ;
            	vo.setPrivName( rs.getString( "privilege_name"));
            	vo.setRegionName( rs.getString( "region_name")) ;
            	alStaffPrivs.add( vo ) ;
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
        return alStaffPrivs;
    }
    
    //================================================================
    /**
     * 获取员工权限的XML形式
     * @param pstaff_id 员工参与人角色标识
     * @return 参与人权限列表（StaffPrivVO构成的ArrayList）
     * @throws DAOSystemException
     */
    public String getStaffPrivXMLItemByPartyRoleId(String pstaff_id) throws DAOSystemException
    {
    	ArrayList privilegeTypeList ;
    	try{
    		privilegeTypeList = StaticAttrCache.getInstance().getStaticAttr("PRIVILEGE_TYPE");
    	}catch( Exception e ){
    		throw new DAOSystemException() ;
    	}
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        //查询计费区域,资源区域,营销区域范围的权限
        /*String SQL = "select distinct sp.party_role_id,sp.privilege_id," +
        " p.privilege_name, p.privilege_type,p.path_code from staff_privilege sp, privilege p " +
        "where sp.privilege_id = p.privilege_id and sp.region_type != '3' and sp.party_role_id = ? order by p.path_code ";*/
        
        //查询员工具有的权限,包括地域线和组织线的权限.
        String SQL = "select distinct sp.party_role_id,sp.privilege_id," +
        " p.privilege_name, p.privilege_type,p.path_code from staff_privilege sp, privilege p " +
        "where sp.privilege_id = p.privilege_id and sp.party_role_id = ? order by p.path_code ";
        
        ArrayList alStaffPrivs = new ArrayList();
        try {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
            stmt.setMaxRows( maxRows );
            stmt.setString( 1, pstaff_id );
            rs = stmt.executeQuery();
            StaffPrivVO vo;
            while(rs.next())
            {
                vo = new StaffPrivVO();
        		vo.setPartyRoleId( rs.getString( "party_role_id" ) );
        		vo.setPrivId( rs.getString( "privilege_id" ) );
                vo.setPrivName(rs.getString("privilege_name"));
                vo.setPrivType(rs.getString("privilege_type"));
                vo.setPathCode(rs.getString("path_code"));
                
                for( int i = 0 ; i < privilegeTypeList.size() ; i ++ ){
                	StaticAttrVO staticAttrVo = (StaticAttrVO)privilegeTypeList.get(i);
                	if( vo.getPrivType().equals(staticAttrVo.getAttrValue())){
                		vo.setPrivType(staticAttrVo.getAttrValueDesc());
                		break ;
                	}
                }
                alStaffPrivs.add( vo );
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
        return XMLSegBuilder.toXmlItems(alStaffPrivs);
    }
    
    public List getStaffPrivilegeRegionInfo( String privilegeId, String partyRoleId ) throws DAOSystemException{
    	/*String sql = "select sp.region_id, sp.region_type, reg.region_name from staff_privilege sp, region reg where sp.region_id = reg.region_id and sp.region_id != -1 " + 
    						" and sp.region_type != '-1' and sp.privilege_id = ? and sp.party_role_id = ? " +
    						" union " +
    						" select sp.region_id, sp.region_type, '区域无关' as region_name from staff_privilege sp where sp.region_id = -1 " + 
    						" and sp.region_type = '-1' and sp.privilege_id = ? and sp.party_role_id = ? " ;*/
    	
    	String sql = "select sp.region_id, sp.region_type, reg.region_name from staff_privilege sp, region reg where sp.region_id = reg.region_id and sp.region_id != -1 " + 
		" and ( sp.region_type = '0' or sp.region_type = '1' or sp.region_type = '2' ) and sp.privilege_id = ? and sp.party_role_id = ? " +
		" union " +
		" select sp.region_id, sp.region_type, '区域无关' as region_name from staff_privilege sp where sp.region_id = -1 " + 
		//" and sp.region_type = '-1' and sp.privilege_id = ? and sp.party_role_id = ? " +
		" and sp.region_type = '9' and sp.privilege_id = ? and sp.party_role_id = ? " +
		" union " + 
		" select sp.region_id, sp.region_type, org.org_name as region_name from staff_privilege sp, organization org where sp.region_id = org.party_id and sp.region_id != -1 " +
		" and sp.region_type = 3 and sp.privilege_id = ? and sp.party_role_id = ?";

    	Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
        	conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
	    	stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
	        stmt.setMaxRows( maxRows );
	        stmt.setString(1, privilegeId );
	        stmt.setString(2, partyRoleId );
	        stmt.setString(3, privilegeId );
	        stmt.setString(4, partyRoleId );
	        stmt.setString(5, privilegeId );
	        stmt.setString(6, partyRoleId );	        
	        rs = stmt.executeQuery();
	        ArrayList list = new ArrayList();
	        while( rs.next() ){
	        	StaffPrivVO vo = new StaffPrivVO();
	        	vo.setRegionId( rs.getString("region_id"));
	        	vo.setRegionName(rs.getString("region_name"));
	        	vo.setRegionType(rs.getString("region_type"));
	        	list.add( vo ) ;
	        }
	        return list; 
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
    
    public long deleteByStaff(String pstaff_id) throws DAOSystemException
    {
        return deleteByCond(" party_role_id = " + pstaff_id);
    }
    
}
