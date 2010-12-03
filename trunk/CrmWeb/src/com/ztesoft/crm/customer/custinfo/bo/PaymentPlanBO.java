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
     * ��Ҫ�滻λ�� ˵�� �� 1. �����������,����ʵ������޸� 2. searchPaymentPlanData �ķ����Ĳ������� 3.
     * findPaymentPlanByCond(String payment_plan_id,String seq_nbr) ������Ҫ����ʵ������޸�
     * 4. ����Ҫ�ķ��������Ը���ʵ��������вü� 5. �˶Ά��»�����ɺ��滻��������ɾ����
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
        // ��������
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

        // ����DAO����
        PaymentPlanDAO dao = new PaymentPlanDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    public PageModel searchPaymentPlanDataHis(DynamicDict dto) throws Exception {
        // ��������
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

        // ����DAO����
        PaymentPlanDAO dao = new PaymentPlanDAO();

        String COUNTSQL = null;
        String SELSQL = null;
        if (!"".equals(queryDate) && queryDate != null) { // ��ʷ��ѯ
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
     * ���ݿ��������ѯ����
     */
    public Map getPaymentPlanById(DynamicDict dto) throws Exception {
        // ����DAO����
        Map keyCondMap = Const.getParam(dto);
        PaymentPlanDAO dao = new PaymentPlanDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findPaymentPlanByCond(DynamicDict dto) throws Exception {
        // ��������
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        // ��֯����DAO���������
        String whereCond = where.getWhereCond();
        List para = where.getPara();
        // ����DAO����
        PaymentPlanDAO dao = new PaymentPlanDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deletePaymentPlanById(DynamicDict dto) throws Exception {
        // ����DAO����
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
