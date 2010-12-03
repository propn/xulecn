package com.ztesoft.vsop.command.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class WoCommandCollectService  {
	public boolean insertWoCommandCollect(HashMap WoCommandCollect ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoCommandCollect", WoCommandCollect) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoCommandCollectBO,
						"insertWoCommandCollect" ,param)) ;
		return result ;
	}

	
	public boolean updateWoCommandCollect(HashMap WoCommandCollect ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoCommandCollect", WoCommandCollect) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoCommandCollectBO,
						"updateWoCommandCollect" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchWoCommandCollectData(String device_id ,int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("device_id", device_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoCommandCollectBO,
						"searchWoCommandCollectData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoCommandCollectById(String command_collect_id) throws Exception {
		Map param = getWoCommandCollectKeyMap(command_collect_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoCommandCollectBO,
						"getWoCommandCollectById" ,param)) ;
		
		return result ;
	}
	

	public List findWoCommandCollectByCond(String command_collect_id) throws Exception {
		Map param = getWoCommandCollectKeyMap(command_collect_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoCommandCollectBO,
						"findWoCommandCollectByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteWoCommandCollectById(String command_collect_id) throws Exception {
		Map param = getWoCommandCollectKeyMap(command_collect_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoCommandCollectBO,
						"deleteWoCommandCollectById" ,param)) ;
		
		return result ;
	}
	
	private Map getWoCommandCollectKeyMap(String command_collect_id){
		Map param = new HashMap() ;
				
		param.put("command_collect_id", command_collect_id) ;
				
		return param ;
	}
	
	/**
	 * 验证集合顺序，不可以重复
	 * @param collect_seq
	 * @return
	 * @throws Exception
	 */
	public boolean validateCollectSeq(String device_id, Integer collect_seq) throws Exception {
		Map map = new HashMap();
		map.put("device_id", device_id);
		map.put("collect_seq", collect_seq);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(
						ServiceList.WoCommandCollectBO, "validateCollectSeq", map));
		return result;
	}
	/**
	 * 验证对端系统是否有关联的指令模板集
	 * @param device_id
	 * @return
	 * @throws Exception
	 */
	public boolean validateCollectByDeviceId(String device_id) throws Exception {
		Map map = new HashMap();
		map.put("device_id", device_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(
						ServiceList.WoCommandCollectBO, "validateCollectByDeviceId", map));
		return result;
	}

}
