package com.ztesoft.oaas.struct;

public class EncryptRespond
{
    private String strMsgNo = "";
    private String success = ""; //������Ϣ�Ƿ�ɹ�: 0 ʧ�� 1 �ɹ�
    private String reason = ""; //ʧ��ԭ��
    private String flag = ""; //ԭʼ���ܻ����"T"����,"F"����,"S"��������Ϊǰ�������ļ���
    private String proclaimedBuff = ""; //ԭʼ���ĵ��ַ���
    private String secretBuff = ""; //ԭʼ�����ַ���
    private String strResultBuff = ""; //����ַ���

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
     * ��ȡ����flag
     * @return flag
     */
    public String getFlag()
    {
        return flag;
    }
    
    /**
     * ��������flag
     * @param flag ����ֵ
     */
    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    /**
     * ��ȡ����proclaimedBuff
     * @return proclaimedBuff
     */
    public String getProclaimedBuff()
    {
        return proclaimedBuff;
    }
    
    /**
     * ��������proclaimedBuff
     * @param proclaimedBuff ����ֵ
     */
    public void setProclaimedBuff(String proclaimedBuff)
    {
        this.proclaimedBuff = proclaimedBuff;
    }

    /**
     * ��ȡ����secretBuff
     * @return secretBuff
     */
    public String getSecretBuff()
    {
        return secretBuff;
    }
    
    /**
     * ��������secretBuff
     * @param secretBuff ����ֵ
     */
    public void setSecretBuff(String secretBuff)
    {
        this.secretBuff = secretBuff;
    }

    /**
     * ��ȡ����strResultBuff
     * @return strResultBuff
     */
    public String getStrResultBuff()
    {
        return strResultBuff;
    }
    
    /**
     * ��������strResultBuff
     * @param strResultBuff ����ֵ
     */
    public void setStrResultBuff(String strResultBuff)
    {
        this.strResultBuff = strResultBuff;
    }

}
