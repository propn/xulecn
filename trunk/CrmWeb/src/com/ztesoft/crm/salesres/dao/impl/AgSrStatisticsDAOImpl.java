package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.AgSrStatisticsDAO;
import com.ztesoft.crm.salesres.vo.AgSrStatisticsVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class AgSrStatisticsDAOImpl implements AgSrStatisticsDAO {
    private String SQL_SELECT = "SELECT ORDER_ID,APP_TYPE,END_TIME,ACT_AMOUNT,AGENT_NAME,SALES_RESOURCE_NAME,STATE,AGENT_KIND_CODE,REAL_PRICE,AGENT_ID,DEPART_ID,AREA_ID,REGION_ID,SALES_RESOURCE_ID,LAN_ID,OPER_NAME,ACCEPT_TYPE,APP_TYPE_NAME,ROWNUM FROM AG_SR_STATISTICS";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM AG_SR_STATISTICS";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO AG_SR_STATISTICS ( ORDER_ID,APP_TYPE,END_TIME,ACT_AMOUNT,AGENT_NAME,SALES_RESOURCE_NAME,STATE,AGENT_KIND_CODE,REAL_PRICE,AGENT_ID,DEPART_ID,AREA_ID,REGION_ID,SALES_RESOURCE_ID,LAN_ID,OPER_NAME,ACCEPT_TYPE,APP_TYPE_NAME ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE AG_SR_STATISTICS SET  ORDER_ID = ?, APP_TYPE = ?, END_TIME = ?, ACT_AMOUNT = ?, AGENT_NAME = ?, SALES_RESOURCE_NAME = ?, STATE = ?, AGENT_KIND_CODE = ?, REAL_PRICE = ?, AGENT_ID = ?, DEPART_ID = ?, AREA_ID = ?, REGION_ID = ?, SALES_RESOURCE_ID = ?, LAN_ID = ?, OPER_NAME = ?, ACCEPT_TYPE = ?, APP_TYPE_NAME = ? WHERE";
    private String SQL_DELETE = "DELETE FROM AG_SR_STATISTICS WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM AG_SR_STATISTICS ";
    private String ACCT_CODE = "0";

    public AgSrStatisticsDAOImpl() {
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
            AgSrStatisticsVO vo = new AgSrStatisticsVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(AgSrStatisticsVO vo, ResultSet rs)
        throws SQLException {
        if ("0".equals(ACCT_CODE)) {
            vo.setOrderId(DAOUtils.trimStr(rs.getString("ORDER_ID")));
            vo.setAppType(DAOUtils.trimStr(rs.getString("APP_TYPE")));
            vo.setEndTime(DAOUtils.getFormatedDateTime(rs.getDate("END_TIME")));
            vo.setActAmount(DAOUtils.trimStr(rs.getString("ACT_AMOUNT")));
            vo.setAgentName(DAOUtils.trimStr(rs.getString("AGENT_NAME")));
            vo.setSalesRescName(DAOUtils.trimStr(rs.getString(
                        "SALES_RESOURCE_NAME")));
            vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
            vo.setAgentKindCode(DAOUtils.trimStr(rs.getString("AGENT_KIND_CODE")));
            vo.setRealPrice(DAOUtils.trimStr(rs.getString("REAL_PRICE")));
            vo.setAgentId(DAOUtils.trimStr(rs.getString("AGENT_ID")));
            vo.setDepartId(DAOUtils.trimStr(rs.getString("DEPART_ID")));
            vo.setAreaId(DAOUtils.trimStr(rs.getString("AREA_ID")));
            vo.setRegionId(DAOUtils.trimStr(rs.getString("REGION_ID")));
            vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
            vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
            vo.setOperName(DAOUtils.trimStr(rs.getString("OPER_NAME")));
            vo.setAcceptType(DAOUtils.trimStr(rs.getString("ACCEPT_TYPE")));
            vo.setAppTypeName(DAOUtils.trimStr(rs.getString("APP_TYPE_NAME")));
            vo.setSeqNo(DAOUtils.trimStr(rs.getString("ROWNUM")));
        } else if ("1".equals(ACCT_CODE)) {
            vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                        "RESOURCE_INSTANCE_CODE")));
            vo.setRescState(DAOUtils.trimStr(rs.getString("STATE_NAME")));
        }
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        AgSrStatisticsVO vo = new AgSrStatisticsVO();

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

            if ("".equals(((AgSrStatisticsVO) vo).getOrderId())) {
                ((AgSrStatisticsVO) vo).setOrderId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getOrderId());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAppType());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((AgSrStatisticsVO) vo).getEndTime()));

            if ("".equals(((AgSrStatisticsVO) vo).getActAmount())) {
                ((AgSrStatisticsVO) vo).setActAmount(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getActAmount());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAgentName());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getSalesRescName());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getState());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAgentKindCode());

            if ("".equals(((AgSrStatisticsVO) vo).getRealPrice())) {
                ((AgSrStatisticsVO) vo).setRealPrice(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getRealPrice());

            if ("".equals(((AgSrStatisticsVO) vo).getAgentId())) {
                ((AgSrStatisticsVO) vo).setAgentId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAgentId());

            if ("".equals(((AgSrStatisticsVO) vo).getDepartId())) {
                ((AgSrStatisticsVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getDepartId());

            if ("".equals(((AgSrStatisticsVO) vo).getAreaId())) {
                ((AgSrStatisticsVO) vo).setAreaId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAreaId());

            if ("".equals(((AgSrStatisticsVO) vo).getRegionId())) {
                ((AgSrStatisticsVO) vo).setRegionId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getRegionId());

            if ("".equals(((AgSrStatisticsVO) vo).getSalesRescId())) {
                ((AgSrStatisticsVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getSalesRescId());

            if ("".equals(((AgSrStatisticsVO) vo).getLanId())) {
                ((AgSrStatisticsVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getLanId());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getOperName());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAcceptType());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAppTypeName());

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
                "UPDATE AG_SR_STATISTICS SET ORDER_ID = ?,APP_TYPE = ?,END_TIME = ?,ACT_AMOUNT = ?,AGENT_NAME = ?,SALES_RESOURCE_NAME = ?,STATE = ?,AGENT_KIND_CODE = ?,REAL_PRICE = ?,AGENT_ID = ?,DEPART_ID = ?,AREA_ID = ?,REGION_ID = ?,SALES_RESOURCE_ID = ?,LAN_ID = ?,OPER_NAME = ?,ACCEPT_TYPE = ?,APP_TYPE_NAME = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((AgSrStatisticsVO) vo).getOrderId())) {
                ((AgSrStatisticsVO) vo).setOrderId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getOrderId());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAppType());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((AgSrStatisticsVO) vo).getEndTime()));

            if ("".equals(((AgSrStatisticsVO) vo).getActAmount())) {
                ((AgSrStatisticsVO) vo).setActAmount(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getActAmount());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAgentName());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getSalesRescName());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getState());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAgentKindCode());

            if ("".equals(((AgSrStatisticsVO) vo).getRealPrice())) {
                ((AgSrStatisticsVO) vo).setRealPrice(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getRealPrice());

            if ("".equals(((AgSrStatisticsVO) vo).getAgentId())) {
                ((AgSrStatisticsVO) vo).setAgentId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAgentId());

            if ("".equals(((AgSrStatisticsVO) vo).getDepartId())) {
                ((AgSrStatisticsVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getDepartId());

            if ("".equals(((AgSrStatisticsVO) vo).getAreaId())) {
                ((AgSrStatisticsVO) vo).setAreaId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAreaId());

            if ("".equals(((AgSrStatisticsVO) vo).getRegionId())) {
                ((AgSrStatisticsVO) vo).setRegionId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getRegionId());

            if ("".equals(((AgSrStatisticsVO) vo).getSalesRescId())) {
                ((AgSrStatisticsVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getSalesRescId());

            if ("".equals(((AgSrStatisticsVO) vo).getLanId())) {
                ((AgSrStatisticsVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((AgSrStatisticsVO) vo).getLanId());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getOperName());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAcceptType());
            stmt.setString(index++, ((AgSrStatisticsVO) vo).getAppTypeName());

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
        return new AgSrStatisticsVO();
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
