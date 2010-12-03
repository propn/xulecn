package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcEntityReportDAO;
import com.ztesoft.crm.salesres.vo.RcEntityReportVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcEntityReportDAOImpl implements RcEntityReportDAO {
    private String SQL_SELECT = "SELECT LAN_ID,TERM_TYPE,REPORT_TYPE,STOCK_NUM_END,ADD_NUM,ADD_NUM2,SALE_NUM,STOCK_NUM_CURR,DIFFERENTNUM,SATDATE FROM RC_ENTITY_REPORT";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_ENTITY_REPORT";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_ENTITY_REPORT ( LAN_ID,TERM_TYPE,REPORT_TYPE,STOCK_NUM_END,ADD_NUM,ADD_NUM2,SALE_NUM,STOCK_NUM_CURR,DIFFERENTNUM,SATDATE ) VALUES ( ?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_ENTITY_REPORT SET  LAN_ID = ?, TERM_TYPE = ?, REPORT_TYPE = ?, STOCK_NUM_END = ?, ADD_NUM = ?, ADD_NUM2 = ?, SALE_NUM = ?, STOCK_NUM_CURR = ?, DIFFERENTNUM = ?, SATDATE = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_ENTITY_REPORT WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_ENTITY_REPORT ";

    public RcEntityReportDAOImpl() {
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
            RcEntityReportVO vo = new RcEntityReportVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcEntityReportVO vo, ResultSet rs)
        throws SQLException {
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setTermType(DAOUtils.trimStr(rs.getString("TERM_TYPE")));
        vo.setReportType(DAOUtils.trimStr(rs.getString("REPORT_TYPE")));
        vo.setStockNumEnd(DAOUtils.trimStr(rs.getString("STOCK_NUM_END")));
        vo.setAddNum(DAOUtils.trimStr(rs.getString("ADD_NUM")));
        //vo.setAddNum2( DAOUtils.trimStr( rs.getString( "ADD_NUM2" ) ) );
        vo.setSaleNum(DAOUtils.trimStr(rs.getString("SALE_NUM")));
        vo.setStockNumCurr(DAOUtils.trimStr(rs.getString("STOCK_NUM_CURR")));
        vo.setDifferentnum(DAOUtils.trimStr(rs.getString("DIFFERENTNUM")));
        vo.setSatdate(DAOUtils.trimStr(rs.getString("SATDATE")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcEntityReportVO vo = new RcEntityReportVO();

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

            if ("".equals(((RcEntityReportVO) vo).getLanId())) {
                ((RcEntityReportVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getLanId());

            if ("".equals(((RcEntityReportVO) vo).getTermType())) {
                ((RcEntityReportVO) vo).setTermType(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getTermType());
            stmt.setString(index++, ((RcEntityReportVO) vo).getReportType());

            if ("".equals(((RcEntityReportVO) vo).getStockNumEnd())) {
                ((RcEntityReportVO) vo).setStockNumEnd(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getStockNumEnd());

            if ("".equals(((RcEntityReportVO) vo).getAddNum())) {
                ((RcEntityReportVO) vo).setAddNum(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getAddNum());

            if ("".equals(((RcEntityReportVO) vo).getAddNum2())) {
                ((RcEntityReportVO) vo).setAddNum2(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getAddNum2());

            if ("".equals(((RcEntityReportVO) vo).getSaleNum())) {
                ((RcEntityReportVO) vo).setSaleNum(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getSaleNum());

            if ("".equals(((RcEntityReportVO) vo).getStockNumCurr())) {
                ((RcEntityReportVO) vo).setStockNumCurr(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getStockNumCurr());

            if ("".equals(((RcEntityReportVO) vo).getDifferentnum())) {
                ((RcEntityReportVO) vo).setDifferentnum(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getDifferentnum());
            stmt.setString(index++, ((RcEntityReportVO) vo).getSatdate());

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
                "UPDATE RC_ENTITY_REPORT SET LAN_ID = ?,TERM_TYPE = ?,REPORT_TYPE = ?,STOCK_NUM_END = ?,ADD_NUM = ?,ADD_NUM2 = ?,SALE_NUM = ?,STOCK_NUM_CURR = ?,DIFFERENTNUM = ?,SATDATE = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcEntityReportVO) vo).getLanId())) {
                ((RcEntityReportVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getLanId());

            if ("".equals(((RcEntityReportVO) vo).getTermType())) {
                ((RcEntityReportVO) vo).setTermType(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getTermType());
            stmt.setString(index++, ((RcEntityReportVO) vo).getReportType());

            if ("".equals(((RcEntityReportVO) vo).getStockNumEnd())) {
                ((RcEntityReportVO) vo).setStockNumEnd(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getStockNumEnd());

            if ("".equals(((RcEntityReportVO) vo).getAddNum())) {
                ((RcEntityReportVO) vo).setAddNum(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getAddNum());

            if ("".equals(((RcEntityReportVO) vo).getAddNum2())) {
                ((RcEntityReportVO) vo).setAddNum2(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getAddNum2());

            if ("".equals(((RcEntityReportVO) vo).getSaleNum())) {
                ((RcEntityReportVO) vo).setSaleNum(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getSaleNum());

            if ("".equals(((RcEntityReportVO) vo).getStockNumCurr())) {
                ((RcEntityReportVO) vo).setStockNumCurr(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getStockNumCurr());

            if ("".equals(((RcEntityReportVO) vo).getDifferentnum())) {
                ((RcEntityReportVO) vo).setDifferentnum(null);
            }

            stmt.setString(index++, ((RcEntityReportVO) vo).getDifferentnum());
            stmt.setString(index++, ((RcEntityReportVO) vo).getSatdate());

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
        return new RcEntityReportVO();
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
