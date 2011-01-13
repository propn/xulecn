package com.ztesoft.oaas.dao.chsalearea;

public class ChSaleAreaDAOFactory
{
    public static ChSaleAreaDAO getChSaleAreaDAO() {
        return new ChSaleAreaDAOImpl();
    }
}
