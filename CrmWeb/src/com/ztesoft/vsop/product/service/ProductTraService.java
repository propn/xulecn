package com.ztesoft.vsop.product.service;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;

public class ProductTraService {
	
	private Map getProductKeyMap(String product_id){
		Map param = new HashMap() ;
				
		param.put("product_id", product_id) ;
				
		return param ;
	}
	
	/**
	 * 根据条件查询
	 * @param product_name
	 * @param prod_type
	 * @param product_code
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductTraData(String product_name,String prod_func_type,String product_code,int pageIndex, int pageSize)throws Exception{
		Map param = new HashMap();
		param.put("product_name", product_name);
		param.put("prod_func_type", prod_func_type);
		param.put("product_code", product_code);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));	
		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.ProductTraManager,
						"searchProductTraData", param));		
		return result;
	}
	
	/**
	 * 根据ID返回
	 * @param product_id
	 * @return
	 * @throws Exception
	 */
	public Map getProductTraById(String product_id) throws Exception {
		Map param = getProductKeyMap(product_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.ProductTraManager,
						"getProductTraById" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 新增
	 * @param Product
	 * @return
	 * @throws Exception
	 */
	public boolean insertProductTra(HashMap Product ) throws Exception {
		Map param = new HashMap() ;
		param.put("Product", Product) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.ProductTraManager,
						"insertProductTra" ,param)) ;
		return result ;
	}
	
	/**
	 * 更新
	 * @param Product
	 * @return
	 * @throws Exception
	 */
	public boolean updateProductTra(HashMap Product ) throws Exception {
		Map param = new HashMap() ;
		param.put("Product", Product) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.ProductTraManager,
						"updateProductTra" ,param)) ;
		
		return result ;
	}
	
	public boolean deleteProductTraById(String product_id) throws Exception {
		Map param = getProductKeyMap(product_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.ProductTraManager,
						"deleteProductTraById" ,param)) ;
		
		return result ;
	}

}
