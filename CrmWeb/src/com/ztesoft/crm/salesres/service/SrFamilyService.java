package com.ztesoft.crm.salesres.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.staticdata.service.StaticAttrService;
import com.ztesoft.crm.salesres.vo.RcLevelDefVO;


public class SrFamilyService extends DictService {

    public PageModel getRCFamilyInfo(String searchType, String searchKeyword,
        int pi, int ps) throws Exception {

        //设置参数,把参数组装到HashMap中
        Map param = new HashMap();
        param.put("searchType", searchType);
        param.put("searchKeyword", searchKeyword);
        param.put("pageIndex",new Integer(pi));
        param.put("pageSize",new Integer(ps));

        //JavaBean方式,调用ServiceManager.callJavaBeanService(service , method , param)方法
        PageModel pm = (PageModel)ServiceManager.callJavaBeanService("RCFamilyBO",
                "getRCFamilyInfo", param);

        return pm;
    }

    /**
     * 资源家族增加
     * @param rcFamilyVo
     * @return
     * @throws Exception
     */
    public boolean updateRCFamilyInfo(VO rcFamilyVo) throws Exception {
        Map param = new HashMap();
        param.put("vo", rcFamilyVo);
        Boolean retResult  = (Boolean)ServiceManager.callJavaBeanService("RCFamilyBO",
                "updateRCFamilyInfo", param);
        //  刷新相关的静态数据 RC_ORDER_FAMILYTYPE_ENTITY
        StaticAttrService staticService = new StaticAttrService();
        staticService.clearAttrDataCache("RC_ORDER_FAMILYTYPE_ENTITY");
        
        return retResult.booleanValue();
    }

    public String saveRCFamilyInfo(VO rcFamilyVo) throws Exception {
    	Map param = new HashMap();
        param.put("vo", rcFamilyVo);
        String retResult  = (String)ServiceManager.callJavaBeanService("RCFamilyBO",
                "saveRCFamilyInfo", param);

        // 刷新相关的静态数据 RC_ORDER_FAMILYTYPE_ENTITY
        StaticAttrService staticService = new StaticAttrService();
        staticService.clearAttrDataCache("RC_ORDER_FAMILYTYPE_ENTITY");

        return retResult;
    }

    public int deleteRCFamilyInfo(String rcFamilyId) throws Exception {
    	Map param = new HashMap();
        param.put("rcFamilyId", rcFamilyId);
        Integer retResult  = (Integer)ServiceManager.callJavaBeanService("RCFamilyBO",
                "deleteRCFamilyInfo", param);

        //  刷新相关的静态数据 RC_ORDER_FAMILYTYPE_ENTITY
        StaticAttrService staticService = new StaticAttrService();
        staticService.clearAttrDataCache("RC_ORDER_FAMILYTYPE_ENTITY");

        return retResult.intValue();
    }

    public List qryRCFamilyAttrInfo(String familyId) throws Exception {
        Map param = new HashMap(1);
        param.put("familyId", familyId);
        List ls  = (List)ServiceManager.callJavaBeanService("RCFamilyBO",
                "qryRCFamilyAttrInfo", param);
        return ls;
    }

    public String insertRCFamilyAttrInfo(String attrValue,
        String attrOwnerType, String familyId) throws Exception {
    	Map param = new HashMap();
    	param.put("attrValue", attrValue);
    	param.put("attrOwnerType", attrOwnerType);
        param.put("familyId", familyId);
        String retResult  = (String)ServiceManager.callJavaBeanService("RCFamilyBO",
                "insertRCFamilyAttrInfo", param);

        return retResult;
    }

    public int deleteAttrInfo(String rcFamilyId, String attrId,
        String attrOwnerType) throws Exception {
    	Map param = new HashMap();
    	param.put("attrId", attrId);
    	param.put("attrOwnerType", attrOwnerType);
        param.put("rcFamilyId", rcFamilyId);
        Integer retResult  = (Integer)ServiceManager.callJavaBeanService("RCFamilyBO",
                "deleteAttrInfo", param);

        return retResult.intValue();
    }

    public PageModel findAttrInfo(String familyId, String attrOwnerType,
        int pageIndex, int pageSize) throws Exception {
        Map param = new HashMap();
        param.put("familyId", familyId);
        param.put("attrOwnerType", attrOwnerType);
        param.put("pageIndex",new Integer(pageIndex));
        param.put("pageSize",new Integer(pageSize));

        PageModel retResult  = (PageModel)ServiceManager.callJavaBeanService("RCFamilyBO",
                "findAttrInfo", param);
        return retResult;
    }

    public PageModel findAttrInfo2(String familyId, String attrOwnerType,
        String salesRescId, int pageIndex, int pageSize)
        throws Exception {
    	Map param = new HashMap();
        param.put("familyId", familyId);
        param.put("attrOwnerType", attrOwnerType);
        param.put("salesRescId", salesRescId);
        param.put("pageIndex",new Integer(pageIndex));
        param.put("pageSize",new Integer(pageSize));

        PageModel retResult  = (PageModel)ServiceManager.callJavaBeanService("RCFamilyBO",
                "findAttrInfo", param);
        return retResult;
    }

    public PageModel findAttrValue(String attrId, int pageIndex, int pageSize)
        throws Exception {
    	Map param = new HashMap();
        param.put("attrId", attrId);
        param.put("pageIndex",new Integer(pageIndex));
        param.put("pageSize",new Integer(pageSize));

        PageModel retResult  = (PageModel)ServiceManager.callJavaBeanService("RCFamilyBO",
                "findAttrValue", param);
        return retResult;
    }

    /**
     * 根据营销资源id，查询该营销资源对应的资源模板可选的状态
     * @param rescInstanceId
     * @return
     */
    public List qryEntityAvailState(String salesRescId)
        throws Exception {
    	Map param = new HashMap();
        param.put("salesRescId", salesRescId);

        List retResult  = (List)ServiceManager.callJavaBeanService("RCFamilyBO",
                "qryEntityAvailState", param);
        return retResult;
    }

    /**
     * 根据家族id查询该家族已经选择的状态的集合
     * @param familyId
     * @return
     */
    public List qryFamilyState(String familyId) throws Exception {
    	Map param = new HashMap();
        param.put("familyId", familyId);

        List retResult  = (List)ServiceManager.callJavaBeanService("RCFamilyBO",
                "qryFamilyState", param);
        return retResult;
    }

    /**
     * 根据家族id查询该家族可选的状态的集合
     * @param familyId
     * @return
     */
    public List qryFamilyAvailState(String familyId) throws Exception {
    	Map param = new HashMap();
    	param.put("familyId", familyId);
        List retResult  = (List)ServiceManager.callJavaBeanService("RCFamilyBO",
                "qryFamilyAvailState", param);
        return retResult;
    }

    /**
     * 给资源家族添加状态。-1代表要插入的数据不完整；1代表插入成功；2代表该纪录已经存在不能重复插入；
     * @param familyId
     * @param state
     * @return
     */
    public int insertFamilyState(String familyId, String stateStr)
        throws Exception {
    	Map param = new HashMap();
    	param.put("familyId", familyId);
    	param.put("stateStr", stateStr);
        Integer retResult  = (Integer)ServiceManager.callJavaBeanService("RCFamilyBO",
                "insertFamilyState", param);
        
        StaticAttrService staticService = new StaticAttrService();
        staticService.clearAttrDataCache("dc_no_state");
        staticService.clearAttrDataCache("dc_sim_state");

        return retResult.intValue();
    }

    /**
     * 删除家族状态，如果该家族的状态已经被所属的资源实例使用则不允许删除；
     * -1：参数缺失
     * -2:该状态已经被所属的资源实例使用不能删除
     * @param familyId
     * @param state
     * @return 删除的数量
     */
    public int delFamilyState(String familyId, String state)
        throws Exception {
    	Map param = new HashMap();
    	param.put("familyId", familyId);
    	param.put("state", state);
        Integer retResult  = (Integer)ServiceManager.callJavaBeanService("RCFamilyBO",
                "delFamilyState", param);
        StaticAttrService staticService = new StaticAttrService();
        staticService.clearAttrDataCache("dc_no_state");
        staticService.clearAttrDataCache("dc_sim_state");

        return retResult.intValue();
    }

    public List qryFamilyLevel(String familyId) throws Exception {
    	Map param = new HashMap();
    	param.put("familyId", familyId);
    	List retResult  = (List)ServiceManager.callJavaBeanService("RcLevelDefBO",
                "qryFamilyLevel", param);

        return retResult;
    }

    public List qryLevelFee(String rcLevelId) throws Exception {
    	Map param = new HashMap();
    	param.put("rcLevelId", rcLevelId);
    	List retResult  = (List)ServiceManager.callJavaBeanService("RcLevelDefBO",
                "qryLevelFee", param);

        return retResult;
    }

    public String addRcLevelDef(RcLevelDefVO vo) throws Exception {
    	Map param = new HashMap();
    	param.put("vo", vo);
    	String retResult  = (String)ServiceManager.callJavaBeanService("RcLevelDefBO",
                "addRcLevelDef", param);
        // 刷新静态数据
        StaticAttrService service = new StaticAttrService();
        service.clearAttrDataCache("dc_rcNo_level");

        return retResult;
    }

    public String updateRcLevelDef(RcLevelDefVO vo) throws Exception {
    	Map param = new HashMap();
    	param.put("vo", vo);
    	String retResult  = (String)ServiceManager.callJavaBeanService("RcLevelDefBO",
                "updateRcLevelDef", param);

        // 刷新静态数据
        StaticAttrService service = new StaticAttrService();
        service.clearAttrDataCache("dc_rcNo_level");

        return retResult;
    }

    /**
     * @ejb.interface-method view-type = "both"
     * 根据levelId修改字段,修改的字段包括：RC_LEVEL_NAME，LEVEL_COMMENTS，PRI_ID，RULE_STRING，is_lucky
     * @param vo
     * @return
     * @throws Exception
     */
    public String updateByLevelId(RcLevelDefVO vo) throws Exception {
    	Map param = new HashMap();
    	param.put("vo", vo);
    	String retResult  = (String)ServiceManager.callJavaBeanService("RcLevelDefBO",
                "updateByLevelId", param);

        // 刷新静态数据
        StaticAttrService service = new StaticAttrService();
        service.clearAttrDataCache("dc_rcNo_level");

        return retResult;
    }

    public String delRcLevelDef(RcLevelDefVO vo) throws Exception {
    	Map param = new HashMap();
    	param.put("vo", vo);
    	String retResult  = (String)ServiceManager.callJavaBeanService("RcLevelDefBO",
                "delRcLevelDef", param);

        return retResult;
    }

    public String checkRCFamilyInfo(String familyId) throws Exception {
    	Map param = new HashMap();
        param.put("familyId", familyId);
        String retResult  = (String)ServiceManager.callJavaBeanService("RCFamilyBO",
                "checkRCFamilyInfo", param);

        // 刷新相关的静态数据 RC_ORDER_FAMILYTYPE_ENTITY
        StaticAttrService staticService = new StaticAttrService();
        staticService.clearAttrDataCache("RC_ORDER_FAMILYTYPE_ENTITY");

        return retResult;
    }
}
