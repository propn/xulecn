package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcNosegWanDAO;
import com.ztesoft.crm.salesres.vo.RcNosegWanVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcNosegWanDAOImpl implements RcNosegWanDAO {
    private String SQL_SELECT = "SELECT SEG_ID,SEG_NAME,BEGIN_NO,END_NO,NO_GROUP_ID,LAN_ID,STATE,OPER_CODE,CREATE_TIME FROM RC_NOSEG_WAN";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_NOSEG_WAN";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_NOSEG_WAN ( SEG_ID,SEG_NAME,BEGIN_NO,END_NO,NO_GROUP_ID,LAN_ID,STATE,OPER_CODE,CREATE_TIME ) VALUES ( ?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_NOSEG_WAN SET  SEG_ID = ?, SEG_NAME = ?, BEGIN_NO = ?, END_NO = ?, NO_GROUP_ID = ?, LAN_ID = ?, STATE = ?, OPER_CODE = ?, CREATE_TIME = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_NOSEG_WAN WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_NOSEG_WAN ";

    public RcNosegWanDAOImpl() {
    }

    public RcNosegWanVO findByPrimaryKey(String pSEG_ID)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT + " WHERE seg_id = ?",
                new String[] { pSEG_ID });

        if (arrayList.size() > 0) {
            return (RcNosegWanVO) arrayList.get(0);
        } else {
            return (RcNosegWanVO) getEmptyVO();
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
            RcNosegWanVO vo = new RcNosegWanVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcNosegWanVO vo, ResultSet rs)
        throws SQLException {
        vo.setSegId(DAOUtils.trimStr(rs.getString("SEG_ID")));
        vo.setSegName(DAOUtils.trimStr(rs.getString("SEG_NAME")));
        vo.setBeginNo(DAOUtils.trimStr(rs.getString("BEGIN_NO")));
        vo.setEndNo(DAOUtils.trimStr(rs.getString("END_NO")));
        vo.setNoGrpId(DAOUtils.trimStr(rs.getString("NO_GROUP_ID")));
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setOperCode(DAOUtils.trimStr(rs.getString("OPER_CODE")));
        vo.setCreateTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "CREATE_TIME")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcNosegWanVO vo = new RcNosegWanVO();

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

            if ("".equals(((RcNosegWanVO) vo).getSegId())) {
                ((RcNosegWanVO) vo).setSegId(null);
            }

            stmt.setString(index++, ((RcNosegWanVO) vo).getSegId());
            stmt.setString(index++, ((RcNosegWanVO) vo).getSegName());
            stmt.setString(index++, ((RcNosegWanVO) vo).getBeginNo());
            stmt.setString(index++, ((RcNosegWanVO) vo).getEndNo());

            if ("".equals(((RcNosegWanVO) vo).getNoGrpId())) {
                ((RcNosegWanVO) vo).setNoGrpId(null);
            }

            stmt.setString(index++, ((RcNosegWanVO) vo).getNoGrpId());

            if ("".equals(((RcNosegWanVO) vo).getLanId())) {
                ((RcNosegWanVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcNosegWanVO) vo).getLanId());
            stmt.setString(index++, ((RcNosegWanVO) vo).getState());
            stmt.setString(index++, ((RcNosegWanVO) vo).getOperCode());
            stmt.setTimestamp(index++, DAOUtils.getCurrentTimestamp());

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
                "UPDATE RC_NOSEG_WAN SET SEG_ID = ?,SEG_NAME = ?,BEGIN_NO = ?,END_NO = ?,NO_GROUP_ID = ?,LAN_ID = ?,STATE = ?,OPER_CODE = ?,CREATE_TIME = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNosegWanVO) vo).getSegId())) {
                ((RcNosegWanVO) vo).setSegId(null);
            }

            stmt.setString(index++, ((RcNosegWanVO) vo).getSegId());
            stmt.setString(index++, ((RcNosegWanVO) vo).getSegName());
            stmt.setString(index++, ((RcNosegWanVO) vo).getBeginNo());
            stmt.setString(index++, ((RcNosegWanVO) vo).getEndNo());

            if ("".equals(((RcNosegWanVO) vo).getNoGrpId())) {
                ((RcNosegWanVO) vo).setNoGrpId(null);
            }

            stmt.setString(index++, ((RcNosegWanVO) vo).getNoGrpId());

            if ("".equals(((RcNosegWanVO) vo).getLanId())) {
                ((RcNosegWanVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcNosegWanVO) vo).getLanId());
            stmt.setString(index++, ((RcNosegWanVO) vo).getState());
            stmt.setString(index++, ((RcNosegWanVO) vo).getOperCode());
            stmt.setTimestamp(index++, DAOUtils.getCurrentTimestamp());

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
        return new RcNosegWanVO();
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
    * findNoSegWanByCond
    *
    * @param whereCond String
    * @return String
    */
    public long findNoSegWanByCond(String whereCond) {
        Connection conn = null;
        long lCount = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "SELECT COUNT(SEG_ID) AS COL_COUNTS FROM RC_NOSEG_WAN WHERE " +
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

    //查询将要写入的号段是否已存在
    public long countRsByCond(String whereCond) throws DAOSystemException {
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

    public String checkWan(String segId, String isPass) {
        String flag = "";
        String state = "";

        if (isPass.equals("2")) { //
            state = "2";
        }

        if (isPass.equals("3")) { //
            state = "1";
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append("UPDATE RC_NOSEG_WAN SET STATE = ?");
            sql.append(" WHERE seg_id = ?");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index++, state);
            stmt.setString(index++, segId);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                flag = "1";
            }
        } catch (SQLException se) {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n" +
                sql.toString(), se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return flag;
    }
}
