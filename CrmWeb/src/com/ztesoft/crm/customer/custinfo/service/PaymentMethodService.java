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


public class PaymentMethodService {
    /**
         ��Ҫ�滻λ�� ˵�� ��
      1. ServiceList.CustBO  �滻ΪServiceListע��ķ���
      2. searchPaymentMethodData �ķ����Ĳ�������
      3. findPaymentMethodByCond(String payment_method) ������Ҫ����ʵ������޸�
      4. ����Ҫ�ķ��������Ը���ʵ��������вü�
      5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertPaymentMethod(HashMap PaymentMethod)
        throws Exception {
        Map param = new HashMap();
        param.put("PaymentMethod", PaymentMethod);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "insertPaymentMethod", param));

        return result;
    }

    public boolean updatePaymentMethod(HashMap PaymentMethod)
        throws Exception {
        Map param = new HashMap();
        param.put("PaymentMethod", PaymentMethod);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "updatePaymentMethod", param));

        return result;
    }

    public PageModel searchPaymentMethodData(String iParam1, String iParam2,
        String iParam3, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "searchPaymentMethodData", param));

        return result;
    }

    public Map getPaymentMethodById(String payment_method)
        throws Exception {
        Map param = getPaymentMethodKeyMap(payment_method);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "getPaymentMethodById", param));

        return result;
    }

    public List findPaymentMethodByCond(String payment_method)
        throws Exception {
        Map param = getPaymentMethodKeyMap(payment_method);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "findPaymentMethodByCond", param));

        return result;
    }

    public boolean deletePaymentMethodById(String payment_method)
        throws Exception {
        Map param = getPaymentMethodKeyMap(payment_method);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "deletePaymentMethodById", param));

        return result;
    }

    private Map getPaymentMethodKeyMap(String payment_method) {
        Map param = new HashMap();

        param.put("payment_method", payment_method);

        return param;
    }
}
