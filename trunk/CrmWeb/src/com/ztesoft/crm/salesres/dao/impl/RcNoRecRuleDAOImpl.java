package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcNoRecRuleDAO;
import com.ztesoft.crm.salesres.vo.RcNoRecRuleVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcNoRecRuleDAOImpl implements RcNoRecRuleDAO {
    private String SQL_SELECT = "SELECT LAN_ID,'' as LAN_NAME,DAY_NUM FROM RC_NO_REC_RULE";
    private String SQL_SELECT_1 = "select a.lan_id,a.lan_name,b.DAY_NUM from rr_lan a left outer join RC_NO_REC_RULE b " +
        " on a.lan_id=b.lan_id ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_NO_REC_RULE";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_NO_REC_RULE ( LAN_ID,DAY_NUM ) VALUES ( ?,? )";
    private String SQL_UPDATE = "UPDATE RC_NO_REC_RULE SET  DAY_NUM = ? WHERE LAN_ID = ? ";
    private String SQL_DELETE = "DELETE FROM RC_NO_REC_RULE WHERE LAN_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_NO_REC_RULE ";

    public RcNoRecRuleDAOImpl() {
    }

    public RcNoRecRuleVO findByPrimaryKey(String pLAN_ID)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT + " WHERE LAN_ID = ? ",
                new String[] { pLAN_ID });

        if (arrayList.size() > 0) {
            return (RcNoRecRuleVO) arrayList.get(0);
        } else {
            return (RcNoRecRuleVO) getEmptyVO();
        }
    }

    /**
     * 查询出所有的本地网配置
     * @return
     */
    public List findAllLanConf() {
        List list = findBySql(SQL_SELECT_1, null);

        return list;
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
            RcNoRecRuleVO vo = new RcNoRecRuleVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcNoRecRuleVO vo, ResultSet rs)
        throws SQLException {
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setDayNum(DAOUtils.trimStr(rs.getString("DAY_NUM")));
        vo.setLanName(DAOUtils.trimStr(rs.getString("LAN_NAME")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcNoRecRuleVO vo = new RcNoRecRuleVO();

        try {
            populateDto(vo, rs);
        } catch (SQLException se) {
            Debug.print("populateCurrRecord出错", this);
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

            if ("".equals(((RcNoRecRuleVO) vo).getLanId())) {
                ((RcNoRecRuleVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcNoRecRuleVO) vo).getLanId());

            if ("".equals(((RcNoRecRuleVO) vo).getDayNum())) {
                ((RcNoRecRuleVO) vo).setDayNum(null);
            }

            stmt.setString(index++, ((RcNoRecRuleVO) vo).getDayNum());

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

    public boolean update(String pLAN_ID, RcNoRecRuleVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append("UPDATE RC_NO_REC_RULE SET LAN_ID = ?,DAY_NUM = ?");
            sql.append(" WHERE  LAN_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNoRecRuleVO) vo).getLanId())) {
                ((RcNoRecRuleVO) vo).setLanId(null);
            }

            stmt.setString(index++, vo.getLanId());

            if ("".equals(((RcNoRecRuleVO) vo).getDayNum())) {
                ((RcNoRecRuleVO) vo).setDayNum(null);
            }

            stmt.setString(index++, vo.getDayNum());
            stmt.setString(index++, pLAN_ID);

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
            sql.append("UPDATE RC_NO_REC_RULE SET LAN_ID = ?,DAY_NUM = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNoRecRuleVO) vo).getLanId())) {
                ((RcNoRecRuleVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcNoRecRuleVO) vo).getLanId());

            if ("".equals(((RcNoRecRuleVO) vo).getDayNum())) {
                ((RcNoRecRuleVO) vo).setDayNum(null);
            }

            stmt.setString(index++, ((RcNoRecRuleVO) vo).getDayNum());

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

    /**
     * 批量更新
     * @param rules
     * @return
     * @throws DAOSystemException
     */
    public long updateBatch(RcNoRecRuleVO[] rules) throws DAOSystemException {
        long result = 0;
        long num = 0;
        RcNoRecRuleVO voTemp = new RcNoRecRuleVO();

        if ((rules != null) && (rules.length > 0)) {
            for (int i = 0; i < rules.length; i++) {
                if ((rules[i] != null) && (rules[i].getLanId() != null) &&
                        (rules[i].getLanId().trim().length() > 0)) {
                    voTemp.setLanId(rules[i].getLanId());
                    voTemp.setDayNum(rules[i].getDayNum());
                    num = this.countByCond(" LAN_ID=" + voTemp.getLanId());

                    if (num > 0) {
                        this.update(voTemp.getLanId(), voTemp);
                    } else {
                        this.insert(voTemp);
                    }

                    result++;
                }
            }
        }

        return result;
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

    public long delete(String pLAN_ID) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pLAN_ID);
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
        return new RcNoRecRuleVO();
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
