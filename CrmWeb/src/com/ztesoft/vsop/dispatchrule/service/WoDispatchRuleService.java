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


public class WoDispatchRuleService  {
	public boolean insertWoDispatchRule(HashMap WoDispatchRule ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoDispatchRule", WoDispatchRule) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchRuleBO,
						"insertWoDispatchRule" ,param)) ;
		return result ;
	}

	
	public boolean updateWoDispatchRule(HashMap WoDispatchRule ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoDispatchRule", WoDispatchRule) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchRuleBO,
						"updateWoDispatchRule" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchWoDispatchRuleData(String name,String code,String state,String dispatch_rule,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("name",name );
		param.put("code", code);
		param.put("state", state);
		param.put("dispatch_rule",dispatch_rule );
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchRuleBO,
						"searchWoDispatchRuleData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoDispatchRuleById(String dispatch_rule_id) throws Exception {
		Map param = getWoDispatchRuleKeyMap(dispatch_rule_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchRuleBO,
						"getWoDispatchRuleById" ,param)) ;
		
		return result ;
	}
	

	public List findWoDispatchRuleByCond(String dispatch_rule_id) throws Exception {
		Map param = getWoDispatchRuleKeyMap(dispatch_rule_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchRuleBO,
						"findWoDispatchRuleByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteWoDispatchRuleById(String dispatch_rule_id) throws Exception {
		Map param = getWoDispatchRuleKeyMap(dispatch_rule_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchRuleBO,
						"deleteWoDispatchRuleById" ,param)) ;
		
		return result ;
	}
	
	private Map getWoDispatchRuleKeyMap(String dispatch_rule_id){
		Map param = new HashMap() ;
				
		param.put("dispatch_rule_id", dispatch_rule_id) ;
				
		return param ;
	}
	public boolean startWoDispatchRule(String dispatch_rule_id) throws Exception {
		Map param = getWoDispatchRuleKeyMap(dispatch_rule_id) ;
		param.put("state", "10A");
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchRuleBO,
						"changeStateWoDispatchRuleById" ,param)) ;
		
		return result ;
	}
	public boolean stopWoDispatchRule(String dispatch_rule_id) throws Exception {
		Map param = getWoDispatchRuleKeyMap(dispatch_rule_id) ;
		param.put("state", "10X");
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchRuleBO,
						"changeStateWoDispatchRuleById" ,param)) ;
		return result ;
	}
	public boolean isRelate(String dispatch_rule_id) throws Exception {
		Map param = getWoDispatchRuleKeyMap(dispatch_rule_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchRuleBO,
						"isRelate" ,param)) ;
		return result ;
	}
	public boolean validateCode(String code,String dispatch_rule_id) throws Exception {
		Map param = new HashMap();
		param.put("code", code);
		param.put("dispatch_rule_id", dispatch_rule_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoDispatchRuleBO,
						"validateCode" ,param)) ;
		return result ;
	}
}
