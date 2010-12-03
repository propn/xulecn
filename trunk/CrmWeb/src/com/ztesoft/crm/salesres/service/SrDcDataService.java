package com.ztesoft.crm.salesres.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.buffalo.request.RequestContext;

import org.apache.log4j.Logger;

import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.vo.DcDataInfoVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class SrDcDataService extends DictService {
    private static Logger logger = Logger.getLogger(SrDcDataService.class);

    /**
     * 查询业务数据提取表
     *
     * @throws Exception
     *
     */
    public PageModel qrydcDataInfo(String data_info_id, String data_info_nam,
        String isAllProvince, String glanId, int pageIndex, int pageSize)
        throws Exception {
        HashMap map = new HashMap();
        map.put("data_info_id", data_info_id);
        map.put("data_info_nam", data_info_nam);
        map.put("isAllProvince", isAllProvince);
        map.put("glanId", glanId);
        
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));

        //return bean.qrydcDataInfo(map, pageIndex, pageSize);
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("SrDcDataBO","qrydcDataInfo" ,map);
        return result;
    }

    /**
     * 保存业务数据提取表
     *
     * @return String
     * @throws Exception
     *
     */
    public String savedcDataInfo(DcDataInfoVO dcDataInfoVO)
        throws Exception {

        //return bean.savedcDataInfo(dcDataInfoVO);
        
        HashMap map = new HashMap();
        map.put("dcDataInfoVO", dcDataInfoVO);
        String result = (String)ServiceManager.callJavaBeanService("SrDcDataBO","savedcDataInfo" ,map);
        return result;  
    }

    /**
     * 修改业务数据提取表
     *
     * @throws Exception
     *
     */
    public void updatedcDataInfo(DcDataInfoVO dcDataInfoVO)
        throws Exception {
        
        //bean.updatedcDataInfo(dcDataInfoVO);
        
        HashMap map = new HashMap();
        map.put("dcDataInfoVO", dcDataInfoVO);
        ServiceManager.callJavaBeanService("SrDcDataBO","updatedcDataInfo" ,map);
    }

    /**
     * 删除业务数据提取表
     *
     * @throws Exception
     *
     */
    public void removedcDataInfo(DcDataInfoVO dcDataInfoVO)
        throws Exception {
//        bean.removedcDataInfo(dcDataInfoVO);
        
        HashMap map = new HashMap();
        map.put("dcDataInfoVO", dcDataInfoVO);
        ServiceManager.callJavaBeanService("SrDcDataBO","removedcDataInfo" ,map);
    }

    /**
     * 查询积分帐户类型
     *
     * @throws Exception
     *
     */
    public PageModel getRrLan(int pageIndex, int pageSize)
        throws Exception {

        //return bean.getRrLan(pageIndex, pageSize);
        
        Map map = new HashMap();
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));

        PageModel result = (PageModel)ServiceManager.callJavaBeanService("SrDcDataBO","getRrLan" ,map);
        return result;
    }

    public String getGlobalVar(String strGlobalName) throws Exception {
        HttpServletRequest req = RequestContext.getContext().getHttpRequest();
        GlobalVariableHelper helper = new GlobalVariableHelper(req);
        String strGlobal = helper.getVariable(strGlobalName);

        if (strGlobal != null) {
            return strGlobal;
        } else {
            return "0";
        }
    }
}
