package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.SalesRescIdRelaDAO;
import com.ztesoft.crm.salesres.vo.SalesRescIdRelaVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class SalesRescIdRelaDAOImpl implements SalesRescIdRelaDAO {
    private String SQL_SELECT = "SELECT NC_SALES_RESOURCE_ID,DC_DEVICE_SCODE,SALES_RESOURCE_ID FROM SALES_RESOURCE_ID_RELA";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM SALES_RESOURCE_ID_RELA";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO SALES_RESOURCE_ID_RELA ( NC_SALES_RESOURCE_ID,DC_DEVICE_SCODE,SALES_RESOURCE_ID ) VALUES ( ?,?,? )";
    private String SQL_UPDATE = "UPDATE SALES_RESOURCE_ID_RELA SET  WHERE DC_DEVICE_SCODE = ? AND NC_SALES_RESOURCE_ID = ? AND SALES_RESOURCE_ID = ? ";
    private String SQL_DELETE = "DELETE FROM SALES_RESOURCE_ID_RELA WHERE DC_DEVICE_SCODE = ? AND NC_SALES_RESOURCE_ID = ? AND SALES_RESOURCE_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM SALES_RESOURCE_ID_RELA ";

    public SalesRescIdRelaDAOImpl() {
    }

    public SalesRescIdRelaVO findByPrimaryKey(String pDC_DEVICE_SCODE,
        String pNC_SALES_RESOURCE_ID, String pSALES_RESOURCE_ID)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE DC_DEVICE_SCODE = ? AND NC_SALES_RESOURCE_ID = ? AND SALES_RESOURCE_ID = ? ",
                new String[] {
                    pDC_DEVICE_SCODE, pNC_SALES_RESOURCE_ID, pSALES_RESOURCE_ID
                });

        if (arrayList.size() > 0) {
            return (SalesRescIdRelaVO) arrayList.get(0);
        } else {
            return (SalesRescIdRelaVO) getEmptyVO();
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
            SalesRescIdRelaVO vo = new SalesRescIdRelaVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(SalesRescIdRelaVO vo, ResultSet rs)
        throws SQLException {
        vo.setNcSalesRescId(DAOUtils.trimStr(rs.getString(
                    "NC_SALES_RESOURCE_ID")));
        vo.setDcDeviceScode(DAOUtils.trimStr(rs.getString("DC_DEVICE_SCODE")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        SalesRescIdRelaVO vo = new SalesRescIdRelaVO();

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
            stmt.setString(index++, ((SalesRescIdRelaVO) vo).getNcSalesRescId());
            stmt.setString(index++, ((SalesRescIdRelaVO) vo).getDcDeviceScode());

            if ("".equals(((SalesRescIdRelaVO) vo).getSalesRescId())) {
                ((SalesRescIdRelaVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((SalesRescIdRelaVO) vo).getSalesRescId());

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

    public boolean update(String pDC_DEVICE_SCODE,
        String pNC_SALES_RESOURCE_ID, String pSALES_RESOURCE_ID,
        SalesRescIdRelaVO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE SALES_RESOURCE_ID_RELA SET NC_SALES_RESOURCE_ID = ?,DC_DEVICE_SCODE = ?,SALES_RESOURCE_ID = ?");
            sql.append(
                " WHERE  DC_DEVICE_SCODE = ? AND NC_SALES_RESOURCE_ID = ? AND SALES_RESOURCE_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index++, vo.getNcSalesRescId());
            stmt.setString(index++, vo.getDcDeviceScode());

            if ("".equals(((SalesRescIdRelaVO) vo).getSalesRescId())) {
                ((SalesRescIdRelaVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, vo.getSalesRescId());
            stmt.setString(index++, pDC_DEVICE_SCODE);
            stmt.setString(index++, pNC_SALES_RESOURCE_ID);
            stmt.setString(index++, pSALES_RESOURCE_ID);

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
                "UPDATE SALES_RESOURCE_ID_RELA SET NC_SALES_RESOURCE_ID = ?,DC_DEVICE_SCODE = ?,SALES_RESOURCE_ID = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index++, ((SalesRescIdRelaVO) vo).getNcSalesRescId());
            stmt.setString(index++, ((SalesRescIdRelaVO) vo).getDcDeviceScode());

            if ("".equals(((SalesRescIdRelaVO) vo).getSalesRescId())) {
                ((SalesRescIdRelaVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((SalesRescIdRelaVO) vo).getSalesRescId());

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

    public long delete(String pDC_DEVICE_SCODE, String pNC_SALES_RESOURCE_ID,
        String pSALES_RESOURCE_ID) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pDC_DEVICE_SCODE);
            stmt.setString(index++, pNC_SALES_RESOURCE_ID);
            stmt.setString(index++, pSALES_RESOURCE_ID);
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
        return new SalesRescIdRelaVO();
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
