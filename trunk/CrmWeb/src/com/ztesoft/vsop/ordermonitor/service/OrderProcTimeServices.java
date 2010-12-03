package com.ztesoft.vsop.ordermonitor.service;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;

public class OrderProcTimeServices {
	
	public PageModel searchOrderProcTime(String acc_nbr,
			String begin_time,String finish_time,
			String product_ids,String system_code,
			String vsop_n_time,String platform_n_time,
			int pageIndex , int pageSize)throws Exception{
		Map param = new HashMap() ;
		param.put("acc_nbr", acc_nbr) ;
		param.put("begin_time", begin_time) ;
		param.put("finish_time", finish_time) ;
		param.put("product_ids", product_ids) ;
		param.put("system_code", system_code) ;
		param.put("vsop_n_time", vsop_n_time) ;
		param.put("platform_n_time", platform_n_time) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		PageModel ret = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.OrderProcTimeBo,
						"searchOrderProcTime" ,param)) ;
		return ret;
	}
	
}
