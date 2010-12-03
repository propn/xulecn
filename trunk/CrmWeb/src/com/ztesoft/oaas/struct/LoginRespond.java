package com.ztesoft.oaas.struct;

import java.io.Serializable;
public class LoginRespond implements Serializable
{
    private String strMsgNo = ""; //消息编号
    private String success = ""; //成功标识：1 成功, 0 失败
    private String reason = ""; //原因
    private String socketId = ""; //本次socket通讯的标识
    private String partyRoleId = ""; //工号
    private String staffCode = ""; //口令
    private String password = ""; //参与人名称
    private String partyName = ""; //参与人生日，用于校验,问候,客户端判断生日快乐!
    private String birthDate = ""; //参与人社会标识识别类型91A身份证91B护照91C税务登记证
    private String socialIdType = ""; //社会标识号码
    private String socialId = ""; //组织标识[最低级别]
    private String orgCode = ""; //组织名称
    private String orgName = ""; //岗位标识
    private String dutyId = ""; //保留字段
    private String addi1 = ""; //服务器时间YYYY-MM-DDHH24:MI:SS
    private String srvTime = ""; //reserved
    private String isEnd = ""; //有效权限个数
    private RoleState[] roleState = null; //权限状态数组
    private String dsFlag = "";//参与人启动双屏标志
    private String lanId = "";//本地网
    
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
     * 获取属性strMsgNo
     * @return strMsgNo
     */
    public String getStrMsgNo()
    {
        return strMsgNo;
    }
    
    /**
     * 设置属性strMsgNo
     * @param strMsgNo 属性值
     */
    public void setStrMsgNo(String strMsgNo)
    {
        this.strMsgNo = strMsgNo;
    }

    /**
     * 获取属性success
     * @return success
     */
    public String getSuccess()
    {
        return success;
    }
    
    /**
     * 设置属性success
     * @param success 属性值
     */
    public void setSuccess(String success)
    {
        this.success = success;
    }

    /**
     * 获取属性reason
     * @return reason
     */
    public String getReason()
    {
        return reason;
    }
    
    /**
     * 设置属性reason
     * @param reason 属性值
     */
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    /**
     * 获取属性socketId
     * @return socketId
     */
    public String getSocketId()
    {
        return socketId;
    }
    
    /**
     * 设置属性socketId
     * @param socketId 属性值
     */
    public void setSocketId(String socketId)
    {
        this.socketId = socketId;
    }

    /**
     * 获取属性partyRoleId
     * @return partyRoleId
     */
    public String getPartyRoleId()
    {
        return partyRoleId;
    }
    
    /**
     * 设置属性partyRoleId
     * @param partyRoleId 属性值
     */
    public void setPartyRoleId(String partyRoleId)
    {
        this.partyRoleId = partyRoleId;
    }

    /**
     * 获取属性staffCode
     * @return staffCode
     */
    public String getStaffCode()
    {
        return staffCode;
    }
    
    /**
     * 设置属性staffCode
     * @param staffCode 属性值
     */
    public void setStaffCode(String staffCode)
    {
        this.staffCode = staffCode;
    }

    /**
     * 获取属性password
     * @return password
     */
    public String getPassword()
    {
        return password;
    }
    
    /**
     * 设置属性password
     * @param password 属性值
     */
    public void setPassword(String password)
    {
        this.password = password;
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
     * 获取属性socialIdType
     * @return socialIdType
     */
    public String getSocialIdType()
    {
        return socialIdType;
    }
    
    /**
     * 设置属性socialIdType
     * @param socialIdType 属性值
     */
    public void setSocialIdType(String socialIdType)
    {
        this.socialIdType = socialIdType;
    }

    /**
     * 获取属性socialId
     * @return socialId
     */
    public String getSocialId()
    {
        return socialId;
    }
    
    /**
     * 设置属性socialId
     * @param socialId 属性值
     */
    public void setSocialId(String socialId)
    {
        this.socialId = socialId;
    }

    /**
     * 获取属性orgCode
     * @return orgCode
     */
    public String getOrgCode()
    {
        return orgCode;
    }
    
    /**
     * 设置属性orgCode
     * @param orgCode 属性值
     */
    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    /**
     * 获取属性orgName
     * @return orgName
     */
    public String getOrgName()
    {
        return orgName;
    }
    
    /**
     * 设置属性orgName
     * @param orgName 属性值
     */
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    /**
     * 获取属性dutyId
     * @return dutyId
     */
    public String getDutyId()
    {
        return dutyId;
    }
    
    /**
     * 设置属性dutyId
     * @param dutyId 属性值
     */
    public void setDutyId(String dutyId)
    {
        this.dutyId = dutyId;
    }

    /**
     * 获取属性addi1
     * @return addi1
     */
    public String getAddi1()
    {
        return addi1;
    }
    
    /**
     * 设置属性addi1
     * @param addi1 属性值
     */
    public void setAddi1(String addi1)
    {
        this.addi1 = addi1;
    }

    /**
     * 获取属性srvTime
     * @return srvTime
     */
    public String getSrvTime()
    {
        return srvTime;
    }
    
    /**
     * 设置属性srvTime
     * @param srvTime 属性值
     */
    public void setSrvTime(String srvTime)
    {
        this.srvTime = srvTime;
    }

    /**
     * 获取属性isEnd
     * @return isEnd
     */
    public String getIsEnd()
    {
        return isEnd;
    }
    
    /**
     * 设置属性isEnd
     * @param isEnd 属性值
     */
    public void setIsEnd(String isEnd)
    {
        this.isEnd = isEnd;
    }

    /**
     * 获取属性roleState
     * @return roleState
     */
    public RoleState[] getRoleState()
    {
        return roleState;
    }
    
    /**
     * 设置属性roleState
     * @param roleState 属性值
     */
    public void setRoleState(RoleState[] roleState)
    {
        this.roleState = roleState;
    }
    /**
     * 获取属性dsFlag
     * @return dsFlag
     */
    public String getDsFlag()
    {
        return dsFlag;
    }
    
    /**
     * 设置属性roleState
     * @param roleState 属性值
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
