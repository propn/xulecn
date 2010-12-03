package com.ztesoft.crm.salesres.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.bo.RcEntityBo;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.vo.RcEntityVO;
import com.ztesoft.crm.salesres.vo.RcEntityVO2;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class SrEntityService extends DictService {
    public PageModel getSalesResourceInfo(String sType, String sContent,
        String familyId, int pi, int ps) throws Exception {
        //资源实例管理界面面，控制终端的开关
        //if (familyId.equals("104") && isCanModify().equals("1")) {
        //    return null;
        //}

        PageModel pm = new PageModel();
        HashMap map = new HashMap();
        map.put("sType", sType);
        map.put("sContent", sContent);
        map.put("familyId", familyId);
        map.put("pageIndex",new Integer(pi));
        map.put("pageSize",new Integer(ps));
        try {
        	pm = (PageModel)ServiceManager.callJavaBeanService("SrSalesRescBo", "getSalesResourceInfo" , map) ;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    /**
     * @param action
     * @param storageId
     * @param salesRescId
     * @param stockAmount
     * @return
     * @throws Exception
     */
    public String dealQuantityStock(String action, String storageId,
        String salesRescId, String stockAmount) throws Exception {
    	HashMap map = new HashMap();
        map.put("action", action);
        map.put("storageId", storageId);
        map.put("salesRescId", salesRescId);
        map.put("stockAmount", stockAmount);
        String operId = getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = getGlobalVar(GlobalVariableHelper.DEPART_ID);
        map.put("operId", operId);
        map.put("departId", departId);
        String pm ="";
        try {
        	pm = (String)ServiceManager.callJavaBeanService("RcEntityBo", "dealQuantityStock" , map) ;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;

       
    }

    public String recycleEntity(String rescInstanceId, String salesId,
        String storageId) throws Exception {
    	HashMap map = new HashMap();
        map.put("rescInstanceId", rescInstanceId);
        map.put("salesId", salesId);
        map.put("storageId", storageId);
        String operId = getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = getGlobalVar(GlobalVariableHelper.DEPART_ID);
        map.put("operId", operId);
        map.put("departId", departId);
        String pm ="";
        try {
        	pm = (String)ServiceManager.callJavaBeanService("RcEntityBo", "recycleEntity" , map) ;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;

    }

    /**
     * 资源回收
     * @param sType
     * @param sContent
     * @param storageId
     * @param sRegionId
     * @param status
     * @param beginDate
     * @param endDate
     * @param lanId
     * @param gjselect
     * @param pi
     * @param ps
     * @return
     * @throws Exception
     */
    public PageModel getRecycleEntityInfo(String sType, String sContent,
        String storageId, String sRegionId, String status, String beginDate,
        String endDate, String lanId, String gjselect, int pi, int ps)
        throws Exception {
        Map map = new HashMap();

        if ((sType != null) && (sType.trim().length() > 0)) {
            map.put("sType", sType);
        }

        if ((sContent != null) && (sContent.trim().length() > 0)) {
            map.put("sContent", sContent);
        }

        if ((storageId != null) && (storageId.trim().length() > 0)) {
            map.put("storageId", storageId);
        }

        if ((sRegionId != null) && (sRegionId.trim().length() > 0)) {
            map.put("sRegionId", sRegionId);
        }

        if ((status != null) && (status.trim().length() > 0)) {
            map.put("status", status);
        }

        if ((beginDate != null) && (beginDate.trim().length() > 0)) {
            map.put("beginDate", beginDate);
        }

        if ((endDate != null) && (endDate.trim().length() > 0)) {
            map.put("endDate", endDate);
        }

        if ((lanId != null) && (lanId.trim().length() > 0)) {
            map.put("lanId", lanId);
        }

        if ((gjselect != null) && (gjselect.trim().length() > 0)) {
            map.put("gjselect", gjselect);
        }

        map.put("provId", this.getGlobalVar(GlobalVariableHelper.PROV_ID));

        PageModel pm = new PageModel();

        //System.out.println(map);
        RcEntityBo rBO = new RcEntityBo();
        pm = rBO.getRecycleEntityInfo(map, pi, ps);

        return pm;
    }

    public PageModel getRcEntityInfo(String sType, String familyId,
        String sContent, String storageId, String sRegionId, String status,
        String beginDate, String endDate, String lanId, String gjselect,
        int pi, int ps) throws Exception {
        Map map = new HashMap();

        map.put("familyId", familyId);

        if ((sType != null) && (sType.trim().length() > 0)) {
            map.put("sType", sType);
        }

        if ((sContent != null) && (sContent.trim().length() > 0)) {
            map.put("sContent", sContent);
        }

        if ((storageId != null) && (storageId.trim().length() > 0)) {
            map.put("storageId", storageId);
        }

        if ((sRegionId != null) && (sRegionId.trim().length() > 0)) {
            map.put("sRegionId", sRegionId);
        }

        if ((status != null) && (status.trim().length() > 0)) {
            map.put("status", status);
        }

        if ((beginDate != null) && (beginDate.trim().length() > 0)) {
            map.put("beginDate", beginDate);
        }

        if ((endDate != null) && (endDate.trim().length() > 0)) {
            map.put("endDate", endDate);
        }

        if ((lanId != null) && (lanId.trim().length() > 0)) {
            map.put("lanId", lanId);
        }

        if ((gjselect != null) && (gjselect.trim().length() > 0)) {
            map.put("gjselect", gjselect);
        }

        map.put("provId", this.getGlobalVar(GlobalVariableHelper.PROV_ID));
        map.put("departId", this.getGlobalVar(GlobalVariableHelper.DEPART_ID));
        map.put("operId", this.getGlobalVar(GlobalVariableHelper.OPER_ID));

        PageModel pm = new PageModel();
        System.out.println(map);
        RcEntityBo rBO = new RcEntityBo();

        //pm = rBO.getRcEntityInfo(map, pi, ps);
        
        map.put("pageIndex",new Integer(pi));
        map.put("pageSize",new Integer(ps));
        
        pm = (PageModel)ServiceManager.callJavaBeanService("RcEntityBo","getRcEntityInfo" ,map);

        return pm;
    }

    public PageModel getQuantityInfo(String salesRescId, String storageId,
        int pi, int ps) throws Exception {
        RcEntityBo rBO = new RcEntityBo();

        return rBO.getQuantityInfo(salesRescId, storageId, pi, ps);
    }

    //查询终端分组信息
    public PageModel qryDeviceGroup(String qryType, String qryValue, int pi,
        int ps) throws Exception {
        RcEntityBo rBO = new RcEntityBo();

        return rBO.qryDeviceGroup(qryType, qryValue, pi, ps);
    }

    public int saveRcEntity(RcEntityVO2 rvo) {
        int retResult = 0;

        try {
        	HashMap map = new HashMap();
            map.put("vo", rvo);
            Integer  pm = (Integer)ServiceManager.callJavaBeanService("RcEntityBo", "saveRcEntity" , map) ;
            retResult= pm.intValue();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return retResult;
    }

    public int updateRcEntity(RcEntityVO2 rvo) {
        int retResult = 0;

        try {
        	HashMap map = new HashMap();
            map.put("vo", rvo);
            
            String operId = getGlobalVar(GlobalVariableHelper.OPER_ID);
            String departId = getGlobalVar(GlobalVariableHelper.DEPART_ID);
            map.put("operId", operId);
            map.put("departId", departId);
            Integer pm =null;
            try {
            	pm = (Integer)ServiceManager.callJavaBeanService("RcEntityBo", "updateRcEntity" , map) ;

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            retResult= pm.intValue();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return retResult;
    }

    public boolean deleteRcEntity(String resourceInstanceId, String salesRescId) {
        boolean retResult = false;

        try {
        	HashMap map = new HashMap();
        	map.put("resourceInstanceId", resourceInstanceId);
            map.put("salesRescId", salesRescId);
            
            String operId = getGlobalVar(GlobalVariableHelper.OPER_ID);
            String departId = getGlobalVar(GlobalVariableHelper.DEPART_ID);
            map.put("operId", operId);
            map.put("departId", departId);
            Boolean pm =null;
            try {
            	pm = (Boolean)ServiceManager.callJavaBeanService("RcEntityBo", "deleteRcEntity" , map) ;

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            retResult= pm.booleanValue();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return retResult;
    }

   

    /**
     * 查询实例的属性
     *
     * @param rescInstanceId
     *            String
     * @throws Exception
     * @return List
     */
    public List qrySrAttrInfoOut(String rescInstanceId)
        throws Exception {
        List pm = new ArrayList();
        RcEntityBo rBO = new RcEntityBo();
        pm = rBO.qrySrAttrInfoOut(rescInstanceId);

        return pm;
    }

    /**
     * 查询实例的属性
     *
     * @param rescInstanceId
     *            String
     * @throws Exception
     * @return List
     */
    public List qrySrAttrInfo(String rescInstanceId) throws Exception {
    	RcEntityBo rBO = new RcEntityBo();
        List pm = rBO.qrySrAttrInfo(rescInstanceId);
        //pm = bean.qrySrAttrInfo(rescInstanceId);

        return pm;
    }

    /**
     * 删除实例的属性
     *
     * @param rescInstanceId
     *            String
     * @throws Exception
     * @return List
     */
    public int deleteAttrInfo(String rescInstanceId, String attrId)
        throws Exception {
    	HashMap map = new HashMap();
    	map.put("rescInstanceId", rescInstanceId);
    	map.put("attrId", attrId);
    	Integer pm =null;
        try {
        	pm = (Integer)ServiceManager.callJavaBeanService("RcEntityBo", "deleteAttrInfo" , map) ;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //pm = bean.qrySrAttrInfo(rescInstanceId);

        return pm.intValue();
    }

    /**
     * 插入实例的属性
     *
     * @param rescInstanceId
     *            String
     * @throws Exception
     * @return List
     */
    public String insertRcEnAttrInfo(String rescInstanceId, String attrId,
        String attrValue) throws Exception {
    	HashMap map = new HashMap();
    	map.put("rescInstanceId", rescInstanceId);
    	map.put("attrId", attrId);
    	map.put("attrValue", attrValue);
    	String pm =null;
        try {
        	pm = (String)ServiceManager.callJavaBeanService("RcEntityBo", "insertRcEnAttrInfo" , map) ;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    /**
     * 查询物资实例列表,根据营销资源不同，会查询不同的表
     *
     * @param map:查询参数列表
     * @param pi
     * @param ps
     * @return
     */
    public PageModel qryRcEntityPage(String salesRescId, String storageId,
        String state, String rescInstanceCode, int pi, int ps)
        throws Exception {
        Map map = new HashMap();
        map.put("salesRescId", salesRescId);
        map.put("storageId", storageId);
        map.put("state", state);
        map.put("rescInstanceCode", rescInstanceCode);
        map.put("pageIndex",new Integer(pi));
        map.put("pageSize",new Integer(ps));
        PageModel pm =null;
        try {
        	pm = (PageModel)ServiceManager.callJavaBeanService("RcEntityBo", "qryRcEntityPage" , map) ;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    /**
     * 删除实例的属性
     *
     * @param rescInstanceId
     *            String
     * @throws Exception
     * @return List
     */
    public String delInputRcEntity(String orderId, String appId)
        throws Exception {
    	HashMap map = new HashMap();
    	map.put("orderId", orderId);
    	map.put("appId", appId);
    	String pm =null;
        try {
        	pm = (String)ServiceManager.callJavaBeanService("RcEntityBo", "delInputRcEntity" , map) ;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    /**
     * 查询号码修改的通用日志
     * @param map
     * @return
     */
    public PageModel qryComEntityChgLog(String logcode, String startCode,
        String endCode, String beginDate, String endDate, String changeType,
        int pi, int ps) throws Exception {
        Map map = new HashMap();
        map.put("logcode", logcode);
        map.put("startCode", startCode);
        map.put("endCode", endCode);
        map.put("beginDate", beginDate);
        map.put("endDate", endDate);
        map.put("changeType", changeType);

     
        map.put("pageIndex",new Integer(pi));
        map.put("pageSize",new Integer(ps));
        PageModel pm =null;
        try {
        	pm = (PageModel)ServiceManager.callJavaBeanService("RcEntityBo", "qryComEntityChgLog" , map) ;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;

    }

    /**
     * 资源实例管理界面面，控制终端的开关
     * @param deptID
     * @param saleIds
     * @return
     */
    public String isCanModify() throws Exception {
        String isCanSql = "select 1 from dc_public where stype = 94914 ";
        SqlComDAO comdao = SrDAOFactory.getInstance().getSqlComDAO();
        boolean isCan = comdao.checkExist(isCanSql);

        if (isCan) {
            return "1";
        }

        return "0";
    }

    /**
     * 资源实例管理界面面，查询可以修改或者删除的状态LIST
     * @param modifyOrDelete 传U表示更新，传D表示删除
     * @return
     */
    public String getStateListCanModifyOrDelete(String modifyOrDelete)
        throws Exception {
    	//String sql = "select pkey from dc_public where stype = 94915 and codea in (?,?)";
        String sql = "select pkey from dc_public where stype = 94915 ";
        SqlComDAO comdao = SrDAOFactory.getInstance().getSqlComDAO();
        String[] params = new String[2];

        if ((modifyOrDelete == null) || (modifyOrDelete.length() <= 0)) {
        }

        params[0] = "B";
        params[1] = modifyOrDelete;

        //List list = comdao.executeQueryForMapList(sql, params);
        List list = comdao.executeQueryForMapList(sql, null);
        String states = "";

        if ((list == null) || (list.size() <= 0)) {
            return null;
        }

        for (int i = 0; i < list.size(); i++) {
            states += ((String) ((HashMap) list.get(i)).get("pkey") + ",");
        }

        return states;
    }

    //批量更修资源状态
    public String updateEntitysState(String familyId, String rescInstanceCode,
        String currState) throws RemoteException {
    	HashMap map = new HashMap();
    	map.put("familyId", familyId);
    	map.put("rescInstanceCode", rescInstanceCode);
    	map.put("currState", currState);
    	String pm =null;
        try {
        	pm = (String)ServiceManager.callJavaBeanService("RcEntityBo", "updateEntitysState" , map) ;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;

    }
}
