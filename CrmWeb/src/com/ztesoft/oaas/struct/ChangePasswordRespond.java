package com.ztesoft.oaas.struct;

public class ChangePasswordRespond
{
    private String strMsgNo = "";
    private String success = ""; //������Ϣ�Ƿ�ɹ�: 0 ʧ�� 1 �ɹ�
    private String reason = ""; //ʧ��ԭ��

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

}
