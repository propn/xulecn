package com.ztesoft.common.dao;



/**
 * @Classname     : StaticDataDAOFactory
 * @Description   : ��ȡ����DAO�ĳ��󹤳�
 * @Copyright     : 2006 ZTEsoft.
 * @Author        : llh
 * @Create Date   : 2006-3-27
 *
 * @Last Modified : 
 * @Modified by   : 
 * @Version       : 1.0
*/
public class SeqDAOFactory {
	
	protected SeqDAOFactory()
	{
		
	}

	private static SeqDAOFactory instance = new SeqDAOFactory();
	
	private static SequenceManageDAO sequenceManageDAO = new SequenceManageDAOImpl();

	/**��ȡ����ʵ��
	 * @return
	 */
	public static SeqDAOFactory getInstance() {
		return instance;
	}

	public SequenceManageDAO getSequenceManageDAO()
	{
		return this.sequenceManageDAO;
	}
}
