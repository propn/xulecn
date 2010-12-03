package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcOrderExcDAO;
import com.ztesoft.crm.salesres.vo.RcOrderExcVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcOrderExcDAOImpl implements RcOrderExcDAO {
    private String SQL_SELECT = "SELECT log_id,order_id,tache_id,exc_time,depart_id,oper_id,state_id,exc_comments FROM RC_ORDER_EXC";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_ORDER_EXC";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_ORDER_EXC ( log_id,order_id,tache_id,exc_time,depart_id,oper_id,state_id,exc_comments ) VALUES ( ?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_ORDER_EXC SET  log_id = ?, order_id = ?, tache_id = ?, exc_time = ?, depart_id = ?, oper_id = ?, state_id = ?, exc_comments = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_ORDER_EXC WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_ORDER_EXC ";

    public RcOrderExcDAOImpl() {
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
            RcOrderExcVO vo = new RcOrderExcVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcOrderExcVO vo, ResultSet rs)
        throws SQLException {
        vo.setLogId(DAOUtils.trimStr(rs.getString("log_id")));
        vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
        vo.setTacheId(DAOUtils.trimStr(rs.getString("tache_id")));
        vo.setExcTime(DAOUtils.getFormatedDateTime(rs.getTimestamp("exc_time")));
        vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
        vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
        vo.setStateId(DAOUtils.trimStr(rs.getString("state_id")));
        vo.setExcComments(DAOUtils.trimStr(rs.getString("exc_comments")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcOrderExcVO vo = new RcOrderExcVO();

        try {
            populateDto(vo, rs);
        } catch (SQLException se) {
            Debug.print("populateCurrRecord����", this);
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

            if ("".equals(((RcOrderExcVO) vo).getLogId())) {
                ((RcOrderExcVO) vo).setLogId(null);
            }

            stmt.setString(index++, ((RcOrderExcVO) vo).getLogId());

            if ("".equals(((RcOrderExcVO) vo).getOrderId())) {
                ((RcOrderExcVO) vo).setOrderId(null);
            }

            stmt.setString(index++, ((RcOrderExcVO) vo).getOrderId());

            if ("".equals(((RcOrderExcVO) vo).getTacheId())) {
                ((RcOrderExcVO) vo).setTacheId(null);
            }

            stmt.setString(index++, ((RcOrderExcVO) vo).getTacheId());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcOrderExcVO) vo).getExcTime()));

            if ("".equals(((RcOrderExcVO) vo).getDepartId())) {
                ((RcOrderExcVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcOrderExcVO) vo).getDepartId());

            if ("".equals(((RcOrderExcVO) vo).getOperId())) {
                ((RcOrderExcVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcOrderExcVO) vo).getOperId());
            stmt.setString(index++, ((RcOrderExcVO) vo).getStateId());
            stmt.setString(index++, ((RcOrderExcVO) vo).getExcComments());

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
                "UPDATE RC_ORDER_EXC SET log_id = ?,order_id = ?,tache_id = ?,exc_time = ?,depart_id = ?,oper_id = ?,state_id = ?,exc_comments = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcOrderExcVO) vo).getLogId())) {
                ((RcOrderExcVO) vo).setLogId(null);
            }

            stmt.setString(index++, ((RcOrderExcVO) vo).getLogId());

            if ("".equals(((RcOrderExcVO) vo).getOrderId())) {
                ((RcOrderExcVO) vo).setOrderId(null);
            }

            stmt.setString(index++, ((RcOrderExcVO) vo).getOrderId());

            if ("".equals(((RcOrderExcVO) vo).getTacheId())) {
                ((RcOrderExcVO) vo).setTacheId(null);
            }

            stmt.setString(index++, ((RcOrderExcVO) vo).getTacheId());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcOrderExcVO) vo).getExcTime()));

            if ("".equals(((RcOrderExcVO) vo).getDepartId())) {
                ((RcOrderExcVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcOrderExcVO) vo).getDepartId());

            if ("".equals(((RcOrderExcVO) vo).getOperId())) {
                ((RcOrderExcVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcOrderExcVO) vo).getOperId());
            stmt.setString(index++, ((RcOrderExcVO) vo).getStateId());
            stmt.setString(index++, ((RcOrderExcVO) vo).getExcComments());

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
            SQL = SQL_SELECT_COUNT + " WHERE " + whereCond;
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

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public VO getEmptyVO() {
        return new RcOrderExcVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + " WHERE " + whereCond;
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
}