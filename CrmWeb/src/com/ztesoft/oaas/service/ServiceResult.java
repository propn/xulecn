package com.ztesoft.oaas.service;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

public class ServiceResult implements Serializable 
{
    /**
     * ������÷�����: 0 �ɹ�; ���� ����
     */
    private String resultCode;
    /**
     * ������÷�����Ϣ: һ��Ϊ����ʧ��ʱ���ش�����Ϣ
     */
    private String resultMessage;
    /**
     * ������÷�����������: 
     * �Ժ�̨����ID��ʵ��INSERT����ʱ����ʵ��ID. 
     * ��ѯʱ����ʵ�����. 
     * ��ѯʵ�弯ʱ����ʵ�弯��XMLƬ�ϻ�ArrayList. 
     * ��ѯ��ʱ����XML�ַ���. 
     * �����쳣ʱ�����쳣��ջ��Ϣ. 
     */
    private Object resultObject;
    
    public ServiceResult()
    {
    }
    
    public ServiceResult(String resultCode, String resultMessage, Object resultObject)
    {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.resultObject = resultObject;
    }
    /**
     * ��ȡ������÷�����. 
     * 0 �ɹ�; ���� ����. 
     * @return ������÷�����
     */
    public String getResultCode()
    {
        return resultCode;
    }
    /**
     * ���÷�����÷�����
     * @param resultCode ������÷�����
     * @see #getResultCode()
     */
    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }
    
    /**
     * ��ȡ������÷�����Ϣ. 
     * һ��Ϊ����ʧ��ʱ���ش�����Ϣ. 
     * @return ������÷�����Ϣ
     */
    public String getResultMessage()
    {
        return resultMessage;
    }
    /**
     * ���÷�����÷�����Ϣ
     * @param resultMessage ������÷�����Ϣ
     * @see #getResultMessage()
     */
    public void setResultMessage(String resultMessage)
    {
        this.resultMessage = resultMessage;
    }
    
    /**
     * ��ȡ������÷�������. 
     * �Ժ�̨����ID��ʵ��INSERT����ʱ����ʵ��ID. 
     * ��ѯʱ����ʵ�����. 
     * ��ѯʵ�弯ʱ����ʵ�弯��XMLƬ�ϻ�ArrayList. 
     * ��ѯ��ʱ����XML�ַ���.
     * �����쳣ʱ�����쳣��ջ��Ϣ. 
     * @return ������÷�������
     */
    public Object getResultObject()
    {
        return resultObject;
    }
    /**
     * ���÷�����÷�������. 
     * @param resultObject ������÷�������
     * @see #getResultObject()
     */
    public void setResultObject(Object resultObject)
    {
        this.resultObject = resultObject;
    }
    
    /**
     * ��ȡ�쳣��ջ��Ϣ
     * @param e �쳣
     * @return �쳣��ջ��Ϣ
     */
    public static String getExceptionStackTrace(Exception e)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
