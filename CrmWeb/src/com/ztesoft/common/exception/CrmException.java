package com.ztesoft.common.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;


public class CrmException extends RuntimeException {
	private static Logger logger = Logger.getLogger(CrmException.class);
	
	private Throwable _cause;
	private CrmError _error;
	
	public CrmException(CrmError error) {
		super("");
		_error = error;
	}

	public CrmException(CrmError error, String message) {
		super(message);
		_error = error;
	}

	public CrmException(CrmError error, Throwable cause) {
		super(cause.getMessage());
		_cause = cause;
		_error = error;
	}

	public CrmException(CrmError error, String message, Throwable cause) {
		super(message);
		if (cause instanceof java.rmi.RemoteException) {
			_cause = ((java.rmi.RemoteException) cause).detail;
		} else {
			_cause = cause;
		}
		_error = error;
	}

	public Throwable getCause() {
		return _cause;
	}

	public CrmError getError() {
		return _error;
	}

	/**
	 * ���صõ���Ϣ�ķ���
	 * @return <code>String</code>,������Ϣ
	 */
	public String getMessage() {
		/**
		 StringBuffer sb = new StringBuffer(getError().toString());
		 sb.append(" ");
		 String message = super.getMessage();
		 if (message != null) {
		 sb.append(message);
		 }
		 else if (getCause() != null) {
		 sb.append(getCause().getMessage());
		 }
		 return sb.toString();*/
		return super.getMessage();
	}

	public String toString() {
		return getMessage();
	}

	public final void printStackTrace() {
		printStackTrace(System.out);
	}

	/**
	 * ��ȡ��ǰ��ջ��Ϣ
	 * @return String
	 */
	public final String getStackTraceAsString() {
		
		if(this._cause == null ) return "";
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * ��ӡ��ջ
	 * @param stream
	 */
	public final void printStackTrace(PrintStream stream) {
		//stream.println("Caused by: " + this.toString());
		Throwable t = getCause();
		if (t == null) {
			//super.printStackTrace(stream);
			stream.println("");
		}
		while (t != null) {
			//stream.println("Caused by: " + t);
			if (t instanceof CrmException) {
				t = ((CrmException) t).getCause();
			} else if (t instanceof RemoteException) {
				t = ((RemoteException) t).detail;
			} else {
				t.printStackTrace(stream);
				break;
			}
		}
	}

	/**
	 * ��ӡ��ջ
	 * @param writer
	 */
	public final void printStackTrace(PrintWriter writer) {
		//writer.println("Caused by: " + this.toString());
		Throwable t = getCause();
		if (t == null) {
			super.printStackTrace(writer);
		}
		while (t != null) {
			//writer.println("Caused by: " + t);
			if (t instanceof CrmException) {
				t = ((CrmException) t).getCause();
			} else if (t instanceof RemoteException) {
				t = ((RemoteException) t).detail;
			} else {
				//writer.print("Caused by: " );
				t.printStackTrace(writer);
				break;
			}
		}
	}

	public static void main(String args[]) {
		Exception ex = new Exception("exception");
		Exception ex2 = new Exception("exception2", ex);
		Exception ex3 = new Exception("exception2", ex2);
		CrmException ex100 = new CrmException(new CommonError(CommonError.COMMON_ERROR), ex3);
 

	}

}
