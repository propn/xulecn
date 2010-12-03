package com.ztesoft.oaas.dao.organizationpost;

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
import com.ztesoft.oaas.vo.OrganizationPostVO;

public class OrganizationPostDAOImpl   implements OrganizationPostDAO {

	private String SQL_SELECT = "SELECT org_post_id,party_id,position_id,state,state_date FROM ORGANIZATION_POST";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM ORGANIZATION_POST";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO ORGANIZATION_POST ( org_post_id,party_id,position_id,state,state_date ) VALUES ( ?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE ORGANIZATION_POST SET  org_post_id = ?, party_id = ?, position_id = ?, state = ?, state_date = ? WHERE";

	private String SQL_DELETE = "DELETE FROM ORGANIZATION_POST WHERE org_post_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM ORGANIZATION_POST ";

	public OrganizationPostDAOImpl() {

	}

    public OrganizationPostVO findByPrimaryKey(String porgpost_id) throws DAOSystemException {
        ArrayList arrayList = findBySql( SQL_SELECT + " WHERE org_post_id = ? ", new String[] { porgpost_id } );
        if (arrayList.size()>0)
            return (OrganizationPostVO)arrayList.get(0);
        else
            return (OrganizationPostVO) getEmptyVO();
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
			OrganizationPostVO vo = new OrganizationPostVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(OrganizationPostVO vo, ResultSet rs) throws SQLException {
		vo.setOrgPostId( rs.getString( "org_post_id" ) );
		vo.setPartyId( rs.getString( "party_id" ) );
		vo.setPositionId( rs.getString( "position_id" ) );
		vo.setState( rs.getString( "state" ) );
		vo.setStateDate( DAOUtils.getFormatedDate(rs.getDate( "state_date" )) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		OrganizationPostVO vo = new OrganizationPostVO();
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
			((OrganizationPostVO)vo).setOrgPostId(smDAO.getNextSequence(vo.getTableName(), "ORG_POST_ID"));
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
            
			int index = 1;
			if ("".equals(((OrganizationPostVO)vo).getOrgPostId())) {
				((OrganizationPostVO)vo).setOrgPostId(null);
			}
			stmt.setString( index++, ((OrganizationPostVO)vo).getOrgPostId() );
			if ("".equals(((OrganizationPostVO)vo).getPartyId())) {
				((OrganizationPostVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((OrganizationPostVO)vo).getPartyId() );
			if ("".equals(((OrganizationPostVO)vo).getPositionId())) {
				((OrganizationPostVO)vo).setPositionId(null);
			}
			stmt.setString( index++, ((OrganizationPostVO)vo).getPositionId() );
			stmt.setString( index++, ((OrganizationPostVO)vo).getState() );
			
			stmt.setTimestamp( index++, DAOUtils.getCurrentTimestamp() );
			
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

    public boolean update( String porgpost_id,OrganizationPostVO vo) throws DAOSystemException {
    	Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();
        try {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            sql.append( "UPDATE ORGANIZATION_POST SET org_post_id = ?,party_id = ?,position_id = ?,state = ?,state_date = ?" );
            sql.append( " WHERE  org_post_id = ? " );
            stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
            int index = 1;
            if ("".equals(((OrganizationPostVO)vo).getOrgPostId())) {
                ((OrganizationPostVO)vo).setOrgPostId(null);
            }
            stmt.setString( index++, ((OrganizationPostVO)vo).getOrgPostId() );
            
            if ("".equals(((OrganizationPostVO)vo).getPartyId())) {
                ((OrganizationPostVO)vo).setPartyId(null);
            }
            stmt.setString( index++, ((OrganizationPostVO)vo).getPartyId());
            
            if ("".equals(((OrganizationPostVO)vo).getPositionId())) {
                ((OrganizationPostVO)vo).setPositionId(null);
            }
            stmt.setString( index++, ((OrganizationPostVO)vo).getPositionId() );
            
            stmt.setString( index++, ((OrganizationPostVO)vo).getState() );
            
            stmt.setTimestamp( index++, DAOUtils.getCurrentTimestamp() );
            
            stmt.setString(index++, porgpost_id);
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
			sql.append( "UPDATE ORGANIZATION_POST SET org_post_id = ?,party_id = ?,position_id = ?,state = ?,state_date = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((OrganizationPostVO)vo).getOrgPostId())) {
				((OrganizationPostVO)vo).setOrgPostId(null);
			}
			stmt.setString( index++, ((OrganizationPostVO)vo).getOrgPostId() );
			if ("".equals(((OrganizationPostVO)vo).getPartyId())) {
				((OrganizationPostVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((OrganizationPostVO)vo).getPartyId() );
			if ("".equals(((OrganizationPostVO)vo).getPositionId())) {
				((OrganizationPostVO)vo).setPositionId(null);
			}
			stmt.setString( index++, ((OrganizationPostVO)vo).getPositionId() );
			stmt.setString( index++, ((OrganizationPostVO)vo).getState() );
			stmt.setTimestamp( index++, DAOUtils.getCurrentTimestamp() );
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

    public long delete( String porgpost_id) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
            int index = 1;
            stmt.setString( index++, porgpost_id );
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
		return new OrganizationPostVO();
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

	public ArrayList queryOrgPosition( String orgId, String postName,String state ) throws DAOSystemException{
		String sql = "SELECT op.org_post_id, op.party_id, op.position_id, op.state, op.state_date, p.position_name FROM ORGANIZATION_POST op, POSITION p WHERE op.position_id=p.position_id AND 1=1";
		if( !"".equals(orgId)){
			sql = sql + " AND op.party_id = " + orgId ;
		}
		if( !"".equals(postName)){
			sql = sql + " AND p.position_name = '" + postName + "'";
		}
		if( !"".equals( state )){
			sql = sql + " AND op.state = '" + state + "'";
		}
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
            stmt.setMaxRows( maxRows );

            rs = stmt.executeQuery();
            
            ArrayList alPositions = new ArrayList();
            while (rs.next()) {
                OrganizationPostVO vo = new OrganizationPostVO();
                populateDto( vo, rs);
                vo.setPositionName(rs.getString("position_name"));
                alPositions.add( vo );
            }
            
            return alPositions;
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
    /**
     * 查询指定组织的所有组织岗位实体
     * @param org_id 组织标识
     * @return 组织岗位实体列表(OrganizationPostVO组成的ArrayList)
     * @throws DAOSystemException
     */
    public ArrayList getOrgPostsByOrganization(String org_id) throws DAOSystemException
    {
        String sql = "SELECT op.org_post_id, op.party_id, op.position_id, op.state, op.state_date, p.position_name FROM ORGANIZATION_POST op, POSITION p WHERE op.position_id=p.position_id AND party_id = ? ";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
            stmt.setMaxRows( maxRows );
            stmt.setString( 1, org_id);

            rs = stmt.executeQuery();
            
            ArrayList alPositions = new ArrayList();
            while (rs.next()) {
                OrganizationPostVO vo = new OrganizationPostVO();
                populateDto( vo, rs);
                vo.setPositionName(rs.getString("position_name"));
                alPositions.add( vo );
            }
            
            return alPositions;
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
    
    public long deleteByOrganization(String org_id) throws DAOSystemException
    {
        return deleteByCond(" party_id = " + org_id);
    }
}
