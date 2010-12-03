package com.ztesoft.vsop.dispatchrule.service;


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


public class VsopActiveProcessService  {	
	public boolean insertVsopActiveProcess(HashMap VsopActiveProcess ) throws Exception {
		Map param = new HashMap() ;
		param.put("VsopActiveProcess", VsopActiveProcess) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.VsopActiveProcessBO,
						"insertVsopActiveProcess" ,param)) ;
		return result ;
	}

	
	public boolean updateVsopActiveProcess(HashMap VsopActiveProcess ) throws Exception {
		Map param = new HashMap() ;
		param.put("VsopActiveProcess", VsopActiveProcess) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.VsopActiveProcessBO,
						"updateVsopActiveProcess" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchVsopActiveProcessData(String process_type , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("process_type", process_type) ;

		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.VsopActiveProcessBO,
						"searchVsopActiveProcessData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getVsopActiveProcessById(String process_code) throws Exception {
		Map param = getVsopActiveProcessKeyMap(process_code) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.VsopActiveProcessBO,
						"getVsopActiveProcessById" ,param)) ;
		
		return result ;
	}
	

	public List findVsopActiveProcessByCond(String process_code) throws Exception {
		Map param = getVsopActiveProcessKeyMap(process_code) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.VsopActiveProcessBO,
						"findVsopActiveProcessByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteVsopActiveProcessById(String process_code) throws Exception {
		Map param = getVsopActiveProcessKeyMap(process_code) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.VsopActiveProcessBO,
						"deleteVsopActiveProcessById" ,param)) ;
		
		return result ;
	}
	
	private Map getVsopActiveProcessKeyMap(String process_code){
		Map param = new HashMap() ;
				
		param.put("process_code", process_code) ;
				
		return param ;
	}
}
