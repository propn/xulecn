package com.ztesoft.oaas.dao.rrtownrelation;

public class RrTownRelationDAOFactory
{
    public static RrTownRelationDAO getRrTownRelationDAO() {
        return new RrTownRelationDAOImpl();
    }
}
