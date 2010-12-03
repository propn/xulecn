package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcLevelLogVO extends ValueObject implements VO {
    private String logId = "";
    private String rescInstanceId = "";
    private String rescInstanceCode = "";
    private String rescLevel = "";
    private String salesRescId = "";
    private String oldRescLevel = "";
    private String operId = "";
    private String operTime = "";
    private String storgeId = "";
    private String storageName = "";
    private String oldRescLevleName = "";
    private String rescLevelName = "";

    public RcLevelLogVO() {
    }

    public RcLevelLogVO(String plogId, String prescInstanceId,
        String prescInstanceCode, String prescLevel, String psalesRescId,
        String poldRescLevel, String poperId, String poperTime) {
        logId = plogId;
        rescInstanceId = prescInstanceId;
        rescInstanceCode = prescInstanceCode;
        rescLevel = prescLevel;
        salesRescId = psalesRescId;
        oldRescLevel = poldRescLevel;
        operId = poperId;
        operTime = poperTime;
    }

    public String getOldRescLevleName() {
        return oldRescLevleName;
    }

    public void setOldRescLevleName(String oldRescLevleName) {
        this.oldRescLevleName = oldRescLevleName;
    }

    public String getRescLevelName() {
        return rescLevelName;
    }

    public void setRescLevelName(String rescLevelName) {
        this.rescLevelName = rescLevelName;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getStorgeId() {
        return storgeId;
    }

    public void setStorgeId(String storgeId) {
        this.storgeId = storgeId;
    }

    public String getLogId() {
        return logId;
    }

    public String getRescInstanceId() {
        return rescInstanceId;
    }

    public String getRescInstanceCode() {
        return rescInstanceCode;
    }

    public String getRescLevel() {
        return rescLevel;
    }

    public String getSalesRescId() {
        return salesRescId;
    }

    public String getOldRescLevel() {
        return oldRescLevel;
    }

    public String getOperId() {
        return operId;
    }

    public String getOperTime() {
        return operTime;
    }

    public void setLogId(String pLogId) {
        logId = pLogId;
    }

    public void setRescInstanceId(String pRescInstanceId) {
        rescInstanceId = pRescInstanceId;
    }

    public void setRescInstanceCode(String pRescInstanceCode) {
        rescInstanceCode = pRescInstanceCode;
    }

    public void setRescLevel(String pRescLevel) {
        rescLevel = pRescLevel;
    }

    public void setSalesRescId(String pSalesRescId) {
        salesRescId = pSalesRescId;
    }

    public void setOldRescLevel(String pOldRescLevel) {
        oldRescLevel = pOldRescLevel;
    }

    public void setOperId(String pOperId) {
        operId = pOperId;
    }

    public void setOperTime(String pOperTime) {
        operTime = pOperTime;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("LOG_ID", this.logId);
        hashMap.put("RESOURCE_INSTANCE_ID", this.rescInstanceId);
        hashMap.put("RESOURCE_INSTANCE_CODE", this.rescInstanceCode);
        hashMap.put("RESOURCE_LEVEL", this.rescLevel);
        hashMap.put("SALES_RESOURCE_ID", this.salesRescId);
        hashMap.put("OLD_RESOURCE_LEVEL", this.oldRescLevel);
        hashMap.put("OPER_ID", this.operId);
        hashMap.put("OPER_TIME", this.operTime);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.logId = (String) hashMap.get("LOG_ID");
            this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
            this.rescInstanceCode = (String) hashMap.get(
                    "RESOURCE_INSTANCE_CODE");
            this.rescLevel = (String) hashMap.get("RESOURCE_LEVEL");
            this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
            this.oldRescLevel = (String) hashMap.get("OLD_RESOURCE_LEVEL");
            this.operId = (String) hashMap.get("OPER_ID");
            this.operTime = (String) hashMap.get("OPER_TIME");
        }
    }

    public void loadFromHashMap2(HashMap hashMap) {
        if (hashMap != null) {
            this.logId = (String) hashMap.get("logId");
            this.rescInstanceId = (String) hashMap.get("rescInstanceId");
            this.rescInstanceCode = (String) hashMap.get("rescInstanceCode");
            this.rescLevel = (String) hashMap.get("rescLevel");
            this.salesRescId = (String) hashMap.get("salesRescId");
            this.oldRescLevel = (String) hashMap.get("oldRescLevel");
            this.operId = (String) hashMap.get("operId");
            this.operTime = (String) hashMap.get("operTime");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("LOG_ID");
        arrayList.add("RESOURCE_INSTANCE_ID");

        return arrayList;
    }

    public String getTableName() {
        return "RC_LEVEL_LOG";
    }
}
