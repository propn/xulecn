package com.ztesoft.common.exception;

import java.io.Serializable;
import java.util.ResourceBundle;

public abstract class CrmError implements Serializable {

    /** ��Դ **/
    private transient static final ResourceBundle res =
        ResourceBundle.getBundle("com.ztesoft.common.exception.CrmResources");
    
    private static final String ERROR = res.getString("error");
    private static final String ERROR_CODE = res.getString("errorCode");
    private static final String ERROR_MESSAGE = res.getString("errorMessage");
    private static final String ERROR_RESOLVE = res.getString("errorResolve");

    private String errorCode;
    private String errorMessage;
    private String errorResolve;
    private int level = ErrorLevel.ERROR;
    
    public CrmError(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMessage = errorMsg;
    }
    
    public CrmError(String errorCode) {
        this.errorCode = errorCode;
        initialize(errorCode);
    }

    protected abstract void initialize(String errorCode);

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    protected void setErrorResolve(String errorResolve) {
        this.errorResolve = errorResolve;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorResolve() {
        return errorResolve;
    }
       
    public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
     * ������Ϣ
     * ����������������������д�Լ��Ĵ�����Ϣ��ʽ�����ǲ�������ô��
     * @return
     */
    public String toString() {
        return ERROR
            + "["
            + ERROR_CODE
            + "="
            + errorCode
            + ", "
            + ERROR_MESSAGE
            + "="
            + errorMessage
            + ", "
            + ERROR_RESOLVE
            + "="
            + errorResolve
            + ']';
    }

}
