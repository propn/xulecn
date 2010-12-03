package com.ztesoft.crm.business.common.sem;

import com.ztesoft.crm.business.common.sem.engine.BusiEngine;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		
		try {
			BusiEngine.set("HELLO", "TEST");
			BusiEngine.call("salesbuy", "23455");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		//((BusiService)ServiceFactory.getService("salesbuy")).trigger();
		
	}

}
