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


public class NeJavaEngineService  {
	
	public boolean insertNeJavaEngine(HashMap NeJavaEngine ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeJavaEngine", NeJavaEngine) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeJavaEngineBO,
						"insertNeJavaEngine" ,param)) ;
		return result ;
	}

	
	public boolean updateNeJavaEngine(HashMap NeJavaEngine ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeJavaEngine", NeJavaEngine) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeJavaEngineBO,
						"updateNeJavaEngine" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchNeJavaEngineData(String name ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("name", name) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.NeJavaEngineBO,
						"searchNeJavaEngineData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeJavaEngineById(String id) throws Exception {
		Map param = getNeJavaEngineKeyMap(id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeJavaEngineBO,
						"getNeJavaEngineById" ,param)) ;
		
		return result ;
	}
	

	public List findNeJavaEngineByCond(String id) throws Exception {
		Map param = getNeJavaEngineKeyMap(id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeJavaEngineBO,
						"findNeJavaEngineByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteNeJavaEngineById(String id) throws Exception {
		Map param = getNeJavaEngineKeyMap(id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeJavaEngineBO,
						"deleteNeJavaEngineById" ,param)) ;
		
		return result ;
	}
	
	private Map getNeJavaEngineKeyMap(String id){
		Map param = new HashMap() ;
				
		param.put("id", id) ;
				
		return param ;
	}
}
