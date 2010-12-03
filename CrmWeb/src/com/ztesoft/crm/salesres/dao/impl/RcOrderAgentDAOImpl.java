package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcOrderAgentDAO;
import com.ztesoft.crm.salesres.vo.RcOrderAgentVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcOrderAgentDAOImpl implements RcOrderAgentDAO {
    private String SQL_SELECT = "SELECT ORDER_ID,APP_ID,DEPART_ID,DEPART_NAME,ACCEPT_ID,ACCEPT_TYPE,RESOURCE_PRICE,DISCOUNT,REAL_PRICE FROM RC_ORDER_AGENT";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_ORDER_AGENT";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_ORDER_AGENT ( ORDER_ID,APP_ID,DEPART_ID,DEPART_NAME,ACCEPT_ID,ACCEPT_TYPE,RESOURCE_PRICE,DISCOUNT,REAL_PRICE ) VALUES ( ?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_ORDER_AGENT SET  ORDER_ID = ?, APP_ID = ?, DEPART_ID = ?, DEPART_NAME = ?, ACCEPT_ID = ?, ACCEPT_TYPE = ?, RESOURCE_PRICE = ?, DISCOUNT = ?, REAL_PRICE = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_ORDER_AGENT WHERE APP_ID = ? AND ORDER_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_ORDER_AGENT ";

    public RcOrderAgentDAOImpl() {
    }

    public RcOrderAgentVO findByPrimaryKey(String pAPP_ID, String pORDER_ID)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE APP_ID = ? AND ORDER_ID = ? ",
                new String[] { pAPP_ID, pORDER_ID });

        if (arrayList.size() > 0) {
            return (RcOrderAgentVO) arrayList.get(0);
        } else {
            return (RcOrderAgentVO) getEmptyVO();
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
            RcOrderAgentVO vo = new RcOrderAgentVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcOrderAgentVO vo, ResultSet rs)
        throws SQLException {
        vo.setOrderId(DAOUtils.trimStr(rs.getString("ORDER_ID")));
        vo.setAppId(DAOUtils.trimStr(rs.getString("APP_ID")));
        vo.setDepartId(DAOUtils.trimStr(rs.getString("DEPART_ID")));
        vo.setDepartName(DAOUtils.trimStr(rs.getString("DEPART_NAME")));
        vo.setAcceptId(DAOUtils.trimStr(rs.getString("ACCEPT_ID")));
        vo.setAcceptType(DAOUtils.trimStr(rs.getString("ACCEPT_TYPE")));
        vo.setRescPrice(DAOUtils.trimStr(rs.getString("RESOURCE_PRICE")));
        vo.setDiscount(DAOUtils.trimStr(rs.getString("DISCOUNT")));
        vo.setRealPrice(DAOUtils.trimStr(rs.getString("REAL_PRICE")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcOrderAgentVO vo = new RcOrderAgentVO();

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

            if ((whereCond != null) && (whereCond.trim().length() > 0)) {
                whereCond = " WHERE " + whereCond;
            } else {
                whereCond = "";
            }

            SQL = SQL_SELECT + whereCond;
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

            if ("".equals(((RcOrderAgentVO) vo).getOrderId())) {
                ((RcOrderAgentVO) vo).setOrderId(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getOrderId());

            if ("".equals(((RcOrderAgentVO) vo).getAppId())) {
                ((RcOrderAgentVO) vo).setAppId(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getAppId());

            if ("".equals(((RcOrderAgentVO) vo).getDepartId())) {
                ((RcOrderAgentVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getDepartId());
            stmt.setString(index++, ((RcOrderAgentVO) vo).getDepartName());
            stmt.setString(index++, ((RcOrderAgentVO) vo).getAcceptId());
            stmt.setString(index++, ((RcOrderAgentVO) vo).getAcceptType());

            if ("".equals(((RcOrderAgentVO) vo).getRescPrice())) {
                ((RcOrderAgentVO) vo).setRescPrice(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getRescPrice());

            if ("".equals(((RcOrderAgentVO) vo).getDiscount())) {
                ((RcOrderAgentVO) vo).setDiscount(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getDiscount());

            if ("".equals(((RcOrderAgentVO) vo).getRealPrice())) {
                ((RcOrderAgentVO) vo).setRealPrice(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getRealPrice());

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

    public boolean update(String pAPP_ID, String pORDER_ID, RcOrderAgentVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_ORDER_AGENT SET ORDER_ID = ?,APP_ID = ?,DEPART_ID = ?,DEPART_NAME = ?,ACCEPT_ID = ?,ACCEPT_TYPE = ?,RESOURCE_PRICE = ?,DISCOUNT = ?,REAL_PRICE = ?");
            sql.append(" WHERE  APP_ID = ? AND ORDER_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcOrderAgentVO) vo).getOrderId())) {
                ((RcOrderAgentVO) vo).setOrderId(null);
            }

            stmt.setString(index++, vo.getOrderId());

            if ("".equals(((RcOrderAgentVO) vo).getAppId())) {
                ((RcOrderAgentVO) vo).setAppId(null);
            }

            stmt.setString(index++, vo.getAppId());

            if ("".equals(((RcOrderAgentVO) vo).getDepartId())) {
                ((RcOrderAgentVO) vo).setDepartId(null);
            }

            stmt.setString(index++, vo.getDepartId());
            stmt.setString(index++, vo.getDepartName());
            stmt.setString(index++, vo.getAcceptId());
            stmt.setString(index++, vo.getAcceptType());

            if ("".equals(((RcOrderAgentVO) vo).getRescPrice())) {
                ((RcOrderAgentVO) vo).setRescPrice(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getRescPrice());

            if ("".equals(((RcOrderAgentVO) vo).getDiscount())) {
                ((RcOrderAgentVO) vo).setDiscount(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getDiscount());

            if ("".equals(((RcOrderAgentVO) vo).getRealPrice())) {
                ((RcOrderAgentVO) vo).setRealPrice(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getRealPrice());

            stmt.setString(index++, pAPP_ID);
            stmt.setString(index++, pORDER_ID);

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
                "UPDATE RC_ORDER_AGENT SET ORDER_ID = ?,APP_ID = ?,DEPART_ID = ?,DEPART_NAME = ?,ACCEPT_ID = ?,ACCEPT_TYPE = ?,RESOURCE_PRICE = ?,DISCOUNT = ?,REAL_PRICE = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcOrderAgentVO) vo).getOrderId())) {
                ((RcOrderAgentVO) vo).setOrderId(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getOrderId());

            if ("".equals(((RcOrderAgentVO) vo).getAppId())) {
                ((RcOrderAgentVO) vo).setAppId(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getAppId());

            if ("".equals(((RcOrderAgentVO) vo).getDepartId())) {
                ((RcOrderAgentVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getDepartId());
            stmt.setString(index++, ((RcOrderAgentVO) vo).getDepartName());
            stmt.setString(index++, ((RcOrderAgentVO) vo).getAcceptId());
            stmt.setString(index++, ((RcOrderAgentVO) vo).getAcceptType());

            if ("".equals(((RcOrderAgentVO) vo).getRescPrice())) {
                ((RcOrderAgentVO) vo).setRescPrice(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getRescPrice());

            if ("".equals(((RcOrderAgentVO) vo).getDiscount())) {
                ((RcOrderAgentVO) vo).setDiscount(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getDiscount());

            if ("".equals(((RcOrderAgentVO) vo).getRealPrice())) {
                ((RcOrderAgentVO) vo).setRealPrice(null);
            }

            stmt.setString(index++, ((RcOrderAgentVO) vo).getRealPrice());

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

            if ((whereCond != null) && (whereCond.trim().length() > 0)) {
                whereCond = " WHERE " + whereCond;
            } else {
                whereCond = "";
            }

            SQL = SQL_SELECT_COUNT + whereCond;
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

    public long delete(String pAPP_ID, String pORDER_ID)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pAPP_ID);
            stmt.setString(index++, pORDER_ID);
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

    public String getSQL_SELECT() {
        return SQL_SELECT;
    }

    public String getSQL_SELECT_COUNT() {
        return SQL_SELECT_COUNT;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public void setSQL_SELECT(String SQL_SELECT) {
        this.SQL_SELECT = SQL_SELECT;
    }

    public void setSQL_SELECT_COUNT(String SQL_SELECT_COUNT) {
        this.SQL_SELECT_COUNT = SQL_SELECT_COUNT;
    }

    public VO getEmptyVO() {
        return new RcOrderAgentVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if ((whereCond != null) && (whereCond.trim().length() > 0)) {
            whereCond = " WHERE " + whereCond;
        } else {
            whereCond = "";
        }

        String SQL = SQL_SELECT + whereCond;
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
     * 更新押金的押金编号
     * @param whereCond String
     * @param acceptId String
     * @throws DAOSystemException
     * @return boolean
     */
    public boolean updateAcceptId(String whereCond, String acceptId)
        throws DAOSystemException {
        if ((whereCond == null) || (whereCond.trim().length() < 1) ||
                (acceptId == null) || (acceptId.trim().length() < 1)) {
            return false;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append("UPDATE RC_ORDER_AGENT SET ACCEPT_ID = ? ");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            stmt.setString(1, acceptId);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                bResult = true;
            }
        } catch (SQLException se) {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException(
                "SQLException while updateAcceptId sql:\n" + sql.toString() +
                "||acceptId:" + acceptId, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return bResult;
    }
}
