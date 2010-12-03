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


public class SpOrderTypeRecordsService  {

	public boolean insertSpOrderTypeRecords(HashMap SpOrderTypeRecords ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpOrderTypeRecords", SpOrderTypeRecords) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeRecordsBO,
						"insertSpOrderTypeRecords" ,param)) ;
		return result ;
	}

	
	public boolean updateSpOrderTypeRecords(HashMap SpOrderTypeRecords ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpOrderTypeRecords", SpOrderTypeRecords) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeRecordsBO,
						"updateSpOrderTypeRecords" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSpOrderTypeRecordsData(String out_order_type_id , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("out_order_type_id", out_order_type_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeRecordsBO,
						"searchSpOrderTypeRecordsData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSpOrderTypeRecordsById() throws Exception {
		Map param = getSpOrderTypeRecordsKeyMap() ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeRecordsBO,
						"getSpOrderTypeRecordsById" ,param)) ;
		
		return result ;
	}
	

	public List findSpOrderTypeRecordsByCond() throws Exception {
		Map param = getSpOrderTypeRecordsKeyMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeRecordsBO,
						"findSpOrderTypeRecordsByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSpOrderTypeRecordsById(String out_order_type_id,String record_id) throws Exception {
		Map param =new HashMap();
		param.put("out_order_type_id", out_order_type_id);
		param.put("record_id", record_id);

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeRecordsBO,
						"deleteSpOrderTypeRecordsById" ,param)) ;
		
		return result ;
	}
	
	private Map getSpOrderTypeRecordsKeyMap(){
		Map param = new HashMap() ;
				
		return param ;
	}
	public boolean isRelate(String out_order_type_id) throws Exception {
		Map param = new HashMap();
		param.put("out_order_type_id", out_order_type_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeRecordsBO,
						"isRelate" ,param)) ;
		
		return result ;
	}
	public boolean validateSpOrderTypeRecord(String out_order_type_id,String record_id) throws Exception {
		Map param = new HashMap();
		param.put("out_order_type_id", out_order_type_id);
		param.put("record_id", record_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpOrderTypeRecordsBO,
						"validateSpOrderTypeRecord" ,param)) ;
		
		return result ;
	}
}
