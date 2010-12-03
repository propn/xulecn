package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RescNumDAO;
import com.ztesoft.crm.salesres.vo.RescNumVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RescNumDAOImpl implements RescNumDAO {
    private String SQL_SELECT = "SELECT SALES_RESOURCE_ID,STORAGE_ID,FAMILY_ID,NUM,LAN_ID,PK_CALBODY,CINVENTORYID,VBATCHCODE FROM RESOURCE_NUM";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RESOURCE_NUM";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RESOURCE_NUM ( SALES_RESOURCE_ID,STORAGE_ID,FAMILY_ID,NUM,LAN_ID,PK_CALBODY,CINVENTORYID,VBATCHCODE ) VALUES ( ?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RESOURCE_NUM SET  FAMILY_ID = ?, NUM = ? WHERE CINVENTORYID = ? AND LAN_ID = ? AND PK_CALBODY = ? AND SALES_RESOURCE_ID = ? AND STORAGE_ID = ? AND VBATCHCODE = ? ";
    private String SQL_DELETE = "DELETE FROM RESOURCE_NUM WHERE CINVENTORYID = ? AND LAN_ID = ? AND PK_CALBODY = ? AND SALES_RESOURCE_ID = ? AND STORAGE_ID = ? AND VBATCHCODE = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RESOURCE_NUM ";

    public RescNumDAOImpl() {
    }

    public RescNumVO findByPrimaryKey(String pCINVENTORYID, String pLAN_ID,
        String pPK_CALBODY, String pSALES_RESOURCE_ID, String pSTORAGE_ID,
        String pVBATCHCODE) throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE CINVENTORYID = ? AND LAN_ID = ? AND PK_CALBODY = ? AND SALES_RESOURCE_ID = ? AND STORAGE_ID = ? AND VBATCHCODE = ? ",
                new String[] {
                    pCINVENTORYID, pLAN_ID, pPK_CALBODY, pSALES_RESOURCE_ID,
                    pSTORAGE_ID, pVBATCHCODE
                });

        if (arrayList.size() > 0) {
            return (RescNumVO) arrayList.get(0);
        } else {
            return null;
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
            RescNumVO vo = new RescNumVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RescNumVO vo, ResultSet rs)
        throws SQLException {
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setStorageId(DAOUtils.trimStr(rs.getString("STORAGE_ID")));
        vo.setFamilyId(DAOUtils.trimStr(rs.getString("FAMILY_ID")));
        vo.setNum(DAOUtils.trimStr(rs.getString("NUM")));
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setPkCalbody(DAOUtils.trimStr(rs.getString("PK_CALBODY")));
        vo.setCinventoryid(DAOUtils.trimStr(rs.getString("CINVENTORYID")));
        vo.setVbatchcode(DAOUtils.trimStr(rs.getString("VBATCHCODE")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RescNumVO vo = new RescNumVO();

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

            if ("".equals(((RescNumVO) vo).getSalesRescId())) {
                ((RescNumVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RescNumVO) vo).getSalesRescId());

            if ("".equals(((RescNumVO) vo).getStorageId())) {
                ((RescNumVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RescNumVO) vo).getStorageId());

            if ("".equals(((RescNumVO) vo).getFamilyId())) {
                ((RescNumVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, ((RescNumVO) vo).getFamilyId());

            if ("".equals(((RescNumVO) vo).getNum())) {
                ((RescNumVO) vo).setNum(null);
            }

            stmt.setString(index++, ((RescNumVO) vo).getNum());

            if ("".equals(((RescNumVO) vo).getLanId())) {
                ((RescNumVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RescNumVO) vo).getLanId());
            stmt.setString(index++, ((RescNumVO) vo).getPkCalbody());
            stmt.setString(index++, ((RescNumVO) vo).getCinventoryid());
            stmt.setString(index++, ((RescNumVO) vo).getVbatchcode());

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

    public boolean update(String pCINVENTORYID, String pLAN_ID,
        String pPK_CALBODY, String pSALES_RESOURCE_ID, String pSTORAGE_ID,
        String pVBATCHCODE, RescNumVO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RESOURCE_NUM SET SALES_RESOURCE_ID = ?,STORAGE_ID = ?,FAMILY_ID = ?,NUM = ?,LAN_ID = ?,PK_CALBODY = ?,CINVENTORYID = ?,VBATCHCODE = ?");
            sql.append(
                " WHERE  CINVENTORYID = ? AND LAN_ID = ? AND PK_CALBODY = ? AND SALES_RESOURCE_ID = ? AND STORAGE_ID = ? AND VBATCHCODE = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RescNumVO) vo).getSalesRescId())) {
                ((RescNumVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, vo.getSalesRescId());

            if ("".equals(((RescNumVO) vo).getStorageId())) {
                ((RescNumVO) vo).setStorageId(null);
            }

            stmt.setString(index++, vo.getStorageId());

            if ("".equals(((RescNumVO) vo).getFamilyId())) {
                ((RescNumVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, vo.getFamilyId());

            if ("".equals(((RescNumVO) vo).getNum())) {
                ((RescNumVO) vo).setNum(null);
            }

            stmt.setString(index++, vo.getNum());

            if ("".equals(((RescNumVO) vo).getLanId())) {
                ((RescNumVO) vo).setLanId(null);
            }

            stmt.setString(index++, vo.getLanId());
            stmt.setString(index++, vo.getPkCalbody());
            stmt.setString(index++, vo.getCinventoryid());
            stmt.setString(index++, vo.getVbatchcode());
            stmt.setString(index++, pCINVENTORYID);
            stmt.setString(index++, pLAN_ID);
            stmt.setString(index++, pPK_CALBODY);
            stmt.setString(index++, pSALES_RESOURCE_ID);
            stmt.setString(index++, pSTORAGE_ID);
            stmt.setString(index++, pVBATCHCODE);

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
                "UPDATE RESOURCE_NUM SET SALES_RESOURCE_ID = ?,STORAGE_ID = ?,FAMILY_ID = ?,NUM = ?,LAN_ID = ?,PK_CALBODY = ?,CINVENTORYID = ?,VBATCHCODE = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RescNumVO) vo).getSalesRescId())) {
                ((RescNumVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RescNumVO) vo).getSalesRescId());

            if ("".equals(((RescNumVO) vo).getStorageId())) {
                ((RescNumVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RescNumVO) vo).getStorageId());

            if ("".equals(((RescNumVO) vo).getFamilyId())) {
                ((RescNumVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, ((RescNumVO) vo).getFamilyId());

            if ("".equals(((RescNumVO) vo).getNum())) {
                ((RescNumVO) vo).setNum(null);
            }

            stmt.setString(index++, ((RescNumVO) vo).getNum());

            if ("".equals(((RescNumVO) vo).getLanId())) {
                ((RescNumVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RescNumVO) vo).getLanId());
            stmt.setString(index++, ((RescNumVO) vo).getPkCalbody());
            stmt.setString(index++, ((RescNumVO) vo).getCinventoryid());
            stmt.setString(index++, ((RescNumVO) vo).getVbatchcode());

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

    public long delete(String pCINVENTORYID, String pLAN_ID,
        String pPK_CALBODY, String pSALES_RESOURCE_ID, String pSTORAGE_ID,
        String pVBATCHCODE) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pCINVENTORYID);
            stmt.setString(index++, pLAN_ID);
            stmt.setString(index++, pPK_CALBODY);
            stmt.setString(index++, pSALES_RESOURCE_ID);
            stmt.setString(index++, pSTORAGE_ID);
            stmt.setString(index++, pVBATCHCODE);
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
        return new RescNumVO();
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
