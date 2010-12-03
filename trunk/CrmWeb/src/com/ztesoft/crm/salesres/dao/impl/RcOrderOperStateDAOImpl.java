package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcOrderOperStateDAO;
import com.ztesoft.crm.salesres.vo.RcOrderOperStateVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcOrderOperStateDAOImpl implements RcOrderOperStateDAO {
    private String SQL_SELECT = "SELECT app_type,f_tache_id,state_id,t_tache_id,rc_desc FROM RC_ORDER_OPER_STATE";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_ORDER_OPER_STATE";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_ORDER_OPER_STATE ( app_type,f_tache_id,state_id,t_tache_id,rc_desc ) VALUES ( ?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_ORDER_OPER_STATE SET  t_tache_id = ?, rc_desc = ? WHERE app_type = ? AND f_tache_id = ? AND state_id = ? ";
    private String SQL_DELETE = "DELETE FROM RC_ORDER_OPER_STATE WHERE app_type = ? AND f_tache_id = ? AND state_id = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_ORDER_OPER_STATE ";

    public RcOrderOperStateDAOImpl() {
    }

    public RcOrderOperStateVO findByPrimaryKey(String papp_type,
        String pf_tache_id, String pstate_id) throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE app_type = ? AND f_tache_id = ? AND state_id = ? ",
                new String[] { papp_type, pf_tache_id, pstate_id });

        if (arrayList.size() > 0) {
            return (RcOrderOperStateVO) arrayList.get(0);
        } else {
            return (RcOrderOperStateVO) getEmptyVO();
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
            RcOrderOperStateVO vo = new RcOrderOperStateVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcOrderOperStateVO vo, ResultSet rs)
        throws SQLException {
        vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
        vo.setFTacheId(DAOUtils.trimStr(rs.getString("f_tache_id")));
        vo.setStateId(DAOUtils.trimStr(rs.getString("state_id")));
        vo.setTTacheId(DAOUtils.trimStr(rs.getString("t_tache_id")));
        vo.setRcDesc(DAOUtils.trimStr(rs.getString("rc_desc")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcOrderOperStateVO vo = new RcOrderOperStateVO();

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
            stmt.setString(index++, ((RcOrderOperStateVO) vo).getAppType());

            if ("".equals(((RcOrderOperStateVO) vo).getFTacheId())) {
                ((RcOrderOperStateVO) vo).setFTacheId(null);
            }

            stmt.setString(index++, ((RcOrderOperStateVO) vo).getFTacheId());
            stmt.setString(index++, ((RcOrderOperStateVO) vo).getStateId());

            if ("".equals(((RcOrderOperStateVO) vo).getTTacheId())) {
                ((RcOrderOperStateVO) vo).setTTacheId(null);
            }

            stmt.setString(index++, ((RcOrderOperStateVO) vo).getTTacheId());
            stmt.setString(index++, ((RcOrderOperStateVO) vo).getRcDesc());

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

    public boolean update(String papp_type, String pf_tache_id,
        String pstate_id, RcOrderOperStateVO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_ORDER_OPER_STATE SET app_type = ?,f_tache_id = ?,state_id = ?,t_tache_id = ?,rc_desc = ?");
            sql.append(
                " WHERE  app_type = ? AND f_tache_id = ? AND state_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index++, vo.getAppType());

            if ("".equals(((RcOrderOperStateVO) vo).getFTacheId())) {
                ((RcOrderOperStateVO) vo).setFTacheId(null);
            }

            stmt.setString(index++, vo.getFTacheId());
            stmt.setString(index++, vo.getStateId());

            if ("".equals(((RcOrderOperStateVO) vo).getTTacheId())) {
                ((RcOrderOperStateVO) vo).setTTacheId(null);
            }

            stmt.setString(index++, vo.getTTacheId());
            stmt.setString(index++, vo.getRcDesc());
            stmt.setString(index++, papp_type);
            stmt.setString(index++, pf_tache_id);
            stmt.setString(index++, pstate_id);

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
                "UPDATE RC_ORDER_OPER_STATE SET app_type = ?,f_tache_id = ?,state_id = ?,t_tache_id = ?,rc_desc = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index++, ((RcOrderOperStateVO) vo).getAppType());

            if ("".equals(((RcOrderOperStateVO) vo).getFTacheId())) {
                ((RcOrderOperStateVO) vo).setFTacheId(null);
            }

            stmt.setString(index++, ((RcOrderOperStateVO) vo).getFTacheId());
            stmt.setString(index++, ((RcOrderOperStateVO) vo).getStateId());

            if ("".equals(((RcOrderOperStateVO) vo).getTTacheId())) {
                ((RcOrderOperStateVO) vo).setTTacheId(null);
            }

            stmt.setString(index++, ((RcOrderOperStateVO) vo).getTTacheId());
            stmt.setString(index++, ((RcOrderOperStateVO) vo).getRcDesc());

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

    public long delete(String papp_type, String pf_tache_id, String pstate_id)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, papp_type);
            stmt.setString(index++, pf_tache_id);
            stmt.setString(index++, pstate_id);
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

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public VO getEmptyVO() {
        return new RcOrderOperStateVO();
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
