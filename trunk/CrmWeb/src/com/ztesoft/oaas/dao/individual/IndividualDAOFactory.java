package com.ztesoft.oaas.dao.individual;

public class IndividualDAOFactory
{
    public static IndividualDAO getIndividualDAO() {
        return new IndividualDAOImpl();
    }
}
