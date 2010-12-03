package com.ztesoft.crm.business.common.sem.engine;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.crm.business.common.sem.kernel.ServiceFactory;
import com.ztesoft.crm.business.common.sem.services.BusiService;

public  class BusiEngine {

	public final static String REQUEST="request";
	
	private static ThreadLocal context=new ThreadLocal();
	
	
	
	static{
		
		
	}
	
	public static BusiResult call(String serviceName, Object request) throws Exception {

		BusiResult busiResult=new BusiResult();
		
		if(serviceName==null||"".equals(serviceName))
				return null;//Å×³öÒì³£
		
		initlize(request);
		
		BusiService busiService = (BusiService) ServiceFactory
				.getService(serviceName);
		busiService.trigger(busiResult);
		
		return busiResult;
	}
	private static void initlize(Object object){
		
			set(REQUEST,object);
			
	}

	public static Object get(String name) {

		Map dataset = (Map) context.get();

		return dataset.get(name);
	}

	public static void set(String name, Object object) {
		
		if(context.get()==null){
			Map map = new HashMap();
			if(object!=null){
				map.put(name, object);
			}
			context.set(map);
		}else{
			if(object!=null){
				((Map)context.get()).put(name, object);
			}
		}
	}
	
}
