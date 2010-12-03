package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.common.EncryptDESUtils;
import com.ztesoft.crm.salesres.dao.RcEntityDAO2;
import com.ztesoft.crm.salesres.vo.RcAttrDefVO;
import com.ztesoft.crm.salesres.vo.RcEntityVO2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcEntityDAOImpl2 implements RcEntityDAO2 {
    //private String SQL_SELECT = "SELECT resource_instance_id,sales_resource_id,resource_instance_code,resource_level,resource_kind,lan_id,owner_type,owner_id,storage_id,curr_state,state,create_date,eff_date,exp_date,pk_calbody,cinventoryid,vbatchcode FROM RC_ENTITY";
    //private String SQL_SELECT = " SELECT a.resource_instance_id,a.sales_resource_id,a.resource_instance_code,a.resource_level,a.resource_kind,a.lan_id,a.owner_type,a.owner_id,a.storage_id,a.curr_state,a.state,a.create_date,a.eff_date,a.exp_date,a.pk_calbody,a.cinventoryid,a.vbatchcode,b.storage_name,c.sales_resource_name FROM (rc_entity a left join RC_STORAGE b  on a.STORAGE_ID=b.STORAGE_ID) left join SALES_RESOURCE c on a.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID left join RC_FAMILY d on c.FAMILY_ID=d.FAMILY_ID";
    private String SQL_SELECT = " SELECT a.resource_instance_id,a.sales_resource_id,a.resource_instance_code,a.resource_level,a.resource_kind,a.lan_id,a.owner_type,a.owner_id,a.storage_id,a.curr_state,a.state,a.create_date,a.eff_date,a.exp_date,a.pk_calbody,a.cinventoryid,a.vbatchcode,b.storage_name,c.sales_resource_name ,a.e_pwd ,a.manufacturer, a.provider,a.shopkeeper FROM rc_entity a, RC_STORAGE b,SALES_RESOURCE c,RC_FAMILY d where a.STORAGE_ID=b.STORAGE_ID and a.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID and c.FAMILY_ID=d.FAMILY_ID ";

    //private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_ENTITY";
    //private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM rc_entity a left join RC_STORAGE b  on a.STORAGE_ID=b.STORAGE_ID left join SALES_RESOURCE c on a.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID left join RC_FAMILY d on c.FAMILY_ID=d.FAMILY_ID"
    private String SQL_SELECT_COUNT = "SELECT COUNT(a.resource_instance_id) AS COL_COUNTS FROM rc_entity a ,RC_STORAGE b,SALES_RESOURCE c,RC_FAMILY d where a.STORAGE_ID=b.STORAGE_ID and a.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID and c.FAMILY_ID=d.FAMILY_ID ";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_ENTITY ( resource_instance_id,sales_resource_id,resource_instance_code,resource_level,resource_kind,lan_id,owner_type,owner_id,storage_id,curr_state,state,create_date,eff_date,exp_date,pk_calbody,cinventoryid,vbatchcode,e_pwd,pre_code,post_code,middle_code,manufacturer,provider,shopkeeper ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?  )";
    private String SQL_UPDATE = "UPDATE RC_ENTITY SET  resource_instance_id = ?, sales_resource_id = ?, resource_instance_code = ?, resource_level = ?, resource_kind = ?, lan_id = ?, owner_type = ?, owner_id = ?, storage_id = ?, curr_state = ?, state = ?, create_date = ?, eff_date = ?, exp_date = ?, pk_calbody = ?, cinventoryid = ?, vbatchcode = ?,manufacturer= ?,provider= ?,shopkeeper= ?  WHERE";
    private String SQL_DELETE = "DELETE FROM RC_ENTITY WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_ENTITY ";
    private String tableName = "";
    private String usageFlag = "0";

    public RcEntityDAOImpl2() {
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
            RcEntityVO2 vo = new RcEntityVO2();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcEntityVO2 vo, ResultSet rs)
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
        vo.setCurrState(DAOUtils.trimStr(rs.getString("curr_state")));
        vo.setState(DAOUtils.trimStr(rs.getString("state")));
        vo.setCreateDate(DAOUtils.getFormatedDateTime(rs.getDate("create_date")));
        vo.setEffDate(DAOUtils.getFormatedDateTime(rs.getDate("eff_date")));
        vo.setExpDate(DAOUtils.getFormatedDateTime(rs.getDate("exp_date")));
        vo.setPkCalbody(DAOUtils.trimStr(rs.getString("pk_calbody")));
        vo.setCinventoryid(DAOUtils.trimStr(rs.getString("cinventoryid")));
        vo.setVbatchcode(DAOUtils.trimStr(rs.getString("vbatchcode")));
        vo.setStorageName(DAOUtils.trimStr(rs.getString("storage_name")));
        vo.setManufacturer(DAOUtils.trimStr(rs.getString("manufacturer")));
        vo.setProvider(DAOUtils.trimStr(rs.getString("provider")));
        vo.setShopkeeper(DAOUtils.trimStr(rs.getString("shopkeeper")));
        vo.setSalsRescName(DAOUtils.trimStr(rs.getString("sales_resource_name")));

        if (usageFlag == "1") {
            vo.setUsage(DAOUtils.trimStr(rs.getString("usage")));
            vo.setManageMode(DAOUtils.trimStr(rs.getString("manage_mode")));
        } else {
            vo.setEPwd(EncryptDESUtils.getInstance()
                                      .decrypt(DAOUtils.trimStr(rs.getString(
                            "e_pwd"))));
        }
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcEntityVO2 vo = new RcEntityVO2();

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

            if (whereCond.trim().toUpperCase().startsWith("AND")) {
                SQL = SQL_SELECT + " " + whereCond;
            } else {
                SQL = SQL_SELECT + " and " + whereCond;
            }

            if (!"".equals(tableName)) {
                SQL = SQL.replaceAll("rc_entity", tableName);
            }

            System.out.println("资源实例表：" + SQL);
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

    public boolean checkManageMode(String whereCond) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "select manage_mode from SALES_RESOURCE where manage_mode = 0 and" +
                whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setMaxRows(maxRows);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

            //				return true;
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

    public void insert2(VO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "INSERT INTO RC_ENTITY2 ( resource_instance_id,sales_resource_id,resource_instance_code,resource_level,resource_kind,lan_id,owner_type,owner_id,storage_id,curr_state,state,create_date,eff_date,exp_date,pk_calbody,cinventoryid,vbatchcode,e_pwd,pre_code,post_code,middle_code ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));

            int index = 1;

            if ("".equals(((RcEntityVO2) vo).getRescInstanceId())) {
                ((RcEntityVO2) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getRescInstanceId());

            if ("".equals(((RcEntityVO2) vo).getSalesRescId())) {
                ((RcEntityVO2) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getSalesRescId());
            stmt.setString(index++, ((RcEntityVO2) vo).getRescInstanceCode());

            if ("".equals(((RcEntityVO2) vo).getRescLevel())) {
                ((RcEntityVO2) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getRescLevel());
            stmt.setString(index++, ((RcEntityVO2) vo).getRescKind());

            if ("".equals(((RcEntityVO2) vo).getLanId())) {
                ((RcEntityVO2) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getLanId());
            stmt.setString(index++, ((RcEntityVO2) vo).getOwnerType());

            if ("".equals(((RcEntityVO2) vo).getOwnerId())) {
                ((RcEntityVO2) vo).setOwnerId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getOwnerId());

            if ("".equals(((RcEntityVO2) vo).getStorageId())) {
                ((RcEntityVO2) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getStorageId());
            stmt.setString(index++, ((RcEntityVO2) vo).getCurrState());
            stmt.setString(index++, ((RcEntityVO2) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcEntityVO2) vo).getCreateDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcEntityVO2) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcEntityVO2) vo).getExpDate()));
            stmt.setString(index++, ((RcEntityVO2) vo).getPkCalbody());
            stmt.setString(index++, ((RcEntityVO2) vo).getCinventoryid());
            stmt.setString(index++, ((RcEntityVO2) vo).getVbatchcode());
            stmt.setString(index++, ((RcEntityVO2) vo).getEPwd());
            stmt.setString(index++, ((RcEntityVO2) vo).getPreCode());
            stmt.setString(index++, ((RcEntityVO2) vo).getPostCode());
            stmt.setString(index++, ((RcEntityVO2) vo).getMiddleCode());

            int rows = stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                sql, se);
        } finally {
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

            if ("".equals(((RcEntityVO2) vo).getRescInstanceId())) {
                ((RcEntityVO2) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getRescInstanceId());

            if ("".equals(((RcEntityVO2) vo).getSalesRescId())) {
                ((RcEntityVO2) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getSalesRescId());
            stmt.setString(index++, ((RcEntityVO2) vo).getRescInstanceCode());

            if ("".equals(((RcEntityVO2) vo).getRescLevel())) {
                ((RcEntityVO2) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getRescLevel());
            stmt.setString(index++, ((RcEntityVO2) vo).getRescKind());

            if ("".equals(((RcEntityVO2) vo).getLanId())) {
                ((RcEntityVO2) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getLanId());
            stmt.setString(index++, ((RcEntityVO2) vo).getOwnerType());

            if ("".equals(((RcEntityVO2) vo).getOwnerId())) {
                ((RcEntityVO2) vo).setOwnerId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getOwnerId());

            if ("".equals(((RcEntityVO2) vo).getStorageId())) {
                ((RcEntityVO2) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getStorageId());
            stmt.setString(index++, ((RcEntityVO2) vo).getCurrState());
            stmt.setString(index++, ((RcEntityVO2) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcEntityVO2) vo).getCreateDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcEntityVO2) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcEntityVO2) vo).getExpDate()));
            stmt.setString(index++, ((RcEntityVO2) vo).getPkCalbody());
            stmt.setString(index++, ((RcEntityVO2) vo).getCinventoryid());
            stmt.setString(index++, ((RcEntityVO2) vo).getVbatchcode());
            stmt.setString(index++, ((RcEntityVO2) vo).getEPwd());
            stmt.setString(index++, ((RcEntityVO2) vo).getPreCode());
            stmt.setString(index++, ((RcEntityVO2) vo).getPostCode());
            stmt.setString(index++, ((RcEntityVO2) vo).getMiddleCode());
            stmt.setString(index++, ((RcEntityVO2) vo).getManufacturer());
            stmt.setString(index++, ((RcEntityVO2) vo).getProvider());
            stmt.setString(index++, ((RcEntityVO2) vo).getShopkeeper());

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

    public boolean update2(String whereCond, VO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_ENTITY2 SET resource_instance_id = ?,sales_resource_id = ?,resource_instance_code = ?,resource_level = ?,resource_kind = ?,lan_id = ?,owner_type = ?,owner_id = ?,storage_id = ?,curr_state = ?,state = ?,create_date = ?,eff_date = ?,exp_date = ?,pk_calbody = ?,cinventoryid = ?,vbatchcode = ?,e_pwd=?,middle_code=?,pre_code=?,post_code=?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcEntityVO2) vo).getRescInstanceId())) {
                ((RcEntityVO2) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getRescInstanceId());

            if ("".equals(((RcEntityVO2) vo).getSalesRescId())) {
                ((RcEntityVO2) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getSalesRescId());
            stmt.setString(index++, ((RcEntityVO2) vo).getRescInstanceCode());

            if ("".equals(((RcEntityVO2) vo).getRescLevel())) {
                ((RcEntityVO2) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getRescLevel());
            stmt.setString(index++, ((RcEntityVO2) vo).getRescKind());

            if ("".equals(((RcEntityVO2) vo).getLanId())) {
                ((RcEntityVO2) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getLanId());
            stmt.setString(index++, ((RcEntityVO2) vo).getOwnerType());

            if ("".equals(((RcEntityVO2) vo).getOwnerId())) {
                ((RcEntityVO2) vo).setOwnerId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getOwnerId());

            if ("".equals(((RcEntityVO2) vo).getStorageId())) {
                ((RcEntityVO2) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getStorageId());
            stmt.setString(index++, ((RcEntityVO2) vo).getCurrState());
            stmt.setString(index++, ((RcEntityVO2) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDate(((RcEntityVO2) vo).getCreateDate()));
            stmt.setDate(index++,
                DAOUtils.parseDate(((RcEntityVO2) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDate(((RcEntityVO2) vo).getExpDate()));
            stmt.setString(index++, ((RcEntityVO2) vo).getPkCalbody());
            stmt.setString(index++, ((RcEntityVO2) vo).getCinventoryid());
            stmt.setString(index++, ((RcEntityVO2) vo).getVbatchcode());
            stmt.setString(index++, ((RcEntityVO2) vo).getEPwd());
            stmt.setString(index++, ((RcEntityVO2) vo).getMiddleCode());
            stmt.setString(index++, ((RcEntityVO2) vo).getPreCode());
            stmt.setString(index++, ((RcEntityVO2) vo).getPostCode());

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
                "UPDATE RC_ENTITY SET resource_instance_id = ?,sales_resource_id = ?,resource_instance_code = ?,resource_level = ?,resource_kind = ?,lan_id = ?,owner_type = ?,owner_id = ?,storage_id = ?,curr_state = ?,state = ?,create_date = ?,eff_date = ?,exp_date = ?,pk_calbody = ?,cinventoryid = ?,vbatchcode = ?,e_pwd=?,middle_code=?,pre_code=?,post_code=?,manufacturer= ?,provider= ?,shopkeeper= ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcEntityVO2) vo).getRescInstanceId())) {
                ((RcEntityVO2) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getRescInstanceId());

            if ("".equals(((RcEntityVO2) vo).getSalesRescId())) {
                ((RcEntityVO2) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getSalesRescId());
            stmt.setString(index++, ((RcEntityVO2) vo).getRescInstanceCode());

            if ("".equals(((RcEntityVO2) vo).getRescLevel())) {
                ((RcEntityVO2) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getRescLevel());
            stmt.setString(index++, ((RcEntityVO2) vo).getRescKind());

            if ("".equals(((RcEntityVO2) vo).getLanId())) {
                ((RcEntityVO2) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getLanId());
            stmt.setString(index++, ((RcEntityVO2) vo).getOwnerType());

            if ("".equals(((RcEntityVO2) vo).getOwnerId())) {
                ((RcEntityVO2) vo).setOwnerId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getOwnerId());

            if ("".equals(((RcEntityVO2) vo).getStorageId())) {
                ((RcEntityVO2) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcEntityVO2) vo).getStorageId());
            stmt.setString(index++, ((RcEntityVO2) vo).getCurrState());
            stmt.setString(index++, ((RcEntityVO2) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDate(((RcEntityVO2) vo).getCreateDate()));
            stmt.setDate(index++,
                DAOUtils.parseDate(((RcEntityVO2) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDate(((RcEntityVO2) vo).getExpDate()));
            stmt.setString(index++, ((RcEntityVO2) vo).getPkCalbody());
            stmt.setString(index++, ((RcEntityVO2) vo).getCinventoryid());
            stmt.setString(index++, ((RcEntityVO2) vo).getVbatchcode());
            stmt.setString(index++, ((RcEntityVO2) vo).getEPwd());
            stmt.setString(index++, ((RcEntityVO2) vo).getMiddleCode());
            stmt.setString(index++, ((RcEntityVO2) vo).getPreCode());
            stmt.setString(index++, ((RcEntityVO2) vo).getPostCode());
            stmt.setString(index++, ((RcEntityVO2) vo).getManufacturer());
            stmt.setString(index++, ((RcEntityVO2) vo).getProvider());
            stmt.setString(index++, ((RcEntityVO2) vo).getShopkeeper());

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
            SQL = SQL_SELECT_COUNT + "  " + whereCond;

            if (!"".equals(tableName)) {
                SQL = SQL.replaceAll("rc_entity", tableName);
            }

            System.out.println("资源实例表=" + SQL);
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

    public long deleteByCond2(String whereCond) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String SQL = "DELETE FROM RC_ENTITY2  where ";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL += whereCond;
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

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public VO getEmptyVO() {
        return new RcEntityVO2();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + "  " + whereCond;

        String filterSQL = SQL;

        if (queryFilter != null) {
            filterSQL = queryFilter.doPreFilter(SQL);
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = filterSQL;

            if (!"".equals(tableName)) {
                SQL = SQL.replaceAll("rc_entity", tableName);
            }

            System.out.println("资源实例表：" + SQL);
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
     * 检查输入的资源实例编码是否有效;返回值为true，则有效，否则无效;
     * @param rescInstanceCode
     * @return
     */
    public boolean checkRescInstanceCode(String rescInstanceCode) {
        boolean retv = false;

        if ((rescInstanceCode == null) || "".equals(rescInstanceCode)) {
            return retv;
        } else {
            String strSql = "select count(RESOURCE_INSTANCE_CODE) as codecount from rc_entity where RESOURCE_INSTANCE_CODE='" +
                rescInstanceCode + "'";
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(strSql));
                rs = stmt.executeQuery();

                if (rs.next()) {
                    String codecount = rs.getString("codecount");

                    if ((codecount == null) || "".equals(codecount)) {
                        codecount = "0";
                    }

                    int intcount = Integer.parseInt(codecount);

                    if (intcount > 0) {
                        retv = true;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }

            return retv;
        }
    }

    /**
     * 检查输入的资源实例编码是否有效;返回值为true，则有效，否则无效;
     * @param rescInstanceCode
     * @return
     */
    public boolean checkRescInstanceCode(RcEntityVO2 rvo) {
        boolean retv = false;
        String rescInstanceId = rvo.getRescInstanceId();
        String rescInstanceCode = rvo.getRescInstanceCode();

        if ((rescInstanceCode == null) || "".equals(rescInstanceCode)) {
            return retv;
        } else {
            String strSql = "select count(RESOURCE_INSTANCE_CODE) as codecount from rc_entity where RESOURCE_INSTANCE_CODE='" +
                rescInstanceCode + "'";

            if ((rescInstanceId != null) && !rescInstanceId.equals("")) {
                strSql += (" and RESOURCE_INSTANCE_ID!=" + rescInstanceId);
            }

            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(strSql));
                rs = stmt.executeQuery();

                if (rs.next()) {
                    String codecount = rs.getString("codecount");

                    if ((codecount == null) || "".equals(codecount)) {
                        codecount = "0";
                    }

                    int intcount = Integer.parseInt(codecount);

                    if (intcount > 0) {
                        retv = true;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }

            return retv;
        }
    }

    /**
    * 检查输入的资源ＩＤ是否有效;返回值为true，则有效，否则无效;
    * @param salesRescId
    * @return
    */
    public boolean checkSalesRescId(String salesRescId) {
        boolean retv = false;

        if ((salesRescId == null) || "".equals(salesRescId)) {
            return retv;
        } else {
            String strSql = "select count(SALES_RESOURCE_ID) as idcount from SALES_RESOURCE where SALES_RESOURCE_ID=" +
                salesRescId;

            //+" and sales_resource_id not in  (select sales_resource_id from sales_resource_id_rela)";
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(strSql));
                rs = stmt.executeQuery();

                if (rs.next()) {
                    String idcount = rs.getString("idcount");

                    if ((idcount == null) || "".equals(idcount)) {
                        idcount = "0";
                    }

                    int intcount = Integer.parseInt(idcount);

                    if (intcount > 0) {
                        retv = true;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }

            return retv;
        }
    }

    /**
     * 检查输入的资源ＩＤ是否有效;返回值为true，则有效，否则无效;
     * @param salesRescId
     * @return
     */
    public boolean checkStorageId(String storageId) {
        boolean retv = false;

        if ((storageId == null) || "".equals(storageId)) {
            return retv;
        } else {
            String strSql = "select count(storage_id) as idcount from rc_storage where storage_id=" +
                storageId;
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(strSql));
                rs = stmt.executeQuery();

                if (rs.next()) {
                    String idcount = rs.getString("idcount");

                    if ((idcount == null) || "".equals(idcount)) {
                        idcount = "0";
                    }

                    int intcount = Integer.parseInt(idcount);

                    if (intcount > 0) {
                        retv = true;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }

            return retv;
        }
    }

    public ArrayList searchAttrInfo(String rescInstanceId, String flag)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        String id = "";
        ArrayList retList = new ArrayList();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            if ("out".equals(flag)) {
                SQL = "select a.attr_id,a.attr_value, b.attr_desc from RC_ENTITY_ATTR_LOG a, attribute b  where a.entity_id = ?  and  a.attr_id = b.attr_id";
            } else {
                SQL = "select a.attr_id,a.attr_value, b.attr_desc from RC_ENTITY_ATTR a, attribute b  where a.entity_id = ?  and  a.attr_id = b.attr_id";
            }

            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, rescInstanceId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = DAOUtils.trimStr(rs.getString("attr_id"));

                if ((id == null) || "".equals(id)) {
                    continue;
                }

                RcAttrDefVO vo = new RcAttrDefVO();
                vo.setAttrValue(DAOUtils.trimStr(rs.getString("attr_value")));
                vo.setAttrId(DAOUtils.trimStr(rs.getString("attr_id")));
                vo.setAttrName(DAOUtils.trimStr(rs.getString("attr_desc")));
                retList.add(vo);
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

    //保存实体资源的属性信息
    public boolean insertRcEnAttrInfo(String rescInstanceId, String attrId,
        String attrValue) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = null;
        boolean flag = false;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql = " insert into RC_ENTITY_ATTR values(?,?,?)";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));

            if ("".equals(rescInstanceId)) {
                rescInstanceId = null;
            }

            stmt.setString(1, rescInstanceId);

            if ("".equals(attrId)) {
                attrId = null;
            }

            stmt.setString(2, attrId);

            if ("".equals(attrValue)) {
                attrValue = null;
            }

            stmt.setString(3, attrValue);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                flag = true;
            }
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return flag;
    }

    //查看当前要添加的属性是否已经添加
    public long countAttrInfo(String rescInstanceId, String attrId)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = null;
        ResultSet rs = null;
        long lCount = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql = " SELECT COUNT(*) AS COL_COUNTS from RC_ENTITY_ATTR where entity_id = ? and attr_id = ? ";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setString(1, rescInstanceId);
            stmt.setString(2, attrId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                lCount = rs.getLong("COL_COUNTS");
            }
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return lCount;
    }

    public int deleteAttrInfo(String rescInstanceId, String attrValue)
        throws DAOSystemException {
        if ((rescInstanceId == null) || (rescInstanceId.trim().length() < 1) ||
                (attrValue == null) || (attrValue.trim().length() < 1)) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = " delete from rc_entity_attr where entity_id = ? and attr_id = ? ";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setString(1, rescInstanceId);
            stmt.setString(2, attrValue);
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
     * 根据实例id删除该实例的所有属性
     * @param rescInstanceId String
     * @throws DAOSystemException
     * @return int
     */
    public int deleteAttrInfoAll(String rescInstanceId)
        throws DAOSystemException {
        if ((rescInstanceId == null) || (rescInstanceId.trim().length() < 1)) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = " delete from rc_entity_attr where entity_id = ? ";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setString(1, rescInstanceId);
            rows = stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while deleting sql:\n" +
                SQL + "||rescInstanceId:" + rescInstanceId, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return rows;
    }

    /**
     * 删除起始和结束编码之间的实例的属性
     * @param resBCode String
     * @param resECode String
     * @throws DAOSystemException
     * @return int
     */
    public int deleteAttrByCode(String salesRescId, String resBCode,
        String resECode, String outStorageId) throws DAOSystemException {
        if ((outStorageId == null) ||
                (((resBCode == null) || (resBCode.trim().length() < 1)) &&
                ((resECode == null) || (resECode.trim().length() < 1)))) {
            return 0;
        }

        String cond = " rc_entity.SALES_RESOURCE_ID=" +
            DAOUtils.filterQureyCond(salesRescId);

        if (outStorageId.trim().length() > 0) {
            cond += (" and rc_entity.storage_id=" + outStorageId + " ");
        }

        //cond +=" and  resource_instance_code between to_number('"+DAOUtils.filterQureyCond(resBCode)+"')"+
        //" and to_number('" +DAOUtils.filterQureyCond(resECode)+"')";
        if ((resBCode != null) && (resBCode.trim().length() > 0)) {
            cond += (" and resource_instance_code>='" + resBCode +
            "' and length(resource_instance_code)>= length('" + resBCode +
            "')");
        }

        if ((resECode != null) && (resECode.trim().length() > 0)) {
            cond += (" and resource_instance_code<='" + resECode +
            "'  and length(resource_instance_code)<= length('" + resECode +
            "')");
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = " delete from rc_entity_attr where entity_id in " +
                " (select resource_instance_id from rc_entity where " + cond +
                ")";
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
     * 更新起始和结束编码之间的实例的仓库信息，改为inStorageId
     * @param resBCode String
     * @param resECode String
     * @throws DAOSystemException
     * @return int
     */
    public int updateStorageByCode(String tableName, String salesRescId,
        String resBCode, String resECode, String outStorageId,
        String inStorageId) throws DAOSystemException {
        if ((tableName == null) || (tableName.trim().length() < 1) ||
                ((salesRescId == null) || (salesRescId.trim().length() < 1)) ||
                (((resBCode == null) || (resBCode.trim().length() < 1)) &&
                ((resECode == null) || (resECode.trim().length() < 1))) ||
                (outStorageId == null) || (outStorageId.trim().length() < 1) ||
                (inStorageId == null) || (inStorageId.trim().length() < 1)) {
            return 0;
        }

        String cond = "";

        if ((resBCode != null) && (resBCode.trim().length() > 0)) {
            cond = " and resource_instance_code>='" +
                DAOUtils.filterQureyCond(resBCode) +
                "' and length(resource_instance_code)>=length('" +
                DAOUtils.filterQureyCond(resBCode) + "') ";
        }

        if ((resECode != null) && (resECode.trim().length() > 0)) {
            cond += (" and resource_instance_code<='" +
            DAOUtils.filterQureyCond(resECode) +
            "' and length(resource_instance_code)<=length('" +
            DAOUtils.filterQureyCond(resECode) + "') ");
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = " update " + tableName +
                " set storage_id = ? where SALES_RESOURCE_ID=? and storage_id = ? " +
                cond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setString(1, inStorageId);
            stmt.setString(2, salesRescId);
            stmt.setString(3, outStorageId);
            rows = stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException(
                "SQLException while updateStorageByCode sql:\n" + SQL + "[" +
                inStorageId + "," + outStorageId + "]", se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return rows;
    }

    public void setUsageFlag(String usageFlag) {
        this.usageFlag = usageFlag;
    }

    public void setSQL_SELECT(String SQL_SELECT) {
        this.SQL_SELECT = SQL_SELECT;
    }

    public void setSQL_SELECT_COUNT(String SQL_SELECT_COUNT) {
        this.SQL_SELECT_COUNT = SQL_SELECT_COUNT;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
