package com.ztesoft.crm.customer.custinfo.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.crm.customer.custinfo.common.CustHelper;
import com.ztesoft.crm.customer.custinfo.dao.PaymentPlanDAO;

public class PaymentPlanBO extends DictAction {
    /**
     * 需要替换位置 说明 ： 1. 错误参数处理,根据实际情况修改 2. searchPaymentPlanData 改方法的参数名称 3.
     * findPaymentPlanByCond(String payment_plan_id,String seq_nbr) 参数需要根据实际情况修改
     * 4. 不需要的方法，可以根据实际情况进行裁剪 5. 此段啰嗦话，完成后替换工作后，请删除！
     */

    public boolean insertPaymentPlan(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map PaymentPlan = (Map) param.get("PaymentPlan");

        PaymentPlan.put("payment_plan_id", SeqDAOFactory.getInstance()
                .getSequenceManageDAO().getNextSequence("PAYMENT_PLAN",
                        "PAYMENT_PLAN_ID"));
        PaymentPlan.put("seq_nbr", "0");
        PaymentPlan.put("record_eff_date", DateFormatUtils.getFormatedDate());
        PaymentPlan.put("record_exp_date", CustHelper.DEFAULT_EXPIRE_DATE);
        PaymentPlan.put("state_date", DateFormatUtils.getFormatedDateTime());
        PaymentPlan.put("state", AcctBO.STATE_USING);
        PaymentPlan.put("action_type", AcctBO.ACTION_TYPE_ADD);

        PaymentPlanDAO dao = new PaymentPlanDAO();
        boolean result = dao.insert(PaymentPlan);
        return result;
    }

    public boolean updatePaymentPlan(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map PaymentPlan = (Map) param.get("PaymentPlan");
        String keyStr = "payment_plan_id,seq_nbr";
        Map keyCondMap = Const.getMapForTargetStr(PaymentPlan, keyStr);
        PaymentPlanDAO dao = new PaymentPlanDAO();

        Map omp = dao.findByPrimaryKey(keyCondMap);
        Map nmp = AcctBO.getMapForTargetMap(omp, PaymentPlan);
        int seq_nbr = Integer.parseInt((String) omp.get("seq_nbr")) + 1;
        nmp.put("seq_nbr", String.valueOf(seq_nbr));
        nmp.put("record_eff_date", DateFormatUtils.getFormatedDate());
        nmp.put("record_exp_date", CustHelper.DEFAULT_EXPIRE_DATE);
        nmp.put("state_date", DateFormatUtils.getFormatedDateTime());
        nmp.put("state", AcctBO.STATE_USING);
        nmp.put("action_type", AcctBO.ACTION_TYPE_MODIFY);

        if (!dao.insert(nmp)) {
            return false;
        }

        omp.put("record_exp_date", DateFormatUtils.getFormatedDate());
        omp.put("state", AcctBO.STATE_CANCEL);

        boolean result = dao.updateByKey(omp, keyCondMap);

        // boolean result = dao.updateByKey( PaymentPlan, keyCondMap );

        return result;
    }

    public PageModel searchPaymentPlanData(DynamicDict dto) throws Exception {
        // 条件处理
        Map param = Const.getParam(dto);
        StringBuffer whereCond = new StringBuffer();
        List para = new ArrayList();
        if (Const.containStrValue(param, "acct_id")) {
            whereCond.append(" and acct_id = ? ");
            para.add(Const.getStrValue(param, "acct_id"));
        }

        // whereCond.append(" and state = ? ");
        // para.add(AcctBO.STATE_USING);
        whereCond.append(" and sysdate <= record_exp_date ");
        whereCond.append(" order by priority  ");

        int pageSize = Const.getPageSize(param);
        int pageIndex = Const.getPageIndex(param);

        // 调用DAO代码
        PaymentPlanDAO dao = new PaymentPlanDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    public PageModel searchPaymentPlanDataHis(DynamicDict dto) throws Exception {
        // 条件处理
        Map param = Const.getParam(dto);
        StringBuffer whereCond = new StringBuffer();
        List para = new ArrayList();

        // para.add(AcctBO.STATE_USING);
        // whereCond.append(" and sysdate <= record_exp_date ");
        // whereCond.append(" order by priority ");

        int pageSize = Const.getPageSize(param);
        int pageIndex = Const.getPageIndex(param);

        String acct_id = (String) param.get("acct_id");
        String queryDate = (String) param.get("queryDate");
        para.add(acct_id);

        // 调用DAO代码
        PaymentPlanDAO dao = new PaymentPlanDAO();

        String COUNTSQL = null;
        String SELSQL = null;
        if (!"".equals(queryDate) && queryDate != null) { // 历史查询
            para.add(queryDate);
            COUNTSQL = "select count(*) as col_counts from PAYMENT_PLAN where acct_id = ? and state_date <= to_date(?,'yyyy-mm-dd hh24:mi:ss') ";
            String r_cnt = Base.query_string(dao.getDbName(), COUNTSQL,
                    new String[] { acct_id, queryDate }, Const.UN_JUDGE_ERROR);

            SELSQL = "select rownum cur_cnt, " + r_cnt
                    + " as cnt, b.bank_name, a.$columns from PAYMENT_PLAN a , BANK b "
                    + " where a.payment_supple_id = b.bank_id and "
                    + " a.acct_id = ? and a.state_date <= to_date(?,'yyyy-mm-dd hh24:mi:ss')  order by a.state_date desc";
        } else {
            COUNTSQL = "select count(*) as col_counts from PAYMENT_PLAN where acct_id = ? and sysdate <= record_exp_date ";
            String r_cnt = Base.query_string(dao.getDbName(), COUNTSQL,
                    new String[] { acct_id }, Const.UN_JUDGE_ERROR);

            SELSQL = "select rownum cur_cnt, " + r_cnt
                    + " as cnt, b.bank_name, a.$columns from PAYMENT_PLAN a , BANK b "
                    + " where a.payment_supple_id = b.bank_id and "
                    + " a.acct_id = ? and sysdate <= record_exp_date  order by priority " ;
        }

        PageModel result = dao.searchByCond(SELSQL, COUNTSQL, para, pageIndex,
                pageSize);

        return result;
    }

    /**
     * 根据库表主键查询对象
     */
    public Map getPaymentPlanById(DynamicDict dto) throws Exception {
        // 调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        PaymentPlanDAO dao = new PaymentPlanDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findPaymentPlanByCond(DynamicDict dto) throws Exception {
        // 条件处理
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        // 组织调用DAO代码参数、
        String whereCond = where.getWhereCond();
        List para = where.getPara();
        // 调用DAO代码
        PaymentPlanDAO dao = new PaymentPlanDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deletePaymentPlanById(DynamicDict dto) throws Exception {
        // 调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        PaymentPlanDAO dao = new PaymentPlanDAO();

        String SQL = "update payment_plan set action_type = ?, state_date = ?, record_exp_date = ?, state = ? "
                + " where payment_plan_id = ? and seq_nbr = ?  ";

        List updateParams = new ArrayList();
        updateParams.add(AcctBO.ACTION_TYPE_DELETE);
        updateParams.add(DAOUtils.getCurrentDate());
        updateParams.add(DAOUtils.getCurrentDate());
        updateParams.add(AcctBO.STATE_CANCEL);
        updateParams.add(keyCondMap.get("payment_plan_id"));
        updateParams.add(keyCondMap.get("seq_nbr"));

        return Base.update(dao.getDbName(), SQL, updateParams, 1,
                Const.UN_JUDGE_ERROR, "") > 0;

        // boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
        // return result ;
    }
}
