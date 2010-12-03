package com.ztesoft.vsop.protocol.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class NeProtocolService  {
	public boolean insertNeProtocol(HashMap NeProtocol ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeProtocol", NeProtocol) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeProtocolBO,
						"insertNeProtocol" ,param)) ;
		return result ;
	}

	
	public boolean updateNeProtocol(HashMap NeProtocol ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeProtocol", NeProtocol) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeProtocolBO,
						"updateNeProtocol" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchNeProtocolData(String name,int pageIndex , int pageSize) throws Exception {
		Map param = new HashMap() ;
		param.put("name", name);
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.NeProtocolBO,
						"searchNeProtocolData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeProtocolById(String ne_protocol_id) throws Exception {
		Map param = getNeProtocolKeyMap(ne_protocol_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeProtocolBO,
						"getNeProtocolById" ,param)) ;
		
		return result ;
	}
	

	public List findNeProtocolByCond(String ne_protocol_id) throws Exception {
		Map param = getNeProtocolKeyMap(ne_protocol_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeProtocolBO,
						"findNeProtocolByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteNeProtocolById(String ne_protocol_id) throws Exception {
		Map param = getNeProtocolKeyMap(ne_protocol_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeProtocolBO,
						"deleteNeProtocolById" ,param)) ;
		
		return result ;
	}
	
	private Map getNeProtocolKeyMap(String ne_protocol_id){
		Map param = new HashMap() ;
				
		param.put("ne_protocol_id", ne_protocol_id) ;
				
		return param ;
	}
}
