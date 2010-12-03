package com.ztesoft.crm.customer.custinfo.dao;

import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BankPlanDAO extends AbstractDAO {
    //查询SQL
    private String SQL_SELECT = "select payment_plan_id,acct_id,payment_method,bank_branch_id,priority,bank_acct_cust_name,bank_acct_type,bank_acct_no,state,state_date,ord_no,event_id,event_seq,event_date,fin_seq,fin_date,cancel_ord_no,cancel_event_seq,cancel_event_date,cancel_fin_seq,cancel_fin_date,site_no,staff_no,action_type from BANK_PLAN where 1=1 ";

    //	统计总数SQL
    private String SQL_SELECT_COUNT = "select count(*) as col_counts from bank_plan where 1=1 ";

    //	insert SQl
    private String SQL_INSERT = "insert into BANK_PLAN (payment_plan_id, acct_id, payment_method, bank_branch_id, priority, bank_acct_cust_name, bank_acct_type, bank_acct_no, state, state_date, ord_no, event_id, event_seq, event_date, fin_seq, fin_date, cancel_ord_no, cancel_event_seq, cancel_event_date, cancel_fin_seq, cancel_fin_date, site_no, staff_no, action_type) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    //	普通update SQL
    private String SQL_UPDATE = "update BANK_PLAN set payment_plan_id=?, acct_id=?, payment_method=?, bank_branch_id=?, priority=?, bank_acct_cust_name=?, bank_acct_type=?, bank_acct_no=?, state=?, state_date=?, ord_no=?, event_id=?, event_seq=?, event_date=?, fin_seq=?, fin_date=?, cancel_ord_no=?, cancel_event_seq=?, cancel_event_date=?, cancel_fin_seq=?, cancel_fin_date=?, site_no=?, staff_no=?, action_type=? where 1=1 ";

    //	普通delete SQL
    private String SQL_DELETE = "delete from BANK_PLAN where 1=1 ";

    //	根据主键delete SQL
    private String SQL_DELETE_KEY = "delete from BANK_PLAN where payment_plan_id=?";

    //	根据主键update SQL
    private String SQL_UPDATE_KEY = "update BANK_PLAN set payment_plan_id=?, acct_id=?, payment_method=?, bank_branch_id=?, priority=?, bank_acct_cust_name=?, bank_acct_type=?, bank_acct_no=?, state=?, state_date=?, ord_no=?, event_id=?, event_seq=?, event_date=?, fin_seq=?, fin_date=?, cancel_ord_no=?, cancel_event_seq=?, cancel_event_date=?, cancel_fin_seq=?, cancel_fin_date=?, site_no=?, staff_no=?, action_type=? where payment_plan_id=?";

    //	根据主键查询SQL
    private String SQL_SELECT_KEY = "select payment_plan_id,acct_id,payment_method,bank_branch_id,priority,bank_acct_cust_name,bank_acct_type,bank_acct_no,state,state_date,ord_no,event_id,event_seq,event_date,fin_seq,fin_date,cancel_ord_no,cancel_event_seq,cancel_event_date,cancel_fin_seq,cancel_fin_date,site_no,staff_no,action_type from BANK_PLAN where payment_plan_id=? ";

    //	当前DAO 所属数据库name
    private String dbName = JNDINames.CM_DATASOURCE;

    public BankPlanDAO() {
    }

    /**
     * Insert参数设置
     * @param map
     * @return
     * @throws FrameException
     *
     */
    public List translateInsertParams(Map map) throws FrameException {
        if ((map == null) || map.isEmpty()) {
            return null;
        }

        List params = new ArrayList();

        params.add(map.get("payment_plan_id"));

        params.add(map.get("acct_id"));

        params.add(map.get("payment_method"));

        params.add(map.get("bank_branch_id"));

        params.add(map.get("priority"));

        params.add(map.get("bank_acct_cust_name"));

        params.add(map.get("bank_acct_type"));

        params.add(map.get("bank_acct_no"));

        params.add(map.get("state"));

        params.add(DAOUtils.parseDateTime(map.get("state_date")));

        params.add(map.get("ord_no"));

        params.add(map.get("event_id"));

        params.add(map.get("event_seq"));

        params.add(DAOUtils.parseDateTime(map.get("event_date")));

        params.add(map.get("fin_seq"));

        params.add(DAOUtils.parseDateTime(map.get("fin_date")));

        params.add(map.get("cancel_ord_no"));

        params.add(map.get("cancel_event_seq"));

        params.add(DAOUtils.parseDateTime(map.get("cancel_event_date")));

        params.add(map.get("cancel_fin_seq"));

        params.add(DAOUtils.parseDateTime(map.get("cancel_fin_date")));

        params.add(map.get("site_no"));

        params.add(map.get("staff_no"));

        params.add(map.get("action_type"));

        return params;
    }

    /**
     * update 参数设置
     * @param vo
     * @param condParas
     * @return
     * @throws FrameException
     */
    public List translateUpdateParams(Map vo, List condParas)
        throws FrameException {
        if ((vo == null) || vo.isEmpty()) {
            return null;
        }

        List params = new ArrayList();

        params.add(vo.get("payment_plan_id"));

        params.add(vo.get("acct_id"));

        params.add(vo.get("payment_method"));

        params.add(vo.get("bank_branch_id"));

        params.add(vo.get("priority"));

        params.add(vo.get("bank_acct_cust_name"));

        params.add(vo.get("bank_acct_type"));

        params.add(vo.get("bank_acct_no"));

        params.add(vo.get("state"));

        params.add(DAOUtils.parseDateTime(vo.get("state_date")));

        params.add(vo.get("ord_no"));

        params.add(vo.get("event_id"));

        params.add(vo.get("event_seq"));

        params.add(DAOUtils.parseDateTime(vo.get("event_date")));

        params.add(vo.get("fin_seq"));

        params.add(DAOUtils.parseDateTime(vo.get("fin_date")));

        params.add(vo.get("cancel_ord_no"));

        params.add(vo.get("cancel_event_seq"));

        params.add(DAOUtils.parseDateTime(vo.get("cancel_event_date")));

        params.add(vo.get("cancel_fin_seq"));

        params.add(DAOUtils.parseDateTime(vo.get("cancel_fin_date")));

        params.add(vo.get("site_no"));

        params.add(vo.get("staff_no"));

        params.add(vo.get("action_type"));

        if ((condParas != null) && !condParas.isEmpty()) {
            for (int i = 0, j = condParas.size(); i < j; i++) {
                params.add(condParas.get(i));
            }
        }

        return params;
    }

    /**
    * 根据主键更新参数设置
    * @param vo
    * @param keyCondMap
    * @return
    * @throws FrameException
    */
    public List translateUpdateParamsByKey(Map vo, Map keyCondMap)
        throws FrameException {
        if ((vo == null) || vo.isEmpty()) {
            return null;
        }

        if ((keyCondMap == null) || keyCondMap.isEmpty()) {
            return null;
        }

        List params = new ArrayList();

        params.add(vo.get("payment_plan_id"));

        params.add(vo.get("acct_id"));

        params.add(vo.get("payment_method"));

        params.add(vo.get("bank_branch_id"));

        params.add(vo.get("priority"));

        params.add(vo.get("bank_acct_cust_name"));

        params.add(vo.get("bank_acct_type"));

        params.add(vo.get("bank_acct_no"));

        params.add(vo.get("state"));

        params.add(DAOUtils.parseDateTime(vo.get("state_date")));

        params.add(vo.get("ord_no"));

        params.add(vo.get("event_id"));

        params.add(vo.get("event_seq"));

        params.add(DAOUtils.parseDateTime(vo.get("event_date")));

        params.add(vo.get("fin_seq"));

        params.add(DAOUtils.parseDateTime(vo.get("fin_date")));

        params.add(vo.get("cancel_ord_no"));

        params.add(vo.get("cancel_event_seq"));

        params.add(DAOUtils.parseDateTime(vo.get("cancel_event_date")));

        params.add(vo.get("cancel_fin_seq"));

        params.add(DAOUtils.parseDateTime(vo.get("cancel_fin_date")));

        params.add(vo.get("site_no"));

        params.add(vo.get("staff_no"));

        params.add(vo.get("action_type"));

        params.add(keyCondMap.get("payment_plan_id"));

        return params;
    }

    /**
    * 主键条件参数设置
    * @param keyCondMap
    * @return
    * @throws FrameException
    *
    */
    public List translateKeyCondMap(Map keyCondMap) throws FrameException {
        if ((keyCondMap == null) || keyCondMap.isEmpty()) {
            return null;
        }

        List params = new ArrayList();

        params.add(keyCondMap.get("payment_plan_id"));

        return params;
    }

    public String getDbName() {
        return this.dbName;
    }

    public String getDeleteSQLByKey() throws FrameException {
        return this.SQL_DELETE_KEY;
    }

    public String getUpdateSQLByKey() throws FrameException {
        return this.SQL_UPDATE_KEY;
    }

    public String getSelectSQL() {
        return this.SQL_SELECT;
    }

    public String getSelectCountSQL() {
        return this.SQL_SELECT_COUNT;
    }

    public String getInsertSQL() {
        return this.SQL_INSERT;
    }

    public String getUpdateSQL() {
        return this.SQL_UPDATE;
    }

    public String getDeleteSQL() {
        return this.SQL_DELETE;
    }

    public String getSQLSQLByKey() throws FrameException {
        return this.SQL_SELECT_KEY;
    }
}
