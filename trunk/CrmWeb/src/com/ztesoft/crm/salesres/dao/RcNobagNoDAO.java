package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;


public interface RcNobagNoDAO extends DAO {
    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    public int getFlag();

    public void setFlag(int flag);

    public String getSQL_SELECT();

    public void setSQL_SELECT(String sql_select);

    public String getSQL_SELECT_COUNT();

    public void setSQL_SELECT_COUNT(String sql_select_count);
}
