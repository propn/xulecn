package com.ztesoft.oaas.dao.organizationpost;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.OrganizationPostVO;

public interface OrganizationPostDAO extends DAO
{

    OrganizationPostVO findByPrimaryKey(String porgpost_id)
            throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
            throws DAOSystemException;

    boolean update(String porgpost_id, OrganizationPostVO vo)
            throws DAOSystemException;

    long delete(String porgpost_id) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * ��ѯָ����֯��������֯��λʵ��
     * @param org_id ��֯��ʶ
     * @return ��֯��λʵ���б�(OrganizationPostVO��ɵ�ArrayList)
     * @throws DAOSystemException
     */
    ArrayList getOrgPostsByOrganization(String org_id) throws DAOSystemException;
    
    ArrayList queryOrgPosition( String orgId, String postName,String state ) throws DAOSystemException;
    
    long deleteByOrganization(String org_id) throws DAOSystemException;
}
