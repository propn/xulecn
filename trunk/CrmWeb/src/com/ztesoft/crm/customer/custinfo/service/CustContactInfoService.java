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


public class CustContactInfoService {
    /**
         ��Ҫ�滻λ�� ˵�� ��
      1. ServiceList.CustBO  �滻ΪServiceListע��ķ���
      2. searchCustContactInfoData �ķ����Ĳ�������
      3. findCustContactInfoByCond(String contact_id,String seq_nbr) ������Ҫ����ʵ������޸�
      4. ����Ҫ�ķ��������Ը���ʵ��������вü�
      5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertCustContactInfo(HashMap CustContactInfo)
        throws Exception {
        Map param = new HashMap();
        param.put("CustContactInfo", CustContactInfo);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "insertCustContactInfo", param));

        return result;
    }

    public boolean updateCustContactInfo(HashMap CustContactInfo)
        throws Exception {
        Map param = new HashMap();
        param.put("CustContactInfo", CustContactInfo);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "updateCustContactInfo", param));

        return result;
    }

    public PageModel searchCustContactInfoData(String iParam1, String iParam2,
        String iParam3, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "searchCustContactInfoData", param));

        return result;
    }

    public Map getCustContactInfoById(String contact_id, String seq_nbr)
        throws Exception {
        Map param = getCustContactInfoKeyMap(contact_id, seq_nbr);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "getCustContactInfoById", param));

        return result;
    }

    public List findCustContactInfoByCond(String contact_id, String seq_nbr)
        throws Exception {
        Map param = getCustContactInfoKeyMap(contact_id, seq_nbr);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "findCustContactInfoByCond", param));

        return result;
    }

    public boolean deleteCustContactInfoById(String contact_id, String seq_nbr)
        throws Exception {
        Map param = getCustContactInfoKeyMap(contact_id, seq_nbr);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "deleteCustContactInfoById", param));

        return result;
    }

    private Map getCustContactInfoKeyMap(String contact_id, String seq_nbr) {
        Map param = new HashMap();

        param.put("contact_id", contact_id);

        param.put("seq_nbr", seq_nbr);

        return param;
    }
}
