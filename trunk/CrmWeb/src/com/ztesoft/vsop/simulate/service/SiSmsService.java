package com.ztesoft.vsop.simulate.service;


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


public class SiSmsService  {
	/**
	     需要替换位置 说明 ：
	  1. ServiceList.MyService  替换为ServiceList注册的服务 
	  2. searchSiSmsData 改方法的参数名称
	  3. findSiSmsByCond(String radom_code) 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertSiSms(HashMap SiSms ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiSms", SiSms) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiSmsBO,
						"insertSiSms" ,param)) ;
		return result ;
	}

	
	public boolean updateSiSms(HashMap SiSms ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiSms", SiSms) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiSmsBO,
						"updateSiSms" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSiSmsData(String iParam1 , String iParam2 ,String iParam3 , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_no", iParam1) ;
		param.put("sms_type", iParam2) ;
		param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiSmsBO,
						"searchSiSmsData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSiSmsById(String radom_code) throws Exception {
		Map param = getSiSmsKeyMap(radom_code) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiSmsBO,
						"getSiSmsById" ,param)) ;
		
		return result ;
	}
	

	public List findSiSmsByCond(String radom_code) throws Exception {
		Map param = getSiSmsKeyMap(radom_code) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiSmsBO,
						"findSiSmsByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSiSmsById(String radom_code) throws Exception {
		Map param = getSiSmsKeyMap(radom_code) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiSmsBO,
						"deleteSiSmsById" ,param)) ;
		
		return result ;
	}
	
	private Map getSiSmsKeyMap(String radom_code){
		Map param = new HashMap() ;
				
		param.put("radom_code", radom_code) ;
				
		return param ;
	}
	public String confirmAccept(HashMap SiSms ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiSms", SiSms) ;

		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.SiSmsBO,
						"confirmAccept" ,param)) ;
		
		return result ;
	}
	public String cancelAccept(HashMap SiSms ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiSms", SiSms) ;

		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.SiSmsBO,
						"cancelAccept" ,param)) ;
		
		return result ;
	}
	
	
}
