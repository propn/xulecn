package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;


public interface RcNosegApplogDAO extends DAO {
    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    PageModel qryRcNoSegAppLog(String segId, int pi, int ps)
        throws DAOSystemException;
}
