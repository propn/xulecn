package com.ztesoft.crm.business.common.logic.auto;

import com.ztesoft.crm.business.common.consts.ServConsts;
import com.ztesoft.crm.business.common.logic.model.Serv;

/**
 * @author liupeidawn
 *
 */
public class DefServAccNbr implements AutoProcessor {

	/* 根据主产品的配置信息，为主产品实例自动生成号码, 例如ADSL号码(主号码作为主产品实例基本属性，在基本属性默认中处理)
	 * @see com.ztesoft.crm.business.common.logic.auto.AutoProcessor#execute(com.ztesoft.crm.business.common.logic.auto.ProcessParameter)
	 */
	public void execute(ProcessParameter parameter)throws Exception {
		String servAction = parameter.serv.get(Serv.ACTION_TYPE);
		if(ServConsts.ACTION_TYPE_A.equals(servAction)){//如果为新装 并且号码信息为空  那么需要为主产品实例自动生成号码 通过配置SQL语句实现
				
			}
		}
	

}
