package com.ztesoft.crm.salesres.service;

import java.util.HashMap;

import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class MpOperatorLanService extends DictService {
    public PageModel qryOperLan(String operCode, String operName, int pi, int ps)
        throws Exception {
        PageModel pm = new PageModel();
        HashMap map = new HashMap();
        map.put("operCode", operCode);
        map.put("operName", operName);
        map.put("pageIndex",new Integer(pi));
        map.put("pageSize",new Integer(ps));
        try {
        	pm = (PageModel)ServiceManager.callJavaBeanService("MpOperatorLanBO", "qryOperLan" , map) ;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    public String saveOperLan(String operIds) {
        String pm = "";

        HashMap map = new HashMap();
        map.put("operIds", operIds);
        try {
        	pm= (String)ServiceManager.callJavaBeanService("MpOperatorLanBO", "saveOperLan" , map) ;

    		
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    public String delOperLan(String operId) {
        String pm = "";
        HashMap map = new HashMap();
        map.put("operId", operId);
        try {
        	pm= (String)ServiceManager.callJavaBeanService("MpOperatorLanBO", "delOperLan" , map) ;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }
}
