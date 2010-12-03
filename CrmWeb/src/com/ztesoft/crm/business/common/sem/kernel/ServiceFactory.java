package com.ztesoft.crm.business.common.sem.kernel;

public final class ServiceFactory {

	private static ServiceContext serviceContext = null;

	static {
		try {
			serviceContext = new ClassPathXmlServiceContext("com/ztesoft/crm/business/common/sem/xml/services.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getService(String serviceName) {
		if(serviceContext == null ){
			serviceContext = new ClassPathXmlServiceContext("com/ztesoft/crm/business/common/sem/xml/services.xml");
		}
		return serviceContext.getService(serviceName);
	}

}
