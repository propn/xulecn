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


public class WoStartegyItemService  {
	public boolean insertWoStartegyItem(HashMap WoStartegyItem ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoStartegyItem", WoStartegyItem) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoStartegyItemBO,
						"insertWoStartegyItem" ,param)) ;
		return result ;
	}

	
	public boolean updateWoStartegyItem(HashMap WoStartegyItem ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoStartegyItem", WoStartegyItem) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoStartegyItemBO,
						"updateWoStartegyItem" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchWoStartegyItemData(String iParam1 , String iParam2 ,String iParam3 , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("iParam1", iParam1) ;
		param.put("iParam2", iParam2) ;
		param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoStartegyItemBO,
						"searchWoStartegyItemData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getWoStartegyItemById(String item_code) throws Exception {
		Map param = getWoStartegyItemKeyMap(item_code) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoStartegyItemBO,
						"getWoStartegyItemById" ,param)) ;
		
		return result ;
	}
	

	public List findWoStartegyItemByCond(String item_code) throws Exception {
		Map param = getWoStartegyItemKeyMap(item_code) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.WoStartegyItemBO,
						"findWoStartegyItemByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteWoStartegyItemById(String item_code) throws Exception {
		Map param = getWoStartegyItemKeyMap(item_code) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoStartegyItemBO,
						"deleteWoStartegyItemById" ,param)) ;
		
		return result ;
	}
	
	private Map getWoStartegyItemKeyMap(String item_code){
		Map param = new HashMap() ;
				
		param.put("item_code", item_code) ;
				
		return param ;
	}
}
