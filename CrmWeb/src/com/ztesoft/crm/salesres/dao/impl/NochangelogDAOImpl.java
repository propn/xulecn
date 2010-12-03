package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.NochangelogDAO;
import com.ztesoft.crm.salesres.vo.NochangelogVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class NochangelogDAOImpl implements NochangelogDAO {
    private String SQL_SELECT = "SELECT LOGCODE,RESOURCE_INSTANCE_CODE,SALES_RESOURCE_ID,RESOURCE_INSTANCE_ID,RESOURCE_STATE_FROM,RESOURCE_STATE_TO,STATE_FROM,STATE_TO,STORAGE_ID_FROM,STORAGE_ID_TO,RESC_LEVEL_FROM,RESC_LEVEL_TO,SELF_HELP_FLAG_OLD,SELF_HELP_FLAG_NEW,OPER_CODE,CHANGEDATE FROM NOCHANGELOG";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM NOCHANGELOG";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO NOCHANGELOG ( LOGCODE,RESOURCE_INSTANCE_CODE,SALES_RESOURCE_ID,RESOURCE_INSTANCE_ID,RESOURCE_STATE_FROM,RESOURCE_STATE_TO,STATE_FROM,STATE_TO,STORAGE_ID_FROM,STORAGE_ID_TO,RESC_LEVEL_FROM,RESC_LEVEL_TO,SELF_HELP_FLAG_OLD,SELF_HELP_FLAG_NEW,OPER_CODE,CHANGEDATE ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE NOCHANGELOG SET  LOGCODE = ?, RESOURCE_INSTANCE_CODE = ?, SALES_RESOURCE_ID = ?, RESOURCE_INSTANCE_ID = ?, RESOURCE_STATE_FROM = ?, RESOURCE_STATE_TO = ?, STATE_FROM = ?, STATE_TO = ?, STORAGE_ID_FROM = ?, STORAGE_ID_TO = ?, RESC_LEVEL_FROM = ?, RESC_LEVEL_TO = ?, SELF_HELP_FLAG_OLD = ?, SELF_HELP_FLAG_NEW = ?, OPER_CODE = ?, CHANGEDATE = ? WHERE";
    private String SQL_DELETE = "DELETE FROM NOCHANGELOG WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM NOCHANGELOG ";

    public NochangelogDAOImpl() {
    }

    public NochangelogVO findByPrimaryKey(String pLOGCODE,
        String pRESOURCE_INSTANCE_CODE) throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE LOGCODE = ? AND RESOURCE_INSTANCE_CODE = ? ",
                new String[] { pLOGCODE, pRESOURCE_INSTANCE_CODE });

        if (arrayList.size() > 0) {
            return (NochangelogVO) arrayList.get(0);
        } else {
            return (NochangelogVO) getEmptyVO();
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
            NochangelogVO vo = new NochangelogVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(NochangelogVO vo, ResultSet rs)
        throws SQLException {
        vo.setLogcode(DAOUtils.trimStr(rs.getString("LOGCODE")));
        vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_CODE")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_ID")));
        vo.setRescStateFrom(DAOUtils.trimStr(rs.getString("RESOURCE_STATE_FROM")));
        vo.setRescStateTo(DAOUtils.trimStr(rs.getString("RESOURCE_STATE_TO")));
        vo.setStateFrom(DAOUtils.trimStr(rs.getString("STATE_FROM")));
        vo.setStateTo(DAOUtils.trimStr(rs.getString("STATE_TO")));
        vo.setStorageIdFrom(DAOUtils.trimStr(rs.getString("STORAGE_ID_FROM")));
        vo.setStorageIdTo(DAOUtils.trimStr(rs.getString("STORAGE_ID_TO")));
        vo.setRescLevelFrom(DAOUtils.trimStr(rs.getString("RESC_LEVEL_FROM")));
        vo.setRescLevelTo(DAOUtils.trimStr(rs.getString("RESC_LEVEL_TO")));
        vo.setSelfHelpFlagOld(DAOUtils.trimStr(rs.getString(
                    "SELF_HELP_FLAG_OLD")));
        vo.setSelfHelpFlagNew(DAOUtils.trimStr(rs.getString(
                    "SELF_HELP_FLAG_NEW")));
        vo.setOperCode(DAOUtils.trimStr(rs.getString("OPER_CODE")));
        vo.setChangedate(DAOUtils.getFormatedDateTime(rs.getDate("CHANGEDATE")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        NochangelogVO vo = new NochangelogVO();

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

            if ("".equals(((NochangelogVO) vo).getLogcode())) {
                ((NochangelogVO) vo).setLogcode(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getLogcode());
            stmt.setString(index++, ((NochangelogVO) vo).getRescInstanceCode());

            if ("".equals(((NochangelogVO) vo).getSalesRescId())) {
                ((NochangelogVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getSalesRescId());

            if ("".equals(((NochangelogVO) vo).getRescInstanceId())) {
                ((NochangelogVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getRescInstanceId());
            stmt.setString(index++, ((NochangelogVO) vo).getRescStateFrom());
            stmt.setString(index++, ((NochangelogVO) vo).getRescStateTo());
            stmt.setString(index++, ((NochangelogVO) vo).getStateFrom());
            stmt.setString(index++, ((NochangelogVO) vo).getStateTo());

            if ("".equals(((NochangelogVO) vo).getStorageIdFrom())) {
                ((NochangelogVO) vo).setStorageIdFrom(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getStorageIdFrom());

            if ("".equals(((NochangelogVO) vo).getStorageIdTo())) {
                ((NochangelogVO) vo).setStorageIdTo(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getStorageIdTo());

            if ("".equals(((NochangelogVO) vo).getRescLevelFrom())) {
                ((NochangelogVO) vo).setRescLevelFrom(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getRescLevelFrom());

            if ("".equals(((NochangelogVO) vo).getRescLevelTo())) {
                ((NochangelogVO) vo).setRescLevelTo(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getRescLevelTo());
            stmt.setString(index++, ((NochangelogVO) vo).getSelfHelpFlagOld());
            stmt.setString(index++, ((NochangelogVO) vo).getSelfHelpFlagNew());
            stmt.setString(index++, ((NochangelogVO) vo).getOperCode());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((NochangelogVO) vo).getChangedate()));

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
                "UPDATE NOCHANGELOG SET LOGCODE = ?,RESOURCE_INSTANCE_CODE = ?,SALES_RESOURCE_ID = ?,RESOURCE_INSTANCE_ID = ?,RESOURCE_STATE_FROM = ?,RESOURCE_STATE_TO = ?,STATE_FROM = ?,STATE_TO = ?,STORAGE_ID_FROM = ?,STOR" +
                "AGE_ID_TO = ?,RESC_LEVEL_FROM = ?,RESC_LEVEL_TO = ?,SELF_HELP_FLAG_OLD = ?,SELF_HELP_FLAG_NEW = ?,OPER_CODE = ?,CHANGEDATE = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((NochangelogVO) vo).getLogcode())) {
                ((NochangelogVO) vo).setLogcode(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getLogcode());
            stmt.setString(index++, ((NochangelogVO) vo).getRescInstanceCode());

            if ("".equals(((NochangelogVO) vo).getSalesRescId())) {
                ((NochangelogVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getSalesRescId());

            if ("".equals(((NochangelogVO) vo).getRescInstanceId())) {
                ((NochangelogVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getRescInstanceId());
            stmt.setString(index++, ((NochangelogVO) vo).getRescStateFrom());
            stmt.setString(index++, ((NochangelogVO) vo).getRescStateTo());
            stmt.setString(index++, ((NochangelogVO) vo).getStateFrom());
            stmt.setString(index++, ((NochangelogVO) vo).getStateTo());

            if ("".equals(((NochangelogVO) vo).getStorageIdFrom())) {
                ((NochangelogVO) vo).setStorageIdFrom(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getStorageIdFrom());

            if ("".equals(((NochangelogVO) vo).getStorageIdTo())) {
                ((NochangelogVO) vo).setStorageIdTo(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getStorageIdTo());

            if ("".equals(((NochangelogVO) vo).getRescLevelFrom())) {
                ((NochangelogVO) vo).setRescLevelFrom(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getRescLevelFrom());

            if ("".equals(((NochangelogVO) vo).getRescLevelTo())) {
                ((NochangelogVO) vo).setRescLevelTo(null);
            }

            stmt.setString(index++, ((NochangelogVO) vo).getRescLevelTo());
            stmt.setString(index++, ((NochangelogVO) vo).getSelfHelpFlagOld());
            stmt.setString(index++, ((NochangelogVO) vo).getSelfHelpFlagNew());
            stmt.setString(index++, ((NochangelogVO) vo).getOperCode());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((NochangelogVO) vo).getChangedate()));

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
        return new NochangelogVO();
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
