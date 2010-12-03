package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.StaffDAO;
import com.ztesoft.crm.salesres.vo.StaffVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class StaffDAOImpl implements StaffDAO {
    private String SQL_SELECT = "SELECT a.party_role_id,a.party_role_name,a.password,a.party_role_type,a.org_maneger,a.party_id,a.office_id,a.state,a.eff_date,a.exp_date,a.pwdvalidtype,a.update_time,a.incor_starttime,a.login_status,a.login_count,a.limit_count,a.menu_code,a.org_party_id,a.memo,a.create_date,a.s_party_id,b.staff_code as party_role_code FROM PARTY_ROLE a left join staff b on a.party_role_id = b.party_role_id ";

    /** 供来自重庆的物资订单流转功能调用 **/
    private String SQL_SELECT2 = "SELECT a.party_role_id,a.party_role_name,a.password,a.party_role_type,a.org_maneger,a.party_id,a.office_id,a.state,a.eff_date,a.exp_date,a.pwdvalidtype,a.update_time,a.incor_starttime,a.login_status,a.login_count,a.limit_count,a.menu_code,a.org_party_id,a.memo,a.create_date,a.s_party_id,b.staff_code as party_role_code FROM PARTY_ROLE a,staff b";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PARTY_ROLE a left join staff b on a.party_role_id = b.party_role_id ";

    /** 供来自重庆的物资订单流转功能调用 **/
    private String SQL_SELECT_COUNT2 = "SELECT COUNT(*) AS COL_COUNTS FROM PARTY_ROLE a,staff b";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO PARTY_ROLE ( party_role_id,party_role_name,password,party_role_type,org_maneger,party_id,office_id,state,eff_date,exp_date,pwdvalidtype,update_time,incor_starttime,login_status,login_count,limit_count,menu_code,org_party_id,memo,create_date,s_party_id ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE PARTY_ROLE SET  party_role_name = ?, password = ?, party_role_type = ?, org_maneger = ?, party_id = ?, office_id = ?, state = ?, eff_date = ?, exp_date = ?, pwdvalidtype = ?, update_time = ?, incor_starttime = ?, login_status = ?, login_count = ?, limit_count = ?, menu_code = ?, org_party_id = ?, memo = ?, create_date = ?, s_party_id = ? WHERE party_role_id = ? ";
    private String SQL_DELETE = "DELETE FROM PARTY_ROLE WHERE party_role_id = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM PARTY_ROLE ";

    /** 判断是否是来自重庆的物资订单流转功能的调用，如果设置为2，则使用标号为2的语句 * */
    private int flag = 1;

    public StaffDAOImpl() {
    }

    public StaffVO findByPrimaryKey(String pparty_role_id)
        throws DAOSystemException {
        String sql_select_use = SQL_SELECT;

        if (this.flag == 2) {
            sql_select_use = SQL_SELECT2;
        }

        ArrayList arrayList = findBySql(sql_select_use +
                " WHERE party_role_id = ? ", new String[] { pparty_role_id });

        if (arrayList.size() > 0) {
            return (StaffVO) arrayList.get(0);
        } else {
            return (StaffVO) getEmptyVO();
        }
    }

    public ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setMaxRows(maxRows);

            for (int i = 0; (sqlParams != null) && (i < sqlParams.length);
                    i++) {
                stmt.setString(i + 1, sqlParams[i]);
            }

            rs = stmt.executeQuery();

            return fetchMultiResults(rs);
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    protected ArrayList fetchMultiResults(ResultSet rs)
        throws SQLException {
        ArrayList resultList = new ArrayList();

        while (rs.next()) {
            StaffVO vo = new StaffVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(StaffVO vo, ResultSet rs)
        throws SQLException {
        if (flag == 3) {
            vo.setPartyRoleId(DAOUtils.trimStr(rs.getString("party_role_id")));
            vo.setPartyRoleName(DAOUtils.trimStr(rs.getString("party_role_name")));
            vo.setParty_role_code(DAOUtils.trimStr(rs.getString(
                        "party_role_code")));
        } else {
            vo.setPartyRoleId(DAOUtils.trimStr(rs.getString("party_role_id")));
            vo.setPartyRoleName(DAOUtils.trimStr(rs.getString("party_role_name")));
            vo.setPassword(DAOUtils.trimStr(rs.getString("password")));
            vo.setPartyRoleType(DAOUtils.trimStr(rs.getString("party_role_type")));
            vo.setOrgManeger(DAOUtils.trimStr(rs.getString("org_maneger")));
            vo.setPartyId(DAOUtils.trimStr(rs.getString("party_id")));
            vo.setOfficeId(DAOUtils.trimStr(rs.getString("office_id")));
            vo.setState(DAOUtils.trimStr(rs.getString("state")));
            vo.setEffDate(DAOUtils.getFormatedDateTime(rs.getDate("eff_date")));
            vo.setExpDate(DAOUtils.getFormatedDateTime(rs.getDate("exp_date")));
            vo.setPwdvalidtype(DAOUtils.trimStr(rs.getString("pwdvalidtype")));
            vo.setUpdateTime(DAOUtils.getFormatedDateTime(rs.getDate(
                        "update_time")));
            vo.setIncorStarttime(DAOUtils.getFormatedDateTime(rs.getDate(
                        "incor_starttime")));
            vo.setLoginStatus(DAOUtils.trimStr(rs.getString("login_status")));
            vo.setLoginCount(DAOUtils.trimStr(rs.getString("login_count")));
            vo.setLimitCount(DAOUtils.trimStr(rs.getString("limit_count")));
            vo.setMenuCode(DAOUtils.trimStr(rs.getString("menu_code")));
            vo.setOrgPartyId(DAOUtils.trimStr(rs.getString("org_party_id")));
            vo.setMemo(DAOUtils.trimStr(rs.getString("memo")));
            vo.setCreateDate(DAOUtils.getFormatedDateTime(rs.getDate(
                        "create_date")));
            vo.setSPartyId(DAOUtils.trimStr(rs.getString("s_party_id")));
            vo.setParty_role_code(DAOUtils.trimStr(rs.getString(
                        "party_role_code")));
        }
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        StaffVO vo = new StaffVO();

        try {
            populateDto(vo, rs);
        } catch (SQLException se) {
            Debug.print("populateCurrRecord出错", this);
            throw new DAOSystemException("SQLException while populateDto:\n", se);
        }

        return vo;
    }

    public List findByCond(String whereCond) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        String sql_select_use = SQL_SELECT;

        if (this.flag == 2) {
            sql_select_use = SQL_SELECT2;
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = sql_select_use + " WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setMaxRows(maxRows);
            rs = stmt.executeQuery();

            return fetchMultiResults(rs);
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public void insert(VO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));

            int index = 1;

            if ("".equals(((StaffVO) vo).getPartyRoleId())) {
                ((StaffVO) vo).setPartyRoleId(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getPartyRoleId());
            stmt.setString(index++, ((StaffVO) vo).getPartyRoleName());
            stmt.setString(index++, ((StaffVO) vo).getPassword());
            stmt.setString(index++, ((StaffVO) vo).getPartyRoleType());
            stmt.setString(index++, ((StaffVO) vo).getOrgManeger());

            if ("".equals(((StaffVO) vo).getPartyId())) {
                ((StaffVO) vo).setPartyId(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getPartyId());

            if ("".equals(((StaffVO) vo).getOfficeId())) {
                ((StaffVO) vo).setOfficeId(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getOfficeId());
            stmt.setString(index++, ((StaffVO) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((StaffVO) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((StaffVO) vo).getExpDate()));
            stmt.setString(index++, ((StaffVO) vo).getPwdvalidtype());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((StaffVO) vo).getUpdateTime()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((StaffVO) vo).getIncorStarttime()));
            stmt.setString(index++, ((StaffVO) vo).getLoginStatus());

            if ("".equals(((StaffVO) vo).getLoginCount())) {
                ((StaffVO) vo).setLoginCount(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getLoginCount());

            if ("".equals(((StaffVO) vo).getLimitCount())) {
                ((StaffVO) vo).setLimitCount(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getLimitCount());
            stmt.setString(index++, ((StaffVO) vo).getMenuCode());

            if ("".equals(((StaffVO) vo).getOrgPartyId())) {
                ((StaffVO) vo).setOrgPartyId(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getOrgPartyId());
            stmt.setString(index++, ((StaffVO) vo).getMemo());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((StaffVO) vo).getCreateDate()));

            if ("".equals(((StaffVO) vo).getSPartyId())) {
                ((StaffVO) vo).setSPartyId(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getSPartyId());

            int rows = stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(SQL_INSERT, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                SQL_INSERT, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public boolean update(String pparty_role_id, StaffVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE PARTY_ROLE SET party_role_id = ?,party_role_name = ?,password = ?,party_role_type = ?,org_maneger = ?,party_id = ?,office_id = ?,state = ?,eff_date = ?,exp_date = ?,pwdvalidtype = ?,update_time = ?,incor_starttime = ?,login_status = ?,login_count = ?,limit_count = ?,menu_code = ?,org_party_id = ?,memo = ?,create_date = ?,s_party_id = ?,org_manager = ?");
            sql.append(" WHERE  party_role_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((StaffVO) vo).getPartyRoleId())) {
                ((StaffVO) vo).setPartyRoleId(null);
            }

            stmt.setString(index++, vo.getPartyRoleId());
            stmt.setString(index++, vo.getPartyRoleName());
            stmt.setString(index++, vo.getPassword());
            stmt.setString(index++, vo.getPartyRoleType());
            stmt.setString(index++, vo.getOrgManeger());

            if ("".equals(((StaffVO) vo).getPartyId())) {
                ((StaffVO) vo).setPartyId(null);
            }

            stmt.setString(index++, vo.getPartyId());

            if ("".equals(((StaffVO) vo).getOfficeId())) {
                ((StaffVO) vo).setOfficeId(null);
            }

            stmt.setString(index++, vo.getOfficeId());
            stmt.setString(index++, vo.getState());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getEffDate()));
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getExpDate()));
            stmt.setString(index++, vo.getPwdvalidtype());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getUpdateTime()));
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getIncorStarttime()));
            stmt.setString(index++, vo.getLoginStatus());

            if ("".equals(((StaffVO) vo).getLoginCount())) {
                ((StaffVO) vo).setLoginCount(null);
            }

            stmt.setString(index++, vo.getLoginCount());

            if ("".equals(((StaffVO) vo).getLimitCount())) {
                ((StaffVO) vo).setLimitCount(null);
            }

            stmt.setString(index++, vo.getLimitCount());
            stmt.setString(index++, vo.getMenuCode());

            if ("".equals(((StaffVO) vo).getOrgPartyId())) {
                ((StaffVO) vo).setOrgPartyId(null);
            }

            stmt.setString(index++, vo.getOrgPartyId());
            stmt.setString(index++, vo.getMemo());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getCreateDate()));

            if ("".equals(((StaffVO) vo).getSPartyId())) {
                ((StaffVO) vo).setSPartyId(null);
            }

            stmt.setString(index++, vo.getSPartyId());

            stmt.setString(index++, pparty_role_id);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                bResult = true;
            }
        } catch (SQLException se) {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n" +
                sql.toString(), se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return bResult;
    }

    public boolean update(String whereCond, VO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE PARTY_ROLE SET party_role_id = ?,party_role_name = ?,password = ?,party_role_type = ?,org_maneger = ?,party_id = ?,office_id = ?,state = ?,eff_date = ?,exp_date = ?,pwdvalidtype = ?,update_time = ?,incor_starttime = ?,login_status = ?,login_count = ?,limit_count = ?,menu_code = ?,org_party_id = ?,memo = ?,create_date = ?,s_party_id = ?,org_manager = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((StaffVO) vo).getPartyRoleId())) {
                ((StaffVO) vo).setPartyRoleId(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getPartyRoleId());
            stmt.setString(index++, ((StaffVO) vo).getPartyRoleName());
            stmt.setString(index++, ((StaffVO) vo).getPassword());
            stmt.setString(index++, ((StaffVO) vo).getPartyRoleType());
            stmt.setString(index++, ((StaffVO) vo).getOrgManeger());

            if ("".equals(((StaffVO) vo).getPartyId())) {
                ((StaffVO) vo).setPartyId(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getPartyId());

            if ("".equals(((StaffVO) vo).getOfficeId())) {
                ((StaffVO) vo).setOfficeId(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getOfficeId());
            stmt.setString(index++, ((StaffVO) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((StaffVO) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((StaffVO) vo).getExpDate()));
            stmt.setString(index++, ((StaffVO) vo).getPwdvalidtype());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((StaffVO) vo).getUpdateTime()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((StaffVO) vo).getIncorStarttime()));
            stmt.setString(index++, ((StaffVO) vo).getLoginStatus());

            if ("".equals(((StaffVO) vo).getLoginCount())) {
                ((StaffVO) vo).setLoginCount(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getLoginCount());

            if ("".equals(((StaffVO) vo).getLimitCount())) {
                ((StaffVO) vo).setLimitCount(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getLimitCount());
            stmt.setString(index++, ((StaffVO) vo).getMenuCode());

            if ("".equals(((StaffVO) vo).getOrgPartyId())) {
                ((StaffVO) vo).setOrgPartyId(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getOrgPartyId());
            stmt.setString(index++, ((StaffVO) vo).getMemo());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((StaffVO) vo).getCreateDate()));

            if ("".equals(((StaffVO) vo).getSPartyId())) {
                ((StaffVO) vo).setSPartyId(null);
            }

            stmt.setString(index++, ((StaffVO) vo).getSPartyId());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                bResult = true;
            }
        } catch (SQLException se) {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n" +
                sql.toString(), se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return bResult;
    }

    public long countByCond(String whereCond) throws DAOSystemException {
        Connection conn = null;
        long lCount = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        String sql_select_count_use = SQL_SELECT_COUNT;

        if ((this.flag == 2) || (this.flag == 3)) {
            sql_select_count_use = SQL_SELECT_COUNT2;
        }

        try {
            int orderbyIndex = whereCond.toUpperCase().lastIndexOf("ORDER BY");

            if (orderbyIndex > 0) {
                whereCond = whereCond.substring(0, orderbyIndex);
            }

            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = sql_select_count_use + " WHERE " + whereCond;

            stmt = conn.prepareStatement(SQL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                lCount = rs.getLong("COL_COUNTS");
            }
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return lCount;
    }

    public long deleteByCond(String whereCond) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = SQL_DELETE_BY_WHERE + " WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            rows = stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while deleting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return rows;
    }

    public long delete(String pparty_role_id) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pparty_role_id);
            rows = stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(SQL_DELETE, this);
            throw new DAOSystemException("SQLException while deleting sql:\n" +
                SQL_DELETE, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return rows;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public int getFlag() {
        return flag;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public VO getEmptyVO() {
        return new StaffVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql_select_use = SQL_SELECT;

        if ((this.flag == 2) || (this.flag == 3)) {
            sql_select_use = SQL_SELECT2;
        }

        String SQL = sql_select_use + " WHERE " + whereCond;
        String filterSQL = SQL;

        if (queryFilter != null) {
            filterSQL = queryFilter.doPreFilter(SQL);
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = filterSQL;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();

            List retList = null;

            if (queryFilter != null) {
                retList = queryFilter.doPostFilter(rs, this);
            } else {
                retList = fetchMultiResults(rs);
            }

            return retList;
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * 电子发票特有。
     */
    public String getOrgPathCode(String sql) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String pathCode = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            rs = stmt.executeQuery();

            if (rs.next()) {
                pathCode = rs.getString(1);
            }

            return pathCode;
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public void setQueryFlag(int flag, String sql, String cont) {
        this.flag = flag;
        this.SQL_SELECT = sql;
        this.SQL_SELECT_COUNT = cont;
    }

    public boolean isFinacialStaff(String operId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select 'x' from RC_EN_PRIVILEGE where party_role_id= ? and MANAGE_ROLE ='IA1' ";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));

            stmt.setString(1, operId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public boolean update(String pparty_role_id, com.ztesoft.oaas.vo.StaffVO vo)
        throws DAOSystemException {
        // TODO Auto-generated method stub
        return false;
    }
}
