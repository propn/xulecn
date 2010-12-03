package com.ztesoft.component.common.log;

import com.ztesoft.component.common.log.bean.LogBean;
import com.ztesoft.component.common.log.vo.PbExceptionLogVO;
import com.ztesoft.component.common.log.vo.PbLogVO;
import com.ztesoft.component.common.log.vo.StaffOnlineVO;

public class LogUtils {

	private static LogBean getLogBean() throws Exception {
		return new LogBean() ;
	}
	
	public static void logException( PbExceptionLogVO pbExceptionVo, PbLogVO pbLogVo ) throws Exception{
		
		getLogBean().addExceptionLog( pbExceptionVo, pbLogVo) ;
	}
	
	public static StaffOnlineVO logStaffSigOn( StaffOnlineVO vo ) throws Exception {
		return getLogBean().addStaffOnlineLog(vo) ;
	}
	
	public static void logStaffSigOff( StaffOnlineVO vo ) throws Exception {
		
		getLogBean().updateStaffOnlineLog(vo);
	}
	
	public static void logActionLog( PbLogVO vo ) throws Exception {
		
		getLogBean().addPbLog( vo ) ;
	}
}
