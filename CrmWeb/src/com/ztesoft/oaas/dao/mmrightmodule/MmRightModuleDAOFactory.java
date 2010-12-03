package com.ztesoft.oaas.dao.mmrightmodule;

public class MmRightModuleDAOFactory
{
    public static MmRightModuleDAO getMmRightModuleDAO() {
        return new MmRightModuleDAOImpl();
    }
}
