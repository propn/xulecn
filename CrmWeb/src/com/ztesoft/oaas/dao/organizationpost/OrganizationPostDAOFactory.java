package com.ztesoft.oaas.dao.organizationpost;

public class OrganizationPostDAOFactory
{
    public static OrganizationPostDAO getOrganizationPostDAO() {
        return new OrganizationPostDAOImpl();
    }
}
