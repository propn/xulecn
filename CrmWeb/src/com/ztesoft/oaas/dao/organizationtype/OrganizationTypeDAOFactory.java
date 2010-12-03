package com.ztesoft.oaas.dao.organizationtype;

public class OrganizationTypeDAOFactory
{
    public static OrganizationTypeDAO getOrganizationTypeDAO() {
        return new OrganizationTypeDAOImpl();
    }
}
