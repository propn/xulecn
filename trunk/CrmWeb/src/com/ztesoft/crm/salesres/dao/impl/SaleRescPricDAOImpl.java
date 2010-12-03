package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.SaleRescPricDAO;
import com.ztesoft.crm.salesres.vo.SaleRescPricVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class SaleRescPricDAOImpl implements SaleRescPricDAO {
    private String SQL_SELECT = "SELECT a.sales_resource_id,a.business_id,a.resource_level,a.price,a.acct_item_id,a.price2,a.price3,b.business_name,c.feeitem_name " +
        " FROM SALE_RESOURCE_PRIC a left outer join RR_BUSINESS b on a.business_id=b.business_id left outer join df_fee_item c on a.acct_item_id = c.feeitem_code where 1=1";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM SALE_RESOURCE_PRIC a left outer join RR_BUSINESS b on a.business_id=b.business_id left outer join df_fee_item c on a.acct_item_id = c.feeitem_code where 1=1";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO SALE_RESOURCE_PRIC ( sales_resource_id,business_id,resource_level,price,acct_item_id,price2,price3 ) VALUES ( ?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE SALE_RESOURCE_PRIC SET  price = ?, acct_item_id = ?,price2=?,price3=? WHERE business_id = ? AND resource_level = ? AND sales_resource_id = ? ";
    private String SQL_DELETE = "DELETE FROM SALE_RESOURCE_PRIC WHERE business_id = ? AND resource_level = ? AND sales_resource_id = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM SALE_RESOURCE_PRIC ";

    public SaleRescPricDAOImpl() {
    }

    public SaleRescPricVO findByPrimaryKey(String pbusiness_id,
        String presource_level, String psales_resource_id)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " and a.business_id = ? AND a.resource_level = ? AND a.sales_resource_id = ? ",
                new String[] { pbusiness_id, presource_level, psales_resource_id });

        if (arrayList.size() > 0) {
            return (SaleRescPricVO) arrayList.get(0);
        } else {
            return (SaleRescPricVO) getEmptyVO();
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
            SaleRescPricVO vo = new SaleRescPricVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(SaleRescPricVO vo, ResultSet rs)
        throws SQLException {
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
        vo.setBusinessId(DAOUtils.trimStr(rs.getString("business_id")));
        vo.setRescLevel(DAOUtils.trimStr(rs.getString("resource_level")));
        vo.setPrice(DAOUtils.trimStr(rs.getString("price")));
        vo.setAcctItemId(DAOUtils.trimStr(rs.getString("acct_item_id")));
        vo.setBusinessName(DAOUtils.trimStr(rs.getString("business_name")));
        vo.setFeeitemName(DAOUtils.trimStr(rs.getString("feeitem_name")));
        vo.setPrice2(DAOUtils.trimStr(rs.getString("price2")));
        vo.setPrice3(DAOUtils.trimStr(rs.getString("price3")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        SaleRescPricVO vo = new SaleRescPricVO();

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

            if ("".equals(((SaleRescPricVO) vo).getSalesRescId())) {
                ((SaleRescPricVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getSalesRescId());

            if ("".equals(((SaleRescPricVO) vo).getBusinessId())) {
                ((SaleRescPricVO) vo).setBusinessId(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getBusinessId());

            if ("".equals(((SaleRescPricVO) vo).getRescLevel())) {
                ((SaleRescPricVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getRescLevel());

            if ("".equals(((SaleRescPricVO) vo).getPrice())) {
                ((SaleRescPricVO) vo).setPrice(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getPrice());

            if ("".equals(((SaleRescPricVO) vo).getAcctItemId())) {
                ((SaleRescPricVO) vo).setAcctItemId(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getAcctItemId());

            if ("".equals(((SaleRescPricVO) vo).getPrice2())) {
                ((SaleRescPricVO) vo).setPrice2(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getPrice2());

            if ("".equals(((SaleRescPricVO) vo).getPrice3())) {
                ((SaleRescPricVO) vo).setPrice3(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getPrice3());

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

    public boolean update(String pbusiness_id, String presource_level,
        String psales_resource_id, SaleRescPricVO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE SALE_RESOURCE_PRIC SET sales_resource_id = ?,business_id = ?,resource_level = ?,price = ?,acct_item_id = ?,price2 = ?,price3 = ? ");
            sql.append(
                " WHERE  business_id = ? AND resource_level = ? AND sales_resource_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((SaleRescPricVO) vo).getSalesRescId())) {
                ((SaleRescPricVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, vo.getSalesRescId());

            if ("".equals(((SaleRescPricVO) vo).getBusinessId())) {
                ((SaleRescPricVO) vo).setBusinessId(null);
            }

            stmt.setString(index++, vo.getBusinessId());

            if ("".equals(((SaleRescPricVO) vo).getRescLevel())) {
                ((SaleRescPricVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, vo.getRescLevel());

            if ("".equals(((SaleRescPricVO) vo).getPrice())) {
                ((SaleRescPricVO) vo).setPrice(null);
            }

            stmt.setString(index++, vo.getPrice());

            if ("".equals(((SaleRescPricVO) vo).getAcctItemId())) {
                ((SaleRescPricVO) vo).setAcctItemId(null);
            }

            stmt.setString(index++, vo.getAcctItemId());

            if ("".equals(((SaleRescPricVO) vo).getPrice2())) {
                ((SaleRescPricVO) vo).setPrice2(null);
            }

            stmt.setString(index++, vo.getPrice2());

            if ("".equals(((SaleRescPricVO) vo).getPrice3())) {
                ((SaleRescPricVO) vo).setPrice3(null);
            }

            stmt.setString(index++, vo.getPrice3());

            stmt.setString(index++, pbusiness_id);
            stmt.setString(index++, presource_level);
            stmt.setString(index++, psales_resource_id);

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
                "UPDATE SALE_RESOURCE_PRIC SET sales_resource_id = ?,business_id = ?,resource_level = ?,price = ?,acct_item_id = ?,price2 = ?,price3 = ? ");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((SaleRescPricVO) vo).getSalesRescId())) {
                ((SaleRescPricVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getSalesRescId());

            if ("".equals(((SaleRescPricVO) vo).getBusinessId())) {
                ((SaleRescPricVO) vo).setBusinessId(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getBusinessId());

            if ("".equals(((SaleRescPricVO) vo).getRescLevel())) {
                ((SaleRescPricVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getRescLevel());

            if ("".equals(((SaleRescPricVO) vo).getPrice())) {
                ((SaleRescPricVO) vo).setPrice(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getPrice());

            if ("".equals(((SaleRescPricVO) vo).getAcctItemId())) {
                ((SaleRescPricVO) vo).setAcctItemId(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getAcctItemId());

            if ("".equals(((SaleRescPricVO) vo).getPrice2())) {
                ((SaleRescPricVO) vo).setPrice2(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getPrice2());

            if ("".equals(((SaleRescPricVO) vo).getPrice3())) {
                ((SaleRescPricVO) vo).setPrice3(null);
            }

            stmt.setString(index++, ((SaleRescPricVO) vo).getPrice3());

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

    public long delete(String pbusiness_id, String presource_level,
        String psales_resource_id) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pbusiness_id);
            stmt.setString(index++, presource_level);
            stmt.setString(index++, psales_resource_id);
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
        return new SaleRescPricVO();
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

    public void setSQL_SELECT(String sql_select) {
        SQL_SELECT = sql_select;
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

    public void setSQL_SELECT_COUNT(String sql_select_count) {
        SQL_SELECT_COUNT = sql_select_count;
    }

    public void setSQL_UPDATE(String sql_update) {
        SQL_UPDATE = sql_update;
    }
}
