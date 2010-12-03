package com.ztesoft.oaas.dao.priv;

public class PrivDAOFactory
{
    public static PrivDAO getPrivDAO() {
        return new PrivDAOImpl();
    }
}
