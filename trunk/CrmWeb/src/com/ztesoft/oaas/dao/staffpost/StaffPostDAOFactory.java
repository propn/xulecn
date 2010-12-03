package com.ztesoft.oaas.dao.staffpost;

public class StaffPostDAOFactory
{
    public static StaffPostDAO getStaffPostDAO() {
        return new StaffPostDAOImpl();
    }
}
