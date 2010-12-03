package com.ztesoft.oaas.dao.resczone;

public class RescZoneDAOFactory
{
    public static RescZoneDAO getRescZoneDAO() {
        return new RescZoneDAOImpl();
    }
}
