package com.ztesoft.oaas.dao.position;

public class PositionDAOFactory
{
    public static PositionDAO getPositionDAO() {
        return new PositionDAOImpl();
    }
}
