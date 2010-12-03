package com.ztesoft.vsop.ordermonitor.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class SpOutMsgSynhbService  {
	public Map getInitPara(String refresh_time,String alarm) throws Exception{
		Map param = new HashMap();
		param.put("refresh_time",refresh_time );
		param.put("alarm", alarm);
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SpOutMsgSynhbBO,
						"getInitPara" ,param)) ;
		
		return result ;
	}
	public PageModel searchSpOutMsgSynhbData(String prod_no , String in_start_time ,
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
					ServiceManager.callJavaBeanService(ServiceList.SpOutMsgSynhbHisBO,
							"searchSpOutMsgSynhbHisData" ,param)) ;
			return hisresult;
		}
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SpOutMsgSynhbBO,
						"searchSpOutMsgSynhbData" ,param)) ;
		
		return result ;
	}
	public List searchMsgStateData(String style) throws Exception {
		Map param = new HashMap() ;
		param.put("style", style);
		if(null == style)
			return null;
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SpOutMsgSynhbBO,
						"searchMsgStateData" ,param)) ;
		
		return result ;
	}
	public Map getSpOutMsgSynhbById(String id,String query_type) throws Exception {
		Map param = getSpOutMsgSynhbKeyMap(id) ;
		if("1".equals(query_type)){  //历史工单列表
			Map hisresult = DataTranslate._Map(
					ServiceManager.callJavaBeanService(ServiceList.SpOutMsgSynhbHisBO,
							"getSpOutMsgSynhbHisById" ,param)) ;
			return hisresult;
		}
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SpOutMsgSynhbBO,
						"getSpOutMsgSynhbById" ,param)) ;
		
		return result ;
	}
	public boolean resetOrder(String id) throws Exception {
		Map param = new HashMap();
		param.put("id",id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOutMsgSynhbBO,
						"resetOrder", param));
		return result;
	}
	private Map getSpOutMsgSynhbKeyMap(String id){
		Map param = new HashMap() ;
				
		param.put("id", id) ;
				
		return param ;
	}
}
