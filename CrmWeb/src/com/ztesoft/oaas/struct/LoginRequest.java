package com.ztesoft.oaas.struct;

public class LoginRequest
{
    private String staffCode = ""; //工号
    private String password = ""; //口令
    private String ipAddress = ""; //IP地址
    private String hostName = ""; //暂时不用，先填空
    private String mac = ""; //MAC地址
    private String dsFlag = "";//用户启动双屏显示标识
    private String[] roleId = null; //请求进行验证的权限Id，为null或长度为0时表示获取所有权限列表, 权限id数组[填写权限编码privilege_code]
    
    
    private String loginType="";//LoginWan
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
     * 获取属性ipAddress
     * @return ipAddress
     */
    public String getIpAddress()
    {
        return ipAddress;
    }
    
    /**
     * 设置属性ipAddress
     * @param ipAddress 属性值
     */
    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    /**
     * 获取属性hostName
     * @return hostName
     */
    public String getHostName()
    {
        return hostName;
    }
    
    /**
     * 设置属性hostName
     * @param hostName 属性值
     */
    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    /**
     * 获取属性mac
     * @return mac
     */
    public String getMac()
    {
        return mac;
    }
    
    /**
     * 设置属性mac
     * @param mac 属性值
     */
    public void setMac(String mac)
    {
        this.mac = mac;
    }

    /**
     * 获取属性roleId
     * @return roleId
     */
    public String[] getRoleId()
    {
        return roleId;
    }
    
    /**
     * 设置属性roleId
     * @param roleId 属性值
     */
    public void setRoleId(String[] roleId)
    {
        this.roleId = roleId;
    }
    /**
     * 获取属性loginType
     * @param loginType 属性值
     */
    public String getLoginType(){
    	return loginType;
    }
    /**
     * 设置属性loginType
     * @return loginType
     */
    public void setLoginType(String loginType){
    	this.loginType= loginType;
    }
    /**
     * 获取属性dsFlag
     * @param loginType 属性值
     */
    public String getDsFlag(){
    	return dsFlag;
    }
    /**
     * 设置属性dsFlag
     * @return dsFlag
     */
    public void setDsFlag(String dsFlag){
    	this.dsFlag= dsFlag;
    }
}
