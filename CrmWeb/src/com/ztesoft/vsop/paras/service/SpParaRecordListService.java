package com.ztesoft.vsop.paras.service;


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


public class SpParaRecordListService  {
	public boolean insertSpParaRecordList(HashMap SpParaRecordList ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpParaRecordList", SpParaRecordList) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordListBO,
						"insertSpParaRecordList" ,param)) ;
		return result ;
	}

	
	public boolean updateSpParaRecordList(HashMap SpParaRecordList ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpParaRecordList", SpParaRecordList) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordListBO,
						"updateSpParaRecordList" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSpParaRecordListData(String record_id,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("record_id", record_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordListBO,
						"searchSpParaRecordListData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSpParaRecordListById() throws Exception {
		Map param = getSpParaRecordListKeyMap() ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordListBO,
						"getSpParaRecordListById" ,param)) ;
		
		return result ;
	}
	

	public List findSpParaRecordListByCond() throws Exception {
		Map param = getSpParaRecordListKeyMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordListBO,
						"findSpParaRecordListByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSpParaRecordListById(String recordId,String commandId) throws Exception {
		Map param = new HashMap();
		param.put("recordId", recordId);
		param.put("commandId", commandId);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordListBO,
						"deleteSpParaRecordListById" ,param)) ;
		
		return result ;
	}
	
	private Map getSpParaRecordListKeyMap(){
		Map param = new HashMap() ;
				
		return param ;
	}
	public boolean isRelate(String record_id) throws Exception {
		Map param = new HashMap();
		param.put("record_id", record_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordListBO,
						"isRelate" ,param)) ;
		
		return result ;
	}
}
