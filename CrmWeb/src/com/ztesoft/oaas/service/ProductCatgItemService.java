package com.ztesoft.oaas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dict.ServiceList;

public class ProductCatgItemService  {
	/**
	     ��Ҫ�滻λ�� ˵�� ��
	  1. ServiceList.MyService  �滻ΪServiceListע��ķ��� 
	  2. searchProductCatgItemData �ķ����Ĳ�������
	  3. findProductCatgItemByCond(String catalog_item_id) ������Ҫ����ʵ������޸�
	  4. ����Ҫ�ķ��������Ը���ʵ��������вü�
	  5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public String insertProductCatgItem(HashMap ProductCatgItem ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductCatgItem", ProductCatgItem) ;

		String newNodeId = DataTranslate._String(
				ServiceManager.callJavaBeanService("ProductCatgItemBO",
						"insertProductCatgItem" ,param)) ;
		return newNodeId ;
	}

	
	public boolean updateProductCatgItem(HashMap ProductCatgItem ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductCatgItem", ProductCatgItem) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductCatgItemBO",
						"updateProductCatgItem" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProductCatgItemData(String iParam1 , String iParam2 ,String iParam3 , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("iParam1", iParam1) ;
		param.put("iParam2", iParam2) ;
		param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.MyService,
						"searchProductCatgItemData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProductCatgItemById(String catalog_item_id) throws Exception {
		Map param = getProductCatgItemKeyMap(catalog_item_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.MyService,
						"getProductCatgItemById" ,param)) ;
		
		return result ;
	}
	

	public List findProductCatgItemByCond(String catalog_item_id) throws Exception {
		Map param = getProductCatgItemKeyMap(catalog_item_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.MyService,
						"findProductCatgItemByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductCatgItemById(String catalog_item_id) throws Exception {
		Map param = getProductCatgItemKeyMap(catalog_item_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductCatgItemBO",
						"deleteProductCatgItemById" ,param)) ;
		
		return result ;
	}
	
	private Map getProductCatgItemKeyMap(String catalog_item_id){
		Map param = new HashMap() ;
				
		param.put("catalog_item_id", catalog_item_id) ;
				
		return param ;
	}
}
