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


public class NeTranRuleService  {
	
	public boolean insertNeTranRule(HashMap NeTranRule ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeTranRule", NeTranRule) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleBO,
						"insertNeTranRule" ,param)) ;
		return result ;
	}

	
	public boolean updateNeTranRule(HashMap NeTranRule ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeTranRule", NeTranRule) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleBO,
						"updateNeTranRule" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchNeTranRuleData(String name,String create_date,String end_date, int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("name", name);
		param.put("create_date",create_date);
		param.put("end_date",end_date);
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleBO,
						"searchNeTranRuleData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeTranRuleById(String tran_rule_id) throws Exception {
		Map param = getNeTranRuleKeyMap(tran_rule_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleBO,
						"getNeTranRuleById" ,param)) ;
		
		return result ;
	}
	

	public List findNeTranRuleByCond(String tran_rule_id) throws Exception {
		Map param = getNeTranRuleKeyMap(tran_rule_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleBO,
						"findNeTranRuleByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteNeTranRuleById(String tran_rule_id) throws Exception {
		Map param = getNeTranRuleKeyMap(tran_rule_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleBO,
						"deleteNeTranRuleById" ,param)) ;
		
		return result ;
	}
	
	private Map getNeTranRuleKeyMap(String tran_rule_id){
		Map param = new HashMap() ;
				
		param.put("tran_rule_id", tran_rule_id) ;
				
		return param ;
	}
	public String isRelateByCommPara(String tran_rule_id) throws Exception {
		Map param = getNeTranRuleKeyMap(tran_rule_id) ;

		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleBO,
						"isRelateByCommPara" ,param)) ;
		
		return result ;
	}


	public boolean isRelateByRulePara(String tran_rule_id) throws Exception {
		Map param = getNeTranRuleKeyMap(tran_rule_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleBO,
						"isRelateByRulePara" ,param)) ;
		
		return result ;
	}
}
