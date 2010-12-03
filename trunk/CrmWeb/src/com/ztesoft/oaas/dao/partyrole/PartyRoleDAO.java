package com.ztesoft.oaas.dao.partyrole;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartyRoleVO;

public interface PartyRoleDAO extends DAO {

	PartyRoleVO findByPrimaryKey(String pparty_role_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pparty_role_id,PartyRoleVO vo) throws DAOSystemException;

	long delete( String pparty_role_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * 获取指定参与人的所有参与人角色
     * @param pparty_id
     * @return 指定参与人的所有参与人角色列表（PartyRoleVO构成的ArrayList）
     * @throws DAOSystemException
     */
    ArrayList getPartyRolesByParty(String pparty_id) throws DAOSystemException;
    
    long deleteByParty(String pparty_id) throws DAOSystemException;
    
    void insertStaffPassLog(String partyRoleId, String password ) throws DAOSystemException ;
}
