package com.ztesoft.oaas.struct;

public class EncryptRespond
{
    private String strMsgNo = "";
    private String success = ""; //返回信息是否成功: 0 失败 1 成功
    private String reason = ""; //失败原因
    private String flag = ""; //原始加密或解密"T"加密,"F"解密,"S"将密文作为前导和明文加密
    private String proclaimedBuff = ""; //原始明文的字符串
    private String secretBuff = ""; //原始密文字符串
    private String strResultBuff = ""; //结果字符串

    /**
     * 获取属性strMsgNo
     * @return strMsgNo
     */
    public String getStrMsgNo()
    {
        return strMsgNo;
    }
    
    /**
     * 设置属性strMsgNo
     * @param strMsgNo 属性值
     */
    public void setStrMsgNo(String strMsgNo)
    {
        this.strMsgNo = strMsgNo;
    }

    /**
     * 获取属性success
     * @return success
     */
    public String getSuccess()
    {
        return success;
    }
    
    /**
     * 设置属性success
     * @param success 属性值
     */
    public void setSuccess(String success)
    {
        this.success = success;
    }

    /**
     * 获取属性reason
     * @return reason
     */
    public String getReason()
    {
        return reason;
    }
    
    /**
     * 设置属性reason
     * @param reason 属性值
     */
    public void setReason(String reason)
    {
        this.reason = reason;
    }

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

    /**
     * 获取属性strResultBuff
     * @return strResultBuff
     */
    public String getStrResultBuff()
    {
        return strResultBuff;
    }
    
    /**
     * 设置属性strResultBuff
     * @param strResultBuff 属性值
     */
    public void setStrResultBuff(String strResultBuff)
    {
        this.strResultBuff = strResultBuff;
    }

}
