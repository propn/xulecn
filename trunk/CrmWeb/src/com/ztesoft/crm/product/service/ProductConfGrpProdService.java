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


public class ProductConfGrpProdService  {
	
	/**
	 * 群增加
	 * @param ProductConfGrp
	 * @return
	 * @throws Exception
	 */
	public boolean insertProductConfGrp(HashMap ProductConfGrp ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductConfGrp", ProductConfGrp) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductConfGrpProdBO",
						"insertProductConfGrp" ,param)) ;
		return result ;
	}

	/**
	 * 群修改
	 * @param ProductConfGrp
	 * @return
	 * @throws Exception
	 */
	public boolean updateProductConfGrp(HashMap ProductConfGrp ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductConfGrp", ProductConfGrp) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductConfGrpProdBO",
						"updateProductConfGrp" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 群查询
	 * @param query_type
	 * @param query_data
	 * @param group_type
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductConfGrpData(String query_type , String query_data , String group_type,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("query_type", query_type) ;
		param.put("query_data", query_data) ;
		param.put("group_type", group_type) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductConfGrpProdBO",
						"searchProductConfGrpData" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 查询可选群
	 * @param group_a_id
	 * @param group_type
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductGroupRelationData(String group_a_id,String group_type,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("group_a_id", group_a_id) ;
		param.put("group_type", group_type) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductConfGrpProdBO",
						"searchProductGroupRelationData" ,param)) ;
		
		return result ;
	}
	
	public PageModel searchProductGroupRelationData2(String group_a_id,String group_type, 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("group_a_id", group_a_id) ;
		param.put("group_type", group_type) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductConfGrpProdBO",
						"searchProductGroupRelationData2" ,param)) ;
		
		return result ;
	}
	
	public boolean saveProductGroupRelation(ArrayList ProductGroupRelation) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductGroupRelation", ProductGroupRelation) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductConfGrpProdBO",
						"saveProductGroupRelation" ,param)) ;
		return result ;
	}
	
	
	public Map getProductConfGrpById(String conf_group_id) throws Exception {
		Map param = getProductConfGrpKeyMap(conf_group_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("ProductConfGrpProdBO",
						"getProductConfGrpById" ,param)) ;
		
		return result ;
	}
	

	public List findProductConfGrpByCond(String conf_group_id) throws Exception {
		Map param = getProductConfGrpKeyMap(conf_group_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("ProductConfGrpProdBO",
						"findProductConfGrpByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductConfGrpById(String conf_group_id) throws Exception {
		Map param = getProductConfGrpKeyMap(conf_group_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductConfGrpProdBO",
						"deleteProductConfGrpById" ,param)) ;
		
		return result ;
	}
	
	private Map getProductConfGrpKeyMap(String conf_group_id){
		Map param = new HashMap() ;
				
		param.put("conf_group_id", conf_group_id) ;
				
		return param ;
	}
}
