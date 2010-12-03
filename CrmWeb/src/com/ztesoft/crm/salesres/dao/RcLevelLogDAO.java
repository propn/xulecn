package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcLevelLogVO;


public interface RcLevelLogDAO extends DAO {
    RcLevelLogVO findByPrimaryKey(String pLOG_ID, String pRESOURCE_INSTANCE_ID)
        throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    boolean update(String pLOG_ID, String pRESOURCE_INSTANCE_ID, RcLevelLogVO vo)
        throws DAOSystemException;

    long delete(String pLOG_ID, String pRESOURCE_INSTANCE_ID)
        throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    public int insertByBatch(List list) throws DAOSystemException;

    public void setFlag(int flag);
}
