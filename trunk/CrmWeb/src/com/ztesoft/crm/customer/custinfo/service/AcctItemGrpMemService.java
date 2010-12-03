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


public class AcctItemGrpMemService {
    /**
         需要替换位置 说明 ：
      1. ServiceList.CustBO  替换为ServiceList注册的服务
      2. searchAcctItemGrpMemData 改方法的参数名称
      3. findAcctItemGrpMemByCond(String acct_item_group_id,String acct_item_type_id,String item_source_id) 参数需要根据实际情况修改
      4. 不需要的方法，可以根据实际情况进行裁剪
      5. 此段啰嗦话，完成后替换工作后，请删除！
     */
    public boolean insertAcctItemGrpMem(HashMap AcctItemGrpMem)
        throws Exception {
        Map param = new HashMap();
        param.put("AcctItemGrpMem", AcctItemGrpMem);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "insertAcctItemGrpMem", param));

        return result;
    }

    public boolean updateAcctItemGrpMem(HashMap AcctItemGrpMem)
        throws Exception {
        Map param = new HashMap();
        param.put("AcctItemGrpMem", AcctItemGrpMem);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "updateAcctItemGrpMem", param));

        return result;
    }

    public PageModel searchAcctItemGrpMemData(String iParam1, String iParam2,
        String iParam3, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "searchAcctItemGrpMemData", param));

        return result;
    }

    public Map getAcctItemGrpMemById(String acct_item_group_id,
        String acct_item_type_id, String item_source_id)
        throws Exception {
        Map param = getAcctItemGrpMemKeyMap(acct_item_group_id,
                acct_item_type_id, item_source_id);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "getAcctItemGrpMemById", param));

        return result;
    }

    public List findAcctItemGrpMemByCond(String acct_item_group_id,
        String acct_item_type_id, String item_source_id)
        throws Exception {
        Map param = getAcctItemGrpMemKeyMap(acct_item_group_id,
                acct_item_type_id, item_source_id);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "findAcctItemGrpMemByCond", param));

        return result;
    }

    public boolean deleteAcctItemGrpMemById(String acct_item_group_id,
        String acct_item_type_id, String item_source_id)
        throws Exception {
        Map param = getAcctItemGrpMemKeyMap(acct_item_group_id,
                acct_item_type_id, item_source_id);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "deleteAcctItemGrpMemById", param));

        return result;
    }

    private Map getAcctItemGrpMemKeyMap(String acct_item_group_id,
        String acct_item_type_id, String item_source_id) {
        Map param = new HashMap();

        param.put("acct_item_group_id", acct_item_group_id);

        param.put("acct_item_type_id", acct_item_type_id);

        param.put("item_source_id", item_source_id);

        return param;
    }
}
