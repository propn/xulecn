package com.ztesoft.oaas.dao.organization;

public class OrganizationDAOFactory
{
    public static OrganizationDAO getOrganizationDAO() {
        return new OrganizationDAOImpl();
    }
}
