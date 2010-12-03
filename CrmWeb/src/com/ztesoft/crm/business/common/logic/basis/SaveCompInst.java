package com.ztesoft.crm.business.common.logic.basis;

import java.util.Map;

import com.ztesoft.crm.business.common.logic.model.CompInst;

public class SaveCompInst {

	
	public static void execute(CompInst compInst, Map common){
        if (null == compInst) {
            return;
        }
		
        /***********保存销售品信息***************/
		SaveOfferInst.perform(compInst, common);
		
        /***********保存销售品构成信息***************/
		SaveOfferInstDetail.perform(compInst, common);
		
	}
}
