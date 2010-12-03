package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcNoRecRuleVO extends ValueObject implements VO {
    private String lanId = "";
    private String dayNum = "";
    private String lanName = "";
    private String oldDayNum = "";

    public RcNoRecRuleVO() {
    }

    public RcNoRecRuleVO(String planId, String pdayNum) {
        lanId = planId;
        dayNum = pdayNum;
    }

    public String getLanId() {
        return lanId;
    }

    public String getDayNum() {
        return dayNum;
    }

    public String getOldDayNum() {
        return dayNum;
    }

    public void setLanId(String pLanId) {
        lanId = pLanId;
    }

    public void setDayNum(String pDayNum) {
        dayNum = pDayNum;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("LAN_ID", this.lanId);
        hashMap.put("DAY_NUM", this.dayNum);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.lanId = (String) hashMap.get("LAN_ID");
            this.dayNum = (String) hashMap.get("DAY_NUM");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("LAN_ID");

        return arrayList;
    }

    public String getTableName() {
        return "RC_NO_REC_RULE";
    }

    public String getLanName() {
        return lanName;
    }

    public void setLanName(String lanName) {
        this.lanName = lanName;
    }
}
