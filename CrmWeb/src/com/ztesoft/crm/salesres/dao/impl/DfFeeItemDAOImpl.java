package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.DfFeeItemDAO;
import com.ztesoft.crm.salesres.vo.DfFeeItemVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DfFeeItemDAOImpl implements DfFeeItemDAO {
    private String SQL_SELECT = "SELECT FEEITEM_CODE,FEEITEM_NAME,FEEKIND_CODE,INVOICE_TYPE,FEERULE,FEERULE_TYPE,RETURNRULE,RETURNRULE_TYPE,BILL_CODE,CATALOG_ID,VALID_FLAG FROM DF_FEE_ITEM";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM DF_FEE_ITEM";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO DF_FEE_ITEM ( FEEITEM_CODE,FEEITEM_NAME,FEEKIND_CODE,INVOICE_TYPE,FEERULE,FEERULE_TYPE,RETURNRULE,RETURNRULE_TYPE,BILL_CODE,CATALOG_ID,VALID_FLAG ) VALUES ( ?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE DF_FEE_ITEM SET  FEEITEM_CODE = ?, FEEITEM_NAME = ?, FEEKIND_CODE = ?, INVOICE_TYPE = ?, FEERULE = ?, FEERULE_TYPE = ?, RETURNRULE = ?, RETURNRULE_TYPE = ?, BILL_CODE = ?, CATALOG_ID = ?, VALID_FLAG = ? WHERE";
    private String SQL_DELETE = "DELETE FROM DF_FEE_ITEM WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM DF_FEE_ITEM ";

    public DfFeeItemDAOImpl() {
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
            DfFeeItemVO vo = new DfFeeItemVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(DfFeeItemVO vo, ResultSet rs)
        throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        HashMap map = new HashMap();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = rsmd.getColumnName(i);
            String columnValue = rs.getString(i);
            map.put(columnName, columnValue);
        }

        vo.loadFromHashMap(map);

        /*                vo.setFeeitemCode( DAOUtils.trimStr( rs.getString( "FEEITEM_CODE" ) ) );
                        vo.setFeeitemName( DAOUtils.trimStr( rs.getString( "FEEITEM_NAME" ) ) );
                        vo.setFeekindCode( DAOUtils.trimStr( rs.getString( "FEEKIND_CODE" ) ) );
                        vo.setInvoiceType( DAOUtils.trimStr( rs.getString( "INVOICE_TYPE" ) ) );
                        vo.setFeerule( DAOUtils.trimStr( rs.getString( "FEERULE" ) ) );
                        vo.setFeeruleType( DAOUtils.trimStr( rs.getString( "FEERULE_TYPE" ) ) );
                        vo.setReturnrule( DAOUtils.trimStr( rs.getString( "RETURNRULE" ) ) );
                        vo.setReturnruleType( DAOUtils.trimStr( rs.getString( "RETURNRULE_TYPE" ) ) );
                        vo.setBillCode( DAOUtils.trimStr( rs.getString( "BILL_CODE" ) ) );
                        vo.setCatgId( DAOUtils.trimStr( rs.getString( "CATALOG_ID" ) ) );
                        vo.setValidFlag( DAOUtils.trimStr( rs.getString( "VALID_FLAG" ) ) );
        */
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        DfFeeItemVO vo = new DfFeeItemVO();

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
            stmt.setString(index++, ((DfFeeItemVO) vo).getFeeitemCode());
            stmt.setString(index++, ((DfFeeItemVO) vo).getFeeitemName());
            stmt.setString(index++, ((DfFeeItemVO) vo).getFeekindCode());
            stmt.setString(index++, ((DfFeeItemVO) vo).getInvoiceType());
            stmt.setString(index++, ((DfFeeItemVO) vo).getFeerule());
            stmt.setString(index++, ((DfFeeItemVO) vo).getFeeruleType());
            stmt.setString(index++, ((DfFeeItemVO) vo).getReturnrule());
            stmt.setString(index++, ((DfFeeItemVO) vo).getReturnruleType());
            stmt.setString(index++, ((DfFeeItemVO) vo).getBillCode());

            if ("".equals(((DfFeeItemVO) vo).getCatgId())) {
                ((DfFeeItemVO) vo).setCatgId(null);
            }

            stmt.setString(index++, ((DfFeeItemVO) vo).getCatgId());
            stmt.setString(index++, ((DfFeeItemVO) vo).getValidFlag());

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
                "UPDATE DF_FEE_ITEM SET FEEITEM_CODE = ?,FEEITEM_NAME = ?,FEEKIND_CODE = ?,INVOICE_TYPE = ?,FEERULE = ?,FEERULE_TYPE = ?,RETURNRULE = ?,RETURNRULE_TYPE = ?,BILL_CODE = ?,CATALOG_ID = ?,VALID_FLAG = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index++, ((DfFeeItemVO) vo).getFeeitemCode());
            stmt.setString(index++, ((DfFeeItemVO) vo).getFeeitemName());
            stmt.setString(index++, ((DfFeeItemVO) vo).getFeekindCode());
            stmt.setString(index++, ((DfFeeItemVO) vo).getInvoiceType());
            stmt.setString(index++, ((DfFeeItemVO) vo).getFeerule());
            stmt.setString(index++, ((DfFeeItemVO) vo).getFeeruleType());
            stmt.setString(index++, ((DfFeeItemVO) vo).getReturnrule());
            stmt.setString(index++, ((DfFeeItemVO) vo).getReturnruleType());
            stmt.setString(index++, ((DfFeeItemVO) vo).getBillCode());

            if ("".equals(((DfFeeItemVO) vo).getCatgId())) {
                ((DfFeeItemVO) vo).setCatgId(null);
            }

            stmt.setString(index++, ((DfFeeItemVO) vo).getCatgId());
            stmt.setString(index++, ((DfFeeItemVO) vo).getValidFlag());

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
        return new DfFeeItemVO();
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

    public void setSQL_DELETE(String sql_delete) {
        SQL_DELETE = sql_delete;
    }

    public void setSQL_DELETE_BY_WHERE(String sql_delete_by_where) {
        SQL_DELETE_BY_WHERE = sql_delete_by_where;
    }

    public void setSQL_INSERT(String sql_insert) {
        SQL_INSERT = sql_insert;
    }

    public void setSQL_SELECT(String sql_select) {
        SQL_SELECT = sql_select;
    }

    public void setSQL_SELECT_COUNT(String sql_select_count) {
        SQL_SELECT_COUNT = sql_select_count;
    }

    public void setSQL_UPDATE(String sql_update) {
        SQL_UPDATE = sql_update;
    }
}
