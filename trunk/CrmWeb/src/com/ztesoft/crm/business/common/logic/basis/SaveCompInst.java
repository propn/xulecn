package com.ztesoft.crm.business.common.logic.basis;

import java.util.Map;

import com.ztesoft.crm.business.common.logic.model.CompInst;

public class SaveCompInst {

	
	public static void execute(CompInst compInst, Map common){
        if (null == compInst) {
            return;
        }
		
        /***********��������Ʒ��Ϣ***************/
		SaveOfferInst.perform(compInst, common);
		
        /***********��������Ʒ������Ϣ***************/
		SaveOfferInstDetail.perform(compInst, common);
		
	}
}
