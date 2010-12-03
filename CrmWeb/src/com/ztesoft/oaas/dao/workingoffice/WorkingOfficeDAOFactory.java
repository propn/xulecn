package com.ztesoft.oaas.dao.workingoffice;

public class WorkingOfficeDAOFactory
{
    public static WorkingOfficeDAO getWorkingOfficeDAO() {
        return new WorkingOfficeDAOImpl();
    }
}
