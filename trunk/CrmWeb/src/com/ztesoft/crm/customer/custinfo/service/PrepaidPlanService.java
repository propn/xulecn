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


public class PrepaidPlanService {
    /**
         ��Ҫ�滻λ�� ˵�� ��
      1. ServiceList.CustBO  �滻ΪServiceListע��ķ���
      2. searchPrepaidPlanData �ķ����Ĳ�������
      3. findPrepaidPlanByCond(String payment_plan_id) ������Ҫ����ʵ������޸�
      4. ����Ҫ�ķ��������Ը���ʵ��������вü�
      5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertPrepaidPlan(HashMap PrepaidPlan)
        throws Exception {
        Map param = new HashMap();
        param.put("PrepaidPlan", PrepaidPlan);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "insertPrepaidPlan", param));

        return result;
    }

    public boolean updatePrepaidPlan(HashMap PrepaidPlan)
        throws Exception {
        Map param = new HashMap();
        param.put("PrepaidPlan", PrepaidPlan);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "updatePrepaidPlan", param));

        return result;
    }

    public PageModel searchPrepaidPlanData(String iParam1, String iParam2,
        String iParam3, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "searchPrepaidPlanData", param));

        return result;
    }

    public Map getPrepaidPlanById(String payment_plan_id)
        throws Exception {
        Map param = getPrepaidPlanKeyMap(payment_plan_id);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "getPrepaidPlanById", param));

        return result;
    }

    public List findPrepaidPlanByCond(String payment_plan_id)
        throws Exception {
        Map param = getPrepaidPlanKeyMap(payment_plan_id);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "findPrepaidPlanByCond", param));

        return result;
    }

    public boolean deletePrepaidPlanById(String payment_plan_id)
        throws Exception {
        Map param = getPrepaidPlanKeyMap(payment_plan_id);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "deletePrepaidPlanById", param));

        return result;
    }

    private Map getPrepaidPlanKeyMap(String payment_plan_id) {
        Map param = new HashMap();

        param.put("payment_plan_id", payment_plan_id);

        return param;
    }
}
