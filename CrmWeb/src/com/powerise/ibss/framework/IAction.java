package com.powerise.ibss.framework;
/**
�˽ӿ���Ϊ�����߼�ʵ����ĸ��࣬��Ϊʵ�ַ�ʽ��
��ֻӵ��һ������ perform(TransRequest)
@author Powerise GD
@version 1.0
 */
public interface IAction {

	/**
	����ʵ�ִ˷�����ɾ���ҵ���߼���ʵ��  
	@param p_trans_request ���ݽṹ��
	@param aDynamicDict
	@return int
	@throws com.powerise.framework.exception.FrameException
	@roseuid 3EBB4BCE0227
	 */
	public int perform(DynamicDict aDynamicDict) throws Exception;
}
