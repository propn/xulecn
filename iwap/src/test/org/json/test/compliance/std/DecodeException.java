package org.json.test.compliance.std;

public class DecodeException extends Exception {
	private static final long serialVersionUID = -6115360040337034174L;

	public DecodeException() {
	}

	public DecodeException(String message) {
		super(message);
	}

	public DecodeException(Throwable cause) {
		super(cause);
	}

	public DecodeException(String message, Throwable cause) {
		super(message, cause);
	}

}
