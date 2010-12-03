package com.ztesoft.oaas.dao.racity;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RaCityVO;

public interface RaCityDAO extends DAO {

	RaCityVO findByPrimaryKey(String pcity_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pcity_id,RaCityVO vo) throws DAOSystemException;

	long delete( String pcity_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
