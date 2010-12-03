package com.ztesoft.oaas.dao.partner;

public class PartnerDAOFactory
{
    public static PartnerDAO getPartnerDAO() {
        return new PartnerDAOImpl();
    }
}
