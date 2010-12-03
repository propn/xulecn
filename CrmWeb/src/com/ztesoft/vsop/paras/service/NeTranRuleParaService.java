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


public class NeTranRuleParaService  {
	public boolean insertNeTranRulePara(HashMap NeTranRulePara,HashMap paraMap) throws Exception {
		Map param = new HashMap() ;
		param.put("NeTranRulePara", NeTranRulePara) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleParaBO,
						"insertNeTranRulePara" ,param)) ;
		return result ;
	}

	
	public boolean updateNeTranRulePara(HashMap NeTranRulePara,HashMap paraMap) throws Exception {
		Map param = new HashMap() ;
		param.put("NeTranRulePara", NeTranRulePara) ;
		param.put("paraMap", paraMap) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleParaBO,
						"updateNeTranRulePara" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchNeTranRuleParaData(String tran_rule_id,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("tran_rule_id", tran_rule_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleParaBO,
						"searchNeTranRuleParaData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeTranRuleParaById(String tran_rule_id ,String para_id,String seq) throws Exception {
		Map param = new HashMap();
		param.put("tran_rule_id", tran_rule_id);
		param.put("para_id", para_id);
		param.put("seq",seq );
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleParaBO,
						"getNeTranRuleParaById" ,param)) ;
		
		return result ;
	}
	

	public List findNeTranRuleParaByCond() throws Exception {
		Map param = getNeTranRuleParaKeyMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleParaBO,
						"findNeTranRuleParaByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteNeTranRuleParaById(HashMap paraMap) throws Exception {
		Map param = new HashMap();
		param.put("paraMap", paraMap);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleParaBO,
						"deleteNeTranRuleParaById" ,param)) ;
		
		return result ;
	}
	public int validateRepartPara(HashMap paraMap) throws Exception {
		Map param = new HashMap();
		param.put("paraMap", paraMap);
		int result = DataTranslate._int(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleParaBO,
						"validateRepartPara" ,param)) ;
		
		return result ;
	}
	public int validateRepartSeq(HashMap paraMap) throws Exception {
		Map param = new HashMap();
		param.put("paraMap", paraMap);
		int result = DataTranslate._int(
				ServiceManager.callJavaBeanService(ServiceList.NeTranRuleParaBO,
						"validateRepartSeq" ,param)) ;
		
		return result ;
	}
	private Map getNeTranRuleParaKeyMap(){
		Map param = new HashMap() ;
				
		return param ;
	}
}
