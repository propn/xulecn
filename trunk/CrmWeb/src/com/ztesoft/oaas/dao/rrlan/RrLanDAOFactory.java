package com.ztesoft.oaas.dao.rrlan;

public class RrLanDAOFactory
{
    public static RrLanDAO getRrLanDAO() {
        return new RrLanDAOImpl();
    }
}
