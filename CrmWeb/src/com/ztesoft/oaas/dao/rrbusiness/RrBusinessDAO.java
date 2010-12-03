package com.ztesoft.oaas.dao.rrbusiness;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RrBusinessVO;

public interface RrBusinessDAO extends DAO {

	RrBusinessVO findByPrimaryKey(String pbusiness_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pbusiness_id,RrBusinessVO vo) throws DAOSystemException;

	long delete( String pbusiness_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
