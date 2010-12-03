package com.ztesoft.crm.business.accept.entry.bo.components;

import com.ztesoft.crm.business.accept.entry.bo.BusiComponent;
import com.ztesoft.crm.business.common.logic.basis.InitServ;
import com.ztesoft.crm.business.common.logic.model.CommonData;
import com.ztesoft.crm.business.common.logic.model.Serv;



/**
 *  * 调用/VsopWeb/src/com/ztesoft/crm/business/common/logic
 *    目录下面的基础类，进行产品初始化操作
 * */
public class InitInstHandler implements BusiComponent{

	public Object execute() {
		CommonData commondata = CommonData.getData();
		
		InitServ initserv = new InitServ();
		
		//产品初始化
		Serv serv = initserv.execute(commondata.getAttributes());
		
		commondata.getServs().add(serv);	
		return null;
		
	}
	
	
}
