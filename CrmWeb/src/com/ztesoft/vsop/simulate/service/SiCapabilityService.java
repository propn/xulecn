package com.ztesoft.vsop.simulate.service;


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


public class SiCapabilityService  {
	/**
	     ��Ҫ�滻λ�� ˵�� ��
	  1. ServiceList.MyService  �滻ΪServiceListע��ķ��� 
	  2. searchSiCapabilityData �ķ����Ĳ�������
	  3. findSiCapabilityByCond(String id) ������Ҫ����ʵ������޸�
	  4. ����Ҫ�ķ��������Ը���ʵ��������вü�
	  5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertSiCapability(HashMap SiCapability ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiCapability", SiCapability) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiCapabilityBO,
						"insertSiCapability" ,param)) ;
		return result ;
	}

	
	public boolean updateSiCapability(HashMap SiCapability ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiCapability", SiCapability) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiCapabilityBO,
						"updateSiCapability" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSiCapabilityData(String iParam1 ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("app_id", iParam1) ;
		//param.put("iParam2", iParam2) ;
		//param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiCapabilityBO,
						"searchSiCapabilityData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSiCapabilityById(String id) throws Exception {
		Map param = getSiCapabilityKeyMap(id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiCapabilityBO,
						"getSiCapabilityById" ,param)) ;
		
		return result ;
	}
	

	public List findSiCapabilityByCond(String id) throws Exception {
		Map param = getSiCapabilityKeyMap(id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiCapabilityBO,
						"findSiCapabilityByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSiCapabilityById(String id) throws Exception {
		Map param = getSiCapabilityKeyMap(id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiCapabilityBO,
						"deleteSiCapabilityById" ,param)) ;
		
		return result ;
	}
	
	private Map getSiCapabilityKeyMap(String id){
		Map param = new HashMap() ;
				
		param.put("id", id) ;
				
		return param ;
	}
}
