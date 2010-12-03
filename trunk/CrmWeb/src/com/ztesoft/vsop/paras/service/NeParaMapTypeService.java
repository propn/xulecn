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


public class NeParaMapTypeService  {
	public boolean insertNeParaMapType(HashMap NeParaMapType ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeParaMapType", NeParaMapType) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeParaMapTypeBO,
						"insertNeParaMapType" ,param)) ;
		return result ;
	}

	
	public boolean updateNeParaMapType(HashMap NeParaMapType ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeParaMapType", NeParaMapType) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeParaMapTypeBO,
						"updateNeParaMapType" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchNeParaMapTypeData(String name ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("name", name) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.NeParaMapTypeBO,
						"searchNeParaMapTypeData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeParaMapTypeById(String map_type_id) throws Exception {
		Map param = getNeParaMapTypeKeyMap(map_type_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeParaMapTypeBO,
						"getNeParaMapTypeById" ,param)) ;
		
		return result ;
	}
	

	public List findNeParaMapTypeByCond(String map_type_id) throws Exception {
		Map param = getNeParaMapTypeKeyMap(map_type_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeParaMapTypeBO,
						"findNeParaMapTypeByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteNeParaMapTypeById(String map_type_id) throws Exception {
		Map param = getNeParaMapTypeKeyMap(map_type_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeParaMapTypeBO,
						"deleteNeParaMapTypeById" ,param)) ;
		
		return result ;
	}
	
	private Map getNeParaMapTypeKeyMap(String map_type_id){
		Map param = new HashMap() ;
				
		param.put("map_type_id", map_type_id) ;
				
		return param ;
	}
}
