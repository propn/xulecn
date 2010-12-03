package com.ztesoft.vsop.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class SPProductService  {
	
	//根据产品的提供商ID来查找产品用于产品查询统计
	public PageModel searchProductByProviderID(String product_provider_id,int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_provider_id", product_provider_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"searchProductByProviderID" ,param)) ;
		return result ;
	}

	public boolean insertProduct(HashMap Product ) throws Exception {
		Map param = new HashMap() ;
		param.put("Product", Product) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"insertProduct" ,param)) ;
		return result ;
	}

	
	public boolean updateProduct(HashMap Product ) throws Exception {
		Map param = new HashMap() ;
		param.put("Product", Product) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"updateProduct" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProductData(String product_nbr,String product_code, String product_name,
			String service_ability_id, String fileterProductId,
			String product_provider_id,String order_host, int pageIndex, int pageSize)
			throws Exception {
		Map param = new HashMap();
		param.put("service_ability_id", service_ability_id);
		param.put("product_name", product_name);
		param.put("product_nbr", product_nbr);
		param.put("product_code", product_code);
		param.put("fileterProductId", fileterProductId);// 选择页面使用参数
		param.put("product_provider_id", product_provider_id);
		param.put("order_host", order_host);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.SPProductManager,
						"searchProductData", param));

		return result;
	}
	
	public PageModel searchProductData2Select(String product_id,String product_name, 
			int pageIndex, int pageSize)
			throws Exception {
		Map param = new HashMap();
		param.put("product_id", product_id);
		param.put("product_name", product_name);
		
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.SPProductManager,
						"searchProductData2Select", param));

		return result;
	}
	
	public PageModel searchProductOfferData2Select(String prod_offer_id,String prod_offer_name, 
			int pageIndex, int pageSize) throws Exception {
		Map param = new HashMap();
		param.put("prod_offer_id", prod_offer_id);
		param.put("prod_offer_name", prod_offer_name);
		
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));
		
		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.SPProductManager,
						"searchProductOfferData2Select", param));
		
		return result;
	}
	//根据产品的提供商ID来查找
	public PageModel searchProductDataByProviderID(String product_provider_id,int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_provider_id", product_provider_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"searchProductDataByProviderID" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProductById(String product_id) throws Exception {
		Map param = getProductKeyMap(product_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"getProductById" ,param)) ;
		
		return result ;
	}
	
	public List getAbilityById(String prod_inst_id) throws Exception{
		
		Map map = new HashMap();
		map.put("prod_inst_id", prod_inst_id);
		List result = DataTranslate._List(ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"getAbilityById" ,map));
		return result;
	}
	
	public PageModel getProductsById(String product_id, int pageIndex, int pageSize) throws Exception {
		Map param = new HashMap();
		param.put("product_id", product_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;

		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"getProductsById" ,param)) ;
		
		return result ;
	}
	

	public List findProductByCond(String product_id) throws Exception {
		Map param = getProductKeyMap(product_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"findProductByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductById(String product_id) throws Exception {
		Map param = getProductKeyMap(product_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
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
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"insertProdAccNbrType" ,param)) ;
		return result ;
	}

	
	public boolean updateProdAccNbrType(HashMap ProdAccNbrType ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdAccNbrType", ProdAccNbrType) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
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
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"searchProdAccNbrTypeData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProdAccNbrTypeById(String prod_acc_nbr_type_id) throws Exception {
		Map param = getProdAccNbrTypeKeyMap(prod_acc_nbr_type_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"getProdAccNbrTypeById" ,param)) ;
		
		return result ;
	}
	

	public List findProdAccNbrTypeByCond(String prod_acc_nbr_type_id) throws Exception {
		Map param = getProdAccNbrTypeKeyMap(prod_acc_nbr_type_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"findProdAccNbrTypeByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProdAccNbrTypeById(String prod_acc_nbr_type_id) throws Exception {
		Map param = getProdAccNbrTypeKeyMap(prod_acc_nbr_type_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
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
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"insertProdPlatform" ,param)) ;
		return result ;
	}

	
	public boolean updateProdPlatform(HashMap ProdPlatform ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdPlatform", ProdPlatform) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
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
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"searchProdPlatformData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProdPlatformById(String prod_platform_id) throws Exception {
		Map param = getProdPlatformKeyMap(prod_platform_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"getProdPlatformById" ,param)) ;
		
		return result ;
	}
	

	public List findProdPlatformByCond(String prod_platform_id) throws Exception {
		Map param = getProdPlatformKeyMap(prod_platform_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"findProdPlatformByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProdPlatformById(String partner_system_info_id) throws Exception {
		Map param = getProdPlatformKeyMap(partner_system_info_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
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
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"insertProductRelation" ,param)) ;
		return result ;
	}

	
	public boolean updateProductRelation(HashMap ProductRelation ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductRelation", ProductRelation) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
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
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"searchProductRelationData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProductRelationById(String product_rel_id) throws Exception {
		Map param = getProductRelationKeyMap(product_rel_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"getProductRelationById" ,param)) ;
		
		return result ;
	}
	

	public List findProductRelationByCond(String product_rel_id) throws Exception {
		Map param = getProductRelationKeyMap(product_rel_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"findProductRelationByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductRelationById(String product_rel_id) throws Exception {
		Map param = getProductRelationKeyMap(product_rel_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
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
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"insertProductServAbilityRel" ,param)) ;
		return result ;
	}

	
	public boolean updateProductServAbilityRel(HashMap ProductServAbilityRel ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductServAbilityRel", ProductServAbilityRel) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
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
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"searchProductServAbilityRelData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProductServAbilityRelById(String product_service_ability_rel_id) throws Exception {
		Map param = getProductServAbilityRelKeyMap(product_service_ability_rel_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"getProductServAbilityRelById" ,param)) ;
		
		return result ;
	}
	

	public List findProductServAbilityRelByCond(String product_service_ability_rel_id) throws Exception {
		Map param = getProductServAbilityRelKeyMap(product_service_ability_rel_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"findProductServAbilityRelByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductServAbilityRelById(String product_service_ability_rel_id) throws Exception {
		Map param = getProductServAbilityRelKeyMap(product_service_ability_rel_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
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
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public String loadProductType(String typeId, String orderId) throws Exception {
		Map param = new HashMap() ;
		param.put("typeId", typeId) ;
		param.put("orderId", orderId) ;
		return DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
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
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager,
						"searchPartnerData" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 用户(产品实例)信息查询
	 * 
	 * @param accNbr
	 * @param lanCode
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductInsts(String accNbr, String lanCode, String productId, String prodInstId, int pageIndex, int pageSize) 
		throws Exception {
		Map param = new HashMap();
		param.put("accNbr", accNbr);
		param.put("lanCode", lanCode);
		param.put("productId", productId);
		param.put("prodInstId", prodInstId);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager, "searchProductInsts", param));
		return result;
	}
	
	/**
	 * 搜索产品.
	 * 
	 * @param prodName 主产品名称
	 * @param prodType 产品类型.0-主要产品; 1-增值产品.
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProducts(String prodName,String prodFuncType ,int pageIndex, int pageSize) throws Exception {
		Map param = new HashMap();
		param.put("productName", prodName);
		param.put("prodFuncType", prodFuncType);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SPProductManager, "searchProducts", param));
		return result;
	}
}
