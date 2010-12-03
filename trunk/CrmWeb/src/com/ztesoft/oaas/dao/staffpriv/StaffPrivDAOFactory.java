package com.ztesoft.oaas.dao.staffpriv;

public class StaffPrivDAOFactory
{
    public static StaffPrivDAO getStaffPrivDAO() {
        return new StaffPrivDAOImpl();
    }
}
