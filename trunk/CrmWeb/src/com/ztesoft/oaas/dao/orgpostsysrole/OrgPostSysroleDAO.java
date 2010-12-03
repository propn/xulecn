package com.ztesoft.oaas.dao.orgpostsysrole;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.OrgPostSysroleVO;

public interface OrgPostSysroleDAO extends DAO {

	OrgPostSysroleVO findByPrimaryKey(String porg_post_role_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String porg_post_role_id,OrgPostSysroleVO vo) throws DAOSystemException;

	long delete( String porg_post_role_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * ������֯��λ��ʶ��ѯ�����н�ɫ
     * @param org_pos_id
     * @return ָ����֯��λ��Ӧ�����н�ɫ�б�RolesVO���ɵ�ArrayList��
     * @throws DAOSystemException
     */
    ArrayList getRolesByOrgPost(String org_pos_id) throws DAOSystemException;
    
    long deleteByOrgPost(String org_pos_id) throws DAOSystemException;
}
