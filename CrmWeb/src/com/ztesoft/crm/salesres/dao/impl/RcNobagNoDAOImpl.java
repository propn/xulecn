package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcNobagNoDAO;
import com.ztesoft.crm.salesres.vo.RcNobagNoVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcNobagNoDAOImpl implements RcNobagNoDAO {
    private String SQL_SELECT = "SELECT a.NOBAG_ID,a.BAGRULE_ID,a.RESOURCE_INSTANCE_ID,a.RESOURCE_INSTANCE_CODE,b.BAGRULE_NAME " +
        " FROM RC_NOBAG_NO a,RC_NOBAG_RULE b where a.BAGRULE_ID=b.BAGRULE_ID ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_NOBAG_NO a,RC_NOBAG_RULE b where a.BAGRULE_ID=b.BAGRULE_ID";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_NOBAG_NO ( NOBAG_ID,BAGRULE_ID,RESOURCE_INSTANCE_ID,RESOURCE_INSTANCE_CODE ) VALUES ( ?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_NOBAG_NO SET  NOBAG_ID = ?, BAGRULE_ID = ?, RESOURCE_INSTANCE_ID = ?, RESOURCE_INSTANCE_CODE = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_NOBAG_NO WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_NOBAG_NO ";
    private int flag = 0;

    public RcNobagNoDAOImpl() {
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
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
            RcNobagNoVO vo = new RcNobagNoVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcNobagNoVO vo, ResultSet rs)
        throws SQLException {
        vo.setNobagId(DAOUtils.trimStr(rs.getString("NOBAG_ID")));
        vo.setBagruleId(DAOUtils.trimStr(rs.getString("BAGRULE_ID")));
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_ID")));
        vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_CODE")));
        vo.setBagruleName(DAOUtils.trimStr(rs.getString("BAGRULE_NAME")));

        if (flag == 1) {
            vo.setNoNum(DAOUtils.trimStr(rs.getString("noNum")));
        }
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcNobagNoVO vo = new RcNobagNoVO();

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

            if ("".equals(((RcNobagNoVO) vo).getNobagId())) {
                ((RcNobagNoVO) vo).setNobagId(null);
            }

            stmt.setString(index++, ((RcNobagNoVO) vo).getNobagId());

            if ("".equals(((RcNobagNoVO) vo).getBagruleId())) {
                ((RcNobagNoVO) vo).setBagruleId(null);
            }

            stmt.setString(index++, ((RcNobagNoVO) vo).getBagruleId());

            if ("".equals(((RcNobagNoVO) vo).getRescInstanceId())) {
                ((RcNobagNoVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcNobagNoVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcNobagNoVO) vo).getRescInstanceCode());

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
                "UPDATE RC_NOBAG_NO SET NOBAG_ID = ?,BAGRULE_ID = ?,RESOURCE_INSTANCE_ID = ?,RESOURCE_INSTANCE_CODE = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNobagNoVO) vo).getNobagId())) {
                ((RcNobagNoVO) vo).setNobagId(null);
            }

            stmt.setString(index++, ((RcNobagNoVO) vo).getNobagId());

            if ("".equals(((RcNobagNoVO) vo).getBagruleId())) {
                ((RcNobagNoVO) vo).setBagruleId(null);
            }

            stmt.setString(index++, ((RcNobagNoVO) vo).getBagruleId());

            if ("".equals(((RcNobagNoVO) vo).getRescInstanceId())) {
                ((RcNobagNoVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcNobagNoVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcNobagNoVO) vo).getRescInstanceCode());

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
        return new RcNobagNoVO();
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

    public String getSQL_SELECT() {
        return SQL_SELECT;
    }

    public void setSQL_SELECT(String sql_select) {
        SQL_SELECT = sql_select;
    }

    public String getSQL_SELECT_COUNT() {
        return SQL_SELECT_COUNT;
    }

    public void setSQL_SELECT_COUNT(String sql_select_count) {
        SQL_SELECT_COUNT = sql_select_count;
    }
}