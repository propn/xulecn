package com.ztesoft.oaas.dao.partnerhistory;

public class PartnerHistoryDAOFactory {
	public static PartnerHistoryDAO getPartnerHistoryDAO(){
		return new PartnerHistoryDAOImpl();
	}
}
