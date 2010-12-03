package com.ztesoft.vsop.simulate.offer.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class ProdOffService  {

	/**
	 * 新增销售品关系时，点击Z销售品时Z销售品的选择范围
	 * @param prod_off_id
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProdOff(String offer_nbr, String prod_offer_name, 
			String prod_off_id, int pageIndex, int pageSize) throws Exception {
		
		Map param = new HashMap();
		param.put("offer_nbr", offer_nbr);
		param.put("prod_offer_name", prod_offer_name);
		param.put("prod_off_id", prod_off_id);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"searchProdOff" ,param));
		
		return result ;
	}	
	
	public boolean insertProdOff(HashMap ProdOff ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdOff", ProdOff) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"insertProdOff" ,param)) ;
		return result ;
	}

	
	public boolean updateProdOff(HashMap ProdOff ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdOff", ProdOff) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"updateProdOff" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProdOffData(String offer_nbr , String prod_offer_name , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("offer_nbr", offer_nbr) ;
		param.put("prod_offer_name", prod_offer_name) ;
		//param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"searchProdOffData" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 销售品中的tab，销售品构成中调用到
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProduct(String package_host,String product_name,int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		//param.put("iParam3", iParam3) ;
		param.put("package_host", package_host);
		param.put("product_name", product_name);
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"searchProduct" ,param)) ;
		
		return result ;
	}
	
	public Map getProdOffById(String prod_offer_id) throws Exception {
		Map param = getProdOffKeyMap(prod_offer_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"getProdOffById" ,param)) ;
		
		return result ;
	}
	

	public List findProdOffByCond(String prod_offer_id) throws Exception {
		Map param = getProdOffKeyMap(prod_offer_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"findProdOffByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffById(String prod_offer_id) throws Exception {
		Map param = getProdOffKeyMap(prod_offer_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"deleteProdOffById" ,param)) ;
		
		return result ;
	}
	
	private Map getProdOffKeyMap(String prod_offer_id){
		Map param = new HashMap() ;
				
		param.put("prod_offer_id", prod_offer_id) ;
				
		return param ;
	}
	
	public boolean insertProdOffRel(HashMap ProdOffRel ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdOffRel", ProdOffRel) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"insertProdOffRel" ,param)) ;
		return result ;
	}

	
	public boolean updateProdOffRel(HashMap ProdOffRel ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdOffRel", ProdOffRel) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"updateProdOffRel" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProdOffRelData(String offer_a_id, String offer_z_id, int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("offer_a_id", offer_a_id) ;
		param.put("offer_z_id", offer_z_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"searchProdOffRelData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProdOffRelById(String prod_offer_rel_id) throws Exception {
		Map param = getProdOffRelKeyMap(prod_offer_rel_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"getProdOffRelById" ,param)) ;
		
		return result ;
	}
	

	public List findProdOffRelByCond(String prod_offer_rel_id) throws Exception {
		Map param = getProdOffRelKeyMap(prod_offer_rel_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"findProdOffRelByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffRelById(String prod_offer_rel_id) throws Exception {
		Map param = new HashMap();
		param.put("prod_offer_rel_id", prod_offer_rel_id) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"deleteProdOffRelById" ,param)) ;
		
		return result ;
	}
	
	public boolean deleteProdOffRel(String relation_type_id, String offer_a_id, String offer_z_id) throws Exception {
		Map param = getKeyForDelete(relation_type_id, offer_a_id, offer_z_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"deleteProdOffRel" ,param)) ;
		
		return result ;
	}
	
	private Map getProdOffRelKeyMap(String prod_offer_rel_id){
		Map param = new HashMap() ;
				
		param.put("prod_offer_rel_id", prod_offer_rel_id) ;
				
		return param ;
	}
	
	private Map getKeyForDelete(String relation_type_id, String offer_a_id, String offer_z_id) throws Exception {
		Map map = new HashMap();
		map.put("relation_type_id", relation_type_id);
		map.put("offer_a_id", offer_a_id);
		map.put("offer_z_id", offer_z_id);
		return map;
	}
	
	/**
	 * 供 prodOff.js 调用
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public String getProdOffNameByKey(String prodOffId) throws Exception {
		Map map = new HashMap();
		map.put("prod_offer_id", prodOffId);
		String productName = DataTranslate._String (
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"getProdOffNameByKey", map));
		return productName;
	}
	
	public boolean insertProdOffDetaRole(HashMap ProdOffDetaRole ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdOffDetaRole", ProdOffDetaRole) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"insertProdOffDetaRole" ,param)) ;
		return result ;
	}

	
	public boolean updateProdOffDetaRole(HashMap ProdOffDetaRole ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdOffDetaRole", ProdOffDetaRole) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"updateProdOffDetaRole" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProdOffDetaRoleData(String prod_offer_id, int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap();
		param.put("prod_offer_id", prod_offer_id);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"searchProdOffDetaRoleData" ,param));
		
		return result ;
	}
	
	
	public Map getProdOffDetaRoleById(String prod_offer_role_cd) throws Exception {
		Map param = getProdOffDetaRoleKeyMap(prod_offer_role_cd) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"getProdOffDetaRoleById" ,param)) ;
		
		return result ;
	}
	

	public List findProdOffDetaRoleByCond(String prod_offer_role_cd) throws Exception {
		Map param = getProdOffDetaRoleKeyMap(prod_offer_role_cd) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"findProdOffDetaRoleByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffDetaRoleById(String prod_offer_role_cd) throws Exception {
		Map param = getProdOffDetaRoleKeyMap(prod_offer_role_cd) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"deleteProdOffDetaRoleById" ,param)) ;
		
		return result ;
	}
	
	private Map getProdOffDetaRoleKeyMap(String prod_offer_role_cd){
		Map param = new HashMap() ;
				
		param.put("prod_offer_role_cd", prod_offer_role_cd) ;
				
		return param ;
	}
}
