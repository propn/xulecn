package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.common.ObjUtil;
import com.ztesoft.crm.salesres.dao.RcPublicLogDAO;
import com.ztesoft.crm.salesres.vo.RcPublicLogVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcPublicLogDAOImpl implements RcPublicLogDAO {
    private String SQL_SELECT = "SELECT log_id,rework_time,rework_wen,rework_ip,rework_table,action,old_note,new_note FROM RC_PUBLIC_LOG";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_PUBLIC_LOG";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_PUBLIC_LOG ( log_id,rework_time,rework_wen,rework_ip,rework_table,action,old_note,new_note ) VALUES ( ?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_PUBLIC_LOG SET  rework_time = ?, rework_wen = ?, rework_ip = ?, rework_table = ?, action = ?, old_note = ?, new_note = ? WHERE log_id = ? ";
    private String SQL_DELETE = "DELETE FROM RC_PUBLIC_LOG WHERE log_id = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_PUBLIC_LOG ";

    public RcPublicLogDAOImpl() {
    }

    public RcPublicLogVO findByPrimaryKey(String plog_id)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT + " WHERE log_id = ? ",
                new String[] { plog_id });

        if (arrayList.size() > 0) {
            return (RcPublicLogVO) arrayList.get(0);
        } else {
            return (RcPublicLogVO) getEmptyVO();
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
            RcPublicLogVO vo = new RcPublicLogVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcPublicLogVO vo, ResultSet rs)
        throws SQLException {
        vo.setLogId(DAOUtils.trimStr(rs.getString("log_id")));
        vo.setReworkTime(DAOUtils.getFormatedDateTime(rs.getDate("rework_time")));
        vo.setReworkWen(DAOUtils.trimStr(rs.getString("rework_wen")));
        vo.setReworkIp(DAOUtils.trimStr(rs.getString("rework_ip")));
        vo.setReworkTable(DAOUtils.trimStr(rs.getString("rework_table")));
        vo.setAct(DAOUtils.trimStr(rs.getString("action")));
        vo.setOldNote(DAOUtils.trimStr(rs.getString("old_note")));
        vo.setNewNote(DAOUtils.trimStr(rs.getString("new_note")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcPublicLogVO vo = new RcPublicLogVO();

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

            if ("".equals(((RcPublicLogVO) vo).getLogId())) {
                ((RcPublicLogVO) vo).setLogId(null);
            }

            stmt.setString(index++, ((RcPublicLogVO) vo).getLogId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcPublicLogVO) vo).getReworkTime()));

            if ("".equals(((RcPublicLogVO) vo).getReworkWen())) {
                ((RcPublicLogVO) vo).setReworkWen(null);
            }

            stmt.setString(index++, ((RcPublicLogVO) vo).getReworkWen());
            stmt.setString(index++, ((RcPublicLogVO) vo).getReworkIp());
            stmt.setString(index++, ((RcPublicLogVO) vo).getReworkTable());
            stmt.setString(index++, ((RcPublicLogVO) vo).getAct());

            //			stmt.setString( index++, ((RcPublicLogVO)vo).getOldNote() );
            //			stmt.setString( index++, ((RcPublicLogVO)vo).getNewNote() );
            java.io.Reader clobReader = null;
            clobReader = new java.io.StringReader(((RcPublicLogVO) vo).getOldNote());
            stmt.setCharacterStream(index++, clobReader,
                ((RcPublicLogVO) vo).getOldNote().trim().length());
            clobReader = new java.io.StringReader(((RcPublicLogVO) vo).getNewNote());
            stmt.setCharacterStream(index++, clobReader,
                ((RcPublicLogVO) vo).getNewNote().trim().length());

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

    public boolean update(String plog_id, RcPublicLogVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_PUBLIC_LOG SET log_id = ?,rework_time = ?,rework_wen = ?,rework_ip = ?,rework_table = ?,action = ?,old_note = ?,new_note = ?");
            sql.append(" WHERE  log_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcPublicLogVO) vo).getLogId())) {
                ((RcPublicLogVO) vo).setLogId(null);
            }

            stmt.setString(index++, vo.getLogId());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getReworkTime()));

            if ("".equals(((RcPublicLogVO) vo).getReworkWen())) {
                ((RcPublicLogVO) vo).setReworkWen(null);
            }

            stmt.setString(index++, vo.getReworkWen());
            stmt.setString(index++, vo.getReworkIp());
            stmt.setString(index++, vo.getReworkTable());
            stmt.setString(index++, vo.getAct());

            //			stmt.setString( index++, vo.getOldNote() );
            java.io.Reader clobReader = null;
            clobReader = new java.io.StringReader(vo.getOldNote());
            stmt.setCharacterStream(index++, clobReader,
                vo.getOldNote().trim().length());
            clobReader = new java.io.StringReader(vo.getNewNote());
            stmt.setCharacterStream(index++, clobReader,
                vo.getNewNote().trim().length());
            stmt.setString(index++, plog_id);

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
                "UPDATE RC_PUBLIC_LOG SET log_id = ?,rework_time = ?,rework_wen = ?,rework_ip = ?,rework_table = ?,action = ?,old_note = ?,new_note = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcPublicLogVO) vo).getLogId())) {
                ((RcPublicLogVO) vo).setLogId(null);
            }

            stmt.setString(index++, ((RcPublicLogVO) vo).getLogId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcPublicLogVO) vo).getReworkTime()));

            if ("".equals(((RcPublicLogVO) vo).getReworkWen())) {
                ((RcPublicLogVO) vo).setReworkWen(null);
            }

            stmt.setString(index++, ((RcPublicLogVO) vo).getReworkWen());
            stmt.setString(index++, ((RcPublicLogVO) vo).getReworkIp());
            stmt.setString(index++, ((RcPublicLogVO) vo).getReworkTable());
            stmt.setString(index++, ((RcPublicLogVO) vo).getAct());

            //			stmt.setString( index++, ((RcPublicLogVO)vo).getOldNote() );
            //			stmt.setString( index++, ((RcPublicLogVO)vo).getNewNote() );
            java.io.Reader clobReader = null;
            clobReader = new java.io.StringReader(((RcPublicLogVO) vo).getOldNote());
            stmt.setCharacterStream(index++, clobReader,
                ((RcPublicLogVO) vo).getOldNote().trim().length());
            clobReader = new java.io.StringReader(((RcPublicLogVO) vo).getNewNote());
            stmt.setCharacterStream(index++, clobReader,
                ((RcPublicLogVO) vo).getNewNote().trim().length());

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

    public long delete(String plog_id) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, plog_id);
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
        return new RcPublicLogVO();
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
     * 记录日志
     * @param vo：必须包括reworkTime(更改时间)、reworkWen(修改人)、
     * reworkIp(修改人ip)、reworkTable(修改表)、act(动作)
     * @param oldValue：旧的值vo
     * @param newValue：新的值vo
     * @return
     */
    public boolean logVO(RcPublicLogVO vo, Object oldValue, Object newValue)
        throws DAOSystemException {
        if ((vo == null) || (vo.getAct() == null) ||
                (vo.getAct().trim().length() < 1)) {
            return false;
        }

        boolean result = true;
        String act = vo.getAct();
        String oldValueStr = null;
        String newValueStr = null;
        String deli = ",";

        if (oldValue != null) {
            //oldValueStr = ReflectionToStringBuilder.toString(oldValue,ToStringStyle.DEFAULT_STYLE);
            oldValueStr = ObjUtil.printFields(oldValue, deli);

            //oldValueStr = this.trimParenthesis(oldValueStr);
        }

        if (newValue != null) {
            //newValueStr = ReflectionToStringBuilder.toString(newValue,ToStringStyle.DEFAULT_STYLE);
            newValueStr = ObjUtil.printFields(newValue, deli);

            //newValueStr = this.trimParenthesis(newValueStr);
        }

        String logid = null;

        if ("A".equals(act) && (newValueStr != null)) {
            logid = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                 .getNextSequence("RC_PUBLIC_LOG", "log_id");
            vo.setLogId(logid);
            vo.setNewNote(newValueStr);
        } else if ("D".equals(act) && (oldValueStr != null)) {
            logid = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                 .getNextSequence("RC_PUBLIC_LOG", "log_id");
            vo.setLogId(logid);
            vo.setOldNote(oldValueStr);
        } else if ("M".equals(act) && (newValueStr != null) &&
                (oldValueStr != null)) {
            logid = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                 .getNextSequence("RC_PUBLIC_LOG", "log_id");
            vo.setLogId(logid);
            vo.setOldNote(oldValueStr);
            vo.setNewNote(newValueStr);
        }

        try {
            if ((vo != null) && (vo.getLogId() != null) &&
                    (vo.getLogId().trim().length() > 0)) {
                this.insert(vo);
            }
        } catch (DAOSystemException e) {
            result = false;
            throw e;
        }

        return result;
    }

    private String trimParenthesis(String str) {
        if ((str == null) || (str.trim().length() < 1)) {
            return str;
        }

        int idx1 = str.indexOf("[");
        int idx2 = str.lastIndexOf("]");

        if ((idx1 != -1) && (idx2 != -1) && ((idx1 + 1) < str.length())) {
            str = str.substring(idx1 + 1, idx2);
        }

        return str;
    }
}
