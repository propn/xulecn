package com.ztesoft.oaas.dao.mmdataprivrule;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.MmDataPrivRuleVO;

public interface MmDataPrivRuleDAO extends DAO {

	MmDataPrivRuleVO findByPrimaryKey(String pprivilege_type) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pprivilege_type,MmDataPrivRuleVO vo) throws DAOSystemException;

	long delete( String pprivilege_type) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
	
	String findTranSQLByType( String type ) throws DAOSystemException;
	
	ArrayList getSimpleDataPrivilegeRule() throws DAOSystemException;
}
