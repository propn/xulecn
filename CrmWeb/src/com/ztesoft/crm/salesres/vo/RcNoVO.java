package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcNoVO extends ValueObject implements VO {
    private String rescInstanceId = "";
    private String rescInstanceCode = "";
    private String rescLevel = "";
    private String salesRescId = "";
    private String storageId = "";
    private String rescState = "";
    private String state = "";
    private String effDate = "";
    private String expDate = "";
    private String imsiId = "";
    private String noSegId = "";
    private String recTime = "";
    private String balaMode = "";
    private String initTime = "";
    private String salesRescName = "";
    private String noSegName = "";
    private String storageName = "";
    private String storageId_old = "";
    private String storageName_old = "";
    private String nobagId = ""; // 号码包id
    private String oldRescLevle = "";
    private String oldRescLevleName = "";
    private String rescLevelName = "";
    private String startCode = "";
    private String endCode = "";
    private String noNumber = "";
    private String lanId = "";
    private String regionId = "";
    private String exchId = "";
    private String selfhelpflag = ""; // 1支持自助选号，0不支持自助选号
    private String operCode = "";
    private String operId = "";
    private String cDoubleFlag = "";

    public RcNoVO() {
    }

    public RcNoVO(String prescInstanceId, String prescInstanceCode,
        String prescLevel, String psalesRescId, String pstorageId,
        String prescState, String pstate, String peffDate, String pexpDate,
        String pimsiId, String pnoSegId) {
        rescInstanceId = prescInstanceId;
        rescInstanceCode = prescInstanceCode;
        rescLevel = prescLevel;
        salesRescId = psalesRescId;
        storageId = pstorageId;
        rescState = prescState;
        state = pstate;
        effDate = peffDate;
        expDate = pexpDate;
        imsiId = pimsiId;
        noSegId = pnoSegId;
    }

    public String getExchId() {
        return exchId;
    }

    public void setExchId(String exchId) {
        this.exchId = exchId;
    }

    public String getLanId() {
        return lanId;
    }

    public void setLanId(String lanId) {
        this.lanId = lanId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getOldRescLevle() {
        return oldRescLevle;
    }

    public void setOldRescLevle(String oldRescLevle) {
        this.oldRescLevle = oldRescLevle;
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

    public String getStorageId() {
        return storageId;
    }

    public String getRescState() {
        return rescState;
    }

    public String getState() {
        return state;
    }

    public String getEffDate() {
        return effDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getImsiId() {
        return imsiId;
    }

    public String getNoSegId() {
        return noSegId;
    }

    public String getSalesRescName() {
        return salesRescName;
    }

    public String getStorageName() {
        return storageName;
    }

    public String getNoSegName() {
        return noSegName;
    }

    public String getRecTime() {
        return recTime;
    }

    public String getBalaMode() {
        return balaMode;
    }

    public String getInitTime() {
        return initTime;
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

    public void setStorageId(String pStorageId) {
        storageId = pStorageId;
    }

    public void setRescState(String pRescState) {
        rescState = pRescState;
    }

    public void setState(String pState) {
        state = pState;
    }

    public void setEffDate(String pEffDate) {
        effDate = pEffDate;
    }

    public void setExpDate(String pExpDate) {
        expDate = pExpDate;
    }

    public void setImsiId(String pImsiId) {
        imsiId = pImsiId;
    }

    public void setNoSegId(String pNoSegId) {
        noSegId = pNoSegId;
    }

    public void setSalesRescName(String salesRescName) {
        this.salesRescName = salesRescName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public void setNoSegName(String noSegName) {
        this.noSegName = noSegName;
    }

    public void setRecTime(String recTime) {
        this.recTime = recTime;
    }

    public void setBalaMode(String balaMode) {
        this.balaMode = balaMode;
    }

    public void setInitTime(String initTime) {
        this.initTime = initTime;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("RESOURCE_INSTANCE_ID", this.rescInstanceId);
        hashMap.put("RESOURCE_INSTANCE_CODE", this.rescInstanceCode);
        hashMap.put("RESOURCE_LEVEL", this.rescLevel);
        hashMap.put("SALES_RESOURCE_ID", this.salesRescId);
        hashMap.put("STORAGE_ID", this.storageId);
        hashMap.put("RESOURCE_STATE", this.rescState);
        hashMap.put("STATE", this.state);
        hashMap.put("EFF_DATE", this.effDate);
        hashMap.put("EXP_DATE", this.expDate);
        hashMap.put("IMSI_ID", this.imsiId);
        hashMap.put("NO_SEG_ID", this.noSegId);

        hashMap.put("BALA_MODE", this.balaMode);
        hashMap.put("REC_TIME", this.recTime);
        hashMap.put("INIT_TIME", this.initTime);

        hashMap.put("LAN_ID", this.lanId);
        hashMap.put("REGION_ID", this.regionId);
        hashMap.put("EXCH_ID", this.exchId);
        hashMap.put("NO_SEG_NAME", this.noSegName);
        hashMap.put("STORAGE_NAME", this.storageName);
        hashMap.put("SALES_RESOURCE_NAME", this.salesRescName);
        hashMap.put("SELF_HELP_FLAG", this.selfhelpflag);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
            this.rescInstanceCode = (String) hashMap.get(
                    "RESOURCE_INSTANCE_CODE");
            this.rescLevel = (String) hashMap.get("RESOURCE_LEVEL");
            this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
            this.storageId = (String) hashMap.get("STORAGE_ID");
            this.rescState = (String) hashMap.get("RESOURCE_STATE");
            this.state = (String) hashMap.get("STATE");
            this.effDate = (String) hashMap.get("EFF_DATE");
            this.expDate = (String) hashMap.get("EXP_DATE");
            this.imsiId = (String) hashMap.get("IMSI_ID");
            this.noSegId = (String) hashMap.get("NO_SEG_ID");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("RESOURCE_INSTANCE_ID");

        return arrayList;
    }

    public String getTableName() {
        return "RC_NO";
    }

    public String getStorageId_old() {
        return storageId_old;
    }

    public void setStorageId_old(String storageId_old) {
        this.storageId_old = storageId_old;
    }

    public String getStorageName_old() {
        return storageName_old;
    }

    public void setStorageName_old(String storageName_old) {
        this.storageName_old = storageName_old;
    }

    public String getNobagId() {
        return nobagId;
    }

    public void setNobagId(String nobagId) {
        this.nobagId = nobagId;
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

    public String getEndCode() {
        return endCode;
    }

    public void setEndCode(String endCode) {
        this.endCode = endCode;
    }

    public String getStartCode() {
        return startCode;
    }

    public void setStartCode(String startCode) {
        this.startCode = startCode;
    }

    public String getNoNumber() {
        return noNumber;
    }

    public void setNoNumber(String noNumber) {
        this.noNumber = noNumber;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getSelfhelpflag() {
        return selfhelpflag;
    }

    public void setSelfhelpflag(String selfhelpflag) {
        this.selfhelpflag = selfhelpflag;
    }

    public String getCDoubleFlag() {
        return cDoubleFlag;
    }

    public void setCDoubleFlag(String doubleFlag) {
        cDoubleFlag = doubleFlag;
    }
}
