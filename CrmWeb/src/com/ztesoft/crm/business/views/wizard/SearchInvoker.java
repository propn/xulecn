package com.ztesoft.crm.business.views.wizard;

import java.util.Map;



public interface SearchInvoker {

	public boolean invoke(String searchCondition,Map arguments)throws Exception;
}
