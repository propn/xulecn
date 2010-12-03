package com.ztesoft.oaas.dao.partyrole;

public class PartyRoleDAOFactory
{
    public static PartyRoleDAO getPartyRoleDAO() {
        return new PartyRoleDAOImpl();
    }
}
