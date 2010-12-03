package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcLevelLogDAO;
import com.ztesoft.crm.salesres.vo.RcLevelLogVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class RcLevelLogDAOImpl implements RcLevelLogDAO {
    private String SQL_SELECT = "SELECT LOG_ID,RESOURCE_INSTANCE_ID,RESOURCE_INSTANCE_CODE,RESOURCE_LEVEL,SALES_RESOURCE_ID,OLD_RESOURCE_LEVEL,OPER_ID,OPER_TIME FROM RC_LEVEL_LOG";
    private String SQL_SELECT2 = "SELECT A.LOG_ID,A.RESOURCE_INSTANCE_ID,A.RESOURCE_INSTANCE_CODE,A.RESOURCE_LEVEL,A.SALES_RESOURCE_ID,A.OLD_RESOURCE_LEVEL,A.OPER_ID,A.OPER_TIME,C.STORAGE_ID,C.STORAGE_NAME ,D.RC_LEVEL_NAME as RESOURCE_LEVEL_NAME,E.RC_LEVEL_NAME AS OLD_RESOURCE_LEVEL_NAME " +
        "FROM RC_LEVEL_LOG a,Rc_No b,RC_STORAGE C ,RC_LEVEL_DEF D,RC_LEVEL_DEF E where a.resource_instance_id = b.resource_instance_id AND C.STORAGE_ID = B.STORAGE_ID and b.resource_state='A' and d.rc_level_id(+)=a.RESOURCE_LEVEL and e.rc_level_id(+)=a.OLD_RESOURCE_LEVEL ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_LEVEL_LOG";
    private String SQL_SELECT_COUNT2 = "SELECT COUNT(*) AS COL_COUNTS " +
        "FROM RC_LEVEL_LOG a,Rc_No b,RC_STORAGE C ,RC_LEVEL_DEF D,RC_LEVEL_DEF E where a.resource_instance_id = b.resource_instance_id AND C.STORAGE_ID = B.STORAGE_ID and b.resource_state='A' and d.rc_level_id(+)=a.RESOURCE_LEVEL and e.rc_level_id(+)=a.OLD_RESOURCE_LEVEL ";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_LEVEL_LOG ( LOG_ID,RESOURCE_INSTANCE_ID,RESOURCE_INSTANCE_CODE,RESOURCE_LEVEL,SALES_RESOURCE_ID,OLD_RESOURCE_LEVEL,OPER_ID,OPER_TIME ) VALUES ( ?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_LEVEL_LOG SET  RESOURCE_INSTANCE_CODE = ?, RESOURCE_LEVEL = ?, SALES_RESOURCE_ID = ?, OLD_RESOURCE_LEVEL = ?, OPER_ID = ?, OPER_TIME = ? WHERE LOG_ID = ? AND RESOURCE_INSTANCE_ID = ? ";
    private String SQL_DELETE = "DELETE FROM RC_LEVEL_LOG WHERE LOG_ID = ? AND RESOURCE_INSTANCE_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_LEVEL_LOG ";
    private int flag = 0;

    public RcLevelLogDAOImpl() {
    }

    public RcLevelLogVO findByPrimaryKey(String pLOG_ID,
        String pRESOURCE_INSTANCE_ID) throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE LOG_ID = ? AND RESOURCE_INSTANCE_ID = ? ",
                new String[] { pLOG_ID, pRESOURCE_INSTANCE_ID });

        if (arrayList.size() > 0) {
            return (RcLevelLogVO) arrayList.get(0);
        } else {
            return (RcLevelLogVO) getEmptyVO();
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
            RcLevelLogVO vo = new RcLevelLogVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcLevelLogVO vo, ResultSet rs)
        throws SQLException {
        vo.setLogId(DAOUtils.trimStr(rs.getString("LOG_ID")));
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_ID")));
        vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_CODE")));
        vo.setRescLevel(DAOUtils.trimStr(rs.getString("RESOURCE_LEVEL")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setOldRescLevel(DAOUtils.trimStr(rs.getString("OLD_RESOURCE_LEVEL")));
        vo.setOperId(DAOUtils.trimStr(rs.getString("OPER_ID")));
        vo.setOperTime(DAOUtils.getFormatedDateTime(rs.getDate("OPER_TIME")));

        if (flag == 2) {
            vo.setStorgeId(DAOUtils.trimStr(rs.getString("STORAGE_ID")));
            vo.setStorageName(DAOUtils.trimStr(rs.getString("STORAGE_NAME")));
            vo.setOldRescLevleName(DAOUtils.trimStr(rs.getString(
                        "OLD_RESOURCE_LEVEL_NAME")));
            vo.setRescLevelName(DAOUtils.trimStr(rs.getString(
                        "RESOURCE_LEVEL_NAME")));
        }
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcLevelLogVO vo = new RcLevelLogVO();

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

            if ("".equals(((RcLevelLogVO) vo).getLogId())) {
                ((RcLevelLogVO) vo).setLogId(null);
            }

            stmt.setString(index++, ((RcLevelLogVO) vo).getLogId());

            if ("".equals(((RcLevelLogVO) vo).getRescInstanceId())) {
                ((RcLevelLogVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcLevelLogVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcLevelLogVO) vo).getRescInstanceCode());

            if ("".equals(((RcLevelLogVO) vo).getRescLevel())) {
                ((RcLevelLogVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcLevelLogVO) vo).getRescLevel());

            if ("".equals(((RcLevelLogVO) vo).getSalesRescId())) {
                ((RcLevelLogVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcLevelLogVO) vo).getSalesRescId());

            if ("".equals(((RcLevelLogVO) vo).getOldRescLevel())) {
                ((RcLevelLogVO) vo).setOldRescLevel(null);
            }

            stmt.setString(index++, ((RcLevelLogVO) vo).getOldRescLevel());

            if ("".equals(((RcLevelLogVO) vo).getOperId())) {
                ((RcLevelLogVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcLevelLogVO) vo).getOperId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcLevelLogVO) vo).getOperTime()));

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

    public boolean update(String pLOG_ID, String pRESOURCE_INSTANCE_ID,
        RcLevelLogVO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_LEVEL_LOG SET LOG_ID = ?,RESOURCE_INSTANCE_ID = ?,RESOURCE_INSTANCE_CODE = ?,RESOURCE_LEVEL = ?,SALES_RESOURCE_ID = ?,OLD_RESOURCE_LEVEL = ?,OPER_ID = ?,OPER_TIME = ?");
            sql.append(" WHERE  LOG_ID = ? AND RESOURCE_INSTANCE_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcLevelLogVO) vo).getLogId())) {
                ((RcLevelLogVO) vo).setLogId(null);
            }

            stmt.setString(index++, vo.getLogId());

            if ("".equals(((RcLevelLogVO) vo).getRescInstanceId())) {
                ((RcLevelLogVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, vo.getRescInstanceId());
            stmt.setString(index++, vo.getRescInstanceCode());

            if ("".equals(((RcLevelLogVO) vo).getRescLevel())) {
                ((RcLevelLogVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, vo.getRescLevel());

            if ("".equals(((RcLevelLogVO) vo).getSalesRescId())) {
                ((RcLevelLogVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, vo.getSalesRescId());

            if ("".equals(((RcLevelLogVO) vo).getOldRescLevel())) {
                ((RcLevelLogVO) vo).setOldRescLevel(null);
            }

            stmt.setString(index++, vo.getOldRescLevel());

            if ("".equals(((RcLevelLogVO) vo).getOperId())) {
                ((RcLevelLogVO) vo).setOperId(null);
            }

            stmt.setString(index++, vo.getOperId());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getOperTime()));
            stmt.setString(index++, pLOG_ID);
            stmt.setString(index++, pRESOURCE_INSTANCE_ID);

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
                "UPDATE RC_LEVEL_LOG SET LOG_ID = ?,RESOURCE_INSTANCE_ID = ?,RESOURCE_INSTANCE_CODE = ?,RESOURCE_LEVEL = ?,SALES_RESOURCE_ID = ?,OLD_RESOURCE_LEVEL = ?,OPER_ID = ?,OPER_TIME = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcLevelLogVO) vo).getLogId())) {
                ((RcLevelLogVO) vo).setLogId(null);
            }

            stmt.setString(index++, ((RcLevelLogVO) vo).getLogId());

            if ("".equals(((RcLevelLogVO) vo).getRescInstanceId())) {
                ((RcLevelLogVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcLevelLogVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcLevelLogVO) vo).getRescInstanceCode());

            if ("".equals(((RcLevelLogVO) vo).getRescLevel())) {
                ((RcLevelLogVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcLevelLogVO) vo).getRescLevel());

            if ("".equals(((RcLevelLogVO) vo).getSalesRescId())) {
                ((RcLevelLogVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcLevelLogVO) vo).getSalesRescId());

            if ("".equals(((RcLevelLogVO) vo).getOldRescLevel())) {
                ((RcLevelLogVO) vo).setOldRescLevel(null);
            }

            stmt.setString(index++, ((RcLevelLogVO) vo).getOldRescLevel());

            if ("".equals(((RcLevelLogVO) vo).getOperId())) {
                ((RcLevelLogVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcLevelLogVO) vo).getOperId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcLevelLogVO) vo).getOperTime()));

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

            if (flag == 2) {
                SQL = SQL_SELECT_COUNT2 + " " + whereCond;
                System.out.println(SQL);
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

    public long delete(String pLOG_ID, String pRESOURCE_INSTANCE_ID)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pLOG_ID);
            stmt.setString(index++, pRESOURCE_INSTANCE_ID);
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
        return new RcLevelLogVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + " WHERE " + whereCond;

        if (flag == 2) {
            SQL = SQL_SELECT2 + " " + whereCond;
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

    public int insertByBatch(List list) throws DAOSystemException {
        //		change hashmap to vo
        if ((list == null) || (list.size() == 0)) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            if (conn != null) {
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                            SQL_INSERT));

                ListIterator iter = list.listIterator();

                while (iter.hasNext()) {
                    RcLevelLogVO vo = (RcLevelLogVO) iter.next();
                    int index = 1;

                    if ("".equals(((RcLevelLogVO) vo).getLogId())) {
                        ((RcLevelLogVO) vo).setLogId(null);
                    }

                    stmt.setString(index++, ((RcLevelLogVO) vo).getLogId());

                    if ("".equals(((RcLevelLogVO) vo).getRescInstanceId())) {
                        ((RcLevelLogVO) vo).setRescInstanceId(null);
                    }

                    stmt.setString(index++,
                        ((RcLevelLogVO) vo).getRescInstanceId());
                    stmt.setString(index++,
                        ((RcLevelLogVO) vo).getRescInstanceCode());

                    if ("".equals(((RcLevelLogVO) vo).getRescLevel())) {
                        ((RcLevelLogVO) vo).setRescLevel(null);
                    }

                    stmt.setString(index++, ((RcLevelLogVO) vo).getRescLevel());

                    if ("".equals(((RcLevelLogVO) vo).getSalesRescId())) {
                        ((RcLevelLogVO) vo).setSalesRescId(null);
                    }

                    stmt.setString(index++, ((RcLevelLogVO) vo).getSalesRescId());

                    if ("".equals(((RcLevelLogVO) vo).getOldRescLevel())) {
                        ((RcLevelLogVO) vo).setOldRescLevel(null);
                    }

                    stmt.setString(index++,
                        ((RcLevelLogVO) vo).getOldRescLevel());

                    if ("".equals(((RcLevelLogVO) vo).getOperId())) {
                        ((RcLevelLogVO) vo).setOperId(null);
                    }

                    stmt.setString(index++, ((RcLevelLogVO) vo).getOperId());
                    stmt.setDate(index++,
                        DAOUtils.parseDateTime(
                            ((RcLevelLogVO) vo).getOperTime()));
                    stmt.executeUpdate();
                    stmt.clearParameters();
                }
            }

            return list.size();
        } catch (SQLException se) {
            Debug.print(SQL_INSERT, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                SQL_INSERT, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
