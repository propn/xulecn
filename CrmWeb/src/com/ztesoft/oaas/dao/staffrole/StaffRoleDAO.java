package com.ztesoft.oaas.dao.staffrole;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.StaffRoleVO;

public interface StaffRoleDAO extends DAO {

	StaffRoleVO findByPrimaryKey(String party_role_id,String role_id, String region_id, String region_type) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( StaffRoleVO vo) throws DAOSystemException;

	long delete( String party_role_id,String role_id, String region_id, String region_type ) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * 查询员工角色
     * @param pstaff_id
     * @return 员工对应的所有员工角色列表(StaffRoleVO构成的ArrayList)
     * @throws DAOSystemException
     */
    ArrayList getStaffRolesByStaff(String pstaff_id) throws DAOSystemException;
    
    long deleteByStaff(String pstaff_id) throws DAOSystemException;
    
    ArrayList getSimpleStaffRolesByPartyRoleId( String pstaff_id ) throws DAOSystemException;
    
}
