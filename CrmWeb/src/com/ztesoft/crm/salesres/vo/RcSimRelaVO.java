package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcSimRelaVO extends ValueObject implements VO {
    private String serialId = "";
    private String mainImsi = "";
    private String mainIccid = "";
    private String additionalImsi = "";
    private String additionalIccid = "";

    public RcSimRelaVO() {
    }

    public RcSimRelaVO(String pserialId, String pmainImsi, String pmainIccid,
        String padditionalImsi, String padditionalIccid) {
        serialId = pserialId;
        mainImsi = pmainImsi;
        mainIccid = pmainIccid;
        additionalImsi = padditionalImsi;
        additionalIccid = padditionalIccid;
    }

    public String getSerialId() {
        return serialId;
    }

    public String getMainImsi() {
        return mainImsi;
    }

    public String getMainIccid() {
        return mainIccid;
    }

    public String getAdditionalImsi() {
        return additionalImsi;
    }

    public String getAdditionalIccid() {
        return additionalIccid;
    }

    public void setSerialId(String pSerialId) {
        serialId = pSerialId;
    }

    public void setMainImsi(String pMainImsi) {
        mainImsi = pMainImsi;
    }

    public void setMainIccid(String pMainIccid) {
        mainIccid = pMainIccid;
    }

    public void setAdditionalImsi(String pAdditionalImsi) {
        additionalImsi = pAdditionalImsi;
    }

    public void setAdditionalIccid(String pAdditionalIccid) {
        additionalIccid = pAdditionalIccid;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("SERIAL_ID", this.serialId);
        hashMap.put("MAIN_IMSI", this.mainImsi);
        hashMap.put("MAIN_ICCID", this.mainIccid);
        hashMap.put("ADDITIONAL_IMSI", this.additionalImsi);
        hashMap.put("ADDITIONAL_ICCID", this.additionalIccid);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.serialId = (String) hashMap.get("SERIAL_ID");
            this.mainImsi = (String) hashMap.get("MAIN_IMSI");
            this.mainIccid = (String) hashMap.get("MAIN_ICCID");
            this.additionalImsi = (String) hashMap.get("ADDITIONAL_IMSI");
            this.additionalIccid = (String) hashMap.get("ADDITIONAL_ICCID");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();

        return arrayList;
    }

    public String getTableName() {
        return "RC_SIM_RELA";
    }
}
