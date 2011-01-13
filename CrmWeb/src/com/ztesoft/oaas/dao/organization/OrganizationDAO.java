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
     * 获取所有组织列表，按path_code排序
     * @return 所有组织(OrganizationVO)构成的ArrayList
     * @throws DAOSystemException
     */
    ArrayList getAllOrganizations() throws DAOSystemException;

    /**
     * 获取所有计费组织列表，按path_code排序
     * @return 所有计费组织(OrganizationVO)构成的ArrayList
     * @throws DAOSystemException
     */
    ArrayList getAllTelecomOrganizations() throws DAOSystemException;

    /**
     * 获取所有合作伙伴组织列表，按path_code排序
     * @return 所有合作伙伴组织(OrganizationVO)构成的ArrayList
     * @throws DAOSystemException
     */
    ArrayList getAllPartnerOrganizations() throws DAOSystemException;

    /**
     * 获取所有对等运营商组织列表，按path_code排序
     * @return 所有对等运营商组织(OrganizationVO)构成的ArrayList
     * @throws DAOSystemException
     */
    ArrayList getAllCompetitorOrganizations() throws DAOSystemException;
    
    public ArrayList getTelecomOrganizationByCond(String orgIds, String orgType) throws DAOSystemException ;
    
    ArrayList getTelecomOrganizationListByParentIdWithPrivFlag( String parentId, String privFlag ) throws DAOSystemException;
}
