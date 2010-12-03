package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.dao.CashPlanDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CashPlanBO extends DictAction {
    /**
        ��Ҫ�滻λ�� ˵�� ��
             1. �����������,����ʵ������޸�
             2. searchCashPlanData �ķ����Ĳ�������
             3. findCashPlanByCond(String payment_plan_id) ������Ҫ����ʵ������޸�
             4. ����Ҫ�ķ��������Ը���ʵ��������вü�
             5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertCashPlan(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map CashPlan = (Map) param.get("CashPlan");

        CashPlanDAO dao = new CashPlanDAO();
        boolean result = dao.insert(CashPlan);

        return result;
    }

    public boolean updateCashPlan(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map CashPlan = (Map) param.get("CashPlan");
        String keyStr = "payment_plan_id";
        Map keyCondMap = Const.getMapForTargetStr(CashPlan, keyStr);
        CashPlanDAO dao = new CashPlanDAO();
        boolean result = dao.updateByKey(CashPlan, keyCondMap);

        return result;
    }

    public PageModel searchCashPlanData(DynamicDict dto)
        throws Exception {
        //��������
        Map param = Const.getParam(dto);
        StringBuffer whereCond = new StringBuffer();
        List para = new ArrayList();

        if (Const.containStrValue(param, "param1")) {
            whereCond.append(" and param1 = ? ");
            para.add(Const.getStrValue(param, "param1"));
        }

        if (Const.containStrValue(param, "param2")) {
            whereCond.append(" and param2 = ? ");
            para.add(Const.getStrValue(param, "param2"));
        }

        if (Const.containStrValue(param, "param3")) {
            whereCond.append(" and param3 = ? ");
            para.add(Const.getStrValue(param, "param3"));
        }

        int pageSize = Const.getPageSize(param);
        int pageIndex = Const.getPageIndex(param);

        //����DAO����
        CashPlanDAO dao = new CashPlanDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * ���ݿ��������ѯ����
     */
    public Map getCashPlanById(DynamicDict dto) throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        CashPlanDAO dao = new CashPlanDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findCashPlanByCond(DynamicDict dto) throws Exception {
        //��������
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        //��֯����DAO���������
        String whereCond = where.getWhereCond();
        List para = where.getPara();

        //����DAO����
        CashPlanDAO dao = new CashPlanDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deleteCashPlanById(DynamicDict dto)
        throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        CashPlanDAO dao = new CashPlanDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
