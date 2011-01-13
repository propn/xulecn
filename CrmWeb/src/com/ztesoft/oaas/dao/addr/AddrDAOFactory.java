package com.ztesoft.oaas.dao.addr;

public class AddrDAOFactory
{
    public static AddrDAO getAddrDAO() {
        return new AddrDAOImpl();
    }
}
