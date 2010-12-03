package com.ztesoft.vsop.protocol.service;


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


public class NeCommunicateProtocolService  {
	
	public boolean insertNeCommunicateProtocol(HashMap NeCommunicateProtocol ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeCommunicateProtocol", NeCommunicateProtocol) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommunicateProtocolBO,
						"insertNeCommunicateProtocol" ,param)) ;
		return result ;
	}

	
	public boolean updateNeCommunicateProtocol(HashMap NeCommunicateProtocol ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeCommunicateProtocol", NeCommunicateProtocol) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommunicateProtocolBO,
						"updateNeCommunicateProtocol" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchNeCommunicateProtocolData(String name,int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("name", name);
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.NeCommunicateProtocolBO,
						"searchNeCommunicateProtocolData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeCommunicateProtocolById(String communicate_protocol_id) throws Exception {
		Map param = getNeCommunicateProtocolKeyMap(communicate_protocol_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeCommunicateProtocolBO,
						"getNeCommunicateProtocolById" ,param)) ;
		
		return result ;
	}
	

	public List findNeCommunicateProtocolByCond(String communicate_protocol_id) throws Exception {
		Map param = getNeCommunicateProtocolKeyMap(communicate_protocol_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeCommunicateProtocolBO,
						"findNeCommunicateProtocolByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCommunicateProtocolById(String communicate_protocol_id) throws Exception {
		Map param = getNeCommunicateProtocolKeyMap(communicate_protocol_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommunicateProtocolBO,
						"deleteNeCommunicateProtocolById" ,param)) ;
		
		return result ;
	}
	
	private Map getNeCommunicateProtocolKeyMap(String communicate_protocol_id){
		Map param = new HashMap() ;
				
		param.put("communicate_protocol_id", communicate_protocol_id) ;
				
		return param ;
	}
}
