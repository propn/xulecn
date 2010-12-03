/**
 * 该类为针对数据表进行直接操作的类，不要轻易修改方法，但可以新增方法
 */
package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;

import com.ztesoft.crm.salesres.dao.SqlComDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SqlComDAOImpl implements SqlComDAO {
    Connection conn = null;
    Statement stmt = null;

    public SqlComDAOImpl() {
    }

    public SqlComDAOImpl(boolean createCon) {
        try {
            if (createCon) {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                stmt = conn.createStatement();
            }
        } catch (SQLException e) {
            throw new DAOSystemException("errors occurs,while get connection and statement in SqlComDAOImpl!",
                e);
        } finally {
            // DAOUtils.closeStatement(stmt, this);
            // DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * 可以插入、更新、删除sql语句
     *
     * @param sql
     *            String
     * @throws DAOSystemException
     * @return int
     */
    public int excute(String sql) throws DAOSystemException {
        int rows = 0;

        if ((sql == null) || (sql.trim().length() < 1)) {
            return rows;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            rows = stmt.executeUpdate();

            return rows;
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while excute sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * author : panyazong 20090617
     * 可以插入、更新、删除sql语句
     * 传入“？”组装的sql，以及按顺序的要set的值数组
     * @param sql
     *            String
     *                     * @param sqlParams
     *            String[]
     * @throws DAOSystemException
     * @return int
     */
    public int excute(String sql, String[] sqlParams) throws DAOSystemException {
        int rows = 0;

        if ((sql == null) || (sql.trim().length() < 1)) {
            return rows;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));

            for (int i = 0; (sqlParams != null) && (i < sqlParams.length);
                    i++) {
                stmt.setString(i + 1, sqlParams[i]);
            }

            rows = stmt.executeUpdate();

            return rows;
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while excute sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public boolean checkExist(String sql) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean r = false;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            rs = stmt.executeQuery();

            if (rs.next()) {
                r = true;
            }
        } catch (SQLException se) {
            throw new DAOSystemException(se.getMessage(), se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return r;
    }

    public boolean checkExist(String sql, String[] params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean r = false;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));

            for (int i = 0; (params != null) && (i < params.length); i++) {
                stmt.setString(i + 1, params[i]);
            }

            rs = stmt.executeQuery();

            if (rs.next()) {
                r = true;
            }
        } catch (SQLException se) {
            throw new DAOSystemException(se.getMessage(), se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return r;
    }

    public long count(String sql) throws DAOSystemException {
        if ((sql == null) || (sql.trim().length() < 1)) {
            return 0L;
        }

        Connection conn = null;
        long lCount = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            rs = stmt.executeQuery();

            while (rs.next()) {
                lCount = rs.getLong("COL_COUNTS");
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

        return lCount;
    }

    /**
     * 查询传入的sql语句，根据传入的字段名封装查询的结果到map中，返回一个list集合
     *
     * @param sql
     *            String
     * @param arrs
     *            String[]：要查询的字段名数组
     * @throws DAOSystemException
     * @return List
     */
    public List qryComSql(String sql, String[] arrs) throws DAOSystemException {
        if ((sql == null) || (sql.trim().length() < 1) || (arrs == null) ||
                (arrs.length < 1)) {
            return null;
        }

        Map map = null;
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            rs = stmt.executeQuery();

            while (rs.next()) {
                map = new HashMap();

                for (int i = 0; i < arrs.length; i++) {
                    map.put(arrs[i], DAOUtils.trimStr(rs.getString(arrs[i])));
                }

                list.add(map);
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

        return list;
    }

    /**
     * 把sql语句加入statement,以后可以批量执行这些语句
     *
     * @param sql
     *            String
     */
    public void addBatchSql(String sql) throws DAOSystemException {
        try {
            if ((stmt != null) && (sql != null) && (sql.trim().length() > 0)) {
                stmt.addBatch(sql);
            }
        } catch (SQLException se) {
            DAOUtils.closeConnection(conn, this);
            Debug.print("errors occurs while addBatchSql in SqlComDAOImpl!",
                this);
            throw new DAOSystemException("errors occurs while addBatchSql in SqlComDAOImpl!",
                se);
        } finally {
            // DAOUtils.closeStatement(stmt, this);
        }
    }

    /**
     * 批量执行语句
     *
     * @param sqlArr
     *            String[]
     * @return long
     */
    public long batchExecute(String[] sqlArr) throws DAOSystemException {
        long rtn = 0;

        try {
            if ((conn == null) || conn.isClosed()) {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                stmt = conn.createStatement();
            }

            if (stmt != null) {
                if ((sqlArr != null) && (sqlArr.length > 0)) {
                    for (int i = 0; i < sqlArr.length; i++) {
                        if ((sqlArr[i] != null) &&
                                (sqlArr[i].trim().length() > 0)) {
                            stmt.addBatch(sqlArr[i]);
                        }
                    }
                }

                int[] intArr = stmt.executeBatch();

                if (intArr != null) {
                    rtn = (long) intArr.length;
                }
            }

            return rtn;
        } catch (SQLException se) {
            Debug.print("insert batch error!", this);
            throw new DAOSystemException("SQLException while insert sql:\n", se);
        } finally {
            // DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public void closeConnection() {
        try {
            if ((conn != null) && !conn.isClosed()) {
                DAOUtils.closeConnection(conn, this);
            }
        } catch (SQLException se) {
            throw new DAOSystemException("errors occurs in closeConnection() in SqlComDAOImpl:",
                se);
        }
    }

    /**
     * 如果连接没有关闭，在此关闭
     */
    protected void finalize() {
        closeConnection();
    }

    public boolean checkStorageRelation(String storageId, String operId,
        String departId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean r = false;
        String sql = "select storage_id from mp_storage where storage_id = " +
            storageId + " and oper_id=" + operId +
            " union select storage_id from storage_depart_rela where storage_id = " +
            storageId + " and depart_id=" + departId;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            rs = stmt.executeQuery();

            if (rs.next()) {
                r = true;
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

        return r;
    }

    /**
        * 批量执行语句
        * @param sqlArr String[]
        * @return long
        */
    public int batchExecuteExtends(List paramters, String sql) {
        int rtn = 0;
        PreparedStatement pstmt = null;

        try {
            if ((conn == null) || conn.isClosed()) {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                pstmt = conn.prepareStatement(sql);
            }

            if (pstmt != null) {
                for (int i = 0; i < paramters.size(); i++) {
                    String[] param = (String[]) paramters.get(i);
                    pstmt.setString(1, param[0]);
                    pstmt.setString(2, param[1]);
                    pstmt.setString(3, param[2]);
                    pstmt.setString(4, param[3]);
                    pstmt.addBatch();
                }

                int[] intArr = pstmt.executeBatch();

                if (intArr != null) {
                    rtn = intArr.length;
                }
            }

            return rtn;
        } catch (SQLException se) {
            Debug.print("insert batch error!", this);
            throw new DAOSystemException("SQLException while insert sql:\n", se);
        } finally {
            DAOUtils.closeStatement(pstmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    /**
    * 根据传入来的sql，以及需要绑定的变量执行查询，返回HashMap封装好的list
    * @param sql String
    * @param sqlArr String[]
    * @return List
    */
    public List executeQueryForMapList(String sql, String[] params)
        throws Exception {
        List list = new ArrayList();
        Connection dbConnection = null;
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            dbConnection = ConnectionContext.getContext()
                                            .getConnection(JNDINames.CRM_RCDB);

            String sqlStr = DAOSQLUtils.getFilterSQL(sql);
            stmt = dbConnection.prepareStatement(sqlStr);

            if ((params != null) && (params.length > 0)) {
                for (int i = 0; i < params.length; i++) {
                    if ((params[i] != null) && (params[i].length() > 0)) {
                        stmt.setString(i + 1, params[i]);
                    }
                }
            }

            result = stmt.executeQuery();
            list = (List) handle(result);
        } catch (Exception se) {
            throw new DAOSystemException("SQLException while execSQL:" + sql +
                "\n", se);
        } finally {
            DAOUtils.closeResultSet(result, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(dbConnection, this);
        }

        return list;
    }

    private Map rowToMap(ResultSet rs) throws SQLException {
        Map result = new HashMap();
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();

        for (int i = 1; i <= cols; i++) {
            result.put(rsmd.getColumnName(i).toLowerCase(), rs.getString(i));
        }

        return result;
    }

    private Object handle(ResultSet rs) throws SQLException {
        List results = new ArrayList();

        while (rs.next()) {
            results.add(this.rowToMap(rs));
        }

        return results;
    }
}
