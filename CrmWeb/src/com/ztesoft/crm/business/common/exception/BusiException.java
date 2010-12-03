package com.ztesoft.crm.business.common.exception;

import com.ztesoft.common.exception.CrmException;

public class BusiException extends CrmException{

	/**
	 * 
	 * BusiException.java������
	 */
	public BusiException(String errorMsg) {
		super(new BusiError(errorMsg));
	}

	/**
	 * 
	 * BusiException.java������
	 */
	public BusiException(String errorCode, Throwable cause) {
		super(new BusiError(errorCode), cause);
	}
	
	public BusiException(Throwable cause) {
		super(new BusiError(null), cause);
	}
	
	/**
	 * 
	 * BusiException.java������
	 */
	public BusiException(BusiError error, Throwable cause) {
		super(error, cause);
	}

	/**
	 * 
	 * DisplayException.java������
	 */
	public BusiException(BusiError error) {
		super(error);
	}

	/**
	 * 
	 * BusiException.java������
	 */
	public BusiException(BusiError error, Exception e) {
		super(error, e);
	}
	
	/**
	 * 
	 * BusiException.java������
	 */
	public BusiException(BusiError error, String msg) {
		super(error, msg);
	}

	

	
	

}
