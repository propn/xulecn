package com.ztesoft.oaas.dao.rrprovince;

public class RrProvinceDAOFactory
{
    public static RrProvinceDAO getRrProvinceDAO() {
        return new RrProvinceDAOImpl();
    }
}
