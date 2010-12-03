package com.ztesoft.crm.business.common.sem.steps;

import java.lang.reflect.InvocationTargetException;

import com.ztesoft.crm.business.common.sem.engine.BusiResult;
import com.ztesoft.crm.business.common.sem.kernel.ClassUtils;




public  class BusiActionImplemention implements BusiAction {
	public final static String EXECUTE="execute";
	
	Object comp;

	BusiAction next;

	public BusiAction getNext() {
		return next;
	}

	public void setNext(BusiAction next) {
		this.next = next;
	}
	public  void execute(BusiResult busiResult) throws Exception{
		
		try {
			Object returnObject=ClassUtils.invokeMethod(comp,EXECUTE,null);
			if(returnObject!=null)
				busiResult.add(returnObject);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw e;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	public void process(BusiResult busiResult) throws Exception{
		
		this.execute(busiResult);
		
		if(this.next!=null){
			this.next.process(busiResult);
		}
		
	}
	public Object getComp() {
		return comp;
	}

	public void setComp(Object comp) {
		this.comp = comp;
	}

}
