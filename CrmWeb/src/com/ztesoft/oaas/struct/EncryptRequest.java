package com.ztesoft.oaas.struct;

public class EncryptRequest
{
    private String flag = ""; //���ܻ����"T"����,"F"����,"S"��������Ϊǰ�������ļ���
    private String proclaimedBuff = ""; //���ĵ��ַ���
    private String secretBuff = ""; //�����ַ���

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

}
