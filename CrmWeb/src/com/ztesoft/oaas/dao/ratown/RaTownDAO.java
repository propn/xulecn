package com.ztesoft.oaas.dao.ratown;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RaTownVO;

public interface RaTownDAO extends DAO {

	RaTownVO findByPrimaryKey(String ptown_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String ptown_id,RaTownVO vo) throws DAOSystemException;

	long delete( String ptown_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
