//Source file: D:\\WLSApp\\Eclipse\\workspace\\ibss\\src\\com\\powerise\\ibss\\framework\\FrameException.java

package com.powerise.ibss.framework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;

import org.apache.log4j.Logger;

import com.powerise.ibss.util.GlobalNames;
import com.powerise.ibss.util.SqlExec;
/**
 * 业务系统自行定义的意外。
 */
public class FrameException extends Exception {
	int m_ErrorCode = 0;

	String m_ErrorMsg = null;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		// TODO Auto-generated method stub
		return  m_ErrorMsg;
	}

	String m_SysMsg = null;

	static private Logger m_Logger = null;

	private static void getLogger() {
		if (m_Logger == null)
			m_Logger = Logger
					.getLogger("com.powerise.ibss.framework.FrameException");

	}

	/**
	 * @since 2002
	 */
	public FrameException() {

	}

	/**
	 * @param ErrorCode
	 * @param ErrorMsg
	 * @param SysMsg
	 * @since 2002
	 */
	public FrameException(int ErrorCode, String ErrorMsg, String SysMsg) {
		getLogger();
		m_ErrorCode = ErrorCode;
		m_ErrorMsg = ErrorMsg;
		m_SysMsg = SysMsg;

		m_Logger.debug("[Error code:" + m_ErrorCode + "][Error Message:" + m_ErrorMsg
				+ "][System Error:" + m_SysMsg + "]");
		m_Logger.debug(getStackInfo());
	}

	public FrameException(int ErrorCode, String ErrorMsg, Exception SysMsg) {
		getLogger();
		m_ErrorCode = ErrorCode;
		m_ErrorMsg = ErrorMsg;
		m_SysMsg = SysMsg.toString();

		m_Logger.debug("[Error code:" + m_ErrorCode + "][Error Message:" + m_ErrorMsg
				+ "][System Error:" + m_SysMsg + "]");
		m_Logger.debug(getStackInfo());
	}
	
	/**
	 * @param ErrorMsg
	 * @since 2002
	 */
	public FrameException(String ErrorMsg) {
		getLogger();
		m_ErrorMsg = ErrorMsg;
		m_Logger.debug("[Error Message:" + m_ErrorMsg + "]");
		m_Logger.debug(getStackInfo());
	}

	/**
	 * @param ErrorMsg
	 * @since 2010-01-23
	 */
	public FrameException(Exception e) {
		getLogger();
		m_ErrorMsg = e.getMessage() ;
		m_Logger.debug("[Error Message:" + m_ErrorMsg + "]");
		m_Logger.debug(getStackInfo());
	}
	
	/**
	 * @param ErrorMsg
	 * @since 2010-01-23
	 */
	public FrameException(DynamicDict dto) {
		super(dto.msg+"\n 异常标识码为["+dto.flag+"]\n"+dto.exception) ;
		getLogger();
		m_ErrorCode = -999;
		m_ErrorMsg = dto.exception;
		m_SysMsg = dto.msg;
		
//		m_ErrorMsg = dto.msg+"\n 异常标识码为["+dto.flag+"]\n"+dto.exception;
//		m_Logger.debug("[Error Message:" + m_ErrorMsg + "]");
//		m_Logger.debug(getStackInfo());
		
	}
	/**
	 * @param ErrorCode
	 * @param ErrorMsg
	 * @since 2002
	 */
	public FrameException(int ErrorCode, String ErrorMsg) {
		getLogger();
		m_ErrorCode = ErrorCode;
		m_ErrorMsg = ErrorMsg;
		m_Logger.debug("[Error Code:" + m_ErrorCode + "][Error Message:" + m_ErrorMsg + "]");
		m_Logger.debug(getStackInfo());
	}

    public FrameException(Connection conn,int errorCode, String errorMsg) {
          getLogger();
          m_ErrorCode = errorCode;
          SqlExec db = new SqlExec(conn);
          try {
            db.prepare(
                "select error_text from tfm_error_config where error_code=? and lang=?");
            db.bind(1, errorCode);
            db.bind(2, GlobalNames.CURR_LANG);
            db.run();
            if (db.next())
              m_ErrorMsg = db.getString(1);
            else
              m_ErrorMsg = errorMsg;
          }
          catch (Exception ee) {
            ee.printStackTrace();
            m_ErrorMsg = "Error occurs when get error text:" + ee.getMessage();
          }
          finally {
            try {
              db.reset();
            }
            catch (Exception ee) {

            }
          }
          m_Logger.debug("[Error Code:" + m_ErrorCode + "][Error Message:" +
                         m_ErrorMsg + "]");
          m_Logger.debug(getStackInfo());
    }

    public FrameException(int ErrorCode, Connection conn) {
                getLogger();
                m_ErrorCode = ErrorCode;
                SqlExec db=new SqlExec(conn);
                try {
                  db.prepare("select error_text from tfm_error_config where error_code=? and lang=?");
                  db.bind(1, ErrorCode);
                  db.bind(2,GlobalNames.CURR_LANG);
                  db.run();
                  if(db.next())
                    m_ErrorMsg=db.getString(1);
                  else
                    m_ErrorMsg="Unknown error code:"+ErrorCode;
                } catch(Exception ee) {
                  ee.printStackTrace();
                  m_ErrorMsg = "Error occurs when get error text:"+ee.getMessage();
                } finally {
                  try {
                    db.reset();
                  } catch(Exception ee) {

                  }
                }
                m_Logger.debug("[Error Code:" + m_ErrorCode + "][Error Message:" + m_ErrorMsg + "]");
                m_Logger.debug(getStackInfo());
    }

	public void printStackTrace() {
		System.out.println(" Error Code :" + m_ErrorCode);
		System.out.println(" Error Message :" + m_ErrorMsg);
		if (m_SysMsg != null)
			System.out.println(" System Error :" + m_SysMsg);
		super.printStackTrace();
	}

	public void printStackTrace(PrintWriter s) {
		s.println(" Error Code :" + m_ErrorCode);
		s.println(" Error Message :" + m_ErrorMsg);
		if (m_SysMsg != null)
			s.println(" System Error :" + m_SysMsg);
		super.printStackTrace(s);
	}

	public int getErrorCode() {
		return m_ErrorCode;
	}

	public String getErrorMsg() {
		return m_ErrorMsg;
	}

	public String getSysMsg() {
		return m_SysMsg;
	}

	private String getStackInfo() {
		StringWriter sw = null;
		PrintWriter pw = null;
		String strMsg = "";

		sw = new StringWriter();
		pw = new PrintWriter(sw);
		try {
			this.printStackTrace(pw);
			sw.flush();
			strMsg = sw.toString();
			sw.close();
			pw.close();
		} catch (java.io.IOException le) {
			strMsg = "待定处理";
		}
		return strMsg;
	}

}
