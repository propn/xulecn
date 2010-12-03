package com.ztesoft.oaas.dao.staffpost;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.StaffPostVO;

public interface StaffPostDAO extends DAO {

	StaffPostVO findByPrimaryKey(String porg_post_id,String pparty_role_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String porg_post_id, String pparty_role_id,StaffPostVO vo) throws DAOSystemException;

	long delete( String porg_post_id, String pparty_role_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * ����Ա����ʶ��ȡ�����и�λ
     * @param pstaff_id Ա�������˽�ɫ��ʶ
     * @return ָ��Ա��������Ա����λ�б�StaffPostVO���ɵ�ArrayList��
     * @throws DAOSystemException
     */
    ArrayList getStaffPostsByStaff(String pstaff_id) throws DAOSystemException;
    
    long deleteByStaff(String pstaff_id) throws DAOSystemException;

    PageModel getStaffListByPartyAndPost(String partyId, String positionId, int pageIndex, int pageSize) throws DAOSystemException;

}
