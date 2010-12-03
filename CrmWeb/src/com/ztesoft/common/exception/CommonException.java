package com.ztesoft.common.exception;

/**
 * 
 * <p>Description: ϵͳ�����Ĵ����쳣��</p>
 * <p>Copyright 2006 ZTEsoft Corp.</p>
 * @Create Date   : 2006-4-26
 * @Version       : 1.0
 */
public class CommonException extends CrmException {

	/**
	 * 
	 * CommonException.java������
	 */
	public CommonException(String errorCode) {
		super(new CommonError(errorCode));
	}

	/**
	 * 
	 * CommonException.java������
	 */
	public CommonException(String errorCode, Throwable cause) {
		super(new CommonError(errorCode), cause);
	}
	
	/**
	 * 
	 * CommonException.java������
	 */
	public CommonException(CommonError error, Throwable cause) {
		super(error, cause);
	}

	/**
	 * 
	 * CommonException.java������
	 */
	public CommonException(CommonError error) {
		super(error);
	}

	/**
	 * 
	 * CommonException.java������
	 */
	public CommonException(CommonError error, Exception e) {
		super(error, e);
	}
	
	/**
	 * 
	 * CommonException.java������
	 */
	public CommonException(CommonError error, String msg) {
		super(error, msg);
	}

}
