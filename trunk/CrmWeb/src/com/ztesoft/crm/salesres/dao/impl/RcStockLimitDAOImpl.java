package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcStockLimitDAO;
import com.ztesoft.crm.salesres.vo.RcStockLimitVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcStockLimitDAOImpl implements RcStockLimitDAO {
    private String SQL_SELECT = "SELECT a.STORAGE_ID,a.FAMILY_ID,a.UP_LIMIT,a.DOWN_LIMIT,b.storage_name,c.family_name FROM RC_STOCK_LIMIT a,rc_storage b,RC_FAMILY c" +
        " where a.STORAGE_ID=b.STORAGE_ID and a.FAMILY_ID=c.FAMILY_ID ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_STOCK_LIMIT a,rc_storage b,RC_FAMILY c" +
        " where a.STORAGE_ID=b.STORAGE_ID and a.FAMILY_ID=c.FAMILY_ID ";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_STOCK_LIMIT ( STORAGE_ID,FAMILY_ID,UP_LIMIT,DOWN_LIMIT ) VALUES ( ?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_STOCK_LIMIT SET  UP_LIMIT = ?, DOWN_LIMIT = ? WHERE FAMILY_ID = ? AND STORAGE_ID = ? ";
    private String SQL_DELETE = "DELETE FROM RC_STOCK_LIMIT WHERE FAMILY_ID = ? AND STORAGE_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_STOCK_LIMIT ";

    public RcStockLimitDAOImpl() {
    }

    public RcStockLimitVO findByPrimaryKey(String pFAMILY_ID, String pSTORAGE_ID)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " and a.FAMILY_ID = ? AND a.STORAGE_ID = ? ",
                new String[] { pFAMILY_ID, pSTORAGE_ID });

        if (arrayList.size() > 0) {
            return (RcStockLimitVO) arrayList.get(0);
        } else {
            return (RcStockLimitVO) getEmptyVO();
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
            RcStockLimitVO vo = new RcStockLimitVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcStockLimitVO vo, ResultSet rs)
        throws SQLException {
        vo.setStorageId(DAOUtils.trimStr(rs.getString("STORAGE_ID")));
        vo.setFamilyId(DAOUtils.trimStr(rs.getString("FAMILY_ID")));
        vo.setStorageName(DAOUtils.trimStr(rs.getString("storage_name")));
        vo.setFamilyName(DAOUtils.trimStr(rs.getString("family_name")));
        vo.setUpLimit(DAOUtils.trimStr(rs.getString("UP_LIMIT")));
        vo.setDownLimit(DAOUtils.trimStr(rs.getString("DOWN_LIMIT")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcStockLimitVO vo = new RcStockLimitVO();

        try {
            populateDto(vo, rs);
        } catch (SQLException se) {
            Debug.print("populateCurrRecord³ö´í", this);
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
            SQL = SQL_SELECT + "  " + whereCond;
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

            if ("".equals(((RcStockLimitVO) vo).getStorageId())) {
                ((RcStockLimitVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcStockLimitVO) vo).getStorageId());

            if ("".equals(((RcStockLimitVO) vo).getFamilyId())) {
                ((RcStockLimitVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, ((RcStockLimitVO) vo).getFamilyId());

            if ("".equals(((RcStockLimitVO) vo).getUpLimit())) {
                ((RcStockLimitVO) vo).setUpLimit(null);
            }

            stmt.setString(index++, ((RcStockLimitVO) vo).getUpLimit());

            if ("".equals(((RcStockLimitVO) vo).getDownLimit())) {
                ((RcStockLimitVO) vo).setDownLimit(null);
            }

            stmt.setString(index++, ((RcStockLimitVO) vo).getDownLimit());

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

    public boolean update(String pFAMILY_ID, String pSTORAGE_ID,
        RcStockLimitVO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_STOCK_LIMIT SET STORAGE_ID = ?,FAMILY_ID = ?,UP_LIMIT = ?,DOWN_LIMIT = ?");
            sql.append(" WHERE  FAMILY_ID = ? AND STORAGE_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcStockLimitVO) vo).getStorageId())) {
                ((RcStockLimitVO) vo).setStorageId(null);
            }

            stmt.setString(index++, vo.getStorageId());

            if ("".equals(((RcStockLimitVO) vo).getFamilyId())) {
                ((RcStockLimitVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, vo.getFamilyId());

            if ("".equals(((RcStockLimitVO) vo).getUpLimit())) {
                ((RcStockLimitVO) vo).setUpLimit(null);
            }

            stmt.setString(index++, vo.getUpLimit());

            if ("".equals(((RcStockLimitVO) vo).getDownLimit())) {
                ((RcStockLimitVO) vo).setDownLimit(null);
            }

            stmt.setString(index++, vo.getDownLimit());
            stmt.setString(index++, pFAMILY_ID);
            stmt.setString(index++, pSTORAGE_ID);

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
                "UPDATE RC_STOCK_LIMIT SET STORAGE_ID = ?,FAMILY_ID = ?,UP_LIMIT = ?,DOWN_LIMIT = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcStockLimitVO) vo).getStorageId())) {
                ((RcStockLimitVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcStockLimitVO) vo).getStorageId());

            if ("".equals(((RcStockLimitVO) vo).getFamilyId())) {
                ((RcStockLimitVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, ((RcStockLimitVO) vo).getFamilyId());

            if ("".equals(((RcStockLimitVO) vo).getUpLimit())) {
                ((RcStockLimitVO) vo).setUpLimit(null);
            }

            stmt.setString(index++, ((RcStockLimitVO) vo).getUpLimit());

            if ("".equals(((RcStockLimitVO) vo).getDownLimit())) {
                ((RcStockLimitVO) vo).setDownLimit(null);
            }

            stmt.setString(index++, ((RcStockLimitVO) vo).getDownLimit());

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
            SQL = SQL_SELECT_COUNT + "  " + whereCond;
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

    public long delete(String pFAMILY_ID, String pSTORAGE_ID)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pFAMILY_ID);
            stmt.setString(index++, pSTORAGE_ID);
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
        return new RcStockLimitVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + "  " + whereCond;
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
