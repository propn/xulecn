package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcNobagNoVO extends ValueObject implements VO {
    private String nobagId = "";
    private String bagruleId = "";
    private String rescInstanceId = "";
    private String rescInstanceCode = "";
    private String bagruleName = "";
    private String noNum = "";

    public RcNobagNoVO() {
    }

    public RcNobagNoVO(String pnobagId, String pbagruleId,
        String prescInstanceId, String prescInstanceCode) {
        nobagId = pnobagId;
        bagruleId = pbagruleId;
        rescInstanceId = prescInstanceId;
        rescInstanceCode = prescInstanceCode;
    }

    public String getNoNum() {
        return noNum;
    }

    public void setNoNum(String noNum) {
        this.noNum = noNum;
    }

    public String getNobagId() {
        return nobagId;
    }

    public String getBagruleId() {
        return bagruleId;
    }

    public String getRescInstanceId() {
        return rescInstanceId;
    }

    public String getRescInstanceCode() {
        return rescInstanceCode;
    }

    public void setNobagId(String pNobagId) {
        nobagId = pNobagId;
    }

    public void setBagruleId(String pBagruleId) {
        bagruleId = pBagruleId;
    }

    public void setRescInstanceId(String pRescInstanceId) {
        rescInstanceId = pRescInstanceId;
    }

    public void setRescInstanceCode(String pRescInstanceCode) {
        rescInstanceCode = pRescInstanceCode;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("NOBAG_ID", this.nobagId);
        hashMap.put("BAGRULE_ID", this.bagruleId);
        hashMap.put("RESOURCE_INSTANCE_ID", this.rescInstanceId);
        hashMap.put("RESOURCE_INSTANCE_CODE", this.rescInstanceCode);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.nobagId = (String) hashMap.get("NOBAG_ID");
            this.bagruleId = (String) hashMap.get("BAGRULE_ID");
            this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
            this.rescInstanceCode = (String) hashMap.get(
                    "RESOURCE_INSTANCE_CODE");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();

        return arrayList;
    }

    public String getTableName() {
        return "RC_NOBAG_NO";
    }

    public String getBagruleName() {
        return bagruleName;
    }

    public void setBagruleName(String bagruleName) {
        this.bagruleName = bagruleName;
    }
}
