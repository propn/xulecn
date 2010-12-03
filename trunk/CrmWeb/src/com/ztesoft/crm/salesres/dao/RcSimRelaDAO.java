package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;


public interface RcSimRelaDAO extends DAO {
    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    public void deleteByAddiIccid(String addiIccid);

    public void deleteByMainIccid(String mainIccid);

    public String getMainIccid(String addiIccid);

    public String getAddiIccid(String mainIccid);

    /**
     * ÅúÁ¿²åÈëË«Ð¾¿¨
     * @param list
     * @return
     */
    public int insertBatch(List list);
}
