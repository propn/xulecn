package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcNobagRuleVO extends ValueObject implements VO {
    private String bagruleId = "";
    private String bagruleName = "";
    private String bagruleDesc = "";
    private String noNum = "";
    private String operId = "";
    private String operName = "";
    private String operCode = "";
    private String cDate = "";
    private String lanId = "";
    private List detailList;

    public RcNobagRuleVO() {
    }

    public RcNobagRuleVO(String pbagruleId, String pbagruleName,
        String pbagruleDesc, String pnoNum, String poperId, String pcDate,
        String planId) {
        bagruleId = pbagruleId;
        bagruleName = pbagruleName;
        bagruleDesc = pbagruleDesc;
        noNum = pnoNum;
        operId = poperId;
        cDate = pcDate;
        lanId = planId;
    }

    public List getDetailList() {
        if (detailList == null) {
            detailList = new ArrayList();
        }

        return detailList;
    }

    public void setDetailList(List detailList) {
        this.detailList = detailList;
    }

    public String getBagruleId() {
        return bagruleId;
    }

    public String getBagruleName() {
        return bagruleName;
    }

    public String getBagruleDesc() {
        return bagruleDesc;
    }

    public String getNoNum() {
        return noNum;
    }

    public String getOperId() {
        return operId;
    }

    public String getCDate() {
        return cDate;
    }

    public String getLanId() {
        return lanId;
    }

    public void setBagruleId(String pBagruleId) {
        bagruleId = pBagruleId;
    }

    public void setBagruleName(String pBagruleName) {
        bagruleName = pBagruleName;
    }

    public void setBagruleDesc(String pBagruleDesc) {
        bagruleDesc = pBagruleDesc;
    }

    public void setNoNum(String pNoNum) {
        noNum = pNoNum;
    }

    public void setOperId(String pOperId) {
        operId = pOperId;
    }

    public void setCDate(String pCDate) {
        cDate = pCDate;
    }

    public void setLanId(String pLanId) {
        lanId = pLanId;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("BAGRULE_ID", this.bagruleId);
        hashMap.put("BAGRULE_NAME", this.bagruleName);
        hashMap.put("BAGRULE_DESC", this.bagruleDesc);
        hashMap.put("NO_NUM", this.noNum);
        hashMap.put("OPER_ID", this.operId);
        hashMap.put("C_DATE", this.cDate);
        hashMap.put("LAN_ID", this.lanId);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.bagruleId = (String) hashMap.get("BAGRULE_ID");
            this.bagruleName = (String) hashMap.get("BAGRULE_NAME");
            this.bagruleDesc = (String) hashMap.get("BAGRULE_DESC");
            this.noNum = (String) hashMap.get("NO_NUM");
            this.operId = (String) hashMap.get("OPER_ID");
            this.cDate = (String) hashMap.get("C_DATE");
            this.lanId = (String) hashMap.get("LAN_ID");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();

        return arrayList;
    }

    public String getTableName() {
        return "RC_NOBAG_RULE";
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }
}
