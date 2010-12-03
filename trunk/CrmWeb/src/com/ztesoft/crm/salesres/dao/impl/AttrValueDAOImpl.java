package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.AttrValueDAO;
import com.ztesoft.crm.salesres.vo.AttrValueVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class AttrValueDAOImpl implements AttrValueDAO {
    private String SQL_SELECT = "SELECT attr_value_id,attr_id,attr_value,attr_value_desc,sortby FROM ATTRIBUTE_VALUE";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM ATTRIBUTE_VALUE";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO ATTRIBUTE_VALUE ( attr_value_id,attr_id,attr_value,attr_value_desc,sortby ) VALUES ( ?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE ATTRIBUTE_VALUE SET  attr_id = ?, attr_value = ?, attr_value_desc = ?, sortby = ? WHERE attr_value_id = ? ";
    private String SQL_DELETE = "DELETE FROM ATTRIBUTE_VALUE WHERE attr_value_id = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM ATTRIBUTE_VALUE ";
    private String MULTI_TAB_SELECT = "select a.*, c.attr_name, b.offer_a_id,b.offer_z_id," +
        " b.relation_type_id from  ATTRIBUTE_value a join ATTRIBUTE c on a.attr_id=c.attr_id " +
        " left join OFFER_RELATION_ATTR_VAL b on a.attr_id=b.attr_id and a.attr_value = b.attr_value ";

    public AttrValueDAOImpl() {
    }

    public AttrValueVO findByPrimaryKey(String pattr_value_id)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE attr_value_id = ? ", new String[] { pattr_value_id });

        if (arrayList.size() > 0) {
            return (AttrValueVO) arrayList.get(0);
        } else {
            return (AttrValueVO) getEmptyVO();
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
            AttrValueVO vo = new AttrValueVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected ArrayList fetchMultiResults2(ResultSet rs)
        throws SQLException {
        ArrayList resultList = new ArrayList();

        while (rs.next()) {
            AttrValueVO vo = new AttrValueVO();
            populateDto2(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(AttrValueVO vo, ResultSet rs)
        throws SQLException {
        vo.setAttrValueId(rs.getString("attr_value_id"));
        vo.setAttrId(rs.getString("attr_id"));
        vo.setAttrValue(rs.getString("attr_value"));
        vo.setAttrValueDesc(rs.getString("attr_value_desc"));
        vo.setSortby(rs.getString("sortby"));
        vo.setFactorValue(rs.getString("attr_value"));
        vo.setFactorName(rs.getString("attr_value_desc"));
    }

    protected void populateDto2(AttrValueVO vo, ResultSet rs)
        throws SQLException {
        vo.setAttrValueId(rs.getString("attr_value_id"));
        vo.setAttrId(rs.getString("attr_id"));
        vo.setAttrValue(rs.getString("attr_value"));
        vo.setAttrValueDesc(rs.getString("attr_value_desc"));
        vo.setSortby(rs.getString("sortby"));
        vo.setAttrName(rs.getString("attr_name"));
        vo.setOffAId(rs.getString("offer_a_id"));
        vo.setOffZId(rs.getString("offer_z_id"));
        vo.setRelationTypeId(rs.getString("relation_type_id"));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        AttrValueVO vo = new AttrValueVO();

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

    public ArrayList multiTabQry(String whereCond) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = MULTI_TAB_SELECT + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setMaxRows(maxRows);
            rs = stmt.executeQuery();

            return fetchMultiResults2(rs);
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

            if ("".equals(((AttrValueVO) vo).getAttrValueId())) {
                ((AttrValueVO) vo).setAttrValueId(null);
            }

            stmt.setString(index++, ((AttrValueVO) vo).getAttrValueId());

            if ("".equals(((AttrValueVO) vo).getAttrId())) {
                ((AttrValueVO) vo).setAttrId(null);
            }

            stmt.setString(index++, ((AttrValueVO) vo).getAttrId());
            stmt.setString(index++, ((AttrValueVO) vo).getAttrValue());
            stmt.setString(index++, ((AttrValueVO) vo).getAttrValueDesc());

            if ("".equals(((AttrValueVO) vo).getSortby())) {
                ((AttrValueVO) vo).setSortby(null);
            }

            stmt.setString(index++, ((AttrValueVO) vo).getSortby());

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

    public boolean update(String pattr_value_id, AttrValueVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE ATTRIBUTE_VALUE SET attr_value_id = ?,attr_id = ?,attr_value = ?,attr_value_desc = ?,sortby = ?");
            sql.append(" WHERE  attr_value_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((AttrValueVO) vo).getAttrValueId())) {
                ((AttrValueVO) vo).setAttrValueId(null);
            }

            stmt.setString(index++, vo.getAttrValueId());

            if ("".equals(((AttrValueVO) vo).getAttrId())) {
                ((AttrValueVO) vo).setAttrId(null);
            }

            stmt.setString(index++, vo.getAttrId());
            stmt.setString(index++, vo.getAttrValue());
            stmt.setString(index++, vo.getAttrValueDesc());

            if ("".equals(((AttrValueVO) vo).getSortby())) {
                ((AttrValueVO) vo).setSortby(null);
            }

            stmt.setString(index++, vo.getSortby());
            stmt.setString(index++, pattr_value_id);

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
                "UPDATE ATTRIBUTE_VALUE SET attr_value_id = ?,attr_id = ?,attr_value = ?,attr_value_desc = ?,sortby = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((AttrValueVO) vo).getAttrValueId())) {
                ((AttrValueVO) vo).setAttrValueId(null);
            }

            stmt.setString(index++, ((AttrValueVO) vo).getAttrValueId());

            if ("".equals(((AttrValueVO) vo).getAttrId())) {
                ((AttrValueVO) vo).setAttrId(null);
            }

            stmt.setString(index++, ((AttrValueVO) vo).getAttrId());
            stmt.setString(index++, ((AttrValueVO) vo).getAttrValue());
            stmt.setString(index++, ((AttrValueVO) vo).getAttrValueDesc());

            if ("".equals(((AttrValueVO) vo).getSortby())) {
                ((AttrValueVO) vo).setSortby(null);
            }

            stmt.setString(index++, ((AttrValueVO) vo).getSortby());

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

    public long delete(String pattr_value_id) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pattr_value_id);
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
        return new AttrValueVO();
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
