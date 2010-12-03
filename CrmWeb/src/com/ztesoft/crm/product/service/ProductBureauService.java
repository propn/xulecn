package com.ztesoft.crm.product.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.dict.DataTranslate;

import com.ztesoft.common.util.PageModel;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.ServiceList;


public class ProductBureauService  {
	/**
	     需要替换位置 说明 ：
	  1. "ProductBureauBO"  替换为ServiceList注册的服务 
	  2. searchProductBureauData 改方法的参数名称
	  3. findProductBureauByCond(String product_id) 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	//DictService dictService = new DictService();
	
	public String getBureau(HashMap map) throws Exception{		
			Map param = new HashMap() ; 
			param.put("ProductBureau", map) ;
			//System.out.println("+++++++++ProductBureau+++++++++++"+param.toString());
			String result = DataTranslate._String(
					ServiceManager.callJavaBeanService("ProductBureauBO",
							"getBureau" ,param)) ;
			return result ;
	   }
	
	public List getProductSeledBureau(HashMap map) throws Exception{		
		Map param = new HashMap() ; 
		param.put("ProductBureau", map) ;
		//System.out.println("+++++++++ProductBureau+++++++++++"+param.toString());
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("ProductBureauBO",
						"getProductSeledBureau" ,param)) ;
		return result ;
   }
	   
	   
	public boolean insertProductBureau(HashMap ProductBureau ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductBureau", ProductBureau) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductBureauBO",
						"insertProductBureau" ,param)) ;
		return result ;
	}
	
	public boolean saveProductBureau(ArrayList ProductBureau ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductBureau", ProductBureau) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductBureauBO",
						"saveProductBureau" ,param)) ;
		return result ;
	}
	
	

	
	public boolean updateProductBureau(HashMap ProductBureau ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductBureau", ProductBureau) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductBureauBO",
						"updateProductBureau" ,param)) ;
		
		return result ;
	}
	
	//查询产品适用区域
	public PageModel searchProductBureauData(String product_id,int pageSize,int pageIndex) throws Exception {		
		Map param = new HashMap() ;
		param.put("product_id", product_id) ;	
		param.put("pageSize", new Integer(pageSize)) ;	
		param.put("pageIndex", new Integer(pageIndex)) ;	
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductBureauBO",
						"searchProductBureauData" ,param)) ;		
		return result ;
	}
	
	
	public Map getProductBureauById(String product_id,String region_id) throws Exception {
		Map param = new HashMap() ;		
		param.put("product_id", product_id) ;
		param.put("region_id", region_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("ProductBureauBO",
						"getProductBureauById" ,param)) ;
		
		return result ;
	}
	

	public List findProductBureauByCond(String product_id) throws Exception {
		Map param = getProductBureauKeyMap(product_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("ProductBureauBO",
						"findProductBureauByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductBureauById(String product_id) throws Exception {
		Map param = getProductBureauKeyMap(product_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductBureauBO",
						"deleteProductBureauById" ,param)) ;
		
		return result ;
	}
	
	private Map getProductBureauKeyMap(String product_id){
		Map param = new HashMap() ;
				
		param.put("product_id", product_id) ;
				
		return param ;
	}
}
