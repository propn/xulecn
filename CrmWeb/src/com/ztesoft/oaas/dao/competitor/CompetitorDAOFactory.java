package com.ztesoft.oaas.dao.competitor;

public class CompetitorDAOFactory
{
    public static CompetitorDAO getCompetitorDAO() {
        return new CompetitorDAOImpl();
    }
}
