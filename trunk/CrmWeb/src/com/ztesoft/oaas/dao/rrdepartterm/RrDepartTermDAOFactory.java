package com.ztesoft.oaas.dao.rrdepartterm;

public class RrDepartTermDAOFactory
{
    public static RrDepartTermDAO getRrDepartTermDAO() {
        return new RrDepartTermDAOImpl();
    }
}
