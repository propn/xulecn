package com.ztesoft.vsop.simulate.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class SiProdOffDetaRoleService  {
	
	public boolean insertProdOffDetaRole(HashMap ProdOffDetaRole ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdOffDetaRole", ProdOffDetaRole) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffDetaRoleBO,
						"insertProdOffDetaRole" ,param)) ;
		return result ;
	}

	
	public boolean updateProdOffDetaRole(HashMap ProdOffDetaRole ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdOffDetaRole", ProdOffDetaRole) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffDetaRoleBO,
						"updateProdOffDetaRole" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProdOffDetaRoleData(String prod_offer_id, int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap();
		param.put("prod_offer_id", prod_offer_id);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffDetaRoleBO,
						"searchProdOffDetaRoleData" ,param));
		
		return result ;
	}
	
	
	public Map getProdOffDetaRoleById(String prod_offer_role_cd) throws Exception {
		Map param = getProdOffDetaRoleKeyMap(prod_offer_role_cd) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffDetaRoleBO,
						"getProdOffDetaRoleById" ,param)) ;
		
		return result ;
	}
	

	public List findProdOffDetaRoleByCond(String prod_offer_role_cd) throws Exception {
		Map param = getProdOffDetaRoleKeyMap(prod_offer_role_cd) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffDetaRoleBO,
						"findProdOffDetaRoleByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffDetaRoleById(String prod_offer_role_cd) throws Exception {
		Map param = getProdOffDetaRoleKeyMap(prod_offer_role_cd) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffDetaRoleBO,
						"deleteProdOffDetaRoleById" ,param)) ;
		
		return result ;
	}
	
	private Map getProdOffDetaRoleKeyMap(String prod_offer_role_cd){
		Map param = new HashMap() ;
				
		param.put("prod_offer_role_cd", prod_offer_role_cd) ;
				
		return param ;
	}
}
