package com.ztesoft.vsop.ordermonitor.service;


import java.util.HashMap;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class WoNeOrderFeebackService  {
	public boolean updateWoNeOrderFeeback(HashMap WoNeOrderFeeback ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoNeOrderFeeback", WoNeOrderFeeback) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderFeebackBO,
						"updateWoNeOrderFeeback" ,param)) ;
		
		return result ;
	}
	public boolean changeState(String ne_order_id,String state ) throws Exception {
		Map param = new HashMap() ;
		param.put("ne_order_id", ne_order_id) ;
		param.put("state", state) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderFeebackBO,
						"changeState" ,param)) ;
		
		return result ;
	}	
	public PageModel searchWoNeOrderFeebackData(String id , String query_type, 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("ne_order_id", id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		if("1".equals(query_type)){
			PageModel result = DataTranslate._PageModel(
					ServiceManager.callJavaBeanService(ServiceList.WoNeOrderFeebackHisBO,
							"searchWoNeOrderFeebackHisData" ,param)) ;
			
			return result ;
		}
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderFeebackBO,
						"searchWoNeOrderFeebackData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoNeOrderFeebackById(String id,String query_type) throws Exception {
		Map param = getWoNeOrderFeebackKeyMap(id) ;
		if("1".equals(query_type)){
			Map result = DataTranslate._Map(
					ServiceManager.callJavaBeanService(ServiceList.WoNeOrderFeebackHisBO,
							"getWoNeOrderFeebackHisById" ,param)) ;
			
			return result ;
		}
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderFeebackBO,
						"getWoNeOrderFeebackById" ,param)) ;
		
		return result ;
	}
	public boolean successWoNeOrderFeeback(String ne_order_id,String state,String feeback_info) throws Exception {
		Map param = new HashMap();
		param.put("ne_order_id", ne_order_id);
		param.put("state", state);
		param.put("feeback_info", feeback_info);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderFeebackBO,
						"successWoNeOrderFeeback" ,param)) ;
		
		return result ;
	}
	private Map getWoNeOrderFeebackKeyMap(String id){
		Map param = new HashMap() ;
				
		param.put("id", id) ;
				
		return param ;
	}
}
