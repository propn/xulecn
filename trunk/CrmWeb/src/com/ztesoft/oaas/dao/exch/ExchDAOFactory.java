package com.ztesoft.oaas.dao.exch;

public class ExchDAOFactory
{
    public static ExchDAO getExchDAO() {
        return new ExchDAOImpl();
    }
}
