package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcEntityDAO;
import com.ztesoft.crm.salesres.vo.RcEntityVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcEntityDAOImpl implements RcEntityDAO {
    private String SQL_SELECT = "SELECT resource_instance_id,sales_resource_id,resource_instance_code,resource_level,resource_kind,lan_id,owner_type,owner_id,storage_id,CURR_STATE,state,create_date,eff_date,exp_date,pk_calbody,cinventoryid,vbatchcode FROM RC_ENTITY";
    private String SQL_SELECT_EX = "SELECT " +
        "RESOURCE_INSTANCE_ID,A.SALES_RESOURCE_ID,A.RESOURCE_INSTANCE_CODE,A.RESOURCE_LEVEL,A.RESOURCE_KIND,A.LAN_ID,A.OWNER_TYPE,A.OWNER_ID,E.STORAGE_NAME AS STORAGE_ID," +
        "A.CURR_STATE,DECODE(A.STATE,'00A','有效','00X','失效') AS STATE,A.CREATE_DATE,A.EFF_DATE,A.EXP_DATE,A.PK_CALBODY,A.CINVENTORYID,A.VBATCHCODE " +
        "FROM RC_ENTITY A,STORAGE_DEPART_RELA C,PARTY_ROLE D,RC_STORAGE E,organization F,organization G ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_ENTITY A,STORAGE_DEPART_RELA C,PARTY_ROLE D,RC_STORAGE E,organization F,organization G ";
    private String SQL_SELECT_COUNT_2 = "SELECT COUNT(*) AS COL_COUNTS FROM RC_ENTITY ";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_ENTITY ( resource_instance_id,sales_resource_id,resource_instance_code,resource_level,resource_kind,lan_id,owner_type,owner_id,storage_id,CURR_STATE,state,create_date,eff_date,exp_date,pk_calbody,cinventoryid,vbatchcode ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_ENTITY SET  sales_resource_id = ?, resource_instance_code = ?, resource_level = ?, resource_kind = ?, lan_id = ?, owner_type = ?, owner_id = ?, storage_id = ?, CURR_STATE = ?, state = ?, create_date = ?, eff_date = ?, exp_date = ?, pk_calbody = ?, cinventoryid = ?, vbatchcode = ? WHERE resource_instance_id = ? ";
    private String SQL_DELETE = "DELETE FROM RC_ENTITY WHERE resource_instance_id = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_ENTITY ";
    private int flag = 1;
    private int updateFlag = 0;

    // 代表操作表的类型，0代表rc_entity，1代表rc_entity2
    private int tableType = 0;

    public RcEntityDAOImpl() {
    }

    public RcEntityVO findByPrimaryKey(String presource_instance_id)
        throws DAOSystemException {
        String SQL = SQL_SELECT + " WHERE resource_instance_id = ? ";

        if (tableType == 1) {
            SQL = SQL.replaceAll("RC_ENTITY ", "RC_ENTITY2 ");
            SQL = SQL.replaceAll("rc_entity ", "rc_entity2 "); //  因为有些查询sql中有状态的比较，所以不能统一转为大写或小写
        }

        ArrayList arrayList = findBySql(SQL,
                new String[] { presource_instance_id });

        if (arrayList.size() > 0) {
            return (RcEntityVO) arrayList.get(0);
        } else {
            return (RcEntityVO) getEmptyVO();
        }
    }

    /**
     * 根据编码查找营销实例
     * @param rescInstanceCode
     * @return
     */
    public RcEntityVO findByEntityCode(String rescInstanceCode)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE resource_instance_code = ? ",
                new String[] { rescInstanceCode });

        if (arrayList.size() > 0) {
            return (RcEntityVO) arrayList.get(0);
        } else {
            return (RcEntityVO) getEmptyVO();
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
            RcEntityVO vo = new RcEntityVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcEntityVO vo, ResultSet rs)
        throws SQLException {
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "resource_instance_id")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
        vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                    "resource_instance_code")));
        vo.setRescLevel(DAOUtils.trimStr(rs.getString("resource_level")));
        vo.setRescKind(DAOUtils.trimStr(rs.getString("resource_kind")));
        vo.setLanId(DAOUtils.trimStr(rs.getString("lan_id")));
        vo.setOwnerType(DAOUtils.trimStr(rs.getString("owner_type")));
        vo.setOwnerId(DAOUtils.trimStr(rs.getString("owner_id")));
        vo.setStorageId(DAOUtils.trimStr(rs.getString("storage_id")));
        vo.setRescState(DAOUtils.trimStr(rs.getString("CURR_STATE")));
        vo.setState(DAOUtils.trimStr(rs.getString("state")));
        vo.setCreateDate(DAOUtils.getFormatedDate(rs.getDate("create_date")));
        vo.setEffDate(DAOUtils.getFormatedDate(rs.getDate("eff_date")));
        vo.setExpDate(DAOUtils.getFormatedDate(rs.getDate("exp_date")));

        String PkCalbody = rs.getString("pk_calbody");

        if (PkCalbody == null) {
            PkCalbody = "";
        }

        if ("null".equals(PkCalbody)) {
            PkCalbody = "";
        }

        vo.setPkCalbody(DAOUtils.trimStr(PkCalbody));

        String Cinventoryid = rs.getString("cinventoryid");

        if (Cinventoryid == null) {
            Cinventoryid = "";
        }

        if ("null".equals(Cinventoryid)) {
            Cinventoryid = "";
        }

        vo.setCinventoryid(DAOUtils.trimStr(Cinventoryid));

        String Vbatchcode = rs.getString("vbatchcode");

        if (Vbatchcode == null) {
            Vbatchcode = "";
        }

        if ("null".equals(Vbatchcode)) {
            Vbatchcode = "";
        }

        vo.setVbatchcode(DAOUtils.trimStr(Vbatchcode));

        vo.setLvEffDate(String.valueOf(rs.getDate("eff_date").getTime()));
        vo.setLvExpDate(String.valueOf(rs.getDate("exp_date").getTime()));
        vo.setLvCurrent(String.valueOf(new java.util.Date().getTime()));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcEntityVO vo = new RcEntityVO();

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

            if (tableType == 1) {
                SQL = SQL.replaceAll("RC_ENTITY ", "RC_ENTITY2 ");
                SQL = SQL.replaceAll("rc_entity ", "rc_entity2 "); //  因为有些查询sql中有状态的比较，所以不能统一转为大写或小写
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

            if ("".equals(((RcEntityVO) vo).getRescInstanceId())) {
                ((RcEntityVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcEntityVO) vo).getRescInstanceId());

            if ("".equals(((RcEntityVO) vo).getSalesRescId())) {
                ((RcEntityVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcEntityVO) vo).getSalesRescId());
            stmt.setString(index++, ((RcEntityVO) vo).getRescInstanceCode());

            if ("".equals(((RcEntityVO) vo).getRescLevel())) {
                ((RcEntityVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcEntityVO) vo).getRescLevel());
            stmt.setString(index++, ((RcEntityVO) vo).getRescKind());

            if ("".equals(((RcEntityVO) vo).getLanId())) {
                ((RcEntityVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcEntityVO) vo).getLanId());
            stmt.setString(index++, ((RcEntityVO) vo).getOwnerType());

            if ("".equals(((RcEntityVO) vo).getOwnerId())) {
                ((RcEntityVO) vo).setOwnerId(null);
            }

            stmt.setString(index++, ((RcEntityVO) vo).getOwnerId());

            if ("".equals(((RcEntityVO) vo).getStorageId())) {
                ((RcEntityVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcEntityVO) vo).getStorageId());
            stmt.setString(index++, ((RcEntityVO) vo).getRescState());
            stmt.setString(index++, ((RcEntityVO) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDate(((RcEntityVO) vo).getCreateDate()));
            stmt.setDate(index++,
                DAOUtils.parseDate(((RcEntityVO) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDate(((RcEntityVO) vo).getExpDate()));
            stmt.setString(index++, ((RcEntityVO) vo).getPkCalbody());
            stmt.setString(index++, ((RcEntityVO) vo).getCinventoryid());
            stmt.setString(index++, ((RcEntityVO) vo).getVbatchcode());

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

    public boolean update(String presource_instance_id, RcEntityVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        String tableName = "RC_ENTITY";

        if (tableType == 1) {
            tableName = "RC_ENTITY2";
        }

        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append("UPDATE " + tableName +
                " SET resource_instance_id = ?,sales_resource_id = ?,resource_instance_code = ?,resource_level = ?,resource_kind = ?,lan_id = ?,owner_type = ?,owner_id = ?,storage_id = ?,CURR_STATE = ?,state = ?,create_date = ?,eff_date = ?,exp_date = ?,pk_calbody = ?,cinventoryid = ?,vbatchcode = ?");
            sql.append(" WHERE  resource_instance_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcEntityVO) vo).getRescInstanceId())) {
                ((RcEntityVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, vo.getRescInstanceId());

            if ("".equals(((RcEntityVO) vo).getSalesRescId())) {
                ((RcEntityVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, vo.getSalesRescId());
            stmt.setString(index++, vo.getRescInstanceCode());

            if ("".equals(((RcEntityVO) vo).getRescLevel())) {
                ((RcEntityVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, vo.getRescLevel());
            stmt.setString(index++, vo.getRescKind());

            if ("".equals(((RcEntityVO) vo).getLanId())) {
                ((RcEntityVO) vo).setLanId(null);
            }

            stmt.setString(index++, vo.getLanId());
            stmt.setString(index++, vo.getOwnerType());

            if ("".equals(((RcEntityVO) vo).getOwnerId())) {
                ((RcEntityVO) vo).setOwnerId(null);
            }

            stmt.setString(index++, vo.getOwnerId());

            if ("".equals(((RcEntityVO) vo).getStorageId())) {
                ((RcEntityVO) vo).setStorageId(null);
            }

            stmt.setString(index++, vo.getStorageId());
            stmt.setString(index++, vo.getRescState());
            stmt.setString(index++, vo.getState());
            stmt.setDate(index++, DAOUtils.parseDate(vo.getCreateDate()));
            stmt.setDate(index++, DAOUtils.parseDate(vo.getEffDate()));
            stmt.setDate(index++, DAOUtils.parseDate(vo.getExpDate()));
            stmt.setString(index++, vo.getPkCalbody());
            stmt.setString(index++, vo.getCinventoryid());
            stmt.setString(index++, vo.getVbatchcode());
            stmt.setString(index++, presource_instance_id);

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
        String tableName = "RC_ENTITY";

        if (tableType == 1) {
            tableName = "RC_ENTITY2";
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append("UPDATE " + tableName +
                " SET resource_instance_id = ?,sales_resource_id = ?,resource_instance_code = ?,resource_level = ?,resource_kind = ?,lan_id = ?,owner_type = ?,owner_id = ?,storage_id = ?,CURR_STATE = ?,state = ?,create_date = ?,eff_date = ?,exp_date = ?,pk_calbody = ?,cinventoryid = ?,vbatchcode = ?");

            if (updateFlag == 1) {
                sql.append(" , usage = ?");
            }

            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcEntityVO) vo).getRescInstanceId())) {
                ((RcEntityVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcEntityVO) vo).getRescInstanceId());

            if ("".equals(((RcEntityVO) vo).getSalesRescId())) {
                ((RcEntityVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcEntityVO) vo).getSalesRescId());
            stmt.setString(index++, ((RcEntityVO) vo).getRescInstanceCode());

            if ("".equals(((RcEntityVO) vo).getRescLevel())) {
                ((RcEntityVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcEntityVO) vo).getRescLevel());
            stmt.setString(index++, ((RcEntityVO) vo).getRescKind());

            if ("".equals(((RcEntityVO) vo).getLanId())) {
                ((RcEntityVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcEntityVO) vo).getLanId());
            stmt.setString(index++, ((RcEntityVO) vo).getOwnerType());

            if ("".equals(((RcEntityVO) vo).getOwnerId())) {
                ((RcEntityVO) vo).setOwnerId(null);
            }

            stmt.setString(index++, ((RcEntityVO) vo).getOwnerId());

            if ("".equals(((RcEntityVO) vo).getStorageId())) {
                ((RcEntityVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcEntityVO) vo).getStorageId());
            stmt.setString(index++, ((RcEntityVO) vo).getRescState());
            stmt.setString(index++, ((RcEntityVO) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDate(((RcEntityVO) vo).getCreateDate()));
            stmt.setDate(index++,
                DAOUtils.parseDate(((RcEntityVO) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDate(((RcEntityVO) vo).getExpDate()));
            stmt.setString(index++, ((RcEntityVO) vo).getPkCalbody());
            stmt.setString(index++, ((RcEntityVO) vo).getCinventoryid());
            stmt.setString(index++, ((RcEntityVO) vo).getVbatchcode());

            if (updateFlag == 1) {
                stmt.setString(index++, ((RcEntityVO) vo).getUsage());
            }

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
            String SQL_SELECT_COUNT_Use = SQL_SELECT_COUNT;

            if (flag == 2) {
                SQL_SELECT_COUNT_Use = SQL_SELECT_COUNT_2;
            }

            int orderbyIndex = whereCond.toUpperCase().lastIndexOf("ORDER BY");

            if (orderbyIndex > 0) {
                whereCond = whereCond.substring(0, orderbyIndex);
            }

            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = SQL_SELECT_COUNT_Use + " WHERE " + whereCond;
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

    public long delete(String presource_instance_id) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, presource_instance_id);
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

    public int getFlag() {
        return flag;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setUpdateFlag(int updateFlag) {
        this.updateFlag = updateFlag;
    }

    public VO getEmptyVO() {
        return new RcEntityVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT_EX + " WHERE " + whereCond;
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

    public boolean checkEntity(String salesRescId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String id = "";
        String SQL = " select family_id from SALES_RESOURCE WHERE SALES_RESOURCE_ID= ?";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setString(1, salesRescId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getString("family_id");

                if ("104".equals(id)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
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
    }

    public int getTableType() {
        return tableType;
    }

    public void setTableType(int tableType) {
        this.tableType = tableType;
    }
}
