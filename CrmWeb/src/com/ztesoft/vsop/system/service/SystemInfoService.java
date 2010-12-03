package com.ztesoft.vsop.system.service;


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


public class SystemInfoService  {
	
	public boolean insertSystemInfo(HashMap SystemInfo ) throws Exception {
		Map param = new HashMap() ;
		param.put("SystemInfo", SystemInfo) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SystemInfoBo,
						"insertSystemInfo" ,param)) ;
		return result ;
	}

	
	public boolean updateSystemInfo(HashMap SystemInfo ) throws Exception {
		Map param = new HashMap() ;
		param.put("SystemInfo", SystemInfo) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SystemInfoBo,
						"updateSystemInfo" ,param)) ;
		
		return result ;
	}
	
	public PageModel searchSystemInfoData(int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SystemInfoBo,
						"searchSystemInfoData" ,param)) ;
		
		return result ;
	}
	
	public PageModel searchSystemInfoData(String partner_id,int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		if(null!=partner_id && !"".equals(partner_id)){
			param.put("partner_id", partner_id);
		}
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SystemInfoBo,
						"searchSystemInfoData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSystemInfoById(String system_code) throws Exception {
		Map param = getSystemInfoKeyMap(system_code) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SystemInfoBo,
						"getSystemInfoById" ,param)) ;
		
		return result ;
	}
	

	public List findSystemInfoByCond(String system_code) throws Exception {
		Map param = getSystemInfoKeyMap(system_code) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SystemInfoBo,
						"findSystemInfoByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSystemInfoById(String system_code) throws Exception {
		Map param = getSystemInfoKeyMap(system_code) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SystemInfoBo,
						"deleteSystemInfoById" ,param)) ;
		
		return result ;
	}
	
	private Map getSystemInfoKeyMap(String system_code){
		Map param = new HashMap() ;
				
		param.put("system_code", system_code) ;
				
		return param ;
	}
}
