package com.ztesoft.oaas.dao.priv;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Set;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PrivVO;

public interface PrivDAO extends DAO
{

    PrivVO findByPrimaryKey(String pprivilege_id) throws DAOSystemException;
    ArrayList findBySqlNoFilter( String sql, String[] sqlParams ) throws DAOSystemException;
    ArrayList findBySql(String sql, String[] sqlParams)
            throws DAOSystemException;

    boolean update(String pprivilege_id, PrivVO vo) throws DAOSystemException;

    long delete(String pprivilege_id) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * 获取所有权限列表，按path_code排序
     * @return 所有权限(PrivVO)构成的ArrayList
     * @throws DAOSystemException
     */
    ArrayList getAllPrivs() throws DAOSystemException;
    ArrayList getDirectSubNodes(String idParent) throws DAOSystemException;
    ArrayList getPrivilegeListWithRegionRelatByName( String privilegeName, Set privilegeSet ) throws DAOSystemException;
    ArrayList getPrivilegeListWithRegionRelatByParentId( String parentId, Set privilegeSet ) throws DAOSystemException;
    ArrayList getStaffPrivs( String partyRoleId ) throws DAOSystemException;
}
