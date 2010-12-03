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


public class SpOutOrderTypeService  {
	public boolean insertSpOutOrderType(HashMap SpOutOrderType ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpOutOrderType", SpOutOrderType) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOutOrderTypeBO,
						"insertSpOutOrderType" ,param)) ;
		return result ;
	}

	
	public boolean updateSpOutOrderType(HashMap SpOutOrderType ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpOutOrderType", SpOutOrderType) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOutOrderTypeBO,
						"updateSpOutOrderType" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSpOutOrderTypeData(String order_type_code , String order_tyoe_name, 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("order_type_code", order_type_code) ;
		param.put("order_type_name", order_tyoe_name) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SpOutOrderTypeBO,
						"searchSpOutOrderTypeData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSpOutOrderTypeById(String out_order_type_id) throws Exception {
		Map param = getSpOutOrderTypeKeyMap(out_order_type_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SpOutOrderTypeBO,
						"getSpOutOrderTypeById" ,param)) ;
		
		return result ;
	}
	

	public List findSpOutOrderTypeByCond(String out_order_type_id) throws Exception {
		Map param = getSpOutOrderTypeKeyMap(out_order_type_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SpOutOrderTypeBO,
						"findSpOutOrderTypeByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSpOutOrderTypeById(String out_order_type_id) throws Exception {
		Map param = getSpOutOrderTypeKeyMap(out_order_type_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOutOrderTypeBO,
						"deleteSpOutOrderTypeById" ,param)) ;
		
		return result ;
	}
	
	private Map getSpOutOrderTypeKeyMap(String out_order_type_id){
		Map param = new HashMap() ;
				
		param.put("out_order_type_id", out_order_type_id) ;
				
		return param ;
	}
	public boolean checkCode(String code) throws Exception{
		Map param = new HashMap();
		param.put("code", code);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOutOrderTypeBO,
						"checkCode" ,param)) ;
		
		return result ;
		
	}
}
