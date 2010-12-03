package com.ztesoft.vsop.module.service;


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


public class PbBusinessObjService  {
	public boolean insertPbBusinessObj(HashMap PbBusinessObj ) throws Exception {
		Map param = new HashMap() ;
		param.put("PbBusinessObj", PbBusinessObj) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PbBusinessObjBO,
						"insertPbBusinessObj" ,param)) ;
		return result ;
	}

	
	public boolean updatePbBusinessObj(HashMap PbBusinessObj ) throws Exception {
		Map param = new HashMap() ;
		param.put("PbBusinessObj", PbBusinessObj) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PbBusinessObjBO,
						"updatePbBusinessObj" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchPbBusinessObjData(String business_obj_name , String obj_type_code ,String business_obj_id , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("business_obj_name", business_obj_name) ;
		param.put("obj_type_code", obj_type_code) ;
		param.put("business_obj_id", business_obj_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.PbBusinessObjBO,
						"searchPbBusinessObjData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getPbBusinessObjById(String business_obj_id) throws Exception {
		Map param = getPbBusinessObjKeyMap(business_obj_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.PbBusinessObjBO,
						"getPbBusinessObjById" ,param)) ;
		
		return result ;
	}
	

	public List findPbBusinessObjByCond(String business_obj_id) throws Exception {
		Map param = getPbBusinessObjKeyMap(business_obj_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.PbBusinessObjBO,
						"findPbBusinessObjByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deletePbBusinessObjById(String business_obj_id) throws Exception {
		Map param = getPbBusinessObjKeyMap(business_obj_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PbBusinessObjBO,
						"deletePbBusinessObjById" ,param)) ;
		
		return result ;
	}
	
	private Map getPbBusinessObjKeyMap(String business_obj_id){
		Map param = new HashMap() ;
				
		param.put("business_obj_id", business_obj_id) ;
				
		return param ;
	}
}
