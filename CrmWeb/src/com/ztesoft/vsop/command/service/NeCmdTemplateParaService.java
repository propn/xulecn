package com.ztesoft.vsop.command.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class NeCmdTemplateParaService  {
	public boolean insertNeCmdTemplatePara(HashMap NeCmdTemplatePara ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeCmdTemplatePara", NeCmdTemplatePara) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateParaBO,
						"insertNeCmdTemplatePara" ,param)) ;
		return result ;
	}

	
	public boolean updateNeCmdTemplatePara(HashMap NeCmdTemplatePara ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeCmdTemplatePara", NeCmdTemplatePara) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateParaBO,
						"updateNeCmdTemplatePara" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchNeCmdTemplateParaData(String template_id ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("template_id", template_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateParaBO,
						"searchNeCmdTemplateParaData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeCmdTemplateParaById() throws Exception {
		Map param = getNeCmdTemplateParaKeyMap() ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateParaBO,
						"getNeCmdTemplateParaById" ,param)) ;
		
		return result ;
	}
	

	public List findNeCmdTemplateParaByCond() throws Exception {
		Map param = getNeCmdTemplateParaKeyMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateParaBO,
						"findNeCmdTemplateParaByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCmdTemplateParaById(String template_id,String command_id) throws Exception {
		Map param = new HashMap() ;
		param.put("template_id", template_id);	
		param.put("command_id", command_id);	

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateParaBO,
						"deleteNeCmdTemplateParaById" ,param)) ;
		
		return result ;
	}
	
	private Map getNeCmdTemplateParaKeyMap(){
		Map param = new HashMap() ;
				
		return param ;
	}
	public boolean isRelate(String template_id) throws Exception {
		Map param = new HashMap() ;
		param.put("template_id", template_id);

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateParaBO,
						"isRelate" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 根据模板标识、数据项标识在ne_cmd_template_para中检查模板输入数据项唯一性
	 * @param template_id
	 * @param command_id
	 * @return
	 * @throws Exception
	 */
	public boolean validateTemplateCommond(String template_id, String command_id) throws Exception {
		
		Map map = new HashMap();
		map.put("template_id", template_id);
		map.put("command_id", command_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateParaBO, 
						"validateTemplateCommond", map));
		
		return result;
	}
}
