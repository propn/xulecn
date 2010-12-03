package com.ztesoft.crm.business.common.sem.services;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.crm.business.common.sem.engine.BusiResult;
import com.ztesoft.crm.business.common.sem.steps.BusiAction;

public class BusiServiceImplemention implements BusiService {

	public List steps=new ArrayList();
	
	public BusiAction firstAction;
	
	public void trigger(BusiResult busiResult) throws Exception {
		
		
		//需要做异常处理
		setFirstAction();
		
		
		firstAction.process(busiResult);
	}

	public void setFirstAction(){
		
		if(steps!=null&&!steps.isEmpty()){
			
			firstAction= (BusiAction)steps.get(0);
		}
	}
	public void setSteps(List steps) {
		this.steps = steps;
	}



}
