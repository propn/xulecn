package com.ztesoft.oaas.struct;

public class SrvInf
{
    private String srvName = "";
    private String dbUser = "";
    private String dbPassword = "";

    /**
     * 获取属性srvName
     * @return srvName
     */
    public String getSrvName()
    {
        return srvName;
    }
    
    /**
     * 设置属性srvName
     * @param srvName 属性值
     */
    public void setSrvName(String srvName)
    {
        this.srvName = srvName;
    }

    /**
     * 获取属性dbUser
     * @return dbUser
     */
    public String getDbUser()
    {
        return dbUser;
    }
    
    /**
     * 设置属性dbUser
     * @param dbUser 属性值
     */
    public void setDbUser(String dbUser)
    {
        this.dbUser = dbUser;
    }

    /**
     * 获取属性dbPassword
     * @return dbPassword
     */
    public String getDbPassword()
    {
        return dbPassword;
    }
    
    /**
     * 设置属性dbPassword
     * @param dbPassword 属性值
     */
    public void setDbPassword(String dbPassword)
    {
        this.dbPassword = dbPassword;
    }

}
