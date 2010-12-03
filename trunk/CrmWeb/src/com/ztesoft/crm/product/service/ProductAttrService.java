package com.ztesoft.crm.product.service;


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


public class ProductAttrService  {
		
	public boolean insertProductAttr(HashMap ProductAttr ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductAttr", ProductAttr) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductAttrBO",
						"insertProductAttr" ,param)) ;
		return result ;
	}

	
	public boolean updateProductAttr(HashMap ProductAttr ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductAttr", ProductAttr) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductAttrBO",
						"updateProductAttr" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProductAttrData(String product_id, 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("product_id", product_id) ;		
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductAttrBO",
						"searchProductAttrData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProductAttrById(String attr_seq,String product_id) throws Exception {
		Map param = getProductAttrKeyMap(attr_seq,product_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("ProductAttrBO",
						"getProductAttrById" ,param)) ;
		
		return result ;
	}
	

	public List findProductAttrByCond(String attr_seq,String product_id) throws Exception {
		Map param = getProductAttrKeyMap(attr_seq,product_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("ProductAttrBO",
						"findProductAttrByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductAttrById(String attr_seq,String product_id) throws Exception {
		Map param = getProductAttrKeyMap(attr_seq,product_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductAttrBO",
						"deleteProductAttrById" ,param)) ;
		
		return result ;
	}
	
	private Map getProductAttrKeyMap(String attr_seq,String product_id){
		Map param = new HashMap() ;
				
		param.put("attr_seq", attr_seq) ;
				
		param.put("product_id", product_id) ;
				
		return param ;
	}
}
