package com.ztesoft.oaas.dao.partnerinfo;

public class PartnerInfoDAOFactory {
	public static PartnerInfoDAO getPartnerInfoDAO(){
		return new PartnerInfoDAOImpl();
	}
}
