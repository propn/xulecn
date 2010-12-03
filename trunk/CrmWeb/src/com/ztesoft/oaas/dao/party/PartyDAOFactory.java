package com.ztesoft.oaas.dao.party;

public class PartyDAOFactory
{
    public static PartyDAO getPartyDAO() {
        return new PartyDAOImpl();
    }
}
