package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class StaffVO extends ValueObject implements VO {
	private String scope ="";
	private String partyRoleId = "";
	private String officeId = "";
	private String staffCode = "";//员工编码
	private String staffDesc = "";//员工描述
	private String password = "";//密码
	private String pwdvalidtype = "";//密码有效期类型
	private String updateTime = "";//
	private String loginStatus = "";
	private String loginCount = "";
	private String limitCount = "";//登录次数限制
	private String menuCode = "";
	private String channelSegmentId = "";//渠道ID
	private String channelSegmentName = "";//渠道名称

    private String partyId = "";
    private String gender = "";//性别
    private String birthPlace = "";//籍贯
    private String birthDate = "";//生日
    private String maritalStatus = "";//婚姻状态
    private String skill = "";//特长
    private String partyName = "";//姓名
    private String effDate = "";//生效日期
    private String state = "";//状态
    private String stateDate = "";//状态时间
    private String orgPartyId = "";//所属组织
    private String orgPartyName = "";//所属组织名称
    private String partyRoleType  = "";//参与人角色类型：电信内部参与人(90A)、对等运营商(90B)、合作伙伴(90B)
    private String orgManager = "";//是否组织管理者
    private String createDate = "";//创建日期
    private String expDate = "";//失效日期
    private String PromotionType = "";//add by lpy 071009 促销人类别
    private String devUserBelong = "";
    private String devUserBelongName = "";
    private String lanId = "";
    private String devOrgId = "";
    private String devOrgName = "";

    
    //
    private String officeName = "" ;
    private String businessId = "" ;	//营业区
    private String countyType = "" ;	//城乡标志
    
	public String getCountyType() {
		return countyType;
	}

	public void setCountyType(String countyType) {
		this.countyType = countyType;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getDevOrgName() {
		return devOrgName;
	}

	public void setDevOrgName(String devOrgName) {
		this.devOrgName = devOrgName;
	}

	public String getDevOrgId() {
		return devOrgId;
	}

	public void setDevOrgId(String devOrgId) {
		this.devOrgId = devOrgId;
	}

	public StaffVO() {}

	public StaffVO( String ppartyRoleId, String pofficeId, String pstaffCode, String pstaffDesc, String ppassword, String ppwdvalidtype, String pupdateTime, String ploginStatus, String ploginCount, String plimitCount, String pmenuCode, String pchannelSegmentId ) {
		partyRoleId = ppartyRoleId;
		officeId = pofficeId;
		staffCode = pstaffCode;
		staffDesc = pstaffDesc;
		password = ppassword;
		pwdvalidtype = ppwdvalidtype;
		updateTime = pupdateTime;
		loginStatus = ploginStatus;
		loginCount = ploginCount;
		limitCount = plimitCount;
		menuCode = pmenuCode;
		channelSegmentId = pchannelSegmentId ;
	}

	public String getPartyRoleId() {
		return partyRoleId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public String getStaffDesc() {
		return staffDesc;
	}

	public String getPassword() {
		return password;
	}

	public String getPwdvalidtype() {
		return pwdvalidtype;
	}

	public String getUpdateTime() {
		return updateTime;
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

	public void setPartyRoleId(String pPartyRoleId) {
		partyRoleId = pPartyRoleId;
	}

	public void setOfficeId(String pOfficeId) {
		officeId = pOfficeId;
	}

	public void setStaffCode(String pStaffCode) {
		staffCode = pStaffCode;
	}

	public void setStaffDesc(String pStaffDesc) {
		staffDesc = pStaffDesc;
	}

	public void setPassword(String pPassword) {
		password = pPassword;
	}

	public void setPwdvalidtype(String pPwdvalidtype) {
		pwdvalidtype = pPwdvalidtype;
	}

	public void setUpdateTime(String pUpdateTime) {
		updateTime = pUpdateTime;
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

    /**
     * 获取属性partyId
     * @return partyId
     */
    public String getPartyId()
    {
        return partyId;
    }
    
    /**
     * 设置属性partyId
     * @param partyId 属性值
     */
    public void setPartyId(String partyId)
    {
        this.partyId = partyId;
    }

    /**
     * 获取属性gender
     * @return gender
     */
    public String getGender()
    {
        return gender;
    }
    
    /**
     * 设置属性gender
     * @param gender 属性值
     */
    public void setGender(String gender)
    {
        this.gender = gender;
    }

    /**
     * 获取属性birthPlace
     * @return birthPlace
     */
    public String getBirthPlace()
    {
        return birthPlace;
    }
    
    /**
     * 设置属性birthPlace
     * @param birthPlace 属性值
     */
    public void setBirthPlace(String birthPlace)
    {
        this.birthPlace = birthPlace;
    }

    /**
     * 获取属性birthDate
     * @return birthDate
     */
    public String getBirthDate()
    {
        return birthDate;
    }
    
    /**
     * 设置属性birthDate
     * @param birthDate 属性值
     */
    public void setBirthDate(String birthDate)
    {
        this.birthDate = birthDate;
    }

    /**
     * 获取属性maritalStatus
     * @return maritalStatus
     */
    public String getMaritalStatus()
    {
        return maritalStatus;
    }
    
    /**
     * 设置属性maritalStatus
     * @param maritalStatus 属性值
     */
    public void setMaritalStatus(String maritalStatus)
    {
        this.maritalStatus = maritalStatus;
    }

    /**
     * 获取属性skill
     * @return skill
     */
    public String getSkill()
    {
        return skill;
    }
    
    /**
     * 设置属性skill
     * @param skill 属性值
     */
    public void setSkill(String skill)
    {
        this.skill = skill;
    }

    /**
     * 获取属性partyName
     * @return partyName
     */
    public String getPartyName()
    {
        return partyName;
    }
    
    /**
     * 设置属性partyName
     * @param partyName 属性值
     */
    public void setPartyName(String partyName)
    {
        this.partyName = partyName;
    }

    /**
     * 获取属性effDate
     * @return effDate
     */
    public String getEffDate()
    {
        return effDate;
    }
    
    /**
     * 设置属性effDate
     * @param effDate 属性值
     */
    public void setEffDate(String effDate)
    {
        this.effDate = effDate;
    }

    /**
     * 获取属性state
     * @return state
     */
    public String getState()
    {
        return state;
    }
    
    /**
     * 设置属性state
     * @param state 属性值
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * 获取属性stateDate
     * @return stateDate
     */
    public String getStateDate()
    {
        return stateDate;
    }
    
    /**
     * 设置属性stateDate
     * @param stateDate 属性值
     */
    public void setStateDate(String stateDate)
    {
        this.stateDate = stateDate;
    }

    /**
     * 获取属性orgPartyId
     * @return orgPartyId
     */
    public String getOrgPartyId()
    {
        return orgPartyId;
    }
    
    /**
     * 设置属性orgPartyId
     * @param orgPartyId 属性值
     */
    public void setOrgPartyId(String orgPartyId)
    {
        this.orgPartyId = orgPartyId;
    }
    
    /**
     * 获取属性PromotionType
     * @return PromotionType
     */
    public String getPromotionType()
    {
        return PromotionType;
    }
    
    /**
     * 设置属性PromotionType
     * @param PromotionType 属性值
     */
    public void setPromotionType(String PromotionType)
    {
        this.PromotionType = PromotionType;
    }

    /**
     * 获取属性partyRoleType 
     * @return partyRoleType 
     */
    public String getPartyRoleType ()
    {
        return partyRoleType ;
    }
    
    /**
     * 设置属性partyRoleType 
     * @param partyRoleType  属性值
     */
    public void setPartyRoleType (String partyRoleType )
    {
        this.partyRoleType  = partyRoleType ;
    }

    /**
     * 获取属性orgManager
     * @return orgManager
     */
    public String getOrgManager()
    {
        return orgManager;
    }
    
    /**
     * 设置属性orgManager
     * @param orgManager 属性值
     */
    public void setOrgManager(String orgManager)
    {
        this.orgManager = orgManager;
    }

    /**
     * 获取属性createDate
     * @return createDate
     */
    public String getCreateDate()
    {
        return createDate;
    }
    
    /**
     * 设置属性createDate
     * @param createDate 属性值
     */
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    /**
     * 获取属性expDate
     * @return expDate
     */
    public String getExpDate()
    {
        return expDate;
    }
    
    /**
     * 设置属性expDate
     * @param expDate 属性值
     */
    public void setExpDate(String expDate)
    {
        this.expDate = expDate;
    }

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ROLE_ID",this.partyRoleId);
		hashMap.put("OFFICE_ID",this.officeId);
		hashMap.put("STAFF_CODE",this.staffCode);
		hashMap.put("STAFF_DESC",this.staffDesc);
		hashMap.put("PASSWORD",this.password);
		hashMap.put("PWDVALIDTYPE",this.pwdvalidtype);
		hashMap.put("UPDATE_TIME",this.updateTime);
		hashMap.put("LOGIN_STATUS",this.loginStatus);
		hashMap.put("LOGIN_COUNT",this.loginCount);
		hashMap.put("LIMIT_COUNT",this.limitCount);
		hashMap.put("MENU_CODE",this.menuCode);
		hashMap.put("CHANNEL_SEGMENT_ID", this.channelSegmentId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyRoleId = (String) hashMap.get("PARTY_ROLE_ID");
			this.officeId = (String) hashMap.get("OFFICE_ID");
			this.staffCode = (String) hashMap.get("STAFF_CODE");
			this.staffDesc = (String) hashMap.get("STAFF_DESC");
			this.password = (String) hashMap.get("PASSWORD");
			this.pwdvalidtype = (String) hashMap.get("PWDVALIDTYPE");
			this.updateTime = (String) hashMap.get("UPDATE_TIME");
			this.loginStatus = (String) hashMap.get("LOGIN_STATUS");
			this.loginCount = (String) hashMap.get("LOGIN_COUNT");
			this.limitCount = (String) hashMap.get("LIMIT_COUNT");
			this.menuCode = (String) hashMap.get("MENU_CODE");
			this.channelSegmentId = (String)hashMap.get("CHANNEL_SEGMENT_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTY_ROLE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "STAFF";
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getChannelSegmentId() {
		return channelSegmentId;
	}

	public void setChannelSegmentId(String channelSegmentId) {
		this.channelSegmentId = channelSegmentId;
	}

	public String getChannelSegmentName() {
		return channelSegmentName;
	}

	public void setChannelSegmentName(String channelSegmentName) {
		this.channelSegmentName = channelSegmentName;
	}

	public String getOrgPartyName() {
		return orgPartyName;
	}

	public void setOrgPartyName(String orgPartyName) {
		this.orgPartyName = orgPartyName;
	}

	public String getDevUserBelong() {
		return devUserBelong;
	}

	public void setDevUserBelong(String devUserBelong) {
		this.devUserBelong = devUserBelong;
	}

	public String getDevUserBelongName() {
		return devUserBelongName;
	}

	public void setDevUserBelongName(String devUserBelongName) {
		this.devUserBelongName = devUserBelongName;
	}

	public String getLanId() {
		return lanId;
	}

	public void setLanId(String lanId) {
		this.lanId = lanId;
	}

}
