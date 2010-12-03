package com.ztesoft.crm.product.service;


import java.util.ArrayList;
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


public class ProductRelationService  {
		
	public boolean insertProductRelation(HashMap ProductRelation ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductRelation", ProductRelation) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductRelationBO",
						"insertProductRelation" ,param)) ;
		return result ;
	}

	
	public boolean updateProductRelation(HashMap ProductRelation ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductRelation", ProductRelation) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductRelationBO",
						"updateProductRelation" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProductRelationData(String product_id,String prod_cat_type,String relation_type_id, String product_classification,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_id", product_id) ;
		param.put("prod_cat_type", prod_cat_type) ;			
		param.put("relation_type_id", relation_type_id) ;	
		param.put("product_classification", product_classification) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductRelationBO",
						"searchProductRelationData" ,param)) ;
		
		return result ;
	}
	
	public PageModel searchProductRelationData2(String product_id,String prod_cat_type, String relation_type_id, 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_id", product_id) ;
		param.put("prod_cat_type", prod_cat_type) ;		
		param.put("relation_type_id", relation_type_id) ;	
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductRelationBO",
						"searchProductRelationData2" ,param)) ;
		
		return result ;
	}
	
	public PageModel searchProductRelationData3(String product_id,String relation_type_id, 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_id", product_id) ;	
		param.put("relation_type_id", relation_type_id) ;	
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductRelationBO",
						"searchProductRelationData3" ,param)) ;
		
		return result ;
	}
	
	public boolean saveProductRelation(ArrayList ProductRelation) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductRelation", ProductRelation) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductRelationBO",
						"saveProductRelation" ,param)) ;
		return result ;
	}
	
	
	public Map getProductRelationById(String relation_id) throws Exception {
		Map param = getProductRelationKeyMap(relation_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("ProductRelationBO",
						"getProductRelationById" ,param)) ;
		
		return result ;
	}
	

	public List findProductRelationByCond(String relation_id) throws Exception {
		Map param = getProductRelationKeyMap(relation_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("ProductRelationBO",
						"findProductRelationByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductRelationById(String relation_id) throws Exception {
		Map param = getProductRelationKeyMap(relation_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductRelationBO",
						"deleteProductRelationById" ,param)) ;
		
		return result ;
	}
	
	private Map getProductRelationKeyMap(String relation_id){
		Map param = new HashMap() ;
				
		param.put("relation_id", relation_id) ;
				
		return param ;
	}
}
