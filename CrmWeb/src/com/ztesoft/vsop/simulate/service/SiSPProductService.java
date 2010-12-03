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


public class SiSPProductService  {

	public boolean insertProduct(HashMap Product ) throws Exception {
		Map param = new HashMap() ;
		param.put("Product", Product) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"insertProduct" ,param)) ;
		return result ;
	}

	
	public boolean updateProduct(HashMap Product ) throws Exception {
		Map param = new HashMap() ;
		param.put("Product", Product) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"updateProduct" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProductData(String product_nbr , String product_name ,String service_code , String fileterProductId ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("service_code", service_code) ;
		param.put("product_name", product_name) ;
		param.put("product_nbr", product_nbr) ;
		param.put("fileterProductId", fileterProductId) ;//选择页面使用参数
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"searchProductData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProductById(String product_id) throws Exception {
		Map param = getProductKeyMap(product_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"getProductById" ,param)) ;
		
		return result ;
	}
	

	public List findProductByCond(String product_id) throws Exception {
		Map param = getProductKeyMap(product_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"findProductByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductById(String product_id) throws Exception {
		Map param = getProductKeyMap(product_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"deleteProductById" ,param)) ;
		
		return result ;
	}
	
	private Map getProductKeyMap(String product_id){
		Map param = new HashMap() ;
				
		param.put("product_id", product_id) ;
				
		return param ;
	}
	
	public boolean insertProdAccNbrType(HashMap ProdAccNbrType ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdAccNbrType", ProdAccNbrType) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"insertProdAccNbrType" ,param)) ;
		return result ;
	}

	
	public boolean updateProdAccNbrType(HashMap ProdAccNbrType ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdAccNbrType", ProdAccNbrType) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"updateProdAccNbrType" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProdAccNbrTypeData(String product_id , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_id", product_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"searchProdAccNbrTypeData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProdAccNbrTypeById(String prod_acc_nbr_type_id) throws Exception {
		Map param = getProdAccNbrTypeKeyMap(prod_acc_nbr_type_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"getProdAccNbrTypeById" ,param)) ;
		
		return result ;
	}
	

	public List findProdAccNbrTypeByCond(String prod_acc_nbr_type_id) throws Exception {
		Map param = getProdAccNbrTypeKeyMap(prod_acc_nbr_type_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"findProdAccNbrTypeByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProdAccNbrTypeById(String prod_acc_nbr_type_id) throws Exception {
		Map param = getProdAccNbrTypeKeyMap(prod_acc_nbr_type_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"deleteProdAccNbrTypeById" ,param)) ;
		
		return result ;
	}
	
	private Map getProdAccNbrTypeKeyMap(String prod_acc_nbr_type_id){
		Map param = new HashMap() ;
				
		param.put("prod_acc_nbr_type_id", prod_acc_nbr_type_id) ;
				
		return param ;
	}
	public boolean insertProdPlatform(HashMap ProdPlatform ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdPlatform", ProdPlatform) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"insertProdPlatform" ,param)) ;
		return result ;
	}

	
	public boolean updateProdPlatform(HashMap ProdPlatform ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdPlatform", ProdPlatform) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"updateProdPlatform" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProdPlatformData(String product_id, 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_id", product_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"searchProdPlatformData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProdPlatformById(String partner_system_info_id) throws Exception {
		Map param = getProdPlatformKeyMap(partner_system_info_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"getProdPlatformById" ,param)) ;
		
		return result ;
	}
	

	public List findProdPlatformByCond(String partner_system_info_id) throws Exception {
		Map param = getProdPlatformKeyMap(partner_system_info_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"findProdPlatformByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProdPlatformById(String partner_system_info_id) throws Exception {
		Map param = getProdPlatformKeyMap(partner_system_info_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"deleteProdPlatformById" ,param)) ;
		
		return result ;
	}
	
	private Map getProdPlatformKeyMap(String partner_system_info_id){
		Map param = new HashMap() ;
				
		param.put("partner_system_info_id", partner_system_info_id) ;
				
		return param ;
	}
	public boolean insertProductRelation(HashMap ProductRelation ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductRelation", ProductRelation) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"insertProductRelation" ,param)) ;
		return result ;
	}

	
	public boolean updateProductRelation(HashMap ProductRelation ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductRelation", ProductRelation) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"updateProductRelation" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProductRelationData(String product_id , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_id", product_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"searchProductRelationData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProductRelationById(String product_rel_id) throws Exception {
		Map param = getProductRelationKeyMap(product_rel_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"getProductRelationById" ,param)) ;
		
		return result ;
	}
	

	public List findProductRelationByCond(String product_rel_id) throws Exception {
		Map param = getProductRelationKeyMap(product_rel_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"findProductRelationByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductRelationById(String product_rel_id) throws Exception {
		Map param = getProductRelationKeyMap(product_rel_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"deleteProductRelationById" ,param)) ;
		
		return result ;
	}
	
	private Map getProductRelationKeyMap(String product_rel_id){
		Map param = new HashMap() ;
				
		param.put("product_rel_id", product_rel_id) ;
				
		return param ;
	}
	
	public boolean insertProductServAbilityRel(HashMap ProductServAbilityRel ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductServAbilityRel", ProductServAbilityRel) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"insertProductServAbilityRel" ,param)) ;
		return result ;
	}

	
	public boolean updateProductServAbilityRel(HashMap ProductServAbilityRel ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductServAbilityRel", ProductServAbilityRel) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"updateProductServAbilityRel" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProductServAbilityRelData(String product_id,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_id", product_id) ;
		
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"searchProductServAbilityRelData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProductServAbilityRelById(String product_service_ability_rel_id) throws Exception {
		Map param = getProductServAbilityRelKeyMap(product_service_ability_rel_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"getProductServAbilityRelById" ,param)) ;
		
		return result ;
	}
	

	public List findProductServAbilityRelByCond(String product_service_ability_rel_id) throws Exception {
		Map param = getProductServAbilityRelKeyMap(product_service_ability_rel_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"findProductServAbilityRelByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductServAbilityRelById(String product_service_ability_rel_id) throws Exception {
		Map param = getProductServAbilityRelKeyMap(product_service_ability_rel_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"deleteProductServAbilityRelById" ,param)) ;
		
		return result ;
	}
	
	private Map getProductServAbilityRelKeyMap(String product_service_ability_rel_id){
		Map param = new HashMap() ;
				
		param.put("product_service_ability_rel_id", product_service_ability_rel_id) ;
				
		return param ;
	}
	/**
	 * 加载产品类型树
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public String loadProductType(String typeId ) throws Exception {
		Map param = new HashMap() ;
		param.put("typeId", typeId) ;
		return DataTranslate._String(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"loadProductType" ,param)) ;
	}


	/**
	 * 查询供应商
	 * @param partner_code
	 * @param partner_type
	 * @param partner_name
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchPartnerData(String partner_code , String partner_type ,String partner_name , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("partner_code", partner_code) ;
		param.put("partner_type", partner_type) ;
		param.put("partner_name", partner_name) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("SiSPProductManager",
						"searchPartnerData" ,param)) ;
		
		return result ;
	}
}
