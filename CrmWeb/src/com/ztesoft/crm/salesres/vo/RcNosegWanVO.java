package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcNosegWanVO extends ValueObject implements VO {
    private String segId = "";
    private String segName = "";
    private String beginNo = "";
    private String endNo = "";
    private String noGrpId = "";
    private String lanId = "";
    private String state = "";
    private String operCode = "";
    private String createTime = "";
    private String departId = "";

    public RcNosegWanVO() {
    }

    public RcNosegWanVO(String psegId, String psegName, String pbeginNo,
        String pendNo, String pnoGrpId, String planId, String pstate,
        String poperCode, String pcreateTime, String pdepartId) {
        segId = psegId;
        segName = psegName;
        beginNo = pbeginNo;
        endNo = pendNo;
        noGrpId = pnoGrpId;
        lanId = planId;
        state = pstate;
        operCode = poperCode;
        createTime = pcreateTime;
        departId = pdepartId;
    }

    public String getSegId() {
        return segId;
    }

    public String getSegName() {
        return segName;
    }

    public String getBeginNo() {
        return beginNo;
    }

    public String getEndNo() {
        return endNo;
    }

    public String getNoGrpId() {
        return noGrpId;
    }

    public String getLanId() {
        return lanId;
    }

    public String getState() {
        return state;
    }

    public String getOperCode() {
        return operCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setSegId(String pSegId) {
        segId = pSegId;
    }

    public void setSegName(String pSegName) {
        segName = pSegName;
    }

    public void setBeginNo(String pBeginNo) {
        beginNo = pBeginNo;
    }

    public void setEndNo(String pEndNo) {
        endNo = pEndNo;
    }

    public void setNoGrpId(String pNoGrpId) {
        noGrpId = pNoGrpId;
    }

    public void setLanId(String pLanId) {
        lanId = pLanId;
    }

    public void setState(String pState) {
        state = pState;
    }

    public void setOperCode(String pOperCode) {
        operCode = pOperCode;
    }

    public void setCreateTime(String pCreateTime) {
        createTime = pCreateTime;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("SEG_ID", this.segId);
        hashMap.put("SEG_NAME", this.segName);
        hashMap.put("BEGIN_NO", this.beginNo);
        hashMap.put("END_NO", this.endNo);
        hashMap.put("NO_GROUP_ID", this.noGrpId);
        hashMap.put("LAN_ID", this.lanId);
        hashMap.put("STATE", this.state);
        hashMap.put("OPER_CODE", this.operCode);
        hashMap.put("CREATE_TIME", this.createTime);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.segId = (String) hashMap.get("SEG_ID");
            this.segName = (String) hashMap.get("SEG_NAME");
            this.beginNo = (String) hashMap.get("BEGIN_NO");
            this.endNo = (String) hashMap.get("END_NO");
            this.noGrpId = (String) hashMap.get("NO_GROUP_ID");
            this.lanId = (String) hashMap.get("LAN_ID");
            this.state = (String) hashMap.get("STATE");
            this.operCode = (String) hashMap.get("OPER_CODE");
            this.createTime = (String) hashMap.get("CREATE_TIME");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();

        return arrayList;
    }

    public String getTableName() {
        return "RC_NOSEG_WAN";
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }
}
