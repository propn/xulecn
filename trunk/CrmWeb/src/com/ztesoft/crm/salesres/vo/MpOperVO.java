package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class MpOperVO extends ValueObject implements VO {

	private String lanId = "";
	private String operId = "";
	private String operCode = "";
	private String password = "";
	private String operGrade = "";
	private String departId = "";
	private String pwdday = "";
	private String updateTime = "";
	private String operName = "";
	private String menuCode = "";
	private String jobType = "";
	private String validFlag = "";
	private String comments = "";
	private String createDate = "";
	private String loginStatus = "";
	private String loginCount = "";
	private String limitCount = "";
	private String employeeId = "";
	private String departName="";
	
	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public MpOperVO() {}

	public MpOperVO( String planId, String poperId, String poperCode, String ppassword, String poperGrade, String pdepartId, String ppwdday, String pupdateTime, String poperName, String pmenuCode, String pjobType, String pvalidFlag, String pcomments, String pcreateDate, String ploginStatus, String ploginCount, String plimitCount, String pemployeeId ) {
		lanId = planId;
		operId = poperId;
		operCode = poperCode;
		password = ppassword;
		operGrade = poperGrade;
		departId = pdepartId;
		pwdday = ppwdday;
		updateTime = pupdateTime;
		operName = poperName;
		menuCode = pmenuCode;
		jobType = pjobType;
		validFlag = pvalidFlag;
		comments = pcomments;
		createDate = pcreateDate;
		loginStatus = ploginStatus;
		loginCount = ploginCount;
		limitCount = plimitCount;
		employeeId = pemployeeId;
	}

	public String getLanId() {
		return lanId;
	}

	public String getOperId() {
		return operId;
	}

	public String getOperCode() {
		return operCode;
	}

	public String getPassword() {
		return password;
	}

	public String getOperGrade() {
		return operGrade;
	}

	public String getDepartId() {
		return departId;
	}

	public String getPwdday() {
		return pwdday;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getOperName() {
		return operName;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public String getJobType() {
		return jobType;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public String getComments() {
		return comments;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public String getLoginCount() {
		return loginCount;
	}

	public String getLimitCount() {
		return limitCount;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setOperCode(String pOperCode) {
		operCode = pOperCode;
	}

	public void setPassword(String pPassword) {
		password = pPassword;
	}

	public void setOperGrade(String pOperGrade) {
		operGrade = pOperGrade;
	}

	public void setDepartId(String pDepartId) {
		departId = pDepartId;
	}

	public void setPwdday(String pPwdday) {
		pwdday = pPwdday;
	}

	public void setUpdateTime(String pUpdateTime) {
		updateTime = pUpdateTime;
	}

	public void setOperName(String pOperName) {
		operName = pOperName;
	}

	public void setMenuCode(String pMenuCode) {
		menuCode = pMenuCode;
	}

	public void setJobType(String pJobType) {
		jobType = pJobType;
	}

	public void setValidFlag(String pValidFlag) {
		validFlag = pValidFlag;
	}

	public void setComments(String pComments) {
		comments = pComments;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setLoginStatus(String pLoginStatus) {
		loginStatus = pLoginStatus;
	}

	public void setLoginCount(String pLoginCount) {
		loginCount = pLoginCount;
	}

	public void setLimitCount(String pLimitCount) {
		limitCount = pLimitCount;
	}

	public void setEmployeeId(String pEmployeeId) {
		employeeId = pEmployeeId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("OPER_CODE",this.operCode);
		hashMap.put("PASSWORD",this.password);
		hashMap.put("OPER_GRADE",this.operGrade);
		hashMap.put("DEPART_ID",this.departId);
		hashMap.put("PWDDAY",this.pwdday);
		hashMap.put("UPDATE_TIME",this.updateTime);
		hashMap.put("OPER_NAME",this.operName);
		hashMap.put("MENU_CODE",this.menuCode);
		hashMap.put("JOB_TYPE",this.jobType);
		hashMap.put("VALID_FLAG",this.validFlag);
		hashMap.put("COMMENTS",this.comments);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("LOGIN_STATUS",this.loginStatus);
		hashMap.put("LOGIN_COUNT",this.loginCount);
		hashMap.put("LIMIT_COUNT",this.limitCount);
		hashMap.put("EMPLOYEE_ID",this.employeeId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.lanId = (String) hashMap.get("LAN_ID");
			this.operId = (String) hashMap.get("OPER_ID");
			this.operCode = (String) hashMap.get("OPER_CODE");
			this.password = (String) hashMap.get("PASSWORD");
			this.operGrade = (String) hashMap.get("OPER_GRADE");
			this.departId = (String) hashMap.get("DEPART_ID");
			this.pwdday = (String) hashMap.get("PWDDAY");
			this.updateTime = (String) hashMap.get("UPDATE_TIME");
			this.operName = (String) hashMap.get("OPER_NAME");
			this.menuCode = (String) hashMap.get("MENU_CODE");
			this.jobType = (String) hashMap.get("JOB_TYPE");
			this.validFlag = (String) hashMap.get("VALID_FLAG");
			this.comments = (String) hashMap.get("COMMENTS");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.loginStatus = (String) hashMap.get("LOGIN_STATUS");
			this.loginCount = (String) hashMap.get("LOGIN_COUNT");
			this.limitCount = (String) hashMap.get("LIMIT_COUNT");
			this.employeeId = (String) hashMap.get("EMPLOYEE_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "MP_OPERATOR";
	}

}
