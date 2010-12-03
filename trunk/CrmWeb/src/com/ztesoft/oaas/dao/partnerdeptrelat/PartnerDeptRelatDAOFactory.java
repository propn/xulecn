package com.ztesoft.oaas.dao.partnerdeptrelat;

public class PartnerDeptRelatDAOFactory {
	public static PartnerDeptRelatDAO getPartnerDeptRelatDAO(){
		return new PartnerDeptRelatDAOImpl();
	}
}
