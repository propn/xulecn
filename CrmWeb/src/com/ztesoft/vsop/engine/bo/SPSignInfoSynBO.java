/**
 * 
 */
package com.ztesoft.vsop.engine.bo;

import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.engine.ErrorCode;
import com.ztesoft.vsop.engine.dao.SPSignInfoSynDao;
import com.ztesoft.vsop.engine.vo.SPSignInfoVO;

/**
 * <pre>
 * Title:SP/CPҵ������ͬ��ҵ���߼�
 * Description: SP/CPҵ������ͬ��ҵ���߼� 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class SPSignInfoSynBO {
	
	SPSignInfoSynDao dao = new SPSignInfoSynDao();
	/**
	 * ִ��ҵ���߼�
	 * @param vo
	 * @param in
	 * @return
	 */
	public Map execute(SPSignInfoVO vo, Map in)throws Exception {
		boolean flag = true;
		//���ͬ������Ϊ��1�� ��Ϊ����
		if ("1".equals(vo.getOPFlag())) {
			List  spSignInfoList = vo.getSPSignInfo();
			dao.addSPSignInfo(vo.getSPSignInfo());
			flag = false;
		}
		
		//���ͬ������Ϊ��2�� ��Ϊ�޸�
		if ("2".equals(vo.getOPFlag())) {
			dao.updateSPSignInfo(vo.getSPSignInfo());
			flag = false;
		}
		
		//���ͬ������Ϊ��3�� ��Ϊɾ��
		if ("3".equals(vo.getOPFlag())) {
			dao.deteteSPSignInfo(vo.getSPSignInfo());
			flag = false;
		}
		//�����������ֲ�����û�ߵ� ˵�����������쳣
		if(flag){
			in.put("resultCode", "1002");
			in.put("resultDesc", ErrorCode.DATA_ERROR);
		}
		return in;

	}
}
