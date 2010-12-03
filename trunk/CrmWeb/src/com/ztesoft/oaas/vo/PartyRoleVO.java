package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

/**
 * 1：因为原BSN使用party_id作为了参与人的组织id,现在数据割接和BSN影响较大。 
 		决定增加一个字段s_party_id做为我们理解的参与人id。 
 		这样系统中就是ORG_PARTY_ID，party_id两个字段都是参与人的组织id。 
 		系统管理的程序以ORG_PARTY_ID为准，但是要同时更新party_id字段。
 		原party_id = 现在的  s_party_id
 		现在的 party_id就是原来的org_party_id 
 * @author Administrator
 *
 */
public class PartyRoleVO extends ValueObject implements VO {

	private String partyRoleId = "";
	private String partyId = "";
	private String orgPartyId = "";
	private String partyRoleName = "";
	private String partyRoleType = "";
	private String orgManager = "";
	private String createDate = "";
	private String memo = "";
	private String state = "";
	private String effDate = "";
	private String expDate = "";
	private String officeId = "" ;
	private String password = "";
	private String sPartyId = "";
	
	private String pwdValidType = "";
	private String updateTime = "";
	private String loginStatus = "";
	private String loginCount = "";
	private String limitCount = "";
	private String incorStarttime = "";
	private String lanId="";
	
	//added 2008-11-06
	private String devOrgId = "";
	public void setDevOrgId( String devOrgId ){
		this.devOrgId = devOrgId ;
	}
	public String getDevorgid(){
		return this.devOrgId ;
	}//added end here

	public String getIncorStarttime() {
		return incorStarttime;
	}

	public void setIncorStarttime(String incorStarttime) {
		this.incorStarttime = incorStarttime;
	}

	public String getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(String limitCount) {
		this.limitCount = limitCount;
	}

	public String getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(String loginCount) {
		this.loginCount = loginCount;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getPwdValidType() {
		return pwdValidType;
	}

	public void setPwdValidType(String pwdValidType) {
		this.pwdValidType = pwdValidType;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getSPartyId() {
		return sPartyId;
	}

	public void setSPartyId(String partyId) {
		sPartyId = partyId;
	}

	public PartyRoleVO() {}

	public PartyRoleVO( String ppartyRoleId, String ppartyId, String porgPartyId, String ppartyRoleName, 
			String ppartyRoleType, String porgManager, String pcreateDate, String pmemo, String pstate, 
			String peffDate, String pexpDate, String pofficeId, String ppassword,
			String ppwdValidType,String pupdateTime,String ploginStatus,
			String ploginCount,String plimitCount,String pincorStarttime ) {
		
		partyRoleId = ppartyRoleId;
		sPartyId = ppartyId;
		orgPartyId = porgPartyId; this.partyId = porgPartyId ;//orgPartyId 和 partyId 都是所属组织,orgPartyId在CRM系统中使用,partyId在BSN系统中使用,名称不同,意义相同.
		partyRoleName = ppartyRoleName;
		partyRoleType = ppartyRoleType;
		orgManager = porgManager;
		createDate = pcreateDate;
		memo = pmemo;
		state = pstate;
		effDate = peffDate;
		expDate = pexpDate;
		password = ppassword;
		officeId = pofficeId;
		
		pwdValidType = ppwdValidType;
		updateTime = pupdateTime;
		loginStatus = ploginStatus;
		loginCount = ploginCount;
		limitCount = plimitCount;
		incorStarttime = pincorStarttime;
	}

	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password ;
	}
	public String getOfficeId(){
		return this.officeId;
	}
	public void setOfficeId(String officeId){
		this.officeId = officeId;
	}
	public String getPartyRoleId() {
		return partyRoleId;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getOrgPartyId() {
		return orgPartyId;
	}

	public String getPartyRoleName() {
		return partyRoleName;
	}

	public String getPartyRoleType() {
		return partyRoleType;
	}

	public String getOrgManager() {
		return orgManager;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getMemo() {
		return memo;
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

	public void setPartyRoleId(String pPartyRoleId) {
		partyRoleId = pPartyRoleId;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setOrgPartyId(String pOrgPartyId) {
		orgPartyId = pOrgPartyId;
	}

	public void setPartyRoleName(String pPartyRoleName) {
		partyRoleName = pPartyRoleName;
	}

	public void setPartyRoleType(String pPartyRoleType) {
		partyRoleType = pPartyRoleType;
	}

	public void setOrgManager(String pOrgManager) {
		orgManager = pOrgManager;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setMemo(String pMemo) {
		memo = pMemo;
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

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ROLE_ID",this.partyRoleId);
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("S_PARTY_ID",this.sPartyId);		
		hashMap.put("ORG_PARTY_ID",this.orgPartyId);
		hashMap.put("PARTY_ROLE_NAME",this.partyRoleName);
		hashMap.put("PARTY_ROLE_TYPE",this.partyRoleType);
		hashMap.put("ORG_MANEGER",this.orgManager);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("MEMO",this.memo);
		hashMap.put("STATE",this.state);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		hashMap.put("PASSWORD",this.password);
		hashMap.put("OFFICE_ID",this.officeId);
		
		hashMap.put("PWDVALIDTYPE",this.pwdValidType ) ;  
		hashMap.put("UPDATE_TIME",this.updateTime ) ;    
		hashMap.put("LOGIN_STATUS",this.loginStatus ) ;   
		hashMap.put("LOGIN_COUNT",this.loginCount ) ;    
		hashMap.put("LIMIT_COUNT",this.limitCount ) ;    
		hashMap.put("INCOR_STARTTIME",this.incorStarttime ) ;
		
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyRoleId = (String) hashMap.get("PARTY_ROLE_ID");
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.sPartyId = (String) hashMap.get("S_PARTY_ID");
			this.orgPartyId = (String) hashMap.get("ORG_PARTY_ID");
			this.partyRoleName = (String) hashMap.get("PARTY_ROLE_NAME");
			this.partyRoleType = (String) hashMap.get("PARTY_ROLE_TYPE");
			this.orgManager = (String) hashMap.get("ORG_MANEGER");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.memo = (String) hashMap.get("MEMO");
			this.state = (String) hashMap.get("STATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.password = (String)hashMap.get("PASSWORD");
			this.officeId = (String)hashMap.get("OFFICE_ID");
			
			this.pwdValidType   = (String) hashMap.get("PWDVALIDTYPE");                       
			this.updateTime     = (String) hashMap.get("UPDATE_TIME");                       
			this.loginStatus    = (String) hashMap.get("LOGIN_STATUS");                       
			this.loginCount     = (String) hashMap.get("LOGIN_COUNT");                       
			this.limitCount     = (String) hashMap.get("LIMIT_COUNT");                       
			this.incorStarttime = (String) hashMap.get("INCOR_STARTTIME"); 
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

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public String getLanId() {
		return lanId;
	}

}
