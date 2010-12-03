package com.ztesoft.oaas.dao.organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.common.util.DCSystemParamUtil;
import com.ztesoft.oaas.dao.agentprop.AgentPropertyDAO;
import com.ztesoft.oaas.dao.agentprop.AgentPropertyDAOFactory;
import com.ztesoft.oaas.vo.AgentPropertyVO;
import com.ztesoft.oaas.vo.OrganizationVO;

public class OrganizationDAOImpl   implements OrganizationDAO
{

    //private String SQL_SELECT = "SELECT decode (cast(org_type_id as varchar(20)),0,'集团公司',1,'省公司',2,'市公司',3,'分公司',4,'营业区',5,'部门',6,'班组',9,'其他组织',cast( org_type_id as varchar(20))) as org_type_name,  party_id,parent_party_id,org_code,org_name,org_level,org_type_id,org_content,address_id,state,state_date,path_code,path_name,org_class,org_type FROM ORGANIZATION";
    private String SQL_SELECT = "SELECT decode (cast(org_type_id as varchar(20)),0,'集团公司',1,'省公司',2,'本地网',3,'营业区',4,'营销区',5,'部门',6,'班组',9,'其他组织',cast( org_type_id as varchar(20))) as org_type_name,  party_id,parent_party_id,org_code,org_name,org_level,org_type_id,org_content,address_id,state,state_date,path_code,path_name,org_class,org_type FROM ORGANIZATION";

    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM ORGANIZATION";

    private int maxRows;

    private String SQL_INSERT = "INSERT INTO ORGANIZATION ( party_id,parent_party_id,org_code,org_name,org_level,org_type_id,org_content,address_id,state,state_date,path_code,path_name,org_class,org_type ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

    private String SQL_UPDATE = "UPDATE ORGANIZATION SET  parent_party_id = ?, org_code = ?, org_name = ?, org_level = ?, org_type_id = ?, org_content = ?, address_id = ?, state = ?, state_date = ?, path_code = ?, path_name = ?, org_class = ?, org_type WHERE party_id = ? ";

    private String SQL_DELETE = "DELETE FROM ORGANIZATION WHERE party_id = ? ";

    private String SQL_DELETE_BY_WHERE = "DELETE FROM ORGANIZATION ";

    public OrganizationDAOImpl()
    {

    }

    public OrganizationVO findByPrimaryKey(String pparty_id)
            throws DAOSystemException
    {
        ArrayList arrayList = findBySql(
                SQL_SELECT + " WHERE party_id = ? ",
                new String[]{
                    pparty_id
                });
        if(arrayList.size() > 0) return (OrganizationVO)arrayList.get(0);
        else return (OrganizationVO)getEmptyVO();
    }

    public ArrayList findBySql(String sql, String[] sqlParams)
            throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));

            
            stmt.setMaxRows(maxRows);
            for(int i = 0; sqlParams != null && i < sqlParams.length; i++)
            {
                stmt.setString(i + 1, sqlParams[i]);
            }

            rs = stmt.executeQuery();

            return fetchMultiResults(rs);
        }
        catch (SQLException se)
        {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while getting sql:\n"
                    + sql, se);
        }
        finally
        {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
    }

    protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException
    {
        ArrayList resultList = new ArrayList();
        while (rs.next())
        {
            OrganizationVO vo = new OrganizationVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }
        return resultList;
    }

    protected void populateDto(OrganizationVO vo, ResultSet rs)
            throws SQLException
    {
        vo.setPartyId(rs.getString("party_id"));
        vo.setParentPartyId(rs.getString("parent_party_id"));
        vo.setOrgCode(rs.getString("org_code"));
        vo.setOrgName(rs.getString("org_name"));
        vo.setOrgLevel(rs.getString("org_level"));
        vo.setOrgTypeId(rs.getString("org_type_id"));
        vo.setOrgContent(rs.getString("org_content"));
        vo.setAddrId(rs.getString("address_id"));
        vo.setState(rs.getString("state"));
        vo.setStateDate(DAOUtils.getFormatedDate(rs.getDate("state_date")));
        vo.setPathCode(rs.getString("path_code"));
        vo.setPathName(rs.getString("path_name"));
        vo.setOrgClass(rs.getString("org_class"));
        
        vo.setOrgTypeName(rs.getString("org_type_name"));//组织类型(部门,班组......) 
        vo.setOrgType(rs.getString("org_type"));//计费组织类型(92B:社会代办点,'92A':电信内部组织)
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException
    {
        OrganizationVO vo = new OrganizationVO();
        try
        {
            populateDto(vo, rs);
        }
        catch (SQLException se)
        {
            Debug.print("populateCurrRecord出错", this);
            throw new DAOSystemException(
                    "SQLException while populateDto:\n",
                    se);
        }
        return vo;
    }

    public List findByCond(String whereCond) throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        try
        {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            SQL = SQL_SELECT + " WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setMaxRows(maxRows);
            rs = stmt.executeQuery();

            return fetchMultiResults(rs);
        }
        catch (SQLException se)
        {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n"
                    + SQL, se);
        }
        finally
        {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
    }

    public boolean update( VO vo ) throws DAOSystemException {
    	update( "party_id = '" + ((OrganizationVO)vo).getPartyId() + "'", vo );
    	return true;
    }
    public boolean update(String whereCond, VO vo) throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();
        try
        {

        	//*********************************************
        	//对于org_type_id为"部门,班组"的组织,增加org_type字段维护,如果org_type
            //选择了"92B:社会代办点",则保存时按默认取值添加记录到代办点属性表
        	AgentPropertyDAO agentPropertyDAO = AgentPropertyDAOFactory.getAgentPropertyDAO();
            if( ("5".equals(((OrganizationVO)vo).getOrgTypeId()) 
            		||"6".equals(((OrganizationVO)vo).getOrgTypeId())) 
            		&& "92B".equals(((OrganizationVO)vo).getOrgType())){
            		AgentPropertyVO testVO = agentPropertyDAO.findByPrimaryKey(((OrganizationVO)vo).getPartyId());
            		if( testVO == null ){
            			DCSystemParamUtil dcSystemParamUtil = new DCSystemParamUtil();
                		String alarmAmout = dcSystemParamUtil.getParam("AGENT_MONEY_ALARM_AMOUNT");
                		
                        AgentPropertyVO agentPropertyVO = new AgentPropertyVO();
                        agentPropertyVO.setPartyId(((OrganizationVO)vo).getPartyId());
                        agentPropertyVO.setDepositAmount("0");
                        agentPropertyVO.setCeilFlag("0");
                        agentPropertyVO.setUpperAmount("0");
                        agentPropertyVO.setScopeFlag("F");
                        agentPropertyVO.setServGroupId(null);
                        agentPropertyVO.setSettledCharge("0");
                        agentPropertyVO.setCharge("0");
                        agentPropertyVO.setLastSettleDate(null);
                        agentPropertyVO.setState("00A");
                        agentPropertyVO.setStateDate(DateFormatUtils.getFormatedDateTime());
                        agentPropertyVO.setAlarmAmount(alarmAmout);
                        agentPropertyDAO.insert(agentPropertyVO);
            		}
            	}else{
            		agentPropertyDAO.deleteByCond(" party_id = " + ((OrganizationVO)vo).getPartyId());
            	}
            //*********************************************
            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            sql
                    .append("UPDATE ORGANIZATION SET party_id = ?,org_code = ?,org_name = ?,org_level = ?,org_type_id = ?,org_content = ?,address_id = ?,state = ?,state_date = ?,org_class = ?, org_type = ? ");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
                    .toString()));
            int index = 1;
            if("".equals(((OrganizationVO)vo).getPartyId()))
            {
                ((OrganizationVO)vo).setPartyId(null);
            }
            stmt.setString(index++, ((OrganizationVO)vo).getPartyId());
            if("".equals(((OrganizationVO)vo).getParentPartyId()))
            {
                ((OrganizationVO)vo).setParentPartyId(null);
            }
            stmt.setString(index++, ((OrganizationVO)vo).getOrgCode());
            stmt.setString(index++, ((OrganizationVO)vo).getOrgName());
            if("".equals(((OrganizationVO)vo).getOrgLevel()))
            {
                ((OrganizationVO)vo).setOrgLevel(null);
            }
            stmt.setString(index++, ((OrganizationVO)vo).getOrgLevel());
            if("".equals(((OrganizationVO)vo).getOrgTypeId()))
            {
                ((OrganizationVO)vo).setOrgTypeId(null);
            }
            stmt.setString(index++, ((OrganizationVO)vo).getOrgTypeId());
            stmt.setString(index++, ((OrganizationVO)vo).getOrgContent());
            if("".equals(((OrganizationVO)vo).getAddrId()))
            {
                ((OrganizationVO)vo).setAddrId(null);
            }
            stmt.setString(index++, ((OrganizationVO)vo).getAddrId());
            stmt.setString(index++, ((OrganizationVO)vo).getState());
            stmt.setDate(index++, DAOUtils.parseDateTime(DateFormatUtils.getFormatedDateTime()));
            stmt.setString(index++, ((OrganizationVO)vo).getOrgClass());
            stmt.setString(index++,((OrganizationVO)vo).getOrgType());
            int rows = stmt.executeUpdate();
            if(rows > 0)
            {
                bResult = true;
            }
        }
        catch (SQLException se)
        {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n"
                    + sql.toString(), se);
        }
        finally
        {
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
        return bResult;
    }

    public long countByCond(String whereCond) throws DAOSystemException
    {
        Connection conn = null;
        long lCount = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        try
        {

            int orderbyIndex = whereCond.toUpperCase().lastIndexOf("ORDER BY");
            if(orderbyIndex > 0)
            {
                whereCond = whereCond.substring(0, orderbyIndex);
            }
            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            SQL = SQL_SELECT_COUNT + " WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            rs = stmt.executeQuery();

            while (rs.next())
            {
                lCount = rs.getLong("COL_COUNTS");
            }
        }
        catch (SQLException se)
        {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n"
                    + SQL, se);
        }
        finally
        {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
        return lCount;
    }

    public long deleteByCond(String whereCond) throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String SQL = "";
        try
        {
            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            SQL = SQL_DELETE_BY_WHERE + " WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            rows = stmt.executeUpdate();
        }
        catch (SQLException se)
        {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while deleting sql:\n"
                    + SQL, se);
        }
        finally
        {
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
        return rows;
    }

    public int getMaxRows()
    {
        return maxRows;
    }

    public void setMaxRows(int maxRows)
    {
        this.maxRows = maxRows;
    }

    public VO getEmptyVO()
    {
        return new OrganizationVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
            throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + " WHERE " + whereCond;
        String filterSQL = SQL;
        if(queryFilter != null)
        {
            filterSQL = queryFilter.doPreFilter(SQL);
        }
        try
        {
            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            SQL = filterSQL;
            stmt = conn.prepareStatement(
                    DAOSQLUtils.getFilterSQL(SQL),
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();

            List retList = null;
            if(queryFilter != null)
            {
                retList = queryFilter.doPostFilter(rs, this);
            }
            else
            {
                retList = fetchMultiResults(rs);
            }
            return retList;
        }
        catch (SQLException se)
        {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n"
                    + SQL, se);
        }
        finally
        {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * 获取所有组织列表，按path_code排序
     * @return 所有组织(OrganizationVO)构成的ArrayList
     * @throws DAOSystemException
     */
    public ArrayList getAllOrganizations() throws DAOSystemException
    {
        return findBySql(SQL_SELECT + " ORDER BY path_code", null);
    }

    /**
     * 获取所有计费组织列表，按path_code排序
     * @return 所有计费组织(OrganizationVO)构成的ArrayList
     * @throws DAOSystemException
     */
    public ArrayList getAllTelecomOrganizations() throws DAOSystemException
    {
        return findBySql(SQL_SELECT + " WHERE org_class='00' ORDER BY path_code", null);
    }

    public ArrayList getTelecomOrganizationListByParentIdWithPrivFlag( String parentId, String privFlag ) throws DAOSystemException{
    	String sql = "select decode (cast(org_type_id as varchar(20)),0,'集团公司',1,'省公司',2,'市公司',3,'分公司',4,'营业区',5,'部门',6,'班组',9,'其他组织',cast( org_type_id as varchar(20))) as org_type_name, party_id,parent_party_id,org_code,org_name,org_level,org_type_id,org_content,address_id,state,state_date,path_code,path_name,org_class,org_type from organization " +
    	" WHERE parent_party_id = " + parentId + " ORDER BY path_code";
    	Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setMaxRows(maxRows);
            
            rs = stmt.executeQuery();
            ArrayList resultList = new ArrayList();
            while (rs.next()){
                OrganizationVO vo = new OrganizationVO();
                populateDto(vo, rs);
                vo.setOrgTypeName(rs.getString("org_type_name"));
                vo.setPrivilegeFlag(privFlag);
                resultList.add(vo);
            }
            return resultList;
        }
        catch (SQLException se)
        {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while getting sql:\n"
                    + sql, se);
        }
        finally
        {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
    }
    public ArrayList getTelecomOrganizationListByParentId( String parentId ) throws DAOSystemException{
    	String cond = SQL_SELECT + " WHERE parent_party_id = " + parentId + " ORDER BY path_code";
    	return findBySql(cond, null);
    }
    
    public ArrayList getTelecomOrganizationByCond(String orgIds, String orgTypeId) throws DAOSystemException{
    	String sql = SQL_SELECT + " WHERE ORG_TYPE_ID = '" + orgTypeId +  "' AND org_class = '00' AND PARTY_ID IN ( " + orgIds + " ) ORDER BY path_code";
    	return findBySql(sql,null);
    }
    /**
     * 获取所有合作伙伴组织列表，按path_code排序
     * @return 所有合作伙伴组织(OrganizationVO)构成的ArrayList
     * @throws DAOSystemException
     */
    public ArrayList getAllPartnerOrganizations() throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT org.party_id, org.parent_party_id, org.org_code, org.org_name, org.org_level, org.org_type_id, org.org_content, org.address_id, org.state, org.state_date, org.path_code, org.path_name, org.org_class,org.org_type, p.eff_date, pr.exp_date, p.add_description, pr.org_manager, pt.partner_type FROM ORGANIZATION org LEFT OUTER JOIN PARTY p ON p.party_id=org.party_id LEFT OUTER JOIN PARTY_ROLE pr ON org.party_id=pr.s_party_id LEFT OUTER JOIN PARTNER pt ON pt.party_role_id=pr.party_role_id WHERE org.org_class='01' ORDER BY org.path_code";
        ArrayList alOrgs = new ArrayList();
        try {
            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
            rs = stmt.executeQuery();
            OrganizationVO vo;
            while(rs.next())
            {
                vo = new OrganizationVO();
                populateDto( vo, rs);
                vo.setEffDate(rs.getString("eff_date"));
                vo.setExpDate(rs.getString("exp_date"));
                vo.setAddDescription(rs.getString("add_description"));
                vo.setOrgManager(rs.getString("org_manager"));
                vo.setPartnerType(rs.getString("partner_type"));
                alOrgs.add( vo );
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
        return alOrgs;
    }

    /**
     * 获取所有对等运营商组织列表，按path_code排序
     * @return 所有对等运营商组织(OrganizationVO)构成的ArrayList
     * @throws DAOSystemException
     */
    public ArrayList getAllCompetitorOrganizations() throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT org.party_id, org.parent_party_id, org.org_code, org.org_name, org.org_level, org.org_type_id, org.org_content, org.address_id, org.state, org.state_date, org.path_code, org.path_name, org.org_class, org.org_type,p.eff_date, pr.exp_date, p.add_description, pr.org_manager FROM ORGANIZATION org LEFT OUTER JOIN PARTY p ON p.party_id=org.party_id LEFT OUTER JOIN PARTY_ROLE pr ON org.party_id=pr.party_id WHERE org.org_class='02' ORDER BY org.path_code";
        ArrayList alOrgs = new ArrayList();
        try {
            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
            rs = stmt.executeQuery();
            OrganizationVO vo;
            while(rs.next())
            {
                vo = new OrganizationVO();
                populateDto( vo, rs);
                vo.setEffDate(rs.getString("eff_date"));
                vo.setExpDate(rs.getString("exp_date"));
                vo.setAddDescription(rs.getString("add_description"));
                vo.setOrgManager(rs.getString("org_manager"));
                alOrgs.add( vo );
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
        return alOrgs;
    }

    /**
     * 获取某节点下的所有直接子节点
     * 
     * @param idParent
     *            父节点标识，不能为空
     * @return
     * @throws DAOSystemException
     */
    public ArrayList getDirectSubNodes(String idParent)
            throws DAOSystemException
    {
        return findBySql(SQL_SELECT + " WHERE parent_party_id=?", new String[]{
            idParent
        });
    }

    /**
     * 插入组织实体，生成组织标识和path_code
     */
    public void insert(VO vo) throws DAOSystemException
    {
        String parent = ((OrganizationVO)vo).getParentPartyId();
        if(parent != null && !"-1".equals(parent) && !"".equals(parent))
        {
            OrganizationVO voParent = findByPrimaryKey(parent);
            if(voParent == null)
            {
                throw new DAOSystemException("INVALID PARENT_PARTY_ID ["
                        + parent + "]");
            }
            parent = voParent.getPathCode();
        }
        else
        {
            parent = null;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        try
        {
            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));
            ((OrganizationVO)vo)
                    .setPathCode(parent == null ? ((OrganizationVO)vo)
                            .getPartyId() : parent + "."
                            + ((OrganizationVO)vo).getPartyId());
            int index = 1;
            if("".equals(((OrganizationVO)vo).getPartyId()))
            {
                ((OrganizationVO)vo).setPartyId(null);
            }
            stmt.setString(index++, ((OrganizationVO)vo).getPartyId());
            if("".equals(((OrganizationVO)vo).getParentPartyId()))
            {
                ((OrganizationVO)vo).setParentPartyId(null);
            }
            stmt.setString(index++, ((OrganizationVO)vo).getParentPartyId());
            stmt.setString(index++, ((OrganizationVO)vo).getOrgCode());
            stmt.setString(index++, ((OrganizationVO)vo).getOrgName());
            if("".equals(((OrganizationVO)vo).getOrgLevel()))
            {
                ((OrganizationVO)vo).setOrgLevel(null);
            }
            stmt.setString(index++, ((OrganizationVO)vo).getOrgLevel());
            if("".equals(((OrganizationVO)vo).getOrgTypeId()))
            {
                ((OrganizationVO)vo).setOrgTypeId(null);
            }
            stmt.setString(index++, ((OrganizationVO)vo).getOrgTypeId());
            stmt.setString(index++, ((OrganizationVO)vo).getOrgContent());
            if("".equals(((OrganizationVO)vo).getAddrId()))
            {
                ((OrganizationVO)vo).setAddrId(null);
            }
            stmt.setString(index++, ((OrganizationVO)vo).getAddrId());
            stmt.setString(index++, ((OrganizationVO)vo).getState());
            stmt.setDate(index++, DAOUtils.parseDateTime(DateFormatUtils.getFormatedDateTime()));
            stmt.setString(index++, ((OrganizationVO)vo).getPathCode());
            stmt.setString(index++, ((OrganizationVO)vo).getPathName());
            stmt.setString(index++, ((OrganizationVO)vo).getOrgClass());
            stmt.setString(index++,((OrganizationVO)vo).getOrgType());
            int rows = stmt.executeUpdate();
        }
        catch (SQLException se)
        {
            Debug.print(SQL_INSERT, this);
            throw new DAOSystemException("SQLException while insert sql:\n"
                    + SQL_INSERT, se);
        }
        finally
        {
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
        
        //对于org_type_id为"部门,班组"的组织,增加org_type字段维护,如果org_type
        //选择了"92B:社会代办点",则保存时按默认取值添加记录到代办点属性表
        if( ("5".equals(((OrganizationVO)vo).getOrgTypeId()) 
        		||"6".equals(((OrganizationVO)vo).getOrgTypeId())) 
        		&& "92B".equals(((OrganizationVO)vo).getOrgType())){
        		DCSystemParamUtil dcSystemParamUtil = new DCSystemParamUtil();
        		String alarmAmout = dcSystemParamUtil.getParam("AGENT_MONEY_ALARM_AMOUNT");
        		AgentPropertyDAO agentPropertyDAO = AgentPropertyDAOFactory.getAgentPropertyDAO();
                AgentPropertyVO agentPropertyVO = new AgentPropertyVO();
                agentPropertyVO.setPartyId(((OrganizationVO)vo).getPartyId());
                agentPropertyVO.setDepositAmount("0");
                agentPropertyVO.setCeilFlag("0");
                agentPropertyVO.setUpperAmount("0");
                agentPropertyVO.setScopeFlag("F");
                agentPropertyVO.setServGroupId(null);
                agentPropertyVO.setSettledCharge("0");
                agentPropertyVO.setCharge("0");
                agentPropertyVO.setLastSettleDate(null);
                agentPropertyVO.setState("00A");
                agentPropertyVO.setStateDate(DateFormatUtils.getFormatedDateTime());
                agentPropertyVO.setAlarmAmount(alarmAmout);

                agentPropertyDAO.insert(agentPropertyVO);
        }
    }

    /**
     * 更新组织实体，如果其标识或父节点变更，重构其path_code，并重构其所有子孙节点的path_code
     */
    public boolean update(String pparty_id, OrganizationVO vo)
            throws DAOSystemException
    {
    	 //对于org_type_id为"部门,班组"的组织,增加org_type字段维护,如果org_type
        //选择了"92B:社会代办点",则保存时按默认取值添加记录到代办点属性表
    	AgentPropertyDAO agentPropertyDAO = AgentPropertyDAOFactory.getAgentPropertyDAO();
        if( ("5".equals(((OrganizationVO)vo).getOrgTypeId()) 
        		||"6".equals(((OrganizationVO)vo).getOrgTypeId())) 
        		&& "92B".equals(((OrganizationVO)vo).getOrgType())){
        		AgentPropertyVO testVO = agentPropertyDAO.findByPrimaryKey(pparty_id);
        		if( testVO == null ){
        			DCSystemParamUtil dcSystemParamUtil = new DCSystemParamUtil();
            		String alarmAmout = dcSystemParamUtil.getParam("AGENT_MONEY_ALARM_AMOUNT");
            		
                    AgentPropertyVO agentPropertyVO = new AgentPropertyVO();
                    agentPropertyVO.setPartyId(((OrganizationVO)vo).getPartyId());
                    agentPropertyVO.setDepositAmount("0");
                    agentPropertyVO.setCeilFlag("0");
                    agentPropertyVO.setUpperAmount("0");
                    agentPropertyVO.setScopeFlag("F");
                    agentPropertyVO.setServGroupId(null);
                    agentPropertyVO.setSettledCharge("0");
                    agentPropertyVO.setCharge("0");
                    agentPropertyVO.setLastSettleDate(null);
                    agentPropertyVO.setState("00A");
                    agentPropertyVO.setStateDate(DateFormatUtils.getFormatedDateTime());
                    agentPropertyVO.setAlarmAmount(alarmAmout);
                    agentPropertyDAO.insert(agentPropertyVO);
        		}
        	}else{
        		agentPropertyDAO.delete(pparty_id);
        	}
        
        //****************************************
        boolean rebuildSubNodes = true;
        if(pparty_id.equals(vo.getPartyId()))
        {
            OrganizationVO voOld = findByPrimaryKey(pparty_id);
            if(vo.getParentPartyId() == null
                    || "-1".equals(vo.getParentPartyId())
                    || "".equals(vo.getParentPartyId()))
            {
                if(voOld.getParentPartyId() == null
                        || "-1".equals(voOld.getParentPartyId())
                        || "".equals(voOld.getParentPartyId()))
                {
                    vo.setPathCode(voOld.getPathCode());
                    rebuildSubNodes = false;
                }
            }
            else
            {
                if(vo.getParentPartyId().equals(voOld.getParentPartyId()))
                {
                    vo.setPathCode(voOld.getPathCode());
                    rebuildSubNodes = false;
                }
            }
        }
        if(rebuildSubNodes)
        {
            String pathCode = null;
            if(vo.getParentPartyId() == null
                    || "".equals(vo.getParentPartyId()))
            {
                pathCode = vo.getPartyId();
            }
            else
            {
                OrganizationVO voParent = findByPrimaryKey(vo
                        .getParentPartyId());
                if(voParent == null)
                {
                    throw new DAOSystemException("INVALID PARENT_PARTY_ID ["
                            + vo.getParentPartyId() + "]");
                }
                pathCode = voParent.getPathCode() + "." + vo.getPartyId();
            }
            vo.setPathCode(pathCode);
            return updateTreeFromNode(pparty_id, vo) > 0;
        }
        else
        {
            return updateNodeSelf(pparty_id, vo);
        }
    }

    /**
     * 删除组织，若存在子节点则不允许删除
     */
    public long delete(String pparty_id) throws DAOSystemException
    {
        if(getDirectSubNodes(pparty_id).size() > 0)
        {
            throw new DAOSystemException("UNABLE TO DELETE WITH SUBNODES");
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try
        {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));
            int index = 1;
            stmt.setString(index++, pparty_id);
            rows = stmt.executeUpdate();
            AgentPropertyDAO agentPropertyDAO = AgentPropertyDAOFactory.getAgentPropertyDAO();
            agentPropertyDAO.deleteByCond(" party_id = " + pparty_id);
        }
        catch (SQLException se)
        {
            Debug.print(SQL_DELETE, this);
            throw new DAOSystemException("SQLException while deleting sql:\n"
                    + SQL_DELETE, se);
        }
        finally
        {
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
        return rows;
    }

    /**
     * 更新某个组织节点自身
     * @param pparty_id 组织标识
     * @param vo 新值对象
     * @return
     * @throws DAOSystemException
     */
    private boolean updateNodeSelf(String pparty_id, OrganizationVO vo)
            throws DAOSystemException
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();
        try
        {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            sql
                    .append("UPDATE ORGANIZATION SET party_id = ?,parent_party_id = ?,org_code = ?,org_name = ?,org_level = ?,org_type_id = ?,org_content = ?,address_id = ?,state = ?,state_date = ?,path_code = ?,path_name = ?,org_class = ?,org_type");
            sql.append(" WHERE  party_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
                    .toString()));
            int index = 1;
            if("".equals(((OrganizationVO)vo).getPartyId()))
            {
                ((OrganizationVO)vo).setPartyId(null);
            }
            stmt.setString(index++, vo.getPartyId());
            if("".equals(((OrganizationVO)vo).getParentPartyId()))
            {
                ((OrganizationVO)vo).setParentPartyId(null);
            }
            stmt.setString(index++, vo.getParentPartyId());
            stmt.setString(index++, vo.getOrgCode());
            stmt.setString(index++, vo.getOrgName());
            if("".equals(((OrganizationVO)vo).getOrgLevel()))
            {
                ((OrganizationVO)vo).setOrgLevel(null);
            }
            stmt.setString(index++, vo.getOrgLevel());
            if("".equals(((OrganizationVO)vo).getOrgTypeId()))
            {
                ((OrganizationVO)vo).setOrgTypeId(null);
            }
            stmt.setString(index++, vo.getOrgTypeId());
            stmt.setString(index++, vo.getOrgContent());
            if("".equals(((OrganizationVO)vo).getAddrId()))
            {
                ((OrganizationVO)vo).setAddrId(null);
            }
            stmt.setString(index++, vo.getAddrId());
            stmt.setString(index++, vo.getState());
            stmt.setDate(index++, DAOUtils.parseDateTime(DateFormatUtils.getFormatedDateTime()));
            stmt.setString(index++, vo.getPathCode());
            stmt.setString(index++, vo.getPathName());
            stmt.setString(index++, vo.getOrgClass());
            stmt.setString(index++,vo.getOrgType());
            stmt.setString(index++, pparty_id);
            int rows = stmt.executeUpdate();
            if(rows > 0)
            {
                bResult = true;
            }
        }
        catch (SQLException se)
        {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n"
                    + sql.toString(), se);
        }
        finally
        {
            DAOUtils.closeStatement(stmt, this);
            //DAOUtils.closeConnection(conn, this);
        }
        return bResult;
    }

    /**
     * 从某个节点开始更新其本身和所有子孙节点的path_code
     * @param pparty_id 起始节点标识
     * @param vo
     * @return
     * @throws DAOSystemException
     */
    private int updateTreeFromNode(String pparty_id, OrganizationVO vo)
            throws DAOSystemException
    {
        int recsUpdated = 0;
        if(updateNodeSelf(pparty_id, vo))
        {
            recsUpdated += 1;
            Iterator iterSubNodes = getDirectSubNodes(pparty_id).iterator();
            OrganizationVO voNode;
            int recsUpdSub;
            while (iterSubNodes.hasNext())
            {
                voNode = (OrganizationVO)iterSubNodes.next();
                voNode.setParentPartyId(vo.getPartyId());
                voNode
                        .setPathCode(vo.getPathCode() + "."
                                + voNode.getPartyId());
                recsUpdSub = updateTreeFromNode(voNode.getPartyId(), voNode);
                recsUpdated = recsUpdated + recsUpdSub;
                if(recsUpdSub < 1)
                {
                    throw new DAOSystemException("UPDATE FAIL WITH SUBNODE["
                            + voNode.getPartyId() + "]");
                }
            }
        }

        return recsUpdated;
    }

}
