package com.ztesoft.oaas.struct;

public class LoginV50Request
{
    private String staffId = "";
    private String password = "";
    private String ipAddress = "";
    private String hostName = "";
    private String mac = "";
    private String versionId = "";
    private String moduleId = "";
    private String srvNum = "";
    private String[] srvName = null;
    private String roleNum = "";
    private String[] roleId = null;
    
    /**
     * ��ȡ����staffId
     * @return staffId
     */
    public String getStaffId()
    {
        return staffId;
    }
    
    /**
     * ��������staffId
     * @param staffId ����ֵ
     */
    public void setStaffId(String staffId)
    {
        this.staffId = staffId;
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
     * ��ȡ����ipAddress
     * @return ipAddress
     */
    public String getIpAddress()
    {
        return ipAddress;
    }
    
    /**
     * ��������ipAddress
     * @param ipAddress ����ֵ
     */
    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    /**
     * ��ȡ����hostname
     * @return hostname
     */
    public String getHostName()
    {
        return hostName;
    }
    
    /**
     * ��������hostname
     * @param hostname ����ֵ
     */
    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    /**
     * ��ȡ����mac
     * @return mac
     */
    public String getMac()
    {
        return mac;
    }
    
    /**
     * ��������mac
     * @param mac ����ֵ
     */
    public void setMac(String mac)
    {
        this.mac = mac;
    }

    /**
     * ��ȡ����versionId
     * @return versionId
     */
    public String getVersionId()
    {
        return versionId;
    }
    
    /**
     * ��������versionId
     * @param versionId ����ֵ
     */
    public void setVersionId(String versionId)
    {
        this.versionId = versionId;
    }

    /**
     * ��ȡ����moduleId
     * @return moduleId
     */
    public String getModuleId()
    {
        return moduleId;
    }
    
    /**
     * ��������moduleId
     * @param moduleId ����ֵ
     */
    public void setModuleId(String moduleId)
    {
        this.moduleId = moduleId;
    }

    /**
     * ��ȡ����srvNum
     * @return srvNum
     */
    public String getSrvNum()
    {
        return srvNum;
    }
    
    /**
     * ��������srvNum
     * @param srvNum ����ֵ
     */
    public void setSrvNum(String srvNum)
    {
        this.srvNum = srvNum;
    }

    /**
     * ��ȡ����srvName
     * @return srvName
     */
    public String[] getSrvName()
    {
        return srvName;
    }
    
    /**
     * ��������srvName
     * @param srvName ����ֵ
     */
    public void setSrvName(String[] srvName)
    {
        this.srvName = srvName;
    }

    /**
     * ��ȡ����roleNum
     * @return roleNum
     */
    public String getRoleNum()
    {
        return roleNum;
    }
    
    /**
     * ��������roleNum
     * @param roleNum ����ֵ
     */
    public void setRoleNum(String roleNum)
    {
        this.roleNum = roleNum;
    }

    /**
     * ��ȡ����roleId
     * @return roleId
     */
    public String[] getRoleId()
    {
        return roleId;
    }
    
    /**
     * ��������roleId
     * @param roleId ����ֵ
     */
    public void setRoleId(String[] roleId)
    {
        this.roleId = roleId;
    }

}
