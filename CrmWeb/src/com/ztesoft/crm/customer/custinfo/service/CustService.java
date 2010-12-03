package com.ztesoft.crm.customer.custinfo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.crm.customer.custinfo.dao.IndustryDAO;

/**
 * CustService.java
 * 
 * @function:
 * 
 * @author nik
 * @since 2010-1-23
 * @modified
 */
public class CustService extends DictService {

    /**
     * 查询行业类型
     * 
     * @param parentId
     * @return
     * @throws Exception
     */
    public String queryIndustryXml(String attrId, String attrValueId)
            throws Exception {
        Map param = new HashMap();
        param.put("attrId", attrId);
        param.put("attrValueId", attrValueId);

        String retXml = DataTranslate._String(ServiceManager
                .callJavaBeanService(ServiceList.CustBO, "queryIndustryXml",
                        param));

        return retXml;
    }

    /**
     * 新增保存客户
     * 
     * @param Cust
     * @return
     * @throws Exception
     */
    public Map insertCust(HashMap Cust) throws Exception {
        Map param = new HashMap();
        param.put("Cust", Cust);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                ServiceList.CustBO, "insertCust", param));

        return result;
    }

    /**
     * 修改客户信息，基本资料，联系人信息，扩展信息
     * 
     * @param Cust
     * @return
     * @throws Exception
     */
    public Map updateCust(HashMap Cust) throws Exception {
        Map param = new HashMap();
        param.put("Cust", Cust);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                ServiceList.CustBO, "updateCust", param));

        return result;
    }

    public List getCustHisInfo(String custId, String expDate
    		) throws Exception {
    	Map param = new HashMap();
    	param.put("custId", custId);
    	param.put("expDate", expDate);
    	List result = DataTranslate._List(ServiceManager.callJavaBeanService(
    			ServiceList.CustBO, "getCustHisInfo", param));
    	
    	return result;
    }
    public List getCustContactHisInfo(String contactId,String custId, String expDate
    ) throws Exception {
    	Map param = new HashMap();
    	param.put("contactId", contactId);
    	param.put("custId", custId);
    	param.put("expDate", expDate);
    	List result = DataTranslate._List(ServiceManager.callJavaBeanService(
    			ServiceList.CustBO, "getCustContactHisInfo", param));
    	
    	return result;
    }
    public List getCustPersonHisInfo(String custId, String expDate
    ) throws Exception {
    	Map param = new HashMap();
    	param.put("custId", custId);
    	param.put("expDate", expDate);
    	List result = DataTranslate._List(ServiceManager.callJavaBeanService(
    			ServiceList.CustBO, "getCustPersonHisInfo", param));
    	
    	return result;
    }
    public List getCustCorporateHisInfo(String custId, String expDate
    ) throws Exception {
    	Map param = new HashMap();
    	param.put("custId", custId);
    	param.put("expDate", expDate);
    	List result = DataTranslate._List(ServiceManager.callJavaBeanService(
    			ServiceList.CustBO, "getCustCorporateHisInfo", param));
    	
    	return result;
    }
    public PageModel getCustData(String sPartyType, String sQueryCond,
            String sContent, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("sPartyType", sPartyType);
        param.put("sQueryCond", sQueryCond);
        param.put("sContent", sContent);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));
        PageModel result = null;
        result = DataTranslate._PageModel(ServiceManager.callJavaBeanService(
                ServiceList.CustBO, "getCustData", param));

        return result;
    }

    /**
     * 查询客户
     * 
     * @param servRegoin
     * @param qrytype
     * @param qryparam
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    public PageModel searchCustData(String servRegoin, String qrytype,
            String qryparam, int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("servRegoin", servRegoin);
        param.put("qryType", qrytype);
        param.put("qryParam", qryparam);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));
        PageModel result = null;
        if (qrytype.equals("product_no")) {// 业务号码查询,调用客户接待服务
            result = DataTranslate._PageModel(ServiceManager
                    .callJavaBeanService(ServiceList.CustBO, "searchCustData",
                            param));
        } else {
            result = DataTranslate._PageModel(ServiceManager
                    .callJavaBeanService(ServiceList.CustBO, "searchCustData",
                            param));
        }
        return result;
    }

    /**
     * 企业客户扩展信息
     * 
     * @param cust_id
     * @param seq_nbr
     * @return
     * @throws Exception
     */
    public Map getCustCorporateInfoById(String cust_id, String seq_nbr)
            throws Exception {
        Map param = getCustKeyMap(cust_id, seq_nbr);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                ServiceList.CustBO, "getCustCorporateInfoById", param));

        return result;
    }

    /**
     * 个人客户扩展信息
     * 
     * @param cust_id
     * @param seq_nbr
     * @return
     * @throws Exception
     */
    public Map getCustPersonInfoById(String cust_id, String seq_nbr)
            throws Exception {
        Map param = getCustKeyMap(cust_id, seq_nbr);

        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                ServiceList.CustBO, "getCustPersonInfoById", param));

        return result;
    }

    public Map getCustById(String cust_id, String seq_nbr) throws Exception {
        //Map param = getCustKeyMap(cust_id, seq_nbr);
    	Map param = new HashMap();
        param.put("cust_id", cust_id);
        Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
                ServiceList.CustBO, "getCustById", param));

        return result;
    }

    public List findCustByCond(String cust_id, String seq_nbr) throws Exception {
        Map param = getCustKeyMap(cust_id, seq_nbr);

        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                ServiceList.CustBO, "findCustByCond", param));

        return result;
    }

    public boolean deleteCustById(String cust_id, String seq_nbr)
            throws Exception {
        Map param = getCustKeyMap(cust_id, seq_nbr);

        boolean result = DataTranslate._boolean(ServiceManager
                .callJavaBeanService(ServiceList.CustBO, "deleteCustById",
                        param));

        return result;
    }

    private Map getCustKeyMap(String cust_id, String seq_nbr) {
        Map param = new HashMap();

        param.put("cust_id", cust_id);

        param.put("seq_nbr", seq_nbr);

        return param;
    }

    /**
     * 查询客户联系人信息
     * 
     * @param custId
     * @param seqNbr
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    public PageModel searchCustContactInfoData(String custId, String seqNbr,
            int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("custId", custId);
        param.put("seqNbr", seqNbr);
        param.put("pageIndex", new Integer(pageIndex));
        param.put("pageSize", new Integer(pageSize));

        PageModel result = DataTranslate._PageModel(ServiceManager
                .callJavaBeanService(ServiceList.CustBO,
                        "searchCustContactInfoData", param));

        return result;
    }

    /**
     * @param contact_id
     * @param seq_nbr
     * @return
     * @throws Exception
     */
    public List findCustContactInfoByCond(String cust_id, String seq_nbr)
            throws Exception {
        Map param = new HashMap();

        param.put("cust_id", cust_id);
        // param.put("seq_nbr", "");
        List result = DataTranslate._List(ServiceManager.callJavaBeanService(
                ServiceList.CustBO, "findCustContactInfoByCond", param));

        return result;
    }
}
