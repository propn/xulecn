package com.ztesoft.crm.business.common.logic.auto;

import com.ztesoft.crm.business.common.consts.ServConsts;
import com.ztesoft.crm.business.common.logic.model.Serv;

/**
 * @author liupeidawn
 *
 */
public class DefServAccNbr implements AutoProcessor {

	/* ��������Ʒ��������Ϣ��Ϊ����Ʒʵ���Զ����ɺ���, ����ADSL����(��������Ϊ����Ʒʵ���������ԣ��ڻ�������Ĭ���д���)
	 * @see com.ztesoft.crm.business.common.logic.auto.AutoProcessor#execute(com.ztesoft.crm.business.common.logic.auto.ProcessParameter)
	 */
	public void execute(ProcessParameter parameter)throws Exception {
		String servAction = parameter.serv.get(Serv.ACTION_TYPE);
		if(ServConsts.ACTION_TYPE_A.equals(servAction)){//���Ϊ��װ ���Һ�����ϢΪ��  ��ô��ҪΪ����Ʒʵ���Զ����ɺ��� ͨ������SQL���ʵ��
				
			}
		}
	

}
