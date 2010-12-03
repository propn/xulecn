package com.ztesoft.oaas.dao.mmmenu;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.MmMenuVO;

public interface MmMenuDAO extends DAO
{

    MmMenuVO findByPrimaryKey(String pmenu_id) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
            throws DAOSystemException;

    boolean update(String pmenu_id, MmMenuVO vo) throws DAOSystemException;

    long delete(String pmenu_id) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * 获取所有菜单列表，按path_code排序
     * @return 所有菜单(MmMenuVO)构成的ArrayList
     * @throws DAOSystemException
     */
    ArrayList getAllMmMenus() throws DAOSystemException;

    /**
     * 获取权限列表对应的所有菜单，按path_code排序
     * @param privset 权限列表，格式为"privId1, privId2, ..."
     * @return 指定权限集合对应的所有菜单(MmMenuVO)构成的ArrayList
     * @throws DAOSystemException
     */
    public ArrayList getMmMenusByPrivSet(String privset,String parentMenuId,String staffCode) throws DAOSystemException;
}
