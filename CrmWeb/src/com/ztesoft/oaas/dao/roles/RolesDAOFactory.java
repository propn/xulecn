package com.ztesoft.oaas.dao.roles;

public class RolesDAOFactory
{
    public static RolesDAO getRolesDAO() {
        return new RolesDAOImpl();
    }
}
