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

        //���ò���,�Ѳ�����װ��HashMap��
        Map param = new HashMap();
        param.put("searchType", searchType);
        param.put("searchKeyword", searchKeyword);
        param.put("pageIndex",new Integer(pi));
        param.put("pageSize",new Integer(ps));

        //JavaBean��ʽ,����ServiceManager.callJavaBeanService(service , method , param)����
        PageModel pm = (PageModel)ServiceManager.callJavaBeanService("RCFamilyBO",
                "getRCFamilyInfo", param);

        return pm;
    }

    /**
     * ��Դ��������
     * @param rcFamilyVo
     * @return
     * @throws Exception
     */
    public boolean updateRCFamilyInfo(VO rcFamilyVo) throws Exception {
        Map param = new HashMap();
        param.put("vo", rcFamilyVo);
        Boolean retResult  = (Boolean)ServiceManager.callJavaBeanService("RCFamilyBO",
                "updateRCFamilyInfo", param);
        //  ˢ����صľ�̬���� RC_ORDER_FAMILYTYPE_ENTITY
        StaticAttrService staticService = new StaticAttrService();
        staticService.clearAttrDataCache("RC_ORDER_FAMILYTYPE_ENTITY");
        
        return retResult.booleanValue();
    }

    public String saveRCFamilyInfo(VO rcFamilyVo) throws Exception {
    	Map param = new HashMap();
        param.put("vo", rcFamilyVo);
        String retResult  = (String)ServiceManager.callJavaBeanService("RCFamilyBO",
                "saveRCFamilyInfo", param);

        // ˢ����صľ�̬���� RC_ORDER_FAMILYTYPE_ENTITY
        StaticAttrService staticService = new StaticAttrService();
        staticService.clearAttrDataCache("RC_ORDER_FAMILYTYPE_ENTITY");

        return retResult;
    }

    public int deleteRCFamilyInfo(String rcFamilyId) throws Exception {
    	Map param = new HashMap();
        param.put("rcFamilyId", rcFamilyId);
        Integer retResult  = (Integer)ServiceManager.callJavaBeanService("RCFamilyBO",
                "deleteRCFamilyInfo", param);

        //  ˢ����صľ�̬���� RC_ORDER_FAMILYTYPE_ENTITY
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
     * ����Ӫ����Դid����ѯ��Ӫ����Դ��Ӧ����Դģ���ѡ��״̬
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
     * ���ݼ���id��ѯ�ü����Ѿ�ѡ���״̬�ļ���
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
     * ���ݼ���id��ѯ�ü����ѡ��״̬�ļ���
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
     * ����Դ�������״̬��-1����Ҫ��������ݲ�������1�������ɹ���2����ü�¼�Ѿ����ڲ����ظ����룻
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
     * ɾ������״̬������ü����״̬�Ѿ�����������Դʵ��ʹ��������ɾ����
     * -1������ȱʧ
     * -2:��״̬�Ѿ�����������Դʵ��ʹ�ò���ɾ��
     * @param familyId
     * @param state
     * @return ɾ��������
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
        // ˢ�¾�̬����
        StaticAttrService service = new StaticAttrService();
        service.clearAttrDataCache("dc_rcNo_level");

        return retResult;
    }

    public String updateRcLevelDef(RcLevelDefVO vo) throws Exception {
    	Map param = new HashMap();
    	param.put("vo", vo);
    	String retResult  = (String)ServiceManager.callJavaBeanService("RcLevelDefBO",
                "updateRcLevelDef", param);

        // ˢ�¾�̬����
        StaticAttrService service = new StaticAttrService();
        service.clearAttrDataCache("dc_rcNo_level");

        return retResult;
    }

    /**
     * @ejb.interface-method view-type = "both"
     * ����levelId�޸��ֶ�,�޸ĵ��ֶΰ�����RC_LEVEL_NAME��LEVEL_COMMENTS��PRI_ID��RULE_STRING��is_lucky
     * @param vo
     * @return
     * @throws Exception
     */
    public String updateByLevelId(RcLevelDefVO vo) throws Exception {
    	Map param = new HashMap();
    	param.put("vo", vo);
    	String retResult  = (String)ServiceManager.callJavaBeanService("RcLevelDefBO",
                "updateByLevelId", param);

        // ˢ�¾�̬����
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

        // ˢ����صľ�̬���� RC_ORDER_FAMILYTYPE_ENTITY
        StaticAttrService staticService = new StaticAttrService();
        staticService.clearAttrDataCache("RC_ORDER_FAMILYTYPE_ENTITY");

        return retResult;
    }
}
