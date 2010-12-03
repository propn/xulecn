package com.ztesoft.vsop.ordermonitor.service;


import java.io.Serializable;
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


public class WoNeOrderService  {
	public boolean updateWoNeOrder(HashMap WoNeOrder ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoNeOrder", WoNeOrder) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderBO,
						"updateWoNeOrder" ,param)) ;
		
		return result ;
	}
	public boolean changeState(String ne_order_id,String state ) throws Exception {
		Map param = new HashMap() ;
		param.put("ne_order_id", ne_order_id) ;
		param.put("state", state) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderBO,
						"changeState" ,param)) ;
		
		return result ;
	}	
	
	public PageModel searchWoNeOrderData(String user_type,String prod_no , String lan_code ,String in_start_time ,
			String in_end_time , String state ,String order ,
			String device_id , String deal_start_time ,String deal_end_time ,String query_type ,
			int pageIndex , int pageSize) throws Exception {
		Map param = new HashMap() ;
		param.put("user_type", user_type) ;
		param.put("prod_no", prod_no) ;
		param.put("lan_code", lan_code) ;
		param.put("in_start_time", in_start_time) ;
		param.put("in_end_time", in_end_time) ;
		param.put("state", state) ;
		param.put("order", order) ;
		param.put("device_id", device_id) ;
		param.put("deal_start_time", deal_start_time) ;
		param.put("deal_end_time", deal_end_time) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;

		if("1".equals(query_type)){
			PageModel hisresult = DataTranslate._PageModel(
					ServiceManager.callJavaBeanService(ServiceList.WoNeOrderHisBO,
							"searchWoNeOrderHisData" ,param)) ;
			
			return hisresult ;
		}
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderBO,
						"searchWoNeOrderData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoNeOrderById(String ne_order_id,String query_type) throws Exception {
		Map param = getWoNeOrderKeyMap(ne_order_id) ;
		if("1".equals(query_type)){
			Map result = DataTranslate._Map(
					ServiceManager.callJavaBeanService(ServiceList.WoNeOrderHisBO,
							"getWoNeOrderHisById" ,param)) ;
			return result;
		}
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderBO,
						"getWoNeOrderById" ,param)) ;
		
		return result ;
	}
	
	public boolean successWoNeOrder(String ne_order_id,String state,String is_success) throws Exception {
		Map param = getWoNeOrderKeyMap(ne_order_id) ;
		param.put("state", state);
		param.put("is_success", is_success);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderBO,
						"successWoNeOrder" ,param)) ;
		
		return result ;
	}

	
	private Map getWoNeOrderKeyMap(String ne_order_id){
		Map param = new HashMap() ;
				
		param.put("ne_order_id", ne_order_id) ;
				
		return param ;
	}
	//子工单人工回单
	public List subOrderBackByMan(String neorderIds) throws Exception {
		Map param = new HashMap();
		param.put("neorderIds", neorderIds);
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderHisBO,
						"subOrderBackByMan" ,param)) ;
		return result ;
		
	}
	//重送业务平台
	public List reSendPlatforms(String neorderIds) throws Exception {
		Map param = new HashMap();
		param.put("neorderIds", neorderIds);
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderHisBO,
						"reSendPlatforms" ,param)) ;
		
		return result ;
	}	
}
