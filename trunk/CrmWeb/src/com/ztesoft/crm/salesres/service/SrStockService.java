package com.ztesoft.crm.salesres.service;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.security.auth.login.ConfigFile;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.component.common.staticdata.StaticAttrCache;
import com.ztesoft.component.common.staticdata.vo.StaticAttrVO;
import com.ztesoft.crm.salesres.bo.SrStockBo;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.vo.RcAppTypeVO;
import com.ztesoft.crm.salesres.vo.RcApplicationVO;
import com.ztesoft.crm.salesres.vo.RcAttrDefVO;
import com.ztesoft.crm.salesres.vo.RcEntityReportVO;
import com.ztesoft.crm.salesres.vo.RcFamilyVO;
import com.ztesoft.crm.salesres.vo.RcOrderAgentVO;
import com.ztesoft.crm.salesres.vo.RcOrderSegInfoVO;
import com.ztesoft.crm.salesres.vo.RcOrderVO;
import com.ztesoft.crm.salesres.vo.RcServAcceptVO;
import com.ztesoft.crm.salesres.vo.RcServReturnVO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class SrStockService extends DictService {
    private static Logger logger = Logger.getLogger(SrStockService.class);

    public SrStockService() {
    }

    /**
     * �ֶβ�ѯУ��
     * @param resBCode
     * @param resECode
     * @param preCode
     * @param postCode
     * @return
     */
    public String getSegInfo(String resBCode, String resECode, String preCode,
        String postCode, String storageId, String salesRescId) {
        SrStockBo bo = new SrStockBo();

        return bo.getSegInfo(resBCode, resECode, preCode, postCode, storageId,
            salesRescId);
    }

    /**
     * �Զ����������Դʵ��ID��ѯrc_order_agent���ظ���Դ�Ƿ���ʵ�壬�Լ���Ӧ�Ĵ�����ID
     * @param orderId
     * @param salesRescId
     * @return
     */
    public String[] getAgentInfo(String orderId, String salesRescId) {
        SrStockBo bo = new SrStockBo();

        return bo.getAgentInfo(orderId, salesRescId);
    }
//
//    /**
//     * ���ü��Žӿڣ��洢�������ն���Ϣ
//     * @param agentId
//     * @param salesId
//     * @param salesName
//     * @param salesBCode
//     * @param salesECode
//     * @param actionType
//     * @return
//     * @throws Exception
//     * @throws RemoteException
//     */
//    public String insertAgentInfo(String orderId, String agentId,
//        String salesId, String salesName, RcOrderSegInfoVO[] seg,
//        String actionType, String delType) throws RemoteException, Exception {
//SrStock",
//                SrStockHome.class);
//        HashMap map = new HashMap(6);
//        map.put("agentId", agentId);
//        map.put("salesId", salesId);
//        map.put("salesName", salesName);
//        map.put("seg", seg);
//        map.put("actionType", actionType);
//        map.put("orderId", orderId);
//        map.put("delType", delType);
//
//        return bean.insertAgentInfo(map);
//    }
//
    //******************begin xiangyuwen rc_order***************************
    public com.ztesoft.common.util.PageModel getStaffInfo_order(String sType,
        String sContent, String departId, int pageIndex, int pageSize)
        throws Exception {
        HashMap map = new HashMap();
        map.put("sType", sType);
        map.put("sContent", sContent);
        map.put("departId", departId);

        // SrStock",
        // SrStockHome.class);
        PageModel pageModel = null;
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));

        try {
            //pageModel = bean.getStaffInfo_order(map, pageIndex, pageSize);
            pageModel = (PageModel)ServiceManager.callJavaBeanService("SRSTOCKBO","getStaffInfo_order" ,map);
        } /*catch (RemoteException e) {
            // TODO Auto-generated catch block
            logger.error("SrStockService.querySrStockOffer():Զ���쳣");
            e.printStackTrace();
            throw e;
        }*/ catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("SrStockService.querySrStockOffer():��ͨ�쳣");
            e.printStackTrace();
            throw e;
        }

        return pageModel;
    }

//    /**
//     * ��ѯ�����б���Ϣ
//     * @param beginDate String
//     * @param endDate String
//     * @param appType String
//     * @param appStatus String
//     * @param appTache String
//     * @param appCode String
//     * @param gjselect String
//     * @param pageIndex int
//     * @param pageSize int
//     * @throws Exception
//     * @return PageModel
//     */
//    public com.ztesoft.common.util.PageModel getRcOrderInfo(String beginDate,
//        String endDate, String appType, String appStatus, String appTache,
//        String appCode, String inStorageId, String gjselect, int pageIndex,
//        int pageSize) throws Exception {
//        PageModel pageModel = null;
//        pageModel = this.getRcOrderInfo2(beginDate, endDate, appType,
//                appStatus, appTache, appCode, inStorageId, gjselect, "", "",
//                pageIndex, pageSize);
//
//        return pageModel;
//    }
//
//    /**
//     * ��ѯ�����б���Ϣ2
//     * @param beginDate String
//     * @param endDate String
//     * @param appType String
//     * @param appStatus String
//     * @param appTache String
//     * @param appCode String
//     * @param gjselect String
//     * @param pageIndex int
//     * @param pageSize int
//     * @throws Exception
//     * @return PageModel
//     */
//    public com.ztesoft.common.util.PageModel getRcOrderInfo2(String beginDate,
//        String endDate, String appType, String appStatus, String appTache,
//        String appCode, String inStorageId, String gjselect, String orderId,
//        String familyId, int pageIndex, int pageSize) throws Exception {
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        HashMap map = new HashMap();
//        map.put("departId", this.getGlobalVar(GlobalVariableHelper.DEPART_ID));
//        map.put("operId", operId);
//        map.put("beginDate", beginDate);
//        map.put("endDate", endDate);
//        map.put("appType", appType);
//        map.put("appStatus", appStatus);
//        map.put("appTache", appTache);
//        map.put("appCode", appCode);
//        map.put("inStorageId", inStorageId);
//        map.put("gjselect", gjselect);
//        map.put("orderId", orderId);
//        map.put("familyId", familyId);
//
//SrStock",
//                SrStockHome.class);
//        PageModel pageModel = null;
//
//        try {
//            pageModel = bean.getRcOrderInfo(map, pageIndex, pageSize);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.querySrStockOffer():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.querySrStockOffer():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return pageModel;
//    }
//
//    public List getOrderInfoList(String orderId) throws java.lang.Exception {
//SrStock",
//                SrStockHome.class);
//        HashMap prMap = new HashMap();
//        prMap.put("orderId", orderId);
//
//        List list = null;
//
//        try {
//            list = bean.getOrderInfoList(prMap);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return list;
//    }
//
//    /**
//     * ��ѯ�����ֶ���Ϣ
//    * @param orderId
//    * @param flag
//    * @return
//    */
//    public List getOrderSegInfo(String orderId, String flag) {
//        SrStockBo bo = new SrStockBo();
//
//        return bo.getOrderSegInfo(orderId, flag);
//    }
//
//    public List getOrderExcInfoList(String orderId) throws java.lang.Exception {
//SrStock",
//                SrStockHome.class);
//        HashMap prMap = new HashMap();
//        prMap.put("orderId", orderId);
//
//        List list = null;
//
//        try {
//            list = bean.getOrderExcInfoList(prMap);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return list;
//    }
//
//    public List getSalesResourceByFamilyType(String familyType)
//        throws java.lang.Exception {
//SrStock",
//                SrStockHome.class);
//        HashMap prMap = new HashMap();
//        prMap.put("familyType", familyType);
//
//        List list = null;
//
//        try {
//            list = bean.getSalesResourceByFamilyType(prMap);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return list;
//    }
//
//    public String saveRCAppOrder(RcApplicationVO rcApplicationVO,
//        RcOrderVO[] objArray) throws java.lang.Exception {
//SrStock",
//                SrStockHome.class);
//
//        String retv = "";
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        List rcOrderList = new ArrayList();
//
//        try {
//            rcApplicationVO.setAppTime(DateFormatUtils.getFormatedDateTime());
//            rcApplicationVO.setOperId(operId);
//            rcApplicationVO.setDepartId(this.getGlobalVar(
//                    GlobalVariableHelper.DEPART_ID));
//
//            if ((objArray != null) && (objArray.length > 0)) {
//                int len = objArray.length;
//
//                for (int i = 0; i < len; i++) {
//                    RcOrderVO rcOrderVO = (RcOrderVO) objArray[i];
//                    rcOrderVO.setAppType(rcApplicationVO.getAppType());
//                    rcOrderVO.setStateId("n"); //��ʾ������̬;
//                    rcOrderVO.setOperId(operId);
//                    rcOrderVO.setDepartId(this.getGlobalVar(
//                            GlobalVariableHelper.DEPART_ID));
//                    rcOrderVO.setTacheTime(DateFormatUtils.getFormatedDateTime());
//                    rcOrderVO.setAppTime(DateFormatUtils.getFormatedDateTime());
//
//                    if ("submit".equals(rcOrderVO.getStatus())) {
//                        rcOrderVO.setTacheId("2");
//                    }
//
//                    rcOrderList.add(rcOrderVO);
//                }
//            }
//
//            retv = bean.saveRCAppOrder(rcApplicationVO, rcOrderList, operId);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return retv;
//    }
//
//    public String updateRCAppOrder(RcApplicationVO rcApplicationVO,
//        RcOrderVO[] objArray) throws java.lang.Exception {
//SrStock",
//                SrStockHome.class);
//
//        String retv = "";
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        String ipStr = (this.getRequest() == null) ? ""
//                                                   : (this.getRequest()
//                                                          .getRemoteAddr());
//        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
//        List rcOrderList = new ArrayList();
//
//        try {
//            //rcApplicationVO.setAppTime(DateFormatUtils.getFormatedDateTime());
//            //rcApplicationVO.setOperId(operId);
//            //rcApplicationVO.setDepartId(this.getGlobalVar(GlobalVariableHelper.DEPART_ID));
//            if ((objArray != null) && (objArray.length > 0)) {
//                int len = objArray.length;
//
//                for (int i = 0; i < len; i++) {
//                    RcOrderVO rcOrderVO = (RcOrderVO) objArray[i];
//                    rcOrderVO.setAppType(rcApplicationVO.getAppType());
//                    rcOrderVO.setStateId("n"); //��ʾ������̬;
//                    rcOrderVO.setOperId(operId);
//                    rcOrderVO.setDepartId(departId);
//                    rcOrderVO.setTacheTime(DateFormatUtils.getFormatedDateTime());
//                    rcOrderVO.setAppTime(DateFormatUtils.getFormatedDateTime());
//
//                    if ("submit".equals(rcOrderVO.getStatus())) {
//                        rcOrderVO.setTacheId("2");
//                    }
//
//                    rcOrderList.add(rcOrderVO);
//                }
//            }
//
//            retv = bean.updateRCAppOrder(rcApplicationVO, rcOrderList, operId,
//                    ipStr);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return retv;
//    }
//
//    public List getStorageInfoByDepartIdList() throws java.lang.Exception {
//SrStock",
//                SrStockHome.class);
//        HashMap prMap = new HashMap();
//
//        //String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
//        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
//        prMap.put("departId", departId);
//
//        List list = null;
//
//        try {
//            list = bean.getStorageInfoByDepartIdList(prMap);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return list;
//    }
//
//    public List getRcApplicationDataByAppId(String appId)
//        throws java.lang.Exception {
//SrStock",
//                SrStockHome.class);
//        HashMap prMap = new HashMap();
//        prMap.put("appId", appId);
//
//        List list = null;
//
//        try {
//            list = bean.getRcApplicationDataByAppId(prMap);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return list;
//    }
//
//    public List getRcOrderDataByAppId(String appId) throws java.lang.Exception {
//SrStock",
//                SrStockHome.class);
//        HashMap prMap = new HashMap();
//        prMap.put("appId", appId);
//
//        List list = null;
//
//        try {
//            list = bean.getRcOrderDataByAppId(prMap);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return list;
//    }
//
//    public int repealRCAppOrder(String orderIdArr) throws java.lang.Exception {
//SrStock",
//                SrStockHome.class);
//        HashMap prMap = new HashMap();
//        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        String ipStr = (this.getRequest() == null) ? ""
//                                                   : (this.getRequest()
//                                                          .getRemoteAddr());
//        int retv = 0;
//
//        try {
//            retv = bean.repealRCAppOrder(orderIdArr, operId, departId, ipStr);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return retv;
//    }
//
//    public int submitRCAppOrder(String orderIdArr) throws java.lang.Exception {
//SrStock",
//                SrStockHome.class);
//        HashMap prMap = new HashMap();
//        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        String ipStr = (this.getRequest() == null) ? ""
//                                                   : (this.getRequest()
//                                                          .getRemoteAddr());
//
//        int retv = 0;
//
//        try {
//            retv = bean.submitRCAppOrder(orderIdArr, operId, departId, ipStr);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return retv;
//    }
//
//    /**
//     * ���ݲ���id��Ӫ����Դid���ַ�����ѯ�ò��������ֿ���Դ�Ŀ��
//     * @param deptID
//     * @param saleIds
//     * @return
//     */
//    public PageModel queryStockNum(String lanId, String regionId,
//        String deptID, String saleIds, String upStorageId, String storageId,
//        int pi, int ps) throws Exception {
//SrStock",
//                SrStockHome.class);
//        Map map = new HashMap();
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
//        // ����ϵͳ�ڽ��涼ֻ���ֿ��ѯ�����ٰ�Ӫҵ�����Ų�ѯ
//        deptID = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
//
//        map.put("operId", operId);
//
//        if ((lanId != null) && (lanId.trim().length() > 0)) {
//            map.put("lanId", lanId);
//        }
//
//        if ((regionId != null) && (regionId.trim().length() > 0)) {
//            map.put("regionId", regionId);
//        }
//
//        if ((deptID != null) && (deptID.trim().length() > 0)) {
//            map.put("deptID", deptID);
//        }
//
//        if ((saleIds != null) && (saleIds.trim().length() > 0)) {
//            map.put("saleIds", saleIds);
//        }
//
//        if ((upStorageId != null) && (upStorageId.trim().length() > 0)) {
//            map.put("upStorageId", upStorageId);
//        }
//
//        if ((storageId != null) && (storageId.trim().length() > 0)) {
//            map.put("storageId", storageId);
//        }
//
//        return bean.queryStockNum(map, pi, ps);
//    }
//
//    public RcAppTypeVO getRcAppType(String appTypeId)
//        throws java.lang.Exception {
//SrStock",
//                SrStockHome.class);
//        RcAppTypeVO retVO = new RcAppTypeVO();
//
//        try {
//            retVO = bean.getRcAppType(appTypeId);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return retVO;
//    }
//
//    /**
//     * ��ȡȫ�ֱ���(��½��Ϣ)
//     *
//     * @param strGlobalName
//     * @return
//     * @throws Exception
//     */
//    public String getGlobalVar(String strGlobalName) throws Exception {
//        GlobalVariableHelper helper = new GlobalVariableHelper(getRequest());
//        String strGlobal = helper.getVariable(strGlobalName);
//
//        if (strGlobal != null) {
//            return strGlobal;
//        } else {
//            return "0";
//        }
//    }
//
//    //************************end****************************************
//
//    /**
//     * �����ĳ���������
//     * @param vo��vo�е�orderId��tacheId��stateId��salesRescId��actAmount��comments����Ϊ��
//     * @return -1����������д��󣬿�����ȱ�����ϲ���֮һ
//     * 1:�ɹ���ɲ�������
//     * 2:�ö����Ѿ���������
//     * 3:�ö����Ļ����Ѿ��ı䣬�������ٲ���
//     * 4:����Աû��Ȩ�޲�����������������ֿ�
//     * 5:��ת����ʱʧ��
//     * 6:��治�㣬���¿��ʧ��
//     * 7:���¿�����ʧ��
//     */
//    public String inOutStock(RcOrderVO vo,
//        com.ztesoft.crm.sr.vo2.RcEntityVO eneityVO, RcAttrDefVO[] objArray)
//        throws Exception {
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
//        String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);
//SrStock",
//                SrStockHome.class);
//        vo.setOperId(operId);
//        vo.setDepartId(departId);
//        vo.setLanId(lanId);
//
//        String ip = (this.getRequest() == null) ? ""
//                                                : (this.getRequest()
//                                                       .getRemoteAddr());
//        // ��װʵ��VO
//        vo.setEntityVO(eneityVO);
//
//        // ��װʵ��������Ϣ
//        if ((objArray != null) && (objArray.length > 0)) {
//            List attrList = new ArrayList();
//
//            for (int i = 0; i < objArray.length; i++) {
//                RcAttrDefVO attrVO = (RcAttrDefVO) objArray[i];
//
//                if (attrVO != null) {
//                    attrList.add(attrVO);
//                }
//            }
//
//            if ((vo != null) && (attrList != null) && (attrList.size() > 0)) {
//                vo.setAttrList(attrList);
//            }
//        }
//
//        return bean.inOutStock(vo, ip);
//    }
//
//    /**
//     * ���ݶ������Ͳ��Ҹ����Ͷ����������ֿ�������ֿ��Ƿ�Ϊ��
//     * @param appType
//     * @return Map: ����:appType:��������
//     * inStorage��1Ϊ��Ҫָ����0Ϊ����Ҫָ��
//     * outStorage��1Ϊ��Ҫָ����0Ϊ����Ҫָ��
//     */
//    public Map qryInOutSetting(String appType) throws Exception {
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryInOutSetting(appType);
//    }
//
//    /**
//     * ���ݴ����Ķ��������ַ�����ѯ�����б�,���ҰѲ�Ĵ�ӡ�б���Ϣ����request��
//     * @param orderIds:��ʽΪid1,id2,id3
//     * @return
//     */
//    public List qryPrintOrders(String orderIds) throws Exception {
//SrStock",
//                SrStockHome.class);
//        List listRtn = bean.qryPrintOrders(orderIds);
//
//        return listRtn;
//    }
//
//    public int checkPassRCAppOrder(RcOrderVO[] objArray)
//        throws java.lang.Exception {
//SrStock",
//                SrStockHome.class);
//        HashMap prMap = new HashMap();
//        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
//        String ipStr = (this.getRequest() == null) ? ""
//                                                   : (this.getRequest()
//                                                          .getRemoteAddr());
//        RcOrderVO rcOrderVO = null;
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        int retv = 0;
//
//        try {
//            if ((objArray != null) && (objArray.length > 0)) {
//                List rcOrderList = new ArrayList();
//                int len = objArray.length;
//
//                for (int i = 0; i < len; i++) {
//                    rcOrderVO = (RcOrderVO) objArray[i];
//                    rcOrderList.add(rcOrderVO);
//                }
//
//                retv = bean.checkPassRCAppOrder(rcOrderList, operId, departId,
//                        ipStr);
//            }
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():Զ���쳣");
//            e.printStackTrace();
//            throw e;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            logger.error("SrStockService.test():��ͨ�쳣");
//            e.printStackTrace();
//            throw e;
//        }
//
//        return retv;
//    }
//
//    public PageModel getOrderInfoByOrderId_Excel(String orderIdStr, int pi,
//        int ps) throws Exception {
//SrStock",
//                SrStockHome.class);
//
//        return bean.getOrderInfoByOrderId_Excel(orderIdStr, pi, ps);
//    }
//
//    public List getOrderInfoByOrderId_Print(String orderIdStr)
//        throws Exception {
//SrStock",
//                SrStockHome.class);
//        String[] arr = orderIdStr.split(",");
//        int ps = 100;
//
//        if ((arr != null) && (arr.length > 0)) {
//            ps = arr.length;
//        }
//
//        PageModel pm = getOrderInfoByOrderId_Excel(orderIdStr, 1, ps);
//        List retList = pm.getList();
//
//        return retList;
//    }
//
//    public String getFamilyNameBySalesResourceId(String saleResourceId)
//        throws Exception {
//SrStock",
//                SrStockHome.class);
//        List listRtn = bean.getSalesResourceBySalesResourceId(saleResourceId);
//        String retv = "";
//
//        if ((listRtn != null) && (listRtn.size() > 0)) {
//            SalesRescVO vo = (SalesRescVO) listRtn.get(0);
//            retv = vo.getFamilyName();
//        }
//
//        return retv;
//    }
//
//    /////////////////////////////////////////////////////////////////////////////////////////////
//    /**
//     * ��ѯ������־
//     * @param departIds:
//     * @param salesRescIds:
//     * @param rescInstance2:
//     * @param beginDate:
//     * @param endDate:
//     * @param pageIndex
//     * @param pageSize
//     * @return
//     * @throws Exception
//     */
//    public PageModel qryRcSaleLog(String storageId, String salesRescIds,
//        String rescInstance2, String beginDate, String endDate, String prodNo,
//        int pageIndex, int pageSize) throws Exception {
//        Map map = new HashMap();
//
//        if ((storageId != null) && (storageId.trim().length() > 0)) {
//            map.put("storageId", storageId);
//        }
//
//        if ((salesRescIds != null) && (salesRescIds.trim().length() > 0)) {
//            map.put("salesRescIds", salesRescIds);
//        }
//
//        if ((rescInstance2 != null) && (rescInstance2.trim().length() > 0)) {
//            map.put("rescInstance2", rescInstance2);
//        }
//
//        if ((beginDate != null) && (beginDate.trim().length() > 0)) {
//            map.put("beginDate", beginDate);
//        }
//
//        if ((endDate != null) && (endDate.trim().length() > 0)) {
//            map.put("endDate", endDate);
//        }
//
//        if ((prodNo != null) && (prodNo.trim().length() > 0)) {
//            map.put("prodNo", prodNo);
//        }
//
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryRcSaleLog(map, pageIndex, pageSize);
//    }
//
//    /**
//     * ͳ��ʵ���ն��������
//     * @param lanIds:
//     * @param salesRescIds:
//     * @param beginDate:
//     * @param endDate:
//     * @param pageIndex
//     * @param pageSize
//     * @return
//     * @throws Exception
//     */
//    public PageModel satEntitySaleLog(String lanIds, String salesRescIds,
//        String beginDate, String endDate, int pageIndex, int pageSize)
//        throws Exception {
//        Map map = new HashMap();
//
//        if ((lanIds != null) && (lanIds.trim().length() > 0)) {
//            map.put("lanIds", lanIds);
//        }
//
//        if ((salesRescIds != null) && (salesRescIds.trim().length() > 0)) {
//            map.put("salesRescIds", salesRescIds);
//        }
//
//        if ((beginDate != null) && (beginDate.trim().length() > 0)) {
//            map.put("beginDate", beginDate);
//        }
//
//        if ((endDate != null) && (endDate.trim().length() > 0)) {
//            map.put("endDate", endDate);
//        }
//
//        SrStockBo bo = new SrStockBo();
//
//        return bo.satEntitySaleLog(map, pageIndex, pageSize);
//    }
//
//    /**
//     * ͳ����Ʒ�������
//     * @param departIds:
//     * @param salesRescIds:
//     * @param beginDate:
//     * @param endDate:
//     * @param pageIndex
//     * @param pageSize
//     * @return
//     * @throws Exception
//     */
//    public PageModel satSaleLog(String departIds, String salesRescIds,
//        String beginDate, String endDate, int pageIndex, int pageSize)
//        throws Exception {
//        Map map = new HashMap();
//
//        if ((departIds != null) && (departIds.trim().length() > 0)) {
//            map.put("departIds", departIds);
//        }
//
//        if ((salesRescIds != null) && (salesRescIds.trim().length() > 0)) {
//            map.put("salesRescIds", salesRescIds);
//        }
//
//        if ((beginDate != null) && (beginDate.trim().length() > 0)) {
//            map.put("beginDate", beginDate);
//        }
//
//        if ((endDate != null) && (endDate.trim().length() > 0)) {
//            map.put("endDate", endDate);
//        }
//
//SrStock",
//                SrStockHome.class);
//
//        return bean.satSaleLog(map, pageIndex, pageSize);
//    }
//
//    /**
//     * ���ݶ��������ж��������͵Ķ��������ǰ�Ļ����Ƿ��ǳ��⻷�ڡ�
//     * @param appType String
//     * @return boolean true:�ǳ��⻷�ڣ�false:���ǳ��⻷��
//     */
//    public boolean isOutBeforeInStorage(String appType)
//        throws Exception {
//SrStock",
//                SrStockHome.class);
//
//        return bean.isOutBeforeInStorage(appType);
//    }
//
//    /**
//     * ����Ӫ����Դid��ѯ�������ļ����ʵ�����Լ�
//     * @param salesRescId String
//     * @return List
//     */
//    public List qryEntityAttrInfoBySalesResc(String salesRescId)
//        throws Exception {
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryEntityAttrInfoBySalesResc(salesRescId);
//    }
//
//    /**
//     * ��ҳ�����,�����߼����ݰ���:
//     * 1. �����ʼ����ͽ�������֮���Ƿ��ʵ��������ͬ,���ӱ��뱾��������
//     * 2. ����ǳ���������������ֱ��ͨ�������еļ�����������
//     * 3. ������ǰ�Ĳ��費�ǳ���,����֤����ʼ�������ֹ����֮���Ƿ��Ѿ�����ʵ����������ڣ������
//     * 4. ������ǰ�ǳ���˵��ʵ���Ѿ�����,��֤��ʼ����ֹ����֮����Ѵ��ڵ�ʵ�������Ƿ��Ҫ������һ�£����һ�»�Ҫ��֤�Ƿ��в�����״̬��ʵ��
//     * @param appType String
//     * @param tacheId String
//     * @param resBCode String
//     * @param resECode String
//     * @param actAmount String
//     * @return Map:result:0Ϊʧ�ܣ�1Ϊ�ɹ�
//     * info:������Ϣ
//     */
//    public Map checkEntityInOut(RcOrderVO vo) throws Exception {
//        Map map = new HashMap();
//SrStock",
//                SrStockHome.class);
//
//        if ((vo.getResBCode() != null) &&
//                (vo.getResBCode().trim().length() > 0) &&
//                (vo.getResECode() != null) &&
//                (vo.getResECode().trim().length() > 0)) {
//            map = this.checkEntityCodeNum(vo.getResBCode(), vo.getResECode(),
//                    vo.getActAmount());
//
//            if ((map != null) && (map.get("result") != null)) {
//                String result = (String) map.get("result");
//
//                if ((result != null) && "1".equals(result)) {
//                    map = bean.checkEntityInStorage(vo);
//                }
//            }
//        } else {
//            map = bean.checkEntityInStorage(vo);
//        }
//
//        return map;
//    }
//
//    /**
//     * �����ʼ����ͽ�������֮���Ƿ��ʵ��������ͬ
//     * @param resBCode String
//     * @param resECode String
//     * @param actAmount String
//     * @return Map:result:0Ϊʧ�ܣ�1Ϊ�ɹ�
//     * info:������Ϣ
//     */
//    public Map checkEntityCodeNum(String resBCode, String resECode,
//        String actAmount) {
//        Map map = new HashMap();
//
//        if ((resBCode == null) || (resBCode.trim().length() < 1) ||
//                (resECode == null) || (resECode.trim().length() < 1) ||
//                (actAmount == null) || (actAmount.trim().length() < 1)) {
//            map.put("result", "0");
//            map.put("info", "ȱ�ٲ���");
//
//            return map;
//        }
//
//        // �ж���ʼ����ͽ�������֮���Ƿ��ʵ��������ͬ
//        BigDecimal actAmountNum = new BigDecimal(actAmount);
//        BigDecimal resBCodeNum = new BigDecimal(resBCode);
//        BigDecimal resECodeNum = new BigDecimal(resECode);
//        BigDecimal subNum = (resECodeNum.subtract(resBCodeNum)).add(new BigDecimal(
//                    "1"));
//
//        if (actAmountNum.compareTo(subNum) == 1) {
//            map.put("result", "0");
//            map.put("info", "��ʼ�������ֹ����֮���ʵ����С����Ҫ�ĳ�������������߱�����ͬ!");
//        } else if (actAmountNum.compareTo(subNum) == -1) {
//            map.put("result", "0");
//            map.put("info", "��ʼ�������ֹ����֮���ʵ����������Ҫ�ĳ�������������߱�����ͬ!");
//        } else {
//            map.put("result", "1");
//            map.put("info", "");
//        }
//
//        return map;
//    }
//
//    ////////////////////////////���������/////////////////////////////////////////
//    /**
//     * ���ݳ���ֿ��ѯ��������Ϣ
//     * @param map Map:�������outStorageId��inStorageId
//     * @param pi int
//     * @param ps int
//     * @return PageModel
//     */
//    public PageModel qryAgentByStorage(String outStorageId, String inStorageId,
//        int pi, int ps) throws Exception {
//        Map map = new HashMap();
//
//        if ((outStorageId != null) && (outStorageId.trim().length() > 0)) {
//            map.put("outStorageId", outStorageId);
//        }
//
//        if ((inStorageId != null) && (inStorageId.trim().length() > 0)) {
//            map.put("inStorageId", inStorageId);
//        }
//
//        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
//        map.put("provId", provId);
//
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryAgentByStorage(map, pi, ps);
//    }
//
//    /**
//     * ����������ѯ����Ѻ����Ϣ
//     * @param appId String
//     * @param orderId String
//     * @return RcOrderAgentVO
//     */
//    public RcOrderAgentVO qryOrderAgentByPk(String appId, String orderId)
//        throws Exception {
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryOrderAgentByPk(appId, orderId);
//    }
//
//    /**
//     * ���ݶ�����Ϣ����Ѻ�����˲���
//     * @param vo��vo�е�orderId��tacheId��stateId��operId��acceptId��departId����Ϊ��
//     * @return -1����������д��󣬿�����ȱ�����ϲ���֮һ
//     * 1:�ɹ���ɲ�������
//     * 2:�ö����Ѿ���������
//     * 3:�ö����Ļ����Ѿ��ı䣬�������ٲ���
//     * 5:��ת����ʱʧ��
//     * @return String
//     */
//    public String orderAgentSubmit(RcOrderVO vo) throws Exception {
//SrStock",
//                SrStockHome.class);
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
//        vo.setOperId(operId);
//        vo.setDepartId(departId);
//
//        String ip = (this.getRequest() == null) ? ""
//                                                : (this.getRequest()
//                                                       .getRemoteAddr());
//        vo.setReworkIp(ip);
//
//        return bean.orderAgentSubmit(vo);
//    }
//
//    ///////////////////////// ͳ�ƴ������Ϣ�ķ��� //////////////////////////////
//    /**
//     * ��Դʵ��������־��ѯ������״̬Ϊ����
//     * @param rescInstanceCode String
//     * @param salesRescId String
//     * @return List
//     */
//    public List qryRescInOutLog(String rescInstanceCode, String salesRescId)
//        throws Exception {
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryRescInOutLog(rescInstanceCode, salesRescId);
//    }
//
//    /**
//     * ͳ�ƴ��������
//     * @param hashMap HashMap
//     * @param pi int
//     * @param ps int
//     * @return PageModel
//     */
//    public PageModel satRescInOutAmount(String lanId, String regionId,
//        String storageId, String departId, String familyId, String salesRescId,
//        String beginDate, String endDate, int pageIndex, int pageSize)
//        throws Exception {
//        Map map = new HashMap();
//
//        if ((lanId != null) && (lanId.trim().length() > 0)) {
//            map.put("lanId", lanId);
//        }
//
//        if ((regionId != null) && (regionId.trim().length() > 0)) {
//            map.put("regionId", regionId);
//        }
//
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//
//        if (operId != null) {
//            map.put("operId", operId);
//        }
//
//        if (storageId != null) {
//            map.put("storageId", storageId);
//        }
//
//        if (departId != null) {
//            map.put("departId", departId);
//        }
//
//        if (familyId != null) {
//            map.put("familyId", familyId);
//        }
//
//        if (salesRescId != null) {
//            map.put("salesRescId", salesRescId);
//        }
//
//        if (beginDate != null) {
//            map.put("beginDate", beginDate);
//        }
//
//        if (endDate != null) {
//            map.put("endDate", endDate);
//        }
//
//        map.put("provId", this.getGlobalVar(GlobalVariableHelper.PROV_ID));
//
//SrStock",
//                SrStockHome.class);
//
//        return bean.satRescInOutAmount(map, pageIndex, pageSize);
//    }
//
//    public PageModel qryOrderBill(String lanId, String regionId,
//        String storageId, String departId, String familyId, String salesRescId,
//        String beginDate, String endDate, String type, int pageIndex,
//        int pageSize) throws Exception {
//        Map map = new HashMap();
//
//        if ((lanId != null) && (lanId.trim().length() > 0)) {
//            map.put("lanId", lanId);
//        }
//
//        if ((regionId != null) && (regionId.trim().length() > 0)) {
//            map.put("regionId", regionId);
//        }
//
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//
//        if (operId != null) {
//            map.put("operId", operId);
//        }
//
//        if (storageId != null) {
//            map.put("storageId", storageId);
//        }
//
//        if (departId != null) {
//            map.put("departId", departId);
//        }
//
//        if (familyId != null) {
//            map.put("familyId", familyId);
//        }
//
//        if (salesRescId != null) {
//            map.put("salesRescId", salesRescId);
//        }
//
//        if (beginDate != null) {
//            map.put("beginDate", beginDate);
//        }
//
//        if (endDate != null) {
//            map.put("endDate", endDate);
//        }
//
//        if (type != null) {
//            map.put("type", type);
//        }
//
//        map.put("provId", this.getGlobalVar(GlobalVariableHelper.PROV_ID));
//
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryOrderBill(map, pageIndex, pageSize);
//    }
//
//    /**
//    * ���ݲ��Ų���Ӫҵ���������Ҷ�Ӧ��Ӫ����Դ�ļ۸񣬳������õ���ֵ
//    * @param departId String
//    * @param salesRescId String
//    * @param amount double
//    * @return double
//    */
//    public String computeSalesRescValue(String departId, String salesRescId,
//        String amount) throws Exception {
//        double amountDouble = Double.parseDouble(amount);
//SrStock",
//                SrStockHome.class);
//        double result = bean.computeSalesRescValue(departId, salesRescId,
//                amountDouble);
//
//        return String.valueOf(result);
//    }
//
//    /**
//    * ͨ��APP TYPE �õ�IN STYLE��OUT STYLE��ֵ
//    */
//    public String getAppTypeStyle(String appType) throws Exception {
//        Map map = new HashMap();
//        map.put("appType", appType);
//
//SrStock",
//                SrStockHome.class);
//        Map map2 = SD.getAppTypeStyle(map);
//        System.out.println(map2);
//
//        String str = "";
//
//        if (map2 != null) {
//            str = map2.get("IN_STYLE") + " " + map2.get("OUT_STYLE");
//        }
//
//        return str;
//    }
//
//    /**
//     * ͨ��STYLE��ȷ����ѯ�ֿ������������
//     * @param style String
//     * @throws Exception
//     * @return List
//     */
//    public List getStorageInfoByStyle(String style) throws Exception {
//        String operId = "";
//        String departId = "";
//        Map map = null;
//
//        if ((style == null) || style.equals("")) {
//            return new ArrayList();
//        } else if (style.equals("o") || style.equals("i")) {
//            map = new HashMap();
//            operId = getGlobalVar("vg_oper_id");
//            departId = getGlobalVar("vg_depart_id");
//            map.put("operId", operId);
//            map.put("departId", departId);
//            map.put("lanId", getGlobalVar("vg_lan_id"));
//            map.put("regionId", null);
//        } else if (style.equals("m")) {
//        }
//
//        // System.out.println(getGlobalVar("vg_lan_id")+" ????? "+new GlobalVariableHelper(getRequest()).getVariableFromSession2("business_id"));
//SrStock",
//                SrStockHome.class);
//        List list = sd.getStorageInfoByStyle(map);
//
//        return list;
//    }
//
//    /**
//     *oper_id����½���ţ�
//     depart_id����½���ţ�
//     * @param vo RcOrderVO
//     * @throws Exception
//     * @return String
//     */
//    public String inOutStock_HENAN(RcOrderVO paramObj, String manageModel,
//        List attrArrays, String byFile, String effDate, String expDate)
//        throws Exception {
//        HashMap map = new HashMap();
//        String operId = getGlobalVar("vg_oper_id");
//        String departId = getGlobalVar("vg_depart_id");
//        String lanId = getGlobalVar("vg_lan_id");
//
//        if (paramObj == null) {
//            return "";
//        }
//
//        paramObj.setOperId(operId);
//        paramObj.setDepartId(departId);
//        map.put("manageMode", manageModel); //����ģʽ
//        map.put("lanId", lanId);
//        map.put("attrList", attrArrays); //ʵ������ȡֵ
//        map.put("byFile", byFile); //�Ƿ����ļ��ϴ��ı�־
//        map.put("effDate", effDate); //ʵ����Чʱ��
//        map.put("expDate", expDate); //ʵ��ʧЧʱ��
//
//        String str = "";
//
//        try {
//SrStock",
//                    SrStockHome.class);
//            str = sd.inOutStock(paramObj, map);
//
//            if (str.equals("-1")) {
//                str = "ϵͳ���������޷��õ���Ч����";
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//
//            String[] strs = ex.getMessage().split("---");
//            str = strs[strs.length - 1];
//        }
//
//        return str;
//    }
//
//    /**
//     * ��ѯ���붩���Ŷ�Ӧ�Ķ�����Ϊ��ѯ���˶��������
//     * @param orderId String
//     * @throws Exception
//     * @return RcOrderVO
//     */
//    public RcOrderVO qryBackOrder(String orderId) throws Exception {
//        Map map = new HashMap();
//        map.put("orderId", orderId);
//
//        String operId = getGlobalVar(GlobalVariableHelper.OPER_ID);
//        map.put("operId", operId);
//
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryBackOrder(map);
//    }
//
//    /**
//     * ���Ҵ����̶���ĳ��Ӫ����Դ��������ȡ��������
//     * @param departId String
//     * @param salesRescId String
//     * @return long
//     */
//    public String qryLeftTakeResNum(String departId, String salesRescId)
//        throws Exception {
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryLeftTakeResNum(departId, salesRescId);
//    }
//
//    /**
//     * ���ϱ��ط���
//     * @param orderId String
//     * @throws Exception
//     * @return String
//     */
//    public String deleteOrder_HENAN(String orderId) throws Exception {
//SrStock",
//                SrStockHome.class);
//
//        return bean.deleteOrder_HENAN(orderId);
//    }
//
//    /**
//     * ���ش����������õ������ӵ�ַweb��ַ����
//     * http://localhost:7002/CsssWeb/servlet/LoginServlet?projectName=other&staffCode=yccs03&passwd=lueSGJZetyySpUndWjMBEg==
//     * @return
//     * @throws Exception
//     */
//    public String getCoopLinkAddr() throws Exception {
//        ConfigFile config = new ConfigFile();
//        String addr = config.getProperty(ConfigFile.ConfigFileName,
//                ConfigFile.HostWebName);
//        String operCode = getGlobalVar(GlobalVariableHelper.OPER_CODE);
//        String passWD = getGlobalVar(GlobalVariableHelper.PASSWORD);
//
//        //if(passWD==null||passWD.trim().length()<1)
//        //{
//SrDepart",
//                SrDepartHome.class);
//        Map ret = bean.qryOperatorByCode(operCode);
//
//        if ((ret != null) && (ret.get("PASSWORD") != null)) {
//            passWD = (String) ret.get("PASSWORD");
//        }
//
//        //}
//        addr += ("&staffCode=" + operCode + "&passwd=" + passWD);
//        System.out.println("���ô�����ҳ���ַ��" + addr);
//
//        return addr;
//    }
//
//    /**
//     * ��ѯ��涩������
//     * @param regionId
//     * @param familyId
//     * @param salesResourceIDs
//     * @param fromStorageId
//     * @param toStorageId
//     * @param orderId
//     * @param beginDate
//     * @param endDate
//     * @param pageIndex
//     * @param pageSize
//     * @return
//     * @throws Exception
//     */
//    public PageModel qryRcOrderReport(String regionId, String familyId,
//        String salesResourceIDs, String outStorageId, String inStorageId,
//        String orderId, String beginDate, String endDate, int pageIndex,
//        int pageSize) throws Exception {
//        Map map = new HashMap();
//
//        if ((regionId != null) && (regionId.trim().length() > 0)) {
//            map.put("regionId", regionId);
//        }
//
//        if ((familyId != null) && (familyId.trim().length() > 0)) {
//            map.put("familyId", familyId);
//        }
//
//        if ((salesResourceIDs != null) &&
//                (salesResourceIDs.trim().length() > 0)) {
//            map.put("salesResourceIDs", salesResourceIDs);
//        }
//
//        if ((outStorageId != null) && (outStorageId.trim().length() > 0)) {
//            map.put("outStorageId", outStorageId);
//        }
//
//        if ((inStorageId != null) && (inStorageId.trim().length() > 0)) {
//            map.put("inStorageId", inStorageId);
//        }
//
//        if ((orderId != null) && (orderId.trim().length() > 0)) {
//            map.put("orderId", orderId);
//        }
//
//        if ((beginDate != null) && (beginDate.trim().length() > 0)) {
//            map.put("beginDate", beginDate);
//        }
//
//        if ((endDate != null) && (endDate.trim().length() > 0)) {
//            map.put("endDate", endDate);
//        }
//
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryRcOrderReport(map, pageIndex, pageSize);
//    }
//
//    /**
//     * ��ѯ������־
//     * @param departIds:
//     * @param salesRescIds:
//     * @param rescInstance2:
//     * @param beginDate:
//     * @param endDate:
//     * @param pageIndex
//     * @param pageSize
//     * @return
//     * @throws Exception
//     */
//    public PageModel qryRescFixOrder(String lanId, String operId,
//        String familyId, String salesRescIds, String rescInstance2,
//        String storageId, String prodNo, String custName, String beginDate,
//        String endDate, int pageIndex, int pageSize) throws Exception {
//        Map map = new HashMap();
//
//        if ((lanId != null) && (lanId.trim().length() > 0)) {
//            map.put("lanId", lanId);
//        }
//
//        if ((operId != null) && (operId.trim().length() > 0)) {
//            map.put("operId", operId);
//        }
//
//        if ((familyId != null) && (familyId.trim().length() > 0)) {
//            map.put("familyId", familyId);
//        }
//
//        if ((storageId != null) && (storageId.trim().length() > 0)) {
//            map.put("storageId", storageId);
//        }
//
//        if ((salesRescIds != null) && (salesRescIds.trim().length() > 0)) {
//            map.put("salesRescIds", salesRescIds);
//        }
//
//        if ((rescInstance2 != null) && (rescInstance2.trim().length() > 0)) {
//            map.put("rescInstance2", rescInstance2);
//        }
//
//        if ((beginDate != null) && (beginDate.trim().length() > 0)) {
//            map.put("beginDate", beginDate);
//        }
//
//        if ((endDate != null) && (endDate.trim().length() > 0)) {
//            map.put("endDate", endDate);
//        }
//
//        if ((prodNo != null) && (prodNo.trim().length() > 0)) {
//            map.put("prodNo", prodNo);
//        }
//
//        if ((custName != null) && (custName.trim().length() > 0)) {
//            map.put("custName", custName);
//        }
//
//        // ֻ��ѯ�۳��ļ�¼
//        map.put("isOut", "o");
//
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryRcSaleLog(map, pageIndex, pageSize);
//    }
//
//    /**
//     * ��ѯӪ����ԴΪ�޸��µ���Ϣ
//     * @param logId
//     * @return
//     * @throws Exception
//     */
//    public RcServAcceptVO qryServAccrptByLogId(String logId)
//        throws Exception {
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryServAccrptByLogId(logId);
//    }
//
//    /**
//     * ����Ӫ����ԴΪ�޸��µ���Ϣ
//     * @param logId
//     * @return
//     * @throws Exception
//     */
//    public String saveServAccept(RcServAcceptVO vo) throws Exception {
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        vo.setOperId(operId);
//
//SrStock",
//                SrStockHome.class);
//
//        return bean.saveServAccept(vo);
//    }
//
//    /**
//     * ��ѯ������־
//     * @param departIds:
//     * @param salesRescIds:
//     * @param rescInstance2:
//     * @param beginDate:
//     * @param endDate:
//     * @param pageIndex
//     * @param pageSize
//     * @return
//     * @throws Exception
//     */
//    public PageModel qryRcServiceAccept(String lanId, String operId,
//        String familyId, String salesRescIds, String rescInstanceCode,
//        String prodNo, String custName, String beginDate, String endDate,
//        int pageIndex, int pageSize) throws Exception {
//        Map map = new HashMap();
//
//        if ((lanId != null) && (lanId.trim().length() > 0)) {
//            map.put("lanId", lanId);
//        }
//
//        if ((operId != null) && (operId.trim().length() > 0)) {
//            map.put("operId", operId);
//        }
//
//        if ((familyId != null) && (familyId.trim().length() > 0)) {
//            map.put("familyId", familyId);
//        }
//
//        if ((salesRescIds != null) && (salesRescIds.trim().length() > 0)) {
//            map.put("salesRescIds", salesRescIds);
//        }
//
//        if ((rescInstanceCode != null) &&
//                (rescInstanceCode.trim().length() > 0)) {
//            map.put("rescInstanceCode", rescInstanceCode);
//        }
//
//        if ((beginDate != null) && (beginDate.trim().length() > 0)) {
//            map.put("beginDate", beginDate);
//        }
//
//        if ((endDate != null) && (endDate.trim().length() > 0)) {
//            map.put("endDate", endDate);
//        }
//
//        if ((prodNo != null) && (prodNo.trim().length() > 0)) {
//            map.put("prodNo", prodNo);
//        }
//
//        if ((custName != null) && (custName.trim().length() > 0)) {
//            map.put("custName", custName);
//        }
//
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryRcServiceAccept(map, pageIndex, pageSize);
//    }
//
//    /**
//     * �����µ����в�ѯ��Դά��ȡ����Ϣ
//     * @param logId
//     * @return
//     * @throws Exception
//     */
//    public RcServReturnVO qryServReturnByAcceptId(String sAcceptId)
//        throws Exception {
//SrStock",
//                SrStockHome.class);
//
//        return bean.qryServReturnByAcceptId(sAcceptId);
//    }
//
//    /**
//     * ����Ӫ����ԴΪ�޸��µ���Ϣ
//     * @ejb.interface-method view-type = "both"
//     * @param logId
//     * @return
//     * @throws Exception
//     */
//    public String saveServReturn(RcServReturnVO vo) throws Exception {
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        vo.setOperId(operId);
//
//SrStock",
//                SrStockHome.class);
//
//        return bean.saveServReturn(vo);
//    }
//
//    /**
//     * ���ݲֿ�ID��Ӫ����Դid���ַ�����ѯ���¼��ֿ���Դ�Ŀ��
//     * @param deptID
//     * @param saleIds
//     * @param pageIndex
//     * @param pageSize
//     * @return
//     */
//    public PageModel queryDownStockNum(String downStorageIds,
//        String salesRescIds, String lanId, String regionId, int pageIndex,
//        int pageSize) throws Exception {
//SrStock",
//                SrStockHome.class);
//        HashMap map = new HashMap();
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        map.put("operId", operId);
//
//        if ((lanId != null) && (lanId.trim().length() > 0)) {
//            map.put("lanId", lanId);
//        }
//
//        if ((regionId != null) && (regionId.trim().length() > 0)) {
//            map.put("regionId", regionId);
//        }
//
//        if ((downStorageIds != null) && (downStorageIds.trim().length() > 0)) {
//            map.put("downStorageIds", downStorageIds);
//        }
//
//        if ((salesRescIds != null) && (salesRescIds.trim().length() > 0)) {
//            map.put("salesRescIds", salesRescIds);
//        }
//
//        return bean.queryDownStockNum(map, pageIndex, pageSize);
//    }
//
//    /**
//     * ͳ���ն�������Դ�Ŀ����Ŀ
//     * @param deptID
//     * @param saleIds
//     * @return
//     */
//    public PageModel satTerminalStockNum(String saleIds, String rescState,
//        String upStorageId, int pi, int ps) throws Exception {
//SrStock",
//                SrStockHome.class);
//        Map map = new HashMap();
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        String deptId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
//        String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);
//        String familyId = RcFamilyVO.TerminalFamily;
//        map.put("operId", operId);
//        map.put("deptId", deptId);
//        map.put("lanId", lanId);
//        map.put("familyId", familyId);
//
//        if ((saleIds != null) && (saleIds.trim().length() > 0)) {
//            map.put("saleIds", saleIds);
//        }
//
//        if ((upStorageId != null) && (upStorageId.trim().length() > 0)) {
//            map.put("upStorageId", upStorageId);
//        }
//
//        if ((rescState != null) && (rescState.trim().length() > 0)) {
//            map.put("rescState", rescState);
//        }
//
//        return bean.satTerminalStockNum(map, pi, ps);
//    }
//
//    /**
//     * ͳ���ն�������Դҵ���������
//     * @param deptID
//     * @param saleIds
//     * @return
//     */
//    public PageModel satTerminalInOutNum(String isOut, String saleIds,
//        String biztype, String beginDate, String endDate, int pi, int ps)
//        throws Exception {
//SrStock",
//                SrStockHome.class);
//        Map map = new HashMap();
//        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
//        String deptId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
//        String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);
//        String familyId = RcFamilyVO.TerminalFamily;
//        map.put("operId", operId);
//        map.put("deptId", deptId);
//        map.put("lanId", lanId);
//        map.put("familyId", familyId);
//
//        if ((isOut != null) && (isOut.trim().length() > 0)) {
//            map.put("isOut", isOut);
//        }
//
//        if ((saleIds != null) && (saleIds.trim().length() > 0)) {
//            map.put("saleIds", saleIds);
//        }
//
//        if ((biztype != null) && (biztype.trim().length() > 0)) {
//            map.put("bizType", biztype);
//        }
//
//        if ((beginDate != null) && (beginDate.trim().length() > 0)) {
//            map.put("beginDate", beginDate);
//        }
//
//        if ((endDate != null) && (endDate.trim().length() > 0)) {
//            map.put("endDate", endDate);
//        }
//
//        return bean.satTerminalInOutNum(map, pi, ps);
//    }
//
//    /**
//     * ����������棬����������ѡ�񡰵��������ҡ���Դģ�桱ѡ���նˡ�ʱ����������ӡ���ť�����жϣ���ʾ�������ӡ�-----����
//     * @param deptID
//     * @param saleIds
//     * @return
//     */
//    public String isCanAdd() throws Exception {
//        String isCanSql = "select 1 from dc_public where stype = 94912 and codea = '1'";
//        SqlComDAO comdao = SrDAOFactory.getInstance().getSqlComDAO();
//        boolean isCan = comdao.checkExist(isCanSql);
//
//        if (isCan) {
//            return "1";
//        }
//
//        return "0";
//    }
//
//    /**
//     * �ն�ͳ��ƽ���
//     * @param deptID
//     * @param saleIds
//     * @return
//     */
//    public List qryRcEntityCount(String lanId, String countType,
//        String sheetType, String date) throws Exception {
//        SrStockBo bo = new SrStockBo();
//        List list = bo.qryRcEntityCount(lanId, countType, sheetType, date);
//
//        List addnumList = new ArrayList();
//        List currStockList = new ArrayList();
//        List differentnumList = new ArrayList();
//        List salenumList = new ArrayList();
//        List stockList = new ArrayList();
//
//        if ((list == null) || (list.size() <= 0)) {
//            return null;
//        }
//
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i) != null) {
//                RcEntityReportVO vo = (RcEntityReportVO) list.get(i);
//                addnumList.add((String) vo.getAddNum());
//                currStockList.add((String) vo.getStockNumCurr());
//                differentnumList.add((String) vo.getDifferentnum());
//                salenumList.add((String) vo.getSaleNum());
//                stockList.add((String) vo.getStockNumEnd());
//            }
//        }
//
//        //�ϼ�
//        long addnumSum = sumList(addnumList);
//        long currStockSum = sumList(currStockList);
//        long differentnumSum = sumList(differentnumList);
//        long salenumSum = sumList(salenumList);
//        long stockSum = sumList(stockList);
//        RcEntityReportVO sumvo = new RcEntityReportVO();
//        sumvo.setAddNum(String.valueOf(addnumSum));
//        sumvo.setStockNumCurr(String.valueOf(currStockSum));
//        sumvo.setDifferentnum(String.valueOf(differentnumSum));
//        sumvo.setSaleNum(String.valueOf(salenumSum));
//        sumvo.setStockNumEnd(String.valueOf(stockSum));
//        sumvo.setLanId("�ϼ�");
//
//        list.add(sumvo);
//        transStaticData(list);
//
//        return list;
//    }
//
//    public long sumList(List list) {
//        if ((list == null) || (list.size() <= 0)) {
//            return 0;
//        }
//
//        long sum = 0;
//
//        for (int i = 0; i < list.size(); i++) {
//            if ((list.get(i) == null) ||
//                    (((String) (list.get(i))).length() <= 0)) {
//                continue;
//            }
//
//            sum += Long.parseLong((String) (list.get(i)));
//        }
//
//        return sum;
//    }
//
//    private void transStaticData(List list) {
//        if ((list != null) && (list.size() > 0)) {
//            StaticAttrCache cache = StaticAttrCache.getInstance();
//            RcEntityReportVO voTemp = null;
//            List lanList = cache.getAttrData("DC_LAN_CODE");
//            StaticAttrVO dataVO = null;
//            Map classMap = new HashMap();
//
//            if ((lanList != null) && (lanList.size() > 0)) {
//                for (int j = 0; j < lanList.size(); j++) {
//                    dataVO = (StaticAttrVO) lanList.get(j);
//
//                    if (dataVO != null) {
//                        classMap.put(dataVO.getAttrValue(),
//                            dataVO.getAttrValueDesc());
//                    }
//                }
//            }
//
//            for (int i = 0; i < list.size(); i++) {
//                voTemp = (RcEntityReportVO) list.get(i);
//
//                if ((voTemp.getLanId() != null) &&
//                        (voTemp.getLanId().trim().length() > 0)) {
//                    if (classMap.get(voTemp.getLanId().trim()) != null) {
//                        voTemp.setLanId((String) classMap.get(
//                                voTemp.getLanId().trim()));
//                    }
//                }
//            }
//        }
//    }
//
//    public List upLoadStockOut(String salesRescId, String inStorage,
//        String outStorage, String appType, String codes)
//        throws RemoteException, Exception {
//        String landId = getGlobalVar(GlobalVariableHelper.LAN_ID);
//        Map map = new HashMap();
//        map.put("salesRescId", salesRescId);
//        map.put("inStorageId", inStorage);
//        map.put("outStorageId", outStorage);
//        map.put("appType", appType);
//
//        Collection fileList = new HashSet();
//
//        if ((codes == null) || (codes.length() <= 0)) {
//            return null;
//        }
//
//        String[] c = codes.split(",");
//
//        for (int i = 0; i < c.length; i++) {
//            fileList.add(c[i]);
//        }
//
//SrStock",
//                SrStockHome.class);
//
//        return bean.upLoadStockOut(map, fileList);
//
//        //        return null;
//    }
}
