package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;

import com.ztesoft.crm.salesres.vo.RcEntityVO2;

import java.util.*;


public class RcOrderVO extends ValueObject implements VO {
    public final static String HandleType_Entity_ImportFile = "1"; // 表明对实例操作，需操作的编码来自于文件上传
    public final static String HandleType_Entity_SeriesCode = "2"; // 表明对实例操作，需操作的编码从界面的起始编码和终止编码决定
    public final static String HandleType_Entity_SegCode = "3"; // 表明对实例操作，需操作的编码从界面的分段的起始编码和终止编码决定
    private String orderId = "";
    private String appId = "";
    private String appType = "";
    private String operId = "";
    private String departId = "";
    private String tacheId = "";
    private String stateId = "";
    private String tacheTime = "";
    private String appTime = "";
    private String endTime = "";
    private String outStorageId = "";
    private String inStorageId = "";
    private String appStorageId = "";
    private String salesRescId = "";
    private String appAmount = "";
    private String actAmount = "";
    private String requireTime = "";
    private String comments = "";
    private String resComments = "";
    private String resBCode = "";
    private String resECode = "";
    private String status = "";
    
    //订单分段信息
    private RcOrderSegInfoVO[] rSeg = null;//new RcOrderSegInfoVO[3];
    // private String appType = "";
    private String departName = "";
    private String appTypeName = "";
    private String tacheName = "";
    private String stateName = "";
    private String salesRescName = "";
    private String outStorageName = "";
    private String inStorageName = "";
    private String appStorageName = "";
    private String operName = "";
    private String lanId = "";
    private String recOptType = "";

    /** 设置工作的ip * */
    private String reworkIp = "";

    /** 回退单编号 * */
    private String backOrderId = "";
    private String upAppType = ""; // 表RC_APP_TYPE对应的字段，代表回退类型
    private String rescInstanceCode = "";

    /** 记录入库时需要新增实体的字段值 * */
    private RcEntityVO entityVO;

    /** 属性列表 * */
    private List attrList;

    /** 记录要出入库的实例编码的集合，为RcOrderListVO的集合 * */
    private List entityCodeList;

    /** 帮助字段，表明从哪里获得处理实例所需的编码 * */
    private String handleType = null;

    /** 实例出入库时，根据家族id确定的需要操作的实例表名 * */
    private String entityTabName = "";

    /** 该营销资源的家族id * */
    private String familyId = "";
    private String familyName = "";

    // /////////// 代理商信息 ////////////////
    private String agentId = "";
    private String agentName = "";
    private String acceptId = "";
    private String acceptType = "";
    private String rescPrice = "";
    private String discount = "";
    private String realPrice = "";

    // //////////// 以下是统计处入库时需要用到的/////////////////////
    private String outAmount = "";
    private String inAmount = "";
    private String netAmount = "";
    private String storageId = "";
    private String storageName = "";
    private String beginDate = "";
    private String endDate = "";

    // ////////////////////表示订单生成是起止编码或者文件导入/////////////////////////////
    private String orderType = "";

    public RcOrderVO() {
    }

    public RcOrderVO(String porderId, String pappId, String pappType,
        String poperId, String pdepartId, String ptacheId, String pstateId,
        String ptacheTime, String pappTime, String pendTime,
        String poutStorageId, String pinStorageId, String pappStorageId,
        String psalesRescId, String pappAmount, String pactAmount,
        String prequireTime, String pcomments, String presComments) {
        orderId = porderId;
        appId = pappId;
        appType = pappType;
        operId = poperId;
        departId = pdepartId;
        tacheId = ptacheId;
        stateId = pstateId;
        tacheTime = ptacheTime;
        appTime = pappTime;
        endTime = pendTime;
        outStorageId = poutStorageId;
        inStorageId = pinStorageId;
        appStorageId = pappStorageId;
        salesRescId = psalesRescId;
        appAmount = pappAmount;
        actAmount = pactAmount;
        requireTime = prequireTime;
        comments = pcomments;
        resComments = presComments;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppStorageName() {
        return appStorageName;
    }

    public void setAppStorageName(String appStorageName) {
        this.appStorageName = appStorageName;
    }

    public String getInStorageName() {
        return inStorageName;
    }

    public void setInStorageName(String inStorageName) {
        this.inStorageName = inStorageName;
    }

    public String getOutStorageName() {
        return outStorageName;
    }

    public void setOutStorageName(String outStorageName) {
        this.outStorageName = outStorageName;
    }

    public String getSalesRescName() {
        return salesRescName;
    }

    public void setSalesRescName(String salesRescName) {
        this.salesRescName = salesRescName;
    }

    public String getAppTypeName() {
        return appTypeName;
    }

    public void setAppTypeName(String appTypeName) {
        this.appTypeName = appTypeName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getTacheName() {
        return tacheName;
    }

    public void setTacheName(String tacheName) {
        this.tacheName = tacheName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppType() {
        return appType;
    }

    public String getOperId() {
        return operId;
    }

    public String getDepartId() {
        return departId;
    }

    public String getTacheId() {
        return tacheId;
    }

    public String getStateId() {
        return stateId;
    }

    public String getTacheTime() {
        return tacheTime;
    }

    public String getAppTime() {
        return appTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getOutStorageId() {
        return outStorageId;
    }

    public String getInStorageId() {
        return inStorageId;
    }

    public String getAppStorageId() {
        return appStorageId;
    }

    public String getSalesRescId() {
        return salesRescId;
    }

    public String getAppAmount() {
        return appAmount;
    }

    public String getActAmount() {
        return actAmount;
    }

    public String getRequireTime() {
        return requireTime;
    }

    public String getComments() {
        return comments;
    }

    public String getResComments() {
        return resComments;
    }

    public String getResBCode() {
        return resBCode;
    }

    public String getResECode() {
        return resECode;
    }

    public List getAttrList() {
        return attrList;
    }

    public String getLanId() {
        return lanId;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getAcceptType() {
        return acceptType;
    }

    public String getAcceptId() {
        return acceptId;
    }

    public String getReworkIp() {
        return reworkIp;
    }

    public RcEntityVO getEntityVO() {
        return entityVO;
    }

    public String getHandleType() {
        return handleType;
    }

    public List getEntityCodeList() {
        return entityCodeList;
    }

    public String getEntityTabName() {
        return entityTabName;
    }

    public String getFamilyId() {
        return familyId;
    }

    public String getRescInstanceCode() {
        return rescInstanceCode;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getDepartName() {
        return departName;
    }

    public String getOutAmount() {
        return outAmount;
    }

    public String getStorageId() {
        return storageId;
    }

    public String getStorageName() {
        return storageName;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public String getInAmount() {
        return inAmount;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public String getRescPrice() {
        return rescPrice;
    }

    public String getBackOrderId() {
        return backOrderId;
    }

    public String getUpAppType() {
        return upAppType;
    }

    public String getHandleType_Entity_SeriesCode() {
        return HandleType_Entity_SeriesCode;
    }

    public void setOrderId(String pOrderId) {
        orderId = pOrderId;
    }

    public void setAppId(String pAppId) {
        appId = pAppId;
    }

    public void setAppType(String pAppType) {
        appType = pAppType;
    }

    public void setOperId(String pOperId) {
        operId = pOperId;
    }

    public void setDepartId(String pDepartId) {
        departId = pDepartId;
    }

    public void setTacheId(String pTacheId) {
        tacheId = pTacheId;
    }

    public void setStateId(String pStateId) {
        stateId = pStateId;
    }

    public void setTacheTime(String pTacheTime) {
        tacheTime = pTacheTime;
    }

    public void setAppTime(String pAppTime) {
        appTime = pAppTime;
    }

    public void setEndTime(String pEndTime) {
        endTime = pEndTime;
    }

    public void setOutStorageId(String pOutStorageId) {
        outStorageId = pOutStorageId;
    }

    public void setInStorageId(String pInStorageId) {
        inStorageId = pInStorageId;
    }

    public void setAppStorageId(String pAppStorageId) {
        appStorageId = pAppStorageId;
    }

    public void setSalesRescId(String pSalesRescId) {
        salesRescId = pSalesRescId;
    }

    public void setAppAmount(String pAppAmount) {
        appAmount = pAppAmount;
    }

    public void setActAmount(String pActAmount) {
        actAmount = pActAmount;
    }

    public void setRequireTime(String pRequireTime) {
        requireTime = pRequireTime;
    }

    public void setComments(String pComments) {
        comments = pComments;
    }

    public void setResComments(String pResComments) {
        resComments = pResComments;
    }

    public void setResBCode(String resBCode) {
        this.resBCode = resBCode;
    }

    public void setResECode(String resECode) {
        this.resECode = resECode;
    }

    public void setAttrList(List attrList) {
        this.attrList = attrList;
    }

    public void setLanId(String lanId) {
        this.lanId = lanId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public void setAcceptType(String acceptType) {
        this.acceptType = acceptType;
    }

    public void setAcceptId(String acceptId) {
        this.acceptId = acceptId;
    }

    public void setReworkIp(String reworkIp) {
        this.reworkIp = reworkIp;
    }

    public void setEntityVO(RcEntityVO entityVO) {
        this.entityVO = entityVO;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    public void setEntityCodeList(List entityCodeList) {
        this.entityCodeList = entityCodeList;
    }

    public void setEntityTabName(String entityTabName) {
        this.entityTabName = entityTabName;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public void setRescInstanceCode(String rescInstanceCode) {
        this.rescInstanceCode = rescInstanceCode;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public void setOutAmount(String outAmount) {
        this.outAmount = outAmount;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public void setInAmount(String inAmount) {
        this.inAmount = inAmount;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setRescPrice(String rescPrice) {
        this.rescPrice = rescPrice;
    }

    public void setBackOrderId(String backOrderId) {
        this.backOrderId = backOrderId;
    }

    public void setUpAppType(String upAppType) {
        this.upAppType = upAppType;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("ORDER_ID", this.orderId);
        hashMap.put("APP_ID", this.appId);
        hashMap.put("APP_TYPE", this.appType);
        hashMap.put("OPER_ID", this.operId);
        hashMap.put("DEPART_ID", this.departId);
        hashMap.put("TACHE_ID", this.tacheId);
        hashMap.put("STATE_ID", this.stateId);
        hashMap.put("TACHE_TIME", this.tacheTime);
        hashMap.put("APP_TIME", this.appTime);
        hashMap.put("END_TIME", this.endTime);
        hashMap.put("OUT_STORAGE_ID", this.outStorageId);
        hashMap.put("IN_STORAGE_ID", this.inStorageId);
        hashMap.put("APP_STORAGE_ID", this.appStorageId);
        hashMap.put("SALES_RESOURCE_ID", this.salesRescId);
        hashMap.put("APP_AMOUNT", this.appAmount);
        hashMap.put("ACT_AMOUNT", this.actAmount);
        hashMap.put("REQUIRE_TIME", this.requireTime);
        hashMap.put("COMMENTS", this.comments);
        hashMap.put("RES_COMMENTS", this.resComments);
        hashMap.put("RES_B_CODE", this.resBCode);
        hashMap.put("RES_E_CODE", this.resECode);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.orderId = (String) hashMap.get("ORDER_ID");
            this.appId = (String) hashMap.get("APP_ID");
            this.appType = (String) hashMap.get("APP_TYPE");
            this.operId = (String) hashMap.get("OPER_ID");
            this.departId = (String) hashMap.get("DEPART_ID");
            this.tacheId = (String) hashMap.get("TACHE_ID");
            this.stateId = (String) hashMap.get("STATE_ID");
            this.tacheTime = (String) hashMap.get("TACHE_TIME");
            this.appTime = (String) hashMap.get("APP_TIME");
            this.endTime = (String) hashMap.get("END_TIME");
            this.outStorageId = (String) hashMap.get("OUT_STORAGE_ID");
            this.inStorageId = (String) hashMap.get("IN_STORAGE_ID");
            this.appStorageId = (String) hashMap.get("APP_STORAGE_ID");
            this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
            this.appAmount = (String) hashMap.get("APP_AMOUNT");
            this.actAmount = (String) hashMap.get("ACT_AMOUNT");
            this.requireTime = (String) hashMap.get("REQUIRE_TIME");
            this.comments = (String) hashMap.get("COMMENTS");
            this.resComments = (String) hashMap.get("RES_COMMENTS");
            this.resBCode = (String) hashMap.get("RES_B_CODE");
            this.resECode = (String) hashMap.get("RES_E_CODE");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();

        return arrayList;
    }

    public String getTableName() {
        return "RC_ORDER";
    }

    public RcOrderVO copy() {
        RcOrderVO obj = new RcOrderVO();
        obj.setActAmount(this.getActAmount());
        obj.setAppAmount(this.getAppAmount());
        obj.setAppId(this.getAppId());
        obj.setAppStorageId(this.getAppStorageId());
        obj.setAppStorageName(this.getAppStorageName());
        obj.setAppTime(this.getAppTime());
        obj.setAppType(this.getAppType());
        obj.setAppTypeName(this.getAppTypeName());
        obj.setComments(this.getComments());
        obj.setDeleteFlag(this.getDeleteFlag());
        obj.setDepartId(this.getDepartId());
        obj.setEndTime(this.getEndTime());
        obj.setInsertFlag(this.getInsertFlag());
        obj.setInStorageId(this.getInStorageId());
        obj.setInStorageName(this.getInStorageName());
        obj.setOperId(this.getOperId());
        obj.setOperName(this.getOperName());
        obj.setOrderId(this.getOrderId());
        obj.setOutStorageId(this.getOutStorageId());
        obj.setOutStorageName(this.getOutStorageName());
        obj.setRequireTime(this.getRequireTime());
        obj.setResComments(this.getResComments());
        obj.setRowVersion(this.getRowVersion());
        obj.setSalesRescId(this.getSalesRescId());
        obj.setSalesRescName(this.getSalesRescName());
        obj.setStateId(this.getStateId());
        obj.setStateName(this.getStateName());
        obj.setStatus(this.getStatus());
        obj.setTacheId(this.getTacheId());
        obj.setTacheName(this.getTacheName());
        obj.setTacheTime(this.getTacheTime());
        obj.setUpdateFlag(this.getUpdateFlag());

        return obj;
    }

	public RcOrderSegInfoVO[] getRSeg() {
		return rSeg;
	}

	public void setRSeg(RcOrderSegInfoVO[] seg) {
		rSeg = seg;
	}

	public String getRecOptType() {
		return recOptType;
	}

	public void setRecOptType(String recOptType) {
		this.recOptType = recOptType;
	}

	
}
