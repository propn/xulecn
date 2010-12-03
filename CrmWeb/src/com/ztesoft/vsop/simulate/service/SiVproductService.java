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


public class SiVproductService  {
	/**
	     ��Ҫ�滻λ�� ˵�� ��
	  1. ServiceList.MyService  �滻ΪServiceListע��ķ��� 
	  2. searchSiVproductData �ķ����Ĳ�������
	  3. findSiVproductByCond(String id) ������Ҫ����ʵ������޸�
	  4. ����Ҫ�ķ��������Ը���ʵ��������вü�
	  5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertSiVproduct(HashMap SiVproduct ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiVproduct", SiVproduct) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiVproductBO,
						"insertSiVproduct" ,param)) ;
		return result ;
	}

	
	public boolean updateSiVproduct(HashMap SiVproduct ) throws Exception {
		Map param = new HashMap() ;
		param.put("SiVproduct", SiVproduct) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiVproductBO,
						"updateSiVproduct" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSiVproductData(String iParam1 , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("app_id", iParam1) ;
		/*param.put("iParam2", iParam2) ;
		param.put("iParam3", iParam3) ;*/
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiVproductBO,
						"searchSiVproductData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSiVproductById(String id) throws Exception {
		Map param = getSiVproductKeyMap(id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiVproductBO,
						"getSiVproductById" ,param)) ;
		
		return result ;
	}
	

	public List findSiVproductByCond(String id) throws Exception {
		Map param = getSiVproductKeyMap(id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiVproductBO,
						"findSiVproductByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSiVproductById(String id) throws Exception {
		Map param = getSiVproductKeyMap(id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiVproductBO,
						"deleteSiVproductById" ,param)) ;
		
		return result ;
	}
	
	private Map getSiVproductKeyMap(String id){
		Map param = new HashMap() ;
				
		param.put("id", id) ;
				
		return param ;
	}
}
