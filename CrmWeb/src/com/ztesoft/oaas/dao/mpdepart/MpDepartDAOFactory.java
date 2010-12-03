package com.ztesoft.oaas.dao.mpdepart;

public class MpDepartDAOFactory
{
    public static MpDepartDAO getMpDepartDAO() {
        return new MpDepartDAOImpl();
    }
}
