package com.ztesoft.crm.business.common.sem.steps;

import java.lang.reflect.InvocationTargetException;

import com.ztesoft.crm.business.common.sem.engine.BusiResult;


/**
 * ҵ����
 * */
public interface BusiAction {

	/**
	 * ҵ����ִ�з���
	 * */
	void execute(BusiResult busiResult) throws Exception;
	
	void process(BusiResult busiResult) throws Exception;
}
