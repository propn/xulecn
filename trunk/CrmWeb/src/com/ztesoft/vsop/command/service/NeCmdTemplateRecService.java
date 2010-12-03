package com.ztesoft.vsop.command.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class NeCmdTemplateRecService  {
	public boolean insertNeCmdTemplateRec(HashMap NeCmdTemplateRec ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeCmdTemplateRec", NeCmdTemplateRec) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateRecBO,
						"insertNeCmdTemplateRec" ,param)) ;
		return result ;
	}

	
	public boolean updateNeCmdTemplateRec(HashMap NeCmdTemplateRec ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeCmdTemplateRec", NeCmdTemplateRec) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateRecBO,
						"updateNeCmdTemplateRec" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchNeCmdTemplateRecData(String template_id , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("template_id", template_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateRecBO,
						"searchNeCmdTemplateRecData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeCmdTemplateRecById() throws Exception {
		Map param = getNeCmdTemplateRecKeyMap() ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateRecBO,
						"getNeCmdTemplateRecById" ,param)) ;
		
		return result ;
	}
	

	public List findNeCmdTemplateRecByCond() throws Exception {
		Map param = getNeCmdTemplateRecKeyMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateRecBO,
						"findNeCmdTemplateRecByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCmdTemplateRecById(String template_id,String command_id ) throws Exception {
		Map param = new HashMap();
		param.put("template_id",template_id );
		param.put("command_id", command_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateRecBO,
						"deleteNeCmdTemplateRecById" ,param)) ;
		
		return result ;
	}
	
	private Map getNeCmdTemplateRecKeyMap(){
		Map param = new HashMap() ;
				
		return param ;
	}
	public boolean isRelate(String template_id) throws Exception {
		Map param = new HashMap() ;
		param.put("template_id", template_id);

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateRecBO,
						"isRelate" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 根据模板标识、数据项记录标识在 ne_cmd_template_rec中检查模板输入数据项记录唯一性
	 * @param template_id
	 * @param record_id
	 * @return
	 * @throws Exception
	 */
	public boolean validateTemplateRecord(String template_id, String record_id) throws Exception {
		Map map = new HashMap();
		map.put("template_id", template_id);
		map.put("record_id", record_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCmdTemplateRecBO, 
						"validateTemplateRecord", map));
		return result;
	}
}
