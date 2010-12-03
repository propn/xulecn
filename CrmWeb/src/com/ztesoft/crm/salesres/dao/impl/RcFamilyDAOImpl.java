package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcFamilyDAO;
import com.ztesoft.crm.salesres.vo.RcAttrDefVO;
import com.ztesoft.crm.salesres.vo.RcFamilyVO;
import com.ztesoft.crm.salesres.vo.SalesRescAttrVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcFamilyDAOImpl implements RcFamilyDAO {
    private String SQL_SELECT = "SELECT family_id,family_name,comments,code_min_len,code_max_len,resource_type,state,state_date,create_date,eff_date,exp_date FROM RC_FAMILY";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_FAMILY";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_FAMILY ( family_id,family_name,comments,code_min_len,code_max_len,resource_type,state,state_date,create_date,eff_date,exp_date ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?)";
    private String SQL_UPDATE = "UPDATE RC_FAMILY SET  family_id = ?, family_name = ?, comments = ?, code_min_len = ?, code_max_len = ?, resource_type = ?, state = ?, state_date = ?, create_date = ?, eff_date = ?, exp_date = ?,family_type = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_FAMILY WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_FAMILY ";
    private int att_flag = 0;

    public RcFamilyDAOImpl() {
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
            RcFamilyVO vo = new RcFamilyVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcFamilyVO vo, ResultSet rs)
        throws SQLException {
        vo.setFamilyId(DAOUtils.trimStr(rs.getString("family_id")));
        vo.setFamilyName(DAOUtils.trimStr(rs.getString("family_name")));
        vo.setComments(DAOUtils.trimStr(rs.getString("comments")));
        vo.setCodeMinLen(DAOUtils.trimStr(rs.getString("code_min_len")));
        vo.setCodeMaxLen(DAOUtils.trimStr(rs.getString("code_max_len")));
        vo.setRescType(DAOUtils.trimStr(rs.getString("resource_type")));
        vo.setState(DAOUtils.trimStr(rs.getString("state")));
        vo.setStateDate(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "state_date")));
        vo.setCreateDate(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "create_date")));
        vo.setEffDate(DAOUtils.getFormatedDateTime(rs.getTimestamp("eff_date")));
        vo.setExpDate(DAOUtils.getFormatedDateTime(rs.getTimestamp("exp_date")));

        //    vo.setFamilyType(DAOUtils.trimStr(rs.getString("family_type")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcFamilyVO vo = new RcFamilyVO();

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

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = SQL_SELECT + " WHERE " + whereCond;
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

            if ("".equals(((RcFamilyVO) vo).getFamilyId())) {
                ((RcFamilyVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, ((RcFamilyVO) vo).getFamilyId());
            stmt.setString(index++, ((RcFamilyVO) vo).getFamilyName());
            stmt.setString(index++, ((RcFamilyVO) vo).getComments());

            if ("".equals(((RcFamilyVO) vo).getCodeMinLen())) {
                ((RcFamilyVO) vo).setCodeMinLen(null);
            }

            stmt.setString(index++, ((RcFamilyVO) vo).getCodeMinLen());

            if ("".equals(((RcFamilyVO) vo).getCodeMaxLen())) {
                ((RcFamilyVO) vo).setCodeMaxLen(null);
            }

            stmt.setString(index++, ((RcFamilyVO) vo).getCodeMaxLen());
            stmt.setString(index++, ((RcFamilyVO) vo).getRescType());
            stmt.setString(index++, ((RcFamilyVO) vo).getState());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcFamilyVO) vo).getStateDate()));
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcFamilyVO) vo).getCreateDate()));
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcFamilyVO) vo).getEffDate()));
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcFamilyVO) vo).getExpDate()));

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

    public boolean update(String whereCond, VO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_FAMILY SET family_id = ?,family_name = ?,comments = ?,code_min_len = ?,code_max_len = ?,resource_type = ?,state = ?,state_date = ?,create_date = ?,eff_date = ?,exp_date = ?");
            sql.append(" WHERE 1=1 " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcFamilyVO) vo).getFamilyId())) {
                ((RcFamilyVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, ((RcFamilyVO) vo).getFamilyId());
            stmt.setString(index++, ((RcFamilyVO) vo).getFamilyName());
            stmt.setString(index++, ((RcFamilyVO) vo).getComments());

            if ("".equals(((RcFamilyVO) vo).getCodeMinLen())) {
                ((RcFamilyVO) vo).setCodeMinLen(null);
            }

            stmt.setString(index++, ((RcFamilyVO) vo).getCodeMinLen());

            if ("".equals(((RcFamilyVO) vo).getCodeMaxLen())) {
                ((RcFamilyVO) vo).setCodeMaxLen(null);
            }

            stmt.setString(index++, ((RcFamilyVO) vo).getCodeMaxLen());
            stmt.setString(index++, ((RcFamilyVO) vo).getRescType());
            stmt.setString(index++, ((RcFamilyVO) vo).getState());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcFamilyVO) vo).getStateDate()));
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcFamilyVO) vo).getCreateDate()));
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcFamilyVO) vo).getEffDate()));
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcFamilyVO) vo).getExpDate()));

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

        try {
            int orderbyIndex = whereCond.toUpperCase().lastIndexOf("ORDER BY");

            if (orderbyIndex > 0) {
                whereCond = whereCond.substring(0, orderbyIndex);
            }

            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = SQL_SELECT_COUNT + " WHERE 1=1 " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
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
            // 删除属性
            deleteAttrByCond(whereCond);
            // 删除家族
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = SQL_DELETE_BY_WHERE + " WHERE 1=1 " + whereCond;
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

    public void deleteAttrByCond(String whereCond) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "delete from rc_attr_def " + " WHERE 1=1 " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while deleting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public VO getEmptyVO() {
        return new RcFamilyVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + " WHERE 1=1 " + whereCond;
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

    public boolean checkFamilyState(String familyId) throws DAOSystemException {
        boolean retv = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = " select count(*) as row_count from SALES_RESOURCE a where a.family_id=" +
                familyId;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            rs = stmt.executeQuery();

            if (rs.next()) {
                int rowcount = Integer.parseInt(rs.getString("row_count"));

                if (rowcount > 0) {
                    retv = true;
                }
            }
        } catch (Exception se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return retv;
    }

    public ArrayList searchAttrInfo(String familyId) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList retList = new ArrayList();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "select a.ATTR_OWNER_TYPE, a.attr_id, b.attr_name from rc_attr_def a, attribute b where a.family_id = ? and a.attr_id =  b.attr_id ";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, familyId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                RcAttrDefVO vo = new RcAttrDefVO();
                vo.setAttrOwnerType(DAOUtils.trimStr(rs.getString(
                            "ATTR_OWNER_TYPE")));
                vo.setAttrId(DAOUtils.trimStr(rs.getString("attr_id")));
                vo.setAttrName(DAOUtils.trimStr(rs.getString("attr_name")));
                retList.add(vo);
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

    public boolean insertAttrInfo(String attrId, String attrOwnerType,
        String familyId) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = null;
        boolean flag = false;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql = " insert into RC_ATTR_DEF values(?,?,?,'')";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));

            if ("".equals(familyId)) {
                familyId = null;
            }

            stmt.setString(1, familyId);

            if ("".equals(attrId)) {
                attrId = null;
            }

            stmt.setString(2, attrId);

            if ("".equals(attrOwnerType)) {
                attrOwnerType = null;
            }

            stmt.setString(3, attrOwnerType);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                flag = true;
            }
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return flag;
    }

    public long countAttrInfo(String attrId, String familyId)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = null;
        ResultSet rs = null;
        long lCount = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql = " SELECT COUNT(*) AS COL_COUNTS from RC_ATTR_DEF where family_id = ? and attr_id = ?";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setString(1, familyId);
            stmt.setString(2, attrId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                lCount = rs.getLong("COL_COUNTS");
            }
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return lCount;
    }

    public int deleteAttrInfo(String rcFamilyId, String attrId,
        String attrOwnerType) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String SQL = "";
        long lCount = 0;
        lCount = countAttr(attrId, rcFamilyId, attrOwnerType);

        if (lCount != 0) {
            return -1;
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = " delete from RC_ATTR_DEF where family_id = ? and attr_id = ? ";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setString(1, rcFamilyId);
            stmt.setString(2, attrId);
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

    public ArrayList findAttrInfo(String familyId, String attrOwnerType)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList retList = new ArrayList();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            SQL = "select b.attr_name,a.attr_id,b.input_method from rc_attr_def a, attribute b where  b.attr_id = a.attr_id and a.family_id = ? and a.attr_owner_type = ? ";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            if ("1".equals(attrOwnerType) && (att_flag == 0)) {
                familyId = findFamilyId(familyId);
            }

            int i = 1;

            if ((familyId != null) && !"".equals(familyId) &&
                    (attrOwnerType != null) && !"".equals(attrOwnerType)) {
                stmt.setString(i++, familyId);
                stmt.setString(i++, attrOwnerType);
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                RcAttrDefVO vo = new RcAttrDefVO();
                vo.setAttrId(DAOUtils.trimStr(rs.getString("attr_id")));
                vo.setAttrName(DAOUtils.trimStr(rs.getString("attr_name")));
                vo.setInputMethod(DAOUtils.trimStr(rs.getString("input_method")));
                retList.add(vo);
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

    public ArrayList findAttrInfo(String familyId, String attrOwnerType,
        String salesRescId) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList retList = new ArrayList();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            SQL = "select b.attr_name,a.attr_id,b.input_method" +
                " ,(select e.attr_value from sales_resource d, sales_resource_attr e where d.sales_resource_id = e.sales_resource_id" +
                " and d.family_id = c.family_id and e.attr_id = a.attr_id and d.sales_resource_id =?) as attr_value" +
                " from rc_attr_def a, attribute b , rc_family c where  b.attr_id = a.attr_id and a.family_id = c.family_id and a.family_id = ? and a.attr_owner_type = ?";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            if ("1".equals(attrOwnerType) && (att_flag == 0)) {
                familyId = findFamilyId(familyId);
            }

            int i = 1;

            if ((familyId != null) && !"".equals(familyId) &&
                    (attrOwnerType != null) && !"".equals(attrOwnerType) &&
                    (salesRescId != null) && !"".equals(salesRescId)) {
                stmt.setString(i++, salesRescId);
                stmt.setString(i++, familyId);
                stmt.setString(i++, attrOwnerType);
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                SalesRescAttrVO vo = new SalesRescAttrVO();
                vo.setAttrId(DAOUtils.trimStr(rs.getString("attr_id")));
                vo.setAttrName(DAOUtils.trimStr(rs.getString("attr_name")));
                vo.setInputMethod(DAOUtils.trimStr(rs.getString("input_method")));
                vo.setAttrValue(rs.getString("attr_value"));
                retList.add(vo);
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

    // 查询family_id
    public String findFamilyId(String familyId) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        String sRs = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = " select family_id from SALES_RESOURCE where sales_resource_id = ? ";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, familyId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                sRs = DAOUtils.trimStr(rs.getString("family_id"));
            }

            return sRs;
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

    //查询属性对应的取值列表
    public ArrayList findAttrValue(String attrId) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList retList = new ArrayList();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            SQL = "select attr_value from attribute_value where attr_id =  ?  ";

            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, attrId);

            rs = stmt.executeQuery();

            while (rs.next()) {
                RcAttrDefVO vo = new RcAttrDefVO();
                vo.setAttrValue(DAOUtils.trimStr(rs.getString("attr_value")));

                retList.add(vo);
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

    public long countAttr(String attrId, String familyId, String attrOwnerType)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = null;
        ResultSet rs = null;
        long lCount = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            if ("0".equals(attrOwnerType)) {
                sql = "select COUNT(*) AS COL_COUNTS from SALES_RESOURCE_ATTR a , SALES_RESOURCE b where b.family_id = ? and a.attr_id = ? and a.sales_resource_id = b.sales_resource_id  ";
            } else if ("1".equals(attrOwnerType)) {
                sql = " select  COUNT(*) AS COL_COUNTS from RC_ENTITY a , RC_ENTITY_ATTR b,sales_resource c where c.family_id = ? and a.sales_resource_id = c.sales_resource_id  and b.attr_id = ? and b.entity_id = a.resource_instance_id";
            } else {
                return 0;
            }

            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setString(1, familyId);
            stmt.setString(2, attrId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                lCount = rs.getLong("COL_COUNTS");
            }
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return lCount;
    }

    public void setAtt_flag(int att_flag) {
        this.att_flag = att_flag;
    }

    public ArrayList findAttrInfo2(String familyId, String attrOwnerType)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList retList = new ArrayList();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            SQL = "select b.attr_code as attr_name,a.attr_id,b.input_method from rc_attr_def a, attribute b where  b.attr_id = a.attr_id and a.family_id = ? and a.attr_owner_type = ? ";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            if ("1".equals(attrOwnerType) && (att_flag == 0)) {
                familyId = findFamilyId(familyId);
            }

            int i = 1;

            if ((familyId != null) && !"".equals(familyId) &&
                    (attrOwnerType != null) && !"".equals(attrOwnerType)) {
                stmt.setString(i++, familyId);
                stmt.setString(i++, attrOwnerType);
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                RcAttrDefVO vo = new RcAttrDefVO();
                vo.setAttrId(DAOUtils.trimStr(rs.getString("attr_id")));
                vo.setAttrName(DAOUtils.trimStr(rs.getString("attr_name")));
                vo.setInputMethod(DAOUtils.trimStr(rs.getString("input_method")));
                retList.add(vo);
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
}
