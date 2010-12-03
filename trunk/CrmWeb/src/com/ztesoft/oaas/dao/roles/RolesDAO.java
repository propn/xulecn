package com.ztesoft.oaas.dao.roles;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RolesVO;

public interface RolesDAO extends DAO
{

    RolesVO findByPrimaryKey(String prole_id) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
            throws DAOSystemException;
    ArrayList findBySql2(String sql, String[] sqlParams)
    throws DAOSystemException;
    boolean update(String prole_id, RolesVO vo) throws DAOSystemException;

    long delete(String prole_id) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    ArrayList getAllRoles() throws DAOSystemException;
}
