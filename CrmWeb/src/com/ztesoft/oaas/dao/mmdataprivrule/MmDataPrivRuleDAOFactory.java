package com.ztesoft.oaas.dao.mmdataprivrule;

public class MmDataPrivRuleDAOFactory {
	public static MmDataPrivRuleDAO getMmDataPrivRuleDAO(){
		return new MmDataPrivRuleDAOImpl() ;
	}
}
