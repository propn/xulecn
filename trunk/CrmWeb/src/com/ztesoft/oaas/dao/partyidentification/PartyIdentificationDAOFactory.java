package com.ztesoft.oaas.dao.partyidentification;

public class PartyIdentificationDAOFactory
{
    public static PartyIdentificationDAO getPartyIdentificationDAO() {
        return new PartyIdentificationDAOImpl();
    }
}
