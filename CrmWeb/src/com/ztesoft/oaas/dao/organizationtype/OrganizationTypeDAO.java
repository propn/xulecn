package com.ztesoft.oaas.dao.organizationtype;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.OrganizationTypeVO;

public interface OrganizationTypeDAO extends DAO {

	OrganizationTypeVO findByPrimaryKey(String porg_type_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String porg_type_id,OrganizationTypeVO vo) throws DAOSystemException;

	long delete( String porg_type_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    ArrayList getOrgTypesByOrgClass(String porg_class_id) throws DAOSystemException;
}
