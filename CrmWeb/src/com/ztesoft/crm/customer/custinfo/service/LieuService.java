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


public class LieuService {
    /**
         需要替换位置 说明 ：
      1. ServiceList.CustBO  替换为ServiceList注册的服务
      2. searchLieuData 改方法的参数名称
      3. findLieuByCond(String lieu_id) 参数需要根据实际情况修改
      4. 不需要的方法，可以根据实际情况进行裁剪
      5. 此段啰嗦话，完成后替换工作后，请删除！
     */
    public boolean insertLieu(HashMap Lieu) throws Exception {
        Map param = new HashMap();
        param.put("Lieu", Lieu);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "insertLieu", param));

        return result;
    }

    public boolean updateLieu(HashMap Lieu) throws Exception {
        Map param = new HashMap();
        param.put("Lieu", Lieu);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "updateLieu", param));

        return result;
    }

    public PageModel searchLieuData(String iParam1, String iParam2,
        String iParam3, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "searchLieuData", param));

        return result;
    }

    public Map getLieuById(String lieu_id) throws Exception {
        Map param = getLieuKeyMap(lieu_id);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "getLieuById", param));

        return result;
    }

    public List findLieuByCond(String lieu_id) throws Exception {
        Map param = getLieuKeyMap(lieu_id);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "findLieuByCond", param));

        return result;
    }

    public boolean deleteLieuById(String lieu_id) throws Exception {
        Map param = getLieuKeyMap(lieu_id);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "deleteLieuById", param));

        return result;
    }

    private Map getLieuKeyMap(String lieu_id) {
        Map param = new HashMap();

        param.put("lieu_id", lieu_id);

        return param;
    }
}
