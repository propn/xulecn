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


public class IndustryService {
    /**
         需要替换位置 说明 ：
      1. ServiceList.CustBO  替换为ServiceList注册的服务
      2. searchIndustryData 改方法的参数名称
      3. findIndustryByCond(String industry_id) 参数需要根据实际情况修改
      4. 不需要的方法，可以根据实际情况进行裁剪
      5. 此段啰嗦话，完成后替换工作后，请删除！
     */
    public boolean insertIndustry(HashMap Industry) throws Exception {
        Map param = new HashMap();
        param.put("Industry", Industry);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "insertIndustry", param));

        return result;
    }

    public boolean updateIndustry(HashMap Industry) throws Exception {
        Map param = new HashMap();
        param.put("Industry", Industry);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "updateIndustry", param));

        return result;
    }

    public PageModel searchIndustryData(String iParam1, String iParam2,
        String iParam3, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "searchIndustryData", param));

        return result;
    }

    public Map getIndustryById(String industry_id) throws Exception {
        Map param = getIndustryKeyMap(industry_id);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "getIndustryById", param));

        return result;
    }

    public List findIndustryByCond(String industry_id)
        throws Exception {
        Map param = getIndustryKeyMap(industry_id);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "findIndustryByCond", param));

        return result;
    }

    public boolean deleteIndustryById(String industry_id)
        throws Exception {
        Map param = getIndustryKeyMap(industry_id);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "deleteIndustryById", param));

        return result;
    }

    private Map getIndustryKeyMap(String industry_id) {
        Map param = new HashMap();

        param.put("industry_id", industry_id);

        return param;
    }
}
