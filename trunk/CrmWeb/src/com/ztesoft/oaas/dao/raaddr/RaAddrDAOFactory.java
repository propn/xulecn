package com.ztesoft.oaas.dao.raaddr;

public class RaAddrDAOFactory
{
    public static RaAddrDAO getRaAddrDAO() {
        return new RaAddrDAOImpl();
    }
}
