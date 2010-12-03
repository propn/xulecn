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


public class AcctService {
    /**
         ��Ҫ�滻λ�� ˵�� ��
      1. ServiceList.AcctBO  �滻ΪServiceListע��ķ���
      2. searchAcctData �ķ����Ĳ�������
      3. findAcctByCond(String acct_id,String seq_nbr) ������Ҫ����ʵ������޸�
      4. ����Ҫ�ķ��������Ը���ʵ��������вü�
      5. �˶Ά��»�����ɺ��滻��������ɾ����
     */


    public boolean insertAcct(HashMap Acct) throws Exception {
        Map param = new HashMap();
        param.put("Acct", Acct);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.AcctBO, "insertAcct", param));

        return result;
    }

    public boolean updateAcct(HashMap Acct) throws Exception {
        Map param = new HashMap();
        param.put("Acct", Acct);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.AcctBO, "updateAcct", param));

        return result;
    }
    
    
    public boolean updateAcctAll(HashMap all) throws Exception {
        Map param = new HashMap();
        param.put("all", all);

        
        boolean result = false;
        try {
            result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.AcctBO, "updateAcctAll", param));
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    public PageModel searchAcctData(String cust_id, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("cust_id", cust_id);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.AcctBO, "searchAcctData", param));

        return result;
    }
    
    public PageModel searchAcctDataHis(String cust_id, String queryDate,  int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("cust_id", cust_id);
        param.put("queryDate", queryDate);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.AcctBO, "searchAcctDataHis", param));

        return result;
    }
    
    public PageModel queryServAcctByAcctId(String cust_id, String queryDate,  int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("cust_id", cust_id);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    "QueryServAcctBO", "queryServAcctByAcctId", param));

        return result;
    }

    public Map getAcctById(String acct_id, String seq_nbr)
        throws Exception {
        Map param = getAcctKeyMap(acct_id, seq_nbr);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.AcctBO, "getAcctById", param));

        return result;
    }

    public List findAcctByCond(String acct_id, String seq_nbr)
        throws Exception {
        Map param = getAcctKeyMap(acct_id, seq_nbr);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.AcctBO, "findAcctByCond", param));

        return result;
    }

    public int deleteAcctById(String acct_id, String seq_nbr)
        throws Exception {
        Map param = getAcctKeyMap(acct_id, seq_nbr);

        int result = DataTranslate._int(ServiceManager.callJavaBeanService(
                    ServiceList.AcctBO, "deleteAcctById", param));

        return result;
    }

    private Map getAcctKeyMap(String acct_id, String seq_nbr) {
        Map param = new HashMap();

        param.put("acct_id", acct_id);

        param.put("seq_nbr", seq_nbr);

        return param;
    }
    
}
