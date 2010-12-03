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


public class SiVproductService  {
	/**
	     需要替换位置 说明 ：
	  1. ServiceList.MyService  替换为ServiceList注册的服务 
	  2. searchSiVproductData 改方法的参数名称
	  3. findSiVproductByCond(String id) 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertSiVproduct(HashMap SiVproduct ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiVproduct", SiVproduct) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiVproductBO,
						"insertSiVproduct" ,param)) ;
		return result ;
	}

	
	public boolean updateSiVproduct(HashMap SiVproduct ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiVproduct", SiVproduct) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiVproductBO,
						"updateSiVproduct" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSiVproductData(String iParam1 , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("app_id", iParam1) ;
		/*param.put("iParam2", iParam2) ;
		param.put("iParam3", iParam3) ;*/
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiVproductBO,
						"searchSiVproductData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSiVproductById(String id) throws Exception {
		Map param = getSiVproductKeyMap(id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiVproductBO,
						"getSiVproductById" ,param)) ;
		
		return result ;
	}
	

	public List findSiVproductByCond(String id) throws Exception {
		Map param = getSiVproductKeyMap(id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiVproductBO,
						"findSiVproductByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSiVproductById(String id) throws Exception {
		Map param = getSiVproductKeyMap(id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiVproductBO,
						"deleteSiVproductById" ,param)) ;
		
		return result ;
	}
	
	private Map getSiVproductKeyMap(String id){
		Map param = new HashMap() ;
				
		param.put("id", id) ;
				
		return param ;
	}
}
