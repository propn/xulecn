package com.ztesoft.crm.business.common.sem.steps;

import java.lang.reflect.InvocationTargetException;

import com.ztesoft.crm.business.common.sem.engine.BusiResult;


/**
 * 业务动作
 * */
public interface BusiAction {

	/**
	 * 业务动作执行方法
	 * */
	void execute(BusiResult busiResult) throws Exception;
	
	void process(BusiResult busiResult) throws Exception;
}
