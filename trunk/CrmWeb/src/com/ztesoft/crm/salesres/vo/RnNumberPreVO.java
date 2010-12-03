package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RnNumberPreVO extends ValueObject implements VO {
    private String lanId = "";
    private String prodNo = "";
    private String custName = "";
    private String pwd = "";
    private String preTime = "";
    private String comments = "";
    private String rescInstanceId = "";
    private String validDate = "";
    private String noType = "";

    public RnNumberPreVO() {
    }

    public RnNumberPreVO(String planId, String pprodNo, String pcustName,
        String ppwd, String ppreTime, String pcomments, String prescInstanceId,
        String pvalidDate, String pnoType) {
        lanId = planId;
        prodNo = pprodNo;
        custName = pcustName;
        pwd = ppwd;
        preTime = ppreTime;
        comments = pcomments;
        rescInstanceId = prescInstanceId;
        validDate = pvalidDate;
        noType = pnoType;
    }

    public String getLanId() {
        return lanId;
    }

    public String getProdNo() {
        return prodNo;
    }

    public String getCustName() {
        return custName;
    }

    public String getPwd() {
        return pwd;
    }

    public String getPreTime() {
        return preTime;
    }

    public String getComments() {
        return comments;
    }

    public String getRescInstanceId() {
        return rescInstanceId;
    }

    public String getValidDate() {
        return validDate;
    }

    public String getNoType() {
        return noType;
    }

    public void setLanId(String pLanId) {
        lanId = pLanId;
    }

    public void setProdNo(String pProdNo) {
        prodNo = pProdNo;
    }

    public void setCustName(String pCustName) {
        custName = pCustName;
    }

    public void setPwd(String pPwd) {
        pwd = pPwd;
    }

    public void setPreTime(String pPreTime) {
        preTime = pPreTime;
    }

    public void setComments(String pComments) {
        comments = pComments;
    }

    public void setRescInstanceId(String pRescInstanceId) {
        rescInstanceId = pRescInstanceId;
    }

    public void setValidDate(String pValidDate) {
        validDate = pValidDate;
    }

    public void setNoType(String pNoType) {
        noType = pNoType;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("LAN_ID", this.lanId);
        hashMap.put("PRODUCT_NO", this.prodNo);
        hashMap.put("CUST_NAME", this.custName);
        hashMap.put("PWD", this.pwd);
        hashMap.put("PRE_TIME", this.preTime);
        hashMap.put("COMMENTS", this.comments);
        hashMap.put("RESOURCE_INSTANCE_ID", this.rescInstanceId);
        hashMap.put("VALID_DATE", this.validDate);
        hashMap.put("NO_TYPE", this.noType);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.lanId = (String) hashMap.get("LAN_ID");
            this.prodNo = (String) hashMap.get("PRODUCT_NO");
            this.custName = (String) hashMap.get("CUST_NAME");
            this.pwd = (String) hashMap.get("PWD");
            this.preTime = (String) hashMap.get("PRE_TIME");
            this.comments = (String) hashMap.get("COMMENTS");
            this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
            this.validDate = (String) hashMap.get("VALID_DATE");
            this.noType = (String) hashMap.get("NO_TYPE");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("LAN_ID");
        arrayList.add("PRODUCT_NO");

        return arrayList;
    }

    public String getTableName() {
        return "RN_NUMBER_PRE";
    }
}
