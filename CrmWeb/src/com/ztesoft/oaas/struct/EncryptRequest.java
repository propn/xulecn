package com.ztesoft.oaas.struct;

public class EncryptRequest
{
    private String flag = ""; //加密或解密"T"加密,"F"解密,"S"将密文作为前导和明文加密
    private String proclaimedBuff = ""; //明文的字符串
    private String secretBuff = ""; //密文字符串

    /**
     * 获取属性flag
     * @return flag
     */
    public String getFlag()
    {
        return flag;
    }
    
    /**
     * 设置属性flag
     * @param flag 属性值
     */
    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    /**
     * 获取属性proclaimedBuff
     * @return proclaimedBuff
     */
    public String getProclaimedBuff()
    {
        return proclaimedBuff;
    }
    
    /**
     * 设置属性proclaimedBuff
     * @param proclaimedBuff 属性值
     */
    public void setProclaimedBuff(String proclaimedBuff)
    {
        this.proclaimedBuff = proclaimedBuff;
    }

    /**
     * 获取属性secretBuff
     * @return secretBuff
     */
    public String getSecretBuff()
    {
        return secretBuff;
    }
    
    /**
     * 设置属性secretBuff
     * @param secretBuff 属性值
     */
    public void setSecretBuff(String secretBuff)
    {
        this.secretBuff = secretBuff;
    }

}
