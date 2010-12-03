package com.ztesoft.crm.salesres.service;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.vo.RcOrderListVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcOrderListService extends DictService {
    public PageModel showOrderBill(String orderId, int pi, int ps)
        throws Exception {
    	Map map = new HashMap();
        map.put("pageIndex",new Integer(pi));
        map.put("pageSize",new Integer(ps));
        
        map.put("orderId", orderId);
        
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("RcOrderListBO","showOrderBill" ,map);
        return result;

    }

    public String clearOrderBill(String orderId) throws Exception {
    	Map map = new HashMap();
        
        map.put("orderId", orderId);
        
        String result = (String)ServiceManager.callJavaBeanService("RcOrderListBO","clearOrderBill" ,map);
        return result;

    }

    /**
     * 新增提交的出入库上传实例列表
     * @param lists
     * @return Map: result:0 失败，1 成功；info: 错误信息
     */
    public Map addOrderListEntity(RcOrderListVO[] lists)
        throws Exception {
    	Map map = new HashMap();
        map.put("lists", lists);
        Map result = (Map)ServiceManager.callJavaBeanService("RcOrderListBO","addOrderListEntity" ,map);

        return result;
    }

    public PageModel showOrderRescDetail(String orderId, String salesRescName,
        String resBCode, String resECode, String appType,
        String outStorageName, String inStorageName, String operName,
        int pstart, int pend) throws Exception {
        Map map = new HashMap();

        if (orderId != null) {
            map.put("orderId", orderId);
        }

        if (salesRescName != null) {
            map.put("salesRescName", salesRescName);
        }

        if (resBCode != null) {
            map.put("resBCode", resBCode);
        }

        if (resECode != null) {
            map.put("resECode", resECode);
        }

        if (appType != null) {
            map.put("appType", appType);
        }

        if (outStorageName != null) {
            map.put("outStorageName", outStorageName);
        }

        if (inStorageName != null) {
            map.put("inStorageName", inStorageName);
        }

        if (operName != null) {
            map.put("operName", operName);
        }
        map.put("pageIndex",new Integer(pstart));
        map.put("pageSize",new Integer(pend));
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("RcOrderListBO","showOrderRescDetail" ,map);

        return result;

    }
}
