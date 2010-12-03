package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.NochangelogVO;


public interface NochangelogDAO extends DAO {
    NochangelogVO findByPrimaryKey(String pLOGCODE,
        String pRESOURCE_INSTANCE_CODE) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    //boolean update( String pLOGCODE, String pRESOURCE_INSTANCE_CODE,NochangelogVO vo) throws DAOSystemException;

    //long delete( String pLOGCODE, String pRESOURCE_INSTANCE_CODE) throws DAOSystemException;
    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
}
