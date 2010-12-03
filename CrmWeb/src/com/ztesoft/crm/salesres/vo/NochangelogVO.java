package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class NochangelogVO extends ValueObject implements VO {
    private String logcode = "";
    private String rescInstanceCode = "";
    private String salesRescId = "";
    private String rescInstanceId = "";
    private String rescStateFrom = "";
    private String rescStateTo = "";
    private String stateFrom = "";
    private String stateTo = "";
    private String storageIdFrom = "";
    private String storageIdTo = "";
    private String rescLevelFrom = "";
    private String rescLevelTo = "";
    private String selfHelpFlagOld = "";
    private String selfHelpFlagNew = "";
    private String operCode = "";
    private String changedate = "";

    public NochangelogVO() {
    }

    public NochangelogVO(String plogcode, String prescInstanceCode,
        String psalesRescId, String prescInstanceId, String prescStateFrom,
        String prescStateTo, String pstateFrom, String pstateTo,
        String pstorageIdFrom, String pstorageIdTo, String prescLevelFrom,
        String prescLevelTo, String pselfHelpFlagOld, String pselfHelpFlagNew,
        String poperCode, String pchangedate) {
        logcode = plogcode;
        rescInstanceCode = prescInstanceCode;
        salesRescId = psalesRescId;
        rescInstanceId = prescInstanceId;
        rescStateFrom = prescStateFrom;
        rescStateTo = prescStateTo;
        stateFrom = pstateFrom;
        stateTo = pstateTo;
        storageIdFrom = pstorageIdFrom;
        storageIdTo = pstorageIdTo;
        rescLevelFrom = prescLevelFrom;
        rescLevelTo = prescLevelTo;
        selfHelpFlagOld = pselfHelpFlagOld;
        selfHelpFlagNew = pselfHelpFlagNew;
        operCode = poperCode;
        changedate = pchangedate;
    }

    public String getLogcode() {
        return logcode;
    }

    public String getRescInstanceCode() {
        return rescInstanceCode;
    }

    public String getSalesRescId() {
        return salesRescId;
    }

    public String getRescInstanceId() {
        return rescInstanceId;
    }

    public String getRescStateFrom() {
        return rescStateFrom;
    }

    public String getRescStateTo() {
        return rescStateTo;
    }

    public String getStateFrom() {
        return stateFrom;
    }

    public String getStateTo() {
        return stateTo;
    }

    public String getStorageIdFrom() {
        return storageIdFrom;
    }

    public String getStorageIdTo() {
        return storageIdTo;
    }

    public String getRescLevelFrom() {
        return rescLevelFrom;
    }

    public String getRescLevelTo() {
        return rescLevelTo;
    }

    public String getSelfHelpFlagOld() {
        return selfHelpFlagOld;
    }

    public String getSelfHelpFlagNew() {
        return selfHelpFlagNew;
    }

    public String getOperCode() {
        return operCode;
    }

    public String getChangedate() {
        return changedate;
    }

    public void setLogcode(String pLogcode) {
        logcode = pLogcode;
    }

    public void setRescInstanceCode(String pRescInstanceCode) {
        rescInstanceCode = pRescInstanceCode;
    }

    public void setSalesRescId(String pSalesRescId) {
        salesRescId = pSalesRescId;
    }

    public void setRescInstanceId(String pRescInstanceId) {
        rescInstanceId = pRescInstanceId;
    }

    public void setRescStateFrom(String pRescStateFrom) {
        rescStateFrom = pRescStateFrom;
    }

    public void setRescStateTo(String pRescStateTo) {
        rescStateTo = pRescStateTo;
    }

    public void setStateFrom(String pStateFrom) {
        stateFrom = pStateFrom;
    }

    public void setStateTo(String pStateTo) {
        stateTo = pStateTo;
    }

    public void setStorageIdFrom(String pStorageIdFrom) {
        storageIdFrom = pStorageIdFrom;
    }

    public void setStorageIdTo(String pStorageIdTo) {
        storageIdTo = pStorageIdTo;
    }

    public void setRescLevelFrom(String pRescLevelFrom) {
        rescLevelFrom = pRescLevelFrom;
    }

    public void setRescLevelTo(String pRescLevelTo) {
        rescLevelTo = pRescLevelTo;
    }

    public void setSelfHelpFlagOld(String pSelfHelpFlagOld) {
        selfHelpFlagOld = pSelfHelpFlagOld;
    }

    public void setSelfHelpFlagNew(String pSelfHelpFlagNew) {
        selfHelpFlagNew = pSelfHelpFlagNew;
    }

    public void setOperCode(String pOperCode) {
        operCode = pOperCode;
    }

    public void setChangedate(String pChangedate) {
        changedate = pChangedate;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("LOGCODE", this.logcode);
        hashMap.put("RESOURCE_INSTANCE_CODE", this.rescInstanceCode);
        hashMap.put("SALES_RESOURCE_ID", this.salesRescId);
        hashMap.put("RESOURCE_INSTANCE_ID", this.rescInstanceId);
        hashMap.put("RESOURCE_STATE_FROM", this.rescStateFrom);
        hashMap.put("RESOURCE_STATE_TO", this.rescStateTo);
        hashMap.put("STATE_FROM", this.stateFrom);
        hashMap.put("STATE_TO", this.stateTo);
        hashMap.put("STORAGE_ID_FROM", this.storageIdFrom);
        hashMap.put("STORAGE_ID_TO", this.storageIdTo);
        hashMap.put("RESC_LEVEL_FROM", this.rescLevelFrom);
        hashMap.put("RESC_LEVEL_TO", this.rescLevelTo);
        hashMap.put("SELF_HELP_FLAG_OLD", this.selfHelpFlagOld);
        hashMap.put("SELF_HELP_FLAG_NEW", this.selfHelpFlagNew);
        hashMap.put("OPER_CODE", this.operCode);
        hashMap.put("CHANGEDATE", this.changedate);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.logcode = (String) hashMap.get("LOGCODE");
            this.rescInstanceCode = (String) hashMap.get(
                    "RESOURCE_INSTANCE_CODE");
            this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
            this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
            this.rescStateFrom = (String) hashMap.get("RESOURCE_STATE_FROM");
            this.rescStateTo = (String) hashMap.get("RESOURCE_STATE_TO");
            this.stateFrom = (String) hashMap.get("STATE_FROM");
            this.stateTo = (String) hashMap.get("STATE_TO");
            this.storageIdFrom = (String) hashMap.get("STORAGE_ID_FROM");
            this.storageIdTo = (String) hashMap.get("STORAGE_ID_TO");
            this.rescLevelFrom = (String) hashMap.get("RESC_LEVEL_FROM");
            this.rescLevelTo = (String) hashMap.get("RESC_LEVEL_TO");
            this.selfHelpFlagOld = (String) hashMap.get("SELF_HELP_FLAG_OLD");
            this.selfHelpFlagNew = (String) hashMap.get("SELF_HELP_FLAG_NEW");
            this.operCode = (String) hashMap.get("OPER_CODE");
            this.changedate = (String) hashMap.get("CHANGEDATE");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();

        return arrayList;
    }

    public String getTableName() {
        return "NOCHANGELOG";
    }
}
