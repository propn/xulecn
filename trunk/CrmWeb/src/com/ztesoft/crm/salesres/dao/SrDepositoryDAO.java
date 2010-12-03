package com.ztesoft.crm.salesres.dao;

import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;

public interface SrDepositoryDAO  extends DAO{
    public int update(String sql,List args)throws DAOSystemException;
}
