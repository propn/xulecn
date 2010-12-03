package com.ztesoft.vsop.spManage.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;

public class PartnerService  {
	/**
	     ��Ҫ�滻λ�� ˵�� ��
	  1. ServiceList.MyService  �滻ΪServiceListע��ķ��� 
	  2. searchPartnerData �ķ����Ĳ�������
	  3. findPartnerByCond(String partner_id) ������Ҫ����ʵ������޸�
	  4. ����Ҫ�ķ��������Ը���ʵ��������вü�
	  5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertPartner(HashMap Partner ) throws Exception {
		Map param = new HashMap() ;
		param.put("Partner", Partner) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PartnerBO,
						"insertPartner" ,param)) ;
		return result ;
	}

	
	public boolean updatePartner(HashMap Partner ) throws Exception {
		Map param = new HashMap() ;
		param.put("Partner", Partner) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PartnerBO,
						"updatePartner" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchPartnerData(String iParam1 , String iParam2 ,String iParam3,String iParam4,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("iParam1", iParam1) ;
		param.put("iParam2", iParam2) ;
		param.put("iParam3", iParam3) ;
		param.put("iParam4", iParam4) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.PartnerBO,
						"searchPartnerData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getPartnerById(String partner_id) throws Exception {
		Map param = getPartnerKeyMap(partner_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.PartnerBO,
						"getPartnerById" ,param)) ;
		
		return result ;
	}
	

	public List findPartnerByCond(String partner_id) throws Exception {
		Map param = getPartnerKeyMap(partner_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.PartnerBO,
						"findPartnerByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deletePartnerById(String partner_id) throws Exception {
		Map param = getPartnerKeyMap(partner_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PartnerBO,
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
				ServiceManager.callJavaBeanService(ServiceList.PartnerBO,
						"insertPartnerSop" ,param)) ;
		return result ;
	}

	
	public boolean updatePartnerSop(HashMap Partner ) throws Exception {
		Map param = new HashMap() ;
		param.put("Partner", Partner) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PartnerBO,
						"updatePartnerSop" ,param)) ;
		
		return result ;
	}
	
	public boolean deletePartnerByIdSop(String partner_id) throws Exception {
//		Map param = getPartnerKeyMap(partner_id) ;
		Map param = new HashMap() ;
		
		param.put("partner_code", partner_id) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.PartnerBO,
						"deletePartnerByIdSop" ,param)) ;
		
		return result ;
	}
}
