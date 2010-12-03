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


public class BatchImportRelationService  {
	
	
	
	public PageModel searchBatchImportRelationData(String operator , String batch ,String state , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("operator", operator) ;
		param.put("batch", batch) ;
		param.put("state", state) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.BatchImportRelationBo,
						"searchBatchImportRelationData" ,param)) ;
		
		return result ;
	}
	
}
