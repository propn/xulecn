package com.ztesoft.crm.business.common.sem;
import com.ztesoft.crm.business.common.sem.engine.BusiEngine;



public class Test2 {

	
	public void execute(){
		
		System.out.println("----------------------2"+BusiEngine.get(BusiEngine.REQUEST));
		
		System.out.println("----------------------2"+BusiEngine.get("HELLO"));
		
		
	}
	
}
