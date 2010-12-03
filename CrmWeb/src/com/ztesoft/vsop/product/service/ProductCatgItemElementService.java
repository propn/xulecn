package com.ztesoft.vsop.product.service;


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


public class ProductCatgItemElementService  {
	/**
	     需要替换位置 说明 ：
	  1. "ProductCatgItemElementBO"  替换为ServiceList注册的服务 
	  2. searchProductCatgItemElementData 改方法的参数名称
	  3. findProductCatgItemElementByCond(String catalog_item_id,String element_type,String element_id) 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertProductCatgItemElement(HashMap ProductCatgItemElement ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductCatgItemElement", ProductCatgItemElement) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductCatgItemElementBO",
						"insertProductCatgItemElement" ,param)) ;
		return result ;
	}

	//批量插入
	public int insertBatchProductCatgItemElement(List selList ) throws Exception {
		Map param = new HashMap() ;
		param.put("selList", selList) ;
//		int result[] = (int[])(
//				ServiceManager.callJavaBeanService("ProductCatgItemElementBO", 
//						"batchInsert", param));
		int result = DataTranslate._int(
				ServiceManager.callJavaBeanService("ProductCatgItemElementBO", 
						"batchInsert", param));
		return result ;
	}
	
	public boolean updateProductCatgItemElement(HashMap ProductCatgItemElement ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductCatgItemElement", ProductCatgItemElement) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductCatgItemElementBO",
						"updateProductCatgItemElement" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProductCatgItemElementData(String iParam1 , String iParam2 ,String iParam3 , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("iParam1", iParam1) ;
		param.put("iParam2", iParam2) ;
		param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductCatgItemElementBO",
						"searchProductCatgItemElementData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProductCatgItemElementById(String catalog_item_id,String element_type,String element_id) throws Exception {
		Map param = getProductCatgItemElementKeyMap(catalog_item_id,element_type,element_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("ProductCatgItemElementBO",
						"getProductCatgItemElementById" ,param)) ;
		
		return result ;
	}
	

	public List findProductCatgItemElementByCond(String catalog_item_id,String element_type,String element_id) throws Exception {
		Map param = getProductCatgItemElementKeyMap(catalog_item_id,element_type,element_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("ProductCatgItemElementBO",
						"findProductCatgItemElementByCond" ,param)) ;
		
		return result ;
	}
	
	
//	public boolean deleteProductCatgItemElementById(String catalog_item_id,String element_type,String element_id) throws Exception {
	public boolean deleteProductCatgItemElementById(HashMap ProductCatgItemElement) throws Exception {
//		Map param = getProductCatgItemElementKeyMap(catalog_item_id,element_type,element_id) ;
		Map param = new HashMap();
		param.put("productCatgItemElement"	, ProductCatgItemElement);
		
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductCatgItemElementBO",
						"deleteProductCatgItemElementById" ,param)) ;
		
		return result ;
	}
	
	private Map getProductCatgItemElementKeyMap(String catalog_item_id,String element_type,String element_id){
		Map param = new HashMap() ;
				
		param.put("catalog_item_id", catalog_item_id) ;
				
		param.put("element_type", element_type) ;
				
		param.put("element_id", element_id) ;
				
		return param ;
	}
}
