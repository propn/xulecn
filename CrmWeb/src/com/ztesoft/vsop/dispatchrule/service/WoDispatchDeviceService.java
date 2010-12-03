package com.ztesoft.vsop.dispatchrule.service;


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


public class WoDispatchDeviceService  {
	public boolean insertWoDispatchDevice(HashMap WoDispatchDevice ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoDispatchDevice", WoDispatchDevice) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchDeviceBO,
						"insertWoDispatchDevice" ,param)) ;
		return result ;
	}

	
	public boolean updateWoDispatchDevice(HashMap WoDispatchDevice ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoDispatchDevice", WoDispatchDevice) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchDeviceBO,
						"updateWoDispatchDevice" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchWoDispatchDeviceData(String dispatch_rule_id ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("dispatch_rule_id", dispatch_rule_id);
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchDeviceBO,
						"searchWoDispatchDeviceData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoDispatchDeviceById() throws Exception {
		Map param = getWoDispatchDeviceKeyMap() ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchDeviceBO,
						"getWoDispatchDeviceById" ,param)) ;
		
		return result ;
	}
	

	public List findWoDispatchDeviceByCond() throws Exception {
		Map param = getWoDispatchDeviceKeyMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchDeviceBO,
						"findWoDispatchDeviceByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteWoDispatchDeviceById() throws Exception {
		Map param = getWoDispatchDeviceKeyMap() ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchDeviceBO,
						"deleteWoDispatchDeviceById" ,param)) ;
		
		return result ;
	}
	
	private Map getWoDispatchDeviceKeyMap(){
		Map param = new HashMap() ;
				
		return param ;
	}
}
