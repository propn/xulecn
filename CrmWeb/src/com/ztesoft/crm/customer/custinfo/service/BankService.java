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


public class BankService {
    /**
         需要替换位置 说明 ：
      1. ServiceList.BankBO  替换为ServiceList注册的服务
      2. searchBankData 改方法的参数名称
      3. findBankByCond(String bank_id) 参数需要根据实际情况修改
      4. 不需要的方法，可以根据实际情况进行裁剪
      5. 此段啰嗦话，完成后替换工作后，请删除！
     */
    public boolean insertBank(HashMap Bank) throws Exception {
        Map param = new HashMap();
        param.put("Bank", Bank);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.BankBO, "insertBank", param));

        return result;
    }

    public boolean updateBank(HashMap Bank) throws Exception {
        Map param = new HashMap();
        param.put("Bank", Bank);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.BankBO, "updateBank", param));

        return result;
    }

    public PageModel searchBankData(String sType, String sContent, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("sType", sType);
        param.put("sContent", sContent);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.BankBO, "searchBankData", param));

        return result;
    }
    
    public PageModel getBankData(String sType, String sContent, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("sType", sType);
        param.put("sContent", sContent);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                    ServiceList.BankBO, "getBankData", param));

        return result;
    }
    
    /**
     * 查询银行
     * 
     * @param parentId
     * @return
     * @throws Exception
     */
    public String queryBankXml(String region_id, String parent_bank_id)
            throws Exception {
        Map param = new HashMap();
        param.put("region_id", region_id);
        param.put("parent_bank_id", parent_bank_id);

        String retXml = DataTranslate._String(ServiceManager
                .callJavaBeanService(ServiceList.BankBO, "queryBankXml",
                        param));

        return retXml;
    }

    public Map getBankById(String bank_id) throws Exception {
        Map param = getBankKeyMap(bank_id);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                    ServiceList.BankBO, "getBankById", param));

        return result;
    }

    public List findBankByCond(String bank_id) throws Exception {
        Map param = getBankKeyMap(bank_id);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                    ServiceList.BankBO, "findBankByCond", param));

        return result;
    }

    public boolean deleteBankById(String bank_id) throws Exception {
        Map param = getBankKeyMap(bank_id);

        boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService(
                    ServiceList.BankBO, "deleteBankById", param));

        return result;
    }

    private Map getBankKeyMap(String bank_id) {
        Map param = new HashMap();

        param.put("bank_id", bank_id);

        return param;
    }
}
