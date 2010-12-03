/**
 * 
 */
package com.ztesoft.crm.business.views.wizard;


import java.util.Map;



public class JavaSearchInvoker  implements SearchInvoker {

	public boolean invoke(String searchCondition, Map arguments) throws Exception {
		
	    String className=searchCondition;
	    JavaSearcher searcher;
        ClassLoader loader=Thread.currentThread().getContextClassLoader();
        try {
            Class clazz = loader.loadClass(className);
            searcher= (JavaSearcher) clazz.newInstance();
            
        } catch (Exception e) {
        	e.printStackTrace();
            throw e;
        }
        
        return searcher.search(arguments);
	}

}
