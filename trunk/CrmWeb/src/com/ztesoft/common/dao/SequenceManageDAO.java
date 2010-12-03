package com.ztesoft.common.dao;



/**
 * @Classname     : SequenceManangeDAO
 * @Description   : ��ȡ���еĹ�����ӿ�
 * @Copyright     : 2006 ZTEsoft.
 * @Author        : llh
 * @Create Date   : 2006-3-27
 *
 * @Last Modified :
 * @Modified by   :
 * @Version       : 1.0
*/
public interface SequenceManageDAO {

	/**���ݱ������ֶ�����ȡ����
	 * @param tableCode
	 * @param fieldCode
	 * @return
	 */
	public String getNextSequence(String tableCode,String fieldCode);
	public String getNextSequence(String sequenceCode);
        public String getNextSequenceFormat(String tableCode,
                        String fieldCode,int seqNum);
}
