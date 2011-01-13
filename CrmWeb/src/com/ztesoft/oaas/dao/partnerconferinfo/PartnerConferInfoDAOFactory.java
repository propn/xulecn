package com.ztesoft.oaas.dao.partnerconferinfo;

public class PartnerConferInfoDAOFactory {
	public static PartnerConferInfoDAO getPartnerConferInfoDAO(){
		return new PartnerConferInfoDAOImpl() ;
	}
}
