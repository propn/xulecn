package com.ztesoft.crm.salesres.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;


public class RcNoPreVO extends ValueObject implements VO {
    private String rescInstanceId = "";
    private String rescInstanceCode = "";
    private String userName = "";
    private String pwd = "";
    private String validDate = "";
    private String comments = "";

    public RcNoPreVO() {
    }

    public RcNoPreVO(String prescInstanceId, String prescInstanceCode,
        String puserName, String ppwd, String pvalidDate, String pcomments) {
        rescInstanceId = prescInstanceId;
        rescInstanceCode = prescInstanceCode;
        userName = puserName;
        pwd = ppwd;
        validDate = pvalidDate;
        comments = pcomments;
    }

    public String getRescInstanceId() {
        return rescInstanceId;
    }

    public String getRescInstanceCode() {
        return rescInstanceCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getPwd() {
        return pwd;
    }

    public String getValidDate() {
        return validDate;
    }

    public String getComments() {
        return comments;
    }

    public void setRescInstanceId(String pRescInstanceId) {
        rescInstanceId = pRescInstanceId;
    }

    public void setRescInstanceCode(String pRescInstanceCode) {
        rescInstanceCode = pRescInstanceCode;
    }

    public void setUserName(String pUserName) {
        userName = pUserName;
    }

    public void setPwd(String pPwd) {
        pwd = pPwd;
    }

    public void setValidDate(String pValidDate) {
        validDate = pValidDate;
    }

    public void setComments(String pComments) {
        comments = pComments;
    }

    public HashMap unloadToHashMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("RESOURCE_INSTANCE_ID", this.rescInstanceId);
        hashMap.put("RESOURCE_INSTANCE_CODE", this.rescInstanceCode);
        hashMap.put("USER_NAME", this.userName);
        hashMap.put("PWD", this.pwd);
        hashMap.put("VALID_DATE", this.validDate);
        hashMap.put("COMMENTS", this.comments);

        return hashMap;
    }

    public void loadFromHashMap(HashMap hashMap) {
        if (hashMap != null) {
            this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
            this.rescInstanceCode = (String) hashMap.get(
                    "RESOURCE_INSTANCE_CODE");
            this.userName = (String) hashMap.get("USER_NAME");
            this.pwd = (String) hashMap.get("PWD");
            this.validDate = (String) hashMap.get("VALID_DATE");
            this.comments = (String) hashMap.get("COMMENTS");
        }
    }

    public List getKeyFields() {
        ArrayList arrayList = new ArrayList();

        return arrayList;
    }

    public String getTableName() {
        return "RC_NO_PRE";
    }
}
