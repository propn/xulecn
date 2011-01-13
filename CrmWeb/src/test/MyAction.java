package test;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.DictAction;

public class MyAction extends DictAction{
	
	public String testService(DynamicDict dto) throws Exception {
		return "TEST--";
	}
	
	/**
	 * throws FrameException 控制事务回滚 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String testTransaction(DynamicDict dto) throws Exception {
		boolean controlTransaction = true ;
		new XADAO().iappInsert() ;//如果抛出了异常，此处insert操作将会被回滚
		if(controlTransaction) 
			throw new FrameException("") ;
		return "TEST--";
	}
}
