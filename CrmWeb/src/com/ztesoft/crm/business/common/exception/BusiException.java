package com.ztesoft.crm.business.common.exception;

import com.ztesoft.common.exception.CrmException;

public class BusiException extends CrmException{

	/**
	 * 
	 * BusiException.java构建器
	 */
	public BusiException(String errorMsg) {
		super(new BusiError(errorMsg));
	}

	/**
	 * 
	 * BusiException.java构建器
	 */
	public BusiException(String errorCode, Throwable cause) {
		super(new BusiError(errorCode), cause);
	}
	
	public BusiException(Throwable cause) {
		super(new BusiError(null), cause);
	}
	
	/**
	 * 
	 * BusiException.java构建器
	 */
	public BusiException(BusiError error, Throwable cause) {
		super(error, cause);
	}

	/**
	 * 
	 * DisplayException.java构建器
	 */
	public BusiException(BusiError error) {
		super(error);
	}

	/**
	 * 
	 * BusiException.java构建器
	 */
	public BusiException(BusiError error, Exception e) {
		super(error, e);
	}
	
	/**
	 * 
	 * BusiException.java构建器
	 */
	public BusiException(BusiError error, String msg) {
		super(error, msg);
	}

	

	
	

}
