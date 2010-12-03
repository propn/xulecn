package com.ztesoft.vsop.system.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class SpIntSysService  {
	/**
	     需要替换位置 说明 ：
	  1. ServiceList.SpIntSysBO  替换为ServiceList注册的服务 
	  2. searchSpIntSysData 改方法的参数名称
	  3. findSpIntSysByCond(String int_sys_id) 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertSpIntSys(HashMap SpIntSys ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpIntSys", SpIntSys) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpIntSysBO,
						"insertSpIntSys" ,param)) ;
		return result ;
	}

	
	public boolean updateSpIntSys(HashMap SpIntSys ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpIntSys", SpIntSys) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpIntSysBO,
						"updateSpIntSys" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSpIntSysData(String sysCode,String name , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("sys_code", sysCode);
	    param.put("name", name);
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SpIntSysBO,
						"searchSpIntSysData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSpIntSysById(String int_sys_id) throws Exception {
		Map param = getSpIntSysKeyMap(int_sys_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SpIntSysBO,
						"getSpIntSysById" ,param)) ;
		
		return result ;
	}
	

	public List findSpIntSysByCond(String int_sys_id) throws Exception {
		Map param = getSpIntSysKeyMap(int_sys_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SpIntSysBO,
						"findSpIntSysByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSpIntSysById(String int_sys_id) throws Exception {
		Map param = getSpIntSysKeyMap(int_sys_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpIntSysBO,
						"deleteSpIntSysById" ,param)) ;
		
		return result ;
	}
	
	private Map getSpIntSysKeyMap(String int_sys_id){
		Map param = new HashMap() ;
				
		param.put("int_sys_id", int_sys_id) ;
				
		return param ;
	}
	
	/**
	 * 新增、修改时校验系统编码是否唯一
	 * @param sys_code
	 * @return
	 * @throws Exception
	 */
	public boolean validateUniqueForSysCode(String sys_code) throws Exception {
		Map map = new HashMap();
		map.put("sys_code", sys_code);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(
						ServiceList.SpIntSysBO, "validateUniqueForSysCode", map));
		return result;
	}
}
