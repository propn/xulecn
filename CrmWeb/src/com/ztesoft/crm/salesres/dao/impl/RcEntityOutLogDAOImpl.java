package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcEntityOutLogDAO;
import com.ztesoft.crm.salesres.vo.RcEntityOutLogVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcEntityOutLogDAOImpl implements RcEntityOutLogDAO {
    private String SQL_SELECT = "SELECT RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,RESOURCE_LEVEL,RESOURCE_KIND,LAN_ID,OWNER_TYPE,OWNER_ID,STORAGE_ID,CURR_STATE,STATE,CREATE_DATE,EFF_DATE,EXP_DATE,PK_CALBODY,CINVENTORYID,VBATCHCODE FROM RC_ENTITY_OUT_LOG";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_ENTITY_OUT_LOG";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_ENTITY_OUT_LOG ( RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,RESOURCE_LEVEL,RESOURCE_KIND,LAN_ID,OWNER_TYPE,OWNER_ID,STORAGE_ID,CURR_STATE,STATE,CREATE_DATE,EFF_DATE,EXP_DATE,PK_CALBODY,CINVENTORYID,VBATCHCODE ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_ENTITY_OUT_LOG SET  SALES_RESOURCE_ID = ?, RESOURCE_INSTANCE_CODE = ?, RESOURCE_LEVEL = ?, RESOURCE_KIND = ?, LAN_ID = ?, OWNER_TYPE = ?, OWNER_ID = ?, STORAGE_ID = ?, CURR_STATE = ?, STATE = ?, CREATE_DATE = ?, EFF_DATE = ?, EXP_DATE = ?, PK_CALBODY = ?, CINVENTORYID = ?, VBATCHCODE = ? WHERE RESOURCE_INSTANCE_ID = ? ";
    private String SQL_DELETE = "DELETE FROM RC_ENTITY_OUT_LOG WHERE RESOURCE_INSTANCE_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_ENTITY_OUT_LOG ";

    public RcEntityOutLogDAOImpl() {
    }

    public RcEntityOutLogVO findByPrimaryKey(String pRESOURCE_INSTANCE_ID)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE RESOURCE_INSTANCE_ID = ? ",
                new String[] { pRESOURCE_INSTANCE_ID });

        if (arrayList.size() > 0) {
            return (RcEntityOutLogVO) arrayList.get(0);
        } else {
            return (RcEntityOutLogVO) getEmptyVO();
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
            RcEntityOutLogVO vo = new RcEntityOutLogVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcEntityOutLogVO vo, ResultSet rs)
        throws SQLException {
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_ID")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_CODE")));
        vo.setRescLevel(DAOUtils.trimStr(rs.getString("RESOURCE_LEVEL")));
        vo.setRescKind(DAOUtils.trimStr(rs.getString("RESOURCE_KIND")));
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setOwnerType(DAOUtils.trimStr(rs.getString("OWNER_TYPE")));
        vo.setOwnerId(DAOUtils.trimStr(rs.getString("OWNER_ID")));
        vo.setStorageId(DAOUtils.trimStr(rs.getString("STORAGE_ID")));
        vo.setCurrState(DAOUtils.trimStr(rs.getString("CURR_STATE")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setCreateDate(DAOUtils.getFormatedDateTime(rs.getDate("CREATE_DATE")));
        vo.setEffDate(DAOUtils.getFormatedDateTime(rs.getDate("EFF_DATE")));
        vo.setExpDate(DAOUtils.getFormatedDateTime(rs.getDate("EXP_DATE")));
        vo.setPkCalbody(DAOUtils.trimStr(rs.getString("PK_CALBODY")));
        vo.setCinventoryid(DAOUtils.trimStr(rs.getString("CINVENTORYID")));
        vo.setVbatchcode(DAOUtils.trimStr(rs.getString("VBATCHCODE")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcEntityOutLogVO vo = new RcEntityOutLogVO();

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

            if ("".equals(((RcEntityOutLogVO) vo).getRescInstanceId())) {
                ((RcEntityOutLogVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcEntityOutLogVO) vo).getRescInstanceId());

            if ("".equals(((RcEntityOutLogVO) vo).getSalesRescId())) {
                ((RcEntityOutLogVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcEntityOutLogVO) vo).getSalesRescId());
            stmt.setString(index++,
                ((RcEntityOutLogVO) vo).getRescInstanceCode());

            if ("".equals(((RcEntityOutLogVO) vo).getRescLevel())) {
                ((RcEntityOutLogVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcEntityOutLogVO) vo).getRescLevel());
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getRescKind());

            if ("".equals(((RcEntityOutLogVO) vo).getLanId())) {
                ((RcEntityOutLogVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcEntityOutLogVO) vo).getLanId());
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getOwnerType());

            if ("".equals(((RcEntityOutLogVO) vo).getOwnerId())) {
                ((RcEntityOutLogVO) vo).setOwnerId(null);
            }

            stmt.setString(index++, ((RcEntityOutLogVO) vo).getOwnerId());

            if ("".equals(((RcEntityOutLogVO) vo).getStorageId())) {
                ((RcEntityOutLogVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcEntityOutLogVO) vo).getStorageId());
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getCurrState());
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcEntityOutLogVO) vo).getCreateDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcEntityOutLogVO) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcEntityOutLogVO) vo).getExpDate()));
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getPkCalbody());
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getCinventoryid());
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getVbatchcode());

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

    public boolean update(String pRESOURCE_INSTANCE_ID, RcEntityOutLogVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_ENTITY_OUT_LOG SET RESOURCE_INSTANCE_ID = ?,SALES_RESOURCE_ID = ?,RESOURCE_INSTANCE_CODE = ?,RESOURCE_LEVEL = ?,RESOURCE_KIND = ?,LAN_ID = ?,OWNER_TYPE = ?,OWNER_ID = ?,STORAGE_ID = ?,CURR_STATE = ?,STATE = ?,CREATE_DATE = ?,EFF_DATE = ?,EXP_DATE = ?,PK_CALBODY = ?,CINVENTORYID = ?,VBATCHCODE = ?");
            sql.append(" WHERE  RESOURCE_INSTANCE_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcEntityOutLogVO) vo).getRescInstanceId())) {
                ((RcEntityOutLogVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, vo.getRescInstanceId());

            if ("".equals(((RcEntityOutLogVO) vo).getSalesRescId())) {
                ((RcEntityOutLogVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, vo.getSalesRescId());
            stmt.setString(index++, vo.getRescInstanceCode());

            if ("".equals(((RcEntityOutLogVO) vo).getRescLevel())) {
                ((RcEntityOutLogVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, vo.getRescLevel());
            stmt.setString(index++, vo.getRescKind());

            if ("".equals(((RcEntityOutLogVO) vo).getLanId())) {
                ((RcEntityOutLogVO) vo).setLanId(null);
            }

            stmt.setString(index++, vo.getLanId());
            stmt.setString(index++, vo.getOwnerType());

            if ("".equals(((RcEntityOutLogVO) vo).getOwnerId())) {
                ((RcEntityOutLogVO) vo).setOwnerId(null);
            }

            stmt.setString(index++, vo.getOwnerId());

            if ("".equals(((RcEntityOutLogVO) vo).getStorageId())) {
                ((RcEntityOutLogVO) vo).setStorageId(null);
            }

            stmt.setString(index++, vo.getStorageId());
            stmt.setString(index++, vo.getCurrState());
            stmt.setString(index++, vo.getState());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getCreateDate()));
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getEffDate()));
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getExpDate()));
            stmt.setString(index++, vo.getPkCalbody());
            stmt.setString(index++, vo.getCinventoryid());
            stmt.setString(index++, vo.getVbatchcode());
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
                "UPDATE RC_ENTITY_OUT_LOG SET RESOURCE_INSTANCE_ID = ?,SALES_RESOURCE_ID = ?,RESOURCE_INSTANCE_CODE = ?,RESOURCE_LEVEL = ?,RESOURCE_KIND = ?,LAN_ID = ?,OWNER_TYPE = ?,OWNER_ID = ?,STORAGE_ID = ?,CURR_STATE = ?,STATE = ?,CREATE_DATE = ?,EFF_DATE = ?,EXP_DATE = ?,PK_CALBODY = ?,CINVENTORYID = ?,VBATCHCODE = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcEntityOutLogVO) vo).getRescInstanceId())) {
                ((RcEntityOutLogVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcEntityOutLogVO) vo).getRescInstanceId());

            if ("".equals(((RcEntityOutLogVO) vo).getSalesRescId())) {
                ((RcEntityOutLogVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcEntityOutLogVO) vo).getSalesRescId());
            stmt.setString(index++,
                ((RcEntityOutLogVO) vo).getRescInstanceCode());

            if ("".equals(((RcEntityOutLogVO) vo).getRescLevel())) {
                ((RcEntityOutLogVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcEntityOutLogVO) vo).getRescLevel());
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getRescKind());

            if ("".equals(((RcEntityOutLogVO) vo).getLanId())) {
                ((RcEntityOutLogVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcEntityOutLogVO) vo).getLanId());
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getOwnerType());

            if ("".equals(((RcEntityOutLogVO) vo).getOwnerId())) {
                ((RcEntityOutLogVO) vo).setOwnerId(null);
            }

            stmt.setString(index++, ((RcEntityOutLogVO) vo).getOwnerId());

            if ("".equals(((RcEntityOutLogVO) vo).getStorageId())) {
                ((RcEntityOutLogVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcEntityOutLogVO) vo).getStorageId());
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getCurrState());
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcEntityOutLogVO) vo).getCreateDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcEntityOutLogVO) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcEntityOutLogVO) vo).getExpDate()));
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getPkCalbody());
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getCinventoryid());
            stmt.setString(index++, ((RcEntityOutLogVO) vo).getVbatchcode());

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

    public long delete(String pRESOURCE_INSTANCE_ID) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
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
        return new RcEntityOutLogVO();
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
     * 把单个实例信息插入到本日至表中
     * @param resBCode String
     * @param resECode String
     * @throws DAOSystemException
     * @return int
     */
    public int insertFromSingleEntity(String rescInstanceCode)
        throws DAOSystemException {
        if ((rescInstanceCode == null) ||
                (rescInstanceCode.trim().length() < 1)) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String sql = "INSERT INTO RC_ENTITY_OUT_LOG (resource_instance_id,sales_resource_id,resource_instance_code,resource_level,resource_kind,lan_id,owner_type,owner_id,storage_id,CURR_STATE,state,create_date,eff_date,exp_date,pk_calbody,cinventoryid,vbatchcode) " +
            " select resource_instance_id,sales_resource_id,resource_instance_code,resource_level,resource_kind,lan_id,owner_type,owner_id,storage_id,CURR_STATE,state,create_date,eff_date,exp_date,pk_calbody,cinventoryid,vbatchcode from rc_entity " +
            " where resource_instance_code='" +
            DAOUtils.filterQureyCond(rescInstanceCode) + "'";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            rows = stmt.executeUpdate();

            return rows;
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException(
                "SQLException while insert insertFromSingleEntity:\n" + sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * 把物资实例表中起始实例编码和终止编码之间的实例插入到本日至表中
     * @param resBCode String
     * @param resECode String
     * @throws DAOSystemException
     * @return int
     */
    public int insertFromEntity(String cond) throws DAOSystemException {
        if ((cond == null) || (cond.length() <= 0)) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String sql = "INSERT INTO RC_ENTITY_OUT_LOG  select * from rc_entity  where  "; // +

        sql += cond;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            rows = stmt.executeUpdate();
            insertFromEntityAttr(cond);
            deleteFromEntityAttr(cond);

            return rows;
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException(
                "SQLException while insertFromEntity sql:\n" + sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * 把物资实例表中起始实例编码和终止编码之间的实例插入到本日至表中
     * @param resBCode String
     * @param resECode String
     * @throws DAOSystemException
     * @return int
     */
    public int insertFromEntityAttr(String cond) throws DAOSystemException {
        if ((cond == null) || (cond.length() <= 0)) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String sql = "INSERT INTO RC_ENTITY_ATTR_LOG  select * from RC_ENTITY_ATTR where entity_id in (select resource_instance_id from rc_entity where   ";

        sql += (cond + " )");

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            rows = stmt.executeUpdate();

            return rows;
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException(
                "SQLException while insertFromEntity sql:\n" + sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public int deleteFromEntityAttr(String cond) throws DAOSystemException {
        if ((cond == null) || (cond.length() <= 0)) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String sql = "delete from rc_entity_attr  where entity_id in (select resource_instance_id from rc_entity where  ";

        sql += (cond + " )");

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            rows = stmt.executeUpdate();

            return rows;
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException(
                "SQLException while insertFromEntity sql:\n" + sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }
}
