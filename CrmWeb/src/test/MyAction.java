package test;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.DictAction;

public class MyAction extends DictAction{
	
	public String testService(DynamicDict dto) throws Exception {
		return "TEST--";
	}
	
	/**
	 * throws FrameException ��������ع� 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String testTransaction(DynamicDict dto) throws Exception {
		boolean controlTransaction = true ;
		new XADAO().iappInsert() ;//����׳����쳣���˴�insert�������ᱻ�ع�
		if(controlTransaction) 
			throw new FrameException("") ;
		return "TEST--";
	}
}
