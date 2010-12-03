package com.ztesoft.vsop.spManage.service;


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


public class PartnerSystemInfoService  {
	/**
	     需要替换位置 说明 ：
	  1. ServiceList.MyService  替换为ServiceList注册的服务 
	  2. searchPartnerSystemInfoData 改方法的参数名称
	  3. findPartnerSystemInfoByCond(String partner_system_info_id) 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertPartnerSystemInfo(HashMap PartnerSystemInfo ) throws Exception {
		Map param = new HashMap() ;
		param.put("PartnerSystemInfo", PartnerSystemInfo) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("PartnerSystemInfoBO",
						"insertPartnerSystemInfo" ,param)) ;
		return result ;
	}

	
	public boolean updatePartnerSystemInfo(HashMap PartnerSystemInfo ) throws Exception {
		Map param = new HashMap() ;
		param.put("PartnerSystemInfo", PartnerSystemInfo) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("PartnerSystemInfoBO",
						"updatePartnerSystemInfo" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchPartnerSystemInfoData(String product_id , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("partner_id", product_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("PartnerSystemInfoBO",
						"searchPartnerSystemInfoData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getPartnerSystemInfoById(String partner_system_info_id) throws Exception {
		Map param = getPartnerSystemInfoKeyMap(partner_system_info_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("PartnerSystemInfoBO",
						"getPartnerSystemInfoById" ,param)) ;
		
		return result ;
	}
	

	public List findPartnerSystemInfoByCond(String partner_system_info_id) throws Exception {
		Map param = getPartnerSystemInfoKeyMap(partner_system_info_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("PartnerSystemInfoBO",
						"findPartnerSystemInfoByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deletePartnerSystemInfoById(String partner_system_info_id) throws Exception {
		Map param = getPartnerSystemInfoKeyMap(partner_system_info_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("PartnerSystemInfoBO",
						"deletePartnerSystemInfoById" ,param)) ;
		
		return result ;
	}
	
	private Map getPartnerSystemInfoKeyMap(String partner_system_info_id){
		Map param = new HashMap() ;
				
		param.put("partner_system_info_id", partner_system_info_id) ;
				
		return param ;
	}
	
}
