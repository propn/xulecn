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

import com.ztesoft.crm.salesres.dao.RcNosegApplogDAO;
import com.ztesoft.crm.salesres.vo.RcNosegApplogVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcNosegApplogDAOImpl implements RcNosegApplogDAO {
    private String SQL_SELECT = "SELECT SEG_ID,STATE,LAN_ID,OPER_CODE,DEPART_ID,OPERTIME,NOTES FROM RC_NOSEG_APPLOG";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_NOSEG_APPLOG";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_NOSEG_APPLOG ( SEG_ID,STATE,LAN_ID,OPER_CODE,DEPART_ID,OPERTIME,NOTES ) VALUES ( ?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_NOSEG_APPLOG SET  SEG_ID = ?, STATE = ?, LAN_ID = ?, OPER_CODE = ?, DEPART_ID = ?, OPERTIME = ?, NOTES = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_NOSEG_APPLOG WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_NOSEG_APPLOG ";

    public RcNosegApplogDAOImpl() {
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
            RcNosegApplogVO vo = new RcNosegApplogVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcNosegApplogVO vo, ResultSet rs)
        throws SQLException {
        vo.setSegId(DAOUtils.trimStr(rs.getString("SEG_ID")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setOperCode(DAOUtils.trimStr(rs.getString("OPER_CODE")));
        vo.setDepartId(DAOUtils.trimStr(rs.getString("DEPART_ID")));
        vo.setOpertime(DAOUtils.getFormatedDateTime(rs.getTimestamp("OPERTIME")));
        vo.setNotes(DAOUtils.trimStr(rs.getString("NOTES")));
        vo.setSegName(DAOUtils.trimStr(rs.getString("SEG_NAME")));

        vo.setOpername(DAOUtils.trimStr(rs.getString("OPER_NAME")));
        vo.setDepartname(DAOUtils.trimStr(rs.getString("DEPART_NAME")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcNosegApplogVO vo = new RcNosegApplogVO();

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

            if ("".equals(((RcNosegApplogVO) vo).getSegId())) {
                ((RcNosegApplogVO) vo).setSegId(null);
            }

            stmt.setString(index++, ((RcNosegApplogVO) vo).getSegId());
            stmt.setString(index++, ((RcNosegApplogVO) vo).getState());

            if ("".equals(((RcNosegApplogVO) vo).getLanId())) {
                ((RcNosegApplogVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcNosegApplogVO) vo).getLanId());
            stmt.setString(index++, ((RcNosegApplogVO) vo).getOperCode());

            if ("".equals(((RcNosegApplogVO) vo).getDepartId())) {
                ((RcNosegApplogVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcNosegApplogVO) vo).getDepartId());
            stmt.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
            stmt.setString(index++, ((RcNosegApplogVO) vo).getNotes());

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
                "UPDATE RC_NOSEG_APPLOG SET SEG_ID = ?,STATE = ?,LAN_ID = ?,OPER_CODE = ?,DEPART_ID = ?,OPERTIME = ?,NOTES = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNosegApplogVO) vo).getSegId())) {
                ((RcNosegApplogVO) vo).setSegId(null);
            }

            stmt.setString(index++, ((RcNosegApplogVO) vo).getSegId());
            stmt.setString(index++, ((RcNosegApplogVO) vo).getState());

            if ("".equals(((RcNosegApplogVO) vo).getLanId())) {
                ((RcNosegApplogVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcNosegApplogVO) vo).getLanId());
            stmt.setString(index++, ((RcNosegApplogVO) vo).getOperCode());

            if ("".equals(((RcNosegApplogVO) vo).getDepartId())) {
                ((RcNosegApplogVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcNosegApplogVO) vo).getDepartId());
            stmt.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
            stmt.setString(index++, ((RcNosegApplogVO) vo).getNotes());

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
        return new RcNosegApplogVO();
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

    //²éÑ¯ÉóÅú
    public PageModel qryRcNoSegAppLog(String segId, int pi, int ps) {
        PageModel pm = new PageModel();
        List list = new ArrayList();
        String sql = "SELECT a.SEG_ID,a.STATE,a.LAN_ID,a.OPER_CODE,a.DEPART_ID,a.OPERTIME,a.NOTES,b.seg_name,c.oper_name,d.depart_name " +
            " FROM RC_NOSEG_APPLOG a,rc_noseg_wan b , staff c ,mp_department d  where";
        sql += " 1 = 1 ";
        sql += " and a.seg_id = b.seg_id";
        sql += " and a.oper_code = c.oper_code";
        sql += " and a.depart_id = d.depart_id";

        if ((segId != null) && (segId.trim().length() > 0)) {
            sql += (" and a.seg_id = '" + segId + "' ");
        }

        sql += " order by a.OPERTIME";

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
