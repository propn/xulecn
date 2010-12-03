package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcNosegAppVO extends ValueObject implements VO {
    private String segId = "";
    private String state = "";
    private String lanId = "";
    private String operCode = "";
    private String departId = "";
    private String opertime = "";
    private String notes = "";
    private String segName = "";
    private String wanState = "";
    private String departname = "";
    private String opername = "";

    public RcNosegAppVO() {
    }

    public RcNosegAppVO(String psegId, String pstate, String planId,
        String poperCode, String pdepartId, String popertime, String pnotes,
        String psegName) {
        segId = psegId;
        state = pstate;
        lanId = planId;
        operCode = poperCode;
        departId = pdepartId;
        opertime = popertime;
        notes = pnotes;
        segName = psegName;
    }

    public String getSegId() {
        return segId;
    }

    public String getState() {
        return state;
    }

    public String getLanId() {
        return lanId;
    }

    public String getOperCode() {
        return operCode;
    }

    public String getDepartId() {
        return departId;
    }

    public String getOpertime() {
        return opertime;
    }

    public String getNotes() {
        return notes;
    }

    public void setSegId(String pSegId) {
        segId = pSegId;
    }

    public void setState(String pState) {
        state = pState;
    }

    public void setLanId(String pLanId) {
        lanId = pLanId;
    }

    public void setOperCode(String pOperCode) {
        operCode = pOperCode;
    }

    public void setDepartId(String pDepartId) {
        departId = pDepartId;
    }

    public void setOpertime(String pOpertime) {
        opertime = pOpertime;
    }

    public void setNotes(String pNotes) {
        notes = pNotes;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("SEG_ID", this.segId);
        hashMap.put("STATE", this.state);
        hashMap.put("LAN_ID", this.lanId);
        hashMap.put("OPER_CODE", this.operCode);
        hashMap.put("DEPART_ID", this.departId);
        hashMap.put("OPERTIME", this.opertime);
        hashMap.put("NOTES", this.notes);

        //		hashMap.put("SEG_NAME",this.segName);
        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.segId = (String) hashMap.get("SEG_ID");
            this.state = (String) hashMap.get("STATE");
            this.lanId = (String) hashMap.get("LAN_ID");
            this.operCode = (String) hashMap.get("OPER_CODE");
            this.departId = (String) hashMap.get("DEPART_ID");
            this.opertime = (String) hashMap.get("OPERTIME");
            this.notes = (String) hashMap.get("NOTES");

            //this.segName = (String) hashMap.get("SEG_NAME");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();

        return arrayList;
    }

    public String getTableName() {
        return "RC_NOSEG_APP";
    }

    public String getSegName() {
        return segName;
    }

    public void setSegName(String segName) {
        this.segName = segName;
    }

    public String getWanState() {
        return wanState;
    }

    public void setWanState(String wanState) {
        this.wanState = wanState;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    public String getOpername() {
        return opername;
    }

    public void setOpername(String opername) {
        this.opername = opername;
    }
}
