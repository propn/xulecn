package com.ztesoft.oaas.struct;

public class ChangePasswordRequest
{
    private String staffCode = "";
    private String preemption = ""; //�Ƿ�ǿ���޸�    "T"��(����У�������--�����������û�)��"F"��(�������޸�����)
    private String oldPassword = "";
    private String newPassword = "";

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
     * ��ȡ����preemption
     * @return preemption
     */
    public String getPreemption()
    {
        return preemption;
    }
    
    /**
     * ��������preemption
     * @param preemption ����ֵ
     */
    public void setPreemption(String preemption)
    {
        this.preemption = preemption;
    }

    /**
     * ��ȡ����oldPassword
     * @return oldPassword
     */
    public String getOldPassword()
    {
        return oldPassword;
    }
    
    /**
     * ��������oldPassword
     * @param oldPassword ����ֵ
     */
    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

    /**
     * ��ȡ����newPassword
     * @return newPassword
     */
    public String getNewPassword()
    {
        return newPassword;
    }
    
    /**
     * ��������newPassword
     * @param newPassword ����ֵ
     */
    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

}
