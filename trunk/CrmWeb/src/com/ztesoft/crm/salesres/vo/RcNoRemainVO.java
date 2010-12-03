package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcNoRemainVO extends ValueObject implements VO {
    private String rescInstanceId = "";
    private String rescInstanceCode = "";
    private String remainId = "";

    public RcNoRemainVO() {
    }

    public RcNoRemainVO(String prescInstanceId, String prescInstanceCode,
        String premainId) {
        rescInstanceId = prescInstanceId;
        rescInstanceCode = prescInstanceCode;
        remainId = premainId;
    }

    public String getRescInstanceId() {
        return rescInstanceId;
    }

    public String getRescInstanceCode() {
        return rescInstanceCode;
    }

    public String getRemainId() {
        return remainId;
    }

    public void setRescInstanceId(String pRescInstanceId) {
        rescInstanceId = pRescInstanceId;
    }

    public void setRescInstanceCode(String pRescInstanceCode) {
        rescInstanceCode = pRescInstanceCode;
    }

    public void setRemainId(String pRemainId) {
        remainId = pRemainId;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("RESOURCE_INSTANCE_ID", this.rescInstanceId);
        hashMap.put("RESOURCE_INSTANCE_CODE", this.rescInstanceCode);
        hashMap.put("REMAIN_ID", this.remainId);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
            this.rescInstanceCode = (String) hashMap.get(
                    "RESOURCE_INSTANCE_CODE");
            this.remainId = (String) hashMap.get("REMAIN_ID");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("REMAIN_ID");
        arrayList.add("RESOURCE_INSTANCE_ID");

        return arrayList;
    }

    public String getTableName() {
        return "RC_NO_REMAIN";
    }
}
