package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.AttrDAO;
import com.ztesoft.crm.salesres.vo.AttrVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class AttrDAOImpl implements AttrDAO {
    //private String SQL_SELECT = "SELECT attr_id,attr_value_unit_id,attr_name,attr_desc,value_type,input_method,min_value,max_value,display_flag,state,eff_date,exp_date,if_default_value,'' nullable,default_value,attr_short_code,attr_code,value_max_len,value_allow_edit,value_kind FROM ATTRIBUTE";
    private String SQL_SELECT = "SELECT attr_id,'' as attr_value_unit_id,attr_name,attr_desc,value_type,input_method,min_value,max_value,display_flag,state,eff_date,exp_date,if_default_value,'' nullable,default_value,'' as attr_short_code,attr_code,'' as value_max_len,'' as value_allow_edit,'' as value_kind FROM ATTRIBUTE";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM ATTRIBUTE";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO ATTRIBUTE ( attr_id,attr_value_unit_id,attr_name,attr_desc,value_type,input_method,min_value,max_value,display_flag,state,eff_date,exp_date,if_default_value,nullable,default_value,attr_short_code,attr_code,value_max_len,value_allow_edit,value_kind ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE ATTRIBUTE SET  attr_value_unit_id = ?, attr_name = ?, attr_desc = ?, value_type = ?, input_method = ?, min_value = ?, max_value = ?, display_flag = ?, state = ?, eff_date = ?, exp_date = ?, if_default_value = ?, nullable = ?, default_value = ?, attr_short_code = ?, attr_code = ?, value_max_len = ?, value_allow_edit = ?, value_kind = ? WHERE attr_id = ? ";
    private String SQL_DELETE = "DELETE FROM ATTRIBUTE WHERE attr_id = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM ATTRIBUTE ";

    public AttrDAOImpl() {
    }

    public AttrVO findByPrimaryKey(String pattr_id) throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT + " WHERE attr_id = ? ",
                new String[] { pattr_id });

        if (arrayList.size() > 0) {
            return (AttrVO) arrayList.get(0);
        } else {
            return (AttrVO) getEmptyVO();
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
            AttrVO vo = new AttrVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(AttrVO vo, ResultSet rs)
        throws SQLException {
        vo.setAttrId(DAOUtils.trimStr(rs.getString("attr_id")));
        vo.setAttrValueUnitId(DAOUtils.trimStr(rs.getString(
                    "attr_value_unit_id")));
        vo.setAttrName(DAOUtils.trimStr(rs.getString("attr_name")));
        vo.setAttrDesc(DAOUtils.trimStr(rs.getString("attr_desc")));
        vo.setValueType(DAOUtils.trimStr(rs.getString("value_type")));
        vo.setInputMethod(DAOUtils.trimStr(rs.getString("input_method")));
        vo.setMinValue(DAOUtils.trimStr(rs.getString("min_value")));
        vo.setMaxValue(DAOUtils.trimStr(rs.getString("max_value")));
        vo.setDisplayFlag(DAOUtils.trimStr(rs.getString("display_flag")));
        vo.setState(DAOUtils.trimStr(rs.getString("state")));
        vo.setEffDate(DAOUtils.getFormatedDateTime(rs.getDate("eff_date")));
        vo.setExpDate(DAOUtils.getFormatedDateTime(rs.getDate("exp_date")));
        vo.setIfDefaultValue(DAOUtils.trimStr(rs.getString("if_default_value")));
        vo.setNullable(DAOUtils.trimStr(rs.getString("nullable")));
        vo.setDefaultValue(DAOUtils.trimStr(rs.getString("default_value")));
        vo.setAttrShortCode(DAOUtils.trimStr(rs.getString("attr_short_code")));
        vo.setAttrCode(DAOUtils.trimStr(rs.getString("attr_code")));
        vo.setValueMaxLen(DAOUtils.trimStr(rs.getString("value_max_len")));
        vo.setValueAllowEdit(DAOUtils.trimStr(rs.getString("value_allow_edit")));
        vo.setValueKind(DAOUtils.trimStr(rs.getString("value_kind")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        AttrVO vo = new AttrVO();

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

            if ("".equals(((AttrVO) vo).getAttrId())) {
                ((AttrVO) vo).setAttrId(null);
            }

            stmt.setString(index++, ((AttrVO) vo).getAttrId());

            if ("".equals(((AttrVO) vo).getAttrValueUnitId())) {
                ((AttrVO) vo).setAttrValueUnitId(null);
            }

            stmt.setString(index++, ((AttrVO) vo).getAttrValueUnitId());
            stmt.setString(index++, ((AttrVO) vo).getAttrName());
            stmt.setString(index++, ((AttrVO) vo).getAttrDesc());
            stmt.setString(index++, ((AttrVO) vo).getValueType());
            stmt.setString(index++, ((AttrVO) vo).getInputMethod());
            stmt.setString(index++, ((AttrVO) vo).getMinValue());
            stmt.setString(index++, ((AttrVO) vo).getMaxValue());
            stmt.setString(index++, ((AttrVO) vo).getDisplayFlag());
            stmt.setString(index++, ((AttrVO) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((AttrVO) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((AttrVO) vo).getExpDate()));
            stmt.setString(index++, ((AttrVO) vo).getIfDefaultValue());
            stmt.setString(index++, ((AttrVO) vo).getNullable());
            stmt.setString(index++, ((AttrVO) vo).getDefaultValue());
            stmt.setString(index++, ((AttrVO) vo).getAttrShortCode());
            stmt.setString(index++, ((AttrVO) vo).getAttrCode());

            if ("".equals(((AttrVO) vo).getValueMaxLen())) {
                ((AttrVO) vo).setValueMaxLen(null);
            }

            stmt.setString(index++, ((AttrVO) vo).getValueMaxLen());
            stmt.setString(index++, ((AttrVO) vo).getValueAllowEdit());
            stmt.setString(index++, ((AttrVO) vo).getValueKind());

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

    public boolean update(String pattr_id, AttrVO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE ATTRIBUTE SET attr_id = ?,attr_value_unit_id = ?,attr_name = ?,attr_desc = ?,value_type = ?,input_method = ?,min_value = ?,max_value = ?,display_flag = ?,state = ?,eff_date = ?,exp_date = ?,if_default_value = ?,nullable = ?,default_value = ?,attr_short_code = ?,attr_code = ?,value_max_len = ?,value_allow_edit = ?,value_kind = ?");
            sql.append(" WHERE  attr_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((AttrVO) vo).getAttrId())) {
                ((AttrVO) vo).setAttrId(null);
            }

            stmt.setString(index++, vo.getAttrId());

            if ("".equals(((AttrVO) vo).getAttrValueUnitId())) {
                ((AttrVO) vo).setAttrValueUnitId(null);
            }

            stmt.setString(index++, vo.getAttrValueUnitId());
            stmt.setString(index++, vo.getAttrName());
            stmt.setString(index++, vo.getAttrDesc());
            stmt.setString(index++, vo.getValueType());
            stmt.setString(index++, vo.getInputMethod());
            stmt.setString(index++, vo.getMinValue());
            stmt.setString(index++, vo.getMaxValue());
            stmt.setString(index++, vo.getDisplayFlag());
            stmt.setString(index++, vo.getState());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getEffDate()));
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getExpDate()));
            stmt.setString(index++, vo.getIfDefaultValue());
            stmt.setString(index++, vo.getNullable());
            stmt.setString(index++, vo.getDefaultValue());
            stmt.setString(index++, vo.getAttrShortCode());
            stmt.setString(index++, vo.getAttrCode());

            if ("".equals(((AttrVO) vo).getValueMaxLen())) {
                ((AttrVO) vo).setValueMaxLen(null);
            }

            stmt.setString(index++, vo.getValueMaxLen());
            stmt.setString(index++, vo.getValueAllowEdit());
            stmt.setString(index++, vo.getValueKind());
            stmt.setString(index++, pattr_id);

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
                "UPDATE ATTRIBUTE SET attr_id = ?,attr_value_unit_id = ?,attr_name = ?,attr_desc = ?,value_type = ?,input_method = ?,min_value = ?,max_value = ?,display_flag = ?,state = ?,eff_date = ?,exp_date = ?,if_default_value = ?,nullable = ?,default_value = ?,attr_short_code = ?,attr_code = ?,value_max_len = ?,value_allow_edit = ?,value_kind = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((AttrVO) vo).getAttrId())) {
                ((AttrVO) vo).setAttrId(null);
            }

            stmt.setString(index++, ((AttrVO) vo).getAttrId());

            if ("".equals(((AttrVO) vo).getAttrValueUnitId())) {
                ((AttrVO) vo).setAttrValueUnitId(null);
            }

            stmt.setString(index++, ((AttrVO) vo).getAttrValueUnitId());
            stmt.setString(index++, ((AttrVO) vo).getAttrName());
            stmt.setString(index++, ((AttrVO) vo).getAttrDesc());
            stmt.setString(index++, ((AttrVO) vo).getValueType());
            stmt.setString(index++, ((AttrVO) vo).getInputMethod());
            stmt.setString(index++, ((AttrVO) vo).getMinValue());
            stmt.setString(index++, ((AttrVO) vo).getMaxValue());
            stmt.setString(index++, ((AttrVO) vo).getDisplayFlag());
            stmt.setString(index++, ((AttrVO) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((AttrVO) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((AttrVO) vo).getExpDate()));
            stmt.setString(index++, ((AttrVO) vo).getIfDefaultValue());
            stmt.setString(index++, ((AttrVO) vo).getNullable());
            stmt.setString(index++, ((AttrVO) vo).getDefaultValue());
            stmt.setString(index++, ((AttrVO) vo).getAttrShortCode());
            stmt.setString(index++, ((AttrVO) vo).getAttrCode());

            if ("".equals(((AttrVO) vo).getValueMaxLen())) {
                ((AttrVO) vo).setValueMaxLen(null);
            }

            stmt.setString(index++, ((AttrVO) vo).getValueMaxLen());
            stmt.setString(index++, ((AttrVO) vo).getValueAllowEdit());
            stmt.setString(index++, ((AttrVO) vo).getValueKind());

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

    public long delete(String pattr_id) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pattr_id);
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
        return new AttrVO();
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

    public long findAttrByCond(String cond) throws DAOSystemException {
        Connection conn = null;
        long lCount = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "SELECT COUNT(*) AS COL_COUNTS FROM RC_ATTR_DEF WHERE  ATTR_ID = ? ";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setString(1, cond);
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
}
