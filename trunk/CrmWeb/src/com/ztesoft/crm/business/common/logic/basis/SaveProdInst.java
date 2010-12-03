package com.ztesoft.crm.business.common.logic.basis;

import java.util.Map;

import com.ztesoft.crm.business.common.logic.model.Serv;

public class SaveProdInst {

	public static void execute(Serv serv, Map common) throws Exception {

		//�����Ʒʵ������Ϊ����ֱ�ӷ���
		if(serv==null)
			return ;
		
		/***********�����Ʒ��Ϣ***************/
		SaveServ.perform(serv, common);

		/***********���渽����Ʒ��Ϣ***************/
		SaveServProduct.perform(serv, common);

		/***********������������Ϣ***************/
		SaveServAcct.perform(serv, common);

	}

}