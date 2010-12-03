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


public class BankPlanService {
    /**
         ��Ҫ�滻λ�� ˵�� ��
      1. ServiceList.CustBO  �滻ΪServiceListע��ķ���
      2. searchBankPlanData �ķ����Ĳ�������
      3. findBankPlanByCond(String payment_plan_id) ������Ҫ����ʵ������޸�
      4. ����Ҫ�ķ��������Ը���ʵ��������вü�
      5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertBankPlan(HashMap BankPlan) throws Exception {
        Map param = new HashMap();
        param.put("BankPlan", BankPlan);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "insertBankPlan", param));

        return result;
    }

    public boolean updateBankPlan(HashMap BankPlan) throws Exception {
        Map param = new HashMap();
        param.put("BankPlan", BankPlan);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "updateBankPlan", param));

        return result;
    }

    public PageModel searchBankPlanData(String iParam1, String iParam2,
        String iParam3, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "searchBankPlanData", param));

        return result;
    }

    public Map getBankPlanById(String payment_plan_id)
        throws Exception {
        Map param = getBankPlanKeyMap(payment_plan_id);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "getBankPlanById", param));

        return result;
    }

    public List findBankPlanByCond(String payment_plan_id)
        throws Exception {
        Map param = getBankPlanKeyMap(payment_plan_id);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "findBankPlanByCond", param));

        return result;
    }

    public boolean deleteBankPlanById(String payment_plan_id)
        throws Exception {
        Map param = getBankPlanKeyMap(payment_plan_id);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "deleteBankPlanById", param));

        return result;
    }

    private Map getBankPlanKeyMap(String payment_plan_id) {
        Map param = new HashMap();

        param.put("payment_plan_id", payment_plan_id);

        return param;
    }
}
