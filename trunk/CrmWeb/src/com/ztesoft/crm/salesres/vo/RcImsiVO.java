package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcImsiVO extends ValueObject implements VO {
    private String imsiId = "";
    private String imsiSegId = "";
    private String rescInstanceId = "";
    private String startImsiId = "";
    private String endImsiId = "";
    private String state = "";
    private String masterFlag = "";
    private String rescInstanceCode = "";
    private String recTime = "";

    public RcImsiVO() {
    }

    public RcImsiVO(String pimsiId, String pimsiSegId, String prescInstanceId,
        String pstate, String pmasterFlag) {
        imsiId = pimsiId;
        imsiSegId = pimsiSegId;
        rescInstanceId = prescInstanceId;
        state = pstate;
        masterFlag = pmasterFlag;
    }

    public String getRecTime() {
        return recTime;
    }

    public void setRecTime(String recTime) {
        this.recTime = recTime;
    }

    public String getImsiId() {
        return imsiId;
    }

    public String getImsiSegId() {
        return imsiSegId;
    }

    public String getRescInstanceId() {
        return rescInstanceId;
    }

    public String getState() {
        return state;
    }

    public String getMasterFlag() {
        return masterFlag;
    }

    public String getRescInstanceCode() {
        return rescInstanceCode;
    }

    public void setImsiId(String pImsiId) {
        imsiId = pImsiId;
    }

    public void setImsiSegId(String pImsiSegId) {
        imsiSegId = pImsiSegId;
    }

    public void setRescInstanceId(String pRescInstanceId) {
        rescInstanceId = pRescInstanceId;
    }

    public void setState(String pState) {
        state = pState;
    }

    public void setMasterFlag(String pMasterFlag) {
        masterFlag = pMasterFlag;
    }

    public void setRescInstanceCode(String rescInstanceCode) {
        this.rescInstanceCode = rescInstanceCode;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("IMSI_ID", this.imsiId);
        hashMap.put("IMSI_SEG_ID", this.imsiSegId);
        hashMap.put("RESOURCE_INSTANCE_ID", this.rescInstanceId);
        hashMap.put("STATE", this.state);
        hashMap.put("MASTER_FLAG", this.masterFlag);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.imsiId = (String) hashMap.get("IMSI_ID");
            this.imsiSegId = (String) hashMap.get("IMSI_SEG_ID");
            this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
            this.state = (String) hashMap.get("STATE");
            this.masterFlag = (String) hashMap.get("MASTER_FLAG");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();

        return arrayList;
    }

    public String getTableName() {
        return "RC_IMSI";
    }

	public String getStartImsiId() {
		return startImsiId;
	}

	public void setStartImsiId(String startImsiId) {
		this.startImsiId = startImsiId;
	}

	public String getEndImsiId() {
		return endImsiId;
	}

	public void setEndImsiId(String endImsiId) {
		this.endImsiId = endImsiId;
	}
}
