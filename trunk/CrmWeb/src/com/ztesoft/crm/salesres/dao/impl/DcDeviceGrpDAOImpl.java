package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.DcDeviceGrpDAO;
import com.ztesoft.crm.salesres.vo.DcDeviceGrpVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class DcDeviceGrpDAOImpl implements DcDeviceGrpDAO {
    private String SQL_SELECT = "SELECT NC_RES_CODE,DC_DEVICE_SCODE,GROUP_CODE,GROUP_NAME,OPER_DATE FROM DC_DEVICE_GROUP";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM DC_DEVICE_GROUP";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO DC_DEVICE_GROUP ( NC_RES_CODE,DC_DEVICE_SCODE,GROUP_CODE,GROUP_NAME,OPER_DATE ) VALUES ( ?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE DC_DEVICE_GROUP SET  GROUP_NAME = ?, OPER_DATE = ? WHERE DC_DEVICE_SCODE = ? AND GROUP_CODE = ? AND NC_RES_CODE = ? ";
    private String SQL_DELETE = "DELETE FROM DC_DEVICE_GROUP WHERE DC_DEVICE_SCODE = ? AND GROUP_CODE = ? AND NC_RES_CODE = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM DC_DEVICE_GROUP ";

    public DcDeviceGrpDAOImpl() {
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
            DcDeviceGrpVO vo = new DcDeviceGrpVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(DcDeviceGrpVO vo, ResultSet rs)
        throws SQLException {
        vo.setNcResCode(DAOUtils.trimStr(rs.getString("NC_RES_CODE")));
        vo.setDcDeviceScode(DAOUtils.trimStr(rs.getString("DC_DEVICE_SCODE")));
        vo.setGrpCode(DAOUtils.trimStr(rs.getString("GROUP_CODE")));
        vo.setGrpName(DAOUtils.trimStr(rs.getString("GROUP_NAME")));
        vo.setOperDate(DAOUtils.getFormatedDateTime(rs.getDate("OPER_DATE")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        DcDeviceGrpVO vo = new DcDeviceGrpVO();

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
            stmt.setString(index++, ((DcDeviceGrpVO) vo).getNcResCode());
            stmt.setString(index++, ((DcDeviceGrpVO) vo).getDcDeviceScode());
            stmt.setString(index++, ((DcDeviceGrpVO) vo).getGrpCode());
            stmt.setString(index++, ((DcDeviceGrpVO) vo).getGrpName());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((DcDeviceGrpVO) vo).getOperDate()));

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

    public boolean update(String pDC_DEVICE_SCODE, String pGROUP_CODE,
        String pNC_RES_CODE, DcDeviceGrpVO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE DC_DEVICE_GROUP SET NC_RES_CODE = ?,DC_DEVICE_SCODE = ?,GROUP_CODE = ?,GROUP_NAME = ?,OPER_DATE = ?");
            sql.append(
                " WHERE  DC_DEVICE_SCODE = ? AND GROUP_CODE = ? AND NC_RES_CODE = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index++, vo.getNcResCode());
            stmt.setString(index++, vo.getDcDeviceScode());
            stmt.setString(index++, vo.getGrpCode());
            stmt.setString(index++, vo.getGrpName());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getOperDate()));
            stmt.setString(index++, pDC_DEVICE_SCODE);
            stmt.setString(index++, pGROUP_CODE);
            stmt.setString(index++, pNC_RES_CODE);

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
                "UPDATE DC_DEVICE_GROUP SET NC_RES_CODE = ?,DC_DEVICE_SCODE = ?,GROUP_CODE = ?,GROUP_NAME = ?,OPER_DATE = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index++, ((DcDeviceGrpVO) vo).getNcResCode());
            stmt.setString(index++, ((DcDeviceGrpVO) vo).getDcDeviceScode());
            stmt.setString(index++, ((DcDeviceGrpVO) vo).getGrpCode());
            stmt.setString(index++, ((DcDeviceGrpVO) vo).getGrpName());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((DcDeviceGrpVO) vo).getOperDate()));

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

    public long delete(String pDC_DEVICE_SCODE, String pGROUP_CODE,
        String pNC_RES_CODE) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pDC_DEVICE_SCODE);
            stmt.setString(index++, pGROUP_CODE);
            stmt.setString(index++, pNC_RES_CODE);
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
        return new DcDeviceGrpVO();
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

    public DcDeviceGrpVO findByPrimaryKey(String pdc_device_scode,
        String pgroup_code, String pnc_res_code) throws DAOSystemException {
        // TODO Auto-generated method stub
        return null;
    }
}
