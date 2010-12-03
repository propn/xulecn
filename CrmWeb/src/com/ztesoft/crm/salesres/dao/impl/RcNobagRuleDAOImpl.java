package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcNobagRuleDAO;
import com.ztesoft.crm.salesres.vo.RcNobagRuleVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcNobagRuleDAOImpl implements RcNobagRuleDAO {
    private String SQL_SELECT = "SELECT a.BAGRULE_ID,a.BAGRULE_NAME,a.BAGRULE_DESC,a.NO_NUM,a.OPER_ID,a.C_DATE,a.LAN_ID,b.OPER_CODE,b.OPER_NAME " +
        " FROM RC_NOBAG_RULE a,mp_operator b where a.OPER_ID=b.OPER_ID ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_NOBAG_RULE a,mp_operator b where a.OPER_ID=b.OPER_ID ";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_NOBAG_RULE ( BAGRULE_ID,BAGRULE_NAME,BAGRULE_DESC,NO_NUM,OPER_ID,C_DATE,LAN_ID ) VALUES ( ?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_NOBAG_RULE SET  BAGRULE_ID = ?, BAGRULE_NAME = ?, BAGRULE_DESC = ?, NO_NUM = ?, OPER_ID = ?, C_DATE = ?, LAN_ID = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_NOBAG_RULE WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_NOBAG_RULE ";

    public RcNobagRuleDAOImpl() {
    }

    public RcNobagRuleVO findByPk(String bagruleId) {
        if ((bagruleId == null) || (bagruleId.trim().length() < 1)) {
            return null;
        }

        RcNobagRuleVO vo = null;
        String sql = SQL_SELECT + " and a.BAGRULE_ID=?";
        List list = this.findBySql(sql, new String[] { bagruleId });

        if ((list != null) && (list.size() > 0)) {
            vo = (RcNobagRuleVO) list.get(0);
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
            RcNobagRuleVO vo = new RcNobagRuleVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcNobagRuleVO vo, ResultSet rs)
        throws SQLException {
        vo.setBagruleId(DAOUtils.trimStr(rs.getString("BAGRULE_ID")));
        vo.setBagruleName(DAOUtils.trimStr(rs.getString("BAGRULE_NAME")));
        vo.setBagruleDesc(DAOUtils.trimStr(rs.getString("BAGRULE_DESC")));
        vo.setNoNum(DAOUtils.trimStr(rs.getString("NO_NUM")));
        vo.setOperId(DAOUtils.trimStr(rs.getString("OPER_ID")));
        vo.setCDate(DAOUtils.getFormatedDateTime(rs.getTimestamp("C_DATE")));
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setOperCode(DAOUtils.trimStr(rs.getString("OPER_CODE")));
        vo.setOperName(DAOUtils.trimStr(rs.getString("OPER_NAME")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcNobagRuleVO vo = new RcNobagRuleVO();

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
            SQL = SQL_SELECT + whereCond;
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

            if ("".equals(((RcNobagRuleVO) vo).getBagruleId())) {
                ((RcNobagRuleVO) vo).setBagruleId(null);
            }

            stmt.setString(index++, ((RcNobagRuleVO) vo).getBagruleId());
            stmt.setString(index++, ((RcNobagRuleVO) vo).getBagruleName());
            stmt.setString(index++, ((RcNobagRuleVO) vo).getBagruleDesc());

            if ("".equals(((RcNobagRuleVO) vo).getNoNum())) {
                ((RcNobagRuleVO) vo).setNoNum(null);
            }

            stmt.setString(index++, ((RcNobagRuleVO) vo).getNoNum());

            if ("".equals(((RcNobagRuleVO) vo).getOperId())) {
                ((RcNobagRuleVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcNobagRuleVO) vo).getOperId());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcNobagRuleVO) vo).getCDate()));

            if ("".equals(((RcNobagRuleVO) vo).getLanId())) {
                ((RcNobagRuleVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcNobagRuleVO) vo).getLanId());

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
                "UPDATE RC_NOBAG_RULE SET BAGRULE_NAME = ?,BAGRULE_DESC = ?,NO_NUM = ?,OPER_ID = ?,C_DATE = ?,LAN_ID = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            stmt.setString(index++, ((RcNobagRuleVO) vo).getBagruleName());
            stmt.setString(index++, ((RcNobagRuleVO) vo).getBagruleDesc());

            if ("".equals(((RcNobagRuleVO) vo).getNoNum())) {
                ((RcNobagRuleVO) vo).setNoNum(null);
            }

            stmt.setString(index++, ((RcNobagRuleVO) vo).getNoNum());

            if ("".equals(((RcNobagRuleVO) vo).getOperId())) {
                ((RcNobagRuleVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcNobagRuleVO) vo).getOperId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNobagRuleVO) vo).getCDate()));

            if ("".equals(((RcNobagRuleVO) vo).getLanId())) {
                ((RcNobagRuleVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcNobagRuleVO) vo).getLanId());

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

    public boolean updateByPk(RcNobagRuleVO vo) throws DAOSystemException {
        if ((vo == null) || (vo.getBagruleId() == null) ||
                (vo.getBagruleId().trim().length() < 1)) {
            return false;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_NOBAG_RULE SET BAGRULE_NAME = ?,BAGRULE_DESC = ?,NO_NUM = ?,OPER_ID = ?,C_DATE = ?,LAN_ID = ?");
            sql.append(" WHERE BAGRULE_ID = ?");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            stmt.setString(index++, vo.getBagruleName());
            stmt.setString(index++, vo.getBagruleDesc());

            if ("".equals(((RcNobagRuleVO) vo).getNoNum())) {
                ((RcNobagRuleVO) vo).setNoNum(null);
            }

            stmt.setString(index++, ((RcNobagRuleVO) vo).getNoNum());

            if ("".equals(((RcNobagRuleVO) vo).getOperId())) {
                ((RcNobagRuleVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcNobagRuleVO) vo).getOperId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNobagRuleVO) vo).getCDate()));

            if ("".equals(((RcNobagRuleVO) vo).getLanId())) {
                ((RcNobagRuleVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcNobagRuleVO) vo).getLanId());
            stmt.setString(index++, ((RcNobagRuleVO) vo).getBagruleId());

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
     * 更新号码包号码数量
     * @param bagruleId
     * @return
     * @throws DAOSystemException
     */
    public boolean updateNoNum(String bagruleId) throws DAOSystemException {
        if ((bagruleId == null) || (bagruleId.trim().length() < 1)) {
            return false;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_NOBAG_RULE SET NO_NUM = (select sum(no_num) from rc_nobag_rule_detail where bagrule_id = ?)");
            sql.append(" WHERE BAGRULE_ID = ?");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            stmt.setString(index++, bagruleId);
            stmt.setString(index++, bagruleId);

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
            SQL = SQL_SELECT_COUNT + whereCond;
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
        return new RcNobagRuleVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + whereCond;
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
