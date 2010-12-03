package com.ztesoft.crm.customer.custinfo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.dict.DataTranslate;

import com.ztesoft.common.util.PageModel;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.ServiceList;

public class PaymentPlanService {
    /**
     * 需要替换位置 说明 ： 1. ServiceList.PaymentPlanBO 替换为ServiceList注册的服务 2.
     * searchPaymentPlanData 改方法的参数名称 3. findPaymentPlanByCond(String
     * payment_plan_id,String seq_nbr) 参数需要根据实际情况修改 4. 不需要的方法，可以根据实际情况进行裁剪 5.
     * 此段啰嗦话，完成后替换工作后，请删除！
     */

    public boolean insertPaymentPlan(HashMap PaymentPlan) throws Exception {
        Map param = new HashMap();
        param.put("PaymentPlan", PaymentPlan);

        boolean result = DataTranslate._boolean(ServiceManager
                .callJavaBeanService(ServiceList.PaymentPlanBO,
                        "insertPaymentPlan", param));
        return result;
    }

    public boolean updatePaymentPlan(HashMap PaymentPlan) throws Exception {
        Map param = new HashMap();
        param.put("PaymentPlan", PaymentPlan);
        boolean result = DataTranslate._boolean(ServiceManager
                .callJavaBeanService(ServiceList.PaymentPlanBO,
                        "updatePaymentPlan", param));

        return result;
    }

    public PageModel searchPaymentPlanData(String acct_id, int pageIndex,
            int pageSize) throws Exception {

        Map param = new HashMap();
        param.put("acct_id", acct_id);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager
                .callJavaBeanService(ServiceList.PaymentPlanBO,
                        "searchPaymentPlanData", param));

        return result;
    }
    
    public PageModel searchPaymentPlanDataHis(String acct_id,  String queryDate, int pageIndex,
            int pageSize) throws Exception {

        Map param = new HashMap();
        param.put("acct_id", acct_id);
        param.put("queryDate", queryDate);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager
                .callJavaBeanService(ServiceList.PaymentPlanBO,
                        "searchPaymentPlanDataHis", param));

        return result;
    }

    public Map getPaymentPlanById(String payment_plan_id, String seq_nbr)
            throws Exception {
        Map param = getPaymentPlanKeyMap(payment_plan_id, seq_nbr);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                ServiceList.PaymentPlanBO, "getPaymentPlanById", param));

        return result;
    }

    public List findPaymentPlanByCond(String payment_plan_id, String seq_nbr)
            throws Exception {
        Map param = getPaymentPlanKeyMap(payment_plan_id, seq_nbr);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                ServiceList.PaymentPlanBO, "findPaymentPlanByCond", param));

        return result;
    }

    public boolean deletePaymentPlanById(String payment_plan_id, String seq_nbr)
            throws Exception {
        Map param = getPaymentPlanKeyMap(payment_plan_id, seq_nbr);

        boolean result = DataTranslate._boolean(ServiceManager
                .callJavaBeanService(ServiceList.PaymentPlanBO,
                        "deletePaymentPlanById", param));

        return result;
    }

    private Map getPaymentPlanKeyMap(String payment_plan_id, String seq_nbr) {
        Map param = new HashMap();

        param.put("payment_plan_id", payment_plan_id);

        param.put("seq_nbr", seq_nbr);

        return param;
    }
}
