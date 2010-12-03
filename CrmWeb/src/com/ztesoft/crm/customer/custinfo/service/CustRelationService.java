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

public class CustRelationService {
    /**
     * ��Ҫ�滻λ�� ˵�� �� 1. ServiceList.CustRelationBO �滻ΪServiceListע��ķ��� 2.
     * searchCustRelationData �ķ����Ĳ������� 3. findCustRelationByCond(String
     * cust_relation_id,String seq_nbr) ������Ҫ����ʵ������޸� 4. ����Ҫ�ķ��������Ը���ʵ��������вü� 5.
     * �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertCustRelation(HashMap CustRelation) throws Exception {
        Map param = new HashMap();
        param.put("CustRelation", CustRelation);

        boolean result = DataTranslate._boolean(ServiceManager
                .callJavaBeanService(ServiceList.CustRelationBO,
                        "insertCustRelation", param));

        return result;
    }

    public boolean updateCustRelation(HashMap CustRelation) throws Exception {
        Map param = new HashMap();
        param.put("CustRelation", CustRelation);

        boolean result = DataTranslate._boolean(ServiceManager
                .callJavaBeanService(ServiceList.CustRelationBO,
                        "updateCustRelation", param));

        return result;
    }

    public boolean updateCustRelationAll(HashMap all) throws Exception {
        Map param = new HashMap();
        param.put("all", all);

        boolean result = false;

        try {
            result = DataTranslate
                    ._boolean(ServiceManager.callJavaBeanService(
                            ServiceList.CustRelationBO,
                            "updateCustRelationAll", param));
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    public PageModel searchCustRelationData(String iParam1, String iParam2,
            String iParam3, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager
                .callJavaBeanService(ServiceList.CustRelationBO,
                        "searchCustRelationData", param));

        return result;
    }

    public List searchCustRelationDataList(String cust_id, String datatype)
            throws Exception {
        Map param = new HashMap();
        param.put("cust_id", cust_id);
        param.put("datatype", datatype);

        List result = DataTranslate._List(ServiceManager
                .callJavaBeanService(ServiceList.CustRelationBO,
                        "searchCustRelationDataList", param));

        return result;
    }

    public List searchCustRelationDataListHis(String cust_id, String datatype, String queryDate)
            throws Exception {
        Map param = new HashMap();
        param.put("cust_id", cust_id);
        param.put("datatype", datatype);
        param.put("queryDate", queryDate);

        List result = DataTranslate._List(ServiceManager
                .callJavaBeanService(ServiceList.CustRelationBO,
                        "searchCustRelationDataListHis", param));

        return result;
    }

    public Map getCustRelationById(String cust_relation_id, String seq_nbr)
            throws Exception {
        Map param = getCustRelationKeyMap(cust_relation_id, seq_nbr);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                ServiceList.CustRelationBO, "getCustRelationById", param));

        return result;
    }

    public List findCustRelationByCond(String cust_relation_id, String seq_nbr)
            throws Exception {
        Map param = getCustRelationKeyMap(cust_relation_id, seq_nbr);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                ServiceList.CustRelationBO, "findCustRelationByCond", param));

        return result;
    }

    public boolean deleteCustRelationById(String cust_relation_id,
            String seq_nbr) throws Exception {
        Map param = getCustRelationKeyMap(cust_relation_id, seq_nbr);

        boolean result = DataTranslate._boolean(ServiceManager
                .callJavaBeanService(ServiceList.CustRelationBO,
                        "deleteCustRelationById", param));

        return result;
    }

    private Map getCustRelationKeyMap(String cust_relation_id, String seq_nbr) {
        Map param = new HashMap();

        param.put("cust_relation_id", cust_relation_id);

        param.put("seq_nbr", seq_nbr);

        return param;
    }
}
