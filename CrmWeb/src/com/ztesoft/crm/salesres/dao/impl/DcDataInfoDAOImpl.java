package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.common.SqlExcuteByStr;
import com.ztesoft.crm.salesres.dao.DcDataInfoDAO;
import com.ztesoft.crm.salesres.vo.DcDataInfoVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class DcDataInfoDAOImpl implements DcDataInfoDAO {
    private String SQL_SELECT = "SELECT info_id,info_name,run_sql,ret_cols,info_type,memo,obj_type,lan_id FROM DCDATAINFO_ODS";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM DCDATAINFO_ODS";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO DCDATAINFO_ODS ( info_id,info_name,run_sql,ret_cols,info_type,memo,obj_type,lan_id ) VALUES ( ?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE DCDATAINFO_ODS SET  info_name = ?, run_sql = ?, ret_cols = ?, info_type = ?, memo = ?, obj_type = ?, lan_id = ? WHERE info_id = ? ";
    private String SQL_DELETE = "DELETE FROM DCDATAINFO_ODS WHERE info_id = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM DCDATAINFO_ODS ";

    public DcDataInfoDAOImpl() {
    }

    public DcDataInfoVO findByPrimaryKey(String pinfo_id)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT + " WHERE info_id = ? ",
                new String[] { pinfo_id });

        if (arrayList.size() > 0) {
            return (DcDataInfoVO) arrayList.get(0);
        } else {
            return (DcDataInfoVO) getEmptyVO();
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
            DcDataInfoVO vo = new DcDataInfoVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(DcDataInfoVO vo, ResultSet rs)
        throws SQLException {
        vo.setInfoId(DAOUtils.trimStr(rs.getString("info_id")));
        vo.setInfoName(DAOUtils.trimStr(rs.getString("info_name")));
        vo.setRunSql(DAOUtils.trimStr(rs.getString("run_sql")));
        vo.setRetCols(DAOUtils.trimStr(rs.getString("ret_cols")));
        vo.setInfoType(DAOUtils.trimStr(rs.getString("info_type")));
        vo.setMemo(DAOUtils.trimStr(rs.getString("memo")));
        vo.setObjType(DAOUtils.trimStr(rs.getString("obj_type")));
        vo.setLanId(DAOUtils.trimStr(rs.getString("lan_id")));

        String lanId = DAOUtils.trimStr(rs.getString("lan_id"));

        if ((lanId.length() >= 1) && (lanId.charAt(0) == ',')) {
            lanId = lanId.substring(1, lanId.length());
        }

        SqlExcuteByStr sqlExcuteByStr = new SqlExcuteByStr();
        vo.setLanIdCN(DAOUtils.trimStr(sqlExcuteByStr.getCNStr(" lan_name ",
                    "lan_id", "rr_lan", lanId)));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        DcDataInfoVO vo = new DcDataInfoVO();

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

            if ("".equals(((DcDataInfoVO) vo).getInfoId())) {
                ((DcDataInfoVO) vo).setInfoId(null);
            }

            stmt.setString(index++, ((DcDataInfoVO) vo).getInfoId());
            stmt.setString(index++, ((DcDataInfoVO) vo).getInfoName());
            stmt.setString(index++, ((DcDataInfoVO) vo).getRunSql());

            if ("".equals(((DcDataInfoVO) vo).getRetCols())) {
                ((DcDataInfoVO) vo).setRetCols(null);
            }

            stmt.setString(index++, ((DcDataInfoVO) vo).getRetCols());

            if ("".equals(((DcDataInfoVO) vo).getInfoType())) {
                ((DcDataInfoVO) vo).setInfoType(null);
            }

            stmt.setString(index++, ((DcDataInfoVO) vo).getInfoType());
            stmt.setString(index++, ((DcDataInfoVO) vo).getMemo());
            stmt.setString(index++, ((DcDataInfoVO) vo).getObjType());
            stmt.setString(index++, ((DcDataInfoVO) vo).getLanId());

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

    public boolean update(String pinfo_id, DcDataInfoVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE DCDATAINFO_ODS SET info_id = ?,info_name = ?,run_sql = ?,ret_cols = ?,info_type = ?,memo = ?,obj_type = ?,lan_id = ?");
            sql.append(" WHERE  info_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((DcDataInfoVO) vo).getInfoId())) {
                ((DcDataInfoVO) vo).setInfoId(null);
            }

            stmt.setString(index++, vo.getInfoId());
            stmt.setString(index++, vo.getInfoName());
            stmt.setString(index++, vo.getRunSql());

            if ("".equals(((DcDataInfoVO) vo).getRetCols())) {
                ((DcDataInfoVO) vo).setRetCols(null);
            }

            stmt.setString(index++, vo.getRetCols());

            if ("".equals(((DcDataInfoVO) vo).getInfoType())) {
                ((DcDataInfoVO) vo).setInfoType(null);
            }

            stmt.setString(index++, vo.getInfoType());
            stmt.setString(index++, vo.getMemo());
            stmt.setString(index++, vo.getObjType());
            stmt.setString(index++, vo.getLanId());
            stmt.setString(index++, pinfo_id);

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
                "UPDATE DCDATAINFO_ODS SET info_id = ?,info_name = ?,run_sql = ?,ret_cols = ?,info_type = ?,memo = ?,obj_type = ?,lan_id = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((DcDataInfoVO) vo).getInfoId())) {
                ((DcDataInfoVO) vo).setInfoId(null);
            }

            stmt.setString(index++, ((DcDataInfoVO) vo).getInfoId());
            stmt.setString(index++, ((DcDataInfoVO) vo).getInfoName());
            stmt.setString(index++, ((DcDataInfoVO) vo).getRunSql());

            if ("".equals(((DcDataInfoVO) vo).getRetCols())) {
                ((DcDataInfoVO) vo).setRetCols(null);
            }

            stmt.setString(index++, ((DcDataInfoVO) vo).getRetCols());

            if ("".equals(((DcDataInfoVO) vo).getInfoType())) {
                ((DcDataInfoVO) vo).setInfoType(null);
            }

            stmt.setString(index++, ((DcDataInfoVO) vo).getInfoType());
            stmt.setString(index++, ((DcDataInfoVO) vo).getMemo());
            stmt.setString(index++, ((DcDataInfoVO) vo).getObjType());
            stmt.setString(index++, ((DcDataInfoVO) vo).getLanId());

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

    public long delete(String pinfo_id) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pinfo_id);
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
        return new DcDataInfoVO();
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
}
