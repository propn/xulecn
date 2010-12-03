package com.ztesoft.vsop.ordermonitor.service;


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


public class WoOrderInfoService  {
	public PageModel searchWoOrderInfoData(String user_type,String prod_no , String lan_code ,String in_start_time ,
			String in_end_time , String state ,String order ,
			String sys , String deal_start_time ,String deal_end_time ,String query_type ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("user_type", user_type) ;
		param.put("prod_no", prod_no) ;
		param.put("lan_code", lan_code) ;
		param.put("in_start_time", in_start_time) ;
		param.put("in_end_time", in_end_time) ;
		param.put("state", state) ;
		param.put("order", order) ;
		param.put("sys", sys) ;
		param.put("deal_start_time", deal_start_time) ;
		param.put("deal_end_time", deal_end_time) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		if("1".equals(query_type)){
			PageModel hisresult = DataTranslate._PageModel(
					ServiceManager.callJavaBeanService(ServiceList.WoOrderInfoHisBO,
							"searchWoOrderInfoHisData" ,param)) ;
			return hisresult;
		}
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderInfoBO,
						"searchWoOrderInfoData" ,param)) ;
		
		return result ;
	}
	public String getBatchList(String order_id) throws Exception {
		Map param = getWoOrderInfoKeyMap(order_id) ;
		String result =DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderInfoBO,
						"getBatchList" ,param));
		return result;
	}
	
	public Map getWoOrderInfoById(String order_id,String query_type) throws Exception {
		Map param = getWoOrderInfoKeyMap(order_id) ;
		
		if("1".equals(query_type)){
			Map hisresult = DataTranslate._Map(
					ServiceManager.callJavaBeanService(ServiceList.WoOrderInfoHisBO,
							"getWoOrderInfoHisById" ,param)) ;
			return hisresult;
		}
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderInfoBO,
						"getWoOrderInfoById" ,param));
		return result ;
	}
	

	private Map getWoOrderInfoKeyMap(String order_id){
		Map param = new HashMap() ;
				
		param.put("order_id", order_id) ;
				
		return param ;
	}

	/**
	 * 人工回单
	 * @param order_ids
	 * @return list
	 * @throws Exception
	 */
	public List woOrderFeedBackById(String order_ids)throws Exception{
		Map param = getWoOrderInfoKeyMap(order_ids) ;
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderInfoBO,
						"woOrderFeedBackById" ,param)) ;
		return result;
	}
}
