package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcSimImsiDAO;
import com.ztesoft.crm.salesres.vo.RcSimVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcSimImsiDAOImpl implements RcSimImsiDAO {
    private String SQL_SELECT = "SELECT CARDMILL,INIT_TIME,PUK_NO,SIM_TYPE,CAPACITY,TEL_CAPACITY,SMS_CAPACITY,BACKUP,EXP_DATE,EFF_DATE,STATE,RESOURCE_STATE,STORAGE_ID,SALES_RESOURCE_ID,RESOURCE_LEVEL,RESOURCE_INSTANCE_CODE,RESOURCE_INSTANCE_ID FROM RC_SIM";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_SIM";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_SIM ( CARDMILL,INIT_TIME,PUK_NO,SIM_TYPE,CAPACITY,TEL_CAPACITY,SMS_CAPACITY,BACKUP,EXP_DATE,EFF_DATE,STATE,RESOURCE_STATE,STORAGE_ID,SALES_RESOURCE_ID,RESOURCE_LEVEL,RESOURCE_INSTANCE_CODE,RESOURCE_INSTANCE_ID ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_SIM SET  CARDMILL = ?, INIT_TIME = ?, PUK_NO = ?, SIM_TYPE = ?, CAPACITY = ?, TEL_CAPACITY = ?, SMS_CAPACITY = ?, BACKUP = ?, EXP_DATE = ?, EFF_DATE = ?, STATE = ?, RESOURCE_STATE = ?, STORAGE_ID = ?, SALES_RESOURCE_ID = ?, RESOURCE_LEVEL = ?, RESOURCE_INSTANCE_CODE = ?, RESOURCE_INSTANCE_ID = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_SIM WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_SIM ";

    public RcSimImsiDAOImpl() {
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
            RcSimVO vo = new RcSimVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcSimVO vo, ResultSet rs)
        throws SQLException {
        vo.setCardmill(DAOUtils.trimStr(rs.getString("CARDMILL")));
        vo.setInitTime(DAOUtils.getFormatedDateTime(rs.getDate("INIT_TIME")));
        vo.setPukNo(DAOUtils.trimStr(rs.getString("PUK_NO")));
        vo.setSimType(DAOUtils.trimStr(rs.getString("SIM_TYPE")));
        vo.setCapacity(DAOUtils.trimStr(rs.getString("CAPACITY")));
        vo.setTelCapacity(DAOUtils.trimStr(rs.getString("TEL_CAPACITY")));
        vo.setSmsCapacity(DAOUtils.trimStr(rs.getString("SMS_CAPACITY")));
        vo.setBackup(DAOUtils.trimStr(rs.getString("BACKUP")));
        vo.setExpDate(DAOUtils.getFormatedDateTime(rs.getDate("EXP_DATE")));
        vo.setEffDate(DAOUtils.getFormatedDateTime(rs.getDate("EFF_DATE")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setRescState(DAOUtils.trimStr(rs.getString("RESOURCE_STATE")));
        vo.setStorageId(DAOUtils.trimStr(rs.getString("STORAGE_ID")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setRescLevel(DAOUtils.trimStr(rs.getString("RESOURCE_LEVEL")));
        vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_CODE")));
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_ID")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcSimVO vo = new RcSimVO();

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

            if ("".equals(((RcSimVO) vo).getCardmill())) {
                ((RcSimVO) vo).setCardmill(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getCardmill());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSimVO) vo).getInitTime()));
            stmt.setString(index++, ((RcSimVO) vo).getPukNo());
            stmt.setString(index++, ((RcSimVO) vo).getSimType());

            if ("".equals(((RcSimVO) vo).getCapacity())) {
                ((RcSimVO) vo).setCapacity(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getCapacity());

            if ("".equals(((RcSimVO) vo).getTelCapacity())) {
                ((RcSimVO) vo).setTelCapacity(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getTelCapacity());

            if ("".equals(((RcSimVO) vo).getSmsCapacity())) {
                ((RcSimVO) vo).setSmsCapacity(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getSmsCapacity());
            stmt.setString(index++, ((RcSimVO) vo).getBackup());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSimVO) vo).getExpDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSimVO) vo).getEffDate()));
            stmt.setString(index++, ((RcSimVO) vo).getState());
            stmt.setString(index++, ((RcSimVO) vo).getRescState());

            if ("".equals(((RcSimVO) vo).getStorageId())) {
                ((RcSimVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getStorageId());

            if ("".equals(((RcSimVO) vo).getSalesRescId())) {
                ((RcSimVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getSalesRescId());

            if ("".equals(((RcSimVO) vo).getRescLevel())) {
                ((RcSimVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getRescLevel());
            stmt.setString(index++, ((RcSimVO) vo).getRescInstanceCode());

            if ("".equals(((RcSimVO) vo).getRescInstanceId())) {
                ((RcSimVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getRescInstanceId());

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
                "UPDATE RC_SIM SET CARDMILL = ?,INIT_TIME = ?,PUK_NO = ?,SIM_TYPE = ?,CAPACITY = ?,TEL_CAPACITY = ?,SMS_CAPACITY = ?,BACKUP = ?,EXP_DATE = ?,EFF_DATE = ?,STATE = ?,RESOURCE_STATE = ?,STORAGE_ID = ?,SALES_RESOURCE_ID = ?,RESOURCE_LEVEL = ?,RESOURCE_INSTANCE_CODE = ?,RESOURCE_INSTANCE_ID = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcSimVO) vo).getCardmill())) {
                ((RcSimVO) vo).setCardmill(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getCardmill());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSimVO) vo).getInitTime()));
            stmt.setString(index++, ((RcSimVO) vo).getPukNo());
            stmt.setString(index++, ((RcSimVO) vo).getSimType());

            if ("".equals(((RcSimVO) vo).getCapacity())) {
                ((RcSimVO) vo).setCapacity(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getCapacity());

            if ("".equals(((RcSimVO) vo).getTelCapacity())) {
                ((RcSimVO) vo).setTelCapacity(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getTelCapacity());

            if ("".equals(((RcSimVO) vo).getSmsCapacity())) {
                ((RcSimVO) vo).setSmsCapacity(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getSmsCapacity());
            stmt.setString(index++, ((RcSimVO) vo).getBackup());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSimVO) vo).getExpDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSimVO) vo).getEffDate()));
            stmt.setString(index++, ((RcSimVO) vo).getState());
            stmt.setString(index++, ((RcSimVO) vo).getRescState());

            if ("".equals(((RcSimVO) vo).getStorageId())) {
                ((RcSimVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getStorageId());

            if ("".equals(((RcSimVO) vo).getSalesRescId())) {
                ((RcSimVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getSalesRescId());

            if ("".equals(((RcSimVO) vo).getRescLevel())) {
                ((RcSimVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getRescLevel());
            stmt.setString(index++, ((RcSimVO) vo).getRescInstanceCode());

            if ("".equals(((RcSimVO) vo).getRescInstanceId())) {
                ((RcSimVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getRescInstanceId());

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
        return new RcSimVO();
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
