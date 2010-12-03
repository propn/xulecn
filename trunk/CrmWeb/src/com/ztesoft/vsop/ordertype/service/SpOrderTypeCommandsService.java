package com.ztesoft.vsop.ordertype.service;


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


public class SpOrderTypeCommandsService  {
	public boolean insertSpOrderTypeCommands(HashMap SpOrderTypeCommands ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpOrderTypeCommands", SpOrderTypeCommands) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeCommandsBO,
						"insertSpOrderTypeCommands" ,param)) ;
		return result ;
	}

	
	public boolean updateSpOrderTypeCommands(HashMap SpOrderTypeCommands ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpOrderTypeCommands", SpOrderTypeCommands) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeCommandsBO,
						"updateSpOrderTypeCommands" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSpOrderTypeCommandsData(String out_order_type_id ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("out_order_type_id", out_order_type_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeCommandsBO,
						"searchSpOrderTypeCommandsData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSpOrderTypeCommandsById() throws Exception {
		Map param = getSpOrderTypeCommandsKeyMap() ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeCommandsBO,
						"getSpOrderTypeCommandsById" ,param)) ;
		
		return result ;
	}
	

	public List findSpOrderTypeCommandsByCond() throws Exception {
		Map param = getSpOrderTypeCommandsKeyMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeCommandsBO,
						"findSpOrderTypeCommandsByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSpOrderTypeCommandsById(String out_order_type_id,String command_Id) throws Exception {
		Map param = new HashMap();
		param.put("out_order_type_id", out_order_type_id);
		param.put("command_Id", command_Id);

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeCommandsBO,
						"deleteSpOrderTypeCommandsById" ,param)) ;
		
		return result ;
	}
	
	private Map getSpOrderTypeCommandsKeyMap(){
		Map param = new HashMap() ;
				
		return param ;
	}
	public boolean isRelate(String out_order_type_id) throws Exception {
		Map param = new HashMap();
		param.put("out_order_type_id", out_order_type_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeCommandsBO,
						"isRelate" ,param)) ;
		
		return result ;
	}
	public boolean validateSpOrderTypeCommond(String out_order_type_id,String command_id) throws Exception {
		Map param = new HashMap();
		param.put("out_order_type_id", out_order_type_id);
		param.put("command_id", command_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeCommandsBO,
						"validateSpOrderTypeCommond" ,param)) ;
		
		return result ;
	}
}
