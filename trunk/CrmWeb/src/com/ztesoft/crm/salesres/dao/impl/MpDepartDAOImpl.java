package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.MpDepartDAO;
import com.ztesoft.crm.salesres.vo.MpDepartVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class MpDepartDAOImpl implements MpDepartDAO {
    private String SQL_SELECT = "SELECT DEPART_ID,DEPART_CODE,DEPART_TYPE,DEPART_NAME,SUPER_ID,TOWN_ID,REGION_ID,TERM_FLAG,UNIT_TYPE,UNIT_CODE,VALID_FLAG,COMMENTS FROM MP_DEPARTMENT";
    private String SQL_SELECT2 = "SELECT a.DEPART_ID,a.DEPART_CODE,a.DEPART_TYPE,a.DEPART_NAME,a.SUPER_ID,a.TOWN_ID,a.REGION_ID,a.TERM_FLAG,a.UNIT_TYPE,a.UNIT_CODE,a.VALID_FLAG,a.COMMENTS  " +
        " FROM MP_DEPARTMENT a,mp_operator_depart b where a.DEPART_ID=b.DEPART_ID ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM MP_DEPARTMENT";
    private String SQL_SELECT_COUNT2 = "SELECT COUNT(*) AS COL_COUNTS  FROM MP_DEPARTMENT a,mp_operator_depart b where a.DEPART_ID=b.DEPART_ID ";

    /** 如果为2，则使用有权限过滤的sql语句 **/
    private int flag = 1;
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO MP_DEPARTMENT ( DEPART_ID,DEPART_CODE,DEPART_TYPE,DEPART_NAME,SUPER_ID,TOWN_ID,REGION_ID,TERM_FLAG,UNIT_TYPE,UNIT_CODE,VALID_FLAG,COMMENTS ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE MP_DEPARTMENT SET  DEPART_CODE = ?, DEPART_TYPE = ?, DEPART_NAME = ?, SUPER_ID = ?, TOWN_ID = ?, REGION_ID = ?, TERM_FLAG = ?, UNIT_TYPE = ?, UNIT_CODE = ?, VALID_FLAG = ?, COMMENTS = ?  WHERE DEPART_ID = ? ";
    private String SQL_DELETE = "DELETE FROM MP_DEPARTMENT WHERE DEPART_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM MP_DEPARTMENT ";

    public MpDepartDAOImpl() {
    }

    public MpDepartVO findByPrimaryKey(String pDEPART_ID)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT + " WHERE DEPART_ID = ? ",
                new String[] { pDEPART_ID });

        if (arrayList.size() > 0) {
            return (MpDepartVO) arrayList.get(0);
        } else {
            return (MpDepartVO) getEmptyVO();
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
            MpDepartVO vo = new MpDepartVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(MpDepartVO vo, ResultSet rs)
        throws SQLException {
        vo.setDepartId(DAOUtils.trimStr(rs.getString("DEPART_ID")));
        vo.setDepartCode(DAOUtils.trimStr(rs.getString("DEPART_CODE")));
        vo.setDepartType(DAOUtils.trimStr(rs.getString("DEPART_TYPE")));
        vo.setDepartName(DAOUtils.trimStr(rs.getString("DEPART_NAME")));
        vo.setSuperId(DAOUtils.trimStr(rs.getString("SUPER_ID")));
        vo.setTownId(DAOUtils.trimStr(rs.getString("TOWN_ID")));
        vo.setRegionId(DAOUtils.trimStr(rs.getString("REGION_ID")));
        vo.setTermFlag(DAOUtils.trimStr(rs.getString("TERM_FLAG")));
        vo.setUnitType(DAOUtils.trimStr(rs.getString("UNIT_TYPE")));
        vo.setUnitCode(DAOUtils.trimStr(rs.getString("UNIT_CODE")));
        vo.setValidFlag(DAOUtils.trimStr(rs.getString("VALID_FLAG")));
        vo.setComments(DAOUtils.trimStr(rs.getString("COMMENTS")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        MpDepartVO vo = new MpDepartVO();

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

            String sql_select_use = SQL_SELECT;
            String wherecon = " where ";

            if ((whereCond == null) || (whereCond.length() < 1)) {
                wherecon = " ";
                whereCond = "";
            }

            if (flag == 2) {
                wherecon = " ";
                sql_select_use = SQL_SELECT2;
            }

            SQL = sql_select_use + wherecon + whereCond;
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

            if ("".equals(((MpDepartVO) vo).getDepartId())) {
                ((MpDepartVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((MpDepartVO) vo).getDepartId());
            stmt.setString(index++, ((MpDepartVO) vo).getDepartCode());
            stmt.setString(index++, ((MpDepartVO) vo).getDepartType());
            stmt.setString(index++, ((MpDepartVO) vo).getDepartName());

            if ("".equals(((MpDepartVO) vo).getSuperId())) {
                ((MpDepartVO) vo).setSuperId(null);
            }

            stmt.setString(index++, ((MpDepartVO) vo).getSuperId());

            if ("".equals(((MpDepartVO) vo).getTownId())) {
                ((MpDepartVO) vo).setTownId(null);
            }

            stmt.setString(index++, ((MpDepartVO) vo).getTownId());

            if ("".equals(((MpDepartVO) vo).getRegionId())) {
                ((MpDepartVO) vo).setRegionId(null);
            }

            stmt.setString(index++, ((MpDepartVO) vo).getRegionId());
            stmt.setString(index++, ((MpDepartVO) vo).getTermFlag());
            stmt.setString(index++, ((MpDepartVO) vo).getUnitType());
            stmt.setString(index++, ((MpDepartVO) vo).getUnitCode());
            stmt.setString(index++, ((MpDepartVO) vo).getValidFlag());
            stmt.setString(index++, ((MpDepartVO) vo).getComments());
            stmt.setString(index++, ((MpDepartVO) vo).getDepartBelong());

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

    public boolean update(String pDEPART_ID, MpDepartVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE MP_DEPARTMENT SET DEPART_ID = ?,DEPART_CODE = ?,DEPART_TYPE = ?,DEPART_NAME = ?,SUPER_ID = ?,TOWN_ID = ?,REGION_ID = ?,TERM_FLAG = ?,UNIT_TYPE = ?,UNIT_CODE = ?,VALID_FLAG = ?,COMMENTS = ?");
            sql.append(" WHERE  DEPART_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((MpDepartVO) vo).getDepartId())) {
                ((MpDepartVO) vo).setDepartId(null);
            }

            stmt.setString(index++, vo.getDepartId());
            stmt.setString(index++, vo.getDepartCode());
            stmt.setString(index++, vo.getDepartType());
            stmt.setString(index++, vo.getDepartName());

            if ("".equals(((MpDepartVO) vo).getSuperId())) {
                ((MpDepartVO) vo).setSuperId(null);
            }

            stmt.setString(index++, vo.getSuperId());

            if ("".equals(((MpDepartVO) vo).getTownId())) {
                ((MpDepartVO) vo).setTownId(null);
            }

            stmt.setString(index++, vo.getTownId());

            if ("".equals(((MpDepartVO) vo).getRegionId())) {
                ((MpDepartVO) vo).setRegionId(null);
            }

            stmt.setString(index++, vo.getRegionId());
            stmt.setString(index++, vo.getTermFlag());
            stmt.setString(index++, vo.getUnitType());
            stmt.setString(index++, vo.getUnitCode());
            stmt.setString(index++, vo.getValidFlag());
            stmt.setString(index++, vo.getComments());
            stmt.setString(index++, vo.getDepartBelong());
            stmt.setString(index++, pDEPART_ID);

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
                "UPDATE MP_DEPARTMENT SET DEPART_ID = ?,DEPART_CODE = ?,DEPART_TYPE = ?,DEPART_NAME = ?,SUPER_ID = ?,TOWN_ID = ?,REGION_ID = ?,TERM_FLAG = ?,UNIT_TYPE = ?,UNIT_CODE = ?,VALID_FLAG = ?,COMMENTS = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((MpDepartVO) vo).getDepartId())) {
                ((MpDepartVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((MpDepartVO) vo).getDepartId());
            stmt.setString(index++, ((MpDepartVO) vo).getDepartCode());
            stmt.setString(index++, ((MpDepartVO) vo).getDepartType());
            stmt.setString(index++, ((MpDepartVO) vo).getDepartName());

            if ("".equals(((MpDepartVO) vo).getSuperId())) {
                ((MpDepartVO) vo).setSuperId(null);
            }

            stmt.setString(index++, ((MpDepartVO) vo).getSuperId());

            if ("".equals(((MpDepartVO) vo).getTownId())) {
                ((MpDepartVO) vo).setTownId(null);
            }

            stmt.setString(index++, ((MpDepartVO) vo).getTownId());

            if ("".equals(((MpDepartVO) vo).getRegionId())) {
                ((MpDepartVO) vo).setRegionId(null);
            }

            stmt.setString(index++, ((MpDepartVO) vo).getRegionId());
            stmt.setString(index++, ((MpDepartVO) vo).getTermFlag());
            stmt.setString(index++, ((MpDepartVO) vo).getUnitType());
            stmt.setString(index++, ((MpDepartVO) vo).getUnitCode());
            stmt.setString(index++, ((MpDepartVO) vo).getValidFlag());
            stmt.setString(index++, ((MpDepartVO) vo).getComments());
            stmt.setString(index++, ((MpDepartVO) vo).getDepartBelong());

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

            String sql_select_count_use = SQL_SELECT_COUNT;
            String wherecon = " where ";

            if ((whereCond == null) || (whereCond.length() < 1)) {
                wherecon = " ";
                whereCond = "";
            }

            if (flag == 2) {
                wherecon = " ";
                sql_select_count_use = SQL_SELECT_COUNT2;
            }

            SQL = sql_select_count_use + wherecon + whereCond;
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

            String wherecon = " where ";

            if ((whereCond == null) || (whereCond.length() < 1)) {
                wherecon = " ";
                whereCond = "";
            }

            SQL = SQL_DELETE_BY_WHERE + wherecon + whereCond;
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

    public long delete(String pDEPART_ID) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pDEPART_ID);
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

    public int getFlag() {
        return flag;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public VO getEmptyVO() {
        return new MpDepartVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql_select_use = SQL_SELECT;
        String wherecon = " where ";

        if ((whereCond == null) || (whereCond.length() < 1)) {
            wherecon = " ";
            whereCond = "";
        }

        if (flag == 2) {
            wherecon = " ";
            sql_select_use = SQL_SELECT2;
        }

        String SQL = sql_select_use + wherecon + whereCond;
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

    public void setSQL_DELETE(String sql_delete) {
        SQL_DELETE = sql_delete;
    }

    public void setSQL_DELETE_BY_WHERE(String sql_delete_by_where) {
        SQL_DELETE_BY_WHERE = sql_delete_by_where;
    }

    public void setSQL_INSERT(String sql_insert) {
        SQL_INSERT = sql_insert;
    }

    public void setSQL_SELECT(String sql_select) {
        SQL_SELECT = sql_select;
    }

    public void setSQL_SELECT_COUNT(String sql_select_count) {
        SQL_SELECT_COUNT = sql_select_count;
    }

    public void setSQL_SELECT_COUNT2(String sql_select_count2) {
        SQL_SELECT_COUNT2 = sql_select_count2;
    }

    public void setSQL_SELECT2(String sql_select2) {
        SQL_SELECT2 = sql_select2;
    }

    public void setSQL_UPDATE(String sql_update) {
        SQL_UPDATE = sql_update;
    }
}
