package com.ztesoft.oaas.dao.region;

public class RegionDAOFactory
{
    public static RegionDAO getRegionDAO() {
        return new RegionDAOImpl(); 
    }
}
