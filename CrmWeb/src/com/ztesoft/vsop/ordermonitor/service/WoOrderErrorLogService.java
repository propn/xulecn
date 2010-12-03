package com.ztesoft.vsop.ordermonitor.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.dict.DataTranslate;

import com.ztesoft.common.util.PageModel;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.ServiceList;


public class WoOrderErrorLogService  {
	public boolean insertWoOrderErrorLog(HashMap WoOrderErrorLog ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoOrderErrorLog", WoOrderErrorLog) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderErrorLogBO,
						"insertWoOrderErrorLog" ,param)) ;
		return result ;
	}

	
	public boolean updateWoOrderErrorLog(HashMap WoOrderErrorLog ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoOrderErrorLog", WoOrderErrorLog) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderErrorLogBO,
						"updateWoOrderErrorLog" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchWoOrderErrorLogData(String order_id ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("order_id", order_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderErrorLogBO,
						"searchWoOrderErrorLogData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoOrderErrorLogById(String error_serial_id) throws Exception {
		Map param = getWoOrderErrorLogKeyMap(error_serial_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderErrorLogBO,
						"getWoOrderErrorLogById" ,param)) ;
		
		return result ;
	}
	

	public List findWoOrderErrorLogByCond(String error_serial_id) throws Exception {
		Map param = getWoOrderErrorLogKeyMap(error_serial_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderErrorLogBO,
						"findWoOrderErrorLogByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteWoOrderErrorLogById(String error_serial_id) throws Exception {
		Map param = getWoOrderErrorLogKeyMap(error_serial_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderErrorLogBO,
						"deleteWoOrderErrorLogById" ,param)) ;
		
		return result ;
	}
	
	private Map getWoOrderErrorLogKeyMap(String error_serial_id){
		Map param = new HashMap() ;
				
		param.put("error_serial_id", error_serial_id) ;
				
		return param ;
	}
}
