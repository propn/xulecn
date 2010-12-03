package com.ztesoft.oaas.struct;

public class ChangePasswordRespond
{
    private String strMsgNo = "";
    private String success = ""; //返回信息是否成功: 0 失败 1 成功
    private String reason = ""; //失败原因

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

}
