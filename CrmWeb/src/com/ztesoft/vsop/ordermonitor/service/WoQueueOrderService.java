package com.ztesoft.vsop.ordermonitor.service;


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


public class WoQueueOrderService  {
	
	public Map getWoQueueOrderById(String ne_order_id,String query_type) throws Exception {
		Map param = new HashMap();
		param.put("ne_order_id", ne_order_id);
		if("1".equals(query_type)){
			Map result = DataTranslate._Map(
					ServiceManager.callJavaBeanService(ServiceList.WoQueueOrderHisBO,
							"getWoQueueOrderHisById" ,param)) ;
			
			return result ;
		}
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.WoQueueOrderBO,
						"getWoQueueOrderById" ,param)) ;
		
		return result ;
	}
	public boolean updateWoQueueOrder(HashMap WoQueueOrder ) throws Exception {
		Map param = new HashMap() ;
		param.put("WoQueueOrder", WoQueueOrder) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.WoQueueOrderBO,
						"updateWoQueueOrder" ,param)) ;
		
		return result ;
	}
}
