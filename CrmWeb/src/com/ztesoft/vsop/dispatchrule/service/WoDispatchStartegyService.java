package com.ztesoft.vsop.dispatchrule.service;


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


public class WoDispatchStartegyService  {
	public boolean insertWoDispatchStartegy(HashMap WoDispatchStartegy ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoDispatchStartegy", WoDispatchStartegy) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchStartegyBO,
						"insertWoDispatchStartegy" ,param)) ;
		return result ;
	}

	
	public boolean updateWoDispatchStartegy(HashMap WoDispatchStartegy ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoDispatchStartegy", WoDispatchStartegy) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchStartegyBO,
						"updateWoDispatchStartegy" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchWoDispatchStartegyData(String dispatch_rule_id ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("dispatch_rule_id", dispatch_rule_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchStartegyBO,
						"searchWoDispatchStartegyData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoDispatchStartegyById(String dispatch_rule_id,String item_code) throws Exception {
		Map param = getWoDispatchStartegyKeyMap(dispatch_rule_id,item_code) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchStartegyBO,
						"getWoDispatchStartegyById" ,param)) ;
		
		return result ;
	}
	

	public List findWoDispatchStartegyByCond(String dispatch_rule_id,String item_code) throws Exception {
		Map param = getWoDispatchStartegyKeyMap(dispatch_rule_id,item_code) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchStartegyBO,
						"findWoDispatchStartegyByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteWoDispatchStartegyById(String dispatch_rule_id,String item_code) throws Exception {
		Map param = getWoDispatchStartegyKeyMap(dispatch_rule_id,item_code) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchStartegyBO,
						"deleteWoDispatchStartegyById" ,param)) ;
		
		return result ;
	}
	
	private Map getWoDispatchStartegyKeyMap(String dispatch_rule_id,String item_code){
		Map param = new HashMap() ;
				
		param.put("dispatch_rule_id", dispatch_rule_id) ;
				
		param.put("item_code", item_code) ;
				
		return param ;
	}
}
