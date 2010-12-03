package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcNosegAppDAO;
import com.ztesoft.crm.salesres.vo.RcNosegAppVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcNosegAppDAOImpl implements RcNosegAppDAO {
    private String SQL_SELECT = "SELECT SEG_ID,STATE,LAN_ID,OPER_CODE,DEPART_ID,OPERTIME,NOTES FROM RC_NOSEG_APP";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_NOSEG_APP";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_NOSEG_APP ( SEG_ID,STATE,LAN_ID,OPER_CODE,DEPART_ID,OPERTIME,NOTES ) VALUES ( ?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_NOSEG_APP SET  SEG_ID = ?, STATE = ?, LAN_ID = ?, OPER_CODE = ?, DEPART_ID = ?, OPERTIME = ?, NOTES = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_NOSEG_APP WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_NOSEG_APP ";

    public RcNosegAppDAOImpl() {
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
            RcNosegAppVO vo = new RcNosegAppVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcNosegAppVO vo, ResultSet rs)
        throws SQLException {
        vo.setSegId(DAOUtils.trimStr(rs.getString("SEG_ID")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setOperCode(DAOUtils.trimStr(rs.getString("OPER_CODE")));
        vo.setDepartId(DAOUtils.trimStr(rs.getString("DEPART_ID")));
        vo.setOpertime(DAOUtils.getFormatedDateTime(rs.getTimestamp("OPERTIME")));
        vo.setNotes(DAOUtils.trimStr(rs.getString("NOTES")));
        vo.setSegName(DAOUtils.trimStr(rs.getString("SEG_NAME")));
        vo.setWanState(DAOUtils.trimStr(rs.getString("WAN_STATE")));

        vo.setOpername(DAOUtils.trimStr(rs.getString("OPER_NAME")));
        vo.setDepartname(DAOUtils.trimStr(rs.getString("DEPART_NAME")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcNosegAppVO vo = new RcNosegAppVO();

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

            if ("".equals(((RcNosegAppVO) vo).getSegId())) {
                ((RcNosegAppVO) vo).setSegId(null);
            }

            stmt.setString(index++, ((RcNosegAppVO) vo).getSegId());
            stmt.setString(index++, ((RcNosegAppVO) vo).getState());

            if ("".equals(((RcNosegAppVO) vo).getLanId())) {
                ((RcNosegAppVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcNosegAppVO) vo).getLanId());
            stmt.setString(index++, ((RcNosegAppVO) vo).getOperCode());

            if ("".equals(((RcNosegAppVO) vo).getDepartId())) {
                ((RcNosegAppVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcNosegAppVO) vo).getDepartId());
            stmt.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
            stmt.setString(index++, ((RcNosegAppVO) vo).getNotes());

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
                "UPDATE RC_NOSEG_APP SET SEG_ID = ?,STATE = ?,LAN_ID = ?,OPER_CODE = ?,DEPART_ID = ?,OPERTIME = ?,NOTES = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNosegAppVO) vo).getSegId())) {
                ((RcNosegAppVO) vo).setSegId(null);
            }

            stmt.setString(index++, ((RcNosegAppVO) vo).getSegId());
            stmt.setString(index++, ((RcNosegAppVO) vo).getState());

            if ("".equals(((RcNosegAppVO) vo).getLanId())) {
                ((RcNosegAppVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcNosegAppVO) vo).getLanId());
            stmt.setString(index++, ((RcNosegAppVO) vo).getOperCode());

            if ("".equals(((RcNosegAppVO) vo).getDepartId())) {
                ((RcNosegAppVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcNosegAppVO) vo).getDepartId());
            stmt.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
            stmt.setString(index++, ((RcNosegAppVO) vo).getNotes());

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
        return new RcNosegAppVO();
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

    public String appAgain(RcNosegAppVO vo, String state) {
        String flag = "0";
        Connection conn = null;
        PreparedStatement stmt = null;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_NOSEG_APP SET STATE = ?,OPERTIME = ?,NOTES = ?");
            sql.append(" WHERE seg_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()), ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            int index = 1;
            stmt.setString(index++, state);
            stmt.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
            stmt.setString(index++, vo.getNotes());
            stmt.setString(index++, vo.getSegId());

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

    //²éÑ¯ÉóÅú
    public PageModel qryRcNoSegAppForCheck(String lanId, String state,
        String segId, String segName, int pi, int ps) {
        PageModel pm = new PageModel();
        List list = new ArrayList();
        String sql = "SELECT a.SEG_ID,a.STATE,a.LAN_ID,a.OPER_CODE,a.DEPART_ID,a.OPERTIME,a.NOTES,b.seg_name,b.state as wan_state ,c.oper_name,d.depart_name" +
            " FROM RC_NOSEG_APP a,rc_noseg_wan b , staff c ,mp_department d where";
        sql += " 1 = 1 ";
        sql += " and a.seg_id = b.seg_id";
        sql += " and a.oper_code = c.oper_code";
        sql += " and a.depart_id = d.depart_id";

        if ((segId != null) && (segId.trim().length() > 0)) {
            sql += (" and a.seg_id = '" + segId + "' ");
        }

        if ((segName != null) && (segName.trim().length() > 0)) {
            sql += (" and a.seg_id in (select seg_id from rc_noseg_wan where seg_name like '%" +
            segName + "%')");
        }

        if ((state != null) && (state.trim().length() > 0)) {
            sql += (" and a.state = '" + state + "' ");
        }

        if ((lanId != null) && (lanId.trim().length() > 0)) {
            sql += (" and a.lan_id = '" + lanId + "' ");
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setMaxRows(maxRows);
            rs = stmt.executeQuery();

            list = fetchMultiResults(rs);
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        pm = PageHelper.popupPageModel(list, pi, ps);

        return pm;
    }
}
