package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcSimEvdoDAO;
import com.ztesoft.crm.salesres.vo.RcSimEvdoVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcSimEvdoDAOImpl implements RcSimEvdoDAO {
    private String SQL_SELECT = "SELECT RESOURCE_INSTANCE_ID,ATTRNAME,ATTRID,ATTRVALUE,ORD FROM RC_SIM_EVDO ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_SIM_EVDO ";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_SIM_EVDO ( RESOURCE_INSTANCE_ID,ATTRNAME,ATTRID,ATTRVALUE,ORD ) VALUES ( ?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_SIM_EVDO SET  RESOURCE_INSTANCE_ID = ?, ATTRNAME = ?, ATTRID = ?, ATTRVALUE = ?, ORD = ? WHERE ";
    private String SQL_DELETE = "DELETE FROM RC_SIM_EVDO WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_SIM_EVDO ";

    public RcSimEvdoDAOImpl() {
    }

    /**
     * 根据sim卡主键查询相关的evdo卡的信息，此处仅包含evdo单个属性
     * @param rescInstanceId
     * @return
     */
    public RcSimEvdoVO findEvdoBySimId(String rescInstanceId) {
        if ((rescInstanceId == null) || (rescInstanceId.trim().length() < 1)) {
            return null;
        }

        String cond = " RESOURCE_INSTANCE_ID=" +
            DAOUtils.filterQureyCond(rescInstanceId);
        List list = this.findByCond(cond);
        RcSimEvdoVO vo = new RcSimEvdoVO();

        if (list != null) {
            RcSimEvdoVO tempVO = new RcSimEvdoVO();

            for (int i = 0; i < list.size(); i++) {
                tempVO = (RcSimEvdoVO) list.get(i);

                if ("HRPDUPP".equals(tempVO.getAttrname())) {
                    vo.setHrpdUpp(tempVO.getAttrvalue());
                } else if ("HRPD_SS".equals(tempVO.getAttrname())) {
                    vo.setHrpdSs(tempVO.getAttrvalue());
                }
            }
        }

        return vo;
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
            RcSimEvdoVO vo = new RcSimEvdoVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcSimEvdoVO vo, ResultSet rs)
        throws SQLException {
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_ID")));
        vo.setAttrid(DAOUtils.trimStr(rs.getString("ATTRID")));
        vo.setAttrname(DAOUtils.trimStr(rs.getString("ATTRNAME")));
        vo.setAttrvalue(DAOUtils.trimStr(rs.getString("ATTRVALUE")));
        vo.setOrd(DAOUtils.trimStr(rs.getString("ORD")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcSimEvdoVO vo = new RcSimEvdoVO();

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

            if ("".equals(((RcSimEvdoVO) vo).getRescInstanceId())) {
                ((RcSimEvdoVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcSimEvdoVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcSimEvdoVO) vo).getAttrname());

            if ("".equals(((RcSimEvdoVO) vo).getAttrid())) {
                ((RcSimEvdoVO) vo).setAttrid(null);
            }

            stmt.setString(index++, ((RcSimEvdoVO) vo).getAttrid());
            stmt.setString(index++, ((RcSimEvdoVO) vo).getAttrvalue());

            if ("".equals(((RcSimEvdoVO) vo).getOrd())) {
                ((RcSimEvdoVO) vo).setOrd(null);
            }

            stmt.setString(index++, ((RcSimEvdoVO) vo).getOrd());

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

    public boolean updateByPk(RcSimEvdoVO vo) throws DAOSystemException {
        if ((vo == null) || (vo.getRescInstanceId() == null) ||
                (vo.getRescInstanceId().trim().length() < 1) ||
                (vo.getAttrname() == null) ||
                (vo.getAttrname().trim().length() < 1) ||
                (vo.getAttrid() == null) ||
                (vo.getAttrid().trim().length() < 1)) {
            return false;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_SIM_EVDO SET ATTRVALUE = ? where RESOURCE_INSTANCE_ID =? and ATTRNAME =? and ATTRID =? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index++, ((RcSimEvdoVO) vo).getAttrvalue());
            stmt.setString(index++, ((RcSimEvdoVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcSimEvdoVO) vo).getAttrname());
            stmt.setString(index++, ((RcSimEvdoVO) vo).getAttrid());

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
        if ((vo == null) || (whereCond == null) ||
                (whereCond.trim().length() < 1)) {
            return false;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append("UPDATE RC_SIM_EVDO SET ATTRVALUE = ? ");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index++, ((RcSimEvdoVO) vo).getAttrvalue());

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

    /**
     * 根据sim卡的主键删除evdo卡的属性
     * @param rescInstanceId
     * @return
     */
    public long deleteBySimId(String rescInstanceId) {
        if ((rescInstanceId == null) || (rescInstanceId.trim().length() < 1)) {
            return 0;
        }

        String whereCond = " RESOURCE_INSTANCE_ID = " +
            DAOUtils.filterQureyCond(rescInstanceId);
        long rtn = this.deleteByCond(whereCond);

        return rtn;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public VO getEmptyVO() {
        return new RcSimEvdoVO();
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
     * 批量插入evdo卡属性
     * @param list
     * @return
     */
    public long insertBatch(List list) {
        long num = 0;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            if ((list != null) && (list.size() > 0)) {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                            SQL_INSERT));

                RcSimEvdoVO tempVO = null;

                for (int i = 0; i < list.size(); i++) {
                    tempVO = (RcSimEvdoVO) list.get(i);

                    if (tempVO != null) {
                        int index = 1;

                        if ("".equals(tempVO.getRescInstanceId())) {
                            tempVO.setRescInstanceId(null);
                        }

                        stmt.setString(index++, tempVO.getRescInstanceId());
                        stmt.setString(index++, tempVO.getAttrname());

                        if ("".equals(tempVO.getAttrid())) {
                            tempVO.setAttrid(null);
                        }

                        stmt.setString(index++, tempVO.getAttrid());
                        stmt.setString(index++, tempVO.getAttrvalue());

                        if ("".equals(tempVO.getOrd())) {
                            tempVO.setOrd(null);
                        }

                        stmt.setString(index++, tempVO.getOrd());
                        stmt.addBatch();
                    }
                }

                int[] resultArr = stmt.executeBatch();

                if (resultArr != null) {
                    num = (long) resultArr.length;
                }
            }

            return num;
        } catch (SQLException se) {
            Debug.print(SQL_INSERT, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                SQL_INSERT, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }
}
