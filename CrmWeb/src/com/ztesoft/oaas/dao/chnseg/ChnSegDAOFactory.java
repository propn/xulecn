package com.ztesoft.oaas.dao.chnseg;

public class ChnSegDAOFactory
{
    public static ChnSegDAO getChnSegDAO() {
        return new ChnSegDAOImpl();
    }
}
