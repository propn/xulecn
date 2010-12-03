package com.ztesoft.oaas.dao.raroad;

public class RaRoadDAOFactory
{
    public static RaRoadDAO getRaRoadDAO() {
        return new RaRoadDAOImpl();
    }
}
