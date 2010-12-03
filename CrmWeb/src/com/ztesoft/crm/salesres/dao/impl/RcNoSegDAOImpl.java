package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcNoSegDAO;
import com.ztesoft.crm.salesres.vo.RcNoSegVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcNoSegDAOImpl implements RcNoSegDAO {
    //private String SQL_SELECT = "SELECT NO_SEG_ID,NO_SEG_NAME,STARTTIME,INTIME,BEGINN,ENDNO,STATE,IMSI_SEG_ID,NO_GROUP_ID,SALES_RESOURCE_ID FROM RC_NO_SEG";
    private String SQL_SELECT = " SELECT a.*,b.lan_id,b.state group_state,c.sales_resource_name FROM RC_NO_SEG a,RC_NO_GROUP b,SALES_RESOURCE C ";
    private String SQL_SELECT_RS_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_NO_SEG";
    private String SQL_SELECT_COUNT = "SELECT COUNT(a.no_seg_id) AS COL_COUNTS FROM RC_NO_SEG a,RC_NO_GROUP b,SALES_RESOURCE C ";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_NO_SEG ( NO_SEG_ID,NO_SEG_NAME,STARTTIME,INTIME,BEGINN,ENDNO,STATE,IMSI_SEG_ID,NO_GROUP_ID,SALES_RESOURCE_ID,BALA_MODE,datacard_flag,seg_id,c_double_flag ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_NO_SEG SET  NO_SEG_NAME = ?, STARTTIME = ?, INTIME = ?, BEGINN = ?, ENDNO = ?, STATE = ?, IMSI_SEG_ID = ?, NO_GROUP_ID = ?, SALES_RESOURCE_ID = ?, BALA_MODE = ?,datacard_flag=?,seg_id = ? WHERE NO_SEG_ID = ? ";
    private String SQL_DELETE = "DELETE FROM RC_NO_SEG WHERE NO_SEG_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_NO_SEG ";

    public RcNoSegDAOImpl() {
    }

    public RcNoSegVO findByPrimaryKey(String pNO_SEG_ID)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE a.no_seg_id = ? and a.no_group_id = b.no_group_id and a.sales_resource_id = c.sales_resource_id",
                new String[] { pNO_SEG_ID });

        if (arrayList.size() > 0) {
            return (RcNoSegVO) arrayList.get(0);
        } else {
            return (RcNoSegVO) getEmptyVO();
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
            RcNoSegVO vo = new RcNoSegVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcNoSegVO vo, ResultSet rs)
        throws SQLException {
        vo.setNoSegId(DAOUtils.trimStr(rs.getString("NO_SEG_ID")));
        vo.setNoSegName(DAOUtils.trimStr(rs.getString("NO_SEG_NAME")));
        vo.setStarttime(DAOUtils.getFormatedDateTime(rs.getDate("STARTTIME")));
        vo.setIntime(DAOUtils.getFormatedDateTime(rs.getDate("INTIME")));
        vo.setBeginn(DAOUtils.trimStr(rs.getString("BEGINN")));
        vo.setEndno(DAOUtils.trimStr(rs.getString("ENDNO")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setImsiSegId(DAOUtils.trimStr(rs.getString("IMSI_SEG_ID")));
        vo.setNoGrpId(DAOUtils.trimStr(rs.getString("NO_GROUP_ID")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setBalaMode(DAOUtils.trimStr(rs.getString("BALA_MODE")));
        vo.setGroupState(DAOUtils.trimStr(rs.getString("group_state")));
        vo.setLanId(DAOUtils.trimStr(rs.getString("lan_id")));
        vo.setSalesRescName(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_NAME")));
        vo.setDatacardflag(DAOUtils.trimStr(rs.getString("datacard_flag")));
        vo.setSegId(DAOUtils.trimStr(rs.getString("SEG_ID")));
        vo.setCDoubleFlag(DAOUtils.trimStr(rs.getString("C_DOUBLE_FLAG")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcNoSegVO vo = new RcNoSegVO();

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

            if ("".equals(((RcNoSegVO) vo).getNoSegId())) {
                ((RcNoSegVO) vo).setNoSegId(null);
            }

            stmt.setString(index++, ((RcNoSegVO) vo).getNoSegId());
            stmt.setString(index++, ((RcNoSegVO) vo).getNoSegName());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoSegVO) vo).getStarttime()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoSegVO) vo).getIntime()));
            stmt.setString(index++, ((RcNoSegVO) vo).getBeginn());
            stmt.setString(index++, ((RcNoSegVO) vo).getEndno());
            stmt.setString(index++, ((RcNoSegVO) vo).getState());

            if ("".equals(((RcNoSegVO) vo).getImsiSegId())) {
                ((RcNoSegVO) vo).setImsiSegId(null);
            }

            stmt.setString(index++, ((RcNoSegVO) vo).getImsiSegId());

            if ("".equals(((RcNoSegVO) vo).getNoGrpId())) {
                ((RcNoSegVO) vo).setNoGrpId(null);
            }

            stmt.setString(index++, ((RcNoSegVO) vo).getNoGrpId());

            if ("".equals(((RcNoSegVO) vo).getSalesRescId())) {
                ((RcNoSegVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcNoSegVO) vo).getSalesRescId());
            stmt.setString(index++, ((RcNoSegVO) vo).getBalaMode());
            stmt.setString(index++, ((RcNoSegVO) vo).getDatacardflag());
            stmt.setString(index++, ((RcNoSegVO) vo).getSegId());
            stmt.setString(index++, ((RcNoSegVO) vo).getCDoubleFlag());

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

    public boolean update(String pNO_SEG_ID, RcNoSegVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_NO_SEG SET NO_SEG_ID = ?,NO_SEG_NAME = ?,STARTTIME = ?,INTIME = ?,BEGINN = ?,ENDNO = ?,STATE = ?,IMSI_SEG_ID = ?,NO_GROUP_ID = ?,SALES_RESOURCE_ID = ?,BALA_MODE = ?,datacard_flag=?,c_double_flag=? ");
            sql.append(" WHERE  NO_SEG_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNoSegVO) vo).getNoSegId())) {
                ((RcNoSegVO) vo).setNoSegId(null);
            }

            stmt.setString(index++, vo.getNoSegId());
            stmt.setString(index++, vo.getNoSegName());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getStarttime()));
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getIntime()));
            stmt.setString(index++, vo.getBeginn());
            stmt.setString(index++, vo.getEndno());
            stmt.setString(index++, vo.getState());

            if ("".equals(((RcNoSegVO) vo).getImsiSegId())) {
                ((RcNoSegVO) vo).setImsiSegId(null);
            }

            stmt.setString(index++, vo.getImsiSegId());

            if ("".equals(((RcNoSegVO) vo).getNoGrpId())) {
                ((RcNoSegVO) vo).setNoGrpId(null);
            }

            stmt.setString(index++, vo.getNoGrpId());

            if ("".equals(((RcNoSegVO) vo).getSalesRescId())) {
                ((RcNoSegVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, vo.getSalesRescId());
            stmt.setString(index++, ((RcNoSegVO) vo).getBalaMode());
            stmt.setString(index++, ((RcNoSegVO) vo).getDatacardflag());
            stmt.setString(index++, ((RcNoSegVO) vo).getCDoubleFlag());

            stmt.setString(index++, pNO_SEG_ID);

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
                "UPDATE RC_NO_SEG SET NO_SEG_ID = ?,NO_SEG_NAME = ?,STARTTIME = ?,INTIME = ?,BEGINN = ?,ENDNO = ?,STATE = ?,IMSI_SEG_ID = ?,NO_GROUP_ID = ?,SALES_RESOURCE_ID = ?,BALA_MODE = ?,datacard_flag=? ");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNoSegVO) vo).getNoSegId())) {
                ((RcNoSegVO) vo).setNoSegId(null);
            }

            stmt.setString(index++, ((RcNoSegVO) vo).getNoSegId());
            stmt.setString(index++, ((RcNoSegVO) vo).getNoSegName());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoSegVO) vo).getStarttime()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoSegVO) vo).getIntime()));
            stmt.setString(index++, ((RcNoSegVO) vo).getBeginn());
            stmt.setString(index++, ((RcNoSegVO) vo).getEndno());
            stmt.setString(index++, ((RcNoSegVO) vo).getState());

            if ("".equals(((RcNoSegVO) vo).getImsiSegId())) {
                ((RcNoSegVO) vo).setImsiSegId(null);
            }

            stmt.setString(index++, ((RcNoSegVO) vo).getImsiSegId());

            if ("".equals(((RcNoSegVO) vo).getNoGrpId())) {
                ((RcNoSegVO) vo).setNoGrpId(null);
            }

            stmt.setString(index++, ((RcNoSegVO) vo).getNoGrpId());

            if ("".equals(((RcNoSegVO) vo).getSalesRescId())) {
                ((RcNoSegVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcNoSegVO) vo).getSalesRescId());
            stmt.setString(index++, ((RcNoSegVO) vo).getBalaMode());
            stmt.setString(index++, ((RcNoSegVO) vo).getDatacardflag());

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

    public long delete(String pNO_SEG_ID) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pNO_SEG_ID);
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
        return new RcNoSegVO();
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

    /**
     * 查询号码类营销资源的id
     * @return String
     */
    public String findNoFamilyId() {
        String familyId = "";
        String SQL = "select DC_SQL from dc_sql where DC_NAME='Rc_No_FamilyId'";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setMaxRows(maxRows);
            rs = stmt.executeQuery();

            while (rs.next()) {
                familyId = DAOUtils.trimStr(rs.getString("DC_SQL"));
            }

            return familyId;
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

    /**
     * 根据静态数据查找该静态数据配置的家族id
     * @return String
     */
    public String findFamilyId(String attrCode) {
        String familyId = null;

        if ((attrCode == null) || (attrCode.trim().length() < 1)) {
            return familyId;
        }

        String SQL = "select DC_SQL from dc_sql where DC_NAME='" + attrCode +
            "'";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setMaxRows(maxRows);
            rs = stmt.executeQuery();

            while (rs.next()) {
                familyId = DAOUtils.trimStr(rs.getString("DC_SQL"));
            }

            if (familyId != null) {
                familyId = familyId.trim();
            }

            return familyId;
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

    //查询将要写入的号段是否已存在
    public long countRsByCond(String whereCond) throws DAOSystemException {
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
            SQL = SQL_SELECT_RS_COUNT + " WHERE " + whereCond;
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

    //查询将要写入的号段是否已存在
    public String findStateByCond(String whereCond) throws DAOSystemException {
        Connection conn = null;
        String lCount = "";
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
            SQL = "SELECT STATE FROM RC_NO_SEG WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            rs = stmt.executeQuery();

            while (rs.next()) {
                lCount = DAOUtils.trimStr(rs.getString("STATE"));
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

    /**
     * findNoSegByCond
     *
     * @param whereCond String
     * @return String
     */
    public long findNoSegByCond(String whereCond) {
        Connection conn = null;
        long lCount = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "SELECT COUNT(NO_SEG_ID) AS COL_COUNTS FROM RC_NO_SEG WHERE " +
                whereCond;
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
}
