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
	     ��Ҫ�滻λ�� ˵�� ��
	  1. ServiceList.MyService  �滻ΪServiceListע��ķ��� 
	  2. searchPartnerSystemInfoData �ķ����Ĳ�������
	  3. findPartnerSystemInfoByCond(String partner_system_info_id) ������Ҫ����ʵ������޸�
	  4. ����Ҫ�ķ��������Ը���ʵ��������вü�
	  5. �˶Ά��»�����ɺ��滻��������ɾ����
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
