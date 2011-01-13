package com.ztesoft.oaas.dao.racity;

public class RaCityDAOFactory
{
    public static RaCityDAO getRaCityDAO() {
        return new RaCityDAOImpl();
    }
}
