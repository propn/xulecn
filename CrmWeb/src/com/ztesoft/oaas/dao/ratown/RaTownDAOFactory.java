package com.ztesoft.oaas.dao.ratown;

public class RaTownDAOFactory
{
    public static RaTownDAO getRaTownDAO() {
        return new RaTownDAOImpl();
    }
}
