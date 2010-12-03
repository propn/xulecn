package com.ztesoft.oaas.struct;

public class ChangePasswordRequest
{
    private String staffCode = "";
    private String preemption = ""; //是否强制修改    "T"真(不会校验旧密码--适用于新增用户)，"F"否(适用于修改密码)
    private String oldPassword = "";
    private String newPassword = "";

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
     * 获取属性preemption
     * @return preemption
     */
    public String getPreemption()
    {
        return preemption;
    }
    
    /**
     * 设置属性preemption
     * @param preemption 属性值
     */
    public void setPreemption(String preemption)
    {
        this.preemption = preemption;
    }

    /**
     * 获取属性oldPassword
     * @return oldPassword
     */
    public String getOldPassword()
    {
        return oldPassword;
    }
    
    /**
     * 设置属性oldPassword
     * @param oldPassword 属性值
     */
    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

    /**
     * 获取属性newPassword
     * @return newPassword
     */
    public String getNewPassword()
    {
        return newPassword;
    }
    
    /**
     * 设置属性newPassword
     * @param newPassword 属性值
     */
    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

}
