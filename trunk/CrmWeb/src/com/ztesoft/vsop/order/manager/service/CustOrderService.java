package com.ztesoft.vsop.order.manager.service;


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


public class CustOrderService  {
	/**
	     ��Ҫ�滻λ�� ˵�� ��
	  1. ServiceList.MyService  �滻ΪServiceListע��ķ��� 
	  2. searchCustOrderData �ķ����Ĳ�������
	  3. findCustOrderByCond(String cust_order_id) ������Ҫ����ʵ������޸�
	  4. ����Ҫ�ķ��������Ը���ʵ��������вü�
	  5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	
	
	public PageModel searchCustOrderData(String lan_id , String product_id ,String acc_nbr , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("lan_id", lan_id) ;
		param.put("product_id", product_id) ;
		param.put("acc_nbr", acc_nbr) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("CustOrderBO",
						"searchCustOrderData" ,param)) ;
		
		return result ;
	}
	
	public PageModel searchCustOrderHisData(String lan_id , String product_id ,String acc_nbr , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("lan_id", lan_id) ;
		param.put("product_id", product_id) ;
		param.put("acc_nbr", acc_nbr) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("CustOrderBO",
						"searchCustOrderHisData" ,param)) ;
		
		return result ;
	}
	


	public List findCustOrderByCond(String cust_order_id) throws Exception {
		Map param = getCustOrderKeyMap(cust_order_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("CustOrderBO",
						"findCustOrderByCond" ,param)) ;
		
		return result ;
	}
	
	
	
	private Map getCustOrderKeyMap(String cust_order_id){
		Map param = new HashMap() ;
				
		param.put("cust_order_id", cust_order_id) ;
				
		return param ;
	}
	
	public List getOrderItemList(String cust_order_id,String type) throws Exception{
		List result=null;
		Map param = new HashMap() ;
		param.put("cust_order_id", cust_order_id) ;
        if("0".equals(type)){
		result = DataTranslate._List(
				ServiceManager.callJavaBeanService("CustOrderBO",
						"getOrderItemList" ,param));
		}
        if("1".equals(type)){
    		result = DataTranslate._List(
    				ServiceManager.callJavaBeanService("CustOrderBO",
    						"getOrderItemHisList" ,param));
    		}
		
		return result ;
	}
	
	public List getOrderItemHisList(String cust_order_id) throws Exception{
		Map param = new HashMap() ;
		param.put("cust_order_id", cust_order_id) ;

		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("CustOrderBO",
						"getOrderItemHisList" ,param));
		
		return result ;
	}
	
	/**
	 * 
	 * @param out_order_id
	 * @return
	 */
	public List getPfOrder(String out_order_id) throws Exception{
		if(null == out_order_id || "".equals(out_order_id)) return null;
		Map param = new HashMap() ;
		param.put("out_order_id", out_order_id) ;
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("CustOrderBO",
						"getPfOrder" ,param));
		return result ;
	}
	
	
}
