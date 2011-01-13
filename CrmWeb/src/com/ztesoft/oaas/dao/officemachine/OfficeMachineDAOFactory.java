package com.ztesoft.oaas.dao.officemachine;

public class OfficeMachineDAOFactory
{
    public static OfficeMachineDAO getOfficeMachineDAO() {
        return new OfficeMachineDAOImpl();
    }
}
