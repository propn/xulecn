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


public class SpOutMsgFeedbackService  {
	
	public Map getInitPara(String refresh_time,String alarm) throws Exception{
		Map param = new HashMap();
		param.put("refresh_time",refresh_time );
		param.put("alarm", alarm);
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SpOutMsgFeedbackBO,
						"getInitPara" ,param)) ;
		
		return result ;
	}
	public PageModel searchSpOutMsgFeedbackData(String prod_no ,String in_start_time ,
			String in_end_time , String state ,String order ,
			String sys , String deal_start_time ,String deal_end_time ,String query_type ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("prod_no", prod_no) ;
		param.put("in_start_time", in_start_time) ;
		param.put("in_end_time", in_end_time) ;
		param.put("state", state) ;
		param.put("order", order) ;
		param.put("sys", sys) ;
		param.put("deal_start_time", deal_start_time) ;
		param.put("deal_end_time", deal_end_time) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		if("1".equals(query_type)){  //历史工单列表
			PageModel hisresult = DataTranslate._PageModel(
					ServiceManager.callJavaBeanService(ServiceList.SpOutMsgFeedbackHisBO,
							"searchSpOutMsgFeedbackHisData" ,param)) ;
			return hisresult;
		}
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SpOutMsgFeedbackBO,
						"searchSpOutMsgFeedbackData" ,param)) ;
		
		return result ;
	}
	public List searchMsgStateData(String style) throws Exception {
		Map param = new HashMap() ;
		param.put("style", style);
		if(null == style)
			return null;
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SpOutMsgFeedbackBO,
						"searchMsgStateData" ,param)) ;
		
		return result ;
	}
	public boolean resetOrder(String id) throws Exception {
		Map param = new HashMap();
		param.put("id",id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOutMsgFeedbackBO,
						"resetOrder", param));
		return result;
	}
	public Map getSpOutMsgFeedbackById(String id,String query_type) throws Exception {
		Map param = getSpOutMsgFeedbackKeyMap(id) ;
		if("1".equals(query_type)){  //历史工单列表
			Map hisresult = DataTranslate._Map(
					ServiceManager.callJavaBeanService(ServiceList.SpOutMsgFeedbackHisBO,
							"getSpOutMsgFeedbackHisById" ,param)) ;
			return hisresult;
		}
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SpOutMsgFeedbackBO,
						"getSpOutMsgFeedbackById" ,param)) ;
		
		return result ;
	}
	
	
	private Map getSpOutMsgFeedbackKeyMap(String id){
		Map param = new HashMap() ;
				
		param.put("id", id) ;
				
		return param ;
	}}
