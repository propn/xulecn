package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcNobagRuleDetaVO extends ValueObject implements VO {
    private String bagruleId = "";
    private String rescLevel = "";
    private String noNum = "";

    public RcNobagRuleDetaVO() {
    }

    public RcNobagRuleDetaVO(String pbagruleId, String prescLevel, String pnoNum) {
        bagruleId = pbagruleId;
        rescLevel = prescLevel;
        noNum = pnoNum;
    }

    public String getBagruleId() {
        return bagruleId;
    }

    public String getRescLevel() {
        return rescLevel;
    }

    public String getNoNum() {
        return noNum;
    }

    public void setBagruleId(String pBagruleId) {
        bagruleId = pBagruleId;
    }

    public void setRescLevel(String pRescLevel) {
        rescLevel = pRescLevel;
    }

    public void setNoNum(String pNoNum) {
        noNum = pNoNum;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("BAGRULE_ID", this.bagruleId);
        hashMap.put("RESOURCE_LEVEL", this.rescLevel);
        hashMap.put("NO_NUM", this.noNum);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.bagruleId = (String) hashMap.get("BAGRULE_ID");
            this.rescLevel = (String) hashMap.get("RESOURCE_LEVEL");
            this.noNum = (String) hashMap.get("NO_NUM");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();

        return arrayList;
    }

    public String getTableName() {
        return "RC_NOBAG_RULE_DETAIL";
    }
}
