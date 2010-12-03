package com.ztesoft.crm.customer.custinfo.service;

import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;

import net.buffalo.request.RequestContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CcardPlanService {
    /**
         ��Ҫ�滻λ�� ˵�� ��
      1. ServiceList.CustBO  �滻ΪServiceListע��ķ���
      2. searchCcardPlanData �ķ����Ĳ�������
      3. findCcardPlanByCond(String payment_plan_id) ������Ҫ����ʵ������޸�
      4. ����Ҫ�ķ��������Ը���ʵ��������вü�
      5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertCcardPlan(HashMap CcardPlan) throws Exception {
        Map param = new HashMap();
        param.put("CcardPlan", CcardPlan);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "insertCcardPlan", param));

        return result;
    }

    public boolean updateCcardPlan(HashMap CcardPlan) throws Exception {
        Map param = new HashMap();
        param.put("CcardPlan", CcardPlan);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "updateCcardPlan", param));

        return result;
    }

    public PageModel searchCcardPlanData(String iParam1, String iParam2,
        String iParam3, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "searchCcardPlanData", param));

        return result;
    }

    public Map getCcardPlanById(String payment_plan_id)
        throws Exception {
        Map param = getCcardPlanKeyMap(payment_plan_id);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "getCcardPlanById", param));

        return result;
    }

    public List findCcardPlanByCond(String payment_plan_id)
        throws Exception {
        Map param = getCcardPlanKeyMap(payment_plan_id);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "findCcardPlanByCond", param));

        return result;
    }

    public boolean deleteCcardPlanById(String payment_plan_id)
        throws Exception {
        Map param = getCcardPlanKeyMap(payment_plan_id);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "deleteCcardPlanById", param));

        return result;
    }

    private Map getCcardPlanKeyMap(String payment_plan_id) {
        Map param = new HashMap();

        param.put("payment_plan_id", payment_plan_id);

        return param;
    }
}
