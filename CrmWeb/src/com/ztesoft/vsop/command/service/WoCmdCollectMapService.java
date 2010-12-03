package com.ztesoft.vsop.command.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class WoCmdCollectMapService  {
	public boolean insertWoCmdCollectMap(HashMap WoCmdCollectMap ,HashMap paraMap) throws Exception {
		Map param = new HashMap() ;
		param.put("WoCmdCollectMap", WoCmdCollectMap) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectMapBO,
						"insertWoCmdCollectMap" ,param)) ;
		return result ;
	}

	
	public boolean updateWoCmdCollectMap(HashMap WoCmdCollectMap,HashMap paraMap ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoCmdCollectMap", WoCmdCollectMap) ;
		param.put("paraMap", paraMap) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectMapBO,
						"updateWoCmdCollectMap" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchWoCmdCollectMapData(String command_collect_id ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("command_collect_id", command_collect_id) ;

		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectMapBO,
						"searchWoCmdCollectMapData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoCmdCollectMapById(String command_collect_id ,String order_type_id,String order_act_type) throws Exception {
		Map param = new HashMap();
		param.put("command_collect_id", command_collect_id);
		param.put("order_type_id", order_type_id);
		param.put("order_act_type",order_act_type );
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectMapBO,
						"getWoCmdCollectMapById" ,param)) ;
		
		return result ;
	}
	

	public List findWoCmdCollectMapByCond() throws Exception {
		Map param = getWoCmdCollectMapKeyMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectMapBO,
						"findWoCmdCollectMapByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteWoCmdCollectMapById(HashMap paraMap) throws Exception {
		Map param = new HashMap();
		param.put("paraMap", paraMap);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectMapBO,
						"deleteWoCmdCollectMapById" ,param)) ;
		
		return result ;
	}
	
	private Map getWoCmdCollectMapKeyMap(){
		Map param = new HashMap() ;
				
		return param ;
	}
	public boolean isRelate(String command_collect_id) throws Exception {
		Map param = new HashMap() ;
		param.put("command_collect_id", command_collect_id);

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoCmdCollectMapBO,
						"isRelate" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 验证工单类型是否唯一
	 * @param command_collect_id
	 * @param order_type_id
	 * @return
	 * @throws Exception
	 */
	public boolean validateOrderType(String command_collect_id, String order_type_id) throws Exception {
		Map map = new HashMap();
		map.put("command_collect_id", command_collect_id);
		map.put("order_type_id", order_type_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(
						ServiceList.WoCmdCollectMapBO, "validateOrderType", map));
		return result;
	}
	
	/**
	 * 验证工单动作是否唯一
	 * @param command_collect_id
	 * @param order_act_type
	 * @return
	 * @throws Exception
	 */
	public boolean validateOrderActType(String command_collect_id, String order_act_type) throws Exception {
		Map map = new HashMap();
		map.put("command_collect_id", command_collect_id);
		map.put("order_act_type", order_act_type);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(
						ServiceList.WoCmdCollectMapBO, "validateOrderActType", map));
		return result;
	}
}
