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


public class NeValueMapListService  {
	public boolean insertNeValueMapList(HashMap NeValueMapList,HashMap paraMap ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeValueMapList", NeValueMapList) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeValueMapListBO,
						"insertNeValueMapList" ,param)) ;
		return result ;
	}

	
	public boolean updateNeValueMapList(HashMap NeValueMapList,HashMap paraMap ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeValueMapList", NeValueMapList) ;
		param.put("paraMap", paraMap) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeValueMapListBO,
						"updateNeValueMapList" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchNeValueMapListData(String map_type_id ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("map_type_id", map_type_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.NeValueMapListBO,
						"searchNeValueMapListData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeValueMapListById(String map_type_id ,String para_value,String map_value) throws Exception {
		Map param = new HashMap();
		param.put("map_type_id", map_type_id);
		param.put("para_value", para_value);
		param.put("map_value",map_value );
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeValueMapListBO,
						"getNeValueMapListById" ,param)) ;
		
		return result ;
	}
	

	public List findNeValueMapListByCond() throws Exception {
		Map param = getNeValueMapListKeyMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeValueMapListBO,
						"findNeValueMapListByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteNeValueMapListById(HashMap paraMap) throws Exception {
		Map param = getNeValueMapListKeyMap() ;
		param.put("paraMap", paraMap);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeValueMapListBO,
						"deleteNeValueMapListById" ,param)) ;
		
		return result ;
	}
	public boolean validateRepart(HashMap paraMap) throws Exception {
		Map param = new HashMap();
/*		//Map maplist = (Map) paraMap.get("NeValueMapList");
		param.put("map_type_id", paraMap.get("map_type_id"));
		param.put("para_value", paraMap.get("para_value"));
		param.put("map_value",paraMap.get("map_value") );*/
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeValueMapListBO,
						"validateRepart" ,paraMap)) ;
		
		return result ;
	}
	private Map getNeValueMapListKeyMap(){
		Map param = new HashMap() ;
				
		return param ;
	}
}
