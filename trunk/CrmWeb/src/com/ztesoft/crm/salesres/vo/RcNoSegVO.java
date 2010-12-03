package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcNoSegVO extends ValueObject implements VO {
    private String noSegId = "";
    private String noSegName = "";
    private String starttime = "";
    private String intime = "";
    private String beginn = "";
    private String endno = "";
    private String state = "";
    private String imsiSegId = "";
    private String noGrpId = "";
    private String salesRescId = "";
    private String lanId = "";
    private String noGroupName = "";
    private String groupState = "";
    private String pdType = "";
    private String balaMode = "";
    private String salesRescName = "";
    private String segId = "";
    private String datacardflag = "";
    private String cDoubleFlag = "";

    public RcNoSegVO() {
    }

    public RcNoSegVO(String pnoSegId, String pnoSegName, String pstarttime,
        String pintime, String pbeginn, String pendno, String pstate,
        String pimsiSegId, String pnoGrpId, String psalesRescId, String psegId) {
        noSegId = pnoSegId;
        noSegName = pnoSegName;
        starttime = pstarttime;
        intime = pintime;
        beginn = pbeginn;
        endno = pendno;
        state = pstate;
        imsiSegId = pimsiSegId;
        noGrpId = pnoGrpId;
        salesRescId = psalesRescId;
        segId = psegId;
    }

    public String getSalesRescName() {
        return salesRescName;
    }

    public void setSalesRescName(String salesRescName) {
        this.salesRescName = salesRescName;
    }

    public String getNoSegId() {
        return noSegId;
    }

    public String getNoSegName() {
        return noSegName;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getIntime() {
        return intime;
    }

    public String getBeginn() {
        return beginn;
    }

    public String getEndno() {
        return endno;
    }

    public String getState() {
        return state;
    }

    public String getImsiSegId() {
        return imsiSegId;
    }

    public String getNoGrpId() {
        return noGrpId;
    }

    public String getSalesRescId() {
        return salesRescId;
    }

    public String getGroupState() {
        return groupState;
    }

    public String getNoGroupName() {
        return noGroupName;
    }

    public String getPdType() {
        return pdType;
    }

    public String getLanId() {
        return lanId;
    }

    public String getBalaMode() {
        return balaMode;
    }

    public void setNoSegId(String pNoSegId) {
        noSegId = pNoSegId;
    }

    public void setNoSegName(String pNoSegName) {
        noSegName = pNoSegName;
    }

    public void setStarttime(String pStarttime) {
        starttime = pStarttime;
    }

    public void setIntime(String pIntime) {
        intime = pIntime;
    }

    public void setBeginn(String pBeginn) {
        beginn = pBeginn;
    }

    public void setEndno(String pEndno) {
        endno = pEndno;
    }

    public void setState(String pState) {
        state = pState;
    }

    public void setImsiSegId(String pImsiSegId) {
        imsiSegId = pImsiSegId;
    }

    public void setNoGrpId(String pNoGrpId) {
        noGrpId = pNoGrpId;
    }

    public void setSalesRescId(String pSalesRescId) {
        salesRescId = pSalesRescId;
    }

    public void setGroupState(String groupState) {
        this.groupState = groupState;
    }

    public void setNoGroupName(String noGroupName) {
        this.noGroupName = noGroupName;
    }

    public void setPdType(String pdType) {
        this.pdType = pdType;
    }

    public void setLanId(String lanId) {
        this.lanId = lanId;
    }

    public void setBalaMode(String balaMode) {
        this.balaMode = balaMode;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("NO_SEG_ID", this.noSegId);
        hashMap.put("NO_SEG_NAME", this.noSegName);
        hashMap.put("STARTTIME", this.starttime);
        hashMap.put("INTIME", this.intime);
        hashMap.put("BEGINN", this.beginn);
        hashMap.put("ENDNO", this.endno);
        hashMap.put("STATE", this.state);
        hashMap.put("IMSI_SEG_ID", this.imsiSegId);
        hashMap.put("NO_GROUP_ID", this.noGrpId);
        hashMap.put("SALES_RESOURCE_ID", this.salesRescId);
        hashMap.put("SEG_ID", this.segId);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.noSegId = (String) hashMap.get("NO_SEG_ID");
            this.noSegName = (String) hashMap.get("NO_SEG_NAME");
            this.starttime = (String) hashMap.get("STARTTIME");
            this.intime = (String) hashMap.get("INTIME");
            this.beginn = (String) hashMap.get("BEGINN");
            this.endno = (String) hashMap.get("ENDNO");
            this.state = (String) hashMap.get("STATE");
            this.imsiSegId = (String) hashMap.get("IMSI_SEG_ID");
            this.noGrpId = (String) hashMap.get("NO_GROUP_ID");
            this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
            this.segId = (String) hashMap.get("SEG_ID");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("NO_SEG_ID");

        return arrayList;
    }

    public String getTableName() {
        return "RC_NO_SEG";
    }

    public String getDatacardflag() {
        return datacardflag;
    }

    public void setDatacardflag(String datacardflag) {
        this.datacardflag = datacardflag;
    }

    public String getSegId() {
        return segId;
    }

    public void setSegId(String segId) {
        this.segId = segId;
    }

    public String getCDoubleFlag() {
        return cDoubleFlag;
    }

    public void setCDoubleFlag(String doubleFlag) {
        cDoubleFlag = doubleFlag;
    }
}
