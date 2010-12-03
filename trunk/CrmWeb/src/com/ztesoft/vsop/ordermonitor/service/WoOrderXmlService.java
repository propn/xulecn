package com.ztesoft.vsop.ordermonitor.service;


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


public class WoOrderXmlService  {
	public boolean insertWoOrderXml(HashMap WoOrderXml ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoOrderXml", WoOrderXml) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderXmlBO,
						"insertWoOrderXml" ,param)) ;
		return result ;
	}

	
	public boolean updateWoOrderXml(HashMap WoOrderXml ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoOrderXml", WoOrderXml) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderXmlBO,
						"updateWoOrderXml" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchWoOrderXmlData(String order_id ,String query_type,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("order_id", order_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		if("1".equals(query_type)){
			PageModel hisresult = DataTranslate._PageModel(
					ServiceManager.callJavaBeanService(ServiceList.WoOrderXmlHisBO,
							"searchWoOrderXmlHisData" ,param)) ;
			return hisresult;
		}
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderXmlBO,
						"searchWoOrderXmlData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoOrderXmlById() throws Exception {
		Map param = getWoOrderXmlKeyMap() ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderXmlBO,
						"getWoOrderXmlById" ,param)) ;
		
		return result ;
	}
	

	public List findWoOrderXmlByCond() throws Exception {
		Map param = getWoOrderXmlKeyMap() ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderXmlBO,
						"findWoOrderXmlByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteWoOrderXmlById() throws Exception {
		Map param = getWoOrderXmlKeyMap() ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderXmlBO,
						"deleteWoOrderXmlById" ,param)) ;
		
		return result ;
	}
	
	private Map getWoOrderXmlKeyMap(){
		Map param = new HashMap() ;
				
		return param ;
	}
}
