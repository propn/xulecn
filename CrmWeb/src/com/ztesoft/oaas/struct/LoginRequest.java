package com.ztesoft.oaas.struct;

public class LoginRequest
{
    private String staffCode = ""; //����
    private String password = ""; //����
    private String ipAddress = ""; //IP��ַ
    private String hostName = ""; //��ʱ���ã������
    private String mac = ""; //MAC��ַ
    private String dsFlag = "";//�û�����˫����ʾ��ʶ
    private String[] roleId = null; //���������֤��Ȩ��Id��Ϊnull�򳤶�Ϊ0ʱ��ʾ��ȡ����Ȩ���б�, Ȩ��id����[��дȨ�ޱ���privilege_code]
    
    
    private String loginType="";//LoginWan
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
     * ��ȡ����hostName
     * @return hostName
     */
    public String getHostName()
    {
        return hostName;
    }
    
    /**
     * ��������hostName
     * @param hostName ����ֵ
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
    /**
     * ��ȡ����loginType
     * @param loginType ����ֵ
     */
    public String getLoginType(){
    	return loginType;
    }
    /**
     * ��������loginType
     * @return loginType
     */
    public void setLoginType(String loginType){
    	this.loginType= loginType;
    }
    /**
     * ��ȡ����dsFlag
     * @param loginType ����ֵ
     */
    public String getDsFlag(){
    	return dsFlag;
    }
    /**
     * ��������dsFlag
     * @return dsFlag
     */
    public void setDsFlag(String dsFlag){
    	this.dsFlag= dsFlag;
    }
}
