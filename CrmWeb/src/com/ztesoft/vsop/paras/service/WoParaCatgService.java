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


public class WoParaCatgService  {
	
	public boolean insertWoParaCatg(HashMap WoParaCatg ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoParaCatg", WoParaCatg) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoParaCatgBo,
						"insertWoParaCatg" ,param)) ;
		return result ;
	}

	
	public boolean updateWoParaCatg(HashMap WoParaCatg ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoParaCatg", WoParaCatg) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoParaCatgBo,
						"updateWoParaCatg" ,param)) ;
		
		return result ;
	}
	
	
	public List searchWoParaCatgData() throws Exception {
		
		Map param = new HashMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoParaCatgBo,
						"searchWoParaCatgData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoParaCatgById(String para_dir_id) throws Exception {
		Map param = getWoParaCatgKeyMap(para_dir_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoParaCatgBo,
						"getWoParaCatgById" ,param)) ;
		
		return result ;
	}
	

	public List findWoParaCatgByCond(String para_dir_id) throws Exception {
		Map param = getWoParaCatgKeyMap(para_dir_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoParaCatgBo,
						"findWoParaCatgByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteWoParaCatgById(String para_dir_id) throws Exception {
		Map param = getWoParaCatgKeyMap(para_dir_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoParaCatgBo,
						"deleteWoParaCatgById" ,param)) ;
		
		return result ;
	}
	
	private Map getWoParaCatgKeyMap(String para_dir_id){
		Map param = new HashMap() ;
				
		param.put("para_dir_id", para_dir_id) ;
				
		return param ;
	}
	public boolean isRelate(String para_dir_id) throws Exception {
		Map param = getWoParaCatgKeyMap(para_dir_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoParaCatgBo,
						"isRelate" ,param)) ;
		
		return result ;
	}
}
