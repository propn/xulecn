package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.bo.CommonUtilBo;
import com.ztesoft.crm.salesres.dao.RcOrderListDAO;
import com.ztesoft.crm.salesres.vo.RcOrderListVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcOrderListDAOImpl implements RcOrderListDAO {
    private String SQL_SELECT = "SELECT ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,STORAGE_ID_FROM,STORAGE_NAME_FROM,STORAGE_ID_TO,STORAGE_NAME_TO FROM RC_ORDER_LIST";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_ORDER_LIST";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,STORAGE_ID_FROM,STORAGE_NAME_FROM,STORAGE_ID_TO,STORAGE_NAME_TO ) VALUES ( ?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_ORDER_LIST SET  SALES_RESOURCE_ID = ?, RESOURCE_INSTANCE_CODE = ? WHERE ORDER_ID = ? AND RESOURCE_INSTANCE_ID = ? ";
    private String SQL_DELETE = "DELETE FROM RC_ORDER_LIST WHERE ORDER_ID = ? AND RESOURCE_INSTANCE_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_ORDER_LIST ";
    private String SQL_CHECK = "select resource_instance_id,sales_resource_id,resource_instance_code from ? where  SALES_RESOURCE_ID=? and resource_instance_code=?";

    public RcOrderListDAOImpl() {
    }

    public RcOrderListVO findByPrimaryKey(String pORDER_ID,
        String pSALES_RESOURCE_ID) throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE ORDER_ID = ? AND RESOURCE_INSTANCE_ID = ? ",
                new String[] { pORDER_ID, pSALES_RESOURCE_ID });

        if (arrayList.size() > 0) {
            return (RcOrderListVO) arrayList.get(0);
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
            RcOrderListVO vo = new RcOrderListVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcOrderListVO vo, ResultSet rs)
        throws SQLException {
        vo.setOrderId(DAOUtils.trimStr(rs.getString("ORDER_ID")));
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_ID")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_CODE")));
        vo.setStorageIdFrom(DAOUtils.trimStr(rs.getString("STORAGE_ID_FROM")));
        vo.setStorageNameFrom(DAOUtils.trimStr(rs.getString("STORAGE_NAME_FROM")));
        vo.setStorageIdTo(DAOUtils.trimStr(rs.getString("STORAGE_ID_TO")));
        vo.setStorageNameTo(DAOUtils.trimStr(rs.getString("STORAGE_NAME_TO")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcOrderListVO vo = new RcOrderListVO();

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
            System.out.println(vo.toString());
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));

            int index = 1;

            if ("".equals(((RcOrderListVO) vo).getOrderId())) {
                ((RcOrderListVO) vo).setOrderId(null);
            }

            stmt.setString(index++, ((RcOrderListVO) vo).getOrderId());

            if ("".equals(((RcOrderListVO) vo).getRescInstanceId())) {
                ((RcOrderListVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcOrderListVO) vo).getRescInstanceId());

            if ("".equals(((RcOrderListVO) vo).getSalesRescId())) {
                ((RcOrderListVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcOrderListVO) vo).getSalesRescId());
            stmt.setString(index++, ((RcOrderListVO) vo).getRescInstanceCode());

            if ("".equals(((RcOrderListVO) vo).getStorageIdFrom())) {
                ((RcOrderListVO) vo).setStorageIdFrom(null);
            }

            stmt.setString(index++, ((RcOrderListVO) vo).getStorageIdFrom());
            stmt.setString(index++, ((RcOrderListVO) vo).getStorageNameFrom());

            if ("".equals(((RcOrderListVO) vo).getStorageIdTo())) {
                ((RcOrderListVO) vo).setStorageIdTo(null);
            }

            stmt.setString(index++, ((RcOrderListVO) vo).getStorageIdTo());
            stmt.setString(index++, ((RcOrderListVO) vo).getStorageNameTo());

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

    public boolean update(String pORDER_ID, String pRESOURCE_INSTANCE_ID,
        RcOrderListVO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_ORDER_LIST SET ORDER_ID = ?,RESOURCE_INSTANCE_ID = ?,SALES_RESOURCE_ID = ?,RESOURCE_INSTANCE_CODE = ?");
            sql.append(" WHERE  ORDER_ID = ? AND RESOURCE_INSTANCE_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcOrderListVO) vo).getOrderId())) {
                ((RcOrderListVO) vo).setOrderId(null);
            }

            stmt.setString(index++, vo.getOrderId());

            if ("".equals(((RcOrderListVO) vo).getRescInstanceId())) {
                ((RcOrderListVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, vo.getRescInstanceId());

            if ("".equals(((RcOrderListVO) vo).getSalesRescId())) {
                ((RcOrderListVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, vo.getSalesRescId());
            stmt.setString(index++, vo.getRescInstanceCode());
            stmt.setString(index++, pORDER_ID);
            stmt.setString(index++, pRESOURCE_INSTANCE_ID);

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
                "UPDATE RC_ORDER_LIST SET ORDER_ID = ?,RESOURCE_INSTANCE_ID = ?,SALES_RESOURCE_ID = ?,RESOURCE_INSTANCE_CODE = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcOrderListVO) vo).getOrderId())) {
                ((RcOrderListVO) vo).setOrderId(null);
            }

            stmt.setString(index++, ((RcOrderListVO) vo).getOrderId());

            if ("".equals(((RcOrderListVO) vo).getRescInstanceId())) {
                ((RcOrderListVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcOrderListVO) vo).getRescInstanceId());

            if ("".equals(((RcOrderListVO) vo).getSalesRescId())) {
                ((RcOrderListVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcOrderListVO) vo).getSalesRescId());
            stmt.setString(index++, ((RcOrderListVO) vo).getRescInstanceCode());

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
            SQL = SQL_SELECT_COUNT + " WHERE 1=1 " + whereCond;
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

    public long delete(String pORDER_ID, String pRESOURCE_INSTANCE_ID)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pORDER_ID);
            stmt.setString(index++, pRESOURCE_INSTANCE_ID);
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
        return new RcOrderListVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + " WHERE 1=1 " + whereCond;
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

    public String[] checkRcEntity(String salesRescId, String rc)
        throws DAOSystemException {
        String sqlHead = "select resource_instance_id,sales_resource_id,resource_instance_code from ";
        String sqlTail = " where  SALES_RESOURCE_ID=? and resource_instance_code=?";
        String sql = "";

        //String table_name = "RC_ENTITY";
        String table_name = CommonUtilBo.getTableNameByResouceId(salesRescId);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // table_name= (getTableMap(salesRescId).equals("")?table_name:getTableMap(salesRescId));
            sql = sqlHead + table_name + sqlTail;
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setLong(1, Long.parseLong(salesRescId));
            stmt.setString(2, rc);
            rs = stmt.executeQuery();

            String[] s = null;

            if (rs.next()) {
                s = new String[3];
                s[0] = String.valueOf(rs.getLong(1));
                s[1] = String.valueOf(rs.getLong(2));
                s[2] = String.valueOf(rs.getString(3));
            }

            return s;
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

    public String getTableMap(String SalesRescId) throws DAOSystemException {
        System.out.println("############## " + SalesRescId);

        String sql = "SELECT ENTITY_TAB_NAME FROM RC_FAMILY_ENTITY_RELA F,SALES_RESOURCE S WHERE F.FAMILY_ID=S.FAMILY_ID AND S.SALES_RESOURCE_ID=" +
            SalesRescId;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("rs.getstring---------------" +
                    rs.getString(1));

                return rs.getString(1);
            }

            return "";
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

    public String[] checkRcEntity(String salesRescId, String rc,
        String storageId) throws DAOSystemException {
        String sqlHead = "select resource_instance_id,sales_resource_id,resource_instance_code from ";
        String sqlTail = " where  SALES_RESOURCE_ID=? and resource_instance_code=? and storage_id=?";
        String sql = "";
        String table_name = "RC_ENTITY";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            table_name = (getTableMap(salesRescId).equals("") ? table_name
                                                              : getTableMap(salesRescId));
            System.out.println("table name ---> " + table_name);
            sql = sqlHead + table_name + sqlTail;
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setLong(1, Long.parseLong(salesRescId));
            stmt.setString(2, rc);
            stmt.setLong(3, Long.parseLong(storageId));
            rs = stmt.executeQuery();

            String[] s = null;

            if (rs.next()) {
                s = new String[3];
                s[0] = String.valueOf(rs.getLong(1));
                s[1] = String.valueOf(rs.getLong(2));
                s[2] = String.valueOf(rs.getString(3));
            }

            return s;
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
}
