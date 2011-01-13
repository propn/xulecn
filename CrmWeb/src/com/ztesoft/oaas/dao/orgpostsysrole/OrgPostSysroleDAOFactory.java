package com.ztesoft.oaas.dao.orgpostsysrole;

public class OrgPostSysroleDAOFactory
{
    public static OrgPostSysroleDAO getOrgPostSysroleDAO() {
        return new OrgPostSysroleDAOImpl();
    }
}
