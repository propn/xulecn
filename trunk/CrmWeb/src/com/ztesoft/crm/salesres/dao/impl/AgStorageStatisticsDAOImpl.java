package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.AgStorageStatisticsDAO;
import com.ztesoft.crm.salesres.vo.AgStorageStatisticsVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class AgStorageStatisticsDAOImpl implements AgStorageStatisticsDAO {
    private String SQL_SELECT = "SELECT AGENT_ID,AGENT_NAME,AGENT_KIND_CODE,AGENT_CODE,DEPART_ID,AREA_ID,REGION_ID,LAN_ID,STORAGE_ID FROM AG_STORAGE_STATISTICS";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM AG_STORAGE_STATISTICS";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO AG_STORAGE_STATISTICS ( AGENT_ID,AGENT_NAME,AGENT_KIND_CODE,AGENT_CODE,DEPART_ID,AREA_ID,REGION_ID,LAN_ID,STORAGE_ID ) VALUES ( ?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE AG_STORAGE_STATISTICS SET  AGENT_ID = ?, AGENT_NAME = ?, AGENT_KIND_CODE = ?, AGENT_CODE = ?, DEPART_ID = ?, AREA_ID = ?, REGION_ID = ?, LAN_ID = ?, STORAGE_ID = ? WHERE";
    private String SQL_DELETE = "DELETE FROM AG_STORAGE_STATISTICS WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM AG_STORAGE_STATISTICS ";
    private String ACCT_CODE = "0";

    public AgStorageStatisticsDAOImpl() {
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
            AgStorageStatisticsVO vo = new AgStorageStatisticsVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(AgStorageStatisticsVO vo, ResultSet rs)
        throws SQLException {
        if ("0".equals(ACCT_CODE)) {
            vo.setAgentId(DAOUtils.trimStr(rs.getString("AGENT_ID")));
            vo.setAgentName(DAOUtils.trimStr(rs.getString("AGENT_NAME")));
            vo.setAgentKindCode(DAOUtils.trimStr(rs.getString("AGENT_KIND_CODE")));
            vo.setAgentCode(DAOUtils.trimStr(rs.getString("AGENT_CODE")));
            vo.setDepartId(DAOUtils.trimStr(rs.getString("DEPART_ID")));
            vo.setAreaId(DAOUtils.trimStr(rs.getString("AREA_ID")));
            vo.setRegionId(DAOUtils.trimStr(rs.getString("REGION_ID")));
            vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
            vo.setStorageId(DAOUtils.trimStr(rs.getString("STORAGE_ID")));
        } else if ("1".equals(ACCT_CODE)) {
            vo.setAgentName(DAOUtils.trimStr(rs.getString("AGENT_NAME")));
            vo.setAgentCode(DAOUtils.trimStr(rs.getString("AGENT_CODE")));
            vo.setSalesRescName(DAOUtils.trimStr(rs.getString(
                        "sales_resource_name")));
            vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                        "resource_instance_code")));
            vo.setSeqNo(DAOUtils.trimStr(rs.getString("rownum")));
        }
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        AgStorageStatisticsVO vo = new AgStorageStatisticsVO();

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

            if ("".equals(((AgStorageStatisticsVO) vo).getAgentId())) {
                ((AgStorageStatisticsVO) vo).setAgentId(null);
            }

            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getAgentId());
            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getAgentName());
            stmt.setString(index++,
                ((AgStorageStatisticsVO) vo).getAgentKindCode());
            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getAgentCode());

            if ("".equals(((AgStorageStatisticsVO) vo).getDepartId())) {
                ((AgStorageStatisticsVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getDepartId());

            if ("".equals(((AgStorageStatisticsVO) vo).getAreaId())) {
                ((AgStorageStatisticsVO) vo).setAreaId(null);
            }

            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getAreaId());

            if ("".equals(((AgStorageStatisticsVO) vo).getRegionId())) {
                ((AgStorageStatisticsVO) vo).setRegionId(null);
            }

            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getRegionId());

            if ("".equals(((AgStorageStatisticsVO) vo).getLanId())) {
                ((AgStorageStatisticsVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getLanId());

            if ("".equals(((AgStorageStatisticsVO) vo).getStorageId())) {
                ((AgStorageStatisticsVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getStorageId());

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
                "UPDATE AG_STORAGE_STATISTICS SET AGENT_ID = ?,AGENT_NAME = ?,AGENT_KIND_CODE = ?,AGENT_CODE = ?,DEPART_ID = ?,AREA_ID = ?,REGION_ID = ?,LAN_ID = ?,STORAGE_ID = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((AgStorageStatisticsVO) vo).getAgentId())) {
                ((AgStorageStatisticsVO) vo).setAgentId(null);
            }

            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getAgentId());
            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getAgentName());
            stmt.setString(index++,
                ((AgStorageStatisticsVO) vo).getAgentKindCode());
            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getAgentCode());

            if ("".equals(((AgStorageStatisticsVO) vo).getDepartId())) {
                ((AgStorageStatisticsVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getDepartId());

            if ("".equals(((AgStorageStatisticsVO) vo).getAreaId())) {
                ((AgStorageStatisticsVO) vo).setAreaId(null);
            }

            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getAreaId());

            if ("".equals(((AgStorageStatisticsVO) vo).getRegionId())) {
                ((AgStorageStatisticsVO) vo).setRegionId(null);
            }

            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getRegionId());

            if ("".equals(((AgStorageStatisticsVO) vo).getLanId())) {
                ((AgStorageStatisticsVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getLanId());

            if ("".equals(((AgStorageStatisticsVO) vo).getStorageId())) {
                ((AgStorageStatisticsVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((AgStorageStatisticsVO) vo).getStorageId());

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
        return new AgStorageStatisticsVO();
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

    /**
     * setAcctCode
     *
     * @param acctCode String
     */
    public void setAcctCode(String acctCode) {
        ACCT_CODE = acctCode;
    }

    /**
     * setSql
     *
     * @param qrySql String
     * @param countSql String
     */
    public void setSql(String qrySql, String countSql) {
        if ((qrySql != null) && !qrySql.equals("")) {
            SQL_SELECT = qrySql;
        }

        if ((countSql != null) && !countSql.equals("")) {
            SQL_SELECT_COUNT = countSql;
        }
    }
}
