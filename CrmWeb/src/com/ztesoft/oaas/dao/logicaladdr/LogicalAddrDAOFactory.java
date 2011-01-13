package com.ztesoft.oaas.dao.logicaladdr;

public class LogicalAddrDAOFactory
{
    public static LogicalAddrDAO getLogicalAddrDAO() {
        return new LogicalAddrDAOImpl();
    }
}
