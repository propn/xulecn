package com.ztesoft.vsop.simulate.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;


public class SiProdOffRelService  {
	
	public boolean insertProdOffRel(HashMap ProdOffRel ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdOffRel", ProdOffRel) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffRelBO,
						"insertProdOffRel" ,param)) ;
		return result ;
	}

	
	public boolean updateProdOffRel(HashMap ProdOffRel ) throws Exception {
		Map param = new HashMap() ;
		param.put("ProdOffRel", ProdOffRel) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffRelBO,
						"updateProdOffRel" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchProdOffRelData(String offer_a_id, String offer_z_id, int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("offer_a_id", offer_a_id) ;
		param.put("offer_z_id", offer_z_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffRelBO,
						"searchProdOffRelData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getProdOffRelById(String prod_offer_rel_id) throws Exception {
		Map param = getProdOffRelKeyMap(prod_offer_rel_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffRelBO,
						"getProdOffRelById" ,param)) ;
		
		return result ;
	}
	

	public List findProdOffRelByCond(String prod_offer_rel_id) throws Exception {
		Map param = getProdOffRelKeyMap(prod_offer_rel_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffRelBO,
						"findProdOffRelByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffRelById(String relation_type_id, String offer_a_id, String offer_z_id) throws Exception {
		//Map param = getProdOffRelKeyMap(prod_offer_rel_id) ;
		Map param = getKeyForDelete(relation_type_id, offer_a_id, offer_z_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffRelBO,
						"deleteProdOffRelById" ,param)) ;
		
		return result ;
	}
	
	public boolean deleteProdOffRel(String relation_type_id, String offer_a_id, String offer_z_id) throws Exception {
		Map param = getKeyForDelete(relation_type_id, offer_a_id, offer_z_id);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffRelBO,
						"deleteProdOffRel" ,param)) ;
		
		return result ;
	}
	
	private Map getProdOffRelKeyMap(String prod_offer_rel_id){
		Map param = new HashMap() ;
				
		param.put("prod_offer_rel_id", prod_offer_rel_id) ;
				
		return param ;
	}
	
	private Map getKeyForDelete(String relation_type_id, String offer_a_id, String offer_z_id) throws Exception {
		Map map = new HashMap();
		map.put("relation_type_id", relation_type_id);
		map.put("offer_a_id", offer_a_id);
		map.put("offer_z_id", offer_z_id);
		return map;
	}
	
	/**
	 * 根据销售品id查找销售品名称，供 prodOff.js 调用
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public String getProdOffNameByKey(String prodOffId) throws Exception {
		Map map = new HashMap();
		map.put("prod_offer_id", prodOffId);
		String productName = DataTranslate._String (
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffRelBO,
						"getProdOffNameByKey", map));
		return productName;
	}
	
}
