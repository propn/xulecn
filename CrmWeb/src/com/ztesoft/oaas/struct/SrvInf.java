package com.ztesoft.oaas.struct;

public class SrvInf
{
    private String srvName = "";
    private String dbUser = "";
    private String dbPassword = "";

    /**
     * ��ȡ����srvName
     * @return srvName
     */
    public String getSrvName()
    {
        return srvName;
    }
    
    /**
     * ��������srvName
     * @param srvName ����ֵ
     */
    public void setSrvName(String srvName)
    {
        this.srvName = srvName;
    }

    /**
     * ��ȡ����dbUser
     * @return dbUser
     */
    public String getDbUser()
    {
        return dbUser;
    }
    
    /**
     * ��������dbUser
     * @param dbUser ����ֵ
     */
    public void setDbUser(String dbUser)
    {
        this.dbUser = dbUser;
    }

    /**
     * ��ȡ����dbPassword
     * @return dbPassword
     */
    public String getDbPassword()
    {
        return dbPassword;
    }
    
    /**
     * ��������dbPassword
     * @param dbPassword ����ֵ
     */
    public void setDbPassword(String dbPassword)
    {
        this.dbPassword = dbPassword;
    }

}
