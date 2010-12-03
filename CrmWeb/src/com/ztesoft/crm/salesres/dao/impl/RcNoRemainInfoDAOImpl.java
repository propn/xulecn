package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcNoRemainInfoDAO;
import com.ztesoft.crm.salesres.vo.RcNoRemainInfoVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcNoRemainInfoDAOImpl implements RcNoRemainInfoDAO {
    private String SQL_SELECT1 = "SELECT remain_id,oper_id,oper_code,depart_id,depart_name,start_no,end_no,remain_time,end_remain_time,remain_num,comments,remain_flag,rel_oper_id,rel_oper_code,rel_depart_id,rel_depart_name,rel_time,RESOURCE_STATE FROM RC_NO_REMAIN_INFO";
    private String SQL_SELECT = "SELECT A.remain_id,A.oper_id,B.OPER_CODE oper_code,A.depart_id,C.ORG_NAME depart_name,A.start_no,A.end_no,A.remain_time,A.end_remain_time,A.remain_num,A.comments,A.remain_flag,A.rel_oper_id,D.OPER_CODE rel_oper_code,A.rel_depart_id,E.ORG_NAME rel_depart_name,A.rel_time,A.RESOURCE_STATE FROM RC_NO_REMAIN_INFO A " +
        " LEFT OUTER JOIN STAFF B on A.OPER_ID=B.OPER_ID" +
        " LEFT OUTER JOIN ORGANIZATION C ON A.DEPART_ID=C.PARTY_ID" +
        " LEFT OUTER JOIN STAFF D on A.REL_OPER_ID=D.OPER_ID" +
        " LEFT OUTER JOIN ORGANIZATION E ON A.REL_DEPART_ID=E.PARTY_ID";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_NO_REMAIN_INFO A " +
        " LEFT OUTER JOIN STAFF B on A.OPER_ID=B.OPER_ID" +
        " LEFT OUTER JOIN ORGANIZATION C ON A.DEPART_ID=C.PARTY_ID" +
        " LEFT OUTER JOIN STAFF D on A.REL_OPER_ID=D.OPER_ID" +
        " LEFT OUTER JOIN ORGANIZATION E ON A.REL_DEPART_ID=E.PARTY_ID";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_NO_REMAIN_INFO ( remain_id,oper_id,depart_id,start_no,end_no,remain_time,end_remain_time,remain_num,comments,remain_flag,rel_oper_id,rel_depart_id,rel_time,RESOURCE_STATE ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_NO_REMAIN_INFO SET  oper_id = ?, depart_id = ?, start_no = ?, end_no = ?, remain_time = ?, end_remain_time = ?, remain_num = ?, comments = ?, remain_flag = ?, rel_oper_id = ?, rel_depart_id = ?, rel_time = ?,RESOURCE_STATE=? WHERE remain_id = ? ";
    private String SQL_DELETE = "DELETE FROM RC_NO_REMAIN_INFO WHERE remain_id = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_NO_REMAIN_INFO ";

    public RcNoRemainInfoDAOImpl() {
    }

    public RcNoRemainInfoVO findByPrimaryKey(String premain_id)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT + " WHERE remain_id = ? ",
                new String[] { premain_id });

        if (arrayList.size() > 0) {
            return (RcNoRemainInfoVO) arrayList.get(0);
        } else {
            return (RcNoRemainInfoVO) getEmptyVO();
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
            RcNoRemainInfoVO vo = new RcNoRemainInfoVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcNoRemainInfoVO vo, ResultSet rs)
        throws SQLException {
        vo.setRemainId(DAOUtils.trimStr(rs.getString("remain_id")));
        vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
        vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
        vo.setStartNo(DAOUtils.trimStr(rs.getString("start_no")));
        vo.setEndNo(DAOUtils.trimStr(rs.getString("end_no")));
        vo.setRemainTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "remain_time")));
        vo.setEndRemainTime(DAOUtils.trimStr(rs.getString("end_remain_time")));
        vo.setRemainNum(DAOUtils.trimStr(rs.getString("remain_num")));
        vo.setComments(DAOUtils.trimStr(rs.getString("comments")));
        vo.setRemainFlag(DAOUtils.trimStr(rs.getString("remain_flag")));
        vo.setRelOperId(DAOUtils.trimStr(rs.getString("rel_oper_id")));
        vo.setRelDepartId(DAOUtils.trimStr(rs.getString("rel_depart_id")));
        vo.setRelTime(DAOUtils.getFormatedDateTime(rs.getTimestamp("rel_time")));
        vo.setOperCode(DAOUtils.trimStr(rs.getString("oper_code")));
        vo.setDepartName(DAOUtils.trimStr(rs.getString("depart_Name")));
        vo.setRelOperCode(DAOUtils.trimStr(rs.getString("rel_oper_code")));
        vo.setRelDepartName(DAOUtils.trimStr(rs.getString("rel_depart_Name")));
        vo.setRescState(DAOUtils.trimStr(rs.getString("RESOURCE_STATE")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcNoRemainInfoVO vo = new RcNoRemainInfoVO();

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

            if ("".equals(((RcNoRemainInfoVO) vo).getRemainId())) {
                ((RcNoRemainInfoVO) vo).setRemainId(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getRemainId());

            if ("".equals(((RcNoRemainInfoVO) vo).getOperId())) {
                ((RcNoRemainInfoVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getOperId());

            if ("".equals(((RcNoRemainInfoVO) vo).getDepartId())) {
                ((RcNoRemainInfoVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getDepartId());
            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getStartNo());
            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getEndNo());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcNoRemainInfoVO) vo).getRemainTime()));

            if ("".equals(((RcNoRemainInfoVO) vo).getEndRemainTime())) {
                ((RcNoRemainInfoVO) vo).setEndRemainTime(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getEndRemainTime());

            if ("".equals(((RcNoRemainInfoVO) vo).getRemainNum())) {
                ((RcNoRemainInfoVO) vo).setRemainNum(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getRemainNum());
            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getComments());
            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getRemainFlag());

            if ("".equals(((RcNoRemainInfoVO) vo).getRelOperId())) {
                ((RcNoRemainInfoVO) vo).setRelOperId(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getRelOperId());

            if ("".equals(((RcNoRemainInfoVO) vo).getRelDepartId())) {
                ((RcNoRemainInfoVO) vo).setRelDepartId(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getRelDepartId());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcNoRemainInfoVO) vo).getRelTime()));
            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getRescState());

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

    public boolean update(String premain_id, RcNoRemainInfoVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_NO_REMAIN_INFO SET remain_id = ?,oper_id = ?,depart_id = ?,start_no = ?,end_no = ?,remain_time = ?,end_remain_time = ?,remain_num = ?,comments = ?,remain_flag = ?,rel_oper_id = ?,rel_depart_id = ?,rel_time = ?");
            sql.append(" WHERE  remain_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNoRemainInfoVO) vo).getRemainId())) {
                ((RcNoRemainInfoVO) vo).setRemainId(null);
            }

            stmt.setString(index++, vo.getRemainId());

            if ("".equals(((RcNoRemainInfoVO) vo).getOperId())) {
                ((RcNoRemainInfoVO) vo).setOperId(null);
            }

            stmt.setString(index++, vo.getOperId());

            if ("".equals(((RcNoRemainInfoVO) vo).getDepartId())) {
                ((RcNoRemainInfoVO) vo).setDepartId(null);
            }

            stmt.setString(index++, vo.getDepartId());
            stmt.setString(index++, vo.getStartNo());
            stmt.setString(index++, vo.getEndNo());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(vo.getRemainTime()));

            if ("".equals(((RcNoRemainInfoVO) vo).getEndRemainTime())) {
                ((RcNoRemainInfoVO) vo).setEndRemainTime(null);
            }

            stmt.setString(index++, vo.getEndRemainTime());

            if ("".equals(((RcNoRemainInfoVO) vo).getRemainNum())) {
                ((RcNoRemainInfoVO) vo).setRemainNum(null);
            }

            stmt.setString(index++, vo.getRemainNum());
            stmt.setString(index++, vo.getComments());
            stmt.setString(index++, vo.getRemainFlag());

            if ("".equals(((RcNoRemainInfoVO) vo).getRelOperId())) {
                ((RcNoRemainInfoVO) vo).setRelOperId(null);
            }

            stmt.setString(index++, vo.getRelOperId());

            if ("".equals(((RcNoRemainInfoVO) vo).getRelDepartId())) {
                ((RcNoRemainInfoVO) vo).setRelDepartId(null);
            }

            stmt.setString(index++, vo.getRelDepartId());
            stmt.setTimestamp(index++, DAOUtils.parseTimestamp(vo.getRelTime()));
            stmt.setString(index++, premain_id);

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
                "UPDATE RC_NO_REMAIN_INFO SET remain_id = ?,oper_id = ?,depart_id = ?,start_no = ?,end_no = ?,remain_time = ?,end_remain_time = ?,remain_num = ?,comments = ?,remain_flag = ?,rel_oper_id = ?,rel_depart_id = ?,rel_time = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNoRemainInfoVO) vo).getRemainId())) {
                ((RcNoRemainInfoVO) vo).setRemainId(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getRemainId());

            if ("".equals(((RcNoRemainInfoVO) vo).getOperId())) {
                ((RcNoRemainInfoVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getOperId());

            if ("".equals(((RcNoRemainInfoVO) vo).getDepartId())) {
                ((RcNoRemainInfoVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getDepartId());
            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getStartNo());
            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getEndNo());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoRemainInfoVO) vo).getRemainTime()));

            if ("".equals(((RcNoRemainInfoVO) vo).getEndRemainTime())) {
                ((RcNoRemainInfoVO) vo).setEndRemainTime(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getEndRemainTime());

            if ("".equals(((RcNoRemainInfoVO) vo).getRemainNum())) {
                ((RcNoRemainInfoVO) vo).setRemainNum(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getRemainNum());
            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getComments());
            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getRemainFlag());

            if ("".equals(((RcNoRemainInfoVO) vo).getRelOperId())) {
                ((RcNoRemainInfoVO) vo).setRelOperId(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getRelOperId());

            if ("".equals(((RcNoRemainInfoVO) vo).getRelDepartId())) {
                ((RcNoRemainInfoVO) vo).setRelDepartId(null);
            }

            stmt.setString(index++, ((RcNoRemainInfoVO) vo).getRelDepartId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoRemainInfoVO) vo).getRelTime()));

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

    public long delete(String premain_id) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, premain_id);
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
        return new RcNoRemainInfoVO();
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

    public void batchInsert(VO vo, List nolist, List reidlist, String type)
        throws DAOSystemException {
        if ((vo == null) || (nolist == null) || (nolist.size() <= 0)) {
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        int index = 1;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));

            if (type.equals("1")) {
                for (int i = 0; i < nolist.size(); i++) {
                    index = 1;
                    stmt.setString(index++, (String) reidlist.get(i));

                    if ("".equals(((RcNoRemainInfoVO) vo).getOperId())) {
                        ((RcNoRemainInfoVO) vo).setOperId(null);
                    }

                    stmt.setString(index++, ((RcNoRemainInfoVO) vo).getOperId());

                    if ("".equals(((RcNoRemainInfoVO) vo).getDepartId())) {
                        ((RcNoRemainInfoVO) vo).setDepartId(null);
                    }

                    stmt.setString(index++,
                        ((RcNoRemainInfoVO) vo).getDepartId());
                    stmt.setString(index++, (String) nolist.get(i));
                    stmt.setString(index++, (String) nolist.get(i));
                    stmt.setTimestamp(index++,
                        DAOUtils.parseTimestamp(
                            ((RcNoRemainInfoVO) vo).getRemainTime()));

                    if ("".equals(((RcNoRemainInfoVO) vo).getEndRemainTime())) {
                        ((RcNoRemainInfoVO) vo).setEndRemainTime(null);
                    }

                    stmt.setString(index++,
                        ((RcNoRemainInfoVO) vo).getEndRemainTime());

                    if ("".equals(((RcNoRemainInfoVO) vo).getRemainNum())) {
                        ((RcNoRemainInfoVO) vo).setRemainNum(null);
                    }

                    stmt.setString(index++,
                        ((RcNoRemainInfoVO) vo).getRemainNum());
                    stmt.setString(index++,
                        ((RcNoRemainInfoVO) vo).getComments());
                    stmt.setString(index++,
                        ((RcNoRemainInfoVO) vo).getRemainFlag());

                    if ("".equals(((RcNoRemainInfoVO) vo).getRelOperId())) {
                        ((RcNoRemainInfoVO) vo).setRelOperId(null);
                    }

                    stmt.setString(index++,
                        ((RcNoRemainInfoVO) vo).getRelOperId());

                    if ("".equals(((RcNoRemainInfoVO) vo).getRelDepartId())) {
                        ((RcNoRemainInfoVO) vo).setRelDepartId(null);
                    }

                    stmt.setString(index++,
                        ((RcNoRemainInfoVO) vo).getRelDepartId());
                    stmt.setTimestamp(index++,
                        DAOUtils.parseTimestamp(
                            ((RcNoRemainInfoVO) vo).getRelTime()));
                    stmt.setString(index++,
                        ((RcNoRemainInfoVO) vo).getRescState());

                    stmt.addBatch();
                }

                int[] rows = stmt.executeBatch();
            } else {
                insert(vo);
            }
        } catch (SQLException se) {
            Debug.print(SQL_INSERT, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                SQL_INSERT, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }
}
