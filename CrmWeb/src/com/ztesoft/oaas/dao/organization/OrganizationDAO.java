package com.ztesoft.oaas.dao.organization;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.OrganizationVO;

public interface OrganizationDAO extends DAO
{

	ArrayList getTelecomOrganizationListByParentId( String parentId ) throws DAOSystemException ;
	
    OrganizationVO findByPrimaryKey(String pparty_id) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
            throws DAOSystemException;

    boolean update( VO vo ) throws DAOSystemException;

    boolean update(String pparty_id, OrganizationVO vo)
            throws DAOSystemException;

    long delete(String pparty_id) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * ��ȡ������֯�б���path_code����
     * @return ������֯(OrganizationVO)���ɵ�ArrayList
     * @throws DAOSystemException
     */
    ArrayList getAllOrganizations() throws DAOSystemException;

    /**
     * ��ȡ���мƷ���֯�б���path_code����
     * @return ���мƷ���֯(OrganizationVO)���ɵ�ArrayList
     * @throws DAOSystemException
     */
    ArrayList getAllTelecomOrganizations() throws DAOSystemException;

    /**
     * ��ȡ���к��������֯�б���path_code����
     * @return ���к��������֯(OrganizationVO)���ɵ�ArrayList
     * @throws DAOSystemException
     */
    ArrayList getAllPartnerOrganizations() throws DAOSystemException;

    /**
     * ��ȡ���жԵ���Ӫ����֯�б���path_code����
     * @return ���жԵ���Ӫ����֯(OrganizationVO)���ɵ�ArrayList
     * @throws DAOSystemException
     */
    ArrayList getAllCompetitorOrganizations() throws DAOSystemException;
    
    public ArrayList getTelecomOrganizationByCond(String orgIds, String orgType) throws DAOSystemException ;
    
    ArrayList getTelecomOrganizationListByParentIdWithPrivFlag( String parentId, String privFlag ) throws DAOSystemException;
}
