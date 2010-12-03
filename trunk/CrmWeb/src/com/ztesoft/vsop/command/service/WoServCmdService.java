package com.ztesoft.vsop.command.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class WoServCmdService  {
	public boolean insertWoServCmd(HashMap WoServCmd,HashMap paraMap ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoServCmd", WoServCmd) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoServCmdBO,
						"insertWoServCmd" ,param)) ;
		return result ;
	}

	
	public boolean updateWoServCmd(HashMap WoServCmd ,HashMap paraMap) throws Exception {
		Map param = new HashMap() ;
		param.put("WoServCmd", WoServCmd) ;
		param.put("paraMap", paraMap) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoServCmdBO,
						"updateWoServCmd" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchWoServCmdData(String command_collect_id ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("command_collect_id", command_collect_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoServCmdBO,
						"searchWoServCmdData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoServCmdById(String command_collect_id ,String template_id,String seq) throws Exception {
		Map param = new HashMap();
		param.put("command_collect_id", command_collect_id);
		param.put("template_id", template_id);
		param.put("seq",seq );
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoServCmdBO,
						"getWoServCmdById" ,param)) ;
		
		return result ;
	}
	

	public List findWoServCmdByCond() throws Exception {
		Map param = getWoServCmdKeyMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoServCmdBO,
						"findWoServCmdByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteWoServCmdById(HashMap paraMap) throws Exception {
		Map param = getWoServCmdKeyMap() ;
		param.put("paraMap", paraMap);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoServCmdBO,
						"deleteWoServCmdById" ,param)) ;
		
		return result ;
	}
	
	private Map getWoServCmdKeyMap(){
		Map param = new HashMap() ;
				
		return param ;
	}
	public boolean isRelate(String command_collect_id) throws Exception {
		Map param = new HashMap() ;
		param.put("command_collect_id", command_collect_id);

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoServCmdBO,
						"isRelate" ,param)) ;
		
		return result ;
	}
	
	public boolean validTemplateId(String command_collect_id, String template_id) throws Exception {
		
		Map param = new HashMap();
	    param.put("command_collect_id", command_collect_id);
	    param.put("template_id", template_id);
	    
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoServCmdBO,
						"validTemplateId" ,param)) ;
		
		return result;
		
	}
	
	public boolean validSeq(String command_collect_id, Integer seq) throws Exception {
		
		Map param = new HashMap();
	    param.put("command_collect_id", command_collect_id);
	    param.put("seq", seq);
	    
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoServCmdBO,
						"validSeq" ,param)) ;
		
		return result;
		
	}
}
