package com.ztesoft.oaas.dao.chsalecomm;

public class ChSaleCommDAOFactory
{
    public static ChSaleCommDAO getChSaleCommDAO() {
        return new ChSaleCommDAOImpl();
    }
}
