package com.ztesoft.oaas.dao.rolepriv;

public class RolePrivDAOFactory
{
    public static RolePrivDAO getRolePrivDAO() {
        return new RolePrivDAOImpl();
    }
}
