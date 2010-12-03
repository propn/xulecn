package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.common.CustHelper;
import com.ztesoft.crm.customer.custinfo.dao.AcctDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * AcctBO.java
 * 
 * @function:
 * 
 * @author nik
 * @since 2010-1-26
 * @modified
 */
public class AcctBO extends DictAction {

    public static final String
    // 状态类型
            STATE_USING = "00A", // 有效
            STATE_CANCEL = "00X", // 注销
            STATE_ARCHIVE = "00H", // 归档
            // 动作类型
            ACTION_TYPE_ADD = "1", // 增
            ACTION_TYPE_DELETE = "2", // 删
            ACTION_TYPE_MODIFY = "3"; // 改

    public String insertAcct(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map Acct = (Map) param.get("Acct");
        
        CustHelper helper = new CustHelper();
        String acct_code = helper.generatorAcctCode();
        Acct.put("acct_code", acct_code );
        
        String acct_id = SeqDAOFactory.getInstance().getSequenceManageDAO().getNextSequence("ACCT", "ACCT_ID");

        Acct.put("acct_id", acct_id );
        Acct.put("seq_nbr", "0");
        Acct.put("record_eff_date", DateFormatUtils.getFormatedDate());
//        Acct.put("record_exp_date", CrmConstants.DEFAULT_EXPIRED_DATE);
        Acct.put("record_exp_date", CustHelper.DEFAULT_EXPIRE_DATE);
        Acct.put("state_date", DateFormatUtils.getFormatedDateTime());
        Acct.put("state", STATE_USING);
        Acct.put("action_type", ACTION_TYPE_ADD);

        AcctDAO dao = new AcctDAO();
        boolean result = dao.insert(Acct);

        //return result;
        return acct_id;
    }

    public boolean updateAcctAll(DynamicDict dto) throws Exception {
        DynamicDict adict = new DynamicDict();
        Map param = Const.getParam(dto);
        Map allinfo = (Map) param.get("all");
        Map acctInfo = (Map) allinfo.get("acctInfo");
        Map payplanInfo = (Map) allinfo.get("payplanInfo");
        String acctId = (String) allinfo.get("acctId");
        String custId = (String) allinfo.get("custId");

        List currentEntityAcctInfo = (List) acctInfo.get("currentEntity");
        if (currentEntityAcctInfo == null) { currentEntityAcctInfo = new ArrayList(); }
        for (Iterator it = currentEntityAcctInfo.iterator(); it.hasNext();) {
            Map mp = (Map) it.next();
            mp.put("cust_id", custId);
            Map pa = new HashMap();
            pa.put("Acct", mp);
            adict.setValueByName(Const.ACTION_PARAMETER, pa);
            String actionType = (String) mp.get("action_type");
            String acct_id = (String) mp.get("acct_id");
            if ("ACCT_M".equals(actionType) && !"".equals(acct_id)) {// 对原记录的修改
                updateAcct(adict);
            }
            if ("ACCT_A".equals(actionType)) {// 新增记录的修改
                if ("".equals(acct_id)) {
                    acctId = insertAcct(adict);
                }
            }
        }

        List deleteEntityAcctInfo = (List) acctInfo.get("deleteEntity");
        if (deleteEntityAcctInfo == null) {deleteEntityAcctInfo = new ArrayList();}
        for (Iterator it = deleteEntityAcctInfo.iterator(); it.hasNext();) {
            Map mp = (Map) it.next();
            String acctid = (String) mp.get("acct_id");
            if ("" != acctid) {
                adict.setValueByName(Const.ACTION_PARAMETER, mp);
                deleteAcctById(adict);
            }
        }

        PaymentPlanBO paymentplanbo = new PaymentPlanBO();

        List currentEntityPayPlan = (List) payplanInfo.get("currentEntity");
        if (currentEntityPayPlan == null) {currentEntityPayPlan = new ArrayList();}
        for (Iterator it = currentEntityPayPlan.iterator(); it.hasNext();) {
            Map mp = (Map) it.next();
            mp.put("acct_id", acctId);
            Map pa = new HashMap();
            pa.put("PaymentPlan", mp);
            adict.setValueByName(Const.ACTION_PARAMETER, pa);
            String actionType = (String) mp.get("action_type");
            String payment_plan_id = (String) mp.get("payment_plan_id");
            if ("PayPlan_M".equals(actionType) && !"".equals(payment_plan_id)) {// 对原记录的修改
                paymentplanbo.updatePaymentPlan(adict);
            }
            if ("PayPlan_A".equals(actionType)) {// 新增记录的修改
                if ("".equals(payment_plan_id)) {
                    paymentplanbo.insertPaymentPlan(adict);
                }
            }
        }

        List deleteEntityPayPlan = (List) payplanInfo.get("deleteEntity");
        if (deleteEntityPayPlan == null) {deleteEntityPayPlan = new ArrayList();}
        for (Iterator it = deleteEntityPayPlan.iterator(); it.hasNext();) {
            Map mp = (Map) it.next();
            adict.setValueByName(Const.ACTION_PARAMETER, mp);
            String paymentplanid = (String) mp.get("payment_plan_id");
            if ("" != paymentplanid) {
                paymentplanbo.deletePaymentPlanById(adict);
            }
        }

        return true;
    }

    public boolean updateAcct(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map Acct = (Map) param.get("Acct");
        String keyStr = "acct_id,seq_nbr";
        Map keyCondMap = Const.getMapForTargetStr(Acct, keyStr);
        AcctDAO dao = new AcctDAO();

        Map omp = dao.findByPrimaryKey(keyCondMap);
        Map nmp = getMapForTargetMap(omp, Acct);
        int seq_nbr = Integer.parseInt((String) omp.get("seq_nbr")) + 1;
        nmp.put("seq_nbr", String.valueOf(seq_nbr));
        nmp.put("record_eff_date", DateFormatUtils.getFormatedDate());
        //nmp.put("record_exp_date", CrmConstants.DEFAULT_EXPIRED_DATE);
        nmp.put("record_exp_date", CustHelper.DEFAULT_EXPIRE_DATE);
        nmp.put("state_date", DateFormatUtils.getFormatedDateTime());
        nmp.put("state", STATE_USING);
        nmp.put("action_type", ACTION_TYPE_MODIFY);

        if (!dao.insert(nmp)) {
            return false;
        }

        omp.put("record_exp_date", DateFormatUtils.getFormatedDate());
        omp.put("state", STATE_CANCEL);

        boolean result = dao.updateByKey(omp, keyCondMap);

        // boolean result = dao.updateByKey(Acct, keyCondMap);

        return result;
    }

    /**
     * 根据客户ID查询对应的账户
     * 
     * @param dto
     * @return
     * @throws Exception
     */
    public PageModel searchAcctData(DynamicDict dto) throws Exception {
        // 条件处理
        Map param = Const.getParam(dto);
        StringBuffer whereCond = new StringBuffer();
        List para = new ArrayList();

        if (Const.containStrValue(param, "cust_id")) {
            whereCond.append(" and cust_id = ? ");
            para.add(Const.getStrValue(param, "cust_id"));
        }

        // whereCond.append(" and state = ? ");
        // para.add(STATE_USING);

        whereCond.append(" and sysdate <= record_exp_date ");
        whereCond.append(" order by acct_id  ");

        int pageSize = Const.getPageSize(param);
        int pageIndex = Const.getPageIndex(param);

        // 调用DAO代码
        AcctDAO dao = new AcctDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }
    
    /**
     * 根据客户ID查询对应的账户历史数据
     * 
     * @param dto
     * @return
     * @throws Exception
     */
    public PageModel searchAcctDataHis(DynamicDict dto) throws Exception {
        // 条件处理
        Map param = Const.getParam(dto);

        int pageSize = Const.getPageSize(param);
        int pageIndex = Const.getPageIndex(param);

        String cust_id = (String) param.get("cust_id");
        String queryDate = (String) param.get("queryDate");

        List para = new ArrayList();
        para.add(cust_id);
        // para.add(DAOUtils.parseDateTime(queryDate));
       

        // 调用DAO代码
        AcctDAO dao = new AcctDAO();

        String COUNTSQL=null;
        String SELSQL=null;
        if (!"".equals(queryDate) && queryDate != null) { //历史查询
            para.add(queryDate);
            COUNTSQL = "select count(*) as col_counts from acct where cust_id = ? and state_date <= to_date(?,'yyyy-mm-dd hh24:mi:ss') ";
            String r_cnt = Base.query_string(dao.getDbName(), COUNTSQL,
                    new String[] { cust_id, queryDate }, Const.UN_JUDGE_ERROR);

             SELSQL = "select rownum cur_cnt, " + r_cnt
                    + " as cnt, $columns from acct where cust_id = ? and state_date <= to_date(?,'yyyy-mm-dd hh24:mi:ss')  order by state_date desc";
        }else {
            //当前查询
             COUNTSQL = "select count(*) as col_counts from acct where cust_id = ?  and sysdate <= record_exp_date ";
            String r_cnt = Base.query_string(dao.getDbName(), COUNTSQL,
                    new String[] { cust_id}, Const.UN_JUDGE_ERROR);

             SELSQL = "select rownum cur_cnt, " + r_cnt
                    + " as cnt, $columns from acct where cust_id = ?  and sysdate <= record_exp_date  order by acct_id ";
        }

        PageModel result = dao.searchByCond(SELSQL, COUNTSQL, para, pageIndex,
                pageSize);

        return result;
    }

    /**
     * 根据库表主键查询对象
     */
    public Map getAcctById(DynamicDict dto) throws Exception {
        // 调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        AcctDAO dao = new AcctDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findAcctByCond(DynamicDict dto) throws Exception {
        // 条件处理
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        // 组织调用DAO代码参数、
        String whereCond = where.getWhereCond();
        List para = where.getPara();

        // 调用DAO代码
        AcctDAO dao = new AcctDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public int deleteAcctById(DynamicDict dto) throws FrameException {
        // 调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        AcctDAO dao = new AcctDAO();

        // Map omp = dao.findByPrimaryKey(keyCondMap);
        // omp.put("action_type", ACTION_TYPE_DELETE);
        // omp.put("state_date", DateFormatUtils.getFormatedDateTime());
        // omp.put("record_exp_date", DateFormatUtils.getFormatedDate());
        // omp.put("state", STATE_CANCEL);

        // boolean result = dao.updateByKey(omp, keyCondMap);

        // int cnt = Base.query_int(dao.getDbName(), -11011,
        // " select count(1) from payment_plan where acct_id = ? and state =
        // '00A' ",
        // new String[]{(String)keyCondMap.get("acct_id")});
        // if ( cnt > 0) {
        // return -1;
        // }

        String SQL = "update acct set action_type = ?, state_date = ?, record_exp_date = ?, state = ? "
                + " where acct_id = ? and seq_nbr = ?  ";

        List updateParams = new ArrayList();
        updateParams.add(ACTION_TYPE_DELETE);
        updateParams.add(DAOUtils.getCurrentDate());
        updateParams.add(DAOUtils.getCurrentDate());
        updateParams.add(STATE_CANCEL);
        updateParams.add(keyCondMap.get("acct_id"));
        updateParams.add(keyCondMap.get("seq_nbr"));

        return Base.update(dao.getDbName(), SQL, updateParams, 1,
                Const.UN_JUDGE_ERROR, "");

        // boolean result = dao.deleteByKey(keyCondMap) > 0;

        // return result;
    }

    public static Map getMapForTargetMap(Map sm, Map tm) {
        if (sm == null || sm.isEmpty() || tm == null || sm.isEmpty())
            return new HashMap();
        Map retmp = new HashMap();
        for (Iterator it = sm.keySet().iterator(); it.hasNext();) {
            String n = (String) it.next();
            if (tm.get(n) != null) {
                retmp.put(n, (String) tm.get(n));
            } else {
                retmp.put(n, (String) sm.get(n));
            }
        }
        return retmp;
    }
}
