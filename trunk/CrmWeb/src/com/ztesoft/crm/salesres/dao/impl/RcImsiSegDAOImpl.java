package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcImsiSegDAO;
import com.ztesoft.crm.salesres.vo.RcImsiSegVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcImsiSegDAOImpl implements RcImsiSegDAO {
    private String SQL_SELECT = "SELECT IMSI_SEG_ID,START_IMSI_ID,END_IMSI_ID,INIT_TIME,START_TIME,STATE,NO_GROUP_ID FROM RC_IMSI_SEG";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_IMSI_SEG";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_IMSI_SEG ( IMSI_SEG_ID,START_IMSI_ID,END_IMSI_ID,INIT_TIME,START_TIME,STATE,NO_GROUP_ID ) VALUES ( ?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_IMSI_SEG SET  IMSI_SEG_ID = ?, START_IMSI_ID = ?, END_IMSI_ID = ?, INIT_TIME = ?, START_TIME = ?, STATE = ?, NO_GROUP_ID = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_IMSI_SEG WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_IMSI_SEG ";

    public RcImsiSegDAOImpl() {
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
            RcImsiSegVO vo = new RcImsiSegVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcImsiSegVO vo, ResultSet rs)
        throws SQLException {
        vo.setImsiSegId(DAOUtils.trimStr(rs.getString("IMSI_SEG_ID")));
        vo.setStartImsiId(DAOUtils.trimStr(rs.getString("START_IMSI_ID")));
        vo.setEndImsiId(DAOUtils.trimStr(rs.getString("END_IMSI_ID")));
        vo.setInitTime(DAOUtils.getFormatedDateTime(rs.getDate("INIT_TIME")));
        vo.setStartTime(DAOUtils.getFormatedDateTime(rs.getDate("START_TIME")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setNoGrpId(DAOUtils.trimStr(rs.getString("NO_GROUP_ID")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcImsiSegVO vo = new RcImsiSegVO();

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

            if ("".equals(((RcImsiSegVO) vo).getImsiSegId())) {
                ((RcImsiSegVO) vo).setImsiSegId(null);
            }

            stmt.setString(index++, ((RcImsiSegVO) vo).getImsiSegId());

            if ("".equals(((RcImsiSegVO) vo).getStartImsiId())) {
                ((RcImsiSegVO) vo).setStartImsiId(null);
            }

            stmt.setString(index++, ((RcImsiSegVO) vo).getStartImsiId());

            if ("".equals(((RcImsiSegVO) vo).getEndImsiId())) {
                ((RcImsiSegVO) vo).setEndImsiId(null);
            }

            stmt.setString(index++, ((RcImsiSegVO) vo).getEndImsiId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcImsiSegVO) vo).getInitTime()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcImsiSegVO) vo).getStartTime()));
            stmt.setString(index++, ((RcImsiSegVO) vo).getState());

            if ("".equals(((RcImsiSegVO) vo).getNoGrpId())) {
                ((RcImsiSegVO) vo).setNoGrpId(null);
            }

            stmt.setString(index++, ((RcImsiSegVO) vo).getNoGrpId());

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
                "UPDATE RC_IMSI_SEG SET START_IMSI_ID = ?,END_IMSI_ID = ?,STATE = ?,NO_GROUP_ID = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcImsiSegVO) vo).getStartImsiId())) {
                ((RcImsiSegVO) vo).setStartImsiId(null);
            }

            stmt.setString(index++, ((RcImsiSegVO) vo).getStartImsiId());

            if ("".equals(((RcImsiSegVO) vo).getEndImsiId())) {
                ((RcImsiSegVO) vo).setEndImsiId(null);
            }

            stmt.setString(index++, ((RcImsiSegVO) vo).getEndImsiId());
            stmt.setString(index++, ((RcImsiSegVO) vo).getState());

            if ("".equals(((RcImsiSegVO) vo).getNoGrpId())) {
                ((RcImsiSegVO) vo).setNoGrpId(null);
            }

            stmt.setString(index++, ((RcImsiSegVO) vo).getNoGrpId());

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
        return new RcImsiSegVO();
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
     * ��ѯIMSI���Ƿ�����,�ǵĻ��򲻸�ɾ��
     *
     * @param whereCond String
     * @return long
     */
    public long findRcImsiBySegId(String whereCond) {
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
            SQL = " select count(imsi_seg_id) COL_COUNTS from rc_imsi  WHERE " +
                whereCond;
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
}