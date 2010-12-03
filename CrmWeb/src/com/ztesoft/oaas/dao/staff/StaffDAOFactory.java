package com.ztesoft.oaas.dao.staff;

public class StaffDAOFactory
{
    public static StaffDAO getStaffDAO() {
        return new StaffDAOImpl();
    }
}
