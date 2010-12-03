package com.ztesoft.oaas.dao.rrbusiness;

public class RrBusinessDAOFactory
{
    public static RrBusinessDAO getRrBusinessDAO() {
        return new RrBusinessDAOImpl();
    }
}
