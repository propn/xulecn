package com.ztesoft.crm.product.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.dict.DataTranslate;

import com.ztesoft.common.util.PageModel;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.ServiceList;


public class ProductMainService  {
	/**
	     需要替换位置 说明 ：
	  1. ServiceList.MyService  替换为ServiceList注册的服务 
	  2. searchProductData 改方法的参数名称
	  3. findProductByCond(String product_id,String seq) 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertProduct(HashMap Product ) throws Exception {
		Map param = new HashMap() ;
		param.put("Product", Product) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.MyService,
						"insertProduct" ,param)) ;
		return result ;
	}

	
	public boolean updateProduct(HashMap Product ) throws Exception {
		Map param = new HashMap() ;
		param.put("Product", Product) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.MyService,
						"updateProduct" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProductData(String iParam1 , String iParam2 ,String iParam3 , 
			int pageIndex , int pageSize) throws Exception {
		
		/*DynamicDict dto = getServiceDTO("PRODUCTCONFIGBO" ,"getProductCatalogItem");
	      dto.setValueByName("parameter",map) ;
	      dto = ActionDispatch.dispatch(dto);
	      return ((String)dto.getValueByName("result")) ;*/
		
		
		Map param = new HashMap() ;
		param.put("CATALOG_ITEM_ID", iParam1) ;
		//param.put("iParam2", iParam2) ;
		//param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("PRODUCTMAINBO",
						"searchProductData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProductById(String product_id,String seq) throws Exception {
		Map param = getProductKeyMap(product_id,seq) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.MyService,
						"getProductById" ,param)) ;
		
		return result ;
	}
	

	public List findProductByCond(String product_id,String seq) throws Exception {
		Map param = getProductKeyMap(product_id,seq) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.MyService,
						"findProductByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductById(String product_id,String seq) throws Exception {
		Map param = getProductKeyMap(product_id,seq) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.MyService,
						"deleteProductById" ,param)) ;
		
		return result ;
	}
	
	private Map getProductKeyMap(String product_id,String seq){
		Map param = new HashMap() ;
				
		param.put("product_id", product_id) ;
				
		param.put("seq", seq) ;
				
		return param ;
	}
}
