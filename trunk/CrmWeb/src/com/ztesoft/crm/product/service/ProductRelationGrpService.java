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


public class ProductRelationGrpService  {
		
	public boolean insertProductRelationGrp(HashMap ProductRelationGrp ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductRelationGrp", ProductRelationGrp) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductRelationGrpBO",
						"insertProductRelationGrp" ,param)) ;
		return result ;
	}

	
	public boolean updateProductRelationGrp(HashMap ProductRelationGrp ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductRelationGrp", ProductRelationGrp) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductRelationGrpBO",
						"updateProductRelationGrp" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 查询产品群可选产品
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductRelationGrpData(String conf_group_id , String product_cat , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("conf_group_id", conf_group_id) ;
		param.put("product_cat", product_cat) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductRelationGrpBO",
						"searchProductRelationGrpData" ,param)) ;
		
		return result ;
	}
	/**
	 * 查询产品群已选产品
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductRelationGrpData2(String conf_group_id , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("conf_group_id", conf_group_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductRelationGrpBO",
						"searchProductRelationGrpData2" ,param)) ;
		
		return result ;
	}
	/**
	 * 保存产品群内关系
	 * @param ProductRelationGrp
	 * @return
	 * @throws Exception
	 */
	public boolean saveProductRelationGrp(ArrayList ProductRelationGrp ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductRelationGrp", ProductRelationGrp) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductRelationGrpBO",
						"saveProductRelationGrp" ,param)) ;
		return result ;
	}
	
	public Map getProductRelationGrpById(String group_id,String product_id,String seq) throws Exception {
		Map param = getProductRelationGrpKeyMap(group_id,product_id,seq) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("ProductRelationGrpBO",
						"getProductRelationGrpById" ,param)) ;
		
		return result ;
	}
	

	public List findProductRelationGrpByCond(String group_id,String product_id,String seq) throws Exception {
		Map param = getProductRelationGrpKeyMap(group_id,product_id,seq) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("ProductRelationGrpBO",
						"findProductRelationGrpByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductRelationGrpById(String group_id,String product_id,String seq) throws Exception {
		Map param = getProductRelationGrpKeyMap(group_id,product_id,seq) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductRelationGrpBO",
						"deleteProductRelationGrpById" ,param)) ;
		
		return result ;
	}
	
	private Map getProductRelationGrpKeyMap(String group_id,String product_id,String seq){
		Map param = new HashMap() ;
				
		param.put("group_id", group_id) ;
				
		param.put("product_id", product_id) ;
				
		param.put("seq", seq) ;
				
		return param ;
	}
}
