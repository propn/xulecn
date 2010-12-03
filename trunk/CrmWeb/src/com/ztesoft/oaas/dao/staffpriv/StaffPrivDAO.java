package com.ztesoft.oaas.dao.staffpriv;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.StaffPrivVO;

public interface StaffPrivDAO extends DAO {

	StaffPrivVO findByPrimaryKey(String pstaff_privilege_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pstaff_privilege_id,StaffPrivVO vo) throws DAOSystemException;

	long delete( String pstaff_privilege_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * ��ȡԱ��Ȩ��
     * @param pstaff_id Ա�������˽�ɫ��ʶ
     * @return ������Ȩ���б�StaffPrivVO���ɵ�ArrayList��
     * @throws DAOSystemException
     */
    ArrayList getStaffPrivsByStaff(String pstaff_id) throws DAOSystemException;
    
    String getStaffPrivXMLItemByPartyRoleId(String pstaff_id) throws DAOSystemException;
    
    List getStaffPrivilegeRegionInfo( String privilegeId, String partyRoleId ) throws DAOSystemException;
    
    long deleteByStaff(String pstaff_id) throws DAOSystemException;
}
