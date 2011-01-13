package com.ztesoft.oaas.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;
import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;

public class ProductCatgService  {
	
	/**
	 * ��ȡ����TreeList�����XML��ʽ��֯��
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList�����xml�ַ���
	 */
	public String getRootProductCatgList() throws Exception{
		return DataTranslate._String(
				ServiceManager.callJavaBeanService("ProductCatgBO", 
						"getRootProductCatgList", null));
	}

    /**
     * ��ȡ����TreeList�����XML��ʽ������֯��
     * 
     * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList�����xml�ַ���
     */
	public String getTelecomProductCatgListByParentId(String catalogId,String catalogItemId) throws Exception{
		Map param = new HashMap();
		//	catalogId,parentCatalogItemId
		param.put("catalogId", catalogId);
		param.put("catalogItemId", catalogItemId);
		return DataTranslate._String(
				ServiceManager.callJavaBeanService("ProductCatgBO",
						"getTelecomProductCatgListByParentId" ,param)) ;
	}
	
	//���productcatg��ϸ��Ϣ
	public Map getDetailProductCatg(String catalog_id,String parent_catalog_item_id,String catalog_item_id) throws Exception {
		Map param = new HashMap();
		param.put("catalog_id", catalog_id);
		param.put("parent_catalog_item_id", parent_catalog_item_id);
		param.put("catalog_item_id", catalog_item_id);
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("ProductCatgBO",
						"getDetailProductCatg" ,param)) ;
		
		return result ;
	}
	
	public PageModel searchProductCatgItemData(String catalog_item_id ,String catalog_type,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("catalog_item_id", catalog_item_id) ;
		param.put("catalog_type", catalog_type) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductCatgBO",
						"searchProductCatgItemData" ,param)) ;
		
		return result ;
	}
	
	/**
	     ��Ҫ�滻λ�� ˵�� ��
	  1. ServiceList.MyService  �滻ΪServiceListע��ķ��� 
	  2. searchProductCatgData �ķ����Ĳ�������
	  3. findProductCatgByCond(String catalog_id) ������Ҫ����ʵ������޸�
	  4. ����Ҫ�ķ��������Ը���ʵ��������вü�
	  5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertProductCatg(HashMap ProductCatg ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductCatg", ProductCatg) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductCatgBO",
						"insertProductCatg" ,param)) ;
		return result ;
	}

	
	public boolean updateProductCatg(HashMap ProductCatg ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProductCatg", ProductCatg) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.MyService,
						"updateProductCatg" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProductCatgData(String iParam1 , String iParam2 ,String iParam3 , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("iParam1", iParam1) ;
		param.put("iParam2", iParam2) ;
		param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("ProductCatgBO",
						"searchProductCatgData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProductCatgById(String catalog_id) throws Exception {
		Map param = getProductCatgKeyMap(catalog_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("ProductCatgBO",
						"getProductCatgById" ,param)) ;
		
		return result ;
	}
	

	public List findProductCatgByCond(String catalog_id) throws Exception {
		Map param = getProductCatgKeyMap(catalog_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("ProductCatgBO",
						"findProductCatgByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProductCatgById(String catalog_id) throws Exception {
		Map param = getProductCatgKeyMap(catalog_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("ProductCatgBO",
						"deleteProductCatgById" ,param)) ;
		
		return result ;
	}
	     
	private Map getProductCatgKeyMap(String catalog_id){
		Map param = new HashMap() ;
				
		param.put("catalog_id", catalog_id) ;
				
		return param ;
	}
}
