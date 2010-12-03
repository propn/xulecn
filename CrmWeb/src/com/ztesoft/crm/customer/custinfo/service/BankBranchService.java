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


public class BankBranchService {
    /**
         需要替换位置 说明 ：
      1. ServiceList.CustBO  替换为ServiceList注册的服务
      2. searchBankBranchData 改方法的参数名称
      3. findBankBranchByCond(String bank_branch_id) 参数需要根据实际情况修改
      4. 不需要的方法，可以根据实际情况进行裁剪
      5. 此段啰嗦话，完成后替换工作后，请删除！
     */
    public boolean insertBankBranch(HashMap BankBranch)
        throws Exception {
        Map param = new HashMap();
        param.put("BankBranch", BankBranch);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "insertBankBranch", param));

        return result;
    }

    public boolean updateBankBranch(HashMap BankBranch)
        throws Exception {
        Map param = new HashMap();
        param.put("BankBranch", BankBranch);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "updateBankBranch", param));

        return result;
    }

    public PageModel searchBankBranchData(String iParam1, String iParam2,
        String iParam3, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("iParam1", iParam1);
        param.put("iParam2", iParam2);
        param.put("iParam3", iParam3);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "searchBankBranchData", param));

        return result;
    }

    public Map getBankBranchById(String bank_branch_id)
        throws Exception {
        Map param = getBankBranchKeyMap(bank_branch_id);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "getBankBranchById", param));

        return result;
    }

    public List findBankBranchByCond(String bank_branch_id)
        throws Exception {
        Map param = getBankBranchKeyMap(bank_branch_id);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "findBankBranchByCond", param));

        return result;
    }

    public boolean deleteBankBranchById(String bank_branch_id)
        throws Exception {
        Map param = getBankBranchKeyMap(bank_branch_id);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.CustBO, "deleteBankBranchById", param));

        return result;
    }

    private Map getBankBranchKeyMap(String bank_branch_id) {
        Map param = new HashMap();

        param.put("bank_branch_id", bank_branch_id);

        return param;
    }
}
