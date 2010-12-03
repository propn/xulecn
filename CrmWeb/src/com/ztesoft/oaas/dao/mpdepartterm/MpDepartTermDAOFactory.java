package com.ztesoft.oaas.dao.mpdepartterm;

public class MpDepartTermDAOFactory
{
    public static MpDepartTermDAO getMpDepartTermDAO() {
        return new MpDepartTermDAOImpl();
    }
}
