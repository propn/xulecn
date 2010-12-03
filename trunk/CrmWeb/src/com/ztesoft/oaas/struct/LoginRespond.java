package com.ztesoft.oaas.struct;

import java.io.Serializable;
public class LoginRespond implements Serializable
{
    private String strMsgNo = ""; //��Ϣ���
    private String success = ""; //�ɹ���ʶ��1 �ɹ�, 0 ʧ��
    private String reason = ""; //ԭ��
    private String socketId = ""; //����socketͨѶ�ı�ʶ
    private String partyRoleId = ""; //����
    private String staffCode = ""; //����
    private String password = ""; //����������
    private String partyName = ""; //���������գ�����У��,�ʺ�,�ͻ����ж����տ���!
    private String birthDate = ""; //����������ʶʶ������91A���֤91B����91C˰��Ǽ�֤
    private String socialIdType = ""; //����ʶ����
    private String socialId = ""; //��֯��ʶ[��ͼ���]
    private String orgCode = ""; //��֯����
    private String orgName = ""; //��λ��ʶ
    private String dutyId = ""; //�����ֶ�
    private String addi1 = ""; //������ʱ��YYYY-MM-DDHH24:MI:SS
    private String srvTime = ""; //reserved
    private String isEnd = ""; //��ЧȨ�޸���
    private RoleState[] roleState = null; //Ȩ��״̬����
    private String dsFlag = "";//����������˫����־
    private String lanId = "";//������
    
    private boolean isSuperStaff = false ;

    /**
	 * @return Returns the isSuperStaff.
	 */
	public boolean isSuperStaff() {
		return isSuperStaff;
	}

	/**
	 * @param isSuperStaff The isSuperStaff to set.
	 */
	public void setSuperStaffFlag(boolean isSuperStaff) {
		this.isSuperStaff = isSuperStaff;
	}

	/**
     * ��ȡ����strMsgNo
     * @return strMsgNo
     */
    public String getStrMsgNo()
    {
        return strMsgNo;
    }
    
    /**
     * ��������strMsgNo
     * @param strMsgNo ����ֵ
     */
    public void setStrMsgNo(String strMsgNo)
    {
        this.strMsgNo = strMsgNo;
    }

    /**
     * ��ȡ����success
     * @return success
     */
    public String getSuccess()
    {
        return success;
    }
    
    /**
     * ��������success
     * @param success ����ֵ
     */
    public void setSuccess(String success)
    {
        this.success = success;
    }

    /**
     * ��ȡ����reason
     * @return reason
     */
    public String getReason()
    {
        return reason;
    }
    
    /**
     * ��������reason
     * @param reason ����ֵ
     */
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    /**
     * ��ȡ����socketId
     * @return socketId
     */
    public String getSocketId()
    {
        return socketId;
    }
    
    /**
     * ��������socketId
     * @param socketId ����ֵ
     */
    public void setSocketId(String socketId)
    {
        this.socketId = socketId;
    }

    /**
     * ��ȡ����partyRoleId
     * @return partyRoleId
     */
    public String getPartyRoleId()
    {
        return partyRoleId;
    }
    
    /**
     * ��������partyRoleId
     * @param partyRoleId ����ֵ
     */
    public void setPartyRoleId(String partyRoleId)
    {
        this.partyRoleId = partyRoleId;
    }

    /**
     * ��ȡ����staffCode
     * @return staffCode
     */
    public String getStaffCode()
    {
        return staffCode;
    }
    
    /**
     * ��������staffCode
     * @param staffCode ����ֵ
     */
    public void setStaffCode(String staffCode)
    {
        this.staffCode = staffCode;
    }

    /**
     * ��ȡ����password
     * @return password
     */
    public String getPassword()
    {
        return password;
    }
    
    /**
     * ��������password
     * @param password ����ֵ
     */
    public void setPassword(String password)
    {
        this.password = password;
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
     * ��ȡ����socialIdType
     * @return socialIdType
     */
    public String getSocialIdType()
    {
        return socialIdType;
    }
    
    /**
     * ��������socialIdType
     * @param socialIdType ����ֵ
     */
    public void setSocialIdType(String socialIdType)
    {
        this.socialIdType = socialIdType;
    }

    /**
     * ��ȡ����socialId
     * @return socialId
     */
    public String getSocialId()
    {
        return socialId;
    }
    
    /**
     * ��������socialId
     * @param socialId ����ֵ
     */
    public void setSocialId(String socialId)
    {
        this.socialId = socialId;
    }

    /**
     * ��ȡ����orgCode
     * @return orgCode
     */
    public String getOrgCode()
    {
        return orgCode;
    }
    
    /**
     * ��������orgCode
     * @param orgCode ����ֵ
     */
    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    /**
     * ��ȡ����orgName
     * @return orgName
     */
    public String getOrgName()
    {
        return orgName;
    }
    
    /**
     * ��������orgName
     * @param orgName ����ֵ
     */
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    /**
     * ��ȡ����dutyId
     * @return dutyId
     */
    public String getDutyId()
    {
        return dutyId;
    }
    
    /**
     * ��������dutyId
     * @param dutyId ����ֵ
     */
    public void setDutyId(String dutyId)
    {
        this.dutyId = dutyId;
    }

    /**
     * ��ȡ����addi1
     * @return addi1
     */
    public String getAddi1()
    {
        return addi1;
    }
    
    /**
     * ��������addi1
     * @param addi1 ����ֵ
     */
    public void setAddi1(String addi1)
    {
        this.addi1 = addi1;
    }

    /**
     * ��ȡ����srvTime
     * @return srvTime
     */
    public String getSrvTime()
    {
        return srvTime;
    }
    
    /**
     * ��������srvTime
     * @param srvTime ����ֵ
     */
    public void setSrvTime(String srvTime)
    {
        this.srvTime = srvTime;
    }

    /**
     * ��ȡ����isEnd
     * @return isEnd
     */
    public String getIsEnd()
    {
        return isEnd;
    }
    
    /**
     * ��������isEnd
     * @param isEnd ����ֵ
     */
    public void setIsEnd(String isEnd)
    {
        this.isEnd = isEnd;
    }

    /**
     * ��ȡ����roleState
     * @return roleState
     */
    public RoleState[] getRoleState()
    {
        return roleState;
    }
    
    /**
     * ��������roleState
     * @param roleState ����ֵ
     */
    public void setRoleState(RoleState[] roleState)
    {
        this.roleState = roleState;
    }
    /**
     * ��ȡ����dsFlag
     * @return dsFlag
     */
    public String getDsFlag()
    {
        return dsFlag;
    }
    
    /**
     * ��������roleState
     * @param roleState ����ֵ
     */
    public void setDsFlag(String dsFlag)
    {
        this.dsFlag = dsFlag;
    }

	public String getLanId() {
		return lanId;
	}

	public void setLanId(String lanId) {
		this.lanId = lanId;
	}
}
