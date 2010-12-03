package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.dao.PrepaidPlanDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PrepaidPlanBO extends DictAction {
    /**
        ��Ҫ�滻λ�� ˵�� ��
             1. �����������,����ʵ������޸�
             2. searchPrepaidPlanData �ķ����Ĳ�������
             3. findPrepaidPlanByCond(String payment_plan_id) ������Ҫ����ʵ������޸�
             4. ����Ҫ�ķ��������Ը���ʵ��������вü�
             5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertPrepaidPlan(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map PrepaidPlan = (Map) param.get("PrepaidPlan");

        PrepaidPlanDAO dao = new PrepaidPlanDAO();
        boolean result = dao.insert(PrepaidPlan);

        return result;
    }

    public boolean updatePrepaidPlan(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map PrepaidPlan = (Map) param.get("PrepaidPlan");
        String keyStr = "payment_plan_id";
        Map keyCondMap = Const.getMapForTargetStr(PrepaidPlan, keyStr);
        PrepaidPlanDAO dao = new PrepaidPlanDAO();
        boolean result = dao.updateByKey(PrepaidPlan, keyCondMap);

        return result;
    }

    public PageModel searchPrepaidPlanData(DynamicDict dto)
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
        PrepaidPlanDAO dao = new PrepaidPlanDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * ���ݿ��������ѯ����
     */
    public Map getPrepaidPlanById(DynamicDict dto) throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        PrepaidPlanDAO dao = new PrepaidPlanDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findPrepaidPlanByCond(DynamicDict dto)
        throws Exception {
        //��������
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        //��֯����DAO���������
        String whereCond = where.getWhereCond();
        List para = where.getPara();

        //����DAO����
        PrepaidPlanDAO dao = new PrepaidPlanDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deletePrepaidPlanById(DynamicDict dto)
        throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        PrepaidPlanDAO dao = new PrepaidPlanDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
