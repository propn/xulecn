package com.ztesoft.oaas.dao.rolepriv;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RolePrivVO;

public interface RolePrivDAO extends DAO
{

    RolePrivVO findByPrimaryKey(String pprivilege_id, String prole_id,String pprivilege_type)
            throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
            throws DAOSystemException;

    boolean update(String pprivilege_id, String prole_id, String pprivlege_type,RolePrivVO vo)
            throws DAOSystemException;

    long delete(String pprivilege_id, String prole_id,String pprivilege_type)
            throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    ArrayList getPrivsByRole(String role_id,String privilegeType) throws DAOSystemException;
    ArrayList getRelatingRoleByRole(String role_id) throws DAOSystemException;
}
