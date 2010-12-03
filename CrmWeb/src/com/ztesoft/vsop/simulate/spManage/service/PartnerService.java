package com.ztesoft.vsop.simulate.spManage.service;


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


public class PartnerService  {
	public boolean insertPartner(HashMap Partner ) throws Exception {
		Map param = new HashMap() ;
		param.put("Partner", Partner) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiPartnerBO,
						"insertPartner" ,param)) ;
		return result ;
	}

	
	public boolean updatePartner(HashMap Partner ) throws Exception {
		Map param = new HashMap() ;
		param.put("Partner", Partner) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiPartnerBO,
						"updatePartner" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchPartnerData(String iParam1 , String iParam2 ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("iParam1", iParam1) ;
		param.put("iParam2", iParam2) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiPartnerBO,
						"searchPartnerData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getPartnerById(String partner_id) throws Exception {
		Map param = getPartnerKeyMap(partner_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiPartnerBO,
						"getPartnerById" ,param)) ;
		
		return result ;
	}
	

	public List findPartnerByCond(String partner_id) throws Exception {
		Map param = getPartnerKeyMap(partner_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiPartnerBO,
						"findPartnerByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deletePartnerById(String partner_id) throws Exception {
		Map param = getPartnerKeyMap(partner_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiPartnerBO,
						"deletePartnerById" ,param)) ;
		
		return result ;
	}
	
	private Map getPartnerKeyMap(String partner_id){
		Map param = new HashMap() ;
				
		param.put("partner_id", partner_id) ;
				
		return param ;
	}
	
	public boolean insertPartnerSop(HashMap Partner ) throws Exception {
		Map param = new HashMap() ;
		param.put("Partner", Partner) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiPartnerBO,
						"insertPartnerSop" ,param)) ;
		return result ;
	}

	
	public boolean updatePartnerSop(HashMap Partner ) throws Exception {
		Map param = new HashMap() ;
		param.put("Partner", Partner) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiPartnerBO,
						"updatePartnerSop" ,param)) ;
		
		return result ;
	}
	
	public boolean deletePartnerByIdSop(String partner_id) throws Exception {
//		Map param = getPartnerKeyMap(partner_id) ;
		Map param = new HashMap() ;
		
		param.put("partner_code", partner_id) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiPartnerBO,
						"deletePartnerByIdSop" ,param)) ;
		
		return result ;
	}

	public boolean insertPartnerServAbility(HashMap PartnerServAbility ) throws Exception {
		Map param = new HashMap() ;
		param.put("PartnerServAbility", PartnerServAbility) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiPartnerBO",
						"insertPartnerServAbility" ,param)) ;
		return result ;
	}

	
	public boolean updatePartnerServAbility(HashMap PartnerServAbility ) throws Exception {
		Map param = new HashMap() ;
		param.put("PartnerServAbility", PartnerServAbility) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiPartnerBO",
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
				ServiceManager.callJavaBeanService("SiPartnerBO",
						"searchPartnerServAbilityData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getPartnerServAbilityById(String partner_service_ability_id) throws Exception {
		Map param = getPartnerServAbilityKeyMap(partner_service_ability_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("SiPartnerBO",
						"getPartnerServAbilityById" ,param)) ;
		
		return result ;
	}
	

	public List findPartnerServAbilityByCond(String partner_service_ability_id) throws Exception {
		Map param = getPartnerServAbilityKeyMap(partner_service_ability_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("SiPartnerBO",
						"findPartnerServAbilityByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deletePartnerServAbilityById(String partner_service_ability_id) throws Exception {
		Map param = getPartnerServAbilityKeyMap(partner_service_ability_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiPartnerBO",
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
				ServiceManager.callJavaBeanService("SiPartnerBO",
						"insertPartnerServAbilitySop" ,PartnerServAbility)) ;
		return result ;
	}

	
	public boolean updatePartnerServAbilitySop(HashMap PartnerServAbility ) throws Exception {
//		Map param = new HashMap() ;
//		param.put("PartnerServAbility", PartnerServAbility) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiPartnerBO",
						"updatePartnerServAbilitySop" ,PartnerServAbility)) ;
		
		return result ;
	}
	public boolean deletePartnerServAbilityByIdSop(HashMap PartnerServAbility) throws Exception {
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiPartnerBO",
						"deletePartnerServAbilityByIdSop" ,PartnerServAbility)) ;
		
		return result ;
	}
}
