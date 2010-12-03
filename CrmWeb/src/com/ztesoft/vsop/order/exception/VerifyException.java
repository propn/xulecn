package com.ztesoft.vsop.order.exception;

public class VerifyException extends RuntimeException {
	private String message;
	public VerifyException() {
		super();
	}


	public VerifyException(String arg0) {
		message = arg0;
	}

	public VerifyException(Throwable arg0) {
		
	}

	public String getMessage() {
		return message;
	}
	
}
