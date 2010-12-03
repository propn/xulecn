package com.ztesoft.vsop.paras.service;


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


public class NeCommandCatgService  {
	public boolean insertNeCommandCatg(HashMap NeCommandCatg ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeCommandCatg", NeCommandCatg) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandCatgBO,
						"insertNeCommandCatg" ,param)) ;
		return result ;
	}

	
	public boolean updateNeCommandCatg(HashMap NeCommandCatg ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeCommandCatg", NeCommandCatg) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandCatgBO,
						"updateNeCommandCatg" ,param)) ;
		
		return result ;
	}
	
	
	public List searchNeCommandCatgData() throws Exception {
		
		Map param = new HashMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandCatgBO,
						"searchNeCommandCatgData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeCommandCatgById(String command_catalog_id) throws Exception {
		Map param = getNeCommandCatgKeyMap(command_catalog_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandCatgBO,
						"getNeCommandCatgById" ,param)) ;
		
		return result ;
	}
	

	public List findNeCommandCatgByCond(String command_catalog_id) throws Exception {
		Map param = getNeCommandCatgKeyMap(command_catalog_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandCatgBO,
						"findNeCommandCatgByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCommandCatgById(String command_catalog_id) throws Exception {
		Map param = getNeCommandCatgKeyMap(command_catalog_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandCatgBO,
						"deleteNeCommandCatgById" ,param)) ;
		
		return result ;
	}
	
	private Map getNeCommandCatgKeyMap(String command_catalog_id){
		Map param = new HashMap() ;
				
		param.put("command_catalog_id", command_catalog_id) ;
				
		return param ;
	}
	public boolean isRelate(String para_dir_id) throws Exception {
		Map param = getNeCommandCatgKeyMap(para_dir_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandCatgBO,
						"isRelate" ,param)) ;
		
		return result ;
	}
}
