package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcNosegAppVO;


public interface RcNosegAppDAO extends DAO {
    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    String appAgain(RcNosegAppVO vo, String state) throws DAOSystemException;

    PageModel qryRcNoSegAppForCheck(String lanId, String state, String segId,
        String segName, int pi, int ps) throws DAOSystemException;
}
