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
     * ��ȡ���в˵��б���path_code����
     * @return ���в˵�(MmMenuVO)���ɵ�ArrayList
     * @throws DAOSystemException
     */
    ArrayList getAllMmMenus() throws DAOSystemException;

    /**
     * ��ȡȨ���б��Ӧ�����в˵�����path_code����
     * @param privset Ȩ���б���ʽΪ"privId1, privId2, ..."
     * @return ָ��Ȩ�޼��϶�Ӧ�����в˵�(MmMenuVO)���ɵ�ArrayList
     * @throws DAOSystemException
     */
    public ArrayList getMmMenusByPrivSet(String privset,String parentMenuId,String staffCode) throws DAOSystemException;
}
