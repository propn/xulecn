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


public class WoOrderCmdLogService  {

	public PageModel searchWoOrderCmdLogData(String ne_order_id ,
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("ne_order_id", ne_order_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoOrderCmdLogBO,
						"searchWoOrderCmdLogData" ,param)) ;
		
		return result ;
	}

}
