package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcNoImsiRelateVO extends ValueObject implements VO {
    private String rescInstanceId = "";
    private String wTime = "";
    private String imsiId = "";

    public RcNoImsiRelateVO() {
    }

    public RcNoImsiRelateVO(String prescInstanceId, String pwTime,
        String pimsiId) {
        rescInstanceId = prescInstanceId;
        wTime = pwTime;
        imsiId = pimsiId;
    }

    public String getRescInstanceId() {
        return rescInstanceId;
    }

    public String getWTime() {
        return wTime;
    }

    public String getImsiId() {
        return imsiId;
    }

    public void setRescInstanceId(String pRescInstanceId) {
        rescInstanceId = pRescInstanceId;
    }

    public void setWTime(String pWTime) {
        wTime = pWTime;
    }

    public void setImsiId(String pImsiId) {
        imsiId = pImsiId;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("RESOURCE_INSTANCE_ID", this.rescInstanceId);
        hashMap.put("W_TIME", this.wTime);
        hashMap.put("IMSI_ID", this.imsiId);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
            this.wTime = (String) hashMap.get("W_TIME");
            this.imsiId = (String) hashMap.get("IMSI_ID");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();

        return arrayList;
    }

    public String getTableName() {
        return "RC_NO_IMSI_RELATE";
    }
}
