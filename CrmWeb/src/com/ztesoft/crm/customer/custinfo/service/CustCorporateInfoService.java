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


public class CustCorporateInfoService {
    /**
         ��Ҫ�滻λ�� ˵�� ��
      1. ServiceList.CustBO  �滻ΪServiceListע��ķ���
      2. searchCustCorporateInfoData �ķ����Ĳ�������
      3. findCustCorporateInfoByCond(String cust_id,String seq_nbr) ������Ҫ����ʵ������޸�
      4. ����Ҫ�ķ��������Ը���ʵ��������вü�
      5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertCustCorporateInfo(HashMap CustCorporateInfo)
        throws Exception {
        Map param = new HashMap();
        param.put("CustCorporateInfo", CustCorporateInfo);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "insertCustCorporateInfo", param));

        return result;
    }

    public boolean updateCustCorporateInfo(HashMap CustCorporateInfo)
        throws Exception {
        Map param = new HashMap();
        param.put("CustCorporateInfo", CustCorporateInfo);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "updateCustCorporateInfo", param));

        return result;
    }

    public PageModel searchCustCorporateInfoData(String iParam1,
        String iParam2, String iParam3, int pageIndex, int pageSize)
        throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "searchCustCorporateInfoData", param));

        return result;
    }

    public Map getCustCorporateInfoById(String cust_id, String seq_nbr)
        throws Exception {
        Map param = getCustCorporateInfoKeyMap(cust_id, seq_nbr);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "getCustCorporateInfoById", param));

        return result;
    }

    public List findCustCorporateInfoByCond(String cust_id, String seq_nbr)
        throws Exception {
        Map param = getCustCorporateInfoKeyMap(cust_id, seq_nbr);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "findCustCorporateInfoByCond", param));

        return result;
    }

    public boolean deleteCustCorporateInfoById(String cust_id, String seq_nbr)
        throws Exception {
        Map param = getCustCorporateInfoKeyMap(cust_id, seq_nbr);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "deleteCustCorporateInfoById", param));

        return result;
    }

    private Map getCustCorporateInfoKeyMap(String cust_id, String seq_nbr) {
        Map param = new HashMap();

        param.put("cust_id", cust_id);

        param.put("seq_nbr", seq_nbr);

        return param;
    }
}
