package com.ztesoft.vsop.paras.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class SpParaRecordService  {
	public boolean insertSpParaRecord(HashMap SpParaRecord ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpParaRecord", SpParaRecord) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordBO,
						"insertSpParaRecord" ,param)) ;
		return result ;
	}

	
	public boolean updateSpParaRecord(HashMap SpParaRecord ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpParaRecord", SpParaRecord) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordBO,
						"updateSpParaRecord" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSpParaRecordData(String record_name , String record_path ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("record_name", record_name) ;
		param.put("record_path", record_path) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordBO,
						"searchSpParaRecordData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSpParaRecordById(String record_id) throws Exception {
		Map param = getSpParaRecordKeyMap(record_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordBO,
						"getSpParaRecordById" ,param)) ;
		
		return result ;
	}
	

	public List findSpParaRecordByCond(String record_id) throws Exception {
		Map param = getSpParaRecordKeyMap(record_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordBO,
						"findSpParaRecordByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSpParaRecordById(String record_id) throws Exception {
		Map param = getSpParaRecordKeyMap(record_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordBO,
						"deleteSpParaRecordById" ,param)) ;
		
		return result ;
	}
	
	private Map getSpParaRecordKeyMap(String record_id){
		Map param = new HashMap() ;
				
		param.put("record_id", record_id) ;
				
		return param ;
	}
	
	/**
	 * 根据数据项记录标识在SP_ORDER_Type_RECORDS检查是否被工单类型引用
	 * @param recordId
	 * @return
	 * @throws Exception
	 */
	public String validateRecordId(String recordId) throws Exception {
		Map map = new HashMap();
		map.put("record_id", recordId);
		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordBO, "validateRecordId", map));
		return result;
	}
	
	/**
	 * 数据项记录管理中添加目标数据项时验证目标数据项的唯一性
	 * @param record_id 从js页面传过来的当前数据记录的id
	 * @param command_id 从js页面传过来的当前弹出页面所选择的目标数据项的id
	 * @return
	 * @throws Exception
	 */
	public boolean validateCommandPara(String record_id, String command_id) throws Exception {
		Map m = new HashMap();
		m.put("record_id", record_id);
		m.put("command_id", command_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpParaRecordBO, "validateCommandPara", m));
		return result;
	}
}
