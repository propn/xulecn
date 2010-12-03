package com.ztesoft.oaas.dao.areacode;

public class AreaCodeDAOFactory
{
    public static AreaCodeDAO getAreaCodeDAO() {
        return new AreaCodeDAOImpl();
    }
}
