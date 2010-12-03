package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.StaffVO;



public interface StaffDAO extends DAO {
    StaffVO findByPrimaryKey(String pparty_role_id) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    boolean update(String pparty_role_id, StaffVO vo) throws DAOSystemException;

    long delete(String pparty_role_id) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    public void setFlag(int flag);

    //      µç×Ó·¢Æ±
    String getOrgPathCode(String sql) throws DAOSystemException;

    void setQueryFlag(int flag, String sql, String cont);

    boolean isFinacialStaff(String operId);
}
