package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class StaffVO extends ValueObject implements VO {

	private String partyRoleId = "";
	private String partyRoleName = "";
	private String password = "";
	private String partyRoleType = "";
	private String orgManeger = "";
	private String partyId = "";
	private String officeId = "";
	private String state = "";
	private String effDate = "";
	private String expDate = "";
	private String pwdvalidtype = "";
	private String updateTime = "";
	private String incorStarttime = "";
	private String loginStatus = "";
	private String loginCount = "";
	private String limitCount = "";
	private String menuCode = "";
	private String orgPartyId = "";
	private String memo = "";
	private String createDate = "";
	private String sPartyId = "";
	private String party_role_code = "";
	
	private String orgName = "";
	private String staffCode = "";
	private String managerRole = "";
	
	private String devUserBelong = "";
	private String channelSegmentId = "";
	private String staffDesc = "";
	public StaffVO() {}

	public StaffVO( String ppartyRoleId, String ppartyRoleName, String ppassword, String ppartyRoleType, String porgManeger, String ppartyId, String pofficeId, String pstate, String peffDate, String pexpDate, String ppwdvalidtype, String pupdateTime, String pincorStarttime, String ploginStatus, String ploginCount, String plimitCount, String pmenuCode, String porgPartyId, String pmemo, String pcreateDate, String psPartyId ,
			String DevUserBelong,String ChannelSegmentId,String StaffDesc) {
		partyRoleId = ppartyRoleId;
		partyRoleName = ppartyRoleName;
		password = ppassword;
		partyRoleType = ppartyRoleType;
		orgManeger = porgManeger;
		partyId = ppartyId;
		officeId = pofficeId;
		state = pstate;
		effDate = peffDate;
		expDate = pexpDate;
		pwdvalidtype = ppwdvalidtype;
		updateTime = pupdateTime;
		incorStarttime = pincorStarttime;
		loginStatus = ploginStatus;
		loginCount = ploginCount;
		limitCount = plimitCount;
		menuCode = pmenuCode;
		orgPartyId = porgPartyId;
		memo = pmemo;
		createDate = pcreateDate;
		sPartyId = psPartyId;
		devUserBelong = DevUserBelong;
		 channelSegmentId = ChannelSegmentId;
		 staffDesc = StaffDesc;
	}

	public String getPartyRoleId() {
		return partyRoleId;
	}

	public String getPartyRoleName() {
		return partyRoleName;
	}

	public String getPassword() {
		return password;
	}

	public String getPartyRoleType() {
		return partyRoleType;
	}

	public String getOrgManeger() {
		return orgManeger;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public String getState() {
		return state;
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public String getPwdvalidtype() {
		return pwdvalidtype;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getIncorStarttime() {
		return incorStarttime;
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

	public String getMenuCode() {
		return menuCode;
	}

	public String getOrgPartyId() {
		return orgPartyId;
	}

	public String getMemo() {
		return memo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getSPartyId() {
		return sPartyId;
	}



	public void setPartyRoleId(String pPartyRoleId) {
		partyRoleId = pPartyRoleId;
	}

	public void setPartyRoleName(String pPartyRoleName) {
		partyRoleName = pPartyRoleName;
	}

	public void setPassword(String pPassword) {
		password = pPassword;
	}

	public void setPartyRoleType(String pPartyRoleType) {
		partyRoleType = pPartyRoleType;
	}

	public void setOrgManeger(String pOrgManeger) {
		orgManeger = pOrgManeger;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setOfficeId(String pOfficeId) {
		officeId = pOfficeId;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setEffDate(String pEffDate) {
		effDate = pEffDate;
	}

	public void setExpDate(String pExpDate) {
		expDate = pExpDate;
	}

	public void setPwdvalidtype(String pPwdvalidtype) {
		pwdvalidtype = pPwdvalidtype;
	}

	public void setUpdateTime(String pUpdateTime) {
		updateTime = pUpdateTime;
	}

	public void setIncorStarttime(String pIncorStarttime) {
		incorStarttime = pIncorStarttime;
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

	public void setMenuCode(String pMenuCode) {
		menuCode = pMenuCode;
	}

	public void setOrgPartyId(String pOrgPartyId) {
		orgPartyId = pOrgPartyId;
	}

	public void setMemo(String pMemo) {
		memo = pMemo;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setSPartyId(String pSPartyId) {
		sPartyId = pSPartyId;
	}



	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ROLE_ID",this.partyRoleId);
		hashMap.put("PARTY_ROLE_NAME",this.partyRoleName);
		hashMap.put("PASSWORD",this.password);
		hashMap.put("PARTY_ROLE_TYPE",this.partyRoleType);
		hashMap.put("ORG_MANEGER",this.orgManeger);
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("OFFICE_ID",this.officeId);
		hashMap.put("STATE",this.state);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		hashMap.put("PWDVALIDTYPE",this.pwdvalidtype);
		hashMap.put("UPDATE_TIME",this.updateTime);
		hashMap.put("INCOR_STARTTIME",this.incorStarttime);
		hashMap.put("LOGIN_STATUS",this.loginStatus);
		hashMap.put("LOGIN_COUNT",this.loginCount);
		hashMap.put("LIMIT_COUNT",this.limitCount);
		hashMap.put("MENU_CODE",this.menuCode);
		hashMap.put("ORG_PARTY_ID",this.orgPartyId);
		hashMap.put("MEMO",this.memo);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("S_PARTY_ID",this.sPartyId);
		
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyRoleId = (String) hashMap.get("PARTY_ROLE_ID");
			this.partyRoleName = (String) hashMap.get("PARTY_ROLE_NAME");
			this.password = (String) hashMap.get("PASSWORD");
			this.partyRoleType = (String) hashMap.get("PARTY_ROLE_TYPE");
			this.orgManeger = (String) hashMap.get("ORG_MANEGER");
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.officeId = (String) hashMap.get("OFFICE_ID");
			this.state = (String) hashMap.get("STATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.pwdvalidtype = (String) hashMap.get("PWDVALIDTYPE");
			this.updateTime = (String) hashMap.get("UPDATE_TIME");
			this.incorStarttime = (String) hashMap.get("INCOR_STARTTIME");
			this.loginStatus = (String) hashMap.get("LOGIN_STATUS");
			this.loginCount = (String) hashMap.get("LOGIN_COUNT");
			this.limitCount = (String) hashMap.get("LIMIT_COUNT");
			this.menuCode = (String) hashMap.get("MENU_CODE");
			this.orgPartyId = (String) hashMap.get("ORG_PARTY_ID");
			this.memo = (String) hashMap.get("MEMO");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.sPartyId = (String) hashMap.get("S_PARTY_ID");
			
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTY_ROLE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PARTY_ROLE";
	}

	public String getParty_role_code() {
		return party_role_code;
	}

	public void setParty_role_code(String party_role_code) {
		this.party_role_code = party_role_code;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	
	public String getManagerRole() {
		return managerRole;
	}

	public void setManagerRole(String managerRole) {
		this.managerRole = managerRole;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getChannelSegmentId() {
		return channelSegmentId;
	}

	public void setChannelSegmentId(String channelSegmentId) {
		this.channelSegmentId = channelSegmentId;
	}

	public String getDevUserBelong() {
		return devUserBelong;
	}

	public void setDevUserBelong(String devUserBelong) {
		this.devUserBelong = devUserBelong;
	}

	public String getStaffDesc() {
		return staffDesc;
	}

	public void setStaffDesc(String staffDesc) {
		this.staffDesc = staffDesc;
	}

}
