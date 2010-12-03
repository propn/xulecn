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


public class CustPersonInfoService {
    public boolean insertCustPersonInfo(HashMap CustPersonInfo)
        throws Exception {
        Map param = new HashMap();
        param.put("CustPersonInfo", CustPersonInfo);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "insertCustPersonInfo", param));

        return result;
    }

    public boolean updateCustPersonInfo(HashMap CustPersonInfo)
        throws Exception {
        Map param = new HashMap();
        param.put("CustPersonInfo", CustPersonInfo);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "updateCustPersonInfo", param));

        return result;
    }

    public PageModel searchCustPersonInfoData(String iParam1, String iParam2,
        String iParam3, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "searchCustPersonInfoData", param));

        return result;
    }

    public Map getCustPersonInfoById(String cust_id, String seq_nbr)
        throws Exception {
        Map param = getCustPersonInfoKeyMap(cust_id, seq_nbr);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "getCustPersonInfoById", param));

        return result;
    }

    public List findCustPersonInfoByCond(String cust_id, String seq_nbr)
        throws Exception {
        Map param = getCustPersonInfoKeyMap(cust_id, seq_nbr);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "findCustPersonInfoByCond", param));

        return result;
    }

    public boolean deleteCustPersonInfoById(String cust_id, String seq_nbr)
        throws Exception {
        Map param = getCustPersonInfoKeyMap(cust_id, seq_nbr);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "deleteCustPersonInfoById", param));

        return result;
    }

    private Map getCustPersonInfoKeyMap(String cust_id, String seq_nbr) {
        Map param = new HashMap();

        param.put("cust_id", cust_id);

        param.put("seq_nbr", seq_nbr);

        return param;
    }
}
