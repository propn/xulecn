package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.MpOperatorLanDAO;
import com.ztesoft.crm.salesres.vo.MpOperVO;
import com.ztesoft.crm.salesres.vo.RcOperatorLanVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class MpOperatorLanDAOImpl implements MpOperatorLanDAO {
    //	private String SQL_SELECT = "SELECT a.LAN_ID,a.OPER_ID,a.state," +
    //			"b.OPER_CODE,b.PASSWORD,b.OPER_GRADE,b.DEPART_ID,b.PWDDAY,b.UPDATE_TIME,b.OPER_NAME,b.MENU_CODE,b.JOB_TYPE,b.VALID_FLAG," +
    //			"b.COMMENTS,b.CREATE_DATE,b.LOGIN_STATUS,b.LOGIN_COUNT,b.LIMIT_COUNT FROM rc_operator_lan a, MP_OPERATOR b ";
    private String SQL_SELECT = "SELECT a.LAN_ID,a.OPER_ID,a.state," +
        "b.OPER_CODE,b.PASSWORD,b.OPER_GRADE,b.DEPART_ID,b.PWDDAY,b.UPDATE_TIME,b.OPER_NAME,b.MENU_CODE,b.JOB_TYPE,b.VALID_FLAG," +
        "b.COMMENTS,b.CREATE_DATE,b.LOGIN_STATUS,b.LOGIN_COUNT,b.LIMIT_COUNT,c.depart_name FROM rc_operator_lan a, MP_OPERATOR b,mp_department c ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM rc_operator_lan a, MP_OPERATOR b,mp_department c  ";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO MP_OPERATOR ( LAN_ID,OPER_ID,OPER_CODE,PASSWORD,OPER_GRADE,DEPART_ID,PWDDAY,UPDATE_TIME,OPER_NAME,MENU_CODE,JOB_TYPE,VALID_FLAG,COMMENTS,CREATE_DATE,LOGIN_STATUS,LOGIN_COUNT,LIMIT_COUNT,EMPLOYEE_ID ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE MP_OPERATOR SET  LAN_ID = ?, OPER_ID = ?, OPER_CODE = ?, PASSWORD = ?, OPER_GRADE = ?, DEPART_ID = ?, PWDDAY = ?, UPDATE_TIME = ?, OPER_NAME = ?, MENU_CODE = ?, JOB_TYPE = ?, VALID_FLAG = ?, COMMENTS = ?, CREATE_DATE = ?, LOGIN_STATUS = ?, LOGIN_COUNT = ?, LIMIT_COUNT = ?, EMPLOYEE_ID = ? WHERE";
    private String SQL_DELETE = "DELETE FROM MP_OPERATOR WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM MP_OPERATOR ";
    private String SQLFlag = "0";

    public MpOperatorLanDAOImpl() {
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
            RcOperatorLanVO vo = new RcOperatorLanVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcOperatorLanVO vo, ResultSet rs)
        throws SQLException {
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setOperId(DAOUtils.trimStr(rs.getString("OPER_ID")));
        vo.setOperCode(DAOUtils.trimStr(rs.getString("OPER_CODE")));
        vo.setPassword(DAOUtils.trimStr(rs.getString("PASSWORD")));
        vo.setOperGrade(DAOUtils.trimStr(rs.getString("OPER_GRADE")));
        vo.setDepartId(DAOUtils.trimStr(rs.getString("DEPART_ID")));
        vo.setPwdday(DAOUtils.trimStr(rs.getString("PWDDAY")));
        vo.setUpdateTime(DAOUtils.getFormatedDateTime(rs.getDate("UPDATE_TIME")));
        vo.setOperName(DAOUtils.trimStr(rs.getString("OPER_NAME")));
        vo.setMenuCode(DAOUtils.trimStr(rs.getString("MENU_CODE")));
        vo.setJobType(DAOUtils.trimStr(rs.getString("JOB_TYPE")));
        vo.setValidFlag(DAOUtils.trimStr(rs.getString("VALID_FLAG")));
        vo.setComments(DAOUtils.trimStr(rs.getString("COMMENTS")));
        vo.setCreateDate(DAOUtils.getFormatedDateTime(rs.getDate("CREATE_DATE")));
        vo.setLoginStatus(DAOUtils.trimStr(rs.getString("LOGIN_STATUS")));
        vo.setLoginCount(DAOUtils.trimStr(rs.getString("LOGIN_COUNT")));
        vo.setLimitCount(DAOUtils.trimStr(rs.getString("LIMIT_COUNT")));
        //vo.setEmployeeId( DAOUtils.trimStr( rs.getString( "EMPLOYEE_ID" ) ) );
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setDepartName(DAOUtils.trimStr(rs.getString("DEPART_NAME")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcOperatorLanVO vo = new RcOperatorLanVO();

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

            if ("".equals(((MpOperVO) vo).getLanId())) {
                ((MpOperVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((MpOperVO) vo).getLanId());

            if ("".equals(((MpOperVO) vo).getOperId())) {
                ((MpOperVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((MpOperVO) vo).getOperId());
            stmt.setString(index++, ((MpOperVO) vo).getOperCode());
            stmt.setString(index++, ((MpOperVO) vo).getPassword());
            stmt.setString(index++, ((MpOperVO) vo).getOperGrade());

            if ("".equals(((MpOperVO) vo).getDepartId())) {
                ((MpOperVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((MpOperVO) vo).getDepartId());
            stmt.setString(index++, ((MpOperVO) vo).getPwdday());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((MpOperVO) vo).getUpdateTime()));
            stmt.setString(index++, ((MpOperVO) vo).getOperName());
            stmt.setString(index++, ((MpOperVO) vo).getMenuCode());
            stmt.setString(index++, ((MpOperVO) vo).getJobType());
            stmt.setString(index++, ((MpOperVO) vo).getValidFlag());
            stmt.setString(index++, ((MpOperVO) vo).getComments());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((MpOperVO) vo).getCreateDate()));
            stmt.setString(index++, ((MpOperVO) vo).getLoginStatus());

            if ("".equals(((MpOperVO) vo).getLoginCount())) {
                ((MpOperVO) vo).setLoginCount(null);
            }

            stmt.setString(index++, ((MpOperVO) vo).getLoginCount());

            if ("".equals(((MpOperVO) vo).getLimitCount())) {
                ((MpOperVO) vo).setLimitCount(null);
            }

            stmt.setString(index++, ((MpOperVO) vo).getLimitCount());

            if ("".equals(((MpOperVO) vo).getEmployeeId())) {
                ((MpOperVO) vo).setEmployeeId(null);
            }

            stmt.setString(index++, ((MpOperVO) vo).getEmployeeId());

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
                "UPDATE MP_OPERATOR SET LAN_ID = ?,OPER_ID = ?,OPER_CODE = ?,PASSWORD = ?,OPER_GRADE = ?,DEPART_ID = ?,PWDDAY = ?,UPDATE_TIME = ?,OPER_NAME = ?,MENU_CODE = ?,JOB_TYPE = ?,VALID_FLAG = ?,COMMENTS = ?,CREATE_DATE = ?,LOGIN_STATUS = ?,LOGIN_COUNT = ?,LIMIT_COUNT = ?,EMPLOYEE_ID = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((MpOperVO) vo).getLanId())) {
                ((MpOperVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((MpOperVO) vo).getLanId());

            if ("".equals(((MpOperVO) vo).getOperId())) {
                ((MpOperVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((MpOperVO) vo).getOperId());
            stmt.setString(index++, ((MpOperVO) vo).getOperCode());
            stmt.setString(index++, ((MpOperVO) vo).getPassword());
            stmt.setString(index++, ((MpOperVO) vo).getOperGrade());

            if ("".equals(((MpOperVO) vo).getDepartId())) {
                ((MpOperVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((MpOperVO) vo).getDepartId());
            stmt.setString(index++, ((MpOperVO) vo).getPwdday());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((MpOperVO) vo).getUpdateTime()));
            stmt.setString(index++, ((MpOperVO) vo).getOperName());
            stmt.setString(index++, ((MpOperVO) vo).getMenuCode());
            stmt.setString(index++, ((MpOperVO) vo).getJobType());
            stmt.setString(index++, ((MpOperVO) vo).getValidFlag());
            stmt.setString(index++, ((MpOperVO) vo).getComments());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((MpOperVO) vo).getCreateDate()));
            stmt.setString(index++, ((MpOperVO) vo).getLoginStatus());

            if ("".equals(((MpOperVO) vo).getLoginCount())) {
                ((MpOperVO) vo).setLoginCount(null);
            }

            stmt.setString(index++, ((MpOperVO) vo).getLoginCount());

            if ("".equals(((MpOperVO) vo).getLimitCount())) {
                ((MpOperVO) vo).setLimitCount(null);
            }

            stmt.setString(index++, ((MpOperVO) vo).getLimitCount());

            if ("".equals(((MpOperVO) vo).getEmployeeId())) {
                ((MpOperVO) vo).setEmployeeId(null);
            }

            stmt.setString(index++, ((MpOperVO) vo).getEmployeeId());

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
        return new MpOperVO();
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

    public void setSQLFlag(String flag) {
        SQLFlag = flag;
    }
}
