package com.ztesoft.vsop.order.manager.service;


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


public class OrderRelationCompareLogService  {
	/**
	     需要替换位置 说明 ：
	  1. ServiceList.MyService  替换为ServiceList注册的服务 
	  2. searchOrderRelationCompareLogData 改方法的参数名称
	  3. findOrderRelationCompareLogByCond() 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertOrderRelationCompareLog(HashMap OrderRelationCompareLog ) throws Exception {
		Map param = new HashMap() ;
		param.put("OrderRelationCompareLog", OrderRelationCompareLog) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.OrderRelationCompareLogBO,
						"insertOrderRelationCompareLog" ,param)) ;
		return result ;
	}

	
	public boolean updateOrderRelationCompareLog(HashMap OrderRelationCompareLog ) throws Exception {
		Map param = new HashMap() ;
		param.put("OrderRelationCompareLog", OrderRelationCompareLog) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.OrderRelationCompareLogBO,
						"updateOrderRelationCompareLog" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchOrderRelationCompareLogData(String oa ,String status ,String start_time,String end_time, String field_name,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("oa", oa) ;
		param.put("status", status) ;
		param.put("start_time", start_time) ;
		param.put("end_time", end_time) ;
		param.put("field_name", field_name) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.OrderRelationCompareLogBO,
						"searchOrderRelationCompareLogData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getOrderRelationCompareLogById(String order_compare_id) throws Exception {
		Map param = getOrderRelationCompareLogKeyMap(order_compare_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.OrderRelationCompareLogBO,
						"getOrderRelationCompareLogById" ,param)) ;
		
		return result ;
	}
	

	public List findOrderRelationCompareLogByCond(String order_compare_id) throws Exception {
		Map param = getOrderRelationCompareLogKeyMap(order_compare_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.OrderRelationCompareLogBO,
						"findOrderRelationCompareLogByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteOrderRelationCompareLogById(String order_compare_id) throws Exception {
		Map param = getOrderRelationCompareLogKeyMap(order_compare_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.OrderRelationCompareLogBO,
						"deleteOrderRelationCompareLogById" ,param)) ;
		
		return result ;
	}
	
	private Map getOrderRelationCompareLogKeyMap(String key){
		Map param = new HashMap() ;
				param.put("order_compare_id", key);
		return param ;
	}
	
	
	/**
	 * 属主VSOP，
	 * type:"D":按日比对;"M":按月比对
	 * @throws Exception 
	 */
	public int VSOPcompare(String type) throws Exception{
		Map param=new HashMap();
		param.put("type", type);
		int result=DataTranslate._int(ServiceManager.callJavaBeanService(ServiceList.OrderRelationCompareLogBO, "VSOPcompare", param));
		return result;
	}
	
	public boolean NVSOPcompare(String type,String timeType) throws Exception{
		Map param=new HashMap();
		param.put("type", type);
		param.put("timeType", timeType);
		boolean result=DataTranslate._boolean(ServiceManager.callJavaBeanService(ServiceList.OrderRelationCompareLogBO, "NVSOPcompare", param));
		return result;
	}
	
	public boolean GenFileToODS() throws Exception{
		Map param=new HashMap();
		boolean result=DataTranslate._boolean(ServiceManager.callJavaBeanService(ServiceList.OrderRelationCompareLogBO, "GenFileToODS", param));
		return result;
	}

}
