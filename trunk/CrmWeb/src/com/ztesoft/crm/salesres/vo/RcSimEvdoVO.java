package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.crm.salesres.common.ObjUtil;


public class RcSimEvdoVO extends RcSimVO {
    private String attrname = "";
    private String attrid = "";
    private String attrvalue = "";
    private String ord = "";

    // 以下是evdo卡的属性
    private String hrpdUpp = "";
    private String hrpdSs = "";

    public RcSimEvdoVO() {
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = super.unloadToHashMap();
        hashMap.put("HRPDUPP", this.hrpdUpp);
        hashMap.put("HRPD_SS", this.hrpdSs);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            super.loadFromHashMap(hashMap);
            this.hrpdUpp = (String) hashMap.get("HRPDUPP");
            this.hrpdSs = (String) hashMap.get("HRPD_SS");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("RESOURCE_INSTANCE_ID");
        arrayList.add("ATTRNAME");
        arrayList.add("ATTRID");

        return arrayList;
    }

    public String getTableName() {
        return "RC_SIM_EVDO";
    }

    public String getHrpdSs() {
        return hrpdSs;
    }

    public void setHrpdSs(String hrpdSs) {
        this.hrpdSs = hrpdSs;
    }

    public String getHrpdUpp() {
        return hrpdUpp;
    }

    public void setHrpdUpp(String hrpdUpp) {
        this.hrpdUpp = hrpdUpp;
    }

    public String getAttrid() {
        return attrid;
    }

    public void setAttrid(String attrid) {
        this.attrid = attrid;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public String getAttrvalue() {
        return attrvalue;
    }

    public void setAttrvalue(String attrvalue) {
        this.attrvalue = attrvalue;
    }

    public String getOrd() {
        return ord;
    }

    public void setOrd(String ord) {
        this.ord = ord;
    }

    public void copyProps(RcSimVO vo) {
        if (vo != null) {
            ObjUtil.copyProperties(this, vo);
        }

        return;
    }
}
