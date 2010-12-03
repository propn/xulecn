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


public class CashPlanService {
    /**
         需要替换位置 说明 ：
      1. ServiceList.CustBO  替换为ServiceList注册的服务
      2. searchCashPlanData 改方法的参数名称
      3. findCashPlanByCond(String payment_plan_id) 参数需要根据实际情况修改
      4. 不需要的方法，可以根据实际情况进行裁剪
      5. 此段啰嗦话，完成后替换工作后，请删除！
     */
    public boolean insertCashPlan(HashMap CashPlan) throws Exception {
        Map param = new HashMap();
        param.put("CashPlan", CashPlan);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "insertCashPlan", param));

        return result;
    }

    public boolean updateCashPlan(HashMap CashPlan) throws Exception {
        Map param = new HashMap();
        param.put("CashPlan", CashPlan);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "updateCashPlan", param));

        return result;
    }

    public PageModel searchCashPlanData(String iParam1, String iParam2,
        String iParam3, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "searchCashPlanData", param));

        return result;
    }

    public Map getCashPlanById(String payment_plan_id)
        throws Exception {
        Map param = getCashPlanKeyMap(payment_plan_id);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "getCashPlanById", param));

        return result;
    }

    public List findCashPlanByCond(String payment_plan_id)
        throws Exception {
        Map param = getCashPlanKeyMap(payment_plan_id);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "findCashPlanByCond", param));

        return result;
    }

    public boolean deleteCashPlanById(String payment_plan_id)
        throws Exception {
        Map param = getCashPlanKeyMap(payment_plan_id);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "deleteCashPlanById", param));

        return result;
    }

    private Map getCashPlanKeyMap(String payment_plan_id) {
        Map param = new HashMap();

        param.put("payment_plan_id", payment_plan_id);

        return param;
    }
}
