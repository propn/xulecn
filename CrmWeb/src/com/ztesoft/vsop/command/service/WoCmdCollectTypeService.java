package com.ztesoft.vsop.command.service;


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


public class WoCmdCollectTypeService  {
	public boolean insertWoCmdCollectType(HashMap WoCmdCollectType ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoCmdCollectType", WoCmdCollectType) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectTypeBO,
						"insertWoCmdCollectType" ,param)) ;
		return result ;
	}

	
	public boolean updateWoCmdCollectType(HashMap WoCmdCollectType ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoCmdCollectType", WoCmdCollectType) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectTypeBO,
						"updateWoCmdCollectType" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchWoCmdCollectTypeData(String iParam1 , String iParam2 ,String iParam3 , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("iParam1", iParam1) ;
		param.put("iParam2", iParam2) ;
		param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectTypeBO,
						"searchWoCmdCollectTypeData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoCmdCollectTypeById(String cmd_collect_type_id) throws Exception {
		Map param = getWoCmdCollectTypeKeyMap(cmd_collect_type_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectTypeBO,
						"getWoCmdCollectTypeById" ,param)) ;
		
		return result ;
	}
	public Map getWoCmdCollectTypeByDeviceId(String device_id) throws Exception {
		Map param = new HashMap() ;
		param.put("device_id", device_id);
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectTypeBO,
						"getWoCmdCollectTypeByDeviceId" ,param)) ;
		
		return result ;
	}

	public List findWoCmdCollectTypeByCond(String cmd_collect_type_id) throws Exception {
		Map param = getWoCmdCollectTypeKeyMap(cmd_collect_type_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectTypeBO,
						"findWoCmdCollectTypeByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteWoCmdCollectTypeById(String cmd_collect_type_id) throws Exception {
		Map param = getWoCmdCollectTypeKeyMap(cmd_collect_type_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectTypeBO,
						"deleteWoCmdCollectTypeById" ,param)) ;
		
		return result ;
	}
	
	private Map getWoCmdCollectTypeKeyMap(String cmd_collect_type_id){
		Map param = new HashMap() ;
				
		param.put("cmd_collect_type_id", cmd_collect_type_id) ;
				
		return param ;
	}
}
