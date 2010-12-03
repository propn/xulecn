package com.ztesoft.vsop.paras.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class NeCommandParaService  {
	
	public boolean insertNeCommandPara(HashMap NeCommandPara ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeCommandPara", NeCommandPara) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandParaBO,
						"insertNeCommandPara" ,param)) ;
		return result ;
	}

	
	public boolean updateNeCommandPara(HashMap NeCommandPara ) throws Exception {
		Map param = new HashMap() ;
		param.put("NeCommandPara", NeCommandPara) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandParaBO,
						"updateNeCommandPara" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchNeCommandParaData(String name , String para_code ,String command_catalog_id , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("name", name) ;
		param.put("para_code", para_code) ;
		param.put("command_catalog_id", command_catalog_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandParaBO,
						"searchNeCommandParaData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getNeCommandParaById(String command_id) throws Exception {
		Map param = getNeCommandParaKeyMap(command_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandParaBO,
						"getNeCommandParaById" ,param)) ;
		
		return result ;
	}
	

	public List findNeCommandParaByCond(String command_id) throws Exception {
		Map param = getNeCommandParaKeyMap(command_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandParaBO,
						"findNeCommandParaByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCommandParaById(String command_id) throws Exception {
		Map param = getNeCommandParaKeyMap(command_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.NeCommandParaBO,
						"deleteNeCommandParaById" ,param)) ;
		
		return result ;
	}
	
	private Map getNeCommandParaKeyMap(String command_id){
		Map param = new HashMap() ;
				
		param.put("command_id", command_id) ;
				
		return param ;
	}
	
	/**
	 * 删除目标数据项时检查是否被数据项记录引用
	 * @param command_id
	 * @return
	 * @throws Exception
	 */
	public String validateForParaRecord(String command_id) throws Exception {
		Map map = new HashMap();
		map.put("command_id", command_id);
		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService(
						ServiceList.NeCommandParaBO, "validateForParaRecord", map));
		return result;
	}
	
	/**
	 * 删除目标数据项时检查是否被工单类型引用
	 * @param command_id
	 * @return
	 * @throws Exception
	 */
	public String validateForOrderType(String command_id) throws Exception {
		Map map = new HashMap();
		map.put("command_id", command_id);
		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService(
						ServiceList.NeCommandParaBO, "validateForOrderType", map));
		return result;
	}
	
	/**
	 * 删除目标数据项时检查是否被工单类型引用
	 * @param command_id
	 * @return
	 * @throws Exception
	 */
	public String validateForTemplatePara(String command_id) throws Exception {
		Map map = new HashMap();
		map.put("command_id", command_id);
		String result = DataTranslate._String(
				ServiceManager.callJavaBeanService(
						ServiceList.NeCommandParaBO, "validateForTemplatePara", map));
		return result;
	}
	
	/**
	 * 验证编码的唯一性
	 * @param command_catalog_id
	 * @param para_code
	 * @return
	 * @throws Exception
	 */
	public boolean validateParaCode(String command_catalog_id, String para_code,String command_id) throws Exception {
		Map m = new HashMap();
		m.put("command_catalog_id", command_catalog_id);
		m.put("para_code", para_code);
		m.put("command_id", command_id);
		boolean r = DataTranslate._boolean(ServiceManager.callJavaBeanService(
				ServiceList.NeCommandParaBO, "validateParaCode", m));
		return r;
	}
	
}
