package com.ztesoft.component.common.signon;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class SessionListener extends HttpServlet implements HttpSessionListener {
	private static Logger logger = Logger.getLogger(SessionListener.class);
	// Notification that a session was created
	public void sessionCreated(HttpSessionEvent se) {
		//logger.debug("Session Created");
	}

	/**
	 *  Notification that a session was invalidated
	 *  HTTPSession断开的时候触发这个建廷
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		//logger.debug("Session Destroyed");
//    	StaffOnlineVO staffOnlineVO = (StaffOnlineVO)se.getSession().getAttribute("StaffOnlineVO");
//    	if( staffOnlineVO != null ){
//	    	staffOnlineVO.setLogoutDate(DateFormatUtils.getFormatedDateTime());
//	    	try{
//	    	LogUtils.logStaffSigOff(staffOnlineVO);
//	    	}catch( Exception e ){
//	    		e.printStackTrace();
//	    	}
//    	}
//    	SSOLoginKeyDAO ssoDao = SSOLoginKeyDAOFactory.getSSOLoginKeyDAO();
//    	String loginKey = (String)se.getSession().getAttribute("SSOLoginKey");
//    	ssoDao.delete(loginKey);
	}
}

