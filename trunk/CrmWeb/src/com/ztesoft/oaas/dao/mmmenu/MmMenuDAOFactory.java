package com.ztesoft.oaas.dao.mmmenu;

public class MmMenuDAOFactory
{
    public static MmMenuDAO getMmMenuDAO() {
        return new MmMenuDAOImpl();
    }
}
