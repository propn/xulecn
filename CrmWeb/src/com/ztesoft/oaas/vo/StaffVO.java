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
	private String staffCode = "";//Ա������
	private String staffDesc = "";//Ա������
	private String password = "";//����
	private String pwdvalidtype = "";//������Ч������
	private String updateTime = "";//
	private String loginStatus = "";
	private String loginCount = "";
	private String limitCount = "";//��¼��������
	private String menuCode = "";
	private String channelSegmentId = "";//����ID
	private String channelSegmentName = "";//��������

    private String partyId = "";
    private String gender = "";//�Ա�
    private String birthPlace = "";//����
    private String birthDate = "";//����
    private String maritalStatus = "";//����״̬
    private String skill = "";//�س�
    private String partyName = "";//����
    private String effDate = "";//��Ч����
    private String state = "";//״̬
    private String stateDate = "";//״̬ʱ��
    private String orgPartyId = "";//������֯
    private String orgPartyName = "";//������֯����
    private String partyRoleType  = "";//�����˽�ɫ���ͣ������ڲ�������(90A)���Ե���Ӫ��(90B)���������(90B)
    private String orgManager = "";//�Ƿ���֯������
    private String createDate = "";//��������
    private String expDate = "";//ʧЧ����
    private String PromotionType = "";//add by lpy 071009 ���������
    private String devUserBelong = "";
    private String devUserBelongName = "";
    private String lanId = "";
    private String devOrgId = "";
    private String devOrgName = "";

    
    //
    private String officeName = "" ;
    private String businessId = "" ;	//Ӫҵ��
    private String countyType = "" ;	//�����־
    
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
     * ��ȡ����partyId
     * @return partyId
     */
    public String getPartyId()
    {
        return partyId;
    }
    
    /**
     * ��������partyId
     * @param partyId ����ֵ
     */
    public void setPartyId(String partyId)
    {
        this.partyId = partyId;
    }

    /**
     * ��ȡ����gender
     * @return gender
     */
    public String getGender()
    {
        return gender;
    }
    
    /**
     * ��������gender
     * @param gender ����ֵ
     */
    public void setGender(String gender)
    {
        this.gender = gender;
    }

    /**
     * ��ȡ����birthPlace
     * @return birthPlace
     */
    public String getBirthPlace()
    {
        return birthPlace;
    }
    
    /**
     * ��������birthPlace
     * @param birthPlace ����ֵ
     */
    public void setBirthPlace(String birthPlace)
    {
        this.birthPlace = birthPlace;
    }

    /**
     * ��ȡ����birthDate
     * @return birthDate
     */
    public String getBirthDate()
    {
        return birthDate;
    }
    
    /**
     * ��������birthDate
     * @param birthDate ����ֵ
     */
    public void setBirthDate(String birthDate)
    {
        this.birthDate = birthDate;
    }

    /**
     * ��ȡ����maritalStatus
     * @return maritalStatus
     */
    public String getMaritalStatus()
    {
        return maritalStatus;
    }
    
    /**
     * ��������maritalStatus
     * @param maritalStatus ����ֵ
     */
    public void setMaritalStatus(String maritalStatus)
    {
        this.maritalStatus = maritalStatus;
    }

    /**
     * ��ȡ����skill
     * @return skill
     */
    public String getSkill()
    {
        return skill;
    }
    
    /**
     * ��������skill
     * @param skill ����ֵ
     */
    public void setSkill(String skill)
    {
        this.skill = skill;
    }

    /**
     * ��ȡ����partyName
     * @return partyName
     */
    public String getPartyName()
    {
        return partyName;
    }
    
    /**
     * ��������partyName
     * @param partyName ����ֵ
     */
    public void setPartyName(String partyName)
    {
        this.partyName = partyName;
    }

    /**
     * ��ȡ����effDate
     * @return effDate
     */
    public String getEffDate()
    {
        return effDate;
    }
    
    /**
     * ��������effDate
     * @param effDate ����ֵ
     */
    public void setEffDate(String effDate)
    {
        this.effDate = effDate;
    }

    /**
     * ��ȡ����state
     * @return state
     */
    public String getState()
    {
        return state;
    }
    
    /**
     * ��������state
     * @param state ����ֵ
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * ��ȡ����stateDate
     * @return stateDate
     */
    public String getStateDate()
    {
        return stateDate;
    }
    
    /**
     * ��������stateDate
     * @param stateDate ����ֵ
     */
    public void setStateDate(String stateDate)
    {
        this.stateDate = stateDate;
    }

    /**
     * ��ȡ����orgPartyId
     * @return orgPartyId
     */
    public String getOrgPartyId()
    {
        return orgPartyId;
    }
    
    /**
     * ��������orgPartyId
     * @param orgPartyId ����ֵ
     */
    public void setOrgPartyId(String orgPartyId)
    {
        this.orgPartyId = orgPartyId;
    }
    
    /**
     * ��ȡ����PromotionType
     * @return PromotionType
     */
    public String getPromotionType()
    {
        return PromotionType;
    }
    
    /**
     * ��������PromotionType
     * @param PromotionType ����ֵ
     */
    public void setPromotionType(String PromotionType)
    {
        this.PromotionType = PromotionType;
    }

    /**
     * ��ȡ����partyRoleType 
     * @return partyRoleType 
     */
    public String getPartyRoleType ()
    {
        return partyRoleType ;
    }
    
    /**
     * ��������partyRoleType 
     * @param partyRoleType  ����ֵ
     */
    public void setPartyRoleType (String partyRoleType )
    {
        this.partyRoleType  = partyRoleType ;
    }

    /**
     * ��ȡ����orgManager
     * @return orgManager
     */
    public String getOrgManager()
    {
        return orgManager;
    }
    
    /**
     * ��������orgManager
     * @param orgManager ����ֵ
     */
    public void setOrgManager(String orgManager)
    {
        this.orgManager = orgManager;
    }

    /**
     * ��ȡ����createDate
     * @return createDate
     */
    public String getCreateDate()
    {
        return createDate;
    }
    
    /**
     * ��������createDate
     * @param createDate ����ֵ
     */
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    /**
     * ��ȡ����expDate
     * @return expDate
     */
    public String getExpDate()
    {
        return expDate;
    }
    
    /**
     * ��������expDate
     * @param expDate ����ֵ
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
