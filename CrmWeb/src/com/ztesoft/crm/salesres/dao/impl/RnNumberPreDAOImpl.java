package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RnNumberPreDAO;
import com.ztesoft.crm.salesres.vo.RnNumberPreVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RnNumberPreDAOImpl implements RnNumberPreDAO {
    private String SQL_SELECT = "SELECT LAN_ID,PRODUCT_NO,CUST_NAME,PWD,PRE_TIME,COMMENTS,RESOURCE_INSTANCE_ID,VALID_DATE,NO_TYPE FROM RN_NUMBER_PRE";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RN_NUMBER_PRE";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RN_NUMBER_PRE ( LAN_ID,PRODUCT_NO,CUST_NAME,PWD,PRE_TIME,COMMENTS,RESOURCE_INSTANCE_ID,VALID_DATE,NO_TYPE ) VALUES ( ?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RN_NUMBER_PRE SET  CUST_NAME = ?, PWD = ?, PRE_TIME = ?, COMMENTS = ?, RESOURCE_INSTANCE_ID = ?, VALID_DATE = ?, NO_TYPE = ? WHERE LAN_ID = ? AND PRODUCT_NO = ? ";
    private String SQL_DELETE = "DELETE FROM RN_NUMBER_PRE WHERE LAN_ID = ? AND PRODUCT_NO = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RN_NUMBER_PRE ";

    public RnNumberPreDAOImpl() {
    }

    public RnNumberPreVO findByPrimaryKey(String pLAN_ID, String pPRODUCT_NO)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE LAN_ID = ? AND PRODUCT_NO = ? ",
                new String[] { pLAN_ID, pPRODUCT_NO });

        if (arrayList.size() > 0) {
            return (RnNumberPreVO) arrayList.get(0);
        } else {
            return (RnNumberPreVO) getEmptyVO();
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
            RnNumberPreVO vo = new RnNumberPreVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RnNumberPreVO vo, ResultSet rs)
        throws SQLException {
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setProdNo(DAOUtils.trimStr(rs.getString("PRODUCT_NO")));
        vo.setCustName(DAOUtils.trimStr(rs.getString("CUST_NAME")));
        vo.setPwd(DAOUtils.trimStr(rs.getString("PWD")));
        vo.setPreTime(DAOUtils.getFormatedDateTime(rs.getDate("PRE_TIME")));
        vo.setComments(DAOUtils.trimStr(rs.getString("COMMENTS")));
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_ID")));
        vo.setValidDate(DAOUtils.getFormatedDateTime(rs.getDate("VALID_DATE")));
        vo.setNoType(DAOUtils.trimStr(rs.getString("NO_TYPE")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RnNumberPreVO vo = new RnNumberPreVO();

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

            if ("".equals(((RnNumberPreVO) vo).getLanId())) {
                ((RnNumberPreVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RnNumberPreVO) vo).getLanId());
            stmt.setString(index++, ((RnNumberPreVO) vo).getProdNo());
            stmt.setString(index++, ((RnNumberPreVO) vo).getCustName());
            stmt.setString(index++, ((RnNumberPreVO) vo).getPwd());
            stmt.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
            stmt.setString(index++, ((RnNumberPreVO) vo).getComments());

            if ("".equals(((RnNumberPreVO) vo).getRescInstanceId())) {
                ((RnNumberPreVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RnNumberPreVO) vo).getRescInstanceId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RnNumberPreVO) vo).getValidDate()));
            stmt.setString(index++, ((RnNumberPreVO) vo).getNoType());

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

    public boolean update(String pLAN_ID, String pPRODUCT_NO, RnNumberPreVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RN_NUMBER_PRE SET LAN_ID = ?,PRODUCT_NO = ?,CUST_NAME = ?,PWD = ?,PRE_TIME = ?,COMMENTS = ?,RESOURCE_INSTANCE_ID = ?,VALID_DATE = ?,NO_TYPE = ?");
            sql.append(" WHERE  LAN_ID = ? AND PRODUCT_NO = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RnNumberPreVO) vo).getLanId())) {
                ((RnNumberPreVO) vo).setLanId(null);
            }

            stmt.setString(index++, vo.getLanId());
            stmt.setString(index++, vo.getProdNo());
            stmt.setString(index++, vo.getCustName());
            stmt.setString(index++, vo.getPwd());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getPreTime()));
            stmt.setString(index++, vo.getComments());

            if ("".equals(((RnNumberPreVO) vo).getRescInstanceId())) {
                ((RnNumberPreVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, vo.getRescInstanceId());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getValidDate()));
            stmt.setString(index++, vo.getNoType());
            stmt.setString(index++, pLAN_ID);
            stmt.setString(index++, pPRODUCT_NO);

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
                "UPDATE RN_NUMBER_PRE SET LAN_ID = ?,PRODUCT_NO = ?,CUST_NAME = ?,PWD = ?,PRE_TIME = ?,COMMENTS = ?,RESOURCE_INSTANCE_ID = ?,VALID_DATE = ?,NO_TYPE = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RnNumberPreVO) vo).getLanId())) {
                ((RnNumberPreVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RnNumberPreVO) vo).getLanId());
            stmt.setString(index++, ((RnNumberPreVO) vo).getProdNo());
            stmt.setString(index++, ((RnNumberPreVO) vo).getCustName());
            stmt.setString(index++, ((RnNumberPreVO) vo).getPwd());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RnNumberPreVO) vo).getPreTime()));
            stmt.setString(index++, ((RnNumberPreVO) vo).getComments());

            if ("".equals(((RnNumberPreVO) vo).getRescInstanceId())) {
                ((RnNumberPreVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RnNumberPreVO) vo).getRescInstanceId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RnNumberPreVO) vo).getValidDate()));
            stmt.setString(index++, ((RnNumberPreVO) vo).getNoType());

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

    public long delete(String pLAN_ID, String pPRODUCT_NO)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pLAN_ID);
            stmt.setString(index++, pPRODUCT_NO);
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
        return new RnNumberPreVO();
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
