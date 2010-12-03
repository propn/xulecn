package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RnNumberPreVO;


public interface RnNumberPreDAO extends DAO {
    RnNumberPreVO findByPrimaryKey(String pLAN_ID, String pPRODUCT_NO)
        throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    boolean update(String pLAN_ID, String pPRODUCT_NO, RnNumberPreVO vo)
        throws DAOSystemException;

    long delete(String pLAN_ID, String pPRODUCT_NO) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
}
