package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcImsiSegVO extends ValueObject implements VO {
    private String imsiSegId = "";
    private String startImsiId = "";
    private String endImsiId = "";
    private String initTime = "";
    private String startTime = "";
    private String state = "";
    private String noGrpId = "";

    public RcImsiSegVO() {
    }

    public RcImsiSegVO(String pimsiSegId, String pstartImsiId,
        String pendImsiId, String pinitTime, String pstartTime, String pstate,
        String pnoGrpId) {
        imsiSegId = pimsiSegId;
        startImsiId = pstartImsiId;
        endImsiId = pendImsiId;
        initTime = pinitTime;
        startTime = pstartTime;
        state = pstate;
        noGrpId = pnoGrpId;
    }

    public String getImsiSegId() {
        return imsiSegId;
    }

    public String getStartImsiId() {
        return startImsiId;
    }

    public String getEndImsiId() {
        return endImsiId;
    }

    public String getInitTime() {
        return initTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getState() {
        return state;
    }

    public String getNoGrpId() {
        return noGrpId;
    }

    public void setImsiSegId(String pImsiSegId) {
        imsiSegId = pImsiSegId;
    }

    public void setStartImsiId(String pStartImsiId) {
        startImsiId = pStartImsiId;
    }

    public void setEndImsiId(String pEndImsiId) {
        endImsiId = pEndImsiId;
    }

    public void setInitTime(String pInitTime) {
        initTime = pInitTime;
    }

    public void setStartTime(String pStartTime) {
        startTime = pStartTime;
    }

    public void setState(String pState) {
        state = pState;
    }

    public void setNoGrpId(String pNoGrpId) {
        noGrpId = pNoGrpId;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("IMSI_SEG_ID", this.imsiSegId);
        hashMap.put("START_IMSI_ID", this.startImsiId);
        hashMap.put("END_IMSI_ID", this.endImsiId);
        hashMap.put("INIT_TIME", this.initTime);
        hashMap.put("START_TIME", this.startTime);
        hashMap.put("STATE", this.state);
        hashMap.put("NO_GROUP_ID", this.noGrpId);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.imsiSegId = (String) hashMap.get("IMSI_SEG_ID");
            this.startImsiId = (String) hashMap.get("START_IMSI_ID");
            this.endImsiId = (String) hashMap.get("END_IMSI_ID");
            this.initTime = (String) hashMap.get("INIT_TIME");
            this.startTime = (String) hashMap.get("START_TIME");
            this.state = (String) hashMap.get("STATE");
            this.noGrpId = (String) hashMap.get("NO_GROUP_ID");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();

        return arrayList;
    }

    public String getTableName() {
        return "RC_IMSI_SEG";
    }
}
