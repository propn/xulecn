package com.ztesoft.vsop.command.service;


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


public class NeCommandTemplateService  {
	public boolean insertNeCommandTemplate(HashMap NeCommandTemplate ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeCommandTemplate", NeCommandTemplate) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandTemplateBO,
						"insertNeCommandTemplate" ,param)) ;
		return result ;
	}

	
	public boolean updateNeCommandTemplate(HashMap NeCommandTemplate ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeCommandTemplate", NeCommandTemplate) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandTemplateBO,
						"updateNeCommandTemplate" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchNeCommandTemplateData(String name ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("name", name) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandTemplateBO,
						"searchNeCommandTemplateData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeCommandTemplateById(String template_id) throws Exception {
		Map param = getNeCommandTemplateKeyMap(template_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandTemplateBO,
						"getNeCommandTemplateById" ,param)) ;
		
		return result ;
	}
	

	public List findNeCommandTemplateByCond(String template_id) throws Exception {
		Map param = getNeCommandTemplateKeyMap(template_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandTemplateBO,
						"findNeCommandTemplateByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCommandTemplateById(String template_id) throws Exception {
		Map param = getNeCommandTemplateKeyMap(template_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandTemplateBO,
						"deleteNeCommandTemplateById" ,param)) ;
		
		return result ;
	}
	
	private Map getNeCommandTemplateKeyMap(String template_id){
		Map param = new HashMap() ;
				
		param.put("template_id", template_id) ;
				
		return param ;
	}
	public String isRelate(String template_id) throws Exception {
		Map param = new HashMap() ;
		param.put("template_id", template_id);

		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandTemplateBO,
						"isRelate" ,param)) ;
		
		return result ;
	}
}
