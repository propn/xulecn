package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.dao.RcImsiDAO;
import com.ztesoft.crm.salesres.exception.LogicInfoException;
import com.ztesoft.crm.salesres.vo.RcImsiVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcImsiDAOImpl implements RcImsiDAO {
    private String SQL_SELECT = "SELECT IMSI_ID,IMSI_SEG_ID,RESOURCE_INSTANCE_ID,STATE,MASTER_FLAG FROM RC_IMSI";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_IMSI";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_IMSI ( IMSI_ID,IMSI_SEG_ID,RESOURCE_INSTANCE_ID,STATE,MASTER_FLAG ) VALUES ( ?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_IMSI SET  IMSI_SEG_ID = ?, RESOURCE_INSTANCE_ID = ?, STATE = ?, MASTER_FLAG = ? WHERE IMSI_ID = ? ";
    private String SQL_DELETE = "DELETE FROM RC_IMSI WHERE IMSI_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_IMSI ";
    private String SQL_SELECT_IMSI = "SELECT a.IMSI_ID,a.IMSI_SEG_ID,a.RESOURCE_INSTANCE_ID,a.STATE,a.MASTER_FLAG,b.RESOURCE_INSTANCE_CODE,a.REC_TIME FROM RC_IMSI a left outer join rc_sim b on a.RESOURCE_INSTANCE_ID = b.RESOURCE_INSTANCE_ID";
    private String SQL_SELECT_COUNT_IMSI = "SELECT COUNT(a.IMSI_ID) AS COL_COUNTS FROM RC_IMSI a left outer join rc_sim b on a.RESOURCE_INSTANCE_ID = b.RESOURCE_INSTANCE_ID";
    private String ACT_CODE = "0";

    public RcImsiDAOImpl() {
    }

    public RcImsiVO findByPrimaryKey(String pIMSI_ID) throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT + " WHERE IMSI_ID = ? ",
                new String[] { pIMSI_ID });

        if (arrayList.size() > 0) {
            return (RcImsiVO) arrayList.get(0);
        } else {
            return (RcImsiVO) getEmptyVO();
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
            RcImsiVO vo = new RcImsiVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcImsiVO vo, ResultSet rs)
        throws SQLException {
        vo.setImsiId(DAOUtils.trimStr(rs.getString("IMSI_ID")));
        vo.setImsiSegId(DAOUtils.trimStr(rs.getString("IMSI_SEG_ID")));
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_ID")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setMasterFlag(DAOUtils.trimStr(rs.getString("MASTER_FLAG")));

        if ("1".equals(ACT_CODE)) {
            vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                        "RESOURCE_INSTANCE_CODE")));
            vo.setRecTime(DAOUtils.getFormatedDate(rs.getDate("REC_TIME")));
        }
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcImsiVO vo = new RcImsiVO();

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

            if ("1".equals(ACT_CODE)) {
                SQL = SQL_SELECT_IMSI + " WHERE " + whereCond;
            } else {
                SQL = SQL_SELECT + " WHERE " + whereCond;
            }

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

            if ("".equals(((RcImsiVO) vo).getImsiId())) {
                ((RcImsiVO) vo).setImsiId(null);
            }

            stmt.setString(index++, ((RcImsiVO) vo).getImsiId());

            if ("".equals(((RcImsiVO) vo).getImsiSegId())) {
                ((RcImsiVO) vo).setImsiSegId(null);
            }

            stmt.setString(index++, ((RcImsiVO) vo).getImsiSegId());

            if ("".equals(((RcImsiVO) vo).getRescInstanceId())) {
                ((RcImsiVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcImsiVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcImsiVO) vo).getState());
            stmt.setString(index++, ((RcImsiVO) vo).getMasterFlag());

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

    public boolean update(String pIMSI_ID, RcImsiVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_IMSI SET IMSI_ID = ?,IMSI_SEG_ID = ?,RESOURCE_INSTANCE_ID = ?,STATE = ?,MASTER_FLAG = ?");
            sql.append(" WHERE  IMSI_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcImsiVO) vo).getImsiId())) {
                ((RcImsiVO) vo).setImsiId(null);
            }

            stmt.setString(index++, vo.getImsiId());

            if ("".equals(((RcImsiVO) vo).getImsiSegId())) {
                ((RcImsiVO) vo).setImsiSegId(null);
            }

            stmt.setString(index++, vo.getImsiSegId());

            if ("".equals(((RcImsiVO) vo).getRescInstanceId())) {
                ((RcImsiVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, vo.getRescInstanceId());
            stmt.setString(index++, vo.getState());
            stmt.setString(index++, vo.getMasterFlag());
            stmt.setString(index++, pIMSI_ID);

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
                "UPDATE RC_IMSI SET IMSI_ID = ?,IMSI_SEG_ID = ?,RESOURCE_INSTANCE_ID = ?,STATE = ?,MASTER_FLAG = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcImsiVO) vo).getImsiId())) {
                ((RcImsiVO) vo).setImsiId(null);
            }

            stmt.setString(index++, ((RcImsiVO) vo).getImsiId());

            if ("".equals(((RcImsiVO) vo).getImsiSegId())) {
                ((RcImsiVO) vo).setImsiSegId(null);
            }

            stmt.setString(index++, ((RcImsiVO) vo).getImsiSegId());

            if ("".equals(((RcImsiVO) vo).getRescInstanceId())) {
                ((RcImsiVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcImsiVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcImsiVO) vo).getState());
            stmt.setString(index++, ((RcImsiVO) vo).getMasterFlag());

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

            if ("1".equals(ACT_CODE)) {
                SQL = SQL_SELECT_COUNT_IMSI + " WHERE " + whereCond;
            } else {
                SQL = SQL_SELECT_COUNT + " WHERE " + whereCond;
            }

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

    public long delete(String pIMSI_ID) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pIMSI_ID);
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
        return new RcImsiVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";

        if ("1".equals(ACT_CODE)) {
            SQL = SQL_SELECT_IMSI + " WHERE " + whereCond;
        } else {
            SQL = SQL_SELECT + " WHERE " + whereCond;
        }

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

    public VO getResultForValidate(VO vo) throws DAOSystemException {
        if ((vo == null) || !(vo instanceof RcImsiVO)) {
            return null;
        }

        RcImsiVO ivo = (RcImsiVO) vo;

        //----modified by panyazong 改为变量绑定，预编译方式   20090618
        //    String sql = "SELECT imsi_seg_id FROM rc_imsi_seg WHERE START_IMSI_ID<=" +
        //        ivo.getImsiId() + " and END_IMSI_ID>=" + ivo.getImsiId();
        String sql = "SELECT imsi_seg_id FROM rc_imsi_seg WHERE START_IMSI_ID<=? and END_IMSI_ID>=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setString(1, ivo.getImsiId());
            stmt.setString(2, ivo.getImsiId());
            rs = stmt.executeQuery();
            System.out.println(sql + "\n---------" + rs);

            if (rs.next()) {
                ivo.setImsiSegId(String.valueOf(rs.getLong(1)));
            } else {
                DAOSystemException exx = new LogicInfoException(" IMSI码" +
                        ivo.getImsiId() + "不在任何IMSI段内！");
                throw exx;
            }

            return ivo;
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

    /**
     * 根据imsi码查询其对应的imsiSegId的信息
     * @param vo RcImsiVO
     * @throws DAOSystemException
     * @return String 如果查不到则返回null
     */
    public String qryImsiSeg(String imsiId) throws DAOSystemException {
        if ((imsiId == null) || (imsiId.trim().length() < 1)) {
            return null;
        }

        //----modified by panyazong 改为变量绑定，预编译方式   20090618
        //    String sql = "SELECT imsi_seg_id FROM rc_imsi_seg WHERE START_IMSI_ID<=" +
        //                  imsiId + " and END_IMSI_ID>=" + imsiId;
        String sql = "SELECT imsi_seg_id FROM rc_imsi_seg WHERE START_IMSI_ID<=? and END_IMSI_ID>= ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String imsiSegId = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setString(1, imsiId);
            stmt.setString(2, imsiId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                imsiSegId = String.valueOf(rs.getLong(1));
            } else {
                imsiSegId = null;
            }

            return imsiSegId;
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

    /**
     * setActCode
     *
     * @param actCode String
     */
    public void setActCode(String actCode) {
        ACT_CODE = actCode;
    }

    /**
     * updateState
     *
     * @param whereCond String
     * @return long
     */
    public boolean updateState(String whereCond) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append("UPDATE RC_IMSI SET STATE = ? ");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));
            stmt.setString(1, ParamsConsConfig.RescState_preMakeCard);

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
}
