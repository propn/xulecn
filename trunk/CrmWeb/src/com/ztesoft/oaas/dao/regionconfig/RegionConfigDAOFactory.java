package com.ztesoft.oaas.dao.regionconfig;

public class RegionConfigDAOFactory
{
    public static RegionConfigDAO getRegionConfigDAO() {
        return new RegionConfigDAOImpl();
    }
}
