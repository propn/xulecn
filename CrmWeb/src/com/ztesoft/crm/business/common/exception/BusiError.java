package com.ztesoft.crm.business.common.exception;



import com.ztesoft.common.exception.CrmError;
import com.ztesoft.common.exception.ErrorLevel;

public class BusiError extends CrmError {


	/**
	 * BusiError.java������
	 */
	public BusiError(String errorMsg) {
		super(null,errorMsg);
		setLevel(ErrorLevel.INFO);
	}
	
	/**
	 * BusiError.java������
	 */
	public BusiError(String errorCode,String errorMsg) {
		super(errorCode,errorMsg);
		setLevel(ErrorLevel.INFO);
	}


	protected void initialize(String errorCode) {
	
		
	}


}