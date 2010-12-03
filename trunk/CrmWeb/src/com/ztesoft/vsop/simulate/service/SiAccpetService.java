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


public class SiAccpetService  {
	/**
	     需要替换位置 说明 ：
	  1. ServiceList.MyService  替换为ServiceList注册的服务 
	  2. searchSiAccpetData 改方法的参数名称
	  3. findSiAccpetByCond(String app_id) 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertSiAccpet(HashMap SiAccpet ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiAccpet", SiAccpet) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiAccpetBO,
						"insertSiAccpet" ,param)) ;
		return result ;
	}

	
	public boolean updateSiAccpet(HashMap SiAccpet ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiAccpet", SiAccpet) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiAccpetBO,
						"updateSiAccpet" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSiAccpetData(String iParam1 , String iParam2  , String iParam3,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_no", iParam1) ;
		param.put("app_type", iParam2) ;
		param.put("auth_state", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiAccpetBO,
						"searchSiAccpetData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSiAccpetById(String app_id) throws Exception {
		Map param = getSiAccpetKeyMap(app_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiAccpetBO,
						"getSiAccpetById" ,param)) ;
		
		return result ;
	}
	

	public List findSiAccpetByCond(String app_id) throws Exception {
		Map param = getSiAccpetKeyMap(app_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiAccpetBO,
						"findSiAccpetByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSiAccpetById(String app_id) throws Exception {
		Map param = getSiAccpetKeyMap(app_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiAccpetBO,
						"deleteSiAccpetById" ,param)) ;
		
		return result ;
	}
	
	private Map getSiAccpetKeyMap(String app_id){
		Map param = new HashMap() ;
				
		param.put("app_id", app_id) ;
				
		return param ;
	}
	public String authSiAccpet(HashMap SiAccpet)throws Exception{
		Map param = new HashMap() ;
		param.put("SiAccpet", SiAccpet) ;
		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.SiAccpetBO,
						"authSiAccpet" ,param)) ;
		return result;
	}
	public String workListFKToVSOP(HashMap SiAccpet)throws Exception{
		Map param = new HashMap() ;
		param.put("SiAccpet", SiAccpet) ;
		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.SiAccpetBO,
						"workListFKToVSOP" ,param)) ;
		return result;
	}
	public String subscribeToVSOP(HashMap SiAccpet)throws Exception{
		Map param = new HashMap() ;
		param.put("SiAccpet", SiAccpet) ;
		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.SiAccpetBO,
						"subscribeToVSOP" ,param)) ;
		return result;
	}
	
	
	
}
