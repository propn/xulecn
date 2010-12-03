package com.ztesoft.crm.business.common.logic.basis;

import java.util.Map;

import com.ztesoft.crm.business.common.logic.model.Serv;

public class SaveProdInst {

	public static void execute(Serv serv, Map common) throws Exception {

		//如果产品实例数据为空则直接返回
		if(serv==null)
			return ;
		
		/***********保存产品信息***************/
		SaveServ.perform(serv, common);

		/***********保存附属产品信息***************/
		SaveServProduct.perform(serv, common);

		/***********保存帐务定制信息***************/
		SaveServAcct.perform(serv, common);

	}

}