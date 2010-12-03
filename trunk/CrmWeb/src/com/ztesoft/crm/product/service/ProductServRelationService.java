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


public class ProductServRelationService  {
		
	public boolean insertProductServRelation(HashMap ProductServRelation ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductServRelation", ProductServRelation) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductServRelationBO",
						"insertProductServRelation" ,param)) ;
		return result ;
	}

	
	public boolean updateProductServRelation(HashMap ProductServRelation ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductServRelation", ProductServRelation) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductServRelationBO",
						"updateProductServRelation" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 查询产品可选业务
	 * @param product_cat
	 * @param action_type
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductServRelationData(String product_cat , String action_type , String product_id ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_cat", product_cat) ; 
		param.put("action_type", action_type) ;
		param.put("product_id", product_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductServRelationBO",
						"searchProductServRelationData" ,param)) ;
		
		return result ;
	}
	/**
	 * 查询产品已选业务
	 * @param product_id
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductServRelationData2(String product_id ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_id", product_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductServRelationBO",
						"searchProductServRelationData2" ,param)) ;
		
		return result ;
	}
	/**
	 * 保存产品业务关系
	 * @param ProductServRelation
	 * @return
	 * @throws Exception
	 */
	public boolean saveProductServRelation(ArrayList ProductServRelation) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductServRelation", ProductServRelation) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductServRelationBO",
						"saveProductServRelation" ,param)) ;
		return result ;
	}
	
	
	public Map getProductServRelationById(String product_id,String service_offer_id) throws Exception {
		Map param = getProductServRelationKeyMap(product_id,service_offer_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("ProductServRelationBO",
						"getProductServRelationById" ,param)) ;
		
		return result ;
	}
	

	public List findProductServRelationByCond(String product_id,String service_offer_id) throws Exception {
		Map param = getProductServRelationKeyMap(product_id,service_offer_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("ProductServRelationBO",
						"findProductServRelationByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductServRelationById(String product_id,String service_offer_id) throws Exception {
		Map param = getProductServRelationKeyMap(product_id,service_offer_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductServRelationBO",
						"deleteProductServRelationById" ,param)) ;
		
		return result ;
	}
	
	private Map getProductServRelationKeyMap(String product_id,String service_offer_id){
		Map param = new HashMap() ;
				
		param.put("product_id", product_id) ;
				
		param.put("service_offer_id", service_offer_id) ;
				
		return param ;
	}
}
