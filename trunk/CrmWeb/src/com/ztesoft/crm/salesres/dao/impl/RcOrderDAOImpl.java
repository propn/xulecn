package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.bo.CommonUtilBo;
import com.ztesoft.crm.salesres.dao.RcOrderDAO;
import com.ztesoft.crm.salesres.vo.RcAppTypeVO;
import com.ztesoft.crm.salesres.vo.RcApplicationVO;
import com.ztesoft.crm.salesres.vo.RcOrderExcVO;
import com.ztesoft.crm.salesres.vo.RcOrderListVO;
import com.ztesoft.crm.salesres.vo.RcOrderSegInfoVO;
import com.ztesoft.crm.salesres.vo.RcOrderVO;
import com.ztesoft.crm.salesres.vo.RcStorageVO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class RcOrderDAOImpl implements RcOrderDAO {
    private String SQL_SELECT_Orign = "SELECT ORDER_ID,APP_ID,APP_TYPE,OPER_ID,DEPART_ID,TACHE_ID,STATE_ID,TACHE_TIME,APP_TIME,END_TIME,OUT_STORAGE_ID,IN_STORAGE_ID,APP_STORAGE_ID,SALES_RESOURCE_ID,APP_AMOUNT,ACT_AMOUNT,REQUIRE_TIME,COMMENTS,RES_COMMENTS,RES_B_CODE,RES_E_CODE,BACK_ORDER_ID FROM RC_ORDER";

    // private String SQL_SELECT = "SELECT
    // order_id,app_id,app_type,oper_id,depart_id,tache_id,state_id,tache_time,app_time,end_time,out_storage_id,in_storage_id,app_storage_id,sales_resource_id,app_amount,act_amount,require_time,comments,res_comments
    // FROM RC_ORDER";
    private String SQL_SELECT = "select a.*,c.app_type_name,d.tache_name,e.state_name,f.sales_resource_name,g.STORAGE_NAME as inStorageName from " +
        " rc_order a inner join rc_application b on a.app_id=b.app_id inner join rc_app_type c on b.app_type=c.app_type " +
        " inner join rc_order_tache d on a.TACHE_ID = d.TACHE_ID inner join rc_order_state e on a.STATE_id = e.STATE_id" +
        " inner join SALES_RESOURCE f on a.sales_resource_id=f.sales_resource_id " +
        " left outer join rc_storage g on a.in_storage_id=g.storage_id where 1=1 ";
    private String SQL_SELECT_COUNT = "select COUNT(*) AS COL_COUNTS from " +
        " rc_order a inner join rc_application b on a.app_id=b.app_id inner join rc_app_type c on b.app_type=c.app_type " +
        " inner join rc_order_tache d on a.TACHE_ID = d.TACHE_ID inner join rc_order_state e on a.STATE_id = e.STATE_id" +
        " inner join SALES_RESOURCE f on a.sales_resource_id=f.sales_resource_id " +
        " left outer join rc_storage g on a.in_storage_id=g.storage_id where 1=1 ";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_ORDER ( ORDER_ID,APP_ID,APP_TYPE,OPER_ID,DEPART_ID,TACHE_ID,STATE_ID,TACHE_TIME,APP_TIME,END_TIME,OUT_STORAGE_ID,IN_STORAGE_ID,APP_STORAGE_ID,SALES_RESOURCE_ID,APP_AMOUNT,ACT_AMOUNT,REQUIRE_TIME,COMMENTS,RES_COMMENTS,RES_B_CODE,RES_E_CODE,BACK_ORDER_ID,rec_opt_type ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_ORDER SET  order_id = ?, app_id = ?, app_type = ?, oper_id = ?, depart_id = ?, tache_id = ?, state_id = ?, tache_time = ?, app_time = ?, end_time = ?, out_storage_id = ?, in_storage_id = ?, app_storage_id = ?, sales_resource_id = ?, app_amount = ?, act_amount = ?, require_time = ?, comments = ?, res_comments = ?, RES_B_CODE = ?, RES_E_CODE = ? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_ORDER WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_ORDER ";
    private String operstate = "";

    // flag=2代表查询最原始的表字段
    private int flag = 0;
    private boolean filtered = false;

    public RcOrderDAOImpl() {
    }

    public void setSQL(String sql_select, String sql_select_count,
        String operState) {
        this.SQL_SELECT = sql_select;
        this.SQL_SELECT_COUNT = sql_select_count;
        this.operstate = operState;
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
            RcOrderVO vo = new RcOrderVO();

            if (flag == 1) {
                populateDto_1(vo, rs);
            } else if (flag == 2) {
                populateDto_2(vo, rs);
            } else if (flag == 3) {
                populateDto_3(vo, rs);
            } else {
                populateDto(vo, rs);
            }

            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcOrderVO vo, ResultSet rs)
        throws SQLException {
        if ("excel".equals(operstate)) {
            vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
            vo.setAppId(DAOUtils.trimStr(rs.getString("app_id")));
            vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
            vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
            vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
            vo.setTacheId(DAOUtils.trimStr(rs.getString("tache_id")));
            vo.setStateId(DAOUtils.trimStr(rs.getString("state_id")));
            vo.setTacheTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                        "tache_time")));
            vo.setAppTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                        "app_time")));
            vo.setEndTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                        "end_time")));
            vo.setOutStorageId(DAOUtils.trimStr(rs.getString("out_storage_id")));
            vo.setInStorageId(DAOUtils.trimStr(rs.getString("in_storage_id")));
            vo.setAppStorageId(DAOUtils.trimStr(rs.getString("app_storage_id")));
            vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
            vo.setAppAmount(DAOUtils.trimStr(rs.getString("app_amount")));
            vo.setActAmount(DAOUtils.trimStr(rs.getString("act_amount")));
            vo.setRequireTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                        "require_time")));
            vo.setComments(DAOUtils.trimStr(rs.getString("comments")));
            vo.setResComments(DAOUtils.trimStr(rs.getString("res_comments")));
            vo.setResBCode(DAOUtils.trimStr(rs.getString("res_b_code")));
            vo.setResECode(DAOUtils.trimStr(rs.getString("res_e_code")));

            vo.setOperName(DAOUtils.trimStr(rs.getString("oper_name")));
            vo.setAppStorageName(DAOUtils.trimStr(rs.getString("storage_name")));
            vo.setSalesRescName(DAOUtils.trimStr(rs.getString(
                        "sales_resource_name")));
        } else {
            vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
            vo.setAppId(DAOUtils.trimStr(rs.getString("app_id")));
            vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
            vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
            vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
            vo.setTacheId(DAOUtils.trimStr(rs.getString("tache_id")));
            vo.setStateId(DAOUtils.trimStr(rs.getString("state_id")));
            vo.setTacheTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                        "tache_time")));
            vo.setAppTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                        "app_time")));
            vo.setEndTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                        "end_time")));
            vo.setOutStorageId(DAOUtils.trimStr(rs.getString("out_storage_id")));
            vo.setInStorageId(DAOUtils.trimStr(rs.getString("in_storage_id")));
            vo.setAppStorageId(DAOUtils.trimStr(rs.getString("app_storage_id")));
            vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
            vo.setAppAmount(DAOUtils.trimStr(rs.getString("app_amount")));
            vo.setActAmount(DAOUtils.trimStr(rs.getString("act_amount")));
            vo.setRequireTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                        "require_time")));
            vo.setComments(DAOUtils.trimStr(rs.getString("comments")));
            vo.setResComments(DAOUtils.trimStr(rs.getString("res_comments")));
            vo.setResBCode(DAOUtils.trimStr(rs.getString("res_b_code")));
            vo.setResECode(DAOUtils.trimStr(rs.getString("res_e_code")));
            vo.setBackOrderId(DAOUtils.trimStr(rs.getString("BACK_ORDER_ID")));

            vo.setAppTypeName(DAOUtils.trimStr(rs.getString("app_type_name")));
            vo.setTacheName(DAOUtils.trimStr(rs.getString("tache_name")));
            vo.setStateName(DAOUtils.trimStr(rs.getString("state_name")));
            vo.setSalesRescName(DAOUtils.trimStr(rs.getString(
                        "sales_resource_name")));
            vo.setInStorageName(DAOUtils.trimStr(rs.getString("inStorageName")));
            vo.setAppStorageName(getRcStorageName(vo.getAppStorageId()));
        }
    }

    protected void populateDto_1(RcOrderVO vo, ResultSet rs)
        throws SQLException {
        vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
        vo.setStorageId(DAOUtils.trimStr(rs.getString("storage_id")));
        vo.setInAmount(DAOUtils.trimStr(rs.getString("in_amount")));
        vo.setOutAmount(DAOUtils.trimStr(rs.getString("out_amount")));
        vo.setNetAmount(DAOUtils.trimStr(rs.getString("net_amount")));
        vo.setFamilyName(DAOUtils.trimStr(rs.getString("FAMILY_NAME")));
        vo.setDepartName(DAOUtils.trimStr(rs.getString("DEPART_NAME")));
        vo.setSalesRescName(DAOUtils.trimStr(rs.getString("sales_resource_name")));
        vo.setStorageName(DAOUtils.trimStr(rs.getString("storage_name")));
        vo.setBeginDate(DAOUtils.trimStr(rs.getString("beginDate")));
        vo.setEndDate(DAOUtils.trimStr(rs.getString("endDate")));
    }

    protected void populateDto_4(RcOrderVO vo, ResultSet rs)
        throws SQLException {
        vo.setInAmount(DAOUtils.trimStr(rs.getString("in_amount")));
        vo.setOutAmount(DAOUtils.trimStr(rs.getString("out_amount")));
        vo.setInStorageName(DAOUtils.trimStr(rs.getString("in_storage_name")));
        vo.setOutStorageName(DAOUtils.trimStr(rs.getString("out_storage_name")));

        vo.setOperName(DAOUtils.trimStr(rs.getString("oper_name")));
        vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
        vo.setTacheTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "tache_time")));
    }

    // 复制最原始的表字段
    protected void populateDto_2(RcOrderVO vo, ResultSet rs)
        throws SQLException {
        vo.setOrderId(DAOUtils.trimStr(rs.getString("ORDER_ID")));
        vo.setAppId(DAOUtils.trimStr(rs.getString("APP_ID")));
        vo.setAppType(DAOUtils.trimStr(rs.getString("APP_TYPE")));
        vo.setOperId(DAOUtils.trimStr(rs.getString("OPER_ID")));
        vo.setDepartId(DAOUtils.trimStr(rs.getString("DEPART_ID")));
        vo.setTacheId(DAOUtils.trimStr(rs.getString("TACHE_ID")));
        vo.setStateId(DAOUtils.trimStr(rs.getString("STATE_ID")));
        vo.setTacheTime(DAOUtils.getFormatedDateTime(rs.getDate("TACHE_TIME")));
        vo.setAppTime(DAOUtils.getFormatedDateTime(rs.getDate("APP_TIME")));
        vo.setEndTime(DAOUtils.getFormatedDateTime(rs.getDate("END_TIME")));
        vo.setOutStorageId(DAOUtils.trimStr(rs.getString("OUT_STORAGE_ID")));
        vo.setInStorageId(DAOUtils.trimStr(rs.getString("IN_STORAGE_ID")));
        vo.setAppStorageId(DAOUtils.trimStr(rs.getString("APP_STORAGE_ID")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setAppAmount(DAOUtils.trimStr(rs.getString("APP_AMOUNT")));
        vo.setActAmount(DAOUtils.trimStr(rs.getString("ACT_AMOUNT")));
        vo.setRequireTime(DAOUtils.getFormatedDateTime(rs.getDate(
                    "REQUIRE_TIME")));
        vo.setComments(DAOUtils.trimStr(rs.getString("COMMENTS")));
        vo.setResComments(DAOUtils.trimStr(rs.getString("RES_COMMENTS")));
        vo.setResBCode(DAOUtils.trimStr(rs.getString("RES_B_CODE")));
        vo.setResECode(DAOUtils.trimStr(rs.getString("RES_E_CODE")));
        vo.setBackOrderId(DAOUtils.trimStr(rs.getString("BACK_ORDER_ID")));
    }

    // 
    protected void populateDto_3(RcOrderVO vo, ResultSet rs)
        throws SQLException {
        vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
        vo.setAppId(DAOUtils.trimStr(rs.getString("app_id")));
        vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
        vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
        vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
        vo.setTacheId(DAOUtils.trimStr(rs.getString("tache_id")));
        vo.setStateId(DAOUtils.trimStr(rs.getString("state_id")));
        vo.setTacheTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "tache_time")));
        vo.setAppTime(DAOUtils.getFormatedDateTime(rs.getTimestamp("app_time")));
        vo.setEndTime(DAOUtils.getFormatedDateTime(rs.getTimestamp("end_time")));
        vo.setOutStorageId(DAOUtils.trimStr(rs.getString("out_storage_id")));
        vo.setInStorageId(DAOUtils.trimStr(rs.getString("in_storage_id")));
        vo.setAppStorageId(DAOUtils.trimStr(rs.getString("app_storage_id")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
        vo.setAppAmount(DAOUtils.trimStr(rs.getString("app_amount")));
        vo.setActAmount(DAOUtils.trimStr(rs.getString("act_amount")));
        vo.setRequireTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "require_time")));
        vo.setComments(DAOUtils.trimStr(rs.getString("comments")));
        vo.setResComments(DAOUtils.trimStr(rs.getString("res_comments")));
        vo.setResBCode(DAOUtils.trimStr(rs.getString("res_b_code")));
        vo.setResECode(DAOUtils.trimStr(rs.getString("res_e_code")));
        vo.setInStorageName(DAOUtils.trimStr(rs.getString("in_storage_name")));
        vo.setOutStorageName(DAOUtils.trimStr(rs.getString("out_storage_name")));
        vo.setSalesRescName(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_NAME")));
        vo.setFamilyName(DAOUtils.trimStr(rs.getString("FAMILY_NAME")));
        // 此处的oper_name是申请人的名字
        vo.setOperName(DAOUtils.trimStr(rs.getString("OPER_NAME")));
        vo.setDepartName(DAOUtils.trimStr(rs.getString("OPER_NAME")));
    }

    protected void populateDto_excel(RcOrderVO vo, ResultSet rs)
        throws SQLException {
        vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
        vo.setAppId(DAOUtils.trimStr(rs.getString("app_id")));
        vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
        vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
        vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
        vo.setTacheId(DAOUtils.trimStr(rs.getString("tache_id")));
        vo.setStateId(DAOUtils.trimStr(rs.getString("state_id")));
        vo.setTacheTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "tache_time")));
        vo.setAppTime(DAOUtils.getFormatedDateTime(rs.getTimestamp("app_time")));
        vo.setEndTime(DAOUtils.getFormatedDateTime(rs.getTimestamp("end_time")));
        vo.setOutStorageId(DAOUtils.trimStr(rs.getString("out_storage_id")));
        vo.setInStorageId(DAOUtils.trimStr(rs.getString("in_storage_id")));
        vo.setAppStorageId(DAOUtils.trimStr(rs.getString("app_storage_id")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
        vo.setAppAmount(DAOUtils.trimStr(rs.getString("app_amount")));
        vo.setActAmount(DAOUtils.trimStr(rs.getString("act_amount")));
        vo.setRequireTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "require_time")));
        vo.setComments(DAOUtils.trimStr(rs.getString("comments")));
        vo.setResComments(DAOUtils.trimStr(rs.getString("res_comments")));
        vo.setResBCode(DAOUtils.trimStr(rs.getString("res_b_code")));
        vo.setResECode(DAOUtils.trimStr(rs.getString("res_e_code")));

        vo.setOperName(DAOUtils.trimStr(rs.getString("oper_name")));
        vo.setAppStorageName(DAOUtils.trimStr(rs.getString("storage_name")));
        vo.setSalesRescName(DAOUtils.trimStr(rs.getString("sales_resource_name")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcOrderVO vo = new RcOrderVO();

        try {
            if (flag == 1) {
                populateDto_1(vo, rs);
            } else if (flag == 3) {
                populateDto_3(vo, rs);
            } else if (flag == 4) {
                populateDto_4(vo, rs);
            } else {
                populateDto(vo, rs);
            }
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
            whereCond = whereCond.trim().toUpperCase();

            if (whereCond.startsWith("AND")) {
                SQL = SQL_SELECT + whereCond;
            } else {
                SQL = SQL_SELECT + " AND " + whereCond;
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

            if ("".equals(((RcOrderVO) vo).getOrderId())) {
                ((RcOrderVO) vo).setOrderId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getOrderId());

            if ("".equals(((RcOrderVO) vo).getAppId())) {
                ((RcOrderVO) vo).setAppId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getAppId());
            stmt.setString(index++, ((RcOrderVO) vo).getAppType());

            if ("".equals(((RcOrderVO) vo).getOperId())) {
                ((RcOrderVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getOperId());

            if ("".equals(((RcOrderVO) vo).getDepartId())) {
                ((RcOrderVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getDepartId());

            if ("".equals(((RcOrderVO) vo).getTacheId())) {
                ((RcOrderVO) vo).setTacheId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getTacheId());
            stmt.setString(index++, ((RcOrderVO) vo).getStateId());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcOrderVO) vo).getTacheTime()));
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcOrderVO) vo).getAppTime()));
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcOrderVO) vo).getEndTime()));

            if ("".equals(((RcOrderVO) vo).getOutStorageId())) {
                ((RcOrderVO) vo).setOutStorageId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getOutStorageId());

            if ("".equals(((RcOrderVO) vo).getInStorageId())) {
                ((RcOrderVO) vo).setInStorageId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getInStorageId());

            if ("".equals(((RcOrderVO) vo).getAppStorageId())) {
                ((RcOrderVO) vo).setAppStorageId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getAppStorageId());

            if ("".equals(((RcOrderVO) vo).getSalesRescId())) {
                ((RcOrderVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getSalesRescId());

            if ("".equals(((RcOrderVO) vo).getAppAmount())) {
                ((RcOrderVO) vo).setAppAmount(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getAppAmount());

            if ("".equals(((RcOrderVO) vo).getActAmount())) {
                ((RcOrderVO) vo).setActAmount(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getActAmount());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcOrderVO) vo).getRequireTime()));
            stmt.setString(index++, ((RcOrderVO) vo).getComments());
            stmt.setString(index++, ((RcOrderVO) vo).getResComments());
            stmt.setString(index++, ((RcOrderVO) vo).getResBCode());
            stmt.setString(index++, ((RcOrderVO) vo).getResECode());

            if ("".equals(((RcOrderVO) vo).getBackOrderId())) {
                ((RcOrderVO) vo).setBackOrderId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getBackOrderId());
            stmt.setString(index++, ((RcOrderVO) vo).getRecOptType());

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

    public boolean update(String whereCond, VO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_ORDER SET order_id = ?,app_id = ?,app_type = ?,oper_id = ?,depart_id = ?,tache_id = ?,state_id = ?,tache_time = ?,app_time = ?,end_time = ?,out_storage_id = ?,in_storage_id = ?,app_storage_id = ?,sales_resource_id = ?,app_amount = ?,act_amount = ?,require_time = ?,comments = ?,res_comments = ?,RES_B_CODE = ?,RES_E_CODE = ? ,rec_opt_type=?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcOrderVO) vo).getOrderId())) {
                ((RcOrderVO) vo).setOrderId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getOrderId());

            if ("".equals(((RcOrderVO) vo).getAppId())) {
                ((RcOrderVO) vo).setAppId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getAppId());
            stmt.setString(index++, ((RcOrderVO) vo).getAppType());

            if ("".equals(((RcOrderVO) vo).getOperId())) {
                ((RcOrderVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getOperId());

            if ("".equals(((RcOrderVO) vo).getDepartId())) {
                ((RcOrderVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getDepartId());

            if ("".equals(((RcOrderVO) vo).getTacheId())) {
                ((RcOrderVO) vo).setTacheId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getTacheId());
            stmt.setString(index++, ((RcOrderVO) vo).getStateId());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcOrderVO) vo).getTacheTime()));
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcOrderVO) vo).getAppTime()));
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcOrderVO) vo).getEndTime()));

            if ("".equals(((RcOrderVO) vo).getOutStorageId())) {
                ((RcOrderVO) vo).setOutStorageId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getOutStorageId());

            if ("".equals(((RcOrderVO) vo).getInStorageId())) {
                ((RcOrderVO) vo).setInStorageId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getInStorageId());

            if ("".equals(((RcOrderVO) vo).getAppStorageId())) {
                ((RcOrderVO) vo).setAppStorageId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getAppStorageId());

            if ("".equals(((RcOrderVO) vo).getSalesRescId())) {
                ((RcOrderVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getSalesRescId());

            if ("".equals(((RcOrderVO) vo).getAppAmount())) {
                ((RcOrderVO) vo).setAppAmount(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getAppAmount());

            if ("".equals(((RcOrderVO) vo).getActAmount())) {
                ((RcOrderVO) vo).setActAmount(null);
            }

            stmt.setString(index++, ((RcOrderVO) vo).getActAmount());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcOrderVO) vo).getRequireTime()));
            stmt.setString(index++, ((RcOrderVO) vo).getComments());
            stmt.setString(index++, ((RcOrderVO) vo).getResComments());
            stmt.setString(index++, ((RcOrderVO) vo).getResBCode());
            stmt.setString(index++, ((RcOrderVO) vo).getResECode());
            stmt.setString(index++, ((RcOrderVO) vo).getRecOptType());

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
        String SQL = SQL_SELECT_COUNT;

        try {
            int orderbyIndex = whereCond.toUpperCase().lastIndexOf("ORDER BY");

            if (orderbyIndex > 0) {
                whereCond = whereCond.substring(0, orderbyIndex);
            }

            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            if ((whereCond != null) && (whereCond.trim().length() > 0)) {
                SQL += whereCond;
            }

            // 添加SQL过滤标志
            if (filtered) {
                stmt = conn.prepareStatement((SQL));
            } else {
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            }

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

    public int getMaxRows() {
        return maxRows;
    }

    public int getFlag() {
        return flag;
    }

    public String getSQL_SELECT() {
        return SQL_SELECT;
    }

    public String getSQL_SELECT_COUNT() {
        return SQL_SELECT_COUNT;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setSQL_SELECT(String SQL_SELECT) {
        this.SQL_SELECT = SQL_SELECT;
    }

    public void setSQL_SELECT_COUNT(String SQL_SELECT_COUNT) {
        this.SQL_SELECT_COUNT = SQL_SELECT_COUNT;
    }

    public VO getEmptyVO() {
        return new RcOrderVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT;

        if ((whereCond != null) && (whereCond.trim().length() > 0)) {
            SQL += whereCond;
        }

        String filterSQL = SQL;

        if (queryFilter != null) {
            if (!filtered) {
                filterSQL = queryFilter.doPreFilter(SQL);
            } else {
                filterSQL = queryFilter.doPreFilterWithoutFilterSQL(SQL);
            }
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = filterSQL;

            // 通过过滤标志来判断是否需要过滤
            if (filtered) {
                stmt = conn.prepareStatement((SQL),
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
            } else {
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
            }

            rs = stmt.executeQuery();

            List retList = null;

            if (queryFilter != null) {
                retList = queryFilter.doPostFilter(rs, this);
            } else {
                retList = fetchMultiResults(rs);
            }

            return retList;
        } catch (SQLException se) {
            se.printStackTrace();
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public List getOrderInfoByOrderId(String orderId) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList resultList = new ArrayList();

        if ((orderId != null) && !"".equals(orderId)) {
            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                SQL = "select a.*,d.app_type_name,b.storage_name as out_storage_name,c.storage_name as in_storage_name from rc_order a left join rc_storage b on a.out_storage_id = b.storage_id left join rc_storage c on a.in_storage_id=c.storage_id left join rc_app_type d on  a.app_type=d.app_type where a.order_id=" +
                    orderId;
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
                rs = stmt.executeQuery();

                while (rs.next()) {
                    RcOrderVO vo = new RcOrderVO();
                    vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
                    vo.setAppId(DAOUtils.trimStr(rs.getString("app_id")));
                    vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
                    vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
                    vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
                    vo.setTacheId(DAOUtils.trimStr(rs.getString("tache_id")));
                    vo.setStateId(DAOUtils.trimStr(rs.getString("state_id")));
                    vo.setTacheTime(DAOUtils.getFormatedDateTime(
                            rs.getTimestamp("tache_time")));
                    vo.setAppTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                                "app_time")));
                    vo.setEndTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                                "end_time")));
                    vo.setOutStorageId(DAOUtils.trimStr(rs.getString(
                                "out_storage_id")));
                    vo.setInStorageId(DAOUtils.trimStr(rs.getString(
                                "in_storage_id")));
                    vo.setAppStorageId(DAOUtils.trimStr(rs.getString(
                                "app_storage_id")));
                    vo.setSalesRescId(DAOUtils.trimStr(rs.getString(
                                "sales_resource_id")));
                    vo.setAppAmount(DAOUtils.trimStr(rs.getString("app_amount")));
                    vo.setActAmount(DAOUtils.trimStr(rs.getString("act_amount")));
                    vo.setRequireTime(DAOUtils.getFormatedDateTime(
                            rs.getTimestamp("require_time")));
                    vo.setComments(DAOUtils.trimStr(rs.getString("comments")));
                    vo.setResComments(DAOUtils.trimStr(rs.getString(
                                "res_comments")));
                    vo.setResBCode(DAOUtils.trimStr(rs.getString("res_b_code")));
                    vo.setResECode(DAOUtils.trimStr(rs.getString("res_e_code")));

                    vo.setAppTypeName(DAOUtils.trimStr(rs.getString(
                                "app_type_name")));
                    vo.setInStorageName(DAOUtils.trimStr(rs.getString(
                                "in_storage_name")));
                    vo.setOutStorageName(DAOUtils.trimStr(rs.getString(
                                "out_storage_name")));
                    resultList.add(vo);
                }
            } catch (SQLException se) {
                Debug.print(SQL, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + SQL, se);
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return resultList;
    }

    public List getOrderExcInfoByOrderId(String orderId)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList resultList = new ArrayList();

        if ((orderId != null) && !"".equals(orderId)) {
            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                SQL = "select a.*,b.tache_name,c.org_name as depart_name,d.party_role_name as oper_name,e.state_name from rc_order_exc a left join organization  c on a.depart_id=c.party_id left join party_role d on a.oper_id=d.party_role_id left join rc_order_state e on a.state_id=e.state_id left join rc_order_tache b on a.tache_id=b.tache_id where  a.order_id=" +
                    orderId;
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
                rs = stmt.executeQuery();

                while (rs.next()) {
                    RcOrderExcVO vo = new RcOrderExcVO();
                    vo.setLogId(DAOUtils.trimStr(rs.getString("log_id")));
                    vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
                    vo.setTacheId(DAOUtils.trimStr(rs.getString("tache_id")));
                    vo.setExcTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                                "exc_time")));
                    vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
                    vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
                    vo.setStateId(DAOUtils.trimStr(rs.getString("state_id")));
                    vo.setExcComments(DAOUtils.trimStr(rs.getString(
                                "exc_comments")));

                    vo.setTacheName(DAOUtils.trimStr(rs.getString("tache_name")));
                    vo.setDepartName(DAOUtils.trimStr(rs.getString(
                                "depart_name")));
                    vo.setOperName(DAOUtils.trimStr(rs.getString("oper_name")));
                    vo.setStateName(DAOUtils.trimStr(rs.getString("state_name")));

                    resultList.add(vo);
                }
            } catch (SQLException se) {
                Debug.print(SQL, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + SQL, se);
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return resultList;
    }

    public List getSalesResourceByFamilyType(String familyType)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList resultList = new ArrayList();

        if ((familyType != null) && !"".equals(familyType)) {
            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                SQL = "select * from SALES_RESOURCE where family_id=" +
                    familyType;
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
                rs = stmt.executeQuery();

                while (rs.next()) {
                    SalesRescVO vo = new SalesRescVO();
                    vo.setSalesRescId(DAOUtils.trimStr(rs.getString(
                                "sales_resource_id")));
                    vo.setFamilyId(DAOUtils.trimStr(rs.getString("family_id")));
                    vo.setRescAreaId(DAOUtils.trimStr(rs.getString(
                                "resource_area_id")));
                    vo.setManageMode(DAOUtils.trimStr(rs.getString(
                                "manage_mode")));
                    vo.setSalesRescCode(DAOUtils.trimStr(rs.getString(
                                "sales_resource_code")));
                    vo.setSalesRescName(DAOUtils.trimStr(rs.getString(
                                "sales_resource_name")));
                    vo.setSalesRescWorth(DAOUtils.trimStr(rs.getString(
                                "sales_resource_worth")));
                    vo.setState(DAOUtils.trimStr(rs.getString("state")));
                    vo.setStateDate(DAOUtils.getFormatedDateTime(rs.getDate(
                                "state_date")));
                    vo.setCreateDate(DAOUtils.getFormatedDateTime(rs.getDate(
                                "create_date")));
                    vo.setEffDate(DAOUtils.getFormatedDateTime(rs.getDate(
                                "eff_date")));
                    vo.setExpDate(DAOUtils.getFormatedDateTime(rs.getDate(
                                "exp_date")));
                    vo.setDoDetail("查看属性");

                    resultList.add(vo);
                }
            } catch (SQLException se) {
                Debug.print(SQL, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + SQL, se);
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return resultList;
    }

    public List getSalesResourceBySalesResourceId(String saleResourceId)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList resultList = new ArrayList();

        if ((saleResourceId != null) && !"".equals(saleResourceId)) {
            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                SQL = "select a.*,b.family_name from SALES_RESOURCE a left join rc_family b on a.family_id=b.family_id where sales_resource_id=" +
                    saleResourceId;
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
                rs = stmt.executeQuery();

                while (rs.next()) {
                    SalesRescVO vo = new SalesRescVO();
                    vo.setSalesRescId(DAOUtils.trimStr(rs.getString(
                                "sales_resource_id")));
                    vo.setFamilyId(DAOUtils.trimStr(rs.getString("family_id")));
                    vo.setRescAreaId(DAOUtils.trimStr(rs.getString(
                                "resource_area_id")));
                    vo.setManageMode(DAOUtils.trimStr(rs.getString(
                                "manage_mode")));
                    vo.setSalesRescCode(DAOUtils.trimStr(rs.getString(
                                "sales_resource_code")));
                    vo.setSalesRescName(DAOUtils.trimStr(rs.getString(
                                "sales_resource_name")));
                    vo.setSalesRescWorth(DAOUtils.trimStr(rs.getString(
                                "sales_resource_worth")));
                    vo.setState(DAOUtils.trimStr(rs.getString("state")));
                    vo.setStateDate(DAOUtils.getFormatedDateTime(rs.getDate(
                                "state_date")));
                    vo.setCreateDate(DAOUtils.getFormatedDateTime(rs.getDate(
                                "create_date")));
                    vo.setEffDate(DAOUtils.getFormatedDateTime(rs.getDate(
                                "eff_date")));
                    vo.setExpDate(DAOUtils.getFormatedDateTime(rs.getDate(
                                "exp_date")));
                    vo.setFamilyName(DAOUtils.trimStr(rs.getString(
                                "family_name")));
                    vo.setDoDetail("查看属性");

                    resultList.add(vo);
                }
            } catch (SQLException se) {
                Debug.print(SQL, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + SQL, se);
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return resultList;
    }

    public List getStorageInfoByDepartIdList(String departId)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList resultList = new ArrayList();

        if ((departId != null) && !"".equals(departId)) {
            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                SQL = "select b.* from STORAGE_DEPART_RELA a,RC_STORAGE b where a.storage_id=b.storage_id and a.depart_id=" +
                    departId;
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
                rs = stmt.executeQuery();

                while (rs.next()) {
                    RcStorageVO vo = new RcStorageVO();
                    vo.setStorageId(DAOUtils.trimStr(rs.getString("storage_id")));
                    vo.setStorageName(DAOUtils.trimStr(rs.getString(
                                "storage_name")));
                    vo.setOwnerId(DAOUtils.trimStr(rs.getString("owner_id")));
                    vo.setStorageState(DAOUtils.trimStr(rs.getString(
                                "storage_state")));
                    vo.setStorageDesc(DAOUtils.trimStr(rs.getString(
                                "storage_desc")));
                    vo.setAddr(DAOUtils.trimStr(rs.getString("address")));
                    vo.setStorageCode(DAOUtils.trimStr(rs.getString(
                                "storage_code")));

                    resultList.add(vo);
                }
            } catch (SQLException se) {
                Debug.print(SQL, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + SQL, se);
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return resultList;
    }

    public List getRcApplicationDataByAppId(String appId)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList resultList = new ArrayList();

        if ((appId != null) && !"".equals(appId)) {
            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                SQL = "select a.*,b.app_type_name,c.party_role_name as oper_name,d.org_name as depart_name from rc_application a left join rc_app_type b on a.app_type = b.app_type left join party_role c on a.oper_id=c.party_role_id left join organization d on  a.depart_id=d.party_id where a.app_id=" +
                    appId;
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
                rs = stmt.executeQuery();

                while (rs.next()) {
                    RcApplicationVO vo = new RcApplicationVO();
                    vo.setAppId(DAOUtils.trimStr(rs.getString("app_id")));
                    vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
                    vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
                    vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
                    vo.setAppTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                                "app_time")));

                    vo.setAppTypeName(DAOUtils.trimStr(rs.getString(
                                "app_type_name")));
                    vo.setOperName(DAOUtils.trimStr(rs.getString("oper_name")));
                    vo.setDepartName(DAOUtils.trimStr(rs.getString(
                                "depart_name")));

                    resultList.add(vo);
                }
            } catch (SQLException se) {
                Debug.print(SQL, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + SQL, se);
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return resultList;
    }

    public List getRcOrderDataByAppId(String appId) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList resultList = new ArrayList();

        if ((appId != null) && !"".equals(appId)) {
            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                SQL = "select a.*,b.storage_name as app_storage_name,c.tache_name,d.sales_resource_name from rc_order a left join rc_storage b on a.app_storage_id = b.storage_id left join rc_order_tache c on a.tache_id=c.tache_id left join SALES_RESOURCE d on  a.sales_resource_id=d.sales_resource_id where a.app_id=" +
                    appId;
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
                rs = stmt.executeQuery();

                while (rs.next()) {
                    RcOrderVO vo = new RcOrderVO();
                    vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
                    vo.setAppId(DAOUtils.trimStr(rs.getString("app_id")));
                    vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
                    vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
                    vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
                    vo.setTacheId(DAOUtils.trimStr(rs.getString("tache_id")));
                    vo.setStateId(DAOUtils.trimStr(rs.getString("state_id")));
                    vo.setTacheTime(DAOUtils.getFormatedDateTime(
                            rs.getTimestamp("tache_time")));
                    vo.setAppTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                                "app_time")));
                    vo.setEndTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                                "end_time")));
                    vo.setOutStorageId(DAOUtils.trimStr(rs.getString(
                                "out_storage_id")));
                    vo.setInStorageId(DAOUtils.trimStr(rs.getString(
                                "in_storage_id")));
                    vo.setAppStorageId(DAOUtils.trimStr(rs.getString(
                                "app_storage_id")));
                    vo.setSalesRescId(DAOUtils.trimStr(rs.getString(
                                "sales_resource_id")));
                    vo.setAppAmount(DAOUtils.trimStr(rs.getString("app_amount")));
                    vo.setActAmount(DAOUtils.trimStr(rs.getString("act_amount")));
                    vo.setRequireTime(DAOUtils.getFormatedDateTime(
                            rs.getTimestamp("require_time")));
                    vo.setComments(DAOUtils.trimStr(rs.getString("comments")));
                    vo.setResComments(DAOUtils.trimStr(rs.getString(
                                "res_comments")));
                    vo.setResBCode(DAOUtils.trimStr(rs.getString("res_b_code")));
                    vo.setResECode(DAOUtils.trimStr(rs.getString("res_e_code")));

                    vo.setAppStorageName(DAOUtils.trimStr(rs.getString(
                                "app_storage_name")));
                    vo.setTacheName(DAOUtils.trimStr(rs.getString("tache_name")));
                    vo.setSalesRescName(DAOUtils.trimStr(rs.getString(
                                "sales_resource_name")));

                    resultList.add(vo);
                }
            } catch (SQLException se) {
                Debug.print(SQL, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + SQL, se);
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return resultList;
    }

    public List getRcOrderDataByOrderId(String orderId)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList resultList = new ArrayList();

        if ((orderId != null) && !"".equals(orderId)) {
            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                SQL = "select * from rc_order where order_id=" + orderId;
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
                rs = stmt.executeQuery();

                while (rs.next()) {
                    RcOrderVO vo = new RcOrderVO();
                    vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
                    vo.setAppId(DAOUtils.trimStr(rs.getString("app_id")));
                    vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
                    vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
                    vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
                    vo.setTacheId(DAOUtils.trimStr(rs.getString("tache_id")));
                    vo.setStateId(DAOUtils.trimStr(rs.getString("state_id")));
                    vo.setTacheTime(DAOUtils.getFormatedDateTime(
                            rs.getTimestamp("tache_time")));
                    vo.setAppTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                                "app_time")));
                    vo.setEndTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                                "end_time")));
                    vo.setOutStorageId(DAOUtils.trimStr(rs.getString(
                                "out_storage_id")));
                    vo.setInStorageId(DAOUtils.trimStr(rs.getString(
                                "in_storage_id")));
                    vo.setAppStorageId(DAOUtils.trimStr(rs.getString(
                                "app_storage_id")));
                    vo.setSalesRescId(DAOUtils.trimStr(rs.getString(
                                "sales_resource_id")));
                    vo.setAppAmount(DAOUtils.trimStr(rs.getString("app_amount")));
                    vo.setActAmount(DAOUtils.trimStr(rs.getString("act_amount")));
                    vo.setRequireTime(DAOUtils.getFormatedDateTime(
                            rs.getTimestamp("require_time")));
                    vo.setComments(DAOUtils.trimStr(rs.getString("comments")));
                    vo.setResComments(DAOUtils.trimStr(rs.getString(
                                "res_comments")));
                    vo.setResBCode(DAOUtils.trimStr(rs.getString("res_b_code")));
                    vo.setResECode(DAOUtils.trimStr(rs.getString("res_e_code")));

                    resultList.add(vo);
                }
            } catch (SQLException se) {
                Debug.print(SQL, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + SQL, se);
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return resultList;
    }

    public RcAppTypeVO getRcAppType(String appTypeId) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        RcAppTypeVO vo = new RcAppTypeVO();

        if ((appTypeId != null) && !"".equals(appTypeId)) {
            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                SQL = "select * from  rc_app_type where app_type='" +
                    appTypeId + "'";
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
                rs = stmt.executeQuery();

                while (rs.next()) {
                    vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
                    vo.setAppTypeName(DAOUtils.trimStr(rs.getString(
                                "app_type_name")));
                    vo.setFlowStr(DAOUtils.trimStr(rs.getString("flow_str")));
                    vo.setInStyle(DAOUtils.trimStr(rs.getString("in_style")));
                    vo.setOutStyle(DAOUtils.trimStr(rs.getString("out_style")));
                    vo.setFlowStype(DAOUtils.trimStr(rs.getString("flow_stype")));
                }
            } catch (SQLException se) {
                Debug.print(SQL, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + SQL, se);
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return vo;
    }

    public String getRcStorageName(String rcStorageId)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        String rcStorageName = "";

        if ((rcStorageId != null) && !"".equals(rcStorageId)) {
            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                SQL = "select * from  rc_storage where storage_id=" +
                    rcStorageId;
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
                rs = stmt.executeQuery();

                while (rs.next()) {
                    rcStorageName = DAOUtils.trimStr(rs.getString(
                                "storage_name"));
                }
            } catch (SQLException se) {
                Debug.print(SQL, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + SQL, se);
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return rcStorageName;
    }

    public boolean updateRcOrderData(String strSql) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;

        // ResultSet rs = null;
        boolean retv = false;

        if ((strSql != null) && !"".equals(strSql)) {
            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(strSql));

                int r = stmt.executeUpdate();

                if (r > 0) {
                    retv = true;
                }
            } catch (SQLException se) {
                Debug.print(strSql, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + strSql, se);
            } finally {
                // DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return retv;
    }

    public List getOrderInfoByOrderId_Excel(String orderIdStr)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList resultList = new ArrayList();

        if ((orderIdStr != null) && !"".equals(orderIdStr)) {
            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                SQL = "select a.*,c.party_role_name as oper_name,d.storage_name,e.sales_resource_name from rc_order a left join rc_application b on a.app_id=b.app_id left join party_role c on b.oper_id=c.party_role_id left join rc_storage d on a.app_storage_id = d.storage_id left join sales_resource e on a.sales_resource_id=e.sales_resource_id where a.order_id in(" +
                    orderIdStr + ")";
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
                rs = stmt.executeQuery();

                while (rs.next()) {
                    RcOrderVO vo = new RcOrderVO();
                    vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
                    vo.setAppId(DAOUtils.trimStr(rs.getString("app_id")));
                    vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
                    vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
                    vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
                    vo.setTacheId(DAOUtils.trimStr(rs.getString("tache_id")));
                    vo.setStateId(DAOUtils.trimStr(rs.getString("state_id")));
                    vo.setTacheTime(DAOUtils.getFormatedDateTime(
                            rs.getTimestamp("tache_time")));
                    vo.setAppTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                                "app_time")));
                    vo.setEndTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                                "end_time")));
                    vo.setOutStorageId(DAOUtils.trimStr(rs.getString(
                                "out_storage_id")));
                    vo.setInStorageId(DAOUtils.trimStr(rs.getString(
                                "in_storage_id")));
                    vo.setAppStorageId(DAOUtils.trimStr(rs.getString(
                                "app_storage_id")));
                    vo.setSalesRescId(DAOUtils.trimStr(rs.getString(
                                "sales_resource_id")));
                    vo.setAppAmount(DAOUtils.trimStr(rs.getString("app_amount")));
                    vo.setActAmount(DAOUtils.trimStr(rs.getString("act_amount")));
                    vo.setRequireTime(DAOUtils.getFormatedDateTime(
                            rs.getTimestamp("require_time")));
                    vo.setComments(DAOUtils.trimStr(rs.getString("comments")));
                    vo.setResComments(DAOUtils.trimStr(rs.getString(
                                "res_comments")));
                    vo.setResBCode(DAOUtils.trimStr(rs.getString("res_b_code")));
                    vo.setResECode(DAOUtils.trimStr(rs.getString("res_e_code")));

                    vo.setOperName(DAOUtils.trimStr(rs.getString("oper_name")));
                    vo.setAppStorageName(DAOUtils.trimStr(rs.getString(
                                "storage_name")));
                    vo.setSalesRescName(DAOUtils.trimStr(rs.getString(
                                "sales_resource_name")));
                    resultList.add(vo);
                }
            } catch (SQLException se) {
                Debug.print(SQL, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + SQL, se);
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return resultList;
    }

    public int getTacheId(String strSql) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        int tacheId = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(strSql));
            rs = stmt.executeQuery();

            while (rs.next()) {
                tacheId = rs.getInt("t_tache_id");
            }
        } catch (SQLException se) {
            Debug.print(strSql, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                strSql, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return tacheId;
    }

    /**
     * 查询处入库订单日至记录
     *
     * @param orderIds
     *            String
     * @throws DAOSystemException
     * @return List
     */
    public List qryRescInOutLog(String rescInstanceCode, String orderIds)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList resultList = new ArrayList();

        if ((orderIds != null) && !"".equals(orderIds.trim())) {
            orderIds = orderIds.trim();

            try {
                conn = ConnectionContext.getContext()
                                        .getConnection(JNDINames.CRM_RCDB);
                SQL = "select a.*,h.OPER_NAME,b.storage_name as out_storage_name,c.storage_name as in_storage_name,d.SALES_RESOURCE_NAME,e.FAMILY_NAME,f.ACCEPT_ID from rc_order a left outer join rc_storage b on a.out_storage_id = b.storage_id " +
                    " left outer join rc_storage c on a.in_storage_id=c.storage_id inner join sales_resource d on a.sales_resource_id=d.sales_resource_id " +
                    " inner join rc_family e on d.FAMILY_ID=e.FAMILY_ID left outer join rc_order_agent f on a.order_id=f.order_id " +
                    " inner join rc_application g on a.APP_ID=g.APP_ID left outer join mp_operator h on g.OPER_ID=h.OPER_ID" +
                    " where a.TACHE_ID=5 and a.STATE_ID='n' and a.order_id in(" +
                    orderIds + ") " +
                    " and (b.rc_type=-1 or c.rc_type=-1) order by a.end_time";
                SQL = DAOSQLUtils.getFilterSQL(SQL);
                stmt = conn.prepareStatement(SQL);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    RcOrderVO vo = new RcOrderVO();
                    vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
                    vo.setAppId(DAOUtils.trimStr(rs.getString("app_id")));
                    vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
                    vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
                    vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
                    vo.setTacheId(DAOUtils.trimStr(rs.getString("tache_id")));
                    vo.setStateId(DAOUtils.trimStr(rs.getString("state_id")));
                    vo.setTacheTime(DAOUtils.getFormatedDateTime(
                            rs.getTimestamp("tache_time")));
                    vo.setAppTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                                "app_time")));
                    vo.setEndTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                                "end_time")));
                    vo.setOutStorageId(DAOUtils.trimStr(rs.getString(
                                "out_storage_id")));
                    vo.setInStorageId(DAOUtils.trimStr(rs.getString(
                                "in_storage_id")));
                    vo.setAppStorageId(DAOUtils.trimStr(rs.getString(
                                "app_storage_id")));
                    vo.setSalesRescId(DAOUtils.trimStr(rs.getString(
                                "sales_resource_id")));
                    vo.setAppAmount(DAOUtils.trimStr(rs.getString("app_amount")));
                    vo.setActAmount(DAOUtils.trimStr(rs.getString("act_amount")));
                    vo.setRequireTime(DAOUtils.getFormatedDateTime(
                            rs.getTimestamp("require_time")));
                    vo.setComments(DAOUtils.trimStr(rs.getString("comments")));
                    vo.setResComments(DAOUtils.trimStr(rs.getString(
                                "res_comments")));
                    vo.setResBCode(DAOUtils.trimStr(rs.getString("res_b_code")));
                    vo.setResECode(DAOUtils.trimStr(rs.getString("res_e_code")));
                    vo.setInStorageName(DAOUtils.trimStr(rs.getString(
                                "in_storage_name")));
                    vo.setOutStorageName(DAOUtils.trimStr(rs.getString(
                                "out_storage_name")));
                    vo.setSalesRescName(DAOUtils.trimStr(rs.getString(
                                "SALES_RESOURCE_NAME")));
                    vo.setFamilyName(DAOUtils.trimStr(rs.getString(
                                "FAMILY_NAME")));
                    vo.setAcceptId(DAOUtils.trimStr(rs.getString("ACCEPT_ID")));
                    // 此处的oper_name是申请人的名字
                    vo.setOperName(DAOUtils.trimStr(rs.getString("OPER_NAME")));
                    vo.setRescInstanceCode(rescInstanceCode);
                    resultList.add(vo);
                }
            } catch (SQLException se) {
                Debug.print(SQL, this);
                throw new DAOSystemException(
                    "SQLException while getting sql:\n" + SQL, se);
            } finally {
                DAOUtils.closeResultSet(rs, this);
                DAOUtils.closeStatement(stmt, this);
                DAOUtils.closeConnection(conn, this);
            }
        }

        return resultList;
    }

    /**
     * 查询生成号码或sim卡的订单信息
     *
     * @param orderIds
     *            String
     * @throws DAOSystemException
     * @return List
     */
    public List qryOrderNoSim(Map map) throws DAOSystemException {
        if ((map == null) || (map.get("familyId") == null)) {
            return new ArrayList();
        }

        String databaseType = CrmParamsConfig.getInstance()
                                             .getParamValue("DATABASE_TYPE");
        String familyId = null;
        String startDate = null;
        String endDate = null;
        String operId = null;
        String appType = null;

        if ((map.get("familyId") != null) &&
                (map.get("familyId").toString().trim().length() > 0)) {
            familyId = (String) map.get("familyId");
        }

        if ((map.get("startDate") != null) &&
                (map.get("startDate").toString().trim().length() > 0)) {
            startDate = (String) map.get("startDate");
        }

        if ((map.get("endDate") != null) &&
                (map.get("endDate").toString().trim().length() > 0)) {
            endDate = (String) map.get("endDate");
        }

        if ((map.get("operId") != null) &&
                (map.get("operId").toString().trim().length() > 0)) {
            operId = (String) map.get("operId");
        }

        if ((map.get("appType") != null) &&
                (map.get("appType").toString().trim().length() > 0)) {
            appType = (String) map.get("appType");
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "select a.*,c.oper_name  from rc_order a,sales_resource b,mp_operator c where a.sales_resource_id=b.sales_resource_id and a.oper_id=c.oper_id ";
        List resultList = new ArrayList();

        if (familyId != null) {
            SQL += (" and b.family_id=" + familyId);
        }

        if (operId != null) {
            SQL += (" and a.oper_id=" + operId);
        }

        if (appType != null) {
            SQL += (" and a.app_type='" + appType + "' ");
        }

        if (!"INFORMIX".equals(databaseType)) {
            if (startDate != null) {
                SQL += ("  and a.tache_time>=to_date('" + startDate +
                "','yyyy-mm-dd hh24:mi:ss')  ");
            }

            if ((endDate != null) && !"".equals(endDate)) {
                SQL += ("  and a.tache_time<=to_date('" + endDate +
                " 23:59:59','yyyy-mm-dd hh24:mi:ss')  ");
            }
        } else {
            if (startDate != null) {
                SQL += ("  and a.tache_time>=to_date('" + startDate +
                " 00:00:00','%Y-%m-%d %H:%M:%S')  ");
            }

            if ((endDate != null) && !"".equals(endDate)) {
                SQL += ("  and a.tache_time<=to_date('" + endDate +
                " 23:59:59','%Y-%m-%d %H:%M:%S')  ");
            }
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = DAOSQLUtils.getFilterSQL(SQL);
            stmt = conn.prepareStatement(SQL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                RcOrderVO vo = new RcOrderVO();
                vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
                vo.setAppId(DAOUtils.trimStr(rs.getString("app_id")));
                vo.setAppType(DAOUtils.trimStr(rs.getString("app_type")));
                vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
                vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
                vo.setTacheId(DAOUtils.trimStr(rs.getString("tache_id")));
                vo.setTacheTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                            "tache_time")));
                vo.setEndTime(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                            "end_time")));
                vo.setOutStorageId(DAOUtils.trimStr(rs.getString(
                            "out_storage_id")));
                vo.setInStorageId(DAOUtils.trimStr(rs.getString("in_storage_id")));
                vo.setSalesRescId(DAOUtils.trimStr(rs.getString(
                            "sales_resource_id")));
                vo.setActAmount(DAOUtils.trimStr(rs.getString("act_amount")));
                vo.setResBCode(DAOUtils.trimStr(rs.getString("res_b_code")));
                vo.setResECode(DAOUtils.trimStr(rs.getString("res_e_code")));
                // 此处的oper_name是申请人的名字
                vo.setOperName(DAOUtils.trimStr(rs.getString("OPER_NAME")));

                if (((vo.getResBCode() == null) || vo.getResBCode().equals("")) &&
                        ((vo.getResECode() == null) ||
                        vo.getResECode().equals(""))) {
                    vo.setOrderType("0");
                } else {
                    vo.setOrderType("1");
                }

                resultList.add(vo);
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

        return resultList;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ztesoft.crm.sr.dao.RcOrderDAO#setFiltered(boolean)
     */
    public void setFiltered(boolean b) {
        this.filtered = b;
    }

    public List getOrderSegInfoByOrderId(String orderId, String flag) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList resultList = new ArrayList();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "select order_id ,pre_code,post_code,start_code,end_code from RC_SEG_ORDER where order_id = " +
                orderId + " and flag =" + flag;
            SQL = DAOSQLUtils.getFilterSQL(SQL);
            stmt = conn.prepareStatement(SQL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                RcOrderSegInfoVO vo = new RcOrderSegInfoVO();
                vo.setOrderId(DAOUtils.trimStr(rs.getString("order_id")));
                vo.setPreCode(DAOUtils.trimStr(rs.getString("pre_code")));
                vo.setPostCode(DAOUtils.trimStr(rs.getString("post_code")));
                vo.setResBCode(DAOUtils.trimStr(rs.getString("start_code")));
                vo.setResECode(DAOUtils.trimStr(rs.getString("end_code")));

                resultList.add(vo);
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

        return resultList;
    }

    public String getSegInfo(String resBCode, String resECode, String preCode,
        String postCode, String storageId, String salesRescId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        String r = "0";
        String tableName = CommonUtilBo.getTableNameByResouceId(salesRescId);
        tableName = ("".equals(tableName)) ? "rc_entity2" : tableName;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "select count(resource_instance_id) as cout from " +
                tableName +
                " where state='00A' and middle_code >= ? and middle_code <= ?  and storage_id =? and SALES_RESOURCE_ID =?";

            if (!"".equals(preCode)) {
                SQL += (" and pre_code= '" + preCode + "'");
            }

            if (!"".equals(postCode)) {
                SQL += (" and post_code= '" + postCode + "'");
            }

            SQL = DAOSQLUtils.getFilterSQL(SQL);
            //System.out.println(";;;;;;;;;;;;"+SQL+" "+resBCode+" "+resECode+" "+storageId+" "+salesRescId);
            stmt = conn.prepareStatement(SQL);
            stmt.setString(1, resBCode);
            stmt.setString(2, resECode);
            stmt.setString(3, storageId);
            stmt.setString(4, salesRescId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                r = rs.getString(1);
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

        return r;
    }

    private boolean isExist(String orderId, int flag) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        boolean r = false;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "select order_id from RC_SEG_ORDER where order_id= ? and flag = ?  ";
            SQL = DAOSQLUtils.getFilterSQL(SQL);
            stmt = conn.prepareStatement(SQL);
            stmt.setString(1, orderId);
            stmt.setInt(2, flag);
            rs = stmt.executeQuery();

            if (rs.next()) {
                r = true;
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

        return r;
    }

    private void updateSegInfo(RcOrderSegInfoVO vo, int flag) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "update RC_SEG_ORDER set PRE_CODE=?,POST_CODE=?,START_CODE=?,END_CODE=?  where order_id= ? and flag = ?  ";

            SQL = DAOSQLUtils.getFilterSQL(SQL);
            stmt = conn.prepareStatement(SQL);
            stmt.setString(1, vo.getPreCode());
            stmt.setString(2, vo.getPostCode());
            stmt.setString(3, vo.getResBCode());
            stmt.setString(4, vo.getResECode());
            stmt.setString(5, vo.getOrderId());
            stmt.setInt(6, flag);
            stmt.executeUpdate();
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

    public void insertSegOrder(RcOrderSegInfoVO[] segs) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "insert into RC_SEG_ORDER(ORDER_ID,PRE_CODE,POST_CODE,START_CODE,END_CODE,FLAG) values (?,?,?,?,?,?)";
            SQL = DAOSQLUtils.getFilterSQL(SQL);

            for (int i = 0; i < segs.length; i++) {
                if (!isExist(segs[i].getOrderId(), i + 1)) {
                    if (!"".equals(segs[i].getResBCode()) ||
                            !"".equals(segs[i].getResECode())) {
                        stmt = conn.prepareStatement(SQL);
                        stmt.setString(1, segs[i].getOrderId());
                        stmt.setString(2, segs[i].getPreCode());
                        stmt.setString(3, segs[i].getPostCode());
                        stmt.setString(4, segs[i].getResBCode());
                        stmt.setString(5, segs[i].getResECode());
                        stmt.setInt(6, i + 1);
                        stmt.execute();
                        stmt.clearParameters();
                    }
                } else {
                    updateSegInfo(segs[i], i + 1);
                }
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

    public String getAgentInfo(String orderId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        String r = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "select depart_id from rc_order_agent where order_id= ? ";
            SQL = DAOSQLUtils.getFilterSQL(SQL);
            stmt = conn.prepareStatement(SQL);
            stmt.setString(1, orderId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                r = rs.getString("depart_id");
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

        return r;
    }

    public String insertAgentInfo(HashMap map) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        String agentId = (String) map.get("agentId");
        String salesId = (String) map.get("salesId");
        String salesName = (String) map.get("salesName");
        RcOrderSegInfoVO[] segs = (RcOrderSegInfoVO[]) map.get("seg");
        String actionType = (String) map.get("actionType");
        String delType = (String) map.get("delType");
        String orderId = (String) map.get("orderId");
        String tmp = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "{ call SEND_AGENT_DEVICE(?,?,?,?,?,?,?)}";

            if ("3".equals(delType)) { //分段的起始和终止编码方式

                for (int i = 0; i < segs.length; i++) {
                    if (!"".equals(segs[i].getResBCode()) ||
                            !"".equals(segs[i].getResECode())) {
                        int start = Integer.parseInt(segs[i].getResBCode());
                        int end = Integer.parseInt(segs[i].getResECode());
                        stmt = conn.prepareCall(SQL);
                        stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
                        stmt.registerOutParameter(7, java.sql.Types.VARCHAR);

                        for (int j = start; j <= end; j++) {
                            stmt.setString(1, agentId);
                            stmt.setString(2, salesId);
                            stmt.setString(3, salesName);
                            stmt.setString(4,
                                segs[i].getPreCode() + j +
                                segs[i].getPostCode());
                            stmt.setString(5, actionType);
                            stmt.execute();
                            tmp = stmt.getString(6);
                            System.out.println("代理商终端数据调用集团接口  Err_code is :" +
                                tmp);
                            tmp = stmt.getString(7);
                            System.out.println("代理商终端数据调用集团接口  Err_msg is :" +
                                tmp);
                            stmt.clearParameters();
                        }
                    }
                }
            } else { //文件和见面的方式

                List vos = new RcOrderListDAOImpl().findByCond(" order_id = " +
                        orderId);

                for (Iterator iter = vos.iterator(); iter.hasNext();) {
                    RcOrderListVO element = (RcOrderListVO) iter.next();
                    stmt = conn.prepareCall(SQL);
                    stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
                    stmt.registerOutParameter(7, java.sql.Types.VARCHAR);
                    stmt.setString(1, agentId);
                    stmt.setString(2, salesId);
                    stmt.setString(3, salesName);
                    stmt.setString(4, element.getRescInstanceCode());
                    stmt.setString(5, actionType);
                    stmt.execute();
                    tmp = stmt.getString(6);
                    System.out.println("代理商终端数据调用集团接口  Err_code is :" + tmp);
                    tmp = stmt.getString(7);
                    System.out.println("代理商终端数据调用集团接口  Err_msg is :" + tmp);
                    stmt.clearParameters();
                }
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

        return null;
    }
}
