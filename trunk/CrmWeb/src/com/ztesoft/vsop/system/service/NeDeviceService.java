package com.ztesoft.vsop.system.service;


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


public class NeDeviceService  {
	/**
	     需要替换位置 说明 ：
	  1. ServiceList.NeDeviceBO  替换为ServiceList注册的服务 
	  2. searchNeDeviceData 改方法的参数名称
	  3. findNeDeviceByCond(String device_id) 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertNeDevice(HashMap NeDevice ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeDevice", NeDevice) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeDeviceBO,
						"insertNeDevice" ,param)) ;
		return result ;
	}

	
	public boolean updateNeDevice(HashMap NeDevice ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeDevice", NeDevice) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeDeviceBO,
						"updateNeDevice" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchNeDeviceData(String sys_code , String name,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("sys_code", sys_code) ;
		param.put("name", name) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.NeDeviceBO,
						"searchNeDeviceData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeDeviceById(String device_id) throws Exception {
		Map param = getNeDeviceKeyMap(device_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeDeviceBO,
						"getNeDeviceById" ,param)) ;
		
		return result ;
	}
	

	public List findNeDeviceByCond(String device_id) throws Exception {
		Map param = getNeDeviceKeyMap(device_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeDeviceBO,
						"findNeDeviceByCond" ,param)) ;
		
		return result ;
	}
	public List getAll() throws Exception {
		Map param = new HashMap();
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeDeviceBO,
						"getAll" ,param)) ;
		
		return result ;
	}
	
	public boolean deleteNeDeviceById(String device_id) throws Exception {
		Map param = getNeDeviceKeyMap(device_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeDeviceBO,
						"deleteNeDeviceById" ,param)) ;
		
		return result ;
	}
	
	private Map getNeDeviceKeyMap(String device_id){
		Map param = new HashMap() ;
				
		param.put("device_id", device_id) ;
				
		return param ;
	}
	public boolean startDevice(String deviceId) throws Exception {
		
		Map param = new HashMap() ;
		param.put("deviceId", deviceId) ;
		param.put("state","10A");
		
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeDeviceBO,
						"updateStateDevice" ,param)) ;
		
		return result ;
	}
	public boolean stopDevice(String deviceId) throws Exception {
		
		Map param = new HashMap() ;
		param.put("deviceId", deviceId) ;
		param.put("state","10X");
		
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeDeviceBO,
						"updateStateDevice" ,param)) ;
		
		return result ;
	}
	public boolean changeRuleDevice(String deviceId,String ruleId) throws Exception {
		
		Map param = new HashMap() ;
		param.put("deviceId", deviceId) ;
		param.put("ruleId",ruleId);
		
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeDeviceBO,
						"changeRuleDevice" ,param)) ;
		
		return result ;
	}
	public boolean  checkCodeDevice(String sysCode) throws Exception {
		
		Map param = new HashMap() ;
		param.put("sysCode", sysCode) ;
		
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeDeviceBO,
						"checkCodeDevice" ,param)) ;
		
		return result ;
	}
}
