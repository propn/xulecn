package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RrRegionDAO;
import com.ztesoft.crm.salesres.vo.RrRegionVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RrRegionDAOImpl implements RrRegionDAO {
    private String SQL_SELECT = "SELECT LAN_ID,REGION_ID,REGION_CODE,REGION_NAME FROM RR_REGION";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RR_REGION";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RR_REGION ( LAN_ID,REGION_ID,REGION_CODE,REGION_NAME ) VALUES ( ?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RR_REGION SET  LAN_ID = ?, REGION_CODE = ?, REGION_NAME = ? WHERE REGION_ID = ? ";
    private String SQL_DELETE = "DELETE FROM RR_REGION WHERE REGION_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RR_REGION ";

    public RrRegionDAOImpl() {
    }

    public RrRegionVO findByPrimaryKey(String pREGION_ID)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT + " WHERE REGION_ID = ? ",
                new String[] { pREGION_ID });

        if (arrayList.size() > 0) {
            return (RrRegionVO) arrayList.get(0);
        } else {
            return (RrRegionVO) getEmptyVO();
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
            RrRegionVO vo = new RrRegionVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RrRegionVO vo, ResultSet rs)
        throws SQLException {
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setRegionId(DAOUtils.trimStr(rs.getString("REGION_ID")));
        vo.setRegionCode(DAOUtils.trimStr(rs.getString("REGION_CODE")));
        vo.setRegionName(DAOUtils.trimStr(rs.getString("REGION_NAME")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RrRegionVO vo = new RrRegionVO();

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

            String wherecon = " where ";

            if ((whereCond == null) || (whereCond.length() < 1)) {
                wherecon = " ";
                whereCond = "";
            }

            SQL = SQL_SELECT + wherecon + whereCond;
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

            if ("".equals(((RrRegionVO) vo).getLanId())) {
                ((RrRegionVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RrRegionVO) vo).getLanId());

            if ("".equals(((RrRegionVO) vo).getRegionId())) {
                ((RrRegionVO) vo).setRegionId(null);
            }

            stmt.setString(index++, ((RrRegionVO) vo).getRegionId());
            stmt.setString(index++, ((RrRegionVO) vo).getRegionCode());
            stmt.setString(index++, ((RrRegionVO) vo).getRegionName());

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

    public boolean update(String pREGION_ID, RrRegionVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RR_REGION SET LAN_ID = ?,REGION_ID = ?,REGION_CODE = ?,REGION_NAME = ?");
            sql.append(" WHERE  REGION_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RrRegionVO) vo).getLanId())) {
                ((RrRegionVO) vo).setLanId(null);
            }

            stmt.setString(index++, vo.getLanId());

            if ("".equals(((RrRegionVO) vo).getRegionId())) {
                ((RrRegionVO) vo).setRegionId(null);
            }

            stmt.setString(index++, vo.getRegionId());
            stmt.setString(index++, vo.getRegionCode());
            stmt.setString(index++, vo.getRegionName());
            stmt.setString(index++, pREGION_ID);

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
                "UPDATE RR_REGION SET LAN_ID = ?,REGION_ID = ?,REGION_CODE = ?,REGION_NAME = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RrRegionVO) vo).getLanId())) {
                ((RrRegionVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RrRegionVO) vo).getLanId());

            if ("".equals(((RrRegionVO) vo).getRegionId())) {
                ((RrRegionVO) vo).setRegionId(null);
            }

            stmt.setString(index++, ((RrRegionVO) vo).getRegionId());
            stmt.setString(index++, ((RrRegionVO) vo).getRegionCode());
            stmt.setString(index++, ((RrRegionVO) vo).getRegionName());

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

            String wherecon = " where ";

            if ((whereCond == null) || (whereCond.length() < 1)) {
                wherecon = " ";
                whereCond = "";
            }

            SQL = SQL_SELECT_COUNT + wherecon + whereCond;
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

    public long delete(String pREGION_ID) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pREGION_ID);
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
        return new RrRegionVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String wherecon = " where ";

        if ((whereCond == null) || (whereCond.length() < 1)) {
            wherecon = " ";
            whereCond = "";
        }

        String SQL = SQL_SELECT + wherecon + whereCond;
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
