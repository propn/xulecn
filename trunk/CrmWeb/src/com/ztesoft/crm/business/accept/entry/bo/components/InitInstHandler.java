package com.ztesoft.crm.business.accept.entry.bo.components;

import com.ztesoft.crm.business.accept.entry.bo.BusiComponent;
import com.ztesoft.crm.business.common.logic.basis.InitServ;
import com.ztesoft.crm.business.common.logic.model.CommonData;
import com.ztesoft.crm.business.common.logic.model.Serv;



/**
 *  * ����/VsopWeb/src/com/ztesoft/crm/business/common/logic
 *    Ŀ¼����Ļ����࣬���в�Ʒ��ʼ������
 * */
public class InitInstHandler implements BusiComponent{

	public Object execute() {
		CommonData commondata = CommonData.getData();
		
		InitServ initserv = new InitServ();
		
		//��Ʒ��ʼ��
		Serv serv = initserv.execute(commondata.getAttributes());
		
		commondata.getServs().add(serv);	
		return null;
		
	}
	
	
}
