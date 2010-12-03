package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcNoRemainInfoVO extends ValueObject implements VO {
    private String remainId = "";
    private String operId = "";
    private String departId = "";
    private String startNo = "";
    private String endNo = "";
    private String remainTime = "";
    private String endRemainTime = "";
    private String remainNum = "";
    private String comments = "";
    private String remainFlag = "";
    private String relOperId = "";
    private String relDepartId = "";
    private String relTime = "";
    private String operCode = "";
    private String departName = "";
    private String relOperCode = "";
    private String relDepartName = "";
    private String storageId = "";
    private String storageName = "";
    private String rescState = "";

    public RcNoRemainInfoVO() {
    }

    public RcNoRemainInfoVO(String premainId, String poperId, String pdepartId,
        String pstartNo, String pendNo, String premainTime,
        String pendRemainTime, String premainNum, String pcomments,
        String premainFlag, String prelOperId, String prelDepartId,
        String prelTime) {
        remainId = premainId;
        operId = poperId;
        departId = pdepartId;
        startNo = pstartNo;
        endNo = pendNo;
        remainTime = premainTime;
        endRemainTime = pendRemainTime;
        remainNum = premainNum;
        comments = pcomments;
        remainFlag = premainFlag;
        relOperId = prelOperId;
        relDepartId = prelDepartId;
        relTime = prelTime;
    }

    public String getRescState() {
        return rescState;
    }

    public void setRescState(String rescState) {
        this.rescState = rescState;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getRemainId() {
        return remainId;
    }

    public String getOperId() {
        return operId;
    }

    public String getDepartId() {
        return departId;
    }

    public String getStartNo() {
        return startNo;
    }

    public String getEndNo() {
        return endNo;
    }

    public String getRemainTime() {
        return remainTime;
    }

    public String getEndRemainTime() {
        return endRemainTime;
    }

    public String getRemainNum() {
        return remainNum;
    }

    public String getComments() {
        return comments;
    }

    public String getRemainFlag() {
        return remainFlag;
    }

    public String getRelOperId() {
        return relOperId;
    }

    public String getRelDepartId() {
        return relDepartId;
    }

    public String getRelTime() {
        return relTime;
    }

    public void setRemainId(String pRemainId) {
        remainId = pRemainId;
    }

    public void setOperId(String pOperId) {
        operId = pOperId;
    }

    public void setDepartId(String pDepartId) {
        departId = pDepartId;
    }

    public void setStartNo(String pStartNo) {
        startNo = pStartNo;
    }

    public void setEndNo(String pEndNo) {
        endNo = pEndNo;
    }

    public void setRemainTime(String pRemainTime) {
        remainTime = pRemainTime;
    }

    public void setEndRemainTime(String pEndRemainTime) {
        endRemainTime = pEndRemainTime;
    }

    public void setRemainNum(String pRemainNum) {
        remainNum = pRemainNum;
    }

    public void setComments(String pComments) {
        comments = pComments;
    }

    public void setRemainFlag(String pRemainFlag) {
        remainFlag = pRemainFlag;
    }

    public void setRelOperId(String pRelOperId) {
        relOperId = pRelOperId;
    }

    public void setRelDepartId(String pRelDepartId) {
        relDepartId = pRelDepartId;
    }

    public void setRelTime(String pRelTime) {
        relTime = pRelTime;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public String getRelDepartName() {
        return relDepartName;
    }

    public void setRelDepartName(String relDepartName) {
        this.relDepartName = relDepartName;
    }

    public String getRelOperCode() {
        return relOperCode;
    }

    public void setRelOperCode(String relOperCode) {
        this.relOperCode = relOperCode;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("REMAIN_ID", this.remainId);
        hashMap.put("OPER_ID", this.operId);
        hashMap.put("DEPART_ID", this.departId);
        hashMap.put("START_NO", this.startNo);
        hashMap.put("END_NO", this.endNo);
        hashMap.put("REMAIN_TIME", this.remainTime);
        hashMap.put("END_REMAIN_TIME", this.endRemainTime);
        hashMap.put("REMAIN_NUM", this.remainNum);
        hashMap.put("COMMENTS", this.comments);
        hashMap.put("REMAIN_FLAG", this.remainFlag);
        hashMap.put("REL_OPER_ID", this.relOperId);
        hashMap.put("REL_DEPART_ID", this.relDepartId);
        hashMap.put("REL_TIME", this.relTime);
        hashMap.put("RESOURCE_STATE", this.rescState);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.remainId = (String) hashMap.get("REMAIN_ID");
            this.operId = (String) hashMap.get("OPER_ID");
            this.departId = (String) hashMap.get("DEPART_ID");
            this.startNo = (String) hashMap.get("START_NO");
            this.endNo = (String) hashMap.get("END_NO");
            this.remainTime = (String) hashMap.get("REMAIN_TIME");
            this.endRemainTime = (String) hashMap.get("END_REMAIN_TIME");
            this.remainNum = (String) hashMap.get("REMAIN_NUM");
            this.comments = (String) hashMap.get("COMMENTS");
            this.remainFlag = (String) hashMap.get("REMAIN_FLAG");
            this.relOperId = (String) hashMap.get("REL_OPER_ID");
            this.relDepartId = (String) hashMap.get("REL_DEPART_ID");
            this.relTime = (String) hashMap.get("REL_TIME");
            this.rescState = (String) hashMap.get("RESOURCE_STATE");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("REMAIN_ID");

        return arrayList;
    }

    public String getTableName() {
        return "RC_NO_REMAIN_INFO";
    }
}
