package com.ztesoft.oaas.dao.staffrole;

public class StaffRoleDAOFactory
{
    public static StaffRoleDAO getStaffRoleDAO() {
        return new StaffRoleDAOImpl();
    }
}
