package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.dao.CcardPlanDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CcardPlanBO extends DictAction {
    /**
        ��Ҫ�滻λ�� ˵�� ��
             1. �����������,����ʵ������޸�
             2. searchCcardPlanData �ķ����Ĳ�������
             3. findCcardPlanByCond(String payment_plan_id) ������Ҫ����ʵ������޸�
             4. ����Ҫ�ķ��������Ը���ʵ��������вü�
             5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertCcardPlan(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map CcardPlan = (Map) param.get("CcardPlan");

        CcardPlanDAO dao = new CcardPlanDAO();
        boolean result = dao.insert(CcardPlan);

        return result;
    }

    public boolean updateCcardPlan(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map CcardPlan = (Map) param.get("CcardPlan");
        String keyStr = "payment_plan_id";
        Map keyCondMap = Const.getMapForTargetStr(CcardPlan, keyStr);
        CcardPlanDAO dao = new CcardPlanDAO();
        boolean result = dao.updateByKey(CcardPlan, keyCondMap);

        return result;
    }

    public PageModel searchCcardPlanData(DynamicDict dto)
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
        CcardPlanDAO dao = new CcardPlanDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * ���ݿ��������ѯ����
     */
    public Map getCcardPlanById(DynamicDict dto) throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        CcardPlanDAO dao = new CcardPlanDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findCcardPlanByCond(DynamicDict dto) throws Exception {
        //��������
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        //��֯����DAO���������
        String whereCond = where.getWhereCond();
        List para = where.getPara();

        //����DAO����
        CcardPlanDAO dao = new CcardPlanDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deleteCcardPlanById(DynamicDict dto)
        throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        CcardPlanDAO dao = new CcardPlanDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
