package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcServAcceptDAO;
import com.ztesoft.crm.salesres.vo.RcServAcceptVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcServAcceptDAOImpl implements RcServAcceptDAO {
    private String SQL_SELECT = "SELECT S_ACCEPT_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,S_INFO,R_CUST_NAME,R_CUST_TEL,LOG_ID,R_DATE,OPER_ID,A_TIME,LAN_ID,STATE,PRODUCT_ID,PRODUCT_NO,CUST_ID,CUST_NAME FROM RC_SERVICE_ACCEPT";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_SERVICE_ACCEPT";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_SERVICE_ACCEPT ( S_ACCEPT_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,S_INFO,R_CUST_NAME,R_CUST_TEL,LOG_ID,R_DATE,OPER_ID,A_TIME,LAN_ID,STATE,PRODUCT_ID,PRODUCT_NO,CUST_ID,CUST_NAME ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_SERVICE_ACCEPT SET  S_ACCEPT_ID = ?, RESOURCE_INSTANCE_ID = ?, SALES_RESOURCE_ID = ?, RESOURCE_INSTANCE_CODE = ?, S_INFO = ?, R_CUST_NAME = ?, R_CUST_TEL = ?, LOG_ID = ?, R_DATE = ?, OPER_ID = ?, A_TIME = ?, LAN_ID = ?, STATE = ?, PRODUCT_ID = ?, PRODUCT_NO = ?, CUST_ID = ?, CUST_NAME = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_SERVICE_ACCEPT WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_SERVICE_ACCEPT ";
    private int flag = 0;

    public RcServAcceptDAOImpl() {
    }

    public RcServAcceptVO findByLogId(String logId) {
        if ((logId == null) || (logId.trim().length() < 1)) {
            return new RcServAcceptVO();
        }

        RcServAcceptVO vo = null;
        String[] sqlParams = new String[] { logId };
        String sql = SQL_SELECT + " where LOG_ID=?";
        List list = this.findBySql(sql, sqlParams);

        if ((list != null) && (list.size() > 0)) {
            vo = (RcServAcceptVO) list.get(0);
        } else {
            vo = new RcServAcceptVO();
        }

        return vo;
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
            RcServAcceptVO vo = new RcServAcceptVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcServAcceptVO vo, ResultSet rs)
        throws SQLException {
        vo.setSAcceptId(DAOUtils.trimStr(rs.getString("S_ACCEPT_ID")));
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_ID")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_CODE")));
        vo.setSInfo(DAOUtils.trimStr(rs.getString("S_INFO")));
        vo.setRCustName(DAOUtils.trimStr(rs.getString("R_CUST_NAME")));
        vo.setRCustTel(DAOUtils.trimStr(rs.getString("R_CUST_TEL")));
        vo.setLogId(DAOUtils.trimStr(rs.getString("LOG_ID")));
        vo.setRDate(DAOUtils.getFormatedDateTime(rs.getTimestamp("R_DATE")));
        vo.setOperId(DAOUtils.trimStr(rs.getString("OPER_ID")));
        vo.setATime(DAOUtils.getFormatedDateTime(rs.getTimestamp("A_TIME")));
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setProdId(DAOUtils.trimStr(rs.getString("PRODUCT_ID")));
        vo.setProdNo(DAOUtils.trimStr(rs.getString("PRODUCT_NO")));
        vo.setCustId(DAOUtils.trimStr(rs.getString("CUST_ID")));
        vo.setCustName(DAOUtils.trimStr(rs.getString("CUST_NAME")));

        if (flag == 1) {
            vo.setFamilyId(DAOUtils.trimStr(rs.getString("family_id")));
            vo.setSalesRescName(DAOUtils.trimStr(rs.getString(
                        "sales_resource_name")));
        }
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcServAcceptVO vo = new RcServAcceptVO();

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
            SQL = SQL_SELECT;

            if ((whereCond != null) && (whereCond.trim().length() > 0)) {
                SQL += (" WHERE " + whereCond);
            }

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

            if ("".equals(((RcServAcceptVO) vo).getSAcceptId())) {
                ((RcServAcceptVO) vo).setSAcceptId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getSAcceptId());

            if ("".equals(((RcServAcceptVO) vo).getRescInstanceId())) {
                ((RcServAcceptVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getRescInstanceId());

            if ("".equals(((RcServAcceptVO) vo).getSalesRescId())) {
                ((RcServAcceptVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getSalesRescId());
            stmt.setString(index++, ((RcServAcceptVO) vo).getRescInstanceCode());
            stmt.setString(index++, ((RcServAcceptVO) vo).getSInfo());
            stmt.setString(index++, ((RcServAcceptVO) vo).getRCustName());
            stmt.setString(index++, ((RcServAcceptVO) vo).getRCustTel());

            if ("".equals(((RcServAcceptVO) vo).getLogId())) {
                ((RcServAcceptVO) vo).setLogId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getLogId());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcServAcceptVO) vo).getRDate()));

            if ("".equals(((RcServAcceptVO) vo).getOperId())) {
                ((RcServAcceptVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getOperId());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcServAcceptVO) vo).getATime()));

            if ("".equals(((RcServAcceptVO) vo).getLanId())) {
                ((RcServAcceptVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getLanId());
            stmt.setString(index++, ((RcServAcceptVO) vo).getState());

            if ("".equals(((RcServAcceptVO) vo).getProdId())) {
                ((RcServAcceptVO) vo).setProdId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getProdId());
            stmt.setString(index++, ((RcServAcceptVO) vo).getProdNo());

            if ("".equals(((RcServAcceptVO) vo).getCustId())) {
                ((RcServAcceptVO) vo).setCustId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getCustId());
            stmt.setString(index++, ((RcServAcceptVO) vo).getCustName());

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
                "UPDATE RC_SERVICE_ACCEPT SET S_ACCEPT_ID = ?,RESOURCE_INSTANCE_ID = ?,SALES_RESOURCE_ID = ?,RESOURCE_INSTANCE_CODE = ?,S_INFO = ?,R_CUST_NAME = ?,R_CUST_TEL = ?,LOG_ID = ?,R_DATE = ?,OPER_ID = ?,A_TIME = ?,LAN_ID = ?,STATE = ?,PRODUCT_ID = ?,PRODUCT_NO = ?,CUST_ID = ?,CUST_NAME = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcServAcceptVO) vo).getSAcceptId())) {
                ((RcServAcceptVO) vo).setSAcceptId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getSAcceptId());

            if ("".equals(((RcServAcceptVO) vo).getRescInstanceId())) {
                ((RcServAcceptVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getRescInstanceId());

            if ("".equals(((RcServAcceptVO) vo).getSalesRescId())) {
                ((RcServAcceptVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getSalesRescId());
            stmt.setString(index++, ((RcServAcceptVO) vo).getRescInstanceCode());
            stmt.setString(index++, ((RcServAcceptVO) vo).getSInfo());
            stmt.setString(index++, ((RcServAcceptVO) vo).getRCustName());
            stmt.setString(index++, ((RcServAcceptVO) vo).getRCustTel());

            if ("".equals(((RcServAcceptVO) vo).getLogId())) {
                ((RcServAcceptVO) vo).setLogId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getLogId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcServAcceptVO) vo).getRDate()));

            if ("".equals(((RcServAcceptVO) vo).getOperId())) {
                ((RcServAcceptVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getOperId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcServAcceptVO) vo).getATime()));

            if ("".equals(((RcServAcceptVO) vo).getLanId())) {
                ((RcServAcceptVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getLanId());
            stmt.setString(index++, ((RcServAcceptVO) vo).getState());

            if ("".equals(((RcServAcceptVO) vo).getProdId())) {
                ((RcServAcceptVO) vo).setProdId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getProdId());
            stmt.setString(index++, ((RcServAcceptVO) vo).getProdNo());

            if ("".equals(((RcServAcceptVO) vo).getCustId())) {
                ((RcServAcceptVO) vo).setCustId(null);
            }

            stmt.setString(index++, ((RcServAcceptVO) vo).getCustId());
            stmt.setString(index++, ((RcServAcceptVO) vo).getCustName());

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

    public boolean updateState(String state, String sAcceptId)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_SERVICE_ACCEPT SET STATE = ? where S_ACCEPT_ID = ?");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index++, state);

            if ("".equals(sAcceptId)) {
                sAcceptId = null;
            }

            stmt.setString(index++, sAcceptId);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                bResult = true;
            }
        } catch (SQLException se) {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException(
                "SQLException while updateState sql:\n" + sql.toString(), se);
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
            SQL = SQL_SELECT_COUNT;

            if ((whereCond != null) && (whereCond.trim().length() > 0)) {
                SQL += (" WHERE " + whereCond);
            }

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
        return new RcServAcceptVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT;

        if ((whereCond != null) && (whereCond.trim().length() > 0)) {
            SQL += (" WHERE " + whereCond);
        }

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

    public String getSQL_SELECT() {
        return SQL_SELECT;
    }

    public void setSQL_SELECT(String sql_select) {
        SQL_SELECT = sql_select;
    }

    public String getSQL_SELECT_COUNT() {
        return SQL_SELECT_COUNT;
    }

    public void setSQL_SELECT_COUNT(String sql_select_count) {
        SQL_SELECT_COUNT = sql_select_count;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
