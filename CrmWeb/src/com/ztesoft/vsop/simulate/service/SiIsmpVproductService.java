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


public class SiIsmpVproductService  {
	/**
	     需要替换位置 说明 ：
	  1. ServiceList.MyService  替换为ServiceList注册的服务 
	  2. searchSiIsmpVproductData 改方法的参数名称
	  3. findSiIsmpVproductByCond() 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertSiIsmpVproduct(HashMap SiIsmpVproduct ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiIsmpVproduct", SiIsmpVproduct) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiIsmpVproductBO,
						"insertSiIsmpVproduct" ,param)) ;
		return result ;
	}

	
	public boolean updateSiIsmpVproduct(HashMap SiIsmpVproduct ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiIsmpVproduct", SiIsmpVproduct) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiIsmpVproductBO,
						"updateSiIsmpVproduct" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSiIsmpVproductData(String iParam1 , String iParam2 ,String iParam3 , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("user_type", iParam1) ;
		param.put("product_no", iParam2) ;
		param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiIsmpVproductBO,
						"searchSiIsmpVproductData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSiIsmpVproductById(String id) throws Exception {
		Map param = getSiIsmpVproductKeyMap(id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiIsmpVproductBO,
						"getSiIsmpVproductById" ,param)) ;
		
		return result ;
	}
	

	public List findSiIsmpVproductByCond(String id) throws Exception {
		Map param = getSiIsmpVproductKeyMap(id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiIsmpVproductBO,
						"findSiIsmpVproductByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSiIsmpVproductById(String id) throws Exception {
		Map param = getSiIsmpVproductKeyMap(id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiIsmpVproductBO,
						"deleteSiIsmpVproductById" ,param)) ;
		
		return result ;
	}
	
	private Map getSiIsmpVproductKeyMap(String id){
		Map param = new HashMap() ;
		param.put("id", id) ;		
		return param ;
	}
	public String syncVproduct(HashMap SiIsmpVproduct ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiIsmpVproduct", SiIsmpVproduct) ;
		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.SiIsmpVproductBO,
						"syncVproduct" ,param)) ;
		
		return result ;
	}
}
