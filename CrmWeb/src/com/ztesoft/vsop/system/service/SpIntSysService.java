package com.ztesoft.vsop.system.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class SpIntSysService  {
	/**
	     ��Ҫ�滻λ�� ˵�� ��
	  1. ServiceList.SpIntSysBO  �滻ΪServiceListע��ķ��� 
	  2. searchSpIntSysData �ķ����Ĳ�������
	  3. findSpIntSysByCond(String int_sys_id) ������Ҫ����ʵ������޸�
	  4. ����Ҫ�ķ��������Ը���ʵ��������вü�
	  5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertSpIntSys(HashMap SpIntSys ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpIntSys", SpIntSys) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpIntSysBO,
						"insertSpIntSys" ,param)) ;
		return result ;
	}

	
	public boolean updateSpIntSys(HashMap SpIntSys ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpIntSys", SpIntSys) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpIntSysBO,
						"updateSpIntSys" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSpIntSysData(String sysCode,String name , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("sys_code", sysCode);
	    param.put("name", name);
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SpIntSysBO,
						"searchSpIntSysData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSpIntSysById(String int_sys_id) throws Exception {
		Map param = getSpIntSysKeyMap(int_sys_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SpIntSysBO,
						"getSpIntSysById" ,param)) ;
		
		return result ;
	}
	

	public List findSpIntSysByCond(String int_sys_id) throws Exception {
		Map param = getSpIntSysKeyMap(int_sys_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SpIntSysBO,
						"findSpIntSysByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSpIntSysById(String int_sys_id) throws Exception {
		Map param = getSpIntSysKeyMap(int_sys_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpIntSysBO,
						"deleteSpIntSysById" ,param)) ;
		
		return result ;
	}
	
	private Map getSpIntSysKeyMap(String int_sys_id){
		Map param = new HashMap() ;
				
		param.put("int_sys_id", int_sys_id) ;
				
		return param ;
	}
	
	/**
	 * �������޸�ʱУ��ϵͳ�����Ƿ�Ψһ
	 * @param sys_code
	 * @return
	 * @throws Exception
	 */
	public boolean validateUniqueForSysCode(String sys_code) throws Exception {
		Map map = new HashMap();
		map.put("sys_code", sys_code);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(
						ServiceList.SpIntSysBO, "validateUniqueForSysCode", map));
		return result;
	}
}
