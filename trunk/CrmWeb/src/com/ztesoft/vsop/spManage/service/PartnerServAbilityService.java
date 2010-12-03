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


public class PartnerServAbilityService  {
	/**
	     需要替换位置 说明 ：
	  1. ServiceList.PartnerServAbilityBO  替换为ServiceList注册的服务 
	  2. searchPartnerServAbilityData 改方法的参数名称
	  3. findPartnerServAbilityByCond(String partner_service_ability_id) 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertPartnerServAbility(HashMap PartnerServAbility ) throws Exception {
		Map param = new HashMap() ;
		param.put("PartnerServAbility", PartnerServAbility) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PartnerServAbilityBO,
						"insertPartnerServAbility" ,param)) ;
		return result ;
	}

	
	public boolean updatePartnerServAbility(HashMap PartnerServAbility ) throws Exception {
		Map param = new HashMap() ;
		param.put("PartnerServAbility", PartnerServAbility) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PartnerServAbilityBO,
						"updatePartnerServAbility" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchPartnerServAbilityData(String iParam1 ,  
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("iParam1", iParam1) ;
//		param.put("iParam2", iParam2) ;
//		param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.PartnerServAbilityBO,
						"searchPartnerServAbilityData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getPartnerServAbilityById(String partner_service_ability_id) throws Exception {
		Map param = getPartnerServAbilityKeyMap(partner_service_ability_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.PartnerServAbilityBO,
						"getPartnerServAbilityById" ,param)) ;
		
		return result ;
	}
	

	public List findPartnerServAbilityByCond(String partner_service_ability_id) throws Exception {
		Map param = getPartnerServAbilityKeyMap(partner_service_ability_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.PartnerServAbilityBO,
						"findPartnerServAbilityByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deletePartnerServAbilityById(String partner_service_ability_id) throws Exception {
		Map param = getPartnerServAbilityKeyMap(partner_service_ability_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PartnerServAbilityBO,
						"deletePartnerServAbilityById" ,param)) ;
		
		return result ;
	}
	
	private Map getPartnerServAbilityKeyMap(String partner_service_ability_id){
		Map param = new HashMap() ;
				
		param.put("partner_service_ability_id", partner_service_ability_id) ;
				
		return param ;
	}
	
	public boolean insertPartnerServAbilitySop(HashMap PartnerServAbility ) throws Exception {
//		Map param = new HashMap() ;
//		param.put("PartnerServAbility", PartnerServAbility) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PartnerServAbilityBO,
						"insertPartnerServAbilitySop" ,PartnerServAbility)) ;
		return result ;
	}

	
	public boolean updatePartnerServAbilitySop(HashMap PartnerServAbility ) throws Exception {
//		Map param = new HashMap() ;
//		param.put("PartnerServAbility", PartnerServAbility) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PartnerServAbilityBO,
						"updatePartnerServAbilitySop" ,PartnerServAbility)) ;
		
		return result ;
	}
	public boolean deletePartnerServAbilityByIdSop(HashMap PartnerServAbility) throws Exception {
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PartnerServAbilityBO,
						"deletePartnerServAbilityByIdSop" ,PartnerServAbility)) ;
		
		return result ;
	}
}
